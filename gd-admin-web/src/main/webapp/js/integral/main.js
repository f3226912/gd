
function loadIntegralData(params){
	params = !params ? {}: params;
	//数据加载
	$('#integraldg').datagrid({
		url:CONTEXT+'integral/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#integraltb',
		pageSize:10,
		queryParams: params,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#integraldg").datagrid('clearSelections');
		},
		columns:[[
		          
			{field:'id',title:'',width:100,checkbox:true},
			{field:'memberAccount',title:'账号',width:100,align:'center'},
			{field:'userType',title:'用户类型',width:100,align:'center', formatter:formatterUserType},
			{field:'companyName',title:'企业名称',width:100,align:'center'},
			{field:'linkMan',title:'联系人',width:100,align:'center'},
			{field:'type',title:'积分类型',width:100,align:'center',formatter:formatterIntegralType},
			{field:'integral',title:'积分',width:100,align:'center'},
			{field:'activityName',title:'活动名称',width:100,align:'center'},
			{field:'description',title:'描述',width:100,align:'center'},
			{field:'createUserAccount',title:'操作人账号',width:100,align:'center'},
			{field:'createTime',title:'创建时间',width:100,align:'center'},
			{field:'opt',title:'操作', width:100, align:'center', formatter: optformat}
		]]
	}); 
	//分页加载
	$("#integraldg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

$(document).ready(function(){
	loadIntegralData({});
});

//查询按钮,根据name查询
$('#integral-btn-search').click(function(){ 
	var activityId = $('#integralForm #activityId').val();
	var memberAccount = $('#integralForm #memberAccount').val();
	var userType = $('#integralForm #userType').val()
	var type = $('#integralForm #type').val();
	var queryStartDate = $('#integralForm #queryStartDate').val();
	var queryEndDate = $('#integralForm #queryEndDate').val();
	var params = {'activityId':activityId, 'memberAccount': memberAccount, 'userType':userType, 'type':type,'queryStartDate':queryStartDate, 'queryEndDate':queryEndDate};
	loadIntegralData(params);
});


function formatterIntegralType(val, row) {
	if (val != null) {
		var str=val.toString();
		if(str=="1"){
			return "推广积分";
		}else if(str=="2"){
			return "积分兑换";
		}else if(str=="3"){
			return "推荐人绑定";
		}else if(str=="4"){
			return "管理员管理积分";
		}
	} 
}

function formatterIntegral(val, row){
	if (val != null) {
		var str=row.type.toString();
		if(str=="1" || str=="3" || str=="4"){
			return "+"+val;
		}else if(str=="2"){
			return "-"+val;
		}
	}else{
		return "0";
	}
}

function formatterUserType(val, row){
	if (val != null) {
		var str=val.toString();
		if(str=="1"){
			return "个人";
		}else if(str=="2"){
			return "企业";
		}
	}
}

$('#integral-btn-reset').click(function(){
	$('#btn-activity-select').attr('value','选择活动');
	$('#activityId').val('');
	$('#integralForm')[0].reset();
});

$("#integral-btn-refresh").click(function(){
	$('#btn-activity-select').attr('value','选择活动');
	$('#activityId').val('');
	$('#integralForm')[0].reset();
	$("#integraldg").datagrid('load',{});
});

$("#btn-activity-select").click(function(){
	$('#activityDialog').dialog({'title':'选择活动', 'width':800, 'height':300, 'href':CONTEXT+'integral/activitySelect'}).dialog('open');
});

function activitySelectCallback(id, name){
	$("#activityId").val(id);
	$("#btn-activity-select").val(name);
	$('#activityDialog').dialog("close");
}

function integralReturn(id){
	$.messager.confirm("提示","是否进行积分回退操作？", function(data){
		if(data){
			$.ajax({
		        type: "POST",
		        url:CONTEXT+'integral/return' ,
		        data: {'id':id},
		        success: function(data) {
		           if(data == "success"){
		        	   slideMessage("操作成功！");
		        	   $('#integraldg').datagrid('reload');
		           }else if(data == 'typeErr'){
		        	   warningMessage("只能对积分类型为积分兑换的记录进行积分回退操作！");
		           }else if(data == 'isReturnErr'){
		        	   warningMessage("该记录已经进行过积分回退操作！");
		           }
		        }
		    });
		}
	});
}
