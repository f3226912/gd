<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/pub/constants.inc" %>
<%@ include file="/WEB-INF/jsp/pub/tags.inc" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/WEB-INF/jsp/pub/head.inc" %>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>物流详情</title>
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/layer.css" />
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/transport/global.css" />
		<link rel="stylesheet" href="${CONTEXT}v1.0/css/transport/style.css" />
		<style type="text/css">
			.mark {
			    position: fixed;
			    z-index: 998;
			    left: 0;
			    top: 0;
			    width: 100%;
			    height: 100%;
			    background: #000;
			    opacity: .1;
			}
			.markAll img{width:32px;height:32px;position: absolute;left:50%;top:50%;margin-left:-16px;margin-top:-16px;z-index:99;}
		</style>
	</head>

	<body>
		<div class="markAll">
			<div class="mark"></div>
			<img src="${CONTEXT }v1.0/images/transport/loading.gif"/>
		</div>
		<div class="ditail-main">
			<section class="tip-time timeLeftShow" style="display:none">
				<img src="${CONTEXT}v1.0/images/transport/time-icon.png"><span class="timeLeft"></span>
			</section>
			<section class="ditail-cont">
				<div class="top-text algn-right bor-bottom statusLable"></div>
				<!--已支付-->
				<div class="top-text bor-bottom orderShow" style="display:none"><em></em><span class="fr"></span></div>
				
				<!--运费模块-->
				<div class="view-list bor-bottom"><span>意向运费：</span><span class="fr" id="price"></span></div>
				<!--END-->
				<!--地点模块-->
				<article class="address-con bor-bottom">
					<i class="bg-icon add-img"></i>
					<div class="fhd-text">
						<span><i class="bg-icon"></i>发货地</span>
						<span class="input-text" id="startAddr"></span>
					</div>
					<div class="mhd-text">
						<span><i class="bg-icon"></i>目的地</span>
						<span class="input-text" id="endAddr"></span>
					</div>
				</article>
			</section>			
			<!--司机信息-->
			<section class="mb10">
				<h6 class="mess-title driverInfo">司机信息</h6>
				<article class="siji-cont">
					<div class="left-img"><img src="${CONTEXT}v1.0/images/transport/headpic.png" id="photoUrl"></div>
					<div class="siji-mess">
						<span class="siji-name siji-wrz" id="driverName"></span>
						<p class="siji-num" id="carNo"></p>
						<p class="siji-num" id="carOtherInfo"></p>
					</div>
				</article>
			</section>
			<!--物流信息-->
			<section class="mb11" style="display:none">
				<h6 class="mess-title">物流公司</h6>
				<article class="siji-cont">
					<div class="left-img"><img src="${CONTEXT}v1.0/images/transport/headpic.png" id="photoUrl2"></div>
					<div class="siji-mess">
						<span class="gongsi-name gongsi-wrz" id="companyName"></span>
						<p class="siji-num" id="companyMobile"></p>
					</div>
				</article>
			</section>
			<section class="ditail-cont">
				<div class="bg-icon check-add">查看已添加货物>></div>
				<!--货物信息-->
				<h6 class="mess-title">货物信息</h6>
				<div class="view-list bor-bottom">发货人：<span class="fr" id="deliverer"></span></div>
				<div class="view-list bor-bottom">货物分类：<span class="fr" id="goodsTypeName"></span></div>
				<div class="view-list bor-bottom">总重量/体积：<span class="fr" id="weightSize"></span></div>
				<!--支付运费-->
				<style>
					.edwin-payDetail{display:none;}
				</style>
				<h6 class="mess-title edwin-payDetail">支付信息</h6>
				<div class="view-list bor-bottom edwin-payDetail">支付运费：<span class="fr" id="zhifuyunfei"></span></div>
				<div class="view-list bor-bottom edwin-payDetail">支付流水：<span class="fr" id="zhifuliushui"></span></div>
				<!--用车信息-->
				<h6 class="mess-title">用车信息</h6>
				<div class="view-list bor-bottom">装货时间：
					<span class="fr" id="goodsDate"></span>
				</div>
				<div class="view-list bor-bottom">所需车型：<span class="fr" id="carTypeName"></span></div>
				<div class="area-cont remarkPanel"><span class="fl">备注：</span>
					<div class="textarea-text"><textarea id="remark" readonly="readonly">
					</textarea></div>
				</div>
				<!--支付信息
				<h6 class="mess-title">支付信息</h6>
				<div class="view-list bor-bottom">实付运费：
					<span class="fr">10,000.00元</span>
				</div>
				<div class="view-list bor-bottom">支付流水 ：<span class="fr">201607191882000001</span></div>
				-->
				<!--物流信息-->
				<div class="wuliu-mess">
				<h6 class="mess-title">物流信息</h6>
				<div class="deliverList">
				</div>
				</div>
			</section>		
			<div class="del-cont mt20" style="display:none" id="deleteBtn" st="1011">删除</div>
			<div class="del-cont mt20 phoneBtn" id="eYiwancheng2" style="display:none">
				拨打电话
			</div>
			<div class="del-cont mt20" id="eYiwancheng" style="display:none">
				<span class="phoneBtn">拨打电话</span>
				<span class="payButtom" style="color:#fc6c21;">支付运费</span>
			</div>
			<div class="del-cont mt20 confirmDiv" style="display:none">
				<span class="phoneBtn">拨打电话</span>
				<span class="red-text" id="confirmBtn">确认收货</span>
			</div>
			<div class="del-cont del-three mt20" style="display:none">
				<span class="phoneBtn">拨打电话</span>
				<span class="" id="refuseBtn">拒绝</span>
				<span class="red-text" id="acceptBtn">接受</span>
			</div>
		</div>
		<div class="pop-win">
			<div class="filter"></div>
			<div class="pop-cont">
				<p>确认删除该物流信息？</p>
				<div class="pop-btn">
					<span class="curr-btn">取消</span>
					<span>确定</span>					
				</div>
			</div>
		</div>
		<script>
			var context="${CONTEXT }";
		</script>
		<script src="${CONTEXT}js/layer.js"></script>
		<script src="${CONTEXT }js/transfer/jquery.mobile.custom.min.js"></script>
		<script type="text/javascript">
			var tId = ${transferId};
			var mId = ${memberId};
			var oprtcode = ${oprtcode};
			var orderId = ${orderId};
			
			var gMobile = "";			
			var gVersion = 0;
			getDetailData(tId, oprtcode, orderId);			
			
			function getDetailData(tId, oprtCode, orderId){
				
				$.ajax({
					url: "${CONTEXT}v2/transfer/getDetailData/" + tId + "?oprtCode=" + oprtCode + "&orderId=" + orderId+"&memberId="+mId,
					type: "get",
					dataType: "text",
					success: function(data){	
						var jsonObj = jQuery.parseJSON(data);
						if(jsonObj.statusCode != 0){
							myLayer(jsonObj.msg);
							return false;
						}
						//$(".markAll").show();
						initiatePage(jsonObj.object);
						$(".markAll").hide();
					},
					error:function(){
						failedLoad();
						return false;
					}
				});
			}
			
			//初始化页面数据
			function initiatePage(jsonObj){
				//console.log(jsonObj);
				var dStatus = jsonObj.status;
				orderId = jsonObj.orderId;
				//console.log("orderId: " + orderId);
				var isProxy = jsonObj.isProxy;
				gVersion = jsonObj.version;
				switch(dStatus){
					case 1011:  //已发布
						$('.statusLable').text('已发布');
						$('#deleteBtn').show();
						$('.mb10').hide();
						$('.del-three').hide();
						$('.timeLeftShow').hide();
						initiateGoodsInfo(jsonObj.goodsInfo);
						initiateDeliverInfo(jsonObj.deliverInfo);
						break;
					case 1012:  //待确认
						$('.statusLable').text('待确认');
						if(isProxy == 1){	
							$('.mb10').hide();
							$('.mb11').show();
							initiateCompanyInfo(jsonObj.companyInfo);
							$('.del-cont.mt20.phoneBtn').show();
						}else{
							$('.mb11').hide();
							$('.mb10').show();
							//"2016-08-18 10:36:49"
							var timeStr = jsonObj.acceptTime;	
							var hourAndMin = timeStr.substring(11,16);
							$('.timeLeft').html("请在"+hourAndMin+"前接受车主接单，超时将自动拒绝接单");
							$('.timeLeftShow').show();
							initiateDriverInfo(jsonObj.driverInfo);
							$('.del-three').show();
						}
						initiateGoodsInfo(jsonObj.goodsInfo);
						initiateDeliverInfo(jsonObj.deliverInfo);
						break;
					case 1013:  //待收货
						$('.statusLable').hide();
						$('.orderShow em').html("运单号：" + jsonObj.orderNo);
						$('.orderShow span').html("待收货");
						$('.orderShow').show();
						
						initiateDriverInfo(jsonObj.driverInfo);
						initiateGoodsInfo(jsonObj.goodsInfo);
						initiateDeliverInfo(jsonObj.deliverInfo);
						
						$('.confirmDiv').show();
						break;
					case 1014:  //已完成
						$('.statusLable').hide();
						$('.orderShow em').html("运单号：" + jsonObj.orderNo);
						if(jsonObj.payStatus=="1"){
							$('.orderShow span').html("待支付");
							$('#eYiwancheng').show();
						}else{
							$('.orderShow span').html("已支付");
							$('#eYiwancheng2').show();
						}
						
						$('.orderShow').show();
						
						initiateDriverInfo(jsonObj.driverInfo);
						initiateGoodsInfo(jsonObj.goodsInfo,jsonObj);
						initiateDeliverInfo(jsonObj.deliverInfo);
						$(".payButtom").attr("orderid",jsonObj.orderNo);
						$(".payButtom").attr("transferid",tId);
						eClickPay();
						break;
					case 1015:  //已过期
						$('.statusLable').text('货源已过期');						
						$('.mb10').hide();
						initiateGoodsInfo(jsonObj.goodsInfo);
						initiateDeliverInfo(jsonObj.deliverInfo);
						if(isProxy == 0){
							$('#deleteBtn').attr("st", "1015");
							$('#deleteBtn').show();
						}
						break;
					case 1016:  //分配中
						$('.statusLable').text('分配中');
						$('.mb10').hide();
						initiateGoodsInfo(jsonObj.goodsInfo);
						initiateDeliverInfo(jsonObj.deliverInfo);
						break;
					default:
						myLayer('系统繁忙, 请稍后再试');
						break;
				}
				
			}
			
			//初始化司机信息
			function initiateDriverInfo(driverInfo){
				gMobile = driverInfo.mobile;
				
				if(driverInfo.photoUrl != ""){
					$('#photoUrl').attr("src", driverInfo.photoUrl);
				}					
				$('#driverName').html(driverInfo.name+'<span> &nbsp;'+gMobile+'<span>');
				$('#carNo').text(driverInfo.carNo);
				if(driverInfo.carLength == "-2.0"){
					$('#carOtherInfo').text(driverInfo.carTypeName + "   车长 不限    载重" + driverInfo.carLoad +"吨");
				}else{
					$('#carOtherInfo').text(driverInfo.carTypeName + "   车长" + driverInfo.carLength +"米    载重" + driverInfo.carLoad +"吨");
				}				
				
				if(driverInfo.isIdentify == 1){
					$('#driverName').removeClass("siji-wrz");
				}
				
				var driverMemberId=parseInt(driverInfo.driverMemberId);
				$(".payButtom").attr("drivermemberid",driverMemberId);
			}
			
			//初始化物流公司信息
			function initiateCompanyInfo(companyInfo){
				if(companyInfo.photoUrl != ""){
					$('#photoUrl2').attr("src", companyInfo.photoUrl);
				}				
				$('#companyName').text(companyInfo.name);
				$('#companyMobile').text(companyInfo.mobile);
				
				if(companyInfo.isIdentify == 1){
					$('#companyName').removeClass("gongsi-wrz");
				}
				
				gMobile = companyInfo.mobile;
			}
			
			//初始化货物信息
			function initiateGoodsInfo(goodsInfo,edwin){
				var status=getQueryString("status");
				if(goodsInfo.hasGoods == 0){
					$('.check-add').hide();
				}
				$('#price').text(goodsInfo.price);
				$('#startAddr').text(goodsInfo.startAddr);
				$('#endAddr').text(goodsInfo.endAddr);				
				$('#deliverer').text(goodsInfo.deliverer);
				$('#goodsDate').text(goodsInfo.goodsDate);
				$('#carTypeName').text(goodsInfo.carTypeName);
				$('#goodsTypeName').text(goodsInfo.goodsTypeName);
				if(status=="1014"){
					if(edwin.payStatus=="2"){
						$(".edwin-payDetail").show();
						$("#zhifuyunfei").text(edwin.freight);
						$("#zhifuliushui").text(edwin.paySerialNo);
					}
					
				}
				
				$('.remarkPanel').hide();
				if(goodsInfo.remark != ""){
					$('#remark').text(goodsInfo.remark);
					$('.remarkPanel').show();
				}
				
				
				if(goodsInfo.size == 0 || goodsInfo.size == null){
					$('#weightSize').text(goodsInfo.weight + '吨');
				}else if(goodsInfo.weight == "" || goodsInfo.weight == null){
					$('#weightSize').text(goodsInfo.size + 'M³');
				}else{
					$('#weightSize').text(goodsInfo.weight + '吨/' + goodsInfo.size + 'M³');
				}
			}
			
			//初始化物流信息
			function initiateDeliverInfo(deliverInfoList){
				var len = deliverInfoList.length - 1;
				var html = "";  
				$.each(deliverInfoList, function(n,value) {
					if(n != len){
						html += '<div class="succ-start"><p><i class="bg-icon"></i>' + value.description
						+ '</p><p class="time-text">'+value.dateTime+'</p><div class="line-wuliu"></div></div>';
					}else{
						html += '<div class="succ-start succ-hy"><p><i class="bg-icon"></i>' + value.description
						+ '</p><p class="time-text">'+value.dateTime+'</p></div>';	
					}
								
				});  
				
				$(".deliverList").html(html);
			}
			
			function deleteAfter(status){
				$(".markAll").hide();
				var url = "${CONTEXT}v2/transfer/getListPage?";
				switch(status){
					case 0: //拒绝物流按钮跳转至已发布页面
						url += "memberId=" + mId + "&status=1011";
						window.location.href = url;
						//getDetailData(tId, oprtcode, orderId);
						break;
					case 1: //接受货物按钮跳转至待收货页面
						url += "memberId=" + mId + "&status=1013";
						window.location.href = url; 
						break;
					case 2: //确认收货按钮跳转至已完成页面
						url += "memberId=" + mId + "&status=1014";
						window.location.href = url;
						break;
					default: //删除按钮
						url += "memberId=" + mId + "&status=" + $('#deleteBtn').attr("st");
						window.location.href = url;
						break;
				}
			}
			
			function setStatus(){
				canDelete = true;
				canConfirm = true;
				canRefuse = true;
				canAccept = true;
			}
			
			//删除按钮事件
			var canDelete = true;
			$('#deleteBtn').tap(function(){
				layer.open({
				    content: '是否删除提示框？'
				    ,btn: ['确认', '取消']
				    ,yes: function(index){
				    	$(".markAll").show();
						if(canDelete){
							canDelete = false;
							deleteAjax(mId,tId,gVersion);
						}
						layer.close(index);
				    }
				  });
				/* $(".markAll").show();
				if(canDelete){
					canDelete = false;
					deleteAjax(mId,tId,gVersion);
				} */
				
			})
			
			//确认收货按钮事件
			var canConfirm = true;
			$('#confirmBtn').tap(function(){
				layer.open({
				    content: '确认拒绝司机接单？'
				    ,btn: ['确认', '取消']
				    ,yes: function(index){
				      $(".markAll").show();
						//console.log("confirm orderId: " + orderId);
						if(canConfirm){
							canConfirm = false;
							confirmAjax(mId,tId,orderId,gVersion);
						}
						layer.close(index);
				    }
				  });
				
				
				
			})
			
			//拒绝物流按钮事件
			var canRefuse = true;
			$('#refuseBtn').tap(function(){
				layer.open({
				    content: '确认拒绝司机接单？'
				    ,btn: ['确认', '取消']
				    ,yes: function(index){
				      $(".markAll").show();
						//console.log("refuse orderId: " + orderId);
						if(canRefuse){
							canRefuse = false;
							refuseAjax(mId,tId,orderId,gVersion);
						}
						layer.close(index);
				    }
				  });
				
				
				
			})
			
			//接受物流按钮事件
			var canAccept = true;
			$('#acceptBtn').tap(function(){
				layer.open({
				    content: '确定接受司机接单？'
				    ,btn: ['确认', '取消']
				    ,yes: function(index){
				      $(".markAll").show();
						//console.log("accept orderId: " + orderId);
						if(canAccept){
							canAccept = false;
							acceptAjax(mId,tId,orderId,gVersion);
						}
						layer.close(index);
				    }
				  });
				
			})
						
			//打电话按钮事件
			$('.phoneBtn').tap(function(){
				aonClick(0,'mobile=' + gMobile, mId);
			})
			
			//查看货源按钮事件
			$('.check-add').tap(function(){
				window.location.href = "${CONTEXT}v2/transfer/getGoodsDetail/" + tId;
			})
			
			function eClickPay(){
				$(".payButtom").tap(function(){
					var orderid=$(this).attr("orderid"),
					    drivermemberid=$(this).attr("drivermemberid"),
					    transferid=$(this).attr("transferid");
					layer.open({
						title: [
						        '我是标题',
						        'display:none'
						      ],
					    type: 1
					    , shadeClose: true
					    ,content: '<div class="layer-title">请输入运费</div>'+
					    		'<div class="con"><input type="text" class="payInput" size="14" maxlength="14"/></div>'+
					    		'<div class="buttom" orderid="'+orderid+'" drivermemberid="'+drivermemberid+'" transferid="'+transferid+'">确认支付</div>'
					    ,anim: 'up'
					    ,style: 'position:fixed; bottom:0; left:0; width: 100%; height: 200px; padding:10px 0; border:none;'
					  });
					enNull();
					eClickPayButtom(); 
				});
			}
			
			function eClickPayButtom(){
				$(".layui-layer .buttom").click(function(){
					var orderid=$(this).attr("orderid"),
						drivermemberid=$(this).attr("drivermemberid"),
						transferid=$(this).attr("transferid"),
						payAmt=$("#layui-layer1 .payInput").val();
					if(payAmt>0){
						payAjax(orderid,drivermemberid,payAmt,transferid);
					}else{
						layer.msg('运费不能小于0')
					}
				});
			}
			
			function enNull(){
				$(".payInput").keyup(function(){
					this.value=this.value.replace(/[^\-?\d.]/g,'');
				})
			}
		</script>
		<script src="${CONTEXT}js/transfer/global.js"></script>		
	</body>
</html>