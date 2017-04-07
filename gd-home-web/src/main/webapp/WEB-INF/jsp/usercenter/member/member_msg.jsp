<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/tags.inc" %>

<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>消息中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<%@ include file="../../pub/head.inc" %>
	<link rel="stylesheet" href="../v1.0/css/member.css"/>	
	
	<script type="text/javascript" src="${CONTEXT}v1.0/js/member/msgCenter.js"></script>
</head>

<body>
  <jsp:include page="../../usercenter/userCenter_head.jsp" flush="true"/> 
    <jsp:include page="../../usercenter/userCenter_left.jsp" flush="true"/> 
		
<input type="hidden" id="memberId" name="memberId"    value="${dto.memberId}"/>

  
		<!-- 消息中心 -->
		<div class="mid-right" style="background-color: #fff;" >
		<div class="attest-meg clearfix">
			<div class="attest-meg-left fl">
				<h2 class="meg-tit">消息中心</h2>
				<ul class="meg-list"   id="_msgUl">
				
				<c:if test="${page==null || empty page.pageData }">
					<p class="helps-txt">没有消息</p>
				</c:if>
				
				
				<c:if test="${page!=null && not empty page.pageData}">
					<form id="pageForm" action="${CONTEXT }userCenter/msg/getList" method="post">
						<input type="hidden" id="page" name="page" value="${page.currentPage}">
						<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
						<input type="hidden" id="pageTotal" name="pageTotal" value="${page.pageTotal}">
						<input type="hidden" id="sort" name="sort" value="${param.sort}">
						<input type="hidden" id="order" name="order" value="${param.order}">
					</form>
					<c:forEach  items="${page.pageData}" var="pushRecordDTO" varStatus="s"> 
						<c:choose> 
							<c:when test="${s.index==0}">
								<li class="meg-curr">
							</c:when>
							<c:otherwise>
									<li>
							</c:otherwise>
						</c:choose>
							<input name="_msg" class="msg-checkbox" type="checkbox"  value="${pushRecordDTO.id}" />
						<c:choose> 
							<c:when test="${pushRecordDTO.state==0}">
								<span class="fl"></span><span class="meg-list-ico"  ></span><font style="font-weight: bold;">  ${pushRecordDTO.title}</font></span>
								<span class="meg-time"    	style="font-weight: bold;">
									<fmt:formatDate value="${pushRecordDTO.createTime}"  type="date" pattern="MM-dd"/> 
								</span>
							</c:when>
							<c:otherwise>
								<span class="fl"><span class="meg-list-ico"></span>${pushRecordDTO.title}</span>
								<span class="meg-time">
									<fmt:formatDate value="${pushRecordDTO.createTime}"  type="date" pattern="MM-dd"/> 
								</span>
							</c:otherwise>
				     	</c:choose>
						</li>
					</c:forEach>
				</c:if>
				</ul>
				<p class="meg-Operation">
					<input name="" type="checkbox" value=""  id="_markAll"/>
					<span class="meg-del"  id="_del">删除</span>
					<span class="meg-no-read"  id="_markedUnRead"  onclick="updateMsg(0)">标为未读</span>
					<span class="meg-read" id="_markedRead" onclick="updateMsg(1)">标为已读</span>
				</p>
			</div>
		<div class="attest-meg-right fl">
				<h2 class="meg-tit">消息内容</h2>
				<c:forEach  items="${page.pageData}" var="pushRecordDTO"  varStatus="s"> 
						<c:choose> 
						 	<c:when test="${s.index==0}">
								<div class="meg-txt"  id="${pushRecordDTO.id}"  >
							</c:when>
							<c:otherwise>
								<div class="meg-txt"  id="${pushRecordDTO.id}" style="display:none">
							</c:otherwise>
						</c:choose>
							<p class="meg-txt-tit">${pushRecordDTO.title}</p>
							<p class="meg-txt-con">${pushRecordDTO.content}</p>
						</div>
					
				</c:forEach>
	
						</div>	
		</div>
		
				<div id="page-next" style="background:#F5F5F5; padding:5px 0 10px 0;">
						<div class="gduiPage_main"></div>
					</div>
		
		
		
	</div>
	
	
	
	</div>
			 
			</table>
		
			
			
			
</html>

	
 	<script src="../v1.0/js/jquery-1.8.3.min.js"></script>
	<script src="../v1.0/js/gdui.js"></script>
 	<script>
 	
 	
	gduiPage({
	    cont: 'page-next',
	    pages: $("#pageTotal").val(), //
	    skip: true, //是否开启跳页
	    curr: $("#page").val(), 
	    jump: function(e, first){ //触发分页后的回调ZR
	    	$("#page").val(e.curr);
	        if(!first){ //一定要加此判断，否则初始时会无限刷新
	        	$("#pageForm").submit();	          
	        }
	    },
	    staticPage:true 
	});
 	</script>