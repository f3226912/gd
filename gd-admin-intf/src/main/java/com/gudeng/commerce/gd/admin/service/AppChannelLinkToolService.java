package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.AppChannelLinkDTO;
import com.gudeng.commerce.gd.customer.entity.AppChannelLink;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface AppChannelLinkToolService extends BaseToolService<AppChannelLinkDTO> {
	public Long insert(AppChannelLink entity) throws Exception;

	public int deleteBatch(List<String> list, String userID)throws Exception;
}