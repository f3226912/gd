<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="addForm" method="post" action="adSpace/insert">
	<div style="padding:10px">
		<table>
			<tr>
				<td>广告渠道：&nbsp;</td>
				<td>${menu.menuName }<input type="hidden" name="menuId" value="${menuId }"/></td>
			</tr>		
			<tr>
				<td><span style="color:red">*</span>广告类型：&nbsp;</td>
				<td>
					<select id="add_adType" name="adType" style="min-width:150px" required="true"
						class="easyui-validatebox"  missingMessage="广告类型不能为空">
						<option value="">----请选择----</option>
						<option value="1">图片</option>
						<option value="2">文字</option>
						<option value="3">app启动页</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><span style="color:red">*</span>广告位：&nbsp;</td>
				<td><input type="text" id="add_spaceSign" name="spaceSign" required="true"
				class="easyui-validatebox" validType="spaceSign" missingMessage="广告位名称不能为空" invalidMessage="只能是数字或者英文字母"/></td>
			</tr>
			<tr>
				<td>广告价格：&nbsp;</td>
				<td><input type="text" id="adPrice" name="adPrice" class="easyui-validatebox" validType="price" invalidMessage="价格整数部分最多输入8位和小数点后最多2位"/>&nbsp;/&nbsp;天</td>
			</tr>
			<tr id="tr_adSize">
			<td><span style="color:red">*</span>广告图规格：&nbsp;</td>
				<td>
					<select id="add_adSize" name="adSize" style="min-width:150px">
						<option value="">----请选择----</option>
						<option value="300*750">300*750</option>
						<option value="140*750">140*750</option>
						<option value="140*374">140*374</option>
						<option value="300*246">300*246</option>
						<option value="372*372">372*372</option>
						<option value="246*246">246*246</option>
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
				            <ul id="picture_queen_show" class="multPic-box"></ul>
				        </div> 
					</div>
				</td>
			</tr>
			<tr>
				<td>广告位说明：</td>
				<td><textarea name="adName" style="width:300px;height:80px" class="easyui-validatebox" validType="length[0,100]" invalidMessage="不能超过100个字符！"></textarea></td>
			</tr>
			<tr>
				<td>过期广告默认替换图:</td>
				<td>
					<p></p>
			        <div class="fl mr_10">
			            <input type="file" class="g-u input01" id="upload_btn_replace" value="" name="adPicture" />
			            <input type="hidden" id="add_J_Urls_replaceImg" name="replaceImg" />
			        </div>
			        <div class="form-context">
			            <div class="clear"></div>
			            <ul id="picture_queen_replace" class="grid multPic-box"></ul>
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
	initUploadSignle("upload_btn_replace",adSpaceImgUploadImg, "picture_queen_replace", "add_J_Urls_replaceImg");

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
	
	$("#add_adType").change(function(){
		var val = $(this).val();
		if(val == "1" || val == "3"){
			$("#tr_adSize").show();
		}else{
			$("#tr_adSize").hide();
		}
	});
	
	$("#add_spaceSign").change(function(){
		$(this).val($(this).val().toUpperCase());
	});
});

function saveAdd(){
	if(!$("#addForm").form('validate')){
		return false;
	}
	
	var adType = $("#add_adType").val();
	if(adType == "1" || adType == "3"){
		if($("#add_adSize").val() == ""){
			warningMessage("请选择广告图规格");
			return false;
		}
	}
	
	/*if($("#add_J_Urls_replaceImg").val() == ''){
		warningMessage("请上传过期广告默认替换图");
		return false;
	}*/
	var url = CONTEXT + "adSpace/saveAdd";
	$.post(url, $('#addForm').serialize(), function (data) {
		if (data.status == 0) {
			slideMessage("操作成功！");
			var adSpace=data.obj;
			var node = $('#treeMenu').tree('getSelected');
			if (node){
				$('#treeMenu').tree('append', {
					parent: node.target,
					data: {
						id: adSpace.id,
						text: adSpace.adName+"("+adSpace.spaceSign+")",
						iconCls : 'icon-adSpace',
						attributes:{
				            "type":2,
				        }, 
					}
				});
				$('#editAdSpaceDialog').dialog('close');
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