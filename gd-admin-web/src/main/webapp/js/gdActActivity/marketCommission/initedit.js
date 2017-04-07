$(document).ready(function() {
	initMaketComm();
	initSub();
	initUserRule();
})

function initMaketComm() {
	if(buyerArray && buyerArray.length>0) {
		var buyerdata = buyerArray[0];
		var buyerLayer = $("#buyerLayer");
		if(buyerdata.type==0) {
			debugger;
			buyerLayer.find("#prodRadio").attr("checked","checked").attr("tag","1");
			buyerLayer.find("#buyerOrderProd").val(buyerdata.detail.prodLimitAmt);
			buyerLayer.find("#buyerAmt").val(buyerdata.minAmt);
			//编辑
			buyerLayer.find("#buyerOrderProd").removeAttr("disabled");
			/*	buyerLayer.find("#hid_buyer_id").removeAttr("disabled");
			buyerLayer.find("#buyerStartAmt_id").removeAttr("disabled");
			buyerLayer.find("#buyerEndAmt_id").removeAttr("disabled");
			buyerLayer.find("#buyerPropAmt_id").removeAttr("disabled");
			buyerLayer.find("#buyerLimitAmt_id").removeAttr("disabled");
			buyerLayer.find("#buyerFixedAmt_id").removeAttr("disabled");*/
			

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
			buyerLayer.find("#buyerAmt").val(buyerdata.minAmt);
			buyerLayer.find("#orderAmtCertRadio").removeAttr("disabled");
			buyerLayer.find("#orderRadio").removeAttr("disabled");
			var template = buyerLayer.find("#template_id");
			template.find("#startAmt").removeAttr("disabled");
			template.find("#endAmt").removeAttr("disabled");
			template.find("#startAmt").val(details[0].startAmt);
			template.find("#endAmt").val(details[0].endAmt);
			template.find("#orderCertRadio").removeAttr("disabled");
			template.find("#orderFixedRadio").removeAttr("disabled");

			
			if(details[0].type==0) {
				template.find("#orderCertRadio").attr("checked","checked").attr("tag","1");
				template.find("#propAmt").val(details[0].orderProp);
				template.find("#limitAmt").val(details[0].orderLimitAmt);
				//编辑状态下
				if(typeof(view) == "undefined" || view == "")
				{
					template.find("#propAmt").removeAttr("disabled");
					template.find("#limitAmt").removeAttr("disabled");
					template.find("#fixedAmt").attr("disabled","disabled");
				}
				
			} else {
				template.find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
				template.find("#fixedAmt").val(details[0].fixedAmt);
				if(typeof(view) == "undefined" || view == "")
				{
					template.find("#fixedAmt").removeAttr("disabled");
					template.find("#propAmt").attr("disabled","disabled");
					template.find("#limitAmt").attr("disabled","disabled");
				}
			}
			
			if(details && details.length>1) {
				for(var i = 0; i < (details.length-1); i++) {
					addMoreRule("buyer_tb_comm");
					
					var template = buyerLayer.find("#template" + i);
					
					template.find("#startAmt").val(details[i+1].startAmt);
					template.find("#endAmt").val(details[i+1].endAmt);
					
					if(details[i+1].type==0) {
						template.find("#orderCertRadio").attr("checked","checked").attr("tag","1");
						template.find("#propAmt").val(details[i+1].orderProp);
						template.find("#limitAmt").val(details[i+1].orderLimitAmt);
						if(typeof(view) == "undefined" || view == "")
						{
							template.find("#propAmt").removeAttr("disabled");
							template.find("#limitAmt").removeAttr("disabled");
							template.find("#fixedAmt").attr("disabled","disabled");
						}
					} else {
						template.find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
						template.find("#fixedAmt").val(details[i+1].fixedAmt);
						if(typeof(view) == "undefined" || view == "")
						{
							template.find("#fixedAmt").removeAttr("disabled");
							template.find("#propAmt").attr("disabled","disabled");
							template.find("#limitAmt").attr("disabled","disabled");
						}
					}
				}
			}
		}
	}
	
	if(solderArray && solderArray.length>0) {
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
			var flag1=false;
			var flag2=false;
			
			solderLayer.find("#orderAmtCertRadio").attr("tag","1");
			solderLayer.find("#sellerAmt").val(solderdata.minAmt);
			
			if(details && details.length>0) {
				for(var i = 0; i < details.length; i++) {
					//正向
					if(details[i].isReverse==0){
						if(flag1){
							//非第一次出现则需要新增一行
							addMoreRule("solder_tb_comm");
							$('#solder_tb_comm tr:last').find("#startAmt").val(details[i].startAmt);
							$('#solder_tb_comm tr:last').find("#endAmt").val(details[i].endAmt);
							if(details[i].type==0) {
								$('#solder_tb_comm tr:last').find("#orderCertRadio").attr("checked","checked").attr("tag","1");
								$('#solder_tb_comm tr:last').find("#propAmt").val(details[i].orderProp);
								$('#solder_tb_comm tr:last').find("#limitAmt").val(details[i].orderLimitAmt);
								$('#solder_tb_comm tr:last').find("#propAmt").removeAttr("disabled");
								$('#solder_tb_comm tr:last').find("#limitAmt").removeAttr("disabled");
								$('#solder_tb_comm tr:last').find("#fixedAmt").attr("disabled","disabled");
							} else {
								$('#solder_tb_comm tr:last').find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
								$('#solder_tb_comm tr:last').find("#fixedAmt").val(details[i].fixedAmt);
								$('#solder_tb_comm tr:last').find("#propAmt").attr("disabled","disabled");
								$('#solder_tb_comm tr:last').find("#limitAmt").attr("disabled","disabled");
								$('#solder_tb_comm tr:last').find("#fixedAmt").removeAttr("disabled");
							}
							
						}else{//第一次的时候直接给第一行赋值
								$('#solder_tb_comm tr:last').find("#startAmt").val(details[i].startAmt);
								$('#solder_tb_comm tr:last').find("#endAmt").val(details[i].endAmt);
								if(details[i].type==0) {
									$('#solder_tb_comm tr:last').find("#orderCertRadio").attr("checked","checked").attr("tag","1");
									$('#solder_tb_comm tr:last').find("#propAmt").val(details[i].orderProp);
									$('#solder_tb_comm tr:last').find("#limitAmt").val(details[i].orderLimitAmt);
							
									$('#solder_tb_comm tr:last').find("#propAmt").removeAttr("disabled");
									$('#solder_tb_comm tr:last').find("#limitAmt").removeAttr("disabled");
									$('#solder_tb_comm tr:last').find("#fixedAmt").attr("disabled","disabled");
									
								} else {
									$('#solder_tb_comm tr:last').find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
									$('#solder_tb_comm tr:last').find("#fixedAmt").val(details[i].fixedAmt);
									
									
									$('#solder_tb_comm tr:last').find("#propAmt").attr("disabled","disabled");
									$('#solder_tb_comm tr:last').find("#limitAmt").attr("disabled","disabled");
									$('#solder_tb_comm tr:last').find("#fixedAmt").removeAttr("disabled");
								}
								flag1=true;	
						}
					}//逆向
					else if(details[i].isReverse==1){
						if(flag2){
							//非第一次出现则需要新增一行
							addMoreRule("reverse_tb_comm");
							$('#reverse_tb_comm tr:last').find("#startAmt").val(details[i].startAmt);
							$('#reverse_tb_comm tr:last').find("#endAmt").val(details[i].endAmt);
							if(details[i].type==0) {
								$('#reverse_tb_comm tr:last').find("#orderCertRadio").attr("checked","checked").attr("tag","1");
								$('#reverse_tb_comm tr:last').find("#propAmt").val(details[i].orderProp);
								$('#reverse_tb_comm tr:last').find("#limitAmt").val(details[i].orderLimitAmt);
							
								$('#reverse_tb_comm tr:last').find("#propAmt").removeAttr("disabled");
								$('#reverse_tb_comm tr:last').find("#limitAmt").removeAttr("disabled");
								$('#reverse_tb_comm tr:last').find("#fixedAmt").attr("disabled","disabled");
							} else {
								$('#reverse_tb_comm tr:last').find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
								$('#reverse_tb_comm tr:last').find("#fixedAmt").val(details[i].fixedAmt);
							
								$('#reverse_tb_comm tr:last').find("#propAmt").attr("disabled","disabled");
								$('#reverse_tb_comm tr:last').find("#limitAmt").attr("disabled","disabled");
								$('#reverse_tb_comm tr:last').find("#fixedAmt").removeAttr("disabled");
							}
							
						}else{//第一次的时候直接给第一行赋值
								$('#reverse_tb_comm tr:last').find("#startAmt").val(details[i].startAmt);
								$('#reverse_tb_comm tr:last').find("#endAmt").val(details[i].endAmt);
								if(details[i].type==0) {
									$('#reverse_tb_comm tr:last').find("#orderCertRadio").attr("checked","checked").attr("tag","1");
									$('#reverse_tb_comm tr:last').find("#propAmt").val(details[i].orderProp);
									$('#reverse_tb_comm tr:last').find("#limitAmt").val(details[i].orderLimitAmt);
								
									$('#reverse_tb_comm tr:last').find("#propAmt").removeAttr("disabled");
									$('#reverse_tb_comm tr:last').find("#limitAmt").removeAttr("disabled");
									$('#reverse_tb_comm tr:last').find("#fixedAmt").attr("disabled","disabled");
								} else {
									$('#reverse_tb_comm tr:last').find("#orderFixedRadio").attr("checked","checked").attr("tag","1");
									$('#reverse_tb_comm tr:last').find("#fixedAmt").val(details[i].fixedAmt);
									
									$('#reverse_tb_comm tr:last').find("#propAmt").attr("disabled","disabled");
									$('#reverse_tb_comm tr:last').find("#limitAmt").attr("disabled","disabled");
									$('#reverse_tb_comm tr:last').find("#fixedAmt").removeAttr("disabled");
								}
								flag2=true;	
						}
					}
				}
			}
		}
	}
	
}

function initSub() {
	if(ruleArray && ruleArray.length>0) {
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
				if(rules && rules.length>1) {
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

	addBuyerData = buyerRules;}
