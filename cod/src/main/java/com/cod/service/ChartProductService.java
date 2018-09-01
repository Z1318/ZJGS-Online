package com.cod.service;


import com.cod.dao.ChartProductDao;
import com.cod.entity.chart.ChartProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ChartProductService {

    @Autowired
    ChartProductDao chartProductDao;

    @Autowired
    ReadExcelService readExcelService;

    @Autowired
    ReadCsvService readCsvService;

    /**
     * 读取excel或者csv文件
     * @param name
     * @param excelFile
     * @param groupname
     * @param fileType
     * @return
     */
    public String readExcelAndCsv(String name, MultipartFile excelFile,String groupname,String fileType) {
        String b;
        try {
            List<ChartProduct> chartProductList = new ArrayList<>();
            if (fileType.equals("xls") || fileType.equals("xlsx")) {
                //解析excel，获取商品投放统计信息集合
                chartProductList = readExcelService.ExcelInfo(name, excelFile, groupname);
            } else if (fileType.equals("csv")) {
                chartProductList = readCsvService.CsvInfo(name, excelFile, groupname);
            }
            else{
                log.info("【文件类型错误】", fileType);
                return "文件类型错误";
            }

            //依据时间以及组名删除原有的旧数据
            if (chartProductList != null && chartProductList.size() > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("team", groupname);
                map.put("launch_time", chartProductList.get(0).getLaunch_time());
                chartProductDao.delProductByTeamAndLaunchTime(map);
                //将数据插入到数据库
                chartProductDao.batchinsertProduct(chartProductList);
            }
            else {
                return "文件内容为空！";
            }

        }
        catch (Exception e){
            log.error("【解析文件失败】", e.getMessage());
            return "解析文件失败！";
        }
        return "上传成功！";
    }

    /**
     * 依据投放时间和组名获取初始商品统计数据
     * @param team
     * @param launch_time
     * @return
     */
    public List<ChartProduct> getProductByTeamAndLaunchTime(String team,String launch_time){
        Map<String,Object> map=new HashMap<>();
        map.put("team",team);
        map.put("launch_time",launch_time);
        return chartProductDao.getProductByTeamAndLaunchTime(map);
    }

    /**
     * 查询出组中投放时间最近的
     * @param team
     * @return
     */
    public String selectMaxDate(String team){

        return chartProductDao.selectMaxDate(team);

    }

    /**
     * 依据投放时间，和投放人获取投放sku
     * @param
     * @return
     */
    public List<String> selectSkuByLaunchTimeAndPerson(String launch_time,String person){
        Map<String,Object> map=new HashMap<>();
        map.put("launch_time",launch_time);
        map.put("person",person);
        return chartProductDao.selectSkuByLaunchTimeAndPerson(map);
    }

    /**
     * 依据投放时间,和商品sku获取商品初始导入数据
     * @param
     * @return
     */
    public List<ChartProduct> selectChartProductBySkuAndLaunchTime(String launch_time,String sku,String team){
        //依据sku查询商品初始导入数据
        Map<String,Object> map=new HashMap<>();
        map.put("launch_time",launch_time);
        map.put("sku",sku);
        map.put("team",team);
        return chartProductDao.selectChartProductBySkuAndLaunchTime(map);
    }

    /**
     *
     * @param launch_time 投放时间
     * @param groupName   组名
     * @return
     */
    public List<String> selectSkuByLaunchTime(String launch_time,String groupName){
        Map<String,Object> map=new HashMap<>();
        map.put("launch_time",launch_time);
        map.put("team",groupName);
        return chartProductDao.selectSkuByLaunchTime(map);
    }

    /**
     *by DL
     * 根据id修改产品
     */
    public  int updateProduct(ChartProduct chartProduct){
        return chartProductDao.updateProduct(chartProduct);
    }

    /**
     * 依据时间获取组名
     * @param launch_time
     * @return
     */
    public List<String> getGroupNameByLaunchTime(String launch_time){
        return chartProductDao.getGroupNameByLaunchTime(launch_time);
    }

}
