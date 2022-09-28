package com.jackson.p2padmin2022.creditor.service.impl;

import com.jackson.p2padmin2022.creditor.fdfs.FastdfsClient;
import com.jackson.p2padmin2022.creditor.mapper.CreditorRightsMapper;
import com.jackson.p2padmin2022.creditor.service.CreditorService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CreditorServiceImpl
 * Package: com.jackson.p2padmin2022.creditor.service.impl
 * Description:
 *
 * @Date: 9/7/2022 5:21 PM
 * @Author: JacksonYu
 */
@Service
public class CreditorServiceImpl implements CreditorService {

    @Resource
    private CreditorRightsMapper creditorRightsMapper;

    @Override
    public List<Map> showContract() {
        return creditorRightsMapper.selectContract();
    }

    @Override
    public void createContract(Integer creditorId) {
        RestTemplate restTemplate = new RestTemplate();
        String loanContractPath = restTemplate.getForObject("http://localhost:8081/seal?creditorId=" + creditorId, String.class);

        creditorRightsMapper.insertContractPath(creditorId,loanContractPath);
    }

    @Override
    public void deleteContract(Integer creditorId) {
        String loanContractPath = creditorRightsMapper.selectLoanContractPathByCreditorId(creditorId);
        String subPath = loanContractPath.substring(loanContractPath.indexOf("group"));
        String groupName = subPath.substring(0,subPath.indexOf("/"));
        String remoteFileName = subPath.substring(subPath.indexOf("/")+1);

        FastdfsClient.deleteFileFromFastdfs(groupName, remoteFileName);

        creditorRightsMapper.insertContractPath(creditorId,"");
    }

}
