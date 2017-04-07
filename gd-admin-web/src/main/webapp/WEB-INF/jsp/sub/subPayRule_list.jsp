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
		<script type="text/javascript" src="${CONTEXT}js/sub/payrulelist.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
		<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/zTree/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/zTree/demo.css">
<script type="text/javascript" src="${CONTEXT}/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${CONTEXT}js/sub/addRule.js"></script>
	</head>
<body>
	<table id="ruleList" title="">
	</table>
	<div id="ruletb" style="padding:5px;height:auto">
		<form id="ruleListForm" method="post">
		<div>
<!-- 			推送日期：
			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px">  -->
			商品类目: 
			<input  type="text" id="categroy"    class="easyui-validatebox"    name="categroy" style="width:150px" >
			<gd:btn btncode='BTNBTGZGLBTXTSHGZGL07'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#ruleListForm').form('validate');">查询</a>&nbsp;&nbsp;</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			<gd:btn btncode='BTNBTGZGLBTXTSHGZGL01'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add" >新增</a></gd:btn>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</div>
		
<!-- 		<div id="addDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="smit();">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addDialog').dialog('destroy').remove()">关闭</a>
	        </div>
		</div> -->	
<div id="openRoleDiv" class="easyui-window" closed="true" modal="true" title="添加规则" style="width:1100px;height:500px;">
    <iframe scrolling="auto" id='newRuleIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
 
</div>		

<div id="copyDialog" class="easyui-window" closed="true" modal="true" title="复制规则" style="width:1100px;height:500px;">
    <iframe scrolling="auto" id='copyRuleIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
 
</div>

<div id="editDialog" class="easyui-window" closed="true" modal="true" title="编辑规则" style="width:1100px;height:500px;">
    <iframe scrolling="auto" id='editRuleIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
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
var optfun = function(value,row,index){
	var str = row.status;
		if(str=="0"){//禁用
			return '<gd:btn btncode="BTNBTGZGLBTXTSHGZGL06"><a href="javascript:void(0)" class="hrefbutton" onclick="modifyStatus('+row.ruleId+','+1+')">启用</a></gd:btn>';
		}else if(str=="1"){//启用
			return '<gd:btn btncode="BTNBTGZGLBTXTSHGZGL02"><a href="javascript:void(0)" class="hrefbutton" onclick="modifyStatus('+row.ruleId+','+0+')">禁用</a></gd:btn>';
		}else if(str=="2"){//过期
			return '<gd:btn btncode="BTNBTGZGLBTXTSHGZGL04"><a href="javascript:void(0)" class="hrefbutton" onclick="detailObj('+row.ruleId+')">查看</a></gd:btn>';
		}else if(str=="3"){//排队
			return '<gd:btn btncode="BTNBTGZGLBTXTSHGZGL03"><a href="javascript:void(0)" class="hrefbutton" onclick="editRule('+row.ruleId+')">编辑</a></gd:btn><span style="margin-left:10px"></span><gd:btn btncode="BTNBTGZGLBTXTSHGZGL02"><a href="javascript:void(0)" class="hrefbutton" onclick="modifyStatus('+row.ruleId+','+0+')">禁用</a></gd:btn>';
		}
};

var formatName = function(value,row){
	var html='<gd:btn btncode="BTNBTGZGLBTXTSHGZGL04"><a href="javascript:detailObj('+row.ruleId+')">'+value+'</a></gd:btn>';
	if(html==''){
		return value;
	}else{
		return html;
	}
};
$(document).ready(function(){


	//$('#openRoleDiv').dialog('open');	
	initList();
	$("body").delegate(".rulesel","change",function(){
		var ruleId = $(this).attr("rid");
		var myval = $(this).val(); 
		//alert(ruleId);
		//alert(myval);
		if(myval<0){
			return false;
			}
		if(confirm("是否要更改该条补贴规则的状态？")){
 			var url=CONTEXT+"/subpayrule/updateRuleStatus";
 			jQuery.post(url, {ruleId:ruleId,status:myval}, function (data) {
 				var obj = jQuery.parseJSON(data);
 				if (obj.result*1 > 0) {
 					slideMessage("更改成功！");
 					//刷新父页面列表
 					$("#ruleList").datagrid('load', {});
 				}else {
 					warningMessage("更改失败！");
 					return;
 				}
 			});
		}else{
			$(".rulesel option[value='-1']").attr("selected", true);
		}
		
	})

	//查询按钮
	$('#icon-search').click(function(){
		var params = {
			"categroy" : $("#categroy").val(),
			"search" : 1
			};
		loadList(params);
	});
	
	//重置按钮
	$('#btn-reset').click(function(){
		$('#ruleListForm')[0].reset();
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
         	$('#newRuleIframe')[0].src=CONTEXT+'/subpayrule/addRule/1?t='+(new Date()).getTime();
		$('#openRoleDiv').dialog('open');
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		//$('#adinfoSearchForm')[0].reset();
		$("#ruleList").datagrid('load', {});
	});
	
	
	
	
});
</script>
</html>