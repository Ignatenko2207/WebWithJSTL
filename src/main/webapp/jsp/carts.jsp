<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Carts</title>
</head>
<body>
	<h3>Carts:</h3>

	<form action="item" method="post">
		<input type="text" name="action" value="to-items" hidden> <input
			hidden="true" name="userId" value="${user.id}"> <input
			type="submit" value="Back to items">
	</form>
	<br>
	<br>

	<br>
	<c:forEach items="${cartCollection}" var="cart">
		<form action="cart" method="post">
			<input type="text" name="action" value="open-cart-from-list" hidden>
			<input hidden="true" name="userId" value="${user.id}"> <input
				hidden="true" name="cartId" value="${cart.id}">
			<c:out value="${cart.id} ${cart.status}" />
			<input type="submit" value="open">
		</form>
	</c:forEach>

</body>
</html>