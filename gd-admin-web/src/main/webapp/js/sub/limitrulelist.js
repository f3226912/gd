//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#limitruleList").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#limitruleList').datagrid({
		url:CONTEXT+'sublimitrule/limitRuleQuery',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#limitruletb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#limitruleList").datagrid('clearSelections');
		},
		//marketId,timeStart,timeEnd,subAmount,whiteLimit,contact,`status`,createUserId,marketStr,userName
		//
		columns:[[
			//{field:'id',title:'',width:0,checkbox:true},
			//{field:'ruleId',title:'序号',width:0},
			{field:'marketStr',title:'市场名称',width:100,align:'center'},
			{field:'timeStart',title:'开始时间',width:100,align:'center',formatter:fmtTime},
			{field:'timeEnd',title:'结束时间',width:100,align:'center',formatter:fmtTime},
			//{field:'marketId',title:'市场',width:100,align:'center',formatter:formatMkt},
			{field:'subAmount',title:'补贴总额',width:100,align:'center'},
			{field:'subAmountBal',title:'当前余额',width:100,align:'center'},
			{field:'whiteLimit',title:'白名单',width:100,align:'center',formatter:formatWhite},
			{field:'contact',title:'紧急联系人',width:100,align:'center'},	
			{field:'status',title:'活动状态',width:100,align:'center',formatter:formatStatus},	
			{field:'userName',title:'添加人',width:100,align:'center'},			
			{field:'opt',title:'操作',width:100,align:'center',formatter:optfun}
		]]
		   
	}); 
}

var fmtTime = function(value,row,index){
		return value.substring(0,10);
	
};


var sel = function(value,row,index){
	return "<a class='operate' href='javascript:;' onclick=detailObj('"+row.ruleId+"');>查看</a>&nbsp;" +
	"<select rid="+row.ruleId+" class='rulesel' style='height: 22px;'>" +
		"<option selected value='-1'>更改状态</option>"+
	    "<option value='0'>禁用</option>"+
	    "<option value='1'>启用</option>"+
	    "<option value='2'>过期</option>"+
	    "<option value='3'>排队</option>"+
	"</select>" ;
};

var optfun = function(value,row,index){
	var str = row.status;
	if(str=="0"){//禁用
		return '<gd:btn btncode="BTNBTGZGLBTXZGZ05"><a href="javascript:void(0)" class="hrefbutton" onclick="detail('+row.ruleId+','+row.marketId+')">查看</a></gd:btn>';
	}else if(str=="1"){//启用
		return '<gd:btn btncode="BTNBTGZGLBTXZGZ06"><a href="javascript:void(0)" class="hrefbutton" onclick="modify('+row.ruleId+','+row.marketId+')">编辑</a></gd:btn>';
	}
	
};

	//查看
	function detail(id,marketId){
     	$('#detailLimitRuleIframe')[0].src=CONTEXT+'/sublimitrule/init?ruleId='+id+'&marketId='+marketId+'&type=1&t='+(new Date()).getTime();
		$('#detailDialog').dialog('open');
	}
	
	//编辑
	function modify(id,marketId){
     	$('#editLimitRuleIframe')[0].src=CONTEXT+'/sublimitrule/init?ruleId='+id+'&marketId='+marketId+'&type=2&t='+(new Date()).getTime();
		$('#editDialog').dialog('open');
	}

	
function modifyStatus(ruleId,sta){
	jQuery.messager.confirm('提示', '是否要更改该条补贴规则的状态？', function(r){
		if (r){
			var url=CONTEXT+"/subpayrule/updateRuleStatus";
			jQuery.post(url, {ruleId:ruleId,status:sta}, function (data) {
				var obj = jQuery.parseJSON(data);
				if (obj.result*1 > 0) {
					slideMessage("更改成功！");
					//刷新父页面列表
					$("#ruleList").datagrid('load', {});
				}else {
					warningMessage("更改失败！");
					return;
				}
			});
		}else{
			return;
		}
	});	
}

var formatStatus = function(val, row){
	if(val==0){
		return "禁用";
	}else if(val=1){
		return "开启";
	}
}

var formatWhite = function(val, row){
	if(val==0){
		return "未启用";
	}else if(val=1){
		return "启用";
	}
}

var formatMkt = function(val, row){
	if(val==1){
		return "武汉白沙洲批发市场";
	}else if(val=2){
		return "广西玉林批发市场";
	}
}



