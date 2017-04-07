<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
 	<table id="giftdg" title="">
	</table>
	<input type="hidden" name="memberId" id="memberId" value="${memberId }">
	<input type="hidden" name="integral" id="integral" value="${integral }">
	<div id="gifttb" style="padding:5px;height:auto">
		<form id="giftSearchForm" method="post">
			礼物名字: <input  type="text" id="giftName"    class="easyui-validatebox"  name="giftName" style="width:150px" >
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="gift-search" OnClick ="return $('#giftSearchForm').form('validate');">查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="gift-reset">重置</a>
		</form>
	</div>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/conversion/giftlist.js"></script>

