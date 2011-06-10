<%@ page import="dao.*,entities.*" %>
<jsp:useBean id="user" class="entities.User" scope="session" />
<div>
	<%
		if (session.getAttribute("loggedIn") != null && (Boolean)session.getAttribute("loggedIn")) {
	%>
	<div>
		Logged in as <a href="#">${user.getUsername()}</a>
		<ul>
			<li><a href="#">messages</a></li>
			<li><a href="LogoutServlet">logout</a></li>
		</ul>
	</div>
	<div>
		<a href="index.jsp?page=maps">maps</a>
		<ul><li><em><strong>FIXME:</strong> hier nur maps zeigen, in der man eine participation hat</em></li>
		<%
		
		GameMapDAO dao = new GameMapDAO();
		for (GameMap map : dao.getAll()) {
			%>
			
			<li><a href="index.jsp?page=map&amp;id=<%= map.getId() %>"><%= map.getName() %></a></li>
			
			<%
		}
		
		%>		</ul>
	</div>
	<%
		} else {
	%>
	<ul>
		<li><a href="index.jsp?page=login">login</a></li>
		<li><a href="index.jsp?page=register">register</a></li>
	</ul>
	<%
		}
	%>

</div>