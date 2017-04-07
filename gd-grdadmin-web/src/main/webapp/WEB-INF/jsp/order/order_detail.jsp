<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="bt-order">
	<form id="editForm" method="post" action="order/orderSaveEdit" onkeydown="if(event.keyCode==13){return false;}">
	<h2 class="bt-order-bigt">订单详情</h2>
	<div class="clearfix bt-order-ct">
		<input type="hidden" id="orderNotemp" name="orderNo" value="${dto.orderNo }" />
		<input type="hidden" id="orderStatustemp" value="${dto.orderStatus }" />
		<input type="hidden" id="payIdtemp" name="payId" value="${dto.paySerialnumberDTO.payId }" />
		<input type="hidden" id="typetemp" name="type" value="1" />
	</div>
<!-- 	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable1"> -->
<!-- 		<tr> -->
<%-- 			<td>付款方式：${dto.payType }</td> --%>
<!-- 			<td></td> -->
<%-- 			<td>订单金额：<fmt:formatNumber value="${dto.orderAmount}" pattern="0.00"/></td> --%>
			<!-- <td>抵扣金额：${dto.discountAmount }</td>
			<td>订单创建人账号：${dto.account }</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<%-- 			<td>卖家帐号：${dto.sellAccount }</td> --%>
<%-- 			<td>买家帐号：${dto.account }</td> --%>
<!-- 		</tr> -->
<!-- 		<tr> -->
<%-- 			<td>卖家商铺：${dto.shopName }</td> --%>
<%-- 			<td>买家姓名：${dto.realName }</td> --%>
<!-- 		</tr> -->
<!-- 	</table> -->
	<h2 class="bt-order-ct">订单信息</h2>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable3">
		<tr>
			<td width="13%">订单编号：</td>
			<td width="37%">${dto.orderNo }</td>
			<td>订单来源：</td>
			<td>${dto.orderSourceView }</td>
		</tr>
		<tr>
			<td>支付方式：</td>
			<td>${dto.payTypeView }</td>
			<td width="13%">订单状态：</td>
			<td>${dto.orderStatusView }</td>
		</tr>
		<tr>
			<td colspan="2"></td>
			<td>活动类型：</td>
			<td>${dto.isFirst==1 ? '首单' : '' }</td>
		</tr>
		<tr>
			<td>创建时间：</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.orderTime}" /></td>
			<td>成交时间：</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.payTime}" /></td>
		</tr>
		<tr>
			<td>关闭时间：</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.closeTime}" /></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td>POS终端号：</td>
			<td>${dto.paySerialnumberDTO.posNumber }</td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td>支付卡号：</td>
			<td>${dto.paySerialnumberDTO.paymentAcc }</td>
			<td>交易流水号：</td>
			<td>${dto.paySerialnumberDTO.statementId }</td>
		</tr>
	</table>
	<h2 class="bt-order-ct">买家信息</h2>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable3">
		<tr>
			<td width="13%">买家账号：</td>
			<td width="37%">${dto.account }</td>
			<td width="13%">买家姓名：</td>
			<td>
				<c:if test="${empty dto.realName }">
					农商友用户
				</c:if>
				${dto.realName }
			</td>
		</tr>
		<tr>
			<td width="13%">收货人：</td>
			<td width="37%">${dto.orderDelivery.linkman }</td>
			<td width="13%">收货地：</td>
			<td>
				${dto.orderDelivery.destnation }
			</td>
		</tr>
		<tr>
			<td width="13%">联系方式：</td>
			<td width="37%">${dto.orderDelivery.mobile }</td>
			<td width="13%">买家留言：</td>
			<td>
				${dto.message }
			</td>
		</tr>
	</table>
	<h2 class="bt-order-ct">卖家商铺</h2>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable3">
		<tr>
			<td width="13%">卖家账号：</td>
			<td width="37%">${dto.sellAccount }</td>
			<td width="13%">商铺名称：</td>
			<td>${dto.shopName }</td>
		</tr>
	</table>
	<h2 class="bt-order-ct">商品信息</h2>
	<div class="bt-order-list">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="comtable4">
			<tr>
				<th>序号</th>
				<th>商品名称</th>
				<th>商品分类</th>
				<th>采购量</th>
				<th>单价（元）</th>
				<!-- <th>交易价</th> -->
<!-- 				<th>买家补贴额</th> -->
<!-- 				<th>卖家补贴额</th> -->
				<!-- <th>补贴申请时间</th> -->
				<th>小计（元）</th>
			</tr>
			<c:forEach items="${dto.detailList}" var="detail" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${detail.productName}</td>
					<td>${detail.cateName}</td>
					<td><fmt:formatNumber value="${detail.purQuantity}" pattern="0.00"/>${detail.unitName}</td>
					<td><fmt:formatNumber value="${detail.price}" pattern="0.00"/>/${detail.unitName}</td>
					<!-- <td>${detail.tradingPrice}</td> -->
