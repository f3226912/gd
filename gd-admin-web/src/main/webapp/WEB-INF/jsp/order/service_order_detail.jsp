<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="bt-order">
	<form id="editForm" method="post" action="order/orderSaveEdit"
		onkeydown="if(event.keyCode==13){return false;}">
		<div id="tt" class="easyui-tabs">
		<!-- <h2 class="bt-order-bigt">订单详情</h2>  -->
		<div title="订单详情">
		<div class="clearfix bt-order-ct">
			<input type="hidden" id="orderNotemp" name="orderNo"
				value="${dto.orderNo }" /> <input type="hidden"
				id="orderStatustemp" value="${dto.orderStatus }" /> <input
				type="hidden" id="payIdtemp" name="payId"
				value="${dto.paySerialnumberDTO.payId }" /> <input type="hidden"
				id="typetemp" name="type" value="1" />
		</div>
		<h2 class="bt-order-ct">订单信息</h2>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="comtable3">
			<tr>
				<td width="13%">订单编号：</td>
				<td width="37%">${dto.orderNo }</td>
				<%-- <td>订单来源：</td>
			<td>${dto.orderSourceView }</td> --%>
				<td>订单状态：</td>
				<td>${dto.orderStatusView }</td>
			</tr>
			<tr>
				<td>创建时间：</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
						value="${dto.orderTime}" /></td>
				<td>成交时间：</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
						value="${dto.payTime}" /></td>
			</tr>
			<tr>
				<td>关闭时间：</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
						value="${dto.closeTime}" /></td>
				<td colspan="2"></td>
			</tr>
		</table>
		<h2 class="bt-order-ct">买家信息</h2>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="comtable3">
			<tr>
				<td width="13%">买家账号：</td>
				<td width="37%">${dto.account }</td>
				<td width="13%">手机号码：</td>
				<td>${dto.buyerMobile }</td>
			</tr>
			<tr>
				<td width="13%">买家姓名：</td>
				<td>${dto.realName }</td>
				<td colspan="2"></td>
			</tr>
		</table>
		<h2 class="bt-order-ct">商品信息</h2>
		<div class="bt-order-list">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="comtable4">
				<tr>
					<th>序号</th>
					<th>服务名称</th>
					<th>采购量</th>
					<th>单价（元）</th>
					<th>小计（元）</th>
				</tr>
				<c:forEach items="${dto.detailList}" var="detail" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${detail.productName}</td>
						<td><fmt:formatNumber value="${detail.purQuantity}" maxFractionDigits="0" /></td>
						<td><fmt:formatNumber value="${detail.price}" pattern="0.00" /></td>
						<td><fmt:formatNumber value="${detail.needToPayAmount}" pattern="0.00" /></td>
					</tr>
				</c:forEach>
			</table>
			<div class="bt-order-tal">
				商品总价：
				<fmt:formatNumber value="${dto.orderAmount}" pattern="0.00" />
				元
			</div>
			<div class="bt-order-tal">
				实付款：
				<fmt:formatNumber value="${dto.payAmount}" pattern="0.00" />
				元
			</div>
		</div>
		</div>
		<c:if test="${dto.orderStatus == '3'}">
		<div title="支付详情">
		<div class="clearfix bt-order-ct">
			<input type="hidden" id="orderNotemp" name="orderNo"
				value="${dto.orderNo }" /> <input type="hidden"
				id="orderStatustemp" value="${dto.orderStatus }" /> <input
				type="hidden" id="payIdtemp" name="payId"
				value="${dto.paySerialnumberDTO.payId }" /> <input type="hidden"
				id="typetemp" name="type" value="1" />
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="comtable3">
			<tr>
				<td width="18%">商品总价：</td>
				<td width="37%"><fmt:formatNumber value="${dto.orderAmount}" pattern="0.00" /></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>支付状态：</td>
				<td>
				<c:if test="${empty dto.paySerialnumberDTO.payId }">未支付</c:if>
				<c:if test="${!empty dto.paySerialnumberDTO.payId }">已支付</c:if>
				</td>
				<td>支付方式：</td>
				<td>${dto.payTypeView }</td>
			</tr>
			<tr>
				<td>平台支付流水号：</td>
				<td>${dto.paySerialnumberDTO.statementId }</td>
				<td>第三方支付流水号：</td>
				<td>${dto.paySerialnumberDTO.thirdStatementId }</td>
			</tr>
			<tr>
				<td>付款人账号：</td>
				<td>${dto.paySerialnumberDTO.paymentAcc }</td>
				<td>收款人账号：</td>
				<td>${dto.paySerialnumberDTO.recipientAcc }
				</td>
			</tr>
			<tr>
				<td>支付时间：</td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
						value="${dto.paySerialnumberDTO.payTime}" /></td>
				<td>发票号码：</td>
				<td>${dto.invoiceInfoDTO.content }</td>
			</tr>
		</table>
		</div>
		</c:if>
		</div>
	</form>

</div>
<script type="text/javascript">
	var orderStatustemp = '${dto.orderStatus}';
	var examineStatustemp = '${dto.examineStatus}';
	// var upPayFlagtemp = '${dto.upPayFlag}';
	$(document).ready(function() {
		if ("已付款" == orderStatustemp && "待审核" == examineStatustemp) {
			$("#editButtondiv").show();
		} else {
			$("#editButtondiv").hide();
		}
		//     if("1"==upPayFlagtemp){
		//     	$("#updateButtondiv").show();
		//     }else{
		//     	$("#updateButtondiv").hide();
		//     }
	});
</script>