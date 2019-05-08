package com.lhm.springcloud.security.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author security
 * @since 2019-05-06
 */
public class MapUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户与角色关系id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "MapUserRole{" +
        "id=" + id +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
