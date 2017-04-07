<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>

<form id="auditUnpassForm" method="post" action="certifi/auditUnpass">
		<div>
			<input type="hidden" id="certifiId" name="certifiId"    value="${dto.certifiId}"/>
			<input type="hidden" id="status" name="status"    value="2"/>
			<!-- 审核不通过 -->
			<table>
				<tr>
					<td   >
						驳回原因:
					</td>
					<td>
 				 	<textarea rows="10" id="ngReason" required="true" class="easyui-validatebox" maxlength="200"  cols="100" name="ngReason"></textarea>
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
<script type="text/javascript" src="${CONTEXT}js/certifi/add.js"></script>











