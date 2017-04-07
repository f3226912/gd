$(function(){
	
	(function(){
		var $form=$("form:visible");
		$(".f-tab-list li").click(function(){
			var $this = $(this),
				index = $this.index();
			$this.addClass("cur").siblings("li").removeClass("cur");
			$(".tab-cntent").eq(index).show().siblings(".tab-cntent").hide();
			$form=$("form:visible");
		});
		//$form.find("input").blur(function(){
		$("input").blur(function(){
			var $th=$(this),
				$val=$.trim($th.val()),
				$leb=$th.parent("span.bor").siblings("span.info"),
				defaultvalue = $th.attr("placeholder");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ f-c-f33550");
			switch($th.attr("name")){
				
				case "password"://密码
					var length=$val.length,
						sibLeb=$leb.addClass("info-err");
					if(length==0){
						sibLeb.html("密码不能为空");
					}else if(length<6||length>20){
						sibLeb.html($th.attr("data-err"));
					}else if(length>=6&&/^[0-9]+$/g.test($val)){
						sibLeb.html("密码不能为纯数字");
					}else if(length>=6&&/^[A-Z]+$/g.test($val)){
						sibLeb.html("密码不能为纯大写字母");
					}else if(length>=6&&/^[a-z]+$/g.test($val)){
						sibLeb.html("密码不能为纯小写字母");
					}else if(length>=6&&/^\W+$/g.test($val)){
						sibLeb.html("密码不能为纯符号");
					}else if(length>=6&&/^\s+$/g.test($val)){
						sibLeb.html("密码不能包含空格");
					}else{
						$leb.removeClass("info-err").addClass("info-succ").html("&nbsp;");
					}
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
				case "randomCode"://验证码
					if($val==""||$val.length<4||$val==defaultvalue){
						$leb.addClass("info-err").html($th.attr("data-err"));
					}else{
						$leb.removeClass("info-err").html("");
					}
				break;
				case "email"://邮箱
					checkEmail($th);
					check_empty($th,"请输入您的邮箱地址");
				break;
				case "mobileVerifyCode"://手机验证码
					check_empty($th,"请输入验证码");
				break;
				case "mailVerifyCode"://邮箱验证码
					checkEmailcode($th)
					check_empty($th,"请输入验证码");
				break;
			}

		}).focus(function(){
			var $th=$(this),
				$leb=$th.parent("span.bor").siblings("span.info");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ f-c-f33550");
			$leb.removeClass("onload");
			$leb.html($th.attr("data-po"));
		});	

		//手机验证
		var phone=function(tmp){
			var $th=tmp,$val=$th.val(),
				$leb=$th.parent("span.bor").siblings("span.info");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ f-c-f33550");
			$leb.removeClass("onload");
			$(".mcodebtn").removeClass("clk-bg");
			if($val.length==11&&/(^[1][34587][0-9]{9}$)/g.test($val)){
				$th.parent("span.bor").siblings("span.info").addClass("onload").html("正在进行合法性校验，请稍候...");
				$.ajax({
					url:$th.attr("data-url")+$val,
					type:"get",
					dataType:"json",
					success:function(data){
						switch(data){
							case "1"://手机号码正确
								$leb.addClass("info-succ").html("&nbsp;");
								$(".mcodebtn").addClass("clk-bg");
								break;
							// case "22":
							// 	$leb.addClass("info-err").html("手机号码为空");
							// 	break;
							// case "6":
							// 	$leb.addClass("info-err").html("注册手机号重复");
							// 	break;
							// case "8":
							// 	$leb.addClass("info-err").html("手机号码格式错误");
							// 	break;
							// case "29":
							// 	$leb.addClass("info-err").html("验证码的发送时间间隔为60秒，请稍后再试");
							// 	break;
						}
					}
				});
			}else if($val.length<11){
				$leb.addClass("info-err").html($th.attr("data-err"));
			}
		}

		//邮箱验证
		var checkEmail=function(tmp){
			var $th=tmp,$val=$.trim($th.val()),
				$leb=$th.parent("span.bor").siblings("span.info");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ");
			if(/(^[a-zA-Z\d-_]+(\.[a-zA-Z\d]+)*@([\da-zA-Z](-[\da-zA-Z])?)+(\.{1,2}([a-zA-Z]+){2,})+$)/g.test($val)){
				$th.parent("span.bor").siblings("span.info").addClass("onload").html("正在进行合法性校验，请稍候...");
				$.ajax({
					url:$th.attr("data-url")+$val,
					type:"get",
					dataType:"json",
					success:function(data){
						switch(data){
							case "1"://邮箱地址正确
								$leb.addClass("info-succ").html("&nbsp;");
								$(".mcodebtn").addClass("clk-bg");
								break;
						}
					}
				});
			}else{
				$leb.addClass("info-err").html($th.attr("data-err"));
			}
			if($val=''){
				$leb.addClass("info-err").html($th.attr("data-po"));
			}
		}

		//邮箱验证码验证
		var checkEmailcode=function(tmp){
			var $th=tmp,$val=$.trim($th.val()),
				$leb=$th.parent("span.bor").siblings("span.info");
			$leb.removeClass("info-err");
			$leb.removeClass("info-succ");
			if(/(^[a-zA-Z\d-_]+(\.[a-zA-Z\d]+)*@([\da-zA-Z](-[\da-zA-Z])?)+(\.{1,2}([a-zA-Z]+){2,})+$)/g.test($val)){
				$th.parent("span.bor").siblings("span.info").addClass("onload").html("正在进行合法性校验，请稍候...");
				$.ajax({
					url:$th.attr("data-url")+$val,
					type:"get",
					dataType:"json",
					success:function(data){
						switch(data){
							case "1"://邮箱地址正确
								$leb.addClass("info-succ").html("&nbsp;");
								$(".mcodebtn").addClass("clk-bg");
								break;
						}
					}
				});
			}else{
				$leb.addClass("info-err").html($th.attr("data-err"));
			}
			if($val=''){
				$leb.addClass("info-err").html($th.attr("data-po"));
			}
		}

		var check_empty=function(tmp,msg){
			var $leb=tmp.parent("span.bor").siblings("span.info");
			if(tmp.val()==""){
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
				if($form.find(".input-text").eq(i).val()==""||$form.find(".input-text").eq(i).val()==$form.find(".input-text").eq(i).attr("placeholder")){
					$form.find(".input-text").eq(i).focus();
					//$form.find(".input-text").eq(i).parent("span.bor").siblings("span.info").html($(this).attr("data-po"))
					// $leb.html($th.attr("data-po"));
					// $leb=$th.parent("span.bor").siblings("span.info");
					return false;
				}
			}
			if(register&&$form.find(".info-err").length<=0){
				register=false;
				$form.find('.btn-next').html('提交中...');

				var pwdval=$("input[name='password']").val(),
					pwdval2=$("input[name='confirmPassword']").val();

				$form.find("input[name='password']").val(pwdval);
				$form.find("input[name='confirmPassword']").val(pwdval2);
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
								$form.find('.btn-next').addClass("no-push").html('提交中...');
							break;
							// case "13":
							// 	fnbreak($form.find("input[name='userName']"),"用户名为空");
							// 	break;
							// case "14":
							// 	fnbreak($form.find("input[name='password']"),"密码为空");
							// 	break;
							// case "15":
							// 	fnbreak($form.find("input[name='confirmPassword']"),"密码不匹配");
							// 	break;
							// case "3":
							// 	fnbreak($form.find("input[name='userName']"),"该用户名已被使用，请更换用户名");
							// 	break;
							// case "9":
							// 	fnbreak($form.find("input[name='userName']"),"用户名格式错误");
							// 	break;
							// case "10":
							// 	fnbreak($form.find("input[name='password']"),"密码格式错误");
							// 	break;
							// case "5":
							// 	fnbreak($form.find("input[name='password']"),"密码检验失败");
							// 	break;
						}

					}
				});
			}
		}

		var fnbreak=function(leb,info){
			$form.find('.btn-next').removeClass("no-push").html('下一步');
			leb.parent().siblings(".po").removeClass("info-err");
			leb.parent().siblings(".po").removeClass("info-succ");
			leb.parent().siblings(".po").removeClass("onload");
			leb.parent().siblings(".po").addClass("info-err").html(info);
		}

		var submitData=function(){
			if($(".no-push").length<=0){				
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
			//$(".br-height").html($(".mcodebtn").attr("data-po"));
			$form.find("input[name='mobile']").removeAttr("disabled");
		}

		/**获取验证码并验证**/
		var pushCode=function(info){
			var leb=$(".mcodebtn").siblings(".info");
			leb.removeClass("info-err");
			leb.removeClass("info-succ");
			$form.find("input[name='mobile']").attr("disabled","disabled");
			$.ajax({
				url:$(".mcodebtn").attr("data-url"),
				type:"post",
				data:{"mobile":$form.find("input[name='mobile']").val()},
				dataType:"json",
				success:function(data){
					switch(data){
						case "1":
							timeDwon($(".mcodebtn"),60);
							leb.html("");
							break;
						// case "26":
						// 	stopInfo($(".mcodebtn"));
						// 	leb.addClass("info-err").html("非正常跳转用户，禁止发送短信");
						// 	break;
						// case "27":
						// 	stopInfo($(".mcodebtn"));
						// 	leb.addClass("info-err").html("发送验证码失败");
						// 	break;
						// case "22":
						// 	stopInfo($(".mcodebtn"));
						// 	leb.addClass("info-err").html("手机号码为空");
						// 	break;
						// case "29":
						// 	timeDwon($(".mcodebtn"),$('.mcodebtn i').html());
						// 	leb.addClass("info-err").html("验证码的发送时间间隔为60秒，请稍后再试");
						// 	break;
					}
				}
			});
		}


		var switchCode=function(tmp){
			var timenow =eval(+(new Date));
			var imgUrl=$(".captcha").attr("data-img");
			tmp.parents("li").find("input").val("");
			tmp.parents("li").find("img").attr("src", imgUrl+"?" + timenow);
		}

		//刷新验证码
		$(".get-list").delegate(".re-captcha","click",function(event){
			event.preventDefault();
			var $th=$(this);
			switchCode($th);
			return false;
		});
		$("ul.get-list").delegate(".captcha","click",function(){
			var $th=$(this);
			var timenow =eval(+(new Date));
			var imgUrl=$("ul.get-list .captcha").attr("data-img");
			$th.attr("src", imgUrl+"?" + timenow);
		});

		//获取手机验证码
		$(".get-list").delegate(".mcodebtn","click",function(){
			clearTimeout(timeLefts);
			//$(".br-height").css("display","inline-block").html($(".send-vcode").attr("data-po"));
			pushCode($(".mobileVerifyCode").attr("data-po"));
		});


		$(".get-list").delegate(".btn-next","click",function(event){
			//var $form=$("form:visible")
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