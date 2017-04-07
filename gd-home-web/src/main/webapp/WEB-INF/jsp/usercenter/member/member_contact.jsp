<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/tags.inc" %>
<%@ include file="../../pub/head.inc" %>
<html>
<head> 
	<meta name="Content-Type" content="text/html; charset=UTF-8">
	<title>会员中心</title>
	<meta name="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" href="../v1.0/css/global.css"/>	
	<link rel="stylesheet" href="../v1.0/css/member.css"/>	
</head>

<body>
     <jsp:include page="../../usercenter/userCenter_head.jsp" flush="true"/> 
   <jsp:include page="../../usercenter/userCenter_left.jsp" flush="true"/> 
		
		
<form action="member/save" name="addForm" id="addForm">
<input type="hidden" id="memberId" name="memberId"    value="${dto.memberId}"/>
 
		
		
		
<!--产品发布产品发布产品发布产品发布产品发-->
		<div class="mid-right bg-white">
			<h1 class="mid-right-store">联系我们</h1>
			
 		 <p class="rse-pro-tit-red" style="padding:20px 0 0 50px; font-weight:bold;">请到个人信息中心维护联系信息</p>  
		 
 			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit"><em class="rse-pro-tit-red"></em>联系人：</span>
				<div class="right-contact">
					<span class="right-contact-us" style="border:none">	 ${dto.realName}  </span>
				</div>
			</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">手机号码：</span>
				<div class="right-contact">
					 <span class="right-contact-us" style="border:none">	  ${dto.mobile} </span>
				</div>
			</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">电话：</span>
				<div class="right-contact">
				
					<span class="right-contact-us" style="border:none">		${dto.telephone} </span>
				</div>
			</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">电子邮箱：</span>
				<div class="right-contact">
								<span class="right-contact-us" style="border:none">	 ${dto.email} </span>
				 </div>
			</div>
			<!--联系地址-->
			<div class="edit-cont-box-dn edit-cont-box-act">
					<span class="rse-pro-tit"><em class="rse-pro-tit-red"></em>联系地址：</span>
					<div class="con-pro-cit-area">
					 	<span class="right-contact-us" style="border:none">	 
					 	
							<c:forEach  items="${provinces}" var="area" >
 							 <c:if test="${area.areaID == dto.provinceId}">${area.area}</c:if>
							</c:forEach>
							<c:forEach  items="${citys}" var="area" >
 							 <c:if test="${area.areaID == dto.cityId}">${area.area}</c:if>
							</c:forEach>
							<c:forEach  items="${areas}" var="area" >
 							 <c:if test="${area.areaID == dto.areaId}">${area.area}</c:if>
							</c:forEach>



						 </span>
						 
						<br/>
				<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit"> &nbsp</span>
				<div class="right-contact">
								<span class="right-contact-us right-contact-us2" style="border:none">	${dto.address}</span>
				 </div>
				</div>
					</div>
				</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">邮编：</span>
				<div class="right-contact">
					<span class="right-contact-us" style="border:none">	 ${dto.zipCode} </span>
				</div>
			</div>
			
			<%--  商铺url,需要虚拟		
			<div class="edit-cont-box-dn edit-cont-box-act">
					<span class="rse-pro-tit">商铺网址：</span>
					<div class="right-contact"><span class="right-contact-us" style="border:none">${bdto.shopsUrl}</span></div>
			</div> 
			--%>

			
		</div>
	<div class="clear"></div>

	</div>

	<!--简版底部 star-->
	
	<!--简版底部 end-->


			 
			</table>
			</form>
</html>

	<script src="../v1.0/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">

function changeProvince(pid){
	
	jQuery.post(CONTEXT+"member/queryCity",{"pid":pid},function(data){
		if (data){
		alert(data);
		}else{
			warningMessage(data);
			return;
		}
	});
	 
	
}


function save() {
	var url=CONTEXT+"member/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				alert("保存成功");
			} else {
				alert(data);
				return;
			}
		});
}



</script>
<!--

//-->
</script>
 						