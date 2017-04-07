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
<title>GrdProSupplyofgoodHandoutEntity-Main-代码生成器出品，请酌情修改
	by@lidong</title>
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
						<td>所属市场 :</td>
						<td>
							<select name="marketId" id="marketId" style="width:150px;height:20px;margin-right: 10px;">
								<option value="" selected="selected">全部</option>
							</select>
							</td>
					
						<td>地推姓名 :</td>
						<td><input type="text" id="grdUserName" name="grdUserName"
							placeholder="地推姓名" class="easyui-validatebox"
							style="width: 150px; margin-right: 10px;" maxlength="30"></td>
						<td>地推手机 :</td>
						<td colspan="1"><input type="text" id="grdMobile"
							name="grdMobile" placeholder="地推手机" class="easyui-validatebox"
							style="width: 150px; margin-right: 10px;" maxlength="11">
							</td>
						<td></td>
						<td>
							<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search"
							id="btn-search">查询</a>
							<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload"
							id="btn-reset">重置</a>
						
						</td>
					</tr>
					<tr>
						<td>货源状态 :</td>
						<td><select id="status" name="status" style="width: 150px; margin-right: 10px;"
							>
								<option value="">全部</option>
								<option value="1,2">已发布</option>
								<option value="1">已删除</option>
								<option value="3">已接单</option>
								<option value="4">已过期</option>
						</select></td>
						<td>发布人姓名 :</td>
						<td><input type="text" id="publisher" name="publisher"
							placeholder="发布人姓名" class="easyui-validatebox"
							style="width: 150px; margin-right: 10px;"></td>
						<td>发布人手机 :</td>

						<td><input type="text" id="mobile" name="mobile"
							placeholder="发布人手机" class="easyui-validatebox"
							style="width: 150px; margin-right: 10px;"></td>
						<td>发布时间 :</td>
						<td colspan="1"><input type="text" id="startDate"
							name="startDate"
							onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt:'yyyy-MM-dd'})"
							style="width: 75px">~ <input type="text" id="endDate"
							name="endDate"
							onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}', dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}', dateFmt:'yyyy-MM-dd'})"
							style="width: 75px">
						</td>
					</tr>
					<tr>
					<td align="right">线路类型：</td>
					<td>
						<select name="sourceType" id="sourceType" style="width:150px;height:20px;margin-right: 10px;">
							<option value="" selected="selected">全部</option>
							<option value="1" >干线</option>
							<option value="2" >同城</option>
						</select>
					</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					</tr>
				</table>
			</div>
		</form>
		<div>
			<div style="background: #efefef; padding: 5px; height: 25px;">

				<div style="float: left;font-size:16px;margin-left:5px;">货源信息列表</div>
				<gd:btn btncode='BTNFBHYTJ01'>
					<div style="float: right; margin-right: 57px;">
						<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
					</div>
				</gd:btn>
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

	function optformat(value, row, index) {
		var html = "";
		html += "<a class='operate' href='javascript:;' onclick=view('"
				+ row.id + "');>查看</a>";
		html += "<a class='operate' href='javascript:;' onclick=edit('"
				+ row.id + "');>修改</a>&nbsp;";
		html += "<a class='operate' href='javascript:;' onclick=delete('"
				+ row.id + "');>删除</a>";
		return html;
	}
	var disableExport = false;

	/***数据导出功能***/
	$("#exportData")
			.click(
					function() {
						var marketId = $('#datagrid-form #marketId').val();
						var teamId = $('#datagrid-form #teamGroup').val();
						var grdUserName = $('#datagrid-form #grdUserName')
								.val();
						var grdMobile = $('#datagrid-form #grdMobile').val();
						var status = $('#datagrid-form #status').val();
						var publisher = $('#datagrid-form #publisher').val();
						var mobile = $('#datagrid-form #mobile').val();
						var startDate = $('#datagrid-form #startDate').val();
						var endDate = $('#datagrid-form #endDate').val();
						var sourceType = $('#datagrid-form #sourceType').val();
						var isDeleted;
						if(status&&status=="1"){
							isDeleted=status;
							status="";
						};
						var params = {
							"marketId" : marketId,
							"teamId" : teamId,
							"grdUserName" : grdUserName,
							"grdMobile" : grdMobile,
							"status" : status,
							"publisher" : publisher,
							"mobile" : mobile,
							"startDate" : startDate,
							"endDate" : endDate,
							"isDeleted":isDeleted,
							"sourceType":sourceType
						};
						var paramList = gudeng.commons
								.convertParamsToDelimitedList(params);
						$
								.ajax({
									url : CONTEXT
											+ 'grdProSupplyofgoodHandout/checkExportParams',
									data : params,
									type : 'post',
									success : function(data) {
										//检测通过
										if (data && data.status == 1) {
											/* $("#Loading2").show(); */
											if (!disableExport) {
												slideMessage("数据正在导出中, 请耐心等待...");
												disableExport = true;
												//启动下载
												$
														.download(
																CONTEXT
																		+ 'grdProSupplyofgoodHandout/exportData',
																paramList,
																'post');
											} else {
												slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
											}
										} else {
											warningMessage(data.message);
										}
									},
									error : function(data) {
										warningMessage("error : " + data);
									}
								});
					});
</script>
<script type="text/javascript"
	src="${CONTEXT}js/grdProSupplyofgoodHandout/main.js"></script>
</html>