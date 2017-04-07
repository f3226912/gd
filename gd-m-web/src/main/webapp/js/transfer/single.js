var orderNoF;
var userIdF;
var showAll = false;

function getList(orderNo,page,userId) {
	if(pageTotal < page) {
		if(!showAll) {
			showAll = !showAll;
			$("#published").html($("#published").html()+"<div class=\"mui-text-center blank\">已经没有数据了</div>");
		}
		return;
	}
	
	orderNoF = orderNo;
	userIdF = userId;
	$.ajax({
		type: 'POST',
		url: CONTEXT+'transfer/listDataCond/'+ orderNo,
		data:{
			"page":page,
			"userId":userId
		},
	    cache: 'false' ,
	    success: function(data) {
	    	data = eval("("+data+")");
	    	
	    	pageTotal = data.object.pageTotal;
	    	if(data.statusCode==0) {
	    		showAll = false;

    			if(page==1) {
    				$("#published").html("");
    			}
	    		$(data.object.pageData).each(function(i,v) {
	    			if(v.orderStatusInt==null || v.orderStatusInt == "") {
	    				addSend(v);
	    			} else {
	    				addOther(v);
	    			}
	    		});
	    		
	    		if(data.object.pageData.length==0) {
	    		/*	$("#published").html("<h4>小主暂无此类型物流信息。</h4>");*/
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



function addSend(v) {
	var html = $("#template_layout").html();
	
	var src = '[干线]';
	if(v.source == 1){	    				
		src = '[同城]';		
	}		
	html = html.replace("#source", src);
	
	if(v.nstRule==1) {
		html = html.replace("#comanySend","已分配至物流公司");
	} else {
		html = html.replace("#comanySend","&nbsp;");
	}
	
	html = html.replace("#time",v.timebefore);
	
	html = html.replace("#link",CONTEXT+"transfer/showSendTranDetail/"+v.id+"/"+fromCode+"?orderNo="+orderNoF+"&userId="+userIdF+"&source="+v.source);
	
	html = html.replace("#send",v.s_provinceName+v.s_cityName+v.s_areaName);
	html = html.replace("#for",v.f_provinceName+v.f_cityName+v.f_areaName);
	html = html.replace("#carType",v.carTypeString);
	html = html.replace("#totalWeight",v.totalWeight);
	html = html.replace("#hundredweightString","吨");
	html = html.replace("#goodsType",v.goodsTypeString);

	$("#published").html($("#published").html()+html);
	
	$(".ahref").on("tap",function(){
		location.href=$(this).data("ahref");
	})
}


function addOther(v) {
	var html = $("#template_other_layout").html();
	
	var src = '[干线]';
	if(v.source == 1){	    				
		src = '[同城]';		
	}	
	html = html.replace("#source", src);
	
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

	switch(v.orderStatusInt) {
	case 1:
		link= CONTEXT+"transfer/showWfTranDetail/"+v.nobId+"/"+fromCode+"?orderNo="+orderNoF+"&userId="+userIdF+"&source="+v.source;
		break;
	case 2:
		link= CONTEXT+"transfer/showFTranDetail/"+v.nobId+"/"+fromCode+"?orderNo="+orderNoF+"&userId="+userIdF+"&source="+v.source;
		break;
	case 3:
		link= CONTEXT+"transfer/showFoTranDetail/"+v.nobId+"/"+fromCode+"?orderNo="+orderNoF+"&userId="+userIdF+"&source="+v.source;
		break;
	case 4:
		link= CONTEXT+"transfer/showufTranDetail/"+v.nobId+"/"+fromCode+"?orderNo="+orderNoF+"&userId="+userIdF+"&source="+v.source;
		break;
	case 5:
		link= CONTEXT+"transfer/showufTranDetail/"+v.nobId+"/"+fromCode+"?orderNo="+orderNoF+"&userId="+userIdF+"&source="+v.source;
		break;
	default:
		link= CONTEXT+"transfer/showSendTranDetail/"+v.nobId+"/"+fromCode+"&source="+v.source;
		break;
	}

	html = html.replace("#link",link);
	//html = html.replace("#carType",v.carTypeString);
	//html = html.replace("#totalWeight",v.totalWeight);
	//html = html.replace("#hundredweightString",v.hundredweightString);
	//html = html.replace("#goodsType",v.goodsTypeString);

	$("#published").html($("#published").html()+html);
	
}

