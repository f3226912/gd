package com.gudeng.commerce.gd.admin.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ComplaintEntityDTO;

public interface ComplaintToolService {
	/**
	 * 获取投诉列表
	 * @param map
	 * @return
	 * @throws MalformedURLException
	 */
	public List<ComplaintEntityDTO> getComplaintList(Map<String, Object> map) throws MalformedURLException;

	public int getTotal(Map<String, Object> map) throws  Exception;

	/**
	 * 通过id过去投诉信息
	 * @param id
	 * @return
	 * @throws MalformedURLException
	 */
	public ComplaintEntityDTO getComplaint(Long id)throws MalformedURLException;

	/**
	 * 保存回复内容
	 * @param complaintEntityDTO
	 * @return
	 * @throws MalformedURLException
	 */
	public Integer replyComplaintSave(ComplaintEntityDTO complaintEntityDTO) throws MalformedURLException;

}
