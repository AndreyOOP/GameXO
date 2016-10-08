<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Statistic of ${userName}</h2> <br>
<h3>Your rank is ${rank}</h3> <br><br>

<table class="table table-bordered table-hover">
    <thead>
    <tr bgcolor="#a3a3b3">

        <th width="25%">Player</th>
        <th width="25%">vsPlayer</th>
        <th width="17%">Win</th>
        <th width="16%">Loose</th>
        <th width="17%">Even</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${statistic_listRecords}" var="record">
        <tr>
            <td>${record.user}</td>
            <td>${record.vsUser}</td>
            <td>${record.win}</td>
            <td>${record.loose}</td>
            <td>${record.even}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>