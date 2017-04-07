(function($) {  
	/** 
	 * jQuery EasyUI 1.4 --- 功能扩展 
	 *  
	 * Copyright (c) 2009-2015  
	 * 
	 * 新增 validatebox 校验规则 
	 * author weiwenke
	 * 
	 */  
	$.extend(
			$.fn.validatebox.defaults.rules, {                                     
				checkPosNumber: {                                                                     
					validator: function (value, param) {  
						debugger;
						var businessId=param[0];
						var currid=param[1]; //获取当前对象
						var obj=$.map($("[name=posNumber][id!=posNumber]"),function(x,y){
						    if($(x).val()==value&&$(x).attr('id')!=currid){return x}
						})
						//var obj=$("[name=posNumber][value='"+value+"']");
						if(obj.length>0){
							$.fn.validatebox.defaults.rules.checkPosNumber.message ="此POS终端号当前页面已经存在！";  
						    return false;
						}
						if(!/^[a-zA-Z0-9]{8}$/.test(value)){                                           
							//4-25验证                                                         
							$.fn.validatebox.defaults.rules.checkPosNumber.message ="终端号格式不正确，请输入8位的英文或数字组合！";     
							// 说明：rules.checkPosNumber 是跟上面的 checkPosNumber 是保持一致的                                               
							return false;                                                       
						}
						var exist=$.ajax({                                                     
							url:CONTEXT+"business/checkPosNumber",                                 
							data:{"posNumber":value,"businessId":businessId},                                       
							async:false                                                     
						}).responseText;                                                   
						//如果是json格式的还需要 JSON.parse(exist) 转换下                                           
						if(exist=="Exist"){                                                             
							$.fn.validatebox.defaults.rules.checkPosNumber.message ="此POS终端号已被其他商铺占用!";             
							return false;                                                       
						}                                                                       
						return true;                                                       
					},                                                                     
					message: ''                                                             
				}                                                                           
			});   
})(jQuery);   
