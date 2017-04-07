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
		<title>促销商品列表</title>
	</head>
	<body>
		<table id="spdg" title=""></table>
		<div id="sptb" style="padding:10px;height:auto">
			<a class="easyui-linkbutton" iconCls="icon-save" id="btn-exportSupplier">导出EXCEL</a>
		</div>
		<div style="display:none">
			<iframe id="if1" src=""></iframe>
		</div>
		<script type="text/javascript">
		$(function(){
			$('#spdg').datagrid({
				url:CONTEXT+'active/querySuppler?actId='+$.trim($("#actId").val()),
				queryParams: {},
				height: 'auto', 
				nowrap:true,
				toolbar:'#sptb',
				pageSize:50,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				onLoadSuccess:function(){
					$("#activedg").datagrid('clearSelections');
				},
				columns:[[
					{field:'mobile',title:'手机',width:50,align:'center'},
					{field:'supplierName',title:'姓名',width:100,align:'center'},
					{field:'supplierType',title:'用户类型',width:100,align:'center', formatter:userFormatter},
					{field:'createTime',title:'报名时间',width:100,align:'center'}
				]]
			}); 
			var userTypes = {"1":"谷登农批","2":"农速通","3":"农商友","4":"产地供应商","5":"门岗"};
			function userFormatter(value,row,index){
				return userTypes[row.supplierType]||"";
			}
			
			$("#btn-exportSupplier").click(function(){
				//绑定导出事件
				if(flag == 1){
					alert("没有数据");
				} else {
					$("#if1").attr("src",CONTEXT+"active/exportSupplier?actId="+$.trim($("#actId").val()));
				}
			});
			
		});
		
		</script> 
	</body>
</html>