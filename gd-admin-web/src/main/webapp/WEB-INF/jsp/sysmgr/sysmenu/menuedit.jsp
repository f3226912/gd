<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../pub/tags.inc" %>
<script type="text/javascript">
	$(function(){
// 		$('#editForm #menuModuleID').combobox({    
// 		    url:CONTEXT+'sysmgr/sysMenu/getFirstMenu',    
// 		    valueField:'id',    
// 		    textField:'text',
// 		    editable:false,
// 		    value:'${dto.menuModuleID}'
// 		});  
	})

	function save() {
		var url=CONTEXT+"sysmgr/sysMenu/${actionUrl}";
		if ($('#editForm').form("validate")) {
			jQuery.post(url, $('#editForm').serialize(), function (data) {
				if (data == "success") {
					slideMessage("操作成功！");
					//刷新父页面列表
					$("#dg").datagrid('reload');
					$('#editDialog').dialog('close');
				} else {
					warningMessage(data);
					return;
				}
			});
		}
	}
	
	
	function getChildMenu(menuID,sndMenuID){
		$.ajax({
			type: "GET",
			url: CONTEXT+"sysmgr/sysMenu/getChildMenu/"+ menuID,
			dataType: "json",
			success: function(data) {
				var menus=data.rows;
				if (menus.length != 0) {
					$('#secondMenuID').empty();
					$('#secondMenuID').append("<option value=''>==请选择==</option>");
					for ( var n = 0; n < menus.length; n++) {
						if(sndMenuID==menus[n].id){
							$('#secondMenuID').append("<option selected='selected' value='"+menus[n].id+"'>"+menus[n].menuName+"</option>");
						}else{
							$('#secondMenuID').append("<option value='"+menus[n].id+"'>"+menus[n].menuName+"</option>");
						}
					}
				}
			}
		});
	}

	//显示出二级菜单
	function listSecondMenu(menuID,sndMenuID){
		if(menuID){
			getChildMenu(menuID,sndMenuID);
			$("#tr_secondMenuID").show();
		}else{
			$("#tr_secondMenuID").hide();
		}
	}
</script>
<form id="editForm" method="post" >
<input type="hidden" id="menuID" name="menuID" value="${dto.menuID }">
<br/>
<div>
	<table class="grid" align="center" style="width: 80%">
		<tr>
			<td align="right"><em style="color:red">*</em><b>菜单编号：</b></td>
			<td>
				<input type="text" id="menuCode" name="menuCode" class="easyui-validatebox" style="width: 100%" data-options="required:true,validType:'enmath'" value="${dto.menuCode }"/>
			</td>
		</tr>
		<tr>
			<td align="right"><em style="color:red">*</em><b>菜单名称：</b></td>
			<td>
				<input type="text" id="menuName" name="menuName" class="easyui-validatebox" style="width: 100%" data-options="required:true,validType:'objectName'" value="${dto.menuName }"/>
			</td>
		</tr>
		<tr>
			<td align="right"><b>一级菜单：</b></td>
			<td>
				<select id="menuModuleID" name="menuModuleID" onchange="listSecondMenu($(this).val())" style="width: 100%">
					<option value="">==请选择==</option>
					<c:forEach items="${baseMenuList}" var="menu" varStatus="status">
						<c:if test="${menu.level==1}">
							<option value="${menu.menuID }" <c:if test="${menu.menuID==firstMenu.menuID && dto.level!=1}">selected="selected"</c:if>>${menu.menuName}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr id="tr_secondMenuID" style="display: none;">
			<td align="right"><b>二级菜单：</b></td>
			<td>
				<select id="secondMenuID" name="secondMenuID" style="width: 100%">
					<option value="">==请选择==</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><b>菜单Url：</b></td>
			<td>
				<input type="text" id="actionUrl" name="actionUrl" class="easyui-validatebox" maxlength="200" style="width: 100%" value="${dto.actionUrl }"/>
            </td>
		</tr>
		<tr>
			<td align="right"><b>序号：</b></td>
			<td>
				<input type="text" id="sort" name="sort" class="easyui-validatebox easyui-numberbox" maxlength="200" style="width: 100%" value="${dto.sort}"/>
            </td>
		</tr>
	</table>
		<div style="text-align: center;"><p>说明：1 序号越大，菜单排列越靠前</p></div>
</div>
</form>
<script>
var menuLevel="${dto.level}";
var menuId="${dto.menuID}";
var firstMenuLevel="${firstMenu.level}";
var firstMenuId="${firstMenu.menuID}";
var sndMenuLevel="${sndMenu.level}";
var sndMenuId="${sndMenu.menuID}";
if(menuLevel==3){
	getChildMenu(firstMenuId,sndMenuId);
	$("#tr_secondMenuID").show();
}
if(menuLevel==2){
	getChildMenu(firstMenuId,null);
	$("#tr_secondMenuID").show();
}
</script>