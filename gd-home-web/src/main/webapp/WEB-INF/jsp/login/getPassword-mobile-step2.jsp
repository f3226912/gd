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
					<ul class="step-bar stepnum4 current-step2 ">
			            <li class="step-bar-item step-bar-item1"><span class="step-num">1</span><span class="step-txt">输入用户名</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item2"><span class="step-num">2</span><span class="step-txt">验证身份</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item3"><span class="step-num">3</span><span class="step-txt">设置新密码</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item4"><span class="step-num">4</span><span class="step-txt">确认提交，完成</span></li>
			        </ul>
	       		</div>
				<form name="getPasswordMobile" action="${CONTEXT}login/commitGetPassword2" method="post" data-url="${CONTEXT}login/commitGetPassword2" >
					<ul class="reg-list get-list">
						<li class="clearfix hauto">
							<div class="inp-tit">验证方式：</div>
							<div class="fl">
								<select name="" class="com-select get-select" >
									<option value="0" checked>手机号</option>
								</select>
								<span class="f-c-adadad ml10">为了保证您账户的安全我们需要您进行身份验证，请选择您验证身份的方式。</span>
							</div>
						</li>
						<li class="clearfix hauto get-txt-li">
							<div class="inp-tit">已验证手机：</div>
							<div class="fl ">
								<span class="get-numt">${mobile}</span>
								<span class="f-c-adadad">若当前手机已不用，或无法收到验证码？若您已通过实名认证，您可以提交申请单由客服帮助处理。</span>
								<input type="hidden" name="mobile" value="${dto.mobile }">
								<input type="hidden" name="id" id="id" value="${dto.memberId }">	
							</div>
						</li>						
						<li class="clearfix">
							<div class="inp-tit">短信验证码：</div>
							<div class="fl">
								<span class="bor mr01">
									<input type="text" name="mobileVerifyCode" class="input-text mobileVerifyCode-input2" placeholder="验证码" maxlength="20" data-err="请输入正确的验证码" data-po="请输入收到的验证码">	
								</span>

								<a href="javascript:;" class="mcodebtn" data-url="${CONTEXT }login/sendCodeByGetPassword" data-po="短信验证码已发送到您的手机">获取手机验证码</a>
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
					case "mobileVerifyCode"://手机验证码
						check_empty($th,"请输入短信验证码");
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
				        		window.location.href = CONTEXT+"login/initGetPassword3/"+$("#id").val();
				            }else{
				                //$(".login-tips").addClass("info info-err").show().html(data.msg);
				                fnbreak($form.find("input[name='mobileVerifyCode']"),data.msg);
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

			/**验证倒计时**/
			var timeLefts;
			var timeDwon=function(leb,time){
				leb.html(time+"秒后重新获取");
				if(!time--){
					stopInfo(leb);
				}else{
					timeLefts = setTimeout(function(){timeDwon($(".mcodebtn"),time)},1000);
					leb.addClass("mcodebtn-disabled");
				}
			}

			var stopInfo=function(leb){
				leb.html("获取短信验证码 ");
				leb.removeClass("mcodebtn-disabled");
				//$(".br-height").html($(".mcodebtn").attr("data-po"));
				$form.find("input[name='mobile']").removeAttr("disabled");
			}

			/**获取验证码**/
			var pushCode=function(info){
				var leb=$(".mcodebtn").siblings(".info");
				leb.removeClass("info-err");
				leb.removeClass("info-succ");
				//$form.find("input[name='mobile']").attr("disabled","disabled");
				timeDwon($(".mcodebtn"),60);
				$.ajax({
					url:$(".mcodebtn").attr("data-url"),
					type:"post",
					data:{"mobile":$form.find("input[name='mobile']").val()},
					dataType:"json",
					success:function(data){
						switch(data.msg){
							case "1":
								//timeDwon($(".mcodebtn"),60);
								leb.html("");
								break;
							case "3":
								stopInfo($(".mcodebtn"));
								leb.addClass("info-err").html(data.code);
								break;
							case "27":
								stopInfo($(".mcodebtn"));
								leb.addClass("info-err").html(data.code);
								break;
							// case "26":
							// 	stopInfo($(".mcodebtn"));
							// 	leb.addClass("info-err").html("非正常跳转用户，禁止发送短信");
							// 	break;
							// case "27":
							// 	stopInfo($(".mcodebtn"));
							// 	leb.addClass("info-err").html("发送验证码失败");
							// 	break;
							// case "22":
							// 	stopInfo($(".mcodebtn"));
							// 	leb.addClass("info-err").html("手机号码为空");
							// 	break;
							// case "29":
							// 	timeDwon($(".mcodebtn"),$('.mcodebtn i').html());
							// 	leb.addClass("info-err").html("验证码的发送时间间隔为60秒，请稍后再试");
							// 	break;
						}
					},
					error:function(){
						leb.addClass("info-err").html("网络繁忙，请稍后重试");
					}
				});
			}


			var switchCode=function(tmp){
				var timenow =eval(+(new Date));
				var imgUrl=$(".captcha").attr("data-img");
				tmp.parents("li").find("input").val("");
				tmp.parents("li").find("img").attr("src", imgUrl+"?" + timenow);
			}

			//刷新验证码
			$(".get-list").delegate(".re-captcha","click",function(event){
				event.preventDefault();
				var $th=$(this);
				switchCode($th);
				return false;
			});
			$("ul.get-list").delegate(".captcha","click",function(){
				var $th=$(this);
				var timenow =eval(+(new Date));
				var imgUrl=$("ul.get-list .captcha").attr("data-img");
				$th.attr("src", imgUrl+"?" + timenow);
			});

			//获取手机验证码
			$(".get-list").delegate(".mcodebtn","click",function(){
				
				//$(".br-height").css("display","inline-block").html($(".send-vcode").attr("data-po"));
				if(!$(this).hasClass("mcodebtn-disabled")){
					clearTimeout(timeLefts);
					pushCode($(".mobileVerifyCode").attr("data-po"));
				}
				
			});


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
	</script>
</html>
