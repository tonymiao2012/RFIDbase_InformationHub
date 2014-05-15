<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Test</title>
<script type="text/javascript" src="jquery-1.7.min.js"></script>
<script type="text/javascript">
function  onInvokeAction(){
$.post('Ajax.jsp', {
    text: 'my string',
    number: 23
}, function(app) {
	var app1=app;
    $('#presidents').append(app1);
});
//document.write(app1);
}
</script>
</head>
<body>
<input type=button onclick="onInvokeAction()"  value="显示数据"  >
<div id="presidents"></div>
</body>
</html>
