
$(document).ready(function(){
	//数据加载
	$('#memberdg').datagrid({
		url:CONTEXT+'nst_member/query',
		//width: 1000,  
		height: 'auto', 
		nowrap:true,
		toolbar:'#membertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#memberdg").datagrid('clearSelections');
		},
		columns:[[
					{field:'memberId',title:'',width:0,checkbox:true},
					{field:'userTypeName',title:'用户类型',width:100,align:'center' },
					{field:'regetype',title:'注册来源',width:100,align:'center',formatter:formatterRegeType },
					{field:'account',title:'注册号码',width:100,align:'center'},
					{field:'mobile',title:'手机号码',width:100,align:'center'},
					{field:'createTime',title:'注册时间',width:100,align:'center',formatter:formatterdate   },
					{field:'nstCreateTime',title:'第一次登陆时间',width:100,align:'center'   },
					{field:'nstUserType',title:'个人/企业',width:100,align:'center'},
					{field:'areaName',title:'所属区域',width:100,align:'center' },
					{field:'nstStatus',title:'认证状态',width:100,align:'center' },
					{field:'accountStatus',title:'帐号状态',width:100,align:'center' },
					{field:'cityName',title:'常用城市',width:100,align:'center' },
					{field:'refereeRealName',title:'推荐人姓名',width:100,align:'center' },
					{field:'refereeMobile',title:'推荐人手机号码',width:100,align:'center' },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
				]]
	}); 
	//分页加载
	$("#memberdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});





// 查询按钮,根据name查询
$('#icon-search').click(function(){ 
 var queryParams = $('#memberdg').datagrid('options').queryParams;
	queryParams.account = $('#memberSearchForm #account').val();
	queryParams.mobile = $('#memberSearchForm #mobile').val();
	queryParams.level = $('#memberSearchForm #level').val();
	queryParams.isAuth = $('#memberSearchForm #isAuth').val();
	queryParams.startDate =  $('#memberSearchForm #startDate').val();
	queryParams.endDate =  $('#memberSearchForm #endDate').val();
	queryParams.areaName = $("#memberSearchForm #areaName").val();
	queryParams.regetype = $("#memberSearchForm #regetype").val();
	queryParams.status = $("#memberSearchForm #status").val();
	queryParams.loginStartDate = $("#memberSearchForm #loginStartDate").val();
	queryParams.loginEndDate = $("#memberSearchForm #loginEndDate").val();
	queryParams.cityName = $("#memberSearchForm #cityName").val();
	var queryUrl=CONTEXT+'nst_member/querybysearch';
	$('#memberdg').datagrid({
		url:queryUrl,
		height: 'auto', 
		nowrap:true,
		toolbar:'#membertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'memberId',title:'',width:0,checkbox:true},
					{field:'userTypeName',title:'用户类型',width:100,align:'center' },
					{field:'regetype',title:'注册来源',width:100,align:'center',formatter:formatterRegeType },
					{field:'account',title:'注册号码',width:100,align:'center'},
					{field:'mobile',title:'手机号码',width:100,align:'center'},
					{field:'createTime',title:'注册时间',width:100,align:'center',formatter:formatterdate   },
					{field:'nstCreateTime',title:'第一次登陆时间',width:100,align:'center'   },
					{field:'nstUserType',title:'个人/企业',width:100,align:'center'},
					{field:'areaName',title:'所属区域',width:100,align:'center' },
					{field:'nstStatus',title:'认证状态',width:100,align:'center' },
					{field:'accountStatus',title:'帐号状态',width:100,align:'center' },
					{field:'cityName',title:'常用城市',width:100,align:'center' },
					{field:'refereeRealName',title:'推荐人姓名',width:100,align:'center' },
					{field:'refereeMobile',title:'推荐人手机号码',width:100,align:'center' },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optFormat}
				]]
	}); 
	//分页加载
	$("#memberdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
	
});



//注册来源<!--  0管理后台，1谷登农批网，2农速通，3农商友，4农商友-农批商，5 农批友  -->
function formatterRegeType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="0"){
			return "管理后台";
		}else if(str=="1"){
			return "谷登农批网";
		}else if(str=="2"){
			return "农速通";
		}else if(str=="3"){
			return "农商友";
		}else if(str=="4"){
			return "农商友-农批商";
		}else if(str=="5"){
			return "农批友";
		}else if(str=="6"){
			return "供应商";
		}else if(str=="7"){
			return "POS刷卡";
		}else if(str=="8"){
			return "微信授权";
		}
	}else{
		return "未知来源";
	}
}


function formatterdate(val, row) {
		if (val != null) {
		   var  str=val.toString();
		   str =  str.replace(/-/g,"/");
		   var date = new Date(str);
		   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
			+ date.getDate();
		}
	}


 
 //查看
 function showObj(id){
		$('#showDialog').dialog({'title':'会员信息','href':CONTEXT+'nst_member/showbyid/'+id,'width': 550,'height': 350}).dialog('open');
 }

	//重置
	$('#btn-reset').click(function(){
		$('#memberSearchForm')[0].reset();
	});
	
	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#memberSearchForm')[0].reset();
		$("#memberdg").datagrid('load',{});
		//重启导出功能
		disableExport = false ;
	});