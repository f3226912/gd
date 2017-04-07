
//卖家商铺
var addShopData = jQuery.parseJSON("[]");
//卖家商品
var addProdData = jQuery.parseJSON("[]");
//买家信息
var addBuyerData = jQuery.parseJSON("[]");

var topCateId = null;
var secCateId = null;
var ThirCateId = null;
var oldMarketId = '${activity.marketId }';
var oldCateId = null;
/**
 * 初始化类目，选择市场时触发
 */
function initCategory() {
	var marketId = $("#market_type").val();
	if(marketId != "")
	{
		//类目下拉框重新初始化
		loadCategory(1);
		
		//如果已经选择商铺和商品数据，则清空
		if(addShopData.length > 0 || addProdData.length > 0)
		{
			//提示是否清空数据
			warningMessage("更改市场，相关数据将全部更新.");
			//清空选择的数据
			addShopData = jQuery.parseJSON("[]");
			addProdData = jQuery.parseJSON("[]");
		}
	}
}

/**
 * 校验活动是否存在，同一活动时间内，商品/商铺/买家/类目是否重复
 * @param refId
 * @param userType 活动对象类型（1活动买家 2活动商铺 3商品类目 4活动商品）
 * @param type 活动类型：（1刷卡补贴，2市场，3平台，4预付款违约金，5物流）
 * @param succFunc
 * @param errFunc
 */
function checkExistAct(refId, userType, type, succFunc, errFunc) {
	debugger;
	if(refId==null)
		return;
	var para = {
			'refer_id':refId,
			'user_type':userType,
			'type':type,
			'isExistStartTime':$("#startTime").val(),
			'isExistEndTime':$("#endTime").val()
	}
	
	$.ajax({
		url: CONTEXT + 'gdActActivity/checkExistAct',
		data: para,
		type: 'POST',
		async: true,
		success: function(data) {
			debugger;
			if (data.total > 0 ) { //存在同时间同类型活动
				if(id !=0)
				{
					//编辑时，重复
					if(id != data.id)
					{
						warningMessage("添加的商品用户中存在相同时间同类型规则，请确认后添加，重复活动编号【"+data.code+"】");
						if(errFunc!=null)
							errFunc();
					}else
					{
						//编辑时，查询到重复数据与当前ID相同
						succFunc();
					}
				}else
				{
					//新增时重复
					warningMessage("添加的商品用户中存在相同时间同类型规则，请确认后添加，重复活动编号【"+data.code+"】");
					if(errFunc!=null)
						errFunc();
				}
				return;
			} else {
				succFunc();
			}
		},
	  dataType: 'json'
	});
}

/**
 * 选择类目，下拉框联动修改
 * @param index 0:,1:一级类目，2：二级类目，3：三级类目的 的下拉框修改
 */
function cateChange(index) {
	var val;
	if(index!=0) {
		val = $("#cate"+index).val();
		
		//将当前类目的下一级、下下级类目清空
		for(var i=index;i<3;i++)
		{
			$("#cate"+(i+1)).find("option").remove();
			$("#cate"+(i+1)).append("<option value='-1'>请选择</option>");
		}
		
		//当前选的值为 请选择
		if(val == -1) {
			//将当前的类目的上级类目的值存储
			if(index!=1) {
				$('#categoryId_add').val($("#cate"+(index-1)).val());
			} else {
				$('#categoryId_add').val(-1);
			}
			
			return;
		}
		
		//选择某个具体有效的类目
		var url = "";
		var userType = 3; //对应商品用户规则表中的 user_type，活动类目
		var actType = 4; //活动类型：预付款违约金
		//校验当前类目是否已经参加活动
		checkExistAct(val, userType, actType, function() {
			//未参加活动
			$('#categoryId_add').val(val);
			
			if(index<3)
			{
				//查询并填充下级类目
				var nextIndex = index + 1;
				loadCategory(nextIndex);
			}
		}, 
		function() {
			//当前选择类目已参加活动，则置空
			$("#cate"+index).val(-1);
			$('#categoryId_add').val(-1);
		});
	}
}

/**
 * 加载类目
 * @param index
 */
function loadCategory(index)
{
	var url;
	//则获取当前类目的下级类目
	if(index == 1) 
	{
		//加载一级类目，根据市场去查询
		url = CONTEXT + 'product/listTopProductCategory/' + $("#market_type").val();
	}else
	{
		//根据上级类目的值去查询
		var lastCategory = $("#cate"+(index-1)).val();
		url = CONTEXT+'product/getChildProductCategory/' + lastCategory;
	}
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        data: "{}",
        dataType: 'json',
        async: false,
        success: function(data) {
        	//清空
        	$("#cate"+index).find("option").remove();
    		$("#cate"+index).append("<option value='-1'>请选择</option>");
    		
    		//填充下拉框
            for(var i = 0; i < data.length; i++) 
            {
        		$("#cate"+index).append("<option value='"+data[i].categoryId+"'>"+data[i].cateName+"</option>");
            }
        }
    });
}

