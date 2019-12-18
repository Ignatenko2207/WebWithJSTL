<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Store</title>
</head>
<body>


<h3>Hello, <c:out value="${user.firstName} ${user.lastName}" /></h3>

<br>
<c:forEach items="${itemCollection}" var="item">
	<form action="#">
	<input hidden="true" name="userId" value="${user.id}">
	<input hidden="true" name="itemId" value="${item.id}">
	<c:out value="${item.name} ${item.price}" />
	<input type="submit" value="buy">
	</form>	
	<br>
</c:forEach>
</body>
</html>
