package com.liangzhicheng.config.http;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * @description HttpDeleteRequest相关类
 * @author liangzhicheng
 * @since 2021-03-05
 */
public class HttpDeleteRequest extends HttpEntityEnclosingRequestBase {

    public static final String METHOD_NAME = "DELETE";

    /**
     * @description 获取方法(必须重载)
     * @return
     */
    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

    public HttpDeleteRequest(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public HttpDeleteRequest(final URI uri) {
        super();
        setURI(uri);
    }

    public HttpDeleteRequest() {
        super();
    }

}
