<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="addForm" method="post" action="">
	<div>
		<table>
			<tr>
			<input type="hidden" name="state" value="2">
				<td><font color="red">*</font> &nbsp;消息名称:</td>
				<td>
					<input type="text" id="addTitle" required="true" class="easyui-validatebox" missingMessage="消息名称不能为空"
					validType="unnormal" name="title" maxlength="20" style="width: 350px">&nbsp;
				</td>
			</tr>
			
			<tr>
				<td><font color="red">*</font>&nbsp;消息发送对象:</td>
				<td>
				
					<input type="checkbox" name="addClient" value="1">农批商&nbsp;
					<input type="checkbox" name="addClient"  value="2">供应商&nbsp;
					<input type="checkbox" name="addClient"  value="3">农速通&nbsp;
					<input type="checkbox" name="addClient"  value="4">农商友&nbsp;
					<input type="checkbox" name="addClient"  value="5">谷登农批网&nbsp;
					
					<input type="hidden" name="client" id="addClients">
				</td>
			</tr>
			<tr>
				<td><font color="red">*</font>&nbsp;编辑信息内容:</td>
				<td>
					 <textarea name="content" id="addContent" style="width:680px;height:290px;visibility:hidden;"></textarea>
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
	
});

var getAddClients = function(){
	var clientObjs = $('input[name="addClient"]:checked');
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
//提交新增数据
function saveadd() {
	//var clienti,client;
	//client = document.getElementsByName("client");
	var content =$("#addContent").val();
	if(!$("#addForm").form('validate')){
		return $("#addForm").form('validate');
	}
	var clients = getAddClients();
	if(!clients){
		warningMessage("请选择消息发送对象!");
		return false;
	}else{
		$("#addClients").val(clients);
	}
	/*for (clienti = 0; clienti < client.length; clienti ++){
		if(client[clienti].checked)break;
	}
	if(clienti >= client.length){
		warningMessage("请选择消息发送对象!");
		return false;
	}*/
	if(length ==0 && !content){
		warningMessage("请编辑信息内容!");
		return false;
	}else if(length >1000){
		warningMessage("信息内容不能超过1000字!");
		return false;
	}
	var url=CONTEXT+"push/noticeSaveAdd";
	jQuery.post(url, $('#addForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#noticedg").datagrid('reload');
			$('#addDialog').dialog('close');
			$('#noticedg').datagrid("uncheckAll");
		} else {
			warningMessage(data);
			return;
		}
	});
}
</script>