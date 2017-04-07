function getSendList(userId,page) {
	if(pageTotal < page) {
		if(!showAll) {
			showAll = !showAll;
			$("#published").html($("#published").html()+"<div class=\"mui-text-center blank\">已经没有数据了</div>");
		}
		return;
	}
	$.ajax({
		type: 'POST',
		url: CONTEXT+'transfer/listSendDataCond/'+ userId,
		data:{
			"page":page,
			"userId":userId
		},
	    cache: 'false' ,
	    success: function(data) {
	    	data = eval("("+data+")")
	    	if(data.statusCode==0) {
	    		showAll = false;
	    		pageTotal = data.object.pageTotal;
	    		
	    		var html;
    			if(page==1) {
    				$("#published").html("");
    				$("#pendFinished").html("");
    				$("#dealed").html("");
    				$("#finished").html("");
    				$("#unfinished").html("");
    			}
    			
	    		$(data.object.pageData).each(function(i,v) {
	    			html = $("#template_layout").html();
	    			
	    			var src = '[干线]';
	    			if(v.source == 1){	    				
	    				src = '[同城]';	    				
	    			}	
	    			html = html.replace("#source", src);
	    		
	    			if(v.nstRule==1) {	    				
	    				html = html.replace("#comanySend", "已分配至物流公司");
	    			}else {
	    				html = html.replace("#comanySend","&nbsp;");
	    			}
	    			html = html.replace("#time",v.timebefore);
	    			
	    			link= CONTEXT+"transfer/showSendTranDetail/"+v.id+"/"+fromCode+"?c=1&userId="+userId+"&source="+v.source;
	    			
	    			html = html.replace("#link", link);
	    			
	    			html = html.replace("#send",v.s_provinceName+v.s_cityName+v.s_areaName);
	    			html = html.replace("#for",v.f_provinceName+v.f_cityName+v.f_areaName);
	    			html = html.replace("#carType",v.carTypeString);
	    			html = html.replace("#totalWeight",v.totalWeight);
	    			html = html.replace("#hundredweightString","吨");
	    			html = html.replace("#goodsType",v.goodsTypeString);

		    		$("#published").html($("#published").html()+html);
	    		});
	    		
	    		if(data.object.pageData.length==0) {
	    			$("#published").html($("#blank").html());
	    			setBlackHeight();
	    			
	    		}
	    	} else {
	    	}
	    } ,
	    error: function(err) {
	    }
	});
	
}

function setBlackHeight(){
	var height=$(document).height()/2-140 + "px";
	$("#noInfo").css("margin-top",height);
	console.log(height);
}

