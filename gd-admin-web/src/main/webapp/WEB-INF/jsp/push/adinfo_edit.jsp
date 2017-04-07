<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="editForm" method="post" action="push/adInfoSaveEdit">
	<div>
		<input type="hidden" id="id" name="id" value="${dto.id}" />
		<table>
			<tr>
				<td>广告名称:</td>
				<td><input type="text" id="adName" required="true" class="easyui-validatebox" missingMessage="广告名称不能为空"
					 validType="unnormal" value="${dto.adName}" name="adName" required="true" style="width: 150px">&nbsp;<font color="red">*</font>
					<input type="hidden" id="categoryId1" value="${dto.categoryId1}">
					<input type="hidden" id="categoryId2" value="${dto.categoryId2}">
					<input type="hidden" id="categoryId3" value="${dto.categoryId3}">
					</td>
			</tr>
			<tr>
				<td>广告渠道:</td>
				<td><select name="adCanal" id="adCanal" style="width: 150px;">
						<option value="01" <c:if test="${dto.adCanal==01 }">selected</c:if>>农批web</option>
						<option value="02" <c:if test="${dto.adCanal==02 }">selected</c:if>>农商友</option>
						<option value="03" <c:if test="${dto.adCanal==03 }">selected</c:if>>农速通</option>
				</select></td>
			</tr>
			<tr>
				<td>广告类型:</td>
				<td><select name="adType" id="adType" style="width: 150px;">
						<option value="01" <c:if test="${dto.adType==01 }">selected</c:if>>轮播图</option>
						<option value="02" <c:if test="${dto.adType==02 }">selected</c:if>>产品推送</option>
						<option value="03" <c:if test="${dto.adType==03 }">selected</c:if>>副图</option>
						<option value="04" <c:if test="${dto.adType==04 }">selected</c:if>>全国农贸市场图</option>
				</select></td>
			</tr>
			<tr>
				<td>农贸市场:</td>
				<td>
					<!-- <input id="marketIdType" ><input type="hidden" id="marketId" name="marketId" value="${dto.marketId}"> -->
					<select name="marketId" id="marketId" style="width: 150px;">
						<c:forEach items="${marketList}" var="market" varStatus="s">
							<option value="${market.id }" <c:if test="${dto.marketId==market.id }">selected</c:if>>${market.marketName }</option>
						</c:forEach>
					</select>
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>开始时间:</td>
				<td><input type="text" id="startTime" value="${dto.startTimeStr}"
					onFocus="WdatePicker({onpicked:function(){startTime.focus();},minDate:'%y-%M-%d %H:%m:%s',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
					onClick="WdatePicker({onpicked:function(){startTime.focus();},minDate:'%y-%M-%d %H:%m:%s',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					 name="startTimeStr" style="width: 150px;" /></td>
			</tr>
			<tr>
				<td>截至时间:</td>
				<td><input type="text" id="endTime" value="${dto.endTimeStr}"
					onFocus="WdatePicker({onpicked:function(){startDate.focus();},minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
					onClick="WdatePicker({onpicked:function(){startDate.focus();},minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					 name="endTimeStr" style="width: 150px;" /></td>
			</tr>
			<tr>
				<td>推送类目:</td>
				<td>
					<select id="productType1"><option value="-1">请选择</option></select>
					<select id="productType2"><option value="-1">请选择</option></select>
					<select id="productType3"><option value="-1">请选择</option></select>
					<input type="hidden" id="categoryId" name="categoryId" value="${dto.categoryId}">
				</td>
			</tr>
			<tr>
				<td>产品:</td>
				<td>
					<input type="hidden" id="productId" name="productId" value="${dto.productId}">
					<div id="proview">已选产品:id:${dto.productId}</div>
					<input type="button" id="showProWin" value="选择产品">&nbsp;<font color="red" id="fontid"></font>
				</td>
			</tr>
			<tr>
				<td>排序:</td>
				<td><input type="text" id="sort"
					value="${dto.sort}" name="sort" class="easyui-validatebox" required="true" missingMessage="排序不能为空" style="width: 150px">&nbsp;<font color="red">*</font>
					</td>
			</tr>
			<tr>
				<td>上传图片:</td>
				<td>
					<div class="user-iteam">
				        <div class="fl mr_10" id="uploadContainer">
				            <input type="file" class="g-u input01" id="upload_btn" value="" name="productPicture" />
				            <input type="hidden" id="J_Urls" name="adUrl" value="${dto.adUrl}"/>&nbsp;<font color="red">*</font>
				        </div>
				        <div class="form-context">
				            <div class="clear"></div>
				            <ul id="picture_queen" class="grid">
				            	<c:set var="adUrlStr" value='[{"url":"${dto.adUrl }"}]' />
				            	
				            	<script type="text/uploader-files">
								${adUrlStr}
    							</script>
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
				        <BR>
					</div>
				</td>
			</tr>
			<tr>
				<td>跳转地址:</td>
				<td><input type="text" id="adLinkUrl" validType="urlCheck" class="easyui-validatebox" invalidMessage="请输入正确的url地址" 
					value="${dto.adLinkUrl}" name="adLinkUrl" style="width: 150px"></td>
			</tr>
			<tr>
				<td>状态:</td>
				<td><select name="state" style="width: 150px;">
						<option value="01" <c:if test="${dto.state==01 }">selected</c:if>>开始</option>
						<option value="02" <c:if test="${dto.state==02 }">selected</c:if>>截至</option>
						<option value="03" <c:if test="${dto.state==03 }">selected</c:if>>下架</option>
				</select></td>
			</tr>
		</table>
		<div></div>
		<div></div>
	</div>
