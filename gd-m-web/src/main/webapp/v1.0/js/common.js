
/**
 * CY 2016-6-13
 * H5 通用
 */

var common = (function(){
	
	return {
		//获取url 参数 return 对象
		getUrlData:function(){
			var url = window.location.search;
		    if (url.indexOf("?") != -1) {
		        var str = url.substr(1),
		        	strs = str.split("&"),
		        	obj = {};
		        for (i = 0; i < strs.length; i++) {
		        	
		        	obj[strs[i].split("=")[0]] = strs[i].split("=")[1];
		        }
		        return obj;
		    }
		},
		ajax:function(obj){
			
		},
		//提示
		tips:function(text){
			
		},
		//获取时间函数， 如今天，昨天
		getTime:function(AddDayCount) {
			
	        var dd = new Date();
	        dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
	        var y = dd.getFullYear();
	        var m = dd.getMonth()+1;//获取当前月份的日期
	        var d = dd.getDate();
	        if(m<10){
	            m ='0'+m;
	        };
	        if(d<10){
	            d ='0'+d;
	        };
	        return y+'-'+m+'-'+d;
	    }

	}
})()

