<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>单字名列表</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
</head>
<body>



<table width="100%"  border="0" cellspacing="1" cellpadding="2">
    <c:forEach var="name" items="${nameList}" varStatus="status">
        <c:if test="${(status.index)%5==0}">
            <tr>
        </c:if>
        <td>
            <a href="/named/getNamePlus.do?name=${name}">${name}</a>
        </td>
        <c:if test="${(status.index+1)%5==0}">
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
