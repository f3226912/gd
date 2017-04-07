var SubmitOrder = {
	data: {},
	productNum: 0,
	indexUrl : "",
	init: function(){
		// this.makeQrcode({statusCode: 0 , msg: "3333"})
		if ($.appVersion.ios) {
	        $('.mui-bar').addClass('ios');
	        $('.order-wrap').addClass('ios');
	    };
	    
		//初始化本地存储数据
		var data = $.get("orderProductDetail");
		this.data.orderProductDetail = data? JSON.parse(data) : [];
		this.data.orderBaseInfo = {
			activityType:2,
			payType:2,
			orderStatus:1,
		    sellMemberId : $.get("sellMemberId"),
		    shopName : $.get("shopName"),
		    businessId : $.get("businessId")
		}
		this.indexUrl = 'purchase.html?memberId=' + this.data.orderBaseInfo.sellMemberId 
			+ '&shopName=' + this.data.orderBaseInfo.shopName 
			+ '&businessId=' + this.data.orderBaseInfo.businessId;
		// 模拟数据
		// this.data.orderBaseInfo = {
		// 	activityType:2,
		// 	payType:2,
		// 	orderStatus:1,
		//     sellMemberId : 2,
		//     shopName : "老王肉铺",
		//     businessId : 10001
		// }
		// this.data.orderProductDetail = [
		// 	{
		// 	"productId":10,
		// 	"productName":"jdofjejflajkdkkkkkkkkkkkkkkkkkkkkkkkkkkkkk",
		// 	"purQuantity":5,
		// 	"price":10,
		// 	"price1":10,
		// 	"purQuantity1":4,
		// 	"subTotal1":40.00,
		// 	"price2":10.00,
		// 	"subTotal":"10.00",
		// 	"remark":"只要肉，不带血",
		// 	"tradingPrice":100.00
		// 	},
		// 	{
		// 	"productId":11,
		// 	"productName":"狗肉",
		// 	"purQuantity":5,
		// 	"price":10,
		// 	"price1":10,
		// 	"purQuantity1":4,
		// 	"subTotal1":40.00,
		// 	"price2":10.00,
		// 	"subTotal":"10.00",
		// 	"remark":"只要肉，不带血",
		// 	"tradingPrice":999.99
		// 	},
		// 	{
		// 	"productId":11,
		// 	"productName":"带鱼",
		// 	"purQuantity":5,
		// 	"price":10,
		// 	"price1":10,
		// 	"purQuantity1":4,
		// 	"subTotal1":40.00,
		// 	"price2":10.00,
		// 	"subTotal":"10.00",
		// 	"remark":"只要肉，不带血",
		// 	"tradingPrice":300.00
		// 	},
		// 	{
		// 	"productId":11,
		// 	"productName":"带鱼",
		// 	"purQuantity":5,
		// 	"price":10,
		// 	"price1":10,
		// 	"purQuantity1":4,
		// 	"subTotal1":40.00,
		// 	"price2":10.00,
		// 	"subTotal":"10.00",
		// 	"remark":"只要肉，不带血",
		// 	"tradingPrice":300.00
		// 	}
		// ];
		this.render();
		this.calPrice();
		this.eventBind();
	},
	render: function(){
		$(".OrderList").html($("#temp").tmpl({data: this.data.orderProductDetail}));
		this.productNum = this.data.orderProductDetail.length;
		$("#productNum").html(this.productNum);
		if($(".Switch").attr("status") == "open"){
			$(".OrderList").find("li:not(:first-child)").hide();
		}
		if(this.productNum <= 1){
			$(".switch-bar").hide();
			$(".Switch").attr("status", "");
		}
	},
	calPrice: function(){
		var num = 0;
		for(var i = 0, len = this.data.orderProductDetail.length; i < len; i++){
			num += this.data.orderProductDetail[i].tradingPrice;
		}
		this.data.orderBaseInfo.orderAmount = num;
		this.data.orderBaseInfo.payAmount = num;
		$(".InitPrice").html(num);
	},
	eventBind: function(){
		var _this = this;
		// 展开关闭按钮切换事件
		$(".Switch").on("touchstart", function(){
			event.preventDefault();
			var self = $(this);
			if(self.attr("status") == "open"){
				self.prev().find("li").show();
				self.attr("status", "close");
				self.removeClass("open").addClass("close");
				self.find("span").html("关闭");
			}else if(self.attr("status") == "close"){
				self.prev().find("li:not(:first-child)").hide();
				self.attr("status", "open");
				self.removeClass("close").addClass("open");
				self.find("span").html("展开");
			}
		});
		// 提交订单数据，生成二维码
		$(".GenQrcode").on("touchstart", function(){
			var obj = {};
			if(_this.data.orderBaseInfo.payAmount < 0){
				mui.toast('实收款不能小于0');
				return;
			}else if(_this.data.orderBaseInfo.payAmount > 99999999.99){
				mui.toast('订单金额不能大于99999999.99');
				return;
			}
			obj.orderProductDetail = JSON.stringify(_this.data.orderProductDetail);
			obj.orderBaseInfo = JSON.stringify(_this.data.orderBaseInfo);
			var anVal = JSON.stringify(obj);
			// ios查询参数串
			var iosVal = {
				data: obj,
				fun: "handleData"
			};
			var data = $.enCode(anVal, JSON.stringify(iosVal), 'handleData', function(data){
				_this.submitOrder(data);
			});
			if($.appVersion.android){
				_this.submitOrder(data);
			}
		});
		// $(".mark-warp").on("touchstart", function(){
		// 	$(this).hide();
		// });
		// 删除按钮绑定事件
		$(".OrderList").on("touchstart", ".del-btn", function(){
			var self = $(this);
			var btnArray = ['取消', '确认'];
			if(_this.productNum <= 1 ){
				mui.confirm('确认放弃该订单，返回现场采销吗？', '', btnArray, function(e) {
					if (e.index == 1) {
						var i = +self.attr("index");
						_this.data.orderProductDetail.splice(i,1);
						$.set("orderProductDetail",JSON.stringify(_this.data.orderProductDetail));
						window.location.href = _this.indexUrl;
					}
				});
				return;
			}
			mui.confirm('确认删除该商品吗？', '', btnArray, function(e) {
				if (e.index == 1) {
					var i = +self.attr("index");
					_this.data.orderProductDetail.splice(i,1);
					// self.parent().parent().remove();
					$.set("orderProductDetail",JSON.stringify(_this.data.orderProductDetail));
					_this.calPrice();
					_this.render();
					$(".price-list .edit-price").hide();
					$(".OrderList").find("li:first-child").show();
					if(_this.productNum <= 1){
						$(".switch-bar").hide();
						$(".Switch").attr("status", "");
					}
				}
			});
		});
		var regStrs = [
	        ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
	        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
	        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
	        ['^(\\d+\\.\\d{2}).+', '$1'], //禁止录入小数点后两位以上
	        ['^(\\d{8})\\d+(.\\d+)?$', '$1$2'] //禁止整数部分超过8位
	    ];
		// 修改商品总价格
		$("#editPrice").on("touchstart", function(){
			var btnArray = ['取消', '确定'];
			// 验证输入数值是否合法，2位小数点
			// var reg = /(^[1-9]([0-9]{0,7})?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			mui.prompt('请输入商品总价格：', '0.00', '修改商品总价格', btnArray, function(e) {
				if (e.index == 1) {
					if(e.value != _this.data.orderBaseInfo.orderAmount){
						_this.data.orderBaseInfo.payAmount = e.value;
						$(".TotalPrice").html(e.value);
						$(".price-list .edit-price").show();
					}else{
						$(".TotalPrice").html(e.value);
						$(".price-list .edit-price").hide();
					}
				}
			});
			var inputObj = $('.mui-popup-input input');
			inputObj.attr('type', 'number');
			inputObj.blur();
			inputObj.focus();
			inputObj.on("keyup", function(){
			    for(i=0; i<regStrs.length; i++){
			        var reg = new RegExp(regStrs[i][0]);
			        $(this).val($(this).val().replace(reg, regStrs[i][1]));
			    }
			});
			// 处理手机弹出键盘挡住输入框问题
			$(".mui-popup-in").css("top", "30%");
			inputObj.on("blur", function(){
		    	$(".mui-popup-in").css("top", "50%");
		    });
		});
		// 跳转到客户端订单界面
		$("#toOrder").on("touchstart", function(event){
			event.preventDefault();
			if($.appVersion.ios){
				window.webkit.messageHandlers.NativeMethod.postMessage("toOrderList");
			}
			else if($.appVersion.android){
				JsBridge.toOrderList();
			}
		});
		// 点击添加商品按钮
		$("#addPro").on("touchstart", function(){
			window.location.href = _this.indexUrl + '&app=2';
		});
		// 点击继续制单按钮
		$("#makeOrder").on("touchstart", function(){
			var btnArray = ['取消', '返回现场采销'];
			mui.confirm('请确认二维码已扫描', '继续制单', btnArray, function(e) {
				if (e.index == 1) {
					window.location.href = _this.indexUrl;
				}
			});
		});
		// 点击返回按钮
		$("#return").on("touchstart", function(){
			var btnArray = ['取消', '确认'];
			mui.confirm('确认放弃该订单，返回现场采销？', "", btnArray, function(e) {
				if (e.index == 1) {
					// 清除本地缓存商品列表数据
					$.remove("orderProductDetail");
					window.location.href = _this.indexUrl;
				}
			});
		});
	},
	submitOrder: function(param){
		var _this = this;
		//var curWwwPath = "https://test201.gdeng.cn:8086";
        $.ajax({
			url: "/v33/order/miningOrderAdd",
			 //url: "/gd-api/v33/order/miningOrderAdd",
			data : {"param" : param},
			type: "get",
			dataType: "text",
			contentType: "application/json",
			cache: false,
			success: function(data){
				var iosVal = {
					data: data,
					fun: "decryptCompleted"
				};
				var d = $.deCode(data, JSON.stringify(iosVal), "decryptCompleted", function(d){
					_this.makeQrcode(JSON.parse(d));
				});
				
				if($.appVersion.android){
					_this.makeQrcode(d);
				}
				// 清除本地缓存商品列表数据
				$.remove("orderProductDetail");
			}
		});
	},
	makeQrcode: function(data){
		if(data.statusCode == 0){
			var obj = {
				type: "2",
				object: {
					orderNo: data.msg  //订单号
				}
			}
			$("#qrcode").empty();
			// var qrcode = new QRCode(document.getElementById("qrcode"), {
			// 	width : 150,
			// 	height : 150,
			// 	render: "table"
			// });
			// qrcode.makeCode(JSON.stringify(obj));
			$("#qrcode").qrcode({width: 150, height: 150, correctLevel:0,render: "table",text: JSON.stringify(obj)});   
			$(".mark-warp").show();
    	}else{
    		mui.toast(data.msg);
    	}
	}
}

$(function(){
	SubmitOrder.init();
});


