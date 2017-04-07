<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil,org.apache.commons.lang3.StringUtils" pageEncoding="UTF-8"%>
<%@ include file="constants.inc" %>
<%@ taglib uri="http://www.gdeng.cn/gd" prefix="gd" %>
<!--head star-->
<%
	String marketId = (String)request.getAttribute("marketId");
	if(StringUtils.isEmpty(marketId)){
	    String cookieMarketId=CookieUtil.getValue("marketId");
		marketId=StringUtils.isEmpty(cookieMarketId) ? "1" : cookieMarketId;
	}
	request.setAttribute("marketId", marketId);
%>
<div class="head-wrap">
	<div class="wrap-1170 header clearfix">
		<div class="head-l">
			<div class="sigout-box">
				<span class="mr5">您好，欢迎来到谷登农批网！</span>
				<a href="${CONTEXT}login/initLogin" class="head-link head-btn">登录</a>
				<a style="display:none;" href="${CONTEXT}login/initRegister" class="head-link head-btn">注册</a>
			</div>
			<div class="sigin-box" style="display:none;">
				您好，<a href="${CONTEXT}userCenter" class="sigin-name sigin-mobile" id="loginName"></a><a href="javascript:loginout();" class="ml10">退出</a>
			</div>
		</div>
		<div class="head-r clearfix">
			<a href="${CONTEXT2}${gd:formatMarket(marketId) }.html" class="head-link2">首页</a>
			<span class="head-spilt2"></span>
			<a target="_blank" href="${CONTEXT}notice-list" class="head-link2">公告</a>
			<span class="head-spilt2"></span>			
			<a target="_blank" href="${CONTEXT}nst.shtml" class="head-link2 head-linkico">农速通</a>
			<span class="head-spilt2"></span>			
			<span class="h-app-subm">
				<a target="_blank" href="${CONTEXT}nsy.shtml" class="head-link2 head-linkico">农商友</a>
				<span class="h-app-subc">
					<a target="_blank" href="${CONTEXT}nsy_nps.shtml" class="head-link2 head-linkico">农商友-农批商</a><br/>
					<a target="_blank" href="${CONTEXT}nsy_gys.shtml" class="head-link2 head-linkico" style="position:relative;top:-10px; border-top:none; z-index:99;">农商友-供应商</a>
				</span>
			</span>
			<span class="head-spilt2"></span>
			<a target="_blank" href="${CONTEXT}userCenter" class="head-link2">我的谷登</a>
			<span class="head-spilt2"></span>
			<a target="_blank" href="${CONTEXT}service.html" class="head-link2">客服中心</a>
			<span class="head-spilt2"></span>
			<a target="_blank" href="${CONTEXT}help.html" class="head-link2">帮助中心</a>
			<span class="head-spilt2"></span>
			<div class="head-link2-tp">
				<div class="head-left-pot">
					<a href="javascript:void(0)" class="head-link2 head-link2-a1">网站导航</a>
				</div>	
				<div class="head-block-xz">
					<ul>
						<li><a href="${CONTEXT}logistics.html">物流服务</a></li>
						<!-- <li><a href="${CONTEXT}finance.html">金融服务</a></li> -->
						<div class="clear"></div>
						<li><a href="${CONTEXT}baishazhou/market.html">白沙洲农批市场</a></li>
						<li><a href="${CONTEXT}baishazhou/business.html">白沙洲农批商户</a></li>
						<li><a href="${CONTEXT}baishazhou/test.html">白沙洲农产检测</a></li>
						<li><a href="${CONTEXT}yulin/market.html">玉林农批市场</a></li>
						<li><a href="${CONTEXT}yulin/business.html">玉林农批商户</a></li>
						<li><a href="${CONTEXT}yulin/test.html">玉林农产检测</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){
	$.ajax({
		type:'POST',
		url:CONTEXT+'login/isLogin',
		dataType: 'json',
		success:function(data){
			if(data.mbdto){
				if(data.mbdto.account!=null){
					$(".sigout-box").hide();
					$(".sigin-box").show();
					$("#loginName").html(data.mbdto.mobile);
				}
			}
			
		}
	});
});

function loginout() {
	$.ajax({
		type: 'POST',
		url: CONTEXT+'login/loginOut' ,
	    data: '' ,
	    dataType: 'json' ,
	    success: function(data) {
	    	location.reload();
	    } ,
	    error: function(err) {
	    	alert('系统维护中。。。');
	    }
	});
}
</script>