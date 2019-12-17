<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Store</title>
</head>
<body>
	<h3>
		Hello,
		<c:out value="${user.firstName} ${user.lastName}" />
	</h3>

	<br>
	<form action="user" method="post">
		<c:forEach items="${itemCollection}" var="item">
			<form action="#">
				<input type="text" name="action" value="add-item-in-cart" hidden>
				<input hidden="true" name="userId" value="${user.id}"> 
				<input hidden="true" name="itemId" value="${item.id}">
				<c:out value="${item.id} ${item.name} ${item.price}" />
				<input type="submit" value="buy">
			</form>
		</c:forEach>
	</form>

	<h3>
		<br>
		Cart: <c:out value="${cart.id}" />
	</h3>

	<h3>
		<br>
		<c:out value="${errorMsg}" />
	</h3>

</body>
</html>
