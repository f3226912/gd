$(function(){
	defineValid(); //规则校验器
	$("#minIntegralBox").change(function(){
		if($(this).attr("checked")=="checked"){
			$("#minIntegral").removeAttr("disabled");
		}else{
			$("#minIntegral").attr("disabled","disabled");
		}
	}		
	);
	
	$("#dayIntegralBox").change(function(){
		if($(this).attr("checked")=="checked"){
			$("#dayMaxIntegral").removeAttr("disabled");
		}else{
			$("#dayMaxIntegral").attr("disabled","disabled");
		}
	});
});

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
//商品用户买家信息
//补贴规则
var subRuleJsonObject = jQuery.parseJSON("[]");
//商品用户卖家商铺
var addShopData = jQuery.parseJSON("[]");
//商品用户卖家商品
var addProdData = jQuery.parseJSON("[]");
var addBuyerData = jQuery.parseJSON("[]");
var indexArray = jQuery.parseJSON("[]");

function tab(date1,date2){
	var oDate1 = new Date(date1);
	var oDate2 = new Date(date2);
	if(oDate1.getTime() > oDate2.getTime()){
	    return true;
	} else {
	    return false;
    }
}
//1刷卡补贴，2市场，3平台，4预付款违约金，5物流
var activityType = 5 ; 
/*
 * 保存操作
 */
function save() {
		
	//在判断其他必填项是否都填了
	var forms=$("#tt form");
	var resu=true;
	//各页签表单校验
	$.each(forms,function(x,y){
		var index=$(y).attr('id');
		 if(!validSave(index,x)){
			 resu= false;
			 return false;
		 }
		
	});
	
	//活动时间
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
/*	if(!checkComm()) {
		return;
	}*/
	/**
	 * 校验指定买家用户列表
	 */
	debugger;
	if(!checkBuyerIsNull()){
		return;
	}
	
	/**校验积分倍率是否填写完整*/
	if(!checkIntegralRate()){
		return;
	}
	/**
	 * 校验积分倍率格式
	 */
	if(!checkIntegralRateGS()){
		return;
	}
	/**
	 * 校验区间值是否有填写
	 */
	if (!checkIntegralRateComm()){
		return;
	} 
	/**
	 * 校验区间获取积分值是否填写以及格式
	 */
	if (!checkIntegral()){
		return;
	}
	/**
	 * 校验当日累计积分上限是否比区间获取积分值小
	 */
	if (!checkDayMaxIntegral()){
		return;
	}
	/*
	 * 校验商品用户
	 */
	if(!checkProdUser()) {
		return;
	}
	
	/*
	 * 校验如果没有添加商品 添加商铺 和 关联类目 则市场规则唯一性
	 */
        debugger;
		var marketId = $("#market_type").val();
		if(marketId != oldMarketId) {
			checkExistAct(marketId, 5, 6, function() {
				sendSaveAjax();
			}, function() {
				selTabs(1);
			});
		} else {
			sendSaveAjax();
		}
	
	
}

