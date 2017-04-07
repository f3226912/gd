<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../pub/constants.inc"%>
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
<form id="addForm" method="post" action="market/save">
	<div>
		<table style="width: 800px; height: 280px;">
		    <tr>
				<td><input type="hidden" id="id" name="id"
					value="${id}"  style="width: 150px"></td>
			</tr>
			<tr>
				<td style="width: 100px;"> <span style="color: red;">*</span>街市名称:</td>
				<td><input type="text" id="marketName" name="marketName" maxlength="50"
					value="${marketName}" required="true" class="easyui-validatebox"
					missingMessage="街市名称必须填写" style="width: 150px"></td>
			</tr>
			<tr>
				<td style="width: 100px;"> <span style="color: red;">*</span>市场简称:</td>
				<td><input type="text" id="marketShortName" name="marketShortName" maxlength="50"
					value="${marketShortName}" required="true" class="easyui-validatebox"
					missingMessage="市场简称必须填写" style="width: 150px"></td>
			</tr>
			
		    <tr>
				<td style="width: 100px;"><span style="color: red;">*</span>街市类型:</td>
				<td>
				<input type="radio" name="marketType"  <c:if test="${marketType=='0'}"> checked="checked" </c:if>  value="0">产地供应商
				<input type="radio" name="marketType"  <c:if test="${marketType=='1'}"> checked="checked" </c:if>  value="1">街市
				<input type="radio" name="marketType"  <c:if test="${marketType=='2'}"> checked="checked" </c:if>  value="2">市场（农批中心）
				<br>
				<input type="radio" name="marketType"  <c:if test="${marketType=='3'}"> checked="checked" </c:if>  value="3">用户自定义添加
				<input type="radio" name="marketType"  <c:if test="${marketType=='4'}"> checked="checked" </c:if>  value="4">市场活动
				</td>
			</tr>	
			
	        <tr>                                                             
             <td class="left"><span style="color: red;">*</span>省:</td>   
             <td><input name="province"  id="province"  value="${provinceId}" required="true" class="easyui-validatebox"
					missingMessage="必须填写"   style="width: 174px;" ></td>         
            </tr> 
            <tr>  
             <td class="left"><span style="color: red;">&nbsp;</span>市:</td>                    
             <td><input name="city" id="city" value="${cityId}"   class="easyui-validatebox"
					    style="width: 174px;"></td>                                             
            </tr>
            <tr> 
             <td class="left">&nbsp;县区:</td>   
             <td><input name="county"  id="county" value="${areaId}"   class="easyui-validatebox"
					  style="width: 174px;" ></td>
            </tr> 
        <tr>
				<td style="width: 100px;"> 占地面积:</td>
				<td>
				<input type="text" id="marketArea" name="marketArea" maxlength="50"
					value="${marketArea}"  style="width: 150px">
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"> 年交易量:</td>
				<td>
				<input type="text" id="tradeAmount" name="tradeAmount" maxlength="50"
					value="${tradeAmount}"  style="width: 150px">
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"> 年交易额:</td>
				<td>
				<input type="text" id="tradeMoney" name="tradeMoney" maxlength="50"
					value="${tradeMoney}"  style="width: 150px">
				</td>
			</tr>
            <tr>
			<td style="width: 100px;"><span style="color: red;">*</span> 详细地址:</td>
			   <td>
				<input type="text" id="address" name="address" value="${address}" maxlength="200"
					required="true" class="easyui-validatebox"
					missingMessage="详细地址必须填写" style="width: 150px">
			  </td>
	        </tr>
			
			<tr>
				<td style="width: 100px;"><span style="color: red;">*</span> 状态:</td>
				<td>
				<c:choose>
				<c:when test="${status==0}">
				<input type="radio" name="status"   value="0" checked="checked"> 启用
				<input type="radio" name="status"   value="1"> 停用
				</c:when>
				<c:otherwise>
				<input type="radio" name="status"   value="0"> 启用
				<input type="radio" name="status"   value="1" checked="checked"> 停用
				</c:otherwise>
				</c:choose>
				
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"> 经度:</td>
				<td>
				<input type="text" id="lon" name="lon" maxlength="50"
					value="${lon}"  style="width: 150px;" disabled="disabled" >
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"> 纬度:</td>
				<td>
				<input type="text" id="lat" name="lat" maxlength="50"
					value="${lat}"  style="width: 150px" disabled="disabled" >
				</td>
			</tr>
			<tr>
				<td style="width: 100px;"> 主营分类:</td>
				<td>
				<input type="text" id="category" name="category" maxlength="50"
					value="${category}"  style="width: 150px" readonly="readonly" >
				</td>
			</tr>
			<tr id="photo_edit">
				<td style="width: 100px;"><span style="color: red;">*</span> 上传链接图片:</td>
				<td><input type="file" class="g-u input01" id="upload_btn_link" value="" name="linkImage" />
	            <input type="hidden" id="J_Urls_Link_edit" name="linkImage" />
 	            <div class="form-context">
				            <div class="clear"></div>
				            <ul id=picture_queen_link class="grid">
				            	<c:set var="adUrlStr" value='[{"url":"${marketImg }"}]' />
				            	
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
				<tr>
					<td style="width: 100px;"> 客户端显示排序:</td>
					<td>
					<input type="text" id="sort" name="sort" maxlength="50"
						value="${sort}"  style="width: 150px">
					</td>
				</tr>
				<tr>
				<td style="width: 100px;">入驻商户:</td>
				<td>
				<input type="text" id="businessAmount" name="businessAmount" maxlength="50"
					value="${businessAmount}"  style="width: 150px" disabled="disabled" >
				</td>
			</tr>
			<tr>
				<td style="width: 100px;">按分类统计商户数:</td>
				<td>
				<input type="text" id="businessClassAmount" name="businessClassAmount" maxlength="50"
					value="${businessClassAmount}"  style="width: 150px" disabled="disabled" >
				</td>
			</tr>
			
			<tr>
				<td style="width: 100px;"> 备注:</td>
				<td>
				<input type="text" id="description" name="description" maxlength="50"
					value="${description}"  style="width: 150px">
				</td>
			</tr>

		</table>
	</div>
</form>
<script type="text/javascript">
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
</script>
<script type="text/javascript" src="${CONTEXT}js/market/add.js"></script>
<script type="text/javascript" src="${CONTEXT}js/market/area.js"></script>







