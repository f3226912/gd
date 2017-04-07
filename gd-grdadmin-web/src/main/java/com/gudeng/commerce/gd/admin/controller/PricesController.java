package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.gudeng.commerce.gd.admin.service.PricesToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.supplier.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 市场价格管理
 *
 * @author 李冬
 * @time 2015年10月13日 上午10:41:45
 */
@Controller
public class PricesController extends AdminBaseController {
    /** 记录日志 */
    @SuppressWarnings("unused")
    private static final GdLogger logger = GdLoggerFactory.getLogger(PricesController.class);

    @Autowired
    public PricesToolService pricesToolService;

    /**
     * 市场价格列表首页
     *
     * @param request
     * @return
     * @author 李冬
     * @time 2015年10月13日 上午11:20:50
     */
    @RequestMapping("marketprice/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("marketPrice/index");
        return mv;
    }

    /**
     * 市场价格列表数据查找，默认按照ID查找
     *
     * @param request
     * @return
     * @author 李冬
     * @time 2015年10月13日 上午11:21:32
     */
    @RequestMapping("marketprice/query")
    @ResponseBody
    public String query(HttpServletRequest request, PricesDTO pricesDTO) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productName", pricesDTO.getProductName());
            if (pricesDTO.getMaketId() != null && pricesDTO.getMaketId() > 0) {
                map.put("maketId", pricesDTO.getMaketId());
            }
            map.put("startDate", request.getParameter("startDate"));
            map.put("endDate", request.getParameter("endDate"));
            if (null == pricesDTO.getId() || pricesDTO.getId() == 0) {
                // map.put("id", "1");
            } else {
                map.put("id", pricesDTO.getId());
            }
            // 设置查询参数
            // 记录数
            map.put("total", pricesToolService.getTotal(map));
            // 设定分页,排序
            setCommParameters(request, map);
            // list
            List<PricesDTO> list = pricesToolService.getByCondition(map);
            map.put("rows", list);// rows键 存放每页记录 list
            return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 打开新增页面
     *
     * @param request
     * @return
     * @author 李冬
     * @time 2015年10月13日 下午5:02:22
     */
    @RequestMapping("marketprice/addDto")
    public ModelAndView addDto(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("marketPrice/addDto");
        return mv;
    }

