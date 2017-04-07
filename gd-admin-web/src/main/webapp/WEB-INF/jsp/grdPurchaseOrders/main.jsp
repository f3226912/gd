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
<meta http-equiv="X-UA-Compatible" content="IE=7;IE=9;IE=10;IE=11;IE=12"/>
<title></title>
</head>
<body>
<table id="purchase_datagrid-table" title=""></table>
	
		<form id="purchase_datagrid-form" method="post">
		<div id="purchase_datagrid-tool-bar" >
			<div>
				<table>
					<tr>
						<td>所属市场： <select id="marketId" name="marketId">
						</select>
						</td>
						<td>所属仓库： <select name="giftstoreId" id="giftstoreId"
							style="width: 150px; height: 20px; margin-right: 10px;">
								<option value="" selected="selected">全部</option>
						</select></td>
						<td>入库日期：<input type="text" id="inStorageStartDate"
							name="inStorageStartDate"
							onFocus="WdatePicker({onpicked:function(){inStorageEndDate.focus();},maxDate:'#F{$dp.$D(\'inStorageEndDate\')}',dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){inStorageStartDate.focus();},maxDate:'#F{$dp.$D(\'inStorageEndDate\')}', dateFmt:'yyyy-MM-dd'})"
							style="width: 150px"> - <input type="text"
							id="inStorageEndDate" name="inStorageEndDate"
							onFocus="WdatePicker({onpicked:function(){inStorageEndDate.focus();},minDate:'#F{$dp.$D(\'inStorageStartDate\')}', dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){inStorageEndDate.focus();},minDate:'#F{$dp.$D(\'inStorageStartDate\')}', dateFmt:'yyyy-MM-dd'})"
							style="width: 150px"></td>
					</tr>
					<tr>
						<td>礼品编码： <input type="text" maxlength="12" data-options="min:0,precision:0" 
							id="giftNO" name="giftNO" placeholder="请输入礼品编码"
							class="easyui-validatebox" style="width: 150px">
						</td>
						<td>礼品名称： <input type="text" maxlength="30" id="giftname"
							name="giftname" placeholder="请输入礼品名称" class="easyui-validatebox"
							style="width: 150px">

						</td>
						<td align="right">
								<a class="easyui-linkbutton icon-search-btn"
									iconCls="icon-search" id="btn-search">查询</a>
								<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset">重置</a>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table width="100%">
					<tr>
						<td align="left">礼品入库列表</td>
						<td align="right">合计：<span id="giftSum"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<gd:btn btncode='BTNRKTJDC'>
								<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出EXCEL</a>
							</gd:btn>
						</td>
					</tr>
				</table>
			</div>
	</div>
	</form>
</body>
<script type="text/javascript" src="${CONTEXT}js/grdPurchaseOrders/main.js"></script>

</html>