<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<base href="${CONTEXT}" >
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="utf-8">
		<title>报表详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="v1.0/module/mui/examples/hello-mui/css/mui.css">
		<link rel="stylesheet" href="v1.0/css/report-det.css">
		<link rel="stylesheet" href="v1.0/js/plugs/mobiscroll/mobiscroll.custom.css">
	</head>

	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-icon mui-icon-left-nav mui-pull-left" href="/H5/workbench/main"></a>
			<a class="mui-icon mui-pull-right repmain-inf"><span id="reporttotal">0</span>个报表</a>
			<h1 class="mui-title">${menu.menuName }</h1>
		</header>
		<nav class="mui-bar mui-bar-tab">
			<a id="defaultTab" class="mui-tab-item" href="tab-webview-subpage-about.html">
				<span class="mui-icon mui-icon-workp"></span>
				<span class="mui-tab-label" style="color: #007aff;">工作台</span>
			</a>
			<a class="mui-tab-item" id="messageBtn" href="tab-webview-subpage-chat.html">
				<span class="mui-icon mui-icon-msg"><span class="mui-badge"></span></span>
				<span class="mui-tab-label">消息</span>
			</a>
			<a class="mui-tab-item" id="userCenterBtn"  href="tab-webview-subpage-contact.html">
				<span class="mui-icon mui-icon-mine"></span>
				<span class="mui-tab-label">我的</span>
			</a>
		</nav>
		
		<div class="mui-content">
			<div class="rep-wrap">
				<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">
					<div class="mui-pull">
						<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>
						<div class="mui-pull-caption">正在加载...</div>
					</div>
				</div>
				<p class="no-data-tips">暂无数据</p>
				<p class="error-tips">网络繁忙，请稍后重试...</p>
				<p class="mui-upd-date">最后更新时间：<span class="updataDate">2016-03-10</span></p>
			</div>
		</div>
		<script src="v1.0/module/mui/examples/hello-mui/js/mui.min.js"></script>
		<script>
			var menu = null,
				main = null;
			var showMenu = false;
			mui.init({
				swipeBack: false 
			});
			
			//敲击顶部导航，内容区回到顶部
			document.querySelector('header').addEventListener('doubletap', function() {
				main.children()[0].evalJS('mui.scrollTo(0, 100)');
			});
			
			
				//处理右上角关于图标的点击事件；
			
			
			//首页返回键处理
			//处理逻辑：1秒内，连续两次按返回键，则退出应用；
			getUnReadData('${systemLoginUser.userID }',$(".mui-badge"));
			
		</script>
		<script src="v1.0/js/dialog.js"></script>
		<script src="v1.0/js/jquery-1.8.3.min.js"></script>
    	<script src="v1.0/js/plugs/mobiscroll/mobiscroll.custom.min.js"></script>
    	
    	<script src="v1.0/module/RGraph/librariesmin/RGraph.common.core.js" ></script>
	    <script src="v1.0/module/RGraph/librariesmin/RGraph.common.dynamic.js" ></script>
	    <script src="v1.0/module/RGraph/librariesmin/RGraph.bar.js"></script>
	    <script src="v1.0/module/RGraph/librariesmin/RGraph.common.effects.js"></script>
	    <script src="v1.0/module/RGraph/librariesmin/RGraph.common.tooltips.js" ></script>
	    <script src="v1.0/module/RGraph/librariesmin/RGraph.drawing.yaxis.js" ></script>
	    <script src="v1.0/module/RGraph/librariesmin/RGraph.line.js" ></script>
	    
	    <!--环形图-->
	    <script src="v1.0/module/RGraph/librariesmin/RGraph.common.key.js"></script>
		<script src="v1.0/module/RGraph/librariesmin/RGraph.pie.js"></script>
		
		
		<script>
			var firstreq = true,
				randerBoxarr = [];
			//mui初始化
			mui.init({
				swipeBack: false //启用右滑关闭功能
			});
//			mui('.mui-scroll-wrapper').scroll();
//			mui('body').on('shown', '.mui-popover', function(e) {
//				//console.log('shown', e.detail.id);//detail为当前popover元素
//			});
//			mui('body').on('hidden', '.mui-popover', function(e) {
//				//console.log('hidden', e.detail.id);//detail为当前popover元素
//			});
//			
			
