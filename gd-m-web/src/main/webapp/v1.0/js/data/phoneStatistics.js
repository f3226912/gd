
/*1,7,15,30天获取数据画出图形*/
function changeData(days,data){
    var obj={
                seriesLeft:[],
            };
            if(days==1){//今日统计
                for(var i=0;i<10;i++){
                    if(data.datajson.channelPhoneResultList[i]){
                       obj.seriesLeft[i] = data.datajson.channelPhoneResultList[i].count; 
                    }else{
                        obj.seriesLeft[i]=0;
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
               //text: '',
                subtext: '（被拨打次数）'
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
        option.series[0].data[i]=obj.seriesLeft[i];
    }

    myChart.setOption(option);
}

/*页面初始化*/
$(document).ready(function() {
	var memberId = getQueryString("memberId");
	var time=formatDate("-");
    changeDays($("#oneDay"));
    $.ajax( {  
        url:'getData',// 跳转到 action  
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
        var time=formatDate("-");
        $.ajax( {  
            url:'getData',// 跳转到 action  
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
    })
});

/*点击7日画图*/
$(document).ready(function() {
    $("#sevenDays").tap(function(){
        changeDays($(this));
        var memberId = getQueryString("memberId");
        $.ajax( {  
            url:'getData',// 跳转到 action  
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
            url:'getData', 
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
            url:'getData', 
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

/*填写表格*/
function drawTable(data){
    var context=""
    for(i=0;i<10;i++){
        if(data.datajson.channelPhoneResultList[i]){
            context=context+
            '<tr><td class="td-rank">'+
            (i+1)
            +'</td><td class="td-goods">'+
            data.datajson.channelPhoneResultList[i].pageName
            +'</td><td class="td-amount">'+
            data.datajson.channelPhoneResultList[i].count+
            '</td></tr>';
        }else{
            break;
        }
        
    }
    var tbody = document.body.querySelector('#tbody');
    tbody.innerHTML= context;
}
        

