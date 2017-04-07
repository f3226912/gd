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
		
		<link rel="stylesheet" href="${CONTEXT}v1.0/module/mui/examples/hello-mui/css/mui.min.css">
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/report-det.css">
		<link rel="stylesheet" href="${CONTEXT}v1.0/js/plugs/pickadate/themes/default.css">
		<link rel="stylesheet" href="${CONTEXT}v1.0/js/plugs/pickadate/themes/default.date.css">
		
		<script src="${CONTEXT}v1.0/js/dialog.js"></script>
		<script src="${CONTEXT}v1.0/js/jquery-1.8.3.min.js"></script>		
		<script src="${CONTEXT}v1.0/js/plugs/pickadate/picker.js"></script>
    	<script src="${CONTEXT}v1.0/js/plugs/pickadate/picker.date.js"></script>
    	
    	<script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.common.core.js" ></script>
	    <script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.common.dynamic.js" ></script>
	    <script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.bar.js"></script>
	    <script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.common.effects.js"></script>
	    <script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.common.tooltips.js" ></script>
	    <script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.drawing.yaxis.js" ></script>
	    <script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.line.js" ></script>
	    
	    <!--环形图-->
	    <script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.common.key.js"></script>
		<script src="${CONTEXT}v1.0/module/RGraph/librariesmin/RGraph.pie.js"></script>
		<title>报表详情</title>
	</head>
	<body>
		<div class="rep-wrap">
			
			<!--<div class="mui-pull-top-pocket mui-block"><div class="mui-pull"><div class="mui-pull-loading mui-icon mui-icon-spinner"></div><div class="mui-pull-caption">下拉可以刷新</div></div></div>-->
			<div class="mui-pull-top-pocket mui-block mui-visibility loading-box">
				<div class="mui-pull">
					<div class="mui-pull-loading mui-icon mui-spinner" style="transition: -webkit-transform 0.3s ease-in; transform: rotate(180deg); animation: spinner-spin 1s step-end infinite;"></div>
					<div class="mui-pull-caption">正在加载...</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="reportId" name="reportId" value="${reportsId }"/>
		<div id="container"></div>
		<script>
		var firstreq = true,
		randerBoxarr = [];
	//mui初始化
//	mui.init({
//		swipeBack: true //启用右滑关闭功能
//	});
//	mui('.mui-scroll-wrapper').scroll();
//	mui('body').on('shown', '.mui-popover', function(e) {
//		//console.log('shown', e.detail.id);//detail为当前popover元素
//	});
//	mui('body').on('hidden', '.mui-popover', function(e) {
//		//console.log('hidden', e.detail.id);//detail为当前popover元素
//	});
//	
	
