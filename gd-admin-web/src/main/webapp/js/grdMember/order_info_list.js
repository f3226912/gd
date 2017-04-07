
$(document).ready(function(){
	//数据表格绑定
	initDataGridBind();
	// 查询按钮 点击事件
	$('#searchOrderBtn').click(function(){
		var endDate;
		if($('#queryOrderEndDate').val().length>0){
			endDate =$('#queryOrderEndDate').val()+" 23:59:59";
		}
		$("#orderInfoDataGrid").datagrid('load',{
			userId:$('#grdMemberId').val(),
			businessName:$('#businessName').val(),
			orderCode:$('#orderCode').val(),
			startDate:$('#queryOrderStartDate').val(),
			endDate:endDate
				
		 });
	//	initDataGridBind();
	});
	// 重置按钮 点击事件
	$('#divResetBtn').click(function(){
		$('#grdOrderMemberForm')[0].reset();
	});
	
});

/**数据表格绑定
 * 
 */
function initDataGridBind() {
	$('#orderInfoDataGrid').datagrid({
		url:CONTEXT+'grdMember/queryOrderInfo',
		height: 'auto',
		nowrap:true,
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		pageList:[5,10,20,50],
		pagination:true,
		singleSelect:true,
		onBeforeLoad:setParams,
		onLoadError:function(){warningMessage("查询失败!");},
		onLoadSuccess:onLoadSuccessForDatagrid,
		columns:[[
		    {field:'orderNo',title:'订单号',width:150,align:'left',halign:'center'},
			{field:'orderTime',title:'交易时间',width:150,align:'center',halign:'center'},
			{field:'buyerMobile',title:'买家手机',width:90,align:'left',halign:'center'},
			{field:'buyerName',title:'买家姓名',width:90,align:'left',halign:'center'},
			{field:'shopsName',title:'商铺',width:150,align:'left',halign:'center'},
			{field:'orderPrice',title:'实付金额',width:150,align:'right',halign:'center',formatter:formatterOrderPrice }
		]]
	});
}

/**设置查询参数
 * @param p
 */
function setParams(p){
	p.userId = $('#grdMemberId').val();
	p.businessName=$('#businessName').val();
	p.orderCode=$('#orderCode').val();
	p.startDate =$('#queryOrderStartDate').val();
	if($('#queryOrderEndDate').val().length>0){
	p.endDate =$('#queryOrderEndDate').val()+" 23:59:59";
	}
	
}

function onLoadSuccessForDatagrid(data){
	if(!data.isSuccess){
		warningMessage(data.msg);
		return false;
	}
	
	//订单金额总和
	var orderPriceSum = 0.00;
	$.each(data.rows, function(index, obj){
		if(obj.orderPrice) {
			orderPriceSum += parseFloat(obj.orderPrice);
		}
	});

	var rowJson = {};
	rowJson.orderPrice = "合计：&nbsp;&nbsp; " + (parseFloat(orderPriceSum).toFixed(2));
	
	
	var correctTotal = data.total;
	
	//内置函数，修复datagrid的底部分页展示不正确的问题。
	function correct() {
		//修复分页底部信息
		$("#orderInfoDataGrid").datagrid("getPager").pagination("refresh",{total:correctTotal});
		//去除当前行序号
		$(".orderInfoListDataBody td.datagrid-td-rownumber").last().html("");
	}
	
	//动态添加一行，显示合计数
	$("#orderInfoDataGrid").datagrid("appendRow",rowJson);
	
	//必须要异步线程执行。否则修改分页显示失效。
	setTimeout(correct, 1);
}


/* ***********数据格式化***************** */



//格式化金额总计
function formatterOrderPrice(val,row,index) {
	var numVal = parseFloat(val);
	if(!isNaN(numVal)) {
		val = numVal.toFixed(2);
	}
	return val;
}
