package com.jackson.p2padmin2022.user.mapper;

import com.jackson.p2padmin2022.user.model.PermissionInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PermissionInfo record);

    int insertSelective(PermissionInfo record);

    PermissionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PermissionInfo record);

    int updateByPrimaryKey(PermissionInfo record);

    List<PermissionInfo> selectPermissionMenuByUid(Integer uid, Integer pid);

    List<String> selectPermissionUrlByUid(Integer uid);
}