$("body").delegate("#showShopWin","click",function(){
	$('#shopDialog').dialog(
	{
		'title' : '活动信息',
		'width' : 900,
		'height' : 300,
		'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/0',
		onLoad : function() {
	          //初始化表单数据
	          
	          initShopRuleList();
	      }
	}).dialog('open');

});

$("body").delegate("#showProdWin","click",function(){
	$('#shopDialog').dialog(
	{
		'title' : '活动信息',
		'width' : 900,
		'height' : 300,
		'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/2',
		onLoad : function() {
	          //初始化表单数据
	          
	          initShopRuleList();
	          
	      }
	}).dialog('open');

});

$("body").delegate("#showBuyerWin", "click", function() {
	$('#shopDialog').dialog({
		'title' : '买家信息',
		'width' : 900,
		'height' : 300,
		'href' : CONTEXT + 'gdActActivity/gdActActivity_shop_buyer/1',
		onLoad : function() {
	          //初始化表单数据
	          
	          initShopRuleList();
	      }
	}).dialog('open');
});

/**
 * 点击添加商铺按钮，打开添加商铺的弹出框，并初始化数据（将之前选中但未提交的数据重新显示在弹出框内）
 */
function addShop(){
	var shop=$("#market_type").val();
	if(!shop){
		warningMessage('请选择所属市场');
		return;
	}
	
	$('<div></div>').dialog({
      id : 'addShopDialog',
      title : '添加活动商铺',
      width : 800,
      height : 500,
      closed : false,
      cache : false,
      href : CONTEXT+'gdActActivity/gdActActivity_add_shop',
      modal : true,
      onLoad : function() {
          //初始化表单数据
    	  initAddShop();
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '确认',
          iconCls : 'icon-save',
          handler : function() {
        	  $("#addShopDialog").dialog('destroy');
          }
      }]
  });
}

/**
 * 添加商品
 */
function addProd(){
	var shop=$("#market_type").val();
	if(!shop){
		warningMessage('请选择所属市场');
		return;
	}
	
	if(!$("#startTime").val() || !$("#endTime").val()) {
		$("#tt").tabs("select", 0);
		warningMessage("请先确认活动时间");
		return;
	}
	
	$('<div></div>').dialog({
      id : 'addProdDialog',
      title : '添加活动商品',
      width : 800,
      height : 500,
      closed : false,
      cache : false,
      href : CONTEXT+'gdActActivity/gdActActivity_add_prod',
      modal : true,
      onLoad : function() {
          //初始化表单数据
    	  initProdCategory();
    	  initAddProd();
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '确认',
          iconCls : 'icon-save',
          handler : function() {
        	  $("#addProdDialog").dialog('destroy');
          }
      }]
  });
}

/**
 * 添加活动买家
 */
function addBuyer(){
	$('<div></div>').dialog({
      id : 'addBuyerDialog',
      title : '添加活动买家',
      width : 800,
      height : 500,
      closed : false,
      cache : false,
      href : CONTEXT+'gdActActivity/gdActActivity_add_buyer',
      modal : true,
      onLoad : function() {
          //初始化表单数据
    	  initbuyerProd();
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '确认',
          iconCls : 'icon-save',
          handler : function() {
        	  $("#addBuyerDialog").dialog('destroy');
          }
      }]
  });
}


/**
 * 始化商铺数据（将之前选中但未提交的数据重新显示在弹出框内）
 */
function initAddShop() {
	$.each(addShopData, function(x, y) {
		$("#shopselect").append(
				'<option value="' + y.businessId + '">' + y.shopsName
						+ '</option>');
	});
	
	//根据市场查询 主营分类的数据，并初始化到下拉框
	initCateShop();
}

/**
 * 点击查询按钮，根据条件查询商铺并显示
 */
