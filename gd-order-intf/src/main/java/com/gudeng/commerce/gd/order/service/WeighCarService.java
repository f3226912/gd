package com.gudeng.commerce.gd.order.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.SalToshopsDetailDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarOrderDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;

/**
 * 过磅数据操作
 * @author Ailen
 *
 */
public interface WeighCarService {
	
	/**
	 * 插入一个过磅数据
	 * @param carEntity
	 * @return
	 * @throws Exception
	 */
	public Long insertEntity(WeighCarEntity carEntity);
	
	/**
	 * 根据ID更新一条过磅
	 * @param carEntity
	 * @return
	 * @throws Exception
	 */
	public int updateById(WeighCarEntity carEntity);
	
	/**
	 * 获得过磅数据量，根据查询条件
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getTotal(Map<String, Object> params);

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public WeighCarDTO getById(Long id);
	
	
	/**
	 * 获取过磅记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<WeighCarDTO> getWeighCar(Map<String, Object> map);

	/**
	 * 获取过磅记录 admin后台使用
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<WeighCarDTO> getWeighCarPageForAdmin(Map<String, Object> map) throws Exception;
	
	/**
	 * 更新总重
	 * @param entity
	 * @return
	 */
	public int udateTotalWeight(WeighCarEntity entity);

	/**
	 * 获取最新的过磅记录
	 * @param map
	 * @return
	 */
	public WeighCarDTO getLastWeighCar(Map<String, Object> map);
	
	/**
	 * 获取五个车牌号码的最新一条过磅记录
	 * @param mobile
	 * @return
	 */
	public List<WeighCarDTO> getLastFiveWeighCar(Map<String, Object> map);
	
	/**
	 * 获得指定时间内的车次通过次数
	 * 天计算
	 * @param carNumber 车牌号
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public int getWeighCarNum(String carNumber, Date startDate, Date endDate);

	/**
	 * 更新皮重信息
	 * @param entity
	 * @return
	 */
	public int updateTareWeight(WeighCarEntity entity);
	
	/**
	 * 获取货主入场过磅信息分页
	 * @return
	 * @throws Exception
	 */
	public List<WeighCarDTO> getEntranceWeighList(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取货主入场过磅信息总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int  getEntranceWeighListTotal(Map<String, Object> map) throws Exception;
	

	/**
	 * 根据ID获得对应过磅数据
	 * @param weighCarId
	 * @return
	 */
	public WeighCarDTO getByIdForAdmin(Long weighCarId) throws Exception;
	

	/**
	 * 根据过磅表Id查询
	 * @return
	 * @throws Exception
	 */
	public WeighCarDTO getEntranceWeighById(String weighCarId) throws Exception;
	
	
	/**
	 * 根据过磅ID查询出过磅的商品信息
	 * @param weighCarId
	 * @return
	 * @throws Exception
	 */
	public List<PreWeighCarDetailDTO> getProductListByWeighCarId(String weighCarId) throws Exception;
	
	/**
	 * 获取两天内的过磅记录
	 * @param memberId
	 * @return
	 */
	public List<WeighCarOrderDTO> getLastTwoDayWeighCarOrder(Long memberId);

	/**
	 * 获取两天内的过磅记录数
	 * @param memberId
	 * @return
	 */
	public int getLastTwoDayWeighCarOrderTotal(Long memberId);
	
	/**
	 * 根据过磅ID查询出场商品信息
	 * @param weighCarId
	 * @return
	 * @throws Exception
	 */
	public List<SalToshopsDetailDTO> getOutProductListByWeighCarId(String weighCarId) throws Exception;

	/**
	 * 更新weigh_car表status字段
	 * @param weighCarEntity
	 * @return
	 */
	public int updateStatus(WeighCarEntity weighCarEntity);

	/**
	 * 根据车牌号码carNumber更新weigh_car表status字段
	 * @param weighCarEntity
	 * @return
	 */
	public int updateStatusByCarId(String status, Long carId);

	/**
	 * 根据weighCarId更新status
	 * @param status
	 * @param weighCarId
	 * @return
	 */
	public int updateStatusByWeighCarId(String status, Long weighCarId);

	/**
	 * 分页获取过磅订单列表
	 * @param map
	 * @return
	 */
	public List<WeighCarOrderDTO> getWeighCarOrderPage(Map<String, Object> map);
	
	public int getWeighCarOrderTotal(Map<String, Object> map);
}
