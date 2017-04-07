/*是否禁止导出数据*/
var disableExport = false;

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




$(document).ready(function(){
	//数据加载
	$('#grdGiftDataGrid').datagrid({
		//url:CONTEXT+'grdGiftIssued/queryBySearch', 禁止自动加载
		height: 'auto',
		nowrap:true,
		toolbar:'#grdGiftToolBar',
		pageSize:20,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		singleSelect:true,
		fit:true,
		pageList:[10,20,50,100],
		pagination:true,
		onBeforeLoad:setParams,
		onLoadError:function(){warningMessage("查询失败!");},
		onLoadSuccess:function(data){
			 $(this).datagrid("fixRownumber");
			if(!data.isSuccess){warningMessage(data.msg);}
			},
		columns:[[
			{field:'id',title:'发放记录ID',width:80,align:'left',halign:'center' },
			{field:'typeName',title:'类型',width:90,align:'left',halign:'center'},
			{field:'createUser',title:'地推人员',width:90,align:'left',halign:'center'},
			{field:'mobile',title:'客户手机',width:90,align:'left',halign:'center'},
			{field:'realName',title:'客户姓名',width:90,align:'left',halign:'center'},
			{field:'level',title:'用户类型',width:90,align:'center',halign:'center',formatter:formatterLevel},
			{field:'giftBillDate',title:'开单时间',width:125,align:'left',halign:'center' },
			{field:'status',title:'状态',width:90,align:'center',halign:'center',formatter:formatterStatus },
			{field:'giftIssusedDate',title:'礼品发放时间',width:125,align:'left',halign:'center' },
			{field:'countNo',title:'礼品发放数量',width:100,align:'center',halign:'center',formatter:formatterCountNo },
			{field:'grantUser',title:'礼品发放人',width:90,align:'left',halign:'center'},
			{field:'marketName',title:'所属市场',width:150,align:'left',halign:'center'},
			{field:'giftstoreName',title:'所属仓库',width:150,align:'left',halign:'center'}
		]]
	});
	
	//根据权限判断是否加载数据
	initLoadDataGrid();
	
	/* ***********事件绑定***************** */
	// 查询按钮 点击事件
	$('#searchBtn').click(function(){
		$('#grdGiftDataGrid').datagrid('load');
	});
	
	// 重置按钮 点击事件
	$('#resetBtn').click(function(){
		$('#grdGiftIssuedForm')[0].reset();
	});
	
	// 点击"导出"按钮
	$('#exportData').click(function(){
		exportData();
	});
	
	//刷新
	$("#reBtn").click(function(){
		 $("#grdGiftDataGrid").datagrid('load');
		 disableExport=false;
	});
	
	$("#belongToMarket").change(function(){
		var marketid = $(this).val();
		if(marketid){
			$.ajax({
					 type: 'POST',
				     url:CONTEXT+'grdGiftIssued/getChildStoreInfo/'+marketid ,
				     dataType:'json',
				     success: function(data) {
				    	 $("#giftstoreId").html("");
				    	 $("#giftstoreId").append("<option value=''>全部</option>");
				    	 $.each(data.rows, function(index, item){
				    		$("#giftstoreId").append("<option value='"+item.giftstoreId+"'>"+item.giftstoreName+"</option");
				    	 });
				     }
				});
		}else{
			 $("#giftstoreId").html("");
	    	 $("#giftstoreId").append("<option value=''>全部</option>");
		}
	});
	//弹出框保存按钮
	$("#popWinSaveBtn").click(function(){
		$.ajax({
			url: CONTEXT+'grdGiftIssued/updateGrdGiftRecord',
			data: getUpdateGrdGiftRecordParam(),
			type: 'POST',
			dataType:"json",
			success: function(data) {
				if(data.isSuccess) {
					$('#showDialog').dialog('close');
					slideMessage(data.msg)
				} else {
					warningMessage(data.msg);
				}
			},
			error: function(data){
				warningMessage(data);
			}
		});
	});
	
	//监听回车事件
	$(document).keypress(function(e) {
		if(e.which == 13) {
			$('#searchBtn').trigger("click");
		}  
	});
	
});
	
	//设置查询参数
	function setParams(p){
		p.mobile=$('#mobile').val();
		p.status=$('#status').val();
		p.userType=$('#userType').val();
		p.startDateForGiftIssused=$('#startDateForGiftIssused').val();
		p.endDateForGiftIssused=$('#endDateForGiftIssused').val();
		p.recordId=$('#recordId').val();
		p.grantUser=$('#grantUser').val();
		p.createUser=$('#createUser').val();
		p.belongToMarket=$('#belongToMarket').val();
		p.startDateForGiftBill=$('#startDateForGiftBill').val();
		p.endDateForGiftBill=$('#endDateForGiftBill').val();
		p.giftstoreId=$('#giftstoreId').val();
		p.type=$("#type option:selected").val();
	}