    /**
     * 保存或者编辑市场价格
     *
     * @param request
     * @return
     * @author 李冬
     * @time 2015年10月13日 上午11:54:24
     */
    @RequestMapping(value = "marketprice/save")
    @ResponseBody
    public String saveOrUpdate(HttpServletRequest request, PricesDTO priceDTO, String id) {
        try {
            // UserSummary userInfo = getUserInfo(request);// 当前用户
            SysRegisterUser userInfo = (SysRegisterUser) request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id == null ? 0 : id);
            // 在DTO类中，加上一个String类型的birthday，以此实现mybaties保存或者更新到mysql时，Date类型的数据无法insert的问题。
            priceDTO.setDate_string(request.getParameter("date"));
            priceDTO.setPublishTime_str(request.getParameter("publishTime"));
            priceDTO.setAveragePrice((priceDTO.getMaxPrice() + priceDTO.getMinPrice()) / 2);
            int i = 0;
            if (pricesToolService.checkExsit(map)) {
                // 根据id判断是否存在，存在即为更新，不存在即为增加
                priceDTO.setUpdateUserId(userInfo.getUserID());// 修改者ID
                priceDTO.setUpdateTime_str(DateUtil.getSysDateTimeString());// 修改时间
                i = pricesToolService.updatePricesDTO(priceDTO);
            } else {
                // 新增
                priceDTO.setCreateUserId(userInfo.getUserID());// 创建者ID
                priceDTO.setCreateTime_str(DateUtil.getSysDateTimeString());// 创建时间
                i = pricesToolService.addPricesDTO(priceDTO);
            }
            return i > 0 ? "success" : "failed";
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 打开编辑页
     *
     * @param request
     * @param id
     *            记录ID
     * @return
     * @author 李冬
     * @time 2015年10月13日 下午5:01:54
     */
    @RequestMapping("marketprice/edit/{id}")
    public ModelAndView editbyid(HttpServletRequest request, @PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView();
        try {
            PricesDTO dto = pricesToolService.getById(id);
            mv.addObject("dto", dto);
        } catch (Exception e) {

        }
        mv.setViewName("marketPrice/addDto");
        return mv;
    }

    /**
     * 根据ID进行删除操作
     *
     * @param id
     * @param request
     * @return
     * @author 李冬
     * @time 2015年10月13日 下午5:01:37
     */
    @RequestMapping(value = "marketprice/delete")
    @ResponseBody
    public String deleteById(String ids, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(ids)) {
                return "failed";
            }
            List<String> list = Arrays.asList(ids.split(","));
            int i = pricesToolService.deletePrices(list);
            return i > 0 ? "success" : "failed";
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * @Description importData 使用Excel文档导入市场行情数据
     * @param mv
     * @return
     * @CreationDate 2015年10月31日 下午2:29:35
     * @Author lidong(dli@cnagri-products.com)
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "marketprice/importData")
    @ResponseBody
    public String importData(HttpServletRequest request, @RequestParam(value = "datafile", required = false) MultipartFile file, Long maketId) {
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
                            Workbook wb = WorkbookFactory.create(input);
                            int numberOfSheets = wb.getNumberOfSheets();// 表单个数
                            // // 遍历各表单
                            for (int i = 0; i < numberOfSheets; i++) {
                                Sheet sheet = wb.getSheetAt(i);
                                // 遍历表中的行，0行为表头，不遍历，从1开始
                                int rowCount = sheet.getPhysicalNumberOfRows();// 总行数
                                if (rowCount > 1) {
                                    List<PricesDTO> list = new ArrayList<PricesDTO>();
                                    for (int j = 1; j < rowCount; j++) {
                                        Row row = sheet.getRow(j);
                                        if (row == null) {// 如果为空，不处理
                                            continue;
                                        }
                                        // 每一行为一个对象，创建对象
                                        PricesDTO pricesDTO = new PricesDTO();
                                        if (row.getCell(0) != null && !"".equals(ExcelUtil.getCellValue(row.getCell(0)))) {
                                            pricesDTO.setProductName(("" + ExcelUtil.getCellValue(row.getCell(0))).trim());// 获取第一列数据，商品名称
                                        } else {
                                            break;
                                        }
                                        if (row.getCell(1) != null && !"".equals(ExcelUtil.getCellValue(row.getCell(1)))) {
                                            pricesDTO.setDate_string(("" + ExcelUtil.getCellValue(row.getCell(1))).trim());// 获取第二列数据，采集时间
                                        }
                                        if (row.getCell(2) != null && !"".equals(ExcelUtil.getCellValue(row.getCell(2)))) {
                                            pricesDTO.setMaxPrice((Double) ExcelUtil.getCellValue(row.getCell(2)));// 获取第3列数据，最高报价
                                        }
                                        if (row.getCell(3) != null && !"".equals(ExcelUtil.getCellValue(row.getCell(3)))) {
                                            pricesDTO.setMinPrice((Double) ExcelUtil.getCellValue(row.getCell(3)));// 获取第4列数据，最低报价
                                        }
                                        if (row.getCell(4) != null && !"".equals(ExcelUtil.getCellValue(row.getCell(4)))) {
                                            pricesDTO.setPublishTime_str(("" + ExcelUtil.getCellValue(row.getCell(4))).trim());// 获取第五列数据，发布时间
                                        }
                                        if (row.getCell(5) != null && !"".equals(ExcelUtil.getCellValue(row.getCell(5)))) {
                                            pricesDTO.setTargetUrl(("" + ExcelUtil.getCellValue(row.getCell(5))).trim());// 获取第六列数据，跳转地址
                                        }
                                        if (pricesDTO.getMaxPrice() != null && pricesDTO.getMinPrice() != null) {
                                            pricesDTO.setAveragePrice((pricesDTO.getMaxPrice() + pricesDTO.getMinPrice()) / 2);// 平均报价
                                        } else {
                                            pricesDTO.setAveragePrice(0D);
                                        }
                                        pricesDTO.setCreateUserId(userInfo.getUserID());// 创建人
                                        pricesDTO.setCreateTime_str(DateUtil.getSysDateTimeString());// 创建时间
                                        pricesDTO.setMaketId(maketId);// 采集市场
                                        list.add(pricesDTO);
                                    }
                                    if (list.size() > 0) {
                                        pricesToolService.addPricesBacth(list);
                                    }
                                }
                            }
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
        return "success";
    }

    /**
     * @Description toImportData 跳转到导入数据页面
     * @return
     * @CreationDate 2015年10月31日 下午2:53:23
     * @Author lidong(dli@cnagri-products.com)
     */
    @RequestMapping("marketprice/toImportData")
    public ModelAndView toImportData() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("marketPrice/importData");
        return mv;
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
    @RequestMapping(value = "marketprice/checkExportParams", produces = "application/json; charset=utf-8")
    public String checkExportParams(HttpServletRequest request, PricesDTO pricesDTO) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)) {
                result.put("status", 0);
                result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
                return JSONObject.toJSONString(result);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productName", pricesDTO.getProductName());
            if (pricesDTO.getMaketId() != null && pricesDTO.getMaketId() > 0) {
                map.put("maketId", pricesDTO.getMaketId());
            }
            map.put("startDate", request.getParameter("startDate"));
            map.put("endDate", request.getParameter("endDate"));
            if (null == pricesDTO.getId() || pricesDTO.getId() == 0) {
                // map.put("id", "1");
            } else {
                map.put("id", pricesDTO.getId());
            }
            int total = pricesToolService.getTotal(map);
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
     * @CreationDate 2015年11月5日 下午7:20:28
     * @Author lidong(dli@cnagri-products.com)
     */
    @RequestMapping(value = "marketprice/exportData")
    public String exportData(HttpServletRequest request, HttpServletResponse response, String productName, String maketId, String startDate, String endDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productName", productName);
        if (!"-99".equals(maketId)) {
            map.put("maketId", maketId);
        }
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("startRow", 0);
        map.put("endRow", 99999999);
        OutputStream ouputStream = null;
        try {
            // 设置输出响应头信息，
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("农市行情.xlsx").getBytes(), "ISO-8859-1"));
            ouputStream = response.getOutputStream();
            // 查询数据
            List<PricesDTO> list = pricesToolService.getByCondition(map);
            XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, PricesDTO.class);
            workBook.write(ouputStream);
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
