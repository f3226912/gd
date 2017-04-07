<%@ page language="java" import="java.util.*,com.gudeng.commerce.gd.home.util.CookieUtil" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ include file="constants.inc" %>
<%@ include file="tags.inc" %>
<%
	String marketId = (String)request.getAttribute("marketId");
	if(StringUtils.isEmpty(marketId)){
	    String cookieMarketId=CookieUtil.getValue("marketId");
		marketId=StringUtils.isEmpty(cookieMarketId) ? "1" : cookieMarketId;
	}
	request.setAttribute("marketId", marketId);
%>
<!-- 二级分类 -->
<div class="header_sidenav" style="overflow: visible;">
	<ul class="header_sidenav_list">
		<c:forEach items="${pclist}" var="category" varStatus="s">
			<li class="header_sidenav_item">
				<div class="header_sidenav_item_info">
					<strong class="side-nav-t-lev1"><span class="side-nav-ico"><img src="${imgHostUrl}${category.webTypeIcon}" width="25" height="25" /></span>${category.cateName }</strong>
					<c:forEach items="${category.childList}" var="category2" varStatus="s" begin="0" end="2">
						<span class="side-nav-txt"><a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${category2.categoryId}.html" title="${category2.cateName}">${category2.cateName }</a></span>
					</c:forEach>
					<p class="header_sidenav_item_other">
						<c:forEach items="${category.childList3}" var="childList3" varStatus="s" begin="0" end="2">
							<c:if test="${s.count != 1 }">
								<span class="header-sidenav-spilt">|</span>
							</c:if>
							<a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${childList3.categoryId}.html" title="${childList3.cateName}">${childList3.cateName}</a>
						</c:forEach>
					</p>
				</div>
	            <div class="header_sidenav_cnt clearfix">
	            	<c:forEach items="${category.childList}" var="category2" varStatus="s" begin="0" end="4">
						<div class="header-sidenav-list clearfix">
		            		<h3 class="side-nav-t-lev3"><a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${category2.categoryId}.html" title="${category2.cateName}">${category2.cateName }</a></h3>
		            		<div class="header-sidenav-link">
		            			<c:forEach items="${category2.childList}" var="category3" varStatus="s" begin="0" end="9">
									<c:if test="${s.count != 1 }">
										<span class="header-sidenav-spilt">|</span>
									</c:if>
									<a href="${CONTEXT}${gd:formatMarket(marketId) }/market/${category3.categoryId}.html" title="${category3.cateName}">${category3.cateName}</a>
								</c:forEach>
							</div>
		            	</div>
					</c:forEach>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>
<!-- END: 不一样的保险 -->