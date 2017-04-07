<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/tags.inc"%> 
<!DOCTYPE html>
<html>
<head>
<meta name="Content-Type" content="text/html; charset=UTF-8">
<title>${busiInfo.shopsName }商铺介绍_谷登农批网</title>
<meta name="keywords" content="${busiInfo.shopsName }介绍,关于${busiInfo.shopsName }">
<meta name="description" content="谷登农批网批发商${fn:substring(busiInfo.shopsDesc, 0, 190) }…">
<%@ include file="../pub/head.inc"%>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css">
</head>
<body>
	<!--head page-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	<!-- 主体 -->
	<div class="main clearfix">
		<!-- Navigate -->
		<jsp:include page="/WEB-INF/jsp/pub/store/storenavigate.jsp"></jsp:include>
		<div class="content">
			<!-- 右边主要框架 显示商铺信息 -->
			<jsp:include page="/WEB-INF/jsp/pub/store/storeLeft.jsp"></jsp:include>
			<div class="fl">
				<!-- desc store -->
				<jsp:include page="/WEB-INF/jsp/pub/store/storedesc.jsp"></jsp:include>
				<!-- contact us -->
				<jsp:include page="/WEB-INF/jsp/pub/store/contactus.jsp"></jsp:include>
				<div class="con-right">
					<div class="right-m">
						<p class="right-m-t">最新批发推荐<span class="fr more"><a href="${CONTEXT }business/shop/${bid}/supply">更多&gt;&gt;</a></span></p>
						<div class="product">
							<c:if test="${empty productList }">
								<p style="margin: 50px 0; text-align: center; font-size: 14px;">商铺主人还没有上传批发产品哦！</p>
							</c:if>
							<ul class="product-list clearfix">
							<c:forEach items="${productList}" var="product" varStatus="s"> 
								<li>
									<p><a href="${CONTEXT }market/${product.productId}.html" target="_blank"><img width="200px" height="200px" src="${imgHostUrl}${product.imageUrl }"  onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';"></a></p>
									<p><span class="price">￥
											${gd:formatNumber(product.price) }
										</span><span class="how">元/${gd:showValueByCode('ProductUnit',product.unit) }</span></p>
									<p class="key-tit"><a href="${CONTEXT }market/${product.productId}.html" target="_blank">${product.productName }</a></p>
								</li>
							</c:forEach>
							</ul>
						</div>
					</div>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>
	<!-- foot page -->
	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	<!-- fixed tool -->
	<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>

<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>