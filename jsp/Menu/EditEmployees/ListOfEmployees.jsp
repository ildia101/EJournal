<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Список співробітників</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css" />
  </head>

  <body>
    <a href="${pageContext.request.contextPath}/jsp/Menu">Повернутися</a>
    <center>
      <c:if test="${SomeInfo == true}">
        <p id="error"><c:out value="${Info}" /></p>
        <span class="new-line"></span>
      </c:if>

      <form method="POST" action="${pageContext.request.contextPath}/jsp/Menu/SaveEmployees">
        <p class="input-background">
          <span class="text">Список співробітників: </span>
          <table>
            <c:forEach items="${requestScope.ListOfEmployees}" var="ThisEmployee" varStatus="loop">
              <tr>
                <td><p>${loop.count}</p></td>
                <td><p>${ThisEmployee}</p></td>
                <input type="hidden" name="employee${loop.index}" value="${ThisEmployee}" />
                <td>
                  <select name="JobTitle${loop.index}" size="1">
                    <c:choose>
                      <c:when test="${requestScope.ListOfRoles[loop.index] == sessionScope.Principal}">
                        <option value="principal" selected>Директор</option>
                        <option value="tutor">Класний керівник</option>
                        <option value="teacher">Вчитель</option>
                      </c:when>
                      <c:when test="${requestScope.ListOfRoles[loop.index] == sessionScope.Tutor}">
                        <option value="principal">Директор</option>
                        <option value="tutor" selected>Класний керівник</option>
                        <option value="teacher">Вчитель</option>
                      </c:when>
                      <c:when test="${requestScope.ListOfRoles[loop.index] == sessionScope.Teacher}">
                        <option value="principal">Директор</option>
                        <option value="tutor" >Класний керівник</option>
                        <option value="teacher" selected>Вчитель</option>
                      </c:when>
                    </c:choose>
                  </select>
                </td>
                <td><input type="SUBMIT" name="Delete${loop.index}" value="Видалити" id="button"/></td>
              </tr>
            </c:forEach>
          </table>
        </p>

        <input type="SUBMIT" name="SaveChanges" value="Зберегти" id="button"/>
      </form>
    </center>
  </body>
</html>
