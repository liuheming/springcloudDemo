package com.lhm.springcloud.security.service;

import com.lhm.springcloud.security.entity.RoleInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author security
 * @since 2019-05-06
 */
public interface IRoleInfoService extends IService<RoleInfo> {
    public List<RoleInfo> findRoleInfoAndPermission();
}
