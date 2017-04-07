$(document).ready(function() {
	//编辑状态下，市场不可编辑
	$("#market_type").attr("disabled","disabled");
	$("#b_type").attr("disabled","disabled");
})


/**
 * 初始化商品用户配置信息
 */
function initUserRule(solderRule,buyerRules) {
	//加载一级类目数据
	loadCategory(1);
	
	if(solderRule){
		//商铺
		if(solderRule.type==2) {
			addShopData = solderRule.shops;
			$("#userRuleBuyerShop").attr("checked","checked").attr("tag","1");
			$("#addshop_span input").removeAttr("disabled");
		} 
		//类目
		else if(solderRule.type==3)
		{
			//选中类目
			$("#userRuleBuyerCate").attr("checked","checked").attr("tag","1");
			$('#categoryId_add').val(solderRule.categoryId);
			oldCateId = solderRule.categoryId;
			if(view != 1)
			{
				//类目下拉框设为可用
				$("#categoryPanel select").removeAttr("disabled");
			}
			
			//一级类目
			if(solderRule.level==0) 
			{
				//根据
				topCateId = solderRule.categoryId;
				$("#cate1").find("option[value="+topCateId+"]").attr("selected",true); 
			} 
			//二级类目
			else if(solderRule.level==1) {
				secCateId = solderRule.categoryId;
				topCateId = solderRule.parentId;
				$("#cate1").find("option[value="+topCateId+"]").attr("selected",true); 
				loadCategory(2);
				$("#cate2").find("option[value="+secCateId+"]").attr("selected",true); 
			}
			//三级类目
			else {
				topCateId = solderRule.topParentId;
				secCateId = solderRule.parentId;
				ThirCateId = solderRule.categoryId;
				
				$("#cate1").find("option[value="+topCateId+"]").attr("selected",true); 
				loadCategory(2);
				$("#cate2").find("option[value="+secCateId+"]").attr("selected",true); 
				loadCategory(3);
				$("#cate3").find("option[value="+ThirCateId+"]").attr("selected",true); 
			}
		} 
		//商品
		else if(solderRule.type==4) {
			$("#userRuleBuyerProd").attr("checked","checked").attr("tag","1");
			$("#addprod_span input").removeAttr("disabled");
			addProdData = solderRule.prods;
		}
	}
	//买家信息
	addBuyerData = buyerRules;
}

/**
 * 初始化买家预付款的配置信息
 */
function initAdPay(advancePaymentRule) {
	if(advancePaymentRule){
		var ruleJson = advancePaymentRule.detail;
		if(advancePaymentRule.type==0) {
			//固定金额单选框选中
			$("input[name=advancePayment][value=0]").attr("checked",'checked');
			//设置固定金额的值
			var advancePaymentAmt = ruleJson.prodLimitAmt;
			$("#advancePaymentAmt").attr("disabled",false);
			$("#advancePaymentAmt").val(advancePaymentAmt);
			
			$("#advancePaymentPercent").attr("disabled",true);
			$("#advancePaymentLimit").attr("disabled",true);
		} else if(advancePaymentRule.type==2) {
			//百分比
			$("input[name=advancePayment][value=2]").attr("checked",'checked');
			//设置百分比的值
			var advancePaymentPercent = ruleJson.orderProp;
			var advancePaymentLimit = ruleJson.orderLimitAmt;
			$("#advancePaymentPercent").attr("disabled",false);
			$("#advancePaymentPercent").val(advancePaymentPercent);
			$("#advancePaymentLimit").attr("disabled",false);
			$("#advancePaymentLimit").val(advancePaymentLimit);
			
			$("#advancePaymentAmt").attr("disabled",true);
		} 
	}
}

/**
 * 给平台的违约金配置信息初始化
 */
function initPlateformPenalSum(penalSumPlatFormRule) {
	if(penalSumPlatFormRule){
		var penalSumPlatFormPercent = penalSumPlatFormRule.detail.orderProp;
		$("#penalSumPlatFormPercent").attr("disabled",false);
		$("#penalSumPlatFormPercent").val(penalSumPlatFormPercent);
	}
}

/**
 * 给卖家的违约金配置信息初始化
 */
function initSellerPenalSum(penalSumSellerRule) {
	
	if(penalSumSellerRule){
		var penalSumSellerPercent = penalSumSellerRule.detail.orderProp;
		$("#penalSumSellerPercent").attr("disabled",false);
		$("#penalSumSellerPercent").val(penalSumSellerPercent);
	}
}

/**
 * 给物流公司的违约金配置信息初始化
 */
function initLogisticsPenalSum(penalSumLogisticsRule) {
	if(penalSumLogisticsRule){
		var penalSumLogisticsPercent = penalSumLogisticsRule.detail.orderProp;
		$("#penalSumLogisticsPercent").attr("disabled",false);
		$("#penalSumLogisticsPercent").val(penalSumLogisticsPercent);
	}
}
