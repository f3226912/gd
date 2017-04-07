
$(function(){
	
	 $("#isReverse_cb").change(
				function(){
					//alert($(this).attr("checked")=="checked")
					if($(this).attr("checked")=="checked"){
						//alert( $("#nxDiv").css("display"))
						//根据逆向勾选  商品类目禁用
                        $("#userRuleBuyerCate").attr("disabled","disabled");
						 $("#cate1").attr("disabled","disabled");
						 $("#cate2").attr("disabled","disabled");
						 $("#cate3").attr("disabled","disabled");
						 $("#userRuleBuyerProd").attr("disabled","disabled");
						 $("#addProdBT").attr("disabled","disabled");
						 $("#nxDiv").css("display","");
					}else{
						$("#reverse_tb_comm tr:gt(2)").remove();
						$("#reverse_tb_comm tr:last").find("#startAmt").val("");
						$("#reverse_tb_comm tr:last").find("#endAmt").val("");
						$("#reverse_tb_comm tr:last").find("#propAmt").val("");
						$("#reverse_tb_comm tr:last").find("#limitAmt").val("");
						$("#reverse_tb_comm tr:last").find("#fixedAmt").val("");
					    $("#userRuleBuyerCate").removeAttr("disabled");
						 $("#userRuleBuyerProd").removeAttr("disabled"); 
						$("#nxDiv").reset();
						 $("#nxDiv").css("display","none");

					}
				}	  
			  );
	 
	  if($("#isReverse_cb").attr("checked")=="checked"){
			//alert( $("#nxDiv").css("display"))
		  $("#userRuleBuyerCate").attr("disabled","disabled");
			 $("#cate1").attr("disabled","disabled");
			 $("#cate2").attr("disabled","disabled");
			 $("#cate3").attr("disabled","disabled");
			 $("#userRuleBuyerProd").attr("disabled","disabled");
			 $("#addProdBT").attr("disabled","disabled");
			 $("#nxDiv").css("display","");
		}else{
			//根据逆向勾选取消  解除商品类目禁用
	          $("#userRuleBuyerCate").removeAttr("disabled");

			 $("#cate1").removeAttr("disabled");
			 $("#cate2").removeAttr("disabled");
			 $("#cate3").removeAttr("disabled");
			 $("#nxDiv").css("display","none");

		}
	defineValid(); //规则校验器
	
	//查看时，不需要交互，编辑和新增时需要
	if(typeof(view) == "undefined" || view == "")
	{
		//买家佣金按商品ID/按订单小计金额区间来收取的单选框切换时
		$("input:radio[name='buyer']").change(buyerCommissionChange);
		buyerCommissionChange();
	}
});

