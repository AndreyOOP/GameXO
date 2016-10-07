<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<c:if test="${authorized eq true}">
    <h2> Authorized ! </h2> <br>
    Login is ${log} <br>
    Password is ${pass}
</c:if>

<c:if test="${authorized eq false}">
    <h2>Authorization Error</h2>
</c:if>

</body>
</html>
