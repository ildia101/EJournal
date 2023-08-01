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
              <td><input type="text" name="date0" value="${Dates[0]}" size="4"/></td>
              <td><input type="text" name="date1" value="${Dates[1]}" size="4"/></td>
              <td><input type="text" name="date2" value="${Dates[2]}" size="4"/></td>
              <td><input type="text" name="date3" value="${Dates[3]}" size="4"/></td>
              <td><input type="text" name="date4" value="${Dates[4]}" size="4"/></td>
              <td><input type="text" name="date5" value="${Dates[5]}" size="4"/></td>
              <td><input type="text" name="date6" value="${Dates[6]}" size="4"/></td>
              <td><input type="text" name="date7" value="${Dates[7]}" size="4"/></td>
              <td><input type="text" name="date8" value="${Dates[8]}" size="4"/></td>
              <td><input type="text" name="date9" value="${Dates[9]}" size="4"/></td>
              <td><input type="text" name="date10" value="${Dates[10]}" size="4"/></td>
              <td><input type="text" name="date11" value="${Dates[11]}" size="4"/></td>
              <td><input type="text" name="date12" value="${Dates[12]}" size="4"/></td>
              <td><input type="text" name="date13" value="${Dates[13]}" size="4"/></td>
              <td><input type="text" name="date14" value="${Dates[14]}" size="4"/></td>
              <td><input type="text" name="date15" value="${Dates[15]}" size="4"/></td>
              <td><input type="text" name="date16" value="${Dates[16]}" size="4"/></td>
            </tr>
            <c:forEach items="${sessionScope.StudentsFromThisClass}" var="ThisStundent" varStatus="loop">
              <tr>
                <td><p>${ThisStundent}</p></td>
                <td><input type="text" name="grade${loop.index}0" value="${Marks[loop.index][0]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}1" value="${Marks[loop.index][1]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}2" value="${Marks[loop.index][2]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}3" value="${Marks[loop.index][3]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}4" value="${Marks[loop.index][4]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}5" value="${Marks[loop.index][5]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}6" value="${Marks[loop.index][6]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}7" value="${Marks[loop.index][7]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}8" value="${Marks[loop.index][8]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}9" value="${Marks[loop.index][9]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}10" value="${Marks[loop.index][10]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}11" value="${Marks[loop.index][11]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}12" value="${Marks[loop.index][12]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}13" value="${Marks[loop.index][13]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}14" value="${Marks[loop.index][14]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}15" value="${Marks[loop.index][15]}" size="4"/></td>
                <td><input type="text" name="grade${loop.index}16" value="${Marks[loop.index][16]}" size="4"/></td>
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
