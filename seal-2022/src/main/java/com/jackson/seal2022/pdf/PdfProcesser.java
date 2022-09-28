package com.jackson.seal2022.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * PDF文件处理工具类
 *
 * @author yanglijun
 * 
 */
public class PdfProcesser {
	
	private static final Logger logger = Logger.getLogger(PdfProcesser.class);

    /**
     * 创建PDF表格文件
     *
     * @param outPdfTableFile
     * @param tableHeaderList
     * @param tableDataList
     */
    public static void createPdfTable (String outPdfTableFile, List<String> tableHeaderList, List<String> tableDataList) {
        
    	//创建一个文档对象纸张大小为A4
        Document doc = new Document();
        PdfWriter writer = null;
        try {
        	//设置要输出到磁盘上的文件名称
            writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(outPdfTableFile)));
            //设置作者信息
            doc.addAuthor("");
            //设置文档创建日期
            doc.addCreationDate();
            //设置标题
            doc.addTitle("");
            //设置值主题
            doc.addSubject("");
            //打开文档开始写内容
            doc.open();
            //设置字体
            //使用资源字体(ClassPath)
            String simkaiPath = PdfProcesser.class.getClassLoader().getResource("simkai.ttf").getFile().replaceAll("%20", " ");
            BaseFont baseFont = BaseFont.createFont(simkaiPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font chineseFont = new Font(baseFont, 10, Font.NORMAL, BaseColor.BLACK);
            
            //创建一个六列的表格
            PdfPTable table = new PdfPTable (tableHeaderList.size());
            //设置表格具体宽度
            table.setTotalWidth(100f);
            //设置每一列所占的长度
            table.setWidths(new float[] {13f, 14f, 24f, 16f, 19f, 14f});
            
            Paragraph titleP = new Paragraph("附件：甲方（出借人）：", chineseFont); 
            titleP.setAlignment(Paragraph.ALIGN_CENTER);
            titleP.setLeading(chineseFont.getSize() + 12);//设置行距
            
            PdfPCell titleCell = new PdfPCell(titleP);
            titleCell.setBorder(0);
            titleCell.setColspan(6);//标段名称 占一行 
            table.addCell(titleCell);

            //创建表头
            for (int i=0; i<tableHeaderList.size(); i++) {
            	PdfPCell cell = new PdfPCell();
                Paragraph paragraph = new Paragraph(tableHeaderList.get(i), chineseFont);
                paragraph.setAlignment(Element.ALIGN_CENTER);//设置该段落为居中显示
                cell.setPhrase(paragraph);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }
            //创建表格数据
            for (int i=0; i<tableDataList.size(); i++) {
                //设置编号单元格
            	PdfPCell cell = new PdfPCell();
                Paragraph para = new Paragraph(tableDataList.get(i), chineseFont);
                para.setAlignment(Element.ALIGN_CENTER);//设置该段落为居中显示
                cell.setPhrase(para);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }
            //将表格添加到新的文档
            doc.add(table);
        } catch (DocumentException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {
        	if (doc.isOpen()) {
        		doc.close();
        	}
        	if (null != writer) {
        		writer.close();
        	}
        	logger.info("出借人列表表格创建完成......数据量：" + tableDataList.size());
        }
    }

    /**
     * 合并PDF文件操作
     *
     * @param inputFileList
     * @param outputFile
     * @param paginate
     */
    public static void mergePDFs(List<String> inputFileList, String outputFile, boolean paginate) {
        //logger.info("pdf文件合并......");
        Document document = new Document();
        PdfWriter writer = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            List<PdfReader> readers = new ArrayList<PdfReader>();
            for (String filePath : inputFileList) {
                fis = new FileInputStream(filePath);
                PdfReader pdfReader = new PdfReader(fis);
                readers.add(pdfReader);
            }
            fos = new FileOutputStream(outputFile);
            writer = PdfWriter.getInstance(document, fos);

            document.open();
            //设置字体
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent();

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;

            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();
                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);
                    // Code for pagination.
                    if (paginate) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, String.valueOf(currentPageNumber), 290, 50, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (document.isOpen()) {
                    document.close();
                }
                if (null != fis) {
                    fis.close();
                }
                if (null != fos) {
                    fos.close();
                }
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}