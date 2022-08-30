package com.jackson.p2padmin2022.user.mapper;

import com.jackson.p2padmin2022.user.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo login(UserInfo userInfo);

    void updateLoginCountAndLoginTimeById(UserInfo userInfo);
}