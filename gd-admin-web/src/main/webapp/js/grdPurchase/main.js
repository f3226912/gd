//初始化加载页面列表
function initList(){
	loadList(null);
	//分页加载
	$("#purchase_datagrid-table").datagrid("getPager").pagination({
		pageList: [50,100,200,300,500]
	});
}
//加载列表数据
function loadList(params){
	params = !params ? {}: params;
	//数据加载
	$('#purchase_datagrid-table').datagrid({
		url:CONTEXT+'grdPurchase/query',
		width: 'auto',  
		queryParams: params,
		height: 'auto', 
		nowrap:true,
		toolbar:'#purchase_datagrid-tool-bar',
		pageSize:50,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		singleSelect:true,
		onLoadSuccess:function(){
			$("#purchase_datagrid-table").datagrid('clearSelections');
		},
		columns:[[
		    {field:'id',title:'',width:50,checkbox:true},
		    {field:'purchaseNO1',title:'采购单编号',width:100,align:'center',halign:'center',formatter : updateOrview},
			{field:'marketName',title:'所属市场',width:100,align:'left',halign:'center'},
			{field:'warehouseName',title:'所属仓库',width:100,align:'left',halign:'center'},
			{field:'orderamount',title:'订单总金额',width:100,align:'right',halign:'center',formatter:formatNumber },
			{field:'createTime',title:'订单创建时间',width:100,align:'center',halign:'center'},
			{field:'status',title:'状态',width:100,align:'center',halign:'center',formatter: formatStatus},
			{field:'purchaser',title:'采购申请人',width:100,align:'left',halign:'center'},
			{field:'remark',title:'备注',width:100,align:'left',halign:'center'}
//			{field:'updateUserName',title:'updateUserName',width:100,align:'center'},
//			{field:'createTime',title:'createTime',width:100,align:'center'},
//			{field:'purchaser',title:'purchaser',width:100,align:'center'},
//			{field:'remark',title:'remark',width:100,align:'center'},
//			{field:'status',title:'status',width:100,align:'center'},
//			{field:'updateTime',title:'updateTime',width:100,align:'center'},
//			{field:'warehouseId',title:'warehouseId',width:100,align:'center'},
//			{field:'createUser',title:'createUser',width:100,align:'center'},
//			{field:'createUserName',title:'createUserName',width:100,align:'center'},
//			{field:'purchaseNO',title:'purchaseNO',width:100,align:'center'},
//			{field:'updateUser',title:'updateUser',width:100,align:'center'},
			//{field : 'opt',title : '操作',width : 100,align : 'center',formatter : optformat}
		]]
	}); 
}
function formatNumber(val, row) {
	if (val != null) {
		return val.toFixed(2);
	}
}
function updateOrview(value,row,index){
	var html="";
	if(row.status=="1"){
		html+="<a class='operate' href='javascript:;' onclick=edit('"+row.id+"');>"+row.purchaseNO+"</a>";
	}else{
		html+="<a class='operate' href='javascript:;' onclick=view('"+row.id+"');>"+row.purchaseNO+"</a>";
	}
	return html;
}

function query(){
	var marketId = $('#purchase_datagrid-form #marketId').val();
	var warehouseId = $('#purchase_datagrid-form #warehouseId').val();
	var status = $('#purchase_datagrid-form #status').val();
	var purchaseNO = $('#purchase_datagrid-form #purchaseNO').val();
	var params = {
		"marketId" : marketId,
		"warehouseId" : warehouseId,
		"status" : status,
		"purchaseNO" : purchaseNO
	};
	loadList(params);
	//重启导出功能
	disableExport = false ;
}

function reset(){
	$('#purchase_datagrid-form')[0].reset();
	//重启导出功能
	disableExport = false ;
}

