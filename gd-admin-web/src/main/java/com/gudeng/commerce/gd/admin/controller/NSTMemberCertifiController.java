package com.gudeng.commerce.gd.admin.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
import com.gudeng.commerce.gd.admin.service.AreaSettingToolService;
import com.gudeng.commerce.gd.admin.service.AuditInfoToolService;
import com.gudeng.commerce.gd.admin.service.CarsManageService;
import com.gudeng.commerce.gd.admin.service.MemberBaseinfoToolService;
import com.gudeng.commerce.gd.admin.service.MemberCertifiToolService;
import com.gudeng.commerce.gd.admin.util.DateUtil;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.admin.util.SendMsgUtil;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.customer.dto.AreaSettingDTO;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.dto.CarsDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;

/**
 * 
 * 农速通认证
 * @author 
 *
 */
@Controller
@RequestMapping("nst_certification")
public class NSTMemberCertifiController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(NSTMemberCertifiController.class);
	
	@Autowired
	public MemberCertifiToolService memberCertifiToolService;
	
	@Autowired
	public MemberBaseinfoToolService memberBaseinfoToolService;
	
	@Autowired
	public AuditInfoToolService auditInfoToolService;
	
	@Autowired
	public AreaSettingToolService areaSettingToolService;

	@Autowired
	public CarsManageService carsManageService;
