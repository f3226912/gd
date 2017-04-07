<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
<base href="${CONTEXT }">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${productDto.productName}</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta name="format-detection" content="telephone=no">
	<meta http-equiv="x-rim-auto-match" content="none">
	<meta name="keywords" content="商品详情,${productDto.productName}" />
	<meta name="description" content="库存: ${dealStockCount} 单价: ${priceSign} 商品介绍: ${dealDes}" />
	<link rel="stylesheet" href="${CONTEXT }css/nps/style.css">
	<link rel="stylesheet" href="${CONTEXT }css/swiper.min.css">
	<style>
	.down-wrap{vertical-align: middle;z-index:110;display: none;text-align: center;height: 100%;width: 100%; background: rgba(27,27,27,0.8); position: fixed;bottom: 0;}
	.btn-app{ display: block; width: 15rem; height: 3rem; border-radius: 1rem; font-size: 1.6rem; color: #000000; font-weight: bold; text-align: center; line-height: 3rem; left: 50%; position: absolute; bottom: 3rem; margin-left: -7rem;}
	.down-wrap .img{ margin:0 auto; height: 21.5rem; width: 30rem; top: 50%; left: 50%; margin-top: -10.5rem; margin-left: -15rem; position: absolute;}
	.down-wrap .wx-tip{position: absolute; right: 0; width: 60%; top:2rem;}
	.down-close{width: 3rem; height: 3rem; display: block; position: absolute; right: 0.5rem;top: 0.5rem; background-size:6rem auto ;}
	.sp-wholesale{ background:#fff; margin-top:.5rem; width:100%; padding:.8rem 0 .5rem 0;}
	/* .lact-img{ width:7.3rem; height:auto; display: block; float: left; padding:0; margin:0; } */
	.sp-left-icon{ width:25%; float:left; text-align:center;}
	.home-img{ width:5.75rem; height:5.75rem;}
	.sp-right-box{ width:75%; float:right;}
	.sp-right-box h3{ font-size:1.6rem; color:#666;}
	.label-area{ width:15.5rem; margin:.5rem 0; height:1.5rem; }
	.label-area li{ float:left; height:1.5rem; line-height:1.6rem;  font-size:1rem; color:#fff; border-radius:.2rem; background:-webkit-gradient(linear, 0% 0%, 0% 100%,from(#ff9242), to(#ff6f05)); padding:0 .5rem; margin-right:.5rem;}
	.num-sp{ width:100%; color:#999; font-size:1.2rem;}
	.gold-img{ width:5.7rem; height:1.3rem;}
	.stock-dv{float:left; width:50%;}
	.product-name-map{width: 50%; float: right;}
	.introduction-p{
		width: 77%;
	}
	.lact-img-span{display:inline-block; background:url(${CONTEXT }v1.0/images/marketing/location.png) center center; margin-right:.3rem; background-size:7.3rem 1.6rem; width:7.3rem; height: 1.6rem;}
	.cooperative {
		width: auto;
		padding: 0 .5rem;
	}
	</style>
	<script type="text/javascript">
    	function aonClick(urlType,params){
			switch(urlType){
			case 0:JsBridge.call(params);break;
			}
		}
  	</script>

</head>

<body>
<div id="prodDetailCoontent">
<!--main_visual end-->
    <div class="to-order-wrap">
		<div class="to-order ui-box ui-ver ui-hc ui-vc">
			<div class="ui-box ui-ver order-box">
				<div class="tit">采购量</div>
				<div class="ui-box mid-digit ui-hc ui-vc">
					<div class="ui-box">
						<span class="minus">-</span>
						<input type="number" value="1" id="order-num" onfocus='this.select()' maxLength='9'>
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
						请使用农商友智能POS机支付货款：<em>0元</em>
					</div>
					<div class="close">
						确定
					</div>
				</div>
			</div>
			<div class="loading"><img src="${CONTEXT }v1.0/images/marketing/loading.gif"/></div>
			<div class="bg-gray"></div>
		</div>
	</div>
	<div class="main-container art-wrap" id="mainH"  data-id='${productDto.businessId},${productDto.marketId},${productDto.productId}'>
		<!--主内容  -->
		<div style="margin-bottom:5rem;">
		<!--banner图-->
		<div class="swiper-container">
		  <div class="swiper-wrapper">
		   <c:forEach items="${productDto.pictures}" end="2" var="picture" varStatus="s">
		   <div class="swiper-slide"><span class="swiper-img"><img src="${imgHostUrl }${picture.urlOrg }"/></span></div>
			</c:forEach>
		  </div>
		  <div class="swiper-pagination swiper-pagination-cnet"></div>
		</div>
        <div class="mian-introduction">
        	<div class="box-introduction clearfix">
	        	<p class="introduction-p"><span class="lact-img-span" <c:if test="${productCertifStatus != 1 }">style='display:none;'</c:if> ></span>${productDto.productName}</p>
	        	<!--
	        	<c:if test="${not empty productDto.managementType }">
				<span class="cooperative">
				<c:if test="${productDto.managementType=='1' }">种养大户</c:if>
				<c:if test="${productDto.managementType=='2' }">合作社</c:if>
				<c:if test="${productDto.managementType=='3' }">基地</c:if>
				</span>
				</c:if>
				-->
			</div>
			<div class="xq-price clearfix">
				<div class="ul-price clearfix">
					<div class="ul-price-dj">
						<span class="dj-price">单价：</span>
						<span class="nub-price"><i>${productDto.formattedPrice}</i> <c:if test="${productDto.formattedPrice ne '面议' }"><span class="nub-price-1">元/${productDto.unitName}</span></c:if></span>
					</div>
					<span class="price-date"><fmt:formatDate value="${productDto.updateTime}" pattern="MM-dd"/></span>
				</div>
				<div class="stock-sj">
					<div class="stock-dv"><span class="stock-zl">库存:</span><span class="stock-zl stock-mt">${dealStockCount}</span></div>
					<div class="product-name-map"><c:if test="${not empty productDto.originProvinceName }"><i class="i-icon i-icon-t i-icon-ts"></i></c:if><span class="product-name-sp"><nobr>${productDto.originProvinceName}${productDto.originCityName}${productDto.originAreaName}</nobr></span></div>
				</div>
			</div>
        </div>
		<div class="commodity-about" id="commodity-bt">
			<h2 class="commodity-about-green">商品介绍：</h2>
			<div class="commodity-content">${productDto.description}</div>
		</div>
		<!-- 新增水产批发商店 -->	
		<div class="sp-wholesale clearfix">
		    <div class="sp-left-icon"><img class="home-img" src="${CONTEXT }v1.0/images/marketing/sp-icon.png"/></div>
		    <div class="sp-right-box">
		        <h3>${business.shopsName}</h3>
		        <ul class="label-area clearfix">
		            <c:if test="${business.memberGrade == 1}"><li style="background:none"><img class="gold-img" src="${CONTEXT }v1.0/images/marketing/gold_medal.png"/></li></c:if>
		        	<c:if test="${business.comstatus == 1}"><li>企业</li></c:if>
			    	<c:if test="${business.cbstatus == 1}"><li>基地</li></c:if>
			        <c:if test="${business.ccstatus == 1}"><li>合作社</li></c:if>
		        </ul>
		        <p class="num-sp">共${productCount}件商品</p>
		    </div>
		</div>
		</div>
		<!-- 底部 -->
		<div class="footer">
			<footer style='height:100%;'>
				<ul class="footer-ul ui-box">
					<c:if test="${empty hasGotoShop or hasGotoShop eq 1}">
						<li class="ui-box ui-f1 w-1 ui-hc">
							<div class="footer-a1 ui-box ui-vc" id="goToShop"><i class="i-icon i-icon-sp"></i><a class="footer-a">进入商铺</a></div>
						</li>
					</c:if>
					<li class="ui-box ui-f1 w-1 ui-hc">
						<div class="footer-a1 ui-box ui-vc" id = "confirmBtn111"><i class="i-icon i-icon-phone"></i><a class="footer-a" >拨打电话</a></div>
					</li>
					<c:if test="${empty gdBusinessId or gdBusinessId eq productDto.businessId}">
						<li class="ui-box ui-f1 w-1 ui-hc go-purshase-box">
							<div class="footer-a1 ui-box ui-vc" id = ""><a class="footer-a go-purshase <c:if test='${productDto.state eq 2 }'>valid</c:if>" >我要采购</a></div>
						</li>
					</c:if>
				</ul>
			</footer>
		</div>
	</div>
	</div>
	<div class="down-wrap">
		<div class="img">
			<img src="images/app-down.png" id="levelTypeImg" alt=""/>
			<a href="javascript:;" class="btn-app"></a>
			<a href="javascript:;" class="down-close"></a>
		</div>
		<img src="images/app-wx-tip.png" alt="" class="wx-tip"/>
	</div>
<!--<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>-->
<script type="text/javascript" src="${CONTEXT }js/swiper.min.js"></script>
<script type="text/javascript" src="${CONTEXT }v1.0/module/mui/dist/js/mui.min.js"></script>
<script type="text/javascript">
	var swiperNum = document.getElementsByClassName('swiper-slide').length;
	var swiperNo = document.getElementsByClassName('swiper-pagination')[0];
	if(swiperNum==1){
		swiperNo.style.display="none";
	};
	var mySwiper = new Swiper('.swiper-container', {
		//loop : true,	
		pagination: '.swiper-pagination',
		autoplay: 3000,//可选选项，自动滑动
		autoplayDisableOnInteraction:false,
	})
	//页面居低
	/*var wH = document.body.scrollHeight;
	var cY = document.getElementById('commodity-bt');
	var mH = document.getElementById('mainH');
	var swiper = mH.offsetHeight;
	if(wH > swiper) {
		cY.style.marginTop = wH - swiper+'px';

	}*/
	//分享参数传递
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
	//跳转到商铺列表页面
	function goToShopList(shopData){
		JsBridge.goToShopList(shopData);
	}
	
	  $(document).ready(function(){
		  var unit = $('.nub-price i').text();
			  unit = unit.replace(",","");
		  var isNum = /^[0-9]+.?[0-9]*$/;
		  
		  if (isNum.test(unit))
	      {
			  $('.go-purshase').addClass('valid');
	      }
		  
		  $('.go-purshase').click(function(){
			  if($('.go-purshase').hasClass('valid')){
				  $('.to-order-wrap').show();
			  }
		  });
		  
		//下单hide
		$('.bg-gray').click(function(event){
			$('.to-order-wrap').hide();
			$('#order-num').val('1');
			$('.order-box').show();
			$('.tips_pay').hide();
		});
		$('.cancel').click(function(){
			$('.to-order-wrap').hide();
			$('#order-num').val('1');
		});
		
		$('#order-num').focus(function(){
		   $(".footer").hide();
		});
		$('#order-num').blur(function(){
			$(".footer").show();
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
					
					if(!checkInput()){ //单独 ==0 通过
						return;
					}
					commodityNum++;
					$orderNumInput.val(commodityNum);
				});
				
				$orderNumInput.click(function(){
					$(this).select();
				})
				
				function checkInput(){
					commodityNum = $orderNumInput.val();
					if(!isNum.test(commodityNum)){
						
						tips('请输入采购量')
						
						return false;
					}
					if(commodityNum<0){
						return false;
					}
					return true;
				}
				
				$orderNumInput[0].oninput = function(){
					var reg = new RegExp('^(\\d+\\.\\d{2}).+');
					var val = this.value;
					
					val = val.replace(reg,'$1');
					if(val.length>9){
						val = val.substring(0, 9);
					}
					
					this.value = val;
				};

			};
			
			function tips(text){
				$('.tips_pay .text').html(text);
				$('.order-box').hide();
				$('.tips_pay').show();
				$('.to-order-wrap').show();
			}
			
			// 获取登录用户id
			function getLoginUserId() {
				//渠道 1android 2ios
				var u = navigator.userAgent;
				var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
				var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
				var loginUserId;
				//安卓获取memberId
				if(isAndroid){
					loginUserId = window.JsBridge.getUserId();
					//未登录
					if(loginUserId==''){
						window.JsBridge.toLogin();
						return;
					}
				}
				//IOS获取memberId
				if(isiOS){
					loginUserId = getUserId();
					//未登录
					if(loginUserId==''){
						JSLogin();
						return;
					}
				}
				return loginUserId;
			}
			
			$('.tips_pay .close').click(function(){
				$('.to-order-wrap').hide();
				$('.order-box').show();
				$('.tips_pay').hide();
				$('#order-num').val('1');
				$('.tips_pay .text').html('');
				key = true;
			})
			orderM();
		  	var key = true;
			$('.order-btn .comfirm').click(function(){
				//输入是否正确
				if(!key){
					return;
				}
				key = false;
				var commodNum = $('#order-num').val();
				
				if(!isNum.test(commodNum)){
					tips('请输入采购量');
					return false;
				};
				
				if (parseFloat(commodNum) > 999999.99) {
					tips('输入数字不能大于999999.99');
					return false;
				};
				
				var totalPrice = commodNum*unit; //总额
				
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
				//alert(unit);
				var arrId = $('.art-wrap').data('id').split(',');
				var arrString = '[{"price": '+unit+',"productId": '+arrId[2]+',"purQuantity": '+commodNum+',"actType" :0}]';
				
				//安卓获取memberId
				if(isAndroid){
					arrId[3] = window.JsBridge.getUserId();
					//未登录
					if(arrId[3]==''){
						window.JsBridge.toLogin();
						return;
					}
				}
				//IOS获取memberId
				if(isiOS){
					arrId[3] = getUserId();
					//未登录
					if(arrId[3]==''){
						JSLogin();
						return;
					}
				}
				
				arrString = JSON.stringify(arrString);
				var jsonPost = '{'
					+'"memberId":'+arrId[3]+','
					+'"businessId":'+arrId[0]+','
					+'"marketId":'+arrId[1]+','
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
				//GIF 下单
				$('.loading').show();
				$.ajax({
					type: 'post',
					url:'${CONTEXT }purchOrder/add',
					data:postData,
					success:function(data){
						key = true;
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
						//alert(JsonData.statusCode == 0);
						if(JsonData.statusCode != 0){
							$('.order-box').hide();
							$('.loading').hide();
							
							$('.tips_pay .text').html(JsonData.msg);
							$('.tips_pay').show();
							$('.tips_pay .close').click(function(){
								$('.to-order-wrap').hide();
								$('.order-box').show();
								$('.tips_pay').hide();
								$('.tips_pay .text').html('');
							})
						}else if(JsonData.statusCode == 0){
							$('.loading').hide();
							$('.tips_pay .text').html('请使用农商友智能POS机支付货款：<em>'+JsonData.object.prepayAmt+'元</em>');
							$('.tips_pay').show();
							$('.order-box').hide();
							$('.tips_pay .close').click(function(){
								if(isAndroid){
									window.JsBridge.onOrderSuccess(JsonData.object.orderStatus);
								}
								if(isiOS){
									successEnter(JsonData.object.orderStatus);
								}
								$('.tips_pay .text').html('');
							})
							
						}
					},
					error:function(err){
						tips('服务器异常')
						key = true;
						$('.loading').hide();
						$('.to-order-wrap').hide();
					}
				})

			});
		  

		  
		  if("${flag}"=='1'){
				function getWeiXin(){
				    var ua = window.navigator.userAgent.toLowerCase();
				    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
				        return true;
				    }else{
				        return false;
				    }
				}
				var Terminal = {
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
				$('#prodDetailCoontent').on('touchstart',function(){
					if(getWeiXin()){
			    		$('.wx-tip').show();
			    	}else{
			    		
			    		$('.wx-tip').hide();
			    	}
					$("#levelTypeImg").attr("src", "images/app-down1.png");
					$('.down-wrap').show();
				});

				$('.down-close').on('touchstart',function(){
					$('.down-wrap').hide();
				});
				$('.btn-app').on('touchstart',function(){
					if(Terminal.platform.iPhone || Terminal.platform.iPad || Terminal.platform.isMac){
		        		$(".btn-app").attr("href","https://itunes.apple.com/cn/app/nong-pi-shang/id1074569572?mt=8&l=cn");
		        	}else if(Terminal.platform.android || Terminal.platform.isWin){
		        		$(".btn-app").attr("href","http://www.gdeng.cn/nsy-sell/nsy-nps.apk");
		        	}
				})
			}  
		//点击商户信息跳转
		$("#goToShop").click(function(){
			var shopData={
					"businessId":'${productDto.businessId }',
					"productId":'${productDto.productId}',
					"shopsName":'${productDto.shopsName }',
					"mainProduct":'${productDto.mainProduct }',
					"mobile":'${productDto.mobile }'
				};
	    	goToShopList(JSON.stringify(shopData));
	    	
		});
		
		  $("#confirmBtn111").on("tap",function(){
			  if("${flag}"!='1'){
				aonClick(0, 'mobile=${productDto.mobile }' );
				//对于分享后点击链接进入的用户没有userId，因此不需要记录
				var userId = getLoginUserId();
				if(userId){
					//调用接口拨打电话
					$.ajax({
						url:"${CONTEXT }product/addCall",
						type:"post",
						data:{
							'sysCode':'${fromCode}',
							's_Mobile':'${productDto.mobile }',
							's_Name':'${productDto.realName}',
							//'e_Mobile':'${mem.contactMobile}',
							//'e_Name':'${mem.contactName}',
							'source':"SPXQ",
							'shopName':'${productDto.shopsName }',
							'fromCode':1,
							'b_memberId':'${productDto.businessUserId }',
							'memberId':userId
						},
						dataType:"text",
						success:function(data){
								
						},
						error:function(){
						}
					});
				}
			  }
		});
});
</script>
<%@ include file="/WEB-INF/jsp/pub/baidu.inc" %>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${CONTEXT}js/weixin.js"></script>

<script type="text/javascript">
var picUrl = "${imgHostUrl }${productDto.pictures[0].urlOrg}";
var productIdStr = '${productDto.productId}';
var fromCodeStr = '${fromCode}';
var flagStr = "${flag}";
var productNameStr = '${productDto.productName}';
var descStr="库存:${dealStockCount} 单价:${priceSign} 商品介绍:${dealDes}";
var linkStr = CONTEXT + "product/getProductByProdId?productId="+productIdStr+"&fromCode="+fromCodeStr+"&flag="+flagStr;
  wx.ready(function() {
 	//获取“分享给朋友”按钮点击状态及自定义分享内容接口
	wx.onMenuShareAppMessage({
		title : productNameStr, // 分享标题
		desc : descStr, // 分享描述
		link : linkStr, // 分享链接
		imgUrl : picUrl, // 分享图标
		type : '', // 分享类型,music、video或link，不填默认为link
		dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
		success : function(res) {
			
		},
		 cancel: function () { 
		       // 用户取消分享后执行的回调函数
		}
	});
	// 分享给朋友圈
	wx.onMenuShareTimeline({
		title : productNameStr, // 分享标题
		link : linkStr, // 分享链接
		imgUrl : picUrl, // 分享图标
		success : function(res) {
			
		},
		cancel: function () { 
	       // 用户取消分享后执行的回调函数
	    }
	});
	//分享到QQ
	wx.onMenuShareQQ({
	    title:productNameStr, // 分享标题
	    desc: descStr, // 分享描述
	    link: linkStr, // 分享链接
	    imgUrl : picUrl, // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	       // 用户取消分享后执行的回调函数
	    }
	});
	//分享到QQ空间
	wx.onMenuShareQZone({
	    title:productNameStr, // 分享标题
	    desc: descStr, // 分享描述
	    link:  linkStr, // 分享链接
	    imgUrl: picUrl, // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
});
 
  </script>
</html>