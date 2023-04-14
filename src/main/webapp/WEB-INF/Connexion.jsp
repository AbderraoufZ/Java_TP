<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
</head>
<body>
<jsp:include page="menu.jsp"></jsp:include>


<form method="post" action="/tp4/Connexion">
      <p>
        <label for="login">Login : </label>
        <input type="text" name="login" id="login" tabindex="10" />
      </p>
      <p>
        <label for="password">Password : </label>
        <input type="text" name="password" id="password" tabindex="20" />
      </p>
      
      <p>
        <input type="submit" value="Valider" />
        
      </p>
    </form>
    <p>${ form.resultat }</p>
    <p>
    <c:forEach var="erreur" items = "${ form.erreurs }">
    <ul>
    	<li><c:out value="${ erreur.value }"></c:out></li>
    </ul>
    </c:forEach>
    </p>
</body>
</html>