package com.gudeng.commerce.gd.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.api.service.PushProductToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;

@Service
public class PushProductToolServiceImpl implements PushProductToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
}
