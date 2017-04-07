/*获得日期时间*/
function formatTime(){
	var myDate = new Date();
	var year = myDate.getFullYear().toString();
	var month = notOne(myDate.getMonth().toString());
	var day = notOne(myDate.getDate().toString()); 
	var hour = notOne(myDate.getHours().toString());       
	var minite = notOne(myDate.getMinutes().toString());     
	var seconds = notOne(myDate.getSeconds().toString());
	return year +"-" + month+ "-" + day +" " + hour +":" + minite+ ":" + seconds;
}

/*获得格式化日期*/
function formatDate(str){
	if(!str){
		str="";
	}
	var myDate = new Date();
	var year = myDate.getFullYear().toString();
	var month = notOne(myDate.getMonth().toString());
	var day = notOne(myDate.getDate().toString());
	return year + str + month + str + day;

}

/*判断日期时间是否只有一位*/
function notOne(str){
	if(str.length<2){
		str="0"+str;
	}
	return str;
}

/*点击今日，7日，15日，30日切换的方法*/
function changeDays($_Days){
    var $_this=$_Days;
        $(".lyHeadDays").removeClass('tapped').addClass('not-tapped');
        $_this.removeClass('not-tapped').addClass('tapped');
}

/*获取字符串str最后length位的字符窜*/

function getStr(str,length){ 
	var str_after= str.substring(str.length-length, str.length);
	return str_after; 
} 
/*格式化钱数的方法s为钱数，n为保留几位小数*/
function fmoney(s, n) { 
	n = n > 0 && n <= 20 ? n : 2; 
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
	t = ""; 
	for (i = 0; i < l.length; i++) { 
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
	} 
	return t.split("").reverse().join("") + "." + r; 
} 

/*获取url*/
function getQueryString(name) { 
var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
var r = window.location.search.substr(1).match(reg); 
if (r != null) return unescape(r[2]); return null; 
}


//兼容插件的两个左右滑动
$(document).ready(function() {
	var width = -$("body").width()+'px';
	var rightWidth = 0+ 'px';
	$("#left").tap(function(){
		$(".swiper-wrapper").css({"transform":"translate3d("+width+",0px,0px)","transition-duration":"500ms"});
	})
	$("#right").tap(function(){
		$(".swiper-wrapper").css({"transition-duration":"500ms","transform":"translate3d("+rightWidth+",0px,0px)"});
	})
});
/*页面初始化赋值*/
$(document).ready(function() {
	var time = formatTime();
	var memberId = getQueryString("memberId");
	changeDays($("#oneDay")); //头部改变天数的点击情况
	$.ajax( {  
		url:'tradeAnaly', //从html页面开始找../json/tradeAnalysisOne.json
		data:{ 
			 memberId:memberId,
             timeStr:time 
		},  
		type:'get',  
		cache:false,  
		dataType:'json',  
		success:function(data) {  
			if(data.resultcode =="0000" ){
				setAmount(data);//给页面内各个金额和订单量赋值 
				drawPies(data); //画饼状图
			 }else{  
				alert(data.msg);  
			 } 
			},  
		error : function() {   
			   alert("异常！");  
			}  
	 })
});

/*点击今日*/
$(document).ready(function() {
	$("#oneDay").tap(function(){
		changeDays($("#oneDay"));//头部改变天数的点击情况
		var time = formatTime();
		var memberId = getQueryString("memberId");
		$.ajax( {  
			url:'tradeAnaly', //从html页面开始找../json/tradeAnalysisOne.json
			data:{ 
				memberId:memberId,
			    timeStr:time  
			},  
			type:'get',  
			cache:false,  
			dataType:'json',  
			success:function(data) {  
				if(data.resultcode =="0000" ){
					setAmount(data);//给页面内各个金额和订单量赋值 
					drawPies(data); //画饼状图
				 }else{  
					alert(data.msg);  
				 } 
				},  
			error : function() {   
				   alert("异常！");  
				}  
		 })
	})
});

