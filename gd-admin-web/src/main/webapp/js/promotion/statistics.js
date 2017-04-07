$(document).ready(function(){
	loadStatistics(null);
});

var disableExport = false ;

$("#btn-reset").click(function(){

	$("#promotionSearchForm")[0].reset();
});
$("#serachList").click(function(){
	var params = {
			"name" : $("#name").val(),
			"createTimeStart"  : $("#startDate").val(),
			"createTimeEnd" : $("#endDate").val(),
		};
	loadStatistics(params);
});

function loadStatistics(params){
	params = !params ? {}: params;
	//数据加载
	$('#promotiondg').datagrid({
		url:CONTEXT+'promotion/statistics',
		//width: 1000,
		queryParams: params,
		height: 'auto',
		nowrap:true,
		toolbar:'#promotiontb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#promotiondg").datagrid('clearSelections');
		},
		columns:[[
					{field:'id',title:'',width:0,checkbox:true},
					{field:'name',title:'名称',width:100,align:'center' },
					{field:'type',title:'来源',width:100,align:'center',formatter:formatterType },
					{field:'count',title:'访问量',width:100,align:'center' }
				]]
	});
	//分页加载
	$("#promotiondg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

}

function formatterType(val, row) {
 	if (val != null) {
 		var  str=val.toString();
 		if(str=="1"){
 			return "农速通";
 		}else if(str=="2"){
 			return "农商友";
 		}else if(str=="3"){
 			return "农批商";
 		}
 	}
 }

//刷新按钮
$('#icon-refresh').click(function(){
	$("#name").val("");
	$("#startDate").val("");
	$("#endDate").val("");
	//重启导出功能
	disableExport = false ;
});

$("#exportData").click(function(){
	var params = {
			"name" : $("#name").val(),
			"createTimeStart"  : $("#startDate").val(),
			"createTimeEnd" : $("#endDate").val(),
		};
	var paramList = 'name=' + encodeURIComponent(encodeURIComponent(params.name)) + "&createTimeStart="+params.createTimeStart
	+ "&createTimeEnd="+params.createTimeEnd;

	$.ajax({
		url: CONTEXT+'promotion/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'promotion/exportStatisticsList',paramList,'post' );
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

/*		window.location.href=CONTEXT+'promotion/exportStatisticsList?name=' + encodeURIComponent(encodeURIComponent(params.name)) + "&createTimeStart="+params.createTimeStart
		+ "&createTimeEnd="+params.createTimeEnd;*/
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