package com.cod.utils;

import com.cod.entity.chart.ChartProduct;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class excelUtils {

    // 导出数据到excel
   public void insertMessage(List<ChartProduct> list){
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("网站排名");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("网站url");

        cell = row.createCell(1);
        cell.setCellValue("全球网站排名");

        cell = row.createCell(2);
        cell.setCellValue("综合排名");


       cell = row.createCell(3);
       cell.setCellValue("每日访客uv");

       cell = row.createCell(4);
       cell.setCellValue("每日页面浏览量pv");

       cell = row.createCell(5);
       cell.setCellValue("三个月百万人访问数");

       cell = row.createCell(6);
       cell.setCellValue("一月百万人访问数");

       cell = row.createCell(7);
       cell.setCellValue("一周百万人访问数");

       cell = row.createCell(8);
       cell.setCellValue("一日百万人访问数");


        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值

        for (int i = 0; i < list.size(); i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            ChartProduct chartProduct = list.get(i);
            //创建单元格设值
            row1.createCell(0).setCellValue(chartProduct.getTeam());
            row1.createCell(1).setCellValue(chartProduct.getName());
            row1.createCell(2).setCellValue(chartProduct.getFb_cost());
            row1.createCell(3).setCellValue(chartProduct.getFb_income());
            row1.createCell(4).setCellValue(chartProduct.getOrder_num());
            row1.createCell(5).setCellValue(chartProduct.getCart_num());
            row1.createCell(6).setCellValue(chartProduct.getShow_num());
            row1.createCell(7).setCellValue(chartProduct.getLaunch_time());
            row1.createCell(8).setCellValue(chartProduct.getImport_time());


        }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("D:\\test.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}