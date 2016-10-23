<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1 align="center"> Error occur!</h1> <br>
    <h1 align="center"> ${missingLoginSession}</h1>
    <h1 align="center"> ${userWithYourNameOnline}</h1>
    <h1 align="center"> ${errorMessage}</h1>
</body>
</html>