@Autowired
	private IGDBinaryRedisClient redisClient;
	
	@Autowired
	public GdProperties gdProperties;
	/**
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String certifi(HttpServletRequest request){
		try {
			List<AreaSettingDTO> list = areaSettingToolService.getAllAreaName(null);
			request.setAttribute("areaNameList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "nst_certification/memberCertifi_list";
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
			// 设置查询参数
			map.put("certificationType", 1);
			//记录数
			map.put("total", memberCertifiToolService.getNstTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberCertifiDTO> list =memberCertifiToolService.getNstListBySearch(map);
			for(MemberCertifiDTO mdt:list){
				 if("1".equals(mdt.getType()))
				 mdt.setType("个人");
				 else if("2".equals(mdt.getType()))
				 mdt.setType("企业");
				if (mdt.getAuditTime() != null && mdt.getUpdateTime() != null) {
					if (mdt.getAuditTime().before(mdt.getUpdateTime())) {
						mdt.setAuditTime(mdt.getUpdateTime());
					}
				}
				if ("0".equals(mdt.getNstStatus())) {
					mdt.setAuditTime(null);
				}
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			logger.warn("Exception ="+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
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
			map.put("total", memberCertifiToolService.getNstTotal(map));
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
//			mb.setNstStatus(status);
			MemberCertifiDTO mb=new MemberCertifiDTO();
			mb.setNstStatus(status);
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
	 * 	  审核
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="audit" )
	@ResponseBody
    public String audit(MemberCertifiDTO dto, HttpServletRequest request){      
		try {
/*			if (StringUtils.isNotEmpty(dto.getNst_bzlPhotoUrl())) {
				dto.setType("2");
			} else {
				dto.setType("1");
			}*/
			memberBaseinfoToolService.updateUserType(dto.getMemberId(), Integer.parseInt(dto.getType()));
			memberCertifiToolService.updateMemberCertifiDTO(dto);
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
		aid.setReason(dto.getNst_ngReason());
		aid.setStatus(dto.getNstStatus());
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
			if (StringUtils.isNotEmpty(dto.getNst_bzlPhotoUrl())) {
				dto.setType("2");
			} else {
				dto.setType("1");
			}
			SysRegisterUser regUser = (SysRegisterUser)request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
			memberCertifiToolService.updateMemberCertifiDTO(dto);
			int n=this.saveAuditInfo(regUser, dto);
			if(n>0){
				//如果这个用户（被推荐人）为企业用户，审核不通过，发送短消息给该会员的推荐人
				if("2".equals(dto.getType()))
				{
					MemberBaseinfoDTO member  =memberBaseinfoToolService.getMemberInfoByMemberedId(dto.getMemberId());
					if (member != null) {
						String channel =SendMsgUtil.getChannel(redisClient);
						// 发送内容：农速通认证在年月日时分秒被驳回，用户手机号、姓名、公司名称。驳回原因
						String time =DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME);
						String content =String.format(SendMsgUtil.CERTIFICATE_MSG,time, dto.getNst_linkMan(),dto.getMobile(),dto.getCompanyName(),dto.getNst_ngReason());
						SendMsgUtil.sendMsg(channel, content, member.getMobile());
					}
				}
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
			dto.setNstStatus("1");
			int i=memberCertifiToolService.updateMemberCertifiDTO(dto);
			if(i>0){
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
			dto.setCommitTime_string(DateUtil.toString(dto.getUpdateTime(), "yyyyMMdd HH:mm:ss"));
			//查询车辆
			CarsDTO car =	carsManageService.getByEntUserId(String.valueOf(dto.getMemberId()));
			if(car !=null)
			{
			    dto.setCarNumber(car.getCarNumber());
				dto.setNst_vehiclePhotoUrl(car.getNst_vehiclePhotoUrl());
				dto.setNst_driverPhotoUrl(car.getNst_driverPhotoUrl());
			}
			putModel("dto",dto); 
			if(StringUtils.isNotEmpty(dto.getNst_cardPhotoUrl())){
			request.setAttribute("isBigCardImg", checkImgSize(dto.getNst_cardPhotoUrl()));
			}
			if(StringUtils.isNotEmpty(dto.getNst_bzlPhotoUrl())){
				request.setAttribute("isBigBzImg", checkImgSize(dto.getNst_bzlPhotoUrl()));
			}
			if(StringUtils.isNotEmpty(dto.getNst_vehiclePhotoUrl())){
				request.setAttribute("isBigVehicleImg", checkImgSize(dto.getNst_vehiclePhotoUrl()));
			}
			if(StringUtils.isNotEmpty(dto.getNst_driverPhotoUrl())){
				request.setAttribute("isBigDriverImg", checkImgSize(dto.getNst_driverPhotoUrl()));
			}
			return "nst_certification/memberCertifi_show";
		} catch (Exception e) {
			e.printStackTrace();
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
			return "nst_certification/memberCertifi_unpassShow";
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
			// 设置查询参数
			map.put("certificationType", 1);
			map.put("account", mqb.getAccount());
			map.put("nstStatus", mqb.getStatus());
			map.put("startDate", mqb.getStartDate());
			map.put("endDate", mqb.getEndDate());
			map.put("areaName", request.getParameter("areaName"));
			map.put("certifiType", request.getParameter("certifiType"));
			map.put("total", memberCertifiToolService.getNstTotal(map));
			//设定分页,排序
			setCommParameters(request, map);
			//list
			List<MemberCertifiDTO> list = memberCertifiToolService.getNstListBySearch(map);
			for(MemberCertifiDTO mdt:list){
				 if("1".equals(mdt.getType()))
				 mdt.setType("个人");
				 else if("2".equals(mdt.getType()))
				 mdt.setType("企业");
				 if (mdt.getAuditTime() != null && mdt.getUpdateTime() != null) {
						if (mdt.getAuditTime().before(mdt.getUpdateTime())) {
							mdt.setAuditTime(mdt.getUpdateTime());
						}
				}
				if ("0".equals(mdt.getNstStatus())) {
					mdt.setAuditTime(null);
				}
			}
			map.put("rows", list);//rows键 存放每页记录 list  
			String returnStr=JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
			return returnStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@ResponseBody
	@RequestMapping(value = "checkExportParams", produces="application/json; charset=utf-8")
	public String checkExportParams(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
//		if (DateUtil.isDateIntervalOverFlow(startDate, endDate, 31)){
//			resultMap.put("status", 0);
//			resultMap.put("message", "请选择正确的日期范围, 系统最大支持范围为31天..");
//			return JSONObject.toJSONString(resultMap);
//		}
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("certificationType", 1);
			map.put("account", request.getParameter("account"));
			map.put("nstStatus", request.getParameter("status"));
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("areaName", request.getParameter("areaName"));
			int total = memberCertifiToolService.getTotal(map);
			if (total > 10000){
				resultMap.put("status", 0);
				resultMap.put("message", "查询结果集太大(>10000条), 请缩减日期范围, 或者修改其他查询条件...");
				return JSONObject.toJSONString(resultMap);
			}
			resultMap.put("status", 1);
			resultMap.put("message", "参数检测通过");
		}catch(Exception e){
			logger.info("农速通认证导出验证异常 ", e);
			resultMap.put("status", 0);
			resultMap.put("message", "参数检测异常");
		}
		return JSONObject.toJSONString(resultMap);
	}
	
	
	@RequestMapping(value = "exportData")
	public String exportData(HttpServletRequest request, HttpServletResponse response, String account, String status,String startDate, String endDate) {

		List<MemberCertifiDTO> list = null;
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("certificationType", 1);
			map.put("account",account);
			map.put("nstStatus", status);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			map.put("areaName", request.getParameter("areaName"));
			map.put("startRow", 0);
			map.put("endRow", 99999999);
			list = memberCertifiToolService.getNstListBySearch(map);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		WritableWorkbook wwb = null;
		OutputStream ouputStream = null;
		try {
			// 设置输出响应头信息，
			response.setContentType("application/vnd.ms-excel");
			String fileName  = new String("农速通认证表".getBytes(), "ISO8859-1")+startDate+"_"+endDate;
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
				Label label20 = new Label(2, 0, "姓名");// 填充第一行第四个单元格的内容
				Label label30 = new Label(3, 0, "申请时间");// 填充第一行第五个单元格的内容
				Label label40 = new Label(4, 0, "认证状态");// 填充第一行第七个单元格的内容
				Label label50 = new Label(5, 0, "激活状态");// 填充第一行第六个单元格的内容
				Label label60 = new Label(6, 0, "所属区域");// 填充第一行第六个单元格的内容
				Label label70 = new Label(7, 0, "个人/企业");// 填充第一行第六个单元格的内容
				Label label80 = new Label(8, 0, "审核时间");// 填充第一行第六个单元格的内容
				Label label90 = new Label(9, 0, "审核人");// 填充第一行第六个单元格的内容

				sheet.addCell(label00);// 将单元格加入表格
				sheet.addCell(label10);// 将单元格加入表格
				sheet.addCell(label20);// 将单元格加入表格
				sheet.addCell(label30);
				sheet.addCell(label40);
				sheet.addCell(label50);
				sheet.addCell(label60);
				sheet.addCell(label70);
				sheet.addCell(label80);
				sheet.addCell(label90);
				/*** 循环添加数据到工作簿 ***/
				if (list != null && list.size() > 0) {
					
					SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for (int i = 0, lenght = list.size(); i < lenght; i++) {
						MemberCertifiDTO certifiDTO = list.get(i);
						Label label0 =null;
						if ("1".equals(certifiDTO.getType()))
							certifiDTO.setType("个人");
						else if ("2".equals(certifiDTO.getType()))
							certifiDTO.setType("企业");
						if(certifiDTO.getLevel()==null){
						   continue;
						}else if(certifiDTO.getLevel().equals("1")){
							label0 = new Label(0, i + 1, "谷登农批");
						}else if(certifiDTO.getLevel().equals("2")){
							label0 = new Label(0, i + 1, "农速通");
						}else if(certifiDTO.getLevel().equals("3")){
							label0 = new Label(0, i + 1, "农商友");
						}else if(certifiDTO.getLevel().equals("4")){
							label0 = new Label(0, i + 1, "产地供应商");
						}else{
							label0 = new Label(0, i + 1, "谷登农批");
						}
						Label label1 = new Label(1, i + 1, certifiDTO.getAccount());
						Label label2 = new Label(2, i + 1, certifiDTO.getNst_linkMan());
						Label label3 = new Label(3, i + 1, certifiDTO.getUpdateTime()==null?"":time.format(certifiDTO.getUpdateTime()));
						Label label4 = null;
						if(certifiDTO.getNstStatus()==null || "".equals(certifiDTO.getNstStatus())){
							label4=new Label(4, i + 1, "未提交认证");
						}else if(certifiDTO.getNstStatus().equals("1")){
							label4=new Label(4, i + 1,  "已认证");
						}else if(certifiDTO.getNstStatus().equals("2")){
							label4=new Label(4, i + 1,  "已驳回");
						}else if(certifiDTO.getNstStatus().equals("0")){
							label4=new Label(4, i + 1,  "待认证");
						} 			
						
						Label label5 = null;
						if(certifiDTO.getIsActivi()!=null && "1".equals(certifiDTO.getIsActivi())){
							label5 =new Label(5, i + 1, "启用");
						} else  {
							label5 =new Label(5, i + 1, "禁用");
						} 
						Label label6 = new Label(6, i + 1, certifiDTO.getAreaName());
						Label label7 = new Label(7, i + 1, certifiDTO.getType());
						Label label8 = new Label(8, i + 1, certifiDTO.getAuditTime()==null?"":("0".equals(certifiDTO.getNstStatus()))?"" :DateUtil.toString(certifiDTO.getAuditTime(), DateUtil.DATE_FORMAT_DATETIME));
						Label label9 = new Label(9, i + 1, certifiDTO.getMemberName());

						sheet.addCell(label0);// 将单元格加入表格
						sheet.addCell(label1);// 将单元格加入表格
						sheet.addCell(label2);// 将单元格加入表格
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
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
	
	private Boolean checkImgSize(String fileUrl)
	{
	    try {
	    	 String hostUrl = gdProperties.getProperties().getProperty("gd.fileUpload.url");
	 	      //System.out.println("hostUrl -------------->"+hostUrl);
			  File picture = new File(hostUrl+fileUrl);
			  BufferedImage	sourceImg = ImageIO.read(new FileInputStream(picture));
			  if(sourceImg !=null)
			  {
				if (sourceImg.getWidth() >= 500 || sourceImg.getHeight() >= 300)
				 {
					 return true;
				 }
			  }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return false;
	}

}
