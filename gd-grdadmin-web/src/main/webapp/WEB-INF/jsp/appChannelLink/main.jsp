<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<title>AppChannelLink-Main-代码生成器出品，请酌情修改 by@lidong</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="datagrid-table" title=""></table>
<div id="datagrid-tool-bar" style="padding:5px;height:auto">
	<form id="datagrid-form" method="post">
	<div>
		渠道名称：
		<input type="text" id="channelName" name="channelName" placeholder="渠道名称" class="easyui-validatebox" style="width:150px" >
		<gd:btn btncode='BTNAPPQDLJGL03'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a></gd:btn>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
	</div>
	</form>
	<div style="margin-bottom:5px">
		<gd:btn btncode='BTNAPPQDLJGL02'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="btn-add" >新增</a></gd:btn>
<!-- 		<a class="easyui-linkbutton icon-add-btn" iconCls="icon-edit" id="btn-edit" >编辑</a> -->
<!-- 		<a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="btn-remove">删除</a> -->
		<gd:btn btncode='BTNAPPQDLJGL01'><a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a></gd:btn>
		<gd:btn btncode='BTNAPPQDLJGL04'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
	</div>
</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	var html="";
// 	html+="<a class='operate' href='javascript:;' onclick=view('"+row.id+"');>查看</a>";
	html+="<gd:btn btncode='BTNAPPQDLJGL05'><a class='operate' href='javascript:;' onclick=edit('"+row.id+"');>修改</a></gd:btn>&nbsp;";
	html+="<gd:btn btncode='BTNAPPQDLJGL06'><a class='operate' href='javascript:;' onclick=deleteObj('"+row.id+"');>删除</a></gd:btn>";
	return html;
}
var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var channelName = $('#datagrid-form #channelName').val();

	var params = {
		"channelName" : channelName
	};
	var paramList = gudeng.commons.convertParamsToDelimitedList(params);
	$.ajax({
		url: CONTEXT+'appChannelLink/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				/* $("#Loading2").show(); */
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'appChannelLink/exportData',paramList,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage("error : " + data);
		}
	});
});
</script>
<script type="text/javascript" src="${CONTEXT}js/appChannelLink/main.js"></script>
</html>