function getnstList(type,userId,page) {
	if(pageTotal < page) {
		var content;
		switch(type) {
		case 1:
			content = $("#pendFinished");
			break;
		case 2:
			content = $("#dealed");
			break;
		case 3:
			content = $("#finished");
			break;
		case 4:
			content = $("#unfinished");
			break;
		case 5:
			content = $("#unfinished");
			break;
		}
		if(!showAll) {
			showAll = !showAll;
			content.html(content.html()+"<div class=\"mui-text-center blank\">已经没有数据了</div>");
		}	
		return;
	}
	
	var url;
	switch(type) {
	case 1:
		url = CONTEXT+'transfer/listWfDataCond';
		break;
	case 2:
		url = CONTEXT+'transfer/listFDataCond';
		break;
	case 3:
		url = CONTEXT+'transfer/listFoDataCond';
		break;
	case 4:
		url = CONTEXT+'transfer/listuFDataCond';
		break;
	case 5:
		url = CONTEXT+'transfer/listuFDataCond';
		break;
	}
	$.ajax({
		type: 'POST',
		url: url,
		data:{
			"userId":userId,
			"page":page
		},
	    cache: 'false',
	    success: function(data) {
	    	data = eval("("+data+")");
	    	var content;
			switch(type) {
			case 1:
				content = $("#pendFinished");
				break;
			case 2:
				content = $("#dealed");
				break;
			case 3:
				content = $("#finished");
				break;
			case 4:
				content = $("#unfinished");
				break;
			case 5:
				content = $("#unfinished");
				break;
			}

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
    				html = $("#template_other_layout").html();
    				
    				if(v.orderStatusInt==1) {
        				html = html.replace("#get","已有车主接单");
    				} else if(v.orderStatusInt==2) {
    					html = html.replace("#get","已接受车主接单");
    				} else if(v.orderStatusInt==3) {
    					html = html.replace("#get","已确认收货");
    				} else if(v.orderStatusInt==4) {
    					html = html.replace("#get","已拒绝车主接单");
    				} else if(v.orderStatusInt==5) {
    					html = html.replace("#get","车主已取消接单");
    				} else {
    					html = html.replace("#get","&nbsp;");
    				}
    				
    				html = html.replace("#send",v.s_provinceName+v.s_cityName+v.s_areaName);
	    			html = html.replace("#for",v.f_provinceName+v.f_cityName+v.f_areaName);
	    			html = html.replace("#carType",v.carTypeString);
	    			html = html.replace("#totalWeight",v.totalWeight);
	    			html = html.replace("#hundredweightString","吨");
	    			html = html.replace("#goodsType",v.goodsTypeString);

	    			var link;
	    			//debugger;
	    			switch(v.orderStatusInt) {
	    			case 1:
	    				link= CONTEXT+"transfer/showWfTranDetail/"+v.nobId+"/"+fromCode+"?c=1&userId="+userId;
	    				break;
	    			case 2:
	    				link= CONTEXT+"transfer/showFTranDetail/"+v.nobId+"/"+fromCode+"?c=1&userId="+userId;
	    				break;
	    			case 3:
	    				link= CONTEXT+"transfer/showFoTranDetail/"+v.nobId+"/"+fromCode+"?c=1&userId="+userId;
	    				break;
	    			case 4:
	    				link= CONTEXT+"transfer/showufTranDetail/"+v.nobId+"/"+fromCode+"?c=1&userId="+userId;
	    				break;
	    			case 5:
	    				link= CONTEXT+"transfer/showufTranDetail/"+v.nobId+"/"+fromCode+"?c=1&userId="+userId;
	    				break;
	    			}
	    			
	    			html = html.replace("#link",link);
	    			//html = html.replace("#carType",v.carTypeString);
	    			//html = html.replace("#totalWeight",v.totalWeight);
	    			//html = html.replace("#hundredweightString",v.hundredweightString);
	    			//html = html.replace("#goodsType",v.goodsTypeString);

	    			content.html(content.html()+html);
		    		
	    		});
	    		
	    		if(data.object.pageData.length==0) {
	    			content.html($("#blank").html());
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

