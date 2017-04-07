<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="addForm" method="post" action="promotion/saveTypeInfo">
		<div>
			<table>
				<tr>
					<td>
						 类型名称:
					</td>
					<td>
						<input  type="text" id="name"   maxlength="30" name="name"   value="${dto.name}" required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td>
						 地址:
					</td>
					<td>
						<input  type="text" id="url"   maxlength="120" name="url"   value="${dto.name}" required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
				</tr>
			</table>
			<div>
			</div>
		</div>
</form>








