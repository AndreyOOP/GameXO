<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--todo for refresh only game field--%>
<html>
<head>
    <meta http-equiv="refresh" content="2">
</head>
<body>
<table border="1" align="center">
<c:forEach var="i" begin="0" end="${matrixSize-1}" >
<tr>
<c:forEach var="j" begin="0" end="${matrixSize-1}">

<c:set var="cell_id" value="cell_${i}_${j}" ></c:set>

<td id="${cell_id}"
align="center" width="40" height="40"
onclick      = "setValue(${i}, ${j})"
onmouseover  = "markCell('${cell_id}')"
onmouseleave = "unMarkCell('${cell_id}')">
<c:if test="${cells[i][j] eq 0}">O</c:if>
<c:if test="${cells[i][j] eq 1}">X</c:if>
</td>
</c:forEach>
</tr>
</c:forEach>
</table>

<div style="display: none">

    <form id="turnForm" class="form-horizontal" action="/turn" method="POST">
        <input id="turnI" type="number" name="iPos" >
        <input id="turnJ" type="number" name="jPos" >
        <input type="text" name="authKey" value="${authKey}" >
        <button type="submit">Submit</button>
    </form>
</div>
</body>
</html>
<script>

    //    function autoRefresh() {
    //        window.location.reload();
    //    }
    //    setInterval('autoRefresh()', 2000)

    function setValue(i, j){

        document.getElementById("turnI").value = i;
        document.getElementById("turnJ").value = j;
        document.getElementById("turnForm").submit();

    }

    function markCell(elementId) {
        document.getElementById(elementId).style.backgroundColor = "#5f9ea0";
    }

    function unMarkCell(elementId) {
        document.getElementById(elementId).style.backgroundColor = "";
    }
</script>
