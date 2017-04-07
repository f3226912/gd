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
		<title>礼品兑换记录</title>
	</head>
	<body>
		<table id="actGiftExchangedg" title=""></table>
		<div id="actGiftExchangetb" style="padding:10px;height:auto">
			<form id="actGiftExchangeSearchForm" method="post">
				<div>
					<label>会员账号：<input type="text" name="account" id="account"/></label>
					<label>手机号码：<input type="text" name="mobile" id="mobile"/></label>
					<label>活动名称：<input type="text" name="activityName" id="activityName"/></label>
					<label>礼品名称：<input type="text" name="giftName" id="giftName"/></label>
				</div>
				<div style="margin-top:10px">
					<label>创建时间：
						<input type="text" name="startDate" id="startDate" onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/> - 
						<input type="text" name="endDate" id="endDate" onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" >
					</label>
					<label>发放状态：
						<select name="status" id="status">
							<option value="">请选择</option>
							<option value="1">未发</option>
							<option value="2">已发</option>
							<option value="3">不予发放</option>
						</select>
					</label>
					<gd:btn btncode='BTNWXLPDHGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a></gd:btn>
					<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
				</div>
				<div style="margin-top:10px">
					<gd:btn btncode='BTNWXLPDHGL02'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add">新增</a></gd:btn>
					<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
					<gd:btn btncode='BTNWXLPDHGL03'><a class="easyui-linkbutton" iconCls="icon-save" id="btn-export">导出数据</a></gd:btn>
				</div>
			</form>
		</div>
		<div id="addActGiftExchangeDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveAdd()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addActGiftExchangeDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="editActGiftExchangeDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveEdit()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editActGiftExchangeDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="detailActGiftExchangeDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="dlg-buttonsDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailActGiftExchangeDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</body>
	<script type="text/javascript">
		var disableExport = false;
		function loadList(params){
			params = !params ? {}: params;
			//数据加载
			$('#actGiftExchangedg').datagrid({
				url:CONTEXT+'actGiftExchange/query',
				queryParams: params,
				height: 'auto', 
				nowrap:true,
				toolbar:'#actGiftExchangetb',
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
					{field:'account',title:'会员账号',width:100,align:'center'},
					{field:'mobile',title:'手机号码',width:100,align:'center' },
					{field:'activity_id',title:'活动编号',width:100,align:'center'},
					{field:'activityName',title:'活动名称',width:100,align:'center'},
					{field:'giftName',title:'礼品名称',width:100,align:'center'},
					{field:'status',title:'礼品发放状态',width:100,align:'center', formatter:statusFormat},
					{field:'send_time',title:'礼品发放时间',width:100,align:'center'},
					{field:'createTime',title:'创建时间',width:100,align:'center'},
					{field:'createUserName',title:'创建人',width:80,align:'center'},
					{field:'opt',title:'操作',width:150,align:'center',formatter:optformat}
				]]
			}); 
		}
		function statusFormat(val, row, index){
			if(val == ''){
				return "";
			}
			if(val == "1"){
				return "未发";
			}
			if(val == "2"){
				return "已发";
			}
			if(val == "3"){
				return "不予发放";
			}
		}
		function optformat(value,row,index){
			var opt = "<gd:btn btncode='BTNWXLPDHGL04'><a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a></gd:btn>" +
			"<gd:btn btncode='BTNWXLPDHGL05'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a></gd:btn>";
			if(row.status == '1'){
				opt += "<gd:btn btncode='BTNWXLPDHGL07'><a class='operate' href='javascript:;' onclick=\"updateStatus('"+row.id+"',2);\">已发</a></gd:btn>"+
					"<gd:btn btncode='BTNWXLPDHGL08'><a class='operate' href='javascript:;' onclick=\"updateStatus('"+row.id+"',3);\">不予发放</a></gd:btn>";
			}
			else if(row.status == "2"){
				opt += "<gd:btn btncode='BTNWXLPDHGL06'><a class='operate' href='javascript:;' onclick=\"updateStatus('"+row.id+"',1);\">未发</a></gd:btn>";
			}
			return opt;
		}
		function initList(){
			loadList(null);
			//分页加载
			$("#actGiftExchangedg").datagrid("getPager").pagination({
				pageList: [10,20,50,100]
			});
		}
		function saveAdd(){
			if($("#addActGiftExchangeForm #memberId").val() == ''){
				warningMessage("请选择用户！");
				return false;
			}
			if(!$("#addActGiftExchangeForm").form('validate')){
				return false;
			}
			if($("#addActGiftExchangeForm #giftId").val() == ''){
				warningMessage("请选择礼品！");
				return false;
			}
			$.ajax({
				type : "post",
				url : CONTEXT+"actGiftExchange/saveAdd",
				data : $("#addActGiftExchangeForm").serialize(),
				dataType : "json",
				success : function(data){
					if(data.status == 0){
						slideMessage("操作成功！");
						//刷新父页面列表
						$("#actGiftExchangedg").datagrid('reload');
						$('#addActGiftExchangeDialog').dialog('close');
					}else{
						warningMessage(data.msg);
					}
				}
			});
		}
		
		function editObj(id){
			$('#editActGiftExchangeDialog').dialog({'title':'编辑数据','href':CONTEXT+'actGiftExchange/edit/'+id, 'width': 500,'height': 300}).dialog('open');
		}
		function saveEdit(){
			if($("#editActGiftExchangeDialog #addActGiftExchangeForm #memberId").val() == ''){
				warningMessage("请选择用户！");
				return false;
			}
			if(!$("#editActGiftExchangeDialog #addActGiftExchangeForm").form('validate')){
				return false;
			}
			if($("#editActGiftExchangeDialog #addActGiftExchangeForm #giftId").val() == ''){
				warningMessage("请选择礼品！");
				return false;
			}
			$.ajax({
				type : "post",
				url : CONTEXT+"actGiftExchange/saveEdit",
				data : $("#editActGiftExchangeDialog #addActGiftExchangeForm").serialize(),
				dataType : "json",
				success : function(data){
					if(data.status == 0){
						slideMessage("操作成功！");
						//刷新父页面列表
						$("#actGiftExchangedg").datagrid('reload');
						$('#editActGiftExchangeDialog').dialog('close');
					}else{
						warningMessage(data.msg);
					}
				}
			});
		}
		function detailObj(id){
			$('#detailActGiftExchangeDialog').dialog({'title':'查看数据','href':CONTEXT+'actGiftExchange/view/'+id, 'width': 500,'height': 300}).dialog('open');
		}
		function updateStatus(id, status){
			jQuery.messager.confirm('提示', '您确定要操作所选数据吗?', function(r){
				if (r){
		    		jQuery.post(CONTEXT+"actGiftExchange/updateStatus/"+id,{"status":status},function(data){
						if (data.status == 0){
							slideMessage("操作成功！");
							$("#actGiftExchangedg").datagrid('reload');
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
		$(function(){
			initList();
			//刷新
			$('#icon-refresh').click(function(){
				disableExport = false;
				$('#actGiftExchangeSearchForm')[0].reset();
				$("#actGiftExchangedg").datagrid('load', {});
			});
			//重置按钮
			$('#icon-reload').click(function(){
				$('#actGiftExchangeSearchForm')[0].reset();
			});
			//查询
			$('#icon-search').click(function(){
				var params = {
					"account":$("#actGiftExchangeSearchForm #account").val(),
					"mobile":$("#actGiftExchangeSearchForm #mobile").val(),
					"activityName":$("#actGiftExchangeSearchForm #activityName").val(),
					"giftName":$("#actGiftExchangeSearchForm #giftName").val(),
					"status":$("#actGiftExchangeSearchForm #status").val(),
					"startDate":$("#actGiftExchangeSearchForm #startDate").val(),
					"endDate":$("#actGiftExchangeSearchForm #endDate").val()
				};
				loadList(params);
			});
			//新增
			$('#icon-add').click(function(){
				$('#addActGiftExchangeDialog').dialog({'title':'新增数据','href':CONTEXT+'actGiftExchange/add', 'width': 500,'height': 300}).dialog('open');
			});
			//导出
			$('#btn-export').click(function(){
				var queryParams = {
					"account":$("#actGiftExchangeSearchForm #account").val(),
					"mobile":$("#actGiftExchangeSearchForm #mobile").val(),
					"activityName":$("#actGiftExchangeSearchForm #activityName").val(),
					"giftName":$("#actGiftExchangeSearchForm #giftName").val(),
					"status":$("#actGiftExchangeSearchForm #status").val(),
					"startDate":$("#actGiftExchangeSearchForm #startDate").val(),
					"endDate":$("#actGiftExchangeSearchForm #endDate").val()
				};
				var paramList = "account="+queryParams.account+
					"&mobile="+queryParams.mobile+
					"&activityName="+queryParams.activityName+
					"&giftName="+queryParams.giftName+
					"&status="+queryParams.status+
					"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate;
				$.ajax({
					url: CONTEXT+'actGiftExchange/checkExportParams',
					data : queryParams,
					type:'post',
					success : function(data){
						//检测通过
						if (data && data.status == 1){
							if (!disableExport){
								slideMessage("数据正在导出中, 请耐心等待...");
								disableExport = true ;
								//启动下载
								$.download(CONTEXT+'actGiftExchange/exportData',paramList,'post');
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