<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="../pub/constants.inc" %>
		<%@ include file="../pub/head.inc" %>
		<%@ include file="../pub/tags.inc" %>
		<meta name="Content-Type" content="text/html; charset=utf-8" />
		<meta name="pragma" content="no-cache"/>
		<meta name="cache-control" content="no-cache"/>
		<meta name="expires" content="0"/>
		<meta name="keywords" content="农批网,公告信息,谷登农批网公告"/>
		<meta name="description" content="谷登农批网重大信息公告中心!"/>
		<meta name="renderer" content="webkit">
		<meta name="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/global.css"/>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/index-shop.css"/>
		<title>公告信息-谷登农批网</title>
	</head>
<body>
	<!--head start-->
	<jsp:include page="/WEB-INF/jsp/pub/headsearch.jsp"></jsp:include>

	<div class="nav">
		<div class="nav_inside clearfix">
			<div class="nav_title"><span>公告信息</span>
				<div class="nav_title_show"></div>
			</div>
			<!-- 主导航栏 -->
			<div class="main_menu">
			<jsp:include page="/WEB-INF/jsp/pub/mainMenu.jsp"></jsp:include>
			</div>
		</div>
	</div>
	
	<!-- 主体 -->
	<div class="main clearfix">
		<div class="content mgtop">
			<div class="con-left fl">
				<div class="con-left-tit check-img logi-right helps">
					<p>公告信息</p>
				</div>
				<div class="con-left-tit helps-left helps-left1 helpa-aboutus">
					<h2 class="shopname helps-left-tit public-meg"><a href="${CONTEXT}notice-list">公告信息</a><span class="helps-ico02"></span></h2>
				</div>
			</div>
			<div class="fl">
				<!-- 公告信息 -->
				<div class="con-right products-con helps-cont public-meg-con">
					<h5 class="helps-right-tit">公告信息</h5>
					<p><span class="helps-right-tit-bor1"></span><span class="helps-right-tit-bor2"></span></p>
					<c:if test="${page==null || empty page.pageData }">
						<p class="helps-txt">暂时无公告信息</p>
					</c:if>
					<c:if test="${page!=null && not empty page.pageData}">
						<form id="pageForm" action="${CONTEXT }notice-list" method="post">
							<input type="hidden" id="page" name="page" value="${page.currentPage}">
							<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
							<input type="hidden" id="pageTotal" name="pageTotal" value="${page.pageTotal}">
							<input type="hidden" id="sort" name="sort" value="${param.sort}">
							<input type="hidden" id="order" name="order" value="${param.order}">
						</form>
						<c:forEach items="${page.pageData}" var="notice" varStatus="s">
							<c:if test="${notice.linkUrl !=null && not empty notice.linkUrl}">
								<p class="helps-txt"><a href="${notice.linkUrl}" target="_black">${notice.title }</a></p>
							</c:if>
							<c:if test="${notice.linkUrl ==null || empty notice.linkUrl}">
								<p class="helps-txt"><a href="${CONTEXT }notice-cont/${notice.id }" target="_black">${notice.title }</a></p>
							</c:if>
						</c:forEach>
						<!-- 分页 页码标签 根据具体UI修改 start -->
						<jsp:include page="/WEB-INF/jsp/pub/PageNumTemplate.jsp"></jsp:include>
						<!-- 分页 页码标签 根据具体UI修改 end -->
					</c:if>
				</div>
		    </div>
			<div class="cl"></div>
		</div>
	</div>


	<jsp:include page="/WEB-INF/jsp/pub/foot.jsp"></jsp:include>
	
	<script type="text/javascript" src="${CONTEXT}v1.0/js/gdui.js"></script>
	<script type="text/javascript" src="${CONTEXT}v1.0/js/common.js"></script>
	<script type="text/javascript" src="${CONTEXT}js/page.js"></script>
	
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>