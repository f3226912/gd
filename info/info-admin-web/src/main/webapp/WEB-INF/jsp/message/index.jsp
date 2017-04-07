<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>系统消息管理主页</title>
	</head>
		<%@ include file="../pub/head.inc" %>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="pricesDatagrid" title="市场价格">
	</table>
	<div id="pricetb" style="padding:5px;height:auto">
		<form id="pricesSearchForm" method="post">
		<div>
			消息名称：
			<input type="text" id="title" placeholder="输入文本" class="easyui-validatebox" name="title" style="width:150px" >&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#pricesSearchForm').form('validate');" >查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:600px;height:320px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()" style="display: none;">发送消息</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>

<script type="text/javascript">
function optformat(value,row,index){
	var html="";
	html+="<a class='operate' href='javascript:;' onclick=detail('"+row.id+"');>详情</a>";
	return html;
}

</script>
<script type="text/javascript" src="${CONTEXT}js/jquery.form.js"></script>
<script type="text/javascript" src="${CONTEXT}js/message/index.js"></script>
</html>

