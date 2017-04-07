<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=7, IE=9, IE=10, IE=11, IE=12"/>
		<title>农速通订单评论</title>
	</head>
	  <script type="text/javascript" src="${CONTEXT}js/My97DatePicker/WdatePicker.js"></script> 
<body>
	<table id="nstOrderCommentdg" title="">
	</table>
	<div id="nstOrderCommenttb" style="padding:5px;height:auto">
		<form id="nstOrderCommentSearchForm" method="post">
		<div>
			运单号: 
			<input  type="text" id="orderNo"    name="orderNo"  class="easyui-validatebox"    style="width:150px" >&nbsp;&nbsp;
			
			 发运人: 
			<input  type="text" id="name1"    class="easyui-validatebox"    name="name1" style="width:150px" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
			 发运人手机号码: 
			<input  type="text" id="mobile1"    class="easyui-validatebox"    name="mobile1" style="width:150px" >&nbsp;&nbsp;<br/>
			
			 承运人: 
			<input  type="text" id="name2"    class="easyui-validatebox"    name="name2" style="width:150px" >&nbsp;&nbsp;
			 承运人手机号码: 
			<input  type="text" id="mobile2"    class="easyui-validatebox"    name="mobile2" style="width:150px" >&nbsp;&nbsp;
			
			接单时间：
			<input  type="text"  id="startDate" name="startDate"  onFocus="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    onClick="WdatePicker({onpicked:function(){startDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})"    style="width:150px" >
			~
			<input  type="text"    id="endDate" name="endDate"   onFocus="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   onClick="WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'startDate\')}'})"   style="width:150px"> <br/>
			订单状态: 
			<select name="orderStatus" id="orderStatus"  >
 							<option value="">请选择</option>
							<option value="1" >待成交</option>
							<option value="2" >已成交</option>
							<option value="3" >已完成</option>
					    	<option value="4" >未完成</option>
					    	<option value="5" >已取消</option>
						</select> &nbsp;&nbsp;
			评价: 
			<select name="evaluateType" id="evaluateType"  >
 							<option value="">请选择</option>
							<option value="1" >好评</option>
							<option value="2" >差评</option>
							<option value="3" >未评论</option>
						</select> &nbsp;&nbsp;			
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search" OnClick ="return $('#nstOrderCommentSearchForm').form('validate');"    >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" id="btn-reset" >重置</a>
		</div>
		</form>
	 
		<div style="margin-bottom:5px">
			 <a class="easyui-linkbutton" id="icon-refresh"  iconCls="icon-reload">刷新</a> 
			<%--  <gd:btn btncode='BTNHYGLNSTRZ02'> --%>
			 <a id="exportData" class="easyui-linkbutton" iconCls="icon-save">导出数据</a>
			<%--  </gd:btn> --%>
		</div>
		
		<div id="showDialog" class="easyui-dialog" style="width:600px;height:220px;" closed="true" modal="true" buttons="#dlg-buttonsShow">
			<div id="dlg-buttonsShow" style="text-align:center">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#showDialog').dialog('close')">关闭</a>
	        </div>
		</div>
		</div>
</body>
<script type="text/javascript">
function optformat(value,row,index){
    if(row.evaluateType !="未评价") {
	return "<a class='operate' href='javascript:;' onclick=showObj('"+row.id+"');>查看</a>";
	}
}
</script>
<script type="text/javascript" src="${CONTEXT}js/nst_order/order_comment.js"></script>

</html>

