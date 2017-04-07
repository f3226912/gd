<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
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
<title>拨打电话统计</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="datagrid-table"></table>
<div id="datagrid-tool-bar" style="padding:5px;height:auto">
	<form id="datagrid-form" method="post">
	<div>
		拨打来源：
		<select name="source"  id="source" class="easyui-validatebox" style="width:150px">
		  <option value="">全部</option>
		  <option value="1">物流公司找车</option>
		  <option value="2">物流公司分给我的货</option>
		  <option value="3">物流公司订单</option>
		  <option value="4">车主找货</option>
		  <option value="5">车主订单</option>
		  <option value="6">货主我发的货</option>
		  <option value="7">货主运单</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		APP来源：
		<select name="callRole"  id="callRole" class="easyui-validatebox" style="width:150px">
		  <option value="">全部</option>
		  <option value="0">货主</option>
		  <option value="1">车主</option>
		  <option value="2">物流公司</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主叫姓名：
		<input type="text" id="e_Name" name="e_Name" maxlength="30" placeholder="请输入姓名" class="easyui-validatebox" style="width:150px" >
		&nbsp;&nbsp;&nbsp;&nbsp;
		主叫手机：
		<input type="text" id="e_Mobile" name="e_Mobile" maxlength="11" placeholder="请输入手机号码" class="easyui-numberbox" style="width:150px" >
		<br/>
		地推姓名：
		<input type="text" id="grdUserName" name="grdUserName" placeholder="请输入姓名" maxlength="30" class="easyui-validatebox" style="width:150px;margin-bottom:5px" >
		&nbsp;&nbsp;&nbsp;&nbsp;
		所属市场：
		<select name="marketId"  id="marketId" class="easyui-validatebox" style="width:150px">
		  <option value="">全部</option>
		  <c:forEach items="${allMarket2}" var="market">
					<option value="${market.id }">${market.marketName }</option>
		  </c:forEach>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		主叫业务范围：
		<select name="serviceType"  id="serviceType" class="easyui-validatebox" style="width:150px">
		  <option value="">全部</option>
		  <option value="1">干线业务</option>
		  <option value="2">同城业务</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		被叫姓名：
		<input type="text" id="s_Name" name="s_Name" maxlength="30" placeholder="请输入姓名" class="easyui-validatebox" style="width:150px;margin-top:3px" >
		<br/>
		被叫手机：
		<input type="text" id="s_Mobile" name="s_Mobile" maxlength="11" placeholder="请输入手机号码" class="easyui-numberbox" style="width:150px" >
		&nbsp;&nbsp;&nbsp;&nbsp;
		拨打时间：
		<input type="text" id="startDate" name="startDate" onfocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onclick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" style="width:69px">~
		<input type="text" id="endDate" name="endDate" onfocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onclick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" style="width:69px">
		&nbsp;&nbsp;&nbsp;&nbsp;<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>
		<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		
	</div>
	</form>
	
	<div >
	<!-- 	<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="btn-add" >新增</a>
		<a class="easyui-linkbutton icon-add-btn" iconCls="icon-edit" id="btn-edit" >编辑</a>
		<a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="btn-remove">删除</a> -->
		<label>拨打电话列表</label>
		<span style="padding-left: 870px;">
		<gd:btn btncode='BTNBDDHTJ02'>
		<a class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
		</gd:btn>
		<gd:btn btncode='BTNBDDHTJ01'>
		<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出EXCEL</a>
		</gd:btn>
		</span>
	</div>
</div>

</body>
<script type="text/javascript">
function optformat(value,row,index){
	var html="";
//	html+="<a class='operate' href='javascript:;' onclick=view('"+row.createUserId+"');>查看</a>";
//	html+="<a class='operate' href='javascript:;' onclick=edit('"+row.createUserId+"');>修改</a>&nbsp;";
//	html+="<a class='operate' href='javascript:;' onclick=delete('"+row.createUserId+"');>删除</a>";
	return html;
}
var disableExport = false ;

/***数据导出功能***/
$("#exportData").click(function(){
	var marketId = $('#datagrid-form #marketId').val();
	var startDate = $('#datagrid-form #startDate').val();
	var endDate = $('#datagrid-form #endDate').val();
	var grdUserName = $('#datagrid-form #grdUserName').val();
	var serviceType = $('#datagrid-form #serviceType').val();
	var source = $('#datagrid-form #source').val();
	var s_Mobile = $('#datagrid-form #s_Mobile').val();
	var callRole = $('#datagrid-form #callRole').val();
	var s_Name = $('#datagrid-form #s_Name').val();
	var e_Mobile = $('#datagrid-form #e_Mobile').val();
	var e_Name = $('#datagrid-form #e_Name').val();
	var params = {
		"marketId" : marketId,
		"startDate" : startDate,
		"endDate" : endDate,
		"grdUserName" : grdUserName,
		"serviceType" : serviceType,
		"source" : source,
		"s_Mobile" : s_Mobile,
		"callRole" : callRole,
		"s_Name" : s_Name,
		"e_Mobile" : e_Mobile,
		"e_Name" : e_Name
	};
	var paramList = gudeng.commons.convertParamsToDelimitedList(params);
	$.ajax({
		url: CONTEXT+'grdProCallstatistics/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				/* $("#Loading2").show(); */
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'grdProCallstatistics/exportData',paramList,'post' );
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
<script type="text/javascript" src="${CONTEXT}js/grdProCallstatistics/main.js"></script>
</html>