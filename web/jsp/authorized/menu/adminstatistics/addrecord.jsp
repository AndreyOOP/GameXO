<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>&nbsp;</h3>
<p>&nbsp;<br>&nbsp;</p>

<div class="row">
    <div class="col-md-4" >
        <form class="form-horizontal" action="/addrecord" method="POST">
            <fieldset>
                <legend><h3>Add statistic record :</h3></legend>

                <label for="inputUserName" class="col-md-3 control-label">User Name</label>
                <div class="col-md-9" >
                    <input type="text" name="formUserName" class="form-control" id="inputUserName" placeholder="User Name" value="${Admin_Stat_Saved_UserName}">
                    <span class="help-block">${Admin_Stat_Error_UserName}</span>
                </div>

                <label for="inputVsUser" class="col-md-3 control-label">Vs User</label>
                <div class="col-md-9" >
                    <input type="text" name="vsUserName" class="form-control" id="inputVsUser" placeholder="Vs User" value="${Admin_Stat_Saved_VsUserName}">
                    <span class="help-block">${Admin_Stat_Error_VsUserName}</span>
                </div>

                <label for="inputWin" class="col-md-3 control-label">Win</label>
                <div class="col-md-9" >
                    <input type="number" name="win" class="form-control" id="inputWin" value="0${Admin_Stat_Saved_Win}">
                    <span class="help-block"></span>
                </div>

                <label for="inputLoose" class="col-md-3 control-label">Loose</label>
                <div class="col-md-9" >
                    <input type="number" name="loose" class="form-control" id="inputLoose" value="0${Admin_Stat_Saved_Loose}">
                    <span class="help-block"></span>
                </div>

                <label for="inputEven" class="col-md-3 control-label">Even</label>
                <div class="col-md-9" >
                    <input type="number" name="even" class="form-control" id="inputEven" value="0${Admin_Stat_Saved_Even}">
                    <span class="help-block"></span>
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