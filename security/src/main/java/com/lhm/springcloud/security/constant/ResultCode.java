package com.lhm.springcloud.security.constant;

/**
 * HTTP状态码
 *
 * @author xzc
 */
public interface ResultCode {

    /**
     * socket 状态码
     */
    public final String MSG_TYPE_LOGIN = "login";

    public final String MSG_TYPE_HEARTBEAT = "pong";

    public final String MSG_TYPE_VOD = "vod";

    public final String MSG_TYPE_KEYBOARD = "keyboard";
    /**
     * 处理过程正常返回
     */
    public static final int OK = 200;

    /**
     * 已创建。成功请求并创建了新的资源
     */
    public static final int CREATED = 201;

    /**
     * 已接受。已经接受请求，但未处理完成
     */
    public static final int ACCEPTED = 202;

    /**
     * 非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本
     */
    public static final int NON_AUTHORITATIVE = 203;

    /**
     * 无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档
     */
    public static final int NO_CONTENT = 204;

    /**
     * 重置内容。服务器处理成功，用户终端（例如：浏览器）应重置文档视图。可通过此返回码清除浏览器的表单域
     */
    public static final int RESET_CONTENT = 205;

    /**
     * 部分内容。服务器成功处理了部分GET请求
     */
    public static final int PARTIAL_CONTENT = 206;

    /**
     * 多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端（例如：浏览器）选择
     */
    public static final int MULTIPLE_CHOICES = 300;

    /**
     * 永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替
     */
    public static final int MOVED_PERMANENTLY = 301;

    /**
     * 临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI
     */
    public static final int FOUND = 302;

    /**
     * 查看其它地址。与301类似。使用GET和POST请求查看
     */
    public static final int SEE_OTHER = 303;

    /**
     * 未修改。所请求的资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过的资源，
     * 通过提供一个头信息指出客户端希望只返回在指定日期之后修改的资源
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * 使用代理。所请求的资源必须通过代理访问
     */
    public static final int USE_PROXY = 305;

    /**
     * 临时重定向。与302类似。使用GET请求重定向
     */
    public static final int TEMPORARY_REDIRECT = 307;

    /**
     * 客户端请求的语法错误，服务器无法理解
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 请求要求用户的身份认证
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 服务器理解请求客户端的请求，但是拒绝执行此请求
     */
    public static final int FORBIDDEN = 403;

    /**
     * 服务器无法根据客户端的请求找到资源（网页）
     */
    public static final int NOT_FOUND = 404;

    /**
     * 客户端请求中的方法被禁止
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    /**
     * 服务器无法根据客户端请求的内容特性完成请求
     */
    public static final int NOT_ACCEPTABLE = 406;

    /**
     * 请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权
     */
    public static final int PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * 服务器等待客户端发送的请求时间过长，超时
     */
    public static final int REQUEST_TIME_OUT = 408;

    /**
     * 服务器完成客户端的PUT请求是可能返回此代码，服务器处理请求时发生了冲突
     */
    public static final int CONFLICT = 409;

    /**
     * 客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置
     */
    public static final int GONE = 410;

    /**
     * 服务器无法处理客户端发送的不带Content-Length的请求信息
     */
    public static final int LENGTH_REQUIRED = 411;

    /**
     * 客户端请求信息的先决条件错误
     */
    public static final int PRECONDITION_FAILED = 412;

    /**
     * 由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息
     */
    public static final int REQUEST_ENTITY_TOO_LARGE = 413;

    /**
     * 请求的URI过长（URI通常为网址），服务器无法处理
     */
    public static final int REQUEST_URI_ENTITY_TOO_LARGE = 414;

    /**
     * 服务器无法处理请求附带的媒体格式
     */
    public static final int UNSUPPORTED_MDDIA_TYPE = 415;

    /**
     * 客户端请求的范围无效
     */
    public static final int RANGE_NOT_SATISFIABLE = 416;

    /**
     * 客户端请求的范围无效
     */
    public static final int EXPECTATION_FAILED = 417;

    /**
     * 服务器内部错误，无法完成请求
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    /**
     * 服务器不支持请求的功能，无法完成请求
     */
    public static final int NOT_IMPLEMENTED = 501;

    /**
     * 充当网关或代理的服务器，从远端服务器接收到了一个无效的请求
     */
    public static final int BAD_GATEWAY = 502;

    /**
     * 由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     */
    public static final int SERVICE_UNAVAILABLE = 503;

    /**
     * 充当网关或代理的服务器，未及时从远端服务器获取请求
     */
    public static final int GATEWAY_TIME_OUT = 504;

    /**
     * 服务器不支持请求的HTTP协议的版本，无法完成处理
     */
    public static final int HTTP_VERSION_NOT_SUPPORTED = 505;

    /**
     * 返回成功状态说明
     */
    public static final String SUC_MSG = "操作成功";

    /**
     * 返回失败状态说明
     */
    public static final String FAIL_MSG = "操作失败";

}
