<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/tags.inc" %>
<script type="text/javascript">
	$(function(){
		//用户类型
		$('#editForm #type').combobox({    
		    url:CONTEXT+'sysmgr/sysRegisterUser/getType',    
		    valueField:'id',    
		    textField:'text',
		    editable:false,
		    required:true,
		    onLoadSuccess: function () { 
		    	//数据加载完毕事件
		    	if("${dto.userID}"==""){
		    		 var data = $('#editForm #type').combobox('getData');
		                if (data.length > 0) {
		                    $('#editForm #type').combobox('setValue', data[0].id);
		                }
		    	}
		    	var comVal=$('#editForm #type').combobox('getValue');
		    	//是否验证组织
		    	isCheckOrg(comVal);
            },
            onChange:function(newValue,oldValue){
            	//是否验证组织
		    	isCheckOrg(newValue);
            }
		});  
		
	});
	
	//是否验证组织
	function isCheckOrg(type){
		if(type=="0"){
			//不验证
			$('#editForm #orgButton').linkbutton('disable');
    		$("#editForm #orgUnitName").validatebox('disableValidation');
		}else{
			//验证
			$('#editForm #orgButton').linkbutton('enable');
    		$("#editForm #orgUnitName").validatebox('enableValidation');
		}
	}
	
	function save() {
		var url=CONTEXT+"sysmgr/sysRegisterUser/${actionUrl}";
		if ($('#editForm').form("validate")) {
			jQuery.post(url, $('#editForm').serialize(), function (data) {
				if (data == "success") {
					slideMessage("操作成功！");
					//刷新父页面列表
					$("#dg").datagrid('reload');
					$('#editDialog').dialog('close');
				} else {
					if(data=='sameUser'){
						warningMessage('该用户帐号已作废或已被占用！');
					}else if(data=='fail'){
						warningMessage('操作失败！');
					}
					return;
				}
			});
		}
	}
	
	//选择组织
	function choseOrg(){
		var type=$('#editForm #type').combobox('getValue');
		$('#choseOrgDialog').dialog({'title':'所属组织','href':CONTEXT+'sysmgr/sysRegisterUser/choseOrgInit?type='+type}).dialog('open');
	}
</script>
<form id="editForm" method="post" >
<input type="hidden" id="userID" name="userID" value="${dto.userID }">
<br/>
<div>
	<table class="grid" align="center">
		<tr>
			<td align="right"><em style="color:red">*</em><b>用户帐号：</b></td>
			<td>
				<c:if test="${empty dto.userID }">
				<input type="text" id="userCode" name="userCode" class="easyui-validatebox" data-options="required:true,validType:'loginName'" maxlength="10" value="${dto.userCode }"/>
				</c:if>
				<c:if test="${not empty dto.userID }">
				${dto.userCode }
				</c:if>
			</td>
		</tr>
		<tr>
			<td align="right"><em style="color:red">*</em><b>用户名称：</b></td>
			<td>
				<input type="text" id="userName" name="userName" class="easyui-validatebox" data-options="required:true,validType:'objectName'" maxlength="10" value="${dto.userName }"/>
			</td>
		</tr>
		<tr>
			<td align="right"><b>联系电话：</b></td>
			<td>
				<input type="text" id="mobile" name="mobile" class="easyui-validatebox" maxlength="11" value="${dto.mobile }"/>
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<td align="right"><em style="color:red">*</em><b>类型：</b></td> -->
<!-- 			<td> -->
<%-- 				<c:if test="${empty dto.userID }"> --%>
<!-- 					0系统，1联采中心，2配送站，3学校，4食堂，5基地 -->
<!-- 					<input type="text" id="type" name="type"/>  -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${not empty dto.userID }"> --%>
<%-- 					${dto.typeName } --%>
<%-- 				</c:if> --%>
<!--             </td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td align="right"><b>所属组织：</b></td> -->
<!-- 			<td> -->
<%-- 				<c:if test="${empty dto.userID }"> --%>
<!-- 					<input type="text" id="orgUnitName" name="orgUnitName" class="easyui-validatebox" data-options="required:true" readonly="readonly"/> -->
<%-- 					<input type="hidden" id="orgUnitId" name="orgUnitId" value="${dto.orgUnitId }"/> --%>
<!-- 					<a class="easyui-linkbutton" id="orgButton" iconCls="icon-add" onclick="choseOrg()">选择</a> -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${not empty dto.userID }"> --%>
<%-- 					${dto.orgUnitName } --%>
<%-- 				</c:if> --%>
<!--             </td> -->
<!-- 		</tr> -->
		<c:if test="${empty dto.userID }">
			<tr>
				<td align="right"><em style="color:red">*</em><b>用户密码：</b></td>
				<td>
					<input type="password" id="passWord" name="passWord" class="easyui-validatebox" data-options="required:true,validType:'password'" maxlength="16" value="${dto.userPassWord }"/>
				</td>
			</tr>
		</c:if>
	</table>
</div>
</form>