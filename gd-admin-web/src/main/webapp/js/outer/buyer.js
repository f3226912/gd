
$(document).ready(function(){
	//数据加载
	$('#buyerOuterdg').datagrid({
		url:CONTEXT+'outer/querybysearch/'+type,
		height: 'auto',
		nowrap:true,
		toolbar:'#buyerOutertb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		pageList:[10,20,50,100],
		onBeforeLoad:function(p){setParams(p);},
		columns:[[
			//{field:'tareCreateTime',title:'进场登记时间',align:'center'},
			{field:'orders',title:'订单号',width:120,align:'center',formatter:ordersFormatter},
			{field:'totalCreateTime',title:'出场登记时间',width:90,align:'center'},
			{field:'account',title:'用户名',width:90,align:'center'},
			{field:'memberName',title:'买家姓名',width:90,align:'center'},
			{field:'memberPhone',title:'买家手机号',width:90,align:'center'},
			{field:'carNumber',title:'车牌',width:90,align:'center'  },
			//{field:'carType',title:'手动选择车型',width:90,align:'center',formatter:carFormatter  },
			//{field:'tare',title:'进场车重（吨）',width:90,align:'center' },
			//{field:'totalWeight',title:'出场车重（吨）',width:90,align:'center' },
			//{field:'netWeight',title:'净重（吨）',width:100,align:'center' },
			//{field:'orderWeigh',title:'订单总重（吨）',width:120,align:'center' },
			//{field:'wucha',title:'误差区间（吨）',width:90,align:'center',formatter:showWucha},
			{field:'guoban',title:'门岗人员账号',width:90,align:'center',formatter:member},
			{field:'opt',title:'操作',width:60,align:'center',formatter:optformat}
		]]
	});

	// 点击按钮是触发查询事件
	$('#serachList').click(function(){
		$('#buyerOuterdg').datagrid('load',getParams());
	});

	//刷新按钮
	$('#icon-refresh').click(function(){
		$('#buyerOuterSearchForm')[0].reset();
		$("#buyerOuterdg").datagrid('load',{});
		//重启导出功能
		disableExport = false ;
	});
});

function carFormatter(val, row){
	if(val==null)
		return "未选择";
	return "被选择车型 "+val;
}

function ordersFormatter(val, row){
	var content = "";
	for(var i = 0 ; i < val.length ; i++) {
		if(val[i].orderNo==null) {
			content += "找不到指定订单<br/>";
		} else {
			content += val[i].orderNo + "<br/>";
		}

	}
	return content;
}

function showWucha(val, row) {
	if(row.netWeight==null) {
		return "";
	}
	if(row.orderWeigh==null) {
		return "0";
	}
	var net = parseFloat(row.orderWeigh);
	var wucha = parseFloat(net * 0.1);
	return ((net-wucha).toFixed(2) + "-" + (net+wucha).toFixed(2));
}

function member(val, row) {
	if(type==1) {
		return row.tareMember;
	} else {
		return row.totalMember;
	}
}

//设置查询参数
function setParams(p){
	p.account = $('#buyerOuterSearchForm #account').val();
	p.carNumber = $('#buyerOuterSearchForm #carNumber').val();
	p.tareStartTime = $('#buyerOuterSearchForm #tareStartTime').val();
	p.tareEndTime = $('#buyerOuterSearchForm #tareEndTime').val();
	p.totalStartTime = $('#buyerOuterSearchForm #totalStartTime').val();
	p.totalEndTime = $('#buyerOuterSearchForm #totalEndTime').val();
	p.tapWeight =  $('#buyerOuterSearchForm #tapWeight').val();
	p.orderNo =  $('#buyerOuterSearchForm #orderNo').val();
}
// 获取查询参数
function getParams(){
	var p = $('#buyerOuterdg').datagrid('options').queryParams;
	setParams(p);
	return p;
}

$('#reloadList').click(function(){
	$('#buyerOuterSearchForm')[0].reset();
});

function showDetail(id){
	$('#showDialog').dialog({'title':'详细','href':CONTEXT+'outer/showEdit/'+type+'/'+id,'width': 660,'height': 500}).dialog('open');
}

/***数据导出功能***/
$("#exportData").click(function(){

	var queryParams = getParams();

	var paramList = 'account='+queryParams.account+	"&carNumber="+queryParams.carNumber	+"&tareStartTime="+queryParams.tareStartTime
		+"&tareEndTime="+queryParams.tareEndTime +"&totalStartTime="+queryParams.totalStartTime	+"&totalEndTime="+queryParams.totalEndTime
		+"&tapWeight="+queryParams.tapWeight;

	$.ajax({
		url: CONTEXT+'outer/checkExportParams/'+type,
		data : queryParams,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'outer/exportData/'+type,paramList,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}

			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage(data);
		}
	});

/*	window.location.href=CONTEXT+'outer/exportData/'+type+'?account='+queryParams.account+
	"&carNumber="+queryParams.carNumber
	+"&tareStartTime="+queryParams.tareStartTime
	+"&tareEndTime="+queryParams.tareEndTime
	+"&totalStartTime="+queryParams.totalStartTime
	+"&totalEndTime="+queryParams.totalEndTime
	+"&tapWeight="+queryParams.tapWeight;*/
});

jQuery.download = function(url, data, method){
	// 获得url和data
    if( url && data ){
        // data 是 string或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
        	.appendTo('body').submit().remove();
    };
};

function addCarNumber() {
	var reg = /^[A-Za-z0-9]{6,6}$/;

	if(!reg.test($("#addForm #carNumber").val())) {
		$.messager.alert("操作提示", '车牌号只能是六位英文字母或数字',"error",function(){$("#addForm #carNumber").focus();});
		return;
	}

	var params = {
			carId:$("#addForm #carId").val(),
			wcId:$("#addForm #wcId").val(),
			carNumber:$("#addForm #carCity option:selected").val() + $("#addForm #carNumber").val()
	};

	$.ajax({
		type: 'POST',
		url: CONTEXT + 'outer/buyer/changeCar',
	    cache: 'false' ,
	    data:params,
	    dataType: 'json' ,
	    success: function(data) {
	    	//slideMessage(data.msg);
	    	if(data.msg=="-1") {
	    		$.messager.alert("操作提示", "添加失败!","error");
	    	}
	    	$.messager.progress('close');

	    	$('#showDialog').dialog('close');

	    	$('#buyerOuterSearchForm')[0].reset();
			$("#buyerOuterdg").datagrid('load',{});
	    } ,
	    error: function(err) {
	    	alert('系统维护中。。。');
	    }
	});
}
