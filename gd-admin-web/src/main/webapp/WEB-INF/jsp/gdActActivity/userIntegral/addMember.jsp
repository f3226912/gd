<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<style type="text/css">
div#top{;width:100%;height:50px;border:1px solid grey;background-color:#F0F0F0;}
div#top1{height:100px;border:1px solid grey;background-color:#F0F0F0; padding: 10px; line-height: 36px;}
div#left{;width:400px;height:340px;border:1px solid grey;float:left;}
div#middle{width:100px;height:200px;float:left;padding:30px;}
div#right{width:200px;height:340px;border:1px solid grey;float:right;}

.tab{border-top:1px solid #000;border-left:1px solid #000;text-align:center}
.tab td{border-bottom:1px solid #000;border-right:1px solid #000;}
.tab th{border-bottom:1px solid #000;border-right:1px solid #000;}
</style>
<script type="text/javascript" src="${CONTEXT}js/gdActActivity/userIntegral/addMember.js"></script>
<div id="buyer_buyer">
	<div id="top1">
	 	用户姓名：<input  type="text" id="realName" name="realName" style="width:150px" >&nbsp;&nbsp; 
	 	手机号码：<input type="text" name="mobile" id="mobile"/>&nbsp;&nbsp;
	 	注册来源:
		 <select name="regetype" id="regetype" labelPosition="top" 
			style="width: 150px">
			<option value="">--全部--</option>
			<option value="0">管理后台</option>
			<option value="1">谷登农批网</option>
			<option value="2">农速通</option>
			<option value="3">农商友</option>
			<option value="4">农商友-农批商</option>
			<option value="5">农批友</option>
			<option value="6">供应商</option>
			<option value="7">POS刷卡</option>
			<option value="8">微信注册用户</option>
		</select>
		<br/>
		农商友用户类型:
		 <select name="level" id="level"  >
			<option value="">-全部-</option>
			<option value="1" >农批商</option>
			<option value="3" >农商友</option>
		</select>
     	&nbsp;&nbsp;
		注册时间:
		<input type="text" id="startBuyerTime" placeholder="开始时间"
			required="true" class="easyui-validatebox" name="startTime" value=""
			onFocus="WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"
			onClick="WdatePicker({onpicked:function(){startTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"
			style="width: 150px">~
		<input type="text" id="endBuyerTime" placeholder="结束时间"
			required="true" class="easyui-validatebox" name="endTime" value=""
			onFocus="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}'})"
			onClick="WdatePicker({onpicked:function(){endTime.focus();},minDate:'#F{$dp.$D(\'startTime\')}'})"
			style="width: 150px">
	 	<input type="button" onclick="loadbuyerlist();" value="查询">
 	</div>
 	<!-- 查询出来的用户列表 -->
	<div id="left">
	  <div id="showbuyer_table"></div>
	</div>
	<!-- 操作按钮 添加，删除，全部添加，全部删除 -->
	<div id="middle">
	    <input id="addBuyerOne" onclick="addbuyerone()" value="添加>" type="button"><br/><br/>
     	<input id="removeBuyerOne" onclick="removebuyerone()" value="<移除" type="button"><br/><br/>
		<input id="addBuyerAll" onclick="addbuyerall()" value="全部添加>>" type="button"><br/><br/>
		<input id="removeBuyerAll" onclick="removebuyerall()" type="button"  value="<<全部删除">
	</div>
	
	<!-- 选择用户列表 -->
	<div id="right">
	  	<select id="buyerselect" multiple='multiple' style="width: 200px;height:340px;"></select>
	</div>
</div>