function loadshoplist() {
	var queryParams = {};
	var marketId = $("#market_type").val();
	queryParams.marketId = marketId;
	queryParams.shopsName = $("#shop_buyer #shopsName").val();
	queryParams.mobile = $("#shop_buyer #mobile").val();
	queryParams.level = $("#shop_buyer #level").val();
	queryParams.mainCategoryId = $("#shop_buyer #mainCategoryId").val();
	queryParams.offlineStatus = $("#shop_buyer #offlineStatus").val();
	// 数据加载
	var queryUrl = CONTEXT + 'business/querybysearch';
	$('#showshop_table').datagrid({
		url : queryUrl,
		width : 'auto',
		height : 'auto',
		singleSelect:false,
		nowrap : true,
		pageSize : 100,
		"queryParams" : queryParams,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		onLoadSuccess : function() {
			$("#showshop_table").datagrid('clearSelections');
		},
		columns : [ [ {
			field : 'businessId',
			title : '商铺ID',
			width : 150,
			align : 'center'
		}, {
			field : 'mobile',
			title : '手机号码',
			width : 100,
			align : 'center'
		}, {
			field : 'shopsName',
			title : '商铺名称',
			width : 100,
			align : 'center'
		}, ] ]
	}); 
}

/**
 * 始化商品数据（将之前选中但未提交的数据重新显示在弹出框内）
 */
function initAddProd() {
	$.each(addProdData, function(x, y) {
		$("#prodselect").append(
				'<option value="' + y.productId + '">' + y.name
						+ '</option>');
	});
}

/**
 * 点击查询按钮，查询商品数据并显示
 */
function loadprodlist() {
	debugger;
	var queryParams = {};
	var marketId = $("#market_type").val();
	queryParams.marketId = marketId;
	queryParams.shopsName = $("#prod_buyer #shopsName").val();
	queryParams.name = $("#prod_buyer #name").val();
	queryParams.mobile = $("#prod_buyer #mobile").val();
	queryParams.level = $("#prod_buyer #level").val();
	queryParams.cateNames = $("#prod_buyer #cateNames").val();
	queryParams.categoryId_add = $("#prod_buyer #categoryId_add").val();
	// 数据加载
	var queryUrl = CONTEXT + 'product/queryforact';
	$('#showprod_table').datagrid({
		url : queryUrl,
		width : 'auto',
		height : 'auto',
		nowrap : true,
		pageSize : 100,
		"queryParams" : queryParams,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		singleSelect : false,
		onLoadSuccess : function() {
			$("#showprod_table").datagrid('clearSelections');
		},
		columns : [ [ {
			field : 'productId',
			title : '商品ID',
			width : 150,
			align : 'center'
		}, {
			field : 'mobile',
			title : '手机号码',
			width : 100,
			align : 'center'
		}, {
			field : 'name',
			title : '商品名称',
			width : 100,
			align : 'center'
		}, ] ]
	}); 
}

/**
 * 始化买家数据（将之前选中但未提交的数据重新显示在弹出框内）
 */
function initbuyerProd() {
	$.each(addBuyerData, function(x, y) {
		$("#buyerselect").append(
				'<option value="' + y.memberId + '">' + y.mobile
						+ '</option>');
	});
}

/**
 * 点击查询按钮，加载添加买家数据
 */
function loadbuyerlist() {
	debugger;
	var queryParams = {};
	queryParams.realName = $("#buyer_buyer #realName").val();
	queryParams.mobile = $("#buyer_buyer #mobile").val();
	queryParams.level = $("#buyer_buyer #level").val();
	queryParams.shopsName = $("#buyer_buyer #shopsName").val();
	queryParams.regetype = $("#buyer_buyer #regetype").val();
	queryParams.startDate = $("#buyer_buyer #startBuyerTime").val();
	queryParams.endDate = $("#buyer_buyer #endBuyerTime").val();
	queryParams.certstatus = $("#buyer_buyer #certstatus").val();
	// 数据加载
	debugger;
	var queryUrl = CONTEXT + 'member/querybysearch';
	$('#showbuyer_table').datagrid({
		url : queryUrl,
		width : 'auto',
		height : 'auto',
		nowrap : true,
		pageSize : 100,
		"queryParams" : queryParams,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		singleSelect : false,
		onLoadSuccess : function() {
			$("#showbuyer_table").datagrid('clearSelections');
		},
		columns : [ [ {
			field : 'realName',
			title : '用户姓名',
			width : 100,
			align : 'center'
		}, {
			field : 'mobile',
			title : '手机号码',
			width : 100,
			align : 'center'
		}, ] ]
	}); 
}

//添加一行
function addone() {
	var rows = $('#showshop_table').datagrid('getSelections');
	
	$.each(rows,function(x,y){
		checkExistAct(y.businessId, 2, 4, function() {
		
			$("#shopselect option[value='"+y.businessId+"']").remove(); //移除重复项
			$("#shopselect").append('<option value="'+y.businessId+'">'+y.shopsName+'</option>');
			
			//先删除，删除商铺集合中对应的商铺数据
			removeShopRule(y.businessId);
			
			//再添加
			addShopData.push(y);
			
		}, null);
	});
	
}

