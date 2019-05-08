package com.lhm.springcloud.security.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author security
 * @since 2019-05-06
 */
@Data
public class PermissionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    private String id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 接口路径
     */
    private String path;

    /**
     * 说明
     */
    private String description;

    private String createBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String updateBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 角色状态'ENABLE'开启,'DISABLE'关闭
     */
    private String status;

    /**
     * 请求方式
     */
    private String method;

}
