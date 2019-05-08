package com.lhm.springcloud.security.pojo;

import com.lhm.springcloud.security.entity.PermissionInfo;
import com.lhm.springcloud.security.entity.RoleInfo;
import lombok.Data;

import java.util.List;

/**
 * @ClassName AuthUserPoJo
 * @Description TODO
 * @Author liuheming
 * @Date 2019/5/6 11:50
 * @Version 1.0
 **/
@Data
public class AuthUserPoJo {
    private String userId;
    private String userName;
    private String passWord;
    private String status;
    private List<RoleInfo> roleInfos;
    private List<PermissionInfo> permissionInfos;
}
