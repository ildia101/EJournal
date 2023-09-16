<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Видалити інформацію</title>

    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css" />
  </head>

  <body>
    <a href="${pageContext.request.contextPath}/jsp/Menu/AddInformation">Повернутися</a>
    <div id="wrapper">
      <div id="input-field">
        <p class="input-background">
          <a href="ChooseClass" class="button-link">Видалити клас</a>
        </p>

        <p class="input-background">
          <a href="ChooseSubject" class="button-link">Видалити предмет</a>
        </p>

        <p class="input-background">
          <a href="ChooseClassGrades" class="button-link">Видалити оцінки</a>
        </p>
      </div>
    </div>
  </body>
</html>
