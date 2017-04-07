<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>

<form id="addForm" method="post" action="#">
	
		
	    <div id="tt" class="easyui-tabs">  
        <div title="会员信息">	
			<input type="hidden" id="memberId" name="memberId"    value="${dto.memberId}"/>
			<table>
				<tr>
					<td class="mleft"  >
						账号:
					</td>
					<td class="mright">
						<input disable type="text" id="account"  name="account"   value="${dto.account}" required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						真实姓名:
					</td>
					<td class="mright">
						<input  type="text" id="realName"  name="realName"   value="${dto.realName}" required="true"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						昵称:
					</td>
					<td class="mright">
						<input disable type="text" id="nickName"  name="nickName"   value="${dto.nickName}" required="true"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						密码:
					</td>
					<td class="mright">
						<input disable type="password"  id="password" value="${dto.password}"   name="password" required="true"  class="easyui-validatebox"        style="width:150px" >
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						用户类型:
					</td>
					<td class="mright">
						<select disable name="level"  id="level"  >
<!-- 						（1谷登农批，2农速通 ，3农商友 ，其余待补）
 -->						<option value="">用户类型</option>
							<option value="1" <c:if test="${dto.level==1 }">selected</c:if>   >谷登农批</option>
							<option value="2" <c:if test="${dto.level==2 }">selected</c:if> >农速通</option>
							<option value="3" <c:if test="${dto.level==3 }">selected</c:if>  >农商友</option>
							<option value="4" <c:if test="${dto.level==4 }">selected</c:if>  >产地供应商</option>
							<option value="5" <c:if test="${dto.level==5 }">selected</c:if>  >农批友</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						认证状态:
					</td>
					<td class="mright">
						<select disable name="isAuth">
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_certifi 表得到）
							 -->						
							<option value="-1" <c:if test="${empty dto.isAuth}">selected</c:if>>未提交认证</option>
					
 							<option value="1" <c:if test="${dto.isAuth==1 }">selected</c:if>>已认证</option>
							<option value="0" <c:if test="${dto.isAuth==0 }">selected</c:if>>待认证</option>
							<option value="2" <c:if test="${dto.isAuth==2 }">selected</c:if>>已驳回</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="mleft" >
						商铺名称:
					</td>
					<td class="mright">
						<input  type="text" id="shopName"  name="shopName"  value="${bdto.shopsName}" style="width:150px" >
					</td>
				</tr>
				<c:if test="${dto.level!=4 }">
				<tr>
					<td class="mleft" >
						所属市场:
					</td>
					<td class="mright">
 						<c:choose>
 						<c:when test="${ empty mdto.marketName}">
 						<input type="button" id="showMarketWin" value="选择市场"> 
						<input type="hidden" id="marketId_add" name="marketId" value="" >
 						</c:when>
 						<c:otherwise>
 						<input type="button" id="showMarketWin" value="${mdto.marketName}"> 
						<input type="hidden" id="marketId_add" name="marketId" value="${mdto.id}" >
 						</c:otherwise>
 						</c:choose>
 						
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="mleft" >
					激活状态:
					</td>
					<td class="mright">
						<select name="status">
							<option value="1" <c:if test="${dto.status==1 }">selected</c:if>>启用</option>
 							<option value="0" <c:if test="${dto.status!=1}">selected</c:if>>禁用</option>
						</select>
					</td>
				</tr>
			<%-- 	<tr>
					<td class="mleft" >
					生日:</td>
					<td class="mright">
						<input disable  type="text"  id="birthday" value="${dto.birthday}"   name="birthday" required="true"    class="easyui-datebox"    style="width:150px" >
					</td>
				</tr> --%>
			</table>
			
			
         </div>  

				</div>
      
        </div>  
    </div>  
</form>











