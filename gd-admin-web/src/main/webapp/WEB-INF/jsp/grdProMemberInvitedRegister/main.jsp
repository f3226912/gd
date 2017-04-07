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
<title>邀请会员注册列表</title>
</head>
<script type="text/javascript"
	src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script>
<body>
	<table id="datagrid-table" title=""></table>
	<div id="datagrid-tool-bar"
		style="height: auto; padding: 0px !important; margin: 0px;">
		<form id="datagrid-form" method="post" method="post"
			style="padding: 5px 0px 5px 20px;">
			<div>
				<table>
					<tr>
						<td align="right">&nbsp;所属市场：</td>
						<td><select name="marketId" id="marketId"
							style="width: 150px; height: 20px; margin-right: 10px;">
								<option value="" selected="selected">全部</option>
						</select></td>
						<!-- <td align="right">&nbsp;团 队：</td>
						<td ><select id="teamGroup" name="teamGroup"
							class="easyui-combobox" style="width: 150px;">
								<option value="">全部</option>
						</select></td> -->
						<td align="right">&nbsp;地推姓名：</td>
						<td><input type="text" id="grdUserName" name="grdUserName"
							placeholder="地推姓名" class="easyui-validatebox" maxlength="30"
							style="width: 160px; margin-right: 10px;"></td>
						<td align="right">&nbsp;地推手机：</td>
						<td><input type="text" id="grdMobile" name="grdMobile"
							placeholder="地推手机" class="easyui-validatebox"
							style="width: 165px; margin-right: 10px;" maxlength="11"></td>
						<td align="right">会员手机号码：</td>
						<td><input type="text" id="memberMobile" name="memberMobile"
							placeholder="会员手机号码" class="easyui-validatebox"
							style="width: 160px; margin-right: 10px;" maxlength="11"></td>
					<tr>
						<td align="right">&nbsp;业务类型：</td>
						<td align="right">
							<select id="type" name="type" style="width: 150px; margin-right: 10px;">
								<option value="">全部</option>
								<option value="1">农批</option>
								<option value="2">农速通</option>
							</select>
						</td>
						<td align="right">&nbsp;注册来源：</td>
						<td><select id="regetype" name="regetype"
							style="width: 150px; margin-right: 10px;">
								<option value="">全部</option>
								<option value="0">管理后台</option>
								<option value="1">谷登农批网</option>
								<option value="2">农速通(旧)</option>
								<option value="3">农商友</option>
								<option value="4">农商友-农批商</option>
								<option value="5">农批友</option>
								<option value="6">供应商</option>
								<option value="7">POS刷卡</option>
								<option value="8">微信授权</option>
								<option value="9">农速通-货主</option>
								<option value="10">农速通-司机</option>
								<option value="11">农速通-物流公司</option>
						</select></td>
						<td align="right">&nbsp;注册时间：</td>
						<td colspan="2"><input type="text" id="startDate"
							name="startDate"
							onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd'})"
							style="width: 75px">~ <input type="text" id="endDate"
							name="endDate"
							onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd'})"
							onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd'})"
							style="width: 75px"></td>

						<td colspan="5"><a class="easyui-linkbutton icon-search-btn"
							iconCls="icon-search" id="btn-search">查询</a> <a
							class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload"
							id="btn-reset">重置</a> <a class="easyui-linkbutton"
							iconCls="icon-reload" id="btn-refresh">刷新</a></td>
					</tr>
				</table>
			</div>
		</form>
		<div style="background: #efefef; padding: 5px; height: 25px;">
			<div style="float: left; font-size: 16px; margin-left: 5px;">会员列表</div>
			<gd:btn btncode='BTNGRZCTJ01'>
				<div style="float: right; margin-right: 57px;">
					<a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
				</div>
			</gd:btn>
		</div>
	</div>
</body>
<script type="text/javascript">
	function initMarket(marketType) {
		$.ajax({
			type : "GET",
			url : "${CONTEXT }market/queryByType/" + marketType,
			dataType : "json",
			success : function(data) {
				var markets = data.rows;
				if (markets.length != 0) {
					$('#marketId').empty();
					$('#marketId').append("<option value=''>全部</option>");
					for (var n = 0; n < markets.length; n++) {
						$('#marketId').append(
								"<option value='"+markets[n].id+"'>"
										+ markets[n].marketName + "</option>");
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
						var teamGroup = $('#datagrid-form #teamGroup').val();
						var grdMobile = $('#datagrid-form #grdMobile').val();
						var grdUserName = $('#datagrid-form #grdUserName')
								.val();
						var regetype = $('#datagrid-form #regetype').val();
						var startDate = $('#datagrid-form #startDate').val();
						var endDate = $('#datagrid-form #endDate').val();
						var type = $('#datagrid-form #type').val();
						var params = {
							"marketId" : marketId,
							"teamId" : teamGroup,
							"grdMobile" : grdMobile,
							"grdUserName" : grdUserName,
							"regetype" : regetype,
							"startDate" : startDate,
							"type" : type,
							"endDate" : endDate
						};
						var paramList = gudeng.commons
								.convertParamsToDelimitedList(params);
						$
								.ajax({
									url : CONTEXT
											+ 'grdProMemberInvitedRegister/checkExportParams',
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
																		+ 'grdProMemberInvitedRegister/exportData',
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
	src="${CONTEXT}js/grdProMemberInvitedRegister/main.js"></script>
</html>