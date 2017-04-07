$(document).ready(function(){
	//数据加载
	$('#supSubdg').datagrid({
		url:CONTEXT+'supSub/querybysearch/'+type,
		height: 'auto', 
		nowrap:true,
		toolbar:'#supSubtb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		pageList:[10,20,50,100],
		onBeforeLoad:function(p){setParams(p);},
		columns:[[
			{field:'pwdId',title:'',width:0,checkbox:true},
			{field:'inStores',title:'入库单',width:120,align:'center',formatter:showInStoresFormatter },
			{field:'productName',title:'产品名称',width:90,align:'center'},
			{field:'cateName',title:'商品类目',width:90,align:'center'},
			{field:'weigh',title:'入场产品重量（吨）',width:90,align:'center'},
			{field:'outWeigh',title:'出场重量（吨）',width:90,align:'center',formatter:showNetFormatter  },
			{field:'net',title:'产品净重',width:90,align:'center' },
			{field:'account',title:'供应商用户名',width:90,align:'center' },
			{field:'memberName',title:'供应商姓名',width:100,align:'center' },
			{field:'totalCreateTime',title:'入场时间',width:120,align:'center',formatter:fmtTime },
			{field:'tareCreateTime',title:'出场时间',width:90,align:'center',formatter:fmtTime},
			{field:'subMoney',title:'补贴金额',width:90,align:'center'},
			{field:'paymentStatus',title:'结算状态',width:90,align:'center',formatter:showStatusFormatter}
		]]
	}); 
	
	// 点击按钮是触发查询事件
	$('#icon-search').click(function(){
		$('#supSubdg').datagrid('load',getParams());
	});
	// 点击"导出"按钮
	$('#exportData').click(function(){
		exportData();
	});
	// 批量审核
	$('#verifySelected').click(function(){
		verifyBatch();
	});
});

function showInStoresFormatter(v,r) {
	if(v!=null) {
		for(var i = 0; i < v.length ; i ++) {
			if(v[i].inStoreNo==null) {
				content += "找不到指定入库单<br/>";
			} else {
				content += v[i].inStoreNo + "<br/>";
			}
		}
	} else {
		content = "未绑定入库单";
	}
	return content;
}

function showNetFormatter(v,r) {
	return parseFloat(r.weigh)-parseFloat(outWeigh);
}

function showStatusFormatter(v,r) {
	return v == "1"? "待补贴":"已补贴";
}
// 设置查询参数
function setParams(p){
	p.totalStartTime=$('#supSubSearchForm #totalStartTime').val();
	p.totalEndTime=$('#subauditSearchForm #totalEndTime').val();
	p.tareStartTime=$('#subauditSearchForm #tareStartTime').val();
	p.tareEndTime=$('#subauditSearchForm #tareEndTime').val();
	p.account=$('#subauditSearchForm #account').val();
	p.inStoreNo=$('#subauditSearchForm #inStoreNo').val();
}
// 获取查询参数
function getParams(){
	var p = $('#supSubdg').datagrid('options').queryParams;
	setParams(p);
	return p;
}

// 导出数据
function exportData(){
	var queryParams = getParams();
	window.location.href=CONTEXT+'supSub/exportData/'+type+'?totalStartTime='+queryParams.totalStartTime+
	"&totalEndTime="+queryParams.totalEndTime
	+"&tareStartTime="+queryParams.tareStartTime
	+"&tareEndTime="+queryParams.tareEndTime
	+"&account="+queryParams.account
	+"&inStoreNo="+queryParams.inStoreNo;
}

function fmtTime(val, row) {
	if (val != null) {
	   var  str=val.toString();
	   str =  str.replace(/-/g,"/");
	   var date = new Date(str);
	   return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
}

// 保存补贴(flag 0:不通过；1：通过)
var isSubmited = false;

function sub() {
	if(isSubmited){return;}

	isSubmited = true;
	
	var ids = "";
	var sels = $("#supSubdg input[type=checkbox]:selected");
	sels.each(function(i,v) {
		if($(v).attr("checked")) {
			ids += ($(v).val() + ",");
		}
		
	});
	
	if(ids==null||ids=="") {
		$.messager.alert("错误","请选择要确定已补贴数据");
		isSubmited = false;
		return;
	}
	
	// 参数设置
	$.messager.confirm('确认',"您确认是否已补贴",function(r){
		
	    if (r){
	    	$.ajax({
    		   type: 'POST',
    		   url: CONTEXT+'subaudit/verify/save',
    		   data: {ides:ids},
    		   success: function(msg){
    			  if('SUCCESS'==msg){
    				  $.messager.alert('处理结果','操作成功！','info');
    				  $('#subauditdg').datagrid('reload');
    			  }else{
    				  $.messager.alert('处理结果',msg,'error');
    			  }
    			  $('#showDialog').dialog('close');
    		   }
	    	});
	    }else{
	    }
	    isSubmited = false;
	});
}

function resumeIsSubmited(){
	isSubmited=false;
}
//重置
$('#btn-reset').click(function(){
	$('#supSubSearchForm')[0].reset();
});

//修改状态
$('#icon-update').click(function(){
	sub();
});

//刷新按钮
$('#icon-refresh').click(function(){
	$("#supSubdg").datagrid('load',getParams());
});