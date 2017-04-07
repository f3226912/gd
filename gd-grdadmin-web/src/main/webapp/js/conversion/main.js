
$(document).ready(function(){
	loadConversionIntegral(null,CONTEXT+'conversion/memberinfo');
});

//查询按钮,根据name查询
$('#icon-search').click(function(){ 

	var params={"account":$("#account").val(),
			"userType":$("#userType").val()};
	loadConversionIntegral(params,CONTEXT+'conversion/memberinfo');
	
});

function loadConversionIntegral(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#conversiondg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#conversiontb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'memberId',title:'', hidden:true},
					{field:'account',title:'注册账号',width:100,align:'center'},
					{field:'userType',title:'用户类型',width:100,align:'center',formatter:userTypeMatter},
					{field:'companyName',title:'企业名称',width:100,align:'center'},
					{field:'contacts',title:'联系人',width:100,align:'center'},
					{field:'integral',title:'积分总数',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#conversiondg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
}

function userTypeMatter(val){
	if(val==1){
		return "个人";
	}else if(val==2){
		return "企业";
	}else
		return val;
}

function showGiftList(memberId,integral){
	if(integral<=0 || integral ==''){
		warningMessage("积分不足兑换！");
		return;
	}
	$('#giftDialog').dialog({'title':'选择礼物', 'width':900, 'height':500, 'href':CONTEXT+'conversion/giftselect?memberId='+memberId+'&integral='+integral}).dialog('open');
}

$("#icon-refresh").click(function(){
	$("#conversiondg").datagrid('load',{});
});

$("#btn-reset").click(function(){
	$("#account").val("");
	$("#userType").val("");
});

