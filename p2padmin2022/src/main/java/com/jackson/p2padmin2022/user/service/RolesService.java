package com.jackson.p2padmin2022.user.service;

import com.jackson.p2padmin2022.user.model.RoleInfo;

import java.util.List;

/**
 * ClassName: RolesService
 * Package: com.jackson.p2padmin2022.user.service
 * Description:
 *
 * @Date: 9/3/2022 12:09 PM
 * @Author: JacksonYu
 */
public interface RolesService {
    List<RoleInfo> queryAllRoles();

    Object getPermissionTree(Integer roleId);
}
