<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="uk-UA">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Оберіть клас</title>

    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css" />
  </head>

  <body>
    <a href="${pageContext.request.contextPath}/jsp/Menu/AddInformation/">Повернутися</a>
    <div id="wrapper">
      <div id="input-field">
        <center>
          <form method="POST" action="${pageContext.request.contextPath}/jsp/Menu/AddInformation/EditClassroom">
            <p class="input-background">
              <span class="text">Оберіть клас:</span>
              <span class="new-line"></span>
              <select name="classroom" size="1">
                <option value="-" selected hidden>-</option>
                <c:forEach items="${sessionScope.Classes}" var="ThisClass">
                  <option value="${ThisClass}">${ThisClass}</option>
                </c:forEach>
              </select>
            </p>
            <br /><br />

            <input type="SUBMIT" value="Редагувати клас" id="button"/>
          </form>

          <c:if test="${Error == true}">
            <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
            <span class="new-line"></span>
            <p id="error"><c:out value="${InvalidData}" /></p>
          </c:if>
        </center>
      </div>
    </div>
  </body>
</html>
