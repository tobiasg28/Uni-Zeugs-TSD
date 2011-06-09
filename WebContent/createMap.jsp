<div>
	<%
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
	%>
	<h2>create map:</h2>
	<%
		} else {
	%>
	<p><a href="?page=login">login</a> first!</p>
	<%
		}
	%>
</div>