<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc"%>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc"%>

<form id="editForm" method="post" action="complaint/replyComplaintSave">
<table>
		<input type="hidden" id="id" name="id" value="${id}" />
			<tr>
			<td align="center" height="200">投诉内容：</td><td><textarea name="replyComtent" rows="10" cols="50">${content }</textarea></td>
			</tr>
			</table>
</form>

<script type="text/javascript" >

</script>
