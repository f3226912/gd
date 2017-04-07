//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
}

/*
 * 保存补贴规则暂时运用
 */
var subRuleJsonObject;

//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	params.queryState=$("#hdQueryState").val();
	//数据加载
	$('#datagrid-table').datagrid({
		url:CONTEXT+'gdActActivity/query',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#datagrid-tool-bar',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:false,
		onLoadSuccess:function(){
			$("#datagrid-table").datagrid('clearSelections');
		},
		columns:[[
			{field:'id',title:'',width:50, checkbox:true},
			{field:'code',title:'活动编号',width:100,align:'center'},
			{field:'name',title:'活动名称',width : 100,align:'center'},
			{field:'type',title:'活动类型',width:100,align:'center', formatter : formatActivityType},
			{field:'createTimeString',title:'创建时间',width:100,align:'center'},
			{field:'state',title:'活动状态',width:100,align:'center', formatter : formatActivityState},
			{field:'marketName',title:'所属市场',width:100,align:'center'},
			{field:'startTimeString',title:'活动开始时间',width:100,align:'center'},
			{field:'endTimeString',title:'活动结束时间',width:100,align:'center'},
			{field:'createUserId',title:'创建人',width:100,align:'center'},
			{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}			
		]]
	}); 
	$("#datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
}
function optformat(value,row,index){
	var state=$("#hdQueryState").val();
	var options="";
	if(state&&state=="1"){
		/*
		if(new Date(row.startTime) > new Date()) {
			}
		 */
		if(row.state==0) {
			options += "&nbsp;<a class='operate' href='javascript:;' onclick=edit('"+row.id+"','"+row.type+"') >编辑</a>";
		} else {
			options = "&nbsp;<a class='operate' href='javascript:;' onclick=activityDetail('"+row.id+"','"+row.type+"')>查看</a>";
		}
		options += "&nbsp;<a class='operate' href='javascript:;' onclick=operate('"+row.code+"','"+row.state+"','" + row.type + "') >"+operateView(row.state)+"</a>";
	}else{
		options = "<gd:btn btncode='BTNSPGLQBCP04'><a class='operate' href='javascript:;' onclick=activityDetail('"+row.id+"','"+row.type+"')>查看</a></gd:btn>";
	}
	
	return options;
}
function operate(code,state,type){
	if(state=="0")newState="1";
	if(state=="1")newState="0";

	jQuery.messager.confirm('提示', '您确定要修改所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "gdActActivity/updateStatus", {
				"code" : code,"state":newState, "type":type
			}, function(data) {
				if (data.msg == "success") {
					slideMessage("操作成功！");
					$('#datagrid-table').datagrid('reload');
					$('#datagrid-table').datagrid("uncheckAll");
				} else {
					warningMessage(data.msg);
					return false;
				}
			});
		} else {
			return false;
		}
	});
}

function operateView(status){
	var result="禁用";
	if(status=="0"){
		result="启用";
	}
	return result;
}
function formatActivityType(value,row,index){
	var result = "";
	if (value == 1){
		result = "刷卡补贴";
	} else if(value == 2) {
		result = "市场佣金";
	} else if(value == 3) {
		result = "平台佣金";
	} else if(value == 4) {
		result = "预付款/违约金";
	} else if(value == 5) {
		result = "物流配送";
	}
	else if(value == 6) {
		result = "采购积分";
	}
	return result;
}
function formatActivityState(value,row,index){
	var result = "";
	if (value == 0){
		result = "禁用"
	}else if (value == 1){
		result = "启用";
	}else if (value == 2){
		result = "结束";
	}
	return result;
}
function query(){
	var activityCode = $('#datagrid-form #activityCode').val();
	var activityName = $('#datagrid-form #activityName').val();
	var activityType = $('#datagrid-form #activityType').val();
	var state = $('#datagrid-form #state').val();
	var marketId = $('#datagrid-form #marketId').val();
	var startDate = $('#datagrid-form #startDate').val();
	var endDate = $('#datagrid-form #endDate').val();
	var params = {
		"activityCode" : activityCode,
		"activityName" : activityName,
		"activityType" : activityType,
		"state" : state,
		"marketId" : marketId,
		"startDate" : startDate,
		"endDate" : endDate
	};
	loadList(params);
	//重启导出功能
	disableExport = false ;
}

