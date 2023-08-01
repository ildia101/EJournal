<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Створити клас</title>

    <link rel="stylesheet" href="../../css/general.css" />
  </head>

  <body>
    <a href="../">Повернутися</a>
    <center>
      <div id="wrapper">
        <div id="input-field">
          <form method="POST" action="EnterAListOfStudents">
            <p class="input-background">
              <span class="text">Укажіть цифру класа: </span>
              <span class="new-line"></span>
              <select name="number" size="1">
                <option value="-" selected hidden>-</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
              </select>
            </p>
            <br />
            <p class="input-background">
              <span class="text">Укажіть букву класа: </span>
              <span class="new-line"></span>
              <input type="text" name="letter" value="${Letter}"/>
            </p>
            <br />
            <p class="input-background">
              <span class="text">Укажіть кількість учнів: </span>
              <span class="new-line"></span>
              <input type="number" name="number-of-students" value="${NumberOfStudents}"/>
            </p>
            <br /><br />

            <input type="SUBMIT" value="Далі" id="button" />
          </form>
          <c:if test="${Error == true}">
            <span class="new-line"></span>
            <p id="error"><c:out value="${InvalidData}" /></p>
          </c:if>
        </div>
      </div>
    </center>
  </body>
</html>
