
/**
 * CY 2016-6-13
 * 交易数据
 */

(function($){
	
	var urlData = common.getUrlData();
	
    /*var strHtml = '<ul >'
    +    '<li class="ui-box ui-vj"><label class="ui-box">交易额</label><span class="ui-box"><em>{{tradeAmt}}</em>元</span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">订单量</label><span class="ui-box"><em>{{orderNum}}</em></span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">交易买家</label><span class="ui-box"><em>{{buyerNum}}</em></span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">成交件数</label><span class="ui-box"><em>{{goodsNum}}</em></span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">订单平均交易额</label><span class="ui-box"><em>{{orderAvgTradeAmt}}</em>元</span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">客单价</label><span class="ui-box"><em>{{buyerAvgTradeAmt}}</em>元</span></li>'
    +'</ul>';*/
	var strHtml ='<div class="tital">'
	+                 '<img src="../v1.0/images/data/tital_inon.png"/>'
	+                    '<div>'
	+	                    '<label>交易额</label><span><em>{{tradeAmt}}</em>元</span>'
	+                       '</div>'
	+                              '</div>'
	+							'<ul class="middle">'
	+								'<li>'
	+									'<p class="num">{{orderNum}}</p>'
	+								'<p>订单量</p>'
	+								'</li>'
	+								'<li>'
	+									'<p class="num">{{buyerNum}}</p>'
	+									'<p>交易买家</p>'
	+									'<img class="left" src="../v1.0/images/data/Isolation.png"/>'
	+									'<img class="right" src="../v1.0/images/data/Isolation.png"/>'
	+							'</li>'
	+							'<li>'
	+							'<p class="num">{{goodsNum}}</p>'
	+							'<p>成交件数</p>'
	+							'</li>'
	+								'</ul>'
	+							'<ul class="footer">'
	+							'<li>'
	+							'<p>订单平均交易额</p>'
	+							'<p class="num"><em>{{orderAvgTradeAmt}}</em>元</p>'
	+							'</li>'
	+							'<li>'
	+							'<p>客单价</p>'
	+							'<p class="num"><em>{{buyerAvgTradeAmt}}</em>元</p>'
	+							'<img class="right" src="../v1.0/images/data/Isolation.png"/>'
	+							'</li>'
	+							'</ul>'
	

    var statTplt = new repeater({
        ele:'.cont',
        templete:strHtml
    });

    var today = common.getTime(0), //今天
        yesterday = common.getTime(-1); //昨天
    console.log(today);
    
    //获取url名字
    function getQueryString(name) {
        var result = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
        if (result == null || result.length < 1) {
            return "";
        }
        return result[1];
    };
    
    var type=getQueryString("type");
    
    //默认获取当日时间
    switch(type)
    {
          case "1":
        	  changeCont({
        			memberId:urlData.memberId,
        			timeStr:today
        		});
        	  break; 
          case "2":
        	  changeCont({
        			memberId:urlData.memberId,
        			timeStr:yesterday
        		});
        	  $('.tab li').eq(1).addClass('active').siblings().removeClass();
        	  break; 
          case "3":
        	  changeCont({
      			memberId:urlData.memberId,
      			timeType:4
      		});
        	$('.tab li').eq(2).addClass('active').siblings().removeClass();
        	  break; 
          default:
        	 changeCont({
      			memberId:urlData.memberId,
      			timeStr:today
      		});
    }
    

    //点击获取数据
    $('.tab li').on('click',function(){
        var index = $(this).index(),
            data = null;
        $(this).addClass('active').siblings().removeClass();
        console.log(today)
        //今日
        if(index==0){
        	changeCont({
        		memberId:urlData.memberId,
        		timeStr:today
        	});
        };
        //昨日
        if(index==1){
        	changeCont({
        		memberId:urlData.memberId,
        		timeStr:yesterday
        	});
        };
        //本月
        if(index==2){
        	changeCont({
        		memberId:urlData.memberId,
        		timeType:4
        	});
        };
//        changeCont(data);
    })
    
    function changeCont(obj){
        //请求数据   
        $.ajax({
             type: "GET",
             url: "tradeInfo",
             data:obj,
             dataType: "json",
             success: function(data){
            	 var oData = data['datajson'];
                //数值检测
                for (var i in oData){
                	oData[i] = toThousandsFormat(oData[i]);
                	
                };

                //刷新HTML
                statTplt.render(oData);
             }
         });
    };

    //数值格式 1234,555.00
    function toThousandsFormat(num) {
        // 不需要处理
        if(num<1000){
            return num;
        }
        // 需要处理 
        var reg = /\./,
            int = num+'',
            point = '00';
        // 小数点 分割
        if(reg.test(num)){
            point = int.split('.')[1]
            int = int.split('.')[0];
        };
 
        var result = [ ], counter = 0;
        int = (int || 0).toString().split('');
        for (var i = int.length - 1; i >= 0; i--) {
            counter++;
            result.unshift(int[i]);
            if (!(counter % 3) && i != 0) { result.unshift(','); }
        }
        return result.join('')+'.'+point;
    }
})($)
