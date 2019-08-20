<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
	<h2>Some Title</h2>
	<hr>
	
	<p>Welcome to my home</p>
	
	<hr>
	
	<!-- DISPLAY USERNAME AND ROLE -->
	User: <security:authentication property="principal.username"/>
		<br><br>
	Role(s): <security:authentication property="principal.authorities"/>
	
	<hr>
	
	<security:authorize access="hasRole('MANAGER')"><!-- U HOME PAGE-U OVO CE VIDETI SAMO ONAJ KO SE ULOGOVAO SA ROLOM MANAGERA -->
		<!-- Dodajem link da ukazuje na /leaders ... ovo je za "managers"(rolu) -->
		<p>
			<!-- OVO NAS SALJE NA /leaders U KONTROLER KLASU ControllerDemo -->
			<a href="${pageContext.request.contextPath}/leaders">LeaderShip Meeting</a>
			(Only for Manager peeps(people))
		</p>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')"><!-- U HOME PAGE-U OVO CE VIDETI SAMO ONAJ KO SE ULOGOVAO SA ROLOM ADMINA -->
		<!-- Dodajem link da ukazuje na /system ... ovo je za "admin"(rolu) -->
		<p>
			<!-- OVO NAS SALJE NA /leaders U KONTROLER KLASU ControllerDemo -->
			<a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a>
			(Only for Admin peeps(people))
		</p>
	</security:authorize>
	
	
	<!-- Add a logout button MORA BITI U OBLIKU form:form JER TAKO MORA DA BIH SUBMITOVAO I DA BI ME POSLAO NA NPR LOGIN STRANICU -->
	<form:form action="${pageContext.request.contextPath}/logout" method="POST">
		<input type="submit" value="LogOut" />
	</form:form>
	
</body>
</html>