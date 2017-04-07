package com.gudeng.commerce.gd.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.service.MemberPageStatisticToolService;
import com.gudeng.commerce.gd.api.service.PvStatisticBusinessToolService;
import com.gudeng.commerce.gd.customer.dto.BusinessPvStatisDTO;
import com.gudeng.commerce.gd.customer.entity.PageStatisMemberEntity;

@Controller
@RequestMapping("vv/test")
public class TestController {

	@Autowired
	public PvStatisticBusinessToolService businessToolService;

	@Autowired
	public MemberPageStatisticToolService pageToolService;

	@RequestMapping("")
	public void test() {
		BusinessPvStatisDTO businessPvStatisDTO = new BusinessPvStatisDTO();
		businessPvStatisDTO.setBusinessId(110L);
		businessPvStatisDTO.setFromPage("2");
		businessPvStatisDTO.setAddPv(1);
		try {
			businessToolService.addPv(businessPvStatisDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("member")
	public void member() {
		PageStatisMemberEntity pageStatisMemberEntity = new PageStatisMemberEntity();
		pageStatisMemberEntity.setMemberId(225L);
		pageStatisMemberEntity.setPageType("1");

		try {
			pageToolService.addMemberPage(pageStatisMemberEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("product")
	public void product() {
		try {
			businessToolService.addPvForProduct(326L, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