function buyerCommissionChange()
{
	//区间
	$("#startAmt").attr("disabled",true);
	$("#endAmt").attr("disabled",true);
	//按比例、按固定金额单选框
	$("input:radio[name='buyer_order_id']").attr("disabled",true);
	//比例，上限
	$("#propAmt").attr("disabled",true);
	$("#limitAmt").attr("disabled",true);
	//固定金额
	$("#fixedAmt").attr("disabled",true);
	//按商品ID收取买家佣金
	$("#buyerOrderProd").attr("disabled",true);
	
	var radioValue = $('input[name="buyer"]:checked').val();
	if(radioValue == 0)
	{
		//按商品ID收取买家佣金
		$("#buyerOrderProd").attr("disabled",false);
		//保底佣金
		$("#buyerAmt").attr("disabled",false);
		
		//清空按订单小计金额区间来收取的数据
		$("#startAmt").val("");
		$("#endAmt").val("");
		$("#limitAmt").val("");
		$("#fixedAmt").val("");
		
		//对已经生效的校验去除样式
		$("#startAmt").removeClass("validatebox-invalid");
		$("#endAmt").removeClass("validatebox-invalid");
		$("#limitAmt").removeClass("validatebox-invalid");
		$("#fixedAmt").removeClass("validatebox-invalid");
	}else if(radioValue == 3)
	{
		//按订单小计金额区间来收取的设置为可用
		$("#startAmt").attr("disabled",false);
		$("#endAmt").attr("disabled",false);
		$("input:radio[name='buyer_order_id']").attr("disabled",false);
		$("#propAmt").attr("disabled",false);
		$("#limitAmt").attr("disabled",false);
		//保底佣金
		$("#buyerAmt").attr("disabled",false);
		
		//按商品ID固定金额收取的清空数据并设置为不可用
		$("#buyerOrderProd").val("");
		
		//如果校验已经生效后，再禁用该控件，则需要手动去掉红色告警框
		$("#buyerOrderProd").removeClass("validatebox-invalid");
	}
}
/*//点击买家固定金额    比例不可编辑
function fixedRadio() {
	 	 $("#propAmt").attr("disabled", true);
			$("#fixedAmt").removeAttr("disabled");  
}
//点击买家比例    固定金额不可编辑
function certRadio() {	
 $("#fixedAmt").attr("disabled", true);
	$("#propAmt").removeAttr("disabled"); 
}
*/
//用户自定义校验规则
function defineValid(){
	  $.extend($.fn.validatebox.defaults.rules, {
		  validateActName: {
	            validator: function(value, param) {
	            	debugger;
	            	var posturl = CONTEXT + "gdActActivity/isExistsActivityName"; //验证活动名是否存在
	            	var para={"activityName":value,"activityId":$("#activityId").val()};
	            	 var exist=$.ajax({
	            		 type: "post",
	                     url:posturl,
	                     data:para,
	                     dataType: "json",
	                     contentType:'application/x-www-form-urlencoded; charset=UTF-8',
	                     async:false
	                 }).responseText;
	            		console.debug(exist);
	            		exist=JSON.parse(exist);
	            		if(exist.flag){
	            			$.fn.validatebox.defaults.rules.validateActName.message =exist.message;
	                        return false;
	            		} 
	            		return true; 
	            },
	            message: ''
	        },
	        numberType: {
	        	validator: function(value, param){
	        		var reg = /^\d+(\.\d{2})?$/;
		             if(!reg.test(value)){
		            	 return false;
		             }
		             return true;
		            },   
		            message: '请输入正确的数字，保留两位小数'
	        },
	        moneyType: {
	        	validator: function(value, param){
	        		var reg = /^\d+(\.\d{2})?$/;
		             if(!reg.test(value)){
		            	 return false;
		             }
		             return true;
		            },   
		            message: '请输入正确的金额，保留两位小数'
	        },
	        validate_mincost: { //验证订单金额 
	        	validator: function(value, param){
	             if(parseFloat(value)>=parseFloat($("#max_cost").val())){
	            	 return false;
	             }
	             if(!regMoney.test(value)) {
	            	 return false;
	             }
	             return true;
	            },   
	            message: '开始区间必须小于结束区间，范围[0,9999999.99]'   
	        },
	        validate_maxcost: { //验证订单金额 
	        	validator: function(value, param){
		             if(parseFloat(value)<=parseFloat($("#min_cost").val())){
		            	 return false;
		             }
		             if(value=="") {
		            	 return true;
		             }
		             if(!regMoney.test(value)) {
		            	 return false;
		             }
		             return true;
		            },   
		            message: '结束区间必须大于开始区间，范围[0,9999999.99]'   
		        },
		   //校验区间开始时间
		  validateSectionStart:{
			  validator: function(value, param){
				  var startAmt = parseInt(value);
				  var layoutId = $(param[0]).val();
				  var templateId = $(param[1]).val();
				  
				  var layout =  $("#"+layoutId);
				  var template = layout.find("#"+templateId);
				  
				  var endAmtVal = template.find("#endAmt").val();
				  if(endAmtVal != "")
				  {
					  var endAmt = parseInt(endAmtVal);
					  if(endAmt <= startAmt)
					  {
						  return false;
					  }
				  }
				  return true; 
			  },
			  message: '开始区间必须小于结束区间'
		  },
		  validateSectionEnd:{
			  validator: function(value, param){
				  var endAmt = parseInt(value);
				  var layoutId = $(param[0]).val();
				  var templateId = $(param[1]).val();
				  
				  var layout =  $("#"+layoutId);
				  var template = layout.find("#"+templateId);
				  
				  var startAmtVal = template.find("#startAmt").val();
				  if(startAmtVal != "")
				  {
					  var startAmt = parseInt(startAmtVal);
					  if(endAmt <= startAmt)
					  {
						  return false;
					  }
				  }
				  return true; 
			  },
			  message: '结束区间必须大于开始区间'
		  }
	    });
}

//下一步操作校验
function nextStepCheck(formid,index, checkFunc){
	if(!$("#"+formid+"").form('validate')){
		return;
	}
	if(checkFunc!=null && checkFunc()) {
		$(".dialog-content")[1].scrollTop=0;
		//$("#divMore").show();//新增时，保存成功后，跳转到下一步
		$("#tt").tabs("select", index);
	}
}

//下一步操作校验
function nextStep(formid,index){
	if(!$("#"+formid+"").form('validate')){
		return;
	}
	$(".dialog-content")[1].scrollTop=0;
	//$("#divMore").show();//新增时，保存成功后，跳转到下一步
	$("#tt").tabs("select", index);
}

//下一步操作校验
function validSave(formid,index){
	if(!$("#"+formid+"").form('validate')){
		$("#tt").tabs("select", index);
		return false;
	}
	return true;
	
}

function selTabs(index) {
	$("#tt").tabs("select", index);
}

/*
 * 保存补贴规则暂时运用
 */
//补贴规则
var subRuleJsonObject = jQuery.parseJSON("[]");
//商品用户卖家商铺
var addShopData = jQuery.parseJSON("[]");
//商品用户卖家商品
var addProdData = jQuery.parseJSON("[]");
//商品用户买家信息
var addBuyerData = jQuery.parseJSON("[]");

function tab(date1,date2){
	var oDate1 = new Date(date1);
	var oDate2 = new Date(date2);
	if(oDate1.getTime() > oDate2.getTime()){
	    return true;
	} else {
	    return false;
    }
}
/*
 * 保存操作
 */
