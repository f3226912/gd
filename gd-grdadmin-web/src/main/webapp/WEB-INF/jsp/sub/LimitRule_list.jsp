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
		<title>limitrule</title>
		<script type="text/javascript" src="${CONTEXT}js/sub/limitrulelist.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
	</head>
<body>

	<table id="limitruleList" title="">
	</table>
	
	<div id="limitruletb" style="padding:5px;height:auto">
		<form id="limitruleListForm" method="post">
		<div>
					市场名称: 
			<input  type="text" id="market"    class="easyui-validatebox"    name="market" style="width:150px" >
					总补贴额: 
			<input  type="text" id="subAmount"    class="easyui-validatebox"    name="subAmount" style="width:150px" >			
			按时间搜索：
			<input  type="text"  id="startDate" name="timeStart"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endDate" name="timeEnd"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">  
			活动状态: 
			<select name="status" id="status" class="u132">
				<option selected="" value="">全部</option>
				<option value="1">启用</option>
				<option value="0">禁用</option>
			</select>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#limitruleListForm').form('validate');">查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode="BTNBTGZGLBTXZGZ04"><a class="easyui-linkbutton" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>
		
<!-- 		<div id="addDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="smit();">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('destroy').remove()">关闭</a>
	        </div>
		</div> -->	
<div id="addLimitRuleDialog" class="easyui-window" closed="true" modal="true" title="添加限制规则" style="width:1100px;height:500px;">
    <iframe scrolling="auto" id='newLimitRuleIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
</div>		

<div id="detailDialog" class="easyui-window" closed="true" modal="true" title="查看限制规则" style="width:1100px;height:500px;">
    <iframe scrolling="auto" id='detailLimitRuleIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
</div>

<div id="editDialog" class="easyui-window" closed="true" modal="true" title="编辑限制规则" style="width:1100px;height:500px;">
    <iframe scrolling="auto" id='editLimitRuleIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
</div>
</div> 


</body>
<script type="text/javascript">


var formatName = function(value,row){
	var html='<gd:btn btncode="BTNBTGZGLBTXZGZ04"><a href="javascript:detailObj('+row.ruleId+')">'+value+'</a></gd:btn>';
	if(html==''){
		return value;
	}else{
		return html;
	}
};
$(document).ready(function(){


	//$('#openRoleDiv').dialog('open');	
initList();

	//查询按钮
	$('#icon-search').click(function(){
		var params = {
			"market" : $("#market").val(),
			"timeStart" : $("#startDate").val(),
			"timeEnd":$("#endDate").val(),
			"subAmount":$("#subAmount").val(),
			"status":$("#status").val()	
			};
		loadList(params);
	});
	
	//重置按钮
	$('#btn-reset').click(function(){
		$('#limitruleListForm')[0].reset();
	});
	
	var dialogOwn = $('<div id="addDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">'+
			'<div id="dlg-buttonsAdd" style="text-align:center">'+
        	'<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:\'icon-save\'" id="icon-save-btn" onclick="smit();">保存</a>'+
            '<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$(\'#addDialog\').dialog(\'destroy\').remove()">关闭</a>'+
            '</div>'+
	'</div>')
	
	//新增
	$('#icon-add').click(function(){
		//var dialogParent = $(this).parent();  
         	$('#newLimitRuleIframe')[0].src=CONTEXT+'/sublimitrule/init?t='+(new Date()).getTime();
		$('#addLimitRuleDialog').dialog('open');
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		//$('#adinfoSearchForm')[0].reset();
		$("#limitruleList").datagrid('load', {});
	});
	
	
	
	
});
</script>
</html>