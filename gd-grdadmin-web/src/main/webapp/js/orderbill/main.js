
$.extend($.fn.validatebox.defaults.rules, {
	floatPoint: {    
        validator: function(value, param){

        	return /^\d+(\.\d+)?$/.test(value);

        },    
        message: '请输入正确的数字'
    },
}); 
$(document).ready(function(){
	loadCashInfo(null,CONTEXT+'orderBill/orderBillList');
	$.ajax({
		type:'POST',
		url:CONTEXT+'orderBill/tradeMoneySum',
		dataType: 'json',
		success:function(data){
			$("#tradeMoneySum").val(data.tradeMoneySum);
		}
	});
});

//查询按钮,根据name查询
$('#icon-search').click(function(){ 
	if(!$('#orderBillSearchForm').form('validate')){
		return;
	}
	var params={"billBeginTime":$("#billBeginTime").val(),
				"billEndTime":$("#billEndTime").val(),
				"businessNo":$("#businessNo").val(),
				"sysRefeNo":$("#sysRefeNo").val(),
				"clientNo":$("#clientNo").val(),
				"marketId":$("#marketId").val(),
				"tradeMoney":$("#tradeMoney").val()};
	loadCashInfo(params,CONTEXT+'orderBill/orderBillList');
	$.ajax({
		type:'POST',
		url:CONTEXT+'orderBill/tradeMoneySum',
		data:params,
		dataType: 'json',
		success:function(data){
			$("#tradeMoneySum").val(data.tradeMoneySum);
		}
	});
});

function loadCashInfo(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#orderBilldg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#orderBilltb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'', hidden:true},
					{field:'businessNo',title:'商户号',width:100,align:'center'},
					{field:'businessName',title:'商户名',width:100,align:'center'},
					{field:'tradeType',title:'交易类型',width:100,align:'center'},
					{field:'tradeDay',title:'交易时间',width:100,align:'center'},
					{field:'cardNo',title:'交易卡号',width:100,align:'center'},
					{field:'clientNo',title:'终端号',width:100,align:'center'},
					{field:'tradeMoney',title:'交易金额',width:100,align:'center',formatter:tradeMoney},
					{field:'sysRefeNo',title:'系统参考号',width:100,align:'center'},
//					{field:'fee',title:'手续费',width:100,align:'center'},
					{field:'useStatus',title:'使用状态',width:100,align:'center',formatter:status},
					{field:'marketName',title:'所属市场',width:100,align:'center' },
					{field:'cardType',title:'银行卡类别',width:100,align:'center'}
				]]
	}); 
	//分页加载
	$("#orderBilldg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
}

function status(val){
	if(val=='1'){
		return '补贴规则已使用';
	}else{
		return '未使用';
	}
}

function tradeMoney(val){
	var a= parseFloat(val);
	return a.toFixed(2);
}

$("#icon-refresh").click(function(){
	$("#businessNo").val("");
	$("#clientNo").val("");
	$("#billBeginTime").val("");
	$("#billEndTime").val("");
	$("#sysRefeNo").val("");
	$("#marketId").val("");
	loadCashInfo(null,CONTEXT+'orderBill/orderBillList');
	$.ajax({
		type:'POST',
		url:CONTEXT+'orderBill/tradeMoneySum',
		dataType: 'json',
		success:function(data){
			$("#tradeMoneySum").val(data.tradeMoneySum);
		}
	});
});

$("#btn-reset").click(function(){
	$("#businessNo").val("");
	$("#clientNo").val("");
	$("#billBeginTime").val("");
	$("#billEndTime").val("");
	$("#sysRefeNo").val("");
	$("#marketId").val("");
	
});