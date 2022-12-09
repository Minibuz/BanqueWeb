<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@page import="javaBeans.BOperations" %>
<%@page import="java.util.List" %>
<meta charset="UTF-16">
<title>AAAAAAAAAA</title>
</head>
<body>
<div>Test</div>
<% BOperations obj = (BOperations) request.getSession().getAttribute("BOperation"); %>
		<table>
    		<thead>
        		<tr>
            		<th>No de compte</th>
            		<th>Nom</th>
            		<th>Prenom</th>
            		<th>Solde</th>
        		</tr>
    		</thead>
    		<tbody>
        		<tr>
            		<td><%= obj.getNoDeCompte() %></td>
            		<td><%= obj.getNom() %></td>
            		<td><%= obj.getPrenom() %></td>
            		<td><%= obj.getSolde() %></td>
        		</tr>
    		</tbody>
		</table>
		
		<% if (!obj.getOperationsParDates().isEmpty()) { %>
		<table>
			<thead>
				<th>Date</th>
				<th>Opération</th>
				<th>Valeur</th>
			</thead>
			<% 
			List<String> op = obj.getOperationsParDates();
			for(int i = 0; i < op.size(); i++) {
				String element = op.get(i);
				String[] elements = element.split(" ");
			%>
				<tr>
					<td><%= elements[0] %></td>
					<td><%= elements[1] %></td>
					<td><%= elements[2] %></td>
				</tr>
			<% } %>
		</table>
		<% } else { %>
			<div> Il n'y a aucun résultat </div>
		<% } %>
		
		<form action="http://localhost:8080/BanqueWeb/GestionOperations">
    		<input type="submit" value="Return" />
		</form>
</body>
</html>