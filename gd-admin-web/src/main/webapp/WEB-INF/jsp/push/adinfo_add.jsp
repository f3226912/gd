<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="addForm" method="post" action="push/adInfoSaveAdd">
	<div>
		<table>
			<tr>
				<td>广告名称:</td>
				<td>
					<input type="text" id="adName2" class="easyui-validatebox" missingMessage="广告名称不能为空" maxlength="50"
					validType="unnormal" name="adName" required="true" style="width: 150px">&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>广告渠道:</td>
				<td><select name="adCanal" id="adCanal2" style="width: 150px;">
						<option value="01" selected>农批web</option>
						<option value="02">农商友</option>
						<option value="03">农速通</option>
				</select></td>
			</tr>
			<tr>
				<td>广告类型:</td>
				<td><select name="adType" id="adType2" style="width: 150px;">
						<option value="01" selected>轮播图</option>
						<option value="02">产品推送</option>
						<option value="03">副图</option>
						<option value="04">全国农贸市场图</option>
				</select></td>
			</tr>
			<tr>
				<td>农贸市场:</td>
				<td>
					<select name="marketId" id="marketId2" style="width: 150px;">
						<c:forEach items="${marketList}" var="market" varStatus="s">
							<option value="${market.id }">${market.marketName }</option>
						</c:forEach>
					</select>
					&nbsp;<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td>开始时间:</td>
				<td><input type="text" id="startTime2"
					onFocus="WdatePicker({onpicked:function(){startTime.focus();},minDate:'%y-%M-%d %H:%m:%s',maxDate:'#F{$dp.$D(\'endTime2\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
					onClick="WdatePicker({onpicked:function(){startTime.focus();},minDate:'%y-%M-%d %H:%m:%s',maxDate:'#F{$dp.$D(\'endTime2\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					 name="startTime" style="width: 150px;" /></td>
			</tr>
			<tr>
				<td>截至时间:</td>
				<td><input type="text" id="endTime2"
					onFocus="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime2\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"    
					onClick="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime2\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					 name="endTime" style="width: 150px;" /></td>
			</tr>
			<tr>
				<td>推送类目:</td>
				<td>
					<select id="productType4"><option value="-1">请选择</option></select>
					<select id="productType5"><option value="-1">请选择</option></select>
					<select id="productType6"><option value="-1">请选择</option></select>
					<input type="hidden" id="categoryId4" name="categoryId" value="-1">
				</td>
			</tr>
			<tr>
				<td>产品:</td>
				<td>
					<input type="hidden" id="productId2" name="productId">
					<div id="proview2" closed="true"></div>
					<input type="button" id="showProWin2" value="选择产品">&nbsp;<font color="red" id="fontid2"></font>
				</td>
			</tr>
			<tr>
				<td>排序:</td>
				<td><input type="text" id="sort2"
					 name="sort" class="easyui-validatebox" validType="number" required="true"  missingMessage="排序不能为空" style="width: 150px">&nbsp;<font color="red">*</font>
					</td>
			</tr>
			<tr>
				<td>上传图片:</td>
				<td>
					<div class="user-iteam">
				        <div class="fl mr_10" id="uploadContainer">
				            <input type="file" class="g-u input01" id="upload_btn1" value="" name="productPicture" />
				            <input type="hidden" id="J_Urls1" name="adUrl" />&nbsp;<font color="red">*</font>
				        </div>
				        <div class="form-context">
				            <div class="clear"></div>
				            <ul id="picture_queen1" class="grid">
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
				<td><input type="text" id="adLinkUrl2" validType="urlCheck" class="easyui-validatebox" invalidMessage="请输入正确的url地址" 
					 name="adLinkUrl" style="width: 150px"></td>
			</tr>
			<tr>
				<td>状态:</td>
				<td><select name="state" style="width: 150px;">
						<option value="01">开始</option>
						<option value="02">截至</option>
						<option value="03">下架</option>
				</select></td>
			</tr>
		</table>
		<table>
			<tr>
				<td><font color="red">广告大小需求</font></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>广告名称</td>
				<td>大小</td>
				<td>个数</td>
				<td>所属市场</td>
			</tr>
			<tr>
				<td>谷登农批网-轮播图</td>
				<td>672*400</td>
				<td>5</td>
				<td></td>
			</tr>
			<tr>
				<td>谷登农批网-幅图</td>
				<td>234*100</td>
				<td>5</td>
				<td></td>
			</tr>
			<tr>
				<td>谷登农批网-产品推送</td>
				<td>179*192</td>
				<td>8</td>
				<td>白沙洲首页</td>
			</tr>
			<tr>
				<td>谷登农批网-产品推送</td>
				<td>179*192</td>
				<td>11</td>
				<td>玉林首页</td>
			</tr>
			<tr>
				<td>农商友-产品推送</td>
				<td>160*160</td>
				<td>5</td>
				<td>每级类目</td>
			</tr>
			<tr>
				<td>农商友-轮播图</td>
				<td>720x288</td>
				<td>8</td>
				<td></td>
			</tr>
			<tr>
				<td>农速通-轮播图</td>
				<td>720x288</td>
				<td>8</td>
				<td></td>
			</tr>
		</table>
		<div></div>
		<div></div>
	</div>
