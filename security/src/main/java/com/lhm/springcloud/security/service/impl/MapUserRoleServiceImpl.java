package com.lhm.springcloud.security.service.impl;

import com.lhm.springcloud.security.entity.MapUserRole;
import com.lhm.springcloud.security.mapper.MapUserRoleMapper;
import com.lhm.springcloud.security.service.IMapUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author security
 * @since 2019-05-06
 */
@Service
public class MapUserRoleServiceImpl extends ServiceImpl<MapUserRoleMapper, MapUserRole> implements IMapUserRoleService {

}
