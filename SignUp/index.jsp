<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Зареєструвати обліковий запис</title>

    <link rel="stylesheet" href="../css/login&register.css" />
  </head>

  <body>
    <a href="../">Повернутися</a>
    <center>
      <div id="wrapper">
        <div id="input-field">
          <form method="POST" action="CreateAccount">
            <p class="input-background-wide">
              <span class="input-text">Ваша посада:</span>
              <span class="new-line"></span>
              <select name="role" size="1">
                <option value="none">Не обрано</option>
                <option value="principal">Директор</option>
                <option value="tutor">Класний керівник</option>
                <option value="teacher">Вчитель</option>
              </select>
            </p>
            <p class="input-background-wide">
              <span class="input-text">ПІБ:</span>
              <input type="text" name="name" class="input-info" value="${SavedInfo.Name}"/>
            </p>
            <p class="input-background-wide">
              <span class="input-text">Ім'я поштової скриньки:</span>
              <input type="email" name="email" class="input-info" value="${SavedInfo.Email}"/>
            </p>
            <p class="input-background-wide">
              <span class="input-text">Пароль:</span>
              <input type="password" name="pass" class="input-info"/>
            </p>

            <input type="SUBMIT" value="Зареєструватися" id="register-button"/>
          </form>
          <c:if test="${Error == true}">
            <span class="new-line"></span>
            <p id="error"><c:out value="${InvalidData}"/></p>
          </c:if>
        </div>
      </div>
    </center>
  </body>
</html>
