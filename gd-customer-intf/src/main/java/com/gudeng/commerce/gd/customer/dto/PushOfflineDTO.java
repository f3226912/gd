package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.PushOffline;

/**   
 * @Description 线下推广统计
 * @Project gd-customer-intf
 * @ClassName PushOfflineDTO.java
 * @Author lidong(dli@gdeng.cn)    
 * @CreationDate 2016年2月18日 上午11:56:52
 * @Version V2.0
 * @Copyright 谷登科技 2015-2016
 * @ModificationHistory
 */
public class PushOfflineDTO extends PushOffline {

    private static final long serialVersionUID = 7144221358734680188L;
    private String createTimeStr;//推广时间
    public String getCreateTimeStr() {
        return createTimeStr;
    }
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
    
    
}
