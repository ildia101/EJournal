<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Оновлення класу</title>

    <link rel="stylesheet" href="../../css/general.css" />
  </head>

  <body>
    <center>
      <c:if test="${Error == true}">
        <p id="error"><c:out value="${InvalidData}" /></p>
        <span class="new-line"></span>
      </c:if>
      <form method="POST" action="UpdatedClassroom">
        <p class="input-background">
          <span class="text">Укажіть ПІБ учнів: </span>
          <c:forEach items="${requestScope.StudentsFromThisClass}" var="ThisStundent" varStatus="loop">
            <span class="new-line"></span>
            <input type="text" name="student${loop.index}" value="${ThisStundent}" size="100"/>
          </c:forEach>
        </p>

        <input type="SUBMIT" value="Зберегти" id="button"/>
      </form>
    </center>
  </body>
</html>
