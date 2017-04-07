package com.gudeng.commerce.info.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.BoardDTO;
import com.gudeng.commerce.info.customer.entity.BoardEntity;
import com.gudeng.commerce.info.customer.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	
	
	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long insertEntity(BoardEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("Board.deleteById", map);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		int count=0;
		for(Long id:idList){
			count = deleteById(id);
			if(count<=0) {
				throw new Exception("批量删除记录失败！id:" + id);
			}
		}
		return count;
	}

	@Override
	public int updateDTO(BoardDTO obj) throws Exception {
		int count = baseDao.execute("Board.update", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<BoardDTO> objList) throws Exception {
		int count=0;
		for(BoardDTO dto:objList){
			count = baseDao.execute("Board.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Board.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BoardDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (BoardDTO)this.baseDao.queryForObject("Board.getById", map, BoardDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		List<BoardDTO> list= baseDao.queryForList("Board.getListByConditionPage", map, BoardDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<BoardDTO> list= baseDao.queryForList("Board.getListByCondition", map, BoardDTO.class);
		return list;
	}
    /**
     * @Description 根据用户ID查出该用户所拥有的所有看板权限
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月7日 下午3:17:50
     * @Author lidong(dli@gdeng.cn)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BoardDTO> getListByUserId(Map<String, Object> map) throws Exception {
        List<BoardDTO> list= baseDao.queryForList("Board.getListByUserId", map, BoardDTO.class);
        return list;
    }
    
    /**
     * @Description 根据用户ID查找用户有效综合看板数据
     * @param map
     * @return
     * @throws Exception
     * @CreationDate 2016年3月20日 上午10:22:10
     * @Author lidong(dli@gdeng.cn)
    */
    @SuppressWarnings("unchecked")
    public List<BoardDTO> getCommonListByUserId(Map<String, Object> map) throws Exception{
        List<BoardDTO> list= baseDao.queryForList("Board.getCommonListByUserId", map, BoardDTO.class);
        return list;
    }
}
