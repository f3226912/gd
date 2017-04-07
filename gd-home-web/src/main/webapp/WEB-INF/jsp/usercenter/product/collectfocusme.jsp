<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!doctype html>
<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
<%-- 	<link rel="stylesheet" href="${CONTEXT }v1.0/css/global.css"/>	 --%>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/member.css"/>
	<script type="text/javascript" src="${CONTEXT}js/page.js"></script>
	<script src="${CONTEXT }v1.0/js/jquery-1.8.3.min.js"></script>
	<script src="${CONTEXT }v1.0/js/gdui.js"></script>
	<script src="${CONTEXT }js/common.js"></script>
<%-- 	<script type="text/javascript" src="${CONTEXT }js/jquery-1.8.3.min.js"></script> --%>

</head>
<body>
	<jsp:include page="/WEB-INF/jsp/usercenter/userCenter_head.jsp" flush="true"/> 
   	<jsp:include page="/WEB-INF/jsp/usercenter/userCenter_left.jsp" flush="true"/>
		
		<!-- 收藏的产品 -->
		<div class="mid-right bg-white">
			
			<div class="tab-box-col">
				<ul class="tab_col_menu">
				    	<li class="selected"><a>关注我的客户</a></li>
				        <li><a href="${CONTEXT }userCenter/collect/product">关注的农批产品</a></li>
				        <li><a href="${CONTEXT }userCenter/collect/shops">关注的商铺</a></li>
				</ul>
				
				<div class="tab-box-c">
					<c:if test="${page==null || empty page.pageData }">
						<p style="margin: 50px 0; text-align: center; font-size: 14px;">亲，还没有人关注您哦！</p>
					</c:if>
					<c:if test="${page!=null && not empty page.pageData}">
						<form id="pageForm" action="${bid }" method="post">
							<input type="hidden" id="page" name="page" value="${page.currentPage}">
							<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
							<input type="hidden" id="pageTotal" name="pageTotal" value="${page.pageTotal}">
							<input type="hidden" id="sort" name="sort" value="${param.sort}">
							<input type="hidden" id="order" name="order" value="${param.order}">
							
						</form>
						<div class="tab-box-cet-cl hide1" style="display: block;">
							<table class="tab-att">
								<thead class="tab-att-thd">
									<tr>
										<th>姓名</th>
										<th>电话</th>
									</tr>
								</thead>
								
								<c:forEach items="${page.pageData}" var="collect" varStatus="s">
									<tbody class="tab-att-tb">
										<tr>
											<td>${collect.memberName }</td>
											<td>${collect.mobile }</td>
										</tr>
									</tbody>
								</c:forEach>
								
							</table>
							<!-- 分页 页码标签 根据具体UI修改 start -->
							<jsp:include page="/WEB-INF/jsp/pub/PageNumTemplate.jsp"></jsp:include>
							<!-- 分页 页码标签 根据具体UI修改 end -->
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		
	</script>
	
	<!--简版底部 star-->
	
	<!--简版底部 end-->


</html>
