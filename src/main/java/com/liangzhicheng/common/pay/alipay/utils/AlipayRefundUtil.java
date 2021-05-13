package com.liangzhicheng.common.pay.alipay.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.liangzhicheng.common.utils.SysToolUtil;
import net.sf.json.JSONObject;

/**
 * @description 支付宝金额退款工具类
 * @author liangzhicheng
 * @since 2020-08-12
 */
public class AlipayRefundUtil {

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
    public static AlipayTradeRefundResponse refund(String url, String appId, String privateKey, String format, String charset, String publicKey, String signType/*, xxxOrder order*/) {
        SysToolUtil.info("--- alipayRefund come start ///");
        JSONObject json = new JSONObject();
        //json.element("out_trade_no", order.getOrderNo());
        //json.element("refund_amount", order.getTotal() + "");
        //json.element("refund_amount", "0.01");
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, format, charset, publicKey, signType);
        AlipayTradeRefundRequest AlipayRequest = new AlipayTradeRefundRequest();
        AlipayRequest.setBizContent(json.toString());
        try {
            SysToolUtil.info("-------------------------------------///--------------------------------------", AlipayRefundUtil.class);
            AlipayTradeRefundResponse refundResponse = alipayClient.execute(AlipayRequest);
            return refundResponse;
        } catch (AlipayApiException e) {
            SysToolUtil.info("--- alipayRefund error : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
