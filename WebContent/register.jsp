<jsp:useBean id="user" class="entities.User" scope="session" />

<%
	if (session.getAttribute("loggedIn") != null
			&& (Boolean) session.getAttribute("loggedIn")) {
%>
<p>
	You are already logged in as
	<%=session.getAttribute("loggedIn")%>!
</p>
<%
	} else {
%>
<h2>register:</h2>
<%
	if (request.getAttribute("error") != null
				&& (Boolean) request.getAttribute("error")) {
%>
<p><%=request.getAttribute("errorMsg")%></p>
<%
	}
%>
<form method="post" action="RegisterServlet">
	<p>
		username: <input type="text" name="username">
	</p>
	<p>
		password: <input type="password" name="password" />
	</p>
	<p>
		first name: <input type="text" name="firstname">
	</p>
	<p>
		last name: <input type="text" name="lastname">
	</p>
	<p>
		adress: <input type="text" name="adress">
	</p>
	<p>
		<input type="submit" value="register" />
	</p>
</form>
<%
	}
%>
