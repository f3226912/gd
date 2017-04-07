<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

    <input type="hidden" id="webUrl" name="webUrl" value="http://hnuser.cnhnb.com:80/uc-web/" />
    <input type="hidden" id="showImg" name="showImg" value="http://img.cnhnb.com/"/>
    <input type="hidden" id="userPictures" name="userPictures" value="">
    
    <input type="hidden" id="productId" name="productId" value="${productId}">
    
    <input type="hidden" id="types" name="types" value="${types}">
    
    <div class="user-iteam">
        <p>上传主图</p>
        <div class="fl mr_10" id="uploadContainer">
            <input type="file" class="g-u input01" id="upload_btn" value="" name="productPicture" />
            <input type="hidden" id="J_Urls" name="refundImageUrls" />
        </div>
        <div class="form-context">
            <div class="clear"></div>
            <ul id="picture_queen" class="grid"></ul>
        </div> 
        <BR>
        
        <p>上传App图</p>
        <div class="fl mr_10" id="uploadContainerApp">
            <input type="file" class="g-u input01" id="upload_btn_app" value="" name="productPicture" />
            <input type="hidden" id="J_Urls_App" name="refundImageUrls" />
        </div>
        <div class="form-context">
            <div class="clear"></div>
            <ul id="picture_queen_app" class="grid"></ul>
        </div> 
        <BR>        
        
        <p>上传Web图</p>
        <div class="fl mr_10" id="uploadContainerWeb">
            <input type="file" class="g-u input01" id="upload_btn_web" value="" name="productPicture" />
            <input type="hidden" id="J_Urls_Web" name="refundImageUrls" />
        </div>
        <div class="form-context">
            <div class="clear"></div>
            <ul id="picture_queen_web" class="grid"></ul>
        </div> 
        <BR>    
        
        <p>上传多角度图</p>
        <div class="fl mr_10" id="uploadContainerMultiAngle">
            <input type="file" class="g-u input01" id="upload_btn_multiAngle" value="" name="productPicture" />
            <input type="hidden" id="J_Urls_MultiAngle" name="refundImageUrls" />
        </div>
        <div class="form-context">
            <div class="clear"></div>
            <ul id="picture_queen_MultiAngle" class="grid"></ul>
        </div> 
        <BR>   
        
<!--         
        <p>单图</p>
        <div class="grid clearfix" style="overflow:hidden">
            <input type="file" class="g-u" id="J_UploaderBtn" value="上传多角度图" name="productPicture"  accept="image/*" >
            <input type="hidden" id="J_Urls2" name="urls" value="" />
        </div>
        <div id="J_UploaderQueue" class="grid">

        </div> -->
    </div>
    

    
<script>
    var webUrl = $("#webUrl").val();
	var showImg = $("#showImg").val();
	
	var productId = $("#productId").val();
	var types = $("#types").val();

/* function uploadPicture( uploadBtn, uploadUrl, productId, productType, queueId, JUrls){
	
	var config_master = {
	        galleryUrl : CONTEXT+"js/uploader/",
	        uploadBtn : uploadBtn,
	        queueId : queueId,
	        JUrls : JUrls,
	        action : uploadUrl + productId + "-"+ productType,
	        imgUrlPrefix : showImg+"temp/",
	        max:1,
	        type :"ajax"
	};
	gdKissyMultipleUploader.init(config_master ,function(uploader){
	     var filesStr = '';
	     var certification = '';
	     if(filesStr != ''){
	        var fileList = eval("("+  filesStr + ")");
	        gdKissyMultipleUploader.addFiles(fileList,uploader);
	        if(certification == 0){
	                $("#uploadContainer").find(".file-input").attr("disabled","disabled");
	        }
	     }
	},function(url){
	    if(url!='' && typeof(url!='undefined')){
	        var urlarray = url.split(",");
	        for (var i = 0; i < urlarray.length; i++) {
	            $("#picContainer").append("<input name=\"pictureUrl\" id=\"url+"+i+"\" type=\"hidden\" value=\""+urlarray[i]+"\" />");
	        }
	        
	    }
	});
} */


