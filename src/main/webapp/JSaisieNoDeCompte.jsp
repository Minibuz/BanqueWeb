<%@ page language="java" contentType="text/html; charset=UTF-16"
    pageEncoding="UTF-16"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-16">
<title>Saisie d'un N° de Compte</title>
</head>
<body>
	<header>
	</header>
	<main>
		<h1 style="text-align: center; ">Saisie du N° de compte</h1>
		<div style="text-align: center; ">
			<form action="http://localhost:8080/BanqueWeb/GestionOperations" method="post">
				<div>
    				<label>Entrez le N° de compte: </label>
    				<% if (request.getSession().getAttribute("noCompte") == null) { %>
   						<input type="text" name="noDeCompte" id="noCompte" required>
   					<%} else {%>
   						<input type="text" name="noDeCompte" id="noCompte" value=<%= request.getSession().getAttribute("noCompte") %> required>
   					<%}%>
  				</div>
  				<div>
    				<input type="submit" name="button" value="consulter">
  				</div>
			</form>
			<% if(request.getSession().getAttribute("errorConsulter") != null) {%>
				<%= request.getSession().getAttribute("errorConsulter") %>
			<% } %>
		</div>
	</main>
	<footer>
	</footer>
</body>
</html>