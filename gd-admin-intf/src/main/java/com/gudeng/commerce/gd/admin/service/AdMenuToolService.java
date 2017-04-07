package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AdMenuDTO;

public interface AdMenuToolService {

    List<AdMenuDTO> getAdMenuByCondition(Map<String, Object> map) throws Exception;

    Long insert(AdMenuDTO adMenuDTO) throws Exception;

    Long update(AdMenuDTO adMenuDTO) throws Exception;

    Long delete(Map<String, Object> map)throws Exception;

    AdMenuDTO getById(Long id) throws Exception;
}
