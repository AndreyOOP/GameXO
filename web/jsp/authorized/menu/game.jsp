<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${gameFound eq false}">
    <h1 align="center">Looking for game</h1>
</c:if>

<c:if test="${gameFound eq true}">
    <br>
    <table align="center">
        <tr>
            <td><h1 align="center">${player1}</h1></td>
            <td><h1 align="center">VS</h1></td>
            <td><h1 align="center">${player2}</h1></td>
        </tr>
        <tr>
            <td> <img src="/avatar/${player1}" /> &nbsp; &nbsp; &nbsp; </td>
            <td>
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
            </td>
            <td> &nbsp; &nbsp; &nbsp; <img src="/avatar/${player2}" /> </td>
        </tr>
    </table>
    <br>
    <h1 align="center">${message}</h1>

</c:if>

<c:if test="${youWin eq true}">
    <h1 align="center">Victory!</h1>
</c:if>

<c:if test="${youLoose eq true}">
    <h1 align="center">Game over...</h1>
</c:if>

<c:if test="${youEven eq true}">
    <h1 align="center">Draw</h1>
</c:if>

<c:if test="${youWin eq true || youLoose eq true || youEven eq true}">

    <div align="center">
        <a href="/backtomainmenu?authKey=${authKey}" class="btn btn-primary" >Main Menu</a>
        <a href="/newgame?authKey=${authKey}" class="btn btn-primary" >New Game</a>
    </div>
</c:if>

<div style="display: none">

    <form id="turnForm" class="form-horizontal" action="/turn" method="POST">
        <input id="turnI" type="number" name="iPos" >
        <input id="turnJ" type="number" name="jPos" >
        <input type="text" name="authKey" value="${authKey}" >
        <button type="submit">Submit</button>
    </form>
</div>

<script>
    
    function autoRefresh() {
        window.location.reload();
    }
    setInterval('autoRefresh()', 1500)
    
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




