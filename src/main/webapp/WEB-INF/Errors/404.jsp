<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Не знайдено</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css" />
  </head>

  <body>
    <div id="wrapper">
      <div id="input-field">
        <p class="input-background">
          <span class="button-link">Сервер не може знайти запитуваний ресурс.</span>
        </p>

        <p class="input-background">
          <a href="${pageContext.request.contextPath}/jsp/Menu" class="button-link">Повернутися</a>
        </p>
      </div>
    </div>
  </body>
</html>