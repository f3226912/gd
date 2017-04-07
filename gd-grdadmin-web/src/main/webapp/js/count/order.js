var disableExport = false;
$(document).ready(function(){
	var transportType =$("#transportType").val();
	load(null,CONTEXT+'orderCount/nstOrderCountList?transportType='+transportType);
});
$('#icon-search').click(function(){ 
	var params={"orderNo":$("#orderNo").val(),
			"orderStatus":$("#orderStatus").val(),
			"s_provinceId":$("#s_provinceId").val(),
			"s_cityId":$("#s_cityId").val(),
			"s_areaId":$("#s_areaId").val(),
			"f_provinceId":$("#f_provinceId").val(),
			"f_cityId":$("#f_cityId").val(),
			"f_areaId":$("#f_areaId").val(),
			"receiveMobile":$("#receiveMobile").val(),
			"releaseMobile":$("#releaseMobile").val(),
			"releaseMobile_ed":$("#releaseMobile_ed").val(),
			"releaseAreaName":$("#releaseAreaName").val(),
			"receiveMobile_ed":$("#receiveMobile_ed").val(),
			"receiveAreaName":$("#receiveAreaName").val(),
			"orderBeginTime":$("#orderBeginTime").val(),
			"orderEndTime":$("#orderEndTime").val(),
			"order_completeBeginTime":$("#order_completeBeginTime").val(),
			"order_completeEndTime":$("#order_completeEndTime").val(),
			"order_confirmBeginTime":$("#order_confirmBeginTime").val(),
			"order_confirmEndTime":$("#order_confirmEndTime").val(),
			"transportType":$("#transportType").val()};
	load(params,CONTEXT+'orderCount/nstOrderCountList');
});

