package com.lhm.springcloud.security.pojo;

import org.springframework.security.core.GrantedAuthority;

/**
 * @ClassName MyGrantedAuthority
 * @Description TODO
 * @Author liuheming
 * @Date 2019/5/7 10:39
 * @Version 1.0
 **/
public class MyGrantedAuthority implements GrantedAuthority {
    private String url;
    private String method;

    public String getPermissionUrl() {
        return url;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.url = permissionUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MyGrantedAuthority(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public String getAuthority() {
        return this.url + ";" + this.method;
    }
}
