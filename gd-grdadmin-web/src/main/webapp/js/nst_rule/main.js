$(document).ready(function(){
	load(null,CONTEXT+'nstRule/getNstGoodAssignRuleList');
});
$('#icon-search').click(function(){ 
	var params={"account":$("#account").val(),
			"provinceId":$("#s_provinceId").val(),
			"cityId":$("#s_cityId").val(),
			"memberId":$("#s_areaId").val(),
			"isEffective":$("#isEffective").val(),
			"assignStartBeginTime":$("#assignStartBeginTime").val(),
			"assignStartEndTime":$("#assignStartEndTime").val(),
			"assignEndBeginTime":$("#assignEndBeginTime").val(),
			"assignEndEndTime":$("#assignEndEndTime").val(),
			"assignSumQueryStartTime":$("#assignSumQueryStartTime").val(),
			"assignSumQueryEndTime":$("#assignSumQueryEndTime").val()};
	load(params,CONTEXT+'nstRule/getNstGoodAssignRuleList');
});

function load(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#nstGoodAssignRuledg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#nstGoodAssignRuletb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(data){ 
			$("#nstGoodAssignRuledg").datagrid('clearSelections');
			if(data.rules==0){
				$("#icon-ok").hide();
				$("#icon-no").hide();
				$("#span1").hide();
				$("#isEffective").hide();
			}
			if(data.rules==1){
				$("#icon-ok").show();
				$("#icon-no").show();
				$("#span1").show();
				$("#isEffective").show();
			}
        },
		columns:[[
					{field:'id',title:'',checkbox:true},
					{field:'memberId',hidden:'true'},
					{field:'provinceName',title:'所属区域省份',width:100,align:'center'},
					{field:'cityName',title:'所属区域(市)',width:100,align:'center'},
					{field:'companyName',title:'信息部名称',width:100,align:'center'},
					{field:'dayAssignMax',title:'当日分配上限(条)',width:100,align:'center'},
					{field:'monthAssignMax',title:'当月分配上限(条)',width:100,align:'center'},
					{field:'assignStartTime',title:'分配有效开始时间',width:100,align:'center'},
					{field:'assignEndTime',title:'分配有效结束时间',width:100,align:'center'},
					{field:'assignedNum',title:'已分配信息数',width:100,align:'center',formatter:assignedNum},
					{field:'isEffective',title:'规则状态',width:100,align:'center',formatter:isEffective},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#nstGoodAssignRuledg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function assignedNum(val,row){
	if(typeof(row.dayAssignMax) == "undefined"){
		return "";
	}else{
		return "<a class='operate' href='javascript:viewDeptGoodList("+row.memberId+",\""+row.assignStartTime+"\",\""+row.assignEndTime+"\")'>"+val+"</a>";
	}
}
function viewDeptGoodList(deptMemberId, assignStartTime, assignEndTime){
	var url = CONTEXT+'nstRule/deptGoodList/'+deptMemberId+'?assignStartTime='+assignStartTime+'&assignEndTime='+assignEndTime;
	$('#deptGoodDialog').dialog({'title':'已分配货源', 'width':900, 'height':400, 'href':url}).dialog('open');
}
function isEffective(val,row){
	if(row.rule==0){
		return '无效';
	}
	if(val==0 && row.dayAssignMax!='' && row.dayAssignMax!=null){
		return '有效';
	}else if(val==1){
		return '无效';
	}else{
		return '无效';
	}
}
function optformat(value,row,index){
	if(row.isEffective==1 || row.rule==0){
		return "<a class='operate' href='javascript:;' onclick=detail('"+row.id+"');>详情</a>";
	}
	if((row.assignStartTime==null || row.assignStartTime=='')){
		return "<a class='operate' href='javascript:;' onclick=assign('"+row.id+"');>分配</a> "
		+"<a class='operate' href='javascript:;' onclick=detail('"+row.id+"');>详情</a>";
	}else{
		return "<a class='operate' href='javascript:;' onclick=update('"+row.id+"','"+row.dayAssignMax+"','"+row.monthAssignMax+"','"+row.assignStartTime+"','"+row.assignEndTime+"');>修改</a> "
		+"<a class='operate' href='javascript:;' onclick=detail('"+row.id+"');>详情</a>";
	}
}
$('#btn-add').click(function(){
	$('#addDialog').dialog({'title':'规则设置', 'width':1100, 'height':450, 'href':CONTEXT+'nstRule/setting'}).dialog('open');
});

function detail(id){
	$('#detailDialog').dialog({'title':'规则详情', 'width':500, 'height':400, 'href':CONTEXT+'nstRule/detail/'+id}).dialog('open');
}
function assign(id){
	$('#assignDialog').dialog({'title':'分配规则', 'width':500, 'height':400, 'href':CONTEXT+'nstRule/assign/?id='+id}).dialog('open');
}
function update(id,dayAssignMax,monthAssignMax,assignStartTime,assignEndTime){
	$('#updateDialog').dialog(
			{'title':'修改规则',
				'width':500, 
				'height':400,
				'href':CONTEXT+'nstRule/updateAssign?id='+id+'&dayAssignMax='+dayAssignMax
				+'&monthAssignMax='+monthAssignMax
				+'&assignStartTime='+assignStartTime
				+'&assignEndTime='+ assignEndTime}
	).dialog('open');
}

$("#icon-refresh").click(function(){
	$("#nstGoodAssignRuleSearchForm")[0].reset();
	del();
	$("#nstGoodAssignRuledg").datagrid('load',{});
});

$("#btn-reset").click(function(){
	del();
	$("#nstGoodAssignRuleSearchForm")[0].reset();
});
function del(){
	$("#s_cityId").empty();
	$("#s_cityId").append("<option selected='selected' value=''>选择城市</option>");
	$("#s_areaId").empty();
	$("#s_areaId").append("<option selected='selected' value=''>选择区域</option>");
}
$("#s_provinceId").change(function(){
	del();
	$.get("getCity/"+$("#s_provinceId").val(),function(data){
		if(data.cityList!=""){
			var result="<option selected='selected' value=''>选择城市</option>";
			$.each(data.cityList,function(n,value){
				result +="<option value='"+value.areaID+"'>"+value.area+"</option>";
			});
			$("#s_cityId").html('');
			$("#s_cityId").append(result);
		};
	},"json");
});
$("#s_cityId").change(function(){
	$.get("getQueryArea/"+$("#s_cityId").val(),function(data){
		if(data.areaList!=""){
			var result="<option selected='selected' value=''>选择信息部</option>";
			$.each(data.areaList,function(n,value){
				result +="<option value='"+value.memberId+"'>"+value.companyName+"</option>";
			});
			$("#s_areaId").html('');
			$("#s_areaId").append(result);
		};
	},"json");
});

//启用
$("#icon-ok").click(function(){
	var row = $('#nstGoodAssignRuledg').datagrid("getSelections");
    if($(row).length < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    }
    var idStr = getSelections("id");
	jQuery.post(CONTEXT+"nstRule/updatestatus/",{"idStr":idStr,"isEffective":0},function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#nstGoodAssignRuledg').datagrid('reload');
			$('#nstGoodAssignRuledg').datagrid("uncheckAll");
		}else{
			warningMessage(data);
			return;
		}
	});
});

//禁用
$("#icon-no").click(function(){
	var row = $('#nstGoodAssignRuledg').datagrid("getSelections");
    if($(row).length < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    }
    var idStr = getSelections("id");
	jQuery.post(CONTEXT+"nstRule/updatestatus/",{"idStr":idStr,"isEffective":1},function(data){
		if (data == "success"){
			slideMessage("操作成功！");
			$('#nstGoodAssignRuledg').datagrid('reload');
			$('#nstGoodAssignRuledg').datagrid("uncheckAll");
		}else{
			warningMessage(data);
			return;
		}
	});
});




