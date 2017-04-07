<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/head.inc" %>
<%@ include file="../pub/tags.inc" %>
<title>市场定位</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript" src="${CONTEXT}js/jquery.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=l1ruOrQIf8GerQRjW2RscDSA"></script>

</head>
<body>
	<div id="allmap" style="width:570px;height:435px;"></div>
	<input type="hidden" id="marketID" value="${market.id }">
	<input type="hidden" id="lng" value="${market.lon }">
	<input type="hidden" id="lat" value="${market.lat }">
</body>

<script type="text/javascript">
	var marker;
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	
	var lng = '${market.lon}';
	var lat = '${market.lat}';
	var initlng = '${market.lon}';
	var initlat = '${market.lat}';
	
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);

	function backPoint() {
		if(initlng != null && initlng != "") {
			map.removeOverlay(marker);
			$("#lng").val(initlng);
			$("#lat").val(initlat);
			point = new BMap.Point(initlng, initlat); 
			map.panTo(point);
			// 创建标注对象并添加到地图   
			marker = new BMap.Marker(point, {icon: myIcon});
			map.addOverlay(marker);
		}
	}
	
	var point;
	
	// 创建图标对象   
	var myIcon = new BMap.Icon("${CONTEXT}images/markers.png", new BMap.Size(47, 48), {
		// 指定定位位置。   
		// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
		// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
	   	// 图标中央下端的尖角位置。
	   	anchor: new BMap.Size(23, 36),
	   	// 设置图片偏移。   
	   	// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
	   	// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
	   	imageOffset: new BMap.Size(0, 0) // 设置图片偏移 
	});

	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	
	/*
		添加控制窗口，拖动放大缩小
	*/
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);
 
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

	//默认鼠标指针
	map.setDefaultCursor("crosshair");

	//单击获取点击的经纬度
	map.addEventListener("click",function(e){
		map.removeOverlay(marker);
		
		$("#lng").val(e.point.lng);
		$("#lat").val(e.point.lat);
		
		point = new BMap.Point(e.point.lng, e.point.lat); 
		
		map.panTo(point);
		// 创建标注对象并添加到地图   
		marker = new BMap.Marker(point, {icon: myIcon});
		
		map.addOverlay(marker);
	});
	
	$(document).ready(function() {
		//如果对应地区为保存坐标，默认北京坐标
		if(lng=="") {
			map.panTo(new BMap.Point(116.404, 39.915));  // 初始化地图,设置中心点坐标和地图级别
		} else {
			point = new BMap.Point('${market.lon}', '${market.lat}');
			
			map.panTo(point);  // 初始化地图,设置中心点坐标和地图级别
			
			// 创建标注对象并添加到地图   
			marker = new BMap.Marker(point, {icon: myIcon});
			map.addOverlay(marker);
		}
		

		var size = new BMap.Size(10, 20);
		map.addControl(new BMap.CityListControl({
		    anchor: BMAP_ANCHOR_TOP_RIGHT,
		    offset: size,
		    // 切换城市之间事件
		    // onChangeBefore: function(){
		    //    alert('before');
		    // },
		    // 切换城市之后事件
		    // onChangeAfter:function(){
		    //   alert('after');
		    // }
		}));
	});
	 
</script>
</html>