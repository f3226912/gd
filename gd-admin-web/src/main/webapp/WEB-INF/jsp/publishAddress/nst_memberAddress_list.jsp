<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>发货信息</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="memberdg" title="">
	</table>
	<div id="membertb" style="padding:5px;height:auto">
		<form id="memberSearchForm" method="post">
		<div>
		    <input  type="hidden" id="transportType"    name="transportType"  value="0" />
			发布时间：
			<input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"  id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px"> 
			
			发布来源：
			<!-- 1谷登农批，2农速通，3农商友，4产地供应商，5农商友-农批商' -->
		    <select id="clients" name="clients" class="text_sketch" >
		      <option selected="selected" value="">全部</option>
		      <option  value="1">谷登农批</option>
		      <option  value="2">农速通</option>
		      <option  value="3">农商友</option>
		      <option  value="4">产地供应商</option>
		      <option  value="5">农商友-农批商</option>
		    </select>&nbsp;&nbsp;&nbsp;&nbsp;
		    
			发布人账号: 
			<input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:150px" ></br>
		           公司名称：
		    <input  type="text" id="companyName"    class="easyui-validatebox"    name="companyName" style="width:150px" >
		    代发布人账号: 
			<input  type="text" id="publishAccount"    class="easyui-validatebox"    name="publishAccount" style="width:150px" >
			<br/>
			订单状态: 
				<select name="orderStatus" id="orderStatus"  >
 							<option value="">请选择</option>
 							<option value="0" >待接单</option>
							<option value="1" >待成交</option>
							<option value="2" >已成交</option>
							<option value="3" >已完成</option>
					    	<option value="4" >未完成</option>
					    	<option value="5" >已取消</option>
						</select> &nbsp;&nbsp;
			分配类型: 
			<select name="distributeType" id="distributeType"  >
 							<option value="">请选择</option>
							<option value="0" >直发</option>
							<option value="1" >代发</option>
						</select> &nbsp;&nbsp;
		  常用城市 : 
	     <input  type="text" id="cityName"  name="cityName"  style="width:150px" ></br>
		    始发地省:<input  name="start_provinceId" id="start_provinceId"
					style="width: 174px;" 
					class="easyui-validatebox">
		  始发地市:
				<input name="start_cityId" id="start_cityId"
					style="width: 174px;" 
					class="easyui-validatebox">
		  始发地县区:<input name="start_areaId" id="start_areaId"
					style="width: 174px;" class="easyui-validatebox"> </br>
		 目的地省:<input name="end_provinceId" id="end_provinceId"
					style="width: 174px;" 
					class="easyui-validatebox">
	            目的地市:<input name="end_cityId" id="end_cityId"
					style="width: 174px;" 
					class="easyui-validatebox">
		目的地县区:<input name="end_areaId" id="end_areaId"
					style="width: 174px;" class="easyui-validatebox"></br>
			<gd:btn btncode='BTNGXWLXXGLFHXXJL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#memberSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a> &nbsp;&nbsp;
		    <a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a> &nbsp;&nbsp;
 			<gd:btn btncode='BTNGXWLXXGLFHXXJL02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		</form>
	 
		
		<div id="showDialog" class="easyui-dialog"  closed="true" modal="true" buttons="#dlg-buttonsShow">
			<div id="dlg-buttonsShow" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#showDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
function optFormat(value,row,index){
	return "<gd:btn btncode='BTNGXWLXXGLFHXXJL03'><a class='operate' href='javascript:;' onclick=showObj('"+row.memberId+"');>查看</a></gd:btn>" ;
}
</script>
<script type="text/javascript" src="${CONTEXT}js/publishAddress/main.js"></script>
<script type="text/javascript" src="${CONTEXT}js/publishAddress/area.js"></script>

