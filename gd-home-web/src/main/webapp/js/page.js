/*
 JS for showing data into page plugs
 */

/**
 * 预留代码 排序条件存储
 */
//var sortMap = new Map();
/**
 * Simple Map
 * 
 * 
 * var m = new Map(); m.put('key','value'); ... var s = "";
 * m.each(function(key,value,index){ s += index+":"+ key+"="+value+"/n"; });
 * alert(s);
 * 
 * @author ailen
 * @date 2008-05-24
 */

/**
 * 设置排序参数
 * sort为排序字段
 * order为排序方法 d desc  a asc o 替换之前
 * recall 回调函数，排序完要处理的方法 在提交数据前
*/
function changeOrder(sort, order, recall) {
	if(order=='o') {
		if($("#order").val()=='a' ) {
			order = 'd';
		} else {
			order = 'a';
		}
	}
	
	$("#sort").val(sort);
	$("#order").val(order);

	$("#page").val(1);

	if (recall != null) {
		recall();
	}

	$("#pageForm").submit();
}

/**
 * 用于跳转指定页面JS方法
 */
function goTOPage(num) {

	var reg = /^[0-9]*[1-9][0-9]*$/;
	// 输入的页数必须是正整数
	if (num >= 1 && num <= $("#pageTotal").val() && reg.test(num)) {
		num += '';
		$("#page").val(num);
		$("#pageForm").submit();
	}
}

$(document).ready(function() {
	debugger;
	if($("#pageTotal").val() && $("#pageTotal").val()!=0) {
		gduiPage({
		    cont: 'mar-com-fenye',
		    pages: $("#pageTotal").val(), //可以叫服务端把总页数放在某一个隐藏域，再获取。假设我们获取到的是18
		    skip: true, //是否开启跳页
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = $("#page").val();
		        return page;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	goTOPage(e.curr);
		        }
		    },
		    staticPage:true 
		});
	}
});
