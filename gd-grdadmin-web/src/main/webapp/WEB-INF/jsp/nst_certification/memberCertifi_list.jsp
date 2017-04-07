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
		<title>农速通认证</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="certifidg" title="">
	</table>
	<div id="certifitb" style="padding:5px;height:auto">
		<form id="certifiSearchForm" method="post">
		<div>
			申请时间：
			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:120px" >
			~
			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:120px">
			认证状态:
			<select name="status" id="status"  >
 							<option value="">请选择</option>
							<option value="1" <c:if test="${dto.isAuth==1 }">selected</c:if>>已认证</option>
							<option value="0" <c:if test="${dto.isAuth==0 }">selected</c:if>>待认证</option>
							<option value="2" <c:if test="${dto.isAuth==2 }">selected</c:if>>已驳回</option>

						</select>

		 	注册账号:
			<input  type="text" id="account"    class="easyui-validatebox"    name="account" style="width:120px" >
			所属区域：
			 <select id="areaName" name="areaName" class="text_sketch" >
		       <option selected="selected" value="">请选择</option>
		       <c:forEach items="${areaNameList}" var="obj">
		       <option value="${obj.areaName}">${obj.areaName}</option>
		       </c:forEach>
			</select>
			认证类型:
			<select name="certifiType" id="certifiType"  >
 							<option value="">请选择</option>
							<option value="1" <c:if test="${dto.type==1 }">selected</c:if>>个人</option>
							<option value="2" <c:if test="${dto.type==2 }">selected</c:if>>企业</option>

			</select>
			<gd:btn btncode='BTNYHRZNSTRZ01'><a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#certifiSearchForm').form('validate');"    >查询</a>&nbsp;&nbsp;</gd:btn>
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
		</div>
		</form>

		<div style="margin-bottom:5px">
 			 <a href="#" class="easyui-linkbutton" id="icon-show"  >审核</a>
			 <a class="easyui-linkbutton" id="icon-refresh"  iconCls="icon-reload">刷新</a>
			 <gd:btn btncode='BTNYHRZNSTRZ02'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
		</div>



		<div id="showDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsShow">
			<div id="dlg-buttonsShow" style="text-align:center">
	            <gd:btn btncode='BTNYHRZNSTRZ03'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="audit()">通过</a></gd:btn>
	            <gd:btn btncode='BTNYHRZNSTRZ04'><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-back'" id="icon-back-btn" onclick="unpassShow()">驳回</a></gd:btn>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#showDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		<div id="unpassDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-unpass">

		</div>
	</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
	return "<gd:btn btncode='BTNYHRZNSTRZ05'><a class='operate' href='javascript:;' onclick=showObj('"+row.certifiId+"');>查看</a></gd:btn>";
//	return "<a class='operate' href='javascript:;' onclick=showObj('"+row.certifiId+"');>查看</a><a class='operate' href='javascript:;' onclick=editObj('"+row.certifiId+"');>编辑</a>";
}
</script>
<script type="text/javascript" src="${CONTEXT}js/nst_certification/main.js"></script>
<script type="text/javascript" src="${CONTEXT}js/nst_certification/add.js"></script>

<script type="text/javascript">

	var disableExport = false ;

	$("#exportData").click(function(){
		/*  var queryParams = $('#certifidg').datagrid('options').queryParams;
		queryParams.account = $('#certifiSearchForm #account').val();
		queryParams.status = $('#certifiSearchForm #status').val();
		queryParams.startDate =  $('#certifiSearchForm #startDate').val();
		queryParams.endDate =  $('#certifiSearchForm #endDate').val();
		queryParams.areaName = $('#certifiSearchForm #areaName').val(); */

		var queryParams = {
			"account" : $('#certifiSearchForm #account').val(),
			"status" : $('#certifiSearchForm #status').val(),
			"startDate" : $('#certifiSearchForm #startDate').val(),
			"endDate" : $('#certifiSearchForm #endDate').val(),
			"areaName" : $('#certifiSearchForm #areaName').val()
		}

		var paramList = "account="+queryParams.account+
			"&status="+queryParams.status+
			"&startDate="+queryParams.startDate+
			"&endDate="+queryParams.endDate+
			"&areaName="+queryParams.areaName;

		$.ajax({
			url: CONTEXT+'nst_certification/checkExportParams',
			data : queryParams,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'nst_certification/exportData',paramList,'post');
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
		/* window.location.href=CONTEXT+'nst_certification/exportData?account='+queryParams.account+
						"&status="+queryParams.status+
						"&areaName="+queryParams.areaName+
						"&startDate="+queryParams.startDate+
						"&endDate="+queryParams.endDate; */
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

