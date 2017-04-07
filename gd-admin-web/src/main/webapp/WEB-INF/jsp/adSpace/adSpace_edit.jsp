<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="editAdSpaceForm" method="post" action="adSpace/insert">
	<input type="hidden" value="${adSpaceDTO.id }" name="id"/>
	<div style="padding:10px">
		<table>
			<tr>
				<td>广告渠道：&nbsp;</td>
				<td>${adSpaceDTO.menuName }<input type="hidden" name="menuId" value="${adSpaceDTO.menuId }"/></td>
			</tr>
			<tr>
				<td><span style="color:red">*</span>广告类型：&nbsp;</td>
				<td>
					<select id="edit_adType" name="adType" style="min-width:150px" required="true"
						class="easyui-validatebox"  missingMessage="广告类型不能为空">
						<c:choose>
							<c:when test="${adSpaceDTO.adType == '1' or adSpaceDTO.adType == '2' }">
								<option value="1" ${adSpaceDTO.adType == '1' ? 'selected':'' }>图片</option>
								<option value="2" ${adSpaceDTO.adType == '2' ? 'selected':'' }>文字</option>
							</c:when>
							<c:when test="${adSpaceDTO.adType == '3'}">
								<option value="3" ${adSpaceDTO.adType == '3' ? 'selected':'' }>app启动页</option>
							</c:when>
							<c:otherwise>
								<option value="">----请选择----</option>
								<option value="1">图片</option>
								<option value="2">文字</option>
								<option value="3">app启动页</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
			</tr>
			<tr>
				<td><span style="color:red">*</span>广告位：&nbsp;</td>
				<td><input type="text" id="edit_spaceSign" name="spaceSign" value="${adSpaceDTO.spaceSign }" required="true"
				class="easyui-validatebox" validType="spaceSign" missingMessage="广告位名称不能为空" invalidMessage="只能是数字或者英文字母"/></td>
			</tr>
			<tr>
				<td>广告价格：&nbsp;</td>
				<td><input type="text" id="adPrice" name="adPrice" value="${adSpaceDTO.adPrice }" class="easyui-validatebox" validType="price" invalidMessage="价格整数部分最多输入8位和小数点后最多2位"/>&nbsp;/&nbsp;天</td>
			</tr>
			<tr id="tr_adSize" ${(adSpaceDTO.adType == '1' or  adSpaceDTO.adType == '3') ? "style='display:table-row'" : "style='display:none'"}>
				<td><span style="color:red">*</span>广告图规格：&nbsp;</td>
				<td>
					<select id="edit_adSize" name="adSize" style="min-width:150px">
						<option value="">----请选择----</option>
						<option value="300*750" ${adSpaceDTO.adSize == '300*750' ? 'selected' : '' }>300*750</option>
						<option value="140*750" ${adSpaceDTO.adSize == '140*750' ? 'selected' : '' }>140*750</option>
						<option value="140*374" ${adSpaceDTO.adSize == '140*374' ? 'selected' : '' }>140*374</option>
						<option value="300*246" ${adSpaceDTO.adSize == '300*246' ? 'selected' : '' }>300*246</option>
						<option value="372*372" ${adSpaceDTO.adSize == '372*372' ? 'selected' : '' }>372*372</option>
						<option value="246*246" ${adSpaceDTO.adSize == '246*246' ? 'selected' : '' }>246*246</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>广告位展示图：</td>
				<td>
					<div class="user-iteam">
						<p></p>
				        <div class="fl mr_10">
				            <input type="file" class="g-u input01" id="upload_btn_show" value="" name="adPicture" />
				            <input type="hidden" id="J_Urls_showImg" name="showImg" />
				        </div>
				        <div class="form-context">
				            <div class="clear"></div>
				            <ul id="picture_queen_show" class="grid">
				            	<c:if test="${adSpaceDTO.showImg !=''}">
				           		<c:set var="adShowUrlStr" value='[{"url":"${adSpaceDTO.showImg }"}]' />
					            <script type="text/uploader-files">
								${adShowUrlStr}
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
                        			<div class="J_ProgressBar_{id}"><s class="loading-icon"></s>加载中...</div>
                    				</div>
                    				<div class="status error-status">
                        				<p class="J_ErrorMsg_{id}">服务器故障，请稍候再试！</p></div>
                					</div>
                					<div class="imageUploader_menu"><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a></div>
            						</li>
    							</script>
    							</c:if>
				            </ul>
				        </div> 
					</div>
				</td>
			</tr>
			<tr>
				<td>广告位说明：</td>
				<td><textarea name="adName" style="width:300px;height:80px" class="easyui-validatebox" validType="length[0,100]" invalidMessage="不能超过100个字符！">${adSpaceDTO.adName }</textarea></td>
			</tr>
			<tr>
				<td>过期广告默认替换图:</td>
				<td>
					<p></p>
			        <div class="fl mr_10">
			            <input type="file" class="g-u input01" id="upload_btn_replace" value="" name="adPicture" />
			            <input type="hidden" id="edit_J_Urls_replaceImg" name="replaceImg" />
			        </div>
			        <div class="form-context">
			            <div class="clear"></div>
			            <ul id="picture_queen_replace" class="grid">
			            	<c:if test="${adSpaceDTO.replaceImg != ''}">
			           	 	<c:set var="adReplaceUrlStr" value='[{"url":"${adSpaceDTO.replaceImg }"}]' />
			            	<script type="text/uploader-files">
								${adReplaceUrlStr}
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
				</td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">
