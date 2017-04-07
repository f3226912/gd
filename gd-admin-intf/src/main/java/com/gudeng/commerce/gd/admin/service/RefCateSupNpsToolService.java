package com.gudeng.commerce.gd.admin.service;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.RefCateSupNpsDTO;

/**
 * 农批商供应商关联service for admin
 * @author ailen
 *
 */
public interface RefCateSupNpsToolService {
	
	/**
	 * 获得与对应农批商的供应商ID关联关系
	 * @param cateId
	 * @return
	 */
	public List<RefCateSupNpsDTO> getRefCateSupNpsByNpsCateId(Long cateId, String type) throws Exception;

	/**
	 * 修改对应的农批商关联关系
	 * 此处逻辑：
	 * 		从其中一个对象中的到对应农批商的categoryId
	 * 		删除所有与之对应的供应商关联数据
	 * 		批量添加对应关联供应商数据
	 * 		清空REDIS数据 只清空对应农批商的供应商分类集合数据（只存储集合数据）
	 * @param refs
	 */
	public void updateRefCateSupNps(List<RefCateSupNpsDTO> refs) throws Exception;
}
