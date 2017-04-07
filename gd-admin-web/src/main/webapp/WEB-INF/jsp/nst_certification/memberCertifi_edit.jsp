<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="../${CONTEXT}css/member/main.css"/>

<form id="addForm" method="post" action="member/save">
		<div>
			
			<input type="hidden" id="memberId" name="memberId"    value="${dto.memberId}"/>
			<table>
				<tr>
					<td class="mleft"  >
						账号:
					</td>
					<td class="mright">
						<input  type="text" id="account"  name="account"   value="${dto.account}" required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						昵称:
					</td>
					<td class="mright">
						<input  type="text" id="nickName"  name="nickName"   value="${dto.nickName}" required="true"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						密码:
					</td>
					<td class="mright">
						<input  type="password"  id="password" value="${dto.password}"   name="password" required="true"  class="easyui-validatebox"        style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						用户类型:
					</td>
					<td class="mright">
						<select name="level"  id="level"  >
<!-- 						（1谷登农批，2农速通，3农商友 ，其余待补）
 -->						<option value="">注册来源</option>
							<option value="1" <c:if test="${dto.level==1 }">selected</c:if>   >谷登农批</option>
							<option value="2" <c:if test="${dto.level==2 }">selected</c:if> >农速通</option>
							<option value="3" <c:if test="${dto.level==3 }">selected</c:if>  >农商友</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						认证状态:
					</td>
					<td class="mright">
						<select name="isAuth">
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_certifi 表得到）
							 -->						
<!-- 							<option value="0">请选择</option>
 -->						<option value="1" <c:if test="${dto.isAuth==1 }">selected</c:if>>已验证</option>
							<option value="0" <c:if test="${dto.isAuth==0 }">selected</c:if>>未验证</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
					激活状态:
					</td>
					<td class="mright">
						<select name="nstStatus">
							<!-- 0未启用，1已启用  -->	
							<option value="0" <c:if test="${dto.nstStatus==0 }">selected</c:if>>启用</option>
 							<option value="1" <c:if test="${dto.nstStatus==1 }">selected</c:if>>未启用</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
					生日:</td>
					<td class="mright">
						<input  type="text"  id="birthday" value="${dto.birthday}"   name="birthday" required="true"    class="easyui-datebox"    style="width:150px" >
					</td>
				</tr>
			</table>
			<div>
			 
			</div>
			<div>
			</div>
 			
			
		</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/member/add.js"></script>











