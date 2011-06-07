<jsp:useBean id="user" class="entities.User" scope="session"/>
<h2>Register:</h2>
<% if (user.getUsername() == null){%>
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
<% }else { %>
<p>You are already logged in as <%= user.getUsername() %>!</p>
<%  } %>
