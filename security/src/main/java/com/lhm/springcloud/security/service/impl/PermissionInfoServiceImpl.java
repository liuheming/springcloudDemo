package com.lhm.springcloud.security.service.impl;

import com.lhm.springcloud.security.entity.PermissionInfo;
import com.lhm.springcloud.security.mapper.PermissionInfoMapper;
import com.lhm.springcloud.security.service.IPermissionInfoService;
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
public class PermissionInfoServiceImpl extends ServiceImpl<PermissionInfoMapper, PermissionInfo> implements IPermissionInfoService {

}
