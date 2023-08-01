<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Ви вийшли</title>

    <link rel="stylesheet" href="../css/general.css" />
  </head>

  <body>
    <c:choose>
      <c:when test="${requestScope.ShowSignOutPage == true}">
        <div id="wrapper">
          <div id="input-field">
            <p class="input-background">
              <span class="button-link">Ви успішно вийшли. Можете закрити це вікно.</a>
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
