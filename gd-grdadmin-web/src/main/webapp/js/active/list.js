
var ctrl = new Contrl();
$(function(){
	ctrl.initEvent();
	ctrl.initTable();
	$('#icon-search').click();
	
	$("#queryMarketId").combobox({
		url:CONTEXT+'market/getAllMarket',
		valueField:'id',
		textField:'marketName' 
	});
});

function Contrl(){
	
	this.initEvent=function(){
		//查询事件url:CONTEXT+'active/query',
		$('#icon-search').click(function(){
			var params = getSearchParam();
			$('#activedg').datagrid("options").url=CONTEXT+'active/query';
			$('#activedg').datagrid("reload",params);
		});
		//新增事件
		$('#icon-add').click(function(){
			openActive();
		});
		
		//导出事件
		$('#btn-export').click(function(){
			//启动下载
			$.download(CONTEXT+'active/exportActive',getSearchParam(),'post' );
			
		});
	}
	
	
	function getSearchParam(){
		var queryMarketId = "";
		try {
			queryMarketId = $("#queryMarketId").combobox("getValue");
		} catch (e) {
		}
		
		
		var params = {
				"name":$("#activeSearchForm input[name='name']").val(),
				"queryMarketId":queryMarketId,
				"actStartDate":$("#activeSearchForm #actStartDate").val(),
				"actEndDate":$("#activeSearchForm #actEndDate").val(),
				"startDate":$("#activeSearchForm #startDate").val(),
				"endDate":$("#activeSearchForm #endDate").val()
			};
		return params;
	}
	
	this.initTable=function(){
		//数据加载
		$('#activedg').datagrid({
			height: 'auto', 
			nowrap:true,
			toolbar:'#activetb',
			pageSize:50,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			singleSelect:true,
			fit:true,
			onLoadSuccess:function(){
				$("#activedg").datagrid('clearSelections');
			},
			columns:[[
				//{field:'actId',title:'活动编号',width:50,align:'center'},
				{field:'name',title:'活动名称',width:80,align:'center',formatter:ctrl.nameFormatter},
				{field:'startDate',title:'活动日期',width:130,align:'center', formatter:ctrl.actDateFormatter},
				{field:'createUserName',title:'活动创建人',width:60,align:'center'},
				{field:'createTime',title:'创建时间',width:80,align:'center'},
				{field:'marketNames',title:'活动所属市场 ',width:100,align:'center'},
			]]
		}); 
	}
	
	this.nameFormatter=function(value,row,index){
		if(canEdit == 1){
			return "<a href='javascript:openActive("+row.actId+")'>"+row.name+"</a>";
		} else {
			return row.name;
		}

	}
	
	this.actDateFormatter=function(value,row,index){
		return row.startTime +"~~"+row.endTime;
	}
}

function openActive(actId){
	$("#win").window("open");
	$("#win").window("refresh",CONTEXT+"active/toPromBaseDetail?actId="+$.trim(actId));
	
}

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

function closeWindow(close){
	//刷新列表
	$('#activedg').datagrid("reload");
	if(close){
		$('#win').dialog('close');
	}
}


