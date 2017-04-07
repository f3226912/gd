package com.gudeng.commerce.gd.api.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.ReWeighCarBusinessDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;

/**
 * 货主入场过磅服务
 * @author bob
 *
 */
public interface ShipperToolService {
	/**
	 * 保存货主入磅信息
	 * @param weighCarDTO
	 * @return id
	 * @throws Exception
	 */
	public Long saveWeightLog(WeighCarEntity weighCarEntity) throws Exception;

	/**
	 * 添加产品
	 */
	public Integer addProduct(PreWeighCarDetailDTO preWeighCarDetail)  throws Exception;

	/**
	 * 更新产品
	 * @param preWeighCarDetail
	 * @return
	 * @throws Exception
	 */
	public Integer updateProduct(PreWeighCarDetailDTO preWeighCarDetail) throws Exception;

	/**
	 * 通过id删除产信息
	 * @param id
	 * @return
	 */
	public Integer delProduct(PreWeighCarDetailDTO preWeighCarDetail) throws Exception;

	/**
	 * 获取添加的产品列表
	 * @param preWeighCarDetail
	 * @return List<PreWeighCarDetailDTO>
	 */
	public  List<PreWeighCarDetailDTO> getProductlist(PreWeighCarDetailDTO preWeighCarDetail) throws Exception;

	/**
	 * 批量货主入磅信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer submitWeightLog(List<ReWeighCarBusinessDTO> list) throws Exception;

	/**
	 * 保存类目
	 * @param reWeighCarBusinessDTO
	 * @return
	 */
	public Integer submitWeightLogClass(ReWeighCarBusinessDTO reWeighCarBusinessDTO) throws Exception;
	/**
	 * 提交
	 * @param request
	 * @param response
	 * @param reWeighCarBusinessDTO
	 */
	public Integer submit(ReWeighCarBusinessDTO reWeighCarBusinessDTO) throws Exception;
	
}
