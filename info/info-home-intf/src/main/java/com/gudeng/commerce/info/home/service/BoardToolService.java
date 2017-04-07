package com.gudeng.commerce.info.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.BoardDTO;

public interface BoardToolService {

    /**
     * @Description 根据用户ID查出该用户所拥有的所有看板权限
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月7日 下午3:17:50
     * @Author lidong(dli@gdeng.cn)
     */
    public List<BoardDTO> getListByUserId(Map<String, Object> map) throws Exception;
    
    /**
     * @Description 根据用户ID查找用户有效综合看板数据
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月20日 上午10:22:10
     * @Author lidong(dli@gdeng.cn)
    */
    public List<BoardDTO> getCommonListByUserId(Map<String, Object> map) throws Exception;
}
