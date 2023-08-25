<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Створений табель</title>

    <link rel="stylesheet" href="../css/general.css" />
  </head>

  <body>
    <a href="../Menu">Повернутися</a>
    <center>
      <p class="input-background">
        <span class="text">Збережіть табель: </span>
        <table>
          <tr>
            <td>Табель успішності</td>
            <td colspan="88">${sessionScope.NameOfStudent.lastName} ${sessionScope.NameOfStudent.firstName} ${sessionScope.NameOfStudent.middleName}</td>
          </tr>
          <tr>
            <td>Предмет</td>
            <td>За 1 семестр</td>
            <td>За 2 семестр</td>
            <td>Підсумкові</td>
          </tr>
          <c:forEach items="${sessionScope.Subjects}" var="ThisSubject" varStatus="loopOfSubjects">
            <tr>
              <td>${ThisSubject}</td>
              <td>${sessionScope.Grades[loopOfSubjects.index][0]}</td>
              <td>${sessionScope.Grades[loopOfSubjects.index][1]}</td>
              <td>${sessionScope.Grades[loopOfSubjects.index][2]}</td>
            </tr>
          </c:forEach>
        </table>
      </p>
    </center>        
  </body>
</html>
