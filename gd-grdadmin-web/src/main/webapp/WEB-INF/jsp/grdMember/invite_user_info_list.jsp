<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../pub/constants.inc" %>

<style type="text/css">
	.userInfoListMainDiv{border: thin solid #009DD9;text-align: center;min-height: 99%;overflow: visible;}
	.userInfoListMainDiv .mark {color: red;}
	.userInfoListMainDiv .widget{width: 300px;height: 20px;}
	.userInfoListMainDiv .topTip{width:95%;font-size: 25px;margin:0px auto 15px auto;border-bottom: thin solid #E4E4E4;text-align: left;}
	.userInfoListMainDiv .userInfoListDataBody{width:800px;margin:0px auto;overflow: visible;height: 325px;border: thin dotted #cccccc;}
	.userInfoListMainDiv .userInfoListDataContent{width:100%;overflow: visible;height: 300px;}
</style>
<div id="grdMemberUserToolBar" style="height:auto;padding:0px !important;margin:0px;">
	<form id="grdUserMemberForm" method="post" style="padding:5px 0px 5px 20px;">
	客户手机号：<input  type="text" id="userMobile" class="easyui-validatebox" name="userMobile" style="width:100px;margin-right: 10px;" placeholder="请输客户手机号"/>
	 注册时间：<input  type="text"  id="queryUserStartDate" name="queryUserStartDate"  onFocus="WdatePicker({onpicked:function(){queryUserStartDate.focus();},maxDate:'#F{$dp.$D(\'queryUserEndDate\')}'})"    onClick="WdatePicker({onpicked:function(){queryUserStartDate.focus();},maxDate:'#F{$dp.$D(\'queryUserEndDate\')}'})"    style="width:150px" >
			~
			<input  type="text"   id="queryUserEndDate" name="queryUserEndDate"   onFocus="WdatePicker({onpicked:function(){queryUserEndDate.focus();},minDate:'#F{$dp.$D(\'queryUserStartDate\')}'})"   onClick="WdatePicker({onpicked:function(){queryUserEndDate.focus();},minDate:'#F{$dp.$D(\'queryUserStartDate\')}'})"   style="width:150px"> 
		<gd:btn btncode='BTNGRDMEMBERSL06'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="searchUserBtn">查询</a></gd:btn>
							<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="divUserResetBtn">重置</a>
	</form>
	</div><br>	
<div class="userInfoListMainDiv">
	<div class="topTip">
		<span style="margin-left: 25px;">所属市场：${requestScope.grdMemberDTO.market }</span>
		<span style="margin-left: 25px;">所属团队：${requestScope.grdMemberDTO.giftteamName }</span>
		<span style="margin-left: 75px;">地推人员：${requestScope.grdMemberDTO.name } &nbsp;&nbsp;&nbsp;  ${requestScope.grdMemberDTO.mobile }</span>
	</div>

	<div class="userInfoListDataBody">
		<div class="userInfoListDataContent">
			<div style="background:#efefef;padding:15px 5px 0x 5px;height:24px;">
				<div style="float: left;margin-left:5px;margin-top: 5px;">邀请注册客户列表</div>
			</div>
			<table id="userInfoDataGrid" title=""></table>
		</div>
	</div>
	
	<input type="hidden" id="grdMemberId" name="grdMemberId" value="${requestScope.grdMemberDTO.id }" />
	
</div>

<script type="text/javascript" src="${CONTEXT}js/grdMember/invite_user_info_list.js"></script>
