<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">

	.mleft{
		font-size:16px;
		text-align: right;
		valign:middle;
		width:150px;
	}
	
	.mright{
		font-size:16px;
	    align:left;
	    valign:middle;
	}
</style>

<form id="addForm" method="post" action="gift/save">
 					<div>
 			<input type="hidden" id="id" name="id"    value="${dto.id}"/>
			<table>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>礼品名称:
					</td>
					<td class="mright">
						<input  type="text" id="name"  name="name"   value="${dto.name}" required="true" maxlength="20" class="easyui-validatebox"  style="width:150px" missingMessage="必须填写">
					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>礼品积分:
					</td>
					<td class="mright">
						<input  type="text" id="integral"  name="integral"   value="${dto.integral}" required="true" maxlength="20" class="easyui-validatebox"  style="width:150px" missingMessage="必须填写">
					</td>
				</tr>
				<tr>
					<td class="mleft"  >
						<span style="color: red;">*</span>状态:
					</td>
					<td class="mright">
 					 <input type="radio" name="type"    value="1">有效奖品
 					&nbsp;
 					  <input type="radio" name="type"   value="2">失效奖品
 					&nbsp;
 					  <input type="radio" name="type"   value="3">推荐码积分
 					&nbsp;
 					  <input type="radio" name="type"   value="4">车辆积分
 					</td>
				</tr>
				<tr id="activity">
					<td class="mleft"  >
					<span style="color: red;">*</span>活动：
					</td>
					<td class="mright">
 						<input type="button" id="btn-activity-select" value="选择活动"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="hidden" id="activityId" value="" name="activityId"/>

 					</td>
				</tr>			 
			</table>
 	</div>	
 	
 	<script type="text/javascript" >
 	$('#btn-activity-select').click(function(){
		$('#activityDialog').dialog({'title':'选择活动', 'width':700, 'height':300, 'href':CONTEXT+'gift/activitySelect'}).dialog('open');
	});
 	
 	function activitySelectCallback(id, name){
 		$("#activityId").val(id);
 		$("#btn-activity-select").val(name);
 		$('#activityDialog').dialog("close");
 	}
 	</script>
</form>
