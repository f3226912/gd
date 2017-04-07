$(".text-list li:eq(0)").clone(true).appendTo($(".text-list"));//克隆第一个放到最后(实现无缝滚动)
	var liHeight = $(".cont-text-small li").height();//一个li的高度
	//获取li的总高度再减去一个li的高度(再减一个Li是因为克隆了多出了一个Li的高度)
	var totalHeight = ($(".text-list li").length *  $(".text-list li").eq(0).height()) -liHeight;
	$(".text-list").height(totalHeight);//给ul赋值高度
	var index = 0;
	var autoTimer = 0;//全局变量目的实现左右点击同步
	var clickEndFlag = true; //设置每张走完才能再点击

	function tab(){
		$(".text-list").stop().animate({
			top: -index * liHeight
		},400,function(){
			clickEndFlag = true;//图片走完才会true
			if(index == $(".text-list li").length -1) {
				$(".text-list").css({top:0});
				index = 0;
			}
		})
	}

	function next() {
		index++;
		if(index > $(".text-list li").length - 1) {//判断index为最后一个Li时index为0
			index = 0;
		}
		tab();
	}
	function prev() {
		index--;
		if(index < 0) {
			index = $(".text-list li").size() - 2;//因为index的0 == 第一个Li，减二是因为一开始就克隆了一个LI在尾部也就是多出了一个Li，减二也就是_index = Li的长度减二
			$(".text-list").css("top",- ($(".text-list li").size() -1) * liHeight);//当_index为-1时执行这条，也就是走到li的最后一个
		}
		tab();
	}
	
	//自动轮播
	autoTimer = setInterval(next,3000);
var scrHeight = window.screen.height;
var scrollTop;
$(window).scroll( function() { 
	
scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
	if(scrollTop > scrHeight){
		$(".go-top").show();
	}else{
		$(".go-top").hide();
	}
});
$(".go-top").bind("click",function(){ 	
	$("html,body").animate({scrollTop: 0},300,function(){
		$(".go-top").hide();
	});
	
});	
