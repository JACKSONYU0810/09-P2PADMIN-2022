package com.jackson.seal2022.service.impl;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jackson.seal2022.config.SystemConfig;
import com.jackson.seal2022.constants.Constants;
import com.jackson.seal2022.fastdfs.FastdfsClient;
import com.jackson.seal2022.model.BidInfoVO;
import com.jackson.seal2022.model.CreditorVO;
import com.jackson.seal2022.pdf.PdfProcesser;
import com.jackson.seal2022.service.SealService;
import com.jackson.seal2022.utils.MySealUtils;
import com.jackson.seal2022.utils.MyStringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * ClassName: SealServiceImpl
 * Package: com.jackson.seal2022.service.impl
 * Description:
 *
 * @Date: 9/14/2022 3:00 PM
 * @Author: JacksonYu
 */
@Service
public class SealServiceImpl implements SealService {

    //1.定义合同模板对象
    private static final String CONTRACT_TEMPLATE = "loan_contract.pdf";

    @Override
    public String pdfSeal(CreditorVO creditorVO) {
        PdfReader pdfReader = null;
        FileOutputStream fos = null;
        PdfStamper pdfStamper = null;
        try {
            //2.通过类加载器获取模板对象
            String pdfFile = Thread.currentThread().getContextClassLoader().getResource(CONTRACT_TEMPLATE).getFile();

            //3.将模板对象通过pdfReader加载到内存中
            pdfReader = new PdfReader(pdfFile);

            //4.设置该模板对象的临时存放路径
            String contractTempPath = SystemConfig.getConfigProperty("loan_contract_path") + creditorVO.getApplyNo() + "_temp.pdf";

            //5.定义输出流，将模板对象输出到临时路径
            fos = new FileOutputStream(contractTempPath);

            //6.定义pdfStamper，用于设置pdf文件中的内容
            pdfStamper = new PdfStamper(pdfReader,fos);

            //6.1设置pdf编辑后不可进行更改,true表示不能进行更改
            pdfStamper.setFormFlattening(true);

            //6.2根据PDF模板文件中的表单元素的名字设置数据到临时的PDF文件中
            pdfStamper.getAcroFields().setField("protocolNumber", creditorVO.getApplyNo());
            pdfStamper.getAcroFields().setField("borrowerRealname", creditorVO.getBorrowerRealname());
            pdfStamper.getAcroFields().setField("borrowerIdcard",creditorVO.getBorrowerIdcard());
            pdfStamper.getAcroFields().setField("borrowerSex",creditorVO.getBorrowerSex());
            pdfStamper.getAcroFields().setField("borrowerPresentAddress",creditorVO.getBorrowerPresentAddress());
            pdfStamper.getAcroFields().setField("applyPurpose",creditorVO.getApplyPurpose());
            pdfStamper.getAcroFields().setField("returnWay","等额本息");
            pdfStamper.getAcroFields().setField("returnTerm",creditorVO.getAuditLoanTerm()+"");
            pdfStamper.getAcroFields().setField("returnDay","15");

            //7.将借款金额乘以100并转化成大写
            BigDecimal auditLoanMoney = creditorVO.getAuditLoanMoney();
            double money = auditLoanMoney.doubleValue();
            int num = (int) Math.round(money * 100);
            String strMoney = MyStringUtils.numToUpper(num);

            ///7.1将大写的金额填写到表单元素中
            for (int i = strMoney.length()-1; i >=0 ; i--) {
                String fieldName = "jk_0"+i;
                String fieldDate = strMoney.charAt(strMoney.length()-1-i)+"";
                pdfStamper.getAcroFields().setField(fieldName, fieldDate);
            }

            //8.将还款金额填到表单中
            Date collectFinishTime = creditorVO.getCollectFinishTime();

            //8.1 开始还款时间
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(collectFinishTime);
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
            Date startTime = startCalendar.getTime();

            //8.2结束还款时间
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(startTime);
            endCalendar.add(Calendar.MONTH, 1);
            Date endTime = endCalendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            pdfStamper.getAcroFields().setField("returnStartAndEndDate", sdf.format(startTime)+"--"+sdf.format(endTime));

            AcroFields acroFields = pdfStamper.getAcroFields();
            int pageNo = acroFields.getFieldPositions("Chapter").get(0).page;
            Rectangle rectangle = acroFields.getFieldPositions("Chapter").get(0).position;
            float x = rectangle.getBottom();
            float y = rectangle.getLeft();

            String imagePath = MySealUtils.drawCircularChapter(Constants.CHAPTER_NAME, Constants.CHAPTER_COMPANY_NAME, Constants.CHAPTER_SOCIAL_NUMBER);
            Image image = Image.getInstance(imagePath);
            image.scaleToFit(rectangle.getWidth(), rectangle.getHeight());
            image.setAbsolutePosition(x, y);
            PdfContentByte overContent = pdfStamper.getOverContent(pageNo);
            overContent.addImage(image);

            //10.数据填入完成后,将流进行关闭,否则数据可能填不上
            //这里关流,可能会关不上,所以需要在finaly中再次进行关闭
            pdfStamper.close();
            fos.close();
            pdfReader.close();

            //9.删除公章照片
            File file = new File(imagePath);
            if (file.exists()&&file.isFile()){
                file.delete();
            }

            //10.生成债权人列表pdf
            //10.1定义列表pdf临时路径
            String creditorTablePath = SystemConfig.getConfigProperty("loan_contract_path") + creditorVO.getApplyNo() + "_table.pdf";

            //10.2定义表格的表头数据
            ArrayList<String> tableHeaderList = new ArrayList<>();
            tableHeaderList.add("出借编号");
            tableHeaderList.add("出借人姓名");
            tableHeaderList.add("出借人身份证");
            tableHeaderList.add("出借人手机号");
            tableHeaderList.add("出借金额");
            tableHeaderList.add("出借期限");

            //10.3添加表格的内容
            ArrayList<String> tableDataList = new ArrayList<>();
            for (BidInfoVO bidInfoVO : creditorVO.getBidInfoVOList()) {
                tableDataList.add(bidInfoVO.getBidId()+"");
                tableDataList.add(bidInfoVO.getIdCard());
                tableDataList.add(bidInfoVO.getName());
                tableDataList.add(bidInfoVO.getPhone());
                tableDataList.add(bidInfoVO.getBidMoney()+"元");
                tableDataList.add(creditorVO.getAuditLoanTerm()+"个月");
            }

            //10.4生成表格并输出到指定路径
            PdfProcesser.createPdfTable(creditorTablePath, tableHeaderList, tableDataList);

            //10.5将表格拼接到合同文件上
            String finalContractPath = SystemConfig.getConfigProperty("loan_contract_path") + creditorVO.getApplyNo() + ".pdf";
            ArrayList<String> mergeList = new ArrayList<>();
            mergeList.add(contractTempPath);
            mergeList.add(creditorTablePath);

            PdfProcesser.mergePDFs(mergeList, finalContractPath, true);

            //11.将合同上传到fastdfs上进行保存
            String[] result = FastdfsClient.uploadFileToFastdfs(finalContractPath, "pdf");

            //11.2合同上传成功，删除临时文件
            File tempFile = new File(contractTempPath);
            if (tempFile.exists()&&tempFile.isFile()){
                tempFile.delete();
            }
            File tableFile = new File(creditorTablePath);
            if (tableFile.exists()&&tableFile.isFile()){
                tableFile.delete();
            }
            File finalFile = new File(finalContractPath);
            if (finalFile.exists()&&finalFile.isFile()){
                finalFile.delete();
            }

            System.out.println(result[0]);
            System.out.println(result[1]);

            return SystemConfig.getConfigProperty("loan_access_url_prefix")+result[0]+"/"+result[1];


        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (pdfStamper!=null){
                try {
                    pdfStamper.close();
                } catch (Exception e) {
                }
            }

            if (fos!=null){
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }

            if (pdfReader!=null){
                try {
                    pdfReader.close();
                } catch (Exception e) {
                }
            }

        }


        return null;
    }
}
