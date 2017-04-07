//切换tab的时候，加载数据，用来标记是否加载过，加载过则下次切换时不重复请求后台
var isLoadUserRule = false;
var isLoadAdPayPenalSum = false;

$(function(){
	//活动类型初始化
	var choice=$("select[id='b_type'] option")["${activity.activityType}"];
	if(choice){
		choice.selected=true;
	}
	
	//为用户预付款设置 0.01-100之间的数
	$('#amt').numberbox({  
	    min:0.01,  
	    max:100,
	    precision:2  
	});
	
	//先禁用所有商铺、商品、类目按钮
	$("#addprod_span input").attr("disabled", true);
	$("#categoryPanel select").attr("disabled", true);
	$("#addshop_span input").attr("disabled", true);
	
	$("#userRule").find("input[type=radio]").click(function() {
		$(this).removeAttr('checked');
		var bol = $(this).attr("tag");
		var idValue = $(this).attr("id");
		//选中
		if (bol == "0") 
		{
			$(this).attr('checked', 'checked');
			$("#userRule").find("input[type=radio]").attr("tag", "0")
			$(this).attr("tag", "1");
			
			//选中时取消禁用
			if(idValue == "userRuleBuyerShop")
			{
				$("#addshop_span input").removeAttr("disabled");
			}
			else if(idValue == "userRuleBuyerCate")
			{
				$("#categoryPanel select").removeAttr("disabled");
			}else if(idValue == "userRuleBuyerProd")
			{
				$("#addprod_span input").removeAttr("disabled");
			}
		} 
		//取消选中
		else 
		{
			$(this).removeAttr('checked');
			$(this).attr("tag", "0");
			
			//取消选中时，禁用
			if(idValue == "userRuleBuyerShop")
			{
				$("#addshop_span input").attr("disabled", true);
			}
			else if(idValue == "userRuleBuyerCate")
			{
				$("#categoryPanel select").attr("disabled", true);
			}else if(idValue == "userRuleBuyerProd")
			{
				$("#addprod_span input").attr("disabled", true);
			}
		}
	});
	
	//规则校验器
	defineValid();
	
	//预付款 按订单/按百分比单选框修改事件
	$("input:radio[name='advancePayment']").change(advancePaymentTypeChange);
	advancePaymentTypeChange();
	
	//tab页切换事件，切换时再去加载该tab页的数据
	$('#tt').tabs({
	    border:false,
	    onSelect:function(title,index){
	    	//加载tab页数据
	    	loadTabData(index);
	    }
	});
});

/**
 * 预付款方式修改：按固定金额/按订单小计金额百分比
 */
function advancePaymentTypeChange()
{
	var value = $('input[name="advancePayment"]:checked').val();
	//按固定金额
	if(value == 0)
	{
		//固定金额输入框可用
		$("#advancePaymentAmt").attr("disabled", false);
		
		//清空百分比的输入框的数据
		$("#advancePaymentPercent").val("");
		$("#advancePaymentLimit").val("");
		//预付款百分比禁用
		$("#advancePaymentPercent").attr("disabled", true);
		$("#advancePaymentLimit").attr("disabled", true);

		//如果校验已经生效后，再禁用该控件，则需要手动去掉红色告警框
		$("#advancePaymentPercent").removeClass("validatebox-invalid");
	}
	//按订单小计金额百分比
	else if(value == 2)
	{
		//预付款百分比相关输入框可用
		$("#advancePaymentPercent").attr("disabled", false);
		$("#advancePaymentLimit").attr("disabled", false);
		
		//清空数据
		$("#advancePaymentAmt").val("");
		//输入框不可用
		$("#advancePaymentAmt").attr("disabled", true);
	
		//如果校验已经生效后，再禁用该控件，则需要手动去掉红色告警框
		$("#advancePaymentAmt").removeClass("validatebox-invalid");
	}
}

/*
 * 保存操作
 */
