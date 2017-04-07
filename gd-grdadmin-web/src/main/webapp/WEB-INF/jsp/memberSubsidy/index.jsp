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
		<title>会员补贴信息主页</title>
	</head>
		<%@ include file="../pub/head.inc" %>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="subsidyDatagrid" title="会员补贴信息">
	</table>
	<div id="toolbar" style="padding:5px;height:auto">
		<form id="subsidySearchForm" method="post">
		<div>
			注册时间：
			<input  type="text"  id="startDate" name="startDate" placeholder="开始时间"
			class="easyui-validatebox easyui-datetimebox" editable="false" style="width:150px" >
			至
			<input  type="text"    id="endDate" name="endDate" placeholder="结束时间"
			class="easyui-validatebox easyui-datetimebox" editable="false" style="width:150px">
			注册帐号：
			<input type="text" id="account" placeholder="注册帐号"
			class="easyui-validatebox" name="account" style="width:150px" >
			手机号：
			<input type="text" id="mobile" placeholder="手机号"
			class="easyui-validatebox" name="mobile" style="width:150px" >
			<br>
			注册来源：
			<select id="level" name="level">
				<option value="-99">全部</option>
				<option value="1">谷登农批</option>
				<option value="2">农速通</option>
				<option value="3">农商友</option>
				<option value="4">产地供应商</option>
				<option value="5">门岗</option>
			</select>
			<gd:btn btncode='BTNTJXXHYBT01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#subsidySearchForm').form('validate');" >查询</a></gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNTJXXHYBT02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		</form>
	</div>
</body>

<script type="text/javascript">

var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var account = $('#subsidySearchForm #account').val();
	var mobile = $('#subsidySearchForm #mobile').val();
	var level = $('#subsidySearchForm #level').val();
	var marketId = $('#subsidySearchForm #marketId').val();
	var startDate = $("#startDate").datetimebox("getValue");
	var endDate = $("#endDate").datetimebox("getValue");

	var params = {
			"account" : account,
			"mobile" : mobile,
			"level" : level,
			"marketId" : marketId,
			"startDate" : startDate,
			"endDate" : endDate
		};

	var paramList = 'account='+account+
	"&mobile="+mobile+"&level="+level+"&startDate="+startDate+"&endDate="+endDate;

	$.ajax({
		url: CONTEXT+'memberSubsidy/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){

				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'memberSubsidy/exportData',paramList,'post' );
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
/*
	window.location.href=CONTEXT+'memberSubsidy/exportData?account='+account+
			"&mobile="+mobile+"&level="+level+"&startDate="+startDate+"&endDate="+endDate; */
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
<script type="text/javascript" src="${CONTEXT}js/jquery.form.js"></script>
<script type="text/javascript" src="${CONTEXT}js/memberSubsidy/index.js"></script>
<script type="text/javascript" src="${CONTEXT}js/memberSubsidy/add.js"></script>
<script type="text/javascript">
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#marketId').empty();
				$('#marketId').append("<option value='-99'>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#marketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}
// initMarket(2);
</script>
</html>

