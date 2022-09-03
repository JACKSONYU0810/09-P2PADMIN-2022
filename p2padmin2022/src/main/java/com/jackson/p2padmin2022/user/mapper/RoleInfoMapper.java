package com.jackson.p2padmin2022.user.mapper;

import com.jackson.p2padmin2022.user.model.RoleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);

    List<RoleInfo> selectAllRoles();

}