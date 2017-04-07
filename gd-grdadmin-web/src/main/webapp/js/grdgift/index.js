var disableExport = false;
$(document).ready(function() {
	dataLoad(null);
	$(".datagrid-header-rownumber").text("序号");
});
// 查询按钮,根据name查询
$('#icon-search').click(function() {
	var dataParams = {
		marketId : $('#dataGridSearchForm #marketId').val(),
		id : $('#dataGridSearchForm #id').val(),
		giftNo : $('#dataGridSearchForm #giftNo').val(),
		name : $('#dataGridSearchForm #name').val(),
		giftstoreId : $('#dataGridSearchForm #giftstoreId').val()
	};
	dataLoad(dataParams);
});

function dataLoad(dataParams) {
	// 数据加载
	$('#dataGrid').datagrid({
		url : CONTEXT + 'grdgift/query',
		queryParams : dataParams,
		 width: 'auto',
		height : 'auto',
		nowrap : true,
		toolbar : '#dataGridToolbar',
		pageSize : 50,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		singleSelect:true,
		fit : true,
		onLoadSuccess : function() {
			$("#dataGrid").datagrid('clearSelections');
		},
		columns : [ [ {
			field : 'id',
			title : '仓库礼品编号',
			align : 'left',
			hidden:true,
			width : 60
		},
		{
			field : 'giftNo',
			title : '礼品编码',
			align : 'left',
			width : 100
		},
		{
			field : 'name',
			title : '礼品名称',
			width : 200,
			align : 'left',
			formatter : nameformat
		}, 
		{
			field : 'giftstoreName',
			title : '所属仓库',
			width : 130,
			align : 'left'
		},{
			field : 'marketName',
			title : '所属市场',
			width : 130,
			align : 'left'
		}, {
			field : 'noCount',
			title : '待发放数',
			width : 100,
			align : 'right'
		}, {
			field : 'stockTotal',
			title : '库存',
			width : 100,
			align : 'right'
		}, {
			field : 'unit',
			title : '单位',
			width : 60,
			align : 'center'
		}, {
			field : 'remark',
			title : '备注',
			width : 200,
			align : 'left',
			formatter : remarkformat
		} ] ]
	});
	// 分页加载
	$("#dataGrid").datagrid("getPager").pagination({
		pageList : [ 50, 100, 150, 200 ]
	});
}