function save() {
	//在判断其他必填项是否都填了
	var forms=$("#tt form");
	var resu=true;
	$.each(forms,function(x,y){
		var index=$(y).attr('id');
		 if(!validSave(index,x)){
			 resu= false;
			 return false;
		 }
		
	});
	
	if(tab($("#startTime").val(),$("#endTime").val())) {
		selTabs(0);
		warningMessage("活动开始时间应小于结束时间");
		return;
	}
	
	//校验是否必填项都填了
	if(!resu){
		return;
	}
	debugger;
	
	/*
	 * 各项值验证
	 */
//	if(!checkComm()) {
//		return;
//	}
	
	/*
	 * 校验商品用户
	 */
	if(!checkProdUser()) {
		return;
	}
	
//	if(!checkMinAmt()){
//		return;
//	}
	//校验买家保低佣金
	if(!checkBuyerMinAmt()){
		return;
	}	
	/**
	 * 校验卖家保低佣金
	 */
	if(!checkSellerMinAmt()){
        return;
	}
	/**
	 * 三选一校验
	 */
	if(!sxycheck()){
		return;
	}
	/*
	 * 校验如果没有添加商品 添加商铺 和 关联类目 则市场规则唯一性
	 */
	debugger;
	if(addProdData.length==0&&addShopData.length==0&&$("#cate1").val()=="-1") {
		var marketId = $("#market_type").val();
		
		if(marketId != oldMarketId) {
			checkExistAct(marketId, 5,2, function() {
				
				sendSaveAjax();
			}, function() {
				selTabs(1);
			});
		} else {
			sendSaveAjax();
		}
	} else {
		if($("#categoryId_add").val()!="-1" && $("#categoryId_add").val()!=oldCateId) {
			checkExistAct($("#categoryId_add").val(), 3, 2, function() {
				sendSaveAjax();
			}, function() {
				selTabs(1);
			});
		} else {
			sendSaveAjax();
		}
	}
	
}

function sendSaveAjax() {

	var transRule = $('#transferRule').serializeArray();
	
	var transRuleJson = jQuery.parseJSON("[]");
	for(var i = 0; i < transRule.length; i++) {
		transRuleJson.push(jQuery.parseJSON('{"m_type":'+transRule[i].value+'}'));
	}

	var url = CONTEXT + "gdactMarketCommission/addBaseInfo";
	var para={
				'basicRule':JSON.stringify(changetoObject($('#basicRule').serializeArray())),
				'marketComm':JSON.stringify(changetoObject($('#marketComm').serializeArray())),
				'userRule':JSON.stringify({
						'pageData':changetoObject($('#userRule').serializeArray()),
						'addShopData':addShopData,
						'addProdData':addProdData,
						'addBuyerData':addBuyerData
					})
			};

	ajaxLoading();
	
	$.ajax({
		url: url,
		data: para,
		type: 'POST',
		success: function(data) {
			if (data.msg == "success") { //活动基本信息
				ajaxLoadEnd();
				slideMessage("保存成功！");
				// 刷新父页面列表
				$("#datagrid-table").datagrid('reload');
				$("#activityDialog").dialog('close');
				
				
			} else {
				warningMessage(data.msg);
				ajaxLoadEnd();
				return;
			}
		},
	  dataType: 'json'
	});
	
}

function checkActTime() {
	if(tab($("#startTime").val(),$("#endTime").val())) {
		selTabs(0);
		warningMessage("活动开始时间应小于结束时间");
		return false;
	}
	return true;
}

function checkComm() {
	if(!marketCommCheck()) {
		selTabs(2);
		return false;
	}
	return true;
}

function checkProdUser() {
	if(!checkCategory()) {
		selTabs(1);
		return false;
	}
	
	/*
	 * 验证商品用户 商铺 商品
	 */
	if($("#userRuleBuyerShop").attr("checked") && (addShopData == null || addShopData.length ==0)) {
		warningMessage("请添加活动商铺");
		selTabs(1);
		return false;
	}
	
	if($("#userRuleBuyerProd").attr("checked") && (addProdData == null || addProdData.length ==0)) {
		warningMessage("请添加活动商品");
		selTabs(1);
		return false;
	}
	return true;
}


function checkCategory() {
	
	if($("#userRuleBuyerCate").attr("checked")) {
		var val = $('#categoryId_add').val();
		
		if(val==null||val==""||val==-1) {
			warningMessage("请选择商品类目");
			return false;
		}
	}
	
	return true;
	
}

var regMoney = /^(([1-9]\d{0,6})|0)(\.\d{1,2})?$/;
var regCert = /^(100|[1-9][0-9]|[1-9]|0)(\.[0-9]{1,2})?$/;

