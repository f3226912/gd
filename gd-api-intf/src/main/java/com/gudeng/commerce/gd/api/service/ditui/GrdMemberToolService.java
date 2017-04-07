package com.gudeng.commerce.gd.api.service.ditui;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdMemberEntity;

public interface GrdMemberToolService extends BaseToolService<GrdMemberDTO> {
	public Long insert(GrdMemberEntity entity) throws Exception;
	
	GrdMemberDTO getMemberDTO(Map<String,Object> map) throws Exception ;

	public List<Integer> getUserType(Integer id)throws Exception ;

}