<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login Or Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <style type="text/css">
        .bs-example{
            margin: 20px;
        }
    </style>
</head>
<body>

<div class="row">
    <div class="col-sm-4">
        <div class="demo-content">

            <div class="bs-example">
                <form class="form-horizontal" action="/login" method="POST">
                    <div class="form-group has-warning">
                        <label class="col-xs-2 control-label" for="inputSuccess">Login</label>
                        <div class="col-xs-10">
                            <input type="text" id="inputSuccess" name="userName" class="form-control" placeholder="Login" value="${Login_SavedName}">
                            <span class="help-block">${Login_ErrorMessage_UserId}</span>
                        </div>
                    </div>
                    <div class="form-group has-warning">
                        <label class="col-xs-2 control-label" for="inputWarning">Password</label>
                        <div class="col-xs-10">
                            <input type="password" id="inputWarning" name="userPassword" class="form-control" placeholder="Password" value="${Login_SavedPassword}" >
                            <span class="help-block">${Login_ErrorMessage_UserPassword}</span>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary" name="buttonLogin"><b>Login</b></button>
                </form>
                <a href="/registration">Register a new user</a>
            </div>
        </div>
    </div>
    <div class="col-sm-8">
        <div class="demo-content bg-alt"></div>
    </div>
</div>

</body>
</html>
