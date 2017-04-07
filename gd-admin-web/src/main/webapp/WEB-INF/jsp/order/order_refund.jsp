<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<body>
	<br />
	<form id="refund-form" method="post" action="order/orderRefund">
		<input type="hidden" name="statementId" value="${statementId}" /> <input
			type="hidden" name="orderNo" value="${penalty.orderNo}" />
		<table>
			<tr>
				<td style="text-align: right">预付款：</td>
				<td><span>${penalty.prepayAmt}</span></td>
				<input type="hidden" name="prepayAmt" id="prepayAmt"
					value="${penalty.prepayAmt }">
			</tr>
			<tr>
				<td style="text-align: right">退款理由：</td>
				<td><input type="text" name="refundReason" id="refundReason"
					missingMessage="退款理由不能为空" class="easyui-validatebox"
					data-options="required:true" style="width: 175px" maxlength="300" /></td>

			</tr>
			<tr>
				<td style="text-align: right">违约金（卖家）：</td>
				<td><input type="text" name="buyerPenaltyToSeller"
					class="easyui-numberbox" data-options="min:0,precision:2"
					id="buyerPenaltyToSeller" value="${penalty.sellerPenalty }"
					style="width: 180px" /></td>

			</tr>
			<tr>
				<td style="text-align: right">违约金（物流公司）：</td>
				<td><input type="text" name="buyerPenaltyToWLCompany"
					class="easyui-numberbox" data-options="min:0,precision:2"
					id="buyerPenaltyToWLCompany" value="${penalty.companyPenalty }"
					style="width: 180px" /></td>

			</tr>
			<tr>
				<td style="text-align: right">平台自留：</td>
				<td><input type="text" class="easyui-numberbox"
					name="buyerPenaltyToPlatform" data-options="min:0,precision:2"
					id="buyerPenaltyToPlatform" value="${penalty.platPenalty }"
					style="width: 180px" /></td>

			</tr>
			<tr>
				<td style="text-align: right">退给买家：</td>
				<td><span id="spBuyerPrepayRefund"></span> <input type="hidden"
					name="buyerPrepayRefund" id="buyerPrepayRefund" value="" /></td>

			</tr>
		</table>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<lable id="lbMsg"></lable>
	</form>
	<script type="text/javascript" charset="utf-8">
		$(function() {
			reckon();
			$(":text").keyup(function() {
				reckon();
			})
		})

		function reckon() {
			var prepayAmt = $("#prepayAmt").val();
			var ret=prepayAmt;
			if (!ret)
				return false;
			var sellerPenalty = $("#buyerPenaltyToSeller");
			var companyPenalty = $("#buyerPenaltyToWLCompany");
			var platPenalty = $("#buyerPenaltyToPlatform");

			validText(sellerPenalty,prepayAmt);
			ret = sub(ret, sellerPenalty.val());
			validText(companyPenalty,prepayAmt);
			ret = sub(ret, companyPenalty.val());
			validText(platPenalty,prepayAmt);
			ret = sub(ret, platPenalty.val());
			
			$("#spBuyerPrepayRefund").html(ret);
			$("#buyerPrepayRefund").val(ret);
		}

		function sub(a, b) {
			return (parseFloat(a) - parseFloat(b)).toFixed(2);
		}
		
		function validText(obj,prepayAmt){
			var lsValid = parseFloat(obj.val()) >= 0 ? true : false;
			if(!lsValid){
				obj.val("0.00");
			}else{
				if(parseFloat(obj.val())>parseFloat(prepayAmt)){
					obj.val(prepayAmt);
				}
			}
		}
	</script>
</body>
