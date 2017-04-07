package com.gudeng.commerce.gd.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.admin.dto.CertifiQueryBean;
import com.gudeng.commerce.gd.admin.dto.ProductParamsBean;
import com.gudeng.commerce.gd.admin.entity.UserSummary;
import com.gudeng.commerce.gd.admin.service.AuditInfoToolService;
import com.gudeng.commerce.gd.admin.service.MarketManageService;
import com.gudeng.commerce.gd.admin.service.MemberCertifiToolService;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.MarketDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.commerce.gd.admin.util.CommonUtil;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.StringUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;

@Controller
@RequestMapping("certifi")
public class MemberCertifiController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(MemberCertifiController.class);

	@Autowired
	public MemberCertifiToolService memberCertifiToolService;

	@Autowired
	public AuditInfoToolService auditInfoToolService;

	@Autowired
	private  MarketManageService marketManageService;

	/**
	 * demo
	 * @return
	 */
	@RequestMapping("")
	public String certifi(HttpServletRequest request){
		try {
			List<MarketDTO>  list = marketManageService.getAllByType("2");
			request.setAttribute("marketList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "certifi/memberCertifi_list";
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
	public String query(HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数  非农速通用户
			map.put("certificationType", "0");
			//记录数
			map.put("total", memberCertifiToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberCertifiDTO> list1 = memberCertifiToolService.getList(map);
			List<MemberCertifiDTO> list = new ArrayList();
			for(MemberCertifiDTO mdt:list1){
				if(null !=mdt.getLevel() && mdt.getLevel().equals("4")){
					mdt.setMarketName("");
				}
				list.add(mdt);
			}
			map.put("rows", list);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {

		}
		return null;
	}
//
//
//	/**
//	 * 根据id查询
//	 *
//	 * @param id
//	 * @param request
//	 * @return
//	 */


	@RequestMapping(value="querybyid/{certifiId}")
	@ResponseBody
    public String queryById(@PathVariable("certifiId") String certifiId, HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if(null==certifiId || certifiId.equals("")){
			}else{
				map.put("certifiId", certifiId);
			}
			// 设置查询参数
			//记录数
			map.put("total", memberCertifiToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			MemberCertifiDTO memberCertifiDTO = memberCertifiToolService.getById(certifiId);
			map.put("memberCertifiDTO", memberCertifiDTO);//rows键 存放每页记录 list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 启用&&禁用
	 *
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updatestatus/{certifiId}-{status}" )
	@ResponseBody
    public String updateStatus(@PathVariable("certifiId") String certifiId,@PathVariable("status") String status, HttpServletRequest request){
		try {
//			MemberCertifiDTO mb=memberCertifiToolService.getById(certifiId);
//			mb.setStatus(status);
			MemberCertifiDTO mb=new MemberCertifiDTO();
			mb.setStatus(status);
			mb.setMemberId(Long.valueOf(certifiId));
			int i=memberCertifiToolService.updateMemberCertifiDTO(mb);
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
 		} catch (Exception e) {

		}
		return null;
	}



	/**
	 * 根据ID进行删除操作
	 *
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="deletebyid" )
	@ResponseBody
    public String deleteById(@RequestParam String id, HttpServletRequest request){
		try {
			if(null==id || id.equals("")){
			return "faild";
			}
			int i=memberCertifiToolService.deleteById(id);
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
 		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 增加修改同一个页面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("addDto")
	public String addDto(HttpServletRequest request){
		return "certifi/memberCertifi_edit";
	}



	/**
	 * 	  增加修改同一个方法
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="save" )
	@ResponseBody
    public String saveOrUpdate(MemberCertifiDTO dto, HttpServletRequest request){
		try {
			UserSummary userSummary=this.getUserInfo(request);
			Map map=new HashMap();
			String certifiId=request.getParameter("certifiId");
			if(null!=certifiId && !certifiId.equals("")){
				map.put("certifiId", certifiId);
//				dto.setMemberId(Long.valueOf(certifiId));
				dto.setUpdateTime_string(DateUtil.getSysDateString());
//				dto.setUpdateUserId(userSummary.getUserid());//用户ID，需要规划怎么填写
			}else{
				dto.setCreateTime_string(DateUtil.getSysDateString());
//				dto.setCreateUserId(userSummary.getUserid());//用户ID，需要规划怎么填写
			}
			Date birthday=DateUtil.formateDate(request.getParameter("birthday"));
			dto.setBirthday_string(request.getParameter("birthday"));
			int i=0;
			if(null!=certifiId && !certifiId.equals("")){//根据id判断是否存在，存在即为更新，不存在即为增加
				i=memberCertifiToolService.updateMemberCertifiDTO(dto);
			}else{
				i=memberCertifiToolService.addMemberCertifiDTO(dto);
			}
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 	  审核
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="audit" )
	@ResponseBody
    public String audit(MemberCertifiDTO dto, HttpServletRequest request){
		try {
			String nowTimeString=DateUtil.toString(new Date(),DateUtil.DATE_FORMAT_DATETIME);
			dto.setCertificationTime_string(nowTimeString);//增加审核时间
			int i=memberCertifiToolService.updateMemberCertifiDTO(dto);

			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			int n=this.saveAuditInfo(regUser, dto);


			if(n>0){
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {

		}
		return null;
	}

	private int saveAuditInfo(SysRegisterUser regUser,MemberCertifiDTO dto) throws Exception{
		AuditInfoDTO aid=new AuditInfoDTO();
		aid.setMainId(dto.getCertifiId());
		aid.setReason(dto.getNgReason());
		aid.setStatus(dto.getStatus());
		String dateTime=DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME);
		aid.setAuditTime_string(dateTime);
		aid.setMemberId(regUser.getUserID());
		aid.setMemberName(regUser.getUserName());
		aid.setCreateTime_string(dateTime);
		aid.setType("2");
		int n=auditInfoToolService.addAuditInfoDTO(aid);
		return n;

	}


	/**
	 * 	  审核
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="auditUnpass" )
	@ResponseBody
    public String auditUnpass(MemberCertifiDTO dto, HttpServletRequest request){
		try {
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			int i=memberCertifiToolService.updateMemberCertifiDTO(dto);
			int n=this.saveAuditInfo(regUser, dto);
			if(n>0){
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 	  审核
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="audit/{certifiId}" )
	@ResponseBody
    public String auditById(@PathVariable("certifiId") String certifiId, HttpServletRequest request){
		try {
			MemberCertifiDTO dto=new MemberCertifiDTO();
			dto.setCertifiId(Long.valueOf(certifiId));
			dto.setStatus("1");
			int i=memberCertifiToolService.updateMemberCertifiDTO(dto);
			if(i>0){
				return "success";
			}else{
				return "failed";
			}
		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 打开编辑页
	 *
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="edit" )
	@ResponseBody
    public String edit( HttpServletRequest request) {
		try {
			String certifiId=request.getParameter("certifiId");
			MemberCertifiDTO dto=memberCertifiToolService.getById(certifiId);
			putModel("dto",dto);

			return "certifi/memberCertifi_edit";
		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 打开编辑页
	 *
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="editbyid/{certifiId}")
    public String editbyid(@PathVariable("certifiId") String certifiId, HttpServletRequest request){
		try {
			MemberCertifiDTO dto=memberCertifiToolService.getById(certifiId);
			putModel("dto",dto);
			return "certifi/memberCertifi_edit";
		} catch (Exception e) {

		}
		return null;
	}



	/**
	 * 打开show页面
	 *
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="showbyid/{certifiId}")
    public String showbyid(@PathVariable("certifiId") String certifiId, HttpServletRequest request){
		try {
			MemberCertifiDTO dto=memberCertifiToolService.getById(certifiId);
			dto.setCommitTime_string(DateUtil.toString(dto.getCommitTime(), "yyyyMMdd HH:mm:ss"));
			putModel("dto",dto);
			return "certifi/memberCertifi_show";
		} catch (Exception e) {

		}
		return null;
	}


	/**
	 * 打开show页面
	 *
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value="unpassShow/{certifiId}")
    public String unpassShow(@PathVariable("certifiId") String certifiId, HttpServletRequest request){
		try {
			MemberCertifiDTO dto=memberCertifiToolService.getById(certifiId);
			putModel("dto",dto);
			return "certifi/memberCertifi_unpassShow";
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
	@RequestMapping(value="querybysearch" )
	@ResponseBody
    public String querybysearch(CertifiQueryBean mqb, HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数  非农速通用户
			map.put("certificationType", "0");
			map.put("account", mqb.getAccount());
			map.put("status", mqb.getStatus());
			map.put("startDate", mqb.getStartDate());
			map.put("endDate", mqb.getEndDate());
			map.put("marketId", mqb.getMarketId());
			map.put("total", memberCertifiToolService.getTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberCertifiDTO> list1 = memberCertifiToolService.getList(map);
			List<MemberCertifiDTO> list = new ArrayList();
			for(MemberCertifiDTO mdt:list1){
				if(null !=mdt.getLevel() && mdt.getLevel().equals("4")){
					mdt.setMarketName("");
				}
				list.add(mdt);
			}
			map.put("rows", list);//rows键 存放每页记录 list
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
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
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(CertifiQueryBean mqb, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String startDate = mqb.getStartDate();
			String endDate = mqb.getEndDate();
			if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
				result.put("status", 0);
				result.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
				return JSONObject.toJSONString(result);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数  非农速通用户
			map.put("certificationType", "0");
			map.put("account", mqb.getAccount());
			map.put("status", mqb.getStatus());
			map.put("startDate", mqb.getStartDate());
			map.put("endDate", mqb.getEndDate());
			map.put("marketId", mqb.getMarketId());
			int total = memberCertifiToolService.getTotal(map);
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

	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String account,  String level,String status,String marketId,String startDate, String endDate) {
		List<MemberCertifiDTO> list = new ArrayList();

		try{
			Map<String, Object> map = new HashMap<String, Object>();

			// 设置查询参数  非农速通用户
			map.put("certificationType", "0");
			map.put("account",account);
			map.put("level", level);
			map.put("status", status);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("marketId", marketId);
			map.put("startRow", 0);
			map.put("endRow", 99999999);
			List<MemberCertifiDTO> list1 = memberCertifiToolService.getList(map);

			for(MemberCertifiDTO mdt:list1){
				if(null !=mdt.getLevel() && mdt.getLevel().equals("4")){
					mdt.setMarketName("");
				}
				list.add(mdt);
			}

		}catch(Exception e){
			e.printStackTrace();
		}


		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("认证列表".getBytes(), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			ouputStream = response.getOutputStream();
			// 查询数据
 			// 在输出流中创建一个新的写入工作簿
			wwb = Workbook.createWorkbook(ouputStream);
			if (wwb != null) {
				WritableSheet sheet = wwb.createSheet("认证列表", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
				// 第一个参数表示列，第二个参数表示行
				Label label00 = new Label(0, 0, "用户类型");// 填充第一行第一个单元格的内容
				Label label10 = new Label(1, 0, "账号");// 填充第一行第二个单元格的内容
				Label label20 = new Label(2, 0, "昵称");// 填充第一行第四个单元格的内容
				Label label30 = new Label(3, 0, "申请时间");// 填充第一行第五个单元格的内容
				Label label40 = new Label(4, 0, "认证状态");// 填充第一行第七个单元格的内容
				Label label50 = new Label(5, 0, "激活状态");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "所属市场");// 填充第一行第六个单元格的内容
				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						MemberCertifiDTO certifiDTO = list.get(i);
						Label label0 =null;
						if(certifiDTO.getLevel()==null){
							label0 = new Label(0, i + 1, "");
						}else if(certifiDTO.getLevel().equals("1")){
							label0 = new Label(0, i + 1, "谷登农批");
						}else if(certifiDTO.getLevel().equals("2")){
							label0 = new Label(0, i + 1, "农速通");
						}else if(certifiDTO.getLevel().equals("3")){
							label0 = new Label(0, i + 1, "农商友");
						}else if(certifiDTO.getLevel().equals("4")){
							label0 = new Label(0, i + 1, "产地供应商");
						}else if(certifiDTO.getLevel().equals("5")){
							label0 = new Label(0, i + 1, "农批友");
						}
						Label label1 = new Label(1, i + 1, certifiDTO.getAccount());
						Label label2 = new Label(2, i + 1, certifiDTO.getNickName());
						Label label3 = new Label(3, i + 1, certifiDTO.getCommitTime()==null?"":time.format(certifiDTO.getCommitTime()));
						Label label4 = null;
						if(certifiDTO.getStatus()==null || "".equals(certifiDTO.getStatus())){
							label4=new Label(4, i + 1, "未提交认证");
						}else if(certifiDTO.getStatus().equals("1")){
							label4=new Label(4, i + 1,  "已认证");
						}else if(certifiDTO.getStatus().equals("2")){
							label4=new Label(4, i + 1,  "已驳回");
						}else if(certifiDTO.getStatus().equals("0")){
							label4=new Label(4, i + 1,  "待认证");
						}


						Label label5 = null;
						if(certifiDTO.getIsActivi()!=null && "1".equals(certifiDTO.getIsActivi())){
							label5 =new Label(5, i + 1, "启用");
						} else  {
							label5 =new Label(5, i + 1, "禁用");
						}

						Label label6 = new Label(6, i + 1, certifiDTO.getMarketName());



						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);// 将单元格加入表格
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
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
