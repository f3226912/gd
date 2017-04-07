//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#ruleList").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#ruleList').datagrid({
		url:CONTEXT+'subpayrule/ruleQuery',
		//width: 1000,  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#ruletb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#ruleList").datagrid('clearSelections');
		},
		//ruleId,subRuleName,marketId,memberType,timeStart,timeEnd,subType,subPercent,subAmount,subForTon,status,createTime,createUserId,updateUserId
		//复选框,序号,规则名称,开始时间,结束时间,按交易额比例,按每笔订单,总销售额区间,补贴对象,总销量区间,添加人,状态,操作
		columns:[[
			//{field:'id',title:'',width:0,checkbox:true},
			//{field:'ruleId',title:'序号',width:0},
			{field:'subRuleName',title:'规则名称',width:100,align:'center',formatter:formatName},
			{field:'timeStart',title:'开始时间',width:100,align:'center'},
			{field:'timeEnd',title:'结束时间',width:100,align:'center'},
			//{field:'marketId',title:'市场',width:100,align:'center',formatter:formatMkt},
			{field:'marketStr',title:'市场',width:100,align:'center'},
			//{field:'curDaySubAmount',title:'当日发放补贴',width:80,align:'center',formatter:ruleSubAmount},
			//{field:'sumSubAmount',title:'补贴总额',width:80,align:'center',formatter:ruleSubAmount2},
			{field:'memberType',title:'补贴对象',width:80,align:'center',formatter:formatterMType },
			{field:'subType',title:'补贴方案',width:100,align:'center',formatter:formatterSubType},
			{field:'rangs',title:'方案值',width:150,align:'center',formatter:formatterRangs },
			{field:'category',title:'商品类目',width:150,align:'center' },
			{field:'userName',title:'添加人',width:100,align:'center'},
			{field:'status',title:'状态',width:100,align:'center',formatter:formatterState},
			{field:'opt',title:'操作',width:100,align:'center',formatter:optfun}
		]]
		   
	}); 
}

var ruleSubAmount = function(value,row,index){
	var curd = 0;
	if(row.curDaySubAmount!=null&&typeof(row.curDaySubAmount)!="undefined"){
		curd=row.curDaySubAmount;
	}
	var totalamt = 0;
	if(row.mkSubAmount!=null&&typeof(row.mkSubAmount)!="undefined"){
		totalamt=row.mkSubAmount;
	}
	return curd+"/"+totalamt;
};

var ruleSubAmount2 = function(value,row,index){
	var curd = 0;
	if(row.sumSubAmount!=null&&typeof(row.sumSubAmount)!="undefined"){
		curd=row.sumSubAmount;
	}
	var totalamt = 0;
	if(row.mkSubAmount!=null&&typeof(row.mkSubAmount)!="undefined"){
		totalamt=row.mkSubAmount;
	}
	return curd+"/"+totalamt;
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
			return '<a href="javascript:void(0)" class="hrefbutton" onclick="modifyStatus('+row.ruleId+','+1+')">启用</a>';
		}else if(str=="1"){//启用
			return '<a href="javascript:void(0)" class="hrefbutton" onclick="modifyStatus('+row.ruleId+','+0+')">禁用</a>';
		}else if(str=="2"){//过期
			return '<a href="javascript:void(0)" class="hrefbutton" onclick="detailObj('+row.ruleId+')">查看</a>';
		}else if(str=="3"){//排队
			return '<a href="javascript:void(0)" class="hrefbutton" onclick="editRule('+row.ruleId+')">编辑</a><span style="margin-left:10px"></span><a href="javascript:void(0)" class="hrefbutton" onclick="modifyStatus('+row.ruleId+','+0+')">禁用</a>';
		}else if(str=="4"){//终止
			return '<a href="javascript:void(0)" style="color:red;">系统规则已停用</a>';
		}
};

	//查看
	function editRule(id){
   	$('#editRuleIframe')[0].src=CONTEXT+'subpayrule/editRule/'+id+'?t='+(new Date()).getTime();
	$('#editDialog').dialog('open');
	}
	
	
	
	var formatName = function(value,row){
		return '<a href="javascript:detailObj('+row.ruleId+')">'+value+'</a>';
	};	
	
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

var formatMkt = function(val, row){
	if(val==1){
		return "武汉白沙洲批发市场";
	}else if(val=2){
		return "广西玉林批发市场";
	}
}

//1按比例 2按每笔订单 3采购重量区间,4采购金额区间，5门岗目测审查
function formatterSubType(val, row) {
	var rang="未知";
	var type = row.subType*1;
	switch(type){
	case 1:rang="补贴比例";break;
	case 2:rang="按每笔订单";break;
	case 3:rang="采购重量区间";break;
	case 4:rang="采购金额区间";break;
	case 5:rang="门岗目测审查";break;
	}
	
	return rang;
}
function formatterRangs(val, row) {
	var rang="";
	var type = row.subType*1;
	var items = row.ranges;
	switch(type){
	case 1:rang=row.subPercent/1000+"%";break;
	case 2:rang=row.subAmount;break;
	case 3:
		$.each(items,function(i,item){
			var unit = unitC(item);
			var subUnit=subUnitC(item);
			var lowerlimit = 0 ;
			var upperlimit = "";
			if(item.lowerLimit!=null&&typeof(item.lowerLimit)!="undefined"){
				lowerlimit=item.lowerLimit;
			}
			rang+=">"+lowerlimit+unit;
			if(item.upperLimit!=null&&typeof(item.upperLimit)!="undefined"){
				upperlimit=item.upperLimit;
				rang += ",<="+upperlimit;
			}
			rang+="&nbsp;&nbsp;"+item.subAmount+subUnit+"<br/>";
			//alert(item.subAmount);
		});
		break;
	case 4:
		$.each(items,function(i,item){
			var subUnit=subUnitC(item);
			var lowerlimit = 0 ;
			var upperlimit = "";
			if(item.lowerLimit!=null&&typeof(item.lowerLimit)!="undefined"){
				lowerlimit=item.lowerLimit;
			}
			rang+=">"+lowerlimit+"元";
			if(item.upperLimit!=null&&typeof(item.upperLimit)!="undefined"){
				upperlimit=item.upperLimit;
				rang += ",<="+upperlimit+"元";
			}
			rang+="&nbsp;&nbsp;"+item.subAmount+subUnit+"<br/>";
		});
		break;
	case 5:
		$.each(items,function(i,item){
			var subUnit=subUnitC(item);
			var car = "";
			if(item.carCate!=null&&typeof(item.carCate)!="undefined"){
				car = item.carCate;
			}			
			rang+=car+","+ truckfun(item) +"&nbsp;&nbsp;"+item.subAmount+subUnit+"<br/>";
		});		
		break;
	}
	return rang;
}

