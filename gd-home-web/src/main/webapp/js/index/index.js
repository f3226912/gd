$(function(){
	/**幻灯片**/
	$(".ty_picScroll").gduiSlide({
		customeClass:"slide-wrap-cus",
		speed : 5000,
		auto : true
	});	
	$(".h-search-catlist li").click(function() {
		var _self = $(this),
			curVal = _self.text(),
			perVal = $(".h-search-tit").text();
		$(".h-search-tit").text(curVal);
		$(this).children().text(perVal);
	});
	/* 展开主导航的侧边导航 */
	if(!$('#nav_home').length){
		$('.nav_title').data('timer', null).bind({
			mouseover: function() {
				var $title = $(this);
				clearTimeout($title.data("timer"));
				$title.siblings('.header_sidenav').slideDown(200, function() {
					$(this).css('overflow', 'visible');
				}).bind({
					mouseover: function() {
						clearTimeout($title.data("timer"));
					},
					mouseout: function() {
						var $this = $(this);
						clearTimeout($title.data("timer"));
						$title.data("timer", setTimeout(function() {
							$this.hide();
						}, 200));
					}
				});
			},
			mouseout: function() {
				var $title = $(this);
				clearTimeout($title.data("timer"));
				$title.data("timer", setTimeout(function() {
					$title.siblings('.header_sidenav').hide();
				}, 200));
			}
		});
	}
	
	/* 侧导航菜单 */
	(function(){
	var startX = null, startY = null, endX = null, endY = null, able = true, isTimer = false, hoverTimer = null,
		fn = function (obj1, obj2) {
			obj1.addClass('header_sidenav_item_curr').siblings().removeClass('header_sidenav_item_curr');
			var minus = ( obj1.offset().top + obj2.height() ) - ( $(window).height() + $(window).scrollTop() ) + 20;
			if (minus > 0) {
				obj2.animate({'margin-top':- minus},200);
			} else {
				obj2.animate({'margin-top':'0'},200);
			}
			if($.browser.msie){
				setTimeout(function() {
					$('.header_sidenav_close', obj2).css('zoom','1');
				}, 0);
			}
		};
		
		$('.header_sidenav_item').bind({
			'mouseenter': function(e) {
				if(isTimer){ return }
				isTimer = true;
				startX = e.pageX;
				startY = e.pageY;
				var $this = $(this), $subCont = $this.find('.header_sidenav_cnt'), $img = $this.find('img[data-src]');
				if($img.length>0){
					if($img.attr('isload')!='true'){
						$img.attr('src', $img.attr('data-src')).attr('isload','true');
					}
				}
				if ($subCont.length && able) {
					fn($this, $subCont);
					isTimer = false;
				} else {
					hoverTimer = setTimeout(function(){
						fn($this, $subCont);
						isTimer = false;
					},500);
				}
			},
			'mouseleave': function(e) {
				clearTimeout(hoverTimer);
				isTimer = false;
				endX = e.pageX;
				endY = e.pageY;
				if (Math.abs(endX - startX)/Math.abs(endY - startY) < 0.3) {
					able = true;
				} else {
					able = false;
				}
			}
		});
		$('.header_sidenav_close').bind('click', function() {
			$(this).parents('.header_sidenav_item').removeClass('header_sidenav_item_curr');
			if(!$('#header_b').length){
				$('.header_sidenav').hide();
			}
		});
		$('.header_sidenav').bind({
			'mouseleave': function() {
				$(this).find('.header_sidenav_item').removeClass('header_sidenav_item_curr');
			}
		});
	})();
	function unique(arr) {
	    var result = [], hash = {};
	    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
	        if (!hash[elem]) {
	            result.push(elem);
	            hash[elem] = true;
	        }
	    }
	    return result;
	}
	(function(){
		var topArr = [],
			topArrun = [],
			winHeight = $(window).height(),
			$fixeParent = $(".i-page-fixed-list");
		$(".i-com-cat-box").each(function(){
		    topArr.push($(this).offset().top-winHeight/4 )
		});		
		topArrun = topArr;
		topArr = unique(topArr);
		//console.log(topArr)
		$(window).bind("scroll", function() {
			// $(".i-page-fixed-list li").each()
			var winTop = $(window).scrollTop()
			if(winTop >= winHeight/2){
				$(".page-fixed").show()
			}else{
				$(".page-fixed").hide()
			}
			
			//console.log(winTop);
			$fixeParent.find("li").removeClass("page-fixed-cur");
			for(var i = 0 ,len = topArr.length;i<len;i++){
				if(topArr[i+1]){
					if(winTop > topArr[i] && winTop <= topArr[i+1]){
						$fixeParent.find(".i-page-fixed-item").eq(i).addClass("page-fixed-cur").next().addClass("page-fixed-cur");
					}	
				}else{
					if(winTop>topArr[4] && winTop<= topArr[4]+$(".i-com-cat-box").height()/2){
						$fixeParent.find(".i-page-fixed-item").eq(4).addClass("page-fixed-cur").next().addClass("page-fixed-cur");
					}
					else{
						$fixeParent.find(".i-page-fixed-item").eq(4).removeClass("page-fixed-cur").next().removeClass("page-fixed-cur");
					}
				}
				
			}
		});
		$(window).trigger("scroll");
		$fixeParent.find("li").click(function(){
			var index = $(this).index();			
			$('body,html').animate({scrollTop:topArrun[index]+10+"px"}, 100 );
			$(window).trigger("scroll");
		})
	})();

	$(".js-showMap").click(function(){
		initStreetView("pano_container",39.916527,116.397128);
		layer.open({
			type: 1,
		    title: "开通商铺", //显示标题
		    content: $('.pano_container'), //捕获的元素
		    cancel: function(index){
		    	layer.close(index);

		    }
		})
	});
	// function initStreetView() {
 //    // 创建街景
 //        pano = new qq.maps.Panorama(document.getElementById('pano_container'), {
 //            "pano": "10041049140524091751500",
 //            "pov":{
 //                heading:153.8,
 //                pitch:4
 //            }
 //        });
 //    }

});
var initStreetView = function(targetId, lat, lng) {
    var map = new soso.maps.Panorama(document.getElementById(targetId),{
        center: new soso.maps.LatLng(lat,lng),
        zoom: 13
    });
    pano_layer = new soso.maps.PanoramaLayer();
    pano_layer.setMap(map);
    pano_service = new soso.maps.PanoramaService();
    // bounds检测
    soso.maps.event.addListener(map, 'idle', function (){
        pano_service.checkBounds(map.getBounds(), function (hasPano){
            if (hasPano) {
                document.getElementById('hasPano').innerHTML = "该范围包含全景";
            } else {
                document.getElementById('hasPano').innerHTML = "该范围不包含全景";
            }
        });
    });
};