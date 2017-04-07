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
		<title>business</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="businessdg" title="">
	</table>
	<div id="businesstb" style="padding:5px;height:auto">
		<form id="businessSearchForm" method="post">
		<div>
			商铺ID：
			<input  type="text" id="businessId1"    name="businessId1" style="width:150px" >
			商铺名称：
			<input  type="text" id="shopsName"    name="shopsName" style="width:150px" >
<!-- 			公司名称:  -->
<!-- 		<input  type="text" id="name"       name="name" style="width:150px" > -->
<!-- 			开通时间： -->
<!-- 			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" > -->
<!-- 			~ -->
<!-- 			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">  -->
<!-- 			<br> -->
			用户类型：
			<select name="level" id="level"  >
				<option value="">--用户类型--</option>
				<option value="1" >农批商</option>
				<option value="4" >产地供应商</option>
			</select>
			<input type="hidden" id="marketId_add" name="marketId" value="" >
			 用户账号：<input  type="text" id="account"  name="account" style="width:150px" >
			 电话号码：<input  type="text" id="mobile"  name="mobile" style="width:150px" >
			<!-- <br>
			商铺所在地：<input type="checkbox" id="checkAddress" >
					 <select name="provinceId"  id="provinceId1"   class="i-app-far-mar-c3" ></select>
					 <select name="cityId"  id="cityId1"  class="i-app-far-mar-c3" ></select>
					 <select name="areaId"  id="areaId1"  class="i-app-far-mar-c3" ></select>
			商铺地址：	<input class="i-app-far-mar-ph" type="text" maxlength="200"   name="address" id="address1"/> -->
			<div>
					<%-- <div>
						所属市场：<input type="button" id="showMarketWin" value="选择市场">
								<input type="hidden" id="marketId_add" name="marketId" value="" >
					</div>
					<div id="categorys"></div>
					<div id="categorys">
							<gd:btn btncode='BTNHYGLDPGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#businessSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
							<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
					</div> --%>
					<table width="100%">
						<tr>
							<td width="275" align="left">
									终端号：<input  type="text" id="posNumber"  name="posNumber" style="width:150px" >
							</td>
							<td width="230" align="left">
								所属市场：<input type="button" id="showMarketWin" value="选择市场">
								<input type="hidden" id="marketId_add" name="marketId" value="" >
							</td>
							<td  width="200" class="mleft" >
							线下认证 :
							<select name="offlineStatus" id="offlineStatus" >
								<option value="">-请选择-</option>
								<option value="1">已认证</option>
								<option value="2">未认证</option>
							</select>						
							</td>
							<td width="200" align="left">
								已绑定终端：<select name="termType" id="termType"  >
									<option value="">--请选择--</option>
									<option value="1" >POS机</option>
									<option value="2" >智能秤</option>
								</select>
							</td>
							<td width="200" align="left">
								<div id="categorys" align="left"></div>
							</td>
							<td width="300" align="left">
								<gd:btn btncode='BTNHYGLDPGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#businessSearchForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
								<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
							</td>
						</tr>
					</table>
			</div>
		</div>
		</form>
		<div style="margin-bottom:5px">
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
			<gd:btn btncode='BTNHYGLDPGL02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>

		<div id="addDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="editDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>

		<div id="showDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsShow">
			<div id="dlg-buttonsShow" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#showDialog').dialog('close')">关闭</a>
	        </div>
		</div>
 		<div id="marketDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsMarket">
			<div id="#dlg-buttonsMarket" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#marketDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">

var disableExport = false ;

function optFormat(value,row,index){
	return ""+
	"<a class='operate' href='javascript:;' onclick=showObj('"+row.businessId+"');>查看</a>" +
	"<gd:btn btncode='BTNHYGLDPGL03'><a class='operate' href='javascript:;' onclick=editObj('"+row.businessId+"');>编辑</a></gd:btn>";
}

function marketSelectCallBack(index,row){
	$("#marketId_add").val(row.id);
	$("#showMarketWin").val(row.marketName);
	$('#marketDialog').dialog('close');
	ajaxGetCategory(row.id);
}

$('#showMarketWin').click(function(){
	$('#marketDialog').dialog({
			'title' : '选择街市',
			'width' : 700,
			'height' : 300,
			'href' : CONTEXT + 'product/marketSelect'
		}).dialog('open');
	});

</script>
<script type="text/javascript" src="${CONTEXT}js/business/main.js"></script>

<script type="text/javascript" src="${CONTEXT}js/member/city.js"></script>
<script>



 	$(document).ready(function() {
 		var city = new City($("#provinceId1"),$("#cityId1"),$("#areaId1"));
 		city.init('0','0','0');

 	});
	/**
 	$("#checkAddress").change(function(){
 		if($("#checkAddress").is(':checked')){
 			$("#provinceId1").attr("disabled",false)
 			$("#cityId1").attr("disabled",false)
 			$("#areaId1").attr("disabled",false)
 		}else{
 			$("#provinceId1").attr("disabled","disabled")
 			$("#cityId1").attr("disabled","disabled")
 			$("#areaId1").attr("disabled","disabled")
 		}
 	});
	*/
</script>


</html>

