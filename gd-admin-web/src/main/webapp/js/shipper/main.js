
$(document).ready(function(){
	var params={"state":$("#state").val(),"status":$("#status").val()};
	loadentranceWeighInfo(params,CONTEXT+'shipper/entranceWeighList');
});

//查询按钮
$('#icon-search').click(function(){ 
	var params={"state":$("#state").val(),
			"status":$("#status").val(),
			"account":$("#account").val(),
			"carNumber":$("#carNumber").val(),
			"tareStartTime":$("#tareStartTime").val(),
			"tareEndTime":$("#tareEndTime").val(),
			"totalStartTime":$("#totalStartTime").val(),
			"totalEndTime":$("#totalEndTime").val(),
			"tapWeight":$("#tapWeight").val()};
	loadentranceWeighInfo(params,CONTEXT+'shipper/entranceWeighList');
	
});

function loadentranceWeighInfo(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#entranceWeighdg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#entranceWeightb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'weighCarId',title:'', hidden:true},
					{field:'carNumber',title:'车辆车牌',width:100,align:'center'},
					{field:'tareCreateTime',title:'皮重登记时间',width:100,align:'center'},
					{field:'totalCreateTime',title:'总重登记时间',width:100,align:'center'},
					{field:'account',title:'用户账号',width:100,align:'center'},
					{field:'memberName',title:'姓名',width:100,align:'center'},
					{field:'tare',title:'皮重(吨)',width:100,align:'center'},
					{field:'totalWeight',title:'总重(吨)',width:100,align:'center'},
					{field:'netWeight',title:'净重(吨)',width:100,align:'center'},
					{field:'weigh',title:'产品总重',width:100,align:'center'},
					{field:'rates',title:'误差率',width:100,align:'center',formatter:errorState},
					{field:'weighMember',title:'过磅人员',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#entranceWeighdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
}

function entranceWeighInfo(weighCarId,weigh){
	var status=$("#status").val();
	if(typeof(weigh) == "undefined"){
		weigh="";
	}
	$('#entranceWeighInfoDialog').dialog({'title':'过磅详细信息', 'width':900, 'height':500, 'href':CONTEXT+'shipper/entranceWeighInfo?weighCarId='+weighCarId+'&status='+status+'&weigh='+weigh}).dialog('open');
}

function errorState(val){
	if(val!="" && val!=null){
		state=parseFloat(val)*0.1;
		minState=(parseFloat(val)-state).toFixed(2);
		mxaState=(parseFloat(val)+state).toFixed(2);
		
		s=minState+"~"+mxaState;
		
		return s;
	}else{
		return val;
	}
}

$("#icon-entranceWeighrefresh").click(function(){
	$("#account").val("");
	$("#carNumber").val("");
	$("#tareStartTime").val("");
	$("#totalStartTime").val("");
	$("#tareEndTime").val("");
	$("#totalEndTime").val("");
	$("#tapWeight").val("");
	$("#entranceWeighdg").datagrid('load',{state:'1',status:'1'});
});
$("#icon-outWeighrefresh").click(function(){
	$("#account").val("");
	$("#carNumber").val("");
	$("#tareStartTime").val("");
	$("#totalStartTime").val("");
	$("#tareEndTime").val("");
	$("#totalEndTime").val("");
	$("#tapWeight").val("");
	$("#entranceWeighdg").datagrid('load',{state:'2',status:'2'});
});
$("#btn-reset").click(function(){
	$("#account").val("");
	$("#carNumber").val("");
	$("#tareStartTime").val("");
	$("#totalStartTime").val("");
	$("#tareEndTime").val("");
	$("#totalEndTime").val("");
	$("#tapWeight").val("");
});

/***数据导出功能***/
$("#exportData").click(function(){
	
	var queryParams = $('#entranceWeighdg').datagrid('options').queryParams;
	queryParams.state = $('#state').val();
	queryParams.status = $('#status').val();
	queryParams.account = $('#account').val();
	queryParams.carNumber = $('#carNumber').val();
	queryParams.tareStartTime = $('#tareStartTime').val();
	queryParams.tareEndTime = $("#tareEndTime").val();
	queryParams.totalStartTime = $("#totalStartTime").val();
	queryParams.totalEndTime = $("#totalEndTime").val();
	queryParams.tapWeight = $("#tapWeight").val();

	
	window.location.href=CONTEXT+'shipper/exportData?state='+queryParams.state+
	"&account="+queryParams.account
	+"&carNumber="+queryParams.carNumber
	+"&tareStartTime="+queryParams.tareStartTime
	+"&tareEndTime="+queryParams.tareEndTime
	+"&totalStartTime="+queryParams.totalStartTime
	+"&totalEndTime="+queryParams.totalEndTime
	+"&tapWeight="+queryParams.tapWeight
	+"&status="+queryParams.status;
});

