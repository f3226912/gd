var disableExport = false;
$(document).ready(function(){
	load(null,CONTEXT+'count/publishCarLineList');
});
$('#icon-search').click(function(){ 
	var params={"areaName":$("#areaName").val(),
			"mobile":$("#mobile").val(),
			"mobile_ed":$("#mobile_ed").val(),
			"memberCreateBeginTime":$("#memberCreateBeginTime").val(),
			"memberCreateEndTime":$("#memberCreateEndTime").val(),
			"lineCreateBeginTime":$("#lineCreateBeginTime").val(),
			"lineCreateEndTime":$("#lineCreateEndTime").val(),
			"s_provinceId":$("#s_provinceId").val(),
			"s_cityId":$("#s_cityId").val(),
			"s_areaId":$("#s_areaId").val(),
			"e_provinceId":$("#e_provinceId").val(),
			"e_cityId":$("#e_cityId").val(),
			"e_areaId":$("#e_areaId").val(),};
	load(params,CONTEXT+'count/publishCarLineList');
});

function load(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#countCarLinedg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#countCarLinetb',
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
					{field:'lineCreateTime',title:'发布线路信息时间',width:100,align:'center'},
					{field:'s_startAddress',title:'起始地',width:100,align:'center'},
					{field:'e_endAddress',title:'目的地',width:100,align:'center'},
					{field:'nstStatus',title:'认证状态',width:100,align:'center'},
					{field:'memberCreateTime',title:'被推荐人注册时间',width:100,align:'center'},
					{field:'isDeleted',title:'信息是否删除',width:100,align:'center'},
				]]
	}); 
	//分页加载
	$("#countCarLinedg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}
$("#icon-refresh").click(function(){
	$("#countCarLineSearchForm")[0].reset();
	del();
	$("#countCarLinedg").datagrid('load',{});
	disableExport = false;
});

$("#btn-reset").click(function(){
	del();
	$("#countCarLineSearchForm")[0].reset();
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
function del(){
	$("#s_cityId").empty();
	$("#s_cityId").append("<option selected='selected' value=''>选择城市</option>");
	$("#s_areaId").empty();
	$("#s_areaId").append("<option selected='selected' value=''>选择区域</option>");
	$("#e_cityId").empty();
	$("#e_cityId").append("<option selected='selected' value=''>选择城市</option>");
	$("#e_areaId").empty();
	$("#e_areaId").append("<option selected='selected' value=''>选择区域</option>");
}
$("#s_provinceId").change(function(){
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
	$.get("getArea/"+$("#s_cityId").val(),function(data){
		if(data.areaList!=""){
			var result="<option selected='selected' value=''>选择区域</option>";
			$.each(data.areaList,function(n,value){
				result +="<option value='"+value.areaID+"'>"+value.area+"</option>";
			});
			$("#s_areaId").html('');
			$("#s_areaId").append(result);
		};
	},"json");
});
$("#e_provinceId").change(function(){
	$.get("getCity/"+$("#e_provinceId").val(),function(data){
		if(data.cityList!=""){
			var result="<option selected='selected' value=''>选择城市</option>";
			$.each(data.cityList,function(n,value){
				result +="<option value='"+value.areaID+"'>"+value.area+"</option>";
			});
			$("#e_cityId").html('');
			$("#e_cityId").append(result);
		};
	},"json");
});
$("#e_cityId").change(function(){
	$.get("getArea/"+$("#e_cityId").val(),function(data){
		if(data.areaList!=""){
			var result="<option selected='selected' value=''>选择区域</option>";
			$.each(data.areaList,function(n,value){
				result +="<option value='"+value.areaID+"'>"+value.area+"</option>";
			});
			$("#e_areaId").html('');
			$("#e_areaId").append(result);
		};
	},"json");
});
/***数据导出功能***/
$("#exportData").click(function(){
/*	var queryParams = $('#countCarLinedg').datagrid('options').queryParams;
	queryParams.areaName = $('#areaName').val();
	queryParams.mobile = $('#mobile').val();
	queryParams.mobile_ed = $('#mobile_ed').val();
	queryParams.s_provinceId = $('#s_provinceId').val();
	queryParams.s_cityId = $('#s_cityId').val();
	queryParams.s_areaId = $('#s_areaId').val();
	queryParams.e_provinceId = $('#e_provinceId').val();
	queryParams.e_cityId = $('#e_cityId').val();
	queryParams.e_areaId = $('#e_areaId').val();
	queryParams.memberCreateBeginTime = $("#memberCreateBeginTime").val();
	queryParams.memberCreateEndTime = $("#memberCreateEndTime").val();
	queryParams.lineCreateBeginTime = $("#lineCreateBeginTime").val();
	queryParams.lineCreateEndTime = $("#lineCreateEndTime").val();
	window.location.href=CONTEXT+'count/exportData?areaName='+queryParams.areaName+
	"&mobile="+queryParams.mobile
	+"&mobile_ed="+queryParams.mobile_ed
	+"&s_provinceId="+queryParams.s_provinceId
	+"&s_cityId="+queryParams.s_cityId
	+"&s_areaId="+queryParams.s_areaId
	+"&e_provinceId="+queryParams.e_provinceId
	+"&e_cityId="+queryParams.e_cityId
	+"&e_areaId="+queryParams.e_areaId
	+"&memberCreateBeginTime="+queryParams.memberCreateBeginTime
	+"&memberCreateEndTime="+queryParams.memberCreateEndTime
	+"&lineCreateBeginTime="+queryParams.lineCreateBeginTime
	+"&lineCreateEndTime="+queryParams.lineCreateEndTime;*/
	
	var queryParams = {
		"areaName" : $('#areaName').val(),
		"mobile" : $('#mobile').val(),
		"mobile_ed" : $('#mobile_ed').val(),
		"s_provinceId" : $('#s_provinceId').val(),
		"s_cityId" : $('#s_cityId').val(),
		"s_areaId" : $('#s_areaId').val(),
		"e_provinceId" : $('#e_provinceId').val(),
		"e_cityId" : $('#e_cityId').val(),
		"e_areaId" : $('#e_areaId').val(),
		"memberCreateBeginTime" : $("#memberCreateBeginTime").val(),
		"memberCreateEndTime" : $("#memberCreateEndTime").val(),
		"lineCreateBeginTime" : $("#lineCreateBeginTime").val(),
		"lineCreateEndTime" : $("#lineCreateEndTime").val()
	};
	
	var paramList = "areaName="+queryParams.areaName+
		"&mobile="+queryParams.mobile
		+"&mobile_ed="+queryParams.mobile_ed
		+"&s_provinceId="+queryParams.s_provinceId
		+"&s_cityId="+queryParams.s_cityId
		+"&s_areaId="+queryParams.s_areaId
		+"&e_provinceId="+queryParams.e_provinceId
		+"&e_cityId="+queryParams.e_cityId
		+"&e_areaId="+queryParams.e_areaId
		+"&memberCreateBeginTime="+queryParams.memberCreateBeginTime
		+"&memberCreateEndTime="+queryParams.memberCreateEndTime
		+"&lineCreateBeginTime="+queryParams.lineCreateBeginTime
		+"&lineCreateEndTime="+queryParams.lineCreateEndTime;
	$.ajax({
		url: CONTEXT+'count/checkExportParams',
		data : queryParams,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'count/exportData',paramList,'post');
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

