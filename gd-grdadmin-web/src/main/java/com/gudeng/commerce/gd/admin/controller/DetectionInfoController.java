package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.gudeng.commerce.gd.admin.service.DetectionToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.supplier.util.ExcelUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 市场价格管理
 *
 * @author 李冬
 * @time 2015年10月13日 上午10:41:45
 */
@Controller
public class DetectionInfoController extends AdminBaseController {
    /** 记录日志 */
    @SuppressWarnings("unused")
    private static final GdLogger logger = GdLoggerFactory.getLogger(DetectionInfoController.class);

    @Autowired
    public DetectionToolService detectionToolService;

    /**
     * 市场价格列表首页
     *
     * @param request
     * @return
     * @author 李冬
     * @time 2015年10月13日 上午11:20:50
     */
    @RequestMapping("detectinfo/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("detectionInfo/index");
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
    @RequestMapping("detectinfo/query")
    @ResponseBody
    public String query(HttpServletRequest request, DetectionDTO detectionDTO) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productName", detectionDTO.getProductName());
            map.put("origin", detectionDTO.getOrigin());
            map.put("startDate", request.getParameter("startDate"));
            map.put("endDate", request.getParameter("endDate"));
            if (detectionDTO.getMaketId() != null && detectionDTO.getMaketId() > 0) {
                map.put("maketId", detectionDTO.getMaketId());
            }
            // 设置查询参数
            // 记录数
            map.put("total", detectionToolService.getTotal(map));
            // 设定分页,排序
            setCommParameters(request, map);
            // list
            List<DetectionDTO> list = detectionToolService.getByCondition(map);
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
    @RequestMapping("detectinfo/addDto")
    public ModelAndView addDto(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("detectionInfo/addDto");
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
    @RequestMapping(value = "detectinfo/save")
    @ResponseBody
    public String saveOrUpdate(HttpServletRequest request, DetectionDTO detectionDTO, String id) {
        try {
            // UserSummary userInfo = getUserInfo(request);// 当前用户
            SysRegisterUser userInfo = getUser(request);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            // 在DTO类中，加上一个String类型的birthday，以此实现mybaties保存或者更新到mysql时，Date类型的数据无法insert的问题。
            detectionDTO.setDetectTime_str(request.getParameter("detectTime"));// 检测日期
            detectionDTO.setPublishTime_str(request.getParameter("publishTime"));// 检测日期
            int i = 0;
            if (detectionToolService.checkExsit(map)) {// 根据id判断是否存在，存在即为更新，不存在即为增加
                detectionDTO.setUpdateTime_str(DateUtil.getSysDateTimeString());// 修改者ID
                detectionDTO.setUpdateUserId(userInfo.getUserID());// 修改时间
                i = detectionToolService.updateDetectionDTO(detectionDTO);
            } else {
                detectionDTO.setCreateUserId(userInfo.getUserID());// 创建者ID
                detectionDTO.setCreateTime_str(DateUtil.getSysDateTimeString());// 创建时间
                i = detectionToolService.addDetectionDTO(detectionDTO);
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
    @RequestMapping("detectinfo/edit/{id}")
    public ModelAndView editbyid(HttpServletRequest request, @PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView();
        try {
            DetectionDTO dto = detectionToolService.getById(id);
            mv.addObject("dto", dto);
        } catch (Exception e) {

        }
        mv.setViewName("detectionInfo/addDto");
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
    @RequestMapping(value = "detectinfo/delete")
    @ResponseBody
    public String deleteById(String ids, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(ids)) {
                return "failed";
            }
            List<String> list = Arrays.asList(ids.split(","));
            int i = detectionToolService.deleteDetection(list);
            return i > 0 ? "success" : "failed";
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * @Description toImportData 跳转到导入数据页面
     * @return
     * @CreationDate 2015年10月31日 下午2:53:23
     * @Author lidong(dli@cnagri-products.com)
     */
    @RequestMapping("detectinfo/toImportData")
    public ModelAndView toImportData() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("detectionInfo/importData");
        return mv;
    }

    /**
     * @Description importData 使用Excel文档导入市场行情数据
     * @param mv
     * @return
     * @CreationDate 2015年10月31日 下午2:29:35
     * @Author lidong(dli@cnagri-products.com)
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "detectinfo/importData")
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
                            Workbook wb = Workbook.getWorkbook(input);
                            //
                            int numberOfSheets = wb.getNumberOfSheets();// 表单个数
                            // // 遍历各表单
                            for (int i = 0; i < numberOfSheets; i++) {
                                List<DetectionDTO> list = new ArrayList<DetectionDTO>();
                                Sheet sheet = wb.getSheet(i);// 当前表单
                                // 遍历表中的行，0行为表头，不遍历，从1开始
                                for (int j = 1; j < sheet.getRows(); j++) {
                                    Cell[] row = sheet.getRow(j);// 获取行对象
                                    if (row == null || row.length < 8) {// 如果为空，不处理
                                        continue;
                                    }
                                    // 每一行为一个对象，创建对象
                                    // for (int k = 0; k < row.length; k++) {
                                    DetectionDTO detectionDTO = new DetectionDTO();
                                    if (StringUtils.isNotEmpty(row[0].getContents())) {
                                        detectionDTO.setProductName(row[0].getContents().trim());// 获取第一列数据，商品名称
                                    } else {
                                        break;
                                    }
                                    if (StringUtils.isNotEmpty(row[1].getContents())) {
                                        detectionDTO.setOrigin(row[1].getContents().trim());// 获取第二列数据，出产地
                                    }
                                    if (StringUtils.isNotEmpty(row[2].getContents())) {
                                        detectionDTO.setUnitName(row[2].getContents().trim());// 获取第三列数据，被检单位或姓名
                                    }
                                    if (StringUtils.isNotEmpty(row[3].getContents())) {
                                        // 目前只有一种检测项目
                                        if (row[3].getContents().contains("蔬菜农药残留检测")) {
                                            detectionDTO.setInspection("0");// 获取第四列数据，最高报价
                                        } else {
                                            detectionDTO.setInspection("0");// 获取第四列数据，最高报价
                                        }
                                    }
                                    if (StringUtils.isNotEmpty(row[4].getContents())) {
                                        DecimalFormat df = new DecimalFormat("#.00");
                                        detectionDTO.setRate(Double.valueOf(df.format(Double.valueOf(row[4].getContents().trim().replaceAll("[^-+.\\d]", "")))));// 获取第五列数据，抑制率
                                    }
                                    // 获取第六列数据，是否合格
                                    if (StringUtils.isNotEmpty(row[5].getContents())) {
                                        if ("是".equals(row[5].getContents().trim())) {
                                            detectionDTO.setPass(1);// 是合格
                                        } else {
                                            detectionDTO.setPass(0);// 否合格
                                        }
                                    }
                                    if (StringUtils.isNotEmpty(row[6].getContents())) {
                                        detectionDTO.setDetectTime_str(row[6].getContents().trim());// 获取第七列数据，检测时间
                                    }
                                    if (StringUtils.isNotEmpty(row[7].getContents())) {
                                        detectionDTO.setPublishTime_str(row[7].getContents().trim());// 获取第八列数据，发布时间
                                    }
                                    detectionDTO.setCreateUserId(userInfo.getUserID());// 创建人
                                    detectionDTO.setCreateTime_str(DateUtil.getSysDateTimeString());// 创建时间
                                    detectionDTO.setMaketId(maketId);// 采集市场
                                    list.add(detectionDTO);
                                }
                                if (list.size() > 0) {
                                    detectionToolService.addDetectionBacth(list);
                                }
                            }
                            wb.close();
                            // Thread.sleep(10000);
                            // localFile.delete();// 导入完成后，删除临时文件
                        } catch (Exception e) {
                            e.printStackTrace();
                            return "false";
                        } finally {
                            // localFile.delete();
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
     * 检测导出参数,检测通过则后续会在页面启动文件下载
     * 
     * @param request
     * @param response
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "detectinfo/checkExportParams", produces = "application/json; charset=utf-8")
    public String checkExportParams(HttpServletRequest request, DetectionDTO detectionDTO) {
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
            map.put("productName", detectionDTO.getProductName());
            map.put("origin", detectionDTO.getOrigin());
            map.put("startDate", request.getParameter("startDate"));
            map.put("endDate", request.getParameter("endDate"));
            if (detectionDTO.getMaketId() != null && detectionDTO.getMaketId() > 0) {
                map.put("maketId", detectionDTO.getMaketId());
            }
            int total = detectionToolService.getTotal(map);
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
    @RequestMapping(value = "detectinfo/exportData")
    public String exportData(HttpServletRequest request, HttpServletResponse response, String productName, String origin, String maketId, String startDate, String endDate) {
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
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("检测信息.xlsx").getBytes(), "ISO-8859-1"));
            ouputStream = response.getOutputStream();
            // 查询数据
            List<DetectionDTO> list = detectionToolService.getByCondition(map);
            XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, DetectionDTO.class);
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
