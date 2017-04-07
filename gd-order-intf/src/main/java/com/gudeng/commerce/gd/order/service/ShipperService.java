package com.gudeng.commerce.gd.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.ReWeighCarBusinessDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;

public interface ShipperService {
	/**
	 * 保存货主入磅信息
	 * @param weighCarDTO
	 * @return
	 * @throws Exception
	 */
	public Long saveWeightLog(WeighCarEntity weighCarEntity);
	/**
	 * 添加产品
	 */
	public Integer addProduct(PreWeighCarDetailDTO preWeighCarDetail);
	/**
	 * 更新产品
	 * @param preWeighCarDetail
	 * @return
	 * @throws Exception
	 */
	public Integer updateProduct(PreWeighCarDetailDTO preWeighCarDetail);
	
	/**
	 * 通过id删除产信息
	 * @param id
	 * @return
	 */
	public Integer delProduct(PreWeighCarDetailDTO preWeighCarDetail);
	
	/**
	 * 获取添加的产品列表
	 * @param preWeighCarDetail
	 * @return List<PreWeighCarDetailDTO>
	 */
	public List<PreWeighCarDetailDTO> getProductlist(PreWeighCarDetailDTO preWeighCarDetail);
	
	/**
	 * 批量货主入磅信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer submitWeightLog(List<ReWeighCarBusinessDTO> list);
	/**
	 * 保存类目
	 * @param reWeighCarBusinessDTO
	 * @return
	 */
	public Integer submitWeightLogClass(ReWeighCarBusinessDTO reWeighCarBusinessDTO);
	/**
	 * 更新状态
	 * @param weighCarId
	 */
	public Integer updateWeightCarStatus(Long weighCarId);
	/**
	 * 提交
	 * @param request
	 * @param response
	 * @param reWeighCarBusinessDTO
	 * @return
	 */
	public Integer submit(ReWeighCarBusinessDTO reWeighCarBusinessDTO);
}
