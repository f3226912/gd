$(document).ready(function() {
	//initMaketComm();
	//initSub();
	//initRuleList();
	initUserRule();
})

function initMaketComm() {
	debugger;
	if(buyerArray.length>0) {
		var buyerdata = buyerArray[0];
		var buyerLayer = $("#buyerLayer");
		if(buyerdata.type==0) {
			buyerLayer.find("#prodRadio").attr("checked","checked").attr("tag","1");
			buyerLayer.find("#buyerOrderProd").val(buyerdata.detail.prodLimitAmt);
		} else if(buyerdata.type==1) {
			buyerLayer.find("#orderRadio").attr("checked","checked").attr("tag","1");
			buyerLayer.find("#orderAmt").val(buyerdata.detail.orderLimitAmt);
		} else if(buyerdata.type==2) {
			buyerLayer.find("#orderCertRadio").attr("checked","checked").attr("tag","1");
			buyerLayer.find("#orderBuyerSub").val(buyerdata.detail.orderProp);
			buyerLayer.find("#orderBuyerLimit").val(buyerdata.detail.orderLimitAmt);
		} else if(buyerdata.type==3) {
			var details = buyerdata.detail;
			buyerLayer.find("#orderAmtCertRadio").attr("checked","checked").attr("tag","1");
			
			var template = buyerLayer.find("#template_id");
			
			template.find("#startAmt").val(details[0].startAmt);
			template.find("#endAmt").val(details[0].endAmt);
			
			if(details[0].type==0) {
				template.find("#orderCertRadio").attr("checked","checked").attr("tag","1");
				template.find("#propAmt").val(details[0].orderProp);
				template.find("#limitAmt").val(details[0].orderLimitAmt);
			} else {
				template.find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
				template.find("#fixedAmt").val(details[0].fixedAmt);
			}
			
			if(details.length>1) {
				for(var i = 0; i < (details.length-1); i++) {
					addMoreRule("buyer_tb_comm");
					
					var template = buyerLayer.find("#template" + i);
					
					template.find("#startAmt").val(details[i+1].startAmt);
					template.find("#endAmt").val(details[i+1].endAmt);
					
					if(details[i+1].type==0) {
						template.find("#orderCertRadio").attr("checked","checked").attr("tag","1");
						template.find("#propAmt").val(details[i+1].orderProp);
						template.find("#limitAmt").val(details[i+1].orderLimitAmt);
					} else {
						template.find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
						template.find("#fixedAmt").val(details[i+1].fixedAmt);
					}
				}
			}
			
			
			
		}
	}
	
	if(solderArray.length>0) {
		var solderdata = solderArray[0];
		var solderLayer = $("#solderLayer");
		
		if(solderdata.type==1) {
			solderLayer.find("#orderRadio").attr("checked","checked").attr("tag","1");
			solderLayer.find("#orderAmt").val(solderdata.detail.orderLimitAmt);
		} else if(solderdata.type==2) {
			solderLayer.find("#orderCertRadio").attr("checked","checked").attr("tag","1");
			solderLayer.find("#orderSolderSub").val(solderdata.detail.orderProp);
			solderLayer.find("#orderSolderLimit").val(solderdata.detail.orderLimitAmt);
		} else if(solderdata.type==3) {
			var details = solderdata.detail;
			solderLayer.find("#orderAmtCertRadio").attr("checked","checked").attr("tag","1");
			
			var template = solderLayer.find("#template_id");
			
			template.find("#startAmt").val(details[0].startAmt);
			template.find("#endAmt").val(details[0].endAmt);
			
			if(details[0].type==0) {
				template.find("#orderCertRadio").attr("checked","checked").attr("tag","1");
				template.find("#propAmt").val(details[0].orderProp);
				template.find("#limitAmt").val(details[0].orderLimitAmt);
			} else {
				template.find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
				template.find("#fixedAmt").val(details[0].fixedAmt);
			}
			
			if(details.length>1) {
				for(var i = 0; i < (details.length-1); i++) {
					addMoreRule("solder_tb_comm");
					debugger;
					var template = solderLayer.find("#template" + i);
					
					template.find("#startAmt").val(details[i+1].startAmt);
					template.find("#endAmt").val(details[i+1].endAmt);
					
					if(details[i+1].type==0) {
						template.find("#orderCerRadiot").attr("checked","checked").attr("tag","1");
						template.find("#propAmt").val(details[i+1].orderProp);
						template.find("#limitAmt").val(details[i+1].orderLimitAmt);
					} else {
						template.find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
						template.find("#fixedAmt").val(details[i+1].fixedAmt);
					}
				}
			}
			
		}
	}
	
}

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
			
			var detail = ruleData.rule.detail;
			if(rule.sub_radio==1) {
				rule.orderAmt = detail.orderLimitAmt;
			} else if(rule.sub_radio==2) {
				rule.orderSub = detail.orderProp;
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
	if(solderRule.type==2) {//商铺
		addShopData = solderRule.shops;
		$("#userRuleBuyerShop").attr("checked","checked").attr("tag","1");
		$("#addshop_span input").removeAttr("disabled");
	} else if(solderRule.type==3) {//分类
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
	} else if(solderRule.type==4) {//商品
		$("#userRuleBuyerProd").attr("checked","checked").attr("tag","1");
		$("#addprod_span input").removeAttr("disabled");
		addProdData = solderRule.prods;
	}

	initCategory();
	addBuyerData = buyerRules;
}
