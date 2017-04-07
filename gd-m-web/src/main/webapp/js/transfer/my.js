		//公共变量
		var memberId=getQueryString("memberId"),//用户的memberId
			pageNum=1,//页数
			pageSize=10,//每页的条数
			pageSize1=10,
			pageSize2=10,
			pageSize3=10,
			pageSize4=10;
		if(memberId==null){
			memberId=getQueryString("payerUserId");
		}

		/**
		 * 上拉加载，下拉刷新的js
		 */
			mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					down: {
						callback: pulldownRefresh
					},
					up: {
						contentrefresh: '正在加载...',
						contentnomore:'没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
						callback: pullupRefresh
					}
				}
			});
			$(".more-btn").tap(function(){
				var url = $(this).attr("href");
				if (!mui.os.plus) {
					mui.openWindow({
						url: url,
						id: url,
						show: {
							aniShow: 'zoom-fade-out',
							duration: 300
						}
					});
					return;
				}
			});
			function pulldownRefresh() {
				
				setTimeout(function() {
//					alert("下拉刷新");
//					alert(changeMark);
					switch (changeMark) {
					    case 0:
					        //更新已发布
					        yiFaBu();
					        break;
					    case 1:
					        //更新待确认
					        daiQueRen();
					        break;
					    case 2:
					        //更新待确认
					        daiShouHuo();
					        break;
					    case 3:
					        //更新待确认
					        yiWanCheng();
					        break;
					    case 4:
					        //更新待确认 
					        yiGuoQi();
					        break;
					    default:
					        // ...
					}
					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
				}, 1000);
			}
			

			function pullupRefresh() {	
				setTimeout(function() {
//					alert("上拉加载");
					switch (changeMark) {
					    case 0:
					        //更新已发布
					    	pageSize+=10;
					        yiFaBu();
					        break;
					    case 1:
					        //更新待确认
					    	pageSize1+=10;
					        daiQueRen();
					        break;
					    case 2:
					        //更新待确认
					    	pageSize2+=10;
					        daiShouHuo();
					        break;
					    case 3:
					        //更新待确认
					    	pageSize3+=10;
					        yiWanCheng();
					        break;
					    case 4:
					        //更新待确认
					    	pageSize4+=10;
					        yiGuoQi();
					        break;
					    default:
					        // ...
					}
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); //
				}, 1500);	
			
			}
			if (mui.os.plus) {
				mui.plusReady(function() {
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().pullupLoading();
					}, 1000);

				});
			} else {
				mui.ready(function() {
//					mui('#pullrefresh').pullRefresh().pullupLoading();
					//开始加载
					var status=getQueryString("status");
					console.log(status);
					switch (status) {
				    case '1011':
				        //更新已发布
				        yiFaBu();
				        break;
				    case '1012':
				        //更新待确认
				        $("#topHead a").each(function() {
							$(this).removeClass('bottom_red');
						});
						$("#aDeal a").addClass('bottom_red');
						changeMark=1;
						//这里写ajax请求
						daiQueRen();
				        break;
				    case '1013':
				        //更新待收货
//				        daiShouHuo();
						$("#topHead a").each(function() {
							$(this).removeClass('bottom_red');
						});
						$("#aDealed a").addClass('bottom_red');
						changeMark=2;
						//这里写ajax请求
						daiShouHuo();
				        break;
				    case '1014':
				        //更新已完成
				        $("#topHead a").each(function() {
							$(this).removeClass('bottom_red');
						});
						$("#aFinished a").addClass('bottom_red');
						changeMark=3;
						//这里写ajax请求
				        yiWanCheng();
				        break;
				    case '1015':
				        //更新已过期
				        $("#topHead a").each(function() {
							$(this).removeClass('bottom_red');
						});
						$("#aUnfinished a").addClass('bottom_red');
						changeMark=4;
						//这里写ajax请求
				        yiGuoQi();
				        break;
				    default:
				        yiFaBu();
				}
				});
			}

	/*	页面切换的js*/
		var changeMark=0;
	
		$(document).ready(function(){
			//左滑
			$(document).swipeleft(function(){
				changeHead("left");			
			});
			//右滑
			$(document).swiperight(function(){
				changeHead("right");

			});
			//点击已发布
			$("#aPublished").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				$(this).children("a").addClass('bottom_red');
				changeMark=0;
				//这里写ajax请求
				yiFaBu();
			});
			//点击待确认
			$("#aDeal").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				$(this).children("a").addClass('bottom_red');
				changeMark=1;
				//这里写ajax请求
				daiQueRen();
			});
			//点击待收货
			$("#aDealed").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				$(this).children("a").addClass('bottom_red');
				changeMark=2;
				//这里写ajax请求
				daiShouHuo();
			});
			//点击已完成
			$("#aFinished").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				$(this).children("a").addClass('bottom_red');
				changeMark=3;
				//这里写ajax请求
				yiWanCheng();
			});
			//点击已关闭
			$("#aUnfinished").tap(function(){
				$("#topHead a").each(function() {
					$(this).removeClass('bottom_red');
				});
				$(this).children("a").addClass('bottom_red');
				changeMark=4;
				//这里写ajax请求
				yiGuoQi();
			})
		})

		function changeHead(direction){
			if(changeMark==0){	//已发布
				if(direction=="left"){
					$("#topHead a").eq(0).removeClass('bottom_red').end().eq(1).addClass('bottom_red');
					//这里写ajax请求
					daiQueRen();
					changeMark=1;
				}else if(direction=="right"){

				}			

			}else if(changeMark==1){ //待确认
				if(direction=="left"){
					$("#topHead a").eq(1).removeClass('bottom_red').end().eq(2).addClass('bottom_red');
					//这里写ajax请求
					daiShouHuo();
					changeMark=2;				
				}else if(direction=="right"){
					$("#topHead a").eq(1).removeClass('bottom_red').end().eq(0).addClass('bottom_red');
					//这里写ajax请求
					yiFaBu();
					changeMark=0;	
				}	
				
			}else if(changeMark==2){ //待收货
				if(direction=="left"){
					$("#topHead a").eq(2).removeClass('bottom_red').end().eq(3).addClass('bottom_red');
					//这里写ajax请求
					yiWanCheng();
					changeMark=3;				
				}else if(direction=="right"){
					$("#topHead a").eq(2).removeClass('bottom_red').end().eq(1).addClass('bottom_red');
					//这里写ajax请求
					daiQueRen();
					changeMark=1;	
				}

			}else if(changeMark==3){ //已完成
				if(direction=="left"){
					$("#topHead a").eq(3).removeClass('bottom_red').end().eq(4).addClass('bottom_red');
					//这里写ajax请求
					yiGuoQi();
					changeMark=4;				
				}else if(direction=="right"){
					$("#topHead a").eq(3).removeClass('bottom_red').end().eq(2).addClass('bottom_red');
					//这里写ajax请求
					daiShouHuo()
					changeMark=2;	
				}

			}else if(changeMark==4){ //已关闭
				if(direction=="left"){				
				}else if(direction=="right"){
					$("#topHead a").eq(4).removeClass('bottom_red').end().eq(3).addClass('bottom_red');
					//这里写ajax请求
					yiWanCheng();
					changeMark=3;	
				}

			}else{
				console.log('changeHead方法有误');
			}
		}
		
		