function reset(){
	$('#datagrid-form')[0].reset();
	//重启导出功能
	disableExport = false ;
}

function refresh(){
	//var params ={};
	//params.queryState=$("#hdQueryState").val();
	//$('#datagrid-form')[0].reset();
	query();
	//重启导出功能
	disableExport = false ;
}


$(function(){
	initList();
	//查询按钮
	$('#btn-search').click(function(){
		query();
	});
	
	//刷新按钮
	$('#btn-refresh').click(function(){
		refresh();
	});
	
	//重置按钮
	$('#btn-reset').click(function(){
		reset();
	});
	
	 // 新增
	$('#showActivityWin').click(function() {
		debugger;
		$('#selectActivity').dialog({'title':'新增活动', 'width':400, 'height':300, 'href':CONTEXT+'gdActActivity/gdActActivity_edit_add'}).dialog('open');
		}); 
	
	// 修改
	$('#btn-edit').click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		} else if($(row).length == 1){
			edit(getSelections("id"));
		} else if($(row).length > 1){
			warningMessage("请不要选择多条数据！");
			return false;
		}
	});
	
	// 删除操作
	$("#btn-remove").click(function() {
		var row = $('#datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		}
		deleteObj(getSelections("id"));
	});
});


/**修改操作
 * @param id 当前对象ID
 */

function edit(id, type) {
	if(type==1) {
		$('#activityDialog').dialog({'title':'编辑活动', 'width':800, 'height':400, 'href':CONTEXT+'gdActActivityShaKa/edit/'+id}).dialog('open');

	} else if(type==2) {
		$('#activityDialog').dialog({'title':'编辑活动', 'width':800, 'height':400, 'href':CONTEXT+'gdactMarketCommission/toEdit/'+id}).dialog('open');

	} else if(type==3) {
		$('#activityDialog').dialog({'title':'编辑活动', 'width':800, 'height':400, 'href':CONTEXT+'gdActivityInformation/edit/'+id}).dialog('open');

	} else if(type==4) {
		$('#activityDialog').dialog({'title':'编辑活动', 'width':800, 'height':400, 'href':CONTEXT+'gdAdvancePayPenalSum/edit/'+id}).dialog('open');

	} else if(type==5) {
		$('#activityDialog').dialog({'title':'编辑活动', 'width':800, 'height':400, 'href':CONTEXT+'gdDistActivity/edit/'+id}).dialog('open');
	}
	 else if(type==6) {
			$('#activityDialog').dialog({'title':'编辑活动', 'width':800, 'height':400, 'href':CONTEXT+'gdProcurementIntegration/edit/'+id}).dialog('open');
		}
	
	
}

function activityDetail(id, type){
	var url = "";
	debugger;
	if(type==1) {
		url = 'gdActActivityShaKa/edit/'+id + '?views=1';

	} else if(type==2) {
		url = 'gdactMarketCommission/toEdit/'+id + '?views=1';

	} else if(type==3) {
		url = 'gdActivityInformation/edit/'+id + '?views=1';

	} else if(type==4) {
		url = 'gdAdvancePayPenalSum/edit/'+id + '?views=1';

	} else if(type==5) {
		url = 'gdDistActivity/edit/'+id + '?views=1';
	}
	else if(type==6) {
		url = 'gdProcurementIntegration/edit/'+id + '?views=1';
	}
	
	$('#activityDialog').dialog({
      title : '查看数据',
      width : 800,
      height : 450,
      closed : false,
      cache : false,
      href : CONTEXT + url,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('close');
      },
  });
}
// 删除操作
function deleteObj(id) {
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "gdActActivity/delete", {
				"ids" : id
			}, function(data) {
				if (data.msg == "success") {
					slideMessage("操作成功！");
					$('#datagrid-table').datagrid('reload');
					$('#datagrid-table').datagrid("uncheckAll");
				} else {
					warningMessage(data.msg);
					return false;
				}
			});
		} else {
			return false;
		}
	});
}


$('#marketId').combobox({
	valueField:'keyString',
	textField:'valueString',
	url: CONTEXT+'gdActActivity/marketPairs',
	editable:false,
	onSelect:function(record){
		var marketId = record.keyString ;
		$('#marketId').val(marketId);
	},
	onLoadSuccess : function(){
		var data = $('#marketId').combobox('getData');
		$('#marketId').combobox('select', data[0].keyString);
	}
});

