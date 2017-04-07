<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/tags.inc"%>
<!DOCTYPE html>
<html>
<head>
<base href="${CONTEXT }">
<title>今日农产品价格预测_农产品报价_农产品市场行情_(第${currPage }页)_谷登农批网</title>
<%@ include file="../pub/head.inc"%>
<meta charset="utf-8">
<meta name="pragma" content="no-cache" />
<meta name="cache-control" content="no-cache" />
<meta name="expires" content="0" />
<meta name="keywords"
	content="今日农产品价格,农产品价格走势,最新农产品价格,今日农产品价格预测,农产品报价,农产品市场价格,农产品市场行情" />
<meta name="description"
	content="谷登农批网行情频道提供最新农产品市场行情信息，今日最新农产品价格走势及今日农产品价格预测,包括:海鲜水产、蔬菜、干调、副食、粮油、肉类、禽蛋、水果、冻品等最新价格信息,及时掌握农产品报价就在谷登农批网行情频道。(第${currPage }页)" />
<meta name="renderer" content="webkit">
<meta name="X-UA-Compatible"
	content="IE=7, IE=9, IE=10, IE=11, IE=12" />
<link rel="stylesheet" href="v1.0/css/global.css">
<link rel="stylesheet" href="v1.0/css/index-shop.css">
</head>
<body>
	<!--head star-->

	<!--head star-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>

	<!--nav start-->
	<div class="nav">
		<div class="nav_inside clearfix">
			<!-- 主导航栏 -->
			<div class="main_menu check-nav">
				<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<!--head end-->
	<!--slide start-->

<!-- 主体 -->
<div class="main clearfix">
	<div class="content mgtop">
		<div class="con-left fl">
			<div class="con-left-tit check-img check-mark">
				<p>农市行情</p>
				<img src="v1.0/images/shop-images/quotations_03.jpg" alt="">
				<p>最新农市行情尽在掌握</p>
			</div>
		</div>
		<div class="fl">
			<div class="con-right">
				<p class="right-m-t check-tit">
					<span class="quotations-ico01" style="margin-right: 5px;"></span>所有行情<span></span>
				</p>
				<c:if test="${!empty list }">
				<div class="check-table">
					<table width="900" border="1" cellspacing="0" cellpadding="0">
							<tr class="check-table-tit">
								<td>品名</td>
								<td>采集时间</td>
								<td>采集点</td>
								<td>高价<span class="quotations-tit">（元/公斤）</span></td>
								<td>低价<span class="quotations-tit">（元/公斤））</span></td>
								<td>平均价<span class="quotations-tit">（元/公斤））</span></td>
								<td>涨跌</td>
							</tr>
							<c:forEach items="${list}" var="obj">
								<tr>
									<td title="${obj.productName}">${obj.productName}</td>
									<td><fmt:formatDate value="${obj.date}" type="date"
											pattern="yyyy/MM/dd" /></td>
									<td title="${obj.locale}">${obj.locale}</td>
									<td>${obj.maxPrice}</td>
									<td>${obj.minPrice}</td>
									<td>${obj.averagePrice}</td>
									<td>
									<fmt:formatNumber type="number" value="${obj.averagePrice}" maxFractionDigits="0" var="flag"/>
									<c:choose>
										<c:when test="${flag%2==0 }">
											<span class="quotations-price-up"></span>
										</c:when>
										<c:otherwise>
											<span class="quotations-price-down"></span>
										</c:otherwise>
									</c:choose>
									</td>
								</tr>
							</c:forEach>
					</table>
					<div id="mar-com-fenye" class="fenye"></div>
				</div>
				</c:if>
				<!-- 无结果页面开始 -->
				<c:if test="${empty list}">
		    	<div class="main clearfix" style="float: right;width: 940px;height: 400px;">
					<div class="content mgtop">
						<div class="fl" style="width: 100%;height: 100%;">
							<div class="con-right products-con logi-main" style="width: 100%;margin-left: 0px;border: none;">
								<div style="width: 100%;text-align: center;margin: 0 auto;">
								<p class="logi-tips"><span class="nosearch-ico"></span>抱歉，没有找到符合<span class="logi-tips-span"></span>的农市行情结果。</p>
									<div style="width: 100%;text-align: left;padding-left: 28%;margin-bottom: 80px;margin-top: -30px;">
									</div>
								</div>
							</div>
					    </div>
						<div class="cl"></div>
					</div>
				</div>
				</c:if>
				<!-- 无结果页面结束 -->
				
				
			</div>
		</div>
		<div class="cl"></div>
	</div>
</div>


<!--底部 star-->
<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
<!--底部 end-->
<!-- fixed tool -->
<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>
	
<form id="formSearch-marketPrice" action="${CONTEXT }price/index" method="post">
	<input type="hidden" id="totalPage" name="totalPage" value="${totalPage}">
	<input type="hidden" id="page" name="page" value="${page }">
</form>
<script src="v1.0/js/jquery-1.8.3.min.js"></script>
<script src="v1.0/js/gdui.js"></script>
<script src="v1.0/js/common.js"></script>
<script>
	var totalPage = ${totalPage};
	var page = ${page};
	
	gduiPage({
		cont : 'mar-com-fenye',
		pages : totalPage, //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
		curr : page,
		jump : function(e, first) { //触发分页后的回调
			if (!first) { //一定要加此判断，否则初始时会无限刷新
				$("#page").val(e.curr);
				//alert($("#page").val());
				$("#formSearch-marketPrice").submit();
			}
		},
		staticPage : true
	});
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>