//	/*****/
	
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
//	   	var strDateS = new Date(oDate1[0] + "-" + oDate1[1] + "-" + oDate1[2]);
//	   	var strDateE = new Date(oDate2[0] + "-" + oDate2[1] + "-" + oDate2[2]);
	   	var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
			var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
	   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数 
	   return iDays ;
	}
	(function(){
		jQuery.extend( jQuery.fn.pickadate.defaults, {
		    monthsFull: [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
		    monthsShort: [ '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二' ],
		    weekdaysFull: [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
		    weekdaysShort: [ '日', '一', '二', '三', '四', '五', '六' ],
		    today: '今日',
		    clear: '清除',
		    close: '关闭',
		    firstDay: 1,
		    format: 'yyyy 年 mm 月 dd 日',
		    formatSubmit: 'yyyy/mm/dd'
		});
		
        [100,1,0],[200,0,1]
	})();
	var labelsArr = [],
		dataArr = [];
	/**首次请求**/
	(function(){
		var nowdata = new Date(),
		timeminSeconds = nowdata.getTime()-24*60*60*1000,
		starDatemSeconds = timeminSeconds-6*24*60*60*1000,
		nowdata = new Date(timeminSeconds);
		starDateT = new Date(starDatemSeconds),
		starDateM = starDateT.getMonth()<9?'0'+(starDateT.getMonth()+1):starDateT.getMonth()+1,
		starDateD = starDateT.getDate()<10?'0'+starDateT.getDate():starDateT.getDate(),
		endDateM = nowdata.getMonth()<9?'0'+(nowdata.getMonth()+1):nowdata.getMonth()+1,
		endDateD = nowdata.getDate()<10?'0'+nowdata.getDate():nowdata.getDate(),
		starDateval = starDateT.getFullYear()+'-'+starDateM+'-'+starDateD,
		endDateval =nowdata.getFullYear()+'-'+endDateM+'-'+endDateD;
		
		requestajax("reports/getReportData",{reportsId:$("#reportId").val(),menuId:getUrlmenu.menuId,startTime:starDateval,endTime:endDateval},1)
	})();
	
	
	
	/**声明日历控件**/
	function initDatepicker(ele){
		var nowdata = new Date(),
			timeminSeconds = nowdata.getTime()-24*60*60*1000,
			starDatemSeconds = timeminSeconds-6*24*60*60*1000,
			nowdata = new Date(timeminSeconds);
			starDateT = new Date(starDatemSeconds),
			starDateM = starDateT.getMonth()<9?'0'+(starDateT.getMonth()+1):starDateT.getMonth()+1,
			starDateD = starDateT.getDate()<10?'0'+starDateT.getDate():starDateT.getDate(),
			endDateM = nowdata.getMonth()<9?'0'+(nowdata.getMonth()+1):nowdata.getMonth()+1,
			endDateD = nowdata.getDate()<10?'0'+nowdata.getDate():nowdata.getDate(),
			starDateval = starDateT.getFullYear()+'-'+starDateM+'-'+starDateD,
			endDateval =nowdata.getFullYear()+'-'+endDateM+'-'+endDateD;
		ele.find( '.startDate' ).val(starDateval);
		ele.find( '.endDate' ).val(endDateval);
		var $startDate = ele.find( '.startDate' ).pickadate({
            format: 'yyyy-mm-dd',
            // min: [2015, 7, 14],
//            min: -6,
            container: '#container',
            // editable: true,
            closeOnSelect: true,
            closeOnClear: false,
            clear: '清除',
				close: '关闭',
				selectMonths: true,
				selectYears: true
        });
		var startDate = $startDate.pickadate('picker');
        startDate.set('disable', [
		  { from: [nowdata.getFullYear(), nowdata.getMonth(), nowdata.getDate()+1],to: [nowdata.getFullYear()+100,nowdata.getMonth(),nowdata.getDate()+1]}
		]);
        
		var $endDate = ele.find( '.endDate' ).pickadate({
            format: 'yyyy-mm-dd',
            // min: [2015, 7, 14],
            container: '#container',
            clear: '清除',
				close: '关闭',
				selectMonths: true,
				selectYears: true,
            // editable: true,
            closeOnSelect: true,
            closeOnClear: false
//	            min:$('.js-datepicker-star:visible').val()?$('.js-datepicker-star:visible').val():false


        });
        var endDate = $endDate.pickadate('picker');
        endDate.set('disable', [
		  { from: [nowdata.getFullYear(), nowdata.getMonth(), nowdata.getDate()+1],to: [nowdata.getFullYear()+100,nowdata.getMonth(),nowdata.getDate()+1]}
		]);
        if ( startDate.get('value') ) {
		  	endDate.set('min', startDate.get('select'))
		}
		if ( endDate.get('value') ) {
		  	startDate.set('max', endDate.get('select'))
		}
		
		// When something is selected, update the “from” and “to” limits.
		startDate.on('set', function(event) {
			var starttime = startDate.get('select','yyyy-mm-dd'),/**2016-03-08**/
				endTime = endDate.get('select','yyyy-mm-dd');
		  	if ( event.select ) {
		  		//console.log(startDate.get('select'))
		    	endDate.set('min', startDate.get('select'));
		    	if(endTime!=''){
		    		if(getDays(starttime,endTime)<6){
						alert('日期最小范围必须大于或等于7天', '提示');
						endDate.set(starttime, false)
						return false;								
				  	}else if(getDays(starttime,endTime)<=29){
				  		requestajax("reports/getReportData",{menuId:ele.attr("data-menuId"),startTime:starttime,endTime:endTime,reportsId:ele.attr("data-id")},2)
				  	}else if(getDays(starttime,endTime)>29){
				  		alert('日期最大范围为30天');
				  		endDate.set(starttime, false)
						return false;
				  	}
		    	}
		    	
		  	}
		  	else if ( 'clear' in event ) {
		    	endDate.set('min', false)
		  	}
		  	
		});
		endDate.on('set', function(event) {
			var starttime = startDate.get('select','yyyy-mm-dd'),
				endTime = endDate.get('select','yyyy-mm-dd');
		  	if ( event.select ) {
		    	startDate.set('max', endDate.get('select'));
		    	if(starttime!=''){
		    		if(getDays(starttime,endTime)<6){
						alert('日期最小范围必须大于或等于7天');
						startDate.set(endTime, false)
						return false;
				  	}else if(getDays(starttime,endTime)<=29){
				  		requestajax("reports/getReportData",{menuId:ele.attr("data-menuId"),startTime:starttime,endTime:endTime,reportsId:ele.attr("data-id")},2)
				  	}else if(getDays(starttime,endTime)>29){
				  		alert('日期最大范围为30天');
				  		endDate.set(endTime, false)
						return false;
				  	}						  	
		    	}
		  	}
		  	else if ( 'clear' in event ) {
		    	startDate.set('max', false)
		  	}
//		  	if(getDays(starttime,endTime)<6){
//				alert('日期最小范围必须大于或等于7天', '提示');
//				return false;
//		  	}
		})
	}
	
	/**请求数据**/			
	function requestajax(url,datas,num){
		$.ajax({
			type:"get",
			url:url,
			dataType:'json',
			data:datas,
//			async:false,
			success:function(data){
				var rows = data.rows;
//				for(var y = 0;y<data.rows.length;y++){
//					var dataresult = data.rows[y];
//					for(var i = 0;i<dataresult.proBszbankList.length;i++){
//						labelsArr.push(dataresult.proBszbankList[i].datatimes);
//						dataArr.push(dataresult.proBszbankList[i].orderNumbers)
//					}
//				}
//				var dataresult = data.rows.proBszbankList;
				
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
					initDatepicker($domdiv);
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
//		var ele = this.elements[i];
	    var rect = el.getBoundingClientRect();
	    return (
	    	rect.top >= 0&& rect.top<= H 
	    	
	    )
	}
	
	/**滚动触发逻辑**/
	function triggerWinScroll(randerBoxarr,data){
//		for(var z = 0,len3 = randerBoxarr.length;z<len3;z++){
//			if(randerBoxarr[z+1]!=undefined){
////					if(scrollTop+winHeight>=randerBoxarr[z].offsetT&&scrollTop+winHeight<randerBoxarr[z+1].offsetT){
//				//if(scrollTop+winHeight>=randerBoxarr[z].offsetT){
//					checkChart(randerBoxarr[z].type,randerBoxarr[z].id)
//				//}
//			}else{
//				checkChart(randerBoxarr[len3-1].type,randerBoxarr[len3-1].id)
//			}
//			
//		}				
		$(window).bind("scroll", function() {
			var scrollTop = $(window).scrollTop();
			var winHeight = $(window).height();
			for(var z = 0,len3 = randerBoxarr.length;z<len3;z++){
				//if(randerBoxarr[z+1]!=undefined){
//					if(scrollTop+winHeight>=randerBoxarr[z].offsetT&&scrollTop+winHeight<randerBoxarr[z+1].offsetT){
//					if(scrollTop+winHeight>=randerBoxarr[z].offsetT){
//						checkChart(randerBoxarr[z].type,randerBoxarr[z].id)
//					}
				if(!$("div[data-id='"+randerBoxarr[z].id+"']").hasClass("cvsRandered")){
					if(isElementInViewport($("div[data-id='"+randerBoxarr[z].id+"']"))){
						checkChart(randerBoxarr[z].type,randerBoxarr[z].id,data)
					}
//				}else{
//					checkChart(randerBoxarr[len3-1].type,randerBoxarr[len3-1].id)
				}
				
			}
		});
		$(window).trigger("scroll");
	}
	/**分析类型**/
	function checkChart(type,id,data){
//		if(!$("div[data-id='"+id+"']").hasClass("cvsRandered")){
//			inintChart(type)
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
//				case "8":
//					initMarketDonut(id,data)
//				break;
			}
//		}
	}
	
	function initcavW(datalab,cvs){
		var $parents =  cvs.parents(".report-box"),
			mainWidth = $parents.find(".rep-temp").width(),
			$xais = $parents.find("canvas:first"),
			$xais2 = $xais.next("canvas"),
//			xaisW = xais.width(),
			$cvsmain = $parents.find("canvas:last"),					
//			maincvsW = (mainWidth = 0?$(window).width()-40:mainWidth-20);
			maincvsW = mainWidth-20;
		if(datalab.length>7){
			cvs.css({
				"width":maincvsW/7*(datalab.length-7)+maincvsW,						
			    "height":'auto',
			    'min-height':'25rem',
			    'max-height':'29.5rem',
			    'min-width':'28rem',
			    'max-width':80*datalab.length+'px'
			});
			//$("#cvs").width(maincvsW/7*(dataArr.length-7)+maincvsW);
			$xais.css({
				"height":cvs.height(),
				'max-height':'29.5rem'
			});
			$xais2.css({
				"height":cvs.height(),
				'max-height':'29.5rem'
			});
		}else{
			cvs.css({
					"width":maincvsW+'px',
					"height":'auto',
					'min-height':'22rem',
					'min-width':'27rem',
					'max-width':80*datalab.length+'px'
					});
			$xais.css({
				"height":cvs.height(),
//				'max-width':80*dataArr.length+'px',
				'max-height':'29.5rem'
			});
			$xais2.css({
				"height":cvs.height(),
//				'max-width':80*dataArr.length+'px',
				'max-height':'29.5rem'
			});
		}
	};
	
	
		
		//console.log(labelsArr);
		var maincvsW = $(".rep-temp").width()-40;
//			mainWidth = $(".rep-temp").width();
//			winWidth = parseInt($(window).width());				
		
		/**格式化数据**/
//		function formatdataList(dataObj){
//			
//		}
		/***重绘前清除画布***/
		function clearCvs (ele){
//			if (typeof jQuery === "function" && ele instanceof jQuery) {
//		        ele = ele[0];
//		    }
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
				    			'<div class="mui-scroll">'+
									'<div class="pop-txt-cur">当前市场：全国市场</div>'+
									'<ul class="mui-table-view cus-market">'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+' table-view-cell-cur" data-mak="0"><a href="javascript:;">全部市场</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="1"><a href="javascript:;">玉林宏进农副产品大市场</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'"  data-mak="2"><a href="javascript:;">武汉白沙洲农副产品大市场</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="3"><a href="javascript:;">洛阳宏进农副产品国际物流中心</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="4"><a href="javascript:;">徐州农副产品中心批发市场</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="5"><a href="javascript:;">开封宏进农副产品国际物流中心</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="6"><a href="javascript:;">濮阳宏进农副产品国际物流中心</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="7"><a href="javascript:;">黄石宏进农副产品国际物流中心</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="8"><a href="javascript:;">中国东盟（钦州）农产品大市场</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="9"><a href="javascript:;">淮安宏进农副产品国际物流中心</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="10"><a href="javascript:;">盘锦宏进农副产品国际物流中心</a>'+
										'</li>'+
										'<li class="mui-table-view-cell table-view-cell'+idFlag+'" data-mak="11"><a href="javascript:;">郴州宏进农副产品国际物流中心</a>'+
										'</li>'+
									'</ul>'+
								'</div>'+
							'</div>',
				    //btn: ['确认', '取消'],
				    shadeClose: false,
				    style: 'width:100%;'
//				    yes: function(){
//				        layer.open({content: '你点了确认', time: 1});
//				    }, no: function(){
//				        layer.open({content: '你选择了取消', time: 1});
//				    }
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
					$(this).addClass("table-view-cell-cur").siblings().removeClass("table-view-cell-cur");
					$(this).parents("ul").siblings("div").text('当前市场：'+dataMarkTxt);
					$self.find(".markepopRes").text(dataMarkTxt);
					$self.attr("data-mak",dataMark);
					layer.close(markpop);
//					markpop.close();

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
					requestajax("reports/getReportData",requestData,2)
					
				});
			});
			
			ele.find(".checkUsertype").bind('click',function(){
				var $self2 = $(this),
					$checkUsertype = ele.find(".checkUsertype"),
					curUsertype = $checkUsertype.attr("data-user"),
					curUsertypeTxt = $checkUsertype.text();
				userTypepop = layer.open({
				    content: '<div class="csrolloutter csrolloutter2">'+
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
//				    btn: ['确认', '取消'],
				    shadeClose: false,
//				    yes: function(){
//				        layer.open({content: '你点了确认', time: 1});
//				    }, no: function(){
//				        layer.open({content: '你选择了取消', time: 1});
//				    }
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
//					markpop.close();

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
					requestajax("reports/getReportData",requestData,2)
					
				});
			});
			ele.find(".checkUserfrom").bind('click',function(){
				var $self3 = ele.find(".checkUserfrom"),
					$checkUserfrom = ele.find(".checkUserfrom"),
					curUserfrom = $checkUserfrom.attr("data-userfrom"),
					curUserfromTxt = $checkUserfrom.text();
//					0管理后台，1谷登农批网，2农速通，3农商友，4农商友-农批商，5 农批友，6供应商 
				userFrompop = layer.open({
				    content: '<div class="csrolloutter csrolloutter2">'+
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
//				    btn: ['确认', '取消'],
				    shadeClose: false,
//				    yes: function(){
//				        layer.open({content: '你点了确认', time: 1});
//				    }, no: function(){
//				        layer.open({content: '你选择了取消', time: 1});
//				    }
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
//					markpop.close();
					
					
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
					requestajax("reports/getReportData",requestData,2)
				});
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
							'<input type="text" placeholder="开始时间" value="2016-03-03" class="startDate temp-input">'+
							'<span class="datep-ico"></span>'+
						'</span>'+						
						'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
						'<span class="input-wrap">'+
							'<input type="text" placeholder="结束时间" value="" class="endDate temp-input">'+
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
						    '<canvas id="axes'+idFlag+'" width="41" height="480" style="position: absolute;  left: 0; z-index: 3"></canvas>'+
						    '<div style="width: 600px; overflow: auto; position: absolute; left: 41px" class="temp01-wrap">'+
						        '<canvas id="cvs'+idFlag+'" width="1000" height="480"></canvas>'+
						    '</div>'+
						'</div>'+
					'</div>'+
				'</div>'+
			'</div>';
			$(".rep-wrap").append(temp);
			initTooltemp($("div[data-id='"+idFlag+"']"),show);
			initprop($("div[data-id='"+idFlag+"']"),idFlag);
		}
		/**单条折线(type=1)**/
		function initLine(id,datalist){
			var datalistTemp = datalist.data[0];
			var labelArr1 = [];
			var labelArr2 = [];
			var colorstr = '';
//			var typegraph = 1;
//			var typeY = 1;
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
//					if(jsonData[l]){
						//console.log(jsonData[l]);
						labelArr1 = jsonData.data;
						colorstr = jsonData.color;
//						typegraph = jsonData.type;
//						typegraph = jsonData.typeY;
//					}
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
//	        document.getElementById("num_updates").innerHTML =
//	            parseInt(document.getElementById("num_updates").innerHTML) + 1;
//	
//	        RG.clear(document.getElementById("cvs"+id));
//	        RG.clear(document.getElementById("axes"+id));
			initcavW(labelArr1,$("#cvs"+id));
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
//		        $("div[data-id='"+id+"']").find(".muiltLineKey"+id).html('')
			}
			$("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
//			
			line = new RGraph.Line({
	            id: 'cvs'+id,
	            data: labelArr1,
	            options: {
	                labels: datalist.parms,
	                tooltips: labelArr1,
	                tooltipsCoordsPage: true,
	                noxaxis: true,
	                noyaxis: true,
	                ylabels: false,
//		                ylabelsCount: 8,
	                vmargin: 30,
	                gutterTop:60,//上填充
	                gutterBottom:50,//上填充
//		                shadow: false,
	                labelsAbove: true,
	                labelsAboveSize:20,//折线数据size
	                labelsAboveColor:colorstr,
	                labelsAboveBackground:'transparent',//labelsAbove的背景
	                labelsAboveBorder:false,//labelsAbove的边框
	                backgroundGridDashed:true,//虚线网格
//		                yaxispos:'right',
	                colors: [colorstr],
	                linewidth: 1,//折线宽度
	                backgroundGridVlines: false,//不显示网格y
	                backgroundGridBorder: false,//不显示网格边框
	                textSize: 16,//全局字体大小
	                textColor: '#888',
	                //backgroundGrid: false		                
	                tickmarks: 'filledcircle',
	            }
	        }).trace2();
			yaxis = new RGraph.Drawing.YAxis({
	            id: 'axes'+id,
//						id: idx,
	            x: 40,
	            options: {
	            	
	                max: line.max,
//							max:d1,
	                colors: ['#888888'],
	                gutterTop:60,//上填充
	                gutterBottom:50,//上填充
	                unitsPre:typeYstr[0]
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
							'<input type="text" placeholder="开始时间" value="" class="startDate temp-input">'+
							'<span class="datep-ico"></span>'+
						'</span>'+						
						'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
						'<span class="input-wrap">'+
							'<input type="text" placeholder="结束时间" value="" class="endDate temp-input">'+
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
						    '<canvas id="muiltLineaxes'+idFlag+'" width="41" height="480" style="position: absolute;  left: 0; z-index: 3"></canvas>'+
						    '<div style="width: 600px; overflow: auto; position: absolute; left: 41px" class="temp01-wrap">'+
						        '<canvas id="muiltLinecvs'+idFlag+'" width="1000" height="480"></canvas>'+
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
        	var datalistTemp = datalist.data[0];
        	var parms = datalist.parms;
        	var muiltlineData = {}
		   		muiltlineData.groupline = [];
		   		muiltlineData.muiltDate = parms;
		   	
//			var labelArr1 = [];
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
//				"data1": {"data":[10,9,80,70,30,56,40,80,10,10],"color":"red","type":"1","typeY":"1"}"{\"data\":[10,9,80,70,30,56,40,80,10,10],\"color\":\"red\",\"type\":\"1\",\"typeY\":\"1\"}"
				colorsarr.push(datalistTemp[j].color);
				if(datalistTemp[j].parms){
					labelArr2.push(datalistTemp[j].parms);
				}
				if(datalistTemp[j].typeY=='1'){
					typeY = datalistTemp[j].typeY;
				}else if(datalistTemp[j].typeY=='2'){
					typeY2 = datalistTemp[j].typeY;
				}
//				labelArr2.push()
				for(var l in jsonData){
//					if(jsonData[l]){
//						console.log(jsonData[l]);
//						if(l = "data"){
//							muiltlineData.groupline.push(jsonData.data)
//						}
						
//						colorstr = jsonData.color;
						
						typegraph = jsonData.type;
						typegraph = jsonData.typeY;
//					}
//					switch (jsonData[l]){
//						case value:
//							break;
//						default:
//							break;
//					}
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
		   	initcavW(muiltlineData.muiltDate,$("#muiltLinecvs"+id));
//		    console.log(muiltlineData.groupline);			
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
	                colors: colorsarr,
	                linewidth: 1,//折线宽度
	                backgroundGridVlines: false,//不显示网格y
	                backgroundGridBorder: false,//不显示网格边框
	                textSize: 16,//全局字体大小
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
	            options: {
	            	
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
					'<canvas id="marketDonut'+idFlag+'" width="475" height="350">'+
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
	                textSize:16,
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
							'<input type="text" placeholder="开始时间" value="" class="startDate temp-input">'+
							'<span class="datep-ico"></span>'+
						'</span>'+
						'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
						'<span class="input-wrap">'+
							'<input type="text" placeholder="结束时间" value="" class="endDate temp-input">'+
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
					    '<canvas id="barinbarAxes'+idFlag+'" width="41" height="480" style="position: absolute;  left: 0; z-index: 3"></canvas>'+
					    '<div style="width: 600px; overflow: auto; position: absolute; left: 41px" class="temp01-wrap">'+
					        '<canvas id="barinbar'+idFlag+'" width="1000" height="480"></canvas>'+
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
							'<input type="text" placeholder="开始时间" value="" class="startDate temp-input">'+
							'<span class="datep-ico"></span>'+
						'</span>'+
						'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
						'<span class="input-wrap">'+
							'<input type="text" placeholder="结束时间" value="" class="endDate temp-input">'+
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
					    '<canvas id="baronbarAxes'+idFlag+'" width="41" height="480" style="position: absolute;  left: 0; z-index: 3"></canvas>'+
					    '<div style="width: 600px; overflow: auto; position: absolute; left: 41px" class="temp01-wrap">'+
					        '<canvas id="baronbar'+idFlag+'" width="1000" height="480"></canvas>'+
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
			var datalistTemp = data.data[0];
			var baronbarData = {};
//		   		baronbarData.group = [[10,8,5],[10,30,40],[60,30,2],[44,11,21],[71,52,70],[71,52,70],[10,8,5],[10,47,30],[60,38,10],[5,53,10]];//数据实例
//		   		baronbarData.muiltData = ['1501','1502','1503','1504','1505','1506','1507','1508','1509','1510'];//数据实例
		   		baronbarData.group = [];
		   		baronbarData.muiltData = data.parms;
//		   	var labelArr1 = [];
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
//						labelArr1 = jsonData.data;
						typegraph = jsonData.type;
						typeY = jsonData.typeY;
//						colorstr
//					}
//					arrtemp.push(arrtemp2);
				}
//				console.log(arrtemp)
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
//			console.log(arrTemp2);
			baronbarData.group = arrTemp2;
			initcavW(baronbarData.muiltData,$("#baronbar"+id));
			if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
				var baronbars = document.getElementById("barinbar"+id);
				var baronbarAxess = document.getElementById("baronbarAxes"+id);
				RGraph.Reset(baronbars);
		        RGraph.Reset(baronbarAxess);
		        $("div[data-id='"+id+"']").find("#multbarKey"+id).html('')
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
	                //hmargin: 22,
	                hmargin: $("#baronbar"+id).parents('.report-box').find(".temp01-wrap").width()/28,
	                labels: baronbarData.muiltData,
//		                labelsAbove: barinbardata.shipped,
	                labelsAboveSize:16,//数据size
	                labelsAboveColor:'#59c895',
	                strokestyle: 'rgba(0,0,0,0)',
	                backgroundGridVlines: false,//不显示网格y
	                backgroundGridBorder: false,//不显示网格边框
	                backgroundGridDashed:true,//虚线网格		                
	            }
	        }).on('draw', function (obj)
	        {
	            for (var i=0; i<obj.coords.length; ++i) {
	                obj.context.fillStyle = 'white';
	                RGraph.Text2(obj.context, {
	                    font:'Verdana',
	                    'size':16,
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
	            x: 40,
	            options: {
	                max: baronbar.max,
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
            $("#"+baronbarkey.id).parents('.report-box').addClass('cvsRandered');
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
							'<input type="text" placeholder="开始时间" value="" class="startDate temp-input">'+
							'<span class="datep-ico"></span>'+	
						'</span>'+
						'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
						'<span class="input-wrap">'+
							'<input type="text" placeholder="结束时间" value="" class="endDate temp-input">'+
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
					    '<canvas id="multbarX'+idFlag+'" width="41" height="480" style="position: absolute;  left: 0; z-index: 3"></canvas>'+
					    '<div style="width: 600px; overflow: auto; position: absolute; left: 41px" class="temp01-wrap">'+
					        '<canvas id="multbarcav'+idFlag+'" width="1000" height="480"></canvas>'+
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
		   	//$('.temp01-wrap').width(maincvsW);
//		   	[[10,8,5,13,],[10,30,40,50],[60,30,2,8],[44,11,21,60],[71,52,70,30],[71,52,70,30],[10,8,5,4],[10,47,30,70],[60,38,10,77],[5,53,10,30]]
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
//						colorstr
//					}
//					arrtemp.push(arrtemp2);
				}
				if(datalistTemp[j].typeY=='1'){
					typeY = datalistTemp[j].typeY;
				}else if(datalistTemp[j].typeY=='2'){
					typeY2 = datalistTemp[j].typeY;
				}
//				console.log(arrtemp)
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
//			console.log(arrTemp2);
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
							'<input type="text" placeholder="开始时间" value="" class="startDate temp-input">'+
							'<span class="datep-ico"></span>'+
						'</span>'+
						'<span class="input-tit"><span class="input-titinner">结束时间</span></span>'+
						'<span class="input-wrap">'+
							'<input type="text" placeholder="结束时间" value="" class="endDate temp-input">'+
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
					    '<canvas id="hybridBly'+idFlag+'" width="41" height="480" style="position: absolute;  left: 0; z-index: 3"></canvas>'+
					    '<canvas id="hybridBlly'+idFlag+'" width="41" height="480" style="position: absolute;  right: 0; z-index: 3"></canvas>'+
					    '<div style="width: 600px; overflow: auto; position: absolute; left: 41px" class="temp01-wrap">'+
					        '<canvas id="hybridBlcav'+idFlag+'" width="1000" height="480"></canvas>'+
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
			var datalistTemp = data.data[0];
        	var parms = data.parms;
		   	var muiltData2 = {}
		   		muiltData2.groupBar = [[500,1000],[1170,700],[3000,2000],[4000,4200],[710,520],[710,3000],[1700,800],[1000,470],[600,380],[500,530]];//实例
		   		muiltData2.groupline = [[10,8,5,13,10,30,40,50,60,30],[7,15,10,18,7,35,45,45,55,35]];
		   		muiltData2.muiltData =  parms;
		   	var labelArr1 = [];
			var labelArr2 = [];//图例title
			var colorstr = [];//颜折线色
			var colorstr2 = [];//条形颜色
			var colorstr3 = [];//图例颜色
//			var typegraph = 1;
			var typeY = 0;//1,金额；2,普通数字
			var typeY2 = 0;//1,金额；2,普通数字
			var typeYstr = [];
			var arrTemp = [];//折线不用格式化数据
			var arrTemp3 = [];//条形缓存一级以备格式化
			var arrTemp2 = [];
			
			var boolFlag = [];
			var boolFlags = 0;
//			var typeYstr2 = '';
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
					colorstr.push(datalistTemp[j].color)
				}else if(datalistTemp[j].type=='2'){
					arrTemp3.push(datalistTemp[j].data);
					colorstr2.push(datalistTemp[j].color)
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
//						typegraph = jsonData.type;
//						typeY = jsonData.typeY;
//						colorstr
//					}
//					arrtemp.push(arrtemp2);
				}
//				console.log(arrtemp)
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
			
			for(var z = 0;z<arrTemp3[0].length;z++){	
				var arr = [];
				for(var y = 0;y<arrTemp3.length;y++){
					arr.push(arrTemp3[y][z])
				}
				arrTemp2.push(arr);
			}
//			console.log(arrTemp2);
			muiltData2.groupline = arrTemp;
			muiltData2.groupBar = arrTemp2;
//			muiltData2.muiltData = labelArr2;
			if(typeY!=0&&typeY==1){
				typeYstr.push('￥')
			}
			if(typeY2!=0&&typeY2==2){
				typeYstr.push('')
			}
		   	initcavW(muiltData2.muiltData,$("#hybridBlcav"+id));
		   	if($("div[data-id='"+id+"']").hasClass("cvsRandered")){
		   		var hybridBlcavs = document.getElementById("hybridBlcav"+id);
		   		var hybridBlys = document.getElementById("hybridBly"+id);
		   		var hybridBllys = document.getElementById("hybridBly"+id);
				RGraph.Reset(hybridBlcavs);
		        RGraph.Reset(hybridBlys);
		        RGraph.Reset(hybridBllys);
		        $("div[data-id='"+id+"']").find("#hybridBlKey"+id).html('')
			}
		   	$("div[data-id='"+id+"']").find(".loading-box").removeClass("mui-visibility");
		   	
		   	//var barWrapw = $(".barcav-wrap").width();
		    //$("#barinbar").width(barWrapw);
			/**绘制所有bar**/
	        var hybridBl = new RGraph.Bar({
	            id: 'hybridBlcav'+id,
	            data: muiltData2.groupBar,
	            options: {
	                gutterTop: 40,
	                gutterLeft: 0,
	                colors: colorstr2,
	                shadow:false,
	                noyaxis: true,
	                ylabels: false,
	                //hmargin: 22,
	                hmargin: $("#hybridBlcav"+id).parents('.report-box').find(".temp01-wrap").width()/28,
	                labels: muiltData2.muiltData,
	                hmarginGrouped: 2,
	                labelsAboveSize:16,//数据size
	                labelsAboveColor:'#59c895',
	                strokestyle: 'rgba(0,0,0,0)',
	                backgroundGridVlines: false,//不显示网格y
	                backgroundGridBorder: false,//不显示网格边框
	                backgroundGridDashed:true,//虚线网格	
	                unitsPost:typeYstr[0]
	            }
	        }).draw();
	        /**绘制所有line***/
	        var hybridBl2 = new RGraph.Line({
	            id: 'hybridBlcav'+id,
	            data: muiltData2.groupline,
	            options: {
	                gutterTop: 40,
	                gutterLeft: 0,
	                colors: colorstr,
	                shadow:false,
	                noyaxis: true,
	                noaxes: true,
	                ylabels: false,
	                xlabels: false,
	                //hmargin: 22,
	                hmargin: $("#hybridBlcav"+id).parents('.report-box').find(".temp01-wrap").width()/28,
	                hmarginGrouped: 2,
	                labelsAboveSize:16,//数据size
	                labelsAboveColor:'#59c895',
	                strokestyle: 'rgba(0,0,0,0)',
	                backgroundGridVlines: false,//不显示网格y
	                backgroundGridHlines: false,//不显示网格边框
	                tickmarks: 'filledcircle',
	            }
	        }).draw();
	        /**绘制第一个Y轴**/
	        var muiltbarAxes = new RGraph.Drawing.YAxis({
	            id: 'hybridBly'+id,
	            x: 40,
	            options: {
	                max: hybridBl.max,
	                colors: ['#888888'],
	                gutterTop:hybridBl.Get('gutterTop'),//上填充
//	                unitsPost: ((typeY!=0&&typeY=1)?'￥':'')
					unitsPre:typeYstr[0],
//		                numyticks: 10,//步长
	            }
	        }).draw();
	        /**绘制第二个Y轴**/
	        var muiltbarAxes = new RGraph.Drawing.YAxis({
	            id: 'hybridBlly'+id,
	            x: 40,
	            options: {
	                max: hybridBl2.max,
	                colors: ['#888888'],
	                gutterTop:hybridBl.Get('gutterTop'),//上填充
	                unitsPre: typeYstr[1]
//					unitsPost:(function(){
//						if(typeY2!=0&&typeY2=2){
//							return ''
//						}
//					})()
//		                numyticks: 10,//步长
	            }
	        }).draw();
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