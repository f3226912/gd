var appUrl = CONTEXT+'grdGdGift/uploadProductPic';

$(document).ready(function(){
	//first
	initUploadSignle("uploadFirstPic",appUrl, "pictureShowFirst", "urlWshowImgFirst");
	//second
	initUploadSignle("uploadSecondPic",appUrl, "pictureShowSecond", "urlWshowImgSecond");
	//third
	initUploadSignle("uploadThirdPic",appUrl, "pictureShowThird", "urlWshowImgThird");
	
	$.extend($.fn.validatebox.defaults.rules, {    
		unit: {    
	        validator: function(value){    
	            return !/^\d+$/.test(value);    
	        },    
	        message: '礼品单位不能为数字'   
	    }    
	});  
});



/**单图上传**/
function initUploadSignle(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr){
	var config_mutiple = {
	        galleryUrl : CONTEXT+"js/uploader/",//控件静态地址
	        uploadBtn : uploadBtn,//初始的button
	        queueId : queueId,//图片装载容器id
	        JUrls : JUrls,//成功后返回url参数写入id
	        action : uploadUrl ,//上传地址
	        maxSize:2048,
	        imgUrlPrefix : imgHostUrl,
	        max : pictureCountLimit,//允许上传个数
	        type :"ajax",//上传类型
	        filesStr : filesStr,//此处是附加参数，已经上传需要第二次修的图片参数
	};
	gdKissySingleUploader.init(config_mutiple ,function(uploader){
	     var filesStr = config_mutiple.filesStr;
	     var certification = config_mutiple.certification;
	     if(filesStr != ''&&filesStr!=undefined){
	        
	        gdKissySingleUploader.addFiles(filesStr,uploader);
	        
	        if(certification == 0){
	                $("#"+JUrls).parent().find(".file-input").attr("disabled","disabled");
	        }else{
	        	/**可以修改时注册删除事件**/
	        	$(".form-context").delegate(".imageUploader_del","click",function(){
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

