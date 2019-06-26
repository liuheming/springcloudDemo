package com.lhm.springcloud.security.service.impl;

import com.lhm.springcloud.security.entity.RoleInfo;
import com.lhm.springcloud.security.mapper.RoleInfoMapper;
import com.lhm.springcloud.security.service.IRoleInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author security
 * @since 2019-05-06
 */
@Service
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements IRoleInfoService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;
    /**
     * @Author liuheming
     * @Description 查询全部角色及对应权限
     * @Date 16:27 2019/6/13
     * @Param []
     * @return java.util.List<com.lhm.springcloud.security.entity.RoleInfo>
     **/
    @Override
    public List<RoleInfo> findRoleInfoAndPermission() {
        return roleInfoMapper.findRoleInfoAndPermission();
    }
}
