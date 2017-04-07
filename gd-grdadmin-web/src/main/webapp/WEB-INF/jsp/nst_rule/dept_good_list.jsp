<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<input type="hidden" id="deptMemberId" value="${deptMemberId }">
<input type="hidden" id="assignStartTime" value="${assignStartTime }">
<input type="hidden" id="assignEndTime" value="${assignEndTime }">
<table id="deptGooddg" title=""></table>

<script type="text/javascript">
function loadDeptGood(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#deptGooddg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(data){ 
	
        },
		columns:[[
			{field:'realName',title:'信息发布人',width:100,align:'center'},
			{field:'account',title:'发布人账号',width:100,align:'center'},
			{field:'createTime',title:'发布时间',width:100,align:'center'},
			{field:'s_address',title:'起运地',width:100,align:'center'},
			{field:'f_address',title:'目的地',width:100,align:'center'},
			{field:'nstRule',title:'分配类型',width:100,align:'center', formatter:nstRuleFormatter},
			{field:'orderStatus',title:'订单状态',width:100,align:'center', formatter:orderStatusFormatter},
			{field:'clients',title:'发布来源',width:100,align:'center', formatter:clientsFormatter},
			{field:'commonCityName',title:'常用城市',width:100,align:'center'},
			{field:'sourceType',title:'货源类型',width:100,align:'center', formatter:sourceTypeFormatter},
			{field:'createTime',title:'分配时间',width:100,align:'center'}
		]]
	}); 
	//分页加载
	$("#deptGooddg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function nstRuleFormatter(val){
	if(typeof(val) == "undefined"){
		return "";
	}else{
		if(val == "0"){
			return "直发";
		}
		else if(val=="1"){
			return "代发";
		}
	}
}

function orderStatusFormatter(val){
	if(typeof(val) == "undefined"){
		return "";
	}else{
		if(val == "1"){
			return "待成交";
		}
		else if(val=="2"){
			return "已成交";
		}
		else if(val=="3"){
			return "已完成";
		}
		else if(val=="4"){
			return "未完成";
		}
		else if(val=="5"){
			return "取消订单";
		}
	}
}

function clientsFormatter(val){
	if(typeof(val) == "undefined"){
		return "";
	}else{
		if(val == "1"){
			return "谷登农批";
		}
		else if(val=="2"){
			return "农速通";
		}
		else if(val=="3"){
			return "农商友";
		}
		else if(val=="4"){
			return "产地供应商";
		}
		else if(val=="5"){
			return "农商友-农批商";
		}
	}
}

function sourceTypeFormatter(val){
	if(typeof(val) == "undefined"){
		return "";
	}else{
		if(val == "0"){
			return "干线";
		}
		else if(val=="1"){
			return "同城";
		}
	}
}

$(document).ready(function(){
	var deptMemberId = $("#deptMemberId").val();
	var assignStartTime = $("#assignStartTime").val();
	var assignEndTime = $("#assignEndTime").val();
	loadDeptGood(null,CONTEXT+'nstRule/queryDeptGood/'+deptMemberId+'?assignStartTime='+assignStartTime+"&assignEndTime="+assignEndTime);
});
</script>