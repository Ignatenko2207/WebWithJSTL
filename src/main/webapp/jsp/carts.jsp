<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Carts</title>
</head>
<body>
	<h3>Carts:</h3>

	<!-- Back to items -->
	<form action="item" method="post">
		<input type="text" name="action" value="to-items" hidden> <input
			hidden="true" name="userId" value="${user.id}"> <input
			type="submit" value="Back to items">
	</form>
	<br>
	<br>

	<!-- Table of carts  -->
	<table>
		<!-- here should go some titles... -->
		<tr>
			<th>ID</th>
			<th>STATUS</th>
		</tr>
		<c:forEach items="${cartCollection}" var="cart">
			<form action="cart" method="post">
			<tr>
				<td><c:out value="${cart.id}" /></td>
				<td><c:out value="${cart.status}" /></td>
				<td><input type="text" name="action"
					value="open-cart-from-list" hidden></td>
				<td><input hidden="true" name="userId" value="${user.id}">
				</td>
				<td><input hidden="true" name="cartId" value="${cart.id}">
				</td>
				<td><input type="submit" value="open"></td>
			</tr>
			</form>
		</c:forEach>
	</table>
	
</body>
</html>