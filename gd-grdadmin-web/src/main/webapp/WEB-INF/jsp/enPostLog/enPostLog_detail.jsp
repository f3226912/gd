<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="bt-order">
	<h2 class="bt-order-bigt">日志详情</h2>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		<tr style="width:500px; height:30px;">
			<td style="width:100px; " >主键：</td>
			<td >${enPostLogEntity.ID}</td>
		</tr>
		<tr>
			<td>接口版本号：</td>
			<td>${enPostLogEntity.version}</td>
		</tr>
		<tr>
			<td>字符集：</td>
			<td>${enPostLogEntity.charset}</td>
		</tr>
		<tr>
			<td>POS终端号：</td>
			<td>${enPostLogEntity.machinenum}</td>
		</tr>
		<tr>
			<td>POS商户号：</td>
			<td>${enPostLogEntity.merchantnum}</td>
		</tr>
		<tr>
			<td>状态：</td>
			<td>  <c:if test="${enPostLogEntity.state==1}">请求原文</c:if>
			 <c:if test="${enPostLogEntity.state==2}">请求成功</c:if>
			  <c:if test="${enPostLogEntity.state==3}">请求失败(无对应数据)</c:if>
			</td>
		</tr>
		<tr>
			<td>交易类型：</td>
			<td>
			<c:if test="${enPostLogEntity.transype==0}">订单支付</c:if>
			 <c:if test="${enPostLogEntity.transype==1}">刷卡消费</c:if>
			</td>
		</tr>
		<tr>
			<td>谷登订单号：</td>
			<td>${enPostLogEntity.orderno}</td>
		</tr>
		<tr>
			<td>订单金额：</td>
			<td>${enPostLogEntity.orderfee}</td>
		</tr>
		<tr>
			<td>手续费：</td>
			<td>${enPostLogEntity.ratefee}</td>
		</tr>
		<tr>
			<td>支付金额：</td>
			<td>${enPostLogEntity.payfee}</td>
		</tr>
		<tr>
			<td>支付响应码：</td>
			<td>${enPostLogEntity.payresultcode}</td>
		</tr>
		<tr>
			<td>支付响应码说明：</td>
			<td>${enPostLogEntity.payresultmsg}</td>
		</tr>
		<tr>
			<td>支付卡号：</td>
			<td>${enPostLogEntity.paycardno}</td>
		</tr>
		<tr>
			<td>交易日期：</td>
			<td><fmt:formatDate value="${enPostLogEntity.transdate}" pattern="yyyy-MM-dd"/></td>
		</tr>
		<tr>
			<td>交易时间：</td>
			<td><fmt:formatDate value="${enPostLogEntity.transtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>交易流水：</td>
			<td>${enPostLogEntity.transseqno}</td>
		</tr>
		<tr>
			<td>保留字段：</td>
			<td>${enPostLogEntity.reserved}</td>
		</tr>
		<tr>
			<td>原数据：</td>
			<td> <textarea rows="3" cols="20"  style="width:600px;">
${enPostLogEntity.bz}
</textarea></td>
		</tr>
		<tr>
			<td>生成时间：</td>
			<td><fmt:formatDate value="${enPostLogEntity.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>



	</table>

</div>
