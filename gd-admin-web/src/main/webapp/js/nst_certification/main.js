
$(document).ready(function(){
	//数据加载
	$('#certifidg').datagrid({
		url:CONTEXT+'nst_certification/query',
		//width: 1000,
		height: 'auto',
		nowrap:true,
		toolbar:'#certifitb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#certifidg").datagrid('clearSelections');
		},
		columns:[[
					{field:'certifiId',title:'',width:0,checkbox:true},
					{field:'level',title:'用户类型',width:100,align:'center',formatter:formatterLevel },
					{field:'account',title:'注册号码',width:100,align:'center'},
					{field:'nst_linkMan',title:'姓名',width:100,align:'center'},
					{field:'type',title:'个人/企业',width:100,align:'center'},
					{field:'updateTime',title:'申请时间',width:100,align:'center'    },
					{field:'nstStatus',title:'认证状态',width:100,align:'center',formatter:formatterAuth },
					{field:'auditTime',title:'审核时间',width:100,align:'center'    },
					{field:'memberName',title:'审核人',width:100,align:'center'},
					{field:'isActivi',title:'激活状态',width:100,align:'center',formatter:formatterStatus },
					{field:'areaName',title:'所属区域',width:100,align:'center'  },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	});
	//分页加载
	$("#certifidg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

});



// 查询按钮,根据name查询
$('#icon-search').click(function(){
 var queryParams = $('#certifidg').datagrid('options').queryParams;
	queryParams.account = $('#certifiSearchForm #account').val();
	queryParams.status = $('#certifiSearchForm #status').val();
	queryParams.startDate =  $('#certifiSearchForm #startDate').val();
	queryParams.endDate =  $('#certifiSearchForm #endDate').val();
	queryParams.areaName =  $('#certifiSearchForm #areaName').val();
	queryParams.certifiType =  $('#certifiSearchForm #certifiType').val();

	var queryUrl=CONTEXT+'nst_certification/querybysearch';
	$('#certifidg').datagrid({
		url:queryUrl,
		height: 'auto',
		nowrap:true,
		toolbar:'#certifitb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'certifiId',title:'',width:0,checkbox:true},
					{field:'level',title:'用户类型',width:100,align:'center',formatter:formatterLevel },
					{field:'account',title:'注册号码',width:100,align:'center'},
					{field:'nst_linkMan',title:'姓名',width:100,align:'center'},
					{field:'type',title:'个人/企业',width:100,align:'center'},
					{field:'updateTime',title:'申请时间',width:100,align:'center'    },
					{field:'nstStatus',title:'认证状态',width:100,align:'center',formatter:formatterAuth },
					{field:'auditTime',title:'审核时间',width:100,align:'center'    },
					{field:'memberName',title:'审核人',width:100,align:'center'},
					{field:'isActivi',title:'激活状态',width:100,align:'center',formatter:formatterStatus },
					{field:'areaName',title:'所属区域',width:100,align:'center'  },
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	});
	//分页加载
	$("#certifidg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

});










//会员类别（1谷登农批，2农速通，3农商友，4，产地供应商 ，其余待补）
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
			return "产地供应商";
		}
	}
}


function formatterAuth(val, row) {
	if (val != null) {
	   var  str=val.toString();
	   if(str=="1"){
		   return "已认证";
	   }else if(str=="0"){
		   return "待认证";
	   }else if(str=="2"){
		   return "已驳回";
	   }
	}
}

function formatterStatus(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="1"){
			return "启用";
		}else if(str=="0"){
			return "禁用";
		}
	}else{
		return "禁用";
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

function save() {
	var url=CONTEXT+"nst_certification/save";
		jQuery.post(url, $('#addForm').serialize(), function (data) {
			if (data == "success") {
				slideMessage("操作成功！");
				//刷新父页面列表
				$("#certifidg").datagrid('reload');
				$('#addDialog').dialog('close');
			} else {
				warningMessage(data);
				return;
			}
		});
}



//审核
$("#icon-show").click(function(){

	var row = $('#certifidg').datagrid("getSelections");
    if($(row).length < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    }

    var deleteStr = getSelections("certifiId");
	if($(row).length  >1){
    	warningMessage("请不要操作多条数据！");
	    return;
	}
	$('#showDialog').dialog({'title':'审核','href':CONTEXT+'nst_certification/showbyid/'+deleteStr,'width': 700,'height': 500}).dialog('open');
});




 //查看
 function showObj(id){
		$('#showDialog').dialog({'title':'审核','href':CONTEXT+'nst_certification/showbyid/'+id,'width': 700,'height': 500}).dialog('open');

 }

 function unpassShow(){
	 	var certifiId= $('#certifiId')[0].value;
		$('#unpassDialog').dialog({'title':'驳回原因', 'width':700, 'height':300, 'href':CONTEXT+'nst_certification/unpassShow/'+certifiId}).dialog('open');
}

//重置
$('#btn-reset').click(function(){
	$('#certifiSearchForm')[0].reset();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$('#certifiSearchForm')[0].reset();
	$("#certifidg").datagrid('load', {});
	//重启导出功能
	disableExport = false ;
});
