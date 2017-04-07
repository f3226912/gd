
/**
 * CY 2016-6-13
 * 交易数据
 */

(function($){
	
	var urlData = common.getUrlData();
	
    var strHtml = '<ul >'
    +    '<li class="ui-box ui-vj"><label class="ui-box">交易额</label><span class="ui-box"><em>{{tradeAmt}}</em>元</span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">订单量</label><span class="ui-box"><em>{{orderNum}}</em></span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">交易买家</label><span class="ui-box"><em>{{buyerNum}}</em></span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">成交件数</label><span class="ui-box"><em>{{goodsNum}}</em></span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">订单平均交易额</label><span class="ui-box"><em>{{orderAvgTradeAmt}}</em>元</span></li>'
    +    '<li class="ui-box ui-vj"><label class="ui-box">客单价</label><span class="ui-box"><em>{{buyerAvgTradeAmt}}</em>元</span></li>'
    +'</ul>';

    var statTplt = new repeater({
        ele:'.cont',
        templete:strHtml
    });

    var today = common.getTime(0), //今天
        yesterday = common.getTime(-1); //昨天
    console.log(today);
    //默认获取当日时间
    changeCont({
		memberId:urlData.memberId,
		timeStr:today
	});

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
        changeCont(data);
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
