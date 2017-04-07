<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>报名供应商列表</title>
	</head>
	<body>
		<form id="proForm" style="width:100%;height:100%" action="${CONTEXT}/active/saveProduct" method="post">

		<table id="ppdg" title=""></table>
		
		<div id="pptb" style="padding:10px;height:auto">
			<div>
				<label>供应商名称:</label><input type="text" id="supplierName" name="supplierName"/>
				<label>商品名称:</label><input type="text" id="prodName" name="prodName"/>
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="prodSearch">查询</a>
			</div>
			<div>
				<a class="easyui-linkbutton" iconCls="icon-save" id="btn-saveProd">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-save" id="btn-exportProduct">导出EXCEL</a>
			</div>
			
		</div>
		</form>
		<div style="display:none">
			<iframe id="if2" src=""></iframe>
		</div>
		<script type="text/javascript" src="${CONTEXT}js/active/promProduct.js?version=${version}"></script>
		<script type="text/javascript">

		</script>
	</body>
</html>