<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="col-md-4" >
        <form class="form-horizontal" action="/myprofile/${authKey}" method="POST" enctype="multipart/form-data">
            <fieldset>
                <legend><h3>Profile of ${userName} :</h3></legend>
                <div class="form-group">
                    <label for="inputUserName" class="col-md-3 control-label">User Name</label>
                    <div class="col-md-9" >
                        <input type="text" name="userName" class="form-control" readonly="true" id="inputUserName" placeholder="User Name" value="${MyProfile_SavedName}">
                        <span class="help-block">${MyProfile_ErrorMessage_UserId}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="col-md-3 control-label">Password</label>
                    <div class="col-md-9" >
                        <input type="password" name="userPassword" class="form-control" id="inputPassword" placeholder="Password" value="${MyProfile_SavedPassword}">
                        <span class="help-block">${MyProfile_ErrorMessage_UserPassword}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputEmail" class="col-md-3 control-label">Email</label>
                    <div class="col-md-9" >
                        <input type="email" name="userEmail" class="form-control" id="inputEmail" placeholder="Email" value="${MyProfile_SavedEmail}">
                        <span class="help-block">${MyProfile_ErrorMessage_UserEmail}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputFile" class="col-md-3 control-label">File</label>
                    <div class="col-md-9" >
                        <input type="file" name="avatarFile" class="form-control" id="inputFile" placeholder="File">
                        <span class="help-block">${MyProfile_ErrorMessage_Avatar}</span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-9 col-md-offset-3">
                        <a href="/welcome/${authKey}" class="btn btn-default">Cancel</a>
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="col-md-8" >
        <img src="/avatar/${userName}" />
    </div>
</div>
