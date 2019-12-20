<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Store</title>
</head>
<body>
	<h3>
		<c:out value="${errorMsg}" />
	</h3>

	<br>
		<form action="item" method="post">
			<input type="text" name="action" value="back-to-items" hidden>
			<input hidden="true" name="userId" value="${user.id}"> 
			<input type="submit" value="Back to items">
		</form>

</body>
</html>


