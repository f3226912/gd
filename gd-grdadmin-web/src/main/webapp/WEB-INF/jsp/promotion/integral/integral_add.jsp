<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../../pub/constants.inc" %>
<%@ include file="../../pub/head.inc" %>

<form id="addForm" method="post" action="activityintegral/save">
		<div>
			<input type="hidden" id="id" name="id"    value="${dto.id}"/>
			<table>
				<tr>
					<td   >
						 用户账户:
					</td>
						<td width="200" align="left">
								<input type="button" id="showMemberDailog" required="true"  value="搜索">
								<input type="hidden" id="memberAccount" name="memberAccount"  value="" >
						</td>
				</tr>
				<tr>
					<td>
						 活动编号:
					</td>
					<td  >
						<input  type="text" id="activityId"   maxlength="20"  name="activityId"  required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
				</tr>
				<tr>
					<td>
						 获得积分:
					</td>
					<td  >
						<input  type="text" id="integralNum"   maxlength="20" name="integralNum"    required="true" class="easyui-validatebox"  style="width:150px" >
					</td>
				</tr>
			</table>
			<div id="a-save">
	        	<a href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-save'"  id="icon-save-btn"   type="submit"   onclick="saveIntegral()">保存</a>
	            <a class="easyui-linkbutton icon-reload-btn"  iconCls="icon-reload"  onclick='$("#addForm")[0].reset()'>重置</a>
	         </div>
		</div>
</form>

<div id="memberDailog" class="easyui-dialog"  style="width:600px;height:220px;"  closed="true" modal="true" buttons="#dlg-buttonsMember">
	<div id="#dlg-buttonsMember" style="text-align:center">
	      <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="icon-market-btn" >确定</a>
	      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#memberDailog').dialog('close')">关闭</a>
	</div>
</div>
<script type="text/javascript" >

$('#showMemberDailog').click(function(){
	$('#memberDailog').dialog({
			'title' : '选择用户',
			'width' : 700,
			'height' : 300,
			'href' : CONTEXT + 'activityintegral/memberSelect'
		}).dialog('open');
	});
	
function saveIntegral() {
	var url=CONTEXT+"activityintegral/save";
	jQuery.post(url, $('#addForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#businessdg").datagrid('reload');
			$('#addDialog').dialog('close');
			$('#editDialog').dialog('close');
		} else {
	    	return false;
		}
	});
}		
</script>
