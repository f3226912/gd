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
		<title>活动</title>
	</head>
	<body>
		<table id="actActivitydg" title=""></table>
		<div id="actActivitytb" style="padding:10px;height:auto">
			<form id="actActivitySearchForm" method="post">
				<div>
					<label>活动名称：<input type="text" name="name"/></label>
					<label>活动类型：
						<select name="type">
							<option value="">请选择活动类型</option>
							<option value="1">刮刮卡</option>
							<option value="2">幸运大转盘</option>
							<option value="3">摇一摇</option>
							<option value="4">疯狂抢红包</option>
							<option value="5">砸金蛋</option>
						</select>
					</label>
					<label>活动渠道：
						<select name="channel">
							<option value="">请选择活动渠道</option>
							<option value="1">H5-农商友</option>
						</select>
					</label>
					<label>参与用户：
						<select name="userGroup">
							<option value="">请选择参与用户</option>
							<option value="1">微信绑定用户</option>
						</select>
					</label>
					<label>创建时间：
						<input type="text" name="startDate" id="startDate" onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/> - 
						<input type="text" name="endDate" id="endDate" onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" >
					</label>
					<gd:btn btncode='BTNWXHDGL01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a></gd:btn>
					<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
				</div>
				<div style="margin-top:10px">
					<gd:btn btncode='BTNWXHDGL02'><a class="easyui-linkbutton icon-add-btn" iconCls="icon-add" id="icon-add">新增</a></gd:btn>
					<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
					<gd:btn btncode='BTNWXHDGL03'><a class="easyui-linkbutton" iconCls="icon-save" id="btn-export">导出数据</a></gd:btn>
				</div>
			</form>
		</div>
		<div id="addActActivityDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsAdd">
			<div id="dlg-buttonsAdd" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveAdd()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addActActivityDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="editActActivityDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="saveEdit()">保存</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editActActivityDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="viewActActivityDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsView">
			<div id="dlg-buttonsView" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#viewActActivityDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</body>
	<script type="text/javascript">
		var disableExport = false;
		function loadList(params){
			params = !params ? {}: params;
			//数据加载
			$('#actActivitydg').datagrid({
				url:CONTEXT+'actActivity/query',
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
					{field:'type',title:'活动类型',width:100,align:'center', formatter:typeFormatter},
					{field:'channel',title:'活动渠道',width:100,align:'center', formatter:channelFormatter},
					{field:'userGroup',title:'参与用户群',width:100,align:'center', formatter:userGroupFormatter},
					{field:'createTime',title:'创建时间',width:100,align:'center'},
					{field:'launch',title:'活动状态',width:100,align:'center',formatter:launchFormatter},
					{field:'createUserName',title:'活动创建人',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
			}); 
		}
		function typeFormatter(val, row, index){
			if(val == ''){
				return "";
			}
			if(val == '1'){
				return "刮刮卡";
			}
			if(val == '2'){
				return "幸运大转盘";
			}
			if(val == '3'){
				return "摇一摇";
			}
			if(val == '4'){
				return "疯狂抢红包";
			}
			if(val == '5'){
				return "砸金蛋";
			}
		}
		function channelFormatter(val, row, index){
			if(val == ''){
				return "";
			}
			if(val == '1'){
				return "H5-农商友";
			}
		}
		function launchFormatter(val, row, index){
			if(val == ''){
				return "";
			}
			if(val == '0'){
				return "结束";
			}
			if(val == '1'){
				return "启用";
			}
			if(val == '2'){
				return "等待";
			}
		}
		function userGroupFormatter(val, row, index){
			if(val == ''){
				return "";
			}
			if(val == '1'){
				return "微信绑定用户";
			}
		}
		function optformat(value,row,index){
			var opt = "<gd:btn btncode='BTNWXHDGL04'><a class='operate' href='javascript:;' onclick=detailObj('"+row.id+"');>查看</a></gd:btn>&nbsp;";
			if(row.launch == '1'){
				opt += "<gd:btn btncode='BTNWXHDGL05'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a></gd:btn>&nbsp;";
				opt += "<gd:btn btncode='BTNWXHDGL07'><a class='operate' href='javascript:;' onclick=\"updateStatus('"+row.id+"', 0);\">结束</a></gd:btn>";
			}
			else if(row.launch == '2'){
				opt += "<gd:btn btncode='BTNWXHDGL05'><a class='operate' href='javascript:;' onclick=editObj('"+row.id+"');>编辑</a></gd:btn>&nbsp;";
				opt += "<gd:btn btncode='BTNWXHDGL06'><a class='operate' href='javascript:;' onclick=\"updateStatus('"+row.id+"', 1);\">启用</a></gd:btn>";
			}
			return opt;
		}
		
		function initList(){
			loadList(null);
			//分页加载
			$("#actActivitydg").datagrid("getPager").pagination({
				pageList: [10,20,50,100]
			});
		}
		
		function saveAdd(){
			if(!$("#addActActivityForm").form('validate')){
				return false;
			}
			var name = $("#addActActivityForm input[name='name']").val();
			var type = $("#addActActivityForm select[name='type']").val();
			var channel = $("#addActActivityForm input[name='channel']").val();
			var userGroup = $("#addActActivityForm input[name='userGroup']").val();
			var exchangeTime = $("#addActActivityForm input[name='exchangeTime']").val();
			var effectiveStartTime = $("#addActActivityForm input[name='effectiveStartTime']").val();
			var effectiveEndTime = $("#addActActivityForm input[name='effectiveEndTime']").val();
			var params = "name="+name+"&type="+type+"&channel="+channel+
				"&userGroup="+userGroup+"&exchangeTime="+exchangeTime+
				"&effectiveStartTime="+effectiveStartTime+"&effectiveEndTime="+effectiveEndTime;
			
			var validateGiftPass = true;
			$("#addActActivityForm input[name='giftId']").each(function(){
				if($(this).attr('checked')=='checked'){
					var giftRowCount = $(this).prev().val();
					var giftId = $(this).val();
					if(giftId == '' || giftId=='on'){
						validateGiftPass = false;
						warningMessage("请选择礼品"+giftRowCount);
						return false;
					}
					var cost = $("#cost"+giftRowCount).val();
					if(cost == ''){
						validateGiftPass = false;
						warningMessage("请填写礼品"+giftRowCount+"的活动预算");
						return false;
					}
					if(!/^\d{1,4}$/.test(cost)){
						validateGiftPass = false;
						warningMessage("礼品"+giftRowCount+"的活动预算格式不正确，只能填写数字且最大值为9999");
						return false;
					}
					var exchangeScore = $("#exchangeScore"+giftRowCount).val();
					if(exchangeScore == ''){
						validateGiftPass = false;
						warningMessage("请填写礼品"+giftRowCount+"的所需积分");
						return false;
					}
					if(!/^\d{1,4}$/.test(exchangeScore)){
						validateGiftPass = false;
						warningMessage("礼品"+giftRowCount+"的所需积分格式不正确，只能填写数字且最大值为9999");
						return false;
					}
					params += "&giftId=" + giftId;
					params += "&cost=" + cost;
					params += "&exchangeScore=" + exchangeScore;
				}
			});
			if(!validateGiftPass){
				return false;
			}
			if(effectiveStartTime == ''){
				warningMessage("积分/礼品有效期开始时间不能为空");
				return false;
			}
			if(effectiveEndTime == ''){
				warningMessage("积分/礼品有效期结束时间不能为空");
				return false;
			}
			
			$.ajax({
				type : "post",
				url : CONTEXT+"actActivity/saveAdd",
				data : params,
				dataType : "json",
				success : function(data){
					if(data.status == 0){
						slideMessage("操作成功！");
						//刷新父页面列表
						$("#actActivitydg").datagrid('reload');
						$('#addActActivityDialog').dialog('close');
					}else{
						warningMessage(data.msg);
					}
				}
			});
		}
		
		function saveEdit(){
			if(!$("#editActActivityForm").form('validate')){
				return false;
			}
			var validateGiftPass = true;
			$("#editActActivityForm input[name='giftId']").each(function(giftRowCount){
				giftRowCount++;
				var cost = $("#cost"+giftRowCount).val();
				if(cost == ''){
					validateGiftPass = false;
					warningMessage("请填写礼品"+giftRowCount+"的活动预算");
					return false;
				}
				if(!/^\d{1,4}$/.test(cost)){
					validateGiftPass = false;
					warningMessage("礼品"+giftRowCount+"的活动预算格式不正确，只能填写数字且最大值为9999");
					return false;
				}
				var exchangeScore = $("#exchangeScore"+giftRowCount).val();
				if(exchangeScore == ''){
					validateGiftPass = false;
					warningMessage("请填写礼品"+giftRowCount+"的所需积分");
					return false;
				}
				if(!/^\d{1,4}$/.test(exchangeScore)){
					validateGiftPass = false;
					warningMessage("礼品"+giftRowCount+"的所需积分格式不正确，只能填写数字且最大值为9999");
					return false;
				}
			});
			if(!validateGiftPass){
				return false;
			}
			var effectiveStartTime = $("#editActActivityForm #edit_effectiveStartTime").val();
			if(effectiveStartTime == ''){
				warningMessage("积分/礼品有效期开始时间不能为空");
				return false;
			}
			var effectiveEndTime = $("#editActActivityForm #edit_effectiveEndTime").val();
			if(effectiveEndTime == ''){
				warningMessage("积分/礼品有效期结束时间不能为空");
				return false;
			}
			$.ajax({
				type : "post",
				url : CONTEXT+"actActivity/saveEdit",
				data : $("#editActActivityForm").serialize(),
				dataType : "json",
				success : function(data){
					if(data.status == 0){
						slideMessage("操作成功！");
						//刷新父页面列表
						$("#actActivitydg").datagrid('reload');
						$('#editActActivityDialog').dialog('close');
					}else{
						warningMessage(data.msg);
					}
				}
			});
		}
		function editObj(id){
			$('#editActActivityDialog').dialog({'title':'编辑数据','href':CONTEXT+'actActivity/edit/'+id, 'width': 800,'height': 500}).dialog('open');
		}
		function detailObj(id){
			$('#viewActActivityDialog').dialog({'title':'查看数据','href':CONTEXT+'actActivity/view/'+id, 'width': 800,'height': 500}).dialog('open');
		}
		function delObj(id){
			jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
				if (r){
		    		jQuery.post(CONTEXT+"actActivity/delete/"+id,{},function(data){
						if (data.status == 0){
							slideMessage("操作成功！");
							$("#actActivitydg").datagrid('reload');
						}else{
							warningMessage(data.msg);
							return;
						}
		    		});
				}
			});
		}
		
		function updateStatus(id, status){
			var tip = "您确定要操作所选数据吗？";
			if(status == '0'){
				tip = "活动结束后，将不能再被启用，请确认是否继续？";
			}
			jQuery.messager.confirm('提示', tip, function(r){
				if (r){
		    		jQuery.post(CONTEXT+"actActivity/updateStatus/"+id,{"launch":status},function(data){
						if (data.status == 0){
							slideMessage("操作成功！");
							$("#actActivitydg").datagrid('reload');
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
				$('#addActActivityDialog').dialog({'title':'新增数据','href':CONTEXT+'actActivity/add', 'width': 800,'height': 500}).dialog('open');
			});
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
					"name":$("#actActivitySearchForm input[name='name']").val(),
					"type":$("#actActivitySearchForm select[name='type']").val(),
					"channel":$("#actActivitySearchForm select[name='channel']").val(),
					"userGroup":$("#actActivitySearchForm select[name='userGroup']").val(),
					"startDate":$("#actActivitySearchForm #startDate").val(),
					"endDate":$("#actActivitySearchForm #endDate").val()
				};
				loadList(params);
			});
			//导出
			$('#btn-export').click(function(){
				var queryParams = {
					"name":$("#actActivitySearchForm input[name='name']").val(),
					"type":$("#actActivitySearchForm select[name='type']").val(),
					"channel":$("#actActivitySearchForm select[name='channel']").val(),
					"userGroup":$("#actActivitySearchForm select[name='userGroup']").val(),
					"startDate":$("#actActivitySearchForm #startDate").val(),
					"endDate":$("#actActivitySearchForm #endDate").val()
				};
				var paramList = "name="+queryParams.name+
					"&type="+queryParams.type+
					"&channel="+queryParams.channel+
					"&userGroup="+queryParams.userGroup+
					"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate;
				
				$.ajax({
					url: CONTEXT+'actActivity/checkExportParams',
					data : queryParams,
					type:'post',
					success : function(data){
						//检测通过
						if (data && data.status == 1){
							if (!disableExport){
								slideMessage("数据正在导出中, 请耐心等待...");
								disableExport = true ;
								//启动下载
								$.download(CONTEXT+'actActivity/exportData',paramList,'post');
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