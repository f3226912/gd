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
			<li><a href="${CONTEXT }userCenter/attest/per">个人实名认证</a></li>
			<li class="att-tab-cur"><a href="javascript:;">企业实名认证</a></li>
		</ul>
	</div>
	<div class="attest-list">
	<!--个人认证 start-->
	<div class="att-tab-c">
		<div class="att-tab-c">
		<div class="att-tips2 f-c-d50000 tac">温馨提示：通过个人实名认证您可以获得一定的用户等级，让您获得更多的关注度。
		<c:if test="${cerifiable}"><br/>您已经通过验证!
		<script>
			layer.msg('您已经通过验证!');
		</script>
		</c:if>
		<c:if test="${!cerifiable && not empty cerifi}"><br/>您已经提交过认证，可不用再上传营业执照图和组织机构代码证号码图!</c:if>
		</div>
		<form name="" id="addattestent_form" method="post" action="${CONTEXT }userCenter/attest/addEnt" >
			<ul class="attest-list-wrap">
				<li class="clearfix">
					<div class="inp-tit"><em class="rse-pro-tit-red">*</em>企业名称：</div>
					<div class="fl">
						<span class="bor">
							<input type="text" id="companyName" name="companyName" class="input-text input-mem" value="${cerifi!=null? cerifi.companyName:'' }" placeholder="企业名称需与证件名称保持一致" maxlength="50" data-err="企业名称只能为4-50个字" data-po="请输入企业名称"/>
							<span>(注：通过企业实名认证后将不能再修改，请确保填写正确）</span>	
						</span>
						<span class="info mt3 clinfo"></span>
					</div>
				</li>
				<li class="clearfix">
					<div class="inp-tit"><em class="rse-pro-tit-red">*</em>法人代表：</div>
					<div class="fl">
						<span class="bor">
							<input class="input-text input-mem" type="text" placeholder="请输入法人姓名" id="linkMan" name="linkMan" value="${cerifi!=null? cerifi.linkMan:'' }" maxlength="40" data-po="请输入法人姓名" data-err="请输入法人姓名">
						</span>
						<span class="info mt3"></span>
					</div>
				</li>
				<li class="clearfix">
					<div class="inp-tit"><em class="rse-pro-tit-red">*</em>营业执照号码：</div>
					<div class="fl">
						<span class="bor">
							<input class="input-text input-mem" type="text" id="bzl" placeholder="执照号码与图片号码需要保持一致" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false" name="bzl" value="${cerifi!=null? cerifi.bzl:'' }" maxlength="40" data-po="请输入营业执照号码" data-err="请输入营业执照号码">
						</span>
						<span class="info mt3"></span>
					</div>
				</li>
				<c:if test="${!cerifiable}">
				<li class="clearfix">
					<div class="inp-tit"><em class="rse-pro-tit-red">*</em>上传图片：</div>
					<div class="fl">
						<div class="clearfix" id="uploadContainer2">
				            <input type="file" class="g-u input01" id="licenUploadbtn" value="上传图片" name="attestPicture" />
				            <input type="hidden" id="licen_Urls" name="bzlPhotoUrl" value="${cerifi!=null? cerifi.bzlPhotoUrl:'' }" />
				        </div>
				        <div class="form-context-u">
				        	<div class="clear"></div>
				            <ul id="licenPicture" class="multPic-box"></ul>
				        </div>
				        <div class="att-tips">
				        	请上传1张图片，<br>
							支持jpg、png、gif格式，<br>
							每张最大2M，建议上传清晰、真实图片<br>
							<a href="javascript:;" class="js-showLesen">【案例展示】</a>
				        </div>
						<span class="info mt3"></span>
					</div>
				</li>
				</c:if>
				<li class="clearfix">
					<div class="inp-tit"><em class="rse-pro-tit-red"></em>组织机构代码证号码：</div>
					<div class="fl">
						<span class="bor">
							<input class="input-text input-mem" type="text" placeholder="请输入组织机构代码证号码" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false" id="orgCode" name="orgCode" value="${cerifi!=null? cerifi.orgCode:'' }" maxlength="40">
						</span>
						<span class="info mt3"></span>
					</div>
				</li>
				<c:if test="${!cerifiable}">
				<li class="clearfix">
					<div class="inp-tit"><em class="rse-pro-tit-red"></em>上传图片：</div>
					<div class="fl">
						<div class="clearfix" id="uploadContainer3">
				            <input type="file" class="g-u input01" id="organizationUploadbtn" value="上传图片" name="attestPicture" />
				            <input type="hidden" id="organization_Urls" name="orgCodePhotoUrl" value="${cerifi!=null? cerifi.orgCodePhotoUrl:'' }" />
				        </div>
				        <div class="form-context-u">
				            <div class="clear"></div>
				            <ul id="organizationPicture_queen" class="multPic-box"></ul>
				        </div>
				        <div class="att-tips">
				        	请上传1张图片，<br>
							支持jpg、png、gif格式，<br>
							每张最大2M，建议上传清晰、真实图片<br>
							<a href="javascript:;" class="js-showLOrg">【案例展示】</a>
				        </div>
						<span class="info mt3"></span>
					</div>
				</li>
				</c:if>
				<li class="clearfix">
					<div class="inp-tit">&nbsp;</div>
					<c:if test="${!cerifiable}">
					<div class="fl">
						<a href="javascript:subCheck();" class="btn-submit-u">提交</a>
					</div>
					</c:if>
				</li>
				
			</ul>
		</form>	
		<div class="att-tips2">
			<p class="f-c-d50000 pb10">会员提交后在工作日24小时内完成审核。审核结果请前往消息中心查看</p>
			<p>审核须知：</p>
			<p>1.请上传有效的营业执照与组织机构代码照片或扫描件</p>
			<p>2.商铺名称与营业执照、组织机构代码上的名称必须一致</p>
			<p>3.商铺名称、有效期及证件号码清晰可辨</p>

		</div>		
	</div>
	</div>
	</div>
	</div>
	<!--企业认证 end-->
	<script>
	function subCheck() {
		$("#companyName").val($.trim($("#companyName").val()));
		$("#linkMan").val($.trim($("#linkMan").val()));
		$("#bzl").val($.trim($("#bzl").val()));
		$("#orgCode").val($.trim($("#orgCode").val()));
		
		if($("#companyName").val()=="") {
			layer.msg('请输入企业名',{cusClass:' sss'});
			return;
		}
		
		var reg = /^[a-zA-Z\u4E00-\u9FA5]{4,50}$/;
		
		if($("#companyName").val().indexOf(" ")>-1) {
			layer.msg('企业名称中不能添加空格',{cusClass:' sss'});
			return;
		}
		
		if(!reg.test($("#companyName").val())) {
			layer.msg('企业名称只能为4-50位汉字或英文字母',{cusClass:' sss'});
			return;
		}
		
		if($("#linkMan").val()=="") {
			layer.msg('请输入法人代表',{cusClass:' sss'});
			return;
		}
		
		if($("#linkMan").val().indexOf(" ")>-1) {
			layer.msg('法人代表中不能添加空格',{cusClass:' sss'});
			return;
		}
		
		reg = /^[\u4E00-\u9FA5a-zA-Z]{2,20}$/;
		if(!reg.test($("#linkMan").val())) {
			layer.msg('法人代表必须为4-20位汉字或英文字母',{cusClass:' sss'});
			return;
		}
		
		if($("#bzl").val()=="") {
			layer.msg('请输入营业执照号码',{cusClass:' sss'});
			return;
		}
		
		if($("#licen_Urls").val()=="") {
			layer.msg('请上传营业执照照片',{cusClass:' sss'});
			return;
		}
		
	/* 	if($("#orgCode").val()=="") {
			layer.msg('请输入组织机构代码证号码',{cusClass:' sss'});
			return;
		} 
		
		reg = /^\d{9}$/;
		if($("#orgCode").val()!="" && !reg.test($("#orgCode").val())) {
			layer.msg('组织机构代码证号码必须为9位数字',{cusClass:' sss'});
			return;
		}*/
		
		if($("#orgCode").val()!="" && $("#organization_Urls").val()=="") {
			layer.msg('请上传组织机构代码证照片',{cusClass:' sss'});
			return;
		}
		
		$.post($("#addattestent_form").attr("action"),$("#addattestent_form").serialize(),function(res){
			var resJson = eval('(' + res + ')');
		    if(resJson.status==1) {
		    	layer.msg('已提交企业认证信息',{cusClass:' sss'},function(){location.reload();});
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
		        maxSize: 2048,
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
		initUploadModule("licenUploadbtn", uploadUrl,  "licenPicture", "licen_Urls", 1);
		initUploadModule("organizationUploadbtn", uploadUrl,  "organizationPicture_queen", "organization_Urls", 1);
	});
	</script>
	<!--简版底部 star-->
	
	<!--简版底部 end-->
</html>