<script type="text/javascript">
	var disableExport = false ;
	$("#exportData").click(function(){
       var queryParams = $('#memberdg').datagrid('options').queryParams;
		queryParams.account = $('#memberSearchForm #account').val();
		queryParams.companyName = $('#memberSearchForm #companyName').val();
		queryParams.publishAccount = $('#memberSearchForm #publishAccount').val();
		queryParams.queryStartDate =  $('#memberSearchForm #queryStartDate').val();
		queryParams.queryEndDate =  $('#memberSearchForm #queryEndDate').val();
		queryParams.clients = $("#memberSearchForm #clients").val();
		queryParams.distributeType = $("#memberSearchForm #distributeType").val();
		queryParams.cityName = $("#memberSearchForm #cityName").val();
	    queryParams.orderStatus = $("#memberSearchForm #orderStatus").val();

       //始发地，目的地
	    queryParams.start_provinceId =$('#memberSearchForm #start_provinceId').val();
	    queryParams.start_cityId =$('#memberSearchForm #start_cityId').val();
	    queryParams.start_areaId =$('#memberSearchForm #start_areaId').val();
	    queryParams.end_provinceId =$('#memberSearchForm #end_provinceId').val();
	    queryParams.end_cityId =$('#memberSearchForm #end_cityId').val();
	    queryParams.end_areaId =$('#memberSearchForm #end_areaId').val();
        queryParams.transportType =$('#memberSearchForm #transportType').val();
    
		window.location.href=CONTEXT+'publishAddress/exportData?account='+queryParams.account+
						"&companyName="+queryParams.companyName+"&publishAccount="+queryParams.publishAccount+
						"&clients="+queryParams.clients+"&queryStartDate="+queryParams.queryStartDate+
						"&queryEndDate="+queryParams.queryEndDate+"&distributeType="+queryParams.distributeType+
						"&cityName="+queryParams.cityName+
						"&orderStatus="+queryParams.orderStatus
					    +"&start_provinceId="+queryParams.start_provinceId	
		                +"&start_cityId="+queryParams.start_cityId	
		                +"&start_areaId="+queryParams.start_areaId	
		                +"&end_provinceId="+queryParams.end_provinceId	
		                +"&end_cityId="+queryParams.end_cityId	
		                +"&end_areaId="+queryParams.end_areaId
						+"&transportType="+queryParams.transportType;
		                			var queryParams = {
			"account":$('#memberSearchForm #account').val(),
			"companyName":$('#memberSearchForm #companyName').val(),
			"publishAccount":$('#memberSearchForm #publishAccount').val(),
			"clients":$("#memberSearchForm #clients").val(),
			"queryStartDate":$('#memberSearchForm #queryStartDate').val(),
			"queryEndDate":$('#memberSearchForm #queryEndDate').val(),
			"distributeType":$("#memberSearchForm #distributeType").val(),
			"cityName":$("#memberSearchForm #cityName").val(),
			"orderStatus":$("#memberSearchForm #orderStatus").val(),
			"start_provinceId":$('#memberSearchForm #start_provinceId').val(),
			"start_cityId":$('#memberSearchForm #start_cityId').val(),
			"start_areaId":$('#memberSearchForm #start_areaId').val(),
			"end_provinceId":$('#memberSearchForm #end_provinceId').val(),
			"end_cityId":$('#memberSearchForm #end_cityId').val(),
			"end_areaId":$('#memberSearchForm #end_areaId').val(),
			"transportType":$('#memberSearchForm #transportType').val()
		};
		var paramList = "account="+queryParams.account+
			"&companyName="+queryParams.companyName+"&publishAccount="+queryParams.publishAccount+
			"&clients="+queryParams.clients+"&queryStartDate="+queryParams.queryStartDate+
			"&queryEndDate="+queryParams.queryEndDate+"&distributeType="+queryParams.distributeType+
			"&cityName="+queryParams.cityName+
			"&orderStatus="+queryParams.orderStatus
		    +"&start_provinceId="+queryParams.start_provinceId	
            +"&start_cityId="+queryParams.start_cityId	
            +"&start_areaId="+queryParams.start_areaId	
            +"&end_provinceId="+queryParams.end_provinceId	
            +"&end_cityId="+queryParams.end_cityId	
            +"&end_areaId="+queryParams.end_areaId;
		$.ajax({
			url: CONTEXT+'publishAddress/checkExportParams',
			data : queryParams,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'publishAddress/exportData',paramList,'post');
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

