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
      	   	   <div class="store-left" dataId="5230">
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_1.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">金水桥干调</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right"  dataId="5232">
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_2.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">老陈干果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5234">
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_3.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">顺发海带批发</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5235" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_4.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">宋之洋水果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left"  dataId="5236" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_5.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">孙氏水果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right"  dataId="5237" >
      	   	   	   <div class="store-right-box">
      	   	   	    <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	    <img src="images/nsyimg/images/puyang_6.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">腾飞辣椒行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left"  dataId="5238" >
      	   	   	   <div class="store-left-box">
      	   	   	    <div class="bg_rgba">主营分类：干调</div>
      	   	   	    <img src="images/nsyimg/images/puyang_7.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">万隆干调</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5240" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_8.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">新世纪鲜果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5241" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：冻品</div>
      	   	   	     <img src="images/nsyimg/images/puyang_9.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">玉鑫冷冻</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5242" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：粮油</div>
      	   	   	     <img src="images/nsyimg/images/puyang_10.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">正宗许昌红薯粉条</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5246" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_11.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">二军瓜子行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5263" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_12.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">家辉干菜调料店</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5264" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_13.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">白堽香菇批发行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5265" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_14.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">张老有健康豆腐坊</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5268" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_15.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">香菇专卖</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5270" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_16.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">生姜批发行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5271" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_17.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">丽华包装批发</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5272" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_18.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">二飞包装批发</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5273" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：干调</div>
      	   	   	     <img src="images/nsyimg/images/puyang_19.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">龙源塑料</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5288" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_20.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">康氏洋果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5290" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_21.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">博博洋果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="5291" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_22.jpg"/>
      	   	   	   </div>
      	   	   	  <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">董氏洋果行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="5296" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_23.jpg"/>
      	   	   	   </div>
      	   	   	    <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">靳宝剑菜行</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="6962" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_24.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">特种蔬菜经销部</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="13571" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_25.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">鲜果宜人@</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="23220" >
      	   	   	   <div class="store-right-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_26.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">碧生馨园芽菜有限公司</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	   <div class="store-left" dataId="31072" >
      	   	   	   <div class="store-left-box">
      	   	   	     <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	     <img src="images/nsyimg/images/puyang_27.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-left-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">龙轩酱菜批发</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   	    <div class="store-right" dataId="33498" >
      	   	   	   <div class="store-right-box">
      	   	   	      <div class="bg_rgba">主营分类：果蔬</div>
      	   	   	      <img src="images/nsyimg/images/puyang_28.jpg"/>
      	   	   	   </div>
      	   	   	   <div class="store-right-name">
	      	   	   	      <ul class="store-left-cet clearfix">
	      	   	   	        <li class="store-left-list1"></li>
	      	   	   	        <li class="store-left-list2">蔬菜批发</li>
	      	   	   	        <li class="store-left-list3"></li>
	      	   	   	      </ul>
	      	   	   	  </div>
      	   	   </div>
      	   </div>
      	   <div class="gift-address">
      	   	   <p class="gift-img"><img src="images/nsyimg/images/address.png"/></p>
      	   	   <div class="gift-address-sp">
      	   	   	  <p class="gift-sp"><i class="fillet"></i>&nbsp;濮阳办事处</p>
      	   	   	  <p class="gift-addres-xq">地址：濮阳市宏进农副产品国际物流中心南环路与濮水路交叉口（原王助菜市场）<br/>
                                    联系人：刘增启 15139382833 &nbsp; &nbsp; &nbsp;鲁红旺 15303930677</p>
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
			   var marketName = "puyang";
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
		 
		/* var Terminal = {
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
			    };
		 */
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
