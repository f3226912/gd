
$(document).ready(function(){
	//数据加载
	$('#argtelstatdg').datagrid({
		url:CONTEXT+'argtelstat/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#argtelstattb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#argtelstatdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'source',title:'拨打来源',width:100,align:'center'},
					{field:'s_Mobile',title:'被叫号码',width:100,align:'center'},
					{field:'s_Name',title:'被叫号码姓名',width:100,align:'center'},
					{field:'shopName',title:'商铺名称',width:100,align:'center'},
					{field:'e_Mobile',title:'主叫号码',width:100,align:'center'},
					{field:'e_Name',title:'主叫号码姓名',width:100,align:'center'},
					{field:'createTime',title:'拨打时间',width:100,align:'center'}
				]]
	}); 
	//分页加载
	$("#argtelstatdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});





// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
 var queryParams = $('#argtelstatdg').datagrid('options').queryParams;
	queryParams.e_Mobile = $('#argTelStatForm #e_Mobile').val();
	queryParams.shopName = $('#argTelStatForm #shopName').val();
	queryParams.startDate =  $('#argTelStatForm #startDate').val();
	queryParams.endDate =  $('#argTelStatForm #endDate').val();
	
	var queryUrl=CONTEXT+'argtelstat/querybysearch';
	
	$('#argtelstatdg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#argtelstattb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'source',title:'拨打来源',width:100,align:'center',formatter:formatterSource},
					{field:'s_Mobile',title:'被叫号码',width:100,align:'center'},
					{field:'s_Name',title:'被叫号码姓名',width:100,align:'center'},
					{field:'shopName',title:'商铺名称',width:100,align:'center'},
					{field:'e_Mobile',title:'主叫号码',width:100,align:'center'},
					{field:'e_Name',title:'主叫号码姓名',width:100,align:'center'},
					{field:'createTime',title:'拨打时间',width:100,align:'center'}
				]]
	}); 
	//分页加载
	$("#argtelstatdg").datagrid("getPager").pagination({
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
