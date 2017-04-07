<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="bt-order">
	<h2 class="bt-order-bigt">贷款信息详情</h2>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr style="width:500px; height:30px;display:none;">
			<td style="width:100px; " >主键：</td>
			<td >    ${financeCreditEntity.id}</td>
		</tr>
		<tr style="width:500px; height:30px;">
			<td style="width:100px; " >用户帐号：</td>
			<td >    ${financeCreditEntity.memberAccount}</td>
		</tr>
	<tr style="width:500px; height:30px;">
			<td style="width:100px; " >用户姓名：</td>
			<td >  ${financeCreditEntity.realName}</td>
		</tr>
		<tr style="width:500px; height:30px;">
			<td style="width:100px; " >实体商铺名称</td>
			<td > ${financeCreditEntity.shopName}</td>
		</tr>
		<tr style="width:500px; height:30px;">
			<td style="width:100px; " >联系电话：</td>
			<td > ${financeCreditEntity.mobile}</td>
		</tr>
	   <tr style="width:500px; height:30px;">
			<td style="width:100px; " >主营分类:</td>
			<td > ${financeCreditEntity.cateName}</td>
		</tr>
		<tr style="width:500px; height:30px;">
			<td style="width:100px; " >所在市场：</td>
			<td > ${financeCreditEntity.marketName}</td>
		</tr>
		<tr style="width:500px; height:30px;">
			<td style="width:100px; " >申请时间：</td>
			<td > ${CreateTime}</td>
		</tr>
		<tr style="width:500px; height:30px;">
			<td style="width:100px; " >账号状态:</td>
			<td > 
			<c:if test="${financeCreditEntity.status=='0'}">未启用</c:if>
			<c:if test="${financeCreditEntity.status=='1'}">启用</c:if>	
			
			</td>
		</tr>
	    <tr style="width:500px; height:30px;">
			<td style="width:100px; " >贷款额度:</td>
			<td>
			     <c:if test="${financeCreditEntity.creditQuotaRange  ==  '1'}">1——5万</c:if>
				<c:if test="${financeCreditEntity.creditQuotaRange  == '2'}">10——50万 </c:if>
				<c:if test="${financeCreditEntity.creditQuotaRange  ==  '3'}">50——100万</c:if>
				<c:if test="${financeCreditEntity.creditQuotaRange  ==  '4'}">100——300万</c:if>
			</td>
			
		</tr>
	 <tr style="width:500px; height:30px;">
			<td style="width:100px; " >用户星级:</td>
			<td > 
			<c:if test="${financeCreditEntity.userStar==1}">一星</c:if> 
			<c:if test="${financeCreditEntity.userStar==2}">二星</c:if> 
			<c:if test="${financeCreditEntity.userStar==3}">三星</c:if> 
			<c:if test="${financeCreditEntity.userStar==4}">四星</c:if> 
			<c:if test="${financeCreditEntity.userStar==5}">五星</c:if> 
			</td>
		</tr>
	<tr style="width:500px; height:30px;">
			<td style="width:100px; " >市场商铺地址:</td>
			<td > ${financeCreditEntity.address} </td>
		</tr>
	 
	
	
	</table>

</div>
