<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Збережіть код</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login&register.css" />
  </head>

  <body>
    <c:choose>
      <c:when test="${requestScope.EnterCodePage == true}">
        <div id="wrapper">
          <div id="input-field">
            <p class="input-background-wide">
              <span class="button-link">Ви успішно зареєструвалися.</span>
            </p>
            <p class="input-background-wide">
              <span class="button-link">Збережіть код Вашого закладу: ${Code}</span>
            </p>
            <p class="input-background-wide">
                <a href="${pageContext.request.contextPath}/jsp/Login" class="button-link">Увійти до облікового запису</a>
              </p>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <c:redirect url="${pageContext.request.contextPath}" />
      </c:otherwise>
    </c:choose>
  </body>
</html>
