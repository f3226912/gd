<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil,org.apache.commons.lang3.StringUtils" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.gdeng.cn/gd" prefix="gd" %>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<%
	String marketId = (String)request.getAttribute("marketId");
	if(StringUtils.isEmpty(marketId)){
	    String cookieMarketId=CookieUtil.getValue("marketId");
		marketId=StringUtils.isEmpty(cookieMarketId) ? "1" : cookieMarketId;
	} 
	request.setAttribute("marketId", marketId);
%>

<!DOCTYPE html>
<html>
<head>		
<meta name="Content-Type" content="text/html; charset=utf-8" />
<meta name="pragma" content="no-cache"/>
<meta name="cache-control" content="no-cache"/>
<meta name="expires" content="0"/>
<meta name="keywords" content="农产品交易平台注册,农产品O2O,农产品网注册,农批网注册"/>
<meta name="description" content="免费注册谷登农批网，拓展农产品推广渠道，获取海量农产品客户资源,降低农产品交易成本；谷登农批网是农产品批发市场及农产品交易的首选平台。"/>
<meta name="renderer" content="webkit">
<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
<title>欢迎注册-谷登农批网</title>
<%@ include file="../pub/head.inc" %>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/sign.css"/>
</head>
<body>
	<!--head start-->
	<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
	<div class="head-search">
		<div class="wrap-1170 head-logo clearfix">
			<div class="logo-wrap">
				<a href="${CONTEXT}${gd:formatMarket(marketId) }.html"><img src="${CONTEXT}v1.0/images/logo.png" alt="谷登农批网-中国第一O2O农批平台"></a>
			</div>
			<div class="head-logo-t">欢迎注册</div>
		</div>
	</div>
	<!--head end-->
	
	<div class="login-wrap wrap-1170 clearfix">
		<div class="reg-box  i-box-shadow clearfix">
			<div class="reg-l-left">
				<ul class="reg-tab">
					<li class="item-cur"><a href="javascript:;">采购商</a></li>
					<li><a href="javascript:;">农批商</a></li>
					<li><a href="javascript:;">产地供应商</a></li>
				</ul>
				<div class="reg-process">
					<ul class="step-bar stepnum2 clearfix current-step1">
			            <li class="step-bar-item step-bar-item1"><span class="step-num">1</span><span class="step-txt">输入注册信息</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item2"><span class="step-num">2</span><span class="step-txt">完成注册</span></li>
			        </ul>
			        <ul class="step-bar stepnum3 clearfix current-step1" style="display:none">
			            <li class="step-bar-item step-bar-item1"><span class="step-num">1</span><span class="step-txt">输入注册信息</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item2"><span class="step-num">2</span><span class="step-txt">完善商铺信息</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item3"><span class="step-num">3</span><span class="step-txt">完成注册</span></li>
			        </ul>
			        <ul class="step-bar stepnum3 clearfix current-step1" style="display:none">
			            <li class="step-bar-item step-bar-item1"><span class="step-num">1</span><span class="step-txt">输入注册信息</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item2"><span class="step-num">2</span><span class="step-txt">完善商铺信息</span><span class="step-line"></span></li>
			            <li class="step-bar-item step-bar-item3"><span class="step-num">3</span><span class="step-txt">完成注册</span></li>
			        </ul>
				</div>
				<div class="reg-step1">
				<form name="register" action="${CONTEXT }login/register" method="post" data-url="${CONTEXT }login/register" >
					<input type="hidden" id="regType" name="regType" value="0" />
					<input type="hidden" name="regstep" value="1" />
					<ul class="reg-list">
