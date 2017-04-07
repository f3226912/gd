<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>

<style type="text/css">
.esDialogMainDiv {
	text-align: center;
	border-top: 1px solid #d3d3d3;
}

.mark {
	color: red;
}

.widget {
	width: 300px;
	height: 20px;
}

.esDialogMainDiv table {
	line-height: 30px;
	width: 97%;
	margin: 0px auto;
}

#esDialogRemarks {
	width: 600px;
	height: 150px;
}

td {
	padding-left: 10px;
}
</style>
<div class="esDialogMainDiv">
	<form id="save-form" method="post" action="grdGdGiftteam/save">
		<input type="hidden" name="id" value="${grdGdGiftteamDTO.id}">
		<div>
			<table>
				<tr>
					<td align="right" width="90"><label class="mark">*</label>团队名称：</td>
					<td align="left"><input id="teamName" name="name" type="text"
						class="widget easyui-validatebox" required="true" maxlength="30"
						missingMessage="团队名称不能为空" value="${grdGdGiftteamDTO.name }"
						placeholder="请输入团队名称"
						<c:if test="${not empty grdGdGiftteamDTO.name}">readonly="readonly"</c:if> /></td>
				</tr>
				<tr>
					<td align="right"><label class="mark">*</label>所属市场：</td>
					<td align="left"><select id="esDialogMarketId" name="marketId"
					
						class="widget easyui-validatebox" required="true"
						missingMessage="必须选择所属市场">
							<option value="">————请选择礼品所属农批市场————</option>
							<c:forEach items="${requestScope.marketDTOs }" var="marketDTO">
								<c:if
									test="${not empty grdGdGiftteamDTO and grdGdGiftteamDTO.marketId == marketDTO.id}">
									<option selected="selected" value="${marketDTO.id }">${marketDTO.marketName }</option>
								</c:if>
								<c:if test="${empty grdGdGiftteamDTO}">
									<option value="${marketDTO.id }">${marketDTO.marketName }</option>
								</c:if>

							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td align="right"><label class="mark">*</label>礼品仓库：</td>
					<td align="left"><select id="esDialogGiftstoreId"
						name="giftstoreId" 
						class="widget easyui-validatebox" required="true"
						missingMessage="必须选择所属仓库">
							<option value="">————请选择团队发放礼品对应的仓库————</option>
							<c:forEach items="${requestScope.grdGdGiftstoreDTOs }"
								var="grdGdGiftstoreDTO">
								<option
									${grdGdGiftstoreDTO.id == grdGdGiftteamDTO.giftstoreId ? "selected" : "" }
									value="${grdGdGiftstoreDTO.id }">${grdGdGiftstoreDTO.name }</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td valign="top" align="right">备注：</td>
					<td align="left"><textarea id="esDialogRemarks" name="remarks"
							maxlength="200">${grdGdGiftteamDTO.remarks }</textarea></td>
				</tr>
			</table>
		</div>
		<br>
		<br>
		<div class="dialog-button">
			<div id="dlg-buttonsEdit">
				<div style="margin-top: 2px;">
					<gd:btn btncode='BTNDTLPCKGL03'>
						<a id="popWinSaveBtn" href="javascript:void(0)"
							class="easyui-linkbutton" data-options="iconCls:'icon-save'"
							onclick="save()">保存</a>
					</gd:btn>
					<a id="popWinCloseBtn" href="javascript:void(0)"
						class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
						onclick="$('#saveDialog').dialog('close');">关闭</a>
				</div>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${CONTEXT}js/grdGdGiftteam/detail.js"></script>