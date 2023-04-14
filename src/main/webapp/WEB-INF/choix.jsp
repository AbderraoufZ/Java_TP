<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Choix </title>
	</head>
	<body>
		<p><a href="Deconnexion"> Deconnexion </a></p>
		<h3>Bonjour ${ personne }</h3>
		<form method="post" action="Choix">
			<p>
				<label id="sites">Sites : </label>
				<select id="sites" name="sitesList">
					<c:forEach var="elem" items = "${ sitesList }">
						<option value="${ elem.getId() }">${ elem.getName() }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<input type="submit" value="Valider" />
			</p>
		</form>

		<ul>
			<c:forEach var="elem" items = "${ abonners }">
				<li><b>${ elem.getPersonne().getLogin() }</b> est abonné sur le site : <b>${ elem.getSites().getName() }</b></li>
			</c:forEach>
		</ul>
	</body>
</html>