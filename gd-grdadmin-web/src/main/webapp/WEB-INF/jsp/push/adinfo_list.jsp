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
		<title>push</title>
		<script type="text/javascript" src="${CONTEXT}js/push/adinfo.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
	</head>
<body>
	<table id="adinfodg" title="">
	</table>
	<div id="adinfotb" style="padding:5px;height:auto">
		<form id="adinfoSearchForm" method="post">
		<div>
			广告名称: 
			<input type="text" id="adNameList" class="easyui-validatebox" name="adName" style="width:150px" >
			广告渠道:
			<select name="adCanal" id="adCanalList" style="width: 150px;">
				<option value="">---请选择---</option>
				<option value="01">农批web</option>
				<option value="02">农商友</option>
				<option value="03">农速通</option>
			</select>
			广告类型:
			<select name="adType" id="adTypeList" style="width: 150px;">
				<option value="">---请选择---</option>
				<option value="01">轮播图</option>
				<option value="02">产品推送</option>
				<option value="03">副图</option>
				<option value="04">全国农贸市场图</option>
			</select>
			发布时间 :
			<input  type="text"  id="startDate" name="startDate"  
				onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
				style="width:150px" >~
			<input  type="text"    id="endDate" name="endDate"   
				onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   
				onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"   style="width:150px">
			<br/>
			农贸市场:
			<select name="marketId" id="marketIdList" style="width: 150px;">
				<option value="">---请选择---</option>
				<c:forEach items="${marketList}" var="market" varStatus="s">
					<option value="${market.id }">${market.marketName }</option>
				</c:forEach>
			</select>
			状态:
			<select name="state" id="stateList" style="width: 150px;">
				<option value="">---请选择---</option>
				<option value="01">开始</option>
				<option value="02">截至</option>
				<option value="03">下架</option>
			</select>
			<gd:btn btncode='BTNGGGLGGGL05'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search1">查询</a></gd:btn>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNGGGLGGGL01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
			<gd:btn btncode='BTNGGGLGGGL02'><a class="easyui-linkbutton icon-remove-btn" iconCls="icon-remove" id="icon-remove">删除</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="checkadd()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="editDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="checkedit()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="detailDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="#dlg-buttonsDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="proDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsPro">
			<div id="#dlg-buttonsPro" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#proDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNGGGLGGGL04'><a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a></gd:btn>&nbsp;" +
	"<gd:btn btncode='BTNGGGLGGGL03'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a></gd:btn>&nbsp;" +
	"<gd:btn btncode='BTNGGGLGGGL02'><a class='operate' href='javascript:;' onclick=delObj('"+row.id+"');>删除</a></gd:btn>";
}


$(document).ready(function(){
	initList();
	//删除多个操作
	$("#icon-remove").click(function(){
		var row = $('#adinfodg').datagrid("getSelections");
	    if($(row).length < 1 ) {
	    	warningMessage("请选择要删除的数据！");
	        return ;
	    } else if ($(row).length == 1){
	    	var id = $(row)[0].id ;
	    	delObj(id);
	    } else {
	    	var deleteStr = getSelections("id");
	    	delsObj(deleteStr);
	    }
	});
	
	//查询按钮
	$('#icon-search1').click(function(){
		var params = {
			"startDate" : $("#startDate").val(),
			"endDate" : $("#endDate").val(),
			"adName" : $("#adNameList").val(),
			"adCanal" : $("#adCanalList").val(),
			"marketId" : $("#marketIdList").val(),
			"state" : $("#stateList").val(),
			"adType" : $("#adTypeList").val()
			};
		loadList(params);
	});
	
	//新增
	$('#icon-add').click(function(){
		$('#addDialog').dialog({'title':'新增数据','href':CONTEXT+'push/adInfoAdd', 'width': 800,'height': 500}).dialog('open');
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#adinfoSearchForm')[0].reset();
		$("#adinfodg").datagrid('load', {});
	});
	
	//重置按钮
	$('#icon-reload').click(function(){
		$('#adinfoSearchForm')[0].reset();
	});
});
</script>
</html>