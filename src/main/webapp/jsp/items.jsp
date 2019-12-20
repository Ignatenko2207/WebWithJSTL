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

	<form action="cart" method="post">
		<input type="text" name="action" value="all-carts" hidden> <input
			hidden="true" name="userId" value="${user.id}"> <input
			type="submit" value="All carts">
	</form>
	<br>
	<br>

	<br>
	<c:forEach items="${itemCollection}" var="item">
		<form action="item" method="post">
			<input type="text" name="action" value="add-item-in-cart" hidden>
			<input hidden="true" name="userId" value="${user.id}"> <input
				hidden="true" name="itemId" value="${item.id}">
			<c:out value="${item.id} ${item.name} ${item.price}" />
			<input type="submit" value="buy">
		</form>
	</c:forEach>

	<c:if test="${cart != null}">
		<h3>
			<br> Cart:
			<c:out value="${cart.id}" />
		</h3>

		<form action="item" method="post">
			<input type="text" name="action" value="open-current-cart" hidden>
			<input name="userId" value="${user.id}" hidden> <input
				type="submit" value="Open cart">
		</form>
	</c:if>


</body>
</html>


