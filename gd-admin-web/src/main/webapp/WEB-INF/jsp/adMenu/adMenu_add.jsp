<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<script type="text/javascript">
	function save() {
		var url=CONTEXT+"adMenu/adMenuSaveAdd";
		if($("#optionType").val()=='update'){
			url=CONTEXT+"adMenu/adMenuSaveUpdate";
		}
		if ($('#editForm').form("validate")) {
			jQuery.post(url, $('#editForm').serialize(), function (data) {
				if (data.msg == "success") {
					var adMenu=data.adMenuDTO;
					slideMessage("操作成功！");
					var node = $('#treeMenu').tree('getSelected');
					if (node){
						if(data.option=='add'){
							$('#treeMenu').tree('append', {
								parent: node.target,
								data: {
									id: adMenu.id,
									text: adMenu.menuName+"("+adMenu.menuSign+")",
									attributes:{
							            "type":1,
							        }, 
								}
							});
						}else if(data.option=='update'){
							$('#treeMenu').tree('update', {
								target: node.target,
								text: adMenu.menuName+"("+adMenu.menuSign+")"
							});

						}else if(data.option=='delete'){
							$('#treeMenu').tree('remove', {
								target: node.target
							});
						}
					}
					$('#editButtonDialog').dialog('close');
				} else {
					warningMessage(data.msg);
					return;
				}
			});
		}
	}
	$(function(){
		if($("#optionType").val()=='view'){
			$("input").each(function(){
				$(this).attr("readonly",true)
			});
			$("#btn-save").hide();
		}else{
			$("#btn-save").show();
		}
	});
</script>
<form id="editForm" method="post" >
<input type="hidden" id="id" name="id" value="${dto.id }">
<input type="hidden" id="fatherId" name="fatherId" value="${dto.fatherId }">
<input type="hidden" id="optionType" name="option" value="${option }">
<div>
	<table style="width: 95%;margin: 10px;">
		<tr>
			<td><b>&nbsp;父级菜单：</b>${fatherName}${dto.fatherName }</td>
			<td></td>
		</tr>
		<tr>
			<td><em style="color:red">*</em><b>菜单标识：</b>
				<input onkeyup="value=value.replace(/[^\da-zA-Z-]+/g,'')" type="text" id="menuSign" name="menuSign" class="easyui-validatebox" style="width: 80%" data-options="required:true" value="${dto.menuSign }"/>
			</td>
		</tr>
		<tr>
			<td><em style="color:red">*</em><b>菜单名称：</b>
				<input type="text" id="menuName" name="menuName" class="easyui-validatebox" style="width: 80%" data-options="required:true,validType:'objectName'" value="${dto.menuName }"/>
			</td>
		</tr>
	</table>
</div>
</form>