<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<base href="${CONTEXT }">
		<%@ include file="../pub/head.inc" %>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
		<meta http-equiv="description" content="This is my page"/>
		<title>报表详情</title>
		<!-- Highcharts -->
		<script src="${CONTEXT }js/highcharts/highcharts.js"></script>
		<!-- Highstock -->
		<%-- <script src="${CONTEXT }js/highstock/highstock.js"></script> --%>
		<!-- Highmaps -->
		<%-- <script src="${CONTEXT }js/highmaps/highmaps.js"></script> --%>
	</head>
	<body>
		<div style="text-align:center">
			<form id="searchForm" method="post">
			<c:if test="${reports.show > 0 }">
			时间：
			<input type="text" id="queryStartDate" name="queryStartDate" class="Wdate" readOnly="true" value="${queryMap.queryStartDate }"
				onFocus="WdatePicker({onpicked:function(){queryStartDate.focus();}, minDate:'#F{$dp.$D(\'queryEndDate\',{d:-29});}', maxDate:'#F{$dp.$D(\'queryEndDate\',{d:-6})||\'%y-%M-{%d-7}\'}'})" 
				onClick="WdatePicker({onpicked:function(){queryStartDate.focus();}, minDate:'#F{$dp.$D(\'queryEndDate\',{d:-29});}', maxDate:'#F{$dp.$D(\'queryEndDate\',{d:-6})||\'%y-%M-{%d-7}\'}'})">
			~
			<input type="text" id="queryEndDate" name="queryEndDate" class="Wdate" readOnly="true" value="${queryMap.queryEndDate }"
				onFocus="var day = getMinDate(); WdatePicker({onpicked:function(){queryEndDate.focus();}, minDate:'#F{$dp.$D(\'queryStartDate\',{d:6})}', maxDate:day})" 
				onClick="var day = getMinDate(); WdatePicker({onpicked:function(){queryEndDate.focus();}, minDate:'#F{$dp.$D(\'queryStartDate\',{d:6})}', maxDate:day})">&nbsp;&nbsp;
			</c:if>
			<c:if test="${reports.show == 2 or reports.show == 4 or reports.show == 5}">
			市场：
			<select id="market" name="market" editable="false" panelHeight="auto">
				<option value="">全部市场</option>
				<option value="1">武汉白沙洲农副产品大市场</option>
				<option value="2">广西玉林宏进农副产品大市场</option>
				<option value="3">洛阳宏进农副产品国际物流中心</option>
				<option value="4">徐州农副产品中心批发市场</option>
				<option value="5">开封宏进农副产品国际物流中心</option>
				<option value="6">濮阳宏进农副产品国际物流中心</option>
				<option value="7">黄石宏进农副产品国际物流中心</option>
				<option value="8">中国东盟（钦州）农产品大市场</option>
				<option value="9">淮安宏进农副产品国际物流中心</option>
				<option value="10">盘锦宏进农副产品国际物流中心</option>
				<option value="11">郴州宏进农副产品国际物流中心</option>
			</select>
			</c:if>
			<c:if test="${reports.show == 3  or reports.show == 4 or reports.show == 5}">
			用户类型：
			<select id="user" name="user" editable="false" panelHeight="auto">
				<option value="">全部用户类型</option>
				<!-- <option value="1">谷登农批</option>
				<option value="2">农速通</option>
				<option value="3">农商友</option>
				<option value="4">产地供应商</option>
				<option value="5">门岗</option> -->
			</select>
			</c:if>
			<c:if test="${reports.show == 5 }">
			来源：
			<select id="source" name="source" editable="false" panelHeight="auto">
				<option value="">全部来源</option>
				<!-- <option value="0">管理后台创建</option>
				<option value="1">web前端注册</option>
				<option value="2">农速通注册</option>
				<option value="3">农商友-买家版注册</option>
				<option value="4">农商友-卖家版注册</option>
				<option value="5">门岗添加用户</option>
				<option value="6">产地供应商注册</option> -->
			</select>
			</c:if>
			<c:if test="${reports.show > 0 }">
			<a class="easyui-linkbutton icon-search-btn" iconCls="icon-search" id="icon-detail-search">查询</a>
			</c:if>
			</form>
		</div>
		<br>
		<input type="hidden" id="reportsId" name="reportsId" value="${reports.id }"/>
		<div id="sumData" style="text-align:right;padding:5px;"></div>
		<div id="container" style="min-width:700px;height:400px"></div>
		<script>
			//生成queryEndDate的最大时间(取'queryStartTime+29天'和'昨天'中最小值)
			function getMinDate(){
			    var nowDate = new Date();
			    var preDateTimes = nowDate.getTime() - (24 * 60 * 60 * 1000); //昨天日期
			    
			    var resultDate;
			    var queryStartDateStr = $("#queryStartDate").val();
				if(queryStartDateStr == ''){
					resultDate = new Date(preDateTimes);
				}else{
					var arr = queryStartDateStr.split("-");
					var queryStartDate = new Date(arr[0], arr[1]-1, arr[2]);
					var queryStartDate29Times = queryStartDate.getTime() + (29 * 24 * 60 * 60 * 1000);//queryStartTime+29天
					if(preDateTimes < queryStartDate29Times){
						resultDate = new Date(preDateTimes)
					}else{
						resultDate = new Date(queryStartDate29Times)
					}
				}
				var year = resultDate.getFullYear();
				var month = resultDate.getMonth()+1;
				var day = resultDate.getDate();
				return year + "-" + month + "-" + day;
			}
			
			$(function () {
				ajaxRequest();
				$("#icon-detail-search").click(function(){
					ajaxRequest();
				});
			});
			
			function ajaxRequest(){
				var startTime = $("#queryStartDate").val();
				var endTime = $("#queryEndDate").val();
				if(startTime == '' && endTime != ''){
					alert("请选择开始时间");
					return false;
				}
				else if(startTime != '' && endTime == ''){
					alert("请选择结束时间");
					return false;
				}
				$.ajax({
					type:"post",
					url:"reports/getReportData",
					data:{
						reportsId:$("#reportsId").val(), 
						startTime: startTime,
						endTime: endTime,
						source:$("#source").val(),
						market:$("#market").val(),
						user:$("#user").val()
					},
					dataType:'json',
					async:false,
					success:function(data){	
						if(data.resultCode == 0){
							var sum = data.rows[0].sum;
							if(sum != undefined){
								$("#sumData").html(data.rows[0].sumDate+"天合计数据："+sum)
							}
							var chartType = data.rows[0].type;
							switch (chartType){
								case "1":
									lineChart(data.rows);
									break;
								case "2":
									stackBarChart(data.rows);
									break;
								case "3":
									lineBarChart(data.rows);
									break;
								case "4":
									muiltLineChart(data.rows);
									break;
								case "5":
									muiltBarChart(data.rows);
									break;
								case "6":
									stackBarChart(data.rows);
									break;
								case "7":
									stackBarChart(data.rows);
									break;
							}	
						}else{
							alert("获取数据异常");
						}
					}
				});
			}
			
			function lineChart(rows){
				var series = [];				
				var data = rows[0].data.data[0].data1;
				var serie = {};
				serie.name = data.parms;
				serie.color = data.color;
				serie.data = data.data;
				series.push(serie);
				
				var typeY = data.typeY;
				
				$('#container').highcharts({
			        chart: {
			            type: 'line'
			        },
			        title: {
			            text: rows[0].name
			        },
			        xAxis: {
			            categories: rows[0].data.parms
			        },
			        yAxis: {
			            title: {
			                text: ''
			            },
				        labels: {
				            formatter:function(){
				            	var val = this.value;
				            	var valStr;
				            	if(val >= 10000){
				            		valStr = Highcharts.numberFormat(val / 10000, 0, ".", ",") + "万";
				            	}else{
				            		valStr = val;
				            	}
				            	
				            	if(typeY == 1){
				            		return '￥'+ valStr;
				            	}
				            	return valStr;
				            }
				        }
			        },
			        tooltip: {
			            enabled: true,
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>'+(typeY == 1?'￥':'')+'{point.y} </b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        plotOptions: {
			            line: {
			                dataLabels: {
			                    enabled: true,
			                    formatter: function() {
			                    	var val = this.y;
			                    	var valStr;
					            	if(val >= 10000){
					            		valStr = Highcharts.numberFormat(val / 10000, 2, ".", ",") + "万";
					            	}else{
					            		valStr = val;
					            	}
					            	
					            	if(typeY == 1){
					            		return '￥'+ valStr;
					            	}
			                        return valStr;
			                    }
			                },
			                enableMouseTracking: true
			            }
			        },
			        credits: {
			            enabled: false
			        },
			        series: series
			    });
			} 
			
			/* 多条折线 */
			function muiltLineChart(rows){
				var series = [];				
				var reportDatas = rows[0].data.data[0];
				for(var k in reportDatas){
					var data = reportDatas[k].data;
					var serie = {};
					serie.name = reportDatas[k].parms;
					serie.color = reportDatas[k].color;
					serie.data = data;
					series.push(serie);
				}
				
				$('#container').highcharts({
			       /*  chart: {
			            type: 'line'
			        }, */
			        title: {
			            text: rows[0].name
			        },
			        xAxis: {
			            categories: rows[0].data.parms
			        },
			        yAxis: {
			            title: {
			                text: ''
			            },
				        labels: {
				            formatter:function(){
				            	var val = this.value;
				            	if(val >= 10000){
				            		return Highcharts.numberFormat(val / 10000, 0, ".", ",") + "万";
				            	}
				            	return val;
				            }
				        }
			        },
			        tooltip: {
			            enabled: true,
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>{point.y} </b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        plotOptions: {
			            line: {
			                dataLabels: {
			                    enabled: true,
			                    formatter: function() {
			                    	var val = this.y;
					            	if(val >= 10000){
					            		return Highcharts.numberFormat(val / 10000, 2, ".", ",") + "万";
					            	}else{
					            		return val;
					            	}
			                    }
			                },
			                enableMouseTracking: true
			            }
			        },
			        credits: {
			            enabled: false
			        },
			        series: series
			    });
			} 
			
			/* 堆砌柱状图 */
			function stackBarChart(rows){
				var series= []; 
				var reportDatas = rows[0].data.data[0];
				for(var k in reportDatas){
					var data = reportDatas[k].data;
					var serie = {};
					serie.name = reportDatas[k].parms;
					serie.data = data;
					serie.color = reportDatas[k].color;
					serie.stack = 'type';
					series.push(serie);
				}
				
				$('#container').highcharts({
					chart: {
					      type: 'column'
					},
			        title: {
			            text: rows[0].name
			        },
			        xAxis: {
			            categories: rows[0].data.parms
			        },
			        yAxis: {
			            allowDecimals: false,
			            min: 0,
			            title: {
			              text: ''
			            },
				        labels: {
				        	formatter:function(){
				            	var val = this.value;
				            	if(val >= 10000){
				            		return Highcharts.numberFormat(val / 10000, 0, ".", ",") + "万";
				            	}
				            	return val;
				            }
				        }     
			        },
			        tooltip: {
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>{point.y} </b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        },
			        credits: {
			            enabled: false
			        },
			        plotOptions:{
			            column: {
			                stacking: 'normal',
		                	dataLabels: {
		                        enabled: true,
		                        formatter: function() {
			                    	var val = this.y;
					            	if(val >= 10000){
					            		return Highcharts.numberFormat(val / 10000, 2, ".", ",") + "万";
					            	}else{
					            		return val;
					            	}
			                    }
		                	}
			             }
			        },
			        series: series
			    });
			}
			
			/* 条形+柱状图 */
			function lineBarChart(rows){
				var yAxis = [{ // Primary yAxis
		            labels: {
		            	formatter:function(){
			            	var val = this.value;
			            	if(val >= 10000){
			            		return Highcharts.numberFormat(val / 10000, 0, ".", ",") + "万";
			            	}
			            	return val;
			            },
		                style: {
		                    color: '#606060'
		                }
		            },
		            title: {
		                text: '柱状图坐标轴',
		                style: {
		                    color: '#606060'
		                }
		            }
		        }, { // Secondary yAxis
		            title: {
		                text: '折线图坐标轴',
		                style: {
		                    color: '#606060'
		                }
		            },
		            labels: {
		            	formatter:function(){
			            	var val = this.value;
			            	if(val >= 10000){
			            		return Highcharts.numberFormat(val / 10000, 0, ".", ",") + "万";
			            	}
			            	return val;
			            },
		                style: {
		                    color: '#606060'
		                }
		            },
		            opposite: true
		        }];
				
				var series = [];
				var reportDatas = rows[0].data.data[0];
				for(var k in reportDatas){
					var serie = {};
					var type =  reportDatas[k].type;
					if(type == 1){
						serie.type = 'spline';
						serie.yAxis = 1;
					}else if(type == 2){
						serie.type = 'column';
					}
					serie.name = reportDatas[k].parms;
					serie.data = reportDatas[k].data;
					serie.color = reportDatas[k].color;
					series.push(serie);
				}
				
			    $('#container').highcharts({                                          
			        chart: {                                                          
			        },                                                                
			        title: {                                                          
			        	text: rows[0].name                                     
			        },                                                                
			        xAxis: {                                                          
			        	categories: rows[0].data.parms
			        }, 
			        yAxis: yAxis,
			        tooltip: {
			            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			                '<td style="padding:0"><b>{point.y} </b></td></tr>',
			            footerFormat: '</table>',
			            shared: true,
			            useHTML: true
			        }, 
			        credits: {
			            enabled: false
			        },
			        series: series                                                             
			    });                    
			}
			
			/* 多条柱状 */
			function muiltBarChart(rows){
				var series = [];
				var reportDatas = rows[0].data.data[0];
				for(var k in reportDatas){
					var serie = {};
					serie.name = reportDatas[k].parms;
					serie.data = reportDatas[k].data;
					serie.color = reportDatas[k].color;
					series.push(serie);
				}
				 $('#container').highcharts({
				        chart: {
				            type: 'column'
				        },
				        title: {
				            text: rows[0].name
				        },
				        subtitle: {
				            text: ''
				        },
				        xAxis: {
				            categories: rows[0].data.parms
				        },
				        yAxis: {
				            min: 0,
				            title: {
				                text: ''
				            },
				            formatter:function(){
				            	var val = this.value;
				            	if(val >= 10000){
				            		return Highcharts.numberFormat(val / 10000, 0, ".", ",") + "万";
				            	}
				            	return val;
				            }
				        },
				        tooltip: {
				            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
				            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
				                '<td style="padding:0"><b>{point.y} </b></td></tr>',
				            footerFormat: '</table>',
				            shared: true,
				            useHTML: true
				        },
				        plotOptions: {
				            column: {
				                pointPadding: 0.2,
				                borderWidth: 0,
				                dataLabels: {
			                        enabled: true,
			                        formatter: function() {
				                    	var val = this.y;
						            	if(val >= 10000){
						            		return Highcharts.numberFormat(val / 10000, 2, ".", ",") + "万";
						            	}else{
						            		return val;
						            	}
				                    }
			                	}
				            }
				        },
				        credits: {
				            enabled: false
				        },
				        series: series
				    });
			}
		</script>
	</body>
</html>