function refresh(){
	$('#purchase_datagrid-form')[0].reset();
	$("#purchase_datagrid-table").datagrid('load', {});
	//重启导出功能
	disableExport = false ;
}
var isSave=true;
$(function(){
	initList();
	//查询按钮
	$('#btn-search').click(function(){
		query();
	});
	
	//刷新按钮
	$('#btn-refresh').click(function(){
		refresh();
	});
	
	//重置按钮
	$('#btn-reset').click(function(){
		reset();
	});
	
	// 新增

	$('#btn-add').click(function() {
		$('<div></div>').dialog({
          id : 'saveDialog',
          title : '采购单明细',
          width : 800,
          height : 430,
          closed : false,
          cache : false,
          href : CONTEXT+'grdPurchase/beforeSave',
          modal : true,
          onLoad : function() {
              //初始化表单数据
          },
          onClose : function() {
              $(this).dialog('destroy');
          },
          buttons : [ {
              text : '保存',
              iconCls : 'icon-save',
              handler : function() {
            	 // if(isSave){
                	  isSave =false;
                	  save();
            	  //}
                  return false; // 阻止表单自动提交事件
              }
          }, {
              text : '取消',
              iconCls : 'icon-cancel',
              handler : function() {
                  $("#saveDialog").dialog('destroy');
              }
          } ],
      });
	});
	
	// 修改
	$('#btn-edit').click(function() {
		var row = $('#purchase_datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择要操作的数据！");
			return false;
		} else if($(row).length == 1){
			edit(getSelections("id"));
		} else if($(row).length > 1){
			warningMessage("请不要选择多条数据！");
			return false;
		}
	});
	
	// 删除操作
	$("#btn-remove").click(function() {
		var row = $('#purchase_datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择采购单");
			return false;
		}
		var dodel = true;
		for(var i=0; i<row.length; i++){
			var statue = row[i].status;
			if(statue!='1'){
				dodel = false;
				break;
			}
			
		}
		if(dodel){
			del(getSelections("id"));
		}else{
			warningMessage("采购单已存在入库记录，不允许删除！");
		}
		
		
	});
	
	// 关闭采购单操作
	$("#btn-close").click(function() {
		var row = $('#purchase_datagrid-table').datagrid("getSelections");
		if ($(row).length < 1) {
			warningMessage("请选择采购单");
			return false;
		}
		close(getSelections("id"));
	});
});

//验证单价，数量，金额是否为空
function validata(cla){
	var isblank = false;
	$.each($(cla),function(){
		if(null==$(this).val()||$(this).val()==""){
			isblank = true;
			return;
		}
		if(cla==".countcla"||cla==".unitPricecla"){
			if($(this).val()*1>1000000){
				isblank = true;
				return;
			}
		}
	})
	return isblank;
}

function save() {
	$("#addBtn").linkbutton("disable");
	if($('#save-form').form('validate')){
		var save_purchaser = $("#save_purchaser").val();
		if(save_purchaser==null||save_purchaser==""){
			warningMessage("请填写采购人");
			isSave =true;
			return false;
		}
		if(save_purchaser.length>=20){
			warningMessage("采购人不能超过20");
			isSave =true;
			return false;
		}
		
		
		var selectGifts = $("#seledgiftId").val();
		if(selectGifts==null||selectGifts==""){
			warningMessage("请选择礼品");
			isSave =true;
			return false;
		}
		
		var marketId = $("#save_marketId").val();
		var storedId = $("#save_warehouseId").val();
		if(marketId==null||marketId==""){
			warningMessage("请选择市场");
			isSave =true;
			return false;
		}
		if(storedId==null||storedId==""){
			warningMessage("请选择仓库");
			isSave =true;
			return false;
		}
		var word = $("#save_remark").val();
		if(word!=null&&$.trim(word)!=""){
			if(word.length>200){
				warningMessage("备注请在200字范围内");
				isSave =true;
				return false;
			}
		}
		
		if(validata(".amountcla")||validata(".countcla")||validata(".unitPricecla")){
			warningMessage("单价，数量，金额都不能为空，且单价，数量不能超过100万");
			isSave =true;
			return false;
		}
		
		var totalAmount = $("#totalAmount").val();
		if(totalAmount*1>=100000000){
			warningMessage("总金额不能超过1亿");
			isSave =true;
			return false;
		}
		
		
		var url = CONTEXT + "grdPurchase/save";
		jQuery.post(url, $('#save-form').serialize(), function(data) {
			//var data = jQuery.parseJSON(d);
			if (data.msg == "success") {
				isSave =true;
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#purchase_datagrid-table").datagrid('reload');
				$('#saveDialog').dialog('close');
			} else {
				warningMessage(data.msg);
				isSave =true;
				return;
			}
		});
	}
}