//添加左边所有行
function addall() {
	var rows = $('#showshop_table').datagrid('getData').rows;
	$.each(rows,function(x,y){
		checkExistAct(y.businessId, 2, 4, function() {
		
			$("#shopselect option[value='"+y.businessId+"']").remove(); //移除重复项
			$("#shopselect").append('<option value="'+y.businessId+'">'+y.shopsName+'</option>');
			
			removeShopRule(y.businessId);
			
			addShopData.push(y);
			
		}, null);
	});
	
}

//移除一行
function removeone(){
	removeShopRule($("#shopselect option:selected").val());
	$("#shopselect option:selected").remove();
}
//移除所有
function removeall(){
	$("#shopselect option").remove();
	addShopData = jQuery.parseJSON("[]");
	
}

/*
 * prod
 */
//添加一行
function addprodone(){
	var rows = $('#showprod_table').datagrid('getSelections');
	
	$.each(rows,function(x,y){
		checkExistAct(y.productId, 4, 4, function() {
			$("#prodselect option[value='"+y.productId+"']").remove(); //移除重复项
			$("#prodselect").append('<option value="'+y.productId+'">'+y.name+'</option>');
			
			removeProdRule(y.productId);
			
			addProdData.push(y);
		}, null);
	});
	
}
//添加左边所有行
function addprodall(){
	var rows = $('#showprod_table').datagrid('getData').rows;
	debugger;
	$.each(rows,function(x,y){
		checkExistAct(y.productId, 4, 4, function() {
			$("#prodselect option[value='"+y.productId+"']").remove(); //移除重复项
			$("#prodselect").append('<option value="'+y.productId+'">'+y.name+'</option>');
			
			removeProdRule(y.productId);
			
			addProdData.push(y);
		}, null);
	});
	
}

//移除一行
function removeprodone(){
	removeProdRule($("#prodselect option:selected").val());
	$("#prodselect option:selected").remove();
}
//移除所有
function removeprodall(){
	$("#prodselect option").remove();
	addProdData = jQuery.parseJSON("[]");
}

/*
 * buyer
 */
//添加一行
function addbuyerone(){
	var rows = $('#showbuyer_table').datagrid('getSelections');
	
	$.each(rows,function(x,y){
		$("#buyerselect option[value='"+y.memberId+"']").remove(); //移除重复项
		$("#buyerselect").append('<option value="'+y.memberId+'">'+y.mobile+'</option>');
		
		removeBuyerRule(y.memberId);
		
		addBuyerData.push(y);
	});
}
//添加左边所有行
function addbuyerall(){
	var rows = $('#showbuyer_table').datagrid('getData').rows;
	debugger;
	$.each(rows,function(x,y){
		$("#buyerselect option[value='"+y.memberId+"']").remove(); //移除重复项
		$("#buyerselect").append('<option value="'+y.memberId+'">'+y.mobile+'</option>');
		
		removeBuyerRule(y.memberId);
		
		addBuyerData.push(y);
	});
	
}

//移除一行
function removebuyerone(){
	removeBuyerRule($("#buyerselect option:selected").val());
	$("#buyerselect option:selected").remove();
}
//移除所有
function removebuyerall(){
	$("#buyerselect option").remove();
	addBuyerData = jQuery.parseJSON("[]");
}

/*
 * 删除商铺集合中对应商铺
 */
function removeShopRule(businessId) {
	for (var i = 0; i < addShopData.length; i++) {
		if (addShopData[i].businessId == businessId) {
			addShopData.splice(i, 1);
			break;
		}
	}
}
/*
 * 删除商铺集合中对应买家
 */
function removeBuyerRule(businessId) {
	for (var i = 0; i < addBuyerData.length; i++) {
		if (addBuyerData[i].memberId == memberId) {
			addBuyerData.splice(i, 1);
			break;
		}
	}
}
/*
 * 删除商铺集合中对应商品
 */
function removeProdRule(productId) {
	for (var i = 0; i < addProdData.length; i++) {
		if (addProdData[i].productId == productId) {
			addProdData.splice(i, 1);
			break;
		}
	}
}
/*
 * 删除商铺集合中对应用户
 */
function removeBuyerRule(memberId) {
	for (var i = 0; i < addBuyerData.length; i++) {
		if (addBuyerData[i].memberId == memberId) {
			addBuyerData.splice(i, 1);
			break;
		}
	}
}