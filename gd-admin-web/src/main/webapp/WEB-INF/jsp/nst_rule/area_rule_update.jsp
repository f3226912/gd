<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="margin-top: 30px;">
		<form action="" name="assignForm" id="assignForm">
		<input type="hidden" name="id" id="id1" value="${nstDto.id}" />
	 	<font style="color:red">*</font> 当日分配上限(条)： <input type="text" id="dayAssignMax1" name="dayAssignMax" value="${nstDto.dayAssignMax}" class="easyui-validatebox"  validType="integer" maxlength="10"/><br><br>
	 	<font style="color:red">*</font> 当月分配上限(条)： <input type="text" id="monthAssignMax1" name="monthAssignMax" value="${nstDto.monthAssignMax}"  class="easyui-validatebox"  validType="integer" maxlength="10"/><br><br>
	 	<font style="color:red">*</font> 分配有效开始时间：<input  type="text"  id="assignStartTime1" name="assignStartTime"  value="${nstDto.assignStartTime}" 
				onFocus="WdatePicker({onpicked:function(){assignStartTime1.focus();},maxDate:'#F{$dp.$D(\'assignEndTime1\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){assignStartTime1.focus();},maxDate:'#F{$dp.$D(\'assignEndTime1\')}',dateFmt:'yyyy-MM-dd'})"><br><br>
		<font style="color:red">*</font> 分配有效结束时间：<input  type="text"    id="assignEndTime1" name="assignEndTime" value="${nstDto.assignEndTime}"   
				onFocus="WdatePicker({onpicked:function(){assignEndTime1.focus();},minDate:'#F{$dp.$D(\'assignStartTime1\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){assignEndTime1.focus();},minDate:'#F{$dp.$D(\'assignStartTime1\')}',dateFmt:'yyyy-MM-dd'})">
		</form>
</div>
<script type="text/javascript">
$(function(){
	$.extend($.fn.validatebox.defaults.rules, { 
		integer: { 
		validator: function(value){ 
		return /^\d+$/.test(value); 
		}, 
		message: '请输入大于等于0的整数' 
		} 
		}); 
});
function btnsaveupdate(){
	var dayAssignMax=$('#dayAssignMax1').val();
	var monthAssignMax=$('#monthAssignMax1').val();
	var assignStartTime=$('#assignStartTime1').val();
	var assignEndTime=$('#assignEndTime1').val();
	var a=parseInt(dayAssignMax);
	var b=parseInt(monthAssignMax);
	if(dayAssignMax!='' && monthAssignMax!='' && a>b){
		warningMessage("当日上限不能超过当月上限");
		return;
	}
	if(dayAssignMax=='' || monthAssignMax=='' || assignStartTime=='' || assignEndTime=='' ){
		warningMessage("请将分配规则填全，谢谢!");
		return;
	}
	params={
			"id":$('#id1').val(),
			"dayAssignMax":dayAssignMax,
			"monthAssignMax":monthAssignMax,
			"assignStartTime":assignStartTime,
			"assignEndTime":assignEndTime
	}
	var url=CONTEXT + "nstRule/update";
	jQuery.post(url, params, function(data) {
		if (data == "success") {
			slideMessage("保存成功！");
			$("#nstGoodAssignRuledg").datagrid('reload');
			$('#updateDialog').dialog('close');
		} else {
			warningMessage("保存失败！");
			return;
		}
	}); 
};
</script>