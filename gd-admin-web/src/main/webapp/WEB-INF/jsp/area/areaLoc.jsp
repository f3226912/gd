<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="../pub/constants.inc" %>
<%@ include file="../pub/head.inc" %>
<%@ include file="../pub/tags.inc" %>
<title>地区定位</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript" src="${CONTEXT}js/jquery.min.js"></script>
<script type="text/javascript" src="${CONTEXT}js/jquery.artDialog.js?skin=blue" charset='utf-8'></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=l1ruOrQIf8GerQRjW2RscDSA"></script>

</head>
<body>
	<div id="allmap" style="width:570px;height:500px;"></div>
	<input type="hidden" id="areaID" value="${area.areaID }">
	<input type="hidden" id="area" value="${area.area }">
	<input type="hidden" id="father" value="${area.father }">
	<input type="hidden" id="lng" value="${area.lng }">
	<input type="hidden" id="lat" value="${area.lat }">
</body>

<script type="text/javascript">
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
	var area = '${area.area}';
	var father = '${area.father}';
	var lng = '${area.lng}';
	var lat = '${area.lat}';
	var initlng = '${area.lng}';
	var initlat = '${area.lat}';
	var map;
	var marker;
	var point;
	var myIcon;
	$(document).ready(function() {
		// 创建Map实例
		map = new BMap.Map("allmap");    
		// 创建图标对象   
		myIcon = new BMap.Icon("${CONTEXT}images/markers.png", new BMap.Size(47, 48), {
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

		//左上角，添加比例尺
		map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));       
		//左上角，添加默认缩放平移控件
		map.addControl(new BMap.NavigationControl());
		//右上角，添加城市列表
		map.addControl(new BMap.CityListControl({anchor: BMAP_ANCHOR_TOP_RIGHT,offset: new BMap.Size(10, 20),}));
		//开启鼠标滚轮缩放
		map.enableScrollWheelZoom(true);
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
		if((lng == null || lng == "") && area != null && area != ""){
			var localSearch = new BMap.LocalSearch(map);
			//允许自动调节窗体大小
			localSearch.enableAutoViewport(); 
			localSearch.setSearchCompleteCallback(function (searchResult) {
				var num = searchResult.getNumPois();
				if(num == 1){
					map.centerAndZoom(area);
				} else if(num > 1){
					var poi = searchResult.getPoi(0);
					map.centerAndZoom(poi.point, 11);
				} else {
					map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
				}
			});
			localSearch.search(area);
		} else {
			//默认北京坐标
			map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
		}
		if(lng != null && lng != ""){
			point = new BMap.Point(lng, lat);
			// 初始化地图,设置中心点坐标和地图级别
			map.panTo(point);
			// 创建标注对象并添加到地图   
			marker = new BMap.Marker(point, {icon: myIcon});
			map.addOverlay(marker);
		}
	});
	 
</script>
</html>