<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../pub/constants.inc" %>

<style type="text/css">
	.orderInfoListMainDiv{border: thin solid #009DD9;text-align: center;min-height: 99%;overflow: visible;}
	.orderInfoListMainDiv .mark {color: red;}
	.orderInfoListMainDiv .widget{width: 300px;height: 20px;}
	.orderInfoListMainDiv .topTip{width:95%;font-size: 25px;margin:0px auto 15px auto;border-bottom: thin solid #E4E4E4;text-align: left;}
	.orderInfoListMainDiv .orderInfoListDataBody{width:800px;margin:0px auto;overflow: visible;height: 325px;border: thin dotted #cccccc;}
	.orderInfoListMainDiv .orderInfoListDataContent{width:100%;overflow: visible;height: 300px;}
</style>
<div id="grdMemberToolBar" style="height:auto;padding:0px !important;margin:0px;">
	<form id="grdOrderMemberForm" method="post" style="padding:5px 0px 5px 20px;">
	订单号：<input  type="text" id="orderCode" class="easyui-validatebox" name="orderCode" style="width:100px;margin-right: 10px;" placeholder="请输订单号"/>
	商铺：<input  type="text" id="businessName" class="easyui-validatebox" name="businessName" style="width:100px;margin-right: 10px;" placeholder="请输商铺名"/>
	 交易时间：<input  type="text"  id="queryOrderStartDate" name="queryOrderStartDate"  onFocus="WdatePicker({onpicked:function(){queryOrderStartDate.focus();},maxDate:'#F{$dp.$D(\'queryOrderEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryOrderStartDate.focus();},maxDate:'#F{$dp.$D(\'queryOrderEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryOrderEndDate" name="queryOrderEndDate"   onFocus="WdatePicker({onpicked:function(){queryOrderEndDate.focus();},minDate:'#F{$dp.$D(\'queryOrderStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryOrderEndDate.focus();},minDate:'#F{$dp.$D(\'queryOrderStartDate\')}'})"   style="width:150px"> 
		<gd:btn btncode='BTNGRDMEMBERSL06'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="searchOrderBtn">查询</a></gd:btn>
							<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="divResetBtn">重置</a>
	</form>
	</div><br>	
<div class="orderInfoListMainDiv">
	<div class="topTip">
		<span style="margin-left: 25px;">所属市场：${requestScope.grdMemberDTO.market }</span>
		<span style="margin-left: 25px;">所属团队：${requestScope.grdMemberDTO.giftteamName }</span>
		<span style="margin-left: 75px;">地推人员：${requestScope.grdMemberDTO.name } &nbsp;&nbsp;&nbsp;  ${requestScope.grdMemberDTO.mobile }</span>
	</div>

	<div class="orderInfoListDataBody">
		<div class="orderInfoListDataContent">
			<div style="background:#efefef;padding:15px 5px 0x 5px;height:24px;">
				<div style="float: left;margin-left:5px;margin-top: 5px;">促成的订单列表</div>
			</div>
			<table id="orderInfoDataGrid" title=""></table>
		</div>
	</div>
	
	<input type="hidden" id="grdMemberId" name="grdMemberId" value="${requestScope.grdMemberDTO.id }" />
	
</div>

<script type="text/javascript" src="${CONTEXT}js/grdMember/order_info_list.js"></script>











