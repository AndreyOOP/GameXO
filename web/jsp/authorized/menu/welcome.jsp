<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div align="center">

    <img src="/blob/${authKey}" />
    <br>
    <h1> Dear ${userName}, welcome to your account! </h1>
    <br>
    ${previousSession}
</div>
