<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>	
	
 	<table id="memberdg" title="">
	</table>
	<div id="membertb" style="padding:5px;height:auto">
		<form id="marketSearchForm" method="post">
		<div>
		       账号/手机号: 
			<input  type="text" id="account"  name="account"  style="width:150px" >
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-search-member-select"  >查询</a>&nbsp;&nbsp;
			<a class="easyui-linkbutton icon-reload-btn" iconCls="icon-reload" onclick='$("#marketSearchForm")[0].reset()'>重置</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" id="icon-reload">刷新</a>
		</div>
		</form>
		<input type="hidden" id="status" name="status" value="${status}">
	</div>
<script type="text/javascript">
$(document).ready(function(){
	//alert("");
});
loadMemberData({status: $("#status").val()}, CONTEXT+'product/queryMemberList');

function loadMemberData(params, loadUrl){
	
	params = !params ? {}: params;
	//数据加载
	$('#memberdg').datagrid({
		url: loadUrl,
		//width: 1000,  
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
			{field:'level',title:'用户类型',width:100,align:'center',formatter:formatterLevel },
			/* {field:'account',title:'注册账号',width:100,align:'center'}, */
			{field:'mobile',title:'手机号',width:100,align:'center'},
			/* {field:'nickName',title:'昵称',width:100,align:'center'}, */
			{field:'realName',title:'姓名',width:100,align:'center'},
			{field:'createTime',title:'注册时间',width:100,align:'center',formatter:formatterdate   },
//			{field:'birthday',title:'生日',width:100,align:'center',formatter:formatterdate   },
//			{field:'mobile',title:'手机',width:100,align:'center'},
			/* {field:'isAuth',title:'认证状态',width:100,align:'center',formatter:formatterAuth }, */
			{field:'status',title:'激活状态',width:100,align:'center',formatter:formatterStatus }
		 
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
		/* "account" : $("#account").val(), 废弃 */
		"accountOrPhone" : $("#account").val(),
		"status" : $("#status").val(),
	}
	loadMemberData(params, CONTEXT+'product/queryMemberList');
});	

$('#icon-reload').click(function(){ 
	$('#icon-search-member-select').click();
});	

//会员类别（1谷登农批，2农速通，3农商友，4产地供应商，其余待补）
function formatterLevel(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "谷登农批";
		}else if(str=="2"){
			return "农速通";
		}else if(str=="3"){
			return "农商友";
		} else if(str=="4"){
			return "产地供应商";
		} else if(str=="5"){
			return "农批友";
		} 
	} 
}
function formatterAuth(val, row) {
	if (val != null) {
	   var  str=val.toString();
	   if(str=="1"){
		   return "已认证";
	   }else if(str=="0"){
		   return "待认证";
	   }else if(str=="2"){
		   return "已驳回";
	   }
	}else{
		   return "待认证";
	}
}

function formatterStatus(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "启用";
		}else if(str=="0"){
			return "禁用";
		} 
	}else{
		return "禁用";
	}
}

function formatterdate(val, row) {
		if (val != null) {
		   var  str=val.toString();
		   str =  str.replace(/-/g,"/");
		   var date = new Date(str);
		   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
		}
	}


</script>


