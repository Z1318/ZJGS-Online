package com.cod.service;

import com.cod.entity.chart.ChartProduct;
import com.cod.utils.WDWUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class ReadCsvService {
    /**
     * 读取csv文件
     *
     * @param fileName
     * @param Mfile
     * @return
     */
    public List<ChartProduct> CsvInfo(String fileName, MultipartFile Mfile,String groupname) {

        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf = (CommonsMultipartFile) Mfile; //获取本地存储路径
        //String classpath = this.getClass().getResource("/").getPath().replaceFirst("/", "");
        String classpath1 = this.getClass().getResource("/").getPath();
        String webappRoot = classpath1.replaceAll("WEB-INF/classes/", "WEB-INF/assets/csv/");
        File file = new File(webappRoot);
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) {
            file.mkdirs();
        }
        //新建一个文件
        File file1 = new File(webappRoot + new Date().getTime() + ".csv");
        log.info("【上传文件路径】",webappRoot + new Date().getTime() + ".csv");
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

            //根据新建的文件实例化输入流
            is = new FileInputStream(file1);
            //判断文件类型
            System.out.println(file.getPath());
            //根据csv里面的内容读取客户信息
            chartProductsList = readCsvFile(is,groupname);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chartProductsList;
    }

    public List<ChartProduct> readCsvFile(InputStream csvFile,String groupname) {
                InputStreamReader fr = null;
                BufferedReader br = null;
                List<ChartProduct> csvfile = new ArrayList<ChartProduct>();

                try {
                    fr = new InputStreamReader(csvFile,"utf-8");
                    br = new BufferedReader(fr);
                    String rec = null;
                    String[] argsArr = null;
                    int num = 0;
                    while ((rec = br.readLine()) != null) {
                        ChartProduct csv = new ChartProduct();
                        if (num == 0){
                            num++;
                            continue;
                        }
                        if (rec != null) {

                            argsArr = rec.split(",");
                            for (int i = 0; i < argsArr.length; i++) {
                                if (i == 0){
                                    csv.setImport_time(argsArr[i]);
                                    csv.setLaunch_time(argsArr[i]);
                                    csv.setTeam(groupname);
                                }else if ( i == 2){
                                    String nameArray[]=argsArr[i].split("-");
                                    csv.setName(nameArray[0]);
                                    csv.setSku(nameArray[1]);
                                    csv.setPerson(nameArray[2]);
                                }else if ( i == 3){
                                    if (argsArr[i] != null && !argsArr[i].equals("") && !argsArr[i].equals("null")){
                                        csv.setFb_cost(Float.parseFloat(argsArr[i]));}
                                }else if ( i == 4){
                                    if (argsArr[i] != null && !argsArr[i].equals("") && !argsArr[i].equals("null")){
                                        csv.setFb_income(Float.parseFloat(argsArr[i]));}
                                }else if ( i == 5){
                                    if (argsArr[i] != null && !argsArr[i].equals("") && !argsArr[i].equals("null")){
                                        csv.setOrder_num(Integer.parseInt(argsArr[i]));}
                                }else if ( i == 6){
                                    if (argsArr[i] != null && !argsArr[i].equals("") && !argsArr[i].equals("null")){
                                        csv.setCart_num(Integer.parseInt(argsArr[i]));}
                                }else if ( i == 7){
                                    if (argsArr[i] != null && !argsArr[i].equals("") && !argsArr[i].equals("null")){
                                        csv.setSite_conversion(Integer.parseInt(argsArr[i]));}
                                }else if ( i == 8){
                                    if (argsArr[i] != null && !argsArr[i].equals("") && !argsArr[i].equals("null")){
                                        csv.setShow_num(Integer.parseInt(argsArr[i]));}
                                }else if ( i ==9) {
                                    if (argsArr[i] != null && !argsArr[i].equals("") && !argsArr[i].equals("null")){
                                        csv.setSingleResult(Float.parseFloat(argsArr[i]));}
                                }else if ( i ==10) {
                                    if (argsArr[i] != null && !argsArr[i].equals("") && !argsArr[i].equals("null")){
                                        csv.setSingleFree(Float.parseFloat(argsArr[i]));}
                                }

                            }
                        }
                        csvfile.add(csv);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return csvfile;
            }
        }
