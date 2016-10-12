<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registration</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<div class="row">
    <div class="col-md-4" >
        <form class="form-horizontal" action="<%= blobstoreService.createUploadUrl("/registration") %>" method="POST" enctype="multipart/form-data">
            <fieldset>
                <legend><h3>Registration Form:</h3></legend>
                <div class="form-group">
                    <label for="inputUserName" class="col-md-3 control-label">User Name</label>
                    <div class="col-md-9">
                        <input type="text" name="userName" class="form-control" id="inputUserName" placeholder="User Name" value="${Registration_SavedName}">
                        <span class="help-block">${Registration_ErrorMessage_UserId}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-md-3 control-label">Password</label>
                    <div class="col-md-9" >
                        <input type="password" name="userPassword" class="form-control" id="inputPassword" placeholder="Password" value="${Registration_SavedPassword}">
                        <span class="help-block">${Registration_ErrorMessage_UserPassword}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputEmail" class="col-md-3 control-label">Email</label>
                    <div class="col-md-9" >
                        <input type="email" name="userEmail" class="form-control" id="inputEmail" placeholder="Email" value="${Registration_SavedEmail}">
                        <span class="help-block">${Registration_ErrorMessage_UserEmail}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputFile" class="col-md-3 control-label">Avatar Image</label>
                    <div class="col-md-9" >
                        <input type="file" name="avatarFile" class="form-control" id="inputFile" placeholder="File">
                        <span class="help-block">${Registration_ErrorMessage_Avatar}</span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-9 col-md-offset-3">
                        <a href="/" class="btn btn-default">Cancel</a>
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>


</div>

</body>
</html>