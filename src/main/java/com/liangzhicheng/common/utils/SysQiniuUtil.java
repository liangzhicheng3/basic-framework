package com.liangzhicheng.common.utils;

import com.google.gson.Gson;
import com.liangzhicheng.common.constant.Constants;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description 七牛存储工具类
 * @author liangzhicheng
 * @since 2020-08-20
 */
public class SysQiniuUtil {

    /**
     * @description 生成上传token
     * @param bucket
     * @return String
     */
    public static String generateToken(String bucket) {
        Auth auth = Auth.create(Constants.QINIU_APP_KEY, Constants.QINIU_APP_SECRECT);
        return auth.uploadToken(bucket);
    }

    /**
     * @description 上传处理，byte[]方式
     * @param bucket
     * @param data
     * @return String
     */
    public static String upload(String bucket, byte[] data) {
        try {
            Configuration cfg = new Configuration(Zone.zone2());//Zone.zone2()->华南
            UploadManager uploadManager = new UploadManager(cfg);
            String token = generateToken(bucket);
            String key = SysToolUtil.generateId() + SysToolUtil.random();
            Response response = uploadManager.put(data, key, token);
            SysToolUtil.info("--- SysQiniuUtil upload : " + response.bodyString(), SysQiniuUtil.class);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException e) {
                System.err.println("--- QiniuException : r.bodyString()");
            }
        } catch (IOException e) {
            SysToolUtil.info("--- SysQiniuUtil IOException : " + e.getMessage(), SysQiniuUtil.class);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 上传处理，InputStream方式
     * @param bucket
     * @param is
     * @return String
     */
    public static String upload(String bucket, InputStream is) {
        try {
            Configuration cfg = new Configuration(Zone.zone2());//Zone.zone2()->华南
            UploadManager uploadManager = new UploadManager(cfg);
            String token = generateToken(bucket);
            String key = SysToolUtil.generateId() + SysToolUtil.random();
            Response response = uploadManager.put(is, key, token, null, null);
            SysToolUtil.info("--- SysQiniuUtil upload : " + response.bodyString(), SysQiniuUtil.class);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException e) {
                System.err.println("--- QiniuException : r.bodyString()");
            }
        } catch (IOException e) {
            SysToolUtil.info("--- SysQiniuUtil IOException : " + e.getMessage(), SysQiniuUtil.class);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 文件上传处理
     * @param file
     * @param type
     * @return String
     */
    public static String uploadFile(MultipartFile file, String type){
        String fileName = "";
        try{
            if(file != null && file.getSize() > 0){
                if("1".equals(type)){ //上传图片->type=1
                    fileName = upload(Constants.QINIU_BUCKET_IMAGE, file.getBytes());
                    if(SysToolUtil.isNotBlank(fileName)){
                        return Constants.QINIU_PREFIX_IMAGE + fileName;
                    }
                }else if("2".equals(type)){ //上传视频->type=2
                    fileName = upload(Constants.QINIU_BUCKET_VIDEO, file.getBytes());
                    if(SysToolUtil.isNotBlank(fileName)){
                        return Constants.QINIU_PREFIX_VIDEO + fileName;
                    }
                }
            }
        }catch(IOException e){
            SysToolUtil.info("--- SysQiniuUtil uploadFile error : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
