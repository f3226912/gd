package com.gudeng.commerce.info.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.entity.BoardEntity;

public interface BoardService {

    /**
     * 添加
     * 
     * @param BoardEntity
     *            obj
     * @return Long 添加成功后id
     * 
     */
    public Long insertEntity(BoardEntity obj) throws Exception;

    /**
     * 通过ID删除对象
     * 
     * @param id
     * @return void
     * 
     */
    public int deleteById(Long id) throws Exception;

    /**
     * 批量通过ID删除对象
     * 
     * @param idList
     * @return void
     * 
     */
    public int batchDeleteById(List<Long> idList) throws Exception;

    /**
     * 通过对象更新数据库
     * 
     * @param BoardDTO
     * @return int
     * 
     */
    public int updateDTO(BoardDTO obj) throws Exception;

    /**
     * 批量通过对象更新数据库
     * 
     * @param BoardDTO
     * @return int
     * 
     */
    public int batchUpdateDTO(List<BoardDTO> objList) throws Exception;

    /**
     * 查询记录数
     * 
     * @param map
     * @return 记录数
     * 
     */
    public int getTotal(Map<String, Object> map) throws Exception;

    /**
     * 根据ID查询对象
     * 
     * @param id
     * @return BoardDTO
     * 
     */
    public BoardDTO getById(Long id) throws Exception;

    /**
     * 根据条件查询list(分页查询)
     * 
     * @param map
     * @return List<BoardDTO>
     */
    public List<BoardDTO> getListByConditionPage(Map<String, Object> map) throws Exception;

    /**
     * 根据条件查询list
     * 
     * @param map
     * @return List<BoardDTO>
     */
    public List<BoardDTO> getListByCondition(Map<String, Object> map) throws Exception;

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
