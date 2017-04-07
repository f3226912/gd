<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<%@ include file="../pub/head.inc" %>
<title>编辑商品分类</title>
<script type="text/javascript" src="${CONTEXT}js/jquery.cookie.js"></script>
<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>

</head>
<body>
	<input type="hidden" id="categoryId" name="categoryId" value="${category.categoryId }" />
	<input type="hidden" id="marketId" name="marketId" value="${category.marketId }" /> 分类名称:
	<input maxlength="10" id="cateName" class="easyui-validatebox" value="${category.cateName }"></input>
	<br>
	<br> 顺序:
	<input maxlength="4" id="orderNum" class="easyui-validatebox" value="${category.orderNum }"></input>
	</div>
	<c:if test="${category.marketId != 0 }">
	<div class="rse-pro-tit-pic">
		APP图标：
		<div class="fl mr_10" id="uploadContainer">
			<input type="file" class="g-u input01" id="upload_btn_app_modify" value="" name="categoryPic" />
            <input type="hidden" id="typeIcon" name="typeIcon" value="${category.typeIcon}"  />
		</div>
		<div class="rse-pro-tit-pic">
			<ul id="picture_queen_SigleAngle" class="grid multPic-box">
    		<c:if test="${not empty category.typeIcon}">
			<script type="text/uploader-files">
				[{"url":"${category.typeIcon}"}]
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
		
		WEB图标：
		<div class="fl mr_10" id="uploadContainer2">
			<input type="file" class="g-u input01" id="upload_btn_web_modify" value="" name="categoryPic" />
            <input type="hidden" id="webTypeIcon" name="webTypeIcon" value="${category.webTypeIcon}"  />
		</div>
		<div class="rse-pro-tit-pic">
			<ul id="picture_queen_webSigleAngle" class="grid multPic-box">
			<c:if test="${not empty category.webTypeIcon}">
			<script type="text/uploader-files">
				[{"url":"${category.webTypeIcon}"}]
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
		
		<script type="text/javascript">
			var CONTEXT = '${CONTEXT}';
			function initUploadSignle(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr){
				var config_mutiple = {
				        galleryUrl : CONTEXT+"js/uploader/",//控件静态地址
				        uploadBtn : uploadBtn,//初始的button
				        queueId : queueId,//图片装载容器id
				        JUrls : JUrls,//成功后返回url参数写入id
				        action : uploadUrl ,//上传地址
				        maxSize:2048,
				        imgUrlPrefix : '${imgHostUrl}',
				        max : pictureCountLimit,//允许上传个数
				        type :"ajax",//上传类型
				        filesStr : filesStr,//此处是附加参数，已经上传需要第二次修的图片参数
				};
				gdKissySingleUploader.init(config_mutiple ,function(uploader){
				     var filesStr = config_mutiple.filesStr;
				     var certification = config_mutiple.certification;
				     if(filesStr != ''&&filesStr!=undefined){
				        //var fileList = eval("("+  filesStr + ")");
				        
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
			
			var uploadUrl = CONTEXT + "category/uploadCategoryPic";
			initUploadSignle("upload_btn_app_modify",uploadUrl,"picture_queen_SigleAngle","typeIcon",1,'${category.typeIcon}');
			initUploadSignle("upload_btn_web_modify",uploadUrl,"picture_queen_webSigleAngle","webTypeIcon",1,'${category.webTypeIcon}');
		    </script>
	</div>
	</c:if>
	</div>
</body>
</html>