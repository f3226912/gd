
/*function initProductLabels(){
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
}*/

// 全部分类/选择分类
function initProductCategorySwitch(){
 	$('#categorySwitch').combobox({
		valueField:'valueString',
		textField:'keyString',
		url: CONTEXT+'fastProduct/categorySwitch',
		editable:false,
		onSelect:function(record){
			$('#categorySwitch').val(record.valueString);
		},
		onLoadSuccess : function(){
	        var data = $('#categorySwitch').combobox('getData');
			if (data.length > 0) {
				$('#categorySwitch').combobox('select', data[0].valueString);
			}
		}
	});
}

function initPriceType(){
 	$('#priceType').combobox({
		valueField:'valueString',
		textField:'keyString',
		url: CONTEXT+'fastProduct/loadPriceType',
		editable:false,
		onSelect:function(record){
	         $('#editForm #priceType').val(record.valueString);
	         if(record.valueString == '1'){
	         	$('#editForm #priceEditContainer').hide();
	         	$('#editForm #priceEditTableContainer').show();
	         	addPriceRow();
	         }else{
	         	$('#editForm #priceEditContainer').show();
	         	$('#editForm #priceEditTableBody').empty();
	         	$('#editForm #priceEditTableContainer').hide();
	         }
		},
		onLoadSuccess : function(){
	        var v = $('#editForm #priceType').val();
	        if(v=='1'){
	         	$('#editForm #priceEditContainer').hide();
	         	$('#editForm #priceEditTableContainer').show();
	        }else{
	        	$('#editForm #priceEditContainer').show();
	         	$('#editForm #priceEditTableBody').empty();
	         	$('#editForm #priceEditTableContainer').hide();
	        }
        	$('#editForm #priceType').combobox('select', v);
		}
	});
}


var categoryId ;
//初始化一级分类(联动查询二三级分类)
function initProductCategory(marketId){
	//商品运营分类
 	$('#categoryType_q1').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/listTopProductCategory/' + marketId,
		editable:false,
		onSelect:function(record){
			updateSecondLevel(record.categoryId);
		},
		onLoadSuccess : function(){
			var data = $('#categoryType_q1').combobox('getData');
			if (data.length > 0) {
				$('#categoryType_q1').combobox('select', data[0].categoryId);
			}
			$('#categoryType_q1').next(".combo").show();
		}
	});
}

//分类级联菜单start...
function updateSecondLevel(parentId){
	$('#categoryType_q2').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		editable:false,
		onSelect:function(record){
			updateThirdLevel(record.categoryId);
		},
		onLoadSuccess : function(){
			var data = $('#categoryType_q2').combobox('getData');
			if (data.length > 0) {
				$('#categoryType_q2').combobox('select', data[0].categoryId);
			}
			$('#categoryType_q2').next(".combo").show();
		}
	});
}

function updateThirdLevel(parentId){
	$('#categoryType_q3').combobox({
		valueField:'categoryId',
		textField:'cateName',
		url: CONTEXT+'product/getChildProductCategory/' + parentId,
		editable:false,
		onSelect:function(record){
			//设置商品分类
			$("input[name='selectedCategoryId']").val(record.categoryId);
			
		},
		onLoadSuccess : function(){
			var data = $('#categoryType_q3').combobox('getData');
			if (data.length > 0) {
				$('#categoryType_q3').combobox('select', data[0].categoryId);
			}
			$('#categoryType_q3').next(".combo").show();
		}
	});
}
//分类级联菜单end...

//初始化产品列表
function initProductList(options){
	var params = {sort : "createTime", "order" : "d"};
	loadProduct(params);
	//分页加载
	$("#productdg").datagrid("getPager").pagination({
		pageList: [10,20,50,100]
	});
}

