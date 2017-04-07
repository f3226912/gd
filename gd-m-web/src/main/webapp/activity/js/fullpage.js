$('#mainWrap').fullpage({
	onLeave: function(index, nextIndex, direction){
		if(nextIndex == 7){
			$('#skip').remove();
		}
		if($("#section" + nextIndex).attr('clear') === 'false'){
			$.fn.fullpage.setAllowScrolling(false);
		}
	}
});
addEventListener( "load", init, false );
function init( event ) {
	$(".canvas-box").each(function(i){
		$(this).eraser({
			completeRatio: 0.25,
			size: 60,
			beforeFunction: function(){
				var prev = this.canvas.prev();
				prev.addClass(prev.attr('data'));
				// if(i === 0){
				// }
			},
			completeFunction: function(){
				this.completeFunction = null;
				// $("#redux").eraser('clear');
				// $.fn.fullpage.setAllowScrolling(true, "up");
				// $('#p' + (i + 2) + 'Cvs').prev().addClass('p' + (i + 2) + '-2');
				this.canvas.fadeOut(1000, function(){
					$(this).parents('.section').attr('clear', true);
					$(this).parent().next().show();
					$(this).remove();
					$.fn.fullpage.setAllowScrolling(true);
					$('#skip').show();
				});
			}
		});
	});
}
$(function() {
	// 移除提示动画
	$("#mainWrap").on('touchstart', '.container', function(){
		$(this).children(".tip-box").remove();
	});
	$('#skip').on('touchend', function(){
		$.fn.fullpage.moveTo('page7');
		$(this).remove();
		$.fn.fullpage.setAllowScrolling(true);
		event.stopPropagation();
	});

	share('/activity/fullpage.html','谷登，与你见证中国的崛起',
		'还记得从小时候到现在，你身边有哪些事情发生了翻天覆地的变化吗？',
		'/activity/images/P6_logo.png');
});
