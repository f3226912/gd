package com.gudeng.commerce.gd.m.util;

import java.util.ArrayList;
import java.util.Map;

import com.gudeng.commerce.gd.m.dto.PageDTO;


/**
 * 分页对应pageDTO处理工具
 * 
 * @author Administrator
 * 
 */
public class PageUtil {

	/**
	 * 创建一个pageDTO对象 默认 第一页 页大小 10
	 * 
	 * @return
	 */
	public static <T> PageDTO<T> create(Class<T> c) {
		PageDTO<T> pageDTO = new PageDTO<T>();

		return pageDTO;
	}
	
	/**
	 * 预留方法
	 * 用于重新初始化pageDTO
	 * @param pageDTO
	 */
	public static <T> void reinitialize(PageDTO<T> pageDTO) {
		pageDTO.setCurrentPage(1);
		pageDTO.setIsEmpty(false);
		pageDTO.setPageData(new ArrayList<T>());
	}

	/**
	 * 创建一个pageDTO对象 默认第一页
	 * 
	 * @param pageSize
	 *            页大小
	 * @return
	 */
	public static <T> PageDTO<T> create(Class<T> c, int pageSize) {
		PageDTO<T> pageDTO = new PageDTO<T>();

		pageDTO.setPageSize(pageSize);

		return pageDTO;
	}
	
	/**
	 * 计算Page页面数据
	 * 在查询总记录数后使用
	 * @param pageDTO
	 */
	public static <T> void preCatchPagedataCalculate(PageDTO<T> pageDTO) {
		/*
		 * 计算总页数
		 */
		int size = pageDTO.getTotalSize() / pageDTO.getPageSize();// 总条数/每页显示的条数=总页数
		int mod = pageDTO.getTotalSize() % pageDTO.getPageSize();// 最后一页的条数
		if (mod != 0)
			size++;
		
		//设置到pageDTO中
		pageDTO.setPageTotal(pageDTO.getTotalSize() == 0 ? 1 : size);
		
		/*
		 * 计算当前页是否超标
		 * 超标设置最大页
		 */
		if(pageDTO.getPageTotal()< pageDTO.getCurrentPage()) {
			pageDTO.setCurrentPage(pageDTO.getPageTotal());
		}
		
	}
	
	/**
	 * 计算 添加数据到Page对象中 Page页面数据
	 * 在显示数据之前
	 * @param pageDTO
	 */
	public static <T> void preShowPageCalculate(PageDTO<T> pageDTO) {
		
		//数据为空
		if(pageDTO.getTotalSize()==0) {
			pageDTO.setIsEmpty(true);
		}
		
	}

	/**
	 * 首页数据
	 */
	public static <T> void top(PageDTO<T> pageDTO, Map<String, Object> paramMap) {
		// 初始化数据对象
		pageDTO.setPageData(new ArrayList<T>());

		// 设置查询条件
		paramMap.put("startRow", 0);
		pageDTO.setCurrentPage(0);
		paramMap.put("endRow", pageDTO.getPageSize());

	}
	
	/**
	 * 末页数据
	 */
	public static <T> void foot(PageDTO<T> pageDTO, Map<String, Object> paramMap) {
		// 初始化数据对象
		pageDTO.setPageData(new ArrayList<T>());

		// 设置查询条件
		paramMap.put("startRow", pageDTO.getLastIndex());
		paramMap.put("endRow", pageDTO.getPageSize());
	}

}
