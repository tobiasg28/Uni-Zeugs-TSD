<%@ page import="dao.*,entities.*" %>
<div>
	<%
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
	%>
	<div>
		<h2>create map</h2>
		<p>to create a new map click <a href="index.jsp?page=createMap">here</a>!</p>
	</div>
	<div>
		<h2>participating maps</h2>
		<%
		/**ParticipationDAO pdao = new ParticipationDAO();
		for(Participation participation : pdao.getAll()){
			if (participation.getParticipant().getUsername().equals()
		}*/
		
		%>
	</div>
	<div>
		<h2>existing maps:</h2>
		<ul>
		<%
		
		GameMapDAO dao = new GameMapDAO();
		for (GameMap map : dao.getAll()) {
			%>
			
			<li><a href="index.jsp?page=map&amp;id=<%= map.getId() %>"><%= map.getName() %></a></li>
			
			<%
		}
		
		%>
		</ul>
	</div>
	<%
		} else {
	%>
	<p><a href="index.jsp?page=login">login</a> first!</p>
	<%
		}
	%>
</div>