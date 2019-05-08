/**
 *
 */
package com.lhm.springcloud.security.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lhm.springcloud.security.constant.GlobalCons;
import com.lhm.springcloud.security.constant.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ResUtil {

    private static Logger log = LoggerFactory.getLogger(ResUtil.class);

    private static final String IPTV_STATIC_PREFIX = "/test/iptv-static/";

    /**
     * 将处理成功的数据封装至json data字段，并处理时间格式后返回对应json字符串
     *
     * @param data
     * @return
     */
    public static String getSucJsonData(Object data) {
        return JSON.toJSONStringWithDateFormat(getJson(ResultCode.OK, "success", data), DateUtil.DATETIME_FORMAT_SECOND,
                SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 根据传入的响应code和具体描述信息msg封装返回json字符串
     *
     * @param code
     * @param msg
     * @return "{"code":${code},"msg":${msg}}"
     */
    public static String getJsonStr(int code, String msg) {
        return getJson(code, msg).toJSONString();
    }

    /**
     * 根据传入的响应code、详细信息对象、描述信息msg封装返回json对象
     *
     * @param code
     * @param data
     * @param msg
     * @return "{"code":${code},"data":${data},"msg":${msg}}"
     */
    public static JSONObject getJson(int code, String msg, Object data) {
        JSONObject json = new JSONObject();
        json.put(GlobalCons.CODE, code);
        json.put(GlobalCons.MSG, msg);
        json.put(GlobalCons.DATA, data);
        return json;
    }

    /**
     * 根据传入的响应code、详细信息对象、描述信息msg封装返回json对象
     *
     * @param code
     * @param data
     * @param msg
     * @return "{"code":${code},"data":${data},"msg":${msg}}"
     */
    public static String getJsonStr(int code, String msg, Object data) {
        JSONObject json = new JSONObject();
        json.put(GlobalCons.CODE, code);
        json.put(GlobalCons.DATA, data);
        json.put(GlobalCons.MSG, msg);
        return JSON.toJSONStringWithDateFormat(json, DateUtil.DATETIME_FORMAT_SECOND,
                SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 根据传入的响应code、详细信息对象、描述信息msg封装返回json对象
     *
     * @param code
     * @param data
     * @param msg
     * @param dataFormat
     * @return "{"code":${code},"data":${data},"msg":${msg}}"
     */
    public static String getJsonStr(int code, String msg, Object data, String dataFormat) {
        JSONObject json = new JSONObject();
        json.put(GlobalCons.CODE, code);
        json.put(GlobalCons.DATA, data);
        json.put(GlobalCons.MSG, msg);
        return JSON.toJSONStringWithDateFormat(json, dataFormat,
                SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 根据传入的响应code和具体描述信息msg封装返回json对象
     *
     * @param code
     * @param msg
     * @return {"code":${code},"msg":${msg}}
     */
    public static JSONObject getJson(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put(GlobalCons.CODE, code);
        json.put(GlobalCons.MSG, msg);
        return json;
    }

    /**
     * 基于本工具类封装的json字符串结果，判断是否service正常处理
     *
     * @param res
     * @return
     */
    public static boolean isResRight(String res) {
        try {
            JSONObject json = JSONObject.parseObject(res);
            if (json.containsKey(GlobalCons.CODE) && ResultCode.OK == json.getInteger(GlobalCons.CODE)) {
                return true;
            }
        } catch (Exception e) {
            log.error("ResUtil parse res error:{}", e.getMessage());
        }
        return false;
    }

    /**
     * 返回指定描述的失败msg -- service中用于直接返回controller解析的内容
     *
     * @param des
     * @return "{code:ResultCode.ILLEGAL_PARAM(400),msg:xx}"
     */
    public static String getErrDes(String des) {
        return ResUtil.getJsonStr(ResultCode.INTERNAL_SERVER_ERROR, des);
    }

    /**
     * 返回指定描述的成功msg -- service中用于直接返回controller解析的内容
     *
     * @param des
     * @return "{code:xx,msg:xx}"
     */
    public static String getSucDes(String des) {
        return ResUtil.getJsonStr(ResultCode.OK, des);
    }

    /**
     * 通过boolean生成返回结果
     *
     * @param b b
     * @return String
     */
    public static String getMsgBoo(Boolean b) {
        if (b) {
            return getSucDes("操作成功");
        }
        return getErrDes("操作失败");
    }

    /**
     * 返回需要登录的提示
     *
     * @return
     */
    public static String loginNeeded() {
        return getJsonStr(ResultCode.UNAUTHORIZED, "登录超时，请重新登录");
    }

    public static String getSocketJson(String type, int code, String msg, Object data) {
        JSONObject result = new JSONObject();
        result.put(GlobalCons.TYPE, type);
        result.put(GlobalCons.CODE, code);
        result.put(GlobalCons.MSG, msg);
        result.put("payload", data);
        return result.toJSONString();
    }

    public static String getSocketJson(String type, int code, String msg) {
        JSONObject result = new JSONObject();
        result.put(GlobalCons.TYPE, type);
        result.put(GlobalCons.CODE, code);
        result.put(GlobalCons.MSG, msg);
        result.put("payload", new JSONObject());
        return result.toJSONString();
    }

    public static String getStaticPrefix(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getHeader("x-forwarded-host") + IPTV_STATIC_PREFIX;
    }
}
