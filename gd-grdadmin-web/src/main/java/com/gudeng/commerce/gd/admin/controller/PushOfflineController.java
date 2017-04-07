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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.PushOfflineToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.customer.dto.PushOfflineDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @Description 线下推广统计
 * @Project gd-admin-web
 * @ClassName PushOfflineController.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2016年2月18日 下午5:33:08
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
@Controller
public class PushOfflineController extends AdminBaseController {
    /** 记录日志 */
    @SuppressWarnings("unused")
    private static final GdLogger logger = GdLoggerFactory.getLogger(PushOfflineController.class);

    @Autowired
    public PushOfflineToolService pushOfflineToolService;

    /**
     * @Description 线下推广统计列表首页
     * @param request
     * @return
     * @CreationDate 2016年2月18日 下午5:33:41
     * @Author lidong(dli@gdeng.cn)
     */
    @RequestMapping("pushOffline/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pushOffline/index");
        return mv;
    }

    /**
     * @Description 线下推广统计列表查询
     * @param request
     * @param pushOfflineDTO
     * @return
     * @CreationDate 2016年2月18日 下午5:33:52
     * @Author lidong(dli@gdeng.cn)
     */
    @RequestMapping("pushOffline/query")
    @ResponseBody
    public String query(HttpServletRequest request, PushOfflineDTO pushOfflineDTO) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("startDate", request.getParameter("startDate"));
            map.put("endDate", request.getParameter("endDate"));
            map.put("industry", pushOfflineDTO.getIndustry());
            map.put("source", pushOfflineDTO.getSource());
            map.put("pushName", pushOfflineDTO.getPushName());
            map.put("pushMobile", pushOfflineDTO.getPushMobile());
            map.put("memberMobile", pushOfflineDTO.getMemberMobile());
            // 设置查询参数
            // 记录数
            map.put("total", pushOfflineToolService.getTotal(map));
            // 设定分页,排序
            setCommParameters(request, map);
            // list
            List<PushOfflineDTO> list = pushOfflineToolService.getPushOfflineDTOList(map);
            map.put("rows", list);// rows键 存放每页记录 list
            return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



	/**
	 * 检测导出参数,检测通过则后续会在页面启动文件下载
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "pushOffline/checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request, PushOfflineDTO pushOfflineDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("startDate", request.getParameter("startDate"));
            map.put("endDate", request.getParameter("endDate"));
            map.put("industry", pushOfflineDTO.getIndustry());
            map.put("source", pushOfflineDTO.getSource());
            map.put("pushName", pushOfflineDTO.getPushName());
            map.put("pushMobile", pushOfflineDTO.getPushMobile());
            map.put("memberMobile", pushOfflineDTO.getMemberMobile());

			int total = pushOfflineToolService.getTotal(map);
			if (total > 10000){
				result.put("status", 0);
				result.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(result);
			}
			result.put("status", 1);
			result.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("product checkExportParams with ex : {} ", new Object[]{e});
		}
		return JSONObject.toJSONString(result);
	}

    /**
     * @Description exportData 数据导出
     * @param request
     * @param file
     * @return
     * @CreationDate 2015年11月5日 下午7:20:28
     * @Author lidong(dli@cnagri-products.com)
     */
    @RequestMapping(value = "pushOffline/exportData")
    public String exportData(HttpServletRequest request, HttpServletResponse response, PushOfflineDTO pushOfflineDTO) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", request.getParameter("startDate"));
        map.put("endDate", request.getParameter("endDate"));
        map.put("industry", pushOfflineDTO.getIndustry());
        map.put("source", pushOfflineDTO.getSource());
        map.put("pushName", pushOfflineDTO.getPushName());
        map.put("pushMobile", pushOfflineDTO.getPushMobile());
        map.put("memberMobile", pushOfflineDTO.getMemberMobile());
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
            List<PushOfflineDTO> list = pushOfflineToolService.getPushOfflineDTOList(map);
            // 在输出流中创建一个新的写入工作簿
            wwb = Workbook.createWorkbook(ouputStream);
            if (wwb != null) {
                WritableSheet sheet = wwb.createSheet("线下推广统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
                // 第一个参数表示列，第二个参数表示行
                Label label00 = new Label(0, 0, "所属行业");// 填充第一行第一个单元格的内容
                Label label10 = new Label(1, 0, "推广来源");// 填充第一行第四个单元格的内容
                Label label20 = new Label(2, 0, "推广人");// 填充第一行第五个单元格的内容
                Label label30 = new Label(3, 0, "推广人手机号");// 填充第一行第七个单元格的内容
                Label label40 = new Label(4, 0, "会员手机号");// 填充第一行第二个单元格的内容
                Label label50 = new Label(5, 0, "推广时间");// 填充第一行第六个单元格的内容
                sheet.addCell(label00);// 将单元格加入表格
                sheet.addCell(label10);// 将单元格加入表格
                sheet.addCell(label20);
                sheet.addCell(label30);
                sheet.addCell(label40);
                sheet.addCell(label50);
                /*** 循环添加数据到工作簿 ***/
                if (list != null && list.size() > 0) {
                    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (int i = 0, lenght = list.size(); i < lenght; i++) {
                        PushOfflineDTO dto = list.get(i);
                        Label label0 = new Label(0, i + 1, dto.getIndustry());
                        Label label1 = new Label(1, i + 1, dto.getSource());
                        Label label2 = new Label(2, i + 1, dto.getPushName());
                        Label label3 = new Label(3, i + 1, dto.getPushMobile());
                        Label label4 = new Label(4, i + 1, dto.getMemberMobile());
                        Label label5 = new Label(5, i + 1, time.format(dto.getCreateTime()));
                        sheet.addCell(label0);// 将单元格加入表格
                        sheet.addCell(label1);// 将单元格加入表格
                        sheet.addCell(label2);
                        sheet.addCell(label3);
                        sheet.addCell(label4);
                        sheet.addCell(label5);
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
