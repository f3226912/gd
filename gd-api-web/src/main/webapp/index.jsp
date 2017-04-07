<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<style>
* { padding:0; margin:0; }
html, body { height:100%; border:none 0; }
#iframe { width:100%; height:100%; border:none 0; }
</style>

<head>
	<script src="js/jquery.min.js"></script>
	<script src="js/md5.js"></script>
</head>
<body>

<div align="center" style="font-size: 22">
username: <input type="text"  id="username" value=""/> <br />
password: <input type="password"  id="password" value=""/> <br />

<input type="button"  value="LOGIN" id="submit"  onclick="login()"/>


</div>



<script>


function login(){
	
	var username=$("#username").val();
	var password=$("#password").val();
	password=hex_md5(password+"gudeng2015@*&^-");
	password=password.toUpperCase();
	$.ajax({
		url : "v2/member/login",
		data : {
			account: username,
			password:password,
			level:3
		},
		dataType:"json",
		success : function(data) {  
			if(data.statusCode==0)
			{
				alert("login success");
				window.location.href="login_success.jsp";
			}
			else{
				alert(data.msg);
			}
			
		
		},
		error : function() {  
			alert("更新失败,请稍后再试");
		},
	});
}


</script>




</body>




</html>