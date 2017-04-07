

$.extend($.fn.datagrid.methods, {
	fixRownumber : function (jq) {
		return jq.each(function () {
			var panel = $(this).datagrid("getPanel");
			//获取最后一行的number容器,并拷贝一份
			var clone = $(".datagrid-cell-rownumber", panel).last().clone();
			//由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
			clone.css({
				"position" : "absolute",
				left : -1000
			}).appendTo("body");
			var width = clone.width("auto").width();
			//默认宽度是25,所以只有大于25的时候才进行fix
			if (width > 25) {
				//多加5个像素,保持一点边距
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
				//修改了宽度之后,需要对容器进行重新计算,所以调用resize
				$(this).datagrid("resize");
				//一些清理工作
				clone.remove();
				clone = null;
			} else {
				//还原成默认状态
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
			}
		});
	}
});


//初始化加载页面列表
function initList(){
	loadList(null);
	$(".datagrid-header-rownumber").text("序号");
	//分页加载
}
//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#proOrderRecieved-datagrid-table').datagrid({
		url:CONTEXT+'grdProOrderRecieved/query',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#proOrderRecieved-datagrid-tool-bar',
		pageSize:20,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$(this).datagrid("fixRownumber");
			$("#proOrderRecieved-datagrid-table").datagrid('clearSelections');
		},
		columns:[[
			{field:'marketName',title:'所属市场',width:150,align:'left',halign:'center'},
			//{field:'teamName',title:'团队名称',width:150,align:'left'},
			{field:'grdUserName',title:'地推姓名',width:100,align:'left',halign:'center'},
			{field:'grdMobile',title:'地推手机',width:120,align:'center',halign:'center'},
			{field:'publisher',title:'发布人姓名',width:100,align:'left',halign:'center'},
			{field:'publisherTime',title:'发布时间',width:160,align:'center',halign:'center'},
			{field:'recieveTime',title:'接单时间',width:160,align:'center',halign:'center'},
			{field:'driverName',title:'司机姓名',width:100,align:'left',halign:'center'},
			{field:'confirmTime',title:'确认时间',width:160,align:'center',halign:'center'},
			{field:'confirmStatus',title:'确认状态',width:100,align:'left',formatter : confirmStatusformat,halign:'center'}
		]]
	}); 
	$("#proOrderRecieved-datagrid-table").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function confirmStatusformat(v,d,i){
	if (v == '2' || v == '3') {
		return "接受";
	}else if(v == '4'){
		return '拒绝';
	}else if(v == '5'){
		return '超时';
	}else if(v == '6'){
		return '取消';
	}else{
		return '未知';
	} 
}
var disableExport = false ;

//设置查询参数
function setParams(p){
	p.marketId = $('#proOrderRecieved-datagrid-form #marketId').val();
	//p.teamId = $('#proOrderRecieved-datagrid-form #teamId').val();
	p.grdUserName = $('#proOrderRecieved-datagrid-form #grdUserName').val();
	p.grdMobile = $('#proOrderRecieved-datagrid-form #grdMobile').val();
	p.confirmStatus = $('#proOrderRecieved-datagrid-form #confirmStatus').val();
	p.startPublisherTime = $('#proOrderRecieved-datagrid-form #startPublisherTime').val();
	p.endPublisherTime = $('#proOrderRecieved-datagrid-form #endPublisherTime').val();
	p.startRecieveTime = $('#proOrderRecieved-datagrid-form #startRecieveTime').val();
	p.endRecieveTime = $('#proOrderRecieved-datagrid-form #endRecieveTime').val();
	p.startConfirmTime =  $('#proOrderRecieved-datagrid-form #startConfirmTime').val();
	p.endConfirmTime =  $('#proOrderRecieved-datagrid-form #endConfirmTime').val();
}
//获取查询参数
function getParams(){
	var p = $('#proOrderRecieved-datagrid-table').datagrid('options').queryParams;
	setParams(p);
	return p;
}
/***数据导出功能***/
$("#exportData").click(function(){
	var requestParam = getParams();
	if (disableExport){
		slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,刷新当前页面..");
		return false;
	}
	
	var paramList = 'marketId=' + requestParam.marketId + '&grdUserName=' + requestParam.grdUserName
	+ '&grdMobile=' + requestParam.grdMobile + '&startPublisherTime=' + requestParam.startPublisherTime + '&endPublisherTime=' + requestParam.endPublisherTime 
	+ '&startRecieveTime=' + requestParam.startRecieveTime + '&endRecieveTime=' + requestParam.endRecieveTime + '&startConfirmTime=' + requestParam.startConfirmTime 
	+ '&endConfirmTime=' + requestParam.endConfirmTime + '&confirmStatus=' + requestParam.confirmStatus;
	$.ajax({
		url: CONTEXT+'grdProOrderRecieved/checkExportParams',
		data : requestParam,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				/* $("#Loading2").show(); */
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'grdProOrderRecieved/exportData',paramList,'post' );
				}else{
					slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,请点击刷新按钮...");
				}
			}else{
				warningMessage(data.message);
			}
		},
		error : function(data){
			warningMessage("error : " + data);
			//恢复导出功能
			disableExport = false;
		}
	});
});

function query(){
	var requestParam = getParams();
	loadList(requestParam);
	//重启导出功能
	disableExport = false ;
}

function reset(){
	$('#proOrderRecieved-datagrid-form')[0].reset();
	//重启导出功能
	disableExport = false ;
}

$(function(){
	initList();
	//查询按钮
	$('#btn-search').click(function(){
		query();
		var requestParam = getParams();
		$('#proOrderRecieved-datagrid-table').datagrid('load',requestParam);
		//重启导出功能
		disableExport = false ;
	});
	
	
	//重置按钮
	$('#btn-reset').click(function(){
		reset();
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