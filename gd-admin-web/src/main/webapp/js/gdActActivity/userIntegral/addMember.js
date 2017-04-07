
/**
 * 点击查询按钮，查询买家用户列表数据，并展示
 */
function loadbuyerlist() {
	var queryParams = {};
	queryParams.realName = $("#buyer_buyer #realName").val();
	queryParams.mobile = $("#buyer_buyer #mobile").val();
	queryParams.level = $("#buyer_buyer #level").val();
	queryParams.shopsName = $("#buyer_buyer #shopsName").val();
	queryParams.regetype = $("#buyer_buyer #regetype").val();
	queryParams.startDate = $("#buyer_buyer #startBuyerTime").val();
	queryParams.endDate = $("#buyer_buyer #endBuyerTime").val();
	queryParams.certstatus = $("#buyer_buyer #certstatus").val();
	// 数据加载
	var queryUrl = CONTEXT + 'member/querybysearch';
	$('#showbuyer_table').datagrid({
		url : queryUrl,
		width : 'auto',
		height : 'auto',
		nowrap : true,
		pageSize : 100,
		"queryParams" : queryParams,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		singleSelect : false,
		onLoadSuccess : function() {
			$("#showbuyer_table").datagrid('clearSelections');
		},
		columns : [ [ {
			field : 'realName',
			title : '用户姓名',
			width : 100,
			align : 'center'
		}, {
			field : 'mobile',
			title : '手机号码',
			width : 100,
			align : 'center'
		}, ] ]
	}); 
}

/**
 * 读取已选择的买家信息，显示在已选择列表
 */
function initbuyerProd() {
	$.each(userData, function(index, item) {
		$("#buyerselect").append('<option value="' + item.memberId + '">' + item.mobile + '</option>');
	});
}

/**
 * 添加数据
 * @param rows
 */
function addMember(rows)
{
	$.each(rows,function(x,y){
		
		var memberId = y.memberId;
		var mobile = y.mobile;
		//移除页面控件重复项
		$("#buyerselect option[value='"+memberId+"']").remove(); 
		//移除用户列表数据的重复项
		$.each(userData,function(index,item){
			if(item.memberId == memberId)
			{
				userData.splice(index, 1);
			}
		});
		
		//添加到页面，添加到用户列表
		$("#buyerselect").append('<option value="'+memberId+'">'+mobile+'</option>');
		var user = {};
		user.memberId = memberId;
		user.mobile = mobile;
		userData.push(user);
	});
}

/**
 * 添加选中行
 */
function addbuyerone(){
	var rows = $('#showbuyer_table').datagrid('getSelections');
	addMember(rows);
}
/**
 * 添加左边所有行
 */
function addbuyerall(){
	var rows = $('#showbuyer_table').datagrid('getData').rows;
	addMember(rows);
}


/**
 * 移除右边已选择用户列表的选中的一行
 */
function removebuyerone(){
	var memberId = $("#buyerselect option:selected").val();
	//清除列表
	$("#buyerselect option:selected").remove();
	//清除数据
	$.each(userData,function(index,item){
		if(item.memberId == memberId)
		{
			userData.splice(index, 1);
		}
	});
}

/**
 * 移除已选择的所有用户
 */
function removebuyerall(){
	$("#buyerselect option").remove();
	userData = [];
}
