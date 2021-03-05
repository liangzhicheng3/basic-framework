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
import com.qiniu.util.StringMap;
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
     * @description 上传处理，MultipartFile方式
     * @param bucket
     * @param file
     * @return String
     */
    public static String upload(String bucket, MultipartFile file) {
        try {
            Configuration cfg = new Configuration(Zone.zone0());//Zone.zone0()->华东
            UploadManager uploadManager = new UploadManager(cfg);
            String token = generateToken(bucket);
            String key = SysToolUtil.generateId() + SysToolUtil.random();
            String suffix = SysToolUtil.getFileSuffix(file);
            Response response = uploadManager.put(file.getBytes(), key + suffix, token);
            SysToolUtil.info("--- SysQiniuUtil upload : " + response.bodyString(), SysQiniuUtil.class);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            SysToolUtil.info(r.toString());
            try {
                SysToolUtil.info(r.bodyString());
            } catch (QiniuException e) {
                SysToolUtil.info("--- QiniuException : r.bodyString()");
            }
        } catch (IOException e) {
            SysToolUtil.info("--- SysQiniuUtil IOException : " + e.getMessage(), SysQiniuUtil.class);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 路径方式
     * @param filePath
     * @param key
     * @return String
     */
    public static String upload(String filePath, String key) {
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(Constants.QINIU_APP_KEY, Constants.QINIU_APP_SECRECT);
        String token = getUpToken(Constants.QINIU_BUCKET_FILES, key , auth);
        try {
            Response response = uploadManager.put(filePath, key, token);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            SysToolUtil.info(Constants.QINIU_PREFIX_FILES + putRet.key);
            return Constants.QINIU_PREFIX_FILES + putRet.key;
        } catch (QiniuException e) {
            SysToolUtil.info("--- SysQiniuUtil uploadFile error : " + e.response.statusCode);
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
     * @description 获取上传凭证
     * @param bucketName 空间名称
     * @param key
     * @param auth
     * @return String
     */
    public static String getUpToken(String bucketName, String key, Auth auth) {
        //insertOnly 如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1
        return auth.uploadToken(bucketName, key, 3600, new StringMap().put("insertOnly", 0));   //覆盖上传，访问时需要刷新缓存?v=时间戳
    }

    /**
     * @description 文件上传处理
     * @param file
     * @return String
     */
    public static String uploadFile(MultipartFile file){
        String fileName = "";
        try{
            if(file != null && file.getSize() > 0){
                fileName = upload(Constants.QINIU_BUCKET_FILES, file);
                if (SysToolUtil.isNotBlank(fileName)) {
                    fileName = Constants.QINIU_PREFIX_FILES + fileName;
                }
            }
        }catch(Exception e){
            SysToolUtil.info("--- SysQiniuUtil uploadFile error : " + e.getMessage());
            e.printStackTrace();
        }
        return fileName;
    }

}
