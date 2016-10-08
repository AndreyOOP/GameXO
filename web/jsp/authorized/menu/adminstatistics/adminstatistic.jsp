<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>

    function displayAddForm(){

        document.getElementById("addFrom").style.display  = 'block';
        document.getElementById("editForm1").style.display = 'none';

        document.getElementById("inputUserName").value = '';
        document.getElementById("inputVsUser").value   = '';
        document.getElementById("inputWin").value      = '0';
        document.getElementById("inputLoose").value    = '0';
        document.getElementById("inputEven").value     = '0';
    }
    function displayEditForm(){

        document.getElementById("editForm1").style.display = 'block';
        document.getElementById("addFrom").style.display = 'none';
    }

    function hideAddForm(){
        document.getElementById("addFrom").style.display = 'none';
    }
    function hideEditForm1(){
        document.getElementById("editForm1").style.display = 'none';
    }
</script>

<div class="row">
    <div class="col-md-6" >

        <h3>Statistic Raw Data</h3>


        <p align="right">
            <c:if test="${userRole eq 303}"> <%--Super Admin--%>
                <a href="<c:url value='#'  />" class="btn btn-primary btn-sm" onclick="displayAddForm()" >Add Record</a>
            </c:if>
            <a href="<c:url value='/download?authKey=${authKey}&fileId=0' />" class="btn btn-primary btn-sm">Download .csv</a>
        </p>

        <table class="table table-bordered table-hover">
            <thead>
            <tr bgcolor="#a3a3b3">

                <th width="10%" >Id</th>
                <th width="20%">Player</th>
                <th width="20%">vsPlayer</th>
                <th width="10%">Win</th>
                <th width="10%">Loose</th>
                <th width="10%">Even</th>

                <c:if test="${userRole eq 303}"> <%--Super Admin view--%>
                    <th width="10%">Edit</th>
                    <th width="10%">Delete</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${admin_statistic_listRecords}" var="record">
                <tr>
                    <td>${record.id}</td>
                    <td>${record.user}</td>
                    <td>${record.vsUser}</td>
                    <td>${record.win}</td>
                    <td>${record.loose}</td>
                    <td>${record.even}</td>
                    <c:if test="${userRole eq 303}"> <%--Super Admin view--%>
                        <td><a href="<c:url value='/editrecord?authKey=${authKey}&recordId=${record.id}&tableCurrentPage=${tableCurrentPage}' />" onclick="displayEditForm()" >Edit</a></td>
                        <td><a href="<c:url value='/deleterecord?authKey=${authKey}&deleteRecordId=${record.id}&tablePage=${tableCurrentPage}' />" >Delete</a></td>
                    </c:if>

                </tr>
            </c:forEach>
            </tbody>
        </table>

        <ul class="pagination" >

            <li><a href="<c:url value='/admin/statistic?authKey=${authKey}&tableCurrentPage=${previous}' />">&laquo;</a></li>

            <c:forEach begin="${fromPage}" end="${toPage}" varStatus="loop">

                <c:if test="${loop.index eq tableCurrentPage}">
                    <li class="active"><a href="<c:url value='/admin/statistic?authKey=${authKey}&tableCurrentPage=${loop.index}' />">${loop.index}</a></li>
                </c:if>

                <c:if test="${loop.index ne tableCurrentPage}">
                    <li><a href="<c:url value='/admin/statistic?authKey=${authKey}&tableCurrentPage=${loop.index}' />">${loop.index}</a></li>
                </c:if>
            </c:forEach>

            <li><a href="<c:url value='/admin/statistic?authKey=${authKey}&tableCurrentPage=${next}' />">&raquo;</a></li>
        </ul>

    </div>

    <div id="addFrom" style=
        <c:if test="${showMenu eq true}"> "display: block" </c:if>
        <c:if test="${showMenu ne true}"> "display: none"  </c:if>
    >
        <jsp:include page="/addrecordpagecontent" />
    </div>

    <div id="editForm1" style=
        <c:if test="${showEditMenu eq true}"> "display: block" </c:if>
        <c:if test="${showEditMenu ne true}"> "display: none"  </c:if>
    >
        <jsp:include page="/editrecordpagecontent" />
    </div>

</div>