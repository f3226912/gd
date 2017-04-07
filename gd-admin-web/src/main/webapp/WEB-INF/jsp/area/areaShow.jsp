<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/head.inc" %>
<%@ include file="../pub/tags.inc" %>
<title>地区展示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>

</head>
<body>
	<form action="">
		<c:if test="${ !empty area.areaID }">
	 	<input type="hidden" id="areaID" value="${area.areaID }">	
		<table>
			<tr>
				<td>区域名：
				</td>
				<td>${area.area }
				</td>
			</tr>
			<tr>
				<td>排序字段：
				</td>
				<td><input type="text" id="sort"value="${area.sort}">
				</td>
			</tr>
			<tr>
				<td>是否可用：
				</td>
				<td>
				可用 <input type="radio" id="status" name="status"  value="1" <c:if test="${ area.status == 1 }">checked</c:if>>
				不可用 <input type="radio" id="status" name="status" value="0" <c:if test="${ area.status == 0 }">checked</c:if>>
				
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td><input id="btnLogin" type="button" value="保存" onclick="saveArea();" class="btn" /> 
				</td>
			</tr>
		</table>
		</c:if>
	</form>
	
<script type="text/javascript" src="${CONTEXT}js/jquery.easyui.min.js"></script>
<script type="text/javascript">
 function saveArea(){
	 $.ajax({url: "${CONTEXT}/area/edit2?t="+new Date(),
			data:{
					"areaID": $("#areaID").val(),
					//"area": $("#area").val(),
					"status":$("input[name='status']:checked").val(),
					"sort": $("#sort").val()
				},			    			
			type: 'POST',
			async: false,//同步
			timeout: 1000, 
			error: function(){
				alert("保存失败!");
				ret = false;
			},
			success: function(result){
				if(result=="true"){
					$.messager.show({
						title:'操作提示',
						msg:'保存成功。',
						timeout:3000,
						showType:'slide'
					});

				}
				else {
					alert("保存失败!");
					ret = false;						
				}
			}
 	});
 }
</script>
</body>
</html>