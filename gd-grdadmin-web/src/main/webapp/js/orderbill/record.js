$(document).ready(function(){
	loadPosPayBillInfo();
});

//查询按钮
$('#icon-search').click(function(){
	loadPosPayBillInfo();
});

function loadPosPayBillInfo(){
	//设置参数
	var params={
		"type": $("#typeList").val(),
		"OrderNo": $("#OrderNo").val(),
		"clientNo": $("#clientNo").val(),
		"shopName": $("#shopName").val(),
		"sysRefeNo": $("#sysRefeNo").val(),
		"billBeginTime": $("#billBeginTime").val(),
		"billEndTime": $("#billEndTime").val(),
		"tradeMoney": $("#tradeMoney").val()
	};
	
	//数据加载
	$('#posPayBilldg').datagrid({
		url: CONTEXT + 'posPay/query',
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#posPayBilltb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
			{field:'sysRefeNo',title:'系统参考号',width:100,align:'center'},
			{field:'clientNo',title:'POS流水终端号',width:100,align:'center'},
			{field:'shopPosNumber',title:'商铺终端号',width:100,align:'center'},
			{field:'memberid',title:'', hidden:true},
			{field:'account',title:'买家账号',width:100,align:'center'},
			{field:'cardNo',title:'交易卡号',width:100,align:'center'},
			{field:'tradeMoney',title:'流水交易金额',width:100,align:'center'},
			{field:'tradeAmount',title:'订单实付款',width:100,align:'center'},
			{field:'shopName',title:'商铺名称',width:100,align:'center'},
			{field:'orderNo',title:'订单编号',width:100,align:'center', formatter:orderNoformat},
			{field:'bankTradeTime',title:'银行交易时间 ',width:100,align:'center'},
			{field:'orderTradeTime',title:'订单成交时间',width:100,align:'center'},
			{field:'matchType',title:'类型',width:100,align:'center', formatter:matchFormat},
		]]
	}); 
	
	//分页加载
	$("#posPayBilldg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

/***数据导出功能***/
$("#exportData").click(function(){
	var params={
		"type": $("#typeList").val(),
		"OrderNo": $("#OrderNo").val(),
		"clientNo": $("#clientNo").val(),
		"shopName": $("#shopName").val(),
		"sysRefeNo": $("#sysRefeNo").val(),
		"billBeginTime": $("#billBeginTime").val(),
		"billEndTime": $("#billEndTime").val(),
		"tradeMoney": $("#tradeMoney").val()
	};
	
	var paramList = 'type=' + params.type + "&OrderNo=" + params.OrderNo
		+ "&clientNo="	+ params.clientNo	+ "&shopName=" + params.shopName	+ "&sysRefeNo=" + params.sysRefeNo
		+ "&billBeginTime=" + params.billBeginTime	+ "&billEndTime=" + params.billEndTime	+ "&tradeMoney=" + params.tradeMoney;

	$.ajax({
		url: CONTEXT + 'posPay/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				//启动下载
				$.download(CONTEXT + 'posPay/exportData', paramList, 'post');
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage(data);
		}
	});
}); 

jQuery.download = function(url, data, method){
	// 获得url和data
	if( url && data ){
		// data 是 string或者 array/object
		data = typeof data == 'string' ? data : jQuery.param(data);
		// 把参数组装成 form的  input
		var inputs = '';
		jQuery.each(data.split('&'), function(){
			var pair = this.split('=');
			inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
		});
		// request发送请求
		jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
			.appendTo('body').submit().remove();
	};
};

//格式化匹配结果
function matchFormat(type){
	var str = '未知类型';
	switch(type){
		case '1': 
			str = '完全匹配';
			break;
		case '2': 
			str = '未升级pos';
			break;
		case '3': 
			str = '已升级pos';
			break;
		case '4': 
			str = '延时订单';
			break;
		default : 
			break;
	}
	return str;
}

//查看订单详情
function editObj(persaleId){
	$("#editButtondiv").hide();
	$('#orderDetailDialog').dialog({'title':'查看订单','href':CONTEXT+'order/orderDetailById/'+persaleId,'width': 800,'height': 500}).dialog('open');
}

//重置按钮
$("#btn-reset").click(function(){
	$("#OrderNo").val("");
	$("#shopName").val("");
	$("#clientNo").val("");
	$("#sysRefeNo").val("");
	$("#billBeginTime").val("");
	$("#billEndTime").val("");
	$("#tradeMoney").val("");
});