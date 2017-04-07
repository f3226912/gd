package com.gudeng.commerce.gd.home.util;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.ProductQueryBean;
import com.gudeng.framework.core2.GdLogger;

/**
 * @Description 搜索服务切面，搜集用户的搜索行为信息,并将数据发送到消息系统
 * @Project gd-home-web
 * @ClassName SearchAopUtil.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月21日 下午4:24:33
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
//@Component
//@Aspect
public class SearchAopUtil {

	/**
	 * @Description  单个商品搜索用户行为信息
	 * @CreationDate 2015年12月21日 下午3:55:39
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Pointcut("execution(* com.gudeng.commerce.gd.home.controller.search.ProductSearchController.getProduct(..))")
	private void getProduct() {
	}

	/**
	 * @Description 商品列表搜索用户行为信息
	 * @CreationDate 2015年12月21日 上午10:13:13
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Pointcut("execution(* com.gudeng.commerce.gd.home.controller.search.ProductSearchController.getProductList(..))")
	private void getProductList() {
	}

	/**
	 * @Description  我要进货中商品列表搜索用户行为信息
	 * @CreationDate 2015年12月21日 下午4:01:57
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Pointcut("execution(* com.gudeng.commerce.gd.home.controller.search.ProductSearchController.getProductPurchaseList(..))")
	private void getProductPurchaseList() {
	}

	/**
	 * @Description 店铺列表搜索用户行为信息
	 * @CreationDate 2015年12月21日 上午10:13:22
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Pointcut("execution(* com.gudeng.commerce.gd.home.controller.search.BusinessSearchController.getBusinessList(..))")
	private void getBusinessList() {
	}

	/**
	 * @Description  用户进入商铺行为信息
	 * @CreationDate 2015年12月21日 下午4:33:53
	 * @Author lidong(dli@gdeng.cn)
	 */
	@Pointcut("execution(* com.gudeng.commerce.gd.home.controller.StoreController.storeIndex(..))")
	private void storeIndex() {
	}

	/**
	 * @Description  将用户查看商品完毕后的行为数据发送到消息系统
	 * @param joinPoint
	 * @CreationDate 2015年12月21日 下午4:01:27
	 * @Author lidong(dli@gdeng.cn)
	 */
	@After("getProduct()")
	public void sendMessageByGetProduct(JoinPoint joinPoint) {
		System.err.println("单个产品搜索========================");
		HttpServletRequest request = null;
		MemberBaseinfoEntity userInfo = null;
		String productId = null;
		String ip = null;
		try {
			Object[] objects = joinPoint.getArgs();
			if (objects.length > 0) {
				request = (HttpServletRequest) objects[0];
				userInfo = LoginUserUtil.getSysRegisterUser(request);
				ip = LoginUserUtil.getIpAddr(request);
				productId = (String) objects[1];
			}
		} catch (Exception e) {

		}
		System.err.println(ip);
	}

	/**
	 * @Description 将用户产品列表搜索完毕后的行为数据发送到消息系统
	 * @param joinPoint
	 * @CreationDate 2015年12月21日 上午10:08:46
	 * @Author lidong(dli@gdeng.cn)
	 */
	@After("getProductList()")
	public void sendMessageByGetProductList(JoinPoint joinPoint) {
		System.err.println("产品列表搜索========================");
		HttpServletRequest request = null;
		MemberBaseinfoEntity userInfo = null;
		ProductQueryBean productQueryBean = null;
		String ip = null;
		try {
			Object[] objects = joinPoint.getArgs();
			if (objects.length > 0) {
				request = (HttpServletRequest) objects[0];
				userInfo = LoginUserUtil.getSysRegisterUser(request);
				ip = LoginUserUtil.getIpAddr(request);
				productQueryBean = (ProductQueryBean) objects[1];
			}
		} catch (Exception e) {

		}
		System.err.println(ip);
	}

	/**
	 * @Description  将用户在我要进货中产品列表搜索完毕后的行为数据发送到消息系统
	 * @param joinPoint
	 * @CreationDate 2015年12月21日 下午4:02:40
	 * @Author lidong(dli@gdeng.cn)
	 */
	@After("getProductPurchaseList()")
	public void sendMessageByGetProductPurchaseList(JoinPoint joinPoint) {
		System.err.println("我要进货产品列表搜索========================");
		HttpServletRequest request = null;
		MemberBaseinfoEntity userInfo = null;
		ProductQueryBean productQueryBean = null;
		String ip = null;
		try {
			Object[] objects = joinPoint.getArgs();
			if (objects.length > 0) {
				request = (HttpServletRequest) objects[0];
				userInfo = LoginUserUtil.getSysRegisterUser(request);
				ip = LoginUserUtil.getIpAddr(request);
				productQueryBean = (ProductQueryBean) objects[1];
			}
		} catch (Exception e) {

		}
		System.err.println(ip);
	}

	/**
	 * @Description  将用户商铺列表搜索完毕后的行为数据发送到消息系统
	 * @param joinPoint
	 * @CreationDate 2015年12月21日 下午4:00:54
	 * @Author lidong(dli@gdeng.cn)
	 */
	@After("getBusinessList()")
	public void sendMessageByGetBusinessList(JoinPoint joinPoint) {
		System.err.println("商铺列表搜索========================");
		HttpServletRequest request = null;
		MemberBaseinfoEntity userInfo = null;
		BusinessQueryBean businessQueryBean = null;
		String ip = null;
		try {
			Object[] objects = joinPoint.getArgs();
			if (objects.length > 0) {
				request = (HttpServletRequest) objects[0];
				userInfo = LoginUserUtil.getSysRegisterUser(request);
				ip = LoginUserUtil.getIpAddr(request);
				businessQueryBean = (BusinessQueryBean) objects[1];
			}
		} catch (Exception e) {

		}
		System.err.println(ip);
	}

	/**
	 * @Description  进入商铺
	 * @param joinPoint
	 * @CreationDate 2015年12月21日 下午4:37:50
	 * @Author lidong(dli@gdeng.cn)
	 */
	@After("storeIndex()")
	public void sendMessageByStoreIndex(JoinPoint joinPoint) {
		System.err.println("进入商铺========================");
		HttpServletRequest request = null;
		MemberBaseinfoEntity userInfo = null;
		String bid = null;
		String ip = null;
		try {
			Object[] objects = joinPoint.getArgs();
			if (objects.length > 0) {
				request = (HttpServletRequest) objects[0];
				userInfo = LoginUserUtil.getSysRegisterUser(request);
				ip = LoginUserUtil.getIpAddr(request);
				bid = (String) objects[1];
			}
		} catch (Exception e) {

		}
		System.err.println(ip);
	}
}
