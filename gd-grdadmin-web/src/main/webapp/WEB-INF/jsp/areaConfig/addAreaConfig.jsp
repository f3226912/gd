<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="addForm" method="post" action="areaConfig/save">
	<div>
		<table style="width: 100%; height: 100%;">
		    <tr>
				<td><input type="hidden" id="id" name="id"
					value="${id}"  style="width: 150px"></td>
		    </tr>
		    <tr>
				<td style="width: 100px;"><span style="color: red;">*</span>城市:</td>
				<td><input type="text" id="areaName" name="areaName" maxlength="20" 
				    <c:if test="${id >0}"> readonly="readonly" </c:if>
					value="${areaName}" required="true" class="easyui-validatebox"
					missingMessage="必须填写" style="width: 150px"></td>
			</tr>
           
          	<tr>
				<td style="width: 100px;"><span style="color: red;">*</span>状态:</td>
				<td>
				<input type="radio" name="status"   <c:if test="${status=='0' || status == null}"> checked="checked" </c:if>  value="0" required="true" class="easyui-validatebox"> 启用
				<input type="radio" name="status"   <c:if test="${status=='1'}"> checked="checked" </c:if>  value="1" required="true" class="easyui-validatebox"> 停用
				</td>
			</tr>

		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/areaConfig/areaConfig.js"></script>