//Edwin Function
//模拟数据
		
		
		

//与发布相关的方法
function yiFaBu(){
	$(".markAll").show();
	$.ajax( {  
        url:"getListData/1011",// 跳转到 action  
        data:{  
           memberId:memberId,
           pageNum:pageNum,
           pageSize:pageSize
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) {  
//      	console.log(data);
        	$(".markAll").hide();
        	
        	if(data.statusCode=="0"){
        		var obj =data.object.recordList;
        		
				//判断代发还是自发修改文字
				function bolZiFont(isProxy){
					var oHtml='';
					if(isProxy=='0'){
						oHtml='已发布';
					}else if(isProxy=='1'){
						oHtml='分配中'
					}
					return oHtml;
				}
				//判断代发还是自发显示删除按钮
				function bolZiDelete(isProxy){
//					console.log(oData.transferId);
					var oHtml='';
					if(isProxy=='0'){
						oHtml='<p class="bottomOne Delete" bol="1" transferId="'+oData.transferId+'" version="'+oData.version+'">删除</p>';
					}else if(isProxy=='1'){
						oHtml=''
					}
					return oHtml;
				}
				//循环的主体
				var mainHtml='';
				for(var i=0;i<obj.length;i++){
					var oData=obj[i];
					mainHtml+='<li>'
								+	'<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">'
								+		'<span>装货时间: '+oData.goodsDate+'</span>'
								+		'<div class="color_ff6c00 ab_right ">'+bolZiFont(oData.isProxy)+'</div>	'
								+	'</div>'
								+	'<article orderId="'+oData.orderId+'" transferId="'+oData.transferId+'" status="'+oData.status+'" class="address-con bor-bottom">'
								+		'<i class="bg-icon add-img"></i>'
								+		'<div class="fhd-text">'
								+			'<span><i class="bg-icon"></i>发货地</span>'
								+			'<span class="input-text">'+oData.startAddr+'</span>'
								+		'</div>'
								+		'<div class="mhd-text">'
								+			'<span><i class="bg-icon"></i>目的地</span>'
								+			'<span class="input-text">'+oData.endAddr+'</span>'
								+		'</div>'
								+	'</article>'
								+	bolZiDelete(oData.isProxy);
								+'</li>';
			//		console.log(bolZi(oData.isProxy));
				}
				$(".outline").html(mainHtml);
				bolData(obj);
        	}else{
        		myLayer(data.msg); 
        	}
            deleteBottom();
			jumpToDetails();
            },  
        error : function() {  
        	$(".markAll").hide();
               failedLoad();
            }  
   })
}

