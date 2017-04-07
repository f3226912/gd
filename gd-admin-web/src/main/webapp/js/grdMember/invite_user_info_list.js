
$(document).ready(function(){
	//数据表格绑定
	initDataGridBind();
	// 查询按钮 点击事件
	$('#searchUserBtn').click(function(){
		var endDate;
		if($('#queryUserEndDate').val().length>0){
		endDate = $('#queryUserEndDate').val()+" 23:59:59";
		}
		$("#userInfoDataGrid").datagrid('load',{
			userId:$('#grdMemberId').val(),
		    userMobile:$('#userMobile').val(),
		    userStartDate:$('#queryUserStartDate').val(),
			endDate:endDate
		 });
	});
		
	// 重置按钮 点击事件
	$('#divUserResetBtn').click(function(){
		$('#grdUserMemberForm')[0].reset();
	});
});


/**数据表格绑定
 * 
 */
function initDataGridBind() {
	$('#userInfoDataGrid').datagrid({
		url:CONTEXT+'grdMember/queryInviteRegUserInfo',
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
		    {field:'mobile',title:'客户手机',width:90,align:'left',halign:'center'},
		    {field:'realName',title:'客户姓名',width:90,align:'left',halign:'center'},
			{field:'inviteUserRegDate',title:'注册时间',width:150,align:'center',halign:'center'},
			/*{field:'level',title:'客户类型',width:90,align:'center',halign:'center',formatter:formatterLevel },*/
			{field:'countNo',title:'累计领取礼品数量',width:120,align:'center',halign:'center',formatter:formatterCountNo},
			{field:'orderAmount',title:'累计单数',width:90,align:'center',halign:'center' },
			{field:'orderPriceAmount',title:'累计金额',width:100,align:'right',halign:'center',formatter:formatterOrderPriceAmount }
		]]
	});
}

/**设置查询参数
 * @param p
 */
function setParams(p){
	p.userId = $('#grdMemberId').val();
	p.userMobile = $('#userMobile').val();
	p.userStartDate = $('#queryUserStartDate').val();
	if($('#queryUserEndDate').val().length>0){
	p.userEndDate = $('#queryUserEndDate').val()+" 23:59:59";
	}
}

function onLoadSuccessForDatagrid(data){
	if(!data.isSuccess){
		warningMessage(data.msg);
		return false;
	}
	
	//礼品总和
	var giftSum = 0;
	//订单总和
	var orderSum = 0;
	//订单金额总和
	var orderPriceSum = 0.00;
	$.each(data.rows, function(index, obj){
		if(obj.countNo) {
			giftSum += parseInt(obj.countNo);
		}
		if(obj.orderAmount) {
			orderSum += parseInt(obj.orderAmount);
		}
		if(obj.orderPriceAmount) {
			orderPriceSum += parseFloat(obj.orderPriceAmount);
		}
	});
	
	var rowJson = {};
	rowJson.level = "合计：";
	rowJson.countNo = giftSum;
	rowJson.orderAmount = orderSum;
	rowJson.orderPriceAmount = orderPriceSum;
	
	var correctTotal = data.total;
	
	//内置函数，修复datagrid的底部分页展示不正确的问题。
	function correct() {
		//修复分页底部信息
		$("#userInfoDataGrid").datagrid("getPager").pagination("refresh",{total:correctTotal});
		//去除当前行序号
		$(".userInfoListDataBody td.datagrid-td-rownumber").last().html("");
	}
	
	//动态添加一行，显示合计数
	$("#userInfoDataGrid").datagrid("appendRow",rowJson);
	
	//必须要异步线程执行。否则修改分页显示失效。
	setTimeout(correct, 1);
}


/* ***********数据格式化***************** */
//格式化用户类型
function formatterLevel(val,row,index) {
	var result;
	switch(val) {
		case "1" : 
			result = "谷登农批";
			break;
		case "2" : 
			result = "农速通";
			break;
		case "3" : 
			result = "农商友";
			break;
		default:
			result = val;
			break;
	}
	return result;
}

function formatterCountNo(val, row, index){
	//去除0值的显示
	return val == "0" ? "" : val;
}

//格式化促成订单数量总计
function formatterOrderAmount(val,row,index) {
	//去除0值的显示
	return val == "0" ? "" : val;
}

//格式化促成订单金额总计
function formatterOrderPriceAmount(val,row,index) {
	return val ? parseFloat(val).toFixed(2) : "0.00";
}
