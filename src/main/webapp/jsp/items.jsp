<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Store</title>
</head>
<body>

<h3>Hello, <c:out value="${user.firstName} ${user.lastName}" /></h3>

<br>
<c:forEach items="itemsCollection" var="item"> </c:forEach>
 
 <c:out value="${item.name} ${item.price}"></c:out>
</body>
</html>
