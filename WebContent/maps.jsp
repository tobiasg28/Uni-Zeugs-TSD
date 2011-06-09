<div>
	<%
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
	%>
	<div>
		<h2>create map</h2>
		<p>to create a new map click <a href="?page=createMap">here</a>!</p>
	</div>
	<div>
		<h2>existing maps:</h2>
		<ul>
			<li>map1</li>
			<li>map2</li>
		</ul>
	</div>
	<%
		} else {
	%>
	<p><a href="?page=login">login</a> first!</p>
	<%
		}
	%>
</div>