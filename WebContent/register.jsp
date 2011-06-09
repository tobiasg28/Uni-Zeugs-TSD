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
		username: <input type="text" name="username"
			value="<%if (request.getAttribute("username") != null)
					out.write((String) request.getAttribute("username"));%>" />
	</p>
	<p>
		password: <input type="password" name="password"
			value="<%if (request.getAttribute("password") != null)
					out.write((String) request.getAttribute("password"));%>" />
	</p>
	<p>
		first name: <input type="text" name="firstname"
			value="<%if (request.getAttribute("firstname") != null)
					out.write((String) request.getAttribute("firstname"));%>" />
	</p>
	<p>
		last name: <input type="text" name="lastname"
			value="<%if (request.getAttribute("lastname") != null)
					out.write((String) request.getAttribute("lastname"));%>" />
	</p>
	<p>
		adress: <input type="text" name="adress"
			value="<%if (request.getAttribute("adress") != null)
					out.write((String) request.getAttribute("adress"));%>" />
	</p>
	<p>
		<input type="submit" value="register" />
	</p>
</form>
<%
	}
%>
