<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
	<input type="hidden" name="cashRequestId" id="cashRequestId" value="${cashRequestId}">
	<div align="center" style="margin-top: 100px;">
			银行流水号:&nbsp;&nbsp;<input type="text" name="accountflowId" value="" id="accountflowId" maxlength="12" placeholder="请输入12位的银行流水号"><br>
			<br> <gd:btn btncode='BTNTXGLTXSQGL05'><input type="button" value="已提款" style="margin-left: 80px"
				onclick="confirmPay()"> </gd:btn>
				<input type="button" value="返回" style="margin-left: 60px" onclick="comeBack()">
	</div>
<script type="text/javascript" src="${CONTEXT}js/cash/pay.js"></script>	