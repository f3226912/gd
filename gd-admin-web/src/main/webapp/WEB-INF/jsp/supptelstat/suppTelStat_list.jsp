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
		<title>供应商电话数据统计</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="supptelstatdg" title="">
	</table>
	<div id="supptelstattb" style="padding:5px;height:auto">
		<form id="suppTelStatForm" method="post">
		<div>
			日期：
			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px"> 
				主叫号码: 
			<input  type="text" id="e_Mobile"    class="easyui-validatebox"    name="e_Mobile" style="width:150px" >
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-search" id="icon-search"  OnClick ="return $('#suppTelStatForm').form('validate');">查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="mlogiin-bottom:5px">
 			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
 			<a class="easyui-linkbutton" iconCls="icon-save" id="exportData">导出数据</a>
		</div>
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/supptelstat/main.js"></script>

<script type="text/javascript">
$("#exportData").click(function(){
	/* var queryParams = $('#supptelstatdg').datagrid('options').queryParams;
	queryParams.e_Mobile = $('#suppTelStatForm #e_Mobile').val();
	window.location.href=CONTEXT+'supptelstat/exportData?e_Mobile='+queryParams.e_Mobile; */
	var queryParams = {
		"e_Mobile":$('#suppTelStatForm #e_Mobile').val(),
		"startDate":$('#suppTelStatForm #startDate').val(),
		"endDate":$('#suppTelStatForm #endDate').val()
	};
	var paramList = "e_Mobile="+queryParams.e_Mobile+
					"&startDate="+queryParams.startDate+
					"&endDate="+queryParams.endDate;
	$.ajax({
		url: CONTEXT+'supptelstat/checkExportParams',
		data : queryParams,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				//启动下载
				$.download(CONTEXT+'supptelstat/exportData',paramList,'post' );
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage(data);
		}
	});
});
jQuery.download = function(url, data, method){
	// 获得url和data
    if( url && data ){
        // data 是 string或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
        	.appendTo('body').submit().remove();
    };
};
$('#btn-reset').click(function(){
	$('#suppTelStatForm')[0].reset();
});
//刷新按钮
$('#icon-refresh').click(function(){
	$('#suppTelStatForm')[0].reset();
	$("#supptelstatdg").datagrid('load',{});
});
</script>
</html>

