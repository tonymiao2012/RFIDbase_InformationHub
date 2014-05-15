<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>GOOGLE map v3</title>
<%@include file="mapLogin1.jsp"%>
<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0px; padding: 0px }
  #map_canvas { height: 50% }
</style>

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="jquery-1.7.min.js"></script>
<script type="text/javascript">
//************************************************
 function initialize() {		//初始化地图
    var latlng = new google.maps.LatLng(39.9073477411367, 116.39127169311519);
    var myOptions = {
      zoom: 12,
      center: latlng,
     //控制是否能拖拽 draggable: false,
      overviewMapControl: true,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);		//声明一个地图
    //addListener
}
function drawPath(){
	$.post('mapDivLeft.jsp',function(data){
		$("#map_table").append(data);
	});
	$.post('mapDivRight.jsp',function(data){
		$("#map_canvas").html(data);
	});
}
</script>
</head>
<body onload="initialize()">
  <div id="map_canvas" style="float:right;width:85%; height:85%;"></div>
  <input type=button onclick="drawPath()" value="绘制折线">
  <div id="map_data" style="overflow:auto;word-break:break-all;width:15%; height:85%;float:left;">
  <table id="map_table">
  </table>
  </div>
</body>
</html>