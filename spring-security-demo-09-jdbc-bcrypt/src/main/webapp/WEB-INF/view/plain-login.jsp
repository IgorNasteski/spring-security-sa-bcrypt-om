<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- dodajem biblioteku jstl -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Custom Login Page</title>

	<style>
		.failed{color:red;}
	</style>

</head>
<body>

	<h3>My Custom Login Page</h3>

	<form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="POST">	<!-- IZ KLASE "DemoSecurityConfig" U KOJOJ SMO REKLI GDE CE
																	DA NAS SPRING ODVEDE NA LOGIN CIM POKRENEMO APP, TAMO SMO NAVELI DA CE
																	KADA SUBMITUJEMO FORMU action BITI "authenticateTheUser --- .loginProcessingUrl("/authenticateTheUser"). 
																	method UVEK MORA BITI "POST"-->
		<!-- CHECK FOR LOGIN ERROR -->
		<c:if test="${param.error != null}">
			<i class="failed">Sorry! You entered invalid username/password.</i>
		</c:if>
		
		<p>
			User Name: <input type="text" name="username" />	<!-- name mora biti "username" !!!! JER JEDINO CE SPRING SECURITY ODRADITI U TOM SLUCAJU JER JE name ZA USERNAME UVEK "username" -->
		</p>
		<p>
			Password: <input type="password" name="password" />	<!-- name mora biti "password" !!!! JER JEDINO CE SPRING SECURITY ODRADITI U TOM SLUCAJU JER JE name ZA PASSWORD UVEK "password" -->
		</p>
		
		<input type="submit" value="LogIn">
		
	</form:form>

</body>
</html>