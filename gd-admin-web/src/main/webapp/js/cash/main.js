
$(document).ready(function(){
	var params={"status":$("#status").val()};
	loadCashInfo(params,CONTEXT+'cash/cashList');
});

var disableExport = false ;

//查询按钮,根据name查询
$('#icon-search').click(function(){
	var params={"status":$("#status").val(),
			"account":$("#account").val(),
			"isABC":$("#isABC").val(),
			"realName":$("#realName").val(),
			"bankCardNo":$("#bankCardNo").val(),
			"applyBeginTime":$("#applyBeginTime").val(),
			"applyEndTime":$("#applyEndTime").val(),
			"payBeginTime":$("#payBeginTime").val(),
			"payEndTime":$("#payEndTime").val()};
	loadCashInfo(params,CONTEXT+'cash/cashList');

});

function loadCashInfo(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#cashdg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto',
		nowrap:true,
		toolbar:'#cashtb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'cashReqId',title:'', hidden:true},
					{field:'cashAmount',title:'提现金额',width:100,align:'center'},
					{field:'reqTime',title:'申请时间',width:100,align:'center'},
					{field:'realName',title:'姓名',width:100,align:'center'},
					{field:'account',title:'提现会员账号',width:100,align:'center',formatter:function(value,row,index){
						return "<a class='operate' href='javascript:;' onclick=showAccountFlowList('"+row.cashReqId+"','"+row.status+"');>"+value+"</a>&nbsp;";
					}},
					{field:'depositBankName',title:'开户行',width:100,align:'center'},
					{field:'bankCardNo',title:'银行卡号',width:100,align:'center'},
					{field:'mobile',title:'手机号码',width:100,align:'center'},
					{field:'status',title:'提现状态',width:100,align:'center',formatter:cashStatus},
					{field:'payingTime',title:'结款时间',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	});
	//分页加载
	$("#cashdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

}

function showAccountFlowList(cashReqId,status){

	$('#accountFlowListDialog').dialog({'title':'账户流水', 'width':900, 'height':500, 'href':CONTEXT+'cash/accountFlow?cashReqId='+cashReqId+'&status='+status}).dialog('open');
}

function pay(cashReqId){

	$('#payDialog').dialog({'title':'提款操作', 'width':500, 'height':300, 'href':CONTEXT+'cash/pay?cashReqId='+cashReqId}).dialog('open');
}

function cashStatus(val){
	if(val=='0'){
		return '待提现';
	}else if(val=='1'){
		return '已结款';
	}else{
		return '';
	}
}

$("#icon-refresh").click(function(){
	$("#account").val("");
	$("#realName").val("");
	$("#bankCardNo").val("");
	$("#applyBeginTime").val("");
	$("#applyEndTime").val("");
	$("#payBeginTime").val("");
	$("#isABC").val("");
	$("#payEndTime").val("");
	$("#cashdg").datagrid('load',{status:0});
	//重启导出功能
	disableExport = false ;
});
$("#icon-refreshalready").click(function(){
	$("#account").val("");
	$("#realName").val("");
	$("#bankCardNo").val("");
	$("#applyBeginTime").val("");
	$("#applyEndTime").val("");
	$("#payBeginTime").val("");
	$("#payEndTime").val("");
	$("#isABC").val("");
	$("#cashdg").datagrid('load',{status:1});
	//重启导出功能
	disableExport = false ;
});
$("#btn-reset").click(function(){
	$("#account").val("");
	$("#realName").val("");
	$("#bankCardNo").val("");
	$("#applyBeginTime").val("");
	$("#applyEndTime").val("");
	$("#payBeginTime").val("");
	$("#isABC").val("");
	$("#payEndTime").val("");
});

/***数据导出功能***/
$("#exportData").click(function(){

/*	var queryParams = $('#cashdg').datagrid('options').queryParams;
	queryParams.account = $('#cashSearchForm #account').val();
	queryParams.realName = $('#cashSearchForm #realName').val();
	queryParams.bankCardNo = $('#cashSearchForm #bankCardNo').val();
	queryParams.applyBeginTime = $("#applyBeginTime").val();
	queryParams.applyEndTime = $("#applyEndTime").val();
	queryParams.payBeginTime = $("#payBeginTime").val();
	queryParams.payEndTime = $("#payEndTime").val();
	queryParams.status=$("#status").val();
	queryParams.isABC=$("#isABC").val();*/

	var params = {
			"account" : $('#cashSearchForm #account').val(),
			"realName" : $('#cashSearchForm #realName').val(),
			"bankCardNo" : $('#cashSearchForm #bankCardNo').val(),
			"applyBeginTime" : $('#cashSearchForm #applyBeginTime').val(),
			"applyEndTime" : $('#cashSearchForm #applyEndTime').val(),
			"payBeginTime" : $('#cashSearchForm #payBeginTime').val(),
			"payEndTime" : $('#cashSearchForm #payEndTime').val(),
			"status" : $('#cashSearchForm #status').val(),
			"isABC" : $('#cashSearchForm #isABC').val(),
		};
	var paramList = 'account=' + params.account + "&realName="+params.realName +"&bankCardNo="
		+params.bankCardNo	+ "&applyBeginTime=" + params.applyBeginTime + "&applyEndTime="+params.applyEndTime
		+"&payBeginTime="+params.payBeginTime	+ "&payEndTime="+params.payEndTime	+"&status="+params.status
		+"&isABC="+params.isABC;

	$.ajax({
		url: CONTEXT+'cash/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'cash/exportData',paramList,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}

			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage(data);
		}
	});

/*	window.location.href=CONTEXT+'cash/exportData?account='+queryParams.account+
	"&realName="+queryParams.realName
	+"&bankCardNo="+queryParams.bankCardNo
	+"&applyBeginTime="+queryParams.applyBeginTime
	+"&applyEndTime="+queryParams.applyEndTime
	+"&payBeginTime="+queryParams.payBeginTime
	+"&payEndTime="+queryParams.payEndTime
	+"&status="+queryParams.status
	+"&isABC="+queryParams.isABC;*/
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