<%-- 					<td><fmt:formatNumber value="${detail.subAmount}" pattern="0.00"/></td> --%>
<%-- 					<td><fmt:formatNumber value="${detail.sellSubAmount}" pattern="0.00"/></td> --%>
					<!-- <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${detail.createTime}" /></td> -->
					<td><fmt:formatNumber value="${detail.needToPayAmount}" pattern="0.00"/></td>
				</tr>
			</c:forEach>
		</table>
		<div class="bt-order-tal">
<%-- 			<span class="pr40">买家合计补贴：<fmt:formatNumber value="${dto.subAmount}" pattern="0.00"/>元&nbsp;&nbsp;&nbsp;卖家合计补贴：<fmt:formatNumber value="${dto.sellSubAmount}" pattern="0.00"/>元&nbsp;&nbsp;&nbsp;抵扣金额：<fmt:formatNumber value="${dto.discountAmount}" pattern="0.00"/></span> --%>
			订单金额：<fmt:formatNumber value="${dto.orderAmount}" pattern="0.00"/>元
		</div>
		<div class="bt-order-tal">
			实付款：<fmt:formatNumber value="${dto.payAmount}" pattern="0.00"/>元
		</div>
<%-- 		<c:if test="${dto.orderStatus == '已付款' && dto.examineStatus == '待审核'}"> --%>
<!-- 			<h3 class="bh-tit">驳回原因：</h3> -->
<!-- 			<p class="bh-reason"> -->
<!-- 				<label class="leb-txt2"> -->
<!-- 					<input class="bh-reason-chk"value="流水与订单金额不符" type="checkbox" name="description">流水与订单金额不符 -->
<!-- 					<input class="bh-reason-chk"value="照片不符" type="checkbox" name="description">照片不符 -->
<!-- 				</label> -->
<!-- 			</p> -->
<%-- 		</c:if> --%>
	</div>
<!-- 		<h2 class="bt-order-ct">订单付款凭证相片</h2> -->
<%-- 		<c:if test="${not empty dto.paySerialnumberDTO}"> --%>
<!-- 			<div class="pz-img clearfix"> -->
<!-- 		    	<span class="pz-img-t"> -->
<!-- 		    		银行流水号： -->
<!-- 		    	</span> -->
<!-- 		    	<div class="pz-img-item clearfix"> -->
<%-- 		    		<c:set value="${fn:split(dto.paySerialnumberDTO.payImage,',')}" var="payImage" /> --%>
<%-- 			    	<c:forEach items="${payImage}" var="pi" varStatus="status"> --%>
<!-- 			    		<div class="pz-img-box"> -->
<%-- 				     		<a href="${imgHostUrl}${pi}" target="_blank"><img src="${imgHostUrl}${pi}" width="182" height="110"></a> --%>
<!-- 				     		<br/> -->
<%-- 				     		<c:choose> --%>
<%-- 								<c:when test="${(dto.orderStatus == '已付款' && dto.examineStatus == '待审核') || '1' == dto.upPayFlag}"> --%>
<%-- 									<input type="text" name="statementId" class="input-text input-order easyui-validatebox" value="${fn:split(dto.paySerialnumberDTO.statementId,',')[status.index]}" validType="numbertwo" required="true" maxlength="12" missingMessage="银行流水号应为12位数字"> --%>
<%-- 								</c:when> --%>
<%-- 								<c:otherwise> --%>
<%-- 									<input type="text" name="statementId" readonly="readonly" value="${fn:split(dto.paySerialnumberDTO.statementId,',')[status.index]}" class="input-text input-order"> --%>
<%-- 								</c:otherwise> --%>
<%-- 							</c:choose> --%>
<!-- 			    		</div> -->
<%-- 			    	</c:forEach> --%>
<!-- 		    	</div> -->
<!-- 		    </div> -->
<%-- 	    </c:if> --%>
    </form>
<!--     <h2 class="bt-order-ct">日志：</h2> -->
<%-- 	<c:forEach items="${dto.auditLogList}" var="auditLog" varStatus="status"> --%>
<!-- 		<div class="bt-notice clearfix bt-notice-wrong"> -->
<%-- 			<span>${auditLog.description}&nbsp;</span>  --%>
<%-- 			<span>${auditLog.auditUserName}&nbsp;</span>  --%>
<%-- 			<span><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${auditLog.auditTime}" />&nbsp;</span> --%>
<!-- 		</div> -->
<%-- 	</c:forEach> --%>
</div>
<script type="text/javascript" >
var orderStatustemp = '${dto.orderStatus}';
var examineStatustemp = '${dto.examineStatus}';
// var upPayFlagtemp = '${dto.upPayFlag}';
$(document).ready(function(){
    if("已付款"==orderStatustemp && "待审核" == examineStatustemp){
    	$("#editButtondiv").show();
    }else{
    	$("#editButtondiv").hide();
    }
//     if("1"==upPayFlagtemp){
//     	$("#updateButtondiv").show();
//     }else{
//     	$("#updateButtondiv").hide();
//     }
});
</script>