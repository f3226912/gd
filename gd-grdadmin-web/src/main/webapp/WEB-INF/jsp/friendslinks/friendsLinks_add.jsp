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

<form id="addForm" method="post" action="friendslinks/add">
	<div>
		<table>
			<tr>
				<td class="mleft"><span style="color: red;">*</span><span style="color: red;"></span>链接栏目:</td>
				<td class="mright"><select name="linkCate" id="linkCate_01" onchange="changeLinkCate(this.value);" >
					  </select></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span><span style="color: red;"></span>链接类型:</td>
				<td class="mright"><select name="linkType" id="linkType_1">
						
					  </select></td>
			</tr>
			<tr id="txt">
				<td class="mleft"><span style="color: red;">*</span>链接标题:</td>
				<td class="mright"><input type="text" id="linkWord" name="linkName" maxlength="40" class="easyui-validatebox" style="width: 150px"></td>
			</tr> 
			<tr>
				<td class="mleft"><span style="color: red;">*</span>链接URL:</td>
				<td><input type="text" id="linkUrl_01" name="linkUrl"  style="width: 150px"></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;">*</span>状态:</td>
				<td><select name="status" id="status"  >
							<option value="0" >停用</option>
							<option value="1" selected="selected">启用</option>
<!-- 							<option value="2" >待审核</option> -->
<!-- 							<option value="3" >审核不通过</option> -->
					  </select></td>
			</tr>
			<tr>
				<td class="mleft"><span style="color: red;"></span>说明:</td>
				<td><textarea rows="3" cols="30" style="width:100%;font-size:14px;" 
					name="description" ></textarea></td>
			</tr>
			
			<tr id="photo">
				<td class="mleft"><span style="color: red;">*</span>上传链接图片:</td>
				<td><input type="file" class="g-u input01" id="upload_btn_link_01" value="" name="linkImage" />
	            <input type="hidden" id="J_Urls_Link_01" name="linkImage" />
	            <ul id="picture_queen_link" class="multPic-box"></ul>
	           	 图片规格大小：180*60
	            </td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/formValidatorRegex.js"></script>
<script type="text/javascript" >
	

	var appUrl = CONTEXT+'friendslinks/uploadProductPic';

	initUploadSignle("upload_btn_link_01",appUrl, "picture_queen_link", "J_Urls_Link");

	/**单图上传**/
	function initUploadSignle(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr){
		var config_mutiple = {
		        galleryUrl : CONTEXT+"js/uploader/",//控件静态地址
		        uploadBtn : uploadBtn,//初始的button
		        queueId : queueId,//图片装载容器id
		        JUrls : JUrls,//成功后返回url参数写入id
		        action : uploadUrl ,//上传地址
		        maxSize:1024,
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
	

// 	$("#linkCate_01").append("<option value='0'>请选择</option>"); 
	$("#linkCate_01").append("<option value='1' >友情链接</option>"); 
	$("#linkCate_01").append("<option value='2' >合作媒体</option>"); 
	 $("#photo").animate({ height: 'show', opacity: 'hide' }); 
	 $("#txt").animate({ height: 'show', opacity: 'show' }, 'slow'); 
	 $("#linkType_1").append("<option value='1'>文字</option>"); 
	function changeLinkCate(val){
		
		$("#linkType_1").empty();
		if(val==1){
			 $("#photo").animate({ height: 'hide', opacity: 'hide' }, 'slow');
			 $("#txt").animate({ height: 'show', opacity: 'show' }, 'slow'); 
			 $("#linkType_1").append("<option value='1'>文字</option>"); 
		}
		if(val==2){
			 $("#txt").animate({ height: 'show', opacity: 'show' }, 'slow');
			 $("#photo").animate({ height: 'show', opacity: 'show' }, 'slow');  
			 $("#linkType_1").append("<option value='2'>图片</option>"); 
		}	
	}
// 	$("#linkType_1").change(function(){
// 		if($("#linkType_1 option:selected").val()==2){
// 			 $("#txt").animate({ height: 'hide', opacity: 'hide' }, 'slow');
// 			 $("#photo").animate({ height: 'show', opacity: 'show' }, 'slow');  
// 		}	
// 		if($("#linkType_1 option:selected").val()==3){
// 			$("#txt").animate({ height: 'show', opacity: 'show' }, 'slow'); 
// 		}
// 	});
</script>
<script type="text/javascript" src="${CONTEXT}js/friendslinks/friendslinks.js"></script>
