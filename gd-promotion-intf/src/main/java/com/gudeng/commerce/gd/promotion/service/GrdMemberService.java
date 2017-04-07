package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.GrdMemberDTO;
import com.gudeng.commerce.gd.promotion.entity.GrdMemberEntity;

public interface GrdMemberService extends BaseService<GrdMemberDTO> {
	public Long insert(GrdMemberEntity entity) throws Exception;
	
	/**
	 * 根据参数获取用户
	 * @param params:mobile,id...
	 * @return
	 */
	public GrdMemberDTO getMemberByParams(Map<String,Object> params);
	
	
	/** 根据查询条件获取数据。查询结果按id升序。
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<GrdMemberDTO> queryBySearch(Map<String, Object> map) throws Exception; 
	/**统计 根据查询条件得出的结果 总记录数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int countBySearch(Map<String, Object> map) throws Exception; 
	/**根据id批量删除
	 * @param ids id集合数组
	 * @return 影响的行数
	 * @throws Exception
	 */
	public int deleteByIds(List<String> ids) throws Exception; 
	
	/** 动态更新。读取属性值不为null的数据进行更新。
	 * @param entity
	 * @return 影响的行数
	 * @throws Exception
	 */
	public int dynamicUpdate(GrdMemberEntity entity) throws Exception;
	/**根据id批量重置用户密码。
	 * @param param 集合的key分别为：<br/>
	 * 					ids 需重置的用户id集合，
	 * 					updateUserId：修改人id，
	 * 					defaultPwd：默认密码
	 * 			
	 * @return 影响的行数
	 * @throws Exception
	 */
	public int resetPwdByIds(Map<String, Object> param) throws Exception;
	
	/** 保存数据
	 * @param entity
	 * @return 主键id
	 * @throws Exception
	 */
	public Long save(GrdMemberEntity entity) throws Exception;
	
	

	
	/**根据id批量修改用户状态。
	 * @param param 集合的key分别为：<br/>
	 * 					ids 需重置的用户id集合，
	 * 					updateUserId：修改人id，
	 * 					context：备注信息
	 * 			        status：状态
	 * @return 影响的行数
	 * @throws Exception
	 */
	public int resetStatusByIds(Map<String, Object> param) throws Exception;
	
	 /**根据市场ID查询所属团队信息
	 * @return
	 * @throws Exception
	 */
	 public List<GrdMemberDTO> getChildTeamInfo(Map<String, Object> param)  throws Exception;
	 
	 /**
	  * 根据地推用户id，获取当前地推用户的userType，1表示有农批权限，2表示有农速通的权限
	  * 
	  * */
	 public List<Integer> getUserType(Integer grdUserId)  throws Exception;
	 
	 /**
	 * 查询记录数
	 * 
	 * @param map
	 * @return 记录数
	 * 
	 */
	public int getMember2TeamTotal(Map<String, Object> map) throws Exception;
}