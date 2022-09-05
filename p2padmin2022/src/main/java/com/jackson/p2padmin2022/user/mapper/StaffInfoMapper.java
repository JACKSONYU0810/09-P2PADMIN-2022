package com.jackson.p2padmin2022.user.mapper;

import com.jackson.p2padmin2022.user.model.StaffInfo;

public interface StaffInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StaffInfo record);

    int insertSelective(StaffInfo record);

    StaffInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StaffInfo record);

    int updateByPrimaryKey(StaffInfo record);
}