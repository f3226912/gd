<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
	<script type="text/javascript" src="${CONTEXT }v1.0/js/jquery-1.8.3.min.js"></script>
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/data/base.cy.min.css">
	<link rel="stylesheet" href="${CONTEXT }v1.0/css/marketing/gys-article.css">

</head>
<body>	
	<div class="art-wrap" data-id='${productDto.businessId},${productDto.marketId},${productDto.productId}'>
		<div class="area-1">
			<c:if test="${productDto.pictures != null && fn:length(productDto.pictures) > 0}">
				<img src="${imgHostUrl }${productDto.pictures[0].urlOrg }" width="100%">
			</c:if>
			<div class="info-price ui-box">
				<span class="price ui-box ui-f1 ui-hc">活动价：<em>${productDto.formattedPrice}</em><i>元/${productDto.unitName}</i></span>
				<span id='timing' class="timing ui-box ui-hc ui-vc" data-start='${productDto.promotionStartTime}' data-end='${productDto.promotionEndTime}'>距结束时间<br/><i>00:00:00</i></span>
			</div>
		</div>
<!-- 		<div id="text01" style="padding:1rem;width: 100%;">
		
		</div> -->
		<div class="area-2">
			<div class="ui-box ui-ver info-para">
				<div class="ui-box para-tit">
					<div class="ui-box ui-f1 commodity-tit ui-over-elli2">${productDto.productName}</div>
					<div class="ui-box commodity-ico">	        	
					<c:if test="${not empty productDto.managementType }">
					<span>
					<c:if test="${productDto.managementType=='1' }">种养大户</c:if>
					<c:if test="${productDto.managementType=='2' }">合作社</c:if>
					<c:if test="${productDto.managementType=='3' }">基地</c:if>
					</span>
					</c:if>
					</div>
				</div>
				<div class="ui-box para-place">
					<div class="ui-f1">库存：${dealStockCount}</div>
					<div class="ui-f1 place">${productDto.originProvinceName}${productDto.originCityName}${productDto.originAreaName}</div>
				</div>
			</div>
			<div class="commodity-desc">
				<span>商品介绍：</span>
				<p>${productDto.description}</p>
			</div>

		</div>

	</div>
	<div class="go-purshase">
		我要采购
	</div>
	<div class="to-order-wrap">
		<div class="to-order ui-box ui-ver ui-hc ui-vc">
			<div class="ui-box ui-ver order-box">
				<div class="tit">采购量</div>
				<div class="ui-box mid-digit ui-hc ui-vc">
					<div class="ui-box">
						<span class="minus">-</span>
						<input type="tel" value="1" id="order-num">
						<span class="plus">+</span>
					</div>
				</div>
				<div class="order-btn ui-box">
					<span class="ui-box ui-f1 ui-vc cancel">取消</span>
					<span class="ui-box ui-f1 ui-vc comfirm">下单</span>
				</div>
			</div>
			<div class="tips_pay">
				<div class="ui-box ui-ver">
					<div class="text ui-box ui-hc">
						请使用农商友智能POS机支付预付款：<em>00元</em>
					</div>
					<div class="close">
						确定
					</div>
				</div>
			</div>
			<div class="bg-gray"></div>
		</div>
		
	</div>
	
	<script>
		(function(){
			$.ajax({
				type: 'get',
				url:'http://10.17.3.56:8080/gd-api/purchOrder/add',
				dataType: 'jsonp',
				success:function(data){
					console.log(data);
				},
				error:function(err){
					alert('服务器异常')
					$('.to-order-wrap').hide();
				}
			})
			var isNum = /^[0-9]+.?[0-9]*$/;

			//下单show
			$('.go-purshase').click(function(){
				if(!$(this).hasClass('valid')){
					return;
				}
				$('.to-order-wrap').show();
			});
			//下单hide
			$('.bg-gray').click(function(event){
				$('.to-order-wrap').hide();
			});
			$('.cancel').click(function(){
				$('.to-order-wrap').hide();
			});
			

			//下单数量 +-
			function orderM(){
				var $orderNumInput = $('#order-num'),
					$minus = $('.minus'),
					$plus = $('.plus')
					
				var commodityNum = 0;
				//减
				$minus.on('touchstart',function(){
					if(!checkInput()||commodityNum==0){
						return;
					}
					commodityNum--;
					$orderNumInput.val(commodityNum);
				});
				//加
				$plus.on('touchstart',function(){
					console.log(commodityNum)
					if(!checkInput()){ //单独 ==0 通过
						return;
					}
					commodityNum++;
					$orderNumInput.val(commodityNum);
				});

				function checkInput(){
					commodityNum = $orderNumInput.val();
					if(!isNum.test(commodityNum)){
						alert('输入有误');
						return false;
					}
					if(commodityNum<0){
						return false;
					}
					return true;
				}

			};
			orderM();

			/*
			 * 倒计时 陈翊
			 * 需 引入JQ
			 * 参数说明：startDate 活动开始时间，endDate 活动结束后时间，ele 倒计时元素 ,
			 * 			 callback 结束后运行, nostartHtml 未开始HTML, startHtml 已开始HTML,
			 * 			 endHtml 结束开始HTML
			 */
			
			var countDown =  function(opts){
				var defaultOption = {
					ele:'timing',
					nostartHtml:'活动未开始',
					startHtml:'活动正在进行',
					endHtml:'活动已结束'
				}

				this.opts = $.extend(true, defaultOption,opts);
				//毫秒
				this.opts.nowDate = new Date().getTime();
				this.opts.startDate = Date.parse(this.opts.startDate);
				this.opts.endDate = Date.parse(this.opts.endDate);
				
				//JQ 元素
				this.$ele = $('#'+this.opts.ele);
			};

			countDown.prototype = {
				init:function(){
					//alert(this.opts.startDate);
					//活动未开始
					if(this.opts.nowDate-this.opts.startDate<0){
						this.insertHtml(this.opts.nostartHtml);
					}
					//活动正在进行
					if(this.opts.nowDate-this.opts.startDate>0 && this.opts.nowDate-this.opts.endDate<0){
						this.moveTime();
						$('.go-purshase').addClass('valid');
					}
					//活动已结束
					if(this.opts.nowDate-this.opts.endDate>0){
						this.insertHtml(this.opts.endHtml);
					}
				},
				//插入HTML
				insertHtml:function(html){
					this.$ele.html(html);
				},
				//倒计时
			 	moveTime:function(){

			 		var _self = this;
			 		
					var timer = setInterval(function(){
						var totalSecond = _self.opts.endDate - (new Date().getTime());
						var html = _self.getTime(totalSecond);

						_self.insertHtml(html);
						//活动结束
						if(totalSecond<=0){
							clearInterval(timer);
							_self.insertHtml(_self.opts.endHtml);
							//执行回调函数
							
							if(_self.opts.callback){
							   _self.opts.callback();
							}
						};
					},1000);

			 	},
			 	getTime:function(second){
			 		//console.log(second);
			 		var time_distance = second/1000;

			 	 	// 相减的差数换算成小时

		 	        var int_hour = Math.floor(time_distance/3600);
		 	        time_distance -= int_hour * 3600;

			 		// 相减的差数换算成分钟  

			 	    var int_minute = Math.floor(time_distance/60)   
			 	    time_distance -= int_minute * 60;

			 	 
			 	 	// 相减的差数换算成秒数 
			 	    var int_second = Math.floor(time_distance) 

	                if(int_hour<10){ int_hour = "0" + int_hour };
	                if(int_minute<10){ int_minute = "0" + int_minute };
	                if(int_second<10){ int_second = "0" + int_second };

	                return '距结束时间<br/><i>'+int_hour+":"+int_minute+":"+int_second+"</i>";
			 	}
			}
			
			var ObjCountDown = new countDown({
				ele:'timing',
				startDate:$('.timing').data('start'),
				endDate:$('.timing').data('end'),
				callback:function(){
					console.log(22)
					$('.go-purshase').removeClass('valid');
				}
			})
			ObjCountDown.init();
			
			function getShareInfoList(){
				var shareData={
				"imageUrl": "${imgHostUrl }${productDto.pictures[0].urlOrg}",
				"productName" : "${productDto.productName}",
				"stockCount" : "${productDto.stockCount}",
				"price" : "${productDto.price}",
				"unitName" :"${productDto.unitName}",
				"description" :"${dealDes}",
				};
				JsBridge.getShareInfoList(JSON.stringify(shareData));
			}

			
			$('.order-btn .comfirm').click(function(){
				//输入是否正确
				var commodNum = $('#order-num').val();
				if(!isNum.test(commodNum)){
					alert('输入有误');
					return false;
				};
				var totalPrice = commodNum*$('.price em').text(); //总额
				alert(totalPrice);
				var channel = null;	//渠道
				var strEncode = null; //编译字符串

				//渠道 1android 2ios
				var u = navigator.userAgent;
				var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
				var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

				if(isAndroid){
					channel = 1;
				};
				if(isiOS){
					channel = 2;

				};
				//获取ID
				var arrId = $('.art-wrap').data('id').split(',');
				var arrString = '[{"price": 100,"productId": '+arrId[3]+',"purQuantity": 8,"actType" :'+channel+'}]';

				arrString = JSON.stringify(arrString);
				var jsonPost = '{'
					+'"memberId":'+arrId[0]+','
					+'"businessId":'+arrId[1]+','
					+'"marketId":'+arrId[2]+','
					+'"orderAmount":'+totalPrice+','
					+'"payAmount":'+totalPrice+','
					+'"channel":'+channel+','
					+'"jProductDetails":'+arrString+'}';

				//安卓加密
				if(isAndroid){
					strEncode = window.JsBridge.encode(jsonPost);
				}
				//IOS加密
				if(isiOS){
					strEncode = encodedBuyActionProduct(jsonPost);
				}
				
				//alert(strEncode);

				strEncode = strEncode.replace(/\+/g, "%2B");

				var postData = "param=" + strEncode;
				
				$.ajax({
					type: 'post',
					url:'${CONTEXT }purchOrder/add',
					data:postData,
					success:function(data){
						//console.log(data);
						var JsonData=null;
						//安卓解密
						if(isAndroid){
							JsonData = window.JsBridge.decode(data);
						}
						//IOS解密
						if(isiOS){
							JsonData = decodedBuyActionProduct(data);
						}
						//alert(JsonData);
						JsonData = JSON.parse(JsonData);
						alert(JsonData.statusCode == 0);
						if(JsonData.statusCode != 0){
							alert(JsonData.msg)
						}else if(JsonData.statusCode == 0){
							$('.tips_pay .text em').html(totalPrice+"元");
							$('.order-box').hide();
							$('.tips_pay').show();
							$('.tips_pay .close').click(function(){
								if(isAndroid){
									onOrderSuccess(data);
								}
								if(isiOS){
									successEnter();
								}
							})
							
						}
					},
					error:function(err){
						alert('服务器异常')
						$('.to-order-wrap').hide();
					}
				})

			});
		})()
	</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
</html>