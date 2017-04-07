<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil" pageEncoding="UTF-8"%>
<%@ include file="constants.inc" %>
<%@ taglib uri="http://www.gdeng.cn/gd" prefix="gd" %>
<%-- <% --%>
<!-- // 	String marketId = CookieUtil.getValue("marketId"); -->
<!-- // 	marketId=StringUtils.isEmpty(marketId) ? "1" : marketId; -->
<!-- // 	request.setAttribute("marketId", marketId); -->
<%-- %> --%>
<!-- 该div节点有些页面不同，因此不共用 -->
<!-- <div class="main_menu"> -->
	<ul class="main_menu_list c">
		<li class="main_menu_item">
			<a id="menuIndex" href="${CONTEXT}${gd:formatMarket(marketId) }.html" class="main_menu_item_link" title="首页">首页</a>
		</li>
		<li class="main_menu_item">
			<a id="menuProduct" href="${CONTEXT}${gd:formatMarket(marketId) }/market.html" class="main_menu_item_link " title="农批市场">农批市场</a>	
		</li>	
		<li class="main_menu_item">
			<a id="menuBusiness" href="${CONTEXT}${gd:formatMarket(marketId) }/business.html" class="main_menu_item_link " title="农批商户">农批商户</a>	
		</li>	
		<!-- <li class="main_menu_item">
			<a id="menuMarketPrice" href="${CONTEXT}price/index" class="main_menu_item_link " title="农市行情">农市行情</a>	
		</li> -->
		<li class="main_menu_item" style="position:relative;">
			<a id="menuteseguan" href="${CONTEXT}zhuanti/goldMedal/" class="main_menu_item_link " title="金牌供应商">金牌供应商</a>
			<span style="position:absolute; top:5px; right:0px; padding:3px 5px; background:red; color:#fff; font-size:8px; -webkit-transform:scale(0.6); border-radius: 3px;">HOT</span>
			<span style="position:absolute; top:12px; right:26px; width: 0px; height: 0px; border-bottom: 3px solid transparent; border-top: 3px solid transparent; border-right: 4px solid red; font-size: 0px;line-height: 0px;"></span>	
		</li>
		<li class="main_menu_item" style="position:relative;">
			<a id="menuteseguan" href="${CONTEXT}zhuanti/teseguan/" class="main_menu_item_link " title="地方特色馆">地方特色馆</a>
			<span style="position:absolute; top:5px; right:0px; padding:3px 5px; background:red; color:#fff; font-size:8px; -webkit-transform:scale(0.6); border-radius: 3px;">HOT</span>
			<span style="position:absolute; top:12px; right:26px; width: 0px; height: 0px; border-bottom: 3px solid transparent; border-top: 3px solid transparent; border-right: 4px solid red; font-size: 0px;line-height: 0px;"></span>	
		</li>		
		<li class="main_menu_item">
			<a id="menuTransportation" href="${CONTEXT}logistics.html" class="main_menu_item_link " title="物流服务">物流服务</a>	
		</li>
		<!-- <li class="main_menu_item">
			<a id="aboutus_financial" href="${CONTEXT}finance.html" class="main_menu_item_link " title="金融服务">金融服务</a>	
		</li> -->
		<li class="main_menu_item">
			<a id="menuDetect" href="${CONTEXT}test.html" class="main_menu_item_link " title="食品安全">食品安全</a>	
		</li>
	</ul>
<script>
$(function(){
	var url=window.location.href;
	$(".main_menu_item_link").each(function(){
		if(url.indexOf("finance")!=-1){
			$("#aboutus_financial").css("color","#068139");
			$("#aboutus_financial").css("font-weight","600");
			return false;
		}else if(url.indexOf("marketlink")!=-1){
			$("#menuIndex").css("color","#068139");
			$("#menuIndex").css("font-weight","600");
			return false;
		}else if(url.indexOf("market")!=-1 || url.indexOf("product")!=-1){
			$("#menuProduct").css("color","#068139");
			$("#menuProduct").css("font-weight","600");
			return false;
		}else if(url.indexOf("business")!=-1){
			$("#menuBusiness").css("color","#068139");
			$("#menuBusiness").css("font-weight","600");
			return false;
		}else if(url.indexOf("price")!=-1){
			$("#menuMarketPrice").css("color","#068139");
			$("#menuMarketPrice").css("font-weight","600");
			return false;
		}else if(url.indexOf("test")!=-1){
			$("#menuDetect").css("color","#068139");
			$("#menuDetect").css("font-weight","600");
			return false;
		}else if(url.indexOf("logistics")!=-1){
			$("#menuTransportation").css("color","#068139");
			$("#menuTransportation").css("font-weight","600");
			return false;
		}else if(url.indexOf("teseguan")!=-1){
			$("#menuteseguan").css("color","#068139");
			$("#menuteseguan").css("font-weight","600");
			return false;
		}else{
			$("#menuIndex").css("color","#068139");
			$("#menuIndex").css("font-weight","600");
			return false;
		}
	});
});
</script>
<!-- </div> -->