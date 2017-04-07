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
		<title>活动礼品</title>
	</head>
	<body>
		<table id="actGiftdg" title=""></table>
		<div id="actGifttb" style="padding:10px;height:auto">
			<form id="actGiftSearchForm" method="post">
				<div>
					<label>礼品名称：<input type="text" name="name" id="name"/></label>
					<label>礼品添加时间：
						<input type="text" name="startDate" id="startDate" onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/> - 
						<input type="text" name="endDate" id="endDate" onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" >
					</label>
					<gd:btn btncode='BTNWXLPKGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a></gd:btn>
					<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
				</div>
				<div style="margin-top:10px">
					<gd:btn btncode='BTNWXLPKGL02'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add">新增</a></gd:btn>
					<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
					<gd:btn btncode='BTNWXLPKGL03'><a class="easyui-linkbutton" iconCls="icon-save" id="btn-export">导出数据</a></gd:btn>
				</div>
			</form>
		</div>
		<div id="addActGiftDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveAdd()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addActGiftDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="editActGiftDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveEdit()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editActGiftDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="viewActGiftDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsView">
			<div id="dlg-buttonsView" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#viewActGiftDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</body>
	<script type="text/javascript">
		var disableExport = false;
		function loadList(params){
			params = !params ? {}: params;
			//数据加载
			$('#actGiftdg').datagrid({
				url:CONTEXT+'actGift/query',
				queryParams: params,
				height: 'auto', 
				nowrap:true,
				toolbar:'#actGifttb',
				pageSize:50,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				onLoadSuccess:function(){
					$("#actGiftdg").datagrid('clearSelections');
				},
				columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'礼品名称',width:100,align:'center'},
					{field:'stockTotal',title:'当前库存',width:100,align:'center' },
					{field:'stockAvailable',title:'可用库存',width:100,align:'center'},
					{field:'createTime',title:'创建时间',width:100,align:'center'},
					{field:'createUserName',title:'添加人',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
			}); 
		}
		function optformat(value,row,index){
			return "<gd:btn btncode='BTNWXLPKGL04'><a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a></gd:btn>&nbsp;" +
			"<gd:btn btncode='BTNWXLPKGL05'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a></gd:btn>&nbsp;" +
			"<gd:btn btncode='BTNWXLPKGL06'><a class='operate' href='javascript:;' onclick=deleteObj('"+row.id+"');>删除</a></gd:btn>";
		}
		
		function initList(){
			loadList(null);
			//分页加载
			$("#actGiftdg").datagrid("getPager").pagination({
				pageList: [10,20,50,100]
			});
		}
		
		function saveAdd(){
			if(!$("#addActGiftForm").form('validate')){
				return false;
			}
			$.ajax({
				type : "post",
				url : CONTEXT+"actGift/saveAdd",
				data : $("#addActGiftForm").serialize(),
				dataType : "json",
				success : function(data){
					if(data.status == 0){
						slideMessage("操作成功！");
						//刷新父页面列表
						$("#actGiftdg").datagrid('reload');
						$('#addActGiftDialog').dialog('close');
					}else{
						warningMessage(data.msg);
					}
				}
			});
		}
		function saveEdit(){
			if(!$("#editActGiftForm").form('validate')){
				return false;
			}
			$.ajax({
				type : "post",
				url : CONTEXT+"actGift/saveEdit",
				data : $("#editActGiftForm").serialize(),
				dataType : "json",
				success : function(data){
					if(data.status == 0){
						slideMessage("操作成功！");
						//刷新父页面列表
						$("#actGiftdg").datagrid('reload');
						$('#editActGiftDialog').dialog('close');
					}else{
						warningMessage(data.msg);
					}
				}
			});
		}
		function editObj(id){
			$('#editActGiftDialog').dialog({'title':'编辑数据','href':CONTEXT+'actGift/edit/'+id, 'width': 500,'height': 300}).dialog('open');
		}
		function detailObj(id){
			$('#viewActGiftDialog').dialog({'title':'查看数据','href':CONTEXT+'actGift/view/'+id, 'width': 500,'height': 300}).dialog('open');
		}
		function deleteObj(id){
			jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
				if (r){
		    		jQuery.post(CONTEXT+"actGift/delete/"+id,{},function(data){
						if (data.status == 0){
							slideMessage("操作成功！");
							$("#actGiftdg").datagrid('reload');
						}else{
							warningMessage(data.msg);
							return;
						}
		    		});
				}
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
			$.extend($.fn.validatebox.defaults.rules, {  
				stock: {  
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
				$('#addActGiftDialog').dialog({'title':'新增数据','href':CONTEXT+'actGift/add', 'width': 500,'height': 300}).dialog('open');
			});
			//刷新
			$('#icon-refresh').click(function(){
				disableExport = false;
				$('#actGiftSearchForm')[0].reset();
				$("#actGiftdg").datagrid('load', {});
			});
			
			//重置按钮
			$('#icon-reload').click(function(){
				$('#actGiftSearchForm')[0].reset();
			});
			//查询
			$('#icon-search').click(function(){
				var params = {
					"name":$("#actGiftSearchForm #name").val(),
					"startDate":$("#actGiftSearchForm #startDate").val(),
					"endDate":$("#actGiftSearchForm #endDate").val()
				};
				loadList(params);
			});
			//导出
			$('#btn-export').click(function(){
				var queryParams = {
					"name":$("#actGiftSearchForm #name").val(),
					"startDate":$("#actGiftSearchForm #startDate").val(),
					"endDate":$("#actGiftSearchForm #endDate").val()
				};
				var paramList = "name="+queryParams.name+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate;
				
				$.ajax({
					url: CONTEXT+'actGift/checkExportParams',
					data : queryParams,
					type:'post',
					success : function(data){
						//检测通过
						if (data && data.status == 1){
							if (!disableExport){
								slideMessage("数据正在导出中, 请耐心等待...");
								disableExport = true ;
								//启动下载
								$.download(CONTEXT+'actGift/exportData',paramList,'post');
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