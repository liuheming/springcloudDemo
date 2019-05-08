package com.lhm.springcloud.security.pojo;

import com.lhm.springcloud.security.constant.UserConstant;
import com.lhm.springcloud.security.entity.PermissionInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Exrickx
 */

public class AuthUserDetails extends AuthUserPoJo implements UserDetails {

    private static final long serialVersionUID = 1L;

    public AuthUserDetails(AuthUserPoJo user) {
        if (user != null) {
            this.setUserName(user.getUserName());
            this.setPassWord(user.getPassWord());
            this.setStatus(user.getStatus());
            this.setRoleInfos(user.getRoleInfos());
            this.setPermissionInfos(user.getPermissionInfos());
        }
    }

    //将角色权限 放入GrantedAuthorit的自定义实现类MyGrantedAuthority中  为权限判定提供数据
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        List<PermissionInfo> permissions = this.getPermissionInfos();
        if (permissions != null) {
            for (PermissionInfo permission : permissions) {
                GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getPath(), permission.getMethod());
                authorityList.add(grantedAuthority);
            }
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return super.getPassWord();
    }

    @Override
    public String getUsername() {
        return super.getUserName();
    }


    /**
     * 账户是否过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    /**
     * 是否禁用
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    /**
     * 密码是否过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    /**
     * 是否启用
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return UserConstant.USER_STATUS_NORMAL.equals(this.getStatus()) ? true : false;
    }
}