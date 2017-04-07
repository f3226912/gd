<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc"%>
<%@ include file="/WEB-INF/jsp/pub/tags.inc"%>
<div id="shop_buyer" class="easyui-tabs" style="height:265px;">

	<div title="活动买家信息" id="divMore" style="height:265px;">
		<table id="buyer-table" title=""></table>
	</div>
	
</div>

<script type="text/javascript">
var indexValue=${index};
function initShopRuleList(buyerData,index) {
	
	$("#shop_buyer").tabs("select", 0);
	/*
		活动买家
	*/
	$('#buyer-table').datagrid({
		width: 'auto',
		queryParams: null,
		height: '265px', 
		nowrap:true,
		pageSize:10,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		fit:true,
		nowrap:true,
		fitColumn:true,
		onLoadSuccess:function(){
			$("#buyer-table").datagrid('clearSelections');
		},
		data: buyerData.slice(0, 10),
		columns:[[
			{field:'realName',title:'用户姓名',width:90,align:'center'},
			{field:'mobile',title:'手机号码',width : 100,align:'center'},
			{field:'shopsName', title:'商铺名称',width:100,align:'center'},
			{field:'level',title:'用户类型',width:90,align:'center', formatter: optFormatBuyerLevel},
			{field:'marketName',title:'商铺所属市场',width:100,align:'center'},
			{field:'marketName',title:'认证状态',width:100,align:'center'},
			{field:'regetype',title:'注册来源',width:100, align:'center', formatter: optFormatBuyerRegetype},
			{field:'createTime',title:'创建时间',width:100,align:'center',formatter:formatTime},
			{field : 'opt',title :'操作',width :60,align : 'center',formatter : optformatbuyerrule}
		]]
	});
	$("#buyer-table").datagrid("getPager").pagination({
		pageList: [10,20,30,50]
	});
	
	var buyerPager = $("#buyer-table").datagrid("getPager");

	buyerPager.pagination({
		total : addBuyerData.length,
		onSelectPage : function(pageNo, pageSize) {
			var start = (pageNo - 1) * pageSize;
			var end = start + pageSize;
			$("#buyer-table").datagrid("loadData", addBuyerData.slice(start, end));
			buyerPager.pagination('refresh', {
				total : addBuyerData.length,
				pageNumber : pageNo
			});
		}
	});
	

}

function optformatshoprule(value,row,index) {
	var options = "<gd:btn btncode='BTNSPGLQBCP03'><a class='operate' href='javascript:;' onclick=delshoprule('"+row.businessId+"')>删除</a></gd:btn>";
	return options;
}

function optformatbuyerrule(value,row,index) {
	var options = "<gd:btn btncode='BTNSPGLQBCP03'><a class='operate' href='javascript:;' onclick=delbuyerrule('"+row.memberId+"')>删除</a></gd:btn>";
	return options;
}

function optformatprodrule(value,row,index) {
	var options = "<gd:btn btncode='BTNSPGLQBCP03'><a class='operate' href='javascript:;' onclick=delprodrule('"+row.productId+"')>删除</a></gd:btn>";
	return options;
}

function formatTime(value,row,index) {
	debugger;
	var unixTimestamp = new Date(value);
	return formatDateTime(unixTimestamp);
}

var formatDateTime = function (date) {
	debugger;
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? ('0' + m) : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute;  
    var seconds = date.getSeconds();  
    seconds = seconds < 10 ? ('0' + seconds) : seconds;  
    return y + '-' + m + '-' + d+' '+h+':'+minute + ':' + seconds;
};  

function optFormatBuyerLevel(value, row, index) {
	if(value=="1") {
		return "谷登农批";
	} else if (value=="2") {
		return "农速通";
	} else if (value=="3") {
		return "农商友";
	} else if (value=="4") {
		return "产地供应商";
	} else if (value=="5") {
		return "农批友";
	}
	return "";
}

function optFormatofflineSatus(value, row, index) {
	if(value=="1") {
		return "已认证";
	} else if (value=="2") {
		return "未认证";
	}
	return "";
}

function optFormatBuyerRegetype(value, row, index) {
	if(value=="1") {
		return "谷登农批网";
	} else if (value=="2") {
		return "农速通";
	} else if (value=="3") {
		return "农商友";
	} else if (value=="4") {
		return "农商友-农批商";
	} else if (value=="5") {
		return "农批友";
	} else if (value=="6") {
		return "供应商";
	} else if (value=="7") {
		return "POS刷卡";
	} else if (value=="8") {
		return "微信注册用户";
	} else if (value=="0") {
		return "管理后台";
	}
	
	return "";
}

function delshoprule(businessId) {
	removeShopRule(businessId);
	initShopRuleList();
}

function delbuyerrule(memberId) {
	debugger;
	removeBuyerRule2(memberId,indexValue);
	for(var i=0;i<addBuyerData.length;i++){
		//alert(addBuyerData[i]["addBuyerData"+index])
		if(addBuyerData[i]["addBuyerData"+indexValue]){
			//alert(1);
			
			buyerData=addBuyerData[i]["addBuyerData"+indexValue];
			initShopRuleList(buyerData,indexValue);
		}
	}
}

function delprodrule(productId) {
	removeProdRule(productId);
	initShopRuleList();
}
</script>