var carTypefun = function(item){
	//1两轮摩托车2非人力三轮车3小型车4中型车5大型车
	var c = item.carType*1;
	switch(c){
	case 1:return "两轮摩托车";break;
	case 2:return "非人力三轮车";break;
	case 3:return "小型车";break;
	case 4:return "中型车";break;
	case 5:return "大型车";break;
	default:return "未知";
	}
}
	
	var truckfun = function(item){
		//1明显少量2低于半车3大概半车4满车
		var c = item.truck*1;
		switch(c){
		case 1:return "明显少量";break;
		case 2:return "低于半车";break;
		case 3:return "大概半车";break;
		case 4:return "满车";break;
		default:return "未知";
		}
	}	

var unitC = function(item){
	var unit="吨";
	if(item.unit=="3"){
		unit="公斤";
	}
	return unit;
}

var subUnitC = function subUnitC(item){
	var unit="元";
	var u = item.subUnit*1;
	switch(u){
	case 1:unit="元";break;
	case 31:unit="元/吨";;break;
	case 32:unit="元/公斤";;break;
	case 41:unit="元/千";break;
	case 42:unit="元/万";break;
	case 43:unit="元/十万";break;
	case 44:unit="元/百万";break;
	case 45:unit="元/千万";break;
	case 51:unit="元/天/车";break;
	default:unit="元";
	}

	return unit;
}

//状态
function formatterState(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="0"){
			return "禁用";
		}else if(str=="1"){
			return "启用";
		}else if(str=="2"){
			return "过期";
		}else if(str=="3"){
			return "排队";
		}else if(str=="4"){
			return "活动终止";
		}
	}else{
		return "未知";
	}
}

//发布渠道（01农批web,02谷登物流,03农商友）
function formatterCanal(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str=="01"){
			return "农批web";
		}else if(str=="02"){
			return "农商友";
		}else if(str=="03"){
			return "农速通";
		}
	}else{
		return "未知";
	}
}
//1产地供应商 2农批商 3采购商
function formatterMType(val, row) {
	if (val != null) {
		var  str=val.toString();
		if(str==1){
			return "产地供应商";
		}else if(str==2){
			return "农批商";
		}else if(str==3){
			return "采购商";
		}
	}else{
		return "未知";
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

function formatPrice(value,row,index){
	if (row.unit == 0){
		return "克";
	}else if (row.unit == 1){
		return "公斤";
	}
}

//删除操作
function delObj(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
    		jQuery.post(CONTEXT+"push/adInfoDeleteById",{"id":id},function(data){
				if (data == "success"){
					slideMessage("操作成功！");
					$('#adinfodg').datagrid('reload');
					$('#adinfodg').datagrid("uncheckAll");
				}else{
					warningMessage(data);
					return;
				}
    		});
		}else{
			return;
		}
	});
}

//删除多个操作
function delsObj(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r){
		if (r){
    		jQuery.post(CONTEXT+"push/adInfoDeletesById",{"id":id},function(data){
				if (data == "success"){
					slideMessage("操作成功！");
					$('#adinfodg').datagrid('reload');
					$('#adinfodg').datagrid("uncheckAll");
				}else{
					warningMessage(data);
					return;
				}
    		});
		}else{
			return;
		}
	});
}
//编辑
function editObj(id){
	$('#editDialog').dialog({'title':'编辑DTO','href':CONTEXT+'push/adInfoEditById/'+id,'width': 800,'height': 500}).dialog('open');
}

//查看
function detailObj(id){
	$('#detailDialog').dialog({'title':'查看详情','href':CONTEXT+'subpayrule/ruleDetail/'+id,'width': 1100,'height': 500}).dialog('open');
}

//选择产品
function showPro(){
	$('#proDialog').dialog({'title':'选择产品','href':CONTEXT+'push/proInitList','width': 800,'height': 500}).dialog('open');
}

//提交新增数据
function saveadd() {
	var url=CONTEXT+"push/adInfoSaveAdd";
	jQuery.post(url, $('#addForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#adinfodg").datagrid('reload');
			$('#addDialog').dialog('close');
		} else {
			warningMessage(data);
			return;
		}
	});
}

//提交更新数据
function saveedit() {
	var url=CONTEXT+"push/adInfoSaveEdit";
	jQuery.post(url, $('#editForm').serialize(), function (data) {
		if (data == "success") {
			slideMessage("操作成功！");
			//刷新父页面列表
			$("#adinfodg").datagrid('reload');
			$('#editDialog').dialog('close');
		} else {
			warningMessage(data);
			return;
		}
	});
}
