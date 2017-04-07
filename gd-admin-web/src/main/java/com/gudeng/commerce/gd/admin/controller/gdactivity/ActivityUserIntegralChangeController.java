package com.gudeng.commerce.gd.admin.controller.gdactivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.service.ActivityUserIntegralChangeToolService;
import com.gudeng.commerce.gd.admin.service.GdActActivityToolService;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.ActivityUserIntegralChangeDTO;
import com.gudeng.commerce.gd.customer.util.ExcelUtil;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

/**
 * 用户活动积分变更
 * 
 * @author jiangyanli
 *
 */
@Controller
@RequestMapping("activityIntegral")
public class ActivityUserIntegralChangeController extends AdminBaseController {
	private static final GdLogger logger = GdLoggerFactory.getLogger(ActivityUserIntegralChangeController.class);

	@Autowired
	protected ActivityUserIntegralChangeToolService userIntegralChangeSerivce;

	@Autowired
	protected GdActActivityToolService activitySerivce;

	/**
	 * 进入主页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("listPage")
	public String list() {
		return "gdActActivity/userIntegral/list";
	}

	/**
	 * 进入增加/查看积分变更页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addPage")
	public String addPage() throws Exception {

		return "gdActActivity/userIntegral/add";
	}

	/**
	 * 进入增加/查看积分变更页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("detailPage")
	public String detailPage(int id) {
		putModel("id", id);
		return "gdActActivity/userIntegral/detail";
	}

	/**
	 * 进入选择用户界面
	 * 
	 * @return
	 */
	@RequestMapping("addMemberPage")
	public String addBuyer() {
		return "gdActActivity/userIntegral/memberSelect";
	}

	/**
	 * 查询现场采销活动的用户的积分变更记录列表
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("queryList")
	public Map<String, Object> queryPageList(ActivityUserIntegralChangeDTO paramsObj) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Map<String, Object> paramsMap = BeanUtils.describe(paramsObj);
			result.put("total", userIntegralChangeSerivce.getTotal(paramsMap));

			setCommParameters(request, paramsMap);
			List<ActivityUserIntegralChangeDTO> list = userIntegralChangeSerivce.getListPage(paramsMap);
			result.put("rows", list);

		} catch (Exception e) {
			logger.info("活动列表查询错误 : {}", e);
		}
		return result;
	}

	/**
	 * 根据活动编号获取活动Id
	 * 
	 * @param activityCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getActivityId")
	public Map<String, Object> getActivityId(String activityCode) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Map<String, Object> paramsMap = new HashMap<>();
			paramsMap.put("code", activityCode);
			List<GdActActivityDTO> activitys = activitySerivce.getList(paramsMap);
			int activityId = 0;
			int activityType = 0;
			if (activitys != null && activitys.size() > 0) {
				activityId = activitys.get(0).getId();
				activityType = activitys.get(0).getType();
				result.put("result", "success");
			} else {
				result.put("result", "faiure");
				result.put("msg", "活动不存在！");
			}
			result.put("activityId", activityId);
			result.put("activityType", activityType);

		} catch (Exception e) {
			result.put("result", "error");
			result.put("msg", "获取活动发生异常！");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 新增变更记录
	 * 
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public Map<String, Object> save(ActivityUserIntegralChangeDTO dto) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			SysRegisterUser user = getUser(request);
			dto.setCreateUser(user.getUserCode());
			int rows = userIntegralChangeSerivce.insert(dto);
			if (rows > 0) {
				result.put("result", "success");
			} else {
				result.put("msg", "操作失败，用户积分不能小于变更积分！");
				result.put("result", "fairure");
			}

		} catch (Exception e) {
			result.put("result", "error");
		}
		return result;
	}

	/**
	 * 根据ID查看变更记录
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getById")
	public Map<String, Object> getById(String id) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			ActivityUserIntegralChangeDTO dto = userIntegralChangeSerivce.getById(id);
			result.put("result", "success");
			result.put("data", dto);
		} catch (Exception e) {
			result.put("result", "error");
		}
		return result;
	}

	/**
	 * 检测导出数据量是否过大
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "checkExport")
	@ResponseBody
	public Map<String,Object> checkExport( ActivityUserIntegralChangeDTO dto) {
		Map<String,Object> result = new HashMap<>();
		try {
			// 根据搜索条件查询 要导出的数据
			Map<String, Object> paramsMap = BeanUtils.describe(dto);
			int total = userIntegralChangeSerivce.getTotal(paramsMap);
			if(total > 10000)
			{
				result.put("result", "failure");
				result.put("msg", "导出数据超过10000条，请缩小查询范围！");
				return result;
			}
			
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("导出检测异常,"+e);
		} 
		return result;
	}
	
	/**
	 * 导出Excel文件
	 * 
	 * @param response
	 * @return
	 * @author jiangyanli
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportData")
	public Map<String,Object> exportData(HttpServletResponse response, ActivityUserIntegralChangeDTO dto) {
		OutputStream ouputStream = null;
		try {
			
			// 根据搜索条件查询 要导出的数据
			Map<String, Object> paramsMap = BeanUtils.describe(dto);
			List<ActivityUserIntegralChangeDTO> list = userIntegralChangeSerivce.getList(paramsMap);
			
			// 设置输出响应头信息
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(("userIntegralChange.xlsx").getBytes(), "ISO-8859-1"));
			ouputStream = response.getOutputStream();

			// 导出
			XSSFWorkbook workBook = ExcelUtil.buildXSLXExcel(list, ActivityUserIntegralChangeDTO.class);
			workBook.write(ouputStream);
		} catch (Exception e) {
			e.printStackTrace();
		
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
