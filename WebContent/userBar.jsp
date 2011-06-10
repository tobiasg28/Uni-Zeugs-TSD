<%@page import="java.util.List"%>
<%@ page import="dao.*,entities.*"%>
<jsp:useBean id="user" class="entities.User" scope="session" />
<div>
	<%
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
	%>
	<div>
		logged in as <a href="index.jsp?page=userAccount"><%= user.getUsername() %></a>
		<ul>
			<li><a href="#">messages</a>
			</li>
			<li><a href="LogoutServlet">logout</a>
			</li>
		</ul>
	</div>
	<div>
		<a href="index.jsp?page=maps">maps</a>
		<ul>
			<%
				UserDAO uDao = new UserDAO();
				user = uDao.get(user.getId());
				for (Participation participation : user.getParticipations()){
					%>
					<li><a href="index.jsp?page=map&id=<%= participation.getMap().getId() %>"><%= participation.getMap().getName() %></a>
					<%
				}
			%>
		</ul>
	</div>
	<%
		} else {
	%>
	<ul>
		<li><a href="index.jsp?page=login">login</a>
		</li>
		<li><a href="index.jsp?page=register">register</a>
		</li>
	</ul>
	<%
		}
	%>

</div>