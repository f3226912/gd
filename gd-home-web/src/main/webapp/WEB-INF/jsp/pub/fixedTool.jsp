<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ include file="constants.inc" %>
<%@ include file="tags.inc" %>
<style>
  .b-mark-m li{ background:none; height:auto; width:90px;}
</style>
<div class="page-fixed">
	<ul class="r-fixed-tool">
		<li><a href="${CONTEXT}service.html" target="_blank"><span class="rft-nav-t"><span class="rft-ico rft-ico-ser"></span>客服</span></a></li>
		<li class="rft-qrbtn"><a href="javascript:;"><span class="rft-nav-t"><span class="rft-ico rft-ico-qr"></span>二维码</span></a></li>
	</ul>
	<ul class="i-page-fixed-list">
		<c:forEach items="${pclist}" var="category" varStatus="s">
			<li class=""><a href="javascript:;"><span class="side-nav-t-lev1"><span class="side-nav-ico"><img src="${imgHostUrl}${category.webTypeIcon}"  width="25" height="25" /></span>${category.cateName}</span></a></li>
		</c:forEach>
	</ul>
</div>
	
<!--bottom fixed-->
<div class="bottom-mark">
   	<div class="b-mark-con clearfix">
       	<div class="b-mark-l">
       		<img src="${CONTEXT }v1.0/images/b-fixed-pic2.png" width="564" height="111" alt="进货怕吃亏？快用农商友！-安装注册后，海量货源，多类产品，透明价格，一手掌握！">
       	</div>
       	<ul class="b-mark-m clearfix">
       		<!-- <li class="clearfix"><span class="b-m-qrtxt"><span>农商友</span></span><span class="b-m-qrcode"><img src="http://www.gdeng.cn/v1.0/images/shop-images/sao.png" width="99" height="99" alt="农商友"></span></li>
       		<li class="clearfix"><span class="b-m-qrtxt"><span class="fsz-nps">农商友<span class="fsz12">|</span>农批商</span></span><span class="b-m-qrcode"><img src="http://www.gdeng.cn/v1.0/images/shop-images/sao-sell.png" width="99" height="99" alt="农商友-农批商"></span></li>
       		<li class="clearfix"><span class="b-m-qrtxt"><span>农速通</span></span><span class="b-m-qrcode"><img src="http://www.gdeng.cn/v1.0/images/shop-images/cap.png" width="99" height="99" alt="农速通"></span></li>
       		<li class="clearfix"><span class="b-m-qrtxt"><span>农速通</span></span><span class="b-m-qrcode"><img src="http://www.gdeng.cn/v1.0/images/shop-images/cap.png" width="99" height="99" alt="农速通"></span></li>
       		<li class="clearfix"><span class="b-m-qrtxt"><span>农商友</span></span><span class="b-m-qrcode"><img src="http://www.gdeng.cn/v1.0/images/shop-images/sao.png" width="99" height="99" alt="农商友"></span></li> -->
       		<li class="clearfix"><img src="${CONTEXT }v1.0/images/shop-images/1.png" width="82" height="104" alt="农商友"></li>
       		<li class="clearfix"><img src="${CONTEXT }v1.0/images/shop-images/2.png" width="90" height="106" alt="农商友-农批商"></li>
       		<li class="clearfix"><img src="${CONTEXT }v1.0/images/shop-images/3.png" width="90" height="104" alt="农商友-供应商"></li>
       		<li class="clearfix"><img src="${CONTEXT }v1.0/images/shop-images/4.png" width="82" height="104" alt="农速通"></li>
       	</ul>
       	<a href="javascript:;" class="close-f"></a>
   	</div>
  		
</div>
<!--bottom fixed-->

