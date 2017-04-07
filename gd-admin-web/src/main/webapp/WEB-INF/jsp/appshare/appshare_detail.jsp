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
		<title>APP分享记录</title>
	</head>
	
<body>
<table id="appshareDetaildg" title=""></table>
		<script type="text/javascript">
				var disableExport = false;
				function loadList_info(){
					params = {"memberId":'${memberId}'};
					//数据加载
					$('#appshareDetaildg').datagrid({
						url:CONTEXT+'appshare/queryDetail',
						queryParams: params,
						height: 'auto', 
						nowrap:true,
						pageSize:50,
						rownumbers:true,
						pagination:true,
						fitColumns:true,
						fit:true,
						onLoadSuccess:function(){
							$("#appshareDetaildg").datagrid('clearSelections');
						},
						columns:[[
							//{field:'shareId',title:'',width:0,checkbox:false},
							{field:'account',title:'用户账号',width:100,align:'center'},
							{field:'realName',title:'真实姓名',width:100,align:'center'},
							{field:'mobile',title:'手机号码',width:100,align:'center' },
							{field:'shareDate',title:'分享时间',width:100,align:'center'},
							{field:'marketName',title:'推广链接所属市场',width:100,align:'center'},
							{field:'viewCount',title:'访问次数',width:80,align:'center'}
							
						]]
					}); 
				}
				
				function initList_info(){
					loadList_info();
					//分页加载
					$("#appshareDetaildg").datagrid("getPager").pagination({
						pageList: [10,20,50,100]
					});
				}
				$(function(){
					initList_info();
				});
			</script>
</body>	
</html>