<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible"
	content="IE=7, IE=9, IE=10, IE=11, IE=12" />
<title>礼品仓库管理</title>
</head>
<script type="text/javascript"
	src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="datagrid-table" title=""></table>
	<div id="datagrid-tool-bar"
		style="height: auto; padding: 0px !important; margin: 0px;">
		<form id="datagrid-form" method="post"
			style="padding: 5px 0px 5px 20px;">
			<div>
				所属市场： <select name="marketId" id="marketId"
					style="width: 150px; height: 20px; margin-right: 10px;">
					<option value="" selected="selected">请选择</option>
					<c:forEach items="${requestScope.marketDTOs }" var="market">
						<option value="${market.id }">${market.marketName }</option>
					</c:forEach>
				</select> 
				</select> 仓库名称： <input type="text" id="name" maxlength="30"
					class="easyui-validatebox" name="name"
					style="width: 150px; margin-right: 10px;" placeholder="请输入仓库名称" />&nbsp;&nbsp;
				<gd:btn btncode='BTNDTLPCKGL04'>
					<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search"
						id="btn-search">查询</a>
				</gd:btn>
				<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload"
					id="btn-reset">重置</a>
			</div>
		</form>

		<div style="background: #efefef; padding: 5px; height: 25px;">
			<div style="float: left; font-size: 14px; margin-left: 5px;">礼品仓库列表</div>
			<div style="float: right; margin-right: 57px;">
				<gd:btn btncode='BTNDTLPCKGL01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add"
					id="btn-add">新增</a></gd:btn>
					<gd:btn btncode='BTNDTLPCKGL02'>
					 <a class="easyui-linkbutton icon-remove-btn"
					iconCls="icon-remove" id="btn-remove">删除</a></gd:btn>
			</div>
		</div>
		
	</div>
	
</body>

<script type="text/javascript" src="${CONTEXT}js/grdGdGiftstore/list.js"></script>
</html>