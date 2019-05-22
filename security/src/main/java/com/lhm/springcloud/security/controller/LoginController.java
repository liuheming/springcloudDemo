package com.lhm.springcloud.security.controller;

import com.lhm.springcloud.security.constant.ResultCode;
import com.lhm.springcloud.security.exception.CommonException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Exrickx
 */

@RestController
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {

    @RequestMapping(value = "/needLogin")
    public String needLogin() throws CommonException {
        throw new CommonException(ResultCode.UNAUTHORIZED, "请登陆后操作");
        //return ResUtil.getJsonStr(ResultCode.UNAUTHORIZED, "请登陆后操作");
    }

}
