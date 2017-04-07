<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<form id="addForm" method="post" action="">
<input type="hidden" value="${dto.adType }${adSpaceDTO.adType}" id="adType22"/>
<input type="hidden" value="${dto.adSpaceId }${adSpaceDTO.id}" name="adSpaceId"/>
<input type="hidden" id="optionType" name="option" value="${option }">
<input type="hidden" id="id" name="id" value="${dto.id }">
<input type="hidden" id="categoryId1" value="${dto.categoryId1}">
<input type="hidden" id="categoryId2" value="${dto.categoryId2}">
<input type="hidden" id="categoryId3" value="${dto.categoryId3}">
<input type="hidden" name="state" value="${dto.state}"/>
	<div id="addFormDiv">
		<table>
			<tr>
				<td><font color="red">*</font>广告名称:</td>
				<td>
					<input type="text" id="adName2" class="easyui-validatebox" missingMessage="广告名称不能为空" maxlength="20"
					validType="unnormal" name="adName" required="true" style="width: 350px" value="${dto.adName}">
				</td>
			</tr>
			<tr id="tr-jumpType2">
				<td><font color="red">*</font>跳转类型:</td>
				<td>
					<select name="jumpType" id="jumpType2" style="width: 150px;">
						<option value="1" <c:if test="${dto.jumpType eq '1' }">selected</c:if>>类目-商品列表</option>
						<option value="2" <c:if test="${dto.jumpType eq '2' }">selected</c:if>>商铺首页</option>
						<option value="3" <c:if test="${dto.jumpType eq '3' }">selected</c:if>>URL</option>
						<option value="5" <c:if test="${dto.jumpType eq '5' }">selected</c:if>>商品标签</option>
						<option value="6" <c:if test="${dto.jumpType eq '6' }">selected</c:if>>商品详情</option>
						<option value="7" <c:if test="${dto.jumpType eq '7' }">selected</c:if>>专题活动</option>
					</select>
				</td>
			</tr>
			<tr class="tr-time">
				<td><font color="red">*</font>开始时间:</td>
				<td>
				<input type="text" id="startTime2" value="${dto.startTimeStr}"
					onFocus="WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime2\')}',dateFmt:'yyyy-MM-dd'})"    
					onClick="WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime2\')}',dateFmt:'yyyy-MM-dd'})" 
					name="startTimeStr" style="width: 150px;" />
				<font color="red">*</font>截至时间:
				<input type="text" id="endTime2" value="${dto.endTimeStr}"
					onFocus="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime2\')}',dateFmt:'yyyy-MM-dd'})"    
					onClick="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime2\')}',dateFmt:'yyyy-MM-dd'})" 
					name="endTimeStr" style="width: 150px;" />
				</td>
			</tr>
			<tr id="tr-marketId" style="display: none;">
				<td>广告位所属市场:</td>
				<td>
					<select name="marketId" id="marketId2" style="width: 150px;"></select>
				</td>
			</tr>
			<tr id="tr-productType" style="display: none;">
				<td>跳转类目:</td>
				<td>
					<select id="productType1"><option value="">请选择</option></select>
					<select id="productType2"><option value="">请选择</option></select>
					<select id="productType3"><option value="">请选择</option></select>
					<input type="hidden" id="categoryId" name="categoryId" value="${dto.categoryId}">
				</td>
			</tr>
			<tr id="tr-productSign" style="display: none;">
				<td>商品标签:</td>
				<td>
					<select id="productSign" name="productSign"></select>
				</td>
			</tr>
			<tr id="tr-productId2" style="display: none;">
				<td>选择商品:</td>
				<td>
					<input type="hidden" id="productId2" name="productId" value="${dto.productId}">
					<div id="proview2" closed="true"></div>
					<div id="proview" style="display: none;">已选产品:id:${dto.productId}</div>
					<input type="button" id="showProWin2" value="选择产品">&nbsp;<font color="red" id="fontid2"></font>
				</td>
			</tr>
			<tr id="adType2Sel1" style="display: none;">
				<td><font color="red">*</font>广告文字:</td>
				<td>
					<input type="text" id="adWord"  maxlength="50" name="adWord" required="true" style="width: 350px" value="${dto.adWord }">
				</td>
			</tr>
			<tr id="adType2Sel2" style="display: none;">
				<td>上传图片:</td>
				<td>
					<div class="user-iteam">
				        <div class="fl mr_10" id="uploadContainer">
				            <input type="file" class="g-u input01" id="upload_btn1" value="" name="productPicture" />
				            <input type="hidden" id="J_Urls1" name="adUrl" value="${dto.adUrl}"/>&nbsp;<font color="red">*</font>
				        </div>
				        <div class="form-context">
				            <div class="clear"></div>
				            <ul id="picture_queen1" class="grid">
				            <c:if test="${!empty dto.adUrl }">
				            	<c:set var="adUrlStr" value='[{"url":"${dto.adUrl }"}]' />
				            	<script type="text/uploader-files">
								${adUrlStr}
    							</script>
				            </c:if>
				            <script type="text/uploader-theme">
        
					<li id="queue-file-{id}" class="g-u" data-name="{name}">
                		<div class="pic">
                    		<a href="javascript:void(0);"><img class="J_Pic_{id} preview-img" src="" /></a>
                		</div>
                		<div class=" J_Mask_{id} pic-mask"></div>
                			<div class="status-wrapper">
                    			<div class="status waiting-status"><p>等待上传，请稍候</p></div>
                    			<div class="status start-status progress-status success-status">
                        		<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>上传中...</div>
                    		</div>
                    		<div class="status error-status">
                        	<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                		</div>
                		<div class="imageUploader_menu"> <a href="javascript:void(0);" class="imageUploader_but1" id="imageUploader_but1_{id}"  title="左移">&nbsp;</a><a href="javascript:void(0);" class="imageUploader_but2" title="右移"  id="imageUploader_but2_{id}">&nbsp;</a><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
            		</li>
    					</script>
				            </ul>
				        </div>
					</div>
				</td>
			</tr>
			<tr class="tr-jumpUrl">
				<td>跳转地址:</td>
				<td><input type="text" id="adLinkUrl2" validType="urlCheck" class="easyui-validatebox" invalidMessage="请输入正确的url地址" 
					 name="jumpUrl" style="width: 350px" value="${dto.jumpUrl}"></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td>状态:</td> -->