//根据条件查询产品列表
function loadProduct(params){
	params = !params ? {}: params;
	//数据加载
	$('#productdg').datagrid({
		url:CONTEXT+'fastProduct/list',
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
			{field:'productId',title:'产品Id', width:50, checkbox:true},
			{field:'productName',title:'商品名称',width:100, align:'center'},
			{field:'cateName',title:'商品类目',width:100,align:'center'},
			{field:'originProvinceName',title:'商品产地',width:100,align:'center', formatter: formatOriginPlace},
			{field:'sysUserCode',title:'创建人',width:100,align:'center'},
			{field:'createTime',title:'创建时间',width:100,align:'center'},
			{field:'updateTime',title:'更新时间',width:100,align:'center'},
			{field:'state',title:'审核状态',width:100,align:'center', formatter: formatState},
			{field:'opt',title:'操作',width:100,align:'center',formatter : fpListFormat}
		]],
		onDblClickRow: function(index,row){
		}
	});
}

function fpListFormat(value,row,index){
	var options = "<gd:btn btncode='BTNSPGLQBCP04'><a class='operate' href='javascript:;' onclick=showDetail('"+row.productId+"')>查看</a></gd:btn>";
	options += "&nbsp;<gd:btn btncode='BTNSPGLQBCP03'><a class='operate' href='javascript:;' onclick=toEditFastProduct('"+row.productId+"',"+row.state + ")>修改</a></gd:btn>";
	options += "&nbsp;<gd:btn btncode='BTNSPGLQBCP02'><a class='operate' href='javascript:;' onclick=delSingleProduct('"+row.productId+"');>删除</a></gd:btn>";
	return options;
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

// 格式化产品状态
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

// 查看商品
function showDetail(id){
	$('#detailDialog').dialog({'title':'商品详情', 'width':900, 'height':400, 'href':CONTEXT+'fastProduct/toDetail/'+id }).dialog('open');
}

// 跳转商品编辑
function toEditFastProduct(id, status){
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
        href : CONTEXT+'fastProduct/toEditProduct/'+id ,
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
            	debugger;
                //提交表单
            	editProduct();
            	//重新加载产品列表// wrong; 不可在此处添加, 否则可能导致编辑请求失效(即request被重置导致请求信息丢失[包括提交的编辑信息][异步])
            	//window.location.reload();
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

// <删除>菜单 事件函数 
function delProduct(){
	var rows = $('#productdg').datagrid("getSelections");
	var len = $(rows).length ;
    if( len < 1 ) {
    	warningMessage("请选择要操作的数据！");
        return ;
    }
	var res = window.confirm("确定删除选择的数据么？"); 
	if( !res ) { return; }

	if (len == 1){ 
    	// 删除单个产品
    	var productId = $(rows)[0].productId ;
    	delSingleProduct(productId);
    } else {
    	// 删除多个产品
    	batchDelete(rows);
    }

}

//删除单个产品
function delSingleProduct(productId){
	 $.ajax( {
		    url:CONTEXT+'fastProduct/delete',// 跳转到 action
		    data:{
		       "productId" : productId
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

//批量删除产品
function batchDelete(rows){

	var ids = [];
	var len = rows.length;
	for(var i=0; i<len; i++){
        var row = rows[i];
        ids[i] = row.productId;
    }
	 $.ajax( {
		    url:CONTEXT+'fastProduct/batchDelete',// 跳转到 action
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

//导出
$("#exportData").click(function(){
	var page_options = $('#productdg').datagrid('getPager').data("pagination").options;
	var switchItem = $('#productSearchForm #categorySwitch').val();
	var categoryId = $("#productSearchForm input[name='selectedCategoryId']").val();
	if(switchItem != 1){
		categoryId = "";
	}	
	var params = {
			"productName" : $("#productSearchForm #productName").val(),
			"startDate" : $("#productSearchForm #startDate").val(),
			"endDate" : $("#productSearchForm #endDate").val(),
			"categoryId" : categoryId,
			"createUserName" : $("#productSearchForm #createUserName").val(),
			"rows" : page_options.pageSize, //pageSize
			"sort" : "createTime",
			"order" : "d"
		};
	//paramList 与  params 保持一致
	var paramList = gudeng.commons.convertParamsToDelimitedList(params);

	$.ajax({
		url: CONTEXT+'fastProduct/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'fastProduct/exportData',paramList,'post' );
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


