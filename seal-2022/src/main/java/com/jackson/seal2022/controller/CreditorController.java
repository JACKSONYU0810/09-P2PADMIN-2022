package com.jackson.seal2022.controller;

import com.jackson.seal2022.model.BidInfoVO;
import com.jackson.seal2022.model.CreditorVO;
import com.jackson.seal2022.service.SealService;
import javafx.scene.control.RadioMenuItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * ClassName: CreditorController
 * Package: com.jackson.seal2022.controller
 * Description:
 *
 * @Date: 9/14/2022 2:52 PM
 * @Author: JacksonYu
 */
@RestController
public class CreditorController {

    @Resource
    private SealService sealService;

    @RequestMapping("/seal")
    @ResponseBody
    public Object seal(Integer creditorId){
        CreditorVO creditorVO = new CreditorVO();
        creditorVO.setApplyNo("0001");
        creditorVO.setApplyPurpose("买手机");
        creditorVO.setAuditLoanMoney(new BigDecimal(12000));
        creditorVO.setAuditLoanTerm(1);
        creditorVO.setBorrowerRealname("张三");
        creditorVO.setBorrowerIdcard("123456789987654321");
        creditorVO.setBorrowerPresentAddress("北京大兴");
        creditorVO.setBorrowerSex("男");
        creditorVO.setCollectFinishTime(new Date());

        //定义creditorVO中的bidInfoList属性

        BidInfoVO bidInfoVO1=new BidInfoVO();
        bidInfoVO1.setBidMoney(new BigDecimal(500));
        bidInfoVO1.setIdCard("123456");
        bidInfoVO1.setName("张三");
        bidInfoVO1.setPhone("13700000000");
        bidInfoVO1.setBidId(11);
        bidInfoVO1.setUserId(1);

        BidInfoVO bidInfoVO2=new BidInfoVO();
        bidInfoVO2.setBidMoney(new BigDecimal(1000));
        bidInfoVO2.setIdCard("654321");
        bidInfoVO2.setName("李四");
        bidInfoVO2.setPhone("13800000000");
        bidInfoVO2.setBidId(11);
        bidInfoVO2.setUserId(10);

        BidInfoVO bidInfoVO3=new BidInfoVO();
        bidInfoVO3.setBidMoney(new BigDecimal(5000));
        bidInfoVO3.setIdCard("10987654");
        bidInfoVO3.setName("王五");
        bidInfoVO3.setPhone("13900000000");
        bidInfoVO3.setBidId(11);
        bidInfoVO3.setUserId(20);

        ArrayList<BidInfoVO> bidInfoList = new ArrayList<>();
        bidInfoList.add(bidInfoVO1);
        bidInfoList.add(bidInfoVO2);
        bidInfoList.add(bidInfoVO3);

        creditorVO.setBidInfoVOList(bidInfoList);

        //生成合同，完成电子签章，返回合同存放路径
        String result = sealService.pdfSeal(creditorVO);

        return result;
    }

}
