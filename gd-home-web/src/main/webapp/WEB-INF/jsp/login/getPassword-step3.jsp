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
					<ul class="step-bar stepnum4 current-step3 ">
			            <li class="step-bar-item step-bar-item1"><span class="step-num">1</span><span class="step-txt">输入用户名</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item2"><span class="step-num">2</span><span class="step-txt">验证身份</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item3"><span class="step-num">3</span><span class="step-txt">设置新密码</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item4"><span class="step-num">4</span><span class="step-txt">确认提交，完成</span></li>
			        </ul>
	       		</div>
				<form name="getPasswordMail" action="${CONTEXT}login/commitGetPassword3" method="post" data-url="${CONTEXT}login/commitGetPassword3">
					<ul class="reg-list get-list">	
						<li class="clearfix">
							<div class="inp-tit">设置密码：</div>
							<div class="fl">
								<span class="bor">
									<input class="input-text input-getpwd" type="password" placeholder="请输入您的新密码，长度6-20位" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false" name="password" maxlength="20" data-po="请输入密码，长度6-20位" data-err="请重新输入密码，可以是数字、英文、特殊符号或组合。">
								</span>
								<input type="hidden" name="id" value="${id }">
								<span class="info mt3 info-err"></span>
								<p class="f-c-adadad clear pt5 pb15">6-20位字母、数字或标点符号的组合，区分大小写，不能与登录名、电子邮箱相同</p>
							</div>
						</li>
						<li class="clearfix">
							<div class="inp-tit">确认密码：</div>
							<div class="fl">
								<span class="bor">
									<input class="input-text input-getpwd" type="password" placeholder="请再次输入您的新密码，长度6-20位" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false" name="confirmPassword" maxlength="20" data-po="请再次输入密码，长度6-20位" data-err="请重新输入密码，可以是数字、英文、特殊符号或组合。">
								</span>
								<span class="info mt3 info-err"></span>
							</div>
						</li>

						<li class="clearfix">
							<div class="inp-tit">&nbsp;</div>
							<div class="fl">
								<a href="javascript:;" class="btn-red-dia btn-next">完成</a>
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
					case "password"://密码
						var length=$val.length,
							sibLeb=$leb.addClass("info-err");
						if(length==0){
							sibLeb.html("密码不能为空");
						}else if(length<6||length>20){
							sibLeb.html($th.attr("data-err"));
						/* }else if(length>=6&&/^[A-Z]+$/g.test($val)){
							sibLeb.html("密码不能为纯大写字母");
						}else if(length>=6&&/^[a-z]+$/g.test($val)){
							sibLeb.html("密码不能为纯小写字母");
						}else if(length>=6&&/^\W+$/g.test($val)){
							sibLeb.html("密码不能为纯符号"); */
						}else if(length>=6&&/^\s+$/g.test($val)){
							sibLeb.html("密码不能包含空格");
						}else{
							if(/^[A-Za-z0-9_]+$/g.test($val)||/^[\u2000-\u206F\u2E00-\u2E7F\\'!"#$%&()*+,\-.\/:;<=>?@\[\]^_`{|}~]+$/g.test($val)){
								$leb.removeClass("info-err").addClass("info-succ").html("&nbsp;");
							}
						}
					break;
					case "confirmPassword"://重复密码
						var pwdOne=$form.find("input[name='password']").val();
						if($val.length==0){
							$leb.addClass("info-err").html("确认密码不能为空");
							
						}else if($val.length<6){
							$leb.addClass("info-err").html($th.attr("data-err"));
							
						}else if(pwdOne.length>=6&&pwdOne!=$val&&$val.length>=6){
							$leb.addClass("info-err").html("两次密码输入不一致");
							
						}else if(pwdOne.length>=6&&pwdOne==$val){
							$leb.addClass("info-succ").html("&nbsp;");
						}else{
							$leb.html("");
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
				        		window.location.href = CONTEXT+"login/initGetPassword4";
				            }else{
				                //$(".login-tips").addClass("info info-err").show().html(data.msg);
				                fnbreak($form.find("input[name='password']"),data.msg);
				                $('.btn-next').val('完成');
				                changeAuthCode();
				            }

						}
					});
				}
			}
			

			var fnbreak=function(leb,info){
				$form.find('.btn-next').removeClass("no-push").html('完成');
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
	</script>
</html>
