<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<style type="text/css">
.esDialogMainDiv {
	text-align: center;
	min-height: 99%;
	overflow: visible;
	border-top: 1px solid #d3d3d3;
}

.mark {
	color: red;
}

.widget {
	width: 464px;
	height: 20px;
}

.esDialogMainDiv table {
	line-height: 30px;
	width: 97%;
	margin: 0px auto;
}

#remarks {
	width: 550px;
	height: 100px;
}

td {
	padding-left: 10px;
}

.impcel {
	background: #efefef;
	width: 112px;
}

.multPic-ul {
	width: 156px;
	height: 80px;
}

#picTab {
	border-collapse: collapse;
	border-spacing: 0;
	margin-top: -40px;
}

.user-iteam {
	margin-left: -18px;
}

.error-status {
	margin-top: 25px;
}
</style>
<link href="${CONTEXT}css/uploader.css" rel="stylesheet" type="text/css" />
<div class="esDialogMainDiv">
	<form id="save-form" method="post" action="grdGdGift/save">
		<input type="hidden" id="id" name="id" value="${dto.id}" /> <input
			type="hidden" id="maxId" name="maxId" value="${maxId}" />
		<div style="width: 88%; margin: 0px auto;">
			<table>
				<tr>
					<td align="right" width="90"><label class="mark">*</label>礼品编号：</td>
					<td align="left" colspan="3"><input id="giftNo" name="giftNo"
						type="text" class="widget" required="true" maxlength="30"
						placeholder="系统自增长" readonly="readonly" value="${dto.giftNo}" /></td>
				</tr>
				<tr>
					<td align="right" width="90"><label class="mark">*</label>礼品名称：</td>
					<td align="left" colspan="3"><input id="name" name="name"
						type="text" class="widget easyui-validatebox" required="true"
						maxlength="30" missingMessage="礼品名称不能为空" value="${dto.name }"
						placeholder="请输入礼品名称" /></td>
				</tr>
				<tr>
					<td align="right" width="90"><label class="mark">*</label>单位：</td>
					<td align="left"><input id="unit" name="unit" type="text"
						class=" easyui-validatebox" required="true" maxlength="10"
						width="20" missingMessage="单位不能为空" value="${dto.unit }"
						placeholder="个" validType="unit" /></td>
					<td align="right" width="90">规格：</td>
					<td align="left"><input id="spec" name="spec" type="text"
						maxlength="20" width="20" value="${dto.spec }" placeholder="请输入规格" /></td>
				</tr>
				<tr>
					<td align="right" width="90">参考价：</td>
					<td align="left"><input id="rePrice" name="rePrice"
						type="text" class="easyui-numberbox" maxlength="10" width="20"
						value="${dto.rePrice }" placeholder="请输入参考价" precision="2" /></td>
					<td align="right" width="90">起订量：</td>
					<td align="left"><input id="riseCount" name="riseCount"
						type="text" class="easyui-numberbox" maxlength="10" width="20"
						value="${dto.riseCount }" placeholder="请输入起订量" /></td>
				</tr>
				<tr>
					<td align="right" width="90">供货周期：</td>
					<td align="left"><input id="supplyCycle" name="supplyCycle"
						type="text" maxlength="10" width="20" value="${dto.supplyCycle }"
						placeholder="请输入供货周期" /></td>
					<td align="right" width="90"><gd:btn btncode='BTNDTLPLPK06'>最新价格：</gd:btn></td>
					<td align="left"><gd:btn btncode='BTNDTLPLPK06'>
							<label id="newPrice"> <fmt:formatNumber
									value="${dto.newPrice}" pattern="0.00"></fmt:formatNumber>
							</label>
							<input type="hidden" name="newPrice" value="${dto.newPrice}" />
						</gd:btn></td>
				</tr>
				<tr>
					<td valign="top" align="right">备注：</td>
					<td align="left" colspan="3"><textarea id="remarks"
							name="remarks" maxlength="200" style="width: 506px">${dto.remarks }</textarea></td>
				</tr>
				<tr>
					<td align="right" width="90"></label>图片：</td>
					<td colspan="3">
						<table width="550" border="0" height="100%" cellspacing="0"
							cellpadding="0" id="picTab">
							<tr>
								<td>
									<div class="user-iteam">
										<div class="fl mr_10"
											style="position: relative; top: 95px; left: 1px; z-index: 99">
											<input type="file" class="g-u input01" id="uploadFirstPic"
												value="" name="giftPicture" /> <input type="hidden"
												id="urlWshowImgFirst" name="giftImage"
												data-role="carPicturePath" />
										</div>
										<div class="form-context">
											<div class="clear"></div>
											<ul id="pictureShowFirst" class="multPic-box multPic-ul"
												style="height: 120px;">
												<c:if
													test="${not empty dto.giftImages and imgCount>0 and not empty dto.giftImages[0]}">
													<c:set var="giftPicUrlStrFirst"
														value='[{"url":"${dto.giftImages[0] }"}]' scope="page" />
													<script type="text/uploader-files">
													${giftPicUrlStrFirst}
    											</script>
													<script type="text/uploader-theme">
													<li id="queue-file-{id}" class="g-u" data-name="{name}">
                										<div class="pic">
                    										<a href="javascript:void(0);" style="display:block;"><img class="J_Pic_{id} preview-img" src="" style="height:100%"/></a>
                										</div>
                										<div class=" J_Mask_{id} pic-mask"></div>
                										<div class="status-wrapper">
                    										<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    										<div class="status start-status progress-status success-status">
                        									<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>加载中...</div>
                    									</div>
                    									<div class="status error-status">
                        										<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                										</div>
                										<div class="imageUploader_menu"><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            										</li>
    											</script>
												</c:if>
											</ul>
										</div>
									</div>
								</td>
								<td>
									<div class="user-iteam">
										<div class="fl mr_10"
											style="position: relative; top: 95px; left: 1px; z-index: 99">
											<input type="file" class="g-u input01" id="uploadSecondPic"
												value="" name="giftPicture" /> <input type="hidden"
												id="urlWshowImgSecond" name="giftImage"
												data-role="carPicturePath" />
										</div>
										<div class="form-context">
											<div class="clear"></div>
											<ul id="pictureShowSecond" class="multPic-box multPic-ul"
												style="height: 120px;">
												<c:if
													test="${not empty dto.giftImages and imgCount>1 and not empty dto.giftImages[1]}">
													<c:set var="giftPicUrlStrSecond"
														value='[{"url":"${dto.giftImages[1] }"}]' scope="page" />
													<script type="text/uploader-files">
													${giftPicUrlStrSecond}
    											</script>
													<script type="text/uploader-theme">
													<li id="queue-file-{id}" class="g-u" data-name="{name}">
                										<div class="pic">
                    										<a href="javascript:void(0);" style="display:block;"><img class="J_Pic_{id} preview-img" src="" style="height:100%"/></a>
                										</div>
                										<div class=" J_Mask_{id} pic-mask"></div>
                										<div class="status-wrapper">
                    										<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    										<div class="status start-status progress-status success-status">
                        									<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>加载中...</div>
                    									</div>
                    									<div class="status error-status">
                        										<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                										</div>
                										<div class="imageUploader_menu"><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            										</li>
    											</script>
												</c:if>
											</ul>
										</div>
									</div>
								</td>
								<td>
									<div class="user-iteam">
										<div class="fl mr_10"
											style="position: relative; top: 95px; left: 1px; z-index: 99">
											<input type="file" class="g-u input01" id="uploadThirdPic"
												value="" name="giftPicture" /> <input type="hidden"
												id="urlWshowImgThird" name="giftImage"
												data-role="carPicturePath" />
										</div>
										<div class="form-context">
											<div class="clear"></div>
											<ul id="pictureShowThird" class="multPic-box multPic-ul"
												style="height: 120px;">
												<c:if test="${not empty dto.giftImages and imgCount>2}">
													<c:set var="giftPicUrlStrThird"
														value='[{"url":"${dto.giftImages[2] }"}]' scope="page" />
													<script type="text/uploader-files">
													${giftPicUrlStrThird}
    											</script>
													<script type="text/uploader-theme">
													<li id="queue-file-{id}" class="g-u" data-name="{name}">
                										<div class="pic">
                    										<a href="javascript:void(0);" style="display:block;"><img class="J_Pic_{id} preview-img" src="" style="height:100%"/></a>
                										</div>
                										<div class=" J_Mask_{id} pic-mask"></div>
                										<div class="status-wrapper">
                    										<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    										<div class="status start-status progress-status success-status">
                        									<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>加载中...</div>
                    									</div>
                    									<div class="status error-status">
                        										<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                										</div>
                										<div class="imageUploader_menu"><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            										</li>
    											</script>
												</c:if>
											</ul>
										</div>
									</div>
								</td>
							</tr>
						</table>

					</td>
				</tr>

			</table>
		</div>
		<div class="dialog-button">
			<div id="dlg-buttonsEdit">
				<div style="margin-top: 2px;">
					<gd:btn btncode='BTNDTLPLPK03'>
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
<script type="text/javascript" charset="utf-8">
	var imgHostUrl = "${imgHostUrl}";
</script>
<script type="text/javascript" src="${CONTEXT}js/grdGdGift/detail.js"></script>
