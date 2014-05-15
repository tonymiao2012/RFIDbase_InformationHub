<%@ page language="java" contentType="text/html; charset=GB2312"
    pageEncoding="GB2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0px; padding: 0px }
  #map_canvas { height: 50% }
</style>
<% //传入数据
	int[] result=(int[])session.getAttribute("result");
	double[] getX=(double[])session.getAttribute("getX");
	double[] getY=(double[])session.getAttribute("getY");
	int n=getX.length;
 %>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
 //******全局变量声明**********
var apath=new Array();
var res=new Array();
var map;	//全局变量地图
var m=<%= n%>;		//坐标总数
//如此将数据读入后再执行速度快了很多！
<%for(int i=0;i<getX.length;i++){%>		
    apath[<%=i%>]=new google.maps.LatLng(<%= getX[i]%>,<%= getY[i]%>);
    res[<%=i%>]=<%=result[i]%>;
<%}%>
//**************************
 function initialize() {		//初始化地图
    var latlng = new google.maps.LatLng(<%= getX[0]%>, <%= getY[0]%>);
    var myOptions = {
      zoom: 12,
      center: latlng,
     //控制是否能拖拽 draggable: false,
      overviewMapControl: true,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);		//声明一个地图
    //addListener
    google.maps.event.addListener(map,'click',showMarker);
}
function showMarker(){		//展示标签函数
	for(var p=0;p<m;p++){
		if(res[p]!=0){
			//alert(res[p]);
			var marker=new google.maps.Marker({
			animation: google.maps.Animation.DROP,
			map: map,
			position: apath[p],
			title:"在此处停留了"+res[p]+"秒钟"
			});
		}
	}
}
var i=0;
var intervalid=window.setInterval(drawPath,500);
function drawPath(){
	var temp=new Array(2);
	temp[0]=apath[i];
	i++;
	if(i>=1&&i%2==1){
		temp[1]=apath[i];
		var polyOptions={
	   	path: temp,
	    strokeColor: "#FF0000",
		strokeOpacity: 0.6,
		strokeWeight: 5
		}
		var pa=new google.maps.Polyline(polyOptions);
		pa.setMap(map);
			//map.setCenter(temp[1]);
	}else if(i>=1&&i%2==0){
		temp[1]=apath[i];
		var polyOptions={
	   	path: temp,
	    strokeColor: "#FFF000",
		strokeOpacity: 0.6,
		strokeWeight: 5
		}
		var pa=new google.maps.Polyline(polyOptions);
		pa.setMap(map);
			//map.setCenter(temp[1]);
	}
	if(i==m-1){
		clearInterval(intervalid);
		alert("轨迹回放完毕");
	}
}
</script> 
</head>
<body onload="initialize()">
<div id="map_canvas"></div>
</body>
</html>