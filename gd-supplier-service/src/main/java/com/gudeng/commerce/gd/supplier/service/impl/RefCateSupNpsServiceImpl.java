package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.supplier.bo.CacheBo;
import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.dto.RefCateSupNpsDTO;
import com.gudeng.commerce.gd.supplier.service.RefCateSupNpsService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service
public class RefCateSupNpsServiceImpl implements RefCateSupNpsService {

	@Autowired
	private BaseDao baseDao;

	@Autowired
	private CacheBo cacheBo;
	
	@Override
	public List<RefCateSupNpsDTO> getRefCateSupNpsByNpsCateId(Long cateId, String type) {
		try {
			//redis中加载
			return cacheBo.getRefCateSupNpsByNpsCateId(cateId, type, baseDao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public void updateRefCateSupNps(List<RefCateSupNpsDTO> refs) {
		if(refs!=null && refs.size()>0) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("cateId", refs.get(0).getNpsCategoryId());
			paramMap.put("type", refs.get(0).getType());
			
			//删除以前的关联
			baseDao.execute("RefCateSupNps.deleteRefCateSupNpsByNpsCateId", paramMap);
			
			//清楚redis数据
			cacheBo.deleteRefCateSupNpsByCateId(refs.get(0).getNpsCategoryId(), refs.get(0).getType());
			
			/*
			 * 判断是否存在新的关联
			 * 如果suppCategoryId为空，代表删除数据，取消关联
			 */
			if(refs.get(0).getSuppCategoryId()!=null) {
				paramMap.clear();
				paramMap.put("cateList", refs);
				
				//批量添加对应关联
				baseDao.execute("RefCateSupNps.updateRefCateSupNps", paramMap);
			}
		}else {
			throw new RuntimeException("农批商关联供应商，没有提供有效的关联数据，如无关联需提供空suppCategoryId对象用于删除npsCategoryId对应关联数据，数据为空！");
		}
	}

	@Override
	public Long[] getRefCateSupNpsForHHY(List<Long> cateIds) {
		
		//初始化SET 去除重复项用
		Set<Long> setIds = new HashSet<Long>();
		
		/*
		 * 遍历主要分类
		 */
		for (int i = 0; i < cateIds.size(); i++) {
			Long cateId = cateIds.get(i);
			
			//获取主要分类中对应的供应商
			List<RefCateSupNpsDTO> refs = getRefCateSupNpsByNpsCateId(cateId, "1");
			
			if(refs!=null) {
				//遍历供应商数据存储在Set中 去除重复项
				for (int j = 0; j < refs.size(); j++) {
					setIds.add(refs.get(j).getSuppCategoryId());
				}
			}
		}
		
		Long[] result = new Long[setIds.size()];
		//返回数组，便于性能提高
		return setIds.toArray(result);
	}
	
	

}
