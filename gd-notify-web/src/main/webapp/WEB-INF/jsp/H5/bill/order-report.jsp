<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>月账单</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${CONTEXT }v1.0/css/global.m.css">
		<link rel="stylesheet" href="${CONTEXT }v1.0/module/mui/dist/css/mui.min.css">
		<link rel="stylesheet" href="${CONTEXT }v1.0/css/order-report.css">
	</head>

	<body>		
		
		<!-- <header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href="javascript:reportGoBack()">返回</a>
			<h1 class="mui-title">月账单</h1>
		</header> -->
		<div class="mui-content">
			<!-- 按日 -->
			<div class="rep-wrap" searchType="1" data-detepicker="false">
				<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">
					<div class="mui-pull">
						<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>
						<div class="mui-pull-caption">正在加载...</div>
					</div>
				</div>
				<p class="error-tips" style='display:none;'>网络繁忙，请稍后重试...</p>				
				<canvas class="tempcsv" width="0" height="0"></canvas>
							    
				
				<div class="report-box" data-type="0" >
					<div class="main-pad mainpd-cor">
						<div class="rep-temt-w">
							<h2 class="rep-temt"><span class="cur-mon-txt"><span class="curre-mon"></span>月</span>总<span class="typespicelStr">售出额</span>：<span class="mon-total-d"><span class="mon-total-dt">0</span>万元</span></h2>
						</div>
					</div>
					<div class="main-pad mainpd-cor">
						<div class="rep-temt-w">
							<p class="unit-wrap">金额<span class="yaix-unit">/万元</span></p>
						</div>
					</div>
					<div class="main-pad rgraph-box">
		       			<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">
							<div class="mui-pull">
								<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>
								<div class="mui-pull-caption">正在加载...</div>
							</div>
						</div>
						<p class="no-data-tips">暂无数据</p>
						<div class="rep-temp">
							<div style="position: relative;" class="temp01-wrapper">
							    <canvas id="signlebar0" width="600" height="480" style="cursor: point;"></canvas>
							    
							</div>
						</div>
					</div>
					<div class="main-pad mainpd-cor">
						<div class="rep-temt-w">
							<p class="unit-wrap unit-wrap2 tar">日期</p>
						</div>
					</div>
					<div class="main-pad mainpd-cor">
						<div class="rep-temt-w">
							<p class="cvs-titt"><span class="cvs-tit-m"></span>月每7日<span class="typespicelStr">售出额</span></p>
						</div>
					</div>
				</div>
				
				<div class="report-box" data-type="1" >
					<div class="main-pad mainpd-cor">
						<div class="rep-temt-w">
							<h2 class="rep-temt"><span class="cur-mon-txt"><span class="curre-mon"></span>月</span>总订单量：<span class="mon-total-d"><span class="mon-total-dt">0</span>笔</span></h2>
						</div>
					</div>
					<div class="main-pad mainpd-cor">
						<div class="rep-temt-w">
							<p class="unit-wrap">订单<span class="yaix-unit">/笔</span></p>
						</div>
					</div>
					<div class="main-pad rgraph-box">
		       			<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">
							<div class="mui-pull">
								<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>
								<div class="mui-pull-caption">正在加载...</div>
							</div>
						</div>
						<p class="no-data-tips">暂无数据</p>
						<div class="rep-temp">
							<div style="position: relative;" class="temp01-wrapper">
							        <canvas id="signlebar1" width="600" height="480" style="cursor: default;"></canvas>
							    
							</div>
						</div>
					</div>
					<div class="main-pad mainpd-cor">
						<div class="rep-temt-w">
							<p class="unit-wrap unit-wrap2 tar">日期</p>
						</div>
					</div>
					<div class="main-pad mainpd-cor">
						<div class="rep-temt-w">
							<p class="cvs-titt"><span class="cvs-tit-m"></span>月每7日订单量</p>
						</div>
					</div>
				</div>
				
			</div>
			
		</div>
		<div class="fixed-tool">
			&nbsp;
			
		</div>
		<script src="${CONTEXT }v1.0/js/jquery-1.8.3.min.js"></script>
		<script src="${CONTEXT }v1.0/module/mui/dist/js/mui.min.js"></script>
    	
    	<script src="${CONTEXT }v1.0/module/RGraph/librariesmin/RGraph.common.core.js" ></script>
	    <script src="${CONTEXT }v1.0/module/RGraph/librariesmin/RGraph.common.dynamic.js" ></script>
	    <script src="${CONTEXT }v1.0/module/RGraph/librariesmin/RGraph.bar.js"></script>
	    <script src="${CONTEXT }v1.0/module/RGraph/librariesmin/RGraph.common.tooltips.js" ></script>
	    
		
		
		<script>
			
			
			
		    	
			/**取参数**/
			function getUrldata(){
				var searchStr = window.location.search.substr(1),
		            arr = searchStr.split("&"),
		            regObj = {};
		        for(var i = 0 ;i<arr.length;i++){
		            regObj[arr[i].split("=")[0]] = arr[i].split("=")[1]
		        }
		        return regObj;
			}
			var getUrlmenu = getUrldata();
			
			if(getUrlmenu.channelType!=undefined){
				if(getUrlmenu.channelType==1){
					$(".typespicelStr").text("采购额");
				}else if(getUrlmenu.channelType==2){
					$(".typespicelStr").text("售出额");
				}
			} 
			
			/**首次请求**/
			(function(){
				
				requestajax("${CONTEXT}billDetail/queryMonthAmountList",{account:getUrlmenu.account,payTime:getUrlmenu.payTime,channelType:getUrlmenu.channelType},0)
				requestajax("${CONTEXT}billDetail/queryMonthOrderList",{account:getUrlmenu.account,payTime:getUrlmenu.payTime,channelType:getUrlmenu.channelType},1)

				//requestajax(CONTEXT+"/reports/getReports",{menuId:getUrlmenu.menuId},1,0,{'first':1})
				

			})();
			function messageGoBack(){
				mui.openWindow({
					/*id:'detail',*/
					url:"${CONTEXT }billDetail/showBill?account="+getUrlmenu.account+"&channelType="+getUrlmenu.channelType
				});
		
			}
			
			
			/**请求数据**/			
			function requestajax(url,datas,num){
				$.ajax({
					type:"get",
					url:url,
					dataType:'json',
					data:datas,
					//async:false,
					success:function(data){
						/*var rows = data.rows,
							menuLength=0;*/
						$(".rep-wrap:visible").addClass("randering");
						$(".rep-wrap:visible").find(".loading-box").eq(0).removeClass("mui-visibility");						
						$(".report-box").eq(num).show();
							
							analysisData(data,num);							
						//}
						
					},
					error:function(){
						$(".error-tips").show()
					}
				});	
			}
			
			function daysInMonth(month,year) {
			    return new Date(year, month, 0).getDate();
			}
			
			/**扩展**/
			Array.prototype.insertItem = function (index, item) {  
			  this.splice(index, 0, item);  
			}; 
			
			/**分析数据**/
			function analysisData(data,num){
				//var randerBoxarr = [];
				//randeMontharr
				//var searchType = $(".rep-wrap:visible").attr("searchType");
				var $boxcvs = $(".report-box");
				var $totalnum = $boxcvs.eq(num).find(".mon-total-dt");
				var $curreMon = $boxcvs.eq(num).find(".curre-mon");
				var $cvstitm = $boxcvs.eq(num).find(".cvs-tit-m");
				var loadingwrap = $boxcvs.eq(num).find(".loading-box");
				var $nodata = $boxcvs.eq(num).find(".no-data-tips");
				var $cvsboxa = $boxcvs.eq(num).find(".rep-temp");
				initcavW($("#signlebar"+num));
				if(data){
					if(data.datajson.length>0){
						var randerlist = data.datajson;
						if(randerlist[0].sumAmount==0||randerlist[0].sumOrder==0){
							loadingwrap.removeClass("mui-visibility");
							$cvsboxa.hide();
							$nodata.show()
						}else{
							$nodata.hide();
							$cvsboxa.show();
							
							var arrtemp = {};
							var arrparms = ["1-7号","8-14号","15-21号","22-28号","29-31号"];
							var arrMonth = [];
							var arrData = [];
							var mmonthstr = getUrlmenu.payTime.substr(4);
							var months = mmonthstr;
							var yearstr = getUrlmenu.payTime.substr(0,4);
							var curMonthrdays = daysInMonth(months,yearstr);
							var arrobjdata = randerlist;
							var keyname = num==0?"sumAmount":"sumOrder";
							if(mmonthstr.indexOf("0")==0){
								mmonthstr = mmonthstr.replace(/0/,'');
							}
							$totalnum.text(num==0?randerlist[0].sumAmount:randerlist[0].sumOrder);
							$curreMon.text(mmonthstr);
							$cvstitm.text(mmonthstr);
							 
							var arrlene = (function(){
								if(curMonthrdays == 28){
									return 5;
								}else if(curMonthrdays >28){
									return 6
								}
							})();
							for(var a = 0,len2 = arrlene; a<len2; a++){
								if(randerlist[a]==undefined||randerlist[a].period!=a){
									var objtemp = {};
									objtemp.period = a;
									objtemp[keyname] = 0;
									arrobjdata.insertItem(a,objtemp)
								}
							}
							console.log(arrobjdata)
							for(var i = 1,len = randerlist.length; i<len; i++){
								var period = randerlist[i].period-1;
								if(period<4){
									arrMonth.push(arrparms[period]);
								}else if(period=4){
									if(curMonthrdays == 29){
										arrMonth.push("29号");
									}else if(curMonthrdays == 30){
										arrMonth.push("29-30号");
									}else if(curMonthrdays == 31){
										arrMonth.push(arrparms[period]);
									}
								}
								
								arrData.push(num==0?randerlist[i].sumAmount:randerlist[i].sumOrder)
							}
							arrtemp.data = arrData;						
							arrtemp.parms = arrMonth;
							
							/**绘图**/
							loadingwrap.removeClass("mui-visibility");
							initSignlebar(num, arrtemp);
							$(".bodylock").css({"height":$("body").height()})
						}
						
						
					}else{
						$(".loading-box").eq(0).removeClass("mui-visibility");
						$nodata.show();
					}
					
				}
				
			}
			
			
			
			
			function initcavW(cvs){
				var $parents =  cvs.parents(".report-box"),
					$cvsmainWrap = $parents.find(".cvsmain-wrap"),
					mainWidth = $parents.find(".rep-temp").width();
					$lockmark = $("<div class='lockmark'>&nbsp;</div>");
					
					cvs.attr('width',mainWidth);
					cvs.attr('height',400);
					$lockmark.css({"width":mainWidth,'height':400});
					cvs.before($lockmark);
				
			};
			
			
				
				
				
				
				
				
				function myFormatter(obj, num){	
					var objId = obj.id
		
		        	if(num>=10000 && num < 10000000){		        		
		        		/* if(objId.match(/\d+/)[0]==7){
		        			
		        		}else{ */
		        			return Math.round((num /10000) * 100) / 100 +"万"
		        		/* } */		        		
		        	}else if(num>=10000000 && num < 100000000){
		        		return Math.round((num /10000000) * 100) / 100 +"千万"
		        	}else if(num>=100000000){
		        		return Math.round((num /100000000) * 100) / 100 +"亿"
		        	}else{
		        		return num
		        	}
		        }
				
				
		       
		        function initSignlebar(num,data){
			        var colorarr = ["#59c895","#ec8f77"],
			        	abovecolor = ["#ff6c00","#74b8e7"],
			        	cvsele = $('#signlebar'+num),
			        	cvswidth = cvsele.attr("width")-0,
			        	hmrginl = (cvswidth-40-10)/data.data.length/2-10;
			        var maxnum = Math.max.apply(null,data.data);//最大值
			        var barsign = new RGraph.Bar({
			            id: 'signlebar'+num,
			            data: data.data,			            
			            options: {
			                gutterTop: 25,
			                gutterLeft: 40,
			                gutterRight: 10,
			                colors: [colorarr[num]],
			                labels: data.parms,
			                shadow:false,
			                ylabelsCount: 5,
			                /* scaleDecimals:2,
			                ymax:0.1,*/
			                backgroundGridAutofitNumhlines: 5, 
			                hmargin:hmrginl,
			                noyaxis: true,
			                noxaxis: true,
			                labelsAbove: data.data,
			                textSize: 10,
			                labelsAboveSize:14,//数据size
			                labelsAboveColor:abovecolor[num],
			                strokestyle: 'rgba(0,0,0,0)',
			                backgroundGridVlines: false,//不显示网格y
			                backgroundGridBorder: false,//不显示网格边框
			                backgroundGridDashed:true,//虚线网格
			                textColor: '#888',
			                labelsAboveDecimals:num==0?2:0
			            }
			        });
			        if(num==0&&maxnum<1){
			        	barsign.set("ylabelsCount",5)
			        	.set("scaleDecimals",2)
			        	.set("ymax",1)
			        	.set("background.grid.autofit.numhlines",5)
			        	.set("ylabelsSpecific",[1,0.8,0.6,0.4,0.2,0])
			        	.draw();
			        }else if(num==1&&maxnum<=5){
			        	
			        	barsign.set("background.grid.autofit.numhlines",5)
			        	.set("ymax",5)
			        	.set("ylabelsSpecific",[5,4,3,2,1,0])
			        	.draw()
			        }else{
			        	barsign.set("background.grid.autofit.numhlines",5)
			        	.draw()
			        }
		        }
		        
		        $(".bodylock").on("click",function(e){
		        	e.preventDefault();
		        	   e.stopPropagation();
		        	   $(this).off('click');
		        	
		        	
		        	
		        	
		        	//alert(e.target);
		        	
		        });
			   	
			   	
		    //})();
			
	       
		</script>
	</body>

</html>
