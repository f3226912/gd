package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.HashMap;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.MemberSubResultDTO;

/**
 * @Description: TODO(会员补贴结果处理)
 * @author mpan
 * @date 2015年12月20日 下午9:33:29
 */
public class MemberSubResultProcess {
	
	private Map<String, MemberSubResultDTO> memberSubResultPool = new HashMap<String, MemberSubResultDTO>();

	/**
	 * @Title: setMemberSubResult
	 * @Description: TODO(设置会员补贴处理结果)
	 * @param memberType 会员类型
	 * @param subStatus 补贴状态
	 * @param auditDesc 补贴审核日志内容
	 */
	public void setMemberSubResult(String memberType, String subStatus, String auditDesc) {
		MemberSubResultDTO memberSubResult = getMemberSubResult(memberType);
		memberSubResult.setSubStatus(subStatus);
		memberSubResult.setAuditDesc(auditDesc);
	}
	
	/**
	 * @Title: setMemberSubStatus
	 * @Description: TODO(设置会员补贴状态)
	 * @param memberType 会员类型
	 * @param subStatus 补贴状态
	 */
	public void setMemberSubStatus(String memberType, String subStatus) {
		MemberSubResultDTO memberSubResult = getMemberSubResult(memberType);
		memberSubResult.setSubStatus(subStatus);
	}
	
	/**
	 * @Title: setMemberSubAuditDesc
	 * @Description: TODO(设置补贴审核日志内容)
	 * @param memberType 会员类型
	 * @param auditDesc 补贴审核日志内容
	 */
	public void setMemberSubAuditDesc(String memberType, String auditDesc) {
		MemberSubResultDTO memberSubResult = getMemberSubResult( memberType);
		memberSubResult.setAuditDesc(auditDesc);
	}
	
	private MemberSubResultDTO getMemberSubResult(String memberType) {
		MemberSubResultDTO memberSubResult = null;
		if (memberSubResultPool.containsKey(memberType)) {
			memberSubResult = memberSubResultPool.get(memberType);
		} else {
			memberSubResult = new MemberSubResultDTO();
			memberSubResultPool.put(memberType, memberSubResult);
		}
		return memberSubResult;
	}

	public Map<String, MemberSubResultDTO> getMemberSubResultPool() {
		return memberSubResultPool;
	}

}
