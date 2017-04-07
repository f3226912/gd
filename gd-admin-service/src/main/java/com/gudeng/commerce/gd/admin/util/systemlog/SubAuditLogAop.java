package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.SubAuditWithMemInfoDTO;
import com.gudeng.commerce.gd.order.service.SubAuditService;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;


@Aspect
@SuppressWarnings("unchecked")
public class SubAuditLogAop {
	
	
	@Autowired
	public GdProperties gdProperties;
	
	private static SystemLogService systemLogService;
	
	private static SubAuditService subAuditService;
	
	
	protected SystemLogService getHessianSystemLogService() throws MalformedURLException {
		String url = gdProperties.getSystemLogUrl();
		if (systemLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			systemLogService = (SystemLogService) factory.create(SystemLogService.class, url);
		}
		return systemLogService;
	}
	
	protected SubAuditService getHessianSubAuditService() throws MalformedURLException {
		String url = gdProperties.getSubAuditServiceUrl();
		if(subAuditService == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			subAuditService = (SubAuditService)factory.create(SubAuditService.class, url);
		}
		return subAuditService;
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.SubAuditToolServiceImpl.updateSubStatusById(java.util.Map))",returning= "result")
	public void logSubAuditById(JoinPoint jp, Object result){
		// 操作成功返回null
		if(null != result){
			return;
		}
		
		try {
			Map<String,Object> params = (Map<String,Object>) jp.getArgs()[0];							// 参数(in)
			List<Integer> ids = new ArrayList<Integer>();
			ids.add((Integer)params.get("auditId"));
			
			// 获取补贴相关信息
			List<SubAuditWithMemInfoDTO> sais = this.getHessianSubAuditService().getSubAuditInfo(ids);	// 根据ids查询补贴相关信息
			
			List<SystemLogEntity> logEntityList = generateLogContent(sais, (String)params.get("updateUserId"));
			
			
			
			getHessianSystemLogService().insertLog(logEntityList.get(0));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.SubAuditToolServiceImpl.batchUpdateSubStatusAsPass(java.util.List, java.util.Map))",returning= "result")
	public void logBatchUpdateSubStatusAsPass(JoinPoint jp, Object result){
		if(!"SUCCESS".equals(result)){
			return;
		}
		try {
			List<Integer> ids = (List<Integer>) jp.getArgs()[0];			//处理的id
			Map<String, Object> params = (Map<String, Object>)jp.getArgs()[1];
			
			List<SubAuditWithMemInfoDTO> sais = this.getHessianSubAuditService().getSubAuditInfo(ids);	// 根据ids查询补贴相关信息
			
			List<SystemLogEntity> logEntityList = generateLogContent(sais, (String)params.get("updateUserId"));
			
			//  batch插入log
			getHessianSystemLogService().batchInsertLog(logEntityList);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<SystemLogEntity> generateLogContent(List<SubAuditWithMemInfoDTO> sais, String createUserId){
		List<SystemLogEntity> result = new ArrayList<SystemLogEntity>();
		
		for(SubAuditWithMemInfoDTO sai: sais){
			StringBuffer content = new StringBuffer();			// 日志内容
			SystemLogEntity logEntity = new SystemLogEntity();	// 日志实体类
			
			String subStatus = sai.getSubStatus();
			content.append("补贴审批:\"id:").append(sai.getAuditId());
			content.append(",订单号:").append(sai.getOrderNo());
			content.append(",修改补贴状态为:").append(subStatus);
			
			// 更新新时间
			//content.append("; 更新时间：").append(getTimeStr(sai.getUpdateTime()));
			
			// 如果审核通过，还要记录补贴金额
			if("3".equals(subStatus)){
				if(null != sai.getBuyerSubAmount() && sai.getBuyerSubAmount() > 0){
					content.append(", 买家(").append(createUserId).append(")补贴:").append(sai.getBuyerSubAmount());
				}
				
				if(null != sai.getSellSubAmount() && sai.getSellSubAmount() > 0){
					content.append(", 卖家(").append(sai.getSellMemberId()).append(")补贴:").append(sai.getSellSubAmount());
				}
				
				if(null != sai.getSuppSubAmount() && sai.getSuppSubAmount() > 0){
					content.append(", 卖家(").append(sai.getSuppMemberId()).append(")补贴:").append(sai.getSuppSubAmount());
				}
			}
			
			content.append("\"");
			
			logEntity.setChennel("1");
			logEntity.setContent(content.toString());
			logEntity.setCreateTime(new Date());
			logEntity.setCreateUserId(createUserId);
			logEntity.setOperationId((long)sai.getAuditId());
			logEntity.setType(SystemLogTypeEnum.SUBSIDY.getKey());
			result.add(logEntity);
		}
		
		return result;
	}
	
}
















