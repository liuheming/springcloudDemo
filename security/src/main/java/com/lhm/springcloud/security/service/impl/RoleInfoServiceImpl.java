package com.lhm.springcloud.security.service.impl;

import com.lhm.springcloud.security.entity.RoleInfo;
import com.lhm.springcloud.security.mapper.RoleInfoMapper;
import com.lhm.springcloud.security.service.IRoleInfoService;
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
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements IRoleInfoService {

}
