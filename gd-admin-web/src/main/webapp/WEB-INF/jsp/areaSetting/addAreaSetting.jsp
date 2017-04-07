<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<form id="addForm" method="post" action="areaSetting/save">
	<div>
		<table style="width: 100%; height: 100%;">
		    <tr>
				<td><input type="hidden" id="id" name="id"
					value="${id}"  style="width: 150px"></td>
		    </tr>
			<tr>
				<td style="width: 100px;"><span style="color: red;">*</span>区域名称:</td>
				<td><input type="text" id="areaName" name="areaName" maxlength="20"
					value="${areaName}" required="true" class="easyui-validatebox"
					missingMessage="必须填写" style="width: 150px"></td>
			</tr>
			
		   
            <tr>
				<td style="width: 100px;"><span style="color: red;">*</span>手机号码:</td>
				<td>
				<input type="text" id="mobile" name="mobile" maxlength="13"
					value="${mobile}"  required="true" class="easyui-validatebox"
					missingMessage="必须填写" style="width: 150px">
				</td>
			</tr>
        
        

			<%-- <tr>
				<td style="width: 100px;">备注:</td>
				<td>
				<input type="text" id="description" name="description" maxlength="50"
					value="${description}"  style="width: 150px">
				</td>
			</tr>
             --%>

		</table>
	</div>
</form>
<script type="text/javascript" src="${CONTEXT}js/areaSetting/areaSetting.js"></script>