<!-- 				<td><select name="state" style="width: 150px;"> -->
<%-- 					<option value="2" <c:if test="${dto.state==2 }">selected</c:if>>等待</option> --%>
<%-- 					<option value="1" <c:if test="${dto.state==1 }">selected</c:if>>上架</option> --%>
<%-- 					<option value="3" <c:if test="${dto.state==3 }">selected</c:if>>到期</option> --%>
<%-- 					<option value="4" <c:if test="${dto.state==4 }">selected</c:if>>下架</option> --%>
<!-- 				</select></td> -->
<!-- 			</tr> -->
		</table>
		<div></div>
		<div></div>
	</div>
</form>
<div id='Loading' style="position: absolute;opacity: 0.5;filter:Alpha(opacity=50); z-index: 10000; top: 0px; left: 0px;  
    width: 100%; height: 100%; background: gray; text-align: center;display: none;">  
    <h1 style="top: 48%; position: relative;">  
        <font color="#15428B">数据正在提交，请稍候···</font>
    </h1>  
</div>
<script type="text/javascript" >
var option=$("#optionType").val();
var imgHostUrl='${imgHostUrl}';
var masterUrl = CONTEXT+'pushV2/uploadProductPic/';
var adUrl = $("#J_Urls").val();
var marketId='${dto.marketId}';
var categoryId1 = $("#categoryId1").val();
var categoryId2 = $("#categoryId2").val();
var categoryId3 = $("#categoryId3").val();
//商品标签
var productSign='${dto.productSign}';

</script>
<script type="text/javascript" src="${CONTEXT}js/push-v2/adinfo-add.js"></script>