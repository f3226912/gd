<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
<meta http-equiv="description" content="This is my page"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<title>地推客户管理</title>
</head>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
<table id="grdUserCustomer-datagrid-table" title=""></table>
<div id="grdUserCustomer-datagrid-tool-bar" style="height:auto;padding:0px !important;margin:0px;">
	<form id="grdUserCustomer-datagrid-form" method="post">
<table>
		<tr>
			
			<td align="right">客户姓名：</td>
			<td>
			<input type="text" id="memberName" name="memberName" placeholder="请输入客户姓名" maxlength="30" class="easyui-validatebox" style="width:160px;height:20px;margin-right: 10px;" >
			</td>
			<td align="right">客户手机：</td>
			<td>
			<input type="text" id="memberMobile" name="memberMobile" placeholder="请输手机号码" maxlength="11" class="easyui-validatebox" style="width:160px;height:20px;margin-right: 10px;" >
			</td>
			<td></td>
			<td></td>
			<td></td>

			<td align="right">
				<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="btn-search" >查询</a>&nbsp;&nbsp;
				<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
			</td>
		</tr>
		<tr>
			
			<td align="right">地推姓名：</td>
			<td>
			<input type="text" id="grdUserName" name="grdUserName" placeholder="请输入姓名" maxlength="30" class="easyui-validatebox" style="width:160px;height:20px;margin-right: 10px;" >
			</td>
			<td align="right">地推手机：</td>
			<td>
			<input type="text" id="grdMobile" name="grdMobile" placeholder="请输手机号码" maxlength="11" class="easyui-validatebox" style="width:160px;height:20px;margin-right: 10px;" >
			</td>
			<td align="right">所属市场：</td>
			<td>
				<select name="marketId" id="marketId" style="width:150px;height:20px;margin-right: 10px;">
					<option value="" selected="selected">全部</option>
				</select>
			</td>
		<td></td>
		<td></td>

		</tr>
	</table>

	</form>
	<div style="background:#efefef;padding:5px;height:25px;">
			<div style="float: left;font-size:16px;margin-left:5px;">地推人员客户列表</div>
			<div style="float:right;margin-right:57px;">
				<gd:btn btncode='BTNZPDTRYGLZP01'><a id="assignMember" class="easyui-linkbutton" iconCls="icon-save">指派地推人员</a></gd:btn>
				<gd:btn btncode='BTNZPDTRYGLDC01'><a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a></gd:btn>
			</div>
	</div>
</div>
</body>
<script type="text/javascript">

function initMarket(marketType){
	$.ajax({
		type: "GET",
		url: "${CONTEXT }market/queryByType/"+ marketType,
		dataType: "json",
		success: function(data) {
			var markets=data.rows;
			if (markets.length != 0) {
				$('#marketId').empty();
				$('#marketId').append("<option value=''>全部</option>");
				for ( var n = 0; n < markets.length; n++) {
					$('#marketId').append("<option value='"+markets[n].id+"'>"+markets[n].marketName+"</option>");
				}
			}
		}
	});
}
initMarket(2);
</script>
<script type="text/javascript" src="${CONTEXT}js/grdUserCustomer/main.js"></script>
</html>