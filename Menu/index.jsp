<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Меню</title>

    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>

    <link rel="stylesheet" href="../css/general.css" />
  </head>

  <body>
    <div id="wrapper">
      <div id="input-field">
        <c:if test="${sessionScope.Role == sessionScope.Principal}">
          <p class="input-background">
            <a href="./CreateClassroom" class="button-link">Створити клас</a>
          </p>
        </c:if>

        <p class="input-background">
          <a href="./AddInformation" class="button-link">Додати інформацію</a>
        </p>

        <c:if test="${sessionScope.Role == sessionScope.Principal}">
          <p class="input-background">
            <a href="SelectClassToCreateReportCard" class="button-link">Створити табель</a>
          </p>
        </c:if>
        <c:if test="${sessionScope.Role == sessionScope.Tutor}">
          <p class="input-background">
            <a href="SelectClassToCreateReportCard" class="button-link">Створити табель</a>
          </p>
        </c:if>

        <c:if test="${sessionScope.Role == sessionScope.Principal}">
          <p class="input-background">
            <a href="Employees" class="button-link">Співробітники</a>
          </p>
        </c:if>

        <p class="input-background">
          <a href="SignOut" class="button-link">Вийти з облікового запису</a>
        </p>
      </div>
    </div>
  </body>
</html>
