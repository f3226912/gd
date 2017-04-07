<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="Content-Type" content="text/html; charset=UTF-8">
<title>农产品农药残留及质量安全检测信息_农产品检测-谷登农批网</title>
<meta name="keywords" content="农产品检测,农药残留检测,农产品质量检测,农产品检测项目">
<meta name="description" content="谷登农批网农产品检测频道及时展示各种农产品质量检测的详细信息，包括农产品检测项目农药残留检测、被检测单位、检测地点、检测品名、检测日期、检测结果等；谷登农批网为您在农产品交易中提供安全保障。">
<%@ include file="../pub/constants.inc"%>
<%@ include file="../pub/head.inc"%>
<%@ include file="../pub/tags.inc"%>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css">
<link rel="stylesheet" href="${CONTEXT}v1.0/js/skin/gduiPage.css">
<%-- <script type="text/javascript" src="${CONTEXT}js/page.js"></script> --%>
</head>
<body>
<!--head star-->
<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>
<!--nav start-->
<div class="nav">
<div class="nav_inside clearfix">
<div class="main_menu check-nav">
<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
</div>
</div>
</div>
<div class="main clearfix">
	<div class="content mgtop">
		<div class="con-left fl">
			<div class="con-left-tit check-img">
				<p>食品安全</p>
				<img src="${CONTEXT }v1.0/images/shop-images/check01_03.jpg" alt="">
				<p>专业检测  权威认证</p>
			</div>
		</div>
		<div class="fl">
			<div class="logistics-tit clearfix">
				<h4 class="products-h4 fl logi-tit-1 anti-fake-tit anti-fake-tit-bg"><span class="products-h4-span">农产检测</span></h4>
				<h4 class="products-h4 fl logi-tit-2 anti-fake-tit"><span class="products-h4-span">防伪溯源</span></h4>
			</div>
			
			<div class="con-right anti-tit-1">
				<p class="right-m-t check-tit"><span class="check-ico01" style="margin-right: 5px;"></span>所有检测结果<span></span></p>
				
				<c:if test="${page!=null && not empty page.pageData}">
				<div class="check-table">
				<form id="pageForm" action="" method="post">
					<input type="hidden" id="page" name="page" value="${page.currentPage}"> 
					<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}"> 
					<input type="hidden" id="pageTotal" name="pageTotal" value="${page.pageTotal}">
					<input type="hidden" id="sort" name="sort" value="${sort}">
					<input type="hidden" id="order" name="order" value="${order}">
				</form>
				
				<table width="900" border="1" cellspacing="0" cellpadding="0">
					<tr class="check-table-tit">
					  <td>品称</td>
					  <td>出产地</td>
					  <td>被检单位或姓名</td>
					  <td>检测项目</td>
					  <td>抑制率</td>
					  <td>是否合格</td>
					  <td>检测日期</td>
					</tr>
					<c:forEach items="${page.pageData}" var="detect" varStatus="s">
				  	<tr>
						<td>${detect.productName }</td>
						<td>${detect.origin }</td>
						<td>${detect.unitName }</td>
						<td><!-- ${detect.inspection } -->蔬菜农药残留检测</td>
						<td>${detect.rate }%</td>
						<td>
							<c:choose>
								<c:when test="${detect.pass == 1}">
									<span class="check-yes"></span>
								</c:when>
								<c:otherwise>
									<span class="check-no"></span>
								</c:otherwise>
							</c:choose>
						</td>
						<td><fmt:formatDate value="${detect.detectTime }" type="date" pattern="yyyy-MM-dd"/></td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- 分页 页码标签 根据具体UI修改 start -->
				<div style="display: none;">
					<jsp:include page="/WEB-INF/jsp/pub/PageNumTemplate.jsp"></jsp:include>
				</div>
				<!-- 分页 页码标签 根据具体UI修改 end -->
				<div id="searchPage" class="fenyetest" style="text-align: center;margin-bottom: 20px;">
					<c:set var="currPage" value="${page.currentPage }"></c:set>
					<c:set var="totalPage" value="${page.pageTotal }"></c:set>
					<%@ include file="../pub/pagination/detectionPage.jsp" %>
				</div>
				</div>
				</c:if>
			</div>
			<div class="con-right anti-fake-con anti-tit-2 dn" style="display: none;">
				<h3 class="anti-fake-con-h3">谷登农批网&nbsp;&nbsp;&nbsp;&nbsp;农产品防伪溯源体系</h3>
				<p class="anti-fake-con-p">全方位实现食品从生产源头到流通环节的全程信息追溯，也可以为企业提供防伪防窜等管理解决方案。</p>
				<p class="anti-fake-con-p">覆盖农产品、果蔬、粮食、茶叶、中草药、肉类、水产品等行业的追溯体系。</p>
				<h4 class="anti-fake-con-h4">谷登农批网防伪溯源云平台</h4>
				<img src="${CONTEXT }v1.0/images/shop-images/anti-fake02.jpg" alt="防伪溯源">
				<h3 class="anti-fake-con-h3">农产品防伪溯源案例:</h3>
				<p class="anti-fake-con-p">通过对行业基础数据的采集、整合、处理、存储，建立 “牛肉品质安全溯源平台”，包括：养殖环节、屠宰环节、物流运输环节、精加工环节等，并通过多种终端设备数据采集接口，如手持终端设备、电子秤设备、物流监控信息、电子秤数据采集软件，将各环节信息数据汇集到牛肉品质安全溯源管理平台，打造牛肉品质安全监管的全程信息网络。</p>
				<img src="${CONTEXT }v1.0/images/shop-images/anti-fake03.jpg" alt="防伪溯源">
			</div>
			</div>
		<div class="fr anti-banner-img dn" style="display: none;"><img src="${CONTEXT }v1.0/images/shop-images/anti-fake04.jpg" alt="防伪溯源"></div>
		<div class="cl"></div>
	</div>
</div>
<!-- foot page -->
<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
<!-- fixed tool -->
<jsp:include page="/WEB-INF/jsp/pub/fixedTool.jsp"></jsp:include>
<script type="text/javascript">
$(".logi-tit-1").click(function(){
	$(this).addClass("anti-fake-tit-bg");
	$(".logi-tit-2").removeClass("anti-fake-tit-bg");
	$(".anti-tit-1").show();
	$(".anti-tit-2,.anti-banner-img").hide();
});
$(".logi-tit-2").click(function(){
	$(this).addClass("anti-fake-tit-bg");
	$(".logi-tit-1").removeClass("anti-fake-tit-bg");
	$(".anti-tit-1").hide();
	$(".anti-tit-2,.anti-banner-img").show();
});
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>