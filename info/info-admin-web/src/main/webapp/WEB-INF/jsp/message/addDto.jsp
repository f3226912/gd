<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%-- <%@ include file="../pub/head.inc" %> --%>
<%@ include file="../pub/tags.inc" %>
<form id="addForm" method="post" action="message/save">
<table class="grid" align="center">
	<tr>
		<td align="right"><em style="color:red">*</em><b>消息名称：</b></td>
		<td>
			<input type="text" id="title" name="title" class="easyui-validatebox" size="38" data-options="required:true,validType:'inputName'" maxlength="50" value="${dto.title }" placeholder="输入文本"/>
		</td>
	</tr>
	<tr>
		<td align="right"><b>备注：</b></td>
		<td>
			<textarea id="content" name="content" rows="10" cols="40" maxlength="2000" placeholder="最多输入2000个字符">${dto.content }</textarea>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
var action='${action}';
if(action=='add'){
	$("#icon-save-btn").show();
}else{
	$("#icon-save-btn").hide();
}

</script>
