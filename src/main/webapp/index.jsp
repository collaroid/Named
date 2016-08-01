<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>起名</title>
    <script type="text/javascript" src="./js/jquery-1.7.2.min.js"></script>
</head>
<body>
<form id="nameForm" action="/named/getNameIndex.do" method="post">
<input style="width: 150px" type="text" id="name" name="name" value="苏" placeholder="请输入姓氏" readonly/>
    <input id="borg1" name="borg" type="radio" value="b" checked="checked"/>男
    <input id="borg2" name="borg" type="radio" value="g" />女
</form>
<input type="button" style="width: 50px" id="doEnsure" value="查找">
</body>
<script type="text/javascript">
    $(function() {
        $("#doEnsure").click(function(){
            if($('#name').val() == '') {
                alert("请填写姓氏!");
                return;
            }
            $("#nameForm").submit();
        });
    });
</script>
</html>
