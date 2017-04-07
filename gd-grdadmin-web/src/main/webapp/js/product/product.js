function initProductLabels(){
	var hasDefault = $("#loadPlaceholderLabel").val();
 	$('#product_labels').combobox({
		valueField:'valueString',
		textField:'keyString',
		editable:false ,
		url: CONTEXT+'product/loadProductLabels/' + hasDefault,
		onSelect:function(record){
			$('#product_label_choosed').val(record.valueString);
		},
		onLoadSuccess : function(){
			var data = $('#product_labels').combobox('getData');
			$('#product_labels').combobox('select', data[0].valueString);
		}
	});
}
function initProductList(initState){
	var params ;
	if (initState){
		params = {"state" : initState, "sort" : "createTime", "order" : "d"};
	}else {
		params = {sort : "createTime", "order" : "d"};
	}
	loadProduct(params);
	//分页加载
	$("#productdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

function loadProduct(params){
	params = !params ? {}: params;
	//数据加载
	$('#productdg').datagrid({
		url:CONTEXT+'product/list',
		//width: 1000,
		queryParams: params,
		height: 'auto',
		nowrap:true,
		toolbar:'#producttb',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		onLoadSuccess:function(){
			$("#productdg").datagrid('clearSelections');
		},
		columns:[[
			{field:'productId',title:'产品Id',width:50,checkbox:true},
			{field:'productName',title:'商品名称',width:100,align:'center'},
			{field:'cateName',title:'商品类目',width:100,align:'center'},
			{field:'originProvinceName',title:'商品产地',width:100,align:'center', formatter: formatOriginPlace},
			{field:'realName',title:'真实姓名',width:100,align:'center'},
			{field:'account',title:'会员账号',width:100,align:'center'},
			{field:'sysUserCode',title:'创建人',width:100,align:'center'},
			{field:'createTime',title:'创建时间',width:100,align:'center'},
			{field:'updateTime',title:'更新时间',width:100,align:'center'},
			{field:'infoLifeDay',title:'信息有效期',width:100,align:'center'},
			{field:'state',title:'审核状态',width:100,align:'center', formatter: formatState},
			{field:'productSign',title:'商品标签',width:100,align:'center'},
/*			{field:'stockCount',title:'库存',width:100,align:'center'},
			{field:'unit',title:'单位',width:100,align:'center', formatter: formatPrice},
			{field:'price',title:'单价',width:100,align:'center'},
			{field:'shopsName',title:'商铺名称',width:100,align:'center'},
			{field:'createTime',title:'发布时间',width:100,align:'center'},*/
			{field:'opt',title:'操作',width:100,align:'center',formatter:optformat}
		]],
		onDblClickRow: function(index,row){
			//alert(row.productId);
		}
	});
}
function formatOriginPlace(value,row,index){
	var originPlace = row.originProvinceName;
	if (row.originCityName){
		originPlace += "-" + row.originCityName;
	}
	if (row.originAreaName){
		originPlace += "-" + row.originAreaName;
	}
	return originPlace;
}
function formatState(value,row,index){
	if (row.state == 0){
		return "草稿";
	}else if (row.state == 1){
		return "待审核";
	}else if (row.state == 2){
		return "审核不通过";
	}else if (row.state == 3){
		return "已上架";
	}else if (row.state == 4){
		return "已下架";
	}else if (row.state == 5){
		return "已删除";
	}else if (row.state == 6){
		return "历史版本";
	}
}
function formatPrice(value,row,index){
	if (row.unit == 0){
		return "克";
	}else if (row.unit == 1){
		return "公斤";
	}
}

function productDetail(id){
	$('#detailDialog').dialog({'title':'商品详情', 'width':900, 'height':400, 'href':CONTEXT+'product/toDetail/'+id }).dialog('open');
}
function editProduct(id, status){
	var buttonText = '已确定';
	if (status == 2){
		buttonText = '已修改';
	}
	//$('#editDialog').dialog({'title':'编辑商品', 'width':900, 'height':400, 'href':CONTEXT+'product/toEdit/'+id }).dialog('open');
	$('<div></div>').dialog({
        id : 'editDialog',
        title : '编辑商品',
        width : 1000,
        height : 500,
        closed : false,
        cache : false,
        href : CONTEXT+'product/toEdit/'+id ,
        modal : true,
        onLoad : function() {
            //初始化表单数据
        },
        onClose : function() {
            $(this).dialog('destroy');
        },
        buttons : [ {
            text : buttonText,
            iconCls : 'icon-save',
            handler : function() {
                //提交表单
            	edit();
            }
        }, {
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
                $("#editDialog").dialog('destroy');
            }
        } ],
    });
}
function upload(productId){
	$('#uploadDialog').dialog({'title':'上传商品图片', 'width':900, 'height':400, 'href':CONTEXT+'product/toUpload/'+ productId }).dialog('open');
}

//批量删除产品
function batchDelete(rows){

	var ids = [];
	var len = rows.length;
	for(var i=0; i<len; i++){
        var row = rows[i];
        ids[i] = row.productId;
    }
	 $.ajax( {
		    url:CONTEXT+'product/batchDelete',// 跳转到 action
		    data:{
		    	productIds : ids.join(",")
		    },
		    type:'post',
		    dataType:'json',
		    success:function(data) {
		    	window.location.reload();
		     },
		     error : function(data) {
		    	 warningMessage(data);
		    	 alert(data);
		     }
		});
}
function delProduct(){
	var rows = $('#productdg').datagrid("getSelections");
	var len = $(rows).length ;
    if( len < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    } else if (len == 1){
    	var productId = $(rows)[0].productId ;
    	delObj(productId);
    } else {
    	batchDelete(rows);
    }

}
//删除单个产品
function delObj(id){
	 $.ajax( {
		    url:CONTEXT+'product/delete',// 跳转到 action
		    data:{
		       productId : id
		    },
		    type:'post',
		    dataType:'json',
		    success:function(data) {
		        if(data != "0" ){
		        	slideMessage("删除成功！");
		            window.location.reload();
		        }else{
		        	warningMessage("删除失败！");
		        }
		     },
		     error : function() {
		     }
		});
}

//导出
$("#exportData").click(function(){
	var page_options = $('#productdg').datagrid('getPager').data("pagination").options;
	var switchItem = $('#categorySwitch').val(), categoryId = "";
	if(switchItem == 1){
		categoryId = $("input[name='cateId_q']").val();
		marketId = "";
	}else {
		marketId = $("#marketId_q").val();
		categoryId = "";
	}
	var params = {
			"mobile" : $("#mobile").val(),
			"productName" : $("#productName_q").val(),
			"startDate" : $("#startDate").val(),
			"endDate" : $("#endDate").val(),
			"account" : $("#account_q").val(),
			"rows" : page_options.pageSize, //pageSize
			"state" : $("#state").val(),
			"categoryId" : categoryId,
			"createUserName" : $("#createUserName_q").val(),
			"vendor": $("#vendor").val(),
			"marketId" : marketId,
			"sort" : "createTime",
			"order" : "d"
		};
	//paramList 与  params 保持一致
	var paramList = 'mobile='+params.mobile+ "&productName="+params.productName	+ "&startDate="+params.startDate
		+ "&endDate="+params.endDate+ "&account="+params.account+ "&rows="+params.rows + "&state="+params.state
		+ "&categoryId="+params.categoryId+ "&createUserName="+params.createUserName + "&vendor="
		+ params.vendor+ "&marketId="+params.marketId+ "&sort="+params.sort+ "&order="+params.order;

	$.ajax({
		url: CONTEXT+'product/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'product/exportData',paramList,'post' );
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


