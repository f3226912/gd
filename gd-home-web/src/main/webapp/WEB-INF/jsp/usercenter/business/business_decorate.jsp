<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/tags.inc" %>
<%@ include file="../../pub/head.inc" %>
<html>
 <head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css"/>	
	<link rel="stylesheet" href="${CONTEXT}v1.0/css/member.css"/>	
	<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
	<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>  
	<script type="text/javascript" src="${CONTEXT}js/page.js"></script>
</head>

<body>
   <jsp:include page="../../usercenter/userCenter_head.jsp" flush="true"/> 
   <jsp:include page="../../usercenter/userCenter_left.jsp" flush="true"/> 
   		<div class="mid-right" style="background: #fff;">
			<h1 class="mid-right-store">店铺装修</h1>
			<div class="decoration-cont">
				<p class="dectit">商铺当前店招</p>
				<div class="der-picture clearfix">
					<ul id="picture_decor" class="multPic-box">
							
						<script type="text/uploader-files">
							${userImgUrl}
						</script>
    					<script type="text/uploader-theme">    
							<li id="queue-file-{id}" class="g-u" data-name="{name}">
	            				<div class="pic">
	                				<a href="javascript:void(0);"><img class="J_Pic_{id} preview-img" id="banelImg" src="" /></a>
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
	        				</li>
						</script>
<!-- 							            				
<div class="imageUploader_menu"><a href="javascript:void(0);" class="imageUploader_del" id="imageUploader_del_{id}" title="删除" >&nbsp;</a> </div>
 -->						
					</ul>	
				</div>
				<div class="det-upbtn-box clearfix">
					<input type="file" class="g-u input01" id="attestPicture_btn" value="上传新店招图片" name="attestPicture" />
		            <input type="hidden" id="userImgUrl" name="userImgUrl" value="${dto.userImgUrl!=null? dto.userImgUrl: '' }" />
		            <span class="dec-tips">图片大小1170*160，<span class="f-c-999">请上传2M以内图片，支持jpg、jpeg、gif、png、bmp格式</span></span>
				</div>
				<div class="dec-tab-box clearfix">
					<p class="dec-tab-t">或使用系统默认店招：</p>
					<ul class="dec-tab-list clearfix">
						<li class="${groupId==0?'dec-tab-curr': '' }">
						<a  href="${CONTEXT }userCenter/business/decorate/0"  >全部</a>
						</li>
						<c:forEach  items="${groups}" var="group" >
							 <li class="${groupId==group.groupId?'dec-tab-curr': '' }"><a href="${CONTEXT }userCenter/business/decorate/${group.groupId}"  >${group.groupName }</a></li>
						</c:forEach>	
					 
					</ul>
					<div class="default-dec-w">
					 
						<c:if test="${page!=null && not empty page.pageData}">
							
							<form id="pageForm" action="${CONTEXT }userCenter/business/decorate/${groupId==null?'0': groupId }" method="post">
								<input type="hidden" id="page" name="page" value="${page.currentPage}">
								<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
								<input type="hidden" id="pageTotal" name="pageTotal" value="${page.pageTotal}">
								<input type="hidden" id="sort" name="sort" value="${param.sort}">
								<input type="hidden" id="order" name="order" value="${param.order}">
							</form>
							
					  			  <ul class="default-banner">
									<!-- 分页页面模板 start -->
			 						<c:set var="index" value="0" /> 
									<c:forEach items="${page.pageData}" var="banel" varStatus="s">
										<li class="dec-w-item">
											<img src="${imgHostUrl}${banel.banelImgUrl}" data-url="${imgHostUrl}${banel.banelImgUrl}" alt="">
											<div class="dec-tool clearfix"><span class="fl">${banel.groupName}店招(${index+1})</span><a href="javascript:;" class="dec-toggle"  onclick="changeUrl(${banel.id});">使用店招<span class="dec-arrow"></span></a></div>
										</li>
										<c:set var="index" value="${(index+1)%2}" /> 
									</c:forEach>
								</ul>
							
								<!-- 分页 页码标签 根据具体UI修改 start -->
								<jsp:include page="/WEB-INF/jsp/pub/PageNumTemplate.jsp"></jsp:include>
								<!-- 分页 页码标签 根据具体UI修改 end -->
								</c:if>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	 
	<script type="text/javascript">
	 

 	function changeGroup(id){
 		$.ajax({
			type: 'POST',
			url: CONTEXT+'userCenter/business/getBanelImgByGroupId/'+id ,
		    data: '' ,
		    dataType: 'json' ,
		    success: function(data) {
		    	var obj = eval(data);
		    	document.getElementById("banelImg").src = obj[0].banelImgUrl; 
		    } ,
		    error: function(err) {
		    	alert('系统维护中。。。');
		    }
		});
 	}
	
	
	
 	function changeUrl(id){
 		$.ajax({
			type: 'POST',
			url: CONTEXT+'userCenter/business/getBanelImgurl/'+id ,
		    data: '' ,
		    dataType: 'json' ,
		    success: function(data) {
	    	 	 var obj = eval(data);
	    	 	 document.getElementById("banelImg").src = '${imgHostUrl}'+obj.url; 
		    } ,
		    error: function(err) {
		    	alert('系统维护中。。。');
		    }
		});
 	}
	
	
	var uploadUrl = "${CONTEXT }userCenter/business/uploadAttestPic";
	
 
	
	
	initUploadSignle("attestPicture_btn",uploadUrl, "picture_decor", "userImgUrl");
	function initUploadSignle(uploadBtn, uploadUrl, queueId, JUrls, pictureCountLimit, filesStr){
		var config_mutiple = {
		        galleryUrl : "${CONTEXT }v1.0/js/uploader/",//控件静态地址
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
	
	
	
	
	
	
	
	
	
	
	
		</script>
 	 
 		
 
</html>

 
 						