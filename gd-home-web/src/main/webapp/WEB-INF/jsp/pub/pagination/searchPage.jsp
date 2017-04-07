<%@ page import="java.util.*,org.apache.commons.lang3.StringUtils" pageEncoding="UTF-8"%>
<%
	String queryString=request.getQueryString();
	if(StringUtils.isNotEmpty(queryString)){
	    queryString="?"+queryString;
	}
	request.setAttribute("queryString", queryString);
%>
<c:choose >
	<c:when test="${pageNavigateModel ==1 }">
		<!-- 产品页面分页 -->
		<c:choose >
			<c:when test="${!empty productQueryBean.cateId}">
				<c:set var="cateId" value="${productQueryBean.cateId}"/>
			</c:when>
			<c:otherwise>
				<c:set var="cateId" value="0"/>
			</c:otherwise> 
		</c:choose>
		<c:set var="model" value="market"/>
	</c:when>
	<c:when test="${pageNavigateModel ==2 }">
		<!--  商铺页面分页 --> 
		<c:choose >
			<c:when test="${!empty businessQueryBean.categoryId}">
				<c:set var="cateId" value="${businessQueryBean.categoryId}"/>
			</c:when>
			<c:otherwise>
				<c:set var="cateId" value="0"/>
			</c:otherwise> 
		</c:choose>
		<c:set var="model" value="business"/>
	</c:when>
	<c:when test="${pageNavigateModel ==3 }">
		<!--  物流页面分页 --> 
		<c:choose >
			<c:when test="${!empty businessQueryBean.categoryId}">
				<c:set var="cateId" value="${businessQueryBean.categoryId}"/>
			</c:when>
			<c:otherwise>
			</c:otherwise> 
		</c:choose>
		<c:set var="cateId" value="0"/>
		<c:set var="model" value="logistics"/>
	</c:when>
	<c:otherwise>
		<!-- 默认产品页面 -->
		<c:choose >
			<c:when test="${!empty productQueryBean.cateId}">
				<c:set var="cateId" value="${productQueryBean.cateId}"/>
			</c:when>
			<c:otherwise>
				<c:set var="cateId" value="0"/>
			</c:otherwise> 
		</c:choose>
		<c:set var="model" value="market"/>
	</c:otherwise>
</c:choose>
<div name="gduiPage1.3" class="gduiPage_main gduiPageskin_default" id="gduiPage_1">
<!-- 上一页 -->
<c:if test="${pageNow>1 }">
<a href="${CONTEXT}${gd:formatMarket(marketId) }/${model }/${cateId }-${pageNow-1 }.html${queryString }" class="gduiPage_prev" title="上一页">上一页</a>
</c:if>
<!-- 首页 -->
<c:if test="${pageNow-2>1 }">
<a href="${CONTEXT}${gd:formatMarket(marketId) }/${model }/${cateId }-1.html${queryString }" class="gduiPage_first" title="首页" >1</a>
<c:if test="${pageNow-2 != 2 }">
<span>…</span>
</c:if>
</c:if>
<c:if test="${pageNow-2>0}">
<a href="${CONTEXT}${gd:formatMarket(marketId) }/${model }/${cateId }-${pageNow-2}.html${queryString }" title="${pageNow-2}" >${pageNow-2}</a>
</c:if>
<c:if test="${pageNow-1>0}">
<a href="${CONTEXT}${gd:formatMarket(marketId) }/${model }/${cateId }-${pageNow-1}.html${queryString }" title="${pageNow-1}" >${pageNow-1}</a>
</c:if>
<span class="gduiPage_curr">${pageNow }</span>
<c:if test="${pageNow+1<=pageTotal}">
<a href="${CONTEXT}${gd:formatMarket(marketId) }/${model }/${cateId }-${pageNow+1}.html${queryString }" title="${pageNow+1}" >${pageNow+1}</a>
</c:if>
<c:if test="${pageNow+2<=pageTotal}">
<a href="${CONTEXT}${gd:formatMarket(marketId) }/${model }/${cateId }-${pageNow+2}.html${queryString }" title="${pageNow+2}" >${pageNow+2}</a>
</c:if>
<!-- 尾页 -->
<c:if test="${pageTotal-pageNow>2}">
<c:if test="${(pageNow+2) != (pageTotal-1) }">
<span>…</span>
</c:if>
<a href="${CONTEXT}${gd:formatMarket(marketId) }/${model }/${cateId }-${pageTotal}.html${queryString }" class="gduiPage_last" title="尾页">${pageTotal }</a>
</c:if>
<!-- 下一页 -->
<c:if test="${pageNow<pageTotal }">
<a href="${CONTEXT}${gd:formatMarket(marketId) }/${model }/${cateId }-${pageNow+1}.html${queryString }" class="gduiPage_next" title="下一页">下一页</a>
</c:if>
</div>