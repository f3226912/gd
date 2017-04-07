<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
	<title>我的促成订单</title>
	
	<script type="text/javascript" src="${CONTEXT}js/grdProMemberInvitedRegister/grd_order.js"></script>
	<style type="text/css" rel="stylesheet"  >
		#searchbox{height: 65px; padding: 10px; margin: 0; }
		.datagrid-toolbar {
		    padding-left: 0px !important;
		}
	</style>
</head>

<body>
	
	<table id="datagrid"></table>
	<div id="searchbox">  
		<form id="search_fm" method="post">
			<div class="fitem">
			 	所属市场：<select id="market" name="marketId" style="width:150px;">
			 			<option value="" selected="selected">全部</option>
   					   </select>&nbsp;&nbsp;
   				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 商铺：<input id="shopsName" name="shopsName" maxlength="30" style="width: 150px;">&nbsp;&nbsp;
   				地推姓名：<input id="name" name="name" maxlength="30" style="width: 150px;">&nbsp;&nbsp;
   				地推手机：<input id="mobile" name="mobile" maxlength="11" style="width: 150px;">&nbsp;&nbsp;
			    <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchMarket()">查询</a>
			    <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadMarket()">重置</a><br/>
   				&nbsp;&nbsp;订单号：<input id="orderNo" name="orderNo" maxlength="30" style="width: 150px;">&nbsp;&nbsp;
   				买家手机：<input id="buyerMobile" name="buyerMobile" maxlength="11" style="width: 150px;">&nbsp;&nbsp;
   				交易时间：<input id="startDate" style="width: 100px;" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"  >~
				<input  type="text"  id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:100px">
			</div>
		</form>
		
	</div>
	<div id="exp_btn" style="background:#efefef;padding:5px;height:25px;">
		<div style="float: left;font-size:16px;margin-left:5px;">促成订单统计</div>
		<div style="float:right;margin-right:57px;">
			<gd:btn btncode='BTNCCDDTJ01'><a id="exportData" onclick="exportData()" class="easyui-linkbutton l-btn l-btn-small" group=""><span class="l-btn-left l-btn-icon-left">导出数据<span class="l-btn-icon icon-save">&nbsp;</span></span>
			</a></gd:btn>
		</div>
	</div>
</body>
<script type="text/javascript">
function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#market').empty();
				$('#market').append("<option value=''>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#market').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}
initMarket(2);
</script>
</html>