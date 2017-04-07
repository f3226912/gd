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
            paintForm(obj);         
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
                changeData(1,data);
               /* paintForm(obj); */
               /* insertTable(); */ 
               drawTable(data);                    
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
                   /* paintForm(obj); */
                   /* insertTable(); */ 
                   drawTable(data);                    
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
                   /* paintForm(obj); */
                   /* insertTable(); */ 
                   drawTable(data);                    
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
                   drawTable(data);                    
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
                    changeData(1,data); 
                    drawTable(data);                    
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
 




        
