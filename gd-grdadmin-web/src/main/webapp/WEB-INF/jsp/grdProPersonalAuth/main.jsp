<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<!DOCTYPE>
<html>
<head>
<base href="${CONTEXT}">
<%@ include file="/WEB-INF/jsp/pub/head.inc"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible"
	content="IE=7, IE=9, IE=10, IE=11, IE=12" />
<title>个人认证统计</title>
</head>
<script type="text/javascript"
	src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="datagrid-table" title=""></table>
	<div id="datagrid-tool-bar"
		style="height: auto; padding: 0px !important; margin: 0px;">
		<form id="datagrid-form" method="post"
			style="padding: 5px 0px 5px 20px;">
			<div>
				<table>
					<tr>
						<td align="right">所属市场：</td>
						<td><select name="marketId" id="marketId"
							style="width: 150px; height: 20px; margin-right: 10px;">
								<option value="" selected="selected">全部</option>
						</select></td>
						<!-- <td align="right">团队：</td>
						<td colspan="2"><select id="teamGroup" name="teamGroup"
							class="easyui-combobox" style="width: 150px;"
							onchange="teamChange(this)">
						</select></td> -->
						<td align="right">地推姓名：</td>
						<td colspan="2"><input type="text" id="grdUserName"
							maxlength="30" name="grdUserName" placeholder="地推姓名"
							class="easyui-validatebox"
							style="width: 160px; margin-right: 10px;"></td>
						<td align="right">地推手机：</td>
						<td><input type="text" id="grdMobile" name="grdMobile"
							placeholder="地推手机" class="easyui-validatebox"
							style="width: 160px; margin-right: 10px;" maxlength="11"></td>

					</tr>
					<tr>
						<td align="right">认证状态：</td>
						<td><select id="status" name="status"
							style="width: 150px; margin-right: 10px;">
								<option value="">全部</option>
								<option value="0">待认证</option>
								<option value="1">已认证</option>
								<option value="2">已驳回</option>
						</select></td>
						<td align="right">认证申请时间：</td>
						<td colspan="2"><input type="text" id="applyStartDate"
							name="applyStartDate"
							onFocus="WdatePicker({onpicked:function(){applyStartDate.focus();},maxDate:'#F{$dp.$D(\'applyEndDate\')}',dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){applyStartDate.focus();},maxDate:'#F{$dp.$D(\'applyEndDate\')}', dateFmt:'yyyy-MM-dd'})"
							style="width: 75px">~ <input type="text"
							id="applyEndDate" name="applyEndDate"
							onFocus="WdatePicker({onpicked:function(){applyEndDate.focus();},minDate:'#F{$dp.$D(\'applyStartDate\')}', dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){applyEndDate.focus();},minDate:'#F{$dp.$D(\'applyStartDate\')}', dateFmt:'yyyy-MM-dd'})"
							style="width: 75px"></td>
						<td align="right">审核时间：</td>
						<td colspan="2"><input type="text" id="auditStartDate"
							name="auditStartDate"
							onFocus="WdatePicker({onpicked:function(){auditStartDate.focus();},maxDate:'#F{$dp.$D(\'auditEndDate\')}',dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){auditStartDate.focus();},maxDate:'#F{$dp.$D(\'auditEndDate\')}', dateFmt:'yyyy-MM-dd'})"
							style="width: 75px">~ <input type="text"
							id="auditEndDate" name="auditEndDate"
							onFocus="WdatePicker({onpicked:function(){auditEndDate.focus();},minDate:'#F{$dp.$D(\'auditStartDate\')}', dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){auditEndDate.focus();},minDate:'#F{$dp.$D(\'auditStartDate\')}', dateFmt:'yyyy-MM-dd'})"
							style="width: 75px"></td>
						<td colspan="2"><a class="easyui-linkbutton icon-search-btn"
							iconCls="icon-search" id="btn-search">查询</a> <a
							class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload"
							id="btn-reset">重置</a> <a class="easyui-linkbutton"
							iconCls="icon-reload" id="btn-refresh">刷新</a></td>
					</tr>

				</table>
			</div>
		</form>

		<div>
			<div style="background: #efefef; padding: 5px; height: 25px;">
				<div style="float: left; font-size: 16px; margin-left: 5px;">认证信息列表</div>
				<gd:btn btncode='BTNGRRZTJJ01'>
					<div style="float: right; margin-right: 57px;">
						<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
					</div>
				</gd:btn>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript"
	src="${CONTEXT}js/grdProPersonalAuth/main.js"></script>
</html>