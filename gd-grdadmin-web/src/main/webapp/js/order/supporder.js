$(document).ready(function(){
	initList();
	//查询按钮
	$('#icon-search').click(function(){
		var tempType = $("#typeList").val();
		var tempOrderStatus = $("#orderStatusList").val();
		var statementId=$("#statementIdList").val();
		statementId = (typeof(statementId) == "undefined")?"":statementId;
		var posNumber=$("#posNumberList").val();
		posNumber = (typeof(posNumber) == "undefined")?"":posNumber;
		var params;
		if("0" == tempType){
			params = {
				"startDate" : $("#startDate").val(),
				"endDate" : $("#endDate").val(),
				"orderNo" : $("#orderNoList").val(),
				"orderAmount" : $("#orderAmountList").val(),
				"orderStatus" : tempOrderStatus,
				"payType" : $("#payTypeList").val(),
				"marketId": $("#marketList").val(),
				"account" : $("#accountList").val(),
//				"examineStatus" : $("#examineStatusList").val(),
				"upPayFlag" : $("#upPayFlagList").val(),
				"shopName" : $("#shopNameList").val(),
				"statementId":statementId,
				"posNumber":posNumber
				};
		}else{
			params = {
				"startDate" : $("#startDate").val(),
				"endDate" : $("#endDate").val(),
				"orderNo" : $("#orderNoList").val(),
				"orderAmount" : $("#orderAmountList").val(),
				"orderStatus" : tempType,
				"payType" : $("#payTypeList").val(),
				"account" : $("#accountList").val(),
				"marketId": $("#marketList").val(),
//				"examineStatus" : $("#examineStatusList").val(),
				"upPayFlag" : $("#upPayFlagList").val(),
				"shopName" : $("#shopNameList").val(),
				"statementId":statementId,
				"posNumber":posNumber
				};
		}
		loadList(params);
	});

	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#orderSearchForm')[0].reset();
		initList();
		//重启导出功能
		disableExport = false ;
	});

	//重置按钮
	$('#icon-reload').click(function(){
		$('#orderSearchForm')[0].reset();
	});

	/***数据导出功能***/
	$("#exportData").click(function(){
		var tempType = $("#typeList").val();
		var statementId=$("#statementIdList").val();
		statementId = (typeof(statementId) == "undefined")?"":statementId;
		var posNumber=$("#posNumberList").val();
		posNumber = (typeof(posNumber) == "undefined")?"":posNumber;

/*		var queryParams = $('#orderdg').datagrid('options').queryParams;*/
/*		var tempOrderStatus = $("#orderStatusList").val();
		if("0" == tempType){
			queryParams.orderStatus = tempOrderStatus;
		}else{
			queryParams.orderStatus = tempType;
		}
		queryParams.orderAmount = $("#orderAmountList").val();
		queryParams.orderNo = $("#orderNoList").val();
		queryParams.startDate = $("#startDate").val();
		queryParams.endDate = $("#endDate").val();
		queryParams.payType = $("#payTypeList").val();
		queryParams.account = $("#accountList").val();
		queryParams.orderSource = $("#orderSourceList").val();
//		queryParams.examineStatus = $("#examineStatusList").val();
		queryParams.shopName = encodeURI(encodeURI($("#shopNameList").val()));*/
		var params = {
				"orderAmount" : $("#orderAmountList").val(),
				"orderNo" : $("#orderNoList").val(),
				"startDate" : $("#startDate").val(),
				"endDate" : $("#endDate").val(),
				"payType" : $("#payTypeList").val(),
				"account" : $("#accountList").val(),
				"marketId": $("#marketList").val(),
				"statementId":statementId,
				"posNumber":posNumber,
				"shopName" : $("#shopNameList").val()
			};
		if("0" == tempType){
			params.orderStatus = $("#orderStatusList").val();
		}else{
			params.orderStatus = tempType;
		}
		var paramList = 'orderStatus=' + params.orderStatus + "&orderAmount=" + params.orderAmount
			+ "&startDate="	+ params.startDate	+ "&endDate=" + params.endDate	+ "&orderNo=" + params.orderNo
			+ "&payType=" + params.payType	+ "&account=" + params.account	+ "&marketId=" + params.marketId + "&shopName=" + params.shopName
			+ "&statementId=" + params.statementId+ "&posNumber=" + params.posNumber;

		$.ajax({
			url: CONTEXT+'supporder/checkExportParams',
			data : params,
			type:'post',
			success : function(data){
				//检测通过
				if (data && data.status == 1){

					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'supporder/exportData',paramList,'post' );
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
/*		window.location.href = CONTEXT
				+ 'order/exportData?orderStatus='
				+ queryParams.orderStatus
				+ "&orderAmount="
				+ queryParams.orderAmount
				+ "&startDate="
				+ queryParams.startDate
				+ "&endDate="
				+ queryParams.endDate
				+ "&orderNo="
				+ queryParams.orderNo
				+ "&payType="
				+ queryParams.payType
				+ "&account="
				+ queryParams.account
//				+ "&examineStatus="
//				+ queryParams.examineStatus
				+ "&shopName="
				+ queryParams.shopName
				+ "&orderSource="
				+ queryParams.orderSource;*/
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
//初始化加载页面列表
function initList(){
	var typeList = $("#typeList").val();
	if("0" == typeList){
		loadList(null);
	}else{
		var params = {
			"orderStatus" : typeList
			};
		loadList(params);
	}

	//分页加载
	$("#orderdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#orderdg').datagrid({
		url:CONTEXT+'supporder/orderQuery',
		//width: 1000,
		queryParams: params,
		height: 'auto',
		nowrap:true,
		toolbar:'#ordertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#orderdg").datagrid('clearSelections');
		},
		columns:[[
			{field:'orderNo',title:'订单编号',width:fixWidth(0.13),align:'center',formatter:orderNoformat},
			{field:'orderAmount',title:'订单金额',width:fixWidth(0.1),align:'center' },
			{field:'prepayAmt',title:'预付款',width:fixWidth(0.1),align:'center'},
//			{field:'discountAmount',title:'抵扣金额',width:100,align:'center'},
//			{field:'receAmount',title:'应付金额',width:100,align:'center'},
			{field:'payAmount',title:'实付款',width:fixWidth(0.1),align:'center'},
//			{field:'orderSourceView',title:'订单来源',width:fixWidth(0.08),align:'center'},
			{field:'payTypeView',title:'支付方式',width:fixWidth(0.08),align:'center'},
			{field:'account',title:'买家账号',width:fixWidth(0.08),align:'center'},
			{field:'realName',title:'买家姓名',width:fixWidth(0.08),align:'center'},
			{field:'shopName',title:'商铺名称',width:fixWidth(0.08),align:'center'},
			{field:'orderTime',title:'创建时间',width:fixWidth(0.1),align:'center'},
			{field:'orderStatusView',title:'订单状态',width:fixWidth(0.08),align:'center'},
			{field:'stateComment',title:'状态词',width:fixWidth(0.1),align:'center'},
//			{field:'examineStatus',title:'审核状态',width:100,align:'center',formatter:formatExamineStatus },
			{field:'opt',title:'操作',width:fixWidth(0.08),align:'center',formatter:optformat}
		]]
	});
}

//计算百分比宽度
function fixWidth(percent) {
    return document.body.clientWidth * percent ; //这里你可以自己做调整
}


//付款方式
//function formatPayType(val, row) {
//	if (val != null) {
//		var  str=val.toString();
//		if(str=="1"){
//			return "账户余额";
//		}else if(str=="2"){
//			return "POS刷卡";
//		}else if(str=="3"){
//			return "现金";
//		}else if(str=="12"){
//			return "账户余额+POS刷卡";
//		}else if(str=="13"){
//			return "账户余额+现金";
//		}
//	}else{
//		return "";
//	}
//}

//订单状态
/*function formatOrderStatus(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "付款未完成";
		}else if(str=="2"){
			return "部分付款";
		}else if(str=="3"){
			return "已付款";
		}else if(str=="4"){
			return "已出场";
		}else if(str=="8"){
			return "已取消";
		}else if(str=="9"){
			return "已过期";
		}else if(str=="10"){
			return "已完成";
		}else if(str=="11"){
			return "待发货";
		}else if(str=="12"){
			return "待收货";
		}
	}else{
		return "";
	}
}*/

//审核状态
//function formatExamineStatus(val, row) {
//	if (val != null) {
//		var  str=val.toString();
//		if(str=="0"){
//			return "待审核";
//		}else if(str=="1"){
//			return "审核通过";
//		}else if(str=="2"){
//			return "审核驳回";
//		}
//	}else{
//		return "";
//	}
//}

//查看
function editObj(persaleId){
	$("#editButtondiv").hide();
	$('#editDialog').dialog({'title':'查看订单','href':CONTEXT+'supporder/orderDetailById/'+persaleId,'width': 800,'height': 500}).dialog('open');
}

function saveObj(type){
	var lshtemp = true;
	if("1" == type || "3" == type){
		if(!$("#editForm").form('validate')){
			return $("#editForm").form('validate');
		}
		$(".pz-img-item .pz-img-box").each(function (index) {
			var self = $(this),
			val = self.find("input").val(),
			inputs = self.siblings();
			for(var i = 0;i<inputs.length;i++){
				if(val==inputs.eq(i).find("input").val()){
					alert("请不要重复输入银行流水号," + val);
					lshtemp = false;
					return false;
				}
			}
		});
	}else if("2" == type){
		var ywyyi,ywyy;
		ywyy = document.getElementsByName("description");
		for (ywyyi = 0; ywyyi < ywyy.length; ywyyi ++){
			if(ywyy[ywyyi].checked)break;
		}
		if(ywyyi >= ywyy.length){
			alert("请选择驳回原因!");
			return false;
		}
	}
	$("#typetemp").val(type);
	if(lshtemp){
		var url=CONTEXT+"supporder/orderSaveEdit";
		jQuery.post(url, $('#editForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#orderdg").datagrid('reload');
				$('#editDialog').dialog('close');
			} else if(data == "failedbylsh") {
				warningMessage("银行流水号已存在!");
				return;
			} else if(data == "failedbysh") {
				warningMessage("该订单已审核,请刷新列表审核其他订单!");
				return;
			} else {
				warningMessage(data);
				return;
			}
		});
	}
}
