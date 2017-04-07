<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc"%>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc"%>
<style type="text/css">
.mleft {
	font-size: 16px;
	text-align: right;
	valign: middle;
	width: 150px;
}

.mright {
	font-size: 16px;
	align: left;
	valign: middle;
}
</style>

<form id="editForm" method="post" action="friendslinks/edit">
	<div>
		<input type="hidden" id="id" name="id" value="${dto.id}" />
		<table>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>链接栏目:</td>
				<td class="mright">
				<input type="hidden" name="linkCate" value="${dto.linkCate }">
						<select  id="linkCate_edit" disabled="disabled"   onchange="changeLinkType(this.value);" >
							<option value="1" <c:if test="${dto.linkCate==1 }">selected</c:if>>友情链接</option>
							<option value="2" <c:if test="${dto.linkCate==2 }">selected</c:if>>合作媒体</option>
					  </select></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>链接类型:</td>
				<td class="mright">
				<input type="hidden" name="linkType" value="${dto.linkType }" id="linkType_edit_hiden">
				<select disabled="disabled"    name="linkType" id="linkType_edit" >
							<option value="1" <c:if test="${dto.linkType==1 }">selected</c:if>>文字</option>
							<option value="2" <c:if test="${dto.linkType==2 }">selected</c:if>>图片</option>
<%-- 							<option value="3" <c:if test="${dto.linkType==3 }">selected</c:if>>文字加图片</option> --%>
					  </select></td>
			</tr>
			<tr id="txt_edit">
					<td class="mleft"><span style="color: red;">*</span>链接标题:</td>
					<td class="mright"><input type="text" id="linkWord_edit" name="linkName" maxlength="20" class="easyui-validatebox" style="width: 150px" value="${dto.linkName}"></td>
				</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>链接URL:</td>
				<td><input type="text" id="linkUrl_edit" name="linkUrl" maxlength="50" class="easyui-validatebox" style="width: 150px" value="${dto.linkUrl}"></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>状态:</td>
				<td><select name="status" id="status"  >
							<option value="0" <c:if test="${dto.status==0 }">selected</c:if>>停用</option>
							<option value="1" <c:if test="${dto.status==1 }">selected</c:if>>启用</option>
<%-- 							<option value="2" <c:if test="${dto.status==2 }">selected</c:if>>待审核</option> --%>
<%-- 							<option value="3" <c:if test="${dto.status==3 }">selected</c:if>>审核不通过</option> --%>
					  </select></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;"></span>说明:</td>
				<td><textarea rows="3" cols="30" style="width:100%;font-size:14px;" 
					name="description" >${dto.description}</textarea></td>
			</tr>
			
				
				<tr id="photo_edit">
				<td class="mleft"><span style="color: red;">*</span>上传链接图片:</td>
				<td><input type="file" class="g-u input01" id="upload_btn_link" value="" name="linkImage" />
	            <input type="hidden" id="J_Urls_Link_edit" name="linkImage" />
 	            <div class="form-context">
				            <div class="clear"></div>
				            <ul id=picture_queen_link class="grid">
				            	<c:set var="adUrlStr" value='[{"url":"${dto.linkImage }"}]' />
				            	
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
			</tr>
		</table>
	</div>
</form>

<script type="text/javascript" >

	var appUrl = CONTEXT+'friendslinks/uploadProductPic';

	initUploadModule("upload_btn_link",appUrl, "picture_queen_link", "J_Urls_Link_edit",1,'',1);

	
	
	
	/**单图上传**/
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
	var lin = ${dto.linkCate};

		changeLinkType(lin);

	function changeLinkType(val){
		$("#linktype_edit").empty();
		if(val==1){
			document.getElementById("photo_edit").style.display="none";
			document.getElementById("txt_deit").style.display="";
			 $("#linktype_edit").append("<option value='1'>文字</option>"); 
		}
		if(val==2){
			document.getElementById("txt_edit").style.display="";
			document.getElementById("photo_edit").style.display="";
			 $("#linktype_edit").append("<option value='2'>图片</option>"); 
		}
	}
</script>
