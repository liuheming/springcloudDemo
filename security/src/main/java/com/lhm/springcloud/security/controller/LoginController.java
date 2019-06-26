package com.lhm.springcloud.security.controller;

import com.lhm.springcloud.security.constant.ResultCode;
import com.lhm.springcloud.security.exception.CommonException;
import com.lhm.springcloud.security.utils.ResUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Exrickx
 */

@RestController
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping(value = "/needLogin")
    public String needLogin() throws CommonException {
        throw new CommonException(ResultCode.UNAUTHORIZED, "请登陆后操作");
        //return ResUtil.getJsonStr(ResultCode.UNAUTHORIZED, "请登陆后操作");
    }

    /**
     * @Author liuheming
     * @Description 用户退出，删除redis中的token，让派发的token失效
     * @Date 16:03 2019/6/26
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping(value = "/exit/{userName}")
    public String exit(@PathVariable("userName")String userName) throws CommonException {
        String token=stringRedisTemplate.opsForValue().get("token_"+userName);

        if(StringUtils.isBlank(token)){
            throw new CommonException(ResultCode.UNAUTHORIZED, "请勿重复退出");
        }
        stringRedisTemplate.delete("token_"+userName);

        return ResUtil.getSucDes("注销成功");
    }
}
