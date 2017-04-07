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
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>联系人：</span>
				<div class="right-contact">
					<input class="right-contact-us" id="realName" placeholder="输入名字" type="text" maxlength="20" value="${dto.realName}"  name="realName"  >
				</div>
			</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">手机号码：</span>
				<div class="right-contact">
					<input class="right-contact-us" id="mobile" placeholder="输入手机号" type="text" maxlength="11" value="${dto.mobile}" name="mobile"  >
				</div>
			</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">电话：</span>
				<div class="right-contact">
			<!-- 		<input class="right-contact-op1" placeholder="+86" type="text">&nbsp;—
					<input class="right-contact-op2" placeholder="区号" type="text">&nbsp;— -->
					
					<input class="right-contact-us" id="telephone" placeholder="电话" type="text" value="${dto.telephone}"  maxlength="13"
					name="telephone"  >
					
<!-- 					<input class="right-contact-op4" placeholder="输入分机号" type="text">
 -->					
				</div>
			</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">电子邮箱：</span>
				<div class="right-contact">
					<input class="right-contact-us" id='email' placeholder="电子邮箱" type="text" maxlength="50"  value="${dto.email}"  name="email"   >
				</div>
			</div>
			<!--联系地址-->
			<div class="edit-cont-box-dn edit-cont-box-act">
					<span class="rse-pro-tit"><em class="rse-pro-tit-red">*</em>联系地址：</span>
					<div class="con-pro-cit-area">
					
						<select name="provinceId" id="provinceId"  class="i-app-far-mar-c3" >
						</select>			 	
						<select name="cityId" id="cityId"  class="i-app-far-mar-c3">
						</select>
						<select name="areaId" id="areaId"  class="i-app-far-mar-c3">
						</select>
						<br/>
						<input class="i-app-far-mar-ph" type="text"  value="${dto.address}" maxlength="200"   name="address" id="address"/>
					</div>
				</div>
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">邮编：</span>
				<div class="right-contact">
					<input class="right-contact-us" id='keycode' placeholder="邮编" type="text" maxlength="6" value="${dto.zipCode}"  name="zipCode" />
				</div>
			 
			</div>
			
			<div class="edit-cont-box-dn edit-cont-box-act">
				<span class="rse-pro-tit">&nbsp;</span>
				<div class="right-contact">
					<input class="right-contact-bch" value="保存" type="button">
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
		var reg=/^[\u4e00-\u9fa5a-zA-Z]+$/;
		
		if($("#realName").val()!=""&&!reg.test($("#realName").val())){
			layer.msg('联系人只能输入中文字符或英文字符');
			return;
		}
		reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		
		if($("#email").val()!=""&&!reg.test($("#email").val())) {
			layer.msg('邮箱格式不正确，请填入正确邮箱地址！');
			return;
		}
		reg = /^[\d]{11}$/;
		
		if($("#mobile").val()!=""&&!reg.test($("#mobile").val())) {
			layer.msg('手机号码格式不正确！请输入11位的手机号码');
			return;
		}
		
		reg = /^[\d]{11,13}$/;
		
		if($("#telephone").val()!=""&&!reg.test($("#telephone").val())) {
			layer.msg('电话格式不正确！请输入11-13位的电话');
			return;
		}
		
		reg = /^\d{6}$/;
		
		if($("#keycode").val()!=""&&!reg.test($("#keycode").val())) {
			layer.msg('邮编格式不对，请输入6位数字邮箱！');
			return;
		}
		
		/* reg=/^[\u0391-\uFFE5\w]+$/;
		
		if($("#address").val()!=""&&!reg.test($("#address").val())){
			layer.msg('联系地址格式不对，请输入正确！');
			return;
		} */
		
		var url=CONTEXT+"userCenter/member/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				layer.msg('保存成功！'); 
			}else if(data=="namenotnull"){
				layer.msg('联系人姓名不能为空！'); 
				return;
			}else if(data=="mobile"){
				layer.msg('手机号码不能为空！'); 
				return;
			}else if(data=="mobileE"){
				layer.msg('请输入正确的手机号！'); 
				return;
			}else if(data=="existM"){
				layer.msg('该手机号已经被注册，请更换手机号！'); 
				return;
			}else if(data=="areaEmpty"){
				layer.msg('提交的地址异常或者为空，请刷新页面重试！'); 
				return;
			}else {
				layer.msg('保存失败！'); 
				return;
			}
		});
		
	})
		
 
 
 


</script>
<script src="${CONTEXT }js/city/city.js"></script>
<script>
 	$(document).ready(function() {
 		var city = new City($("#provinceId"),$("#cityId"),$("#areaId"));
 		city.init('${dto.provinceId}','${dto.cityId}','${dto.areaId}');
 		
 	});
					 	
					 	
</script>

 						