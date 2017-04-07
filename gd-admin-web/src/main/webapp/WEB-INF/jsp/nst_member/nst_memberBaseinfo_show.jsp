<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../pub/tags.inc" %>
 <link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>
     		<div>
			<table>
				<tr>
					<td class="mleft"  >
						注册账号:
					</td>
					<td class="mright">
						${dto.account}
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
						真实姓名:
					</td>
					<td class="mright">
					${dto.realName}
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
						用户类型:
					</td>
					<td class="mright">
						${dto.userTypeName}
					</td>
				</tr>
				
				<tr>
					<td class="mleft" >
						认证状态:
					</td>
					<td class="mright">
						${dto.nstStatus }
					</td>
				</tr>
				
			    <tr>
						<td class="mleft" >
						激活状态:
						</td>
						<td class="mright">
							${dto.accountStatus}
						</td>
				</tr>
				
				<tr>
					<td class="mleft" >
						个人/企业:
					</td>
					<td class="mright">
						${dto.nstUserType}
					</td>
				</tr>
				
			    <tr>
					<td class="mleft" >
					手机号:</td>
					<td class="mright">
					${dto.mobile}
					</td>
				</tr>
				
				<tr>
					<td  class="mleft">
					常用城市：
					</td>
					<td class="mright">
					${dto.cityName}
					</td>
				</tr>
				
				<tr>
					<td  class="mleft">
					所属区域：
					</td>
					<td class="mright">
					${dto.areaName}
					</td>
				</tr>
				
				<tr>
					<td  class="mleft">
					推荐人姓名：
					</td>
					<td class="mright">
					${dto.refereeRealName}
					</td>
				</tr>
				<tr>
					<td  class="mleft">
					推荐人手机号：
					</td>
					<td class="mright">
						${dto.refereeMobile}
					</td>
					<c:if test="${dto.refereeMobile !=null && dto.refereeMobile != ''}">
						<td class="mright">
							<gd:btn btncode='BTNWLHYGLHYGL04'>
								<button id="btnEdit" onclick="btnEdit();">修改</button>
							</gd:btn>
						</td>
					</c:if>
				</tr>
				
			</table>
		</div>
		<div id="eidtDialog" class="easyui-dialog"  closed="true" modal="true" buttons="#dlg-buttonsSave">
			<div id="dlg-buttonsSave" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-save-btn" onclick="save()">确认</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#eidtDialog').dialog('close')">取消</a>
	        </div>
		</div>
		<script type="text/javascript">
			function btnEdit(){
				$('#eidtDialog').dialog({'title':'修改推荐人手机号码','href':CONTEXT+'nst_member/editMobileByid/'+'${dto.memberId}'+'-'+'${dto.refereeMobile}','width': 300,'height': 200}).dialog('open');
			}
		</script>
		
