<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
<base href="${CONTEXT }">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>农商友新福利,首单送大礼!</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<link rel="stylesheet" type="text/css" href="css/nsy/appshare/style.css"/>
	<style>
	
	</style>
</head>
<body>
<div class="header-t" style="width:100%; height:5rem; display: none; position:fixed; top:0; z-index:9999;background:rgba(0,0,0,0.7);">
       	    <img src="images/nsyimg/images/tit-2.png" style="width:16.9rem; height:3.2rem; padding:1rem 0 0 1.5rem; text-align:left;" >
       	    <a id="download" style="position: absolute; top:1.35rem; background:#3bb650; width:4.6rem; height:2.4rem; font-size:1.4rem; line-height:2.4rem; text-align:center; border-radius: .5rem;right: 1rem;color:#fff">下载</a>
       </div>

       <!-- banenr广告 -->
       <div class="top-activity">
          <a class="guize" href="${CONTEXT}activityAppshare/getActivitySpecification"><img src="images/nsyimg/images/guize.png"></a>
       	  <div class="banner">
       	  	<img src="images/nsyimg/images/img_t.jpg">    
       	  </div>
          <!-- 倒计时 -->
          <div class="countdown">
       	       <img class="img_tm" src="images/nsyimg/images/img-tm.png">
               <ul class="list-time">
               	 <li class="li-a"><span class="li-time-t">0</span><span class="black-t">天</span></li>
               	 <li class="li-b"><span class="li-time-h">00</span><span class="black-t">时</span></li>
               	 <li class="li-c"><span class="li-time-m">00</span><span class="black-t">分</span></li>
               	 <li class="li-d"><span class="li-time-s">00</span><span class="black-t">秒</span></li>
               </ul>
          </div>
          <div class="activity-time">
              <a class="but-share"><img src="images/nsyimg/images/img_04.png"/></a>
          </div>
       </div>
      <!-- 商铺列表 -->
      <div class="store clearfix">
      	   <h3 class="store-name">推荐优质商铺</h3>
      	   <div class="store-sp">
      	   	   <div class="store-left" dataId="31925">
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：粮油</div>
      	   	   	     <img src="images/nsyimg/images/enshi_1.jpg"/>
      	   	   	   </div>
	      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">邓姐兽药销售服务有限公司</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right"  dataId="31756">
      	   	   	   <div class="store-right-box">
      	   	   	      <div class="bg_rgba">主营分类：副食</div>
      	   	   	      <img src="images/nsyimg/images/enshi_2.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">广缘商贸</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="31757">
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：酒水</div>
      	   	   	     <img src="images/nsyimg/images/enshi_3.jpg"/>
      	   	   	    </div>
      	   	   	    <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">湖北野三关酒业</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="31589" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/enshi_4.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">恩施胜远塑料袋厂</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left"  dataId="31586" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：粮油</div>
      	   	   	     <img src="images/nsyimg/images/enshi_5.jpg"/>
      	   	   	    </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">曾氏粮行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right"  dataId="32015" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：粮油</div>
      	   	   	     <img src="images/nsyimg/images/enshi_6.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">谭大姐粮油</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left"  dataId="31639" >
      	   	   	   <div class="store-left-box">
      	   	   	      <div class="bg_rgba">主营分类：水果</div>
      	   	   	      <img src="images/nsyimg/images/enshi_7.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">童老幺干鲜果品批零……</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>

      	   	   </div>
      	   	    <div class="store-right" dataId="31703" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：蔬菜</div>
      	   	   	     <img src="images/nsyimg/images/enshi_8.jpg"/>
      	   	   	   </div>
      	   	   	    <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">苏波湘菜餐料厅</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="31666" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：水果</div>
      	   	   	     <img src="images/nsyimg/images/enshi_9.jpg"/>
      	   	   	    </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">老朱精品果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="31986" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：水果</div>
      	   	   	     <img src="images/nsyimg/images/enshi_10.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">幸福果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-left" dataId="31709" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：副食</div>
      	   	   	     <img src="images/nsyimg/images/enshi_11.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">恩施州乡巴佬总代理</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="31693" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/enshi_12.jpg"/>
      	   	   	    </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">周记福厨</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>

      	   	   </div>
      	   	    <div class="store-left" dataId="31672" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：水果</div>
      	   	   	     <img src="images/nsyimg/images/enshi_13.jpg"/>
      	   	   	   </div>
      	   	   	    <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">四季果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="31580" >
      	   	   	   <div class="store-right-box">
      	   	   	      <div class="bg_rgba">主营分类：粮油</div>
      	   	   	      <img src="images/nsyimg/images/enshi_14.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">庆丰年粮行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="32124" >
      	   	   	   <div class="store-left-box">
      	   	   	      <div class="bg_rgba">主营分类：水果</div>
      	   	   	      <img src="images/nsyimg/images/enshi_15.jpg"/>
      	   	   	    </div>
      	   	   	    <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">维维果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="31735" >
      	   	   	   <div class="store-right-box">
      	   	   	      <div class="bg_rgba">主营分类：蔬菜</div>
      	   	   	      <img src="images/nsyimg/images/enshi_16.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">蔬菜批发配送中心</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    
      	   </div>
      	   <div class="gift-address">
      	   	   <p class="gift-img"><img src="images/nsyimg/images/address.png"/></p>
      	   	   <div class="gift-address-sp">
      	   	   	  <p class="gift-sp"><i class="fillet"></i>&nbsp;恩施办事处</p>
      	   	   	  <p class="gift-addres-xq">地址：恩施市土桥坝马鞍山路41号华硒生态市场结算中心3楼<br/>
                                    联系人：李杨 13871174900</p>
      	   	   </div>
      	   </div>
      </div>
      <!--底部-->
      <div class="footer">
      	    <div class="weixi"><img src="images/nsyimg/images/wiexi.png"/></div>
      	    <p class="activity-rules">1.  本次活动的最终解释权归主办方深圳谷登科技有限公司所有。<br/>
