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
		<title>积分获取记录</title>
	</head>
	<body>
		<table id="actIntegraldg" title=""></table>
		<div id="actIntegraltb" style="padding:10px;height:auto">
			<form id="actIntegralSearchForm" method="post">
				<div>
					<label>会员账号：<input type="text" name="account" id="account"/></label>
					<label>手机号码：<input type="text" name="mobile" id="mobile"/></label>
					<label>活动名称：<input type="text" name="avtivityName" id="activityName"/></label>
					<label>积分获取时间：
						<input type="text" name="startDate" id="startDate" onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/> - 
						<input type="text" name="endDate" id="endDate" onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" >
					</label></br></br>
					<label>积分来源：<select name="type" id="type">
					<option value="">积分来源类型</option>
					<option value="1">首登积分</option>
					<option value="2">分享积分</option>
					<option value="3">点赞积分</option>
					<option value="4">注册积分</option>
					<option value="5">金牌供应商积分</option>
					</select></label>
					<gd:btn btncode='BTNWXJFJLGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a></gd:btn>
					<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
				</div>
				<div style="margin-top:10px">
					<gd:btn btncode='BTNWXJFJLGL02'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add">新增</a></gd:btn>
					<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
					<gd:btn btncode='BTNWXJFJLGL03'><a class="easyui-linkbutton" iconCls="icon-save" id="btn-export">导出数据</a></gd:btn>
				</div>
			</form>
		</div>
		<div id="addIntegralDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveAdd()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addIntegralDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="editIntegralDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveEdit()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editIntegralDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="detailIntegralDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="dlg-buttonsDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailIntegralDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</body>
	<script type="text/javascript">
		var disableExport = false;
		function loadList(params){
			params = !params ? {}: params;
			//数据加载
			$('#actIntegraldg').datagrid({
				url:CONTEXT+'activityintegral/query',
				queryParams: params,
				height: 'auto', 
				nowrap:true,
				toolbar:'#actIntegraltb',
				pageSize:50,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				onLoadSuccess:function(){
					$("#actIntegraldg").datagrid('clearSelections');
				},
				columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'account',title:'会员账号',width:100,align:'center'},
					{field:'mobile',title:'手机号码',width:100,align:'center'},
					{field:'activityId',title:'活动编号',width:100,align:'center'},
					{field:'activityName',title:'活动名称',width:100,align:'center'},
					{field:'score',title:'所获积分',width:100,align:'center'},
					{field:'typeValue',title:'积分来源',width:100,align:'center'},
					
					{field:'receiveTime',title:'积分获取时间',width:100,align:'center'},
					{field:'createUserName',title:'创建人',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
			}); 
		}
		function initList(){
			loadList(null);
			//分页加载
			$("#actIntegraldg").datagrid("getPager").pagination({
				pageList: [10,20,50,100]
			});
		}
		function optformat(value,row,index){
			var opt = "<gd:btn btncode='BTNWXJFJLGL04'><a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a></gd:btn>&nbsp;" +
			"<gd:btn btncode='BTNWXJFJLGL05'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a></gd:btn>&nbsp;";
			return opt;
		}
		function saveAdd(){
			if($("#addActIntegralForm #memberId").val() == ''){
				warningMessage("请选择用户！");
				return false;
			}
			if(!$("#addActIntegralForm").form('validate')){
				return false;
			}
			$.ajax({
				type : "post",
				url : CONTEXT+"activityintegral/saveAdd",
				data : $("#addActIntegralForm").serialize(),
				dataType : "json",
				success : function(data){
					if(data.status == 0){
						slideMessage("操作成功！");
						//刷新父页面列表
						$("#actIntegraldg").datagrid('reload');
						$('#addIntegralDialog').dialog('close');
					}else{
						warningMessage(data.msg);
					}
				}
			});
		}
		function editObj(id){
			$('#editIntegralDialog').dialog({'title':'编辑数据','href':CONTEXT+'activityintegral/edit/'+id, 'width': 500,'height': 300}).dialog('open');
		}
		
		function saveEdit(){
			if(!$("#editIntegralDialog #addActIntegralForm").form('validate')){
				return false;
			}
			$.ajax({
				type : "post",
				url : CONTEXT+"activityintegral/saveEdit",
				data : $("#editIntegralDialog #addActIntegralForm").serialize(),
				dataType : "json",
				success : function(data){
					if(data.status == 0){
						slideMessage("操作成功！");
						//刷新父页面列表
						$("#actIntegraldg").datagrid('reload');
						$('#editIntegralDialog').dialog('close');
					}else{
						warningMessage(data.msg);
					}
				}
			});
		}
		function detailObj(id){
			$('#detailIntegralDialog').dialog({'title':'查看数据','href':CONTEXT+'activityintegral/view/'+id, 'width': 500,'height': 300}).dialog('open');
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
			$.extend($.fn.validatebox.defaults.rules, {  
				score: {  
			        validator: function(value){
			        	if(value == ''){
			        		return false;
			        	}
			       		if(!/^\d{1,6}?$/.test(value)){
			       			return false;	
			       		}
			        	return true;
			        }
			    }
			});
			initList();
			//新增
			$('#icon-add').click(function(){
				$('#addIntegralDialog').dialog({'title':'新增数据','href':CONTEXT+'activityintegral/add', 'width': 500,'height': 300}).dialog('open');
			});
			//重置按钮
			$('#icon-reload').click(function(){
				$('#actIntegralSearchForm')[0].reset();
			});
			//刷新
			$('#icon-refresh').click(function(){
				disableExport = false;
				$('#actIntegralSearchForm')[0].reset();
				$("#actIntegraldg").datagrid('load', {});
			});
			//查询
			$('#icon-search').click(function(){
				var params = {
					"account":$("#actIntegralSearchForm #account").val(),
					"mobile":$("#actIntegralSearchForm #mobile").val(),
					"activityName":$("#actIntegralSearchForm #activityName").val(),
					"type":$("#actIntegralSearchForm #type").val(),
					"startDate":$("#actIntegralSearchForm #startDate").val(),
					"endDate":$("#actIntegralSearchForm #endDate").val()
				};
				
				loadList(params);
			});
			//导出
			$('#btn-export').click(function(){
				var queryParams = {
					"account":$("#actIntegralSearchForm #account").val(),
					"mobile":$("#actIntegralSearchForm #mobile").val(),
					"activityName":$("#actIntegralSearchForm #activityName").val(),
					"startDate":$("#actIntegralSearchForm #startDate").val(),
					"endDate":$("#actIntegralSearchForm #endDate").val(),
					"type":$("#actIntegralSearchForm #type").val()

				};
				var paramList = "account="+queryParams.account+
					"&mobile="+queryParams.mobile+
					"&type="+queryParams.type+
					"&activityName="+queryParams.activityName+
					"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate;
				
				$.ajax({
					url: CONTEXT+'activityintegral/checkExportParams',
					data : queryParams,
					type:'post',
					success : function(data){
						//检测通过
						if (data && data.status == 1){
							if (!disableExport){
								slideMessage("数据正在导出中, 请耐心等待...");
								disableExport = true ;
								//启动下载
								$.download(CONTEXT+'activityintegral/exportData',paramList,'post');
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