package com.gudeng.commerce.gd.admin.controller.certif;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.admin.controller.AdminBaseController;
import com.gudeng.commerce.gd.admin.dto.KeyValuePair;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
public class CertifCommonController extends AdminBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(CertifCommonController.class);

	@ResponseBody
	@RequestMapping(value="certifCommon/loadStatus",produces="application/json; charset=utf-8")
	public String loadStatus() {
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		try {
			KeyValuePair pair = new KeyValuePair();
			pair.setKeyString("");
			pair.setValueString("全部");
			list.add(pair);

			pair = new KeyValuePair();
			pair.setKeyString("0");
			pair.setValueString("待认证");
			list.add(pair);

			pair = new KeyValuePair();
			pair.setKeyString("1");
			pair.setValueString("已认证");
			list.add(pair);

			pair = new KeyValuePair();
			pair.setKeyString("2");
			pair.setValueString("已驳回");
			list.add(pair);
		} catch (Exception e) {
			logger.info("loadStatus with ex : ", e);
		}
		return JSONObject.toJSONString(list);
	}

}
