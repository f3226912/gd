package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.service.MarketBerthManageService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.tags.TagUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.MarketBerthAddDTO;
import com.gudeng.commerce.gd.customer.dto.MarketBerthAddDTOForm;
import com.gudeng.commerce.gd.customer.dto.MarketBerthDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.util.ExcelUtil;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 市场铺位管理
 * 
 * @author cai.x
 *
 */
@Controller
@RequestMapping("marketberth")
public class MarketBerthController extends AdminBaseController {

	private static final GdLogger logger = GdLoggerFactory.getLogger(MarketBerthController.class);

	@Autowired
	public MarketManageService marketManageService;

	@Autowired
	public MarketBerthManageService marketBerthManageService;
	
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("marketBerth/new_market_berth");
		return mv;
	}

	@RequestMapping("/marketlist/{marketType}")
	@ResponseBody
	public String queryMarketList(HttpServletRequest request,
			@PathVariable("marketType") String marketType) {
		JSONArray ja = new JSONArray();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("total", marketManageService.getAllByType("2").size());
			// 设定分页,排序
			setCommParameters(request, paramMap);
			List<MarketDTO> market = marketManageService.getAllByType("2");
			if (null != market && market.size() != 0) {
				if ("0".equals(marketType)) {
					MarketDTO dto = new MarketDTO();
					dto.setId((long) -1);
					dto.setMarketName("-- 全部 --");
					ja.add(dto);
				}
				for (MarketDTO marketDTO : market) {
					ja.add(marketDTO);
				}
			}
			return ja.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/marketberthlist")
	@ResponseBody
	public String queryMarketBerthList(HttpServletRequest request) {

		try {
			String marketId = "-1".equals(request.getParameter("marketId"))
					? null : request.getParameter("marketId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("marketId", marketId);
			int total = marketBerthManageService.getTotal(paramMap);
			// 设定分页,排序
			setCommParameters(request, paramMap);
			List<MarketBerthDTO> market = marketBerthManageService
					.getByCondition(paramMap);
			return setJson(true, market, "", total);
		} catch (Exception e) {
			e.printStackTrace();
			return setJson(false, null, "查询异常,请联系管理员!", 0);
		}
	}

	@RequestMapping("/marketberthdtl")
	@ResponseBody
	public String queryMarketBerthDtl(HttpServletRequest request) {

		try {
			String marketId = request.getParameter("marketId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("marketId", marketId);
			int total = marketBerthManageService.getTotalByDtl(paramMap);
			// 设定分页,排序
			setCommParameters(request, paramMap);
			List<MarketBerthDTO> market = marketBerthManageService
					.getByConditionByDtl(paramMap);
			return setJson(true, market, "", total);
		} catch (Exception e) {
			e.printStackTrace();
			return setJson(false, null, "查询异常,请联系管理员!", 0);
		}
	}

	@RequestMapping("/marketberthgroup")
	@ResponseBody
	public String queryMarketGroup(HttpServletRequest request) {

		try {
			String marketId = request.getParameter("marketId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("marketId", marketId);
			int total = marketBerthManageService
					.queryMarketGroupTotal(paramMap);
			List<MarketBerthDTO> market = marketBerthManageService
					.queryMarketGroup(paramMap);
			return setJson(true, market, "", total);
		} catch (Exception e) {
			e.printStackTrace();
			return setJson(false, null, "查询异常,请联系管理员!", 0);
		}
	}

	@RequestMapping(value = "/addmarketlist", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addMarketList(MarketBerthAddDTOForm dto,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		try {
			String marketId = request.getParameter("market_add");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("marketId", marketId);
			int total = marketBerthManageService.getTotal(paramMap);
			if (total != 0) {
				return setJson(false, null, "该市场已经有数据,请用编辑按钮操作!", 0);
			}
			SysRegisterUser user = getUser(request);
			if (null == user) {
				return setJson(false, null, "用户登录失效，请重新登录!", 0);
			}
			int count = 0;
			List<MarketBerthAddDTO> marketBerths = dto.getMarketBerthAddDTOs();
			if (null == marketBerths) {
				return setJson(false, null, "获取数据失败,请刷新页面!", 0);
			}
			for (MarketBerthAddDTO marketBerth : marketBerths) {
				// 区
				String area = marketBerth.getAddArea();
				if (null == area || "".equals(area)) {
					return setJson(false, null, "区域不能为空!", 0);
				}
				// 栋
				boolean isNum = isInteger(marketBerth.getAddBuild());
				if (!isNum) {
					return setJson(false, null, "栋必须为正整数!", 0);
				}
				int build = Integer.valueOf(marketBerth.getAddBuild());
				for (int i = 0; i < build; i++) {
					// 层
					isNum = isInteger(marketBerth.getAddLayer());
					if (!isNum) {
						return setJson(false, null, "层必须为正整数!", 0);
					}
					int layer = Integer.valueOf(marketBerth.getAddLayer());
					for (int j = 0; j < layer; j++) {
						// 铺位
						isNum = isInteger(marketBerth.getAddBerth());
						if (!isNum) {
							return setJson(false, null, "铺位必须为正整数!", 0);
						}
						int berth = Integer.valueOf(marketBerth.getAddBerth());
						for (int k = 0; k < berth; k++) {
							count++;
							System.out.println(area + "区\t" + (i + 1) + "栋\t"
									+ (j + 1) + "层\t" + (k + 1) + "铺位");
							System.out.println("总共" + count + "条数据");
							MarketBerthDTO marketBerthDto = new MarketBerthDTO();
							marketBerthDto
									.setMarketId(Integer.valueOf(marketId));
							marketBerthDto.setArea(area);
							marketBerthDto.setBuild(String.valueOf(i + 1));
							marketBerthDto.setLayer(String.valueOf(j + 1));
							marketBerthDto.setBerth(String.valueOf(k + 1));
							marketBerthDto.setCreateUser(user.getUserCode());
							marketBerthDto.setUpdateUser(user.getUserCode());
							marketBerthManageService
									.addMerketBerth(marketBerthDto);
						}
					}
				}
			}
			return setJson(true, null, "操作成功", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return setJson(false, null, "操作异常,请联系管理员!", 0);
		}
	}

	@RequestMapping(value = "/editmarketlist", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String editMarketlist(MarketBerthAddDTOForm dto,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {
			String marketId = request.getParameter("editMarketId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("marketId", marketId);

			SysRegisterUser user = getUser(request);
			if (null == user) {
				return setJson(false, null, "用户登录失效，请重新登录!", 0);
			}
			int count = 0;
			List<MarketBerthAddDTO> marketBerths = dto.getMarketBerthAddDTOs();
			if (null == marketBerths) {
				return setJson(false, null, "获取数据失败,请刷新页面!", 0);
			}
			List<MarketBerthDTO> addMarketBerths = new ArrayList<MarketBerthDTO>();
			for (MarketBerthAddDTO marketBerth : marketBerths) {
				// 区
				String area = marketBerth.getAddArea();
				if (null == area || "".equals(area)) {
					return setJson(false, null, "区域不能为空!", 0);
				}
				// 栋
				boolean isNum = isInteger(marketBerth.getAddBuild());
				if (!isNum) {
					return setJson(false, null, "栋必须为整数!", 0);
				}
				int build = Integer.valueOf(marketBerth.getAddBuild());
				for (int i = 0; i < build; i++) {
					// 层
					isNum = isInteger(marketBerth.getAddLayer());
					if (!isNum) {
						return setJson(false, null, "层必须为整数!", 0);
					}
					int layer = Integer.valueOf(marketBerth.getAddLayer());
					for (int j = 0; j < layer; j++) {
						// 铺位
						isNum = isInteger(marketBerth.getAddBerth());
						if (!isNum) {
							return setJson(false, null, "铺位必须为整数!", 0);
						}
						int berth = Integer.valueOf(marketBerth.getAddBerth());
						for (int k = 0; k < berth; k++) {
							count++;
							System.out.println(area + "区\t" + (i + 1) + "栋\t"
									+ (j + 1) + "层\t" + (k + 1) + "铺位");
							System.out.println("总共" + count + "条数据");
							MarketBerthDTO marketBerthDto = new MarketBerthDTO();
							marketBerthDto
									.setMarketId(Integer.valueOf(marketId));
							marketBerthDto.setArea(area);
							marketBerthDto.setBuild(String.valueOf(i + 1));
							marketBerthDto.setLayer(String.valueOf(j + 1));
							marketBerthDto.setBerth(String.valueOf(k + 1));
							marketBerthDto.setCreateUser(user.getUserCode());
							marketBerthDto.setUpdateUser(user.getUserCode());
							addMarketBerths.add(marketBerthDto);
						}
					}
				}
			}
			marketBerthManageService.deleteMarketBerth(paramMap);
			for (MarketBerthDTO marketBerthDTO : addMarketBerths) {
				marketBerthManageService.addMerketBerth(marketBerthDTO);
			}
			return setJson(true, null, "操作成功", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return setJson(false, null, "操作异常,请联系管理员!", 0);
		}
	}

	/**
	 * 根据id修改市场铺位表
	 * 
	 * @param dto
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author 李冬 dli@gdeng.cn
	 * @time 2016年8月18日 上午10:42:53
	 */
	@RequestMapping(value = "/updateMarketBerthCode", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updateMarketBerth(MarketBerthDTO dto,HttpServletResponse response)throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dto.setUpdateUser(getUser(request).getUserName());
			int i = marketBerthManageService.updateMarketBerthCodeById(dto);
			map.put("result", "success");
			map.put("i", i);
			map.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "error");
			map.put("msg", "保存失败");
		}
		return JSONObject.toJSONString(map);
	}
	
    /**
     * 编辑页面导出
     * @param request
     * @param response
     * @param maketId
     * @return
     * @author lidong
     * @time 2016年8月18日 下午3:18:22
     */
    @RequestMapping(value = "exportData")
    public String exportData(HttpServletRequest request, HttpServletResponse response, String maketId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("marketId", maketId);
        map.put("startRow", 0);
        map.put("endRow", 10000);
        OutputStream ouputStream = null;
        try {
            // 设置输出响应头信息，
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(("市场铺位导出数据.xlsx").getBytes(), "ISO-8859-1"));
            ouputStream = response.getOutputStream();
            // 查询数据
            List<MarketBerthDTO> market = marketBerthManageService.getByConditionByDtl(map);
            XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(market, MarketBerthDTO.class);
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
    
    @RequestMapping("toImportData")
    public ModelAndView toImportData() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("marketBerth/importData");
        return mv;
    }
    /**
     * 导入excel，批量修改市场铺位编号
     * @param request
     * @param file
     * @return
     * @author lidong
     * @time 2016年8月18日 下午5:01:47
     */
    @RequestMapping(value = "importData")
    @ResponseBody
    public String importData(HttpServletRequest request, @RequestParam(value = "datafile", required = false) MultipartFile file) {
        SysRegisterUser userInfo = getUser(request);
        // 解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 先判断request中是否包涵multipart类型的数据，
        if (multipartResolver.isMultipart(request)) {
            try {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    MultipartFile fileTemp = multiRequest.getFile(iter.next());
                    if (fileTemp != null) {
                        try {
                            /****** 导入文件数据到数据库中 *******/
                            InputStream input = fileTemp.getInputStream(); // 建立输入流
                            Workbook workbook =WorkbookFactory.create(input); 
                            int numberOfSheets = workbook.getNumberOfSheets();// 表单个数
                            // // 遍历各表单
                            for (int i = 0; i < numberOfSheets; i++) {
                            	Sheet sheet = workbook.getSheetAt(i);
                            	List<MarketBerthDTO> list = new ArrayList<MarketBerthDTO>();
                            	for (Row row : sheet) {
                            		int rowNum = row.getRowNum();
                            		if (rowNum>0) {
                            			if (row.getCell(0)!=null) {
                            				// 每一行为一个对象，创建对象
                            				MarketBerthDTO dto = new MarketBerthDTO();
                            				String id  = row.getCell(0).toString();//第一列
                            				if (row.getCell(5) != null) {
                            					switch (row.getCell(5).getCellType()) {
												case Cell.CELL_TYPE_NUMERIC:
													Double number=row.getCell(5).getNumericCellValue();
													dto.setBerthCode(TagUtil.formatNumber2(number));
													break;
												default:
													dto.setBerthCode(row.getCell(5).toString());
													break;
												} 
                            				}
                            				dto.setId(Integer.valueOf(id));
                            				dto.setUpdateUser(userInfo.getUserName());
                            				list.add(dto);
										}
									}
								}
                            	if (list.size() > 0) {
                            		int a=marketBerthManageService.updateMarketBerthBacth(list);
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
	 * 返回JSON数据
	 * 
	 * @param result
	 * @param rows
	 * @param msg
	 * @param total
	 * @return
	 */
	public String setJson(boolean result, Object rows, String msg, int total) {
		Map<String, Object> map = new HashMap<String, Object>();
		String resultStr = "";
		if (result)
			resultStr = "SUCCESS";
		else
			resultStr = "ERROR";
		map.put("total", total);
		map.put("rows", rows);
		map.put("result", resultStr);
		map.put("msg", msg);
		return JSONObject.toJSONString(map, SerializerFeature.WriteDateUseDateFormat).toString();
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			int val = Integer.parseInt(value);
			if (val < 0) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