//与待确认相关的方法
function daiQueRen(){
	$(".markAll").show();
	$.ajax( {  
        url:"getListData/1012",// 跳转到 action  
        data:{  
           memberId:memberId,
           pageNum:pageNum,
           pageSize:pageSize1
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) {
        	$(".markAll").hide();
        	if(data.statusCode==0){
        		var obj =data.object.recordList;
        	//判断代发还是自发
        	function bolZi(isProxy){
        		var oHtml='';
        		if(isProxy=='0'){
        			oHtml='<div class="bottomAll">'
        				+			'<p class="bottomThree callPhone" mobile="'+oData.mobile+'">拨打电话</p>'
        				+			'<p class="bottomThree refuse" bol="1" transferId="'+oData.transferId+'" orderId="'+oData.orderId+'" version="'+oData.version+'"><img class="bottomMiddle" style="left:0;" src="../../v1.0/images/transport/bottomMiddle.jpg"/>拒绝<img class="bottomMiddle" style="right:0;" src="../../v1.0/images/transport/bottomMiddle.jpg"/></p>'
        				+			'<p class="color_ff6c00 bottomThree accept" bol="1" transferId="'+oData.transferId+'" version="'+oData.version+'" orderId="'+oData.orderId+'">接受</p>'
        				+		'</div>';
        		}else if(isProxy=='1'){
        			oHtml='<p class="bottomOne callPhone" mobile="'+oData.mobile+'">拨打电话</p>'
        		}
        		return oHtml;
        	}
        	//循环的主体
        	var mainHtml='';
        	for(var i=0;i<obj.length;i++){
        		var oData=obj[i];
        		mainHtml+='<li>'
        					+	'<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">'
        					+		'<span>装货时间: '+oData.goodsDate+'</span>'
        					+		'<div class="color_ff6c00 ab_right ">待确认</div>'
        					+	'</div>'
        					+	'<article orderId="'+oData.orderId+'" transferId="'+oData.transferId+'" status="'+oData.status+'" class="address-con bor-bottom">'
        					+		'<i class="bg-icon add-img"></i>'
        					+		'<div class="fhd-text">'
        					+			'<span><i class="bg-icon"></i>发货地</span>'
        					+			'<span class="input-text">'+oData.startAddr+'</span>'
        					+		'</div>'
        					+		'<div class="mhd-text">'
        					+			'<span><i class="bg-icon"></i>目的地</span>'
        					+			'<span class="input-text">'+oData.endAddr+'</span>'
        					+		'</div>'
        					+	'</article>'
        					+	bolZi(oData.isProxy);
        					+'</li>';
        	}
        	$(".outline").html(mainHtml);
        	bolData(obj);
        	}else{
        		myLayer(data.msg); 
        	}
        		jumpToDetails();
        		refuseBottom();
        		acceptBottom();
        		callPhone();
            },  
        error : function() { 
        	$(".markAll").hide();
               failedLoad();
            }  
   })
	
}