function nameformat(value, row, index) {
	var name = row.name;
	if (name && name.length > 30) {
		name = name.substring(0, 30) + "...";
	}
	return "<a class='operate' href='javascript:;' title="+row.name+" onclick=editObj('" + row.id + "');>" + name + "</a>";
}
function remarkformat(value, row, index) {
	var remark = row.remark;
	if (remark && remark.length > 30) {
		remark = remark.substring(0, 30) + "...";
	}
	return remark;
}
//跳转到采购单选择页面
function gotoPurchaseSelect() {
	$('<div></div>').dialog({
        id : 'purchaseSelectDialog',
        title : '礼品采购单',
        width : 900,
        height : 430,
        closed : false,
        cache : false,
        'href' : CONTEXT + 'grdPurchase/initPurchaseSelect',
        modal : true,
        onLoad : function() {
            //初始化表单数据
        },
        onClose : function() {
            $(this).dialog('destroy');
        },
        buttons : [ {
            text : '下一步',
            iconCls : 'icon-save',
            handler : function() {
            	var rowInfo = $("#purchaseDataGrid").datagrid('getSelected');
        		if(rowInfo){
        			var purchaseNO = $("#purchaseDataGrid").datagrid('getSelected').purchaseNO;
        			 $("#purchaseSelectDialog").dialog('destroy');
        			 gotoInstock(purchaseNO);
        		}else{
        			warningMessage("请选择要入库的采购单！");
        			return false;
        		}
          	  return false; // 阻止表单自动提交事件
            }
        }, {
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
                $("#purchaseSelectDialog").dialog('destroy');
            }
        } ]
    });

	/*$('#purchaseSelectDialog').dialog({
		'title' : '礼品采购单',
		'href' : CONTEXT + 'grdPurchase/initPurchaseSelect',
		"width" : 900,
		"height" :500
	}).dialog('open');*/
}
//跳转到入库页面
function gotoInstock(purchaseNO) {
	$('<div></div>').dialog({
        id : 'instockDialog',
        title : '礼品入库',
        width : 900,
        height : 430,
        closed : false,
        cache : false,
        'href' : CONTEXT + 'grdPurchase/initInstock/'+purchaseNO,
        modal : true,
        onLoad : function() {
            //初始化表单数据
        },
        onClose : function() {
            $(this).dialog('destroy');
            window.location.reload(); 
        },
        buttons : [ {
            text : '确认入库',
            iconCls : 'icon-save',
            handler : function() {
            	dealInstock();
            	return false; // 阻止表单自动提交事件
            }
        }, {
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
                $("#instockDialog").dialog('destroy');
                window.location.reload(); 
            }
        } ]
    });
	
}
function dealInstock(){
	var row = $('#instockDataGrid').datagrid("getSelections");
	var allRows = $('#instockDataGrid').datagrid("getRows");
    if($(row).length < 1 ) {
    	warningMessage("请选择入库的礼品！");
		return false;
    }
    var flag1Array = new Array();
    var flag2Array = new Array();
    var flag3Array = new Array();
    for (var i = 0; i < $(row).length; i++) {
    	var instockcountStr = $("#stock"+$(row)[i].purchaseGiftId).val();
    	var reg = /^[0-9]*[1-9][0-9]*$/;
		if(!reg.test(instockcountStr)){
			flag2Array.push($(row)[i].purchaseGiftId);
		}
    	if(parseInt(instockcountStr) > parseInt($(row)[i].count)){
    		flag1Array.push($(row)[i].purchaseGiftId);
    	}
    	if(parseInt(instockcountStr) == parseInt($(row)[i].count)){
    		flag3Array.push($(row)[i].purchaseGiftId);
    	}
    	
    }
   
    if(flag2Array.length >0){
    	warningMessage("不允许入库,请输入大于0的整数!");
    	return false;
    }
    if(flag1Array.length>0){
    	warningMessage("不允许入库,入库数量不能大于原采购数量!");
		return false;	
    }
    var statusFlag = 0;
    if(allRows.length == flag3Array.length ) {
    	statusFlag = 1;
    }

    $.ajax({  
        url: CONTEXT+'/grdInstorage/save',  
        type: "post",  
        async: true,  
        dataType: 'json',  
        data: {"purchaseData":getPurchaseData(statusFlag),"effectRow":getPurchaseDetailData(row)}, 
        success: function (data) {  
            if(data.msg=="success"){  
            	warningMessage("操作成功!");
               }else{  
            	   warningMessage("操作失败!");  
                   return;  
               }  
            $("#instockDialog").dialog('destroy');
            window.location.reload(); 
        }  
      });  

}
function getPurchaseData(statusFlag){
	var instockPurchaseNO = $("#instockPurchaseNO").val();
	var instockremark =  $("#instockremark").val();
	var instockMarketId =  $("#instockMarketId").val();
	var instockWarehouseId =  $("#instockWarehouseId").val();
	var purchaseData = {"purchaseNO":instockPurchaseNO,
			"remarks":instockremark,
			"marketId":instockMarketId,
			"warehouseId":instockWarehouseId,
			"statusFlag":statusFlag};
	return JSON.stringify(purchaseData);
}

function getPurchaseDetailData(row){
	var resultArray = new Array();
	  for (var i = 0; i < $(row).length; i++) {
	    var instockcountStr = $("#stock"+$(row)[i].purchaseGiftId).val();
	    var purchaseGiftIdStr = $(row)[i].purchaseGiftId;
	    var unit =  $(row)[i].unit;
	    var giftNo =$(row)[i].giftNO;
	    var count = $(row)[i].count;
	    var giftName =  $(row)[i].giftName;
	    var resultRow = {"instockcount":instockcountStr,
	    				"purchaseGiftId":purchaseGiftIdStr,
	    				"unit":unit,
	    				"giftNo":giftNo,
	    				"count":count,
	    				"name":giftName
	    				};
	    resultArray.push(resultRow);
	  }
	  return JSON.stringify(resultArray);
}
$("#icon-instock").click(function(){

	$.ajax({
		type: "GET",
		url: CONTEXT+"grdPurchase/getPurchaseByStatusTotal",
		dataType: "json",
		success: function(data) {
			var dataRow = data.rows;
			if(dataRow.length<1){
				warningMessage("无待入库的采购单！");
				return false;
			//}else if(dataRow.length == 1){
			//	gotoInstock(dataRow[0].purchaseNO);
			}else{
				gotoPurchaseSelect();
			}
		}
	});
});


