<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="editForm" method="post" action="">
	<div>
		<input type="hidden" id="id" name="id" value="${dto.id}" />
		<table>
			<tr>
				<td><font color="red">*</font>&nbsp;消息名称:</td>
				<td><input type="text" id="editTitle" required="true" class="easyui-validatebox" missingMessage="消息名称不能为空"
					validType="unnormal" value="${dto.title}" name="title" maxlength="50" style="width: 350px">&nbsp;<font color="red">*</font>
					</td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>&nbsp;消息发送对象:</td>
				<td>
					<input type="checkbox" name="editClient" value="1">农批商&nbsp;
					<input type="checkbox" name="editClient" value="2">供应商&nbsp;
					<input type="checkbox" name="editClient" value="3">农速通&nbsp;
					<input type="checkbox" name="editClient"  value="4">农商友&nbsp;
					<input type="checkbox" name="editClient" value="5">谷登农批网&nbsp;
					<input type="hidden" name="client" id="editClients">
				</td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>&nbsp;编辑信息内容:</td>
				<td>
					<textarea name="content" id="editContent" style="width:680px;height:290px;visibility:hidden;display: block;">${dto.content}</textarea>
				</td>
			</tr>
			
		</table>
		<div></div>
		<div></div>
	</div>
</form>
<script type="text/javascript" >
var length=0;
$(document).ready(function(){
	KindEditor.create('textarea[name="content"]', {
		cssPath : CONTEXT+'js/kindeditor-4.1.10/plugins/code/prettify.css',
		uploadJson: CONTEXT+'js/kindeditor-4.1.10/jsp/upload_json.jsp',
		fileManagerJson : CONTEXT+'js/kindeditor-4.1.10/jsp/file_manager_json.jsp',
		allowFileManager: true,
		allowImageUpload : true,
		//把编辑器里的内容同步到textarea @_@ @_@ @_@  
		afterBlur: function(){
			length = this.count("text");
			this.sync();}
	});
	
	var client = '${dto.client}';
	var clientname = document.getElementsByName("editClient");
	if(client.indexOf(",") > 0){
		var clients = client.split(",");
		for(var i = 0; i < clients.length; i ++){
			for(var j = 0; j < clientname.length; j ++){
				if(clients[i] == clientname[j].value){
					clientname[j].checked = "checked";
				}
			}
		}
	}else{
		for(var j = 0; j < clientname.length; j ++){
			if(client == clientname[j].value){
				clientname[j].checked = "checked";
			}
		}
	}
});
var getEditClients = function(){
	var clientObjs = $('input[name="editClient"]:checked');
	var len = clientObjs.length ;
	var clients ;
	if (len > 0){
		clients = clientObjs[0].value;
	}else {
		return "";
	}
	for(var i = 1; i < len; i++){
		clients += "," + clientObjs[i].value;
	}
	return clients;
};
//提交更新数据
function saveedit() {
	//var clienti,client;
	//client = document.getElementsByName("client");
	var content = $("#editContent").val();

	if(!$("#editForm").form('validate')){
		return $("#editForm").form('validate');
	}
	/*for (clienti = 0; clienti < client.length; clienti ++){
		if(client[clienti].checked)break;
	}
	if(clienti >= client.length){
		warningMessage("请选择消息发送对象!");
		return false;
	}
	*/
	var clients = getEditClients();
	if(!clients){
		warningMessage("请选择消息发送对象!");
		return false;
	}else{
		$("#editClients").val(clients);
	}
	if(length ==0 && !content){
		warningMessage("请编辑信息内容!");
		return false;
	}else if(length >1000){
		warningMessage("信息内容不能超过1000字!");
		return false;
	}
	
	var url=CONTEXT+"push/updateNoticeInfo";
	jQuery.post(url, $('#editForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#noticedg").datagrid('reload');
			$('#editDialog').dialog('close');
			$('#noticedg').datagrid("uncheckAll");
		} else {
			warningMessage(data);
			return;
		}
	});
}
</script>










