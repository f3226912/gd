<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<style>
td{
  padding-left: 10px;
}
</style>
<input type="hidden" id="id" name="id" value="${dto.id}" />
<div>
	<table style="border: none;width: 100%;">
		<tr>
			<td>id<span style="color: red">*</span></td>
			<td><input type="text" id="id" name="id" value="${dto.id}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>创建时间<span style="color: red">*</span></td>
			<td>
				<input type="text" id="createTime" name="createTime" 
				value='<fmt:formatDate value="${dto.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'
				 readonly="readonly" style="width: 90%;">
			 </td>
		</tr>
		<tr>
			<td>更新时间<span style="color: red">*</span></td>
			<td>
				<input type="text" id="updateTime" name="updateTime" 			
				value='<fmt:formatDate value="${dto.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'
	 			readonly="readonly" style="width: 90%;">
 			</td>
		</tr>
		<tr>
			<td>敏感词<span style="color: red">*</span></td>
			<td><input type="text" id="name" name="name" value="${dto.name}" readonly="readonly" style="width: 90%;"></td>
		</tr>
		<tr>
			<td>说明<span style="color: red">*</span></td>
			<td>
				<textarea  id="description" name="description"  required="true" class="easyui-validatebox" validType="unnormal" missingMessage="必填" style="width: 90%;">${dto.description}</textarea>
			</td>		
		</tr>
	</table>
</div>
