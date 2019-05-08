package com.lhm.springcloud.security.constant;

/**
 * @ClassName UserConstant
 * @Description 用户常量
 * @Author liuheming
 * @Date 2019/5/6 15:18
 * @Version 1.0
 **/
public interface SecurityConstant {
    /**
     * token参数头
     */
    String HEADER = "Authorization";

    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer ";

    /**
     * token中自定义权限标识
     */
    String AUTHORITIES = "authorities";
    /**
     * token失效时间
     */
    Integer tokenExpirationTime = 360;

    /**
     * Token 发行人
     */
    String tokenIssuer = "lhm";

    /**
     * JWT签名加密key
     */
    String tokenSigningKey = "liuheming";

    /**
     * 刷新Token时间
     */
    Integer refreshTokenExpTime = 720;

}
