<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>member</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="memberdg" title="">
	</table>
	<div id="membertb" style="padding:5px;height:auto">
		<form id="memberSearchForm" method="post">
		<div>
				用户账号:
			<input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:150px" >
			手机号：<input  type="text" id="mobile"    class="easyui-validatebox"    name="mobile" style="width:150px" >
						会员服务生效时间：
			<input  type="text"  id="startValidDate" name="startValidDate"  onFocus="WdatePicker({onpicked:function(){startValidDate.focus();},maxDate:'#F{$dp.$D(\'endValidDate\')}'})"    onClick="WdatePicker({onpicked:function(){startValidDate.focus();},maxDate:'#F{$dp.$D(\'endValidDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endValidDate" name="endValidDate"   onFocus="WdatePicker({onpicked:function(){endValidDate.focus();},minDate:'#F{$dp.$D(\'startValidDate\')}'})"   onClick="WdatePicker({onpicked:function(){endValidDate.focus();},minDate:'#F{$dp.$D(\'startValidDate\')}'})"   style="width:150px">
			<br/>
			账号状态:

				<select name="status"  id="status" >
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_certifi 表得到）
							 -->
<!-- 							<option value="0">请选择</option>
 -->
  							<option value="">全部</option>
 							<option value="1" <c:if test="${dto.status==1 }">selected</c:if>>启用</option>
							<option value="0" <c:if test="${dto.status==0 }">selected</c:if>>禁用</option>
						</select>
				广告上架通知:

				<select name="sendFlag"  id="sendFlag" >
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_certifi 表得到）
							 -->
<!-- 							<option value="0">请选择</option>
 -->
  							<option value="">全部</option>
 							<option value="0">未发送</option>
							<option value="1">已发送</option>
						</select>
				会员服务结束时间：
			<input  type="text"  id="startExpireDate" name="startExpireDate"  onFocus="WdatePicker({onpicked:function(){startExpireDate.focus();},maxDate:'#F{$dp.$D(\'endExpireDate\')}'})"    onClick="WdatePicker({onpicked:function(){startExpireDate.focus();},maxDate:'#F{$dp.$D(\'endExpireDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endExpireDate" name="endExpireDate"   onFocus="WdatePicker({onpicked:function(){endExpireDate.focus();},minDate:'#F{$dp.$D(\'startExpireDate\')}'})"   onClick="WdatePicker({onpicked:function(){endExpireDate.focus();},minDate:'#F{$dp.$D(\'startExpireDate\')}'})"   style="width:150px">

			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#memberSearchForm').form('validate');"    >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>

		<div style="margin-bottom:5px">
 			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-refresh">刷新</a>
 			<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
		</div>

		<div id="editDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsEdit">
			<div id="dlg-buttonsEdit" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editDialog').dialog('close')">关闭</a>
	        </div>
		</div>

		<div id="showDialog" class="easyui-dialog"  closed="true" modal="true" buttons="#dlg-buttonsShow">
			<div id="dlg-buttonsShow" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#showDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="marketDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsMarket">
			<div id="#dlg-buttonsMarket" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#marketDialog').dialog('close')">关闭</a>
	        </div>
		</div>
	</div>
</body>
<script type="text/javascript">
function optFormat(value,row,index){
	return "<a class='operate' href='javascript:;' onclick=showObj('"+row.memberId+"');>查看</a>" +
		   "<a class='operate' href='javascript:;' onclick=editObj('"+row.memberId+"');>编辑</a>" +
//		   "<a class='operate' href='javascript:;' onclick=delObj('"+row.memberId+"');>删除</a>" +
			"<a class='operate' href='javascript:;' onclick=sendMessage('"+row.memberId+"');>发送</a>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/member/goldSupplier.js"></script>
<script type="text/javascript">

	var disableExport = false ;

	$("#exportData").click(function(){
/* 		 var queryParams = $('#memberdg').datagrid('options').queryParams;
			queryParams.account = $('#memberSearchForm #account').val();
			queryParams.mobile = $('#memberSearchForm #mobile').val();
			queryParams.level = $('#memberSearchForm #level').val();
			queryParams.isAuth = $('#memberSearchForm #isAuth').val();
			queryParams.startDate =  $('#memberSearchForm #startDate').val();
			queryParams.endDate =  $('#memberSearchForm #endDate').val();
			queryParams.marketId = $("#memberSearchForm #marketId").val();
			queryParams.regetype = $("#memberSearchForm #regetype").val(); */

			var params = {
				"account" : $('#memberSearchForm #account').val(),
				"mobile" : $('#memberSearchForm #mobile').val(),
				"sendFlag" : $('#memberSearchForm #sendFlag').val(),
				"startValidDate" : $('#memberSearchForm #startValidDate').val(),
				"endValidDate" : $('#memberSearchForm #endValidDate').val(),
				"startExpireDate" : $('#memberSearchForm #startExpireDate').val(),
				"endExpireDate" : $('#memberSearchForm #endExpireDate').val(),
				"status" : $('#memberSearchForm #status').val()
			};
			var paramList = "account=" + params.account + "&mobile="+params.mobile+"&sendFlag="+params.sendFlag+"&startValidDate="+params.startValidDate+
				"&endValidDate="+params.endValidDate+"&startExpireDate="+params.startExpireDate+"&endExpireDate="+params.endExpireDate+"&status="+params.status;

			$.ajax({
				url: CONTEXT+'member/checkExportGoldSupplierParams',
				data : params,
				type:'post',
				async:false,
				success : function(data){
					//检测通过
					if (data && data.status == 1){
						if (!disableExport){
							slideMessage("数据正在导出中, 请耐心等待...");
							disableExport = true ;
							//启动下载
							$.download(CONTEXT+'member/exportGoldSupplierData',paramList,'post' );
						}else{
							slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
						}
					}else{
						warningMessage(data.message);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					warningMessage(textStatus);
				}
			});
/* 			window.location.href=CONTEXT+'member/exportData?account='+queryParams.account+
							"&mobile="+queryParams.mobile+"&level="+queryParams.level+"&isAuth="+queryParams.isAuth+
							"&marketId="+queryParams.marketId+"&startDate="+queryParams.startDate+"&endDate="+queryParams.endDate+"&regetype="+queryParams.regetype; */
	});
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
</script>
</html>

