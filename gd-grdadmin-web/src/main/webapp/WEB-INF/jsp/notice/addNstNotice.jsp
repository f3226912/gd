<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<div style="margin: 0 auto;font-size: 17px" >
	<form action="" name="" id="addForm">
		<div style="text-align:left;">
		<font color="red">*</font>公&nbsp;&nbsp;&nbsp;&nbsp;告:<br>
			<textarea rows="10" cols="28" style="margin-left: 60px ;margin-bottom: 10px;" name="notice" maxlength="200" id="notice"></textarea><br>
		<font color="red">*</font>开始时间：<input  type="text"  id="startTime" name="startTime"  
				onFocus="WdatePicker({onpicked:function(){startTime.focus();},minDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){startTime.focus();},minDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:185px"><br/><br/>
		<font color="red">*</font>结束时间：<input  type="text"  id="endTime" name="endTime"  
				onFocus="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd'})"   
				onClick="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd'})"    
				style="width:185px" >
		</div>
	</form>
</div>
