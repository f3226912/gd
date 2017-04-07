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
		<title>member</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="giftdg" title="">
	</table>
	<div id="gifttb" style="padding:5px;height:auto">
		<form id="giftForm" method="post">
			<div>
				礼品名称: 
				<input  type="text" id="giftName"    class="easyui-validatebox"    name="giftName" style="width:150px" >
				活动名称: 
				<input  type="text" id="actName"    class="easyui-validatebox"    name="actName" style="width:150px" >
				<gd:btn btncode='BTNLPGLLPXX01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#giftForm').form('validate');"    >查询</a></gd:btn>&nbsp;&nbsp;
				<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNLPGLLPXX02'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
		
 			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>
		
		<div id="addDialog" class="easyui-dialog" style="width:1000px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		
<!-- 		<div id="editDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit"> -->
<!-- 			<div id="dlg-buttonsEdit" style="text-align:center"> -->
<!-- 	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">保存</a> -->
<!-- 	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a> -->
<!-- 	        </div> -->
<!-- 		</div> -->

			<div id="activityDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsActivity">
			<div id="#dlg-buttonsActivity" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-activityr-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#activityDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNLPGLLPXX03'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a></gd:btn>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/gift/main.js"></script>

<script type="text/javascript">

//刷新按钮
$('#icon-refresh').click(function(){
	$('#giftForm')[0].reset();
	$("#giftdg").datagrid('load', {});
});
</script>

</html>

