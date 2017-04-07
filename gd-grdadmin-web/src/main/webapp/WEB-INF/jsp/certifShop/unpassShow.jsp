<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="auditUnpassForm" method="post" action="certifShop/auditUnpass">
	<input type="hidden" id="id" name="id"    value="${dto.id}"/>
	<input type="hidden" id="status" name="status"    value="2"/>
	<table >
		<tr>
			<td>
				驳回原因:
			</td>
			<td>
			 	<textarea rows="10" id="reason" required="true" 
			 	class="easyui-validatebox" maxlength="50"  
			 	cols="50" name="reason"></textarea>
			</td>
		</tr>
	</table>
</form>











