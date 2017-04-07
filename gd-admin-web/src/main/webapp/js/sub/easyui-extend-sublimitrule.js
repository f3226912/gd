$.extend($.fn.validatebox.defaults.rules, {
    checkAmt: {
        validator: function(value, param) {
        	var flag = new RegExp(regexEnum.decmal6).test(value);
        	var subAmount = $("#subAmout_id").val();
        	//alert(subAmount);
    		if(flag && parseFloat(value)>parseFloat(subAmount)){
    			return false;
    		}
        	if (flag && typeof(param) != "undefined") {
        		if (value.length > 20) {
        			return false;
        		}
        		return parseFloat($(param[0]).val()) <= parseFloat(value);
        	}
            return flag;
        },
        message: '请输入金额（数字）或必须大于等于上一个设定值或不能大于总补贴额'
    },
    checkCount: {
        validator: function(value, param) {
            var flag = new RegExp(regexEnum.intege1).test(value);
        	if (flag && typeof(param) != "undefined") {
        		if (value.length > 9) {
        			return false;
        		}
        		return parseInt($(param[0]).val()) <= parseInt(value);
        	}
            return flag;
        },
        message: '请输入次数（数字）或必须大于等于上一个设定值'
    }
});


$.extend($.fn.validatebox.methods, {  
	remove: function(jq, newposition){  
		return jq.each(function(){  
			$(this).removeClass("validatebox-text validatebox-invalid").unbind('focus.validatebox').unbind('blur.validatebox');
		});  
	},
	reduce: function(jq, newposition){  
		return jq.each(function(){  
		   var opt = $(this).data().validatebox.options;
		   $(this).addClass("validatebox-text").validatebox(opt);
		});  
	}	
}); 