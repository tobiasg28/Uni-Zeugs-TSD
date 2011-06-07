<jsp:useBean id="user" class="entities.User" scope="session" />
<%
	if (session.getAttribute("loggedIn") != null
			&& (Boolean) session.getAttribute("loggedIn")) {
%>
<h2>
	welcome <a href=""><%=user.getUsername()%></a>
</h2>
<%
	} else {
%>
<h2>login:</h2>
<%
	if (request.getAttribute("error") != null
				&& (Boolean) request.getAttribute("error")) {
%>
<p><%=request.getAttribute("errorMsg")%></p>
<%
	}
%>
<form method="post" action="LoginServlet">
	<p>
		username: <input type="text" name="username">
	</p>
	<p>
		password: <input type="password" name="password" />
	</p>
	<p>
		<input type="submit" value="login" />
	</p>
</form>
<%
	}
%>
