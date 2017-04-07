<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<title>${busiInfo.shopsName }${product.productName }价格、库存等供应详情 -谷登农批网</title>
<base href="${CONTEXT }">
<meta charset="utf-8">
<meta name="pragma" content="no-cache"/>
<meta name="cache-control" content="no-cache"/>
<meta name="expires" content="0"/>
<meta name="keywords" content="${product.productName },${product.productName }价格,${busiInfo.shopsName },${product.productName }库存"/>
<meta name="description" content="谷登农批网商家${busiInfo.shopsName }${product.productName }供应，可查询${busiInfo.shopsName }${product.productName }信息。包括${busiInfo.shopsName }的联系方式、${product.productName }库存、价格、库存、订货量、等详情—谷登农批网"/>
<%@ include file="../../pub/head.inc" %>
<link rel="stylesheet" href="v1.0/css/index-shop.css">
</head>
<style>
#con-left-tit-3{
/* 	display: block; */
}
.detail-price{
	width: 150px;
}
.mark{
    margin-right: 20px; 
}
.products-con img{ 
	margin: 10px 40px;
	width: 91%;
}
</style>
<body>
	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
	<!-- 主体 -->
	<div class="main clearfix">
		<!-- 导航栏 -->
		<jsp:include page="/WEB-INF/jsp/pub/store/storenavigate.jsp"></jsp:include>
		<div class="content mgtop">
			<!-- 左边主要框架 显示商铺信息 -->
			<jsp:include page="/WEB-INF/jsp/pub/store/storeLeft.jsp"></jsp:include>
			<div class="fl">
				<div class="con-right pro-detail">
					<div class="detail-top clearfix">
						<div class="det-top-left fl">
							<!-- 商品图片 -->
							<!-- 大图,主图 -->
							<div class="detail-pic">
								<c:forEach items="${pictureList }" var="pic" varStatus="i">
									<c:if test="${pic.pictureType eq '1' }">
										<img id="mainImg" width="370px" height="370px" src="${imgHostUrl}${gd:showImgBySize(pic.urlOrg,370)}" alt="${product.productName }" onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';" title="${product.productName }">
									</c:if>
								</c:forEach>
							</div>
							<!-- 多角度图 -->
							<div class="detail-msg clearfix">
								<c:forEach items="${pictureList }" var="pic" varStatus="i">
									<c:if test="${pic.pictureType eq '2' }">
										<c:if test="${i.index%4==0}">
										<div class="detail-pic2 fl mgl-10">
										</c:if>
										<c:if test="${i.index%4!=0}">
										<div class="detail-pic2 fl">
										</c:if>
											<img width="85px" height="85px" src="${imgHostUrl}${gd:showImgBySize(pic.urlOrg,150)}" alt="${product.productName }" title="${product.productName }" onclick="javascript:changeImg('${imgHostUrl}${gd:showImgBySize(pic.urlOrg,370)}')" onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';">
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
						<div class="det-top-right fl clearfix">
							<!-- 商品名称 -->
							<h3 class="detail-pro-tit" title="${product.productName }">${product.productName }</h3>
							<ul class="detail-pro-how">
								<!-- 商品价格区间 -->
								<li><span class="detail-price">订货量（${gd:showValueByCode('ProductUnit',product.unit)}）</span>价格（不含税）</li>
								<!-- 单价 -->
								<c:if test="${product.priceType eq '0' }">
								 	<li>
								   	<span class="detail-price">≥1</span>
							   		<span class="detail-col">
							   		${gd:formatNumber(product.price) }
							   		</span>
							   		元/${gd:showValueByCode('ProductUnit',product.unit) } 
							   		</li> 
								</c:if>
								<!-- 多价 -->
								<c:forEach var="priceDto" items="${product.priceDtoList}">  
							   		<li>
							   		<c:if test="${empty priceDto.buyCountEnd || priceDto.buyCountEnd==0}">
								   		<span class="detail-price">≥${gd:formatNumber2(priceDto.buyCountStart) } </span>
							   		</c:if>
							   		<c:if test="${!empty priceDto.buyCountEnd }">
								   		<span class="detail-price">${gd:formatNumber2(priceDto.buyCountStart) }-${gd:formatNumber2(priceDto.buyCountEnd) }</span>
							   		</c:if>
							   		<span class="detail-col">
							   		${gd:formatNumber(priceDto.price) }
							   		</span>
							   		元/${gd:showValueByCode('ProductUnit',product.unit) } 
							   		</li>
								</c:forEach>
								<c:set var="timeNow" value="<%=new Date()%>"></c:set>
								<c:if test="${product.state eq '4' || product.state eq '1' || product.state eq '2' || product.state eq '6'}">
									<li class="detail-price-none">此商品已下架</li>
								</c:if>
								<c:if test="${product.state eq '5' }">
									<li class="detail-price-none">此商品已删除</li>
								</c:if>
							</ul>
							<div class="detail-pro-mark">
								<ul class="detail-pro-how mach-w">
									<li>库存：
									<c:choose>
										<c:when test="${product.stockCount>=1000 }">
											<span class="detail-col mach-span">大量</span>
										</c:when>
										<c:otherwise>
										<span class="detail-col mach-span">
										${gd:formatNumber2(product.stockCount) }
										<c:if test="${empty product.stockCount }">0</c:if></span>
										${gd:showValueByCode('ProductUnit',product.unit) }
										</c:otherwise>
									</c:choose>
									</li>
									<li>累计查看：<span class="detail-col mach-span">${product.visitCount }<c:if test="${empty product.visitCount }">0</c:if></span>次 </li> 
								</ul>
								<c:choose>  
									<c:when test="${isFocus }">
										<p class="mark js-uncollectProduct" style="background-color: #666;">
											<span class="reg-mark"></span>取消关注产品
										</p>
										<p class="mark js-collectProduct" style="display:none;">
											<span class="reg-mark"></span>关注产品
										</p>
									</c:when>
									<c:otherwise>
										<p class="mark js-uncollectProduct" style="background-color: #666; display:none;">
											<span class="reg-mark"></span>取消关注产品
										</p>
										<p class="mark js-collectProduct">
											<span class="reg-mark"></span>关注产品
										</p>
									</c:otherwise>  
								</c:choose>
								<script type="text/javascript">
									function focusFuncProduct() {
										$.ajax({
											type: 'POST',
											url: '${CONTEXT }userCenter/focus/'+ '${productId }' ,
										    cache: 'false' ,
										    dataType: 'json' ,
										    success: function(data) {
										    	if(data.statusCode==0) {
										    		layer.open({
												        type: 1,
												        title: false, 
												        skin: 'layui-layer-rim',
												        content: $(".col-result"), //捕获的元素
												        area:["472px","166px"],
												        cancel: function(index){
												            layer.close(index);
												        }
												    });
									
										    		$(".js-collectProduct").hide();
										    		$(".js-uncollectProduct").show();
										    	} else {
										    		alert(data.msg);
										    	}
										    } ,
										    error: function(err) {
										    	alert('系统维护中。。。');
										    }
										});
									}
									
									function blurFuncProduct() {
										$.ajax({
											type: 'POST',
											url: '${CONTEXT }userCenter/blur/'+ '${productId }' ,
										    cache: 'false' ,
										    dataType: 'json' ,
										    success: function(data) {
										    	if(data.statusCode==0) {
										    		$(".js-collectProduct").show();
										    		$(".js-uncollectProduct").hide();
										    	} else {
										    		alert(data.msg);
										    	}
										    } ,
										    error: function(err) {
										    	alert('系统维护中。。。');
										    }
										});
									}
									
									$(document).ready(function() {
										$(".js-collectProduct").click(function(){
											Login.checkLogin(focusFuncProduct);
										});
										
										$(".js-uncollectProduct").click(function(){
											Login.checkLogin(blurFuncProduct);
										});
									});
									</script>
							</div>
						</div>
					</div>
				</div>
				<!-- 商品介绍 -->
				<h4 class="products-h4"><span class="products-h4-span">产品介绍</span></h4>
				<div class="con-right products-con">
					<p>${product.description }</p>
				</div>
				<!-- 商铺最新产品 -->
				<div class="con-right">
					<div class="right-m">
						<p class="right-m-t">商铺最新产品<span class="fr more"><a href="${CONTEXT }business/shop/${bid}/supply">更多>></a></span></p>
						<div class="product">
							<ul class="product-list clearfix">
								<c:forEach items="${productList}" var="product" varStatus="s">
									<li>
										<p>
										<a href="${CONTEXT}market/${product.productId}.html" title="${product.productName }">
										<c:if test="${!empty product.url400}">
										<img width="200px" height="200px" src="${imgHostUrl}${product.imageUrl}" alt="${product.productName }" onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';">
										</c:if>
										<c:if test="${empty product.url400}">
										<img width="200px" height="200px" src="${imgHostUrl}${product.imageUrl}" alt="${product.productName }" onerror="javascript:this.src='${CONTEXT}images/nopic.jpg';">
										</c:if>
										</a>
										</p>
										<p><span class="price">￥${gd:formatNumber(product.price) } </span><span class="how">/${gd:showValueByCode('ProductUnit',product.unit) }</span></p>
										<p class="key-tit"><a href="${CONTEXT}market/${product.productId}.html" title="${product.productName }">${product.productName }</a></p>
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
<!--底部 star-->
<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>

<!-- fixed tool -->
<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>

<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<script>
$(function(){
	
// 	$("#leftTit-3").show();
	$("#nav-list-2").attr("class", "fl list bgc");
});
	function changeImg(img){
		$("#mainImg").attr("src",img);
	}
</script>
</html>