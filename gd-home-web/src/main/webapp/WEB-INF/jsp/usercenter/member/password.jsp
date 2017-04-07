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
		
<form action="member/updatePassword" name="addForm" id="addForm">
<input type="hidden" id="memberId" name="memberId"    value="${dto.memberId}"/>

 
		
		
		
<!--产品发布产品发布产品发布产品发布产品发-->
		<div class="mid-right bg-white">
			<h1 class="mid-right-store">修改密码</h1>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>旧密码：</span>
				<div class="right-contact">
					<input class="right-contact-us" placeholder="请输入旧密码，长度6-20位" 
					type="password" maxlength="10" value=""  name="password"  >
				</div>
			</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>新密码：</span>
				<div class="right-contact">
					<input class="right-contact-us" id="pass_id1" placeholder="请输入密码，长度6-20位" 
					type="password" maxlength="10" value=""  name="password1"  >
				</div>
			</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>确认新密码：</span>
				<div class="right-contact">
					<input class="right-contact-us" placeholder="请再次输入新密码" 
					type="password" maxlength="10" value=""  name="password2"  >
				</div>
			</div>
			 
			 
			 <div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">&nbsp;</span>
				<div class="right-contact">
<!-- 					<input class="right-contact-bch" value="保存" type="button">
 -->								
 						<button class="right-contact-bch" type="button">保存</button>
					
				</div>
			</div>
				
			</div>
		 

			
		</div>
	<div class="clear"></div>

	</div>

	<!--简版底部 star-->
	
	<!--简版底部 end-->


			 
			</table>
			</form>
</html>

 
	
 	<script src="../v1.0/js/jquery-1.8.3.min.js"></script>
	<script src="../v1.0/js/gdui.js"></script>
	<script type="text/javascript">
	$('.right-contact-bch').click(function(){
		var length=$("#pass_id1").val().length;
		if(length==0){
			alert("请重新输入密码，可以是数字、英文、特殊符号或组合");
			return false;
		}
		if(length<6||length>20){
			alert("请重新输入密码，可以是数字、英文、特殊符号或组合");
			return false;
		}		
		var url=CONTEXT+"userCenter/member/updatePassword";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				layer.msg('保存成功！'); 
			}
			else if(data == "notCorrect"){
				layer.msg('原密码输入不正确，请重新输入！'); 
				return;

			}  else if(data == "notnull"){
				layer.msg('原始密码，新密码，确认密码均不能为空！'); 
				return;

			}else if(data=="checkSame"){
				layer.msg('新密码与旧密码不能一致，请重新输入！'); 
				return;
			} else if(data == "notSame"){
				layer.msg('两次密码输入不同，请重新输入！'); 
				return;

			}else {
				layer.msg('保存失败！'); 
				return;
			}
		});
		
	})
		
 
</script>
 						