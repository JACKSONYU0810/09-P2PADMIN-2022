package com.jackson.p2padmin2022.creditor.service;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CreditorService
 * Package: com.jackson.p2padmin2022.creditor.service
 * Description:
 *
 * @Date: 9/7/2022 5:20 PM
 * @Author: JacksonYu
 */
public interface CreditorService {
    List<Map> showContract();

    void createContract(Integer creditorId);


    void deleteContract(Integer creditorId);
}
