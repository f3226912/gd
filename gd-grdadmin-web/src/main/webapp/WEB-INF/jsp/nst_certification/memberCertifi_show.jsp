<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" type="text/css" href="../${CONTEXT}css/member/main.css"/>

<form id="auditForm" method="post" action="nst_certification/audit">
		<div>
			<input type="hidden" id="certifiId" name="certifiId"    value="${dto.certifiId}"/>
			<input type="hidden" id="memberId"  name="memberId"     value="${dto.memberId}"/>
			<input type="hidden" id="nst_bzlPhotoUrl"  name="nst_bzlPhotoUrl"     value="${dto.nst_bzlPhotoUrl}"/>
			<input type="hidden" id="nstStatus" name="nstStatus"    value="1"/>
			<input type="hidden" id="type" name="type"    value="${dto.type}"/>
			<table>
				<tr>
					<td class="mleft"  >
						账号:
					</td>
					<td class="mright">
						${dto.account}
					</td>
				</tr>
			<%-- 	<tr>
					<td class="mleft" >
						昵称:
					</td>
					<td class="mright">
					${dto.nickName}
					</td>
				</tr> --%>
				<tr>
					<td class="mleft" >
				         用户类型:
					</td>
<!-- 					//会员类别（1谷登农批，2农速通，3农商友，4产地供应商 ，其余待补）
 -->					
					<td class="mright">
						<c:if test="${dto.level==1 }">谷登农批</c:if>
 						<c:if test="${dto.level==2 }">农速通</c:if>
						<c:if test="${dto.level==3 }">农商友</c:if>
						<c:if test="${dto.level==4 }">产地供应商</c:if>
					</td>
				</tr>
				
				
				<tr>
					<td class="mleft" >
					姓名:</td>
					<td class="mright">
					${dto.nst_linkMan}
					</td>
				</tr>
				
				
				<tr>
				<td class="mleft" >
					提交时间:</td>
					<td class="mright">
					${dto.commitTime_string}
 					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
					激活状态:
					</td>
					<td class="mright">
							<!-- 	//是否已经认证，0 未认证，1已认证  （通过关联查询 member_baseinfo 表得到）
							 -->
							<!-- 0未启用，1已启用  -->	
							<c:if test="${dto.isActivi==0 }">未启用</c:if>
 						 	<c:if test="${dto.isActivi==1 }">启用</c:if>
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
						商家认证:
					</td>
					<td class="mright">
							<c:if test="${dto.nstStatus==0 }">待认证</c:if>
 						 	<c:if test="${dto.nstStatus==1 }">已认证</c:if>
 						 	<c:if test="${dto.nstStatus==2 || dto.nstStatus==3}">认证不通过</c:if>
					</td>
				</tr>
				
				<c:if test="${dto.type=='2'}">
				 <tr>
				  <td class="mleft" >
					 公司名称:
					</td>
					<td class="mright">
					${dto.companyName}
					</td>
				 </tr>
				</c:if>
				
				<tr>
					<td class="mleft" >
					身份证号:
					</td>
					<td class="mright">
					${dto.nst_idCard}
					</td>
				</tr> 
				
				<c:if test="${dto.carNumber !=null}">
				<tr>
					<td class="mleft" >
					车牌号码:
					</td>
					<td class="mright">
					${dto.carNumber}
					</td>
				</tr>
				</c:if> 
				
				<tr>
					<td style="font-size:16px;" align="center" colspan="2">
					身份证照片:</td>
				</tr>
				
				<tr>
					<c:choose>
						<c:when test="${dto.nst_cardPhotoUrl != null && dto.nst_cardPhotoUrl !=''}">
						<td  colspan="2" style="font-size:16px;">
						<c:if test="${isBigCardImg ne null}">
						<c:forEach items="${fn:split(dto.nst_cardPhotoUrl, ',')}" var="url">
							<img src="${imgHostUrl}${url}" <c:if test="${isBigCardImg eq true}"> width="500px" height="300px"</c:if> />
						</c:forEach>
						</c:if>
					  <c:if test="${isBigCardImg eq null}">
					  <img src="null" />
					  </c:if>
						</td>
						</c:when>
						<c:otherwise>
						<td class="mleft" colspan="2">
						  图片未上传
					     </td>
						</c:otherwise>
					</c:choose>
					 
				</tr>
		         <tr>
					<td  style="font-size:16px;" align="center" colspan="2">
					营业执照照片:</td>
				 </tr>
				<tr>
					<td class="mleft" colspan="2">
					<c:if test="${isBigBzImg ne null}">
					<c:choose>
						<c:when test="${dto.nst_bzlPhotoUrl != null && dto.nst_bzlPhotoUrl !='' }">
						 <c:if test="${isBigBzImg ne null}">
					     	<img src="${imgHostUrl}${dto.nst_bzlPhotoUrl}"  <c:if test="${isBigBzImg eq true}"> width="500px" height="300px"</c:if> />
					     </c:if>
					      <c:if test="${isBigBzImg eq null}">
					      <img src="null" />
					      </c:if>
						</c:when>
						<c:otherwise>
						图片未上传
						</c:otherwise>
					</c:choose>
					 </c:if>
				    <c:if test="${isBigBzImg eq null}">
					   <img src="null" />
					  </c:if>
					</td>
			   </tr>
			   
			     <tr>
					<td  style="font-size:16px;" align="center" colspan="2">
					行驶证照片:</td>
				 </tr>
				<tr>
				<td class="mleft" colspan="2">
				<c:if test="${isBigVehicleImg ne null}">
				    	<c:choose>
						<c:when test="${dto.nst_vehiclePhotoUrl != null && dto.nst_vehiclePhotoUrl !='' }">
						 <c:if test="${isBigVehicleImg ne null}">
						<img src="${imgHostUrl}${dto.nst_vehiclePhotoUrl}"  <c:if test="${isBigVehicleImg eq true}"> width="500px" height="300px"</c:if> />
						 </c:if>
						 <c:if test="${isBigVehicleImg eq null}">
					     <img src="null" />
					     </c:if>
						</c:when>
						<c:otherwise>
						图片未上传
						</c:otherwise>
					</c:choose>
					</c:if>
				    <c:if test="${isBigVehicleImg eq null}">
					   <img src="null" />
					  </c:if>				</td>
			    </tr>
			   
			     <tr>
					<td  style="font-size:16px;" align="center" colspan="2">
					驾驶证照片:</td>
				 </tr>
				 <tr>
					<td class="mleft" colspan="2">
					<c:if test="${isBigDriverImg ne null}">
					<c:choose>
						<c:when test="${dto.nst_driverPhotoUrl != null && dto.nst_driverPhotoUrl !='' }">
						<c:if test="${isBigDriverImg ne null}">
						<img src="${imgHostUrl}${dto.nst_driverPhotoUrl}" <c:if test="${isBigDriverImg eq true}"> width="500px" height="300px" </c:if> />
						</c:if>
						<c:if test="${isBigDriverImg eq null}">
					     <img src="null" />
				     	</c:if>
						</c:when>
						<c:otherwise>
						图片未上传
						</c:otherwise>
					</c:choose>
					 </c:if>
				    <c:if test="${isBigDriverImg eq null}">
					  <img src="null" />
					</c:if>					</td>
			     </tr>
			   
			</table>
			
		</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/nst_certification/add.js"></script>
