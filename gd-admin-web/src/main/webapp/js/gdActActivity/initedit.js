$(document).ready(function() {
	debugger;
	initSub();
	initRuleList();
	initUserRule();
})

function initSub() {
	if(ruleArray.length>0) {
		for(var i = 0; i < ruleArray.length; i++) {
			var ruleData = ruleArray[i];
			var rule = jQuery.parseJSON("{}");
			rule.busiType1 = ruleData.busiType1;
			rule.busiType2 = ruleData.busiType2;
			rule.cardType = ruleData.cardType;
			rule.payChannel = ruleData.payChannel;
			rule.sub_radio = ruleData.rule.type;
      
			if(ruleData.rule.solderLimitType==1) {
				rule.solderSubCheck = "on";
				rule.solderSubAmt = ruleData.rule.solderLimitAmt;
			}
			debugger;
			var detail = ruleData.rule.detail;
			if(rule.sub_radio==1) {
				rule.orderAmt = detail.orderLimitAmt;
			} else if(rule.sub_radio==2) {
				rule.orderSub = detail.orderProp;
				rule.orderLimit = detail.orderLimitAmt;
			} else if(rule.sub_radio==4) {
				rule.proportion = detail.proportion;
				rule.orderLimit = detail.orderLimitAmt;
			} else if(rule.sub_radio==3) {
				var rules = detail;
				
				rule.startAmt_id = rules[0].startAmt;
				rule.endAmt_id = rules[0].endAmt;
				rule.sub_lim_radio_id = rules[0].type;
				debugger;
				if(rules[0].type==0) {
					rule.subLimit_id = rules[0].orderLimitAmt;
					rule.subPerc_id = rules[0].orderProp;
					
				} else {
					rule.subAmt_id = rules[0].fixedAmt;
				}
				rule.hid_id = rules.length -1;
				if(rules.length>1) {
					for(var j = 0; j < (rules.length-1); j++) {
						
						eval("rule.startAmt"+j+" = rules["+(j+1)+"].startAmt;");
						eval("rule.endAmt"+j+" = rules["+(j+1)+"].endAmt;");
						
						eval("rule.endAmt"+j+" = rules["+(j+1)+"].endAmt;");
						eval("rule.sub_lim_radio"+j+" = rules["+(j+1)+"].type;");
						
						if(rules[j+1].type==0) {
							eval("rule.subLimit"+j+" = rules["+(j+1)+"].orderLimitAmt;");
							eval("rule.subPerc"+j+" = rules["+(j+1)+"].orderProp;");
						} else {
							eval("rule.subAmt"+j+" = rules["+(j+1)+"].fixedAmt;");
						}
						
					}
				}
			}
			
			subRuleJsonObject.push(rule);
		}
		
	}
	
}

function initUserRule() {
	debugger;
	if(solderRule.type==2) {
		addShopData = solderRule.shops;
		$("#userRuleBuyerShop").attr("checked","checked").attr("tag","1");
		$("#addshop_span input").removeAttr("disabled");
	} else if(solderRule.type==3) {
		$("#userRuleBuyerCate").attr("checked","checked").attr("tag","1");
		$("#categoryPanel select").removeAttr("disabled");
		if(solderRule.level==0) {
			topCateId = solderRule.categoryId;
		} else if(solderRule.level==1) {
			secCateId = solderRule.categoryId;
			topCateId = solderRule.parentId;
		} else {
			topCateId = solderRule.topParentId;
			secCateId = solderRule.parentId;
			ThirCateId = solderRule.categoryId;
		}
		oldCateId = solderRule.categoryId;
	} else if(solderRule.type==4) {
		$("#userRuleBuyerProd").attr("checked","checked").attr("tag","1");
		$("#addprod_span input").removeAttr("disabled");
		addProdData = solderRule.prods;
	}

	initCategory();
	addBuyerData = buyerRules;
}
