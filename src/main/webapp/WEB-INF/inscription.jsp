<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Inscription</title>
</head>
<body>
	<jsp:include page="menu.jsp"></jsp:include>
	<p>${infoLog}</p>
	<form method="post" action="/tp4/Inscription">
      <p>
        <label for="login">Login : </label>
        <input type="text" name="login" id="login" tabindex="10" />
      </p>
      <p>
        <label for="password">Password : </label>
        <input type="text" name="password" id="password" tabindex="20" />
      </p>
       <p>
        <label for="password">Confirmation Password : </label>
        <input type="text" name="confPassword" id="password" tabindex="20" />
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