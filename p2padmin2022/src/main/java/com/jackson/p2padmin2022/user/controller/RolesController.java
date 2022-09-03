package com.jackson.p2padmin2022.user.controller;

import com.jackson.p2padmin2022.user.mapper.PermissionInfoMapper;
import com.jackson.p2padmin2022.user.model.RoleInfo;
import com.jackson.p2padmin2022.user.service.RolesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName: RolesController
 * Package: com.jackson.p2padmin2022.user.controller
 * Description:
 *
 * @Date: 9/3/2022 12:06 PM
 * @Author: JacksonYu
 */
@Controller
public class RolesController {

    @Resource
    private RolesService rolesService;

    @RequestMapping("/admin/roles")
    public String roles(Model model) {

        List<RoleInfo> roleInfoList = rolesService.queryAllRoles();
        model.addAttribute("roleInfoList", roleInfoList);

        return "roles";
    }

    @RequestMapping("/toDisPermission/{roleId}")
    public String toDisPermission(@PathVariable Integer roleId,Model model){
        model.addAttribute("roleId", roleId);

        return "permission";
    }

    @RequestMapping("/toPermissionTree/{roleId}")
    @ResponseBody
    public Object toPermissionTree(@PathVariable Integer roleId,Model model){
        Object object = rolesService.getPermissionTree(roleId);

        return object;
    }

}
