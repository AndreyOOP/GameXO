<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>XO Game</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-2">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value='/welcome/${authKey}' />">XO Game</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
            <ul class="nav navbar-nav">

                <li><a href="<c:url value='/findgame?authKey=${authKey}' />">Find Game</a></li>

                <li><a href="<c:url value='/statistic?authKey=${authKey}' />">Statistic</a></li>

                <%--<li><a href="#">Download</a></li>--%>

                <c:if test="${(userRole eq 202) || (userRole eq 303)}"> <%--Admin or Super Admin menu--%>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Admin<span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="<c:url value='/admin/users?authKey=${authKey}&tableCurrentPage=0' />">Users Table</a></li>
                            <li><a href="<c:url value='/admin/statistic?authKey=${authKey}&tableCurrentPage=0' />">Statistic Table</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="<c:url value='/myprofile/${authKey}' />">My Profile</a></li>
                <li><a href="<c:url value='/logout/${authKey}' />">Logout</a></li> <%--href="<c:url value='/superadminmenu/edit/${user.name}' />"--%>
            </ul>
        </div>
    </div>
</nav>

<c:if test="${isWelcomePage eq true}">
    <jsp:include page="/welcomepagecontent" />
</c:if>

<c:if test="${isProfile eq true}"> <%--open myProfile menu--%>
    <jsp:include page="/myprofilepagecontent" />
</c:if>

<c:if test="${isAdminPage eq true}"> <%--open Admin Users table menu--%>
    <jsp:include page="/superadminmenupagecontent" />
</c:if>

<c:if test="${isAdminStatistic eq true}">
    <jsp:include page="/adminstatisticpagecontent" />
</c:if>

<c:if test="${isFindGame eq true}"> <%--open Game Page--%>
    <jsp:include page="/gamepagecontent" />
</c:if>

<c:if test="${isStatisticPage eq true}"> <%--open Game Page--%>
    <jsp:include page="/statisticpagecontent" />
</c:if>

</body>
</html>