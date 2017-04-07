			
	//参数获取方法
			var $param = decodeURIComponent(location.search).replace("?", "");
			var $map = paramMap($param);
			var listing;

			function search(key) {
			    for (var j = 0; j < $map.length; j++) {
			        if (key == $map[j].key) {
			            return $map[j].value;
			        }
			    }
			    return null;
			}

			function paramMap(source) {
			    var array_one = source.split("&");
			    var array_two;
			    var map = new Array();
			    for (var i = 0; i < array_one.length; i++) {
			        array_two = array_one[i].split("=");
			        map.push(new bean(array_two[0], array_two[1]));
			    }
			    return map;
			}

			function bean(key, value) {
			    this.key = key;
			    this.value = value;
			    return this;
			};

			//保留小数点两位方法
			function numberTwo(numVal) {
				
			    var numTwo = parseFloat((parseFloat(numVal).toFixed(3)).substring(0, (parseFloat(numVal).toFixed(3)).length - 1));
			    //if(numVal>99999999.99){
					//numTwo =99999999.99;
				//}
			    return numTwo;
			}
		
    mui.init();
    //ios导航处理
    if ($.appVersion.ios) {
        $('.mui-bar').addClass('ios');
        $('.mui-content').addClass('ios')
    };
		    var name=search("name");
			var nameId = search("id");
			var price = search("price") || '';
			var edit = search("edit");
			var index = search("index");
			var imgUrl = search("imgUrl");
			//本地存储
			var storage = window.localStorage;
			var subTotal, subTotal1, price1, remark, purQuantity, purQuantity1, price2;
			// 编辑状态
			if(edit){
				var data = storage.getItem("orderProductDetail");
				var product = JSON.parse(data)[index];
				console.log(product);
				name = product.productName;
				price = product.price;
				nameId = product.productId;
				imgUrl = product.imgUrl;
				price1 = product.price1;
				price2 = product.price2;
				purQuantity = product.purQuantity;
				purQuantity1 = product.purQuantity1;
				remark = product.remark;
				namePrice = product.tradingPrice;
				subTotal = product.subTotal;
				subTotal1 = product.subTotal1;

				$(".allPrice").text(namePrice);
				$("#priceInput").val(price);
				$("#price1Input").val(price1);
				$("#price2Input").val(price2);
				$("#totalPrice1").val(subTotal);
				$("#totalPrice2").html(subTotal1);
				$("#totalPrice3").html(price2);
				$("#productNum").val(purQuantity);
				$(".tectarea-cont textarea").val(remark);
				$("#productNum2").val(purQuantity1);
			}
			$(".mui-title").text(name);
			$("#priceInput").val(price);
			var regStrs = [
		        ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
		        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
		        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
		        ['^(\\d+\\.\\d{2}).+', '$1'], //禁止录入小数点后两位以上
		        ['^(\\d{8})\\d+(.\\d+)?$', '$1$2'] //禁止整数部分超过8位
		    ];
			//填写方式一的输入
			var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			// /^((?!0)\d+(.\d{1,2})?)$/;// /^[1-9]\d*(?:\.\d{1,2}|\d*)$/;
			var $priceInput , $productNum , $totalPrice;
			$(".priceOne").on("keyup change", ".formInput", function(){
			    $(".tips").removeClass("tipsActive");
			    $(this).addClass("inputOver");
			    for(i=0; i<regStrs.length; i++){
			        var reg = new RegExp(regStrs[i][0]);
			        $(this).val($(this).val().replace(reg, regStrs[i][1]));
			    }
			    var val = $(this).val();
			    var nextVal = $(this).parents(".form-list").siblings().find(".formInput").val();
			    var nextDom = $(this).parents(".form-list").siblings().find(".formInput");
			    if($(this).val() === ''){
			    	$("#totalPrice1").val('');
			    }
			    console.log("$priceInput "+$priceInput)
			    if($totalPrice >0){
			    	
			    	console.log("$totalPrice : "+$totalPrice)
			    	if(nextDom.hasClass("inputOver")){
			    		console.log(0)
			    		$("#totalPrice1").val(numberTwo(numberTwo(val) * numberTwo(nextVal)))
			    	}else if(val>0){
			    		console.log("2")
			    	nextDom.val(numberTwo($(".totalPrice").val()/val));	
			    	}
			    	allPrice();
			    }else{
			    	console.log("$totalPrice11 : "+$totalPrice)
				   // if (reg.test(val)) {
				        
				        var totalVal = subTotal = numberTwo(numberTwo(val) * numberTwo(nextVal));
				        $(this).parents(".form-cont").find(".totalPrice").val(numberTwo(totalVal));
				        allPrice();
				   // }
			   }
			    $priceInput = $("#priceInput").val();
			    $productNum = $("#productNum").val();
			});
			// 输入小计1根据采购量计算出单价
			
			$(".priceOne").on("keyup change",".totalPrice",function(){
				//$(this).val($(this).val().replace(/^\d{0,7}(\.\d{0,2})?$/g),'')
				//$(this).val($(this).val().replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'))
				for(i=0; i<regStrs.length; i++){
			        var reg = new RegExp(regStrs[i][0]);
			        $(this).val($(this).val().replace(reg, regStrs[i][1]));
			    }
			
				//$(this).val().replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')
				// if($("#priceInput").val() == '' && $("#productNum").val() > 0){
				// 	$("#priceInput").val(numberTwo($(this).val()/$("#productNum").val()))
				// }else if($("#productNum").val() == '' && $("#priceInput").val() > 0){
				// 	$("#productNum").val(numberTwo($(this).val()/$("#priceInput").val()))
				// }
				//console.log("$priceInput : "+$priceInput)
				//console.log("$productNum : "+$productNum)
				if($(this).val() != ''){
					if($productNum !== '' && $priceInput == ''){
					$("#priceInput").val(numberTwo($(this).val()/$("#productNum").val()))
					}else if($productNum == '' && $priceInput != ''){
						$("#productNum").val(numberTwo($(this).val()/$("#priceInput").val()))
					}else if($productNum !== '' && $priceInput !== ''){
						$("#priceInput").val(numberTwo($(this).val()/$("#productNum").val()))
					}
				}
				
				$totalPrice = numberTwo($(".totalPrice").val());
				allPrice();
			});
			
			//填写方式二的输入
			$(".small-input").on("keyup change", function() {
			    $(".tips").removeClass("tipsActive");
			   // $(this).val($(this).val().replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'))
			   for(i=0; i<regStrs.length; i++){
			        var reg = new RegExp(regStrs[i][0]);
			        $(this).val($(this).val().replace(reg, regStrs[i][1]));
			    }
			    var val = $(this).val() || 0;
			    //if (reg.test(val)) {
			        var nextVal = $(this).siblings(".small-input").val() || 0;
			        var totalVal = subTotal1 = numberTwo(numberTwo(val) * numberTwo(nextVal));
			        $(this).parents(".form-cont").find(".totalPrice").text(numberTwo(totalVal));
			        allPrice();
			   // } else {

			       // $(".tips").addClass("tipsActive");
			        //$(this).val('');
			    //}
			});

			//填写方式三的输入
			$(".priceThree").on("keyup change", ".form-input", function() {
			    $(".tips").removeClass("tipsActive");
			  // $(this).val($(this).val().replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'))
			    //var val = price2 = numberTwo($(this).val());
			    for(i=0; i<regStrs.length; i++){
			        var reg = new RegExp(regStrs[i][0]);
			        $(this).val($(this).val().replace(reg, regStrs[i][1]));
			    }
			    var val = $(this).val();
			    $(".priceThree .totalPrice").text(val);
			    //if(val !== ''){
			    	allPrice();
			   // }
			     
			    console.log(val)
			    
			    //console.log(val)
			    
			    if (!reg.test(val)) {
			        $(".tips").addClass("tipsActive");
			       }
			});
			$(".main-cont").on("blur", "input", function() {
			        $(this).val(numberTwo($(this).val()))
			        /*if($("#totalPrice3")>99999999.99){
			        	$("#totalPrice3").text(99999999.99)
			        }*/
			    })
			
			//备注输入
			/*$(".tectarea-cont").on("keyup",'textarea',function(){
				var valLen = $(this).val().length;
				if(valLen > 0){
					$(".limitText").show();
					$(".limitNum").text(50-valLen)
				}else{
					$(".limitText").hide();
				}
			})*/
			    //总价计算
			function allPrice() {
			    var allPrice = numberTwo(Number($(".priceOne .totalPrice").val()) + Number($(".priceTwo .totalPrice").text()) + Number($(".priceThree .totalPrice").text()));
			    $(".allPrice").text(allPrice);
			}
			//返回
			$(".backGo").on("click", function() {
			    window.history.back(-1);
			});
			//storage.clear();

			var namePrice = '';
			var storageVal = storage.getItem("orderProductDetail");
			//console.log(JSON.parse(storageVal));
			var orderProductDetail = JSON.parse(storageVal) || [];
			//console.log("orderProductDetail "+orderProductDetail)

			//点击确定跳转

			mui("body").on("tap","#btn",function(e){
				e.stopPropagation();
				e.preventDefault();
			    namePrice = Number($(".allPrice").text());
			    price = $("#priceInput").val();
			    price1 = $("#price1Input").val();
			    purQuantity = $("#productNum").val();
			    remark = $(".tectarea-cont textarea").val();
			    purQuantity1 = $("#productNum2").val();
			    subTotal = $("#totalPrice1").val();
			    subTotal1 = Number($("#totalPrice2").text());
			    price2 = Number($("#totalPrice3").text())
			    var dataVal = {
			        productId: nameId,
			        productName: name,
			        imgUrl:imgUrl,
			        purQuantity: purQuantity,
			        price: price,
			        price1: price1,
			        purQuantity1: purQuantity1,
			        subTotal: subTotal,
			        subTotal1: subTotal1,
			        price2: price2,
			        remark: remark,
			        tradingPrice: namePrice
			    }


			    //console.log("strong "+storage.getItem("orderProductDetail").replace(/\\| /g,""))
			    var h = $(document).height() - $(window).height()-10;
		    	//console.log($(document).height()+":"+$(window).height()+":"+h)
		    	$(document).scrollTop(h);
		    	console.log("a "+Number($("#totalPrice2").text()))
			    if (namePrice < 0 || namePrice == 0) {
			    	mui.toast('合计金额应大于0，请重新输入',{duration:3000});
			    }else if(namePrice > 99999999.99){			   
					mui.toast('合计金额太大，请重新输入',{duration:3000});
			    }else if($("#totalPrice1").val() === '' && Number($("#totalPrice2").text())==0){
					mui.toast('小计1或小计2不能为空，请重新输入',{duration:3000});
			    	//mui.alert('小计1不能为空，请重新输入', '提示');
			    	//
			    }else if($("#totalPrice1").val() !== '' && price ==''){
			    	mui.toast('商品单价不能为空，请重新输入',{duration:3000});
			    }else if($("#totalPrice1").val() !== '' && price ==''){
			    	mui.toast('采购量不能为空，请重新输入',{duration:3000});
			    } else {
			        
			        if (edit) {
			            orderProductDetail[index] = dataVal;
			        } else {
			            orderProductDetail.push(dataVal);
			        }
			        storage.setItem("orderProductDetail", JSON.stringify(orderProductDetail));
			        window.location.href = "submitOrder.html"
			    }
			})
			$(".filter-btn").on("tap",function(){
				$(".result-main").hide();
			})
