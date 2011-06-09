<div>
	<%
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
	%>
	<h2>create map:</h2>
	<%
		if (request.getAttribute("error") != null
					&& (Boolean) request.getAttribute("error")) {
	%>
	<p><%=request.getAttribute("errorMsg")%></p>
	<%
		}
	%>
	<form method="post" action="CreateMapServlet">
		<p>
			map name: <input type="text" name="mapname"
				value="<%if (request.getAttribute("mapname") != null)
					out.write((String) request.getAttribute("mapname"));%>" />
		</p>
		<p>
			max players: <input type="text" name="maxplayers"
				value="<%if (request.getAttribute("maxplayers") != null)
					out.write((String) request.getAttribute("maxplayers"));%>" />
		</p>
		<p>
			<input type="submit" value="create map" />
		</p>
	</form>
	<%
		} else {
	%>
	<p>
		<a href="?page=login">login</a> first!
	</p>
	<%
		}
	%>
</div>