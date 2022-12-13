<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@page import="javaBeans.BOperations" %>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table, td, th {  
  border: 1px solid #ddd;
  text-align: center;
}

.container {
  display: flex; /* or inline-flex */
  flex-direction: row;
  width: 
}
</style>
</head>
<body>
	<header>
	</header>
	
	<main>
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
        		<% BOperations obj = (BOperations) request.getSession().getAttribute("BOperation"); %>
            		<td><%= obj.getNoDeCompte() %></td>
            		<td><%= obj.getNom() %></td>
            		<td><%= obj.getPrenom() %></td>
            		<td><%= obj.getSolde() %></td>
        		</tr>
    		</tbody>
		</table>
		<form action="http://localhost:8080/BanqueWeb/GestionOperations" method="post">
		<table>
    		<thead>
        		<tr>
            		<th colspan="4">Opération à effectuer</th>
            		<th colspan="4">Valeur</th>
        		</tr>
    		</thead>
    		<tbody>
        		<tr>
        			<% if (request.getSession().getAttribute("errorTraiter") != null) { %>
        				<% if (obj.getOp() == "+") { %>
            				<td colspan="2">(+)<input type="radio" id="plus" name="op" value="+" checked></td>
							<td colspan="2"><input type="radio" id="moins" name="op" value="-">(-)</td>
						<% } else { %>
							<td colspan="2">(+)<input type="radio" id="plus" name="op" value="+"></td>
							<td colspan="2"><input type="radio" id="moins" name="op" value="-" checked>(-)</td>
						<% } %>
						<td colspan="4">
							<div class="container">
								<div>
									<input style="text-align: end;" type="text" id="valeurEntiere" name="valeurEntiere" minlength="0" maxlength="10" size="10"
									value=<%= obj.getValeurEntiere() %>>
									</div>
								<div> .</div>
								<div>
									<input type="text" id="valeurDecimale" name="valeurDecimale" minlength="0" maxlength="2" size="2"
									value=<%= obj.getValeurDecimale() %>>
								</div>
							</div>
						</td>
						<td><button type="submit" name="button" value="traiter">Traiter</button></td>
					<% } else { %>
						<td colspan="2">(+)<input type="radio" id="plus" name="op" value="+" checked></td>
						<td colspan="2"><input type="radio" id="moins" name="op" value="-">(-)</td>
						<td colspan="4">
							<div class="container">
								<div>
									<input style="text-align: end;" type="text" id="valeurEntiere" name="valeurEntiere" minlength="0" maxlength="10" size="10">
								</div>
								<div> .</div>
								<div>
									<input type="text" id="valeurDecimale" name="valeurDecimale" minlength="0" maxlength="2" size="2">
								</div>
							</div>
						</td>
						<td><button type="submit" name="button" value="traiter">Traiter</button></td>
					<% } %>
        		</tr>
    		</tbody>
		</table>
		</form>
		<% if(request.getSession().getAttribute("errorTraiter") != null) {%>
				<%= request.getSession().getAttribute("errorTraiter") %>
		<% } else if (obj.getOp() != null ){ %>
				<div style="color: blue;">Opération réalisée: <%= obj.getOp() %> <%= obj.getValeur() %></div>
				<div style="color: blue;">Ancien solde: <%= obj.getAncienSolde() %></div>
				<div style="color: blue;">Nouveau solde: <%= obj.getNouveauSolde() %></div>
		<% } %>
		<form action="http://localhost:8080/BanqueWeb/GestionOperations" method="post">
		<table>
    		<thead>
    			<tr>
    				<th colspan="8">Liste des opérations réalisées</th>
    			</tr>
    		</thead>
    		<tbody>
    			<tr>
    				<td>
    					<div>du:</div>
    				</td>
    				<td>
    					<select name="jInit" id="jInit">
    					<% for(int i = 1; i < 32; i++) { %>
    						<% if(i < 10) { %>
    							<option value=" 0 + <%= i %>">0<%= i %></option>
    						<% } else { %>
    							<option value=<%= i %>><%= i %></option>
    						<% } %>
    					<% } %>
    					</select>
    				</td>
    				<td>
    					<select name="mInit" id="mInit">
    					<% for(int i = 1; i < 13; i++) { %>
    						<% if(i < 10) { %>
    							<option value=" 0 + <%= i %>">0<%= i %></option>
    						<% } else { %>
    							<option value=<%= i %>><%= i %></option>
    						<% } %>
    					<% } %>
    					</select>
    				</td>
    				<td>
    					<select name="aInit" id="aInit">
    					<% for(int i = 2022; i > 2017; i--) { %>
    						<option value=<%= i %>><%= i %></option>
    					<% } %>
    					</select>
    				</td>
    				<td>
    					<div>au:</div>
    				</td>
    				<td>
    					<select name="jFinal" id="jFinal">
    					<% for(int i = 1; i < 32; i++) { %>
    						<% if(i < 10) { %>
    							<option value=" 0 + <%= i %>">0<%= i %></option>
    						<% } else { %>
    							<option value=<%= i %>><%= i %></option>
    						<% } %>
    					<% } %>
    					</select>
    				</td>
    				<td>
    					<select name="mFinal" id="mFinal">
    					<% for(int i = 1; i < 13; i++) { %>
    						<% if(i < 10) { %>
    							<option value=" 0 + <%= i %>">0<%= i %></option>
    						<% } else { %>
    							<option value=<%= i %>><%= i %></option>
    						<% } %>
    					<% } %>	
    					</select>
    				</td>
    				<td>
    					<select name="aFinal" id="aFinal">
    					<% for(int i = 2022; i > 2017; i--) { %>
    						<option value=<%= i %>><%= i %></option>
    					<% } %>
    					</select>
    				</td>
    				<td>
    					<div><button type="submit" name="button" value="demande">Extraire la liste</button></div>
    				</td>
    			<tr>
    		</tbody>
		</table>
		</form>
		<% if(request.getSession().getAttribute("errorDemande") != null) {%>
				<%= request.getSession().getAttribute("errorDemande") %>
		<% } %>
		<% if(request.getSession().getAttribute("errorDate") != null) {%>
				<%= request.getSession().getAttribute("errorDate") %>
		<% } %>
		<form action="http://localhost:8080/BanqueWeb/GestionOperations" method="post">
    		<input type="submit" name="button" value="end">
		</form>
	</main>
	
	<footer>
	</footer>
</body>
</html>