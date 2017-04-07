<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/head.inc"%>
<%@ include file="../pub/tags.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible"
	content="IE=7, IE=9, IE=10, IE=11, IE=12" />
<title>product</title>
<link href="${CONTEXT}css/uploader.css" media="screen" rel="stylesheet"
	type="text/css" />
</head>
<style type="text/css">
.csshiden {
 text-decoration:none;
	overflow: hidden; /*自动隐藏文字*/
	text-overflow: ellipsis; /*文字隐藏后添加省略号*/
	white-space: nowrap; /*强制不换行*/
	width: 20em; /*不允许出现半汉字截断*/
}
</style>
<body>
	<table id="complaintdg" title="">
	</table>
	<div id="friendslinkstb" style="padding: 5px; height: auto">
		<form id="complaintForm" method="post" action="">
			<div>
				创建时间: <input type="text" readonly="readonly" id="createTimeStart" name="createTimeStart"
					style="width: 150px"
					onFocus="WdatePicker({onpicked:function(){createTimeStart.focus();},maxDate:'#F{$dp.$D(\'createTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					onClick="WdatePicker({onpicked:function(){createTimeStart.focus();},maxDate:'#F{$dp.$D(\'createTimeEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
				~<input type="text" readonly="readonly" id="createTimeEnd" name="createTimeEnd"
					style="width: 150px"
					onFocus="WdatePicker({onpicked:function(){createTimeEnd.focus();},minDate:'#F{$dp.$D(\'createTimeStart\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					onClick="WdatePicker({onpicked:function(){createTimeEnd.focus();},minDate:'#F{$dp.$D(\'createTimeStart\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})">
				<br> 投诉建议内容: <input type="text" id="content" name="content"
					style="width: 150px">&nbsp;&nbsp; 信息来源 : <select
					name="source" style="width: 150px" id="source">
					<option value="">---请选择---</option>
					<option value="农商友">农商友</option>
					<option value="农批商">农批商</option>
				</select> <br>
				<gd:btn btncode='BTNSPGLQBCP01'>
					<a id="exportData" iconCls="icon-save" class="easyui-linkbutton"
						id="export">导出数据</a>
				</gd:btn>
				<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search"
					id="serachList">查询</a> <a class="easyui-linkbutton icon-reload-btn"
					iconCls="icon-reload" id="btn-reset">重置</a>
			</div>

			<div id="contentDialog" class="easyui-dialog"
				style="width: 600px; height: 500px;" closed="true" modal="true"
				buttons="#dlg-buttonsAdd">
				<div id="dlg-buttonsAdd" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel"
						onclick="javascript:$('#contentDialog').dialog('close')">关闭</a>
				</div>
			</div>

			<div id="eidtDialog" class="easyui-dialog"
				style="width: 600px; height: 500px;" closed="true" modal="true"
				buttons="#dlg-buttonsView">
				<div id="dlg-buttonsView" style="text-align: center">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						data-options="iconCls:'icon-save'" id="icon-save-btn"
						onclick="reply()">回复</a> <a href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-cancel"
						onclick="javascript:$('#eidtDialog').dialog('close')">关闭</a>
				</div>
			</div>

		<div id="showDialog" class="easyui-dialog"  closed="true" modal="true" buttons="#dlg-buttonsShow">
			<div id="dlg-buttonsShow" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#showDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>

<script type="text/javascript" src="${CONTEXT}js/jquery.cookie.js"></script>
<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
<script type="text/javascript"
	src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>

<script charset="utf-8"
	src="${CONTEXT}/js/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8"
	src="${CONTEXT}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script charset="utf-8"
	src="${CONTEXT}/js/kindeditor-4.1.10/plugins/code/prettify.js"></script>
<script type="text/javascript" src="${CONTEXT}js/formValidatorRegex.js"></script>
<script type="text/javascript" src="${CONTEXT}js/complaint/complaint.js"></script>
<script type="text/javascript">
	$("#serachList").click(function() {
		var params = {
			"createTimeStart" : $("#createTimeStart").val(),
			"createTimeEnd" : $("#createTimeEnd").val(),
			"content" : $("#content").val(),
			"source" : $("#source").val(),
		};
		loadComplaintList(params);
	});

	$('#btn-reset').click(function() {
		$('#complaintForm')[0].reset();
	});


	disableExport = false ;
	//刷新按钮
	$('#icon-refresh').click(function(){
		$("#source").val("");
		$("#content").val("");
		$("#createTimeStart").val("");
		$("#createTimeEnd").val("");
		disableExport = false ;
	});
	//导出数据
	$("#exportData").click(function() {

		var params = {
				"source" : $("#source").val(),
				"content" : $("#content").val(),
				"createTimeStart" : $("#createTimeStart").val(),
				"createTimeEnd" : $("#createTimeEnd").val()
			};

		var paramList = 'source=' + $("#source").val() + "&content=" + $("#content").val() + "&createTimeStart="
			+ $("#createTimeStart").val() + "&createTimeEnd=" + $("#createTimeEnd").val();
		$.ajax({
			url: CONTEXT+'complaint/checkExportParams',
			data : params,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'complaint/exportData',paramList,'post' );
					}else{
						slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
					}
				}else{
					warningMessage(data.message);
				}
			},
			error : function(data){
				warningMessage(data);
			}
		});

/* 				window.location.href = CONTEXT + "complaint/exportData?source="
						+ $("#source").val() + "&content="
						+ $("#content").val() + "&createTimeStart="
						+ $("#createTimeStart").val() + "&createTimeEnd="
						+ $("#createTimeEnd").val(); */
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
</script>
</html>

