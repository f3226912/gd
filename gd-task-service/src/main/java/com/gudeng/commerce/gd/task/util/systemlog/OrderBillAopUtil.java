package com.gudeng.commerce.gd.task.util.systemlog;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.OrderBillDTO;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.enums.SystemLogTypeEnum;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;
import com.gudeng.commerce.gd.task.util.GdProperties;

@Aspect
@Component
public class OrderBillAopUtil {

    @Autowired
    public GdProperties gdProperties;

    private static SystemLogService systemLogService;

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderBillAopUtil.class);

    /**
     * 功能描述:日志服务
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

    @Pointcut("execution(* com.gudeng.commerce.gd.task.service.impl.OrderBillTaskServiceImpl.batchAddDTO(..))")
    private void batchAdd() {
    }
    
    
    @AfterReturning("batchAdd()")
    public void batchAdd(JoinPoint jp) {
        Object[] objects = jp.getArgs();
        try {
            if (objects[0]==null) {
                return;
            }
            List<OrderBillDTO> list = (List<OrderBillDTO>) objects[0];
            if (list != null && list.size() > 0) {
                for (OrderBillDTO dto : list) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("交易订单流水【导入】,订单信息");
                    sb.append("商户号:" + dto.getBusinessNo());
                    sb.append("商户名称:" + dto.getBusinessName());
                    sb.append("交易类型:" + dto.getTradeType());
                    sb.append("交易时间:" + dto.getTradeDayStr());
                    sb.append("交易卡号:" + dto.getCardNo());
                    sb.append("终端号:" + dto.getClientNo());
                    sb.append("交易金额:" + dto.getTradeMoney());
                    sb.append("系统参考号:" + dto.getSysRefeNo());
                    sb.append("手续费:" + dto.getFee());
                    SystemLogEntity entity = new SystemLogEntity();
                    entity.setChennel("1");
                    entity.setContent(sb.toString());
                    entity.setCreateTime(new Date());
                    entity.setCreateUserId("SYS");
                    entity.setType(SystemLogTypeEnum.ORDER.getKey());
                    getHessianSystemLogService().insertLog(entity);
                }
            }
        } catch (Exception e) {
            LOGGER.error("记录业务日志失败", e);
        }
    }
}
