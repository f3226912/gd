$(function(){
	var $form=$("form:visible");
	$(".reg-tab li").click(function(){
		var $self = $(this),
			index = $self.index();
		$self.addClass("item-cur").siblings().removeClass("item-cur")
		changeType(index);
		//console.log($form);
	});

	$('.change-item').change(function(){
		var val = $(this).val();
		if(val!=0){
			changeItem(val);
		}
	});

    $(".js-showAgreement").click(function(){
        layer.open({
            type: 2,
            btn:"同意并继续",
            title: "用户注册协议", 
            area: ['983px', '600px'],
		    skin: 'layui-layer-rim', //加上边框
		    content: ['agreement.html'],
		    cusClass:'comDialog',
            cancel: function(index){
                layer.close(index);
            }
        });
        
    });

    (function(){
        var searchStr = window.location.search.substr(1),
            arr = searchStr.split("&"),
            regObj = {},
            $colObj = $(".reg-tab li");
        for(var i = 0 ;i<arr.length;i++){
            regObj[arr[i].split("=")[0]] = arr[i].split("=")[1]
        }
        $colObj.eq(regObj.regtype).trigger("click");
        $(".reg-process ul").eq(regObj.regtype).show().siblings().hide();
    })();

	(function(){		
		$form.find("input").blur(function(){
			var $th=$(this),
				$val=$.trim($th.val()),
				$leb=$th.parent("span.bor").siblings("span.info"),
				defaultvalue = $th.attr("placeholder");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ");
			switch($th.attr("name")){	
				case "username"://用户名
					var length=$val.replace(/[\u2E80-\u9FFF]/g,"aa").length;
					if($val!=defaultvalue){
						if(length>=1&&!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/g.test($val)){
							$leb.addClass("info-err").html("用户名只能由英文字母、中文、数字、下划线组成");
						}else if(length>=4&&!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/g.test($val)){
							$leb.addClass("info-err").html("4-20个字符，可包含英文字母、中文、数字和下划线。");
						}else if(length<4||length>20||!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/g.test($val)){
							$leb.addClass("info-err").html($th.attr("data-err"));
						}else if(length>=4&&/^[0-9]/g.test($val)){
							$leb.addClass("info-err").html("用户名不能以数字开头");
						}else{
							$leb.addClass("onload").html("正在进行校验，请稍候...");
							var param = {};
							$.ajax({
								url:"checkUsername.html?username=" + $val,
								type: "post",
								data: param,
								async: false,
								success: function(data) {
									if (data.result == 1) {
										$leb.addClass("info-succ").html("&nbsp;");
									} else {
										$leb.addClass("info-err").html(data.msg);
									}
								},
								error:function(){
									$leb.addClass("info-err").html("网络繁忙，请稍后重试。");
								}
							});
						}	
					}else{
						check_empty($th,"请输入用户名");
					}
					
				break;			
				case "password"://密码
					var length=$val.length,
						sibLeb=$leb.addClass("info-err");
					if(length==0){
						sibLeb.html("登录密码不能为空");
					}else if(length<6||length>20){
						sibLeb.html($th.attr("data-err"));
					}else if(length>=6&&/^[0-9]+$/g.test($val)){
						sibLeb.html("密码不能为纯数字");
					}else if(length>=6&&/^[A-Z]+$/g.test($val)){
						sibLeb.html("密码不能为纯大写字母");
					}else if(length>=6&&/^\W+$/g.test($val)){
						sibLeb.html("密码不能为纯符号");
					}else if(length>=6&&/^\s+$/g.test($val)){
						sibLeb.html("密码不能包含空格");
					}else{
						$leb.removeClass("info-err").addClass("info-succ").html("&nbsp;");
					}
					/*
					else if(length>=6&&/^[a-z]+$/g.test($val)){
						sibLeb.html("密码不能为纯小写字母");
					}
					*/
				break;
				case "confirmPassword"://重复密码
					var pwdOne=$form.find("input[name='password']").val();
					if($val.length==0){
						$leb.addClass("info-err").html("确认密码不能为空");
						
					}else if($val.length<6){
						$leb.addClass("info-err").html($th.attr("data-err"));
						
					}else if(pwdOne.length>=6&&pwdOne!=$val&&$val.length>=6){
						$leb.addClass("info-err").html("两次密码输入不一致");
						
					}else if(pwdOne.length>=6&&pwdOne==$val){
						$leb.addClass("info-succ").html("&nbsp;");
					}else{
						$leb.html("");
					}
				break;
				case "mobile"://手机号
					phone($th);
					check_empty($th,"请输入您的手机号码");
				break;
				case "mobileVerifyCode"://手机验证码
					check_empty($th,"请输入验证码");
				break;
				// case "code"://验证码
				// 	var code = $form.find("input[name='code']").val();
				// 	if($val.length==0){
				// 		$leb.addClass("info-err").html("验证码不能为空");
				// 	}else{
				// 		$.ajax({
				// 			url:"/user/newregister.html",
				// 			type:"post",
				// 			data:{'code':code,'type':'code'},
				// 			dataType:"json",
				// 			success:function(data){
				// 				switch(data){
				// 					case "1":
				// 						$leb.addClass("info-succ").html("&nbsp;");
				// 						break;
				// 					case "3":
				// 						$leb.addClass("info-err").html("验证码不正确");
				// 						break;
				// 				}
				// 			},
				// 			error:function(){
				// 				$leb.addClass("info-err").html("服务器没有返回数据，可能服务器忙");
				// 			}
				// 		});
				// 	}
				// break;
				
				case "shopname"://手机验证码
					var leng=getBLen($val);
					check_empty($th,"请输入商铺名");
					// if(leng<=0){

					// }
				break;
				case "tradeProduct"://手机验证码
					var leng=getBLen($val);
					check_empty($th,"请正确输主营产品");
				break;				

			}

		}).focus(function(){
			var $th=$(this),
				$leb=$th.parent("span.bor").siblings("span.info");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ");
			$leb.removeClass("onload");
			if($th.attr("name")=="userName"){
				$leb.html($th.attr("data-po"));
			}
		});	

		//手机验证
		var phone=function(tmp){
			var $th=tmp,$val=$th.val(),
				$leb=$th.parent("span.bor").siblings("span.info"),
				defaultvalue = $th.attr("placeholder");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ");
			$leb.removeClass("onload");
			$(".mcodebtn").removeClass("clk-bg");
			if($val.length==11&&/(^[1][34587][0-9]{9}$)/g.test($val)){
				$th.parent("span.bor").siblings("span.info").addClass("onload").html("正在进行合法性校验，请稍候...");
				$.ajax({
					url:$th.attr("data-url"),
					type:"post",
					dataType:"json",
					data:{'type':'check_phone','phone':$val},
					success:function(data){
						switch(data){
							case "1":
								$leb.addClass("info-succ").html("&nbsp;");
								$(".mcodebtn").addClass("clk-bg");
								return false;
								break;
							case "22":
								$leb.addClass("info-err").html("手机号码为空");
								return false;
								break;
							case "6":
								$leb.addClass("info-err").html("注册手机号重复");
								return false;
								break;
							case "8":
								$leb.addClass("info-err").html("手机号码格式错误");
								return false;
								break;
							case "29":
								$leb.addClass("info-err").html("验证码的发送时间间隔为60秒，请稍后再试");
								return false;
								break;
						}
					}
				});
				
			}else if($val.length<11||$val==defaultvalue){
				$leb.addClass("info-err").html($th.attr("data-err"));
				return false;
			}
			return true
		}
		var check_empty=function(tmp,msg){
			var $leb=tmp.parent("span.bor").siblings("span.info"),
				defaultvalue = tmp.attr("placeholder");
			if(tmp.val()==""||tmp.val()==defaultvalue){
				$leb.addClass("info-err").html(msg);
				return false;
			}
			return true;
		}
		
		var register=true;
		var registerPush=function(){
			// if(!ref){
			// 	return false;
			// }
			for(var i=0;i<$form.find(".input-text").length;i++){
				if($form.find(".input-text").eq(i).val()=="" ){
					$form.find(".input-text").eq(i).focus();
					return false;
				}
			}
			for(var x=0;x<$form.find("select").length;x++){
				var $leb=$form.find("select").eq(x).parent("span.bor").siblings("span.info");
				if($form.find("select").eq(x).val()==0 ){
					$leb.addClass("info-err").html($form.find("select").eq(x).attr("data-err"))
					return false;
				}
			}
			for(var y=0;y<$form.find("checkbox").length;y++){
				var $leb=$form.find("checkbox").eq(y).parents("span.bor").siblings("span.info");
				if($form.find("checkbox:checked").length<=0 ){
					$leb.addClass("info-err").html("请选择服务项目")
					return false;
				}
			}
			if(register&&$(".info-err").length<=0){
				register=false;
				$('.btn-reg').html('提交中...');
				//设置密码加密
				var loadMask = layer.load(1);
				// var pwdval=$("input[name='password']").val(),
				// 	pwdval2=$("input[name='confirmPassword']").val();

				var param=$form.serialize();
				$.ajax({
					url:$form.attr("data-url"),
					data:param,
					type:"POST",
					dataType: "json",
					success: function(data){
						//data需要返回{"code":"1","regType":"0","regName":"sss","regstep":"2"}
						//code用作判断是否注册成功，regType表示注册的身份，0：采购商，1：农批商，2：产地供应商;regName为注册用户名;regstep用作表示是第几步，前端需要依据判断该显示哪一步
						register=true;
						changeProbar(data.regstep);
						changeType(data.regType,data.regstep);
						switch(data.code){
							case "1":
								//注册成功
								//$form.submit();
								layer.closeAll('loading');
								$('.btn-reg').addClass("no-push");
								
								if(data.regType == 0){
									if(data.regstep==2){
										$(".reg-step1").hide();
										$(".reg-result-box").eq(0).show();
										//changeProbar(data.regType);
										$(".reg-result-box").eq(0).find(".regName").text(data.regName)
															.end().find(".regType").text("采购商");
									}
									
								}else if(data.regType == 1){
									if(data.regstep==2){
										$(".reg-step1").hide();
										$(".nps-step2").show();										
									}else{
										$(".nps-step2").hide();	
										$(".reg-result-box").eq(1).show().find(".regName").text(data.regName)
															.end().find(".regType").text("农批商");
									}
									
								}else if(data.regType == 2){
									if(data.regstep==2){
										$(".reg-step1").hide();
										$(".cds-step2").show();										
									}else{
										$(".cds-step2").hide();
										$(".reg-result-box").eq(2).show().find(".regName").text(data.regName)
															.end().find(".regType").text("产地供应商");
									}
								}								

							break;
							case "error":
								layer.closeAll('loading');
								fnbreak($form.find("input:first"),"注册失败，请检查注册项是否错误");
								break;
						}

					},
					error:function(){
						fnbreak($form.find("input:first"),"网络繁忙，请稍后重试");
					}
				});
			}
		}

		var fnbreak=function(leb,info){
			$form.find('.btn-reg').removeClass("no-push").html('免费注册');
			leb.parent().siblings(".info").removeClass("info-err");
			leb.parent().siblings(".info").removeClass("info-succ");
			leb.parent().siblings(".info").addClass("info-err").html(info);
		}

		
		var submitData=function(){
			if($(".no-push").length<=0){
				if($("input[name='agre']").is(":checked")){
					if(!$(".info-err").length>0){
						registerPush();
					}
				}else{
					alert("请仔细阅读谷登农批服务条款，并同意")
				}
				registerPush()
			}else{
				registerPush()
			}
		}

		/**验证倒计时**/
		var timeLefts;
		var timeDwon=function(leb,time){
			leb.html("<i>"+time+"</i>秒后重新获取");
			if(!time--){
				stopInfo(leb);
			}else{
				timeLefts = setTimeout(function(){timeDwon($(".mcodebtn"),time)},1000);
			}
		}

		var stopInfo=function(leb){
			leb.html("获取短信验证码 ");
			$(".br-height").html($(".mcodebtn").attr("data-po"));
			//$form.find("input[name='mobile']").removeAttr("disabled");
		}

		/**获取验证码**/
		var pushCode=function(info){
			var leb=$(".mcodebtn").siblings(".info");
			leb.removeClass("info-err");
			leb.removeClass("info-succ");
			//$form.find("input[name='mobile']").attr("disabled","disabled");
			$.ajax({
				url:$(".mcodebtn").attr("data-url"),
				type:"post",
				data:{"mobile":$form.find("input[name='mobile']").val(),'type':'send_mg'},
				dataType:"json",
				success:function(data){
					switch(data){
						case "1":
							timeDwon($(".mcodebtn"),60);
							leb.html("");
							break;
						case "27":
							stopInfo($(".mcodebtn"));
							leb.addClass("info-err").html("发送验证码失败");
							break;
						// case "22":
						// 	stopInfo($(".mcodebtn"));
						// 	leb.addClass("info-err").html("手机号码为空");
						// 	break;
						// case "29":
						// 	timeDwon($(".mcodebtn"),$('.mcodebtn i').html());
						// 	leb.addClass("info-err").html("验证码的发送时间间隔为60秒，请稍后再试");
						// 	break;
					}
				},
				error:function(){
					leb.addClass("info-err").html("网络繁忙，请稍后重试");
				}
			});
		}

		

		//获取手机验证码
		$(".reg-list").delegate(".mcodebtn","click",function(){
			clearTimeout(timeLefts);
			//$(".br-height").css("display","inline-block").html($(".send-vcode").attr("data-po"));
			//check_empty($th,"请输入您的手机号码");
			//phone($("input[name='mobile']"));
			if(phone($("input[name='mobile']"))){
				pushCode($(".mobileVerifyCode").attr("data-po"));
			}
			
		});


		
		$(".reg-list").delegate(".btn-reg","click",function(event){
			event.preventDefault();
			submitData();
			return false;
		});
		//处理键盘的回车键登录
		$(document).keydown(function(event){
			if(event.keyCode==13){					
				submitData();
			}
		});

	})();

	
	
});
/**扩展，移除多个相似类名**/
(function($) {
    $.fn.removeClassWild = function(mask) {
        return this.removeClass(function(index, cls) {
            var re = mask.replace(/\*/g, '\\S+');
            return (cls.match(new RegExp('\\b' + re + '', 'g')) || []).join(' ');
        });
    };
})(jQuery);
var changeType = function(type,step){
	$("#regType").val(type)
	$(".reg-process").find("ul").eq(type).show().siblings().hide();
	if(type ==1||type ==2){
		$(".btn-reg").text("下一步");
	}else{
		$(".btn-reg").text("免费注册");
	}
	if(step==2){
		$(".btn-reg").text("完成注册");
	}
	$form=$("form:visible");
}
var changeProbar = function(step){
	$(".reg-process").find("ul:visible").removeClassWild("current-step*").addClass("current-step"+step);

}

/**获取服务项目**/
var changeItem = function(type){
	$.ajax({
	    type: "get",
	    url: "item.json",
	    data: {type:type},
	    dataType:"json",
	    success: function(data){
	    	console.log(data.length);
	    	var str = '';
	    	for(var i = 0;i<data.length;i++){
	    		str += '<span class="con-pro-cit-area-sp"><label><input class="pro-cit-area-lab" type="checkbox">'+data[i].cat+'</label></span>'
	    	}
	    	$(".con-pro-cit-area-bx").html(str);
	    }
	});
}

/**修正length**/
var getBLen = function(str) {
	if (str == null) return 0;
	if (typeof str != "string"){
	    str += "";
	}
	return str.replace(/[^\x00-\xff]/g,"01").length;
}