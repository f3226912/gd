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
<title>银行信息管理</title>
</head>
<script type="text/javascript"
	src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>
<body>
<input type="hidden" id="hdMsg" name="hdMsg" value="${msg}" />
	<table id="datagrid-table" title=""></table>
	<div id="datagrid-tool-bar"
		style="height: auto; padding: 0px !important; margin: 0px;">
		<form id="datagrid-form" method="post"
			style="padding: 15px 0px 0px 20px;">
			<div>
				发卡行名称： <input type="text" id="bankName" name="bankName"
					placeholder="发卡行名称" class="easyui-validatebox" style="width: 150px"
					maxlength="100" /> 状态： <select>
					<option value="">全部</option>
					<option value="1">启用</option>
					<option value="0">关闭</option>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
					class="easyui-linkbutton icon-search-btn" iconCls="icon-search"
					id="btn-search">查询</a> <a class="easyui-linkbutton icon-reload-btn"
					iconCls="icon-reload" id="btn-reset">重置</a> <a
					class="easyui-linkbutton" iconCls="icon-reload" id="btn-refresh">刷新</a>
			</div>

		</form>



		<div style="background: #efefef; padding: 5px; height: 25px;">
			<div style="float: left; font-size: 14px; margin-left: 5px;">银行信息管理</div>
			<div style="float: right; margin-right: 57px;">
				<a class="easyui-linkbutton icon-add-btn" iconCls="icon-add"
					id="btn-add">新增银行卡</a> <a class="easyui-linkbutton icon-add-btn"
					iconCls="icon-ok" id="btn-using">启用</a> <a
					class="easyui-linkbutton icon-remove-btn" iconCls="icon-no"
					id="btn-close">关闭</a> <a class="easyui-linkbutton"
					iconCls="icon-save" id="btn-excel">导入EXCEL表</a>

			</div>
		</div>

	</div>
	
	<form name="importForm" method="post" id="importForm"
			action="bankInformation/uploadBankExcel" enctype="multipart/form-data" >
			<br/> <br/> <input type="file" name="cardFile" id="cardFile" />
			<br/><br/>
	</form>
</body>
<script type="text/javascript">
	function optformat(value, row, index) {
		var html = "";
		html += "<a class='operate' href='javascript:;' onclick=view('"
				+ row.id + "');>查看</a>";
		html += "<a class='operate' href='javascript:;' onclick=edit('"
				+ row.id + "');>修改</a>&nbsp;";
		html += "<a class='operate' href='javascript:;' onclick=deleteObj('"
				+ row.id + "');>删除</a>";
		return html;
	}
</script>
<script type="text/javascript" charset="utf-8">
	var imgHostUrl = "${imgHostUrl}";
</script>
<script type="text/javascript"
	src="${CONTEXT}js/bankInformation/main.js"></script>
</html>