function initUploadModule(uploadBtn, uploadUrl, productId, productType, queueId, JUrls){
	var config_mutiple = {
	        galleryUrl : CONTEXT+"js/uploader/",
	        uploadBtn : uploadBtn,
	        queueId : queueId,
	        JUrls : JUrls,
	        action : uploadUrl+ productId +"-" + productType,
	        imgUrlPrefix : showImg+"temp/",
	        max:1,
	        type :"ajax"
	};
	gdKissySingleUploader.init(config_mutiple ,function(uploader){
	     var filesStr = '';
	     var certification = '';
	     if(filesStr != ''){
	        var fileList = eval("("+  filesStr + ")");
	        
	        gdKissySingleUploader.addFiles(fileList,uploader);
	        
	        if(certification == 0){
	                $("#uploadContainer").find(".file-input").attr("disabled","disabled");
	        }
	     }
	},function(url){
	    if(url!='' && typeof(url!='undefined')){
	        var urlarray = url.split(",");
	        for (var i = 0; i < urlarray.length; i++) {
	            $("#picContainer").append("<input name=\"pictureUrl\" id=\"url+"+i+"\" type=\"hidden\" value=\""+urlarray[i]+"\" />");
	        }
	        
	    }
	});
}



var masterUrl = CONTEXT+'uploadProductPic/';
var multiAngleUrl = CONTEXT+'uploadProductPic/';
var webUrl = CONTEXT+'uploadProductPic/';
var appUrl = CONTEXT+'uploadProductPic/';

$(document).ready(function(){
	
	if (types){
		var typeArray = types.split(",");
		for(var item in typeArray){
			if (typeArray[item] == 1){
				//$("#upload_btn").val("更换主图");
				masterUrl = CONTEXT+'changeProductPic/';
			}else if (typeArray[item] == 2){
				//$("#upload_btn_multiAngle").val("更换多角度图");
				multiAngleUrl = CONTEXT+'changeProductPic/';
			}else if (typeArray[item] == 3){
				//$("#upload_btn_web").val("更换Web图");
				webUrl = CONTEXT+'changeProductPic/';
			}else if (typeArray[item] == 4){
				//$("#upload_btn_app").val("更换App图");
				appUrl = CONTEXT+'changeProductPic/';
			}
			
		}
	}
	
	
	var productType = 1;
	initUploadModule("upload_btn", masterUrl, productId, productType, "picture_queen", "J_Urls");
	productType = 2;
	initUploadModule("upload_btn_multiAngle",multiAngleUrl, productId, productType, "picture_queen_MultiAngle", "J_Urls_MultiAngle");
	productType = 3;
	initUploadModule("upload_btn_web",webUrl, productId, productType, "picture_queen_web", "J_Urls_Web");
	productType = 4;
	initUploadModule("upload_btn_app",appUrl, productId, productType, "picture_queen_app", "J_Urls_App");
	
	/* initUploadModule("J_UploaderBtn", CONTEXT+'uploadProductPic/', productId, productType, "J_UploaderQueue", "J_Urls2"); */
	
	var filesStr = '';
     if(filesStr != ''){
        var fileList = eval("("+  filesStr + ")");
        for (var i = 0; i < fileList.length; i++) {
            $("#picContainer").append("<input name=\"pictureUrl\" id=\"url+"+i+"\" type=\"hidden\" value=\""+fileList[i].dataOriginalUrl+"\" />");
        }
     }
     
});

$(".imageUploader_del").live("click",function(){
    var originalUrl = $(this).parent().siblings().find("a img").attr("data-original-url");
    $("input[name='pictureUrl']").each(function(){
        if($(this).val()==originalUrl){
            $(this).remove();
        }
    });
});

</script>


