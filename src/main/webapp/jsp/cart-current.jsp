<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Cart</title>
</head>
<body>
	<form action="cart" method="post">
		<input type="text" name="action" value="all-carts" hidden>
		<input hidden="true" name="userId" value="${user.id}"> 
		<input type="submit" value="All carts">
	</form>
	<br>
	<br>
		<form action="item" method="post">
		<input type="text" name="action" value="to-items" hidden> <input
			hidden="true" name="userId" value="${user.id}"> <input
			type="submit" value="Back to items">
	</form>
	<br>
	<br>
	
	<h3>
		Cart, id =
		<c:out value="${cart.id} ${cart.status}" />
	</h3>
	<br>

	<c:if test="${cart.status == 'OPEN'}">
		<form action="cart" method="post">
			<input type="text" name="action" value="do-cart-to-be-closed" hidden>
			<input hidden="true" name="userId" value="${user.id}"> <input
				hidden="true" name="cartId" value="${cart.id}"> <input
				type="submit" value="To be closed">
		</form>
	</c:if>

	<c:if test="${cart.status == 'TO_BE_CLOSED'}">
		<form action="cart" method="post">
			<input type="text" name="action" value="do-cart-closed" hidden>
			<input hidden="true" name="userId" value="${user.id}"> <input
				hidden="true" name="cartId" value="${cart.id}"> <input
				type="submit" value="Close">
		</form>
	</c:if>

	<c:forEach items="${orderDTOCollection}" var="orderDTO">
		<form>
			<c:out
				value="${orderDTO.itemId} ${orderDTO.itemName} ${orderDTO.itemPrice} - ${orderDTO.amount}" />
			<input hidden="true" name="userId" value="${user.id}">
		</form>
	</c:forEach>

</body>
</html>