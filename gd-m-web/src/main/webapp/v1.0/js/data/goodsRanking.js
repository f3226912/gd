/*1,7,15,30天获取数据画出图形*/
function changeData(days,data){
    var obj={
                seriesLeft:[],
            };
            if(days==1){//今日统计
                for(var i=0;i<10;i++){
                    if(data.datajson.goodsTradeResultLists[i]){
                        obj.seriesLeft[i] = (data.datajson.goodsTradeResultLists[i].tradeAmt/10000).toFixed(2);
                    }
                    else{
                        obj.seriesLeft[i] = 0;
                    }
                    
               }
            } 
           /* paintForm(obj);    */     
}

/*画图*/
function paintForm(obj){
    var myChart = echarts.init(document.getElementById('main'));
     option = {
            title: {
               /* text: '',*/
                subtext: '（交易额单位：万）'
            },
            tooltip: {
                trigger: 'axis',
            },
            legend: {
               
            },
            xAxis: [
                {
                    type: 'category',
                     splitLine:{
                        show:false
                    },
                    data: ['1','2','3','4','5','6','7','8','9','10']
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '',
                    min: 0,
                    max: 60,
                    interval: 10,
                    axisLabel: {
                        formatter: '{value}'
                    }
                }
            ],
            series: [
                {
                    name:'蒸发量',
                     itemStyle: {
                        normal: {
                            
                            label:{
                                position:'top',
                                show:true
                            },
                            color: '#5ec796',
                            width: '10'
                        }
                    },
                    type:'bar',
                    data:[]
                }
            ]
        };
    for(i=0;i<obj.seriesLeft.length;i++){
        if(obj.seriesLeft[i]){
           option.series[0].data[i]=obj.seriesLeft[i]; 
        }
        else{
            option.series[0].data[i]=0;
        }    
    }

    myChart.setOption(option);
}

/*填写表格*/
function drawTable(data){
    var context=""
    for(i=0;i<10;i++){
        if(data.datajson.goodsTradeResultLists[i]){
            context=context+
            '<tr><td class="td-rank">'+
            data.datajson.goodsTradeResultLists[i].orderNum
            +'</td><td class="td-goods">'+
            data.datajson.goodsTradeResultLists[i].productName
            +'<td class="td-sales">'+
            data.datajson.goodsTradeResultLists[i].pv+"吨"
            +'</td></td><td class="td-amount">'+
            fmoney(data.datajson.goodsTradeResultLists[i].tradeAmt) +
            '</td></tr>';
        }else{
            break;
        }    
    }
    var tbody = document.body.querySelector('#tbody');
    tbody.innerHTML= context;
}

//输出数据
function drawData(data){
	//计算每种商品的图片个数
	var arry=[],
		arrytt=[],
		length1=data.datajson.goodsTradeResultLists.length,
		eachMoney=0;
	for(var q=0;q<length1;q++){//把所有的销售额加入到数组中
		arry.push(data.datajson.goodsTradeResultLists[q].tradeAmt);
		arrytt.push(data.datajson.goodsTradeResultLists[q].tradeAmt);
	}
	for(var w=0;w<length1;w++){//求出最大值，最大值为arry[length1-1]
		if(arry[w]>arry[w+1]){
			arry[w+1]=arry[w];
		}
	}
	var maxMoney=arry[length1-1],//所有销售金额中的最大值
		eachMoney=maxMoney/15;//每个图标代表的金额
	for(var t=0;t<length1;t++){//总数除以每一个图标代表的金额
		arrytt[t]=arrytt[t]/eachMoney;
		arrytt[t]=Math.ceil(arrytt[t]);
		if(arrytt[t]>15){
			arrytt[t]=15;
		}
	}
	
	console.log(arrytt);
	
	
	var context="";
    for(i=0;i<length1;i++){
        if(data.datajson.goodsTradeResultLists[i]){
            context=context
            +	'<li>'
			+		'<div class="num"></div>'
			+		'<div class="name">'+data.datajson.goodsTradeResultLists[i].productName+'</div>'
			+		'<ul>'
			+			'<li><span class="doc"></span>交易额: <em>'+data.datajson.goodsTradeResultLists[i].tradeAmt+'</em>元</li>'
			+			'<li><span class="doc"></span>销量: <em>'+data.datajson.goodsTradeResultLists[i].sales+'</em>'+data.datajson.goodsTradeResultLists[i].unitString+'</li>'
			+		'</ul>'
			+		'<div class="fruits">'
			+		setImgs(data,arrytt[i]);
			+		'</div>'
			+	'</li>';
        }else{
            break;
        }    
    }
    var list = $(".lists");
    list.html(context);
    numLoop();
}

//设置图片的多少与类型
function setImgs(data,num){
	var mainCategory=data.datajson.goodsTradeResultLists[i].mainCategory,
		imgs='';
	mainCategory=parseInt(mainCategory);
	switch(mainCategory){
	case 35:
//		imgs='<img src="../v1.0/images/data/cabbage.png"/>';
//		for(var p=1;p<num;p++){
//			imgs=imgs+'<img src="../v1.0/images/data/cabbage.png"/>';
//		}
		imgs=addImgs('cabbage',num);
	  break;
	case 1392:
	 	imgs=addImgs('cabbage',num);
	  break;
	case 1393:
	 	imgs=addImgs('apple',num);
	  break;
	case 1394:
	 	imgs=addImgs('shrimp',num);
	  break;
	case 1395:
	 	imgs=addImgs('flour',num);
	  break;
	case 1396:
	 	imgs=addImgs('flour',num);
	  break;
	case 1397:
	 	imgs=addImgs('car',num);
	  break;
	case 1399:
	 	imgs=addImgs('oil',num);
	  break;
	case 1400:
	 	imgs=addImgs('medicine',num);
	  break;
	case 1401:
	 	imgs=addImgs('meat',num);
	  break;
	default: 
		imgs=addImgs('cabbage',num);
}
	return imgs;
}

