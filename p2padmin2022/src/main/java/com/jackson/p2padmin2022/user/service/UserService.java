package com.jackson.p2padmin2022.user.service;

import com.jackson.p2padmin2022.user.model.UserInfo;

import java.util.List;

/**
 * ClassName: UserService
 * Package: com.jackson.p2padmin2022.user.service
 * Description:
 *
 * @Date: 8/27/2022 6:51 PM
 * @Author: JacksonYu
 */
public interface UserService {
    UserInfo login(UserInfo userInfo);

    List<UserInfo> queryAllUsers();

}
