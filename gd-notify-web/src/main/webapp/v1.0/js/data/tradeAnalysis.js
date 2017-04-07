/*页面初始化*/
$(document).ready(function() {
	changeDays($("#oneDay"));
	var time = formatTime();
	var memberId = getQueryString("memberId");
	$.ajax( {  
		url: 'tradeAnaly',//  
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
			   drawTable(data);
			   var money=fmoney(data.datajson.buyerAvgTradeAmt,2); 
			   $("#avMoney").html(money);                   
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
		var time = formatTime();
		var memberId = getQueryString("memberId");
		$.ajax( {  
			url:'tradeAnaly',// 跳转到 action  
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
				   var money=fmoney(data.datajson.buyerAvgTradeAmt,2); 
			   		$("#avMoney").html(money);                     
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
		var time = formatTime();
		var memberId = getQueryString("memberId");
		$.ajax( {  
			url:'tradeAnaly',// 跳转到 action  
			data:{  
				memberId:memberId ,
				timeType:1 
			},  
			type:'get',  
			cache:false,  
			dataType:'json',  
			success:function(data) {  
				if(data.resultcode =="0000" ){  
					changeData(7,data);
				   /* paintForm(obj); */
				   /* insertTable(); */ 
				   drawTable(data);
				   var money=fmoney(data.datajson.buyerAvgTradeAmt,2); 
			   		$("#avMoney").html(money);                     
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
			url:'tradeAnaly', 
			data:{  
			    memberId:memberId,
				timeType:2
			},  
			type:'get',  
			cache:false,  
			dataType:'json',  
			success:function(data) {  
				if(data.resultcode =="0000" ){  
					changeData(15,data); 
				   drawTable(data); 
				   var money=fmoney(data.datajson.buyerAvgTradeAmt,2); 
			   		$("#avMoney").html(money);                    
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
			url:'tradeAnaly', 
			data:{  
			    memberId:memberId,
				timeType:3
			},  
			type:'get',  
			cache:false,  
			dataType:'json',  
			success:function(data) {  
				if(data.resultcode =="0000" ){  
					var lenth=data.datajson.tradeResultList.length;//length为28天，29，天，30天，31天
					changeData(lenth,data); 
					drawTable(data);
					var money=fmoney(data.datajson.buyerAvgTradeAmt,2); 
			   		$("#avMoney").html(money);                     
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

	var tbody = document.body.querySelector('#tbody');
	var context='<tr>'
					+'<td class="td-rank">订单量</td>'
					+'<td class="td-re">' +fmoney(data.datajson.payedOrderNum, 2)+'</td>'
					+'<td class="td-unre">'+fmoney(data.datajson.nonPayOrderNum,2)+'</td>'
					+'<td class="td-amount">'+fmoney(data.datajson.closeOrderNum,2)+'</td>'
				+'</tr>'+
				'<tr>'
					+'<td class="td-rank">交易额</td>'
					+'<td class="td-re">' +fmoney(data.datajson.payedTradeAmt,2)+'</td>'
					+'<td class="td-unre">'+fmoney(data.datajson.nonPayTradeAmt,2)+'</td>'
					+'<td class="td-amount">'+fmoney(data.datajson.closeTradeAmt,2)+'</td>'
				+'</tr>'+
				'<tr>'
					+'<td class="td-rank">无商品信息订单量</td>'
					+'<td class="td-re">' +fmoney(data.datajson.nogPayedOrderNum,2)+'</td>'
					+'<td class="td-unre">'+fmoney(data.datajson.nonPayOrderNum,2)+'</td>'
					+'<td class="td-amount">'+fmoney(data.datajson.closeOrderNum,2)+'</td>'
				+'</tr>'+
				'<tr>'
					+'<td class="td-rank">无商品信息交易额</td>'
					+'<td class="td-re">' +fmoney(data.datajson.payedOrderNum,2)+'</td>'
					+'<td class="td-unre">'+fmoney(data.datajson.nogNonPayTradeAmt,2)+'</td>'
					+'<td class="td-amount">'+fmoney(data.datajson.nogCloseTradeAmt,2)+'</td>'
				+'</tr>';
	tbody.innerHTML= context;
}

/*1,7,15,30天获取数据画出图形*/
function changeData(days,data){
	var obj={	
				seriesLeft:[],
				seriesRight:[],
				xAxisName:"",
				xAxisData:[]
			};
			if(days==1){//
				for(var i=0;i<7;i++){
					if(data.datajson.tradeResultList[i]){
						obj.seriesLeft[i] = data.datajson.tradeResultList[i].orderNum;
						obj.seriesRight[i] = data.datajson.tradeResultList[i].tradeAmt;
						var tempTime=getStr(data.datajson.tradeResultList[i].time,5).replace("-","");
						tempOne=tempTime.substr(0,1);
						if(tempOne==0){//如果是00,04,08.去掉前面的0;
							tempTime=tempTime.substr(1,1);
						}
						obj.xAxisData[i] = tempTime + "点"; 
						} else{

							obj.seriesLeft[i] = 0;
							obj.seriesRight[i] = 0;
							obj.xAxisData=["00","04","08","12","16","20","24"];
						}
					}
				obj.xAxisName="时间段";
				            
			} else if (days==7) {//7日统计
				 for(var i=0;i<7;i++){
				 	if(data.datajson.tradeResultList[i]){
				 		obj.seriesLeft[i] = data.datajson.tradeResultList[i].orderNum;
						obj.seriesRight[i] = data.datajson.tradeResultList[i].tradeAmt;
						obj.xAxisData[i] = getStr(data.datajson.tradeResultList[i].time,5).replace("-","");
				 	}else{
				 		obj.seriesLeft[i] = 0;
						obj.seriesRight[i] = 0;
						obj.xAxisData[i] = 0;
				 	}	
				} 
				obj.xAxisName="";         
			}else if (days==15) { //15日统计
				for(var i=0;i!=14;i=i+2){
				if(data.datajson.tradeResultList[i]){
						var j=i/2;
						obj.seriesLeft[j] = data.datajson.tradeResultList[i].orderNum;
						obj.seriesRight[j] = data.datajson.tradeResultList[i].tradeAmt;
						obj.xAxisData[j] = getStr(data.datajson.tradeResultList[i].time,5).replace("-","");
					}else{
				 		obj.seriesLeft[i] = 0;
						obj.seriesRight[i] = 0;
						obj.xAxisData[i] = 0;
						}	 
            		}
            	obj.xAxisName="";
			}else if(days>27){//一个月统计
				for(var i=0;i<27;i=i+5){
					if(data.datajson.tradeResultList[i]){
						var j=i/5;
						obj.seriesLeft[j] = data.datajson.tradeResultList[i].orderNum;
						obj.seriesRight[j] = data.datajson.tradeResultList[i].tradeAmt;
						obj.xAxisData[j] = getStr(data.datajson.tradeResultList[i].time,5).replace("-",""); 					
					}else{
						var j=i/5;
						obj.seriesLeft[j] = 0;
						obj.seriesRight[j] = 0;
						obj.xAxisData[j] = 0; 
						} 
						obj.seriesLeft[6] = 0;
						obj.seriesRight[6] = 0;
						obj.xAxisData[6] = 0;
					}
					if(data.datajson.tradeResultList[days-1]){
						obj.seriesLeft[6] = data.datajson.tradeResultList[days-1].orderNum;
						obj.seriesRight[6] = data.datajson.tradeResultList[days-1].tradeAmt;
						obj.xAxisData[6] = getStr(data.datajson.tradeResultList[days-1].time,5).replace("-","");
					}
				obj.xAxisName="";
			}
			paintForm(obj);         
}



// 基于准备好的dom，初始化echarts实例
function paintForm(obj){
	var myChart = echarts.init(document.getElementById('main'));
	option = {
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				bottom:"1px",
				icon:"rect",
				data:['订单量','交易额']
			},
			xAxis: [
				{
					type: 'category',
					name: '',
					nameTextStyle:{
					    color: '#e46b51'
					},
					nameLocation:"end",
					data: []
				}
			],
			yAxis: [
				{
					type: 'value',
					name: '单位：（笔）',
					min: 0,
					max: 60,
					interval: 10,
					axisLabel: {
						formatter: '{value}'
					}
				},
				{
					type: 'value',
					name: '单位：（万）',
					min: 0,
					max: 6000,
					interval: 1000,
					axisLabel: {
						formatter: '{value}'
					}
				}
			],
			series: [
				{
					name:'订单量',
					type:'line',
					label:{
						normal:{
							show:true
						}
					},
					 lineStyle:{
						normal:{
							color:"#e46b51"
						}
					},
					areaStyle: {
						normal: {
							color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
								offset: 0,
								color: '#e46b51'
							}, {
								offset: 1,
								color: '#fff'
							}])
						 }
					},
					data:[]
				},
				{
					name:'交易额',
					type:'line',
					label:{
						normal:{
							show:true
						}
					},
					yAxisIndex: 1,
					 lineStyle:{
						normal:{
							color:"#59c895"
						}
					},
					areaStyle: {
						normal: {
							color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
								offset: 0,
								color: '#59c895'
							}, {
								offset:1,
								color: '#fff'
							}])
						}
					},
					data:[]
				}
			]
		};


	for(i=0;i<obj.xAxisData.length;i++){
		option.xAxis[0].data[i]=obj.xAxisData[i];
		option.series[0].data[i]=obj.seriesLeft[i];
		option.series[1].data[i]=obj.seriesRight[i];
	}
	option.xAxis[0].name=obj.xAxisName;

	myChart.setOption(option);
}

	




		
