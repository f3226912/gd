function change(){
	$(".eWrap .choice .ebody .ones").mouseenter(function(){
		$(".eWrap .choice .ebody .ones .right").css("width","0px");
		$(".eWrap .choice .ebody .ones .left").removeClass("in");
		$(this).find(".right").css("width","438px");
		$(this).find(".left").addClass("in")
	})
}change();

function scoll(){
	var arr=[],
		winHeight=300,
		i;
	for(i=1;i<4;i++){
		arr.push($("#cul"+i).offset().top-winHeight);
	}
	var len = arr.length;
	$(window).bind("scroll",function(){
		var scrollTop=$(window).scrollTop();
		if(scrollTop>winHeight){
			$(".fu").show();
//			for(var i=1;i<arr.length;i++){
//				if(arr[i]){
//					if(arr[i-1]<scrollTop&&arr[i]>scrollTop){
//						$(".eWrap .fu a").removeClass("active");
//						$("#cul"+i+i).addClass("active");
//					}
//				}
//			}			
			for (i=1; i<=len; i++){
				if(arr[i]){
					if(arr[i-1] <= scrollTop && arr[i] > scrollTop){ //判断滚动条位置
						$(".eWrap .fu a").removeClass("active"); //清除active类
						$("#cul"+i+i).addClass("active");	//给当前导航加active类
					}
				}else{
					if(arr[len-1] <= scrollTop){
						$(".eWrap .fu a").removeClass("active"); //清除active类
						$("#cul"+i+i).addClass("active");
					}
				}
			}
		}else{
			$(".fu").hide();
		}
	})
}scoll();
