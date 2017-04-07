package com.gudeng.commerce.gd.task.agent.impl;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.gudeng.commerce.gd.task.agent.SinxinAdapter;
import com.gudeng.commerce.gd.task.dto.sinxin.ProductSyncDTO;
import com.gudeng.commerce.gd.task.dto.sinxin.Result;
import com.gudeng.commerce.gd.task.util.GdProperties;
import com.gudeng.commerce.gd.task.util.HttpClientUtil;

/**
 * @Description: TODO(深信接口调用适配实现类)
 * @author mpan
 * @date 2016年3月24日 下午4:00:18
 */
public class SinxinAdapterImpl implements SinxinAdapter {
	
	@Autowired
	public GdProperties gdProperties;

	@Override
	public void syncProduct(ProductSyncDTO productSyncDTO) throws Exception {
		String url = gdProperties.getProperties().getProperty("SINXIN_PRODUCT_SYNC_URL");
		String jsonStr = JSON.toJSONString(productSyncDTO);
		String rtXml = HttpClientUtil.getRequest(url + "?Json=" + URLEncoder.encode(jsonStr, "UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new ByteArrayInputStream(rtXml.getBytes()));
		Element root = doc.getRootElement();
		Result result = JSON.parseObject(root.getText(), Result.class);
		if (!"1".equals(result.getReturnValue())) {
			throw new Exception(result.getReturnMsg());
		}
	}

}
