<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Оберіть клас</title>

    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>

    <link rel="stylesheet" href="../../../css/general.css" />
  </head>

  <body>
    <a href="../DeleteInformation">Повернутися</a>
    <div id="wrapper">
      <div id="input-field">
        <center>
          <form method="POST" action="DeleteClass">
            <input type="hidden" name="Deleting" value="Class" />
            <p class="input-background">
              <span class="text">Оберіть клас для видалення:</span>
              <span class="new-line"></span>
              <select name="classroom" size="1">
                <option value="-" selected hidden>-</option>
                <c:forEach items="${sessionScope.Classes}" var="ThisClass">
                  <option value="${ThisClass}">${ThisClass}</option>
                </c:forEach>
              </select>
            </p>
            <br /><br />

            <input type="SUBMIT" name="button" value="Видалити" id="button"/>
            <br /><br />
            <input type="SUBMIT" name="button" value="Завершити" id="button"/>
          </form>

          <c:if test="${SomeInfo == true}">
            <br /> <br /> <br />
            <span class="new-line"></span>
            <p id="error"><c:out value="${Info}" /></p>
          </c:if>
        </center>
      </div>
    </div>
  </body>
</html>
