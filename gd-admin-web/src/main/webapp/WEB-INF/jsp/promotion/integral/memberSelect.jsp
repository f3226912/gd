<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
 	<table id="memberdg" title="">
	</table>
	<div id="membertb" style="padding:5px;height:auto">
		<form id="memberSearchForm" method="post">
		<div>
		       用户账号: 
			<input  type="text" id="account"  name="account"  style="width:150px" >
			参与用户群:
			<select>
				<option value="">请选择-</option>
				<option value="0">微信绑定用户</option><!--搜索用户表openid不为空的数据 -->
			</select>
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-member-select"  >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick='$("#memberSearchForm")[0].reset()'>重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-reload">刷新</a>
		</div>
		</form>
		<input type="hidden" id="status" name="status" value="${status}">
	</div>
<script type="text/javascript">
$(document).ready(function(){
	
});
loadMemberData({status: $("#status").val()}, CONTEXT+'activityintegral/queryMemberList');

function loadMemberData(params, loadUrl){
	
	params = !params ? {}: params;
	//数据加载
	$('#memberdg').datagrid({
		url: loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#membertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'memberId',title:'',width:0,hidden:true},
			{field:'account',title:'注册账号',width:100,align:'center'},
			{field:'realName',title:'真实姓名',width:100,align:'center'},
			{field:'createTime',title:'创建时间',width:100,align:'center',formatter:formatterdate   },
		]],
		onDblClickRow: function(index,row){
			memberCallBack(index,row);
		}
	}); 
	
	//分页加载
	$("#memberdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

$('#icon-search-member-select').click(function(){ 
	var params = {
		"account" : $("#account").val(),
		"status" : $("#status").val(),
	}
	loadMemberData(params, CONTEXT+'activityintegral/queryMemberList');
});	

$('#icon-reload').click(function(){ 
	$('#icon-search-member-select').click();
});

//日期处理
function formatterdate(val, row) {
		if (val != null) {
		   var  str=val.toString();
		   str =  str.replace(/-/g,"/");
		   var date = new Date(str);
		   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
		}
	}
	
//回调
function memberCallBack(index,row){
	var memberId = row.memberId;
	$("#showMemberDailog").val(row.account);
	$('#memberDailog').dialog('close');
}

</script>