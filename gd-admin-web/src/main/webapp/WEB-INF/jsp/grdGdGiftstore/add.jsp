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

#remarks {
	width: 600px;
	height: 150px;
}

td {
	padding-left: 10px;
}
</style>
<div class="esDialogMainDiv">
	<form id="save-form" method="post" action="grdGdGiftstore/save">
		<input type="hidden" name="id" value="${grdGdGiftstoreDTO.id}">
		<div>
			<table>
				<tr>
					<td align="right" width="90"><label class="mark">*</label>仓库名称：</td>
					<td align="left"><input id="name" name="name" type="text"
						class="widget easyui-validatebox" required="true" maxlength="30"
						missingMessage="仓库名称不能为空" value="${grdGdGiftstoreDTO.name }"
						placeholder="请输入仓库名称" /></td>
				</tr>
				<tr>
					<td align="right"><label class="mark">*</label>所属市场：</td>
					<td align="left"><select id="marketId" name="marketId"
						
						class="widget easyui-validatebox" required="true"
						missingMessage="必须选择所属市场">
							<option value="">————请选择仓库所属农批市场————</option>
							<c:forEach items="${marketDTOs }" var="marketDTO">
								<c:if
									test="${not empty grdGdGiftstoreDTO and grdGdGiftstoreDTO.marketId == marketDTO.id}">
									<option selected="selected" value="${marketDTO.id }">${marketDTO.marketName }</option>
								</c:if>
								<c:if test="${empty grdGdGiftstoreDTO}">
									<option  value="${marketDTO.id }">${marketDTO.marketName }</option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td align="right" width="90"><label class="mark">*</label>使用业务范围：</td>
					<td align="left"><input id="nptype" name="type" type="radio" value="1" checked/>农批&nbsp;&nbsp;			
						<input id="nsttype" name="type" type="radio" value="2"/>农速通</td>
				</tr>
				<tr>
					<td valign="top" align="right">备注：</td>
					<td align="left"><textarea id="remarks" name="remarks"
							maxlength="200">${grdGdGiftstoreDTO.remarks }</textarea></td>
				</tr>
			</table>
		</div>
	</form>
</div>
