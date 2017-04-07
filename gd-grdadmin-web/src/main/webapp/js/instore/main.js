
$(document).ready(function(){
	loadInstoreProductList(null,CONTEXT+'instore/productList');
});


var disableExport = false ;
//查询按钮,根据name查询
$('#icon-search').click(function(){
	var params={"supplierAccount":$("#supplierAccount").val(),
			"specialAccount":$("#specialAccount").val(),
			"inStoreNo":$("#inStoreNo").val(),
			"createName":$("#createName").val(),
			"productStartTime":$("#productStartTime").val(),
			"productEndTime":$("#productEndTime").val(),
			"istoreStatus":$("#istoreStatus").val()};
	loadInstoreProductList(params,CONTEXT+'instore/productList');
});

function loadInstoreProductList(params, loadUrl){
	params = !params ? {}: params;
	//数据加载
	$('#instoredg').datagrid({
		url:loadUrl,
		queryParams: params,
		height: 'auto',
		nowrap:true,
		toolbar:'#instoretb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		columns:[[
					{field:'pwdId',title:'', hidden:true},
					{field:'inStoreNo',title:'入库单',width:100,align:'center'},
					{field:'supplierAccount',title:'供应商账号',width:100,align:'center'},
					{field:'supplierName',title:'供应商姓名',width:100,align:'center'},
					{field:'productName',title:'产品名称',width:100,align:'center'},
					{field:'cateName',title:'商品类目',width:100,align:'center'},
					{field:'weigh',title:'产品重量(吨)',width:100,align:'center'},
					{field:'productTime',title:'产品创建时间',width:100,align:'center'},
					{field:'createName',title:'创建人',width:100,align:'center'},
					{field:'specialAccount',title:'批发商账号',width:100,align:'center'},
					{field:'specialName',title:'批发商姓名',width:100,align:'center'},
					{field:'purQuantity',title:'入库量(吨)',width:100,align:'center'},
					{field:'instoreTime',title:'入库时间',width:100,align:'center'},
					{field:'istoreStatus',title:'入库状态',width:100,align:'center',formatter:istoreState}

				]]
	});
	//分页加载
	$("#instoredg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});

}

function istoreState(val){
	if(val=='0'){
		return '未入库';
	}else if(val=='1'){
		return '已入库';
	}
}

$("#icon-refresh").click(function(){
	$("#supplierAccount").val("");
	$("#specialAccount").val("");
	$("#inStoreNo").val("");
	$("#createName").val("");
	$("#productStartTime").val("");
	$("#productEndTime").val("");
	$("#istoreStatus").val("");
	$("#instoredg").datagrid('load',{});
	//重启导出功能
	disableExport = false ;
});

$("#btn-reset").click(function(){
	$("#supplierAccount").val("");
	$("#specialAccount").val("");
	$("#inStoreNo").val("");
	$("#createName").val("");
	$("#productStartTime").val("");
	$("#productEndTime").val("");
	$("#istoreStatus").val("");
});

/***数据导出功能***/
$("#exportData").click(function(){

	var queryParams = $('#instoredg').datagrid('options').queryParams;
	queryParams.supplierAccount = $('#supplierAccount').val();
	queryParams.specialAccount = $('#specialAccount').val();
	queryParams.inStoreNo = $('#inStoreNo').val();
	queryParams.createName = $('#createName').val();
	queryParams.productStartTime = $("#productStartTime").val();
	queryParams.productEndTime = $("#productEndTime").val();
	queryParams.istoreStatus = $("#istoreStatus").val();

	var paramList = 'supplierAccount='+queryParams.supplierAccount
	+"&specialAccount="+queryParams.specialAccount
	+"&inStoreNo="+queryParams.inStoreNo
	+"&createName="+queryParams.createName
	+"&productStartTime="+queryParams.productStartTime
	+"&productEndTime="+queryParams.productEndTime
	+"&istoreStatus="+queryParams.istoreStatus;

	$.ajax({
		url: CONTEXT+'instore/checkExportParams',
		data : queryParams,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'instore/exportData',paramList,'post' );
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

/*	window.location.href=CONTEXT+'instore/exportData?supplierAccount='+queryParams.supplierAccount
	+"&specialAccount="+queryParams.specialAccount
	+"&inStoreNo="+queryParams.inStoreNo
	+"&createName="+queryParams.createName
	+"&productStartTime="+queryParams.productStartTime
	+"&productEndTime="+queryParams.productEndTime
	+"&istoreStatus="+queryParams.istoreStatus;*/

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
