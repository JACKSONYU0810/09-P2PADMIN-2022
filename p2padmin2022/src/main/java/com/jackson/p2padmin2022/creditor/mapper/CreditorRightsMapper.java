package com.jackson.p2padmin2022.creditor.mapper;

import com.jackson.p2padmin2022.creditor.model.CreditorRights;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CreditorRightsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CreditorRights record);

    int insertSelective(CreditorRights record);

    CreditorRights selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CreditorRights record);

    int updateByPrimaryKey(CreditorRights record);

    List<Map> selectContract();

    void insertContractPath(Integer creditorId, String loanContractPath);

    String selectLoanContractPathByCreditorId(Integer creditorId);


}