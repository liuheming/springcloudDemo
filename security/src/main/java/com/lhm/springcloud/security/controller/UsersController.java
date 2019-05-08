package com.lhm.springcloud.security.controller;


import com.lhm.springcloud.security.utils.ResUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuheming
 * @since 2019-05-06
 */
@RestController
@RequestMapping("/security/users")
public class UsersController {
    @GetMapping
    public String get(){
       return ResUtil.getJsonStr(200,"secc");
    }

    @PostMapping
    public String post(){
        return ResUtil.getJsonStr(200,"secc");
    }
}