//待收货相关的方法
function daiShouHuo(){
	$(".markAll").show();
	$.ajax( {  
        url:"getListData/1013",// 跳转到 action  
        data:{  
           memberId:memberId,
           pageNum:pageNum,
           pageSize:pageSize2
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) { 
        	$(".markAll").hide();
        	//console.log(data)
        	if(data.statusCode==0){
        		var obj =data.object.recordList;
        		var mainHtml='';
        		
				//循环的主体
				
				for(var i=0;i<obj.length;i++){
					var oData=obj[i];
					mainHtml+='<li>'
								+	'<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">'
								+		'<span>运单号: '+oData.orderNo+'</span>'
								+		'<div class="color_ff6c00 ab_right ">待收货</div>'
								+	'</div>'
								+	'<article orderId="'+oData.orderId+'" transferId="'+oData.transferId+'" status="'+oData.status+'" class="address-con bor-bottom">'
								+		'<i class="bg-icon add-img"></i>'
								+		'<div class="fhd-text">'
								+			'<span><i class="bg-icon"></i>发货地</span>'
								+			'<span class="input-text">'+oData.startAddr+'</span>'
								+		'</div>'
								+		'<div class="mhd-text">'
								+			'<span><i class="bg-icon"></i>目的地</span>'
								+			'<span class="input-text">'+oData.endAddr+'</span>'
								+		'</div>'
								+	'</article>'
								+	'<div class="bottomAll">'
								+		'<p class="bottomTwo callPhone" mobile="'+oData.mobile+'">拨打电话</p>'
								+		'<p class="bottomTwo color_ff6c00 confirm" bol="1" orderId="'+oData.orderId+'" transferId="'+oData.transferId+'" version="'+oData.version+'"><img class="bottomMiddle" style="left:0;" src="../../v1.0/images/transport/bottomMiddle.jpg"/>确认收货</p>'
								+	'</div>'
								+'</li>';
							}
							$(".outline").html(mainHtml);
							bolData(obj);
        		}else{
        			myLayer(data.msg); 
        		}
            	jumpToDetails();
            	confirmBottom();
            	callPhone();
						},
        error : function() {
        	$(".markAll").hide();
               failedLoad();
            }  
   })
	
}

//已完成相关的方法
function yiWanCheng(){
	$(".markAll").show();
	$.ajax( {  
        url:"getListData/1014",// 跳转到 action  
        data:{  
           memberId:memberId,
           pageNum:pageNum,
           pageSize:pageSize3
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) {  
        	$(".markAll").hide();
        	console.log(data);
            if(data.statusCode==0){
            	var obj =data.object.recordList;
				//循环的主体
				function isPay(ispay){
					if(ispay=="1"){
						var i='<div class="bottomAll">'
								+	'<p class="bottomTwo callPhone" mobile="'+oData.mobile+'">拨打电话</p>'
								+	'<p class="bottomTwo color_ff6c00 payButtom" bol="1" orderId="'+oData.orderNo+'" transferId="'+oData.transferId+'" version="'+oData.version+'" driverMemberId="'+oData.driverMemberId+'"><img class="bottomMiddle" style="left:0;" src="../../v1.0/images/transport/bottomMiddle.jpg"/>支付运费</p>'
								+	'</div>';
						return i;
					}
					
					if(ispay=="2"){
						var i='<p class="bottomOne callPhone" mobile="'+oData.mobile+'">拨打电话</p>'
						return i;
					}
				}
				
				function isPay2(ispay){
					if(ispay=="1"){
						var i='<div class="color_ff6c00 ab_right ">待支付</div>';
						return i;
					}
					
					if(ispay=="2"){
						var i='<div class="color_ff6c00 ab_right ">已支付</div>';
						return i;
					}
				}
				var mainHtml='';
				for(var i=0;i<obj.length;i++){
					var oData=obj[i];
					mainHtml+='<li>'
								+	'<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">'
								+		'<span>运单号: '+oData.orderNo+'</span>'
								+	isPay2(oData.payStatus)
								+	'</div>'
								+	'<article orderId="'+oData.orderId+'" transferId="'+oData.transferId+'" status="'+oData.status+'" class="address-con bor-bottom">'
								+		'<i class="bg-icon add-img"></i>'
								+		'<div class="fhd-text">'
								+			'<span><i class="bg-icon"></i>发货地</span>'
								+			'<span class="input-text">'+oData.startAddr+'</span>'
								+		'</div>'
								+		'<div class="mhd-text">'
								+			'<span><i class="bg-icon"></i>目的地</span>'
								+			'<span class="input-text">'+oData.endAddr+'</span>'
								+		'</div>'
								+	'</article>'
								+isPay(oData.payStatus);
								+'</li>';
				}
				$(".outline").html(mainHtml);
				
				bolData(obj);
			}else{
				myLayer(data.msg); 
			}
				jumpToDetails();
				callPhone();
				eClickPay();
            },  
        error : function() { 
        	$(".markAll").hide();
               failedLoad();
            }  
   })
	
}