// 导出数据
function exportData(){
	var requestParam = getParams();
	if (disableExport){
		slideMessage("已进行过一次数据导出,导出功能已禁用,勿频繁点击导出,若要重新启用导出,刷新当前页面..");
		return false;
	}
	$.ajax({
		url: CONTEXT+'grdGiftIssued/checkExportExcelParams',
		data: requestParam,
		type: 'post',
		dataType:"json",
		success: function(data) {
			
			if(!data.isSuccess) {
				warningMessage(data.msg);
				
			}else if (data.isPassed){
				slideMessage("数据正在导出中, 请耐心等待...");
				disableExport = true ;
				//启动下载
				$.download(CONTEXT+'grdGiftIssued/exportExcelData',requestParam,'post' );
				//恢复导出功能
				//disableExport = false;
				//alert("第三次"+disableExport);
			}else{
				warningMessage(data.reason); 
			}
			//恢复导出功能
			//disableExport = false;
			
		},
		error: function(data){
			warningMessage(data);
			//恢复导出功能
			disableExport = false;
		}
	});
}

//获取查询参数
function getParams(){
	var p = $('#grdGiftDataGrid').datagrid('options').queryParams;
	setParams(p);
	return p;
}

//下载导出的excel数据文件
jQuery.download = function(url, data, method){
	// 获得url和data
    if( url && data ){
        // data 是 string或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data);
        var decodeParams = decodeURIComponent(data);
        // 把参数组装成 form的  input
        var inputs = '';
        jQuery.each(decodeParams.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        });
        // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method || 'post') +'">'+inputs+'</form>')
        	.appendTo('body').submit().remove();
    };
};



 //查看地推礼品详情
function showGrdGiftDetail(id,type){
	var url;

	if(type&&type=="2"){
		url=CONTEXT+'grdGiftIssued/showDetailNst?recordId='+id;
		$("#popWinSaveBtn").hide();
		$("#divCarNo").hide();
	}else{
		$("#popWinSaveBtn").show();
		$("#divCarNo").show();
		url=CONTEXT+'grdGiftIssued/showDetail?recordId='+id;
	}
	$('#showDialog').dialog({
			'title':'礼品发放明细',
			'href':url,
			'width': 900,
			'height': 500,
			'resizable':true
		}).dialog('open');
}

/* ***********数据格式化***************** */
//格式化用户类型
function formatterLevel(val,row,index) {
	var result;
	switch(val) {
		case "1" : 
			result = "谷登农批";
			break;
		case "2" : 
			result = "农速通";
			break;
		case "3" : 
			result = "农商友";
			break;
		case "4" : 
			result = "供应商";
			break;
		default:
			result = val;
			break;
	}
	return result;
}

//格式化礼品发放状态
function formatterStatus(val,row,index) {
	if (val == "0") {
		return "未发放";
	} else if (val == "1") {
		return "已发放";
	}
}

//格式化礼品发放数量
function formatterCountNo(val,row,index) {
	var str ="";
	if (val != "") {
		str += "<a href='javaScript:showGrdGiftDetail(";
		str += row.id;
		if(row.type){
		    str += ","+row.type;
	    }
		str += ")' style='text-decoration:none;'>" ;
		str += val;
		str += "</a>";
	}
	return str;
}

//格式化市场名称
/*function formatterMarketName(val,row,index) {
	//只有礼品已经发放才显示市场
	if(row.status == "1") {
		return val;
	}
	return "";
}*/

// 获取更新礼品发放记录的参数
function getUpdateGrdGiftRecordParam() {
	var requestParam ={};
	
	//礼品记录id
	requestParam.id=$("#popWinRecordId").val();
	
	//车牌id
	requestParam.carNo = $("#popWinCarNo").val();
	
	//车牌图片路径
	var carPicPathArr = new Array();
	$.each($("input[data-role='carPicturePath']"), function(index, obj){
		var picPath = $(obj).val();
		if (picPath != null && picPath != "") {
			carPicPathArr.push(picPath);
		}
	});
	requestParam.carPicture = carPicPathArr.join(",");
	
	return requestParam;
}

//根据权限判断是否加载数据
function initLoadDataGrid(){
	if($('#searchBtn').length > 0) {
		var options = $('#grdGiftDataGrid').datagrid('options');
		//直接赋值url即可，无需手动去load数据，datagrid会在页面渲染的时候自动加载。
		options.url = CONTEXT+'grdGiftIssued/queryBySearch';
		//$('#grdGiftDataGrid').datagrid(options);
	} else {
		warningMessage("您无礼品发放管理模块查询权限！");
	}
};