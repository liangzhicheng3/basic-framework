package com.liangzhicheng.common.pay.wechatpay.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.liangzhicheng.common.utils.SysToolUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @description 金额工具类
 * @author liangzhicheng
 * @since 2020-08-12
 */
public class FeeUtil {

    /**
     * @description WeChat支付退款方法
     * @param appId
     * @param mchId
     * @param outTradeNo
     * @param outRefundNo
     * @param totalFee
     * @param refundFee
     * @return String
     */
    public static String weChatRefund(String appId, String mchId, String outTradeNo, String outRefundNo, int totalFee, int refundFee, String secret, String pathCert){
        SysToolUtil.info("--- weChatRefund come start ///");
        try {
            StringBuilder sb = new StringBuilder();
            SortedMap<String, Object> sp = new TreeMap<String, Object>();
            sp.put("appid", appId);
            sp.put("mch_id", mchId);
            sp.put("nonce_str", MD5Util.encryptString(System.currentTimeMillis()/1000 + "", true));
            sp.put("out_trade_no", outTradeNo);
            sp.put("out_refund_no", outRefundNo);
            sp.put("total_fee", totalFee);
            sp.put("refund_fee", refundFee);
            sp.put("sign", SignUtil.signApp(sp, secret));
            String xml = SysToolUtil.mapToXml(sp);
            SysToolUtil.info("------ weChatRefund xml : \n" + xml, FeeUtil.class);
            KeyStore keyStore  = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(pathCert));
            try {
                keyStore.load(instream, mchId.toCharArray());
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            try {
                HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
                HttpEntity reqEntity = MultipartEntityBuilder.create().addTextBody("xml", xml).build();
                httpPost.setEntity(reqEntity);
                SysToolUtil.info("--- executing request" + httpPost.getRequestLine(), FeeUtil.class);
                CloseableHttpResponse response = httpclient.execute(httpPost);
                try {
                    HttpEntity entity = response.getEntity();
                    SysToolUtil.info("-------------------------------------///--------------------------------------", FeeUtil.class);
                    SysToolUtil.info("--- response.getStatusLine() : " + response.getStatusLine().toString(), FeeUtil.class);
                    if (entity != null) {
                        SysToolUtil.info("--- Response content length: " + entity.getContentLength(), FeeUtil.class);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                        String text;
                        while ((text = bufferedReader.readLine()) != null) {
                            sb.append(text);
                            SysToolUtil.info("--- text : " + text, FeeUtil.class);
                        }
                    }
                    EntityUtils.consume(entity);
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
            return sb.toString();
        } catch (Exception e) {
            SysToolUtil.info("--- weChatRefund error : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description Alipay支付退款方法
     * @param url
     * @param appId
     * @param privateKey
     * @param format
     * @param charset
     * @param publicKey
     * @param signType
     * @return AlipayTradeRefundResponse
     */
    public static AlipayTradeRefundResponse alipayRefund(String url, String appId, String privateKey, String format, String charset, String publicKey, String signType/*, xxxOrder order*/) {
        JSONObject json = new JSONObject();
        //json.element("out_trade_no", order.getOrderNo());
        //json.element("refund_amount", order.getTotal() + "");
        //json.element("refund_amount", "0.01");
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, format, charset, publicKey, signType);
        AlipayTradeRefundRequest AlipayRequest = new AlipayTradeRefundRequest();
        AlipayRequest.setBizContent(json.toString());
        try {
            AlipayTradeRefundResponse refundResponse = alipayClient.execute(AlipayRequest);
            return refundResponse;
        } catch (AlipayApiException e) {
            SysToolUtil.info("--- alipayRefund error : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