//已过期相关的方法
function yiGuoQi(){
	$(".markAll").show();
	$.ajax( {  
        url:"getListData/1015",// 跳转到 action  
        data:{  
           memberId:memberId,
           pageNum:pageNum,
           pageSize:pageSize4
        },  
        type:'get',  
        cache:false,  
        dataType:'json',  
        success:function(data) { 
        	$(".markAll").hide();
            if(data.statusCode==0){
            	var obj =data.object.recordList;
				//判断代发还是自发显示删除按钮
				function bolZiDelete(isProxy){
					var oHtml='';
					if(isProxy=='0'){
						oHtml='<p class="bottomOne Delete" bol="1" transferId="'+oData.transferId+'" version="'+oData.version+'">删除</p>';
					}else if(isProxy=='1'){
						oHtml=''
					}
					return oHtml;
				}
				//循环的主体
				var mainHtml='';
				for(var i=0;i<obj.length;i++){
					var oData=obj[i];
					mainHtml+='<li>'
								+	'<div class="row plr_10p ptb_6p fontSize_13p mt_5p borberBottom">'
								+		'<span>装货时间: '+oData.goodsDate+'</span>'
								+		'<div class="color_ff6c00 ab_right ">已过期</div>	'
								+	'</div>'
								+	'<article orderId="'+oData.orderId+'" transferId="'+oData.transferId+'" status="'+oData.status+'" class="address-con bor-bottom">'
								+		'<i class="bg-icon add-img"></i>'
								+		'<div class="fhd-text">'
								+			'<span><i class="bg-icon"></i>发货地</span>'
								+			'<span class="input-text">'+oData.startAddr+'</span>'
								+		'</div>'
								+		'<div class="mhd-text">'
								+			'<span><i class="bg-icon"></i>目的地</span>'
								+			'<span class="input-text">'+oData.endAddr+'</span>'
								+		'</div>'
								+	'</article>'
								+	bolZiDelete(oData.isProxy);
								+'</li>';
			//		console.log(bolZi(oData.isProxy));
				}
				$(".outline").html(mainHtml);
				bolData(obj);
			}else{
				myLayer(data.msg); 
			}
			deleteBottom();
			jumpToDetails();
            },  
        error : function() {
        	$(".markAll").hide();
               failedLoad();
            }  
   })
	
}

//点击删除按钮后调用的方法
function deleteBottom(){
	var bottomDelete=$(".Delete");
	bottomDelete.tap(function(){ 
		var eThis=$(this);
		 layer.open({
			    content: '删除货源后不能恢复，确定操作？'
			    ,btn: ['确认', '取消']
			    ,yes: function(index){
			    	var transferId=eThis.attr("transferId"),
						version=eThis.attr("version"),
						bol=eThis.attr("bol");
					if(bol=="1"){
						deleteAjax(memberId,transferId,version);
						eThis.attr("bol","0");
					}
			      layer.close(index);
			    }
			  });
	})
}

//点击拒绝物流按钮后调用的方法
function refuseBottom(){
	var bottomRefuse=$(".refuse");
	bottomRefuse.tap(function(){
		var eThis=$(this);
		layer.open({
		    content: '确认拒绝司机接单？'
		    ,btn: ['确认', '取消']
		    ,yes: function(index){
		    	var transferId=eThis.attr("transferId"),
					version=eThis.attr("version"),
					orderId=eThis.attr("orderId"),
					bol=eThis.attr("bol");
					if(bol=="1"){
						refuseAjax(memberId,transferId,orderId,version);
						eThis.attr("bol","0");
					}
		      layer.close(index);
		    }
		  });
		
	});
}

