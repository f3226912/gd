<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>街市管理</title>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/easyui.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/easyui_${sysType}.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/global.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/icon.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/xlcm.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/xlcn.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/uploader.css"/>
		<script type="text/javascript" src="${CONTEXT}js/jquery.min.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/jquery.form.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/common.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/jquery.artDialog.js?skin=blue"  charset='utf-8'></script>
		<script type="text/javascript" src="${CONTEXT}js/iframeTools.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/jquery.validatebox.fixed.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/jquery.validate.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/moment.js"></script>
		<script type="text/javascript">
		var CONTEXT = "${CONTEXT}";
		var imgUrlHost = "${imgUrlHost}";
		</script>

	</head>
<body>
	<table id="marketdg" title="">
	</table>
	<div id="markettb" style="padding:5px;height:auto">
		<form id="marketSearchForm" method="post">
		<div>
		     街市名称: 
			<input  type="text" id="queryName"  name="queryName"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
		     类型: 	
		  <select id="queryType" name="queryType" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
          <option  value="0">产地供应商 </option>
          <option  value="1">街市 </option>
          <option  value="2">市场</option>
          <option  value="3">用户自定义添加</option>
		  </select>&nbsp;&nbsp;
			<gd:btn btncode='BTNJCXXJSGL04'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#marketSearchForm').form('validate');">查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNJCXXJSGL01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
			<gd:btn btncode='BTNJCXXJSGL03'><a class="easyui-linkbutton icon-edit-btn" iconCls="icon-edit" id="icon-edit" >编辑</a></gd:btn>
			<gd:btn btncode='BTNJCXXJSGL02'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
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
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNJCXXJSGL02'><a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a></gd:btn>"+
	   "<gd:btn btncode='BTNJCXXJSGL06'><a class='operate' href='javascript:;' onclick=locObj('"+row.id+"');>定位</a></gd:btn>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/market/market.js"></script>
<script type="text/javascript" src="${CONTEXT}js/market/area.js"></script>
</html>

