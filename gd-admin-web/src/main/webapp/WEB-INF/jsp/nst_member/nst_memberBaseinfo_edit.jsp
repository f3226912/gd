<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <link rel="stylesheet" type="text/css" href="${CONTEXT}css/member/main.css"/>
 	<form id="eidtForm">
		<div style="margin-left:10px">
			<table style="border-collapse:separate; border-spacing:10px;">
				<tr>
					<td><span style="font-size:14px">请输入推荐人手机号</span></td>
				</tr>
				<tr>
					<td>
						<input type="text" id="phone" name="phone" maxlength="11"
						value="${mobile}" required="true" class="easyui-validatebox"
						missingMessage="必须填写" style="width: 150px"/>
					</td>
				</tr>
			</table>
			<input id="memberId" name="memberId" type="hidden" value="${id}"/>
		</div>
	</form>	
<script type="text/javascript">

	function save() {
		if(!$("#eidtForm").form('validate')){
			return $("#eidtForm").form('validate');
		}
		if(checkData())
		{
		 var url=CONTEXT+"nst_member/save";
			jQuery.post(url, $('#eidtForm').serialize(), function (data) {
				if (data == "success") {
					slideMessage("操作成功！");
					$('#eidtDialog').dialog('close');
					$('#showDialog').dialog('close');
					$('#showDialog').dialog({'title':'会员信息','href':CONTEXT+'nst_member/showbyid/'+'${id}','width': 550,'height': 350}).dialog('open');
				}else if (data == "not exist") {
					warningMessage("您输入的推荐人手机号无效！");
					return;
				 } else{
					warningMessage("系统异常！");
					return;
				}
			});
		}
	}	
	function checkData(){  
		var phoneReg=/^(1[0-9][0-9])\d{8}$/;
		var phone=$("#eidtForm #phone").val();
		if (!phoneReg.test(phone)) {
			warningMessage("输入的手机号码格式不正确!");
			return false;
		}	
		return true;	
	}	
</script>
		
