package com.gudeng.commerce.info.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.info.admin.service.BoardToolService;
import com.gudeng.commerce.info.admin.util.GdProperties;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.entity.BoardEntity;
import com.gudeng.commerce.info.customer.service.BoardService;

@Service
public class BoardToolServiceImpl implements BoardToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static BoardService pushNoticeService;
	
	private BoardService gethessianBoardService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getBoardServiceUrl();
		if (pushNoticeService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushNoticeService = (BoardService) factory.create(BoardService.class, hessianUrl);
		}
		return pushNoticeService;
	}

	@Override
	public Long insertEntity(BoardEntity obj) throws Exception {
		return gethessianBoardService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianBoardService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianBoardService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(BoardDTO obj) throws Exception {
		return gethessianBoardService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<BoardDTO> objList) throws Exception {
		return gethessianBoardService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianBoardService().getTotal(map);
	}

	@Override
	public BoardDTO getById(Long id) throws Exception {
		return gethessianBoardService().getById(id);
	}

	@Override
	public List<BoardDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianBoardService().getListByConditionPage(map);
	}

	@Override
	public List<BoardDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianBoardService().getListByCondition(map);
	}

	
}
