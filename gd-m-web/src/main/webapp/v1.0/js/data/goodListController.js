/**
 * CY 2016-6-13
 * 首页
 */

(function($){
	var urlData = common.getUrlData();
	
    var $arrSortBtn =  $('.sort-way'),
        $objSortLayer = $('.sort-layer'),
        $listWrap = $('.list-wrap');
	
	//模板初始化
    console.log(imgHost);
    
	var strhtml = '<ul class="list">'
	+'{{#goodsTradeResultLists}}'
	+	'<li>'
	+		'<div class="good-area ui-box">'
	+			'<img src="'+imgHost+'{{url}}" alt="">'
	+			'<div class="ui-f1">'
	+				'<h3 class="ui-over-elli2">{{productName}}</h3>'
	+				'<i>成交笔数：{{orderNum}}</i>'
	+			'</div>'
	+		'</div>'
	+		'<div class="bottom ui-box">'
	+			'<span class="ui-f1 w-1 ui-box ui-vc">浏览量：<em>{{pv}}</em></span>'
	+			'<span class="ui-f1 w-1 ui-box ui-vc">交易额(元)：<em>{{tradeAmt}}</em></span>'
	+		'</div>'
	+	'</li>'
	+'{{/goodsTradeResultLists}}'
	+'</ul>';
	
	var goodTplt = new repeater({
        ele:'.list-wrap',
        templete:strhtml
    });
	var ddDate = new Date(),
	    yDate = ddDate.getFullYear(),
	    mDate = ddDate.getMonth()+1;
	
	if(mDate<10){
		mDate ='0'+mDate;
    };
    var sendDate = {
    	memberId:urlData.memberId,
    	sortCode:'tradeAmt:DESC',
    	timeStr:yDate+'-'+mDate
    }
    
    $('#time').text(yDate+'-'+mDate);
    
	//首次渲染HTML
	changeCont(sendDate);
	//渲染goods列表
	function changeCont(obj){
		goodTplt.loading();
        //请求数据   延迟处理
    	$.ajax({
             type: "GET",
             url: "analy",
             data:obj,
             dataType: "json",
             success: function(data){
            	 var oData = data.datajson;
            	 var tempData = data.datajson.goodsTradeResultLists;
            	 //无图片情况使用 默认图片icon_placeholder.png
            	for (var i=0;i<tempData.length;i++){
            			console.log(!tempData[i].url);
                		if(!tempData[i].url){
                			tempData[i].url = CONTEXT+'v1.0/images/data/icon_placeholder.png';
                		}
                		console.log(CONTEXT+'v1.0/images/data/icon_placeholder.png');
            	}
            	oData.goodsTradeResultLists = tempData;
            	 
                //刷新HTML
            	console.log(tempData);
            	if(oData.goodsTradeResultLists.length == 0){
            		goodTplt.noMore();
            	}else{
            		goodTplt.render(oData);
            	}
               
             }
        });
        
    };
	
	//排序方式
    $objSortLayer.find('li').on('click',function(){
    	var sortCode = $(this).data('sort');
    	$(this).addClass('active').siblings().removeClass('active');
    	
    	$objSortLayer.hide();//隐藏弹窗
    	$arrSortBtn.removeClass('arrow');
    	if(sortCode==0){
    		sendDate.sortCode = 'tradeAmt:DESC';
    	}
    	if(sortCode==1){
    		sendDate.sortCode = 'tradeAmt:ASC';
    	}
    	if(sortCode==2){
    		sendDate.sortCode = 'pv:ASC';
    	}
		if(sortCode==3){
			sendDate.sortCode = 'pv:DESC';
		}
    	changeCont(sendDate);//goods列表
    	
    	$('body').css({'overflow':''}); //释放滚动条
    	
    	//排序数字高亮
    	if(sortCode<=1){
    		$listWrap.addClass('trade-highlight').removeClass('scan-highlight');
    	}else{
    		$listWrap.addClass('scan-highlight').removeClass('trade-highlight');
    	}
    	
    })
	
    //点击阴影部分隐藏
    $('.sort-layer-bg').on('touchstart',function(){
        $objSortLayer.hide();
        $arrSortBtn.removeClass('arrow');
        $('body').css({'overflow':''});
    })
	
    //点击排序 弹出选择层
    $arrSortBtn.on('click',function(){

        var index = $(this).index();

        if(index>=2){
            return;
        }

        $('body').css({'overflow':'hidden'});
        $(this).addClass('active').siblings().removeClass('active');
        if(index==0){
        	
            //按交易额
            var arrowLeft = $(this).width()/2;
            $objSortLayer.find('span').css({'left':arrowLeft})
            $('.sum-deal').show();
            $('.sum-browse').hide();

        }else if(index==1){
            //按浏览量
            var arrowLeft = $(this).width()/2 + $(this).width();
            $objSortLayer.find('span').css({'left':arrowLeft});
            $('.sum-browse').show();
            $('.sum-deal').hide();
        };
        $(this).addClass('arrow');
        $objSortLayer.show();
    })
	
    //选取时间
    timerFn({ele:'#time'});
    
    
    
    //监听滚动条 无线加载
//  $(document).scroll(function(){
//  	var docH = $(document).height(),
//  		variableH = $(document).scrollTop()+$(window).height();
//
//  	if(docH-variableH<20){
//  		//加载更多
//  		goodTplt.appendHtml(lldata);
//  	}
//	    
//	})
//  
    //时间插件 

    function timerFn(obj){
        var currYear = (new Date()).getFullYear(); 
        var opt={};
        opt.date = {preset : 'date'};
        opt.datetime = {preset : 'datetime'};
        opt.time = {preset : 'time'};
        opt.default = {
            theme: 'android-ics light', //皮肤样式
            display: 'modal', //显示方式
            mode: 'scroller', //日期选择模式
            dateFormat: 'yy-mm',
            lang: 'zh',
            setText: '确定', //确认按钮名称
            cancelText: '取消',//取消按钮名称
            dateOrder: 'yymm',
            showNow: true,
            nowText: "今天",
            startYear: currYear-1, //开始年份
            endYear: currYear, //结束年份
            minDate:new Date(new Date().setFullYear(currYear - 1)),
            maxDate: new Date(),
            onSelect:function(valueText){   
                $("#time").text(valueText);
                sendDate.timeStr = valueText;
                changeCont(sendDate);
                $objSortLayer.hide();//隐藏弹窗
            }

        };
        if(obj.format==0){
            opt.default.dateFormat = 'yy-mm-dd';
            opt.default.dateOrder = 'yymmdd';
        }
        $(obj.ele).mobiscroll($.extend(opt['date'], opt['default']));
    }
    
})($);
