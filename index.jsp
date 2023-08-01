<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>

    <link rel="stylesheet" href="./css/login&register.css" />

    <title>Електронний журнал</title>
  </head>

  <body>
    <div id="wrapper">
      <div id="input-field">
        <c:choose>
          <c:when test="${sessionScope.LoggedIn == true}">
            <p class="input-background">
              <a href="./Menu" class="button-link">Ви вже увійшли до облікового запису. Натисніть, щоб перейти до системи.</a>
            </p>
          </c:when>
          <c:otherwise>
            <p class="input-background">
              <a href="./Login" class="button-link">Увійти до облікового запису</a>
            </p>
    
            <p class="input-background">
              <a href="./SignUp" class="button-link">Зареєструвати обліковий запис</a>
            </p>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </body>
</html>
