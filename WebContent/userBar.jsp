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
		<a href="?page=maps">maps</a>
		<ul>
			<li><a href="#">map1</a>
			</li>
		</ul>
	</div>
	<%
		} else {
	%>
	<ul>
		<li><a href="?page=login">login</a></li>
		<li><a href="?page=register">register</a></li>
	</ul>
	<%
		}
	%>

</div>