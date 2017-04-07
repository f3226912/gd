<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../pub/tags.inc" %>
<!doctype html>
<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<%@ include file="../../pub/constants.inc" %>
	<%@ include file="../../pub/head.inc" %>
	
	<script type="text/javascript" src="${CONTEXT}js/kissy-min.js"></script>
	<script type="text/javascript" src="${CONTEXT}js/gd.kissy.uploader.js"></script>  
	
 
</head>
<body>
   <jsp:include page="../userCenter_head.jsp" flush="true"/> 
   <jsp:include page="../userCenter_left.jsp" flush="true"/> 
 
 	<div class="mid-right bg-white">
	<div class="att-tab-wrap">
		<ul class="att-tab clearfix">
			<li class="att-tab-cur"><a>个人实名认证</a></li>
			<li><a href="${CONTEXT }userCenter/attest/ent">企业实名认证</a></li>
		</ul>
	</div>
	<div class="attest-list">
	<!--个人认证 start-->
	<div class="att-tab-c">
		<div class="att-tips2 f-c-d50000 tac">温馨提示：通过个人实名认证您可以获得一定的用户等级，让您获得更多的关注度。
		<c:if test="${cerifiable}"><br/>您已经通过验证!
		<script>
			layer.msg('您已经通过验证!');
		</script>
		</c:if>
		<c:if test="${!cerifiable && not empty cerifi}"><br/>您已经提交过认证，可不用再上传身份证号码图!</c:if>
		</div>
		<form id="addattestpre_form" name="" method="post" action="${CONTEXT }userCenter/attest/addPer" >
			<ul class="attest-list-wrap">
				<!-- li class="clearfix">
					<div class="inp-tit""><em class="rse-pro-tit-red">*</em>所在农批市场：</div>
					<div class="fl">
						<span class="bor">
							<select name="market" class="mem-select">
								
					        	<c:forEach items="${markets }" var="market">
					        		<option value="${market.id }">${market.marketName }</option>
					        	</c:forEach>
					        	
					        </select>
						</span>
						<span class="info mt3"></span>
					</div>
				</li> -->
				<li class="clearfix">
					<div class="inp-tit"><em class="rse-pro-tit-red">*</em>姓名: </div>
					<div class="fl">
						<span class="bor">
							<input class="input-text input-mem" type="text" placeholder="请输入姓名" id="linkMan" name="linkMan" value="${cerifi!=null? cerifi.linkMan: '' }" maxlength="20" data-po="请输入您的真实姓名" data-err="请输入您的真实姓名">
						</span>
						<span class="info mt3"></span>
					</div>
				</li>
				<li class="clearfix">
					<div class="inp-tit"><em class="rse-pro-tit-red">*</em>身份证号码：</div>
					<div class="fl">
						<span class="bor">
						
							<input class="input-text input-mem" type="text" placeholder="请输入您的身份证号码" id="idCard" oncut="return false" value="${cerifi!=null? cerifi.idCard: '' }" oncopy="return false" oncontextmenu="return false" onpaste="return false" name="idCard" maxlength="20" data-po="请输入您的身份证号码" data-err="请输入正确的身份证号码">
						</span>
						<span class="info mt3"></span>
					</div>
				</li>
				<c:if test="${!cerifiable}">
				<li class="clearfix" >
					<div class="inp-tit"><em class="rse-pro-tit-red">*</em>上传图片：</div>
					<div class="fl">
						<div class="clearfix" id="uploadContainer">
				            <input type="file" class="g-u input01" id="attestPicture_btn" value="上传图片" name="attestPicture" />
				            <input type="hidden" id="cardPhoto_url" name="cardPhotoUrl" value="${cerifi!=null? cerifi.cardPhotoUrl: '' }" />
        				</div>
				        <div class="form-context-u">
				            <ul id="picture_queen" class="multPic-box"></ul>
							
				        </div>
				        <div class="att-tips">
				        	总共需要上传2张图片，<br>
							支持jpg、png、gif格式，<br>
							每张最大2M，建议上传清晰真实图片<br>
							<a href="javascript:;" class="js-showTemp">【案例展示】</a>
				        </div>
			        </div>
				</li>
				</c:if>
				<li class="clearfix">
					<div class="inp-tit">&nbsp;</div>
					<c:if test="${!cerifiable}">
						<div class="fl">
							<a href="javascript:subCheck();" class="btn-submit-u" >提交</a>
						</div>
					</c:if>
				</li>
			</ul>
		</form>	
		<div class="att-tips2">
			<p class="f-c-d50000 pb10">会员提交后在工作日24小时内完成审核。审核结果请前往消息中心查看</p>
			<p>审核须知：</p>
			<p>1.联系人姓名与身份证的姓名必须保持一致</p>
			<p>2.身份证的照片、姓名及身份证号清晰可辨</p>
			<p>3.临时身份证或第一代身份证将无法通过审核</p>

		</div>
	</div>
	</div>
	</div>
	<!--个人认证 end-->
	<script>
	function subCheck() {
		$("#linkMan").val($.trim($("#linkMan").val()));
		
		if($("#linkMan").val()=="") {
			layer.msg('请输入姓名',{cusClass:' sss'});
			return;
		}
		
		if($("#linkMan").val().indexOf(" ")>-1) {
			layer.msg('姓名中不能添加空格',{cusClass:' sss'});
			return;
		}
		
		var reg = /^[\u4E00-\u9FA5]{2,10}$/;
		
		if(!reg.test($("#linkMan").val())) {
			layer.msg('姓名必须为2-10位汉字',{cusClass:' sss'});
			return;
		}
		
		if($("#idCard").val()=="") {
			layer.msg('请输入身份证号码',{cusClass:' sss'});
			return;
		}
		
		reg=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
		
		if(!reg.test($("#idCard").val())) {
			layer.msg('身份证号码不正确，请确认',{cusClass:' sss'});
			return;
		}
		
		if($("#cardPhoto_url").val()=="") {
			layer.msg('请上传身份证照片',{cusClass:' sss'});
			return;
		}
		
		if($("#cardPhoto_url").val().indexOf(",")<0) {
			layer.msg('身份证照片需要正反两面',{cusClass:' sss'});
			return;
		}
		
		$.post($("#addattestpre_form").attr("action"),$("#addattestpre_form").serialize(),function(res){
			var resJson = eval('(' + res + ')');
		    if(resJson.status==1) {
		    	layer.msg('已提交个人认证信息',{cusClass:' sss'},function(){location.reload();});
		    }
		 });
		
	}
	
	
	function initUploadModule(uploadBtn, uploadUrl,  queueId, JUrls, pictureCountLimit){
		var config_mutiple = {
		        galleryUrl : CONTEXT+"js/uploader/",
		        uploadBtn : uploadBtn,
		        queueId : queueId,
		        JUrls : JUrls,
		        action : uploadUrl,
		        max : pictureCountLimit,
		        maxSize:2048,
		        imgUrlPrefix : '${imgHostUrl}',
		        type :"ajax"
		};
		gdKissyMultipleUploader.init(config_mutiple ,function(uploader){
		     var filesStr = '';
		     var certification = '';
		     if(filesStr != ''){
		        var fileList = eval("("+  filesStr + ")");
		        
		        gdKissyMultipleUploader.addFiles(fileList,uploader);
		        
		        if(certification == 0){
		                $("#editForm #uploadContainer").find(".file-input").attr("disabled","disabled");
		        }
		     }
		},function(url){
		    if(url!='' && typeof(url!='undefined')){
		        var urlarray = url.split(",");
		        for (var i = 0; i < urlarray.length; i++) {
		            $("#editForm #picContainer").append("<input name=\"pictureUrl\" id=\"url+"+i+"\" type=\"hidden\" value=\""+urlarray[i]+"\" />");
		        }
		        
		    }
		});
	}
	
	var uploadUrl = "${CONTEXT }userCenter/attest/uploadAttestPic";
	
	$(document).ready(function() {
		initUploadModule("attestPicture_btn", uploadUrl,  "picture_queen", "cardPhoto_url", 2);
	});
	</script>
	<!--简版底部 star-->
	
	<!--简版底部 end-->
</html>
