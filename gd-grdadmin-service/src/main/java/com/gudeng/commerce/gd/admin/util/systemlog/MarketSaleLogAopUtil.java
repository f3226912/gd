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
import com.gudeng.commerce.gd.supplier.dto.MarketSaleDTO;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

/**
 * @Description 市场交易额功能数据操作记录AOP
 * @Project gd-admin-service
 * @ClassName MarketSaleLogAopUtil.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月30日 上午11:17:01
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Aspect
@Component
public class MarketSaleLogAopUtil {

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
     * @Description 新增
     * @CreationDate 2015年12月30日 上午11:17:32
     * @Author lidong(dli@gdeng.cn)
     */
    @Pointcut("execution(* com.gudeng.commerce.gd.admin.service.impl.MarketSaleServiceImpl.addMarketSale(..))")
    private void add() {
    }

    /**
     * @Description 删除
     * @CreationDate 2015年12月30日 上午11:17:40
     * @Author lidong(dli@gdeng.cn)
     */
    @Pointcut("execution(* com.gudeng.commerce.gd.admin.service.impl.MarketSaleServiceImpl.deleteMarketSale(..))")
    private void delete() {
    }

    /**
     * @Description 修改
     * @CreationDate 2015年12月30日 上午11:52:07
     * @Author lidong(dli@gdeng.cn)
     */
    @Pointcut("execution(* com.gudeng.commerce.gd.admin.service.impl.MarketSaleServiceImpl.updateMarketSaleDTO(..))")
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
                String content = "ID=" + user.getUserID() + " 用户" + user.getUserName() + "【删除】市场交易额信息:ID=" + id;
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
    public void updatePricesDTO(JoinPoint jp) {
        Object[] objects = jp.getArgs();
        MarketSaleDTO dto = (MarketSaleDTO) objects[0];
        if (dto == null) {
            return;
        }
        SysRegisterUser user = getUser();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("ID=" + user.getUserID() + " 用户" + user.getUserName());
            sb.append("【修改】市场交易额信息:");
            sb.append("ID=" + dto.getId());
            sb.append("交易市场=" + dto.getMarketId());
            sb.append("今日交易额=" + dto.getTodaySale());
            sb.append("昨日交易额=" + dto.getYestodaySale());
            SystemLogEntity entity = new SystemLogEntity();
            entity.setOperationId(Long.valueOf(dto.getId()));
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
        MarketSaleDTO dto = (MarketSaleDTO) objects[0];
        if (dto == null) {
            return;
        }
        SysRegisterUser user = getUser();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("ID=" + user.getUserID() + " 用户" + user.getUserName());
            sb.append("【添加】市场交易额信息:");
            sb.append("交易市场=" + dto.getMarketId());
            sb.append("今日交易额=" + dto.getTodaySale());
            sb.append("昨日交易额=" + dto.getYestodaySale());
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
}