<!-- 						<li class="clearfix"> -->
<!-- 							<div class="inp-tit">用户名：</div> -->
<!-- 							<div class="fl"> -->
<!-- 								<span class="bor"> -->
<!-- 									<input type="text" name="username" class="input-text input-reg" placeholder="请输入用户名" maxlength="16" data-err="请输入用户名由6-16个字母、数字、下划线组合" data-po="请输入用户名由6-16个字母、数字、下划线组合"> -->										
<!-- 								</span> -->
<!-- 								<span class="info mt3"></span> -->
<!-- 							</div> -->
<!-- 						</li>						 -->
						<li class="clearfix">
							<div class="inp-tit">手机号码：</div>
							<div class="fl">
								<span class="bor">
									<input type="text" name="mobile" class="input-text input-reg" placeholder="请输入手机号码" maxlength="11" data-err="请输入正确的手机号码" data-po="请输入手机号码"/>	
									<input type="hidden" id="BackUrl" name="BackUrl" value="${BackUrl }">
								</span>
								<span class="info mt3"></span>
							</div>
						</li>
						<li class="clearfix">
							<div class="inp-tit">密码：</div>
							<div class="fl">
								<span class="bor">
									<input class="input-text input-reg" type="password" placeholder="请输入密码，长度6-20位" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false" name="password" maxlength="20" data-po="6~20位字符，至少包含数字、大写字母、小写字母、符号" data-err="请重新输入密码，可以是数字、英文、特殊符号或组合。">
								</span>
								<span class="info mt3"></span>
							</div>
						</li>
						<li class="clearfix">
							<div class="inp-tit">确认密码：</div>
							<div class="fl">
								<span class="bor">
									<input class="input-text input-reg" type="password" placeholder="请再次输入密码，长度6-20位" oncut="return false" oncopy="return false" oncontextmenu="return false" onpaste="return false" name="confirmPassword" maxlength="20" data-po="请再次输入密码" data-err="请重新输入密码，可以是数字、英文、特殊符号或组合。">
								</span>
								<span class="info mt3"></span>
							</div>
						</li>
						
						<li class="clearfix">
							<div class="inp-tit">手机验证码：</div>
							<div class="fl">
								<span class="bor mr01">
									<input type="text" name="mobileVerifyCode" class="input-text mobileVerifyCode-input" placeholder="请输入短信验证码" maxlength="20" data-err="请输入正确的短信验证码" data-po="请输入短信验证码"/>	
								</span>

								<a href="javascript:;" id="yumobiles" class="mcodebtn" data-url="${CONTEXT }login/sendCode" data-po="短信验证码已发送到您的手机">获取验证码</a>
								<span class="info mt3"></span>
							</div>
						</li>
						<li class="clearfix agree-li">
							<div class="inp-tit">&nbsp;</div>
							<div class="fl">
								<span> <label class="leb-txt2"><input class="account_agreeProtocol" value="1" checked="checked" type="checkbox" name="agre">我已阅读并同意《<a href="javascript:;" class="f-c-12adff js-showAgreement">谷登农批服务条款</a>》</label></span>
							</div>
						</li>
						
						<li class="clearfix">
							<div class="inp-tit">&nbsp;</div>
							<div class="fl">
								<a href="javascript:;" class="btn-red-dia btn-reg">免费注册</a>
							</div>
						</li>
						
					</ul>
				</form>	
	
				</div>

				<!--农批商第二步-->
				<div class="nps-step2" style="display:none">
					<form name="registernps2" id="npsregister" action="${CONTEXT }/login/npsStep" method="post" data-url="${CONTEXT }/login/npsStep" >
						<input type="hidden" name="regType" value="1" />
						<input type="hidden" name="regstep" value="2" />
						<ul class="reg-list">
							<li class="clearfix">
								<div class="inp-tit">商铺名称</div>
								<div class="fl">
									<span class="bor">
										<input type="text" name="shopname" class="input-text input-reg" placeholder="请输入商铺名" maxlength="50" data-err="请正确输入商铺名" data-po="请输入商铺名">	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>						
							<li class="clearfix min60">
								<div class="inp-tit">经营模式：</div>
								<div class="fl">
									<span class="bor">
										<select name="tradetype" class="com-select reg-select" data-err="请经营模式">
											<option value="">请选择</option>
									        <option value="0">个人经营</option>
									        <option value="1">企业经营</option>
										</select>	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>
							<li class="clearfix min60">
								<div class="inp-tit">农批市场所在地：</div>
								<div class="fl">
									<span class="bor">
										<select name="market" class="com-select reg-select change-item" onchange="changerV(this.value)" data-err="请选择所在市场">
								            <option value="">请选择</option>
							    			<!--<c:forEach  items="${markets}" var="market" >
											 <option value="${market.id}">${market.marketName}</option>
											</c:forEach>-->
											<option value="1" >武汉白沙洲批发市场</option>			 	
							 				<option value="2" >广西玉林批发市场</option>
								        </select>	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>
							<li class="clearfix">
								<div class="inp-tit">主营分类：</div>
									<div class="fl seritem-box">
										<span class="bor ">
										
								<c:forEach var="map" items="${mapM}">
										<div class="con-pro-cit-area-bx clearfix" id="market${map.key}" style="display:none;">
								            <c:forEach  items="${map.value}" var="productCategoryDTO" >
												<span class="con-pro-cit-area-sp">
													<input class="pro-cit-area-lab" type="checkbox" name="categoryId" id="categoryId"
													value="${productCategoryDTO.categoryId}"
													><label>${productCategoryDTO.cateName}</label></span>
											</c:forEach>									
										</div>
								</c:forEach>
								
									</span>
									<span class="info mt3"></span>
								</div>
							</li>
							<li class="clearfix">
								<div class="inp-tit">&nbsp;</div>
								<div class="fl">
									<a href="javascript:;" class="btn-red-dia btn-reg nps2">完成注册</a>
								</div>
							</li>
							
						</ul>
					</form>
				</div>

				<!--产地供应商第二步-->
				<div class="cds-step2" style="display:none">
					<form name="register" action="/user/register" method="post" data-url="${CONTEXT }/login/cdsStep" >
						<input type="hidden" name="regType" value="2" />
						<input type="hidden" name="regstep" value="2" />
						<ul class="reg-list">
							<li class="clearfix">
								<div class="inp-tit">商铺名称：</div>
								<div class="fl">
									<span class="bor">
										<input type="text" name="shopname" class="input-text input-reg" placeholder="请输入商铺名" maxlength="50" data-err="请正确输入商铺名" data-po="请输入商铺名">	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>						
							<li class="clearfix min60">
								<div class="inp-tit">经营模式：</div>
								<div class="fl">
									<span class="bor">
										<select name="tradetype" class="com-select reg-select" data-err="请经营模式">
											<option value="">请选择</option>
									        <option value="0">个人经营</option>
									        <option value="1">企业经营</option>
										</select>	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>
							<li class="clearfix">
								<div class="inp-tit">主营商品：</div>
								<div class="fl">
									<span class="bor">
										<input type="text" name="tradeProduct" class="input-text input-reg" placeholder="请输入主营产品" data-err="请正确输主营产品" data-po="请输入主营产品">	
									</span>
									<span class="info mt3"></span>
								</div>
							</li>	
							<li class="clearfix">
								<div class="inp-tit">&nbsp;</div>
								<div class="fl">
									<a href="javascript:;" class="btn-red-dia btn-reg">完成注册</a>
								</div>
							</li>
							
						</ul>
					</form>
				</div>
				<div class="reg-result-wrap">
					<!--采购商注册成功-->
					<div class="reg-result-box" style="display:none">
						<h3 class="com-result-t"><span class="result-ico-succ"></span>恭喜您，注册成功！</h3>
						<p>尊敬的“<span class="regName">huaerhuaer</span>” 感谢您注册成为谷登农批网“<span class="regType">农批商</span>”会员！</p>
						<p>您现在可以进入<a href="${CONTEXT}userCenter" class="reg-r-link">会员中心后台</a>，祝您生意兴隆！</p>
					</div>

					<!--农批商注册成功-->
					<div class="reg-result-box" style="display:none">
						<h3 class="com-result-t"><span class="result-ico-succ"></span>恭喜您，注册成功！</h3>
						<p>尊敬的“<span class="regName">huaerhuaer</span>” 感谢您注册成为谷登农批网“<span class="regType">农批商</span>”会员！</p>
						<p>您现在可以<a href="${CONTEXT}userCenter/product/chooseCategory" class="reg-r-link">发布产品</a>或进入<a href="${CONTEXT}userCenter" class="reg-r-link">会员中心后台</a>，祝您生意兴隆！</p>
					</div>

					<!--产地供应商注册成功-->
					<div class="reg-result-box" style="display:none">
						<h3 class="com-result-t"><span class="result-ico-succ"></span>恭喜您，注册成功！</h3>
						<p>尊敬的“<span class="regName">huaerhuaer</span>” 感谢您注册成为谷登农批网“<span class="regType">农批商</span>”会员！</p>
						<p>您现在可以<a href="${CONTEXT}userCenter/product/chooseCategory" class="reg-r-link">发布产品</a>或进入<a href="${CONTEXT}userCenter" class="reg-r-link">会员中心后台</a>，祝您生意兴隆！</p>
					</div>
				</div>
			</div>
			
			<div  class="reg-l-right">
				<p class="reg-l-p"><img src="${CONTEXT}v1.0/images/register-temp.jpg" alt=""></p>
			</div>

		</div>
	</div>	
	
	
	<!--注册成功-->

	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	 <script type="text/javascript" src="${CONTEXT}js/login/register.js"></script> 
</html> 