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
		<table id="appsharedg" title=""></table>
		<div id="appsharetb" style="padding:10px;height:auto">
			<form id="appshareSearchForm" method="post">
				<div>
					<label>用户账号：<input type="text" name="account" id="account"/></label>
					<label>手机号码：<input type="text" name="mobile" id="mobile"/></label>
					<label>真实姓名：<input type="text" name="realName" id="realName"/></label>
				
					<label>分享时间：
						<input type="text" name="startDate" id="startDate" onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"/> - 
						<input type="text" name="endDate" id="endDate" onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})" >
					</label>
					</div>
					<div>
					<label>推广链接所属市场：
					</label>
					<select id="marketId" style="width:200px"  name="marketId" >
					</select>
					<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search">查询</a>
					<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="icon-reload">重置</a>
				</div>
				<div style="margin-top:10px">
					
					<a class="easyui-linkbutton" iconCls="icon-save" id="btn-export">导出数据</a>
					<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
				</div>
			</form>
		</div>
		
		
		<div id="detailAppshareDialog" class="easyui-dialog" style="width:800px;height:500px;" closed="true" modal="true" buttons="#dlg-buttonsDetail">
			<div id="dlg-buttonsDetail" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailAppshareDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</body>
	<script type="text/javascript">
	
		var disableExport = false;
		function loadList(params){
			params = !params ? {}: params;
			//数据加载
			$('#appsharedg').datagrid({
				url:CONTEXT+'appshare/query',
				queryParams: params,
				height: 'auto', 
				nowrap:true,
				toolbar:'#appsharetb',
				pageSize:100,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				fit:true,
				onLoadSuccess:function(){
					$("#appsharedg").datagrid('clearSelections');
				},
				columns:[[
					{field:'shareId',title:'',width:0,checkbox:true},
					{field:'account',title:'用户账号',width:100,align:'center'},
					{field:'realName',title:'真实姓名',width:100,align:'center'},
					{field:'mobile',title:'手机号码',width:100,align:'center' },
					{field:'shareDate',title:'分享时间',width:100,align:'center'},
					{field:'marketName',title:'推广链接所属市场',width:100,align:'center'},
					{field:'viewCount',title:'访问次数',width:80,align:'center'},
					{field:'giftStatu',title:'礼品发放状态',width:80,align:'center', formatter:statusFormat},
					{field:'updateUserName',title:'操作员',width:80,align:'center'},
					{field:'opt',title:'操作',width:150,align:'center',formatter:optformat}
				]]
			}); 
		}
		function statusFormat(val, row, index){
			if(val == ''){
				return "";
			}
			if(val == "0"){
				return "未发";
			}
			if(val == "1"){
				return "已发";
			}
			
		}
		function optformat(value,row,index){
			var opt = "<a class='operate' href='javascript:;' onclick=detailObj('"+row.memberId+"');>详情</a>";
			if(row.giftStatu == '1'){
				opt += "<a class='operate' href='javascript:;' onclick=\"updateStatus('"+row.shareId+"',0);\">未发</a>";
				
			}
			else if(row.giftStatu == "0"){
				opt += "<a class='operate' href='javascript:;' onclick=\"updateStatus('"+row.shareId+"',1);\">已发</a>";
			}
			return opt;
		}
		function initList(){
			loadList(null);
			//分页加载
			$("#appsharedg").datagrid("getPager").pagination({
				pageList: [10,20,50,100]
			});
		}

		
		function detailObj(memberId){
			$('#detailAppshareDialog').dialog({'title':'详情','href':CONTEXT+'appshare/view/'+memberId, 'width': 800,'height': 400}).dialog('open');
		}
		
		function updateStatus(shareId, status){
			jQuery.messager.confirm('提示', '您确定要操作所选数据吗?', function(r){
				if (r){
		    		jQuery.post(CONTEXT+"appshare/updateStatus/"+shareId,{"giftStatu":status},function(data){
						if (data.status == 0){
							slideMessage("操作成功！");
							$("#appsharedg").datagrid('reload');
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
			$.ajax({
				type: "GET",
				url: "${CONTEXT}market/getAllMarket",
				dataType: "json",
				success: function(data) {
					var markets=data;
					if (markets.length != 0) {
						$('#marketId').empty();
						$('#marketId').append("<option value=''>全部</option>");
						for ( var n = 0; n < markets.length; n++) {
							$('#marketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
						}
					}
				}
			});
			/*$("#marketId").combobox({
				url:'${CONTEXT}market/getAllMarket',
				valueField:'id',
				textField:'marketName' 
			});*/
			
			initList();
			//刷新
			$('#icon-refresh').click(function(){
				disableExport = false;
				$('#appshareSearchForm')[0].reset();
				$("#appsharedg").datagrid('load', {});
			});
			//重置按钮
			$('#icon-reload').click(function(){
				$('#appshareSearchForm')[0].reset();
			});
			//查询
			$('#icon-search').click(function(){
				var params = {
					"account":$("#appshareSearchForm #account").val(),
					"mobile":$("#appshareSearchForm #mobile").val(),
					"realName":$("#appshareSearchForm #realName").val(),
					"startDate":$("#appshareSearchForm #startDate").val(),
					"endDate":$("#appshareSearchForm #endDate").val(),
					"marketId":$("#marketId").val()
				};
				loadList(params);
			});
			
			//导出
			$('#btn-export').click(function(){
				var queryParams = {
					"account":$("#appshareSearchForm #account").val(),
					"mobile":$("#appshareSearchForm #mobile").val(),
					"realName":$("#appshareSearchForm #realName").val(),
					"startDate":$("#appshareSearchForm #startDate").val(),
					"endDate":$("#appshareSearchForm #endDate").val(),
					"marketId":$("#marketId").val()
				};
				var paramList = "account="+queryParams.account+
					"&mobile="+queryParams.mobile+
					"&realName="+queryParams.realName+
					"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate+
					"&marketId="+queryParams.marketId;
				$.ajax({
					url: CONTEXT+'appshare/checkExportParams',
					data : queryParams,
					type:'post',
					success : function(data){
						//检测通过
						if (data && data.status == 1){
							if (!disableExport){
								slideMessage("数据正在导出中, 请耐心等待...");
								disableExport = true ;
								//启动下载
								$.download(CONTEXT+'appshare/exportData',paramList,'post');
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