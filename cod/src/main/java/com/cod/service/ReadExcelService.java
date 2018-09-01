package com.cod.service;


import com.cod.utils.DateUtil;
import com.cod.utils.WDWUtil;
import org.springframework.stereotype.Service;
import com.cod.entity.chart.ChartProduct;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReadExcelService {
    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;

    //获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    //获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    //获取错误信息
    public String getErrorInfo() {
        return errorMsg;
    }

    /**
     * 验证EXCEL文件
     *
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))) {
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    /**
     * 读取excel文件
     *
     * @param fileName
     * @param Mfile
     * @return
     */
    public List<ChartProduct> ExcelInfo(String fileName, MultipartFile Mfile,String groupName) {

        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf = (CommonsMultipartFile) Mfile; //获取本地存储路径
        //String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String classpath1 = this.getClass().getResource("/").getPath();
        String webappRoot = classpath1.replaceAll("WEB-INF/classes/", "WEB-INF/assets/excel/");
        File file = new File(webappRoot);
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) {
            file.mkdirs();
        }
        //新建一个文件
        File file1 = new File(webappRoot + new Date().getTime() + ".xlsx");
        //将上传的文件写入新建的文件中
        try {
            cf.getFileItem().write(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化客户信息的集合
        List<ChartProduct> chartProductsList = new ArrayList<ChartProduct>();
        //初始化输入流
        InputStream is = null;
        try {
            //验证文件名是否合格
            if (!validateExcel(fileName)) {
                return null;
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = new FileInputStream(file1);
            //判断文件类型

            //根据excel里面的内容读取客户信息
            chartProductsList = getExcelInfo(is, isExcel2003,groupName);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return chartProductsList;
    }

    /**
     * 根据excel里面的内容读取客户信息
     *
     * @param is          输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public List<ChartProduct> getExcelInfo(InputStream is, boolean isExcel2003,String groupName) {
        List<ChartProduct> chartProductsList = null;
        try {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            //当excel是2003时
            if (isExcel2003) {
                wb = new HSSFWorkbook(is);
            } else {//当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面客户的信息
            chartProductsList = readExcelValue(wb,groupName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chartProductsList;
    }

    /**
     * 读取Excel里面客户的信息
     *
     * @param wb
     * @return
     */
    private List<ChartProduct> readExcelValue(Workbook wb,String groupName) {
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);

        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<ChartProduct> chartProductsList = new ArrayList<ChartProduct>();
        ChartProduct chartProduct;
        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;
            chartProduct = new ChartProduct();

            //循环Excel的列
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {

                    if (c == 0) {
                        chartProduct.setLaunch_time(DateUtil.dateFormat(cell.getDateCellValue()));//投放时间
                    }
                    if (c == 2) {
                        String name=cell.getStringCellValue();
                        String nameArray[]=name.split("-");
                        chartProduct.setName(nameArray[0]);//商品名称
                        chartProduct.setSku(nameArray[1]);//设置商品sku
                        chartProduct.setPerson(nameArray[2]);//设置商品投放人
                        chartProduct.setTeam(groupName);//设置所属组


                    }
                    if (c == 3) {
                        chartProduct.setFb_cost((float)cell.getNumericCellValue());//faceBook支出
                    }
                    if (c == 4) {
                        chartProduct.setFb_income((float)cell.getNumericCellValue());//facebook收入
                    }
                    if (c == 5) {
                        chartProduct.setOrder_num((int)cell.getNumericCellValue());//订单数
                    }
                    if (c == 6) {
                        chartProduct.setCart_num((int)cell.getNumericCellValue());//加车数
                    }
                    if (c == 7) {
                        chartProduct.setSite_conversion((int)cell.getNumericCellValue());//网站转化

                    }
                    if (c == 8) {
                        chartProduct.setShow_num((int)cell.getNumericCellValue());//展示数
                    }
                    if (c == 9) {
                        chartProduct.setSingleResult((float)cell.getNumericCellValue());//单次成效
                    }
                    if (c == 10) {
                        chartProduct.setSingleFree((float)cell.getNumericCellValue());//单次点击费用
                    }
                }

            }
            //添加商品统计信息
            chartProductsList.add(chartProduct);
        }
        return chartProductsList;
    }
}
