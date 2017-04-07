<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="editForm" method="post" action="promotion/saveTypeModification">
		<div>
			<input  type="hidden" id="id"   maxlength="30" name="id"   value="${entity.id}"  >
			<table>
				<tr>
					<td>
						 类型名称:
					</td>
					<td>
						<input  type="text" id="name"   maxlength="30" name="name"   value="${entity.name}" required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
				</tr>
				<%-- <tr>
					<td>
						 地址:
					</td>
					<td>
						<input  type="text" id="url"   maxlength="120" name="url"   value="${entity.url}" required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
				</tr> --%>
			</table>
			<div>
			</div>
		</div>
</form>
