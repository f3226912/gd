<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
	<base href="${CONTEXT }">
		<%@ include file="../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<title>报表管理</title>
	</head>

	<script>
		$(document).ready(function(){
			loadData(null);
			$('#icon-search').click(function(){
				var dataParams = {'name': $('#name').val(), 'menuId':$('#reportsClass').val()};
				loadData(dataParams);
			});
			$('#icon-reset').click(function(){
				$('#searchForm')[0].reset();
			});
			//刷新按钮
			$('#icon-refresh').click(function(){
				$('#searchForm')[0].reset();
				$("#dg").datagrid('load', {});
			});
		});
		function loadData(dataParams){
			//数据加载
			$('#dg').datagrid({
	    		url:CONTEXT+'reports/query',
	    		queryParams : dataParams,
				width: 1000,  
	    		height: 'auto', 
				nowrap:true,
				toolbar:'#tb',
				pageSize:20,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				remoteSort:false,//页面排序必须加
				onLoadSuccess:function(){
					$("#dg").datagrid('clearSelections');
				},
				columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'报表名称',width:100,align:'center'},
					{field:'menuName',title:'报表分类',width:100,align:'center'},
					{field:'type',title:'选用图表类型',width:100,align:'center', formatter:typeFormatter},
					{field:'state',title:'状态',width:100,align:'center',formatter:stateFormatter},
					{field:'opt',title:'操作',width:100,align:'center', formatter:optFormatter}
				]]
			}); 
			//分页加载
			$("#dg").datagrid("getPager").pagination({
				pageList: [20,50,100,200]
			}); 
		}
		function stateFormatter(value,row,index){
			var result;
			if(value == 1){
				result = '启用';
			}
			else if(value == 2){
				result = '禁用';
			}
			return result;
		}
		function typeFormatter(value, row, index){
			var result;
			if(value == 1){
				result = "折线图";
			}
			else if(value == 2){
				result = "叠加条形图";
			}
			else if(value == 3){
				result = "折线+条形";
			}
			else if(value == 4){
				result = "多条折线";
			}
			else if(value == 5){
				result = "多条条形图";
			}
			else if(value == 6){
				result = "堆砌条形图";
			}
			else if(value == 7){
				result = "堆砌条形图";
			}
			return result;
		}
		function optFormatter(value,row,index){
			var opt = "";
			if(row.state == 1){
				opt = "<a class='operate' href='javascript:;' onclick='updateState("+row.id+", 2);'>禁用</a>";
			}
			else if(row.state == 2){
				opt = "<a class='operate' href='javascript:;' onclick='updateState("+row.id+", 1);'>启用</a>";
			}
			opt += '<a class="operate" href="javascript:;" onclick="viewDetail('+row.id+',\''+row.name+'\')">查看</a>';
			return opt;
		}
		function updateState(id, state){
			var tip;
			if(state == 1){
				tip = '您确定要启用所选数据吗?';
			}
			else if(state == 2){
				tip = '您确定要禁用所选数据吗?';
			}else{
				tip = '您确定要操作所选数据吗?';
			}
			jQuery.messager.confirm('提示', tip, function(r){
    			if (r){
    				$.ajax({
    					 type: 'POST',
    				     url:CONTEXT+'reports/updateState' ,
    				     data: {'id':id, 'state':state},
    				     dataType:'json',
    				     success: function(data) {
    				     	if(data.resultCode == 0){
    				        	slideMessage('操作成功！');
    				        	$('#dg').datagrid('reload');
    				        }else{
    				        	warningMessage('操作失败');
    				        }
    				     }   
    				});
    			}
			});
		}
		function addTab(title,url){    
            var jq = top.jQuery;    
            if (jq("#my_tabs").tabs('exists', title)){    
                jq("#my_tabs").tabs('select', title);    
            } else {  
                var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';     
                jq("#my_tabs").tabs('add',{title:title,content:content,closable:true});    
            }    
        } 
		function viewDetail(id, title){
		 	var url=CONTEXT+'reports/detail?reportsID='+id;
		 	addTab(title, url);
		}
    </script>
<body>
	<table id="dg" title=""></table>
	<div id="tb" style="padding:5px;height:auto">
		<form id="searchForm" method="post">
			报表名称：
			<input type="text" name="name" id="name" style="width:150px"/>
	       	<select id="reportsClass" name="reportsClass" editable="false" panelHeight="auto" style="width:100px">
				<option value="">选择报表分类</option>
				<c:forEach items="${menuList}" var="menu" varStatus="s">
					<option value="${menu.menuID }">${menu.menuName }</option>
				</c:forEach>
			</select>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reset">重置</a>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-refresh">刷新</a>
		</form>
	</div>
	<!-- 用户所属组织 -->
	<div id="detailDialog" class="easyui-dialog" style="width:700px;height:600px;" closed="true" modal="true" buttons="#dlg-buttonsOrg">
		<div id="dlg-buttonsOrg" style="text-align:center">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDialog').dialog('close')">关闭</a>
        </div>
	</div>
</body>
</html>