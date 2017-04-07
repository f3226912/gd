<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
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
<title>活动管理</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>

<table id="datagrid-table" title=""></table>
<div id="datagrid-tool-bar" style="padding:5px;height:auto">
	<form id="datagrid-form" method="post">
	<input type="hidden" id="hdQueryState" name="queryState" value="${state}"/>
	<div>
		活动编号 : 
		<input type="text" id="activityCode" name="activityCode" placeholder="活动编号" class="easyui-validatebox" style="width:150px" >
		活动名称 : 
		<input type="text" id="activityName" name="activityName" placeholder="活动名称" class="easyui-validatebox" style="width:150px" >
		活动类型 : 
		<select id="activityType" name="activityType"  style="width:150px" >
			<option value="">全部</option>
			<option value="1">刷卡补贴</option>
			<option value="2">市场佣金</option>
			<option value="3">平台佣金</option>
			<option value="4">预付款/违约金</option>
			<option value="5">物流配送</option>
			<option value="6">采购积分</option>
		</select>
		活动状态 : 
		<select id="state" name="state" style="width:150px" >
			<option value="">全部</option>
			<c:if test="${endPage eq 1}">
				<option value="2">结束</option>
			</c:if>
			<c:if test="${endPage eq 0}">
				<option value="0">禁用</option>
				<option value="1">启用</option>
			</c:if>
		</select><br/>
		所属市场 : 
		<input  id="marketId" name="marketId" >
		活动时间 : 
		<input  type="text"  id="startDate" name="startDate"
			onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
			onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"
			style="width:150px" >~
		<input  type="text"    id="endDate" name="endDate"
			onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"
			onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">
		<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
	</div>
	</form>
	<div style="margin-bottom:5px">
		<c:if test="${endPage eq 0 and state eq 1}">
			<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="showActivityWin" >新增活动</a>
		</c:if>
		<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出EXCEL</a>
		<a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
	</div>
	
	<div id="shopDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsShop">
		<div id="#dlg-buttonsShop" style="text-align:center">
	       	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-member-btn" >确定</a>
	           <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#shopDialog').dialog('close')">关闭</a>
	       </div>
    </div>
    
	<div id="activityDialog" class="easyui-dialog" style="width:800px;height:400px;" closed="true" modal="true" buttons="#dlg-buttonsActivity">
		<div id="#dlg-buttonsActivity" style="text-align:center">
		</div>
    </div>
    <div id="selectActivity" class="easyui-dialog" style="width:400px;height:300px;" closed="true" modal="true" buttons="#dlg-buttonsSelectActivity">
		<div id="#dlg-buttonsSelectActivity" style="text-align:center">
		</div>
    </div>
    
</div>
</body>
<script type="text/javascript">

var disableExport = false ;
/***数据导出功能***/
$("#exportData").click(function(){
	var activityCode = $('#datagrid-form #activityCode').val();
	var activityName = $('#datagrid-form #activityName').val();
	var activityType = $('#datagrid-form #activityType').val();
	var state = $('#datagrid-form #state').val();
	var marketId = $('#datagrid-form #marketId').val();
	var startDate = $('#datagrid-form #startDate').val();
	var endDate = $('#datagrid-form #endDate').val();
	var queryState=$("#hdQueryState").val();

	var params = {
		"activityCode" : activityCode,
		"activityName" : activityName,
		"activityType" : activityType,
		"state" : state,
		"marketId" : marketId,
		"startDate" : startDate,
		"endDate" : endDate,
		"queryState":queryState
	};
	var paramList = gudeng.commons.convertParamsToDelimitedList(params);
	$.ajax({
		url: CONTEXT+'gdActActivity/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				/* $("#Loading2").show(); */
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...") ;
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'gdActActivity/exportData',paramList,'post' );
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
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/main.js"></script>
</html>