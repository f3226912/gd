package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.MemberCertifiDTO;

public interface MemberCerifiToolService {

		/**
		 * 根据ID查询对象
		 * 
		 * @param id
		 * @return MemberCertifiDTO
		 * 
		 */
		public MemberCertifiDTO getById(String id)throws Exception;
		


		
		/**
		 * 查询记录数
		 * 
		 * @param map
		 * @return 记录数
		 * 
		 */
		public int getTotal(Map<String,Object> map)throws Exception;
		
		
		/**
		 * 通过ID删除对象
		 * 
		 * @param id
		 * @return void
		 * 
		 */
		public int deleteById(String id)throws Exception;
		
		/**
		 * 通过Map插入数据库
		 * 
		 * @param map
		 * @return int
		 * 
		 */
		public int addMemberCertifiByMap(Map<String,Object> map)throws Exception;

		/**
		 * 通过对象插入数据库
		 * 
		 * @param MemberCertifiDTO
		 * @return int
		 * 
		 */
		public int addMemberCertifiDTO(MemberCertifiDTO mc) throws Exception;
		
		/**
		 * 通过对象更新数据库
		 * 
		 * @param MemberCertifiDTO
		 * @return int
		 * 
		 */
		public int updateMemberCertifiDTO(MemberCertifiDTO mc) throws Exception;
		
		/**
		 * 通过对象更新数据库
		 * 
		 * @param MemberCertifiDTO
		 * @return int
		 * 
		 */
		public List<MemberCertifiDTO> getBySearch(Map map) throws Exception;

		/**
		 * 通过对象更新数据库
		 * 
		 * @param MemberCertifiDTO
		 * @return int
		 * 
		 */
		public MemberCertifiDTO getByUserId(String userId) throws Exception;



}
