package com.liangzhicheng.common.utils;

import com.liangzhicheng.common.constant.Constants;
import org.apache.commons.mail.HtmlEmail;

/**
 * @description 邮箱验证码工具类
 * @author liangzhicheng
 * @since 2021-01-26
 */
public class SysEmailCodeUtil {

    public static void sendEmailCode(String emailAddress, String vcode) {
        try{
            HtmlEmail email = new HtmlEmail();
            email.setHostName(Constants.EMAIL_HOST_NAME); //126邮箱为smtp.126.com，163邮箱为smtp.163.com，QQ为smtp.qq.com
            email.setCharset("UTF-8");
            email.addTo(emailAddress); //收件地址
            email.setFrom(Constants.EMAIL_SEND_USER_ADDRESS, Constants.EMAIL_SEND_USER_NAME); //此处填邮箱地址和用户名，用户名可以任意填写
            email.setAuthentication(Constants.EMAIL_SEND_USER_ADDRESS, Constants.EMAIL_AUTH_CODE); //此处填写邮箱地址和客户端授权码
            email.setSubject(Constants.EMAIL_NAME); //此处填写邮件名，邮件名可任意填写
            email.setMsg("尊敬的企业会员，您本次验证码为：" + vcode); //此处填写邮件内容
            email.send();
        }catch(Exception e){
            e.printStackTrace();
            SysToolUtil.error("--- sendEmailCode error : " + e.getMessage(), SysEmailCodeUtil.class);
        }
    }

}