2.  对于任何通过不正当手段参加活动者，主办方有权在不事先<br/>
     通知的前提下取消其参加活动的资格。<br/>
3.  如遇不可抗因素，主办方有权提前结束或终止本次活动。<br/>
4.  每个用户限参与一次首单活动和分享活动，多次参加无效。</p>
      <p class="ft-gd-rules">深圳谷登科技有限公司<br/>
©2015-2016 gdeng.cn co. All Rights Reserved</p>
      </div>
		<!--<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>-->
		<script>
		 var memberId = '${memberId }';
		 var marketId ='${marketId }';
		 if(memberId && marketId){
			 $(".header-t").show();
		 }
		//倒计时
	        function getRTime() {
	        	function checkTime(i){
	/*         		if(i<0){
	        			i=i*-1;
	        		}
					if(i<10){
						i="0"+i;
					} */
					return i;
				}
	            var now = new Date().getTime();
	        	var date2 = Date.parse('2016/08/18 09:00:00')
	        	var t = date2-now;
	        	if(t>=0){
	        		var d=Math.floor(t/1000/60/60/24);
	                var h=Math.floor(t/1000/60/60%24);
	                var m=Math.floor(t/1000/60%60);
	                var s=Math.floor(t/1000%60);
	                document.querySelector('.li-time-t').innerHTML = checkTime(d);
	                document.querySelector('.li-time-h').innerHTML = checkTime(h);
	                document.querySelector('.li-time-m').innerHTML = checkTime(m);
	                document.querySelector('.li-time-s').innerHTML = checkTime(s);
	                var timer = setTimeout(getRTime,1000);
	                document.querySelector('.img_tm').style.display='block'; 
	                document.querySelector('.countdown').style.display='block';

	        	}else{
	                document.querySelector('.img_tm').style.display='none'; 
	                document.querySelector('.countdown').style.display='none';
	                clearTimeout(timer);
	        		
	        	}

	            
	        }
        setTimeout(getRTime,1000);
        //点击分享功能
		$(".but-share").on("click",function(){
			if(memberId && marketId){
				window.location.href="http://www.gdeng.cn/nsy/qdb/nsy_shoudanhuodong.html";
			}else{
				var marketName = "enshi";
				shareClick(marketName);
			}
		});
        
        function shareClick(marketName){
			JsBridge.shareClick(marketName);
		}
        
        //点击跳转到店铺
		$(".store-left,.store-right").on('click',function(){
			//如果是从分享点击进来的，需要跳转到下载页面
			if(memberId && marketId){
				window.location.href="http://www.gdeng.cn/nsy/qdb/nsy_shoudanhuodong.html";
			}else{
				var shopId = this.getAttribute("dataId");
				gotoshareshop(shopId);
			}
		});
        
		 function gotoshareshop(shopId){
			JsBridge.gotoshareshop(shopId);
		}
		
		 var url = "${CONTEXT }activityAppshare/updateViewCount";
		 if(memberId && marketId){
			 jQuery.post(url,{"memberId":memberId,"marketId":marketId},function(data){
 				if (data == "success"){
				
 				}
			 });
		 }
		 
		 /*var Terminal = {
			        // 辨别移动终端类型
			        platform : function(){
			            var u = window.navigator.userAgent.toLowerCase(), app = navigator.appVersion;
			            return {
			            	//Windows
			            	isWin: (navigator.platform == "Win32") || (navigator.platform == "Windows"),
			            	//MAC
			            	isMac: (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel"),
			                // android终端或者uc浏览器
			                android: (u.indexOf('android') > -1 || u.indexOf('linux') > -1) && u.indexOf('micromessenger')<0,
			                // 是否为iPhone或者QQHD浏览器
			                iPhone: u.indexOf('iphone') > -1 ,
			                // 是否iPad
			                iPad: u.indexOf('ipad') > -1,
							// 是否是通过微信的扫一扫打开的
//			                weChat: u.indexOf('MicroMessenger') > 0
							weChat: (function (){
							    //var ua = window.navigator.userAgent.toLowerCase();
							    if(u.indexOf('micromessenger')>-1){
							        return true;
							    }else{
							        return false;
							    }
							})()
			            };
			        }(),
			        // 辨别移动终端的语言：zh-cn、en-us、ko-kr、ja-jp...
			        language : (navigator.browserLanguage || navigator.language).toLowerCase()
			    };*/
			    
		 $('#download').on('click',function(){
				/*if(Terminal.platform.iPhone || Terminal.platform.iPad || Terminal.platform.isMac){
	        		$("#download").attr("href","https://itunes.apple.com/cn/app/id1057048929?mt=8&l=cn");
	        	}else if(Terminal.platform.android || Terminal.platform.isWin){
	        		$("#download").attr("href","http://www.gdeng.cn/nsy/nsy_v1.2.9.apk");
	        	}*/
			 $("#download").attr("href","http://www.gdeng.cn/nsy/qdb/nsy_shoudanhuodong.html");
			})
			
		</script>
	<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>
