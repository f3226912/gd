package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.service.CallstatiSticsService;
import com.gudeng.commerce.gd.m.service.CallstatiSticsToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;

public class CallstatiSticsToolServiceImpl implements CallstatiSticsToolService {

	private static Logger logger = LoggerFactory
			.getLogger(CallstatiSticsToolServiceImpl.class);

	public enum SYS_CODE {
		NSY_BUYER("1"), NSY_SELLER("3"), NST("2");

		SYS_CODE(String value) {
			this.value = value;
		}

		private final String value;

		public String getValue() {
			return value;
		}
	}

	private static CallstatiSticsService callstatiSticsService;
	@Autowired
	public GdProperties gdProperties;

	/**
	 * 单独添加电话记录
	 */
	@Override
	public void insert(CallstatiSticsDTO callstatiSticsDTO) throws Exception {
		getCallstatiSticsService().insert(callstatiSticsDTO);

	}

	protected CallstatiSticsService getCallstatiSticsService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty(
				"gd.callstatiSticsService.url");
		if (callstatiSticsService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			callstatiSticsService = (CallstatiSticsService) factory.create(
					CallstatiSticsService.class, url);
		}
		return callstatiSticsService;
	}
}
