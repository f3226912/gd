package com.gudeng.commerce.gd.bi.service;

import java.util.Map;

import com.gudeng.commerce.gd.bi.dto.GrdProMemberInvitedRegisterDTO;
import com.gudeng.commerce.gd.bi.entity.GrdProMemberInvitedRegisterEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface GrdProMemberInvitedRegisterService extends BaseService<GrdProMemberInvitedRegisterDTO> {
	public Long insert(GrdProMemberInvitedRegisterEntity entity) throws Exception;
	
	public int insert(Map map) throws Exception;
}