function marketCommCheck() {
	var buyerlayout = $("#buyerLayer");
	if(buyerlayout.find("#prodRadio").attr("checked")=="checked") {
		if(!regMoney.test(buyerlayout.find("#buyerOrderProd").val())) {
			warningMessage("请输入正确的买家商品收取金额[0,9999999.99]");
			return false;
		}
	}
	
	if(buyerlayout.find("#orderRadio").attr("checked")=="checked") {
		if(!regMoney.test(buyerlayout.find("#orderAmt").val())) {
			warningMessage("请输入正确的买家订单收取金额[0,9999999.99]");
			return false;
		}
	}
	
	if(buyerlayout.find("#orderCertRadio").attr("checked")=="checked") {
		if(!regCert.test(buyerlayout.find("#orderBuyerSub").val())) {
			warningMessage("请输入正确的买家订单收取比例[0，100.00]");
			return false;
		}
		if(buyerlayout.find("#orderBuyerLimit").val()&&!regMoney.test(buyerlayout.find("#orderBuyerLimit").val())) {
			warningMessage("请输入正确的买家佣金上限[0,9999999.99]");
			return false;
		}
	}
	
	if(buyerlayout.find("#orderAmtCertRadio").attr("checked")=="checked") {
		
		var template = buyerlayout.find("#template_id");
		
		if(!checkGroup(buyerlayout, template)) {
			return false;
		}
		
		var value = parseInt(buyerlayout.find('#hid_id').val());
		
		if(value>0) {
			for(var i = 0; i < value; i++) {
				template = buyerlayout.find("#template"+i);
				
				if(!checkGroup(buyerlayout, template)) {
					return false;
				}
			}
			
		}
	}
	
	var solderlayout = $("#solderLayer");
	
	if(solderlayout.find("#orderRadio").attr("checked")=="checked") {
		if(!regMoney.test(solderlayout.find("#orderAmt").val())) {
			warningMessage("请输入正确的卖家订单收取金额[0,9999999.99]");
			return false;
		}
	}
	if(solderlayout.find("#orderCertRadio").attr("checked")=="checked") {
		if(!regCert.test(solderlayout.find("#orderSolderSub").val())) {
			warningMessage("请输入正确的卖家订单收取比例[0，100.00]");
			return false;
		}
		if(solderlayout.find("#orderSolderLimit").val()&&!regMoney.test(solderlayout.find("#orderSolderLimit").val())) {
			warningMessage("请输入正确的买家佣金上限[0,9999999.99]");
			return false;
		}
	}
	
	if(solderlayout.find("#orderAmtCertRadio").attr("checked")=="checked") {
		
		var template = solderlayout.find("#template_id");
		
		if(!checkGroup(solderlayout, template)) {
			return false;
		}
		
		var value = parseInt(solderlayout.find('#hid_id').val());
		
		if(value>0) {
			for(var i = 0; i < value; i++) {
				template = solderlayout.find("#template"+i);
				
				if(!checkGroup(solderlayout, template)) {
					return false;
				}
			}
			
		}
	}
	
	return true;
	
}

function checkGroup(layout, template) {
	if(!regMoney.test(template.find("#startAmt").val())) {
		warningMessage("请输入正确的订单开始金额[0,9999999.99]");
		return false;
	}
	debugger;
	if(template.find("#endAmt").val()) {
		if(parseInt(template.find("#endAmt").val()) <= parseInt(template.find("#startAmt").val())) {
			warningMessage("开始金额应大于结束金额");
			return false;
		}
	}
	
	if(template.find("#orderCertRadio").attr("checked")=="checked") {
		
		if(!regCert.test(template.find("#propAmt").val())) {
			warningMessage("请输入正确的比例[0，100.00]");
			return false;
		}
		if(template.find("#limitAmt").val()&&!regMoney.test(template.find("#limitAmt").val())) {
			warningMessage("请输入正确的佣金上限[0,9999999.99]");
			return false;
		}
	} else {
		debugger;
		if(!regMoney.test(template.find("#fixedAmt").val())) {
			warningMessage("请输入正确的固定金额[0,9999999.99]");
			return false;
		}
	}
	
	return true;
}

function ajaxLoading(){
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");   
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});   
}

function ajaxLoadEnd(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}

/**
 * @description 展示商铺和买家信息
 * @param type 显示的类型
 */
function showShop_buyer(type){
	$('<div></div>').dialog({
      id : 'showShop_buyerDialog',
      title : '活动信息',
      width : 800,
      height : 400,
      closed : false,
      cache : false,
      href : CONTEXT+'gdActActivity/gdActActivity_shop_buyer/'+type,
      modal : true,
      onLoad : function() {
          //初始化表单数据
      },
      onClose : function() {
          $(this).dialog('destroy');
      },
      buttons : [ /*{
          text : '保存',
          iconCls : 'icon-save',
          handler : function() {
        	  save();
              return false; // 阻止表单自动提交事件
          }
      },*/ {
          text : '取消',
          iconCls : 'icon-cancel',
          handler : function() {
              $("#showShop_buyerDialog").dialog('destroy');
          }
      } ],
  });
}