//			/*****/
			/* function orient() {
		        //alert('gete');
		        var locations = window.location.href;
		        if (window.orientation == 0 || window.orientation == 180) {
		            //$("body").attr("class", "portrait");
		            //window.location.href = locations
		            $("body").removeClass('landscape').addClass("portrait");
		            orientation = 'portrait';//纵向
		            return false;
		        }
		        else if (window.orientation == 90 || window.orientation == -90) {		        	
		        	if(!$('body').hasClass('landscape')){
		        		window.location.href = locations;
		        	}		        	
		            $("body").removeClass('portrait').addClass("landscape");
		            orientation = 'landscape';
		   
		            return false;
		        }
		    }
		   
		   
		    $(function(){
		        orient();
		    });
		   
		   
		    $(window).bind( 'orientationchange', function(e){
		        orient();
		    }); */
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
			console.log(getUrlmenu.menuId);
			
			function getDays(strDateStart,strDateEnd){
			   	var strSeparator = "-"; //日期分隔符
			   	var oDate1;
			   	var oDate2;
			   	var iDays;
			   	oDate1= strDateStart.split(strSeparator);
			   	oDate2= strDateEnd.split(strSeparator);
//			   	var strDateS = new Date(oDate1[0] + "-" + oDate1[1] + "-" + oDate1[2]);
//			   	var strDateE = new Date(oDate2[0] + "-" + oDate2[1] + "-" + oDate2[2]);
			   	var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
  				var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
			   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数 
			   return iDays ;
			}
			var labelsArr = [],
				dataArr = [];
			
			/**首次请求**/
			(function(){
				var nowdata = new Date(),
				timeminSeconds = nowdata.getTime(),
				starDatemSeconds = timeminSeconds-7*24*60*60*1000,
				starDateT = new Date(starDatemSeconds),
				starDateM = starDateT.getMonth()<9?'0'+(starDateT.getMonth()+1):starDateT.getMonth()+1,
				starDateD = starDateT.getDate()<10?'0'+starDateT.getDate():starDateT.getDate(),
				endDateM = nowdata.getMonth()<9?'0'+(nowdata.getMonth()+1):nowdata.getMonth()+1,
				endDateD = nowdata.getDate()<10?'0'+nowdata.getDate():nowdata.getDate(),
				starDateval = starDateT.getFullYear()+'-'+starDateM+'-'+starDateD,
				endDateval =nowdata.getFullYear()+'-'+endDateM+'-'+endDateD;
				
				//requestajax("/reports/getReports",{menuId:getUrlmenu.menuId,startTime:starDateval,endTime:endDateval},1)
				requestajax("/reports/getReports",{menuId:getUrlmenu.menuId},1)
				//requestajax("v1.0/js/report2Data.json",{menuId:getUrlmenu.menuId},1)
			})();
			
			
			//日期转换方法
			var timeConver = function(){
				this.newT = new Date();
				this.day = 0;
				this.nowTime = this.newT.getFullYear() +"-"+ (this.newT.getMonth()+1) +"-"+ this.newT.getDate();//默认时间
				this.weekDay=new Array("日", "一", "二", "三", "四", "五", "六");
			};
			timeConver.prototype= {
				//计算转换开始时间和离开时间日期
				defalutTime: function(nowTime, day){
					var s = this.converTime(nowTime)
					var myDate = new Date(Date.parse(s))
					//var myDate = new Date(Date.UTC(nowTime.split("-")[0], nowTime.split("-")[1]-1, nowTime.split("-")[2]))
					var d = myDate.getDate();
					if(arguments.length==2){
						d = d - day;	
					}else{
						d = d - this.day;
					}
					myDate.setDate(parseInt(d));
					var month = myDate.getMonth()+1;
					var day = myDate.getDate();
					function dc(str){
						var tmp = str;
						if(str.toString().length<2){
							tmp =  "0" + str;
						}
						return tmp;
					}
					var endTime = myDate.getFullYear() +"-"+ dc(month) +"-"+ dc(day);
					return endTime
				},
				//转换年月日星期
				dateConver: function(inst){
					inst=inst.split('-');
					var self = this;
				    var tmp=new Date(inst[0],parseInt(inst[1]-1),inst[2]);
					return {
						week:function(){
							var w = self.weekDay[tmp.getDay()];
							return w;
						},
						day:function(){
							var d = inst[2];
							return d;
						},
						month:function(){
							var m = inst[1];
							return m;
						},
						monthWeekDay:function(){
							var tmpDate = inst[1]+"月"+ inst[2] + "日（周" + self.weekDay[tmp.getDay()] + "）";
							return tmpDate;
						},
						yearMonthDay:function(){
							var tmpDate = inst[1]+"月"+ inst[2] + "日（周" + self.weekDay[tmp.getDay()] + "）";
							return tmpDate;
						}
					};
				},
				//转换天数
				converDay: function(day){
					return Math.abs(day/1000/60/60/24);
				},
				//转换日期
				converTime: function(strTime){
					var s = strTime.replace(/\-/g,",").split(",");
					var time = new Date(Date.parse(s[1] + "/"+s[2]+"/"+s[0]+""));
			    	return time;
			    },
			  //转换日期2014,04,04
				/* converTime: function(strTime){
					var s = strTime.replace(/\-/g,",").split(",");
					var time = new Date(Date.parse(s[1] + "/"+s[2]+"/"+s[0]+""));
			    }, */
			    
			    //转换成今天明天后天
			    conver3Day: function(time){
				    var tmp = this.converTime(time)-this.converTime(this.nowTime);
				    var zhTmp = ["今天","明天","后天"];
				    var dd = this.converDay(tmp);
				    var reslut;
				    if(dd<3){
				    	reslut = zhTmp[dd];
					}else{
						//var tmpTime = new Date(time);
						var tmpTime = this.converTime(time);
						var zhTmp = this.weekDay[tmpTime.getDay()];
						reslut =  "周" + zhTmp;
					}
					return reslut;
			    },
			    converDate: function(msecinds){
				    var timess = new Date(msecinds);
				    var years = timess.getFullYear();
				    var months = timess.getMonth()>=9?timess.getMonth()+1:'0'+(timess.getMonth()+1);
				    var dates = timess.getDate()>=10?timess.getDate():'0'+timess.getDate();
				    var reslut = years+'-'+months+'-'+dates;
					return reslut;
			    }
			};
			/**声明日历控件**/
			
			function initDatepicker2(id){
 				var setDate = (function(){
				var newT = new Date();//不包括今天
				var newTmsciends = newT.getTime()-1*1000*60*60*24;//不包括今天
				var nowTime = newT.getFullYear() +"-"+ (newT.getMonth()+1) +"-"+ newT.getDate();//默认时间
				var nowConver = new timeConver();
				var dafendDate = nowConver.converDate(newTmsciends)
				var tmpTime = nowTime;
				var s_e_Day = 28;//开始和结速这间28天
				var mDay = 29;//最大天数30天
				var selStartTime = $("#selStartDate"+id);
				var selEndTime = $("#selEndDate"+id);
				var startTime = $("#startDate"+id);
				var endTime = $("#endDate"+id);
				//var m = 1;
				/* selStartTime.text(nowConver.dateConver(nowTime).monthWeekDay()); *///默认开始时间
				
				var nd = nowConver.defalutTime(nowTime,7);//获取结束时间
				selStartTime.text(nd);
				selEndTime.text(dafendDate);//写入转换结束时间月日星期
				startTime.val(nd);
				endTime.val(dafendDate);
				
				var startTimeobj =  startTime.mobiscroll().date({
				    theme: 'android-ics light', //皮肤样式
				    display: 'modal', //显示方式 
				    mode: 'scroller', //日期选择模式
				    dateFormat: 'yy-mm-dd', // 日期格式
				    setText: '确定', //确认按钮名称
				    cancelText: '取消',//取消按钮名称
				    dateOrder: 'yymmdd', //面板中日期排列格式
				    dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
				    showLabel:true,
					/* minDate: new Date(nowConver.converTime(nowConver.defalutTime(nowTime,mDay-1))), */
					maxDate: new Date(nowConver.converTime(nowConver.defalutTime(dafendDate,6))),
					lang: 'zh',
				    preset: 'date',
				    animate: "pop",
				    onSelect:function(valueText,inst){
				        tmpTime = valueText;
				        startTime.val(valueText);
						selStartTime.text(valueText);//写入开始年月日
						//selEndTime.text(nowConver.dateConver(nowConver.defalutTime(valueText,nowConver.day)).monthWeekDay());//写入月、日、星期
				        var tmp;
						if(endTime.val()!=''){
							tmp =nowConver.converTime(endTime.val())- nowConver.converTime(valueText);
							var betownDay = nowConver.converDay(tmp)
							if(betownDay>=30){
								$("#endDate"+id).val(nowConver.defalutTime(valueText,'-29'));
								selEndTime.text(nowConver.defalutTime(valueText,'-29'));
								endTime.mobiscroll('init', {
									minDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,'-6'))),
									maxDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,'-'+mDay)))
								});//修改结束时间配置 
							}else{
								/* $("#endDate"+id).val(nowConver.defalutTime(valueText,'-'+betownDay));
								selEndTime.text(nowConver.defalutTime(valueText,'-'+betownDay)); */
								endTime.mobiscroll('init', {
									minDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,'-'+betownDay))),
									maxDate: new Date(nowConver.converTime(nowConver.defalutTime(dafendDate)))
								});//修改结束时间配置 
							}
						}else{
							endTime.mobiscroll('init', {
								minDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,'-6'))),
								maxDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,'-'+mDay)))
							});
						}
						
						var $parentDom = $("div[data-id='"+id+"']");
				        var menuId = $parentDom.attr("data-menuId");
				        requestajax("/reports/getReports",{menuId:menuId,startTime:valueText,endTime:endTime.val(),reportsId:id},2)
				    }
				});
				var endTimeobj = endTime.mobiscroll().date({
				    theme: 'android-ics light', //皮肤样式
				    display: 'modal', //显示方式 
				    mode: 'scroller', //日期选择模式
				    dateFormat: 'yy-mm-dd', // 日期格式
				    setText: '确定', //确认按钮名称
				    cancelText: '取消',//取消按钮名名称
				    dateOrder: 'yymmdd', //面板中日期排列格式
				    dayText: '日', monthText: '月', yearText: '年', //面板中年月日文字
				    minDate: new Date(nowConver.converTime(nowConver.defalutTime(startTime.val(),'-'+6))),
				    //maxDate: new Date(nowConver.converTime(nowConver.defalutTime(nowTime,27))),
					maxDate: new Date(nowConver.converTime(nowConver.defalutTime(startTime.val(),'-'+6))), 
					lang: 'zh',
				    preset: 'date',
				    animate: "pop",
				    onSelect:function(valueText){
				        var tmp = nowConver.converTime(valueText) - nowConver.converTime(tmpTime);
				        var endDay = nowConver.converDay(tmp)//计算天数
						endTime.val(valueText);
				        selEndTime.text(valueText);
				        
				        if(startTime.val()!=''){
							tmp =nowConver.converTime(startTime.val())- nowConver.converTime(valueText);
							var betownDay = nowConver.converDay(tmp)
							if(betownDay>=30){
								startTime.val(nowConver.defalutTime(valueText,29));
								selStartTime.text(nowConver.defalutTime(valueText,29));
								startTime.mobiscroll('init', {
									/* minDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,'-6'))), */
									maxDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,mDay)))
								});//修改结束时间配置 
							}else if(betownDay>=6){
								startTime.val(nowConver.defalutTime(valueText,betownDay));
								selStartTime.text(nowConver.defalutTime(valueText,betownDay));
								startTime.mobiscroll('init', {
									/* minDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,'-6'))), */
									maxDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,betownDay)))
								});//修改结束时间配置 
							}else{
								startTime.val(nowConver.defalutTime(dafendDate,6));
								selStartTime.text(nowConver.defalutTime(dafendDate,6));
							}
						}else{
							startTime.mobiscroll('init', {
								//minDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,'-6'))),
								maxDate: new Date(nowConver.converTime(nowConver.defalutTime(valueText,mDay)))
							});
						}
				        var $parentDom = $("div[data-id='"+id+"']");
				        var menuId = $parentDom.attr("data-menuId");
				        requestajax("/reports/getReports",{menuId:menuId,startTime:startTime.val(),endTime:valueText,reportsId:id},2)
				    }
				})
			
				
			})()
			}
			/**请求数据**/			
			function requestajax(url,datas,num){
				$.ajax({
					type:"get",
					url:url,
					dataType:'json',
					data:datas,
//					async:false,
					success:function(data){
						var rows = data.rows;
//						for(var y = 0;y<data.rows.length;y++){
//							var dataresult = data.rows[y];
//							for(var i = 0;i<dataresult.proBszbankList.length;i++){
//								labelsArr.push(dataresult.proBszbankList[i].datatimes);
//								dataArr.push(dataresult.proBszbankList[i].orderNumbers)
//							}
//						}
//						var dataresult = data.rows.proBszbankList;
						
						//$(".totalday").text(dataresult.length);
						if(num==1){
							$('#reporttotal', window.parent.document).html(data.size);
							$(".updataDate").text(data.currentTime);
							analysisData(data);							
						}else if(num==2){
							/**处理第二次请求(点击日历控件触发)***/
							var objdataS = {};
							for(var n = 0,len = rows.length; n<len; n++){
								var datalisttemp = rows[n].data;
								var datalistid = rows[n].id;
								objdataS[datalistid] = datalisttemp;
							}
							checkChart(rows[0].type,rows[0].id,objdataS);
							/**更新模板数据**/
							updatakTemp(rows[0]);
						}
						
					},
					error:function(){
						$(".error-tips").show()
					}
				});	
			}
			
			/**更新模板数据**/
			function updatakTemp(data){
				var $ele = $("div[data-id='"+data.id+"']");
				var sumDate = data.sumDate!=undefined?data.sumDate:null;
				var sum = data.sum!=undefined?data.sum:null;
				
				if(sum!=null&&sumDate!=null){
					$ele.find(".totaldata").text(sum);
					$ele.find(".totalday").text(sumDate)
				}else{
					$ele.find(".rep-toal").hide();
				}
			}
			
			/**分析数据**/
			function analysisData(data){
				if(data){
					if(data.rows.length>0){
						var randerlist = data.rows;
						var heightArr = [];
						var objData = {};
						for(var i = 0,len = randerlist.length; i<len; i++){
							//randerBoxarr.push(randerlist[i].id);
							var obj = {};
							var onj2 = {};
							var datalistid = randerlist[i].id;
							var datalisttemp = randerlist[i].data;
							obj.id = randerlist[i].id;
							obj.type = randerlist[i].type;
							obj.menuId = randerlist[i].menuId;
							obj.graphName = randerlist[i].name;//图表title
							obj.show = randerlist[i].show;//显示tool
							obj.sum = randerlist[i].sum!=undefined?randerlist[i].sum:null;//合计
							obj.sumDate = randerlist[i].sumDate;
							obj.offsetT = null;
							randerBoxarr.push(obj);
							checkTemp(randerlist[i].type,randerlist[i].id,randerlist[i].menuId,randerlist[i].name,randerlist[i].show,randerlist[i].sum,randerlist[i].sumDate);
							objData[datalistid] = datalisttemp;
	//						objData[datalistid][xais] = randerlist[i].data.parms[0].parms
						}
	//					for(var a = 0,len = randerlist.length; i<len; i++){
	//						
	//					}
						$(".mui-upd-date").show().find(".updataDate").text()
						for(var x = 0,len2 = randerBoxarr.length;x<len2; x++){
							var $domdiv = $("div[data-id='"+randerBoxarr[x].id+"']");
							$(".loading-box").eq(0).removeClass("mui-visibility");
							$domdiv.show();
	//						$("div[data-id='"+randerBoxarr[x].id+"']")
							/* initDatepicker($domdiv); */
							var offsetT1 = $domdiv.offset().top;
							//heightArr.push(offsetT);
							randerBoxarr[x].offsetT = offsetT1
						}
	//					alert(1);
	//					triggerWinScroll(heightArr,randerBoxarr);
						triggerWinScroll(randerBoxarr,objData)	
					}else{
						$(".loading-box").eq(0).removeClass("mui-visibility");
						$(".no-data-tips").show();
					}
					
				}
				
			}
			function checkTemp(type,idFlag,menuId,graphName,show,sum,sumDate){
				switch (type){
					case "1":
						initlineTemp(idFlag,menuId,graphName,show,sum,sumDate);
					break;
					case "2":
						initBarinbarTemp(idFlag,menuId,graphName,show,sum,sumDate);
					break;
					case "3":
						initHybridBlTemp(idFlag,menuId,graphName,show,sum,sumDate)
					break;
					case "4":
						initMuiltlineTemp(idFlag,menuId,graphName,show,sum,sumDate)
					break;
					case "5":
						initMuiltbarTemp(idFlag,menuId,graphName,show,sum,sumDate)
					break;
					case "6":
						initBaronbarTemp(idFlag,menuId,graphName,show,sum,sumDate)
					break;
					case "7":
						initMarketDonutTemp(idFlag,menuId,graphName,show,sum,sumDate)
					break;
				}
			}
			
			/**判断某元素是否进入可视区域**/
			function isElementInViewport (el) {				
			    //special bonus for those using jQuery
			    if (typeof jQuery === "function" && el instanceof jQuery) {
			        el = el[0];
			    }
				var W = window.innerWidth || document.documentElement.clientWidth;
    			var H = window.innerHeight || document.documentElement.clientHeight;
//  			var ele = this.elements[i];
			    var rect = el.getBoundingClientRect();
			    return (
			    	rect.top >= 0&& rect.top<= H 
			    	
			    )
			}
			
			/**滚动触发逻辑**/
			function triggerWinScroll(randerBoxarr,data){
//				for(var z = 0,len3 = randerBoxarr.length;z<len3;z++){
//					if(randerBoxarr[z+1]!=undefined){
////							if(scrollTop+winHeight>=randerBoxarr[z].offsetT&&scrollTop+winHeight<randerBoxarr[z+1].offsetT){
//						//if(scrollTop+winHeight>=randerBoxarr[z].offsetT){
//							checkChart(randerBoxarr[z].type,randerBoxarr[z].id)
//						//}
//					}else{
//						checkChart(randerBoxarr[len3-1].type,randerBoxarr[len3-1].id)
//					}
//					
//				}				
				$(window).bind("scroll", function() {
					var scrollTop = $(window).scrollTop();
					var winHeight = $(window).height();
					for(var z = 0,len3 = randerBoxarr.length;z<len3;z++){
						//if(randerBoxarr[z+1]!=undefined){
//							if(scrollTop+winHeight>=randerBoxarr[z].offsetT&&scrollTop+winHeight<randerBoxarr[z+1].offsetT){
//							if(scrollTop+winHeight>=randerBoxarr[z].offsetT){
//								checkChart(randerBoxarr[z].type,randerBoxarr[z].id)
//							}
						if(!$("div[data-id='"+randerBoxarr[z].id+"']").hasClass("cvsRandered")){
							if(isElementInViewport($("div[data-id='"+randerBoxarr[z].id+"']"))){
								initDatepicker2(randerBoxarr[z].id);
								checkChart(randerBoxarr[z].type,randerBoxarr[z].id,data)
							}
//						}else{
//							checkChart(randerBoxarr[len3-1].type,randerBoxarr[len3-1].id)
						}
						
					}
				});
				$(window).trigger("scroll");
			}
			/**分析类型**/
			function checkChart(type,id,data){
//				if(!$("div[data-id='"+id+"']").hasClass("cvsRandered")){
//					inintChart(type)
					switch (type){
						case "1":							
							initLine(id,data[id]);
						break;
						case "2":
							initBarinbar(id,data[id]);
						break;
						case "3":
							initHybridBl(id,data[id])
						break;
						case "4":
							initMuiltline(id,data[id])
						break;
						case "5":
							initMuiltbar(id,data[id])
						break;
						case "6":
							initBaronbar(id,data[id])
						break;
						case "7":
							initMarketDonut(id,data[id])
						break;
//						case "8":
//							initMarketDonut(id,data)
//						break;
					}
//				}
			}
			
			function initcavW(datalab,cvs,typeLen,longnum,flag){
				var $parents =  cvs.parents(".report-box"),
					$cvsmainWrap = $parents.find(".cvsmain-wrap"),
					mainWidth = $parents.find(".rep-temp").width(),
					$xais = $parents.find("canvas:first"),
					$xais2 = $xais.next("canvas"),
//					xaisW = xais.width(),
					$cvsmain = $parents.find("canvas:last"),					
					maincvsW = (mainWidth == 0?$(window).width()-40:mainWidth-40),
					fixwidth = typeLen!=undefined?20*typeLen:20;//hmargin定为20
					//maincvsW = mainWidth-20;
				if(datalab.length>7){
					cvs.attr('width',parseInt((maincvsW/7+fixwidth)*(datalab.length)));
					$cvsmainWrap.css({'width':parseInt((maincvsW/7+fixwidth)*(datalab.length)),'z-index':9,'position':'relative','cursor': 'default'});
					cvs.attr('height',400);
					/* cvs.css({
						"width":(maincvsW/7+20)*(datalab.length),
						
					}); */
					//$("#cvs").width(maincvsW/7*(dataArr.length-7)+maincvsW);
					$xais.attr("height",cvs.height())
					/*$xais.css({
						 "height":cvs.height(), 
						'max-height':'29.5rem'
					});*/
					$xais2.attr("height",cvs.height())
					/* $xais2.css({
						'max-height':'29.5rem'
					}); */
				}else{
					cvs.attr('width',maincvsW);
					$cvsmainWrap.css({'width':maincvsW,'z-index':9,'position':'relative','cursor': 'default'});
					cvs.attr('height',400);
					/* cvs.css({
							"width":maincvsW+'px',
							'min-width':'27rem',
							//'max-width':80*datalab.length+'px'
							}); */
					$xais.attr("height",cvs.height())
					/* $xais.css({
						'max-height':'29.5rem'
					}); */
					$xais2.attr("height",cvs.height())
					/* $xais2.css({
						'max-height':'29.5rem'
					}); */
				}
				var initlongnum = parseInt(longnum).toString();
				if(longnum!=undefined&&flag=='￥'){
					
					if(initlongnum.length>2){
						$xais.attr('width',40+12*(initlongnum.length-2)+12+3)
					}
				}else if(longnum!=undefined&&flag==undefined){
					$xais.attr('width',40+12*(initlongnum.length-2))
				}else if(longnum!=undefined&&flag==''){
					if(initlongnum.length>2){
						$xais.attr('width',40+12*(initlongnum.length-2))
					}
					
				}
			};
			
			
				
				//console.log(labelsArr);
				var maincvsW = $(".rep-temp").width()-40;