</form>
<script type="text/javascript" >
var masterUrl = CONTEXT+'push/uploadProductPic/';
var adUrl = $("#J_Urls").val();
var marketId = $("#marketId").val();
var categoryId1 = $("#categoryId1").val();
var categoryId2 = $("#categoryId2").val();
var categoryId3 = $("#categoryId3").val();
$(document).ready(function(){
    updatePtype1(marketId);
   	if(categoryId1 != '' && null != categoryId1 && '-1' != categoryId1){
   		updatePtype2(categoryId1);
   	}
   	if(categoryId2 != '' && null != categoryId2 && '-1' != categoryId2){
   		updatePtype3(categoryId2);
   	}
    
    /* var initurl = '${imgHostUrl}' + adUrl;
    var filesStr = '[{"dataOriginalUrl":"'+initurl+'","url":"'+initurl+'"}]'; */
    initUploadModule("upload_btn",masterUrl, "picture_queen", "J_Urls",1,'',1);
    
	$('#showProWin').click(function(){
		var categoryIdval = $("#categoryId").val();
		$('#proDialog').dialog({'title':'选择产品', 'width':700, 'height':300, 'href':CONTEXT+'push/proInitList?categoryId=' + categoryIdval + '&selectType=1'}).dialog('open');
	});
	
	$('#marketId').change(function(){
		categoryId1 = "";
		categoryId2 = "";
		categoryId3 = "";
		updatePtype1($(this).val());
   		$("#productType2").empty();
	 	$("#productType2").append($("<option/>").text("--请选择--").attr("value","-1"));
	 	$("#productType3").empty();
	 	$("#productType3").append($("<option/>").text("--请选择--").attr("value","-1"));
   		$("#categoryId").val("-1");
   		$("#productId").val("");
   		$("#proview").html("");
   		$("#adLinkUrl").val("");
	});
	
	$("#adType").change(function(){
		var adTypeval = $("#adType").val();
		var adCanalval = $("#adCanal").val();
		if(("02" == adCanalval || "03" == adCanalval) && "02" == adTypeval){
			$("#fontid").html("*");
		}else{
			$("#fontid").html("");
		}
	});
	
	$("#productType1").change(function () {
		categoryId1 = "";
		categoryId2 = "";
		categoryId3 = "";
		$("#categoryId").val($(this).val());
		$("#productId").val("");
		$("#proview").html("");
		$("#adLinkUrl").val("");
		updatePtype2($(this).val());
		$("#productType3").empty();
	 	$("#productType3").append($("<option/>").text("--请选择--").attr("value","-1"));
    });
	$("#productType2").change(function () {
		categoryId1 = "";
		categoryId2 = "";
		categoryId3 = "";
		if("-1" != $(this).val()){
			$("#categoryId").val($(this).val());
		}else{
			$("#categoryId").val($("#productType1").val());
		}
		$("#productId").val("");
		$("#proview").html("");
		$("#adLinkUrl").val("");
		updatePtype3($(this).val());
    });
	$("#productType3").change(function () {
		categoryId1 = "";
		categoryId2 = "";
		categoryId3 = "";
		if("-1" != $(this).val()){
			$("#categoryId").val($(this).val());
		}else{
			$("#categoryId").val($("#productType2").val());
		}
		$("#productId").val("");
		$("#proview").html("");
		$("#adLinkUrl").val("");
    });
});


