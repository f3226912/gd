package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.Constant;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;
import com.gudeng.commerce.gd.order.dto.CarWeighProDTO;
import com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity;
import com.gudeng.commerce.gd.order.service.CarWeighProService;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@Aspect
@Component
public class CarAopUtil {

	@Autowired
	public GdProperties gdProperties;

	private static CarWeighProService carWeighProService;

	private static SystemLogService systemLogService;

	/**
	 * 功能描述:产品接口服务
	 * 
	 * @param
	 * @return
	 */
	protected CarWeighProService getHessianCarWeighProService()
			throws MalformedURLException {
		String url = gdProperties.getCarWeighProServiceUrl();
		if (carWeighProService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			carWeighProService = (CarWeighProService) factory.create(
					CarWeighProService.class, url);
		}
		return carWeighProService;
	}
	
	private SysRegisterUser getMember() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object user= request.getSession().getAttribute(com.gudeng.commerce.gd.authority.sysmgr.util.Constant.SYSTEM_SMGRLOGINUSER);
		return (SysRegisterUser)user;
	}

	/**
	 * 功能描述:日志服务
	 * 
	 * @param
	 * @return
	 */
	protected SystemLogService getHessianSystemLogService()
			throws MalformedURLException {
		String url = gdProperties.getSystemLogUrl();
		if (systemLogService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			systemLogService = (SystemLogService) factory.create(
					SystemLogService.class, url);
		}
		return systemLogService;
	}

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.CarWeighProToolServiceImpl.insertCarWeighPro(com.gudeng.commerce.gd.order.dto.CarWeighProDTO))")
	public void logCarAdd(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		CarWeighProDTO carWeighProDTO = (CarWeighProDTO) objects[0];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户新增了车辆皮重载重赋值:" + carWeighProDTO.getType() + ",状态："
					+ carWeighProDTO.getStatus() + ",皮重-"
					+ carWeighProDTO.getTareWeigh() + ", 明显少量(0%)总重-"
					+ carWeighProDTO.getZeroperWeigh() + ", 低于半车(30%)总重-"
					+ carWeighProDTO.getThirtyperWeigh() + ", 大概半年(50%)总重-"
					+ carWeighProDTO.getHalfperWeigh() + ", 大概满车(100%)总重-"
					+ carWeighProDTO.getAllWeigh();

			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(carWeighProDTO.getCwpid());
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.CarWeighProToolServiceImpl.updateCarWeighPro(com.gudeng.commerce.gd.order.dto.CarWeighProDTO))")
	public void logCarEdit(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		CarWeighProDTO carWeighProDTO = (CarWeighProDTO) objects[0];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户修改了id为" + carWeighProDTO.getCwpid()
					+ "的车辆载重信息, 修改后的信息如下: ,状态：" + carWeighProDTO.getStatus()
					+ ",皮重-" + carWeighProDTO.getTareWeigh() + ", 明显少量(0%)总重-"
					+ carWeighProDTO.getZeroperWeigh() + ", 低于半车(30%)总重-"
					+ carWeighProDTO.getThirtyperWeigh() + ", 大概半年(50%)总重-"
					+ carWeighProDTO.getHalfperWeigh() + ", 大概满车(100%)总重-"
					+ carWeighProDTO.getAllWeigh();

			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(carWeighProDTO.getCwpid());
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.CarBaseinfoToolServiceImpl.update(com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity))")
	public void logCarNumberEdit(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		CarBaseinfoEntity carBaseinfoEntity = (CarBaseinfoEntity) objects[0];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户修改了id为" + carBaseinfoEntity.getCarId()
					+ "的车辆车牌信息, 修改后的信息如下: 车牌：" + carBaseinfoEntity.getCarNumber();

			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(carBaseinfoEntity.getCarId());
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.ORDER.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.gudeng.commerce.gd.admin.service.impl.CarBaseinfoToolServiceImpl.addCarNumber(com.gudeng.commerce.gd.order.entity.CarBaseinfoEntity,java.lang.Long))")
	public void logCarNumberAdd(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		CarBaseinfoEntity carBaseinfoEntity = (CarBaseinfoEntity) objects[0];
		Long wcId = (Long)objects[1];
		try {
			String content = "id为" + getMember().getUserID()
					+ "的系统用户添加了id为" + carBaseinfoEntity.getCarId()
					+ "的车辆车牌信息, 给过磅数据ID为"+wcId
					+"修改后的信息如下: 车牌：" + carBaseinfoEntity.getCarNumber();

			SystemLogEntity entity = new SystemLogEntity();
			entity.setOperationId(carBaseinfoEntity.getCarId());
			entity.setChennel("1");
			entity.setContent(content);
			entity.setCreateTime(new Date());
			entity.setCreateUserId(getMember().getUserID());
			entity.setType(SystemLogTypeEnum.ORDER.getKey());
			getHessianSystemLogService().insertLog(entity);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