$(function(){
	var adSpaceImgUploadImg = CONTEXT+'adSpace/uploadPic';
	//广告位展示图
	initUploadSignle("upload_btn_show", adSpaceImgUploadImg, "picture_queen_show", "J_Urls_showImg");
	//过期广告默认替换图
	initUploadSignle("upload_btn_replace",adSpaceImgUploadImg, "picture_queen_replace", "edit_J_Urls_replaceImg");

	$.extend($.fn.validatebox.defaults.rules, {  
        price: {  
            validator: function(value){
            	if(value != ''){
            		if(!/^\d{1,8}(.\d{0,2})?$/.test(value)){
            			return false;	
            		}
            	}
            	return true;
            }
        },
        spaceSign:{
        	validator: function(value){
        		if(!/^[a-zA-Z0-9]+$/.test(value)){
        			return false;	
        		}
        		return true;
        	}
        }
	});
	
	$("#edit_adType").change(function(){
		var val = $(this).val();
		if(val == "1" || val == "3"){
			$("#tr_adSize").show();
		}else{
			$("#tr_adSize").hide();
		}
	});
	
	$("#edit_spaceSign").change(function(){
		$(this).val($(this).val().toUpperCase());
	});
});

function saveEdit(){
	if(!$("#editAdSpaceForm").form('validate')){
		return false;
	}
	
	var adType = $("#edit_adType").val();
	if(adType == "1" || adType == "3"){
		if($("#edit_adSize").val() == ""){
			warningMessage("请选择广告图规格");
			return false;
		}
	}
	
	/*if($("#edit_J_Urls_replaceImg").val() == ''){
		warningMessage("请上传过期广告默认替换图");
		return false;
	}*/
	
	var url = CONTEXT + "adSpace/saveEdit";
	$.post(url, $('#editAdSpaceForm').serialize(), function (data) {
		if (data.status == 0) {
			slideMessage("操作成功！");
			var entity=data.entity;
			var node = $('#treeMenu').tree('getSelected');
			if (node){
				$('#treeMenu').tree('update', {
					target: node.target,
					text: entity.adName+"("+entity.spaceSign+")"
				});
				$("#editAdSpaceDialog").dialog('destroy');
			}
		}else{
			warningMessage(data.message);
			return;
		}
	});
}

/**单图上传**/
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
</script>