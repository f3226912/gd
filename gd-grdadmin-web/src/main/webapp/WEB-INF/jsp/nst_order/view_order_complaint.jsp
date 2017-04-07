<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" href="../${CONTEXT}css/member/main.css"/>

<form id="updateForm" method="post" action=nst_order_complaint/updateStatus">
	<input type="hidden" id="id" name="id"    value="${dto.id}"/>
		<div>
			<table>
				<tr>
					<td class="mleft"  >
						运单号:
					</td>
					<td class="mright">
						${dto.orderNo}
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
						发运人:
					</td>
					<td class="mright">
					${dto.shipperName}
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
					发运人手机号码:</td>
					<td class="mright">
					${dto.shipperMobile}
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
					 承运人:
					</td>
					<td class="mright">
					${dto.ownerName}
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
					 承运人手机号码:</td>
					<td class="mright">
					${dto.ownerMobile}
					</td>
				</tr>
				
				<tr>
				<td class="mleft" >
					货物名称:</td>
					<td class="mright">
					${dto.goodsName}
 					</td>
				</tr>
				
				
				<c:if test="${dto.totalWeight >0}">
				<tr>
				<td class="mleft" >
					货物重量:</td>
				<td class="mright">
					${dto.totalWeight} 
					<c:if test="${dto.hundredweight eq 0}">吨</c:if> &nbsp;&nbsp;
					<c:if test="${dto.hundredweight eq 1}">公斤</c:if>&nbsp;&nbsp;
 		    	</td>
 		    	</tr>
 		    	</c:if>
 		    	
 		    	<c:if test="${dto.totalSize >0}">
 		    	 <tr>
 		    		<td class="mleft" >
					货物体积:</td>
					<td class="mright">
					${dto.totalSize} 立方米
 		    	   </td>
 		    	 </tr>  
 		    	</c:if>
				
				
				<tr>
				<td class="mleft" >
					车牌号码:</td>
					<td class="mright">
					${dto.carNumber}
 					</td>
				</tr>
				
				<tr>
				<td class="mleft" >
					投诉内容:</td>
					<td class="mright">
					 ${dto.complaintsDetails}
 					</td>
				</tr>
				
				<tr>
				<td class="mleft" >
					回复:</td>
					<td class="mright">
					<textarea rows="10" id="reply" cols="60" name="reply" maxlength="200">${dto.reply}</textarea>
 					</td>
				</tr>
			</table>
			
		</div>
		
</form>