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
		<title>线下推广统计</title>
	</head>
		<%@ include file="../pub/head.inc" %>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="pushOfflineDatagrid" title="线下推广统计">
	</table>
	<div  id="toolbar" style="padding:5px;height:auto">
		<form id="pushOfflineSearchForm" method="post">
		<div>
			推广时间：
			<input  type="text"  id="startDate" name="startDate" placeholder="开始时间"
			class="easyui-validatebox easyui-datetimebox" editable="false" style="width:150px" >
			至
			<input  type="text"    id="endDate" name="endDate" placeholder="结束时间"
			class="easyui-validatebox easyui-datetimebox" editable="false" style="width:150px">
			<br>
			所属行业：
			<select id="industry" name="industry">
				<option value="">全部</option>
				<option value="工厂">工厂</option>
				<option value="学校/食堂">学校/食堂</option>
				<option value="下级批发商">下级批发商</option>
				<option value="餐馆">餐馆</option>
				<option value="超市">超市</option>
				<option value="门店">门店</option>
				<option value="垂直生鲜电商">垂直生鲜电商</option>
			</select>&nbsp;&nbsp;
			推广来源：
			<select id="source" name="source">
				<option value="">全部</option>
				<option value="农速通">农速通</option>
				<option value="农商友">农商友</option>
				<option value="农商友-农批商">农商友-农批商</option>
			</select>&nbsp;&nbsp;
			推广人：
			<input type="text" id="pushName" placeholder="推广人"
			class="easyui-validatebox" name="pushName" style="width:150px" >&nbsp;&nbsp;
			推广人手机号：
			<input type="text" id="pushMobile" placeholder="推广人手机号"
			class="easyui-validatebox" name="pushMobile" style="width:150px" >&nbsp;&nbsp;
			会员手机号：
			<input type="text" id="memberMobile" placeholder="会员手机号"
			class="easyui-validatebox" name="memberMobile" style="width:150px" >&nbsp;&nbsp;
			<br>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#pricesSearchForm').form('validate');" >查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
<!-- 			<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a> -->
<!-- 			<a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a> -->
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
<!-- 			<a id="importData" class="easyui-linkbutton" iconCls="icon-save">导入数据</a> -->
			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
		</div>
		<div style="    margin-top: 5px;">共有记录<span id="total"></span>条</div>
		</form>
	</div>
</body>
<script type="text/javascript">

var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var industry = $('#industry').val();
	var source = $('#source').val();
	var pushName = $('#pushName').val();
	var pushMobile = $('#pushMobile').val();
	var memberMobile = $('#memberMobile').val();
	var startDate = $("#startDate").datetimebox("getValue");
	var endDate = $("#endDate").datetimebox("getValue");
	var params = {
			"endDate" : endDate,
			"source" : source,
			"pushName" : pushName,
			"pushMobile" : pushMobile,
			"memberMobile" : memberMobile,
			"endDate" : endDate,
			"startDate" : startDate
		};

	var paramList = 'industry='+industry+
	"&source="+source+"&pushName="+pushName+"&pushMobile="+pushMobile+
	"&memberMobile="+memberMobile+"&startDate="+startDate+"&endDate="+endDate;

	$.ajax({
		url: CONTEXT+'pushOffline/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'pushOffline/exportData',paramList,'post' );
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

/* 	window.location.href=CONTEXT+'pushOffline/exportData?industry='+industry+
			"&source="+source+"&pushName="+pushName+"&pushMobile="+pushMobile+
			"&memberMobile="+memberMobile+"&startDate="+startDate+"&endDate="+endDate; */
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
<script type="text/javascript" src="${CONTEXT}js/pushOffline/index.js"></script>
</html>