function sendSaveAjax() {
	debugger;

	var url = CONTEXT + "gdProcurementIntegration/save";
	var pageData=$('#userRule').serializeArray();
	var marketData=$('#marketComm').serializeArray();
	if($("#buyerBox0").attr("checked")!="checked"){
		  delete pageData.buyerBox0;
	}
	if($("#minIntegralBox").attr("checked")!="checked"){
		delete marketData.minIntegral;
	}
	if($("#dayIntegralBox").attr("checked")!="checked"){
		delete marketData.dayMaxIntegral;
	}
	var para={
				'basicRule':JSON.stringify(changetoObject($('#basicRule').serializeArray())),
				'marketComm':JSON.stringify(changetoObject(marketData)),
				'userRule':JSON.stringify({
						'pageData':changetoObject(pageData),
						'addBuyerData':addBuyerData,
						'indexArray':indexArray
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
		selTabs(1);
		return false;
	}
	return true;
}

function checkProdUser() {
	if(!checkCategory()) {
		selTabs(3);
		return false;
	}
//	/*
//	 * 验证商品用户 商铺 商品
//	 */
//	if($("#userRuleBuyerShop").attr("checked") && (addShopData == null || addShopData.length ==0)) {
//		warningMessage("请添加活动商铺");
//		selTabs(3);
//		return false;
//	}
//	
//	if($("#userRuleBuyerProd").attr("checked") && (addProdData == null || addProdData.length ==0)) {
//		warningMessage("请添加活动商品");
//		selTabs(3);
//		return false;
//	}
	return true;
}


function checkCategory() {
//	
//	if($("#userRuleBuyerCate").attr("checked")) {
//		var val = $('#categoryId_add').val();
//		
//		if(val==null||val==""||val==-1) {
//			warningMessage("请选择商品类目");
//			return false;
//		}
//	}
	return true;
}



var regMoney = /^(([1-9]\d{0,4}))?$/;//0-99999正则匹配
var regCert = /^([1-9]{1,3}|1000)(\.[0-9]{1,2})?$/;//1-1000 正则匹配

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

//????????????????????????????????
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

//打开	添加商铺	对话框
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

//打开	添加商品	对话框
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

//补贴规则相关???????????????????????
var changeSubId = null;

function addSub(id) {
	$('<div></div>').dialog({
      id : 'addSubDialog',
      title : '添加新规则',
      width : 800,
      height : 500,
      closed : false,
      cache : false,
      href : CONTEXT+'gdActActivity/gdActActivity_add_sub',
      modal : true,
      onLoad : function() {
    	  debugger;
    	  if(payChannels.success) {
    		  debugger;
    		  var pays = payChannels.result;
			  $("#sub_order #payChannel").find("option").remove();
    		  for(var i = 0; i < pays.length; i++) {
    			  var pay = pays[i];
    	    	  $("#sub_order #payChannel").append("<option value='"+pay.payChannelCode+"'>"+pay.payChannelName+"</option>");
    		  }
    		  
    	  } else {
    		  warningMessage(payChannels.msg);
    		  $("#addSubDialog").dialog('destroy');
    	  }
    	  
    	  if(id!=null) {
    		  changeSubId = id;
    		  var data = subRuleJsonObject[id];
    		  
    		  $("#addSubDialog").find("#sub_order #payChannel").val(data.payChannel);
    		  $("#addSubDialog").find("#sub_order #cardType").val(data.cardType);
    		  $("#addSubDialog").find("#sub_order #busiType1").val(data.busiType1);
    		  $("#addSubDialog").find("#sub_order #busiType2").val(data.busiType2);

    		  $("#addSubDialog").find("#sub_order input[type=radio][name=sub_radio][value="+data.sub_radio+"]").attr("checked","checked");

    		  if(data.sub_radio==1) {
    			  $("#addSubDialog").find("#sub_order #orderAmt").val(data.orderAmt);
    		  }
    		  
    		  if(data.sub_radio==2) {
	    		  $("#addSubDialog").find("#sub_order #orderSub").val(data.orderSub);
	    		  $("#addSubDialog").find("#sub_order #orderLimit").val(data.orderLimit);
    		  }

    		  if(data.solderSubCheck=="on") {
        		$("#addSubDialog").find("#sub_order input[name=solderSubCheck]").attr("checked", "checked");
        		$("#addSubDialog").find("#solderSubAmt").val(data.solderSubAmt);
    		  }

    		  /*
    		   * 如果未区间值
    		   */
    		  if(data.sub_radio==3) {
    			  $("#addSubDialog").find("#template_id #startAmt").val(data.startAmt_id);
    			  $("#addSubDialog").find("#template_id #endAmt").val(data.endAmt_id);
    			  if(data.sub_lim_radio_id==0) {
	    			  $("#addSubDialog").find("#template_id #propAmt").val(data.subPerc_id);
	    			  $("#addSubDialog").find("#template_id #limitAmt").val(data.subLimit_id);
    			  } else {
    				  $("#addSubDialog").find("#template_id #fixedAmt").val(data.subAmt_id);
    			  }
    			  debugger;
    			  $("#addSubDialog").find("#template_id input[type=radio][name=sub_lim_radio_id][value="+data.sub_lim_radio_id+"]").attr("checked", "checked");
    			  debugger;
        		  var value = parseInt(data.hid_id);
        		  
        		  for(var i = 0; i < value; i++) {
        			  addMoreRuleForLayouter('sub_order_layout');
        			  eval('$("#addSubDialog").find("#template'+i+' #startAmt").val(data.startAmt'+i+')');
        			  eval('$("#addSubDialog").find("#template'+i+' #endAmt").val(data.endAmt'+i+')');
        			  if(eval('data.sub_lim_radio'+i)==0) {
        				  eval('$("#addSubDialog").find("#template'+i+' #propAmt").val(data.subPerc'+i+')');
            			  eval('$("#addSubDialog").find("#template'+i+' #limitAmt").val(data.subLimit'+i+')');
        			  } else {
        				  eval('$("#addSubDialog").find("#template'+i+' #fixedAmt").val(data.subAmt'+i+')');
        			  }
        			  debugger;
        			  eval('$("#addSubDialog").find("#template'+i+' input[type=radio][name=sub_lim_radio'+i+'][value="+data.sub_lim_radio'+i+'+"]").attr("checked", "checked")');
            		  
        		  }
    		  }
    		  
    		  
    	  }
          //初始化表单数据
      },
      onClose : function() {
    	  changeSubId = null;
          $(this).dialog('destroy');
      },
      buttons : [ {
          text : '提交',
          iconCls : 'icon-save',
          handler : function() {
        	  /*
        	   * 校验数据正确性
        	   */
        	  if(checkSubRule()) {
        		  var data = $("#addSubDialog").find("#sub_order").serializeArray();

            	  var dataarray = changetoObject(data);
            	  
            	  /*
            	   * 判断是否已经存在这个规则
            	   */
            	  debugger;
            	  var index = getIndexOfSub(dataarray);
            	  
            	  if(index!=null&&index!=changeSubId) {
            		  warningMessage("已添加过同类型的补贴规则");
            		  return;
            	  }
            	  
            	  if(changeSubId!=null) {
            		  var changeDate = subRuleJsonObject[changeSubId];
            		  clearMore(changeDate.cardType, changeDate.busiType1, changeDate.busiType2, changeDate.payChannel);
            	  }
            	  
            	  subRuleJsonObject.unshift(dataarray);
            	  
            	  initRuleList();
            	  
            	  $("#addSubDialog").dialog('destroy');
        	  }
          }
	      }, {
	          text : '关闭',
	          iconCls : 'icon-cancel',
	          handler : function() {
	        	  changeSubId = null;
	              $("#addSubDialog").dialog('destroy');
	          }
	      } ],
		});
}

function getIndexOfSub(dataarray) {
	for (var i = 0; i < subRuleJsonObject.length; i++) {
			if (subRuleJsonObject[i].payChannel == dataarray.payChannel
					&& subRuleJsonObject[i].busiType1 == dataarray.busiType1
					&& subRuleJsonObject[i].busiType2 == dataarray.busiType2
					&& subRuleJsonObject[i].cardType == dataarray.cardType) {
				return i;
			}
		}
}

//补贴规则???????????
function checkSubRule() {
	var sublayout = $("#sub_buyer");
	
	if(sublayout.find("#orderRadio").attr("checked")=="checked") {
		if(!regMoney.test(sublayout.find("#orderAmt").val())) {
			warningMessage("请输入正确的买家订单收取金额");
			return false;
		}
	}
	
	if(sublayout.find("#orderCertRadio").attr("checked")=="checked") {
		if(!regCert.test(sublayout.find("#orderSub").val())) {
			warningMessage("请输入正确的买家订单收取比例");
			return false;
		}
		if(sublayout.find("#orderLimit").val()&&!regMoney.test(sublayout.find("#orderLimit").val())) {
			warningMessage("请输入正确的买家佣金上限");
			return false;
		}
	}
	
	if(sublayout.find("#orderAmtCertRadio").attr("checked")=="checked") {
		
		var template = sublayout.find("#template_id");
		
		if(!checkGroup(sublayout, template)) {
			return false;
		}
		
		var value = parseInt(sublayout.find('#hid_id').val());
		
		if(value>0) {
			for(var i = 0; i < value; i++) {
				template = sublayout.find("#template"+i);
				
				if(!checkGroup(sublayout, template)) {
					return false;
				}
			}
			
		}
	}
	return true;
}

function changetoObject(data) {
	var json = {};
	for(var j = 0; j < data.length; j++) {
		eval("json."+data[j].name+"='"+data[j].value+"';");
	}
	return json;
}

//打开	添加买家	对话框
function addBuyer(index){
	$('<div></div>').dialog({
      id : 'addBuyerDialog',
      title : '添加活动买家',
      width : 800,
      height : 500,
      closed : false,
      cache : false,
      href : CONTEXT+'gdActActivity/gdActActivity_add_buyer?index='+index,
      modal : true,
      onLoad : function() {
          //初始化表单数据
    	  for(var i=0;i<addBuyerData.length;i++){
  			//alert(addBuyerData[i]["addBuyerData"+index])
  			if(addBuyerData[i]["addBuyerData"+index]){
  				//alert(1);
  				buyerData=addBuyerData[i]["addBuyerData"+index];
  				initbuyerProd(buyerData);
   			}
  			
  		}
    	  
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
 * shop	初始化	商铺-已选择
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
 * prod		初始化	商品-已选择
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
 * buyer 初始化	买家-已选择
 */
function initbuyerProd(buyerData) {
	$.each(buyerData, function(x, y) {
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

//商铺	添加一行
function addone() {
	var rows = $('#showshop_table').datagrid('getSelections');
	
	$.each(rows,function(x,y){
		checkExistAct(y.businessId, 6, activityType, function() {
		
			$("#shopselect option[value='"+y.businessId+"']").remove(); //移除重复项
			$("#shopselect").append('<option value="'+y.businessId+'">'+y.shopsName+'</option>');
			
			removeShopRule(y.businessId);
			
			addShopData.push(y);
			
		}, null);
	});
	
}

//商铺	添加左边所有行
function addall() {
	var rows = $('#showshop_table').datagrid('getData').rows;
	$.each(rows,function(x,y){
		checkExistAct(y.businessId, 6, activityType, function() {
		
			$("#shopselect option[value='"+y.businessId+"']").remove(); //移除重复项
			$("#shopselect").append('<option value="'+y.businessId+'">'+y.shopsName+'</option>');
			
			removeShopRule(y.businessId);
			
			addShopData.push(y);
			
		}, null);
	});
	
}

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
			if (data.total > 0) { //存在同时间同类型活动
				warningMessage("添加的商品用户中存在相同时间同类型规则，请确认后添加，重复活动编号【"+data.code+"】");
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
 * prod	商品
 */
//添加一行
function addprodone(){
	var rows = $('#showprod_table').datagrid('getSelections');
	
	$.each(rows,function(x,y){
		checkExistAct(y.productId, 4, activityType, function() {
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
		checkExistAct(y.productId, 4, activityType, function() {
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
function addbuyerone(index){
	var rows = $('#showbuyer_table').datagrid('getSelections');
	var integralRate=$("#integralRate"+index).val();
	var flag=false;
	var msg="";
	$.each(rows,function(x,y){
		//alert()
		debugger;
		var b=true;
		for(var i=0;i<indexArray.length;i++){
			if(indexArray[i]!=index){
			for(var j=0;j<addBuyerData.length;j++){
				if(addBuyerData[j]["addBuyerData"+indexArray[i]]){
					var buyerJsonData=addBuyerData[j]["addBuyerData"+indexArray[i]];
					for(var key in buyerJsonData){
						 if(buyerJsonData[key].memberId==y.memberId){
							 flag=true;
							 b=false;
							 msg+=y.mobile+",";
						 }
					}
				}
			}
			}
		}
		if(b){
			
		$("#buyerselect option[value='"+y.memberId+"']").remove(); //移除重复项
		$("#buyerselect").append('<option value="'+y.memberId+'">'+y.mobile+'</option>');
		var buyerData=[];
		var buyerDataIndex={};
		var b=false;
		debugger;
	
		for(var i=0;i<addBuyerData.length;i++){
			//alert(addBuyerData[i]["addBuyerData"+index])
			if(addBuyerData[i]["addBuyerData"+index]){
				//alert(1);
				debugger;
				buyerData=addBuyerData[i]["addBuyerData"+index];
				removeBuyerRule2(y.memberId,index);

				buyerData.push(y);
				addBuyerData[i]["addBuyerData"+index]=buyerData;
				b=true;
 			}
			
		}
		if(!b){
			removeBuyerRule2(y.memberId,index);
			buyerData.push(y);
			buyerDataIndex["addBuyerData"+index]=buyerData;
			addBuyerData.push(buyerDataIndex);
		}
		}
		//alert()
		
		//addBuyerData.push(y);
	});
	if(flag){
		warningMessage("买家用户"+msg+"已被指定");
	}
}

/*
 * 删除商铺集合中对应用户
 */
function removeBuyerRule2(memberId,index) {
	debugger;
	 
	for(var i=0;i<addBuyerData.length;i++){
		//alert(addBuyerData[i]["addBuyerData"+index])
		if(addBuyerData[i]["addBuyerData"+index]){
			//alert(1);
			var buyerData=addBuyerData[i]["addBuyerData"+index];
			for (var j = 0; j < buyerData.length; j++) {
				if (buyerData[j].memberId == memberId) {
					buyerData.splice(j, 1);
					break;
				}
			}
			addBuyerData[i]["addBuyerData"+index]=buyerData;

			}
		
	}

}
//添加左边所有行
function addbuyerall(index){
	var rows = $('#showbuyer_table').datagrid('getData').rows;
	debugger;
	var flag=false;
	var msg="";
	$.each(rows,function(x,y){
		 var b=true;
		for(var i=0;i<indexArray.length;i++){
			if(indexArray[i]!=index){
			for(var j=0;j<addBuyerData.length;j++){
				if(addBuyerData[j]["addBuyerData"+indexArray[i]]){
					var buyerJsonData=addBuyerData[j]["addBuyerData"+indexArray[i]];
					for(var key in buyerJsonData){
						 if(buyerJsonData[key].memberId==y.memberId){
							 flag=true;
							 b=false;
							 msg+=y.mobile+",";
						 }
					}
				}
			}
			}
		}
		if(b){
		
		$("#buyerselect option[value='"+y.memberId+"']").remove(); //移除重复项
		$("#buyerselect").append('<option value="'+y.memberId+'">'+y.mobile+'</option>');
		
		
		var buyerData=[];
		var buyerDataIndex={};
		var b=false;
		debugger;
	
		for(var i=0;i<addBuyerData.length;i++){
			//alert(addBuyerData[i]["addBuyerData"+index])
			if(addBuyerData[i]["addBuyerData"+index]){
				//alert(1);
				debugger;
				buyerData=addBuyerData[i]["addBuyerData"+index];
				removeBuyerRule2(y.memberId,index);

				buyerData.push(y);
				addBuyerData[i]["addBuyerData"+index]=buyerData;
				b=true;
 			}
			
		}
		if(!b){
			removeBuyerRule2(y.memberId,index);
			buyerData.push(y);
			buyerDataIndex["addBuyerData"+index]=buyerData;
			addBuyerData.push(buyerDataIndex);
		}
		}
	});
	if(flag){
		warningMessage("买家用户"+msg+"已被指定");
	}
}

//移除一行
function removebuyerone(index){
	removeBuyerRule($("#buyerselect option:selected").val(),index);
	$("#buyerselect option:selected").remove();
}
//移除所有
function removebuyerall(index){
	$("#buyerselect option").remove();
	 
	for(var i=0;i<addBuyerData.length;i++){
		//alert(addBuyerData[i]["addBuyerData"+index])
		if(addBuyerData[i]["addBuyerData"+index]){
			//alert(1);
			var buyerData=[];
		 
			addBuyerData[i]["addBuyerData"+index]=buyerData;

			}
		
	}
}

function delBuyerRow(index,obj){
	for(var i=0;i<indexArray.length;i++){
		if(index==indexArray[i]){
			indexArray.splice(i,1);
		}
	}
	for(var i=0;i<addBuyerData.length;i++){
		//alert(addBuyerData[i]["addBuyerData"+index])
		if(addBuyerData[i]["addBuyerData"+index]){
			//alert(1);
			addBuyerData.splice(i,1);

			}
	}
	$(obj).parent().parent().parent().remove();
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
	var temp = $("#"+id).find("#template_id");
	
	var replaceStr = "_id";
	var value = $("#marketComm").find("#hid_id").attr("value");
	
	var endAmt;
	
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
		warningMessage("必须有结束区间才能添加新区间，且必须为正确金额");
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
	$("#marketComm").find("#hid_id").attr("value", parseInt(value)+1);
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
	var value = $("#marketComm").find("#hid_id").attr("value");
	
	/*
	 * 验证必须填入结束值才能添加新的区间
	 */
	if(value==0) {
		endAmt = $("#"+id).find("#template_id").find("#endAmt").val();
	} else {
		endAmt = $("#"+id).find("#template"+(value-1)).find("#endAmt").val();
	}
	
	if(!regMoney.test(endAmt)) {
		warningMessage("必须有结束区间才能添加新区间，且必须为正确金额");
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
	$("#marketComm").find("#hid_id").attr("value", parseInt(value)+1);

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
	
	$("#marketComm").find("#hid_id").attr("value", value);
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





//添加更多指定买家
var value2=1;
var addUserRule=function(id){
	value2++;
$("#"+id).append("<tr>"+
					"<td><input type='checkbox' onclick='return isUnChecked(this)' id='buyerBox"+(value2-1)+"' name='buyerBox"+(value2-1)+"' onchange='buyerBoxChange(this,"+(value2-1)+")' value='"+(value2-1)+"'/><span>指定买家"+value2+"：</span></td>"+
					"<td> 指定用户" +
					 "<input type='button' onclick='addBuyer("+(value2-1)+")' value='添加买家'   id='tjmj"+(value2-1)+"' />"+
					 "积分倍率：<input type='text' id='integralRate"+(value2-1)+"' name='integralRate"+(value2-1)+"' />"+
					"<span style='font-size: large;'><a id='showMJXX' onclick='showAddBuyer("+(value2-1)+")' href='javascript:void(0)' >活动买家信息</a></span>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='font-size: large;'><a id='delRow"+(value2-1)+"' onclick='delBuyerRow("+(value2-1)+",this)' href='javascript:void(0)' >删除</a></span></td>"+
				"</tr>"
					);
$("#tjmj"+(value2-1)).attr("disabled","disabled");
$("#integralRate"+(value2-1)).attr("disabled","disabled");

}
//查看 修改采购积分时  对后端回传的指定买家用户做处理   转存页面全局变量中
var addBuyerDataJson=function(jsonData,index){
	var buyerData=[];
	var buyerDataIndex={};
	var b=false;
	debugger;

	for(var i=0;i<addBuyerData.length;i++){
		//alert(addBuyerData[i]["addBuyerData"+index])
		if(addBuyerData[i]["addBuyerData"+index]){
			//alert(1);
			debugger;
			buyerData=addBuyerData[i]["addBuyerData"+index];
			removeBuyerRule2(jsonData.memberId,index);

			buyerData.push(jsonData);
			addBuyerData[i]["addBuyerData"+index]=buyerData;
			b=true;
			}
		
	}
	if(!b){
		removeBuyerRule2(jsonData.memberId,index);
		buyerData.push(jsonData);
		buyerDataIndex["addBuyerData"+index]=buyerData;
		addBuyerData.push(buyerDataIndex);
	}
}
//指定买家复选框处理函数
var buyerBoxChange=function(obj,index){
	
	if($(obj).attr("checked")=="checked"){
		if(index==0){
			$("#integralRate").removeAttr("disabled");
		}else{
			indexArray.push(index);
				$("#tjmj"+index).removeAttr("disabled");
				$("#integralRate"+index).removeAttr("disabled");
		}		
			 // $("#buyerBox"+index).removeAttr("disabled");
	}else{
          if(index==0){
        	  $("#integralRate").attr("disabled","disabled");
          }else{
        	    clearBuyer(index);
        	  	$("#tjmj"+index).attr("disabled","disabled");
        	  	$("#integralRate"+index).attr("disabled","disabled");
		
          }
			//  $("#buyerBox"+index).attr("disabled","disabled");
	}
}
//取消勾选指定买家复选框清楚该行指定买家数据
var clearBuyer=function(index){
	for(var i=0;i<indexArray.length;i++){
		if(index==indexArray[i]){
			indexArray.splice(i,1);
		}
	}
	for(var i=0;i<addBuyerData.length;i++){
		//alert(addBuyerData[i]["addBuyerData"+index])
		if(addBuyerData[i]["addBuyerData"+index]){
			//alert(1);
			addBuyerData.splice(i,1);

			}
	}
}

//校验积分倍率是否为空

var checkIntegralRate=function(){
	if($("#buyerBox0").attr("checked")!="checked"&&!indexArray.length>0){
		warningMessage("请至少指定一条买家积分倍率设置");
		$("#tt").tabs("select", 1);
		return false;
	}
	
	if($("#buyerBox0").attr("checked")=="checked"){
		if($("#integralRate").val()==""){
			warningMessage("指定买家1的积分倍率不能空，上限值[1.00,1000.00]");
			$("#tt").tabs("select", 1);
			return false;
		}
	}
	for(var key in indexArray){
		if($("#integralRate"+indexArray[key]).val()==""){
			warningMessage("指定买家的积分倍率不能空，上限值[1.00,1000.00]");
			$("#tt").tabs("select", 1);
			return false;
		}
	}
	return true;
}
//校验是否符合规则
var checkIntegralRateGS=function(){
	debugger;
	if($("#buyerBox0").attr("checked")=="checked"){
		if(!regCert.test($("#integralRate").val())){
			warningMessage("请输入正确的买家积分倍率，上限[1.00,1000.00]");
			$("#tt").tabs("select", 1);
			return false;
		}
	}
	for(var key in indexArray){
		if(!regCert.test($("#integralRate"+indexArray[key]).val())){
			warningMessage("请输入正确的买家积分倍率，上限[1.00,1000.00]");
			$("#tt").tabs("select", 1);
			return false;
		}
	}
	return true;
}
//校验指定买家是否为空
var checkBuyerIsNull=function(){
	for(var i=0;i<indexArray.length;i++){
		var b=false;
		for(var j=0;j<addBuyerData.length;j++){
			if(addBuyerData[j]["addBuyerData"+indexArray[i]]&&addBuyerData[j]["addBuyerData"+indexArray[i]].length>0){
			 b=true;
			}
		}
		if(!b){
			warningMessage("指定买家"+(indexArray[i]+1)+"的添加买家列表不能为空");
			$("#tt").tabs("select", 1);
			return false;
		} 
	}
	return true;
}
//校验积分规则区间是否有填写
var checkIntegralRateComm=function(){
	if($("input [name='buyerStartAmt_id']").val()==''||$("input [name='integral_id']").val()){
		warningMessage("请至少填写一条完整的区间积分规则");
		$("#tt").tabs("select", 2);
		return false;
	}
	return true;
}
var checkIntegral=function(){
	var integral=$("#buyer_tb_comm").find("input[name^='integral']");
    for(var i=0;i<integral.length;i++){
	  if($(integral[i]).val()==""){
			warningMessage("区间获取积分值不能为空");
			$("#tt").tabs("select", 2);
			return false;
	  }else{
		  if(!regMoney.test($(integral[i]).val())){
			  warningMessage("请输入正确的区间获取积分值，上限[1，99999]");
				$("#tt").tabs("select", 2);
				return false;
		  }
	  }
  }
    return true;
} 
//校验当日最高累计积分
var checkDayMaxIntegral=function(){
//	debugger;
//	var startAmt=$("#buyer_tb_comm").find("input[name^='buyerStartAmt']");
//	var endAmt=$("#buyer_tb_comm").find("input[name^='buyerEndAmt']");
//	var integral=$("#buyer_tb_comm").find("input[name^='integral']");
//	var dayMaxIntegral=$("#dayMaxIntegral").val();
//	if($("#dayIntegralBox").attr("checked")=="checked"){
//		for(var i=0;i< integral.length;i++){
//			if(parseInt(dayMaxIntegral)<parseInt($(integral[i]).val())){
//				warningMessage("当日最高累计积分不能少于区间获取积分值");
//				$("#tt").tabs("select", 2);
//				return false;
//			}
//		}
//	}
	return true;
}
function isUnChecked(obj){
	if($(obj).attr("checked")!="checked"){
		var b=confirm('取消勾选，则清空当前指定用户列表信息？');
		return b;
	}
	
	return true;
}