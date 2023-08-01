<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Створити клас</title>

    <link rel="stylesheet" href="../../css/general.css" />
  </head>

  <body>
    <center>
      <c:if test="${Error == true}">
        <p id="error"><c:out value="${InvalidData}" /></p>
        <span class="new-line"></span>
      </c:if>
          
      <form method="POST" action="CreatedClassroom">
        <p class="input-background">
          <span class="text">Укажіть ПІБ учнів: </span>
          <c:forEach items="${sessionScope.ListOfStudents}" var="student" varStatus="loop" >
            <span class="new-line"></span>
            <input type="text" name="children${loop.index}" value="${student}" size="100" />
          </c:forEach>
        </p>

        <input id="button" type="SUBMIT" value="Створити клас"/>
      </form>
    </center>
  </body>
</html>
