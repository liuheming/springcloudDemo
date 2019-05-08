package com.lhm.springcloud.security.constant;

public class GlobalCons {

    // 返回变量
    public static final String CODE = "code";
    public static final String MSG = "message";
    public static final String DATA = "data";

    public static final String TYPE = "type";

    // status状态码
    public abstract static class STATUS {
        /**
         * 数据库记录启用状态
         */
        public static final String ENABLE = "enable";
        /**
         * 数据库记录停用状态
         */
        public static final String DISABLE = "disable";
    }

    // redis配置
    public abstract static class REDIS {
        /**
         * 缓存前缀
         */
        public static final String PREFIX = "iptv_mp_";

        /**
         * 获取redis微信登录用户信息前缀
         */
        public static final String WX_AUTH_USERINFO = PREFIX + "wx_auth_userinfo_";
    }


}
