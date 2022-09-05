package com.jackson.p2padmin2022.user.service.impl;

import com.jackson.p2padmin2022.user.mapper.PermissionInfoMapper;
import com.jackson.p2padmin2022.user.mapper.UserInfoMapper;
import com.jackson.p2padmin2022.user.model.PermissionInfo;
import com.jackson.p2padmin2022.user.model.UserInfo;
import com.jackson.p2padmin2022.user.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: UserServiceImpl
 * Package: com.jackson.p2padmin2022.user.service.impl
 * Description:
 *
 * @Date: 8/27/2022 6:53 PM
 * @Author: JacksonYu
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private PermissionInfoMapper permissionInfoMapper;

    @Override
    public UserInfo login(UserInfo userInfo) {
        userInfo = userInfoMapper.login(userInfo);

        if (ObjectUtils.allNull(userInfo)){
            return null;
        }

        //更新登录次数和登录时间
        userInfo.setLogincount(userInfo.getLogincount()==null?1:userInfo.getLogincount()+1);
        userInfo.setLastlogintime(new Date());
        userInfoMapper.updateLoginCountAndLoginTimeById(userInfo);

        //根据用户id，查询对应的菜单权限，并添加到用户信息中
        List<PermissionInfo> permissionInfoList = permissionInfoMapper.selectPermissionMenuByUid(userInfo.getId(),1);
        userInfo.setPermissionInfoList(permissionInfoList);

        //根据用户id，获取对应权限的url
        List<String> urlList = permissionInfoMapper.selectPermissionUrlByUid(userInfo.getId());
        HashMap<String, String> urlMap = new HashMap<>();
        for (String url : urlList) {
            urlMap.put(url, "");
        }
        userInfo.setMap(urlMap);

        return userInfo;
    }

    @Override
    public List<UserInfo> queryAllUsers() {
        return userInfoMapper.selectAllUser();
    }
}
