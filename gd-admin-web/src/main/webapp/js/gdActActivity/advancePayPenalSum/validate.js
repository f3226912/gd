

/**
 * 下一步操作以及校验
 */
function nextStepCheck(formid,index, checkFunc){
	if(!$("#"+formid+"").form('validate')){
		return;
	}
	if(checkFunc!=null && checkFunc()) {
		$(".dialog-content")[1].scrollTop=0;
		$("#tt").tabs("select", index);
	}
}

/**
 * 下一步，无校验
 * @param formid
 * @param index
 */
function nextStep(formid,index){
	if(!$("#"+formid+"").form('validate')){
		return;
	}
	$(".dialog-content")[1].scrollTop=0;
	$("#tt").tabs("select", index);
}

function checkActTime() {
	if(tab($("#startTime").val(),$("#endTime").val())) {
		selTabs(0);
		warningMessage("活动开始时间应小于结束时间");
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
/**
 * 用户自定义校验规则
 */
function defineValid(){
	  $.extend($.fn.validatebox.defaults.rules, {
		  validateActName: {
	            validator: function(value, param) {
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
			range:{
				validator:function(value,param){
					var regMoney = /^(([1-9]\d{0,6})|0)(\.\d{1,2})?$/;
					if(regMoney.test(value)){
						return value >= param[0] && value <= param[1]
					}else{
						return false;
					}
				},
				message:'输入的数字在{0}到{1}之间'
			},
			penalSumTotal:{
				validator: function(value,param){
					var penalSum1 = $(param[0]).val();
					var penalSum2 = $(param[1]).val();
					var penalSumTotal = 0;
					if(penalSum1 != "")
					{
						penalSumTotal = penalSumTotal + parseFloat(penalSum1);
					}
					if(penalSum2 != "")
					{
						penalSumTotal = penalSumTotal + parseFloat(penalSum2);
					}
					var penalSum3 = parseFloat(value);
					penalSumTotal = (penalSumTotal + penalSum3).toFixed(2);
					if(penalSumTotal <= 100)
					{
						return true
					}
					return false;
				},
				message: '给卖家违约金、给平台违约金、给物流公司违约金之和不能超过100%'
		    }
    });
}

function tab(date1,date2){
	var oDate1 = new Date(date1);
	var oDate2 = new Date(date2);
	if(oDate1.getTime() > oDate2.getTime()){
	    return true;
	} else {
	    return false;
    }
}