/*点击7日*/
$(document).ready(function() {
	$("#sevenDays").tap(function(){
		changeDays($("#sevenDays"));//头部改变天数的点击情况
		var time = formatTime();
		var memberId = getQueryString("memberId");
		$.ajax( {  
			url:'tradeAnaly', //从html页面开始找../json/tradeAnalysisOne.json
			data:{  
				memberId:memberId ,
				timeType:1 
			},  
			type:'get',  
			cache:false,  
			dataType:'json',  
			success:function(data) {  
				if(data.resultcode =="0000" ){
					setAmount(data);//给页面内各个金额和订单量赋值 
					drawPies(data); //画饼状图
				 }else{  
					alert(data.msg);  
				 } 
				},  
			error : function() {   
				   alert("异常！");  
				}  
		 })
	})
});

/*点击15日*/
$(document).ready(function() {
	$("#fifDays").tap(function(){
		changeDays($("#fifDays"));//头部改变天数的点击情况
		var memberId = getQueryString("memberId");
		$.ajax( {  
			url:'tradeAnaly', //从html页面开始找../json/tradeAnalysisOne.json
			data:{ 
				memberId:memberId,
				timeType:2
			},  
			type:'get',  
			cache:false,  
			dataType:'json',  
			success:function(data) {  
				if(data.resultcode =="0000" ){
					setAmount(data);//给页面内各个金额和订单量赋值 
					drawPies(data); //画饼状图
				 }else{  
					alert(data.msg);  
				 } 
				},  
			error : function() {   
				   alert("异常！");  
				}  
		 })
	})
});

/*点击30日*/
$(document).ready(function() {
	$("#thirtyDays").tap(function(){
		var memberId = getQueryString("memberId");
		changeDays($("#thirtyDays"));//头部改变天数的点击情况
		$.ajax( {  
			url:'tradeAnaly', //从html页面开始找../json/tradeAnalysisOne.json
			data:{ 
				memberId:memberId,
				timeType:3 
			},  
			type:'get',  
			cache:false,  
			dataType:'json',  
			success:function(data) {  
				if(data.resultcode =="0000" ){
					setAmount(data);//给页面内各个金额和订单量赋值 
					drawPies(data); //画饼状图
				 }else{  
					alert(data.msg);  
				 } 
				},  
			error : function() {   
				   alert("异常！");  
				}  
		 })
	})
});

