<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/tags.inc" %>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/global.m.css"/>
		<link rel="stylesheet" type="text/css" href="${CONTEXT}css/nsy-kaif.css"/>
		<script src="${CONTEXT}js/jquery-1.8.3.min.js" type="text/javascript" charset="utf-8"></script>
<%@ include file="../pub/head.inc" %>
<link rel="stylesheet" href="${CONTEXT}v1.0/css/home.css">			
	</head>
	<body>
	<jsp:include page="/WEB-INF/jsp/pub/head.jsp"></jsp:include>
	<div class="bg-head">
		<div class="down-count">
			<span>
				<em>02</em>天
				<em>16</em>时
				<em>34</em>分
				<em>34</em>秒
			</span>
		</div>
	</div>
	<div class="bg-box">
		<div class="wrap1280 box">
			<a href="http://www.gdeng.cn/nsy/qdb/nsy_shoudanhuodong.html"></a>
			<p>
				活动参与时间：8月18日-12月31日<br/>
				礼品领取时间：8月18日-12月31日
			</p>
			<span>
				分享赠送：活动期间凡成功分享活动页面至<em>微信朋友圈</em>，可额外获得一份精美礼品，每个用户仅限参加一次，先到先得，送完即止。
			</span>
		</div>
	</div>
	<div class="bg-main">
		<div class="main">
			<div class="store-title">
				推荐优质商铺
			</div>
			<div class="store-lay clear">
				<div class="lay-left">
					<a href="http://www.gdeng.cn/kaifeng/business/shop/33785.html">
						<img src="${CONTEXT}images/tuiguang0811/11.jpg" alt="" />
						<div class="grey">
							主营分类：干粮、粮油
						</div>
					</a>
				</div>
				<div class="lay-right">
					<p>
						<span class="point"></span>
						<span>开封办事处</span><br/>
						开封市禹王台区310国道芦花岗转盘东2公里，开封宏进农副产品国际物流中心C区<br/>
						C14--124<br/>
						<i>联系人：丁广信 13733838485</i><br/>
						<em>刘举15637808808</em>
					</p>
				</div>
			</div>
		</div>
		<div class="wrap1280 activity-obj">
			<p>
				活动参与对象：<br/>
				所有农商友注册用户（老用户可直接参与、新用户需先下载并注册成为农商友会员）。
				<em></em>
			</p>
			<p>
				活动参与有效日期：<br/>
				8月18日——12月31日<br/>
				活动礼品领取有效日期：<br/>
				8月18日——12月31日
				<em></em>
			</p>
			<span>如有任何疑问，可以联系市场活动负责人，也可以拨打客服电话4007-600-800。</span>
		</div>
	</div>
	<div class="bg-foot">
		<div class="wrap1280 clear">
			<img src="${CONTEXT}images/tuiguang0811/code.jpg" alt="" class="fl" />
			<div class="tips fl">
				<p>
					1.  本次活动的最终解释权归主办方深圳谷登科技有限公司所有。<br/>
					2.  对于任何通过不正当手段参加活动者，主办方有权在不事先通知的前提下取消其参加活动的资格。<br/>
					3.  如遇不可抗因素，主办方有权提前结束或终止本次活动。<br/>
					4.  每个用户限参与一次首单活动和分享活动，多次参加无效。
				</p>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
	<script src="${CONTEXT}js/nsy-hd.js" type="text/javascript" charset="utf-8"></script>
	<script>
	(function(){
		function timerFn(){
			var nowDate = new Date().getTime();
			beginDate = new Date('2016-08-18 09:00:00').getTime();
			//dateline = new Date('2016-12-31 00:00:00').getTime();
			//活动未开始
			if(nowDate<beginDate){
				var timeLag = (beginDate-nowDate)/1000;

				var day = Math.floor( timeLag / 60 / 60 / 24);
					hours = Math.floor( timeLag /60 / 60 % 24);
					min = Math.floor( timeLag /60 % 60);
					second = Math.floor( timeLag % 60);
				day = checkTime(day);
				hours = checkTime(hours);
				min = checkTime(min);
				second = checkTime(second);

				var timeString = '<em>'+day+'</em>天<em>'+hours+'</em>时<em>'+min+'</em>分<em>'+second+'</em>秒';

				$('.down-count span').html(timeString);

				$('.down-count').show();
				
			}else{
				$('.down-count').hide();
				clearInterval(otimer);
			};
			console.log(11);
		}

		var otimer = setInterval(timerFn,1000);

		function checkTime(i)    
        {    
           /* if (i < 10) {    
               i = "0" + i;    
            }   */  
           return i;    
        }    
		
	})()
	</script>
</html>
