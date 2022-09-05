package com.jackson.p2padmin2022.user.service.impl;

import com.jackson.p2padmin2022.user.mapper.PermissionInfoMapper;
import com.jackson.p2padmin2022.user.mapper.RoleInfoMapper;
import com.jackson.p2padmin2022.user.model.RoleInfo;
import com.jackson.p2padmin2022.user.service.RolesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RolesServiceImpl
 * Package: com.jackson.p2padmin2022.user.service.impl
 * Description:
 *
 * @Date: 9/3/2022 12:10 PM
 * @Author: JacksonYu
 */
@Service
public class RolesServiceImpl implements RolesService {

    @Resource
    private RoleInfoMapper roleInfoMapper;

    @Resource
    private PermissionInfoMapper permissionInfoMapper;

    @Override
    public List<RoleInfo> queryAllRoles() {
        return roleInfoMapper.selectAllRoles();
    }

    @Override
    public Object getPermissionTree(Integer roleId) {
        List<Map> mapList = permissionInfoMapper.getPermissionTree(roleId);

        return mapList;
    }

    @Transactional
    @Override
    public void disPermission(Integer roleId, Integer[] permissionIds) {
        //1.先删除原有权限，在新增权限
        roleInfoMapper.deletePermissionByRoleId(roleId);

        if (permissionIds==null){
            return;
        }

        for (Integer pid : permissionIds) {
            roleInfoMapper.insertPermissionByRoleIdAndPerId(roleId,pid);
        }
    }
}
