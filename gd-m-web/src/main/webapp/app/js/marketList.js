var startNum = 0;
var MarketList = {
	render: function(pullObj){
		startNum++;
		$.ajax({
		   type: "POST", 
		   url: "/market/getMarketList",
		   data: {"startRow":startNum},
		   async:false,
		   success: function(data){
			   //alert(data);
		   		data = eval("(" + data + ")");  
				//alert(startNum);
				if(data.datajson.length > 0){
					// $("#marketTemp").tmpl({rows:data.datajson}).appendTo('#marketList');
					var str = "";
			   		for(var i=0; i<data.datajson.length; i++){
			   			var row = data.datajson[i];
			   			str +='<li class="market-item" marketId='+row.id+' marketName='+row.marketName+'>'+
				   			'<div class="banner" style="background-image: url('+row.marketImg+');">'+
				   				'<ul class="banner-info-list">';
					   			if(row.tradeAmount != undefined && row.tradeAmount != "" && row.tradeAmount != null){
					   				str += '<li class="banner-info-item">'+
					   				'<p class="total">'+row.tradeAmount+'</p><p class="name">年交易量</p>'+
					   				'</li>';
					   			}
					   			if(row.tradeMoney != undefined && row.tradeMoney != "" && row.tradeMoney != null){
					   				str += '<li class="banner-info-item">'+
			   						'<p class="total">'+row.tradeMoney+'</p><p class="name">年交易额</p>'+
				   					'</li>';
					   			}
					   			if(row.businessAmount != undefined && row.businessAmount != "" && row.businessAmount != null){
					   				str += '<li class="banner-info-item">'+
			   						'<p class="total">'+row.businessAmount+'个</p><p class="name">入驻商户</p>'+
				   					'</li>';
					   			}			
					   			if(row.marketArea != undefined && row.marketArea != "" && row.marketArea != null){
					   				str += '<li class="banner-info-item">'+
			   						'<p class="total">'+row.marketArea+'</p><p class="name">占地面积</p>'+
				   					'</li>';
					   			}		
				   					
				   				str +='</ul>'+
				   			'</div>'+
				   			'<div class="content">'+
				   				'<p class="title">'+row.marketName+'</p>'+
				   				'<p class="location"><img src="images/location.png" alt="">'+row.provinceName+' '+row.cityName+' '+row.areaName+'</p>'+
				   				'<p class="info">'+
				   					'<span class="info-t">覆盖分类：</span>';
					   				if(row.category != undefined){
					   					str +='<span class="info-c">'+row.category+'</span>';
					   				}
				   					str +='</br>'+
				   					'<span class="info-t">市场商户：</span>';
				   					if(row.businessClassAmount != undefined){
					   					str +='<span class="info-c">'+row.businessClassAmount+'</span>';
					   				}
				   				str += '</p>'+
				   			'</div>'+
				   		'</li>';
			   		}
			   		$('#marketList').append(str);
					pullObj && pullObj.endPullupToRefresh(true);
				}else{
					pullObj && pullObj.endPullupToRefresh(false);
				}
		    }
		});
		
		
		
		//$('#marketList').append($('#marketTemp').tmpl());
	},
	eventBind: function(){
		$("#topBtn").on("tap", function(){
			// $(".mui-scrollbar.mui-scrollbar-vertical").remove();
			mui('.mui-scroll-wrapper').scroll().scrollTo(0,0);
			// $(".mui-scrollbar-indicator").css({
			// 	"transform": "translate3d(0px, 0px, 0px) translateZ(0px)"
			// });
			// $(".mui-scroll").css({
			// 	"transform": "translate3d(0px, 0px, 0px) translateZ(0px)"
			// });
		});
		
		$("#marketList").on("tap","li" ,function(){
			var marketId = $(this).attr("marketId");
			var marketName = $(this).attr("marketName");
			try{
				window.webkit.messageHandlers.NativeMethod.postMessage({"marketId": marketId, "marketName":marketName});
	 		}catch (e) {
	 			
	 		}
			JsBridge.onClickMarket(marketId, marketName)
			
		});
		
		var wh = $(document).height();
		$(".mui-scroll").scroll(function(){
			var hstr = $(".mui-scroll").css("transform").split(",")[5];
			hstr = + hstr.substring(2, hstr.length-1);
			if(hstr > wh){
				$("#topBtn").show();
			}else{
				$("#topBtn").hide();
			}
		});
	},
	init: function(){
		this.render();

		var _this = this;
		mui.init({
			pullRefresh: {
				container: '#pullrefresh',
				// 上拉加载
				up: {
					contentrefresh: '正在加载...',
					contentnomore:'没有更多数据了',
					callback: function(){
						_this.render(this);
					}
				}
			}
		});
		$(".mui-scrollbar.mui-scrollbar-vertical").remove();
		mui('.mui-scroll-wrapper').scroll();
		this.eventBind();
	}
}

$(function(){
	MarketList.init();
});


