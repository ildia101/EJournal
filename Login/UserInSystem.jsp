<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Ви у системі</title>

    <link rel="stylesheet" href="../css/login&register.css" />
  </head>

  <body>
    <c:choose>
      <c:when test="${sessionScope.LoggedIn == true}">
        <div id="wrapper">
          <div id="input-field">
            <p class="input-background-wide">
              <a href="../Menu" class="button-link">Ви успішно увійшли. Тепер можна перейти до системи.</a>
            </p>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <c:redirect url="../index.jsp" />
      </c:otherwise>
    </c:choose>
  </body>
</html>