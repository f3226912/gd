<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div style="width:300px; height:100px; margin:20px auto">
	<form id="editActGiftForm" action="actGift/saveEdit" method="post">
		<input type="hidden" name="id" value="${dto.id }">
		<table>
			<tr>
				<td>礼品名称：</td>
				<td><input type="text" name="name" value="${dto.name }" class="easyui-validatebox" required="true" 
					missingMessage="礼品名称不能为空" validType="length[1,20]"></td>
			</tr>
			</tr>
				<td>库存：</td>
				<td><input type="text" name="stockTotal" value="${dto.stockTotal }" class="easyui-validatebox" required="true" 
					missingMessage="库存不能为空" validType="stock" invalidMessage="只能输入整数且最大允许输入值为999999"></td>
			</tr>
		</table>
	</form>
</div>