/**修改操作
 * @param id 当前对象ID
 */
function edit(id){
	$('<div></div>').dialog({
      id : 'editDialog',
      title : '编辑采购单',
      width : 800,
      height : 430,
      closed : false,
      cache : false,
      href : CONTEXT+'grdPurchase/edit/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '保存',
          iconCls : 'icon-save',
          handler : function() {
        	  saveEdit();
              return false; // 阻止表单自动提交事件
          }
      }, {
          text : '取消',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#editDialog").dialog('destroy');
          }
      } ],
  });
}



function saveEdit() {
	if($('#edit_form').form('validate')){
		var selectGifts = $("#seledgiftId").val();
		if(selectGifts==null||selectGifts==""){
			warningMessage("请选择礼品");
			return false;
		}
		
		var marketId = $("#edit_marketId").val();
		var storedId = $("#edit_warehouseId").val();
		if(marketId==null||marketId==""){
			warningMessage("请选择市场");
			return false;
		}
		if(storedId==null||storedId==""){
			warningMessage("请选择仓库");
			return false;
		}
/*		var remark = $("#save_remark").val();
		if(remark!=null&&$.trim(remark)!=""){
			if(remark.length>200){
				warningMessage("备注请在200字范围内");
				return false;
			}
		}*/
		
		if(validata(".amountcla")||validata(".countcla")||validata(".unitPricecla")){
			warningMessage("单价，数量，金额都不能为空，且单价，数量不能超过100万");
			return false;
		}
		
		var totalAmount = $("#totalAmount").val();
		if(totalAmount*1>=100000000){
			warningMessage("总金额不能超过1亿");
			return false;
		}
		
		var url = CONTEXT + "grdPurchase/saveEdit";
		jQuery.post(url, $('#edit_form').serialize(), function(data) {
			//var data = jQuery.parseJSON(json);
			if (data.msg == "success") {
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#purchase_datagrid-table").datagrid('reload');
				$('#editDialog').dialog('close');
			} else {
				warningMessage(data.msg);
				return;
			}
		});
	}
}



/**查看操作
 * @param id 当前对象ID
 */
function view(id){
	$('<div></div>').dialog({
      id : 'viewDialog',
      title : '采购单详情',
      width : 800,
      height : 430,
      closed : false,
      cache : false,
      href : CONTEXT+'grdPurchase/view/'+id,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '确定',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#viewDialog").dialog('destroy');
          }
      } ],
  });
}

function del(id){
	jQuery.messager.confirm('提示', '您确定要删除所选数据吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "grdPurchase/delete", {
				"ids" : id
			}, function(d) {
				var data = jQuery.parseJSON(d);
				if (data.msg == "success") {
					slideMessage("操作成功！");
					$('#purchase_datagrid-table').datagrid('reload');
					$('#purchase_datagrid-table').datagrid("uncheckAll");
				} else {
					warningMessage(data.msg);
					return false;
				}
			});
		} else {
			return false;
		}
	});
}

function close(id){
	jQuery.messager.confirm('提示', '您确认关闭采购单吗?', function(r) {
		if (r) {
			jQuery.post(CONTEXT + "grdPurchase/close", {
				"ids" : id
			}, function(d) {
				var data = jQuery.parseJSON(d);
				if (data.msg == "success") {
					slideMessage("关闭采购单成功！");
					$('#purchase_datagrid-table').datagrid('reload');
					$('#purchase_datagrid-table').datagrid("uncheckAll");
				} else {
					warningMessage(data.msg);
					return false;
				}
			});
		} else {
			return false;
		}
	});
}


