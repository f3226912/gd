<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="Content-Type" content="text/html; charset=UTF-8">
<title>${busiInfo.shopsName }-${categorys }供应商_谷登农批网</title>
<meta name="keywords" content="${busiInfo.shopsName },${busiInfo.mainProduct },农产品批发,农产品供应">
<meta name="description" content="谷登农批网批发商${busiInfo.shopsName }，以${busiInfo.businessModelStr}模式主营：${productLine }等产品，更多详情欢迎联系洽谈！谷登农批网第一的农产品交易O2O平台。">
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/head.inc"%>
<%@ include file="../pub/tags.inc"%>
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
				<div class="con-right">
				<div class="right-m">
					<p class="right-m-t">
						商铺介绍<span class="fr more"><a href="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid}/intro.html">查看更多信息&gt;&gt;</a></span>
					</p>
					<p class="right-m-p">${busiInfo.shopsDesc }</p>
				</div>
			</div>
				<div class="con-right">
					<div class="right-m">
						<p class="right-m-t">最新产品<span class="fr more"><a href="${CONTEXT }${gd:formatMarket(marketId) }/business/shop/${bid}/supply.html">更多&gt;&gt;</a></span></p>
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
				
				<!-- contact us -->
				<jsp:include page="/WEB-INF/jsp/pub/store/contactus.jsp"></jsp:include>
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