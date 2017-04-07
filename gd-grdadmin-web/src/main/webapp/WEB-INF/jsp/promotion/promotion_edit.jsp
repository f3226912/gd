<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="addForm" method="post" action="promotion/save">
		<div>
			
			<input type="hidden" id="id" name="id"    value="${dto.id}"/>
			<table>
				<tr>
					<td   >
						 渠道名称:
					</td>
					<td  >
						<input  type="text" id="name"   maxlength="20" name="name"   value="${dto.name}" required="true" class="easyui-validatebox"  style="width:150px" >
					
					</td>
				</tr>
			</table>
			<div>
			 
			</div>
		</div>
</form>
<script >

</script>









