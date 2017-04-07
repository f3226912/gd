<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<form id="addForm" method="post" action="demo/save">
		<div>
			
			id:<input type="text" id="id"  required="true" class="easyui-validatebox" missingMessage="id必须填写"   name="id" value="${id}"/>
		           名称: 
			<input  type="text" id="name"  value="${name}" required="true" class="easyui-validatebox" missingMessage="name必须填写"   name="name" style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			生日：
			<input  type="text"  id="birthday" value="${birthday}"   name="birthday" required="true"    class="easyui-datebox"    style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;
			 
			
		</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/demo/add.js"></script>











