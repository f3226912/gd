<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div style="width:300px; height:100px; margin:20px auto">
	<table>
		<tr>
			<td>礼品名称：</td>
			<td><input type="text" name="name" value="${dto.name }" disabled="disabled"></td>
		</tr>
		<tr>
			<td>已发放礼品数 ：</td>
			<td><input type="text" name="countApply" value="${dto.countApply }" disabled="disabled"></td>
		</tr>
		<tr>
			<td>库存：</td>
			<td><input type="text" name="stockTotal" value="${dto.stockTotal }" disabled="disabled"></td>
		</tr>
		<tr>
			<td>可用库存：</td>
			<td><input type="text" name="stockAvailable" value="${dto.stockAvailable }" disabled="disabled"></td>
		</tr>
		<tr>
			<td>创建时间：</td>
			<td><input type="text" name="createTime" value="<fmt:formatDate value='${dto.createTime }' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled="disabled"></td>
		</tr>
		<tr>
			<td>创建人：</td>
			<td><input type="text" name="createUserName" value="${dto.createUserName }" disabled="disabled"></td>
		</tr>
	</table>
</div>