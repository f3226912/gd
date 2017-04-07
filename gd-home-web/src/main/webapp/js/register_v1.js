$(function(){

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
		var $form=$("form[name='register']"),ref=false;
		$form.find("input").blur(function(){
			var $th=$(this),
				$val=$.trim($th.val()),
				$leb=$th.parent("span.bor").siblings("span.info"),
				defaultvalue = $th.attr("placeholder");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ");
			switch($th.attr("name")){				
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
			if(register&&$(".info-err").length<=0){
				register=false;
				$('.btn-reg').html('提交中...');
				//设置密码加密
				var pwdval=$("input[name='password']").val(),
					pwdval2=$("input[name='confirmPassword']").val();

				var param=$form.serialize();
				$.ajax({
					url:$form.attr("data-url"),
					data:param,
					type:"POST",
					dataType: "json",
					success: function(data){
						register=true;
						switch(data){
							case "1":
								//注册成功
								$form.submit();
								$('.btn-reg').addClass("no-push").html('成功');
								location.href = "/";

							break;
							case "error":
								fnbreak($form.find("input[name='userName']"),"注册失败，请检查注册项是否错误");
								break;
						}

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

	
	
})