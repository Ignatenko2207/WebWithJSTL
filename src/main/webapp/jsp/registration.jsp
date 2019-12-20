<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>>Registration page</title>
</head>
<body>
	<h3>Hello, input all date. please!</h3>

	<form action="user" method="post">
    <input type="text" name="action" value="register" hidden>
    <input type="text" size="40" name="login" placeholder="Input login" required>
    <br>
    <input type="password" size="40" name="password" placeholder="Input password" required>
    <br>
    <input type="text" size="40" name="fname" placeholder="Input first name" required>
    <br>
    <input type="text" size="40" name="lname" placeholder="Input last name" required>
    <br>
    <input type="text" size="40" name="email" placeholder="Input email" required>
    <br>
    <input type="text" size="40" name="phone" placeholder="Input phone">
    <br>
    <input type="submit" value="register">
</form>

<br>
<h3>
    <a href="/WebWithJSTLMA45/index.jsp">GO BACK!</a>
</h3>

	<h2><c:out value="${errorMsg}"/></h2>
</body>
</html>
