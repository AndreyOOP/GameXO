<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
    <div class="col-md-6" >

        <h3>User Status</h3>


        <p align="right">
            <a href="<c:url value='/admin/status?authKey=${authKey}&tableCurrentPage=0' />" class="btn btn-primary btn-sm">Refresh</a>
        </p>

        <table class="table table-bordered table-hover">
            <thead>
            <tr bgcolor="#a3a3b3">

                <th width="40%" >User Name</th>
                <th width="20%">Status</th>
                <th width="20%">Login Session</th>
                <th width="20%">Game Session</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${admin_status_online}" var="record">
                <tr>
                    <td>${record.userName}</td>
                    <td>${record.status}</td>
                    <td><a href="<c:url value='/killloginsession?authKey=${authKey}&recordId=${record.userName}&tableCurrentPage=${tableCurrentPage}' />" >Remove</a></td>
                    <td><a href="<c:url value='/killgamesession?authKey=${authKey}&recordId=${record.userName}&tableCurrentPage=${tableCurrentPage}' />" >Remove</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <ul class="pagination" >

            <li><a href="<c:url value='/admin/status?authKey=${authKey}&tableCurrentPage=${previous}' />">&laquo;</a></li>

            <c:forEach begin="${fromPage}" end="${toPage}" varStatus="loop">

                <c:if test="${loop.index eq tableCurrentPage}">
                    <li class="active"><a href="<c:url value='/admin/status?authKey=${authKey}&tableCurrentPage=${loop.index}' />">${loop.index}</a></li>
                </c:if>

                <c:if test="${loop.index ne tableCurrentPage}">
                    <li><a href="<c:url value='/admin/status?authKey=${authKey}&tableCurrentPage=${loop.index}' />">${loop.index}</a></li>
                </c:if>
            </c:forEach>

            <li><a href="<c:url value='/admin/status?authKey=${authKey}&tableCurrentPage=${next}' />">&raquo;</a></li>
        </ul>

    </div>

</div>