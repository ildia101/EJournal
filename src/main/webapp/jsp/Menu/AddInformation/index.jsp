<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Додати інформацію</title>

    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css" />
  </head>

  <body>
    <a href="${pageContext.request.contextPath}/jsp/Menu">Повернутися</a>
    <div id="wrapper">
      <div id="input-field">
        <c:if test="${sessionScope.Role == sessionScope.Principal}">
          <p class="input-background">
            <a href="SelectClassroom" class="button-link">Редагувати класи</a>
          </p>
        </c:if>

        <p class="input-background">
          <a href="./AddSubject" class="button-link">Додати предмет</a>
        </p>

        <p class="input-background">
          <a href="ChooseClassAndSubject" class="button-link">Внести оцінки</a>
        </p>

        <c:if test="${sessionScope.Role == sessionScope.Principal}">
          <p class="input-background">
            <a href="./DeleteInformation" class="button-link">Видалити інформацію</a>
          </p>
        </c:if>
      </div>
    </div>
  </body>
</html>
