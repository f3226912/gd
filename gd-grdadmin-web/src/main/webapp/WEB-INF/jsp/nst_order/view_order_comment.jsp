<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css" href="../${CONTEXT}css/member/main.css"/>

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
						评价:
					</td>
					<td class="mright">
						<c:if test="${dto.evaluateType=='1'}">好评</c:if>
 						<c:if test="${dto.evaluateType=='2'}">差评</c:if>
					</td>
				</tr>
				
				
				<tr>
					<td class="mleft" >
					评语:</td>
					<td class="mright">
					${dto.comment}
					</td>
				</tr>
				
				
				<tr>
				<td class="mleft" >
					提交时间:</td>
					<td class="mright">
					${dto.createTimeString}
 					</td>
				</tr>
				
			</table>
			
		</div>
