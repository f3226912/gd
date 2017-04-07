<%@ page import="java.util.*,org.apache.commons.lang3.StringUtils" pageEncoding="UTF-8"%>
<%
	String queryString=request.getQueryString();
	if(StringUtils.isNotEmpty(queryString)){
	    queryString="?"+queryString;
	}
	request.setAttribute("queryString", queryString);
%>
<c:set var="model" value="test"/>
<div name="gduiPage1.2" class="gduiPage_main gduiPageskin_default" id="gduiPage_0">
<!-- 上一页 -->
<c:if test="${currPage>1 }">
<a href="${CONTEXT}${model }/${currPage-1 }.html${queryString }" class="gduiPage_prev" title="上一页">上一页</a>
</c:if>
<!-- 首页 -->
<c:if test="${currPage-2>1}">
<a href="${CONTEXT}${model }/1.html${queryString }" class="gduiPage_first" title="首页" >1</a>
<c:if test="${currPage-2 != 2 }">
<span>…</span>
</c:if>
</c:if>
<c:if test="${currPage-2>0}">
<a href="${CONTEXT}${model }/${currPage-2}.html${queryString }" title="${currPage-2}" >${currPage-2}</a>
</c:if>
<c:if test="${currPage-1>0}">
<a href="${CONTEXT}${model }/${currPage-1}.html${queryString }" title="${currPage-1}" >${currPage-1}</a>
</c:if>
<span class="gduiPage_curr">${currPage }</span>
<c:if test="${currPage+1<=totalPage}">
<a href="${CONTEXT}${model }/${currPage+1}.html${queryString }" title="${currPage+1}" >${currPage+1}</a>
</c:if>
<c:if test="${currPage+2<=totalPage}">
<a href="${CONTEXT}${model }/${currPage+2}.html${queryString }" title="${currPage+2}" >${currPage+2}</a>
</c:if>
<!-- 尾页 -->
<c:if test="${totalPage-currPage>2 }">
<c:if test="${(currPage+2) != (totalPage-1) }">
<span>…</span>
</c:if>
<a href="${CONTEXT}${model }/${totalPage}.html${queryString }" class="gduiPage_last" title="尾页">${totalPage }</a>
</c:if>
<!-- 下一页 -->
<c:if test="${currPage<totalPage }">
<a href="${CONTEXT}${model }/${currPage+1}.html${queryString }" class="gduiPage_next" title="下一页">下一页</a>
</c:if>
<!-- <span class="gduiPage_total"><label>到第</label> -->
<%-- <input type="number" min="1" onkeyup="this.value=this.value.replace(/\D/, '');" class="gduiPage_skip" value="${currPage}"> --%>
<!-- <label>页</label> -->
<!-- <button type="button" class="gduiPage_btn">确定</button></span> -->
</div>