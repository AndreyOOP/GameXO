<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<h3>&nbsp;</h3>
<p>&nbsp;<br>&nbsp;</p>

<div class="row">
    <div class="col-md-4" >
        <form class="form-horizontal" action="<%= blobstoreService.createUploadUrl("/addnewuser") %>" method="POST" enctype="multipart/form-data">
            <fieldset>
                <legend><h3>Add new user :</h3></legend>

                <label for="inputUserName" class="col-md-3 control-label">User Name</label>
                <div class="col-md-9" >
                    <input type="text" name="formUserName" class="form-control" id="inputUserName" placeholder="User Name" value="${SavedName}">
                    <span class="help-block">${ErrorMessage_UserId}</span>
                </div>

                <label for="inputPassword" class="col-md-3 control-label">Password</label>
                <div class="col-md-9" >
                    <input type="password" name="userPassword" class="form-control" id="inputPassword" placeholder="Password" value="${SavedPassword}">
                    <span class="help-block">${ErrorMessage_UserPassword}</span>
                </div>

                <label for="inputRole" class="col-md-3 control-label">Role</label>
                <div class="col-md-9" >
                    <select class="form-control" id="inputRole" name="userRole" value=${SavedRole}>
                        <option>User</option>
                        <option <c:if test="${SavedRole eq 202}" >selected</c:if>>Admin</option>
                        <option <c:if test="${SavedRole eq 303}" >selected</c:if>>Super_Admin</option>
                    </select>
                    <span class="help-block">${ErrorMessage_UserRole}</span>
                </div>

                <label for="inputEmail" class="col-md-3 control-label">Email</label>
                <div class="col-md-9" >
                    <input type="email" name="userEmail" class="form-control" id="inputEmail" placeholder="Email" value="${SavedEmail}">
                    <span class="help-block">${ErrorMessage_UserEmail}</span>
                </div>

                <label for="inputFile" class="col-md-3 control-label">File</label>
                <div class="col-md-9" >
                    <input type="file" name="avatarFile" class="form-control" id="inputFile" placeholder="File">
                    <span class="help-block">${ErrorMessage_Avatar}</span>
                </div>

                <input type="hidden" class="form-control" name="tableCurrentPage" value="${tableCurrentPage}">
                <input type="hidden" class="form-control" name="authKey" value="${authKey}">

                <div class="col-md-9 col-md-offset-3">
                    <a class="btn btn-default" onclick="hideAddForm()">Cancel</a>
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
            </fieldset>
        </form>
    </div>
</div>