</form>
<script type="text/javascript" >
var masterUrl = CONTEXT+'push/uploadProductPic/';
$(document).ready(function(){
    initUploadModule("upload_btn1",masterUrl, "picture_queen1", "J_Urls1");
    
  	updatePtype4($('#marketId2').val());
  	
	$('#showProWin2').click(function(){
		var categoryIdval = $("#categoryId4").val();
		$('#proDialog').dialog({'title':'选择产品', 'width':700, 'height':300, 'href':CONTEXT+'push/proInitList?categoryId=' + categoryIdval + '&selectType=2'}).dialog('open');
	});
	
	$(".imageUploader_del").live("click",function(){
	    var originalUrl = $(this).parent().siblings().find("a img").attr("data-original-url");
	    $("input[name='pictureUrl']").each(function(){
	        if($(this).val()==originalUrl){
	            $(this).remove();
	        }
	    });
	});
	
	$('#marketId2').change(function(){
   		updatePtype4($(this).val());
   		$("#productType5").empty();
	 	$("#productType5").append($("<option/>").text("--请选择--").attr("value",""));
	 	$("#productType6").empty();
	 	$("#productType6").append($("<option/>").text("--请选择--").attr("value",""));
   		//updatePtype5('0');
   		//updatePtype6('0');
   		$("#categoryId4").val("-1");
   		$("#productId2").val("");
   		$("#proview2").html("");
   		$("#adLinkUrl2").val("");
	});
	
	$("#adType2").change(function(){
		var adTypeval = $("#adType2").val();
		var adCanalval = $("#adCanal2").val();
		if(("02" == adCanalval || "03" == adCanalval) && "02" == adTypeval){
			$("#fontid2").html("*");
		}else{
			$("#fontid2").html("");
		}
	});
	
	$("#productType4").change(function () {
		$("#categoryId4").val($(this).val());
		$("#productId2").val("");
		$("#proview2").html("");
		$("#adLinkUrl2").val("");
		updatePtype5($(this).val());
		$("#productType6").empty();
	 	$("#productType6").append($("<option/>").text("--请选择--").attr("value",""));
    });
	$("#productType5").change(function () {
		if("" != $(this).val()){
			$("#categoryId4").val($(this).val());
		}else{
			$("#categoryId4").val($("#productType4").val());
		}
		$("#productId2").val("");
		$("#proview2").html("");
		$("#adLinkUrl2").val("");
		updatePtype6($(this).val());
    });
	$("#productType6").change(function () {
		if("" != $(this).val()){
			$("#categoryId4").val($(this).val());
		}else{
			$("#categoryId4").val($("#productType5").val());
		}
		$("#productId2").val("");
		$("#proview2").html("");
		$("#adLinkUrl2").val("");
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
	        		$("#JUrls1").val("");
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



function checkadd(){
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	
	var adTypeval = $("#adType2").val();
	var adCanalval = $("#adCanal2").val();
	if("02" == adCanalval || "03" == adCanalval){
		if("03" == adTypeval){
			alert("农商友和农速通不能选择副图,请选择其他广告类型!");
			return false;
		}
		if("02" == adTypeval){
			var pval = $("#productId2").val();
			if(null == pval || '' == pval){
				alert("农商友和农速通广告类型为产品推送时必须选择产品!");
				return false;
			}
		}
	}
	
	var J_Urlsval = $("#J_Urls1").val();
	if(null == J_Urlsval || '' == J_Urlsval){
		alert("请上传图片!");
		return false;
	}
	
	$.ajax( {  
	    url:CONTEXT+'push/adInfoSaveCheck',// 跳转到 action  
	    data:{  
	    	adCanal : $("#adCanal2").val(),
	    	adType : $("#adType2").val(),
	    	marketId : $("#marketId2").val(),
	    	sort : $("#sort2").val()
	    },  
	    type:'post',  
	    dataType:'json',  
	    success:function(data) {
	    	if (data == "success") {
				saveadd();
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

function updatePtype4(marketId){
 	$("#productType4").empty();
 	$("#productType4").append($("<option/>").text("--请选择--").attr("value",""));
	var productType4url = CONTEXT+'product/listTopProductCategory/' + marketId;
	$.getJSON(productType4url,function (data) {
        $(data).each(function () {
            $("#productType4").append($("<option/>").text(this.cateName).attr("value",this.categoryId));
        });
    });
}

function updatePtype5(parentId){
	$("#productType5").empty();
 	$("#productType5").append($("<option/>").text("--请选择--").attr("value",""));
	var productType5url = CONTEXT+'product/getChildProductCategory/' + parentId;
	$.getJSON(productType5url,function (data) {
        $(data).each(function () {
            $("#productType5").append($("<option/>").text(this.cateName).attr("value",this.categoryId));
        });
    });
}

function updatePtype6(parentId){
	$("#productType6").empty();
 	$("#productType6").append($("<option/>").text("--请选择--").attr("value",""));
	var productType6url = CONTEXT+'product/getChildProductCategory/' + parentId;
	$.getJSON(productType6url,function (data) {
        $(data).each(function () {
            $("#productType6").append($("<option/>").text(this.cateName).attr("value",this.categoryId));
        });
    });
}
</script>