function init(){$(".canvas-box").each(function(){$(this).eraser({completeRatio:.25,size:60,beforeFunction:function(){var t=this.canvas.prev();t.addClass(t.attr("data"))},completeFunction:function(){this.completeFunction=null,this.canvas.fadeOut(1e3,function(){$(this).parents(".section").attr("clear",!0),$(this).parent().next().show(),$(this).remove(),$.fn.fullpage.setAllowScrolling(!0),$("#skip").show()})}})})}$("#mainWrap").fullpage({onLeave:function(t,n){7==n&&$("#skip").remove(),"false"===$("#section"+n).attr("clear")&&$.fn.fullpage.setAllowScrolling(!1)}}),addEventListener("load",init,!1),$(function(){$("#mainWrap").on("touchstart",".container",function(){$(this).children(".tip-box").remove()}),$("#skip").on("touchend",function(){$.fn.fullpage.moveTo("page7"),$(this).remove(),$.fn.fullpage.setAllowScrolling(!0),event.stopPropagation()}),share("http://jasmine.ittun.com/activity/fullpage.html","谷登，与你见证中国的崛起","还记得从小时候到现在，你身边有哪些事情发生了翻天覆地的变化吗？","http://jasmine.ittun.com/activity/images/P6_logo.png")});