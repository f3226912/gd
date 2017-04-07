package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.ComplaintEntityDTO;
import com.gudeng.commerce.gd.customer.entity.ComplaintEntity;

public interface ComplaintService {
	public List<ComplaintEntityDTO> getComplaintList(Map<String, Object> map);

	public ComplaintEntityDTO getComplaint(Long id);

	public Integer replyComplaintSave(ComplaintEntityDTO complaintEntityDTO);

	public Long addComplaint(ComplaintEntity ComplaintEntity);

	public int getTotal(Map<String, Object> map) throws Exception;

}
