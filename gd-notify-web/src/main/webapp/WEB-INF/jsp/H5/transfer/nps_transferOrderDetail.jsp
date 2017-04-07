<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
	<meta charset="utf-8">
	<title>货物详情</title>
	<meta name="description" content="">
	<meta http-equiv="X-UA-Compatible" content="chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
	<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
	<meta name="apple-mobile-web-app-title" content="商品" />
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link href="${CONTEXT }v1.0/module/mui/examples/hello-mui/css/mui.min.css" rel="stylesheet">
	<link href="${CONTEXT }v1.0/css/global.m.css" type="text/css" rel="stylesheet">
	<link href="${CONTEXT }v1.0/css/logistics-product.css" type="text/css" rel="stylesheet">

</head>
<body>
<script type="text/javascript" src="${CONTEXT }v1.0/js/zepto.min.js"></script>
<script type="text/javascript" src="${CONTEXT }v1.0/module/mui/dist/js/mui.min.js"></script>
		<div class="refreshcon">
			<c:forEach items="${orderList }" var="order">
			<section class="wrap-item">
				<div class="p-item-tit">
					<div class="com-pad clearfix">
						<span class="date-r"><fmt:formatDate value="${order.createTime }" pattern="yyyy-MM-dd"/></span>
						<span>订单编号：${order.orderNo }</span>
					</div>
				</div>
				<c:forEach items="${order.productList }" var="product">
				<div class="p-item-det">
					<div class="com-pad clearfix">
						<div class="p-pic-box">
							<img src="${imgHostUrl }${product.imageUrl }" alt="" />
						</div>
						<div class="p-pic-txt">
							<h2 class="p-pic-tit">${product.productName }</h2>
							<p class="p-det-other">采购量：${product.purQuantity }${product.unit }</p>
							<p class="p-det-other clearfix"><span class="fr">小计：${product.subTotal }元</span>单价：${product.price}元/${product.unit }</p>
						</div>
					</div>		
				</div>
				</c:forEach>
				<div class="com-pad total-p">
						共${fn:length(order.productList)}件商品
				</div>
			</section>
			</c:forEach>
		</div>

<%-- <script src="${CONTEXT }v1.0/js/zepto.min.js"></script>
<script src="${CONTEXT }v1.0/js/touch.js"></script>
<script src="${CONTEXT }v1.0/js/gdui.m.js"></script> --%>
<script>
function messageGoBack(){
	if("${nobId}"=="") {
		mui.openWindow({
			url:CONTEXT+'transfer/${pa}/${memberAddressId}/${fromCode}?userId=${memberId}&orderNo=${orderNo}&c=${c}'
	  	});
	} else {
		mui.openWindow({
			url:CONTEXT+'transfer/${pa}/${nobId}/${fromCode}?userId=${memberId}&orderNo=${orderNo}&c=${c}'
	  	});
	}
}

</script>
</body>
</html>
