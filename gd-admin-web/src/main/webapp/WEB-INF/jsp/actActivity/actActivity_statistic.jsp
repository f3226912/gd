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
		<title>活动统计</title>
	</head>
	<body>
		<table id="actActivitydg" title=""></table>
		<div id="actActivitytb" style="padding:10px;height:auto">
			<form id="actActivitySearchForm" method="post">
				<div>
					<label>创建时间：
						<input type="text" name="startDate" id="startDate" onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/> - 
						<input type="text" name="endDate" id="endDate" onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" >
					</label>
					<label>活动状态：
						<select name="launch">
							<option value="">请选择活动状态</option>
							<option value="0">结束</option>
							<option value="1">开启</option>
						</select>
					</label>
					<gd:btn btncode='BTNWXHDTJ01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a></gd:btn>
					<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
				</div>
				<div style="margin-top:10px">
					<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
					<gd:btn btncode='BTNWXHDTJ02'><a class="easyui-linkbutton" iconCls="icon-save" id="btn-export">导出数据</a></gd:btn>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
		var disableExport = false;
		function loadList(params){
			params = !params ? {}: params;
			//数据加载
			$('#actActivitydg').datagrid({
				url:CONTEXT+'activityStatistic/query',
				queryParams: params,
				height: 'auto', 
				nowrap:true,
				toolbar:'#actActivitytb',
				pageSize:50,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				onLoadSuccess:function(){
					$("#actActivitydg").datagrid('clearSelections');
				},
				columns:[[
					{field:'id',title:'活动编号',width:50,align:'center'},
					{field:'name',title:'活动名称',width:100,align:'center'},
					{field:'pv',title:'总浏览数',width:100,align:'center',formatter:pvFormatter},
					{field:'joinCount',title:'总参与数',width:100,align:'center'},
					{field:'shareCount',title:'总分享数',width:100,align:'center'},
					{field:'effectiveStartTime',title:'活动开始时间',width:100,align:'center'},
					{field:'effectiveEndTime',title:'活动结束时间',width:100,align:'center'},
					{field:'launch',title:'活动状态',width:100,align:'center',formatter:launchFormatter}
					
				]]
			}); 
		}
		function launchFormatter(val, row, index){
			if(val == ''){
				return "";
			}
			if(val == '0'){
				return "结束";
			}
			if(val == '1'){
				return "开启";
			}
		}
		function pvFormatter(val, row, index){
			if(val == undefined || val == ''){
				return '0';
			}
			return val;
		}
		function initList(){
			loadList(null);
			//分页加载
			$("#actActivitydg").datagrid("getPager").pagination({
				pageList: [10,20,50,100]
			});
		}
		jQuery.download = function(url, data, method){
			// 获得url和data
		    if( url && data ){
		        // data 是 string或者 array/object
		        data = typeof data == 'string' ? data : jQuery.param(data);
		        // 把参数组装成 form的  input
		        var inputs = '';
		        jQuery.each(data.split('&'), function(){
		            var pair = this.split('=');
		            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
		        });
		        // request发送请求
		        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
		        	.appendTo('body').submit().remove();
		    };
		};
		$(document).ready(function(){
			initList();	
			//刷新
			$('#icon-refresh').click(function(){
				disableExport = false;
				$('#actActivitySearchForm')[0].reset();
				$("#actActivitydg").datagrid('load', {});
			});
			
			//重置按钮
			$('#icon-reload').click(function(){
				$('#actActivitySearchForm')[0].reset();
			});
			//查询
			$('#icon-search').click(function(){
				var params = {
					"launch":$("#actActivitySearchForm select[name='launch']").val(),
					"startDate":$("#actActivitySearchForm #startDate").val(),
					"endDate":$("#actActivitySearchForm #endDate").val()
				};
				loadList(params);
			});
			//导出
			$('#btn-export').click(function(){
				var queryParams = {
					"launch":$("#actActivitySearchForm select[name='launch']").val(),
					"startDate":$("#actActivitySearchForm #startDate").val(),
					"endDate":$("#actActivitySearchForm #endDate").val()
				};
				var paramList = "launch="+queryParams.launch+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate;
				
				$.ajax({
					url: CONTEXT+'activityStatistic/checkExportParams',
					data : queryParams,
					type:'post',
					success : function(data){
						//检测通过
						if (data && data.status == 1){
							if (!disableExport){
								slideMessage("数据正在导出中, 请耐心等待...");
								disableExport = true ;
								//启动下载
								$.download(CONTEXT+'activityStatistic/exportData',paramList,'post');
							}else{
								slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
							}
						}else{
							warningMessage(data.message);
						}
					},
					error : function(data){
						warningMessage(data);
					}
				});
			});
		});
	</script>
</html>