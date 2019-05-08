package com.lhm.springcloud.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuheming
 * @since 2019-05-06
 */
@Data
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 用户头像地址
     */
    private String avatar;

    /**
     * 手机号
     */
    private Integer mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 是否生效
     */
    private String status;


}