function load(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#orderCountdg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#orderCounttb',
		pageSize:10,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'id',title:'', hidden:true},
					{field:'orderNo',title:'订单号',width:100,align:'center'},
					{field:'f_address_detail',title:'起始地',width:100,align:'center'},
					{field:'s_address_detail',title:'目的地',width:100,align:'center'},
					{field:'orderType',title:'订单类型',width:100,align:'center'},
					{field:'orderStatus',title:'订单状态',width:100,align:'center',formatter:status},
					{field:'orderTime',title:'接单时间',width:100,align:'center'},
					{field:'order_confirmTime',title:'订单确认时间',width:100,align:'center'},
					{field:'order_completeTime',title:'订单完成时间',width:100,align:'center'},
					{field:'receiveMobile',title:'接单者电话',width:100,align:'center'},
					{field:'receiveNstStatus',title:'接单者认证状态',width:100,align:'center' ,formatter:getNstStatusString},
					{field:'releaseMobile',title:'发布者电话',width:100,align:'center'},
					{field:'releaseNstStatus',title:'发布者认证状态',width:100,align:'center',formatter:getNstStatusString},
					{field:'releaseMobile_ed',title:'发布者推荐人电话',width:100,align:'center'},
					{field:'releaseAreaName',title:'发布者推荐人所属区域',width:100,align:'center'},
					{field:'receiveMobile_ed',title:'接单者推荐人电话',width:100,align:'center'},
					{field:'receiveAreaName',title:'接单者推荐人所属区域',width:100,align:'center'},
					{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
				]]
	}); 
	//分页加载
	$("#orderCountdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}
function optformat(value,row,index){
	return "<a class='operate' href='javascript:;' onclick=showOrderDetail('"+row.orderNo+"');>详情</a>";
}
function showOrderDetail(orderNo){
	var transportType =$("#transportType").val();
	$('#orderDetailDialog').dialog({'title':'订单详情', 'width':700, 'height':500, 'href':CONTEXT+'orderCount/showOrderDetail?orderNo='+orderNo+"&transportType="+transportType}).dialog('open');
}
function status(val){
	if(val==1){
		return '待成交';
	}else if(val==2){
		return '已成交';
	}else if(val==3){
		return '已完成';
	}else if(val==4){
		return '未完成';
	}else if(val==5){
		return '已取消';
	}else{
		return '';
	}
}


function getNstStatusString(val) {
	if (val != null) {
	   var  str=val.toString();
	   if(str=="1"){
		   return "已认证";
	   }else if(str=="0"){
		   return "待认证";
	   }else if(str=="2"){
		   return "已驳回";
	   }
	}else{
		 return "未提交认证";
	} 
}


$("#icon-refresh").click(function(){
	$("#orderCountSearchForm")[0].reset();
	del();
	var transportType =$("#transportType").val();
	$("#orderCountdg").datagrid('load',{"transportType":transportType});
	disableExport = false;
});

$("#btn-reset").click(function(){
	del();
	$("#orderCountSearchForm")[0].reset();
});
function del(){
	$("#s_cityId").empty();
	$("#s_cityId").append("<option selected='selected' value=''>选择城市</option>");
	$("#s_areaId").empty();
	$("#s_areaId").append("<option selected='selected' value=''>选择区域</option>");
	$("#f_cityId").empty();
	$("#f_cityId").append("<option selected='selected' value=''>选择城市</option>");
	$("#f_areaId").empty();
	$("#f_areaId").append("<option selected='selected' value=''>选择区域</option>");
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


$("#f_provinceId").change(function(){
	$.get("getCity/"+$("#f_provinceId").val(),function(data){
		if(data.cityList!=""){
			var result="<option selected='selected' value=''>选择城市</option>";
			$.each(data.cityList,function(n,value){
				result +="<option value='"+value.areaID+"'>"+value.area+"</option>";
			});
			$("#f_cityId").html('');
			$("#f_cityId").append(result);
		};
	},"json");
});
$("#f_cityId").change(function(){
	$.get("getArea/"+$("#f_cityId").val(),function(data){
		if(data.areaList!=""){
			var result="<option selected='selected' value=''>选择区域</option>";
			$.each(data.areaList,function(n,value){
				result +="<option value='"+value.areaID+"'>"+value.area+"</option>";
			});
			$("#f_areaId").html('');
			$("#f_areaId").append(result);
		};
	},"json");
});

//设置查询参数
function setParams(p){
	p.orderNo=$('#orderNo').val();
	p.orderStatus=$('#orderStatus').val();
	p.s_provinceId=$('#s_provinceId').val();
	p.s_cityId=$('#s_cityId').val();
	p.s_areaId=$('#s_areaId').val();
	p.f_provinceId=$('#f_provinceId').val();
	p.f_cityId=$('#f_cityId').val();
	p.f_areaId=$('#f_areaId').val();
	p.receiveMobile=$('#receiveMobile').val();
	p.releaseMobile=$('#releaseMobile').val();
	p.releaseMobile_ed=$('#releaseMobile_ed').val();
	p.releaseAreaName=$('#releaseAreaName').val();
	p.receiveMobile_ed=$('#receiveMobile_ed').val();
	p.receiveAreaName=$('#receiveAreaName').val();
	p.orderBeginTime=$('#orderBeginTime').val();
	p.orderEndTime=$('#orderEndTime').val();
	p.order_completeBeginTime=$('#order_completeBeginTime').val();
	p.order_completeEndTime=$('#order_completeEndTime').val();
	p.order_confirmBeginTime=$('#order_confirmBeginTime').val();
	p.order_confirmEndTime=$('#order_confirmEndTime').val();
	p.transportType =$("#transportType").val();
}
// 获取查询参数
function getParams(){
	var p = $('#orderCountdg').datagrid('options').queryParams;
	setParams(p);
	return p;
}

// 导出数据
$("#exportData").click(function exportData(){
	/*var queryParams = getParams();
	window.location.href=CONTEXT+'orderCount/exportData?orderNo='+queryParams.orderNo+
	"&orderStatus="+queryParams.orderStatus
	+"&s_provinceId="+queryParams.s_provinceId
	+"&s_cityId="+queryParams.s_cityId
	+"&s_areaId="+queryParams.s_areaId
	+"&f_provinceId="+queryParams.f_provinceId
	+"&f_cityId="+queryParams.f_cityId
	+"&f_areaId="+queryParams.f_areaId
	+"&receiveMobile="+queryParams.receiveMobile
	+"&releaseMobile="+queryParams.releaseMobile
	+"&releaseMobile_ed="+queryParams.releaseMobile_ed
	+"&releaseAreaName="+queryParams.releaseAreaName
	+"&receiveMobile_ed="+queryParams.receiveMobile_ed
	+"&receiveAreaName="+queryParams.receiveAreaName
	+"&orderBeginTime="+queryParams.orderBeginTime
	+"&orderEndTime="+queryParams.orderEndTime
	+"&order_completeBeginTime="+queryParams.order_completeBeginTime
	+"&order_completeEndTime="+queryParams.order_completeEndTime
	+"&transportType="+queryParams.transportType;*/
	var queryParams = {
		"orderNo" : $('#orderNo').val(),
		"orderStatus" : $('#orderStatus').val(),
		"s_provinceId" : $('#s_provinceId').val(),
		"s_cityId" : $('#s_cityId').val(),
		"s_areaId" : $('#s_areaId').val(),
		"f_provinceId" : $('#f_provinceId').val(),
		"f_cityId" : $('#f_cityId').val(),
		"f_areaId" : $('#f_areaId').val(),
		"receiveMobile" : $('#receiveMobile').val(),
		"releaseMobile" : $('#releaseMobile').val(),
		"releaseMobile_ed" : $('#releaseMobile_ed').val(),
		"releaseAreaName" : $('#releaseAreaName').val(),
		"receiveMobile_ed" : $('#receiveMobile_ed').val(),
		"receiveAreaName" : $('#receiveAreaName').val(),
		"orderBeginTime" : $('#orderBeginTime').val(),
		"orderEndTime" : $('#orderEndTime').val(),
		"order_completeBeginTime" : $('#order_completeBeginTime').val(),
		"order_completeEndTime" : $('#order_completeEndTime').val(),
		"order_confirmBeginTime" : $('#order_confirmBeginTime').val(),
		"order_confirmEndTime" : $('#order_confirmEndTime').val(),
		"transportType":$("#transportType").val()
	};
	
	var paramList = "orderNo="+queryParams.orderNo+
		"&orderStatus="+queryParams.orderStatus
		+"&s_provinceId="+queryParams.s_provinceId
		+"&s_cityId="+queryParams.s_cityId
		+"&s_areaId="+queryParams.s_areaId
		+"&f_provinceId="+queryParams.f_provinceId
		+"&f_cityId="+queryParams.f_cityId
		+"&f_areaId="+queryParams.f_areaId
		+"&receiveMobile="+queryParams.receiveMobile
		+"&releaseMobile="+queryParams.releaseMobile
		+"&releaseMobile_ed="+queryParams.releaseMobile_ed
		+"&releaseAreaName="+queryParams.releaseAreaName
		+"&receiveMobile_ed="+queryParams.receiveMobile_ed
		+"&receiveAreaName="+queryParams.receiveAreaName
		+"&orderBeginTime="+queryParams.orderBeginTime
		+"&orderEndTime="+queryParams.orderEndTime
		+"&order_completeBeginTime="+queryParams.order_completeBeginTime
		+"&order_completeEndTime="+queryParams.order_completeEndTime 
		+"&order_confirmBeginTime="+queryParams.order_confirmBeginTime
		+"&order_confirmEndTime="+queryParams.order_confirmEndTime 
		+"&transportType="+queryParams.transportType;
	$.ajax({
		url: CONTEXT+'orderCount/checkExportParams',
		data : queryParams,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'orderCount/exportData',paramList,'post');
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
	});});

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

