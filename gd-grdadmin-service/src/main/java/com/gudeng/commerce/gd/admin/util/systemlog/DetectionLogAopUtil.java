package com.gudeng.commerce.gd.admin.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.util.Constant;
import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

/**
 * @Description 检测信息功能数据操作记录AOP
 * @Project gd-admin-service
 * @ClassName DetectionLogAopUtil.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月30日 上午11:17:01
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Aspect
@Component
public class DetectionLogAopUtil {

    @Autowired
    public GdProperties gdProperties;

    private static SystemLogService systemLogService;

    /**
     * @Description 获取操作用户
     * @return
     * @CreationDate 2015年12月30日 上午11:20:16
     * @Author lidong(dli@gdeng.cn)
     */
    private SysRegisterUser getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysRegisterUser user = (SysRegisterUser) request.getSession().getAttribute(Constant.SYSTEM_SMGRLOGINUSER);
        return user;
    }

    /**
     * 功能描述:日志服务
     * 
     * @param
     * @return
     */
    protected SystemLogService getHessianSystemLogService() throws MalformedURLException {
        String url = gdProperties.getSystemLogUrl();
        if (systemLogService == null) {
            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setOverloadEnabled(true);
            systemLogService = (SystemLogService) factory.create(SystemLogService.class, url);
        }
        return systemLogService;
    }

    /**
     * @Description 新增检测信息
     * @CreationDate 2015年12月30日 上午11:17:32
     * @Author lidong(dli@gdeng.cn)
     */
    @Pointcut("execution(* com.gudeng.commerce.gd.admin.service.impl.DetectionToolServiceImpl.addDetectionDTO(..))")
    private void add() {
    }

    /**
     * @Description 删除检测信息
     * @CreationDate 2015年12月30日 上午11:17:40
     * @Author lidong(dli@gdeng.cn)
     */
    @Pointcut("execution(* com.gudeng.commerce.gd.admin.service.impl.DetectionToolServiceImpl.deleteDetection(..))")
    private void delete() {
    }

    /**
     * @Description 导入检测信息
     * @CreationDate 2015年12月30日 上午11:17:49
     * @Author lidong(dli@gdeng.cn)
     */
    @Pointcut("execution(* com.gudeng.commerce.gd.admin.service.impl.DetectionToolServiceImpl.addDetectionBacth(..))")
    private void addBacth() {
    }

    /**
     * @Description 修改检测信息
     * @CreationDate 2015年12月30日 上午11:52:07
     * @Author lidong(dli@gdeng.cn)
     */
    @Pointcut("execution(* com.gudeng.commerce.gd.admin.service.impl.DetectionToolServiceImpl.updateDetectionDTO(..))")
    private void update() {
    }

    /**
	 * @Description 删除操作记录
	 * @param jp
	 * @CreationDate 2015年12月30日 上午11:18:18
	 * @Author lidong(dli@gdeng.cn)
	 */
	@AfterReturning("delete()")
	public void logAccTransInfoDel(JoinPoint jp) {
		Object[] objects = jp.getArgs();
		List<String> list = (List<String>) objects[0];
		if (list == null || list.size() < 1) {
			return;
		}
		SysRegisterUser user = getUser();
		try {
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i);
				String content = "ID=" + user.getUserID() + " 用户" + user.getUserName() + "【删除】检测信息:ID=" + id;
				SystemLogEntity entity = new SystemLogEntity();
				entity.setOperationId(Long.valueOf(id));
				entity.setChennel("1");
				entity.setContent(content);
				entity.setCreateTime(new Date());
				entity.setCreateUserId(user.getUserID());
				entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
				Long l = getHessianSystemLogService().insertLog(entity);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * @Description 修改操作记录
     * @param jp
     * @CreationDate 2015年12月30日 上午11:18:31
     * @Author lidong(dli@gdeng.cn)
     */
    @AfterReturning("update()")
    public void updateDetectionDTO(JoinPoint jp) {
        Object[] objects = jp.getArgs();
        DetectionDTO dto = (DetectionDTO) objects[0];
        if (dto == null) {
            return;
        }
        SysRegisterUser user = getUser();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("ID=" + user.getUserID() + " 用户" + user.getUserName());
            sb.append("【修改】检测信息:");
            sb.append("ID=" + dto.getId());
            sb.append("商品名称=" + dto.getProductName());
            sb.append("出产地=" + dto.getOrigin());
            sb.append("被检单位或姓名=" + dto.getUnitName());
            sb.append("检测项目=" + dto.getInspection());
            sb.append("抑制率=" + dto.getRate());
            sb.append("是否合格=" + dto.getPass());
            sb.append("检测日期=" + dto.getDetectTime_str());
            sb.append("发布日期=" + dto.getPublishTime_str());
            sb.append("交易市场=" + dto.getMaketId());
            SystemLogEntity entity = new SystemLogEntity();
            entity.setOperationId(dto.getId());
            entity.setChennel("1");
            entity.setContent(sb.toString());
            entity.setCreateTime(new Date());
            entity.setCreateUserId(user.getUserID());
            entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
            Long l = getHessianSystemLogService().insertLog(entity);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description 新增操作记录
     * @param jp
     * @CreationDate 2015年12月30日 上午11:18:31
     * @Author lidong(dli@gdeng.cn)
     */
    @AfterReturning("add()")
    public void logAccTransInfoAdd(JoinPoint jp) {
        Object[] objects = jp.getArgs();
        DetectionDTO dto = (DetectionDTO) objects[0];
        if (dto == null) {
            return;
        }
        SysRegisterUser user = getUser();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("ID=" + user.getUserID() + " 用户" + user.getUserName());
            sb.append("【添加】检测信息:");
            sb.append("商品名称=" + dto.getProductName());
            sb.append("出产地=" + dto.getOrigin());
            sb.append("被检单位或姓名=" + dto.getUnitName());
            sb.append("检测项目=" + dto.getInspection());
            sb.append("抑制率=" + dto.getRate());
            sb.append("是否合格=" + dto.getPass());
            sb.append("检测日期=" + dto.getDetectTime_str());
            sb.append("发布日期=" + dto.getPublishTime_str());
            sb.append("交易市场=" + dto.getMaketId());
            SystemLogEntity entity = new SystemLogEntity();
            entity.setChennel("1");
            entity.setContent(sb.toString());
            entity.setCreateTime(new Date());
            entity.setCreateUserId(user.getUserID());
            entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
            Long l = getHessianSystemLogService().insertLog(entity);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description 导入操作记录
     * @param jp
     * @CreationDate 2015年12月30日 上午11:19:00
     * @Author lidong(dli@gdeng.cn)
     */
    @AfterReturning("addBacth()")
    public void logAccTransInfoAddBatch(JoinPoint jp) {
        Object[] objects = jp.getArgs();
        List<DetectionDTO> list = (List<DetectionDTO>) objects[0];
        if (list == null || list.size() < 1) {
            return;
        }
        SysRegisterUser user = getUser();
        try {
            for (int i = 0; i < list.size(); i++) {
                DetectionDTO dto = list.get(i);
                StringBuilder sb = new StringBuilder();
                sb.append("ID=" + user.getUserID() + " 用户" + user.getUserName());
                sb.append("【添加】检测信息:");
                sb.append("商品名称=" + dto.getProductName());
                sb.append("出产地=" + dto.getOrigin());
                sb.append("被检单位或姓名=" + dto.getUnitName());
                sb.append("检测项目=" + dto.getInspection());
                sb.append("抑制率=" + dto.getRate());
                sb.append("是否合格=" + dto.getPass());
                sb.append("检测日期=" + dto.getDetectTime_str());
                sb.append("发布日期=" + dto.getPublishTime_str());
                sb.append("交易市场=" + dto.getMaketId());
                SystemLogEntity entity = new SystemLogEntity();
                entity.setChennel("1");
                entity.setContent(sb.toString());
                entity.setCreateTime(new Date());
                entity.setCreateUserId(user.getUserID());
                entity.setType(SystemLogTypeEnum.INFOMATION.getKey());
                Long l = getHessianSystemLogService().insertLog(entity);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
