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
		<title>账户贷款信息</title>
	</head>
<body>
	<table id="carsdg" title="">
	</table>
	<div id="carstb" style="padding:5px;height:auto">
		<form id="carsSearchForm" method="post">
		<div>
		    申请时间：
		     <input  type="text"  id="queryStartDate" name="queryStartDate"  onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryStartDate.focus();},maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryEndDate" name="queryEndDate"   onFocus="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryEndDate.focus();},minDate:'#F{$dp.$D(\'queryStartDate\')}'})"   style="width:150px"> 
		   用户账号：
			<input  type="text" id="memberAccount"  name="memberAccount"  style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
		   所属市场：
		 <select id="marketId" name="marketId" class="text_sketch" >
		  
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
		 <br>
		  订单总交易额：
		     <input  type="text"  id="startOrderAmount" name="startOrderAmount"   style="width:150px" >
			~
			<input  type="text"   id="endOrderAmount" name="endOrderAmount"     style="width:150px"> 
		   用户星级：
			<select id="userStar" name="userStar" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
		   <option value="1">一星</option>        
           <option value="2">二星 </option> 
           <option value="3">三星</option>   
           <option value="4">四星</option>  
           <option value="5">五星</option>  
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
		  贷款额度：
		 <select id="creditQuotaRange" name="creditQuotaRange" class="text_sketch" >
		  <option selected="selected" value="">全部</option>
		   <option value="1">1——5万</option>        
           <option value="2">10——50万 </option> 
           <option value="3">50——100万</option>   
           <option value="4">100——300万</option>   
		 </select>&nbsp;&nbsp;&nbsp;&nbsp;
		 
		<gd:btn btncode='BTNGXWLXXGLCLXXGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-sea	rch" id="icon-search" OnClick ="return $('#carsSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id='btn-reset'>重置</a>
		</div>
		</form>
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNGXWLXXGLCLXXGL05'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>
		<div id="detailDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
	</div>
</body>
<script type="text/javascript" src="${CONTEXT}js/financeCredit/financeCreditList.js"></script>
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
				$('#marketId').append("<option value=''>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#marketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}
initMarket(2);
</script>
</html>

