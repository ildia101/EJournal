<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Оберіть учня</title>

    <link rel="stylesheet" href="../css/general.css" />
  </head>

  <body>
    <a href="../Menu">Повернутися</a>
    <center>
      <form method="POST" action="CreateReportCardOfThisStudent">
        <p class="input-background">
          <span class="text">Оберіть учня: </span>
          <table>
            <c:forEach items="${sessionScope.StudentsFromThisClass}" var="ThisStundent" varStatus="loop">
              <tr>
                <td>${ThisStundent}</td>
                <td><input type="SUBMIT" name="Student${loop.index}" value="Обрати" id="button"/></td>
              </tr>
            </c:forEach>
          </table>
        </p>
      </form>
    </center>
  </body>
</html>
