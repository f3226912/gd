<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<%
	Calendar cal=Calendar.getInstance();
	cal.add(Calendar.DATE,-1);
	String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	request.setAttribute("modifyDate",yesterday);
%>
<!DOCTYPE html>
<html>
<head>
<base href="${CONTEXT}" >
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<link href="v1.0/module/mui/examples/login/css/mui.min.css" rel="stylesheet" type="text/css" />
<link href="v1.0/module/mui/examples/login/css/style.css" rel="stylesheet" type="text/css"  />
<link href="v1.0/css/login.css" rel="stylesheet" type="text/css"/>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="format-detection" content="telephone=no" />
<title>首页</title>
</head>
<body style="background-color: #f5f5f9;">
<p class="mui-upd-date">最后更新时间：${modifyDate }</p>
	<div class="mui-account">
	<!--  综合看板数据 START -->
		<div class="mui-look-edition">
			<div class="mui-lonk-title">
				<h2 class="mui-lonk-title-h2">
					<span class="mui-look-span-icon"></span>综合看板
				</h2>
				<a href="sysUserBoard/getUserBoardList?type=1" target="_top" class="mui-look-span-icon mui-lonk-title-icon"></a>
			</div>
			<c:set var="count" value="0"></c:set>
			<c:set var="len" value="${fn:length(index.commonBoardList) }"></c:set>
			<c:if test="${!empty index.commonBoardList }">
				<div class="mui-table-sz-center">
					<ul class="mui-table-td"> 
					<c:forEach var="board" items="${index.commonBoardList}" varStatus="i">
						<li class="mui-table-td-hs-bed">
						<div class="mui-table-div-box">
							<span class="mui-table-td-span1">
							<c:if test="${board.values >=1000.0 }">
								<fmt:formatNumber value="${gd:formatNumber2(board.values) }" pattern="#,#00.#"/>
							</c:if>
							<c:if test="${board.values <1000.0 }">
								${gd:formatNumber2(board.values) }
							</c:if>
							</span><br/><span class="mui-table-td-span2">${board.name }</span>
						</div>
						</li> 
					</c:forEach>
					</ul>
				</div>
			</c:if>
			<c:if test="${empty index.commonBoardList}">
				<div class="mui-table-sz-center">
				<p style="text-align: center;">您还没有添加数据哦！</p>
				</div>
			</c:if>
		</div>
		<!-- 综合看板数据 END -->	

		<!-- 分类看板数据 START -->
		<c:forEach items="${index.dataMenu2 }" var="menu" varStatus="j">
			<div class="mui-look-edition-shop">
				<div class="mui-look-edition-box1">
				<div class="mui-box-div">
					<span class="mui-box-div-st"></span>
					<span class="mui-look-edition-dp"><span class="mui-look-edition-dp-icon"></span>${menu.menuName }</span>
					<span class="mui-box-div-ed"></span>
				</div>
				<a href="sysUserBoard/getUserBoardList?menuID=${menu.menuID}&type=2" target="_top" class="mui-look-span-icon mui-lonk-title-icon3"></a>
				<c:if test="${ menu.menuID ne '20' &&  menu.menuID ne '21'}">
					<a href="H5/workbench/report?menuId=${menu.menuID }" target="_top"><i class="mui-look-span-icon mui-lonk-i-icon3"></i></a>
				</c:if>
				<c:if test="${menu.menuID eq '20' }">
					<a href="report/report2.html?userID=${systemLoginUser.userID }&CONTEXT=${CONTEXT}" target="_top"><i class="mui-look-span-icon mui-lonk-i-icon3"></i></a>
				</c:if>
				<c:if test="${menu.menuID eq '21' }">
					<a href="report/report1.html?userID=${systemLoginUser.userID }&CONTEXT=${CONTEXT}" target="_top"><i class="mui-look-span-icon mui-lonk-i-icon3"></i></a>
				</c:if>
				</div>
				<c:set var="count" value="0"></c:set>
				<c:set var="len" value="${fn:length(menu.boardList) }"></c:set>
				<c:if test="${!empty menu.boardList }">
				<div class="mui-table-sz-center">
					<ul class="mui-table-td mui-table-td-shp"> 
						<c:forEach var="board" items="${menu.boardList}" varStatus="i">
							<c:if test="${board.status ne '0' }">
								<li class="mui-table-td-hs-bed">
									<div class="mui-table-div-box">
										<span class="mui-table-td-span1">
										<c:if test="${1000.0 <=board.values  }">
											<fmt:formatNumber value="${gd:formatNumber2(board.values) }" pattern="#,#00.#"/>
										</c:if>
										<c:if test="${1000.0>board.values }">
											${gd:formatNumber2(board.values) }
										</c:if>
										</span><br/><span class="mui-table-td-span2">${board.name }</span>
									</div>
								</li>
							</c:if>
							<c:if test="${board.status eq '0' }">
								<c:set var="count" value="${count+1 }"></c:set>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				</c:if>
				<c:if test="${empty menu.boardList || count ==len }">
					<div class="mui-table-sz-center">
					<p style="text-align: center;">您还没有添加数据哦！</p>
					</div>
				</c:if>
			</div>
		</c:forEach>
		<!-- 分类看板数据 END  -->
	</div>
</body>
</html>