function addShop(){
	var shop=$("#market_type").val();
	if(!shop){
		warningMessage('请选择所属市场');
		return;
	}
	
	if(!$("#startTime").val() || !$("#endTime").val()) {
		selTabs(0);
		warningMessage("请先确认活动时间");
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

function addProd(){
	var shop=$("#market_type").val();
	if(!shop){
		warningMessage('请选择所属市场');
		return;
	}
	
	if(!$("#startTime").val() || !$("#endTime").val()) {
		selTabs(0);
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

var changeSubId = null;





function changetoObject(data) {
	var json = {};
	for(var j = 0; j < data.length; j++) {
		eval("json."+data[j].name+"='"+data[j].value+"';");
	}
	return json;
}

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

/*
 * shop
 */
function initAddShop() {
	$.each(addShopData, function(x, y) {
		$("#shopselect").append(
				'<option value="' + y.businessId + '">' + y.shopsName
						+ '</option>');
	});
	initCateShop();
}
//加载添加商铺数据
function loadshoplist() {
	
	/*$.each(addShopData, function(x, y) {
		$("#shopselect").append(
				'<option value="' + y.businessId + '">' + y.shopsName
						+ '</option>');
	});*/

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
	//分页加载
	/*$("#showshop_table").datagrid("getPager").pagination({
		pageList: [20,50,100]
	});*/
}

/*
 * prod
 */
function initAddProd() {
	$.each(addProdData, function(x, y) {
		$("#prodselect").append(
				'<option value="' + y.productId + '">' + y.name
						+ '</option>');
	});
}

//加载添加商铺数据
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

/*
 * buyer
 */
function initbuyerProd() {
	$.each(addBuyerData, function(x, y) {
		$("#buyerselect").append(
				'<option value="' + y.memberId + '">' + y.mobile
						+ '</option>');
	});
}

//加载添加商铺数据
function loadbuyerlist() {
	debugger;
	var queryParams = {};
	//var marketId = $("#market_type").val();
	//queryParams.marketId = marketId;
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
		checkExistAct(y.businessId, 2, 2, function() {
		
			$("#shopselect option[value='"+y.businessId+"']").remove(); //移除重复项
			$("#shopselect").append('<option value="'+y.businessId+'">'+y.shopsName+'</option>');
			
			removeShopRule(y.businessId);
			
			addShopData.push(y);
			
		}, null);
	});
	
}

//添加左边所有行
function addall() {
	var rows = $('#showshop_table').datagrid('getData').rows;
	$.each(rows,function(x,y){
		checkExistAct(y.businessId, 2, 2, function() {
		
			$("#shopselect option[value='"+y.businessId+"']").remove(); //移除重复项
			$("#shopselect").append('<option value="'+y.businessId+'">'+y.shopsName+'</option>');
			
			removeShopRule(y.businessId);
			
			addShopData.push(y);
			
		}, null);
	});
	
}

function checkExistAct(refId, userType, type, succFunc, errFunc) {
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
			if (data.total > 0) { //存在同时间同类型活动
				warningMessage("添加的商品用户中存在相同时间同类型规则，请确认后添加,重复活动编号【"+data.code+"】");
				if(errFunc!=null)
					errFunc();
				return;
			} else {
				succFunc();
			}
		},
	  dataType: 'json'
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
		checkExistAct(y.productId, 4, 2, function() {
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
		checkExistAct(y.productId, 4, 2, function() {
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

function addMoreRule(id) {
	debugger;
	var temp = $("#"+id).find("#template_id");
	
	var replaceStr = "_id";
	var value = $("#"+id).find("#hid_id").attr("value");
	
	var endAmt,satrtAmt;
	
	/*
	 * 验证必须填入结束值才能添加新的区间
	 */
	debugger;
	if(value==0) {
		endAmt = $("#"+id).find("#template_id").find("#endAmt").val();
	} else {
		endAmt = $("#"+id).find("#template"+(value-1)).find("#endAmt").val();
	}
	
	if(!regMoney.test(endAmt)) {
		warningMessage("必须有结束区间才能添加新区间，且必须为正确金额，数值范围[0,9999999.99]");
		return;
	}
	
	if(value==0) {
		startAmt = $("#"+id).find("#template_id").find("#startAmt").val();
	} else {
		startAmt = $("#"+id).find("#template"+(value-1)).find("#startAmt").val();
	}
	
	if(parseInt(startAmt)>parseInt(endAmt)) {
		warningMessage("开始金额必须小于结束金额");
		return;
	}
	
	var html = temp.prop("outerHTML").replace(new RegExp(replaceStr,'gm'),value);
	if(value==0) {
		$("#"+id).find("#del_a_id").hide();
	} else {
		$("#"+id).find("#del_a"+(value-1)).hide();
	}
	$("#"+id).find("#hid_id").attr("value", parseInt(value)+1);
	$("#"+id+" tbody").append(html);
	
	$("#"+id).find("#template"+value).find("#startAmt").val(endAmt).attr("readonly", true);
	$("#"+id).find("#template"+value).find("#endAmt").attr("readonly", false);
	
	if(value==0) {
		$("#"+id).find("#template_id").find("#endAmt").attr("readonly", true);
	} else {
		$("#"+id).find("#template"+(value-1)).find("#endAmt").attr("readonly", true);
	}

	$("#"+id).find("#del_a"+value).show();
}

function addMoreRuleForLayouter(id) {
	var temp = $("#"+id).find("#template_id");
	var replaceStr = "_id";
	var value = $("#"+id).find("#hid_id").attr("value");
	
	/*
	 * 验证必须填入结束值才能添加新的区间
	 */
	if(value==0) {
		endAmt = $("#"+id).find("#template_id").find("#endAmt").val();
	} else {
		endAmt = $("#"+id).find("#template"+(value-1)).find("#endAmt").val();
	}
	
	if(!regMoney.test(endAmt)) {
		warningMessage("必须有结束区间才能添加新区间，且必须为正确金额，数值范围[0,9999999.99]");
		return;
	}
	
	if(value==0) {
		startAmt = $("#"+id).find("#template_id").find("#startAmt").val();
	} else {
		startAmt = $("#"+id).find("#template"+(value-1)).find("#startAmt").val();
	}
	
	if(parseInt(startAmt)>parseInt(endAmt)) {
		warningMessage("开始金额必须小于结束金额");
		return;
	}
	
	var html = temp.prop("outerHTML").replace(new RegExp(replaceStr,'gm'),value);
	if(value==0) {
		$("#"+id).find("#del_a_id").hide();
	} else {
		$("#"+id).find("#del_a"+(value-1)).hide();
	}
	$("#"+id).find("#hid_id").attr("value", parseInt(value)+1);

	$("#"+id).append(html);
	
	$("#"+id).find("#template"+value).find("#startAmt").val(endAmt).attr("readonly", true);
	$("#"+id).find("#template"+value).find("#endAmt").attr("readonly", false);
	
	if(value==0) {
		$("#"+id).find("#template_id").find("#endAmt").attr("readonly", true);
	} else {
		$("#"+id).find("#template"+(value-1)).find("#endAmt").attr("readonly", true);
	}
	
	$("#"+id).find("#del_a"+value).show();
}

function delRul(id,value) {
	var beforValue = parseInt(value)-1;
	if(value!=0) {
		$("#"+id).find("#del_a" + beforValue).show();
		$("#"+id).find("#template"+beforValue).find("#endAmt").attr("readonly", false);
	} else {
		$("#"+id).find("#template_id").find("#endAmt").attr("readonly", false);
	}
	
	$("#"+id).find("#hid_id").attr("value", value);
	$("#"+id).find("#template"+value).remove();
}

function includeExcel() {
    var tempStr = "";  
    //得到文件路径的值  
    var filePath = document.getElementById("upfile").value;
    
    //创建操作EXCEL应用程序的实例  
    var oXL = new ActiveXObject("Excel.application");
     //打开指定路径的excel文件  
    var oWB = oXL.Workbooks.open(filePath);
    //操作第一个sheet(从一开始，而非零)
    oWB.worksheets(1).select();
    var oSheet = oWB.ActiveSheet;
    //使用的行数  
  var rows =  oSheet .usedrange.rows.count;
    try {  
       for (var i = 2; i <= rows; i++) {
          if (oSheet.Cells(i, 2).value == "null" || oSheet.Cells(i, 3).value == "null") break;
          var a = oSheet.Cells(i, 2).value.toString() == "undefined" ? "": oSheet.Cells(i, 2).value;
          tempStr += (" " + oSheet.Cells(i, 2).value + " " + oSheet.Cells(i, 3).value + " " + oSheet.Cells(i, 4).value + " " + oSheet.Cells(i, 5).value + " " + oSheet.Cells(i, 6).value + "\n");  
       }
    } catch(e) {
       document.getElementById("txtArea").value = tempStr;
    }  
    document.getElementById("txtArea").value = tempStr;
    //退出操作excel的实例对象  
    oXL.Application.Quit();  
     //手动调用垃圾收集器  
    CollectGarbage();  
}

function isReverseCk(){
	 var ck=$("#isReverse_cb").attr("checked");
	   if(ck=="checked"){
		 $("#nxDiv").removeAttr("display");
		 }
}
//该函数对金额区间文本框做 禁用启用
function disabledTrue(obj){
	 var name=$(obj).attr("name")
     $("input[name='"+name+"']").each(
     function(){
    	 if($(this).attr("checked")=="checked"){
    		 $(this).next().removeAttr("disabled");
    		 if($(this).next().next()!="undefined"){
    			 $(this).next().next().removeAttr("disabled");
    		 }
    	 }else{
    		 $(this).next().attr("disabled","disabled");
    		 if($(this).next().next()!="undefined"){
    			 $(this).next().next().attr("disabled","disabled");
    		 }
    	 }
     }		 
     )
}

//该函数对买家保底金额做校验
var checkBuyerMinAmt=function(){
	debugger;
	var buyerLimitAmt=$("#buyerLayer #limitAmt");
	var buyerFixedAmt=$("#buyerLayer #fixedAmt");
	var buyerOrderProd=$("#buyerOrderProd");
	var b=true;
	
	var buyerStartAmt=$("input[name^='buyerStartAmt']");
	var buyerEndAmt=$("input[name^='buyerEndAmt']");
	var buyerPropAmt=$("input[name^='buyerPropAmt']");
	var buyerLimitAmt=$("input[name^='buyerLimitAmt']");
	var buyerFixedAmt=$("input[name^='buyerFixedAmt']");
	
    if($(buyerOrderProd).attr("disabled")!="disabled"){
    	if(parseFloat($(buyerOrderProd).val())<parseFloat($("#buyerAmt").val()) ){
    		warningMessage("买家保底佣金不能大于商品ID收取佣金");
			selTabs(2);
			b=false;
			return b;
    	}
    }
	if($("#buyerAmt").val()!=""){
		//校验区间值
		for(var i=0;i<buyerPropAmt.length;i++){
			if($(buyerStartAmt[i]).attr("disabled")!="disabled"){

            if($(buyerStartAmt[i]).val()==""){
            	warningMessage("买家佣金区间不能为空,数值范围为[0,9999999.99]");
				selTabs(2);
				b=false;
				return b;
            }
			if($(buyerPropAmt[i]).attr("disabled")!="disabled"){
				if($(buyerPropAmt[i]).val()==""){
					warningMessage("买家佣金比例不能为空,数值范围为[0,100.00]");
					selTabs(2);
					b=false;
					return b;

				}
			}
			}
		}
		//校验买家固定佣金
		for(var i=0;i<buyerFixedAmt.length;i++){
			if($(buyerStartAmt[i]).attr("disabled")!="disabled"){

			if($(buyerFixedAmt[i]).attr("disabled")!="disabled"){
				if($(buyerFixedAmt[i]).val()==""){
					warningMessage("买家固定佣金金额不能为空,数值范围为[0,9999999.99]");
					selTabs(2);
					b=false;
					return b;

				}
			}
			}
		}
		}
    
    
	for(var i=0;i<buyerLimitAmt.length;i++){
	
		if($(buyerLimitAmt[i]).attr("disabled")!="disabled"){
			if(parseFloat($(buyerLimitAmt[i]).val())<parseFloat($("#buyerAmt").val())){
				warningMessage("买家保底佣金不能大于佣金上限");
				selTabs(2);
				b=false;
				return b;
			}
		}
	}
	for(var i=0;i<buyerFixedAmt.length;i++){
	
		if($(buyerFixedAmt[i]).attr("disabled")!="disabled"){
//		     alert(buyerFixedAmt +"=="+i)
//		     alert(buyerAmt +"=="+i)
			if(parseFloat($(buyerFixedAmt[i]).val())<parseFloat($("#buyerAmt").val())){
				warningMessage("买家保底佣金不能大于固定金额");
				selTabs(2);
				b=false;
				return b;
			}
		}
	}
	return b;

	
}
//该函数对卖家保底金额做校验
var checkSellerMinAmt=function(){
	debugger;
	var SellerLimitAmt=$("#solderLayer").find("#limitAmt");
	var SellerFixedAmt=$("#solderLayer").find("#fixedAmt");
	var b=true;
	
	var solderStartAmt=$("#solder_tb_comm").find("#startAmt");
	var solderEndAmt=$("#solder_tb_comm").find("#endAmt");
	var solderPropAmt=$("#solder_tb_comm").find("#propAmt");
	var solderLimitAmt=$("#solder_tb_comm").find("#limitAmt");
	var solderFixedAmt=$("#solder_tb_comm").find("#fixedAmt");
	
	var reverseStartAmt=$("#reverse_tb_comm").find("#startAmt");
	var reverseEndAmt=$("#reverse_tb_comm").find("#endAmt");
	var reversePropAmt=$("#reverse_tb_comm").find("#propAmt");
	var reverseLimitAmt=$("#reverse_tb_comm").find("#limitAmt");
	var reverseFixedAmt=$("#reverse_tb_comm").find("#fixedAmt");
	if(solderPropAmt.length>1){
	for(var i=0;i<solderPropAmt.length;i++){

		if($(solderPropAmt[i]).attr("disabled")!="disabled"){
			if($(solderPropAmt[i]).val()==""){
				warningMessage("卖家正向制单佣金比例不能为空,数值范围为[0，100.00]");
				selTabs(2);
				b=false;
				return b;

			}
		}
	
	}
	for(var i=0;i<solderFixedAmt.length;i++){

		if($(solderFixedAmt[i]).attr("disabled")!="disabled"){
			if($(solderFixedAmt[i]).val()==""){
				warningMessage("卖家正向制单固定佣金金额不能为空,数值范围为[0,9999999.99]");
				selTabs(2);
				b=false;
				return b;

			}
		}
	
	}
	}
	if(reversePropAmt.length>1){
	for(var i=0;i<reversePropAmt.length;i++){

		if($(reversePropAmt[i]).attr("disabled")!="disabled"){
			if($(reversePropAmt[i]).val()==""){
				warningMessage("卖家逆向制单佣金比例不能为空,数值范围为[0，100.00]");
				selTabs(2);
				b=false;
				return b;

			}
		}
	
	}

	for(var i=0;i<reverseFixedAmt.length;i++){

		if($(reverseFixedAmt[i]).attr("disabled")!="disabled"){
			if($(reverseFixedAmt[i]).val()==""){
				warningMessage("卖家逆向制单固定佣金金额不能为空,数值范围为[0,9999999.99]");
				selTabs(2);
				b=false;
				return b;

			}
		}
	
	}
	}
	if(($(solderStartAmt).val()!=""&&($(solderPropAmt).val()!=""||$(solderFixedAmt).val()!=""))
			||($(reverseStartAmt).val()!=""&&($(reversePropAmt).val()!=""||$(reverseFixedAmt).val()!=""))){
		if($("#sellerAmt").val()==""){
			warningMessage("请输入卖家保底佣金,上限[0,9999999.99]");
			selTabs(2);
			b=false;
			return b;
		}
	}
	
	for(var i=0;i<SellerLimitAmt.length;i++){
		if($(SellerLimitAmt[i]).attr("disabled")!="disabled"){
			if(parseFloat($(SellerLimitAmt[i]).val())<parseFloat($("#sellerAmt").val())){
				warningMessage("卖家保底佣金不能大于佣金上限");
				selTabs(2);
				b=false;
				return b;

			}
		}
	}
	for(var i=0;i<SellerFixedAmt.length;i++){
		if($(SellerFixedAmt[i]).attr("disabled")!="disabled"){
			if(parseFloat($(SellerFixedAmt[i]).val())<parseFloat($("#sellerAmt").val())){
				warningMessage("卖家保底佣金不能大于固定金额");
				selTabs(2);
				b=false;
				return b;

			}
		}
	}
return b;
}
//正向 逆向  买家订单  规则 三选一校验规则
var sxycheck=function(){
	debugger;
	var b=false;
	var buyerStartAmt=$("#buyer_tb_comm").find("#template_id").find("#startAmt");
	var buyerEndAmt=$("#buyer_tb_comm").find("#template_id").find("#endAmt");
	var buyerPropAmt=$("#buyer_tb_comm").find("#template_id").find("#propAmt");
	var buyerLimitAmt=$("#buyer_tb_comm").find("#template_id").find("#limitAmt");
	var buyerFixedAmt=$("#buyer_tb_comm").find("#template_id").find("#fixedAmt");
	var buyerOrderProd=$("#buyerOrderProd").val();

	
	var solderStartAmt=$("#solder_tb_comm").find("#template_id").find("#startAmt");
	var solderEndAmt=$("#solder_tb_comm").find("#template_id").find("#endAmt");
	var solderPropAmt=$("#solder_tb_comm").find("#template_id").find("#propAmt");
	var solderLimitAmt=$("#solder_tb_comm").find("#template_id").find("#limitAmt");
	var solderFixedAmt=$("#solder_tb_comm").find("#template_id").find("#fixedAmt");
	
	
	var reverseStartAmt=$("#reverse_tb_comm").find("#template_id").find("#startAmt");
	var reverseEndAmt=$("#reverse_tb_comm").find("#template_id").find("#endAmt");
	var reversePropAmt=$("#reverse_tb_comm").find("#template_id").find("#propAmt");
	var reverseLimitAmt=$("#reverse_tb_comm").find("#template_id").find("#limitAmt");
	var reverseFixedAmt=$("#reverse_tb_comm").find("#template_id").find("#fixedAmt");
	
	var isReverse_cb=$("#isReverse_cb").attr("checked");
	
	
	  if($(reversePropAmt).attr("disabled")!="disabled" && $(reverseLimitAmt).attr("disabled")!="disabled"){
		//判断逆向制单是否勾选  如果勾选 逆向制单必须完整填写一行数据 否则不予校验通过
		  if(isReverse_cb=="checked"){
			  if($(reverseStartAmt).val()==""  || $(reversePropAmt).val()=="" ){
				  warningMessage("逆向制单佣金区间必须填写区间值以及(佣金比例或固定金额)");
					selTabs(2);
					return b;
			  }
		  }
		if($(reverseStartAmt).val()!="" && $(reversePropAmt).val()!="" ){
			if(!regMoney.test($("#sellerAmt").val())){
				warningMessage("请输入正确的卖家保底佣金上限[0,9999999.99]");
				return false;
			}
			b=true;
			return b;
		}
	}
	  if($(reverseFixedAmt).attr("disabled")!="disabled" ){
		 if($(reverseStartAmt).val()!=""   && $(reverseFixedAmt).val()!=""){
			 if(!regMoney.test($("#sellerAmt").val())){
					warningMessage("请输入正确的卖家保底佣金上限[0,9999999.99]");
					return false;
				}
			b=true;
			return b;
		}
	} 
	  var orderAmtCertRadio=$("#orderAmtCertRadio").attr("checked");
	  var prodRadio=$("#prodRadio").attr("checked");
	  if(orderAmtCertRadio=="checked"){
	if($(buyerPropAmt).attr("disabled")!="disabled" && $(buyerLimitAmt).attr("disabled")!="disabled"){
		if($(buyerStartAmt).val()!="" && $(buyerPropAmt).val()!="" ){
			if(!regMoney.test($("#buyerAmt").val())){
				warningMessage("请输入正确的买家保底佣金上限[0,9999999.99]");
				return false;
			}
			b=true;
			return b;
		}
	}
	  if($(buyerFixedAmt).attr("disabled")!="disabled" ){
		 if($(buyerStartAmt).val()!=""   && $(buyerFixedAmt).val()!=""){
			 if(!regMoney.test($("#buyerAmt").val())){
					warningMessage("请输入正确的买家保底佣金上限[0,9999999.99]");
					return false;
				}
			b=true;
			return b;
		}
	}
	  }
	  debugger;
	  if(prodRadio=="checked"){
		  if(buyerOrderProd==""){
				warningMessage("请输入买家商品ID收取佣金");
				return false;
 			  }
		     else if(!regMoney.test(buyerOrderProd)){
			  warningMessage("请输入正确的买家商品ID收取佣金[0,9999999.99]");
				return false;
			  }
		     else{
		    	 if(!regMoney.test($("#buyerAmt").val())){
						warningMessage("请输入正确的买家保底佣金上限[0,9999999.99]");
						return false;
					}
				  return true;
			  }
		  }
	 
	 
	
	  if($(solderPropAmt).attr("disabled")!="disabled" && $(solderLimitAmt).attr("disabled")!="disabled"){
		if($(solderStartAmt).val()!=""  && $(solderPropAmt).val()!="" ){
			if(!regMoney.test($("#sellerAmt").val())){
				warningMessage("请输入正确的卖家保底佣金上限[0,9999999.99]");
				return false;
			}
			b=true;
			return b;
		}
	}
	  if($(solderFixedAmt).attr("disabled")!="disabled" ){
		 if($(solderStartAmt).val()!=""  && $(solderFixedAmt).val()!=""){
			 if(!regMoney.test($("#sellerAmt").val())){
					warningMessage("请输入正确的卖家保底佣金上限[0,9999999.99]");
					return false;
				}
			b=true;
			return b;
		}
	}

	  
	warningMessage("买家佣金和卖家正逆向佣金必须填写区间值以及(佣金比例或固定金额)");
		selTabs(2);
	 
	
	
	return b;
}