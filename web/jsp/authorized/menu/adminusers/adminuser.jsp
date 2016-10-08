<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>

    function displayAddForm(){

        document.getElementById("addFrom").style.display  = 'block';
        document.getElementById("editForm").style.display = 'none';

        document.getElementById("inputUserName").value = '';
        document.getElementById("inputPassword").value = '';
        document.getElementById("inputEmail").value    = '';
    }
    function displayEditForm(){

        document.getElementById("editForm").style.display = 'block';
        document.getElementById("addFrom").style.display = 'none';
    }

    function hideAddForm(){
        document.getElementById("addFrom").style.display = 'none';
    }
    function hideEditForm(){
        document.getElementById("editForm").style.display = 'none';
    }
</script>

<div class="row">
    <div class="col-md-6" >

        <h3>User List</h3>

        <p align="right">
            <c:if test="${userRole eq 303}">
                <a href="<c:url value='#'  />" class="btn btn-primary btn-sm" onclick="displayAddForm()" >Add Person</a>
            </c:if>
            <a href="<c:url value='/download?authKey=${authKey}&fileId=1' />" class="btn btn-primary btn-sm">Download .csv</a>
        </p>

        <table class="table table-bordered table-hover">
            <thead>
                <tr bgcolor="#a3a3b3">
                    <th width="20%" >User Name</th>
                    <th width="19%">Password</th>
                    <th width="20%">Email</th>
                    <th width="5%">Role</th>

                    <c:if test="${userRole eq 303}">
                        <th width="10%">Delete</th>
                        <th width="10%">Edit</th>
                    </c:if>
                    <th width="16%">Reset Password</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listUsers}" var="user">
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.password}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>

                        <c:if test="${userRole eq 303}">
                            <td><a href="<c:url value='/edituser?authKey=${authKey}&editUser=${user.name}&tableCurrentPage=${tableCurrentPage}' />" onclick="displayEditForm()" >Edit</a></td>
                            <td><a href="<c:url value='/delete?authKey=${authKey}&deleteUser=${user.name}&tableCurrentPage=${tableCurrentPage}' />" >Delete</a></td>
                        </c:if>
                        <td><a href="<c:url value='/reset?authKey=${authKey}&resetUser=${user.name}&tableCurrentPage=${tableCurrentPage}' />" >Reset Password</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <ul class="pagination">

            <li><a href="<c:url value='/admin/users?authKey=${authKey}&tableCurrentPage=${previous}' />">&laquo;</a></li>

            <c:forEach begin="${fromPage}" end="${toPage}" varStatus="loop">

                <c:if test="${loop.index eq tableCurrentPage}">
                    <li class="active"><a href="<c:url value='/admin/users?authKey=${authKey}&tableCurrentPage=${loop.index}' />">${loop.index}</a></li>
                </c:if>

                <c:if test="${loop.index ne tableCurrentPage}">
                    <li><a href="<c:url value='/admin/users?authKey=${authKey}&tableCurrentPage=${loop.index}' />">${loop.index}</a></li>
                </c:if>

            </c:forEach>

            <li><a href="<c:url value='/admin/users?authKey=${authKey}&tableCurrentPage=${next}' />">&raquo;</a></li>
        </ul>

    </div>

    <div id="addFrom" style=
        <c:if test="${showMenu eq true}"> "display: block" </c:if>
        <c:if test="${showMenu ne true}"> "display: none"  </c:if>
    >
        <jsp:include page="/addnewuserpagecontent" />
    </div>

    <div id="editForm" style=
        <c:if test="${showEditMenuX eq true}"> "display: block" </c:if>
        <c:if test="${showEditMenuX ne true}"> "display: none"  </c:if>
    >
        <jsp:include page="/edituserpagecontent" />
    </div>

</div>