/*画8个饼状图*/
function drawPies(data){
	var myChartTR = echarts.init(document.getElementById('pieOne'));//初始化交易分析已收款饼状图
	var myChartNA = echarts.init(document.getElementById('pieTwo'));//初始化交易分析待收款饼状图
	var myChartClosed = echarts.init(document.getElementById('pieThree'));//初始化交易分析已关闭饼状图
	var myChartRatio = echarts.init(document.getElementById('pieFour'));//交易分析交易额比例情况饼状图
	
	var payedTradeAmt=data.datajson.payedTradeAmt;//已收款金额
	var nonPayTradeAmt=data.datajson.nonPayTradeAmt;//待收款金额
	var closeTradeAmt=data.datajson.closeTradeAmt;//已关闭交易金额

	var myChartNR = echarts.init(document.getElementById('pieFive'));//初始化交易分析已收款订单量饼状图
	var myChartNNA = echarts.init(document.getElementById('pieSix'));//初始化交易分析待收款订单量饼状图
	var myChartClosedNum = echarts.init(document.getElementById('pieSeven'));//初始化交易分析已关闭订单量饼状图
	var myChartRatioNum = echarts.init(document.getElementById('pieEight'));//交易分析交易额订单量比例情况饼状图

	var payedOrderNum =	data.datajson.payedOrderNum;//已收款单数
	var nonPayOrderNum = data.datajson.nonPayOrderNum;//待收款单数
	var closeOrderNum = data.datajson.closeOrderNum;//已关闭交易单数

	var haveGoodsAmt = payedTradeAmt - data.datajson.nogPayedTradeAmt;//交易额（有商品）
	var noGoodsAmt = data.datajson.nogPayedTradeAmt;//交易额（无商品）
	var haveGoodsNum = payedOrderNum - data.datajson.nogPayedOrderNum;//订单量（有商品）
	var noGoodsNum = data.datajson.nogPayedOrderNum;//订单量（有商品）

	var amtTotal = data.datajson.tradeAmt;// 总的交易额
	var numTotal = data.datajson.orderNum; //总的订单量


	if(amtTotal==0){ //无数据时
		//交易分析已收款饼状图配置
		optionTR = {		
			series: [
				{
					name:'已收款',
					itemStyle: {
								normal: {
									color: '#5ec796'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{   
							label: {
								normal: {
									show:true,
									position: "center"
								}
							},
							value:0,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		myChartTR.setOption(optionTR);
		//交易分析待收款饼状图配置
		optionNA = {		
			series: [
				{
					name:'待收款',
					itemStyle: {
								normal: {
									color: '#5ec796'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						
						{   
							label: {
								normal: {
									show:true,
									position: "center"
								}
							},
							value:0,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		myChartNA.setOption(optionNA);
		//交易分析已关闭饼状图配置
		optionClosed = {		
			series: [
				{
					name:'已关闭',
					itemStyle: {
								normal: {
									color: '#f77236'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{   
							label: {
								normal: {
									show:true,
									position: "center"
								}
							},
							value:0,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		myChartClosed.setOption(optionClosed);

	}else{
		//交易分析已收款饼状图配置
		optionTR = {		
			series: [
				{
					name:'已收款',
					itemStyle: {
								normal: {
									color: '#5ec796'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{
							
							value:payedTradeAmt, 
							name:'50%',
							itemStyle:{
								normal:{
									color:"#35d5f7"
								}
							}
						},
						{   
							label: {
								normal: {
									show:false,
									position: "inside"
								}
							},
							value:nonPayTradeAmt+closeTradeAmt,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		if((payedTradeAmt/amtTotal)<0.0001 && (payedTradeAmt/amtTotal)>0){
			optionTR.series[0].data[0].value=1;
			optionTR.series[0].data[1].value=9999;
		}else if((nonPayTradeAmt+closeTradeAmt)/amtTotal<0.0001 && (nonPayTradeAmt+closeTradeAmt)/amtTotal){
			optionTR.series[0].data[0].value=9999;
			optionTR.series[0].data[1].value=1;
		}
		myChartTR.setOption(optionTR);
		//交易分析待收款饼状图配置
		optionNA = {		
			series: [
				{
					name:'待收款',
					itemStyle: {
								normal: {
									color: '#5ec796'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{
							
							value:nonPayTradeAmt, 
							name:'50%',
							itemStyle:{
								normal:{
									color:"#ffcf3e"
								}
							}
						},
						{   
							label: {
								normal: {
									show:false,
									position: "inside"
								}
							},
							value:payedTradeAmt+closeTradeAmt,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};

		if((nonPayTradeAmt/amtTotal)<0.0001 && (nonPayTradeAmt/amtTotal)>0){
			optionNA.series[0].data[0].value=1;
			optionNA.series[0].data[1].value=9999;
		}else if((payedTradeAmt+closeTradeAmt)/amtTotal<0.0001 && (payedTradeAmt+closeTradeAmt)/amtTotal>0){
			optionNA.series[0].data[0].value=9999;
			optionNA.series[0].data[1].value=1;
		}
		myChartNA.setOption(optionNA);
		//交易分析已关闭饼状图配置
		optionClosed = {		
			series: [
				{
					name:'已关闭',
					itemStyle: {
								normal: {
									color: '#f77236'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{
							
							value:closeTradeAmt, 
							name:'50%',
							itemStyle:{
								normal:{
									color:"#f77236"
								}
							}
						},
						{   
							label: {
								normal: {
									show:false,
									position: "inside"
								}
							},
							value:payedTradeAmt+nonPayTradeAmt,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		if((closeTradeAmt/amtTotal)<0.0001 && (closeTradeAmt/amtTotal)>0){
			optionClosed.series[0].data[0].value=1;
			optionClosed.series[0].data[1].value=9999;
		}else if((payedTradeAmt+nonPayTradeAmt)/amtTotal<0.0001 &&	(payedTradeAmt+nonPayTradeAmt)/amtTotal>0){
			optionClosed.series[0].data[0].value=9999;
			optionClosed.series[0].data[1].value=1;
		}
		myChartClosed.setOption(optionClosed);
	}

	if(payedTradeAmt==0){ //已收款交易额为0时
		optionRatio = {
			series : [
				
				{
					name: '',
					type: 'pie',
					radius : '45%',
					startAngle:270,
					 label: {
						normal: {
							show: true,
							formatter: '{b}:{c}.00元',
							textStyle:{
							   color:"#ccc"
							},
							position: '',
						}
					},
					center: ['50%', '50%'],
					data:[
						{   
							label:{
						        normal:{
						            textStyle:{
						                color:"#ccc"
						            }
						        }
						    },
							itemStyle:{
								normal:{
									color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
										offset: 0,
										color: '#ccc'
									}, {
										offset: 1,
										color: '#fff'
									}])
								}
							},
							value:0, 
							 name:'交易额'
							
						}		
					],
					itemStyle: {
						emphasis: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};
		myChartRatio.setOption(optionRatio);

	}else{
		//交易分析交易额比例情况饼状图，底下的大图
		optionRatio = {
			series : [
				
				{
					name: '',
					type: 'pie',
					radius : '45%',
					startAngle:80,
					 label: {
						normal: {
							show: true,
							formatter: '交易额\n{b}\n{c}元\n{d}%',
							textStyle:{
							   color:"#ccc"
							},
							position: '',
						}
					},
					center: ['50%', '50%'],
					data:[
						{   
							label:{
						        normal:{
						            textStyle:{
						                color:"#35d5f7"
						            }
						        }
						    },
							itemStyle:{
								normal:{
									color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
										offset: 0,
										color: '#35d5f7'
									}, {
										offset: 1,
										color: '#ade3ca'
									}])
								}
							},
							value:noGoodsAmt, 
							 name:'（无商品）'
							
						},
						{
							label:{
						        normal:{
						            textStyle:{
						                color:"#f77236"
						            }
						        }
						    },
							itemStyle:{
								normal:{
									color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
										offset: 0,
										color: '#fccf45'
									}, {
										offset: 1,
										color: '#ff7006'
									}])
								}
							},
							value:haveGoodsAmt,
							name:'（有商品）'
							
						}
					],
					itemStyle: {
						emphasis: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};
		if((noGoodsAmt/payedTradeAmt)<0.0001 && (noGoodsAmt/payedTradeAmt)>0){
			optionRatio.series[0].label.normal.formatter='交易额\n{b}\n{c}元\n0.01%';
		}else if((haveGoodsAmt/payedTradeAmt)<0.0001 && (haveGoodsAmt/payedTradeAmt)>0){
			optionRatio.series[0].label.normal.formatter='交易额\n{b}\n{c}元\n0.01%';
		}

		myChartRatio.setOption(optionRatio);
	}

	if(numTotal==0){  //订单量为0时
		//交易分析已收款订单量饼状图配置
		optionNR = {		
			series: [
				{
					name:'已收款',
					itemStyle: {
								normal: {
									color: '#5ec796'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{   
							label: {
								normal: {
									show:true,
									position: "center"
								}
							},
							value:0,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		myChartNR.setOption(optionNR);

		//交易分析待收款订单量饼状图配置
		optionNNA = {		
			series: [
				{
					name:'待收款',
					itemStyle: {
								normal: {
									color: '#5ec796'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{   
							label: {
								normal: {
									show:true,
									position: "center"
								}
							},
							value:0,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		myChartNNA.setOption(optionNNA);

		//交易分析已关闭订单量饼状图配置
		optionClosedNum = {		
			series: [
				{
					name:'已关闭',
					itemStyle: {
								normal: {
									color: '#f77236'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{   
							label: {
								normal: {
									show:true,
									position: "center"
								}
							},
							value:0,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		myChartClosedNum.setOption(optionClosedNum);

	}else{
		//交易分析已收款订单量饼状图配置
		optionNR = {		
			series: [
				{
					name:'已收款',
					itemStyle: {
								normal: {
									color: '#5ec796'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{
							
							value:payedOrderNum, 
							name:'50%',
							itemStyle:{
								normal:{
									color:"#35d5f7"
								}
							}
						},
						{   
							label: {
								normal: {
									show:false,
									position: "inside"
								}
							},
							value:nonPayOrderNum+closeOrderNum,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		if((payedOrderNum/numTotal)<0.0001 && (payedOrderNum/numTotal)>0){
			optionNR.series[0].data[0].value=1;
			optionNR.series[0].data[1].value=9999;
		}else if((nonPayOrderNum+closeOrderNum)/numTotal<0.0001 && (nonPayOrderNum+closeOrderNum)/numTotal){
			optionNR.series[0].data[0].value=9999;
			optionNR.series[0].data[1].value=1;
		}
		myChartNR.setOption(optionNR);

		//交易分析待收款订单量饼状图配置
		optionNNA = {		
			series: [
				{
					name:'待收款',
					itemStyle: {
								normal: {
									color: '#5ec796'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{
							
							value:nonPayOrderNum, 
							name:'50%',
							itemStyle:{
								normal:{
									color:"#ffcf3e"
								}
							}
						},
						{   
							label: {
								normal: {
									show:false,
									position: "inside"
								}
							},
							value:payedOrderNum+closeOrderNum,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		if((nonPayOrderNum/numTotal)<0.0001 && (nonPayOrderNum/numTotal)>0){
			optionNNA.series[0].data[0].value=1;
			optionNNA.series[0].data[1].value=9999;
		}else if((payedOrderNum+closeOrderNum)/numTotal<0.0001 && (payedOrderNum+closeOrderNum)/numTotal){
			optionNNA.series[0].data[0].value=9999;
			optionNNA.series[0].data[1].value=1;
		}
		myChartNNA.setOption(optionNNA);

		//交易分析已关闭订单量饼状图配置
		optionClosedNum = {		
			series: [
				{
					name:'已关闭',
					itemStyle: {
								normal: {
									color: '#f77236'
								}
							},
					type:'pie',
					label: {
						normal: {
							textStyle:{
								fontSize:13,
								fontWeight:"bolder"
							},
							formatter: '{d}%',
							position: 'center'
						}
					},
					radius: ['40%', '55%'],
					data:[
						{
							
							value:closeOrderNum, 
							name:'50%',
							itemStyle:{
								normal:{
									color:"#f77236"
								}
							}
						},
						{   
							label: {
								normal: {
									show:false,
									position: "inside"
								}
							},
							value:payedOrderNum+nonPayOrderNum,
							name:'',
							itemStyle:{
								normal:{
									color:"#eee"
								},
								emphasis:{
								    color:"#eee"
								}
							}
							
						}
					]
				}
			]
		};
		if((closeOrderNum/numTotal)<0.0001 && (closeOrderNum/numTotal)>0){
			optionClosedNum.series[0].data[0].value=1;
			optionClosedNum.series[0].data[1].value=9999;
		}else if((payedOrderNum+nonPayOrderNum)/numTotal<0.0001 && (payedOrderNum+nonPayOrderNum)/numTotal){
			optionClosedNum.series[0].data[0].value=9999;
			optionClosedNum.series[0].data[1].value=1;
		}
		myChartClosedNum.setOption(optionClosedNum);

	}
	
	if(payedOrderNum==0){ //有商品订单量为0时
		//交易分析交易额比例情况饼状图，底下的大图
		optionRatioNum = {
			series : [
				
				{
					name: '',
					type: 'pie',
					radius : '45%',
					startAngle:270,
					 label: {
						normal: {
							show: true,
							formatter: '{b}:{c}单',
							textStyle:{
							   color:"#ccc"
							},
							position: '',
						}
					},
					center: ['50%', '50%'],
					data:[
						{   
							label:{
						        normal:{
						            textStyle:{
						                color:"#ccc"
						            }
						        }
						    },
							itemStyle:{
								normal:{
									color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
										offset: 0,
										color: '#ccc'
									}, {
										offset: 1,
										color: '#fff'
									}])
								}
							},
							value:noGoodsNum, 
							 name:'订单量'
							
						}
					],
					itemStyle: {
						emphasis: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};
		myChartRatioNum.setOption(optionRatioNum);
	}else{
		//交易分析交易额订单量比例情况饼状图，底下的大图
		optionRatioNum = {
			series : [
				
				{
					name: '',
					type: 'pie',
					radius : '45%',
					startAngle:160,
					 label: {
						normal: {
							show: true,
							formatter: '交易额\n{b}\n{c}单\n{d}%',
							textStyle:{
							   color:"#5ec796"
							},
							position: '',
						}
					},
					center: ['50%', '50%'],
					data:[
						{   
							label:{
						        normal:{
						            textStyle:{
						                color:"#35d5f7"
						            }
						        }
						    },
							itemStyle:{
								normal:{
									color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
										offset: 0,
										color: '#35d5f7'
									}, {
										offset: 1,
										color: '#ade3ca'
									}])
								}
							},
							value:noGoodsNum, 
							 name:'（无商品）'
							
						},
						{
							label:{
						        normal:{
						            textStyle:{
						                color:"#f77236"
						            }
						        }
						    },
							itemStyle:{
								normal:{
									color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
										offset: 0,
										color: '#fccf45'
									}, {
										offset: 1,
										color: '#ff7006'
									}])
								}
							},
							value:haveGoodsNum,
							name:'（有商品）'
							
						}
					],
					itemStyle: {
						emphasis: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};
		if((noGoodsNum/payedOrderNum)<0.0001 && (noGoodsNum/payedOrderNum)>0){
			optionRatioNum.series[0].label.normal.formatter='交易额\n{b}\n{c}单\n0.01%';
		}else if((haveGoodsNum/payedOrderNum)<0.0001 && (haveGoodsNum/payedOrderNum)>0){
			optionRatioNum.series[0].label.normal.formatter='交易额\n{b}\n{c}单\n0.01%';
		}
		myChartRatioNum.setOption(optionRatioNum);
	}

	
}


/*给页面内各个金额和订单量赋值*/
function setAmount(data){
	var receiveAm = fmoney(data.datajson.payedTradeAmt,2);
	$("#receiveAm").html(receiveAm);
	var notRec =  fmoney(data.datajson.nonPayTradeAmt,2); 
	$("#notRec").html(notRec);
	var hasClose = fmoney(data.datajson.closeTradeAmt,2);
	$("#hasClose").html(hasClose);
	var payedOrderNum =	data.datajson.payedOrderNum;
	$("#receiveNum").html(payedOrderNum);
	var nonPayOrderNum = data.datajson.nonPayOrderNum;
	$("#notRecNum").html(nonPayOrderNum);
	var closeOrderNum = data.datajson.closeOrderNum;
	$("#hasCloseNum").html(closeOrderNum);
	var orderNum = data.datajson.orderNum;
	$("#orderNumAll").html(orderNum);
}

var swiper = new Swiper('.swiper-container');

