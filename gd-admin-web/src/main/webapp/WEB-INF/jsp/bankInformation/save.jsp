<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<link href="${CONTEXT}css/uploader.css" rel="stylesheet" type="text/css" />
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form" method="post" action="bankInformation/save">
	<div>
		<table style="border: none;width: 100%;">
		    <tr>
				<td><span style="color:red;">*</span>机构代码：</td>
				<td><input type="text" id="bankCode" name="bankCode"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="机构代码必填" style="width: 200px;" maxlength="20"></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>发卡行名称：</td>
				<td><input type="text" id="bankName" name="bankName"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="发卡行名称必填" style="width: 200px;" maxlength="50"></td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>发卡行简称：</td>
				<td><input type="text" id="bankShortName" name="bankShortName"  value=""  class="easyui-validatebox" validType="unnormal"  style="width: 200px;" maxlength="50"></td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>发卡行英文简称：</td>
				<td><input type="text" id="bankEShortName" name="bankEShortName"  value=""  class="easyui-validatebox" validType="unnormal"  style="width: 200px;" maxlength="50"></td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>卡名：</td>
				<td><input type="text" id="cardName" name="cardName"  value=""  class="easyui-validatebox" validType="unnormal"  style="width: 200px;" maxlength="50"></td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>卡长度：</td>
				<td><input type="text" id="cardLength" name="cardLength"  value=""  class="easyui-numberbox easyui-validatebox " validType="unnormal"  style="width: 200px;" maxlength="2"><span>
卡长度一般为15-20位
				</span></td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>发卡行标识取值：</td>
				<td><input type="text" id="bankSignId" name="bankSignId"  value="" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="发卡行标识取值必填" style="width: 200px;" maxlength="20" onchange="setBankSignLength(this.value)"><span>
				请输入发卡主账号头几位</span></td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>发卡行标识长度：</td>
				<td><input type="text" id="bankSignLength" name="bankSignLength"   class="easyui-validatebox" required="true" validType="signLength"  missingMessage="发卡行标识长度必填" style="width: 200px;" maxlength="2" readonly="readonly"></td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>卡种:</td>
				<td>
				<select id="cardType" name="cardType"  class="easyui-validatebox" required="true"  missingMessage="卡种类型必填" >
				 <option value="">请选择</option>
				 <option value="借记卡">借记卡</option>
				 <option value="货记卡">货记卡</option>
				 <option value="预付费卡">预付费卡</option>
				 <option value="准货记卡">准货记卡</option>
				</select>
				</td>
			</tr>
			
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>上传银行LOGO</td>
				<td>
				<div class="user-iteam">
										<div class="fl mr_10"
											style="position: relative; top: 5px; z-index: 99">
											<input type="file" class="g-u input01" id="uploadFirstPic"
												value="" name="giftPicture" /> <input type="hidden"
												id="urlWshowImgFirst" name="bankLogo"
												data-role="carPicturePath" />
										</div>
										<div class="form-context">
											<div class="clear"></div>
											<ul id="pictureShowFirst" class="multPic-box multPic-ul" style="height:135px;width:156px;">
												<c:if test="${not empty dto.bankLogo}">
												   <script type="text/uploader-files">
													${dto.bankLogo}
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
	</div>
</form>
<script type="text/javascript" charset="utf-8">
	var imgHostUrl = "${imgHostUrl}";

</script>
<script type="text/javascript" src="${CONTEXT}js/bankInformation/save.js"></script>