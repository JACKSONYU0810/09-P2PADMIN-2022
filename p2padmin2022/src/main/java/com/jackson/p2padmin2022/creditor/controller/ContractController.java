package com.jackson.p2padmin2022.creditor.controller;

import com.jackson.p2padmin2022.creditor.service.CreditorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ContractController
 * Package: com.jackson.p2padmin2022.creditor
 * Description:
 *
 * @Date: 9/7/2022 5:12 PM
 * @Author: JacksonYu
 */
@Controller
public class ContractController {

    @Resource
    private CreditorService creditorService;

    @RequestMapping("/admin/contract")
    public String showContract(Model model){
        List<Map> mapList = creditorService.showContract();
        model.addAttribute("mapList", mapList);
        return "contract";
    }

    @RequestMapping("/createContract")
    public String createContract(Model model,Integer creditorId){
        creditorService.createContract(creditorId);
        model.addAttribute("message", "合同生成成功，点击确认返回合同列表");
        model.addAttribute("url","/admin/contract" );
        return "success";
    }

    @RequestMapping("/deleteContract")
    public String deleteContract(Model model,Integer creditorId){
        creditorService.deleteContract(creditorId);
        model.addAttribute("message", "合同删除成功，点击确认返回合同列表");
        model.addAttribute("url","/admin/contract" );
        return "success";
    }


}
