
$(document).ready(function(){
	//数据加载
	$('#supptelstatdg').datagrid({
		url:CONTEXT+'supptelstat/querybysearch',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#supptelstattb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#supptelstatdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'source',title:'拨打来源',width:100,align:'center'},
					{field:'s_Mobile',title:'被叫号码',width:100,align:'center'},
					{field:'b_level',title:'被叫客户端',width:100,align:'center',formatter:formatterLevel},
					{field:'s_Name',title:'被叫号码姓名',width:100,align:'center'},
					{field:'e_Mobile',title:'主叫号码',width:100,align:'center'},
					{field:'level',title:'主叫客户端',width:100,align:'center',formatter:formatterLevel},
					{field:'e_Name',title:'主叫号码姓名',width:100,align:'center'},
					{field:'createTimeStr',title:'拨打时间',width:100,align:'center'}
				]]
	}); 
	//分页加载
	$("#supptelstatdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});





// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
 var queryParams = $('#supptelstatdg').datagrid('options').queryParams;
	queryParams.e_Mobile = $('#suppTelStatForm #e_Mobile').val();
	queryParams.shopName = $('#suppTelStatForm #shopName').val();
	queryParams.startDate =  $('#suppTelStatForm #startDate').val();
	queryParams.endDate =  $('#suppTelStatForm #endDate').val();
	
	var queryUrl=CONTEXT+'supptelstat/querybysearch';
	
	$('#supptelstatdg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#supptelstattb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'source',title:'拨打来源',width:100,align:'center',formatter:formatterSource},
					{field:'s_Mobile',title:'被叫号码',width:100,align:'center'},
					{field:'b_level',title:'被叫客户类型',width:100,align:'center',formatter:formatterLevel},
					{field:'s_Name',title:'被叫号码姓名',width:100,align:'center'},
					{field:'e_Mobile',title:'主叫号码',width:100,align:'center'},
					{field:'level',title:'主叫客户类型',width:100,align:'center',formatter:formatterLevel},
					{field:'e_Name',title:'主叫号码姓名',width:100,align:'center'},
					{field:'createTimeStr',title:'拨打时间',width:100,align:'center'}
				]]
	}); 
	//分页加载
	$("#supptelstatdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});

function formatterSource(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="INDEX"){
			return "首页";
		}else if(str=="HYXQ"){
			return "货源详情";
		}else if(str=="WYZC"){
			return "我要找车";
		}else if(str=="HYFB"){
			return "货源发布";
		}else if(str=="DPGZ"){
			return "单品关注";
		}else if(str=="SYDP"){
			return "所有店铺(农批商)";
		}else if(str=="DPXQ"){
			return "店铺详情";
		}else if(str=="GZNPS"){
			return "关注的农批商";
		}else if(str=="WYZH"){
			return "我要找货";
		}else if(str=="DDXQ"){
			return "订单详情";
		}else if(str=="KHLXR"){
			return "客户联系人";
		}else if(str=="TJ"){
			return "推荐";
		}else if(str=="SPSY"){
			return "商铺首页";
		}else if(str=="HHY"){
			return "好货源";
		}else if(str=="KHLB"){
			return "客户列表";
		}else{
			return val;
		}
	} 
}

function formatterLevel(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "谷登农批";
		}else if(str=="2"){
			return "农速通";
		}else if(str=="3"){
			return "农商友";
		}else if(str=="4"){
			return "供应商";
		}
		return "";
	} 
}


function formatterSysCode(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "农商友";
		}else if(str=="2"){
			return "农速通";
		}else if(str=="3"){
			return "供应商";
		}
		return "";
	} 
}
