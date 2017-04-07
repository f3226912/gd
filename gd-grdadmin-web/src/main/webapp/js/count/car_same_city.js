var disableExport = false;
$(document).ready(function(){
	load(null,CONTEXT+'count/publishCarListSameCity');
});
$('#icon-search').click(function(){ 
	var params={"areaName":$("#areaName").val(),
			"mobile":$("#mobile").val(),
			"mobile_ed":$("#mobile_ed").val(),
			"memberCreateBeginTime":$("#memberCreateBeginTime").val(),
			"memberCreateEndTime":$("#memberCreateEndTime").val(),
			"carCreateBeginTime":$("#carCreateBeginTime").val(),
			"carCreateEndTime":$("#carCreateEndTime").val()};
	load(params,CONTEXT+'count/publishCarListSameCity');
});

function load(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#countCardg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#countCartb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'', hidden:true},
					{field:'mobile',title:'推荐人号码',width:100,align:'center'},
					{field:'name',title:'推荐人姓名',width:100,align:'center'},
					{field:'mobile_ed',title:'被推荐人号码',width:100,align:'center'},
					{field:'name_ed',title:'被推荐人姓名',width:100,align:'center'},
					{field:'level',title:'被推荐人角色',width:100,align:'center',formatter:levelName},
					{field:'userType',title:'用户类型',width:100,align:'center'},
					{field:'areaName',title:'区域名称',width:100,align:'center'},
					{field:'companyName',title:'公司名称',width:100,align:'center'},
					{field:'carCreateTime',title:'发布车辆信息时间',width:100,align:'center'},
					{field:'nstStatus',title:'认证状态',width:100,align:'center'},
					{field:'memberCreateTime',title:'被推荐人注册时间',width:100,align:'center'},
				]]
	}); 
	//分页加载
	$("#countCardg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}
$("#icon-refresh").click(function(){
	$("#countCarSearchForm")[0].reset();
	$("#countCardg").datagrid('load',{});
	disableExport = false;
});

$("#btn-reset").click(function(){
	$("#countCarSearchForm")[0].reset();
});

function levelName(val){
	if(val==1){
		return '谷登农批';
	}else if(val==2){
		return '农速通';
	}else if(val==3){
		return '农商友';
	}else if(val==4){
		return '产地供应商';
	}else if(val==5){
		return '门岗';
	}else{
		return '';
	}
}

/***数据导出功能***/
$("#exportData").click(function(){
/*	var queryParams = $('#countCardg').datagrid('options').queryParams;
	queryParams.areaName = $('#areaName').val();
	queryParams.mobile = $('#mobile').val();
	queryParams.mobile_ed = $('#mobile_ed').val();
	queryParams.memberCreateBeginTime = $("#memberCreateBeginTime").val();
	queryParams.memberCreateEndTime = $("#memberCreateEndTime").val();
	queryParams.carCreateBeginTime = $("#carCreateBeginTime").val();
	queryParams.carCreateEndTime = $("#carCreateEndTime").val();
	window.location.href=CONTEXT+'count/exportCarData?areaName='+queryParams.areaName+
	"&mobile="+queryParams.mobile
	+"&mobile_ed="+queryParams.mobile_ed
	+"&memberCreateBeginTime="+queryParams.memberCreateBeginTime
	+"&memberCreateEndTime="+queryParams.memberCreateEndTime
	+"&carCreateBeginTime="+queryParams.carCreateBeginTime
	+"&carCreateEndTime="+queryParams.carCreateEndTime;*/
	
	var queryParams = {
		"areaName" : $('#areaName').val(),
		"mobile" : $('#mobile').val(),
		"mobile_ed" : $('#mobile_ed').val(),
		"memberCreateBeginTime" : $("#memberCreateBeginTime").val(),
		"memberCreateEndTime" : $("#memberCreateEndTime").val(),
		"carCreateBeginTime" : $("#carCreateBeginTime").val(),
		"carCreateEndTime" : $("#carCreateEndTime").val()
	};
	
	var paramList = "areaName="+queryParams.areaName+
		"&mobile="+queryParams.mobile
		+"&mobile_ed="+queryParams.mobile_ed
		+"&memberCreateBeginTime="+queryParams.memberCreateBeginTime
		+"&memberCreateEndTime="+queryParams.memberCreateEndTime
		+"&carCreateBeginTime="+queryParams.carCreateBeginTime
		+"&carCreateEndTime="+queryParams.carCreateEndTime;
	$.ajax({
		url: CONTEXT+'count/checkExportCarParams',
		data : queryParams,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'count/exportCarDataSameCity',paramList,'post');
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

