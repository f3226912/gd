<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="../${CONTEXT}css/member/main.css"/>

<form id="auditUnpassForm" method="post" action="nst_certification/auditUnpass">
		<div>
			<input type="hidden" id="certifiId" name="certifiId"    value="${dto.certifiId}"/>
			<input type="hidden" id="memberId"      name="memberId"   value="${dto.memberId}"/>
			<input type="hidden" id="nst_linkMan"    name="nst_linkMan"    value="${dto.nst_linkMan}"/>
			<input type="hidden" id="mobile"         name="mobile"    value="${dto.mobile}"/>
			<input type="hidden" id="companyName"    name="companyName"    value="${dto.companyName}"/>
			<input type="hidden" id="type"           name="type"   value="${dto.type}"/>
			<input type="hidden" id="nstStatus" name="nstStatus"    value="2"/>
			<!-- 审核不通过 -->
			<table>
				<tr>
					<td   >
						驳回原因:
					</td>
					<td>
 				 	<textarea rows="10" id="nst_ngReason" cols="100" name="nst_ngReason" maxlength="200">${dto.nst_ngReason}</textarea>
        			</td>
				</tr>
				 
			</table>
			<div>
			 
			</div>
			<div>
			<div id="#dlg-unpass" style="text-align:center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-back-btn" onclick="auditUnpass();$('#unpassDialog').dialog('close')" >驳回</a>
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#unpassDialog').dialog('close')">关闭</a>
	        </div>
			</div>
 			
			
		</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/nst_certification/add.js"></script>











