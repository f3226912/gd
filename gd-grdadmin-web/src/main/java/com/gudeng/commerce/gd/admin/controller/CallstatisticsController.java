package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.ECallSourceType;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @Description 拨打电话数据统计
 * @Project gd-admin-web
 * @ClassName CallstatisticsController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年5月17日 上午11:53:11
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Controller
@RequestMapping("callstatistics")
public class CallstatisticsController extends AdminBaseController {

    /** 记录日志 */
    private static final GdLogger logger = GdLoggerFactory.getLogger(CallstatisticsController.class);
    @Autowired
    public CallstatiSticsToolService callstatiSticsService;

    /**
     * demo
     * 
     * @return
     */
    @RequestMapping("{sysCode}")
    public String argtelstat(HttpServletRequest request, @PathVariable String sysCode) {
        request.setAttribute("sysCode", sysCode);
        return "callstatistics/callstatistics_list";
    }

    /**
     * 默认查询和id条件查询结合
     *
     * @param request
     * @return
     *
     */
    @RequestMapping("query")
    @ResponseBody
    public String query(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");

            Map<String, Object> map = new HashMap<String, Object>();
            if (null == id || id.equals("")) {
            } else {
                map.put("id", id);
            }
            // 设置查询参数
            // 记录数
            map.put("total", callstatiSticsService.getTotalCount(map));
            // 设定分页,排序
            setCommParameters(request, map);
            // list
            List<CallstatiSticsDTO> list = callstatiSticsService.getList(map);
            for( CallstatiSticsDTO cas: list){
				cas.setSource(ECallSourceType.getValueByCode(cas.getSource()==null?"":cas.getSource()));
			}
            map.put("rows", list);// rows键 存放每页记录 list
            return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 根据多个条件查询
     *
     * @param name
     * @param startDate
     * @param endDate
     * @param request
     * @return
     */
    @RequestMapping(value = { "querybysearch", "querybysearch/{sysCode}" })
    @ResponseBody
    public String queryByName(CallstatiSticsDTO atsqb, HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("e_Mobile", atsqb.getE_Mobile());
            map.put("shopName", atsqb.getShopName());
            map.put("sysCode", atsqb.getSysCode());
            if (StringUtil.isNotEmpty(atsqb.getStartDate())) {
                map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(atsqb.getStartDate())));
            }
            if (StringUtil.isNotEmpty(atsqb.getEndDate())) {
                map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(atsqb.getEndDate())));
            }

            map.put("total", callstatiSticsService.getTotalCount(map));

            // 设定分页,排序
            setCommParameters(request, map);
            // list
            List<CallstatiSticsDTO> list = callstatiSticsService.getList(map);
            for( CallstatiSticsDTO cas: list){
				cas.setSource(ECallSourceType.getValueByCode(cas.getSource()==null?"":cas.getSource()));
			}
            map.put("rows", list);// rows键 存放每页记录 list
            String returnStr = JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
            return returnStr;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 检测导出参数,检测通过则后续会在页面启动文件下载
     * 
     * @param request
     * @param response
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkExportParams", produces = "application/json; charset=utf-8")
    public String checkExportParams(CallstatiSticsDTO atsqb, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String startDate = atsqb.getStartDate();
            String endDate = atsqb.getEndDate();
            if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)) {
                result.put("status", 0);
                result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
                return JSONObject.toJSONString(result);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("e_Mobile", atsqb.getE_Mobile());
            map.put("shopName", atsqb.getShopName());
            map.put("sysCode", atsqb.getSysCode());
            if (StringUtil.isNotEmpty(atsqb.getStartDate())) {
                map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(atsqb.getStartDate())));
            }
            if (StringUtil.isNotEmpty(atsqb.getEndDate())) {
                map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(atsqb.getEndDate())));
            }
            /*
             * if (StringUtil.isNotEmpty(atsqb.getStartDate())) { map.put("startDate", CommonUtil.getStartOfDay(DateUtil .formateDate(atsqb.getStartDate()))); } if
             * (StringUtil.isNotEmpty(atsqb.getEndDate())) { map.put("endDate", CommonUtil.getEndOfDay(DateUtil .formateDate(atsqb.getEndDate()))); }
             */

            int total = callstatiSticsService.getTotalCount(map);
            if (total > 10000) {
                result.put("status", 0);
                result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
                return JSONObject.toJSONString(result);
            }
            result.put("status", 1);
            result.put("message", "参数检测通过");
        } catch (Exception e) {
            logger.info("product checkExportParams with ex : {} ", new Object[] { e });
        }
        return JSONObject.toJSONString(result);
    }

    /**
     * @Description exportData 数据导出
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "exportData")
    public String exportData(HttpServletRequest request, HttpServletResponse response, String sysCode, String e_Mobile, String shopName) {
    	String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
    	Map<String, Object> map = new HashMap<String, Object>();
        map.put("e_Mobile", e_Mobile);
        map.put("e_Mobile", e_Mobile);
        map.put("shopName", shopName);
        map.put("sysCode", sysCode);
        if (StringUtil.isNotEmpty(startDate)) {
            map.put("startDate", CommonUtil.getStartOfDay(DateUtil.formateDate(startDate)));
        }
        if (StringUtil.isNotEmpty(endDate)) {
            map.put("endDate", CommonUtil.getEndOfDay(DateUtil.formateDate(endDate)));
        }
        map.put("startRow", 0);
        map.put("endRow", 99999999);
        WritableWorkbook wwb = null;
        OutputStream ouputStream = null;
        try {
            // 设置输出响应头信息，
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + StringUtil.GenerateRandomStr() + ".xls");
            ouputStream = response.getOutputStream();
            // 查询数据
            List<CallstatiSticsDTO> list = callstatiSticsService.getList(map);
            // 在输出流中创建一个新的写入工作簿
            wwb = Workbook.createWorkbook(ouputStream);
            if (wwb != null) {
                WritableSheet sheet = wwb.createSheet("农批电话数据统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
                // 第一个参数表示列，第二个参数表示行
                Label label00 = new Label(0, 0, "拨打来源");// 填充第一行第一个单元格的内容
                Label label10 = new Label(1, 0, "被叫号码");// 填充第一行第二个单元格的内容
                Label label20 = new Label(2, 0, "被叫号码姓名");// 填充第一行第三个单元格的内容
                Label label30 = new Label(3, 0, "商铺名称");// 填充第一行第四个单元格的内容
                Label label40 = new Label(4, 0, "主叫号码");// 填充第一行第五个单元格的内容
                Label label50 = new Label(5, 0, "主叫号码姓名");// 填充第一行第七个单元格的内容
                Label label60 = new Label(6, 0, "拨打时间");// 填充第一行第七个单元格的内容
                sheet.addCell(label00);// 将单元格加入表格
                sheet.addCell(label10);// 将单元格加入表格
                sheet.addCell(label20);
                sheet.addCell(label30);
                sheet.addCell(label40);
                sheet.addCell(label50);
                sheet.addCell(label60);
                /*** 循环添加数据到工作簿 ***/
                if (list != null && list.size() > 0) {
                    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (int i = 0, lenght = list.size(); i < lenght; i++) {
                        CallstatiSticsDTO callstatiSticsDTO = list.get(i);
                        Label label0 = new Label(0, i + 1, ECallSourceType.getValueByCode(callstatiSticsDTO.getSource()==null?"":callstatiSticsDTO.getSource()));
                        Label label1 = new Label(1, i + 1, callstatiSticsDTO.getS_Mobile());
                        Label label2 = new Label(2, i + 1, callstatiSticsDTO.getS_Name());
                        Label label3 = new Label(3, i + 1, callstatiSticsDTO.getShopName());
                        Label label4 = new Label(4, i + 1, callstatiSticsDTO.getE_Mobile());
                        Label label5 = new Label(5, i + 1, callstatiSticsDTO.getE_Name());

                        sheet.addCell(label0);// 将单元格加入表格
                        sheet.addCell(label1);// 将单元格加入表格
                        sheet.addCell(label2);
                        sheet.addCell(label3);
                        sheet.addCell(label4);
                        sheet.addCell(label5);
                        if (callstatiSticsDTO.getCreateTime() != null) {
                            Label label6 = new Label(6, i + 1, time.format(callstatiSticsDTO.getCreateTime()));
                            sheet.addCell(label6);
                        }

                    }
                }
                wwb.write();// 将数据写入工作簿
            }
            wwb.close();// 关闭
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                ouputStream.flush();
                ouputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