function save() {
	//由于加载tab页时才查询数据，用户编辑时，可能没有进入商品用户和预付款违约金tab页，而直接提交，导致某个tab页的数据因未加载就提交而丢失
	loadTabData(1);
	loadTabData(2);
	
	//在判断其他必填项是否都填了
	var forms=$("#tt form");
	var resu=true;
	$.each(forms,function(x,y){
		var formId = $(y).attr('id');
		
		//校验每一个表单
		if(!$("#"+formId+"").form('validate')){
			//校验不通过则切换到有问题的那个tab页
			$("#tt").tabs("select", x);
			resu= false;
			return false;
		}
	});

	if(!resu)
	{
		return false;
	}
	
	//开始时间结束时间校验
	 
	//校验商品用户
	if(!checkProdUser()) {
		return;
	}
	
	//校验如果没有添加商品 添加商铺 和 关联类目 则市场规则唯一性
	if(addProdData.length == 0 && addShopData.length == 0 && $("#cate1").val()=="-1") 
	{
		var marketId = $("#market_type").val();
		
		if(marketId != oldMarketId) 
		{
			//校验活动名是否存在
			checkExistAct(marketId, 5, 4, function() {
				
				sendSaveAjax();
			}, function() {
				$("#tt").tabs("select", 1);
			});
		} else 
		{
			
			sendSaveAjax();
		}
	} else 
	{
		if($("#categoryId_add").val()!="-1" && $("#categoryId_add").val()!=oldCateId) {
			
			checkExistAct($("#categoryId_add").val(), 3, 4, function() {
				sendSaveAjax();
			}, function() {
				selTabs(1);
			});
		} else {
			sendSaveAjax();
		}
	}
}

/**
 * 发送请求，保存活动基本信息、用户商品、预付款-违约金
 */
function sendSaveAjax() {
	var url = CONTEXT + "gdAdvancePayPenalSum/saveAdPayPenalSum";
	var para=
	{
		'actBasicInfo':JSON.stringify(changetoObject($('#basicRule').serializeArray())),
		'goodsUserRule':JSON.stringify({
				'pageData':changetoObject($('#userRule').serializeArray()),
				'addShopData':addShopData,
				'addProdData':addProdData,
				'addBuyerData':addBuyerData
			}),
		'advancePaymentRule':JSON.stringify(changetoObject($('#advancePayment').serializeArray())),
		'penalSumPlateformRule':JSON.stringify(changetoObject($('#penalSumForPlateform').serializeArray())),
		'penalSumSellerRule':JSON.stringify(changetoObject($('#penalSumForSeller').serializeArray())),
		'penalSumLogisticsRule':JSON.stringify(changetoObject($('#penalSumForLogistics').serializeArray()))
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


function ajaxLoading(){
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");   
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});   
}

function ajaxLoadEnd(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}


function changetoObject(data) {
	var json = {};
	for(var j = 0; j < data.length; j++) {
		eval("json."+data[j].name+"='"+data[j].value+"';");
	}
	return json;
}

function loadTabData(index)
{
	//跳转第二个页面：商品用户
	if(index == 1 && id >0 && !isLoadUserRule)
	{
		$.ajax({
			url: CONTEXT + 'gdAdvancePayPenalSum/queryUserRule?id='+id,
			type: 'GET',
			dataType: 'json',
			async: false,
			success: function(data) {
				isLoadUserRule = true;
				var buyerRules = data.buyerRules;
				var solderRule = data.solderRule;
				initUserRule(solderRule,buyerRules);
			}
		});
	}
	//跳转第三个页面：预付款或者违约金时
	else if(index ==2 || index == 3)
	{
		if(id >0 && !isLoadAdPayPenalSum)
		{
			$.ajax({
				url: CONTEXT + 'gdAdvancePayPenalSum/getAdPayPenalSum?id='+id,
				type: 'GET',
				dataType: 'json',
				async: false,
				success: function(data) 
				{
					isLoadAdPayPenalSum = true;
					var advancePaymentRule = jQuery.parseJSON(data.advancePaymentRule);
					var penalSumPlatFormRule = jQuery.parseJSON(data.penalSumPlatFormRule);
					var penalSumSellerRule = jQuery.parseJSON(data.penalSumSellerRule);
					var penalSumLogisticsRule = jQuery.parseJSON(data.penalSumLogisticsRule);
					
					initAdPay(advancePaymentRule);
					initPlateformPenalSum(penalSumPlatFormRule);
					initSellerPenalSum(penalSumSellerRule);
					initLogisticsPenalSum(penalSumLogisticsRule);
				}
			});
		}
	}
}