//点击接受物流按钮后调用的方法
function acceptBottom(){
	var bottomAccept=$(".accept");
	bottomAccept.tap(function(){
		var eThis=$(this);
		layer.open({
		    content: '确认接受司机接单？'
		    ,btn: ['确认', '取消']
		    ,yes: function(index){
		    	var transferId=eThis.attr("transferId"),
					version=eThis.attr("version"),
					orderId=eThis.attr("orderId"),
					bol=eThis.attr("bol");
					if(bol=="1"){
						acceptAjax(memberId,transferId,orderId,version);
						eThis.attr("bol","0");
					}
		      layer.close(index);
		    }
		  });
		
	});
}

//点击确认收货按钮后调用的方法
function confirmBottom(){
	var bottomConfirm=$(".confirm");
	bottomConfirm.tap(function(){
		var eThis=$(this);
			layer.open({
			    content: '确定收货？'
			    ,btn: ['确认', '取消']
			    ,yes: function(index){
					var transferId=eThis.attr("transferId"),
						version=eThis.attr("version"),
						orderId=eThis.attr("orderId"),
						bol=eThis.attr("bol");
						if(bol=="1"){
							confirmAjax(memberId,transferId,orderId,version);
							eThis.attr("bol","0");
						}
			      layer.close(index);
			    }
			  });
	});
}

//跳转到详情页的方法
function jumpToDetails(){
	var bottomJump=$(".address-con");
	bottomJump.tap(function(){
		var transferid=$(this).attr("transferid"),
			status=$(this).attr("status"),
			orderId=$(this).attr("orderId");
		window.location.href=context+'v2/transfer/getDetailPage?transferId='+transferid+'&memberId='+memberId+'&orderId='+orderId+'&status='+status;
	})
}

//点击拨打电话
function callPhone(){
	$(".callPhone").tap(function(){
		var mobile=$(this).attr("mobile");
			mobile="mobile="+mobile;
	 	aonClick(0,mobile,memberId);
	})
}

//判断是否有数据
function bolData(obj){
			
			if(obj.length==0){
					var mainHtml='';
        			mainHtml=	'<div id="noInfo" class="black_outline" style="margin-top: 193.5px;">'
							+		'<div class="mui-text-center">'
							+			'<img src="../../v1.0/images/blank_box.png" alt="" width="150"> '
							+		'</div>'
							+		'<div style="font-size:13px" class="mui-text-center blank">您还没有相关订单信息</div>'
							+	'</div>';
        			$("#published").html(mainHtml);
        		}
}

//成功删除后的方法
function deleteAfter(num){ 
//	window.top.location.reload();
					switch (changeMark) {
					    case 0:
					        //更新已发布
					        yiFaBu();
					        break;
					    case 1:
					        //更新待确认
//					        daiQueRen();
							if(num==1){
								changeMark=2;
								$("#topHead a").each(function() {
									$(this).removeClass('bottom_red');
								});
								$("#aDealed a").addClass('bottom_red');
								daiShouHuo();
							}else if(num==0){
								changeMark=0;
								$("#topHead a").each(function() {
									$(this).removeClass('bottom_red');
								});
								$("#aPublished a").addClass('bottom_red');
								yiFaBu();
							}
					        break;
					    case 2:
					        //更新待收货
//					        daiShouHuo();
							changeMark=3;
							$("#topHead a").each(function() {
									$(this).removeClass('bottom_red');
								});
								$("#aFinished a").addClass('bottom_red');
							yiWanCheng();
					        break;
					    case 3:
					        //更新已完成
					        yiWanCheng();
					        break;
					    case 4:
					        //更新已过期
					        yiGuoQi();
					        break;
					    default:
					        // ...
					}
}

function eClickPay(){
	$(".payButtom").tap(function(){
		var orderid=$(this).attr("orderid"),
		    drivermemberid=$(this).attr("drivermemberid"),
		    transferid=$(this).attr("transferid")
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
			payAmt=$(".layui-layer .con input").val();
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


function setStatus(){}

	