function initUploadModule(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr, certification){
	var config_mutiple = {
	        galleryUrl : CONTEXT+"js/uploader/",//控件静态地址
	        uploadBtn : uploadBtn,//初始的button
	        queueId : queueId,//图片装载容器id
	        JUrls : JUrls,//成功后返回url参数写入id
	        action : uploadUrl ,//上传地址
	        max : pictureCountLimit,//允许上传个数
	        type :"ajax",//上传类型
	        imgUrlPrefix : '${imgHostUrl}',
	        filesStr : filesStr,//此处是附加参数，已经上传需要第二次修的图片参数
	        certification : certification//此处是附加参数，已经上传需要第二次修，是否可以修改
	};
	gdKissySingleUploader.init(config_mutiple ,function(uploader){
	     var filesStr = config_mutiple.filesStr;
	     var certification = config_mutiple.certification;
	     if(filesStr != ''&&filesStr!=undefined){
	        var fileList = eval("("+  filesStr + ")");
	        
	        gdKissySingleUploader.addFiles(fileList,uploader);
	        
	        if(certification == 0){
	                $("#"+JUrls).parent().find(".file-input").attr("disabled","disabled");
	        }else{
	        	/**可以修改时注册删除事件**/
	        	$(".form-context").delegate(".imageUploader_del","click",function(){
	        		$("#J_Urls").val("");
				    $(this).parents("li").get(0).remove();
				    var originalUrl = $(this).parent().siblings().find("a img").attr("data-original-url");
				    $("input[name*='"+JUrls+"']").each(function(){
				        if($(this).val()==originalUrl){
				            $(this).val("");
				        }
				    });
				});
	        }
	     }
	},function(url){
 	    
	});
}

function checkedit(){
	if(!$("#editForm").form('validate')){
		return $("#editForm").form('validate');
	}
	
	var adTypeval = $("#adType").val();
	var adCanalval = $("#adCanal").val();
	if("02" == adCanalval || "03" == adCanalval){
		if("03" == adTypeval){
			alert("农商友和农速通不能选择副图,请选择其他广告类型!");
			return false;
		}
		if("02" == adTypeval){
			var pval = $("#productId").val();
			if(null == pval || '' == pval){
				alert("农商友和农速通广告类型为产品推送时必须选择产品!");
				return false;
			}
		}
	}
	
	var J_Urlsval = $("#J_Urls").val();
	if(null == J_Urlsval || '' == J_Urlsval){
		alert("请上传图片!");
		return false;
	}
	$.ajax( {  
	    url:CONTEXT+'push/adInfoSaveCheck',// 跳转到 action  
	    data:{
	    	id : $("#id").val(),
	    	adCanal : $("#adCanal").val(),
	    	adType : $("#adType").val(),
	    	marketId : $("#marketId").val(),
	    	sort : $("#sort").val()
	    },  
	    type:'post',  
	    dataType:'json',  
	    success:function(data) {
	    	if (data == "success") {
	    		saveedit();
			} else {
				warningMessage("已有相同农贸市场、广告渠道、广告类型的相同排序位了！");
				return;
			}
	     },  
	     error : function(data) {  
	    	 warningMessage(data);
	     }  
	});
}

function updatePtype1(marketId){
	$("#productType1").empty();
 	$("#productType1").append($("<option/>").text("--请选择--").attr("value","-1"));
	var productType1url = CONTEXT+'product/listTopProductCategory/' + marketId;
	$.getJSON(productType1url,function (data) {
        $(data).each(function () {
        	if(categoryId1 != '' && null != categoryId1 && this.categoryId == categoryId1){
        		$("#productType1").append($("<option/>").text(this.cateName).attr("value",this.categoryId).attr("selected",true));
        	}else{
        		$("#productType1").append($("<option/>").text(this.cateName).attr("value",this.categoryId));
        	}
        });
    });
}

function updatePtype2(parentId){
	$("#productType2").empty();
 	$("#productType2").append($("<option/>").text("--请选择--").attr("value","-1"));
	var productType2url = CONTEXT+'product/getChildProductCategory/' + parentId;
	$.getJSON(productType2url,function (data) {
        $(data).each(function () {
        	if(categoryId2 != '' && null != categoryId2 && this.categoryId == categoryId2){
        		$("#productType2").append($("<option/>").text(this.cateName).attr("value",this.categoryId).attr("selected",true));
        	}else{
        		$("#productType2").append($("<option/>").text(this.cateName).attr("value",this.categoryId));
        	}
        });
    });
}

function updatePtype3(parentId){
	$("#productType3").empty();
 	$("#productType3").append($("<option/>").text("--请选择--").attr("value","-1"));
	var productType3url = CONTEXT+'product/getChildProductCategory/' + parentId;
	$.getJSON(productType3url,function (data) {
        $(data).each(function () {
        	if(categoryId3 != '' && null != categoryId3 && this.categoryId == categoryId3){
        		$("#productType3").append($("<option/>").text(this.cateName).attr("value",this.categoryId).attr("selected",true));
        	}else{
        		$("#productType3").append($("<option/>").text(this.cateName).attr("value",this.categoryId));
        	}
        });
    });
}
</script>