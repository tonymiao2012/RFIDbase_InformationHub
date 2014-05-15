<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>GOOGLE map v3</title>
<style type="text/css">
  html { height: 100% }
  body { height: 100%; margin: 0px; padding: 0px }
  #map_canvas { height: 50% }
</style>

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="jquery-1.7.min.js"></script>
<script type="text/javascript">
 //******全局变量声明**********
//var apath=new Array();
//var res=new Array();
var map;	//全局变量地图
 //**************************
 function initialize() {		//初始化地图,加入判断坐标
	var latlng = new google.maps.LatLng(39.9073477411367,116.39127169311519);
    var myOptions = {
      zoom: 12,
      center: latlng,
     //控制是否能拖拽 draggable: false,
      overviewMapControl: true,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);		//声明一个地图
  }
  
function set(){
  $(document).ready(function(){
  	$.post('mapDivLeft.jsp',{mapcardid:$("#mapcardid")[0].value,mapbegintime:$("#mapbegintime")[0].value,mapendtime:$("#mapendtime")[0].value},function(data){
		$("#map_table").append(data);
	});
  });
}

function rem(){
	$("#map_table").html(" ");
}

</script>
</head>
<body onload="initialize()">
<form name="form1" action="" method="post" onsubmit="set()">
<div align="center">
  <p><font face="黑体">欢迎使用基站地图查询</font></p>
  <table width="87%" height="47" border="9">
    <tr> 
      <td align="left" valign="top">请输入要查询的卡号：
        <% 
        String mapcardid1=(String)session.getAttribute("mapcardid");
        if(null!=mapcardid1){%>
        <input id="mapcardid" name="mapcardid" type="text" value=<%=mapcardid1%> size="15">
        <%}else{ %>
        <input id="mapcardid" name="mapcardid" type="text" value="268437513" size="15">
        <%} %>
        开始时间：
        <%
        String mapbegintime1=(String)session.getAttribute("mapbegintime");
        if(null!=mapbegintime1){
        %>
        <input id="mapbegintime" name="mapbegintime" type="text" id="mapbegintime" value="<%=mapbegintime1%>" size="20">
        <%}else{ %>
        <input id="mapbegintime" name="mapbegintime" type="text" id="mapbegintime" value="2012-3-12 00:00:01" size="20">
        <%} %>
        结束时间：
        <%
        String mapendtime1=(String)session.getAttribute("mapendtime");
        if(null!=mapendtime1){
        %>
        <input id="mapendtime" name="mapendtime" type="text" id="mapendtime" value="<%=mapendtime1%>" size="20">
        <%}else{%>
        <input id="mapendtime" name="mapendtime" type="text" id="mapendtime" value="2012-3-12 23:59:59" size="20">
       	 <%} %>
      </td>
      </tr>
  </table>
</div>
<div align="center">
  <input type="button" name="Submit" value="提交" onclick="set()">
  &nbsp; &nbsp;&nbsp;&nbsp;
  <input type="button" name="Submit" value="回到主页" onclick="location='mainPage.jsp'">
  <input type="button" name="remove" value="清除记录" onclick="rem()">
  <input type="reset" name="Submit2" value="重置">
</div>
</form>

  <div id="map_canvas" style="float:right;width:85%; height:85%;"></div>
  <div id="map_data" style="overflow:auto;word-break:break-all;width:15%; height:85%;float:left;">
  <table id="map_table">
  </table>
  </div>
</body>
</html>
