<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Cart</title>
</head>
<body>

	<!-- 	Back to items -->
	<br>
	<form action="item" method="post">
		<input type="text" name="action" value="to-items" hidden> <input
			hidden="true" name="userId" value="${user.id}"> <input
			type="submit" value="Back to items">
	</form>

	<h3>
		Cart, id =
		<c:out value="${cart.id} ${cart.status}" />
	</h3>

	<!-- Button "To be closed" -->
	<c:if test="${cart.status == 'OPEN'}">
		<form action="cart" method="post">
			<input type="text" name="action" value="do-cart-to-be-closed" hidden>
			<input hidden="true" name="userId" value="${user.id}"> <input
				hidden="true" name="cartId" value="${cart.id}"> <input
				type="submit" value="To be closed">
		</form>
	</c:if>

	<!-- Button "Closed" -->
	<c:if test="${cart.status == 'TO_BE_CLOSED'}">
		<form action="cart" method="post">
			<input type="text" name="action" value="do-cart-closed" hidden>
			<input hidden="true" name="userId" value="${user.id}"> <input
				hidden="true" name="cartId" value="${cart.id}"> <input
				type="submit" value="Close">
		</form>
	</c:if>

	<!-- Table of items in cart -->
	<table>
		Items in cart:
		<tr>
			<th>ID</th>
			<th>NAME</th>
			<th>PRICE</th>
			<th>AMOUNT</th>
		</tr>
		<c:forEach items="${orderDTOCollection}" var="orderDTO">
			<tr>
				<td><c:out value="${orderDTO.itemId}" /></td>
				<td><c:out value="${orderDTO.itemName}" /></td>
				<td><c:out value="${orderDTO.itemPrice}" /></td>
				<td><c:out value="${orderDTO.amount}" /></td>
			</tr>
		</c:forEach>
	</table>

	<!-- All carts -->
	<form action="cart" method="post">
		<input type="text" name="action" value="all-carts" hidden> <input
			hidden="true" name="userId" value="${user.id}"> <input
			type="submit" value="All carts">
	</form>












</body>
</html>