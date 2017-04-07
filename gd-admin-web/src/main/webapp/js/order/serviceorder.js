$(document).ready(function(){
	initList();
	//查询按钮
	$('#icon-search').click(function(){
		var params = {
				"startDate" : $("#startDate").val(),
				"endDate" : $("#endDate").val(),
				"orderNo" : $("#orderNoList").val(),
				"orderStatus" : $("#orderStatusList").val(),
				"thirdStatementId":$("#thirdStatementIdList").val(),
				"account" : $("#accountList").val(),
				"mobile" : $("#mobile").val()
				};
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
	$("#exportData").click(function(){
		var params = {
				"startDate" : $("#startDate").val(),
				"endDate" : $("#endDate").val(),
				"orderNo" : $("#orderNoList").val(),
				"orderStatus" : $("#orderStatusList").val(),
				"thirdStatementIdList":$("#thirdStatementIdList").val(),
				"account" : $("#accountList").val(),
				"mobile" : $("#mobile").val()
		};
		var paramList = "startDate=" + params.startDate + "&endDate="+params.endDate+"&orderNo="+params.orderNo+"&orderStatus="+params.orderStatus+
		"&thirdStatementIdList="+params.thirdStatementIdList+"&account="+params.account+"&mobile="+params.mobile;
		
		$.ajax({
			url: CONTEXT+'serviceOrder/checkExportParams',
			data : params,
			type:'post',
			async:false,
			success : function(data){
				//检测通过
				if (data && data.status == 1){
					if (!disableExport){
						slideMessage("数据正在导出中, 请耐心等待...");
						disableExport = true ;
						//启动下载
						$.download(CONTEXT+'serviceOrder/exportData',paramList,'post' );
					}else{
						slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
					}
				}else{
					warningMessage(data.message);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				warningMessage(textStatus);
			}
		});
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
	params = {};
	//数据加载
	loadList(params);
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
		url:CONTEXT+'serviceOrder/orderQuery',
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
				{field:'serviceName',title:'服务名称',width:fixWidth(0.1),align:'center' },
				{field:'payAmount',title:'实付款',width:fixWidth(0.1),align:'center'},
				{field:'payTypeView',title:'支付方式',width:fixWidth(0.08),align:'center'},
				{field:'account',title:'用户账号',width:fixWidth(0.08),align:'center'},
				{field:'buyerMobile',title:'手机号码',width:fixWidth(0.08),align:'center'},
				{field:'realName',title:'买家姓名',width:fixWidth(0.08),align:'center'},
				{field:'orderTime',title:'创建时间',width:fixWidth(0.1),align:'center'},
				{field:'finishedTime',title:'成交时间',width:fixWidth(0.1),align:'center'},
				{field:'orderStatusView',title:'订单状态',width:fixWidth(0.08),align:'center'},
				{field:'invoice',title:'发票',width:fixWidth(0.08),align:'center'},
				{field:'opt',title:'操作',width:fixWidth(0.08),align:'center',formatter:optformat}
		]]
	});
}

//计算百分比宽度
function fixWidth(percent) {
    return document.body.clientWidth * percent ; //这里你可以自己做调整
}

function formatIsFirst(val, row) {
	if (val != null) {
		var str=val.toString();
		if(str=="1"){
			return "首单";
		}
	}else{
		return "";
	}
}

//查看
function editObj(persaleId){
	$("#editButtondiv").hide();
	$('#editDialog').dialog({'title':'查看订单','href':CONTEXT+'serviceOrder/orderDetailById/'+persaleId,'width': 800,'height': 500}).dialog('open');
}

//编辑订单，补流水 
function editObj2(persaleId,orderNo){
	$("#editDialog2").find("input[name='invoiceContent']").val("");//先清空

	$("#editDialog2").find("input[name='orderNo']").val(orderNo);
	
	$('#editDialog2').dialog({'title':'编辑订单','width': 380,'height': 180}).dialog('open');
}

function editDialog2Save(){
	var orderNo = $("#editDialog2").find("input[name='orderNo']").val();
	var invoiceContent = $("#editDialog2").find("input[name='invoiceContent']").val();
	if(invoiceContent.length != 8){
		$.messager.alert("提示信息","请输入8位发票号码","warn");
		return false;
	}
	$.ajax({
		type : "post",
		url : CONTEXT+"invoiceInfo/insertInvoiceInfo",
		data : {"orderNo":orderNo,"invoiceContent":invoiceContent},
		dataType : "json",
		async:false,
		success : function(data){
	    	if(data.res == "success"){
	    		//保存成功，重新load该页面
	    		slideMessage("操作成功！");
	    		$("#orderdg").datagrid('reload');
	    		$('#editDialog2').dialog('close');
	    	} else {
	    		$.messager.alert("提示信息",data.msg,"warn");
	    	}
		}
	});
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
		var url=CONTEXT+"order/orderSaveEdit";
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
