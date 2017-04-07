<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="Content-Type" content="text/html; charset=UTF-8">
<title>${busiInfo.shopsName }热门批发供应${categorys }_谷登农批网</title>
<meta name="keywords" content="${busiInfo.mainProduct },${busiInfo.shopsName }供应产品,${busiInfo.shopsName }最热产品">
<meta name="description" content="${busiInfo.shopsName }网上批发${busiInfo.mainProduct }，提供最热产品${busiInfo.mainProduct }的供应信息及最新报价！谷登农批网第一的农产品交易O2O平台。">
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/head.inc"%>
<%@ include file="../pub/tags.inc"%>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/js/skin/gduiPage.css">
<script type="text/javascript" src="${CONTEXT}js/page.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
<div class="main clearfix">
<jsp:include page="/WEB-INF/jsp/pub/store/storenavigate.jsp"></jsp:include>
<div class="content">
<jsp:include page="/WEB-INF/jsp/pub/store/storeLeft.jsp"></jsp:include>
<div class="fl">
<div class="con-right" style="display:none">
<div class="right-m">
<p class="right-m-t pro-ico01">供应产品</p>
<p class="product-tit">全部（<span class="pro-yellow"><a href="">58</a></span>）<span class="pro-space"></span>最新（<span class="pro-yellow"><a href="">23</a></span>）<span class="pro-space"></span>最热（<span class="pro-yellow"><a href="">24</a></span>）<span class="pro-space"></span></p>
</div>
</div>
<div class="con-right">
<div class="right-m">
<p class="right-m-t pro-new"><span class="pro-seach-tit">价格：</span>
		<% 	
	String order = request.getParameter("order");
	String sort = request.getParameter("sort");
	if (!"price".equals(sort)) {
		%>
		<span onclick="changeOrder('price','d')" class="pric-mag price-ico-bj3"></span>
		<%
	} else if("price".equals(sort)&&"a".equals(order)) {
		%>
		<span onclick="changeOrder('price','o')" class="pric-mag price-ico-bj2"></span>
		<%
	} else {
		%>
		<span onclick="changeOrder('price','o')" class="pric-mag price-ico-bj1"></span>
		<%
	}
%>
<!-- 						<span class="pro-seach-tit">发布时间：</span><a href="javascript:changeOrder('createTime','d')">从新到旧</a><span class="pro-seach-tit">&nbsp;&nbsp;|&nbsp;&nbsp;</span><a href="javascript:changeOrder('createTime','a')">从旧到新</a></p> -->
<span class="pro-seach-tit">发布时间：</span><a href="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid}/supply/1.html?sort=createTime&order=d">从新到旧</a>
<span class="pro-seach-tit">&nbsp;&nbsp;|&nbsp;&nbsp;</span>
<a href="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid}/supply/1.html?sort=createTime&order=a">从旧到新</a></p>
<div class="product">
<c:if test="${page==null || empty page.pageData }">
<p style="margin: 50px 0; text-align: center; font-size: 14px;">商铺主人还没有上传批发产品哦！</p>
</c:if>
<c:if test="${page!=null && not empty page.pageData}">
<input type="hidden" id="page" name="page" value="${page.currentPage}">
<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
<input type="hidden" id="pageTotal" name="pageTotal" value="${page.pageTotal}">
<form id="pageForm" action="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid}/supply/1.html" method="get">
<input type="hidden" id="sort" name="sort" value="${param.sort}">
<input type="hidden" id="order" name="order" value="${param.order}">
</form>
<ul class="product-list clearfix">
<!-- 分页页面模板 start -->
<c:forEach items="${page.pageData}" var="product" varStatus="s">
<li>	
<p><a href="${CONTEXT }market/${product.productId}.html" target="_blank"><img width="200px" height="200px" src="${imgHostUrl}${product.imageUrl }"  onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';"></a></p>
<p><span class="price">￥
${gd:formatNumber(product.price) }
</span><span class="how">元/${gd:showValueByCode('ProductUnit',product.unit) }</span></p>
<p class="key-tit"><a href="${CONTEXT }market/${product.productId}.html" target="_blank">${product.productName }</a></p>
</li>
</c:forEach>
</ul>
<!-- 分页 页码标签 根据具体UI修改 start -->
<jsp:include page="/WEB-INF/jsp/pub/PageNumTemplate.jsp"></jsp:include>
<div id="searchPage" class="fenyetest" style="text-align: center;margin-bottom: 20px;">
<c:set var="currPage" value="${page.currentPage }"></c:set>
<c:set var="totalPage" value="${page.pageTotal }"></c:set>
<%@ include file="../pub/pagination/storeProductPage.jsp" %>
</div>
<!-- 分页 页码标签 根据具体UI修改 end -->
</c:if>
</div>
</div>
</div>
</div>
<div class="cl"></div>
</div>
</div>
<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
<!-- fixed tool -->
<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>