//确定是哪种图片
function addImgs(name,num){
	imgs='<img src="../v1.0/images/data/'+name+'.png"/>';
		for(var p=1;p<num;p++){
			imgs=imgs+'<img src="../v1.0/images/data/'+name+'.png"/>';
		}
	return imgs;
}

//没有数据的时候
function noData(data){
	if(data.datajson.goodsTradeResultLists.length==0){
		var list = $(".lists");
    	list.html('<img src="../v1.0/images/data/notData.png"/>');
	}
}

/*页面初始化*/
$(document).ready(function() {
    var memberId = getQueryString("memberId");
   // var timeType = getQueryString("timeType");
   // var timeStr = getQueryString("timeStr");
    //var sortCode = getQueryString("sortCode");
    var time=formatDate("-");
    console.log(time);
    changeDays($("#oneDay"));
    $.ajax( {  
        url:'analy',// 跳转到 action  
        data:{  
           memberId:memberId,
           timeType:null,
           timeStr:time
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) {  
            if(data.resultcode =="0000" ){  
            	drawData(data);
            	noData(data);
//                changeData(1,data);
               /* paintForm(obj); */
               /* insertTable(); */ 
               /*drawTable(data);*/                    
             }else{  
                 console.log(data.msg);  
             } 
             console.log(data);
            },  
        error : function() {   
               alert("异常！");  
            }  
     })
}); 

/*点击当日画图*/
$(document).ready(function() {
    $("#oneDay").tap(function(){
        changeDays($(this));
        var memberId = getQueryString("memberId");
        var timeStr = formatDate("-");
        $.ajax( {  
            url:'analy',// 跳转到 action  
            data:{  
                memberId:memberId,
                timeType:null,
                timeStr:timeStr
            },  
            type:'get',  
            cache:false,  
            dataType:'json',  
            success:function(data) {  
                if(data.resultcode =="0000" ){  
                    changeData(1,data);
                    drawData(data);
                    noData(data);
                   /* paintForm(obj); */
                   /* insertTable(); */ 
//                 drawTable(data);                    
                 }else{  
                    alert(data.msg);
                   //  console.log(data.msg);  
                 } 
                 console.log(data);
                },  
            error : function() {   
                   alert("异常！");  
                }  
         })
    })
});

/*点击7日画图*/
$(document).ready(function() {
    $("#sevenDays").tap(function(){
        changeDays($(this));
        var memberId = getQueryString("memberId");
        var timeStr = formatDate("-");
        $.ajax( {  
            url:'analy',// 跳转到 action  
            data:{  
                memberId:memberId,
                timeType:1 
            },  
            type:'get',  
            cache:false,  
            dataType:'json',  
            success:function(data) {  
                if(data.resultcode =="0000" ){  
                    changeData(1,data);
                    drawData(data);
                    noData(data);
                   /* paintForm(obj); */
                   /* insertTable(); */ 
//                 drawTable(data);                    
                 }else{  
                     console.log(data.msg);  
                 } 
                 console.log(data);
                },  
            error : function() {   
                   alert("异常！");  
                }  
         })
    })
});

/*点击15日画图*/
$(document).ready(function() {
    $("#fifDays").tap(function(){
        changeDays($(this));
        var memberId = getQueryString("memberId");
        $.ajax( {  
            url:'analy', 
            data:{  
                memberId:memberId,
                timeType:2
            },  
            type:'get',  
            cache:false,  
            dataType:'json',  
            success:function(data) {  
                if(data.resultcode =="0000" ){  
                    changeData(1,data); 
                    drawData(data);
                     noData(data);
//                 drawTable(data);                    
                 }else{  
                     console.log(data.msg);  
                 } 
                },  
            error : function() {   
                   alert("异常！");  
                }  
         })
   })
});

/*点击30日画图*/
$(document).ready(function() {
    $("#thirtyDays").tap(function(){
        changeDays($(this));
        var memberId = getQueryString("memberId");
        $.ajax( {  
            url:'analy', 
            data:{  
                memberId:memberId,
                timeType:3
            },  
            type:'get',  
            cache:false,  
            dataType:'json',  
            success:function(data) {  
                if(data.resultcode =="0000" ){  
                	console.log(data);
                	drawData(data);
                    changeData(1,data); 
                     noData(data);
//                  drawTable(data);                    
                 }else{  
                     console.log(data.msg);  
                 } 
                },  
            error : function() {   
                   alert("异常！");  
                }  
         })
   })
    
});

//列表数字循环
function numLoop(){
	var nums=$(".cont .lists li>.num");
		nums.each(function(i){
			$(this).html(i+1);
			if(i==0){
				$(this).css({"backgroundColor":"#ff8327","color":"#fff"});
			}
			if(i==1){
				$(this).css({"backgroundColor":"#2fbe48","color":"#fff"});
			}
			if(i==2){
				$(this).css({"backgroundColor":"#3ed2ff","color":"#fff"});
			}
		})
}
	


//点击问号
$(document).ready(function(){
	$(".cont .tital div .wenhao").tap(function(){
		$(".bomb").show();
		$(".mark").show();
	});
	
	$(".nothing").tap(function(){
			$(".bomb").hide();
			$(".mark").hide();
			bol=0;
	});
	$(".mark").tap(function(){
		$(".bomb").hide();
		$(".mark").hide();
	})
});

//设置弹框的宽度
$(document).ready(function(){
	var w=$(window).width();
	$(".bomb").width(w);
});
 




        