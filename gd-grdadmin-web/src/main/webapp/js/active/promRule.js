
$.extend($.fn.validatebox.defaults.rules, {
	floatPoint: {
        validator: function(value, param){

        	return /^\d+$/.test(value)|| /^[0-9]\d*\.\d{1,2}$/.test(value);

        },    
        message: '请输入正确的数字，最多两位小数'
    }
	,range:{
        validator: function(value, param){
        //	value = Math.floor(value);
        	if(value<param[0] || value > param[1]){
        		return false;
        	}
        	return true;

        },    
        message: '请输入{0}到{1}之间的数字'
    }
}); 

/**
 * 保存活动规则
 */
function addPromRuleFee(){
	var actId = $.trim($("#actId").val());
	if(actId == '' && flag == 1){
		alert("请先保存活动基本信息，再保存活动规则");
		return;
	}
	if($('#addRuleFeeForm').form("validate")){
		var data = $("#addRuleFeeForm").serialize();
		data = data+"&actId="+$.trim($("#actId").val());
		$.ajax({
			type : "post",
			url : CONTEXT+"active/addPromRuleFee",
			data : data,
			dataType : "json",
			success : function(data){
		    	if(data.msg){
		    		$.messager.alert("错误信息",data.msg,"error");
		    	} else {
		    	
		    		$.messager.show({title:"提示信息",msg:"保存成功",timeout:3000,showType:'slide'});
		    	}
			}
		});

	}
	
}
$(function(){
	//初始化数据
	initEven();
	initData();
	
	if(flag == 2){
		initUpdatePromRule($.trim($("#actId").val()));
	}

});

function initData(){
	//禁用买家，卖家所有的输入框
	$("#buyFee,#sellFee").find("input:text:visible").prop("disabled",true);
	//选中第一个
	$("#sellRaidioFirst,#byeRaidioFirst").click();
	
}

function initEven(){
	//绑定买家事件
	$("#buyFee").find("input:radio").click(function(){
		//禁用买家所有输入框
		$("#buyFee").find("input:text:visible").prop("disabled",true);
		//移除校验不通过样式
		$("#buyFee").find("input").removeClass("validatebox-invalid");
		//放开当前输入框
		$(this).parent().next().attr("disabled",false);
	});
	//绑定卖家事件
	$("#sellFee").find("input:radio").click(function(){
		//禁用买家所有输入框
		$("#sellFee").find("input:text:visible").prop("disabled",true);
		//移除校验不通过样式
		$("#sellFee").find("input").removeClass("validatebox-invalid");
		//放开当前输入框
		$(this).parent().next().attr("disabled",false);
		//等于4 特殊处理
		if($(this).val() == 4){
			$(this).parent().nextAll("input").attr("disabled",false);
		}
	});
}

function initUpdatePromRule(actId){
	$.getJSON(CONTEXT+"/active/queryPromRuleFeeById?actId="+actId,function(data){
		var rule = data.promRule;
		//设置数量与预付款
		$("#maxProdNum").val(rule.maxProdNum);
		$("#prepayAmt").val(rule.prepayAmt);
		//买家手续费
		var fees = data.promFees||[];
		for(var i=0;i<fees.length;i++){
			var fee = fees[i];
			if(fee.feeSource == 1){
				//买家
				setFee($("#buyFee"),fee);
			} else {
				setFee($("#sellFee"),fee);
			}
		}
	});
	
	function setFee($c,fee){
		if(fee.feeType == 0){ //不收手续费
			var $r = $c.find("input:radio[value='0']");
			$r.click();
		} else if(fee.feeType == 1 || fee.feeType == 2 || fee.feeType == 3){
			var $r = $c.find("input:radio[value='"+fee.feeType+"']");
			$r.click();
			$r.parent().next().val(fee.fee);
		} else if(fee.feeType == 4){
			var $r = $c.find("input:radio[value='"+fee.feeType+"']");
			$r.click();
			$r.parent().nextAll("input[name$='orderPayAmt']").val(fee.orderPayAmt);
			$r.parent().nextAll("input[name$='feeScale']").val(fee.feeScale);
			$r.parent().nextAll("input[name$='fee']").val(fee.fee);
		}
	}
}