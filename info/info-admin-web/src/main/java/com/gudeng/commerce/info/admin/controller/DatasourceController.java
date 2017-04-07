package com.gudeng.commerce.info.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.admin.service.DatasourceBaiduToolService;
import com.gudeng.commerce.info.admin.service.DatasourceToolService;
import com.gudeng.commerce.info.admin.service.OrderBillToolService;
import com.gudeng.commerce.info.admin.service.ProOperateToolService;
import com.gudeng.commerce.info.admin.service.SysMenuAdminService;
import com.gudeng.commerce.info.admin.util.DateUtil;
import com.gudeng.commerce.info.customer.dto.DatasourceBaiduDTO;
import com.gudeng.commerce.info.customer.dto.DatasourceDTO;
import com.gudeng.commerce.info.customer.dto.OrderBillDTO;
import com.gudeng.commerce.info.customer.dto.ProOperateDTO;
import com.gudeng.commerce.info.customer.entity.SysRegisterUser;
import com.gudeng.commerce.info.customer.util.Constant;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("datasource")
public class DatasourceController extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(DatasourceController.class);
	
	@Autowired
	public DatasourceToolService datasourceToolService;
	@Autowired
	public OrderBillToolService orderBillToolService;
	@Autowired
	public DatasourceBaiduToolService datasourceBaiduToolService;
	@Autowired
	public ProOperateToolService proOperateToolService;
	
	 /** 菜单service */
    @Autowired
    private SysMenuAdminService sysMenuService;
	
	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping("list")
	public String datasourceList(HttpServletRequest request,ModelMap map){
		return "datasource/list";
	}
	
	/**
	 * 列表数据查询
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("query")
	@ResponseBody
	public String datasourceQuery(HttpServletRequest request) {
		try {
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			
			//获取条件记录总数
			map.put("total", datasourceToolService.getTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			List<DatasourceDTO> list = datasourceToolService.getListByConditionPage(map);
			//rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 	 初始化 查看详细信息
	 * application/json
	 * @param request
	 * @return
	 */
	@RequestMapping("detailByWhbszyhls/{id}")
	public String detailByWhbszyhls(@PathVariable("id") String id, ModelMap map){
		try {
			DatasourceDTO datasourceDTO = datasourceToolService.getById(Long.valueOf(id));
			datasourceDTO.setLastUpdateTimeStr(DateUtil.toString(datasourceDTO.getLastUpdateTime(), DateUtil.DATE_FORMAT_DATETIME));
			map.put("dto", datasourceDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "datasource/whbszyhlsdetail";
	}
	
	/**
	 * 列表数据查询
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("queryOrderBill")
	@ResponseBody
	public String QueryOrderBill(HttpServletRequest request) {
		try {
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			
			//获取条件记录总数
			map.put("total", orderBillToolService.getTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			List<OrderBillDTO> list = orderBillToolService.getListByConditionPage(map);
			//rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * @Description exportData 数据导出
     * @param request
     * @param file
     * @return
     * @CreationDate 
     * @Author 
     */
    @RequestMapping(value = "exportOrderBill")
    public String exportOrderBill(HttpServletRequest request, HttpServletResponse response, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        WritableWorkbook wwb = null;
        OutputStream ouputStream = null;
        try {
            // 设置输出响应头信息，
            response.setContentType("application/vnd.ms-excel");
            String fileName  = new String("武汉白沙洲银行流水数据源".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
            ouputStream = response.getOutputStream();
            // 查询数据
            List<OrderBillDTO> list = orderBillToolService.getListByConditionPage(map);
            // 在输出流中创建一个新的写入工作簿
            wwb = Workbook.createWorkbook(ouputStream);
            if (wwb != null) {
                WritableSheet sheet = wwb.createSheet("武汉白沙洲银行流水", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
                // 第一个参数表示列，第二个参数表示行
                Label label00 = new Label(0, 0, "商户编号");
                Label label10 = new Label(1, 0, "商户名称");
                Label label20 = new Label(2, 0, "交易类型");
                Label label30 = new Label(3, 0, "交易时间");
                Label label40 = new Label(4, 0, "交易卡号");
                Label label50 = new Label(5, 0, "终端号");
                Label label60 = new Label(6, 0, "交易金额");
                Label label70 = new Label(7, 0, "系统参考号");
                Label label80 = new Label(8, 0, "所属市场");
                sheet.addCell(label00);// 将单元格加入表格
                sheet.addCell(label10);// 将单元格加入表格
                sheet.addCell(label20);
                sheet.addCell(label30);
                sheet.addCell(label40);
                sheet.addCell(label50);
                sheet.addCell(label60);
                sheet.addCell(label70);
                sheet.addCell(label80);
                /*** 循环添加数据到工作簿 ***/
                if (list != null && list.size() > 0) {
                    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (int i = 0, lenght = list.size(); i < lenght; i++) {
                    	OrderBillDTO dto = list.get(i);
                        Label label0 = new Label(0, i + 1, StringUtils.isEmpty(dto.getBusinessNo()) ? "" : dto.getBusinessNo().trim().toString());
                        Label label1 = new Label(1, i + 1, StringUtils.isEmpty(dto.getBusinessName()) ? "" : dto.getBusinessName().trim().toString());
                        Label label2 = new Label(2, i + 1, StringUtils.isEmpty(dto.getTradeType()) ? "" : dto.getTradeType().trim().toString());
                        Label label3 = new Label(3, i + 1, time.format(dto.getTradeDay()));
                        Label label4 = new Label(4, i + 1, StringUtils.isEmpty(dto.getCardNo()) ? "" : dto.getCardNo().trim().toString());
                        Label label5 = new Label(5, i + 1, StringUtils.isEmpty(dto.getClientNo()) ? "" : dto.getBusinessNo().trim().toString());
                        Label label6 = new Label(6, i + 1, dto.getTradeMoney() == null ? "" : dto.getTradeMoney().toString());
                        Label label7 = new Label(7, i + 1, StringUtils.isEmpty(dto.getSysRefeNo()) ? "" : dto.getSysRefeNo().trim().toString());
                        Label label8 = new Label(8, i + 1, "武汉白沙洲批发市场");
                        sheet.addCell(label0);// 将单元格加入表格
                        sheet.addCell(label1);// 将单元格加入表格
                        sheet.addCell(label2);
                        sheet.addCell(label3);
                        sheet.addCell(label4);
                        sheet.addCell(label5);
                        sheet.addCell(label6);
                        sheet.addCell(label7);
                        sheet.addCell(label8);
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
	
	/**
	 * 	 初始化 查看详细信息
	 * application/json
	 * @param request
	 * @return
	 */
	@RequestMapping("detailByBaidu/{id}")
	public String detailByBaidu(@PathVariable("id") String id, ModelMap map){
		try {
			DatasourceDTO datasourceDTO = datasourceToolService.getById(Long.valueOf(id));
			datasourceDTO.setLastUpdateTimeStr(DateUtil.toString(datasourceDTO.getLastUpdateTime(), DateUtil.DATE_FORMAT_DATETIME));
			map.put("dto", datasourceDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "datasource/baidudetail";
	}
	
	/**
	 * 列表数据查询
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("queryDatasourceBaidu")
	@ResponseBody
	public String QueryDatasourceBaidu(HttpServletRequest request) {
		try {
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			
			//获取条件记录总数
			map.put("total", datasourceBaiduToolService.getTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			List<DatasourceBaiduDTO> list = datasourceBaiduToolService.getListByConditionPage(map);
			for(DatasourceBaiduDTO dto : list){
				double d = dto.getAvstop();
				dto.setAvstopStr(DateUtil.timeToStr((int)d));
			}
			//rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * @Description exportData 数据导出
     * @param request
     * @param file
     * @return
     * @CreationDate 
     * @Author 
     */
    @RequestMapping(value = "exportBaidu")
    public String exportBaidu(HttpServletRequest request, HttpServletResponse response, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        if("1".equals(type)){
        	map.put("startRow", 0);
            map.put("endRow", 1);
        }
        WritableWorkbook wwb = null;
        OutputStream ouputStream = null;
        try {
            // 设置输出响应头信息，
            response.setContentType("application/vnd.ms-excel");
            String fileName  = new String("百度统计数据源".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
            ouputStream = response.getOutputStream();
            // 查询数据
            List<DatasourceBaiduDTO> list = new ArrayList<DatasourceBaiduDTO>();
            if("1".equals(type)){
            	list = datasourceBaiduToolService.getListByConditionPage(map);
            }else{
            	list = datasourceBaiduToolService.getListByCondition(map);
            }
            // 在输出流中创建一个新的写入工作簿
            wwb = Workbook.createWorkbook(ouputStream);
            if (wwb != null) {
                WritableSheet sheet = wwb.createSheet("百度统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
                // 第一个参数表示列，第二个参数表示行
                Label label00 = new Label(0, 0, "PV浏览量");
                Label label10 = new Label(1, 0, "UV访客数");
                Label label20 = new Label(2, 0, "IP数");
                Label label30 = new Label(3, 0, "最后数据更新时间");
                Label label40 = new Label(4, 0, "退出率");
                Label label50 = new Label(5, 0, "当日平均停留时长");
                Label label60 = new Label(6, 0, "新访客");
                Label label70 = new Label(7, 0, "老访客");
                Label label80 = new Label(8, 0, "所属客户端");
                sheet.addCell(label00);// 将单元格加入表格
                sheet.addCell(label10);// 将单元格加入表格
                sheet.addCell(label20);
                sheet.addCell(label30);
                sheet.addCell(label40);
                sheet.addCell(label50);
                sheet.addCell(label60);
                sheet.addCell(label70);
                sheet.addCell(label80);
                /*** 循环添加数据到工作簿 ***/
                if (list != null && list.size() > 0) {
                    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (int i = 0, lenght = list.size(); i < lenght; i++) {
                    	DatasourceBaiduDTO dto = list.get(i);
                        Label label0 = new Label(0, i + 1, dto.getPVcount() == null ? "" : dto.getPVcount().toString());
                        Label label1 = new Label(1, i + 1, dto.getUVcount() == null ? "" : dto.getUVcount().toString());
                        Label label2 = new Label(2, i + 1, dto.getIPcount() == null ? "" : dto.getIPcount().toString());
                        Label label3 = new Label(3, i + 1, time.format(dto.getLastUpdateTime()));
                        Label label4 = new Label(4, i + 1, dto.getSignout() == null ? "" : dto.getSignout().toString());
                        Label label5 = new Label(5, i + 1, dto.getAvstop() == null ? "" : dto.getAvstop().toString());
                        Label label6 = new Label(6, i + 1, dto.getNewuser() == null ? "" : dto.getNewuser().toString());
                        Label label7 = new Label(7, i + 1, dto.getOlduser() == null ? "" : dto.getOlduser().toString());
                        Label label8 = new Label(8, i + 1, StringUtils.isEmpty(dto.getClient()) ? "" : dto.getClient().trim().toString());
                        sheet.addCell(label0);// 将单元格加入表格
                        sheet.addCell(label1);// 将单元格加入表格
                        sheet.addCell(label2);
                        sheet.addCell(label3);
                        sheet.addCell(label4);
                        sheet.addCell(label5);
                        sheet.addCell(label6);
                        sheet.addCell(label7);
                        sheet.addCell(label8);
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
    
    @RequestMapping("toImportBaidu")
    public ModelAndView toImportBaidu() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("datasource/importData");
        return mv;
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "importBaidu")
    @ResponseBody
    public String importBaidu(HttpServletRequest request, @RequestParam(value = "datafile", required = false) MultipartFile file, Long maketId) {
        // 解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        SysRegisterUser userInfo = (SysRegisterUser) request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
        // 先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            try {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    MultipartFile fileTemp = multiRequest.getFile((String) iter.next());
                    if (fileTemp != null) {
                        try {
                            /****** 导入文件数据到数据库中 *******/
                            InputStream input = fileTemp.getInputStream(); // 建立输入流
                            Workbook wb = Workbook.getWorkbook(input);
                            int numberOfSheets = wb.getNumberOfSheets();// 表单个数
                            // // 遍历各表单
                            for (int i = 0; i < numberOfSheets; i++) {
                                List<DatasourceBaiduDTO> list = new ArrayList<DatasourceBaiduDTO>();
                                Sheet sheet = wb.getSheet(i);// 当前表单
                                // 遍历表中的行，0行为表头，不遍历，从1开始
                                if(sheet.getRows() < 2){
                                	return "doubi";
                                }
                                for (int j = 1; j < sheet.getRows(); j++) {
                                    Cell[] row = sheet.getRow(j);// 获取行对象
                                    if (row == null) {// 如果为空，不处理
                                        continue;
                                    }
                                    // 每一行为一个对象，创建对象
                                    // for (int k = 0; k < row.length; k++) {
                                    DatasourceBaiduDTO dto = new DatasourceBaiduDTO();
                                    if (StringUtils.isNotEmpty(row[0].getContents())) {
                                    	dto.setPVcount(Double.valueOf(row[0].getContents().trim().replaceAll("[^-+.\\d]", "")));
                                    }
                                    if (StringUtils.isNotEmpty(row[1].getContents())) {
                                    	dto.setUVcount(Double.valueOf(row[1].getContents().trim().replaceAll("[^-+.\\d]", "")));
                                    }
                                    if (StringUtils.isNotEmpty(row[2].getContents())) {
                                    	dto.setIPcount(Double.valueOf(row[2].getContents().trim().replaceAll("[^-+.\\d]", "")));
                                    }
                                    if (StringUtils.isNotEmpty(row[3].getContents())) {
                                    	dto.setLastUpdateTimeStr(row[3].getContents().trim());
                                    }
                                    if (StringUtils.isNotEmpty(row[4].getContents())) {
                                    	dto.setSignout(Double.valueOf(row[4].getContents().trim().replaceAll("[^-+.\\d]", "")));
                                    }
                                    if (StringUtils.isNotEmpty(row[5].getContents())) {
                                    	dto.setAvstop(Double.valueOf(DateUtil.strToTime(row[5].getContents().trim())));
                                    }
                                    if (StringUtils.isNotEmpty(row[6].getContents())) {
                                    	dto.setNewuser(Double.valueOf(row[6].getContents().trim().replaceAll("[^-+.\\d]", "")));
                                    }
                                    if (StringUtils.isNotEmpty(row[7].getContents())) {
                                    	dto.setOlduser(Double.valueOf(row[7].getContents().trim().replaceAll("[^-+.\\d]", "")));
                                    }
                                    dto.setState("1");
                                    dto.setCreateUserID(userInfo.getUserID().toString());
                                    if (StringUtils.isNotEmpty(row[8].getContents())) {
                                    	dto.setClient(row[8].getContents().trim());
                                    }
                                    list.add(dto);
                                }
                                if (list.size() > 0) {
                                	datasourceBaiduToolService.batchUpdateDTO(list);
                                }
                            }
                            wb.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "false";
                        } finally {

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "false";
            }
        }
        DatasourceDTO obj = new DatasourceDTO();
        try {
        	obj.setId(2l);
        	obj.setLastUpdateTimeStr(DateUtil.getSysDateTimeString());
			datasourceToolService.updateDTO(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "success";
    }
    
    
    /**
	 * 	 初始化 查看详细信息
	 * application/json
	 * @param request
	 * @return
	 */
	@RequestMapping("detailByProOperate/{id}")
	public String detailByProOperate(@PathVariable("id") String id, ModelMap map){
		try {
			DatasourceDTO datasourceDTO = datasourceToolService.getById(Long.valueOf(id));
			datasourceDTO.setLastUpdateTimeStr(DateUtil.toString(datasourceDTO.getLastUpdateTime(), DateUtil.DATE_FORMAT_DATETIME));
			map.put("dto", datasourceDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "datasource/prooperatedetail";
	}
	
	/**
	 * 列表数据查询
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@RequestMapping("queryProOperate")
	@ResponseBody
	public String QueryProOperate(HttpServletRequest request) {
		try {
			//设置查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			
			//获取条件记录总数
			map.put("total", proOperateToolService.getTotal(map));
			//设置分页参数
			setCommParameters(request, map);
			//数据集合
			List<ProOperateDTO> list = proOperateToolService.getListByConditionPage(map);
			//rows键 存放每页记录 list
			map.put("rows", list);
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.trace(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * @Description exportData 数据导出
     * @param request
     * @param file
     * @return
     * @CreationDate 
     * @Author 
     */
    @RequestMapping(value = "exportProOperate")
    public String exportProOperate(HttpServletRequest request, HttpServletResponse response, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        WritableWorkbook wwb = null;
        OutputStream ouputStream = null;
        try {
            // 设置输出响应头信息，
            response.setContentType("application/vnd.ms-excel");
            String fileName  = new String("运营统计数据源".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
            ouputStream = response.getOutputStream();
            // 查询数据
            List<ProOperateDTO> list = proOperateToolService.getListByCondition(map);
            // 在输出流中创建一个新的写入工作簿
            wwb = Workbook.createWorkbook(ouputStream);
            if (wwb != null) {
                WritableSheet sheet = wwb.createSheet("运营统计", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
                // 第一个参数表示列，第二个参数表示行
                Label label00 = new Label(0, 0, "数据时间");
                Label label10 = new Label(1, 0, "当日平台注册用户数");
                Label label20 = new Label(2, 0, "累计平台注册用户数");
                Label label30 = new Label(3, 0, "环比增长率");
                /*Label label40 = new Label(4, 0, "交易卡号");
                Label label50 = new Label(5, 0, "终端号");
                Label label60 = new Label(6, 0, "交易金额");
                Label label70 = new Label(7, 0, "系统参考号");
                Label label80 = new Label(8, 0, "所属市场");*/
                sheet.addCell(label00);// 将单元格加入表格
                sheet.addCell(label10);// 将单元格加入表格
                sheet.addCell(label20);
                sheet.addCell(label30);
                /*sheet.addCell(label40);
                sheet.addCell(label50);
                sheet.addCell(label60);
                sheet.addCell(label70);
                sheet.addCell(label80);*/
                /*** 循环添加数据到工作簿 ***/
                if (list != null && list.size() > 0) {
                    SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    for (int i = 0, lenght = list.size(); i < lenght; i++) {
                    	ProOperateDTO dto = list.get(i);
                        Label label0 = new Label(0, i + 1, time.format(dto.getDatatimes()));
                        Label label1 = new Label(1, i + 1, dto.getComRegCount() == null ? "" : dto.getComRegCount().toString());
                        Label label2 = new Label(2, i + 1, dto.getSumComRegCount() == null ? "" : dto.getSumComRegCount().toString());
                        Label label3 = new Label(3, i + 1, dto.getGrowthRate() == null ? "" : dto.getGrowthRate().toString());
                        /*Label label4 = new Label(4, i + 1, StringUtils.isEmpty(dto.getCardNo()) ? "" : dto.getCardNo().trim().toString());
                        Label label5 = new Label(5, i + 1, StringUtils.isEmpty(dto.getClientNo()) ? "" : dto.getBusinessNo().trim().toString());
                        Label label6 = new Label(6, i + 1, dto.getTradeMoney() == null ? "" : dto.getTradeMoney().toString());
                        Label label7 = new Label(7, i + 1, StringUtils.isEmpty(dto.getSysRefeNo()) ? "" : dto.getSysRefeNo().trim().toString());
                        Label label8 = new Label(8, i + 1, StringUtils.isEmpty(dto.getSysRefeNo()) ? "" : dto.getSysRefeNo().trim().toString());*/
                        sheet.addCell(label0);// 将单元格加入表格
                        sheet.addCell(label1);// 将单元格加入表格
                        sheet.addCell(label2);
                        sheet.addCell(label3);
                        /*sheet.addCell(label4);
                        sheet.addCell(label5);
                        sheet.addCell(label6);
                        sheet.addCell(label7);
                        sheet.addCell(label8);*/
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
