<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		
		<meta name="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pragma" content="no-cache"/>
		<meta name="cache-control" content="no-cache"/>
		<meta name="expires" content="0"/>
		<meta name="keywords" content="农产品,农业产品,农产品批发,农产品交易网,农产品网上交易,谷登农批网"/>
		<meta name="description" content="谷登农批网是专业农产品网上交易平台，提供全面农产品批发市场信息，及时发布最新农业产品市场供需资讯，具有高效农产品物流体系，能够安全解决农产品交易中资金问题，作为第一O2O农批平台，谷登农批网为农产品行业人士推出一站式服务。"/>
		<meta name="renderer" content="webkit">
		<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>谷登农批网-专注农产品批发及农业产品网上交易平台-找回密码</title>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/sign.css"/>
	</head>
<body>
	<!--head start-->
	<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
	<div class="head-search">
		<div class="wrap-1170 head-logo clearfix">
			<div class="logo-wrap">
				<a href="${CONTEXT}"><img src="${CONTEXT}v1.0/images/logo.png" alt="谷登农批网-中国第一O2O农批平台"></a>
			</div>
			<div class="head-logo-t">找回密码</div>
		</div>
	</div>
	<!--head end-->

	<div class="wrap-1170 login-wrap i-box-shadow clearfix getpwd-wrapa">
		<div class="getpwd-box">
			
			<div class="tab-cntent">
				<div class="get-tips">忘记密码啦？您可以按照如下步骤找回您的密码！</div>
				<div class="get-tips-pce clearfix">
					<ul class="step-bar stepnum4 current-step1 ">
			            <li class="step-bar-item step-bar-item1"><span class="step-num">1</span><span class="step-txt">输入用户名</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item2"><span class="step-num">2</span><span class="step-txt">验证身份</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item3"><span class="step-num">3</span><span class="step-txt">设置新密码</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item4"><span class="step-num">4</span><span class="step-txt">确认提交，完成</span></li>
			        </ul>
	       		</div>
				<form name="getPasswordMobile" action="${CONTEXT}login/commitGetPassword1" method="post" data-url="${CONTEXT}login/commitGetPassword1" >
					<ul class="reg-list get-list">
						<li class="clearfix">
							<div class="inp-tit">账  户 名：</div>
							<div class="fl">
								<span class="bor">
									<input type="text" name="userName" data-url="#" class="input-text input-getpwd" placeholder="请输入您的账户名" data-err="请输入正确的账户名" data-po="请输入您的账户名"/>	
								</span>
								<span class="info mt3"></span>
							</div>
						</li>
						<li class="clearfix">
							<div class="inp-tit">验  证 码：</div>
							<div class="fl">
								<span class="bor">
									<input type="text" name="randomCode" class="input-text input-getpwd input-getpwd-s" placeholder="验证码"  id="verifyCode" maxlength="4" data-err="请输入正确的验证码" data-po="请输入验证码"/>
								</span>
								<img src="${CONTEXT}login/initImg" data-img="${CONTEXT}login/initImg" width="95" height="33" id="code_img" onclick="changeAuthCode();">
								<span class="get-btn-wrap">看不清？<a href="javascript:changeAuthCode();" class="captcha-change">换一张</a></span>
								<span class="info mt3"></span>
							</div>
						</li>
						<li class="clearfix">
							<div class="inp-tit">&nbsp;</div>
							<div class="fl">
								<a href="javascript:;" class="btn-red-dia btn-next">下一步</a>
							</div>
						</li>
						
					</ul>
				</form>
			</div>		
			
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<script type="text/javascript">
	$(function(){
		(function(){
			var $form=$("form:visible");
			$form.find("input").blur(function(){
			//$("input").blur(function(){
				var $th=$(this),
					$val=$.trim($th.val()),
					$leb=$th.parent("span.bor").siblings("span.info"),
					defaultvalue = $th.attr("placeholder");
				$leb.removeClass("info-err");
				$leb.removeClass("info-succ f-c-f33550");
				$leb.html("");
				switch($th.attr("name")){
					case "userName":
						check_empty($th,"请输入您的账户名");
					break;
					case "randomCode"://验证码
						if($val==""||$val.length<4||$val==defaultvalue){
							$leb.addClass("info-err").html($th.attr("data-err"));
						}else{
							$leb.removeClass("info-err").html("");
						}
					break;
				}

			}).focus(function(){
				var $th=$(this),
					$leb=$th.parent("span.bor").siblings("span.info");
				$leb.removeClass("info-err");
				$leb.removeClass("info-succ f-c-f33550");
				$leb.removeClass("onload");
				$leb.html($th.attr("data-po"));
			});	

			
			var check_empty=function(tmp,msg){
				var $leb=tmp.parent("span.bor").siblings("span.info");
				if(tmp.val()==""){
					$leb.addClass("info-err").html(msg);
					return false;
				}
				return true;
			}
			var pushData=true;
			var getDataPush=function(){
				// if(!ref){
				// 	return false;
				// }
				for(var i=0;i<$form.find(".input-text").length;i++){
					if($form.find(".input-text").eq(i).val()==""||$form.find(".input-text").eq(i).val()==$form.find(".input-text").eq(i).attr("placeholder")){
						$form.find(".input-text").eq(i).focus();
						//$form.find(".input-text").eq(i).parent("span.bor").siblings("span.info").html($(this).attr("data-po"))
						// $leb.html($th.attr("data-po"));
						// $leb=$th.parent("span.bor").siblings("span.info");
						return false;
					}
				}
				if(pushData&&$form.find(".info-err").length<=0){
					pushData=false;
					$form.find('.btn-next').html('提交中...');
					var param=$form.serialize();
					$.ajax({
						url:$form.attr("data-url"),
						data:param,
						type:"POST",
						dataType: "json",
						success: function(data){
							pushData=true;
							if(data.msg==0){
				        		window.location.href = CONTEXT+"login/initGetPassword2/"+data.dto.memberId;
				            }else{
				                //$(".login-tips").addClass("info info-err").show().html(data.msg);
				                fnbreak($form.find("input[name='userName']"),data.msg);
				                $('.btn-next').val('下一步');
				                changeAuthCode();
				            }

						}
					});
				}
			}

			var fnbreak=function(leb,info){
				$form.find('.btn-next').removeClass("no-push").html('下一步');
				leb.parent().siblings(".info").removeClass("info-err");
				leb.parent().siblings(".info").removeClass("info-succ");
				leb.parent().siblings(".info").removeClass("onload");
				leb.parent().siblings(".info").addClass("info-err").html(info);
			}

			var submitData=function(){
				if($(".no-push").length<=0){				
					getDataPush();
				}
			}


			$(".get-list").delegate(".btn-next","click",function(event){
				//var $form=$("form:visible")
				event.preventDefault();
				submitData();
				return false;
			});
			//处理键盘的回车键登录
			$(document).keydown(function(event){
				if(event.keyCode==13){					
					submitData();
				}
			});

		})();

		
	});
	function changeAuthCode() {
	    var num =   new Date().getTime();
	    var rand = Math.round(Math.random() * 10000);
	    num = num + rand;
	    $("#code_img").attr('src',$("#code_img").attr("data-img") + "?" + num);
	        
	}
	</script>
</html>
