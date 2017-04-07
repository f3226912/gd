<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<form id="save-form">
<input type="hidden" id="id" name="id" value="${dto.id}" />
<div>
	<table style="border: none;width: 100%;">
		 <tr>
				<td><span style="color:red;">*</span>机构代码：</td>
				<td><input type="text" id="bankCode" name="bankCode"  value="${dto.bankCode}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="机构代码必填" style="width: 200px;"></td>
			</tr>
			<tr>
				<td><span style="color: red;">*</span>发卡行名称：</td>
				<td><input type="text" id="bankName" name="bankName"  value="${dto.bankName}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="发卡行名称必填" style="width: 200px;"></td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>发卡行简称：</td>
				<td><input type="text" id="bankShortName" name="bankShortName"  value="${dto.bankShortName}"  class="easyui-validatebox" validType="unnormal"  style="width: 200px;"></td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>发卡行英文简称：</td>
				<td><input type="text" id="bankEShortName" name="bankEShortName"  value="${dto.bankEShortName}"  class="easyui-validatebox" validType="unnormal"  style="width: 200px;"></td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>卡名：</td>
				<td><input type="text" id="cardName" name="cardName"  value="${dto.cardName}"  class="easyui-validatebox" validType="unnormal"  style="width: 200px;"></td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>卡长度：</td>
				<td><input type="text" id="cardLength" name="cardLength"  value="${dto.cardLength}"  class="easyui-validatebox" validType="unnormal"  style="width: 200px;"><span>
卡长度一般为15-20位
				</span></td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>发卡行标识取值：</td>
				<td><input type="text" id="bankSignId" name="bankSignId"  value="${dto.bankSignId}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="发卡行标识取值必填" style="width: 200px;"  onchange="setBankSignLength(this.value)"><span>
				请输入发卡主账号头几位</span></td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>发卡行标识长度：</td>
				<td><input type="text" id="bankSignLength" name="bankSignLength"  value="${dto.bankSignLength}" required="true" class="easyui-validatebox" validType="unnormal" missingMessage="发卡行标识长度必填" style="width: 200px;" readonly="readonly"></td>
			</tr>
			<tr>
				<td><span style="color: red">*</span>卡种:</td>
				<td>
				<select id="cardType" name="cardType" style="" >
				 <option>请选择</option>
				 <option value="借记卡">借记卡</option>
				 <option value="货记卡">货记卡</option>
				 <option value="预付费卡">预付费卡</option>
				 <option value="准货记卡">准货记卡</option>
				</select>
				</td>
			</tr>
			<tr>
				<td><span style="color: red;visibility: hidden;">*</span>银行LOGO</td>
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
													<c:set var="giftPicUrlStr"
														value='[{"url":"${dto.bankLogo }"}]' scope="page" />
												   <script type="text/uploader-files">
													${giftPicUrlStr}
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
	$(function(){
		var cardType=$("#cardType").val("${dto.cardType}");
		var imgHostUrl = "${imgHostUrl}";
	})
</script>
<script type="text/javascript" src="${CONTEXT}js/bankInformation/save.js"></script>