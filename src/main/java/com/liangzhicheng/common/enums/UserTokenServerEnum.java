package com.liangzhicheng.common.enums;

import com.liangzhicheng.common.constant.Constants;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum UserTokenServerEnum {

    //用户登录存储user对象的键值对
    LOGIN_TOKEN_SERVER(Constants.LOGIN_TOKEN_SERVER, Constants.LOGIN_TOKEN_EXPIRE_TIME_SERVER, TimeUnit.DAYS);

    private String prefix;
    private long expireTime;
    private TimeUnit timeUnit;

    UserTokenServerEnum(String prefix, long expireTime, TimeUnit timeUnit){
        this.prefix = prefix;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
    }

    public String join(String ... value){
        StringBuilder sb = new StringBuilder(100);
        sb.append(this.prefix);
        for (String s : value) {
            sb.append(":").append(s);
        }
        return sb.toString();
    }

}