//					mainWidth = $(".rep-temp").width();
//					winWidth = parseInt($(window).width());				
				
				/**格式化数据**/
//				function formatdataList(dataObj){
//					
//				}
				/***重绘前清除画布***/
				function clearCvs (ele){
//					if (typeof jQuery === "function" && ele instanceof jQuery) {
//				        ele = ele[0];
//				    }
					var eleObj = ele[0].getContext('2d');
					var cvsW = ele.width(); var cvsH = ele.height();
					eleObj.clearRect(0, 0, cvsW, cvsH);
				}
				function clearAndRedraw (obj)
				{
				    RGraph.clearAnnotations(obj.canvas);
				    RGraph.clear(obj.canvas);
				    obj.draw();
				}
				
//				
				function initprop(ele,idFlag){				
					var markpop,userTypepop,userFrompop;
					ele.find(".checkmark").bind('click',function(){
						var $self = $(this),
							$checkmark = ele.find(".checkmark"),
							curMark = $checkmark.attr("data-mak"),
							curMarkTxt = $checkmark.text();
						markpop = layer.open({
						    content: '<div class="csrolloutter">'+
						    			'<a href="javascript:;" class="layer-close-ico"></a>'+
						    			'<div class="mui-scroll">'+
											'<div class="pop-txt-cur">当前市场：全国市场</div>'+
											'<ul class="mui-table-view cus-market">'+
												'<li class="mui-table-view-cell table-view-cell'+idFlag+' table-view-cell-cur" data-mak="0"><a href="javascript:;">全部市场</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="1"><a href="javascript:;">玉林宏进农副产品大市场</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-cell'+idFlag+'"  data-mak="2"><a href="javascript:;">武汉白沙洲农副产品大市场</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="3"><a href="javascript:;">洛阳宏进农副产品国际物流中心</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="4"><a href="javascript:;">徐州农副产品中心批发市场</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="5"><a href="javascript:;">开封宏进农副产品国际物流中心</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="6"><a href="javascript:;">濮阳宏进农副产品国际物流中心</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="7"><a href="javascript:;">黄石宏进农副产品国际物流中心</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="8"><a href="javascript:;">中国东盟（钦州）农产品大市场</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="9"><a href="javascript:;">淮安宏进农副产品国际物流中心</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="10"><a href="javascript:;">盘锦宏进农副产品国际物流中心</a>'+
												'</li>'+
												'<li class="mui-table-view-cell table-view-disabled table-view-cell'+idFlag+'" data-mak="11"><a href="javascript:;">郴州宏进农副产品国际物流中心</a>'+
												'</li>'+
											'</ul>'+
										'</div>'+
									'</div>',
						    //btn: ['确认', '取消'],
						    shadeClose: false,
						    style: 'width:100%;'
//						    yes: function(){
//						        layer.open({content: '你点了确认', time: 1});
//						    }, no: function(){
//						        layer.open({content: '你选择了取消', time: 1});
//						    }
						});
						$(".table-view-cell"+idFlag).each(function(){
							var $self = $(this),
								dataMark = $self.attr("data-mak");
							if(curMark == dataMark){
								$self.addClass("table-view-cell-cur").siblings().removeClass("table-view-cell-cur");
								$self.parents("ul").siblings("div").text('当前市场：'+curMarkTxt);
							}
						})
						$('body').delegate( '.table-view-cell'+idFlag,'click', function(e) {
							//console.log($(this).attr('data-mak'))
							var dataMark = $(this).attr('data-mak'),
								dataMarkTxt =$(this).text(),
								requestData = {},
								startTime = ele.find(".startDate").val(),
								endTime = ele.find(".endDate").val();
							if(!$(this).hasClass("table-view-disabled")){
							$(this).addClass("table-view-cell-cur").siblings().removeClass("table-view-cell-cur");
							$(this).parents("ul").siblings("div").text('当前市场：'+dataMarkTxt);
							$self.find(".markepopRes").text(dataMarkTxt);
							$self.attr("data-mak",dataMark);
							layer.close(markpop);
							}
//							markpop.close();

							requestData.menuId = ele.attr("data-menuId");
							requestData.startTime = startTime;
							requestData.endTime = endTime;
							requestData.reportsId = idFlag;
							if(ele.find(".checkmark:visible").length>0){
								requestData.markId = ele.find(".checkmark:visible").attr("data-mak");
							}
							if(ele.find(".checkUsertype:visible").length>0){
								requestData.usertype = ele.find(".checkUsertype:visible").attr("data-user");
							}
							if(ele.find(".checkUserfrom:visible").length>0){
								requestData.userfrom = ele.find(".checkUserfrom:visible").attr("data-userfrom");
							}
							if(!$(this).hasClass("table-view-disabled")){
								requestajax("/reports/getReports",requestData,2)
							}
							
						});
					});
					
					ele.find(".checkUsertype").bind('click',function(){
						var $self2 = $(this),
							$checkUsertype = ele.find(".checkUsertype"),
							curUsertype = $checkUsertype.attr("data-user"),
							curUsertypeTxt = $checkUsertype.text();
						userTypepop = layer.open({
						    content: '<div class="csrolloutter csrolloutter2">'+
						    			'<a href="javascript:;" class="layer-close-ico"></a>'+
						    			'<div class="mui-scroll">'+
											'<div class="pop-txt-cur">当前用户类型：全部类型</div>'+
											'<ul class="mui-table-view cus-market">'+
												'<li class="mui-table-view-cell  table-view-cell-cur usertype-cell'+idFlag+'" data-usertype="0"><a href="javascript:;">全部类型</a>'+
												'</li>'+
												'<li class="mui-table-view-cell usertype-cell'+idFlag+'" data-usertype="1"><a href="javascript:;">个人用户</a>'+
												'</li>'+
												'<li class="mui-table-view-cell usertype-cell'+idFlag+'" data-usertype="2"><a href="javascript:;">企业用户</a>'+
												'</li>'+
											'</ul>'+
										'</div>'+
									'</div>',
//						    btn: ['确认', '取消'],
						    shadeClose: false,
//						    yes: function(){
//						        layer.open({content: '你点了确认', time: 1});
//						    }, no: function(){
//						        layer.open({content: '你选择了取消', time: 1});
//						    }
						});
						$(".usertype-cell"+idFlag).each(function(){
							var $selfusertype = $(this),
								dataMark = $selfusertype.attr("data-usertype");
							if(curUsertype == dataMark){
								$selfusertype.addClass("table-view-cell-cur").siblings().removeClass("table-view-cell-cur");
								$selfusertype.parents("ul").siblings("div").text('当前用户类型：'+curUsertypeTxt);
							}
						})
						$('body').delegate('.usertype-cell'+idFlag, 'click', function(e) {
							//console.log($(this).attr('data-mak'))
							var dataUser = $(this).attr('data-usertype'),
								dataUsertypeTxt =$(this).text(),
								requestData = {},
								startTime = ele.find(".startDate").val(),
								endTime = ele.find(".endDate").val();
							$(this).addClass("table-view-cell-cur").siblings().removeClass("table-view-cell-cur");
							$(this).parents("ul").siblings("div").text('当前用户类型：'+dataUsertypeTxt);
							$self2.find(".userpopRes").text(dataUsertypeTxt);
							$self2.attr("data-user",dataUser);
							layer.close(userTypepop);
//							markpop.close();

							requestData.menuId = ele.attr("data-menuId");
							requestData.startTime = startTime;
							requestData.endTime = endTime;
							requestData.reportsId = idFlag;
							if(ele.find(".checkmark:visible").length>0){
								requestData.markId = ele.find(".checkmark:visible").attr("data-mak");
							}
							if(ele.find(".checkUsertype:visible").length>0){
								requestData.usertype = ele.find(".checkUsertype:visible").attr("data-user");
							}
							if(ele.find(".checkUserfrom:visible").length>0){
								requestData.userfrom = ele.find(".checkUserfrom:visible").attr("data-userfrom");
							}
							requestajax("/reports/getReports",requestData,2)
							
						});
					});
					ele.find(".checkUserfrom").bind('click',function(){
						var $self3 = ele.find(".checkUserfrom"),
							$checkUserfrom = ele.find(".checkUserfrom"),
							curUserfrom = $checkUserfrom.attr("data-userfrom"),
							curUserfromTxt = $checkUserfrom.text();
//							0管理后台，1谷登农批网，2农速通，3农商友，4农商友-农批商，5 农批友，6供应商 
						userFrompop = layer.open({
						    content: '<div class="csrolloutter csrolloutter2">'+
						    			'<a href="javascript:;" class="layer-close-ico"></a>'+
						    			'<div class="mui-scroll">'+
											'<div class="pop-txt-cur">当前来源：全部来源</div>'+
											'<ul class="mui-table-view cus-market">'+
												'<li class="mui-table-view-cell  table-view-cell-cur userfrom-cell'+idFlag+'" data-userfrom="7"><a href="javascript:;">全部来源</a>'+
												'</li>'+
												'<li class="mui-table-view-cell userfrom-cell'+idFlag+'" data-userfrom="0"><a href="javascript:;">管理后台</a>'+
												'</li>'+
												'<li class="mui-table-view-cell userfrom-cell'+idFlag+'" data-userfrom="1"><a href="javascript:;">谷登农批网</a>'+
												'</li>'+
												'<li class="mui-table-view-cell userfrom-cell'+idFlag+'" data-userfrom="2"><a href="javascript:;">农速通</a>'+
												'<li class="mui-table-view-cell userfrom-cell'+idFlag+'" data-userfrom="3"><a href="javascript:;">农商友</a>'+
												'<li class="mui-table-view-cell userfrom-cell'+idFlag+'" data-userfrom="4"><a href="javascript:;">农商友-农批商</a>'+
												'<li class="mui-table-view-cell userfrom-cell'+idFlag+'" data-userfrom="5"><a href="javascript:;">农批友</a>'+
												'<li class="mui-table-view-cell userfrom-cell'+idFlag+'" data-userfrom="6"><a href="javascript:;">供应商</a>'+
												'</li>'+
											'</ul>'+
										'</div>'+
									'</div>',
//						    btn: ['确认', '取消'],
						    shadeClose: false,
//						    yes: function(){
//						        layer.open({content: '你点了确认', time: 1});
//						    }, no: function(){
//						        layer.open({content: '你选择了取消', time: 1});
//						    }
						});
						$(".userfrom-cell"+idFlag).each(function(){
							var $self = $(this),
								userfrom = $self.attr("data-userfrom");
							if(curUserfrom == userfrom){
								$self.addClass("table-view-cell-cur").siblings().removeClass("table-view-cell-cur");
								$self.parents("ul").siblings("div").text('当前用户注册来源：'+curUserfromTxt);
							}
						})
						$('body').delegate('.userfrom-cell'+idFlag, 'click', function(e) {
							//console.log($(this).attr('data-mak'))
							var datauserfrom = $(this).attr('data-userfrom'),
								dataUserfromkTxt =$(this).text(),
								requestData = {},
								startTime = ele.find(".startDate").val(),
								endTime = ele.find(".endDate").val();
							$(this).addClass("table-view-cell-cur").siblings().removeClass("table-view-cell-cur");
							$(this).parents("ul").siblings("div").text('当前用户注册来源：'+dataUserfromkTxt);
							$self3.find(".frompopRes").text(dataUserfromkTxt);
							$self3.attr("data-userfrom",datauserfrom);
							layer.close(userFrompop);
//							markpop.close();
							
							
							requestData.menuId = ele.attr("data-menuId");
							requestData.startTime = startTime;
							requestData.endTime = endTime;
							requestData.reportsId = idFlag;
							if(ele.find(".checkmark:visible").length>0){
								requestData.markId = ele.find(".checkmark:visible").attr("data-mak");
							}
							if(ele.find(".checkUsertype:visible").length>0){
								requestData.usertype = ele.find(".checkUsertype:visible").attr("data-user");
							}
							if(ele.find(".checkUserfrom:visible").length>0){
								requestData.userfrom = ele.find(".checkUserfrom:visible").attr("data-userfrom");
							}
							requestajax("/reports/getReports",requestData,2)
						});
					});
					$("body").delegate('.laymshade',"click",function(e){
				        var target  = $(e.target);
				        if(target.closest(".layermanim").length == 0){
				        	layer.closeAll();
				   		}
				    }); 
					$("body").delegate('.layer-close-ico',"click",function(e){
				        layer.closeAll();
				   		
				    }); 
					
				}
				/**修改选择模板***/
				function initTooltemp(ele,show){
					var $checkmarkW = ele.find(".checkmark").parents(".check-t-wrap"),//市场
						$checkUsertypeW = ele.find(".checkUsertype").parents(".check-t-wrap"),//用户类型
						$checkUserfromW = ele.find(".checkUserfrom").parents(".check-t-wrap");//用户来源
					if(show!='5'){
						switch (show){
							case "1":
								$checkmarkW.hide();
								$checkUsertypeW.hide();
								$checkUserfromW.hide();
								break;
							case "2":
								$checkUsertypeW.hide();
								$checkUserfromW.hide();
								break;
							case "3":
								$checkmarkW.hide();
								$checkUserfromW.hide();
								break;
							case "4":
								$checkUserfromW.hide();
								break;
						}	
					}
					
				}
				/**拷贝数组**/
				/* function cloneArr(arr) { 					
					var array2 = arr.concat(); 
					return array2;
				} */
				function cloneArr(obj) {
		            var out = [],i = 0,len = obj.length;
		            for (; i < len; i++) {
		                if (obj[i] instanceof Array){
		                    out[i] = cloneArr(obj[i]);
		                }
		                else out[i] = obj[i];
		            }
		            return out;
		        }
				/**转化万**/
				function formatLennum(arr,maxnum){
					var arrTemp = cloneArr(arr);
					for(var a = 0,len = arrTemp.length;a<len;a++){
						if(arrTemp[a].length&&arrTemp[a].length>=1){
							for(var e = 0,len2 = arrTemp[a].length;e<len2;e++){
								//if(arrTemp[a][e].toString().length>=5){
									arrTemp[a][e] = Math.round((arrTemp[a][e] /10000) * 100) / 100 ;//保留两位小数
								//}
								
							}
						}else{
							if(maxnum.toString().length>=5){
								arrTemp[a] = Math.round((arrTemp[a] /10000) * 100) / 100 ;//保留两位小数
							}	
						}
											
					}
					return arrTemp;
				}
				/**生成单条折线模板**/
				function initlineTemp(idFlag,menuId,graphName,show,sum,sumDate){
					var strdis = (function(){
						if(sum==null){
							return "display:none"
						}
					})();
					var temp = '<div class="report-box" data-type="1" data-id="'+idFlag+'" data-menuId="'+menuId+'">'+						
						'<div class="main-pad mainpd-cor">'+
							'<div class="rep-temt-w">'+
								'<h2 class="rep-temt">'+graphName+'</h2>'+
								'<p class="rep-toal" style="'+strdis+'"><span class="totalday">'+sumDate+'</span>天合计数据：<span class="totaldata">'+sum+'</span></p>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad date-tool">'+
							'<div class="rep-temt-date">'+
								'<span class="input-tit"><span class="input-titinner">开始时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="startDate'+idFlag+'">'+
									'<span id="selStartDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="开始时间" value="" class="startDate dn" id="startDate'+idFlag+'">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+						
								'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="endDate'+idFlag+'">'+
									'<span id="selEndDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="结束时间" value="" class="endDate dn" id="endDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+
							'</div>'+
							'<div class="rep-temt-date rep-temt-ot">'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">市场选择</span>'+
									'<a class="checkmark" href="javascript:;" data-mak="0">'+
										'<span class="markepopRes">全部市场</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">类型选择</span>'+
									'<a class="checkUsertype" href="javascript:;" data-user="0">'+
										'<span class="userpopRes">全部用户</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">来源选择</span>'+
									'<a class="checkUserfrom" href="javascript:;" data-userfrom="7">'+
										'<span class="frompopRes">全部来源</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad rgraph-box">'+
							'<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">'+
								'<div class="mui-pull">'+
									'<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>'+
									'<div class="mui-pull-caption">正在加载...</div>'+
								'</div>'+
							'</div>'+
							'<p class="no-data-tips">暂无数据</p>'+
							'<div class="rep-temp">'+
								'<div style="position: relative;" class="temp01-wrapper">'+
								    '<canvas id="axes'+idFlag+'" width="41" height="480" style="position: absolute;  left: 0; z-index: 3; cursor: default;"></canvas>'+
								    '<div style="width: 600px; overflow-x: auto;overflow-y: hidden; position: absolute; left: 41px;cursor: default;" class="temp01-wrap">'+
								    	'<div class="cvsmain-wrap cvsmain1-bg">'+
								        '<canvas id="cvs'+idFlag+'" width="1000" style="cursor: default;"></canvas>'+
								        '</div>'+
								    '</div>'+
								'</div>'+
							'</div>'+
						'</div>'+
					'</div>';
					$(".rep-wrap").append(temp);
					initTooltemp($("div[data-id='"+idFlag+"']"),show);
					initprop($("div[data-id='"+idFlag+"']"),idFlag);
					/* $('#cvs'+idFlag).mouseover(function(event){
						event.preventDefault();
					}); */
					
				}
				/**单条折线(type=1)**/
				function initLine(id,datalist){
					$("div[data-id='"+id+"']").find(".rep-temp").show().end().find(".no-data-tips").hide();
					var datalistTemp = datalist.data[0];
					var labelArr1 = [];
					var labelArr2 = [];
					var colorstr = '';
//					var typegraph = 1;
//					var typeY = 1;
					var parms = datalist.parms;
					var line = null;
					var yaxis = null;
					var typeY = 0;//1,金额；2,普通数字
					var typeY2 = 0;//1,金额；2,普通数字
					var typeYstr = [];
					
					var boolFlag = [];
					var boolFlags = 0;
					for(var j in datalistTemp){
						//console.log(datalistTemp[j])
						var jsonData = datalistTemp[j];
						if(datalistTemp[j].data.length<=0){
							boolFlag.push(0)
						}else{
							boolFlag.push(1)
						}
						if(datalistTemp[j].typeY=='1'){
							typeY = datalistTemp[j].typeY;
						}else if(datalistTemp[j].typeY=='2'){
							typeY2 = datalistTemp[j].typeY;
						}
						for(var l in jsonData){
//							if(jsonData[l]){
								//console.log(jsonData[l]);
								labelArr1 = jsonData.data;
								colorstr = jsonData.color;
//								typegraph = jsonData.type;
//								typegraph = jsonData.typeY;
//							}
						}
					}
					for(var k=0,lenk = boolFlag.length; k<lenk; k++){
						if(boolFlag[k]==0){
							boolFlags+=1;
						}
					}
					if(boolFlags==boolFlag.length){
						$("div[data-id='"+id+"']").find(".no-data-tips").show().next().hide()
												.end().prev(".loading-box").removeClass("mui-visibility");
						return false;
					}
					//			        // Update the HTML counter - this is totally optional
//			        document.getElementById("num_updates").innerHTML =
//			            parseInt(document.getElementById("num_updates").innerHTML) + 1;
//			
//			        RG.clear(document.getElementById("cvs"+id));
//			        RG.clear(document.getElementById("axes"+id));
					
					if(typeY!=0&&typeY==1){
						typeYstr.push('￥')
					}
					if(typeY2!=0&&typeY2==2){
						typeYstr.push('')
					}
					/**清除canvas**/
					if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
						var cvss = document.getElementById("cvs"+id);
						var axess = document.getElementById("axes"+id);
						RGraph.Reset(cvss);
				        RGraph.Reset(axess);
//				        $("div[data-id='"+id+"']").find(".muiltLineKey"+id).html('')
					}
					$("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
					var maxInNumbers = Math.max.apply(Math, labelArr1); //获取最大值
					/**格式化label**/
					var labelArrFor;
					labelArrFor = formatLennum(labelArr1,maxInNumbers);
					/* if(maxInNumbers.toString().length>=5){ */
					var	labelsunit = maxInNumbers.toString().length>=5?'万':'';
					var firstArrvalL = labelArrFor[0].toString().length;
					var lastArrvalL = labelArrFor[labelArrFor.length-1].toString().length;
					var gutterL =  firstArrvalL>2?(firstArrvalL-2)*6+12:0+24;
					var gutterR =  lastArrvalL>2?(lastArrvalL-2)*12+24:0+24;//右填充要补全单位子
					/* } */
					
					var maxLabelArrFor = Math.max.apply(Math, labelArrFor);
					var maxLabelArrForStr = parseInt(maxLabelArrFor).toString();
					//initcavW(labelArr1,$("#cvs"+id),'',maxLabelArrFor,typeYstr[0]);
					initcavW(labelArr1,$("#cvs"+id),'',maxLabelArrFor,'');
					//var ygutterLeft = maxLabelArrForStr.length>2&&typeYstr[0]=='￥'?60:40
					var ygutterLeft = maxLabelArrForStr.length>2&&typeYstr[0]=='￥'?40:40
					/* var gutterL =  maxInNumbers.toString().length>5? */
					line = new RGraph.Line({
			            id: 'cvs'+id,
			            data: labelArrFor,
			            options: {
			                labels: datalist.parms,
			                tooltips: labelArrFor,
			                tooltipsCoordsPage: true,
			                //noxaxis: true,
			                noyaxis: true,
			                ylabels: false,
			                colors: ['#888888'],
	//		                ylabelsCount: 8,
			                vmargin: 30,
			                gutterTop:60,//上填充
			                gutterLeft:gutterL,//左填充
			                gutterRight:gutterR,//右填充 
			                gutterBottom:50,//上填充
	//		                shadow: false,
							//labelsAboveSpecific:labelArrFor,//特殊label
			                labelsAbove: true,
			                labelsAboveUnitsPost:labelsunit,
			                labelsAboveSize:8,//折线数据size
			                labelsAboveColor:colorstr,
			                labelsAboveBackground:'transparent',//labelsAbove的背景
			                labelsAboveBorder:false,//labelsAbove的边框
			                backgroundGridDashed:true,//虚线网格
	//		                yaxispos:'right',
			                colors: [colorstr],
			                linewidth: 1,//折线宽度
			                backgroundGridVlines: false,//不显示网格y
			                backgroundGridBorder: false,//不显示网格边框
			                textSize: 8,//全局字体大小
			                textColor: '#888',
			                //backgroundGrid: false		                
			                tickmarks: 'filledcircle',
			            }
			        }).draw();
					yaxis = new RGraph.Drawing.YAxis({
			            id: 'axes'+id,
//								id: idx,
						textSize: 8,//全局字体大小
			            x: ygutterLeft,
			            options: {
			            	
			                max: line.max,
//									max:d1,
			                colors: ['#888888'],
			                gutterTop:60,//上填充
			                gutterBottom:50,//上填充
			                gutterLeft:150,//左填充
			                gutterRight:40,
			                //unitsPre:typeYstr[0],
			                unitsPost:labelsunit,
			                textSize: 8
			                /* ylabelsSpecific:labelArrFor, */
	//		                numyticks: 10,//步长
			            }
			        }).draw();
			        $("#"+yaxis.id).parents('.report-box').addClass('cvsRandered');
			        //$('.temp01-wrap').width($(this).parents(".report-box").find("canvas:first").width());
			        initWidth($("#cvs"+id));
				}
				
				
		        
		        /**生成多条折线模板**/
		       	function initMuiltlineTemp(idFlag,menuId,graphName,show,sum,sumDate){
		       		var strdis = (function(){
						if(sum==null){
							return "display:none"
						}
					})();
		       		var temp = '<div class="report-box" data-type="4" data-id="'+idFlag+'" data-menuId="'+menuId+'">'+
						'<div class="main-pad mainpd-cor">'+
							'<div class="rep-temt-w">'+
								'<h2 class="rep-temt">'+graphName+'</h2>'+
								'<p class="rep-toal" style="'+strdis+'"><span class="totalday">'+sumDate+'</span>天合计数据：<span class="totaldata">'+sum+'</span></p>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad date-tool">'+
							'<div class="rep-temt-date">'+
								'<span class="input-tit"><span class="input-titinner">开始时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="startDate'+idFlag+'">'+
									'<span id="selStartDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="开始时间" value="" class="startDate dn" id="startDate'+idFlag+'">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+						
								'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="endDate'+idFlag+'">'+
									'<span id="selEndDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="结束时间" value="" class="endDate dn temp-input" id="endDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+
								'</span>'+
							'</div>'+
							'<div class="rep-temt-date rep-temt-ot">'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">市场选择</span>'+
									'<a class="checkmark" href="javascript:;" data-mak="0">'+
										'<span class="markepopRes">全部市场</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">类型选择</span>'+
									'<a class="checkUsertype" href="javascript:;" data-user="0">'+
										'<span class="userpopRes">全部用户</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">来源选择</span>'+
									'<a class="checkUserfrom" href="javascript:;" data-userfrom="7">'+
										'<span class="frompopRes">全部来源</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad rgraph-box">'+
			       			'<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">'+
								'<div class="mui-pull">'+
									'<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>'+
									'<div class="mui-pull-caption">正在加载...</div>'+
								'</div>'+
							'</div>'+
							'<p class="no-data-tips">暂无数据</p>'+
							'<div class="rep-temp">'+
								'<div style="position: relative;" class="temp01-wrapper">'+
								    '<canvas id="muiltLineaxes'+idFlag+'" width="40" height="480" style="position: absolute;  left: 0; z-index: 3; cursor: default;"></canvas>'+
								    '<div style="width: 600px; overflow-x: auto;overflow-y: hidden; position: absolute; left: 41px" class="temp01-wrap">'+
								    	'<div class="cvsmain-wrap">'+
								        '<canvas id="muiltLinecvs'+idFlag+'" width="1000" height="480" style="cursor: default;"></canvas>'+
								        '</div>'+
								    '</div>'+
								    '<div class="barinbarKey" id="muiltLineKey'+idFlag+'"></div>'+
								'</div>'+
							'</div>'+
						'</div>'+
					'</div>';	
					$(".rep-wrap").append(temp);
					initTooltemp($("div[data-id='"+idFlag+"']"),show);
					initprop($("div[data-id='"+idFlag+"']"),idFlag);
		       	}
		        /**多条折线(type=4)***/
		        function initMuiltline(id,datalist){
		        	$("div[data-id='"+id+"']").find(".rep-temp").show().end().find(".no-data-tips").hide();
		        	var datalistTemp = datalist.data[0];
		        	var parms = datalist.parms;
		        	var muiltlineData = {}
				   		muiltlineData.groupline = [];
				   		muiltlineData.muiltDate = parms;
				   	
//					var labelArr1 = [];
					var labelArr2 = [];//图例
					var colorsarr = [];
					var typegraph = 1;
					var typeY = 0;//1,金额；2,普通数字
					var typeY2 = 0;//1,金额；2,普通数字
					var typeYstr = [];
					var boolFlag = true;//判断数据是否为空
					
					var boolFlag = [];
					var boolFlags = 0;
					
					for(var j in datalistTemp){
						//console.log(datalistTemp[j])
						var jsonData = datalistTemp[j];
						if(datalistTemp[j].data.length<=0){
							boolFlag.push(0)
						}else{
							boolFlag.push(1)
						}
						muiltlineData.groupline.push(datalistTemp[j].data);
//						"data1": {"data":[10,9,80,70,30,56,40,80,10,10],"color":"red","type":"1","typeY":"1"}"{\"data\":[10,9,80,70,30,56,40,80,10,10],\"color\":\"red\",\"type\":\"1\",\"typeY\":\"1\"}"
						colorsarr.push(datalistTemp[j].color);
						if(datalistTemp[j].parms){
							labelArr2.push(datalistTemp[j].parms);
						}
						if(datalistTemp[j].typeY=='1'){
							typeY = datalistTemp[j].typeY;
						}else if(datalistTemp[j].typeY=='2'){
							typeY2 = datalistTemp[j].typeY;
						}
//						labelArr2.push()
						for(var l in jsonData){
//							if(jsonData[l]){
//								console.log(jsonData[l]);
//								if(l = "data"){
//									muiltlineData.groupline.push(jsonData.data)
//								}
								
//								colorstr = jsonData.color;
								
								typegraph = jsonData.type;
								typegraph = jsonData.typeY;
//							}
//							switch (jsonData[l]){
//								case value:
//									break;
//								default:
//									break;
//							}
						}
					}
					
					for(var k=0,lenk = boolFlag.length; k<lenk; k++){
						if(boolFlag[k]==0){
							boolFlags+=1;
						}
					}
					if(boolFlags==boolFlag.length){
						$("div[data-id='"+id+"']").find(".no-data-tips").show().next().hide()
												.end().prev(".loading-box").removeClass("mui-visibility");
						return false;
					}
					
					
					if(typeY!=0&&typeY==1){
						typeYstr.push('￥')
					}
					if(typeY2!=0&&typeY2==2){
						typeYstr.push('')
					}
					
					var muiltlineData2=muiltlineData.groupline;
					var taarr=muiltlineData2.join(",").split(",");//转化为一维数组
					var maxnum = Math.max.apply(null,taarr);//最大值
					
				   	//initcavW(muiltlineData.muiltDate,$("#muiltLinecvs"+id),'',);
				   	initcavW(muiltlineData.muiltDate,$("#muiltLinecvs"+id),'',maxnum,'');
//				    console.log(muiltlineData.groupline);			
				    /**清除canvas**/
					if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
						var muiltLinecvss = document.getElementById("muiltLinecvs"+id);
						var muiltLineaxess = document.getElementById("muiltLineaxes"+id);
						RGraph.Reset(muiltLinecvss);
				        RGraph.Reset(muiltLineaxess);
				        $("div[data-id='"+id+"']").find("#muiltLineKey"+id).html('')
					}
					$("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
			        var muiltline = new RGraph.Line({
			            id: 'muiltLinecvs'+id,
			            data: muiltlineData.groupline,
			            options: {
			                labels: muiltlineData.muiltDate,
			                tooltips: muiltlineData.groupline,
			                tooltipsCoordsPage: true,
			                noxaxis: true,
			                noyaxis: true,
			                ylabels: false,
	//		                ylabelsCount: 8,
			                vmargin: 30,
			                gutterTop:60,//上填充
			                gutterBottom:50,//上填充
			                backgroundGridDashed:true,//虚线网格
	//		                yaxispos:'right',
							labelsAbove: true,
			                //labelsAboveUnitsPost:labelsunit,
			                labelsAboveSize:8,//折线数据size
			                labelsAboveColor:colorsarr,
			                labelsAboveBackground:'transparent',//labelsAbove的背景
			                labelsAboveBorder:false,//labelsAbove的边框
			                colors: colorsarr,
			                linewidth: 1,//折线宽度
			                backgroundGridVlines: false,//不显示网格y
			                backgroundGridBorder: false,//不显示网格边框
			                textSize: 8,//全局字体大小
			                textColor: '#888',
			                shadow:false,
			                //backgroundGrid: false		                
			                tickmarks: 'filledcircle',
			            }
			        }).trace2();
	//		        console.log(line)
			        var yaxis = new RGraph.Drawing.YAxis({
			            id: 'muiltLineaxes'+id,
			            x: 40,
			            textSize:8,
			            options: {
			            	textSize: 8,
			                max: muiltline.max,
			                colors: ['#888888'],
			                gutterTop:60,//上填充
			                gutterBottom:50,//上填充
			                unitsPre:typeYstr[0]
	//		                numyticks: 10,//步长
			            }
			        }).draw();
			        /**绘制自定义图例**/
			        var muiltLinekey = RGraph.HTML.Key('muiltLineKey'+id, {
		                colors: muiltline.Get('colors'),
		                labels:labelArr2,
		                tableClass: 'rgraph_key',
		                
		                blobCss: {
		                    'border-radius': '0'
		                },
		                blobClass: 'rgraph_key_blob',	                
		                labelCss: {
		                },
		                labelClass: 'rgraph_key_label'
		            });
		            $("#"+yaxis.id).parents('.report-box').addClass('cvsRandered');
			        initWidth($("#muiltLinecvs"+id));
		        }
		        
		        
		        
		        
		        /**生成饼状模板**/
		       	function initMarketDonutTemp(idFlag,menuId,graphName,show,sum,sumDate){
		       		var strdis = (function(){
						if(sum==null){
							return "display:none"
						}
					})();
		       		var temp = '<div class="report-box"  data-type="7" data-id="'+idFlag+'" data-menuId="'+menuId+'">'+
						'<div class="main-pad mainpd-cor">'+
							'<div class="rep-temt-w">'+
								'<h2 class="rep-temt">'+graphName+'</h2>'+
								'<p class="rep-toal" style="'+strdis+'"><span class="totalday">'+sumDate+'</span>天合计数据：<span class="totaldata">'+sum+'</span></p>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad date-tool">'+
							'<div class="rep-temt-date rep-temt-ot">'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">市场选择</span>'+
									'<a class="checkmark" href="javascript:;" data-mak="0">'+
										'<span class="markepopRes">全部市场</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">类型选择</span>'+
									'<a class="checkUsertype" href="javascript:;" data-user="0">'+
										'<span class="userpopRes">全部用户</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">来源选择</span>'+
									'<a class="checkUserfrom" href="javascript:;" data-userfrom="7">'+
										'<span class="frompopRes">全部来源</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad rgraph-box">'+
			       			'<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">'+
								'<div class="mui-pull">'+
									'<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>'+
									'<div class="mui-pull-caption">正在加载...</div>'+
								'</div>'+
							'</div>'+
							'<p class="no-data-tips">暂无数据</p>'+
							'<canvas id="marketDonut'+idFlag+'" width="475" height="350" style="cursor: default;">'+
							    '[No canvas support]'+
							'</canvas>'+
						'</div>'+
					'</div>';
					$(".rep-wrap").append(temp);
					initTooltemp($("div[data-id='"+idFlag+"']"),show);
					initprop($("div[data-id='"+idFlag+"']"),idFlag);
		       	}
		        /**饼状(type=7)**/
		        //$("#marketDonut").css({"width":winWidth-60});
		        function initMarketDonut(id,data){
			        $("#marketDonut"+id).css({
	//		        	"width":maincvsW/7*(dataArr.length-7)+maincvsW,	
						"width":$(this).parents(".rep-temp").width(),
			        	"height":'auto',
			        	'max-height':'29.5rem',
			        	'max-width':'25.4rem'
			        });
			        if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
			        	var marketDonuts = document.getElementById("marketDonut"+id);
						RGraph.Reset(marketDonuts);
					}
			        $("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
			        var marketDonut = new RGraph.Pie({
			            id: 'marketDonut'+id,
			            data: [41,37,16,3,3],
			            options: {
			                variant: 'donut',
			                linewidth: 5,
			                exploded: 0,
			                shadow:false,
			                strokestyle: 'rgba(0,0,0,0)',
	//		                title: "数据数据",
			                tooltips: ['MSIE 7 (41%)', 'MSIE 6 (37%)', 'Firefox (16%)', 'Safari (3%)', 'Other (3%)'],
			                colors:['#83e3ff','#00b0da','#2dc1e9','#53cff3','#ff9c00','#ffcf00'],
			                key: ['数据', '数据', '数据', '数据', '数据'],
			                radius:110,//半径
			                keyShadow: false,
			                keyAlign: 'bottom',
			                'key.position':'gutter',
	//		                'key.position.x': winWidth/2-100,
			                'key.position.gutter.boxed':false,
			                'key.text.size':14,
			                align: 'center',
			                //centerx: winWidth/2-110,
			                textSize:12,
			                textFont:["Microsoft YaHei",'Helvetica Neue'],
			                textColor:'#888888',
			                variantDonutWidth:32,//环形宽度
			            }
			        }).roundRobin({'radius': false,'frames':60}).on('draw', function (obj)
				        {
				            // 绘制内部自定义文字.
				            var coords = RGraph.Text2(obj, {
				                font: ["Microsoft YaHei",'Helvetica Neue'],
				                size: 16,
				                x: obj.centerx,
				                y: obj.centery,
				                text: '数据数据',
				                halign: 'center',
				                valign: 'center',
				                bounding: false,
				                boundingFill:'transparent',
				                marker: false,
				                angle: 0,
				                textColor:'#888888'
				                
				            });
				
				            
				
				        });	
				    $("#"+marketDonut.id).parents('.report-box').addClass('cvsRandered');
		        }
		        
			    
			    /**生成重叠bar模板**/
			   	function initBarinbarTemp(idFlag,menuId,graphName,show,sum,sumDate){
			   		var strdis = (function(){
						if(sum==null){
							return "display:none"
						}
					})();
			   		var temp = '<div class="report-box" data-type="2" data-id="'+idFlag+'" data-menuId="'+menuId+'>'+
						'<div class="main-pad mainpd-cor">'+
							'<div class="rep-temt-w">'+
								'<h2 class="rep-temt">'+graphName+'</h2>'+
								'<p class="rep-toal" style="'+strdis+'"><span class="totalday">'+sumDate+'</span>天合计数据：<span class="totaldata">'+sum+'</span></p>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad date-tool">'+
							'<div class="rep-temt-date">'+
								'<span class="input-tit"><span class="input-titinner">开始时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="startDate'+idFlag+'">'+
									'<span id="selStartDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="开始时间" value="" class="startDate dn" id="startDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+
								'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="endDate'+idFlag+'">'+
									'<span id="selEndDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="结束时间" value="" class="endDate dn temp-input" id="endDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+
							'</div>'+
							'<div class="rep-temt-date rep-temt-ot">'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">市场选择</span>'+
									'<a class="checkmark" href="javascript:;" data-mak="0">'+
										'<span class="markepopRes">全部市场</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">类型选择</span>'+
									'<a class="checkUsertype" href="javascript:;" data-user="0">'+
										'<span class="userpopRes">全部用户</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">来源选择</span>'+
									'<a class="checkUserfrom" href="javascript:;" data-userfrom="7">'+
										'<span class="frompopRes">全部来源</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad rgraph-box">'+
				   			'<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">'+
								'<div class="mui-pull">'+
									'<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>'+
									'<div class="mui-pull-caption">正在加载...</div>'+
								'</div>'+
							'</div>'+
							'<p class="no-data-tips">暂无数据</p>'+
							'<div class="rep-temp">'+
							'<div style="position: relative;" class="temp01-wrapper">'+
							    '<canvas id="barinbarAxes'+idFlag+'" width="40" height="480" style="position: absolute;  left: 0; z-index: 3;cursor: default;"></canvas>'+
							    '<div style="width: 600px; overflow-x: auto;overflow-y: hidden; position: absolute; left: 41px" class="temp01-wrap">'+
							    	'<div class="cvsmain-wrap">'+
							        '<canvas id="barinbar'+idFlag+'" width="1000" height="480" style="cursor: default;"></canvas>'+
							        '</div>'+
							    '</div>'+
							    '<div class="barinbarKey" id="barinbarKey'+idFlag+'"></div>'+
							'</div>'+
							'</div>'+
						'</div>'+
					'</div>';
					$(".rep-wrap").append(temp);
					initTooltemp($("div[data-id='"+idFlag+"']"),show);
					initprop($("div[data-id='"+idFlag+"']"),idFlag);
			   	}
			    /**bar重叠(type=2)***/
			   	function initBarinbar(id,data){
			   		$("div[data-id='"+id+"']").find(".rep-temp").show().end().find(".no-data-tips").hide();
				   	initcavW(dataArr,$("#barinbar"+id));
				   	var barWrapw = $(".barcav-wrap").width();
				    $("#barinbar"+id).width(barWrapw);
				   	var barinbardata = {}
			            barinbardata.shipped = dataArr;
			            barinbardata.sold    = [10,8,5,13,10,30,40,50,60,30];
			        if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
			        	var barinbars = document.getElementById("barinbar"+id);
			        	var barinbarAxess = document.getElementById("barinbar"+id);			        	
						RGraph.Reset(barinbars);
				        RGraph.Reset(barinbarAxess);
				        $('#barinbarAxes'+id).html('');
					}
					/**绘制第一个bar**/
					$("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
			        var barinbar = new RGraph.Bar({
			            id: 'barinbar'+id,
			            data: barinbardata.shipped,
			            options: {
			                gutterTop: 40,
			                gutterLeft: 0,
			                colors: ['#94dbbb'],
			                shadow:false,
			                noyaxis: true,
			                ylabels: false,
			                //hmargin: 22,
			                hmargin: $("#barinbar"+id).parents('.report-box').find(".temp01-wrap").width()/28,
			                labels: ['1501','1502','1503','1504','1505','1506','1507','1508','1509','1510'],
			                labelsAbove: barinbardata.shipped,
			                labelsAboveSize:16,//数据size
			                labelsAboveColor:'#59c895',
			                strokestyle: 'rgba(0,0,0,0)',
			                backgroundGridVlines: false,//不显示网格y
			                backgroundGridBorder: false,//不显示网格边框
			                backgroundGridDashed:true,//虚线网格		                
			            }
			        }).draw();		
			        /**绘制第二个bar**/
			        var barinbar2 = new RGraph.Bar({
			            id: 'barinbar'+id,
			            data: barinbardata.sold,
			            options: {
			                ymax: barinbar.scale2.max,
			                gutterTop: 40,
			                gutterLeft: barinbar.Get('gutterLeft'),
			                shadow:false,
			                //gutterLeft: barinbar.Get('gutterLeft'),
			                colors: ['#59c895'],
			                noaxes: true,
			                labelsAbove: true,
			                labelsAboveSize:16,//数据size
			                labelsAboveColor:'#000000', 
			                hmargin:  barinbar.Get('hmargin'),
			                ylabels: false,
			                backgroundGrid: false,		                
			                strokestyle: 'rgba(0,0,0,0)',
	//		                labelsAboveUnitsPost:40,//单位
	
			            }
			        }).grow();
			        /**绘制Y轴**/
			        var barinbarAxes = new RGraph.Drawing.YAxis({
			            id: 'barinbarAxes'+id,
			            x: 40,
			            options: {
			                max: barinbar.max,
			                colors: ['#888888'],
			                gutterTop:barinbar.Get('gutterTop'),//上填充
	//		                numyticks: 10,//步长
			            }
			        }).draw();
			        /**绘制自定义图例**/
			        var key = RGraph.HTML.Key('barinbarKey'+id, {
		                colors: ['#94dbbb','#59c895'],
		                labels:['数据','数据'],	                
	//	                tableCss:{
	//	                    'position':'absolute',
	//	                    'top':'25px',
	//	                    'left':'590px'
	//	                },
		                tableClass: 'rgraph_key',
		                
		                blobCss: {
		                    'border-radius': '0'
		                },
		                blobClass: 'rgraph_key_blob',	                
		                labelCss: {
		                },
		                labelClass: 'rgraph_key_label'
		            });
	//	            $('.temp01-wrap').each(function(){
	//		        	$(this).css({
	//		        		"left":$(this).parents(".report-box").find("canvas:first").width()
	//		        	})
	//		        	
	//		        })
					$("#"+barinbarAxes.id).parents('.report-box'+id).addClass('cvsRandered');
					initWidth($("#barinbar"+id));		
			   	}
				
				
				
				/**生成堆砌表模板**/
				function initBaronbarTemp(idFlag,menuId,graphName,show,sum,sumDate){
					var strdis = (function(){
						if(sum==null){
							return "display:none"
						}
					})();
					var temp = '<div class="report-box" data-type="6" data-id="'+idFlag+'" data-menuId="'+menuId+'">'+
						'<div class="main-pad mainpd-cor">'+
							'<div class="rep-temt-w">'+
								'<h2 class="rep-temt">'+graphName+'</h2>'+
								'<p class="rep-toal" style="'+strdis+'"><span class="totalday">'+sumDate+'</span>天合计数据：<span class="totaldata">'+sum+'</span></p>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad date-tool">'+
							'<div class="rep-temt-date">'+
								'<span class="input-tit"><span class="input-titinner">开始时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="startDate'+idFlag+'">'+
									'<span id="selStartDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="开始时间" value="" class="startDate dn" id="startDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+
								'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="endDate'+idFlag+'">'+
									'<span id="selEndDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="结束时间" value="" class="endDate dn temp-input" id="endDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+
							'</div>'+
							'<div class="rep-temt-date rep-temt-ot">'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">市场选择</span>'+
									'<a class="checkmark" href="javascript:;" data-mak="0">'+
										'<span class="markepopRes">全部市场</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">类型选择</span>'+
									'<a class="checkUsertype" href="javascript:;" data-user="0">'+
										'<span class="userpopRes">全部用户</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">来源选择</span>'+
									'<a class="checkUserfrom" href="javascript:;" data-userfrom="7">'+
										'<span class="frompopRes">全部来源</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad rgraph-box">'+
							'<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">'+
								'<div class="mui-pull">'+
									'<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>'+
									'<div class="mui-pull-caption">正在加载...</div>'+
								'</div>'+
							'</div>'+
							'<p class="no-data-tips">暂无数据</p>'+
							'<div class="rep-temp">'+
							'<div style="position: relative;" class="temp01-wrapper">'+
							    '<canvas id="baronbarAxes'+idFlag+'" width="40" height="480" style="position: absolute;  left: 0; z-index: 3;cursor: default;"></canvas>'+
							    '<div style="width: 600px;overflow-x: auto;overflow-y: hidden; position: absolute; left: 41px" class="temp01-wrap">'+
							    	'<div class="cvsmain-wrap">'+
							        '<canvas id="baronbar'+idFlag+'" width="1000" height="480" style="cursor: default;"></canvas>'+
							        '</div>'+
							    '</div>'+
							    '<div class="baronbarKey" id="baronbarKey'+idFlag+'"></div>'+
							'</div>'+
							'</div>'+
						'</div>'+
					'</div>';
					$(".rep-wrap").append(temp);
					initTooltemp($("div[data-id='"+idFlag+"']"),show);
					initprop($("div[data-id='"+idFlag+"']"),idFlag);
				}
				/**bar堆砌(type=6)***/
				function initBaronbar(id,data){
					$("div[data-id='"+id+"']").find(".rep-temp").show().end().find(".no-data-tips").hide();
					var datalistTemp = data.data[0];
					var baronbarData = {};
//				   		baronbarData.group = [[10,8,5],[10,30,40],[60,30,2],[44,11,21],[71,52,70],[71,52,70],[10,8,5],[10,47,30],[60,38,10],[5,53,10]];//数据实例
//				   		baronbarData.muiltData = ['1501','1502','1503','1504','1505','1506','1507','1508','1509','1510'];//数据实例
				   		baronbarData.group = [];
				   		baronbarData.muiltData = data.parms;
//				   	var labelArr1 = [];
					var labelArr2 = [];//图例title
					var colorstr = [];//颜色
					var typegraph = 1;
					var typeY = 1;
					var arrTemp = [];
					var arrTemp2 = [];
					
					var boolFlag = [];
					var boolFlags = 0;
					
					for(var j in datalistTemp){
						var jsonData = datalistTemp[j];
						
						if(datalistTemp[j].data.length<=0){
							boolFlag.push(0)
						}else{
							boolFlag.push(1)
						}
						arrTemp.push(datalistTemp[j].data);
						colorstr.push(datalistTemp[j].color);
						if(datalistTemp[j].parms){
							labelArr2.push(datalistTemp[j].parms);
						}
						for(var l in jsonData){
//								labelArr1 = jsonData.data;
								typegraph = jsonData.type;
								typeY = jsonData.typeY;
//								colorstr
//							}
//							arrtemp.push(arrtemp2);
						}
//						console.log(arrtemp)
					}
					for(var k=0,lenk = boolFlag.length; k<lenk; k++){
						if(boolFlag[k]==0){
							boolFlags+=1;
						}
					}
					if(boolFlags==boolFlag.length){
						$("div[data-id='"+id+"']").find(".no-data-tips").show().next().hide()
												.end().prev(".loading-box").removeClass("mui-visibility");
						return false;
					}
					
					for(var z = 0;z<arrTemp[0].length;z++){	
						var arr = [];
						for(var y = 0;y<arrTemp.length;y++){
							arr.push(arrTemp[y][z])
						}
						arrTemp2.push(arr);
					}
//					console.log(arrTemp2);
					baronbarData.group = arrTemp2;
					initcavW(baronbarData.muiltData,$("#baronbar"+id));
					if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
						var baronbars = document.getElementById("baronbar"+id);
						var baronbarAxess = document.getElementById("baronbarAxes"+id);
						RGraph.Reset(baronbars);
				        RGraph.Reset(baronbarAxess);
				        $("div[data-id='"+id+"']").find("#baronbarKey"+id).html('')
					}
					/**绘制第一个bar**/
					$("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
			        var baronbar = new RGraph.Bar({
			            id: 'baronbar'+id,
			            data: baronbarData.group,
			            options: {
			            	grouping: 'stacked',
			                gutterTop: 40,
			                gutterLeft: 0,
			                colors: colorstr,
			                shadow:false,
			                noyaxis: true,
			                ylabels: false,
			                hmargin: 20/2,//堆砌的只可能有一一个条状位置
			                //hmargin: $("#baronbar"+id).parents('.report-box').find(".temp01-wrap").width()/6,
			                labels: baronbarData.muiltData,
	//		                labelsAbove: barinbardata.shipped,
			                labelsAboveSize:8,//数据size
			                textSize: 8,
			                labelsAboveColor:'#000000',
			                strokestyle: 'rgba(0,0,0,0)',
			                backgroundGridVlines: false,//不显示网格y
			                backgroundGridBorder: false,//不显示网格边框
			                backgroundGridDashed:true,//虚线网格		                
			            }
			        }).on('draw', function (obj)
			        {
			            for (var i=0; i<obj.coords.length; ++i) {
			            	obj.context.fillStyle = '#000000';
			                /* var colors = '';
			                if(obj.coords[i]<=10){
			                	colors = '#000000'
			                		obj.context.fillStyle = '#000000';
			                }else{
			                	colors = '#ffffff'
			                		//obj.context.fillStyle = '#ffffff';
			                } */
			                RGraph.Text2(obj.context, {
			                    font:'Verdana',
			                    'size':8,
			                    textSize: 8,
			                    /* 'bold':true, */
			                    Color:'#000000',
			                    shadow:true,
			                    'x':obj.coords[i][0] + (obj.coords[i][2] / 2),
			                    'y':obj.coords[i][1] + (obj.coords[i][3] / 2),
			                    'text':obj.data_arr[i].toString(),
			                    'valign':'center',
			                    'halign':'center'
			                });
			            }
			        }).draw();
			        /**绘制Y轴**/
			        var baronbarAxes = new RGraph.Drawing.YAxis({
			            id: 'baronbarAxes'+id,
			            x: 36,
			            textSize:8,
			            options: {
			                max: baronbar.max,
			                textSize: 8,
			                colors: ['#888888'],
			                gutterTop:baronbar.Get('gutterTop'),//上填充
	//		                numyticks: 10,//步长
			            }
			        }).draw();
			        /**绘制自定义图例**/
			        var baronbarkey = RGraph.HTML.Key('baronbarKey'+id, {
		                colors: baronbar.Get('colors'),
		                labels:labelArr2,	                
	//	                tableCss:{
	//	                    'position':'absolute',
	//	                    'top':'25px',
	//	                    'left':'590px'
	//	                },
		                tableClass: 'rgraph_key',
		                
		                blobCss: {
		                    'border-radius': '0'
		                },
		                blobClass: 'rgraph_key_blob',	                
		                labelCss: {
		                },
		                labelClass: 'rgraph_key_label'
		            });
		            $("#baronbar"+id).parents('.report-box').addClass('cvsRandered');
					initWidth($("#baronbar"+id));	
				}
			   	//$('.temp01-wrap').width(maincvsW);
			   		
				
				
			   	/**生成堆砌表模板**/
				function initMuiltbarTemp(idFlag,menuId,graphName,show,sum,sumDate){
					var strdis = (function(){
						if(sum==null){
							return "display:none"
						}
					})();
					var temp = '<div class="report-box" data-type="5" data-id="'+idFlag+'" data-menuId="'+menuId+'">'+
						'<div class="main-pad mainpd-cor">'+
							'<div class="rep-temt-w">'+
								'<h2 class="rep-temt">'+graphName+'</h2>'+
								'<p class="rep-toal" style="'+strdis+'"><span class="totalday">'+sumDate+'</span>天合计数据：<span class="totaldata">'+sum+'</span></p>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad date-tool">'+
							'<div class="rep-temt-date">'+
								'<span class="input-tit"><span class="input-titinner">开始时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="startDate'+idFlag+'">'+
									'<span id="selStartDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="开始时间" value="" class="startDate dn" id="startDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+	
									'</label>'+
								'</span>'+
								'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="endDate'+idFlag+'">'+
									'<span id="selEndDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="结束时间" value="" class="endDate dn temp-input" id="endDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+
							'</div>'+
							'<div class="rep-temt-date rep-temt-ot">'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">市场选择</span>'+
									'<a class="checkmark" href="javascript:;" data-mak="0">'+
										'<span class="markepopRes">全部市场</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">类型选择</span>'+
									'<a class="checkUsertype" href="javascript:;" data-user="0">'+
										'<span class="userpopRes">全部用户</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">来源选择</span>'+
									'<a class="checkUserfrom" href="javascript:;" data-userfrom="7">'+
										'<span class="frompopRes">全部来源</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad rgraph-box">'+							
							'<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">'+
								'<div class="mui-pull">'+
									'<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>'+
									'<div class="mui-pull-caption">正在加载...</div>'+
								'</div>'+
							'</div>'+
							'<p class="no-data-tips">暂无数据</p>'+
							'<div class="rep-temp">'+
							'<div style="position: relative;" class="temp01-wrapper">'+
							    '<canvas id="multbarX'+idFlag+'" width="40" height="480" style="position: absolute;  left: 0; z-index: 3;cursor: default;"></canvas>'+
							    '<div style="width: 600px;overflow-x: auto;overflow-y: hidden; position: absolute; left: 41px" class="temp01-wrap">'+
							    	'<div class="cvsmain-wrap">'+
							        '<canvas id="multbarcav'+idFlag+'" width="1000" height="480" style="cursor: default;"></canvas>'+
							        '</div>'+
							    '</div>'+
							    '<div class="multbarKey" id="multbarKey'+idFlag+'"></div>'+
							'</div>'+
							'</div>'+
						'</div>'+
					'</div>';
					$(".rep-wrap").append(temp);
					initTooltemp($("div[data-id='"+idFlag+"']"),show);
					initprop($("div[data-id='"+idFlag+"']"),idFlag);
					
				}
			   	/**多个bar(type=5)***/
			   	function initMuiltbar(id,data){
			   		$("div[data-id='"+id+"']").find(".rep-temp").show().end().find(".no-data-tips").hide();
				   	//$('.temp01-wrap').width(maincvsW);
//				   	[[10,8,5,13,],[10,30,40,50],[60,30,2,8],[44,11,21,60],[71,52,70,30],[71,52,70,30],[10,8,5,4],[10,47,30,70],[60,38,10,77],[5,53,10,30]]
				   	var datalistTemp = data.data[0];
				   	var muiltData = {};
				   		muiltData.group = [];
				   		muiltData.muiltData = data.parms;
					var labelArr1 = [];
					var labelArr2 = [];//图例title
					var colorstr = [];//颜色
					var typegraph = 1;
					var typeY = 1;
					var arrTemp = [];
					var arrTemp2 = [];
					var typeY = 0;//1,金额；2,普通数字
					var typeY2 = 0;//1,金额；2,普通数字
					var typeYstr = [];
					
					var boolFlag = [];
					var boolFlags = 0;
					
					for(var j in datalistTemp){
						var jsonData = datalistTemp[j];
						
						if(datalistTemp[j].data.length<=0){
							boolFlag.push(0)
						}else{
							boolFlag.push(1)
						}
						arrTemp.push(datalistTemp[j].data);
						colorstr.push(datalistTemp[j].color);
						if(datalistTemp[j].parms){
							labelArr2.push(datalistTemp[j].parms);
						}
						for(var l in jsonData){
								labelArr1 = jsonData.data;
								typegraph = jsonData.type;
								typeY = jsonData.typeY;
//								colorstr
//							}
//							arrtemp.push(arrtemp2);
						}
						if(datalistTemp[j].typeY=='1'){
							typeY = datalistTemp[j].typeY;
						}else if(datalistTemp[j].typeY=='2'){
							typeY2 = datalistTemp[j].typeY;
						}
//						console.log(arrtemp)
					}
					
					for(var k=0,lenk = boolFlag.length; k<lenk; k++){
						if(boolFlag[k]==0){
							boolFlags+=1;
						}
					}
					if(boolFlags==boolFlag.length){
						$("div[data-id='"+id+"']").find(".no-data-tips").show().next().hide()
												.end().prev(".loading-box").removeClass("mui-visibility");
						return false;
					}
					
					for(var z = 0;z<arrTemp[0].length;z++){	
						var arr = [];
						for(var y = 0;y<arrTemp.length;y++){
							arr.push(arrTemp[y][z])
						}
						arrTemp2.push(arr);
					}
					if(typeY!=0&&typeY==1){
						typeYstr.push('￥')
					}
					if(typeY2!=0&&typeY2==2){
						typeYstr.push('')
					}
//					console.log(arrTemp2);
					muiltData.group = arrTemp2;
				   	initcavW(muiltData.group,$("#multbarcav"+id));
				   	if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
				   		var multbarcavs = document.getElementById("multbarcav"+id);
				   		var multbarXs = document.getElementById("multbarX"+id);
						RGraph.Reset(multbarcavs);
				        RGraph.Reset(multbarXs);
				        $("div[data-id='"+id+"']").find("#multbarKey"+id).html('')
					}
				   	//var barWrapw = $(".barcav-wrap").width();
				    //$("#barinbar").width(barWrapw);
					/**绘制第所有bar**/
					$("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
			        var muiltbar = new RGraph.Bar({
			            id: 'multbarcav'+id,
			            data: muiltData.group,
			            options: {
			                gutterTop: 40,
			                gutterLeft: 0,
			                colors: colorstr,
			                shadow:false,
			                noyaxis: true,
			                ylabels: false,
			                //hmargin: 22,
			                hmargin: $("#multbarcav"+id).parents('.report-box').find(".temp01-wrap").width()/28,
			                labels: muiltData.muiltData,
			                hmarginGrouped: 2,
			                labelsAboveSize:16,//数据size
			                labelsAboveColor:'#59c895',
			                strokestyle: 'rgba(0,0,0,0)',
			                backgroundGridVlines: false,//不显示网格y
			                backgroundGridBorder: false,//不显示网格边框
			                backgroundGridDashed:true,//虚线网格		                
			            }
			        }).draw();
			        /**绘制Y轴**/
			        var muiltbarAxes = new RGraph.Drawing.YAxis({
			            id: 'multbarX'+id,
			            x: 40,
			            options: {
			                max: muiltbar.max,
			                colors: ['#888888'],
			                gutterTop:muiltbar.Get('gutterTop'),//上填充
	//		                numyticks: 10,//步长
			            }
			        }).draw();
			        /**绘制自定义图例**/
			        var muiltbarkey = RGraph.HTML.Key('multbarKey'+id, {
		                colors: muiltbar.Get('colors'),
		                labels:labelArr2,
		                tableClass: 'rgraph_key',
		                
		                blobCss: {
		                    'border-radius': '0'
		                },
		                blobClass: 'rgraph_key_blob',	                
		                labelCss: {
		                },
		                labelClass: 'rgraph_key_label'
		            });
	//	            $('.temp01-wrap').each(function(){
	//		        	$(this).css({
	//		        		"left":$(this).parents(".report-box").find("canvas:first").width()
	//		        	})
	//		        	
	//		        })
					$("#"+muiltbarAxes.id).parents('.report-box').addClass('cvsRandered');
					initWidth($("#multbarcav"+id));	
			   	}
			   	
				
				
				/**生成堆砌表模板**/
				function initHybridBlTemp(idFlag,menuId,graphName,show,sum,sumDate){
					var strdis = (function(){
						if(sum==null){
							return "display:none"
						}
					})();
					var temp='<div class="report-box" data-type="3" data-id="'+idFlag+'" data-menuId="'+menuId+'">'+						
						'<div class="main-pad mainpd-cor">'+
							'<div class="rep-temt-w">'+
								'<h2 class="rep-temt">'+graphName+'</h2>'+
								'<p class="rep-toal" style="'+strdis+'"><span class="totalday">'+sumDate+'</span>天合计数据：<span class="totaldata">'+sum+'</span></p>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad date-tool">'+
							'<div class="rep-temt-date">'+
								'<span class="input-tit"><span class="input-titinner">开始时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="startDate'+idFlag+'">'+
									'<span id="selStartDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="开始时间" value="" class="startDate dn" id="startDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+	
									'</label>'+
								'</span>'+
								'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
								'<span class="input-wrap">'+
									'<label class="temp-input-box" for="endDate'+idFlag+'">'+
									'<span id="selEndDate'+idFlag+'"></span>'+
									'<input type="text" placeholder="结束时间" value="" class="endDate dn temp-input" id="endDate'+idFlag+'" readonly="ture">'+
									'<span class="datep-ico"></span>'+
									'</label>'+
								'</span>'+
							'</div>'+
							'<div class="rep-temt-date rep-temt-ot">'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">市场选择</span>'+
									'<a class="checkmark" href="javascript:;" data-mak="0">'+
										'<span class="markepopRes">全部市场</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">类型选择</span>'+
									'<a class="checkUsertype" href="javascript:;" data-user="0">'+
										'<span class="userpopRes">全部用户</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
								'<div class="check-t-wrap">'+
									'<span class="input-tit checkmark-tit">来源选择</span>'+
									'<a class="checkUserfrom" href="javascript:;" data-userfrom="7">'+
										'<span class="frompopRes">全部来源</span>'+
										'<span class="arrow-down"></span>'+
									'</a>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="main-pad rgraph-box">'+
							'<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">'+
								'<div class="mui-pull">'+
									'<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>'+
									'<div class="mui-pull-caption">正在加载...</div>'+
								'</div>'+
							'</div>'+
							'<p class="no-data-tips">暂无数据</p>'+
							'<div class="rep-temp">'+
							'<div style="position: relative;" class="temp01-wrapper">'+
							    '<canvas id="hybridBly'+idFlag+'" width="40" height="480" style="position: absolute;  left: 0; z-index: 3;cursor: default;"></canvas>'+
							    '<canvas id="hybridBlly'+idFlag+'" width="40" height="480" style="position: absolute;  right: 0; z-index: 3;cursor: default;"></canvas>'+
							    '<div style="width: 600px;overflow-x: auto;overflow-y: hidden; position: absolute; left: 41px" class="temp01-wrap">'+
							    	'<div class="cvsmain-wrap">'+
							        '<canvas id="hybridBlcav'+idFlag+'" width="1000" height="480" style="cursor: default;"></canvas>'+
							        '</div>'+
							    '</div>'+
							    '<div class="multbarKey" id="hybridBlKey'+idFlag+'"></div>'+
							'</div>'+
							'</div>'+
						'</div>'+
					'</div>';
					$(".rep-wrap").append(temp);
					initTooltemp($("div[data-id='"+idFlag+"']"),show);
					initprop($("div[data-id='"+idFlag+"']"),idFlag);
				}
				/**Hybrid(混杂：bar+line)(type=3)***/
				function initHybridBl(id,data){
					//$('.temp01-wrap').width(maincvsW);
					$("div[data-id='"+id+"']").find(".rep-temp").show().end().find(".no-data-tips").hide();
					/* $("div[data-id='"+id+"']").find(".rep-temp") */
					var datalistTemp = data.data[0];
		        	var parms = data.parms;
				   	var muiltData2 = {}
				   		/* muiltData2.groupBar = [[500,1000],[1170,700],[3000,2000],[4000,4200],[710,520],[710,3000],[1700,800],[1000,470],[600,380],[500,530]];//实例
				   		muiltData2.groupline = [[10,8,5,13,10,30,40,50,60,30],[7,15,10,18,7,35,45,45,55,35]]; */
				   		muiltData2.groupBar = [];
				   		muiltData2.groupline = [];
				   		muiltData2.muiltData =  parms;
				   	var labelArr1 = [];
					var labelArr2 = [];//图例title
					var colorstr = [];//颜折线色
					var colorstr2 = [];//条形颜色
					var colorstr3 = [];//图例颜色
//					var typegraph = 1;
					var typeY = 0;//1,金额；2,普通数字
					var typeY2 = 0;//1,金额；2,普通数字
					var typeYstr = [];
					var arrTemp = [];//折线不用格式化数据
					var arrTemp3 = [];//条形缓存一级以备格式化
					var arrTemp2 = [];
					
					var boolFlag = [];
					var boolFlags = 0;
//					var typeYstr2 = '';

					/**判断负数**/
					var lineXpos = '';
					for(var j in datalistTemp){
						var jsonData = datalistTemp[j];
						
						if(datalistTemp[j].data.length<=0){
							boolFlag.push(0)
						}else{
							boolFlag.push(1)
						}
						
						//1，折线；2，条形
						if(datalistTemp[j].type=='1'){
							arrTemp.push(datalistTemp[j].data);
							colorstr.push(datalistTemp[j].color);
							/* for(var d= 0 ;d<datalistTemp[j].data.length;d++){
								if(datalistTemp[j].data[d]<0){
									lineXpos = 'center'
								}else{
									lineXpos = 'bottom'
								}
							} */
						}else if(datalistTemp[j].type=='2'){
							arrTemp3.push(datalistTemp[j].data);
							colorstr2.push(datalistTemp[j].color);
							/* for(var e= 0 ;e<datalistTemp[j].data.length;e++){
								if(datalistTemp[j].data[e]<0){
									lineXpos = 'center'
								}else{
									lineXpos = 'bottom'
								}
							} */
						}
						if(datalistTemp[j].typeY=='1'){
							typeY = datalistTemp[j].typeY;
						}else if(datalistTemp[j].typeY=='2'){
							typeY2 = datalistTemp[j].typeY;
						}
						colorstr3.push(datalistTemp[j].color)
						//colorstr.push(datalistTemp[j].color);
						if(datalistTemp[j].parms){
							labelArr2.push(datalistTemp[j].parms);
						}
						for(var l in jsonData){
								labelArr1 = jsonData.data;
//								typegraph = jsonData.type;
//								typeY = jsonData.typeY;
//								colorstr
//							}
//							arrtemp.push(arrtemp2);
						}
//						console.log(arrtemp)
					}
					
					for(var k=0,lenk = boolFlag.length; k<lenk; k++){
						if(boolFlag[k]==0){
							boolFlags+=1;
						}
					}
					if(boolFlags==boolFlag.length){
						$("div[data-id='"+id+"']").find(".no-data-tips").show().next().hide()
												.end().prev(".loading-box").removeClass("mui-visibility");
						return false;
					}
					var barLen =arrTemp3.length;
					if(arrTemp3.length>1){
						for(var z = 0;z<arrTemp3[0].length;z++){	
							var arr = [];
							for(var y = 0;y<arrTemp3.length;y++){
								arr.push(arrTemp3[y][z])
							}
							arrTemp2.push(arr);
						}
					}else{
						arrTemp2 = arrTemp3[0]
					}
					
//					console.log(arrTemp2);
					if(arrTemp.length>1){
						muiltData2.groupline = arrTemp;
					}else if(arrTemp.length=1){
						muiltData2.groupline = arrTemp[0];
					}
					//colorstr2.length==1?colorstr2[0]:colorstr2
					/* if(colorstr2.length==1){
						colorstr2 =colorstr2[0]
					} */
					muiltData2.groupline = arrTemp;
					muiltData2.groupBar = arrTemp2;
//					muiltData2.muiltData = labelArr2;
					if(typeY!=0&&typeY==1){
						typeYstr.push('￥')
					}
					if(typeY2!=0&&typeY2==2){
						typeYstr.push('')
					}
					
					
					
					
					//initcavW(labelArr1,$("#cvs"+id),'',maxLabelArrFor,typeYstr[0]);
				   	
				   	if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
				   		var hybridBlcavs = document.getElementById("hybridBlcav"+id);
				   		var hybridBlys = document.getElementById("hybridBly"+id);
				   		var hybridBllys = document.getElementById("hybridBlly"+id);
						RGraph.Reset(hybridBlcavs);
				        RGraph.Reset(hybridBlys);
				        RGraph.Reset(hybridBllys);
				        $("div[data-id='"+id+"']").find("#hybridBlKey"+id).html('')
					}
				   	$("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
				   	
				   	//var barWrapw = $(".barcav-wrap").width();
				    //$("#barinbar").width(barWrapw);
					/**绘制所有bar**/
					var muiltlineData=muiltData2.groupline;
					var taarr=muiltlineData.join(",").split(",");//转化为一维数组
					var minnum = Math.min.apply(null,taarr);//最小值 */
					var maxnum = Math.max.apply(null,taarr);//最大值
					
					var muiltBarData=muiltData2.groupBar;
					var taarr2=muiltBarData.join(",").split(",");//转化为一维数组
					var maxnumBar = Math.max.apply(null,taarr2);//最大值
					
					var lineminnum = 0;
					if(minnum<0){
						lineXpos = 'center'
						/* if(Math.abs(minnum)>){
							
						} */
					}else{
						lineXpos = 'bottom'
					}
					
					var maxInNumbers = Math.max.apply(Math, arrTemp); //获取最大值
					/**格式化label**/
					var labelArrFor;
					labelArrFor = formatLennum(muiltData2.groupBar,maxnum);
					/* if(maxInNumbers.toString().length>=5){ */
					var	labelsunit = maxnumBar.toString().length>=5?'万':'';
					var firstArrvalL = labelArrFor[0].toString().length;
					var lastArrvalL = labelArrFor[labelArrFor.length-1].toString().length;
					var gutterL =  firstArrvalL>2?(firstArrvalL-2)*6+12:0+24;
					var gutterR =  lastArrvalL>2?(lastArrvalL-2)*12+24:0+24;
					
					/***格式化line**/
					var labelArrFor2;
					labelArrFor2 = formatLennum(muiltData2.groupline);
					//if(maxInNumbers.toString().length>=5){ 
					var	labelsunit2 = maxnum.toString().length>=5?'万':'';
					var firstArrvalL2 = labelArrFor2[0].toString().length;
					var lastArrvalL2 = labelArrFor2[labelArrFor2.length-1].toString().length;
					var gutterL2 =  firstArrvalL2>2?(firstArrvalL-2)*6+12:0+24;
					var gutterR2 =  lastArrvalL2>2?(lastArrvalL-2)*12+24:0+24;
					
					//var hMargin = barLen>1?20*barLen/2/barLen-(barLen>1?barLen-1*2:0):20;
					//var hMargin = barLen>1?20*barLen/2/barLen-(barLen>1?barLen-1*2:0):$("#hybridBlcav"+id).parents('.report-box').find(".temp01-wrap").width()/6*0.1;
					initcavW(muiltData2.muiltData,$("#hybridBlcav"+id),barLen,maxnumBar/10000,typeYstr[0]);
					var hMargin;
					var mainsWidth = $("div[data-id='"+id+"']").find(".temp01-wrap").width();
					var barWidth = barLen>1?(mainsWidth/6-20)/barLen-2:mainsWidth/6-20;
					var wrapcsvMain = $("div[data-id='"+id+"']").find("canvas:last").width();
					/* if(barLen>1){ */
						hMargin =barLen>1?mainsWidth/6*0.1/2:mainsWidth/6*0.1;
						if(muiltData2.muiltData.length>7){
							if(barLen>1){
								hMargin = (wrapcsvMain/muiltData2.muiltData.length-barWidth)/2/barLen
							}else{
								hMargin = (wrapcsvMain/muiltData2.muiltData.length-barWidth)/2
							}							
						}
					/* }else{
						hMargin = $("#hybridBlcav"+id).parents('.report-box').find(".temp01-wrap").width()/6*0.1
					} */
					/**获取格式化之后最长数字**/
					
					
			        var hybridBl = new RGraph.Bar({
			            id: 'hybridBlcav'+id,
			            data: id==7?labelArrFor:muiltData2.groupBar,
			            options: {
			                gutterTop: 40,
			                gutterLeft: 20,
			                colors: colorstr2,
			                shadow:false,
			                noyaxis: true,
			                ylabels: false,
			                //xaxispos:lineXpos,
			                hmargin: hMargin,
			                //hmargin: $("#hybridBlcav"+id).parents('.report-box').find(".temp01-wrap").width()/6*0.1,
			                //hmargin: ($("#hybridBlcav"+id).parents('.report-box').find(".temp01-wrap").width()/7-20)/2,
			                labels: muiltData2.muiltData,
			                //hmarginGrouped: 2,
			                labelsAboveSize:8,//数据size
			                textSize: 8,
			                labelsAboveColor:'#59c895',
			                strokestyle: 'rgba(0,0,0,0)',
			                backgroundGridVlines: false,//不显示网格y
			                backgroundGridBorder: false,//不显示网格边框
			                backgroundGridDashed:true,//虚线网格	
			                //unitsPost:typeYstr[0]
			            }
			        }).draw();
			        /**绘制所有line***/
			        var hybridBl2 = new RGraph.Line({
			            id: 'hybridBlcav'+id,
			            data: muiltData2.groupline,
			            options: {
			                gutterTop: 40,
			                //gutterLeft: gutterL2,
			                gutterLeft:20,
			                gutterRight:20,
			                //gutterRight: gutterR2,
			                colors:colorstr,
			                shadow:false,
			                noyaxis: true,
			                //noaxes: true,
			                ylabels: false,
			                //xlabels: false,
			                xaxispos:lineXpos,
			                labelsAbove:true,
			                labelsAboveColor:'#888888',
			                labelsAboveBackground:'transparent',//labelsAbove的背景
			                labelsAboveBorder:false,//labelsAbove的边框
			                //labelsAboveUnitsPost:labelsunit,
			                //hmargin: 22,
			                //hmargin: $("#hybridBlcav"+id).parents('.report-box').find(".temp01-wrap").width()/28,
			                //hmarginGrouped: 2,
			                labelsAboveSize:8,//数据size
			                textSize: 8,
			                //labelsAboveColor:'#59c895',
			                strokestyle: 'rgba(0,0,0,0)',
			                backgroundGridVlines: false,//不显示网格y
			                backgroundGridHlines: false,//不显示网格边框
			                backgroundGridDashed:true,//虚线网格
			                tickmarks: 'filledcircle',
			            }
			        }).draw();
			        /**绘制第一个Y轴**/
			        var muiltbarAxes = new RGraph.Drawing.YAxis({
			            id: 'hybridBly'+id,
			            x: 40,
			            textSize:7,
			            font:'simsun',
			            options: {
			                max: hybridBl.max,
			                //min:-3,
			                scaleZerostart: true,
			                labelsAboveUnitsPost:labelsunit,
			                textSize: 8,
			                colors: ['#888888'],
			                gutterTop:hybridBl.Get('gutterTop'),//上填充
//			                unitsPost: ((typeY!=0&&typeY=1)?'￥':'')
							unitsPre:typeYstr[0],
							unitsPost:id==7?labelsunit:''
	//		                numyticks: 10,//步长
			            }
			        }).draw();
			        /**绘制第二个Y轴**/
			        
			        if(minnum<0){
			        	/**计算有负值时y轴最小值和最大值基准**/
			        	if(Math.abs(minnum)<maxnum){
			        		lineminnum = -hybridBl2.max;
			        	}else{
			        		lineminnum = minnum;
			        	}
						
					}
					var shortnum = hybridBl2.max<=2?2:0;
			        //var numlabels = 
			        var muiltbarAxes2 = new RGraph.Drawing.YAxis({
			            id: 'hybridBlly'+id,
			            x: 40,
			            textSize:8,
			            options: {
			                max: hybridBl2.max,
			                //max: 10,
			                //scaleZerostart: false,
			                //labelsAboveUnitsPost:labelsunit2,
			                min:lineminnum,
			                colors: ['#888888'],
			                textSize: 8,
			                numlabels:shortnum,
			                gutterTop:hybridBl.Get('gutterTop'),//上填充
			                unitsPre: typeYstr[1]
//							unitsPost:(function(){
//								if(typeY2!=0&&typeY2=2){
//									return ''
//								}
//							})()
	//		                numyticks: 10,//步长
			            }
			        }).draw();
			        if(hybridBl2.max<=2){
			        	muiltbarAxes2.Set('numlabels', 2).draw()
			        }
			        

			        /**绘制自定义图例**/
			        var hybridBlkey = RGraph.HTML.Key('hybridBlKey'+id, {
		                colors: colorstr3,
		                labels:labelArr2,
		                tableClass: 'rgraph_key',
		                
		                blobCss: {
		                    'border-radius': '0'
		                },
		                blobClass: 'rgraph_key_blob',	                
		                labelCss: {
		                },
		                labelClass: 'rgraph_key_label'
		            });
	//	            $('.temp01-wrap').each(function(){
	//		        	$(this).css({
	//		        		"left":$(this).parents(".report-box").find("canvas:first").width()
	//		        	})
	//		        	
	//		        })
					$("#"+muiltbarAxes.id).parents('.report-box').addClass('cvsRandered');
					initWidth($("#hybridBlcav"+id));		
				}
			   		
			   	/**表格**/
			   	function initFixedtable(){
			   		initWidth($(".fixed-table"));	
			   	}
			   	
			   	
			   	/****/
			   	function initWidth(ele){
			   		var $parent = ele.parents(".report-box"),
			   			mainWidth = $parent.find(".rep-temp").width(),
			   			xaisW = $parent.find("canvas:first").width(),
			   			mainHeight = $parent.find("canvas:last").length?$parent.find("canvas:last").height():$parent.find(".fixed-table-det").height()
			   			maincvsW = mainWidth-xaisW;
			   			$parent.find('.temp01-wrap').css({
					   		"width":maincvsW,
					   		"left": xaisW
					   	});
					$parent.find(".temp01-wrapper:visible").css({
						'width':mainWidth,
						'height':mainHeight+60
					});
			   	}
		    //})();
			
	       
		</script>
	</body>

</html>