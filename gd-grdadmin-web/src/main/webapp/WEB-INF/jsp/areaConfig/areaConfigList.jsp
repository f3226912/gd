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
		<title>城市管理</title>
	</head>
<body>
	<table id="areaConfigdg" title="">
	</table>
	<div id="areaConfigtb" style="padding:5px;height:auto">
		<form id="areaConfigSearchForm" method="post">
		<div>
		    城市: 
			<input  type="text" id="queryName"  name="queryName"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
		   状态: 
		  <select id="queryType" name="queryType" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
          <option  value="0">启用 </option>
          <option  value="1">停用 </option>
		  </select>&nbsp;&nbsp;
		  
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#areaConfigSearchForm').form('validate');">查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<xlc:btn btncode='XZ001'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></xlc:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/areaConfig/areaConfig.js"></script>

</html>