// 删除操作
$("#icon-remove").click(function() {
	/** **此处业务要求，只删除一条记录。由于列表未显示id复选框，因此easyUI取不到选择行的id,以下为此种情况下的id选择*** */
	//var id=$(".datagrid-row-selected .datagrid-cell-c1-id").text();

  var row = $('#dataGrid').datagrid("getSelected");
   var id=	row.id;
	if (!id) {
		warningMessage("请选择要操作的数据！");
		return false;
	}
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
// var deleteStr = getSelections("id");
			jQuery.post(CONTEXT + "grdgift/delete", {
				"ids" : id
			}, function(data) {
				if (data.msg == "success") {
					slideMessage("删除成功！");
					$('#dataGrid').datagrid('reload');
					$('#dataGrid').datagrid("uncheckAll");
				} else {
					warningMessage(data.msg);
					return false;
				}
			});
		} else {
			return false;
		}
	});
});

// 编辑
$("#icon-edit").click(function() {
	var row = $('#dataGrid').datagrid("getSelections");
	if ($(row).length < 1) {
		warningMessage("请选择要操作的数据！");
		return false;
	}
	var seleteStr = getSelections("id");
	if (seleteStr.indexOf(",") > 0) {
		warningMessage("请不要操作多条数据！");
		return false;
	}
	$('#addDialog').dialog({
		'title' : '编辑市场价格信息',
		'href' : CONTEXT + 'marketprice/edit/' + seleteStr,
		"width" : 800,
		"height" :500
	}).dialog('open');
});

/**
 * 查看操作
 * 
 * @param id
 *            对象ID
 */
function viewObj(id) {

}

/**
 * 修改操作
 * 
 * @param id
 *            当前对象ID
 */
function editObj(id) {
	$('#addDialog').dialog({
		'title' : '礼品管理',
		'href' : CONTEXT + 'grdgift/edit/' + id,
		"width" : 800,
		"height" :400
	}).dialog('open');
}

// 新增
$('#icon-add').click(function() {
	$('#addDialog').dialog({
		'title' : '礼品管理',
		'href' : CONTEXT + 'grdgift/add',
		"width" : 800,
		"height" :400
	}).dialog('open');
});

function save() {
	if ($('#addForm').form('validate')) {
		var url = CONTEXT + "grdgift/save";
		jQuery.post(url, $('#addForm').serialize(), function(data) {
			if (data.msg == "success") {
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#dataGrid").datagrid('reload');
				$('#addDialog').dialog('close');
			} else {
				warningMessage(data.msg);
				return;
			}
		});
	}
}

$("#marketId").change(function(){
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

function reset(){
	$('#dataGridSearchForm')[0].reset();
	$("input").val("");
}

function refresh(){
	$('#dataGridSearchForm')[0].reset();
	$("#dataGrid").datagrid('load', {});
}


// 重置
$('#btn-reset').click(function() {
	 reset();
});

// 刷新按钮
$('#icon-refresh').click(function() {
	refresh();
	// 重启导出功能
	disableExport = false;
});

var disableExport = false ;
/**
 * 导出数据
 */
function reportData(){
	var page_options = $('#dataGrid').datagrid('getPager').data("pagination").options;
	var params = {
		"marketId" : $("#marketId").val(),
		"id" : $("#id").val(),
		"giftNo": $("#giftNo").val(),
		"name" : $("#name").val(),
		'giftstoreId': $("#giftstoreId").val(),
		"rows" : page_options.pageSize, //pageSize
		"sort" : "id",
		"order" : "desc"
	};
	var paramList = 'marketId='+params.marketId+ "&id="+params.id	+ "&name="+params.name+ "&giftNo="+params.giftNo
	+ "&rows="+params.rows + "&sort="+params.sort+ "&order="+params.order+ "&giftstoreId="+params.giftstoreId;
	$.ajax({ 
		url: CONTEXT+'grdgift/checkExportParams',
		data : params,
		type:'post',
		success : function(data){
			//检测通过
			if (data && data.status == 1){
				if (!disableExport){
					slideMessage("数据正在导出中, 请耐心等待...");
					disableExport = true ;
					//启动下载
					$.download(CONTEXT+'grdgift/exportData',paramList,'post' );
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
