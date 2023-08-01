<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Введіть назву предмета</title>

    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>

    <link rel="stylesheet" href="../../../css/general.css" />
  </head>

  <body>
    <a href="../">Повернутися</a>
    <div id="wrapper">
      <div id="input-field">
        <center>
          <form method="POST" action="AddNextSubject">
            <p class="input-background">
              <span class="text">Введіть назву предмета:</span>
              <span class="new-line"></span>
              <br /><br />
              <input type="text" name="subject" size="50" />
            </p>
            <br /><br />

            <input type="SUBMIT" name="button" value="Додати" id="button"/>
            <br /><br />
            <input type="SUBMIT" name="button" value="Додати й завершити" id="button"/>
            <br /><br />
            <input type="SUBMIT" name="button" value="Вийти без додавання" id="button"/>
          </form>

          <c:if test="${SomeInfo == true}">
            <br /> <br /> <br /> <br />
            <span class="new-line"></span>
            <p id="error"><c:out value="${Info}" /></p>
          </c:if>
        </center>
      </div>
    </div>
  </body>
</html>
