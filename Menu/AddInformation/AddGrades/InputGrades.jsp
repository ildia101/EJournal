<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Додайте оцінки</title>

    <link rel="stylesheet" href="../../css/general.css" />
  </head>

  <body>
    <center>
      <c:if test="${Error == true}">
        <p id="error"><c:out value="${InvalidData}" /></p>
        <span class="new-line"></span>
      </c:if>

      <form method="POST" action="SaveGrades">
        <p class="input-background">
          <span class="text">Додайте оцінки: </span>
          <span class="new-line"></span>
          <span class="text">Т - Тематична; С - Семестрова; Р - Річна</span>
          <table>
            <tr>
              <td></td>
              <td><input type="text" name="date0" value="${sessionScope.Marks[0].date}" size="4"/></td>
              <td><input type="text" name="date1" value="${sessionScope.Marks[1].date}" size="4"/></td>
              <td><input type="text" name="date2" value="${sessionScope.Marks[2].date}" size="4"/></td>
              <td><input type="text" name="date3" value="${sessionScope.Marks[3].date}" size="4"/></td>
              <td><input type="text" name="date4" value="${sessionScope.Marks[4].date}" size="4"/></td>
              <td><input type="text" name="date5" value="${sessionScope.Marks[5].date}" size="4"/></td>
              <td><input type="text" name="date6" value="${sessionScope.Marks[6].date}" size="4"/></td>
              <td><input type="text" name="date7" value="${sessionScope.Marks[7].date}" size="4"/></td>
              <td><input type="text" name="date8" value="${sessionScope.Marks[8].date}" size="4"/></td>
              <td><input type="text" name="date9" value="${sessionScope.Marks[9].date}" size="4"/></td>
              <td><input type="text" name="date10" value="${sessionScope.Marks[10].date}" size="4"/></td>
              <td><input type="text" name="date11" value="${sessionScope.Marks[11].date}" size="4"/></td>
              <td><input type="text" name="date12" value="${sessionScope.Marks[12].date}" size="4"/></td>
              <td><input type="text" name="date13" value="${sessionScope.Marks[13].date}" size="4"/></td>
              <td><input type="text" name="date14" value="${sessionScope.Marks[14].date}" size="4"/></td>
              <td><input type="text" name="date15" value="${sessionScope.Marks[15].date}" size="4"/></td>
              <td><input type="text" name="date16" value="${sessionScope.Marks[16].date}" size="4"/></td>
            </tr>
            <c:forEach items="${sessionScope.StudentsFromThisClass}" var="ThisStudent" varStatus="loop">
              <tr>
                <td><p>${ThisStudent.lastName} ${ThisStudent.firstName} ${ThisStudent.middleName}</p></td>
                <td><input type="text" name="grade${loop.index}0" value="${sessionScope.Marks[0+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}1" value="${sessionScope.Marks[1+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}2" value="${sessionScope.Marks[2+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}3" value="${sessionScope.Marks[3+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}4" value="${sessionScope.Marks[4+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}5" value="${sessionScope.Marks[5+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}6" value="${sessionScope.Marks[6+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}7" value="${sessionScope.Marks[7+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}8" value="${sessionScope.Marks[8+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}9" value="${sessionScope.Marks[9+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}10" value="${sessionScope.Marks[10+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}11" value="${sessionScope.Marks[11+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}12" value="${sessionScope.Marks[12+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}13" value="${sessionScope.Marks[13+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}14" value="${sessionScope.Marks[14+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}15" value="${sessionScope.Marks[15+17*loop.index].mark}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}16" value="${sessionScope.Marks[16+17*loop.index].mark}" size="4"/></td>
              </tr>
            </c:forEach>
          </table>
        </p>

        <input id="button" type="SUBMIT" value="Зберегти"/>
      </form>

      <p class="new-line"></p>

      <form method="POST" action="TurnThePage">
        <input type="SUBMIT" value="<" name="Action">
        <span>Сторінка: ${sessionScope.NumberOfPage}</span>
        <input type="SUBMIT" value=">" name="Action">
      </form>
    </center>
  </body>
</html>
