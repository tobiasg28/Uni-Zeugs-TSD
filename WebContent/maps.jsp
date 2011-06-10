<%@page import="java.util.List"%>
<%@ page import="dao.*,entities.*"%>
<jsp:useBean id="user" class="entities.User" scope="session" />
<div>
	<%
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
	%>
	<div>
		<h2>create map</h2>
		<p>
			to create a new map click <a href="index.jsp?page=createMap">here</a>!
		</p>
	</div>
	<%
		if (user.getParticipations() != null
					&& !user.getParticipations().isEmpty()) {
	%>
	<div>
		<h2>participating maps</h2>
		<table>
			<tr>
				<td>map name</td>
				<td>free slots</td>
				<td>max players</td>
			</tr>
			<%
				for (Participation participation : user.getParticipations()) {
			%>
			<tr>
				<td><a
					href="index.jsp?page=map&id=<%=participation.getMap().getId()%>"><%=participation.getMap().getName()%></a>
				</td>
				<td><%=participation.getMap().getMaxUsers()
								- participation.getMap().getParticipations()
										.size()%></td>
				<td><%=participation.getMap().getMaxUsers()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<%
		}
			GameMapDAO dao = new GameMapDAO();
			List<GameMap> maps = dao.getAll();
			if (maps != null && !maps.isEmpty()) {
	%>
	<div>
		<h2>all maps</h2>
		<table>
			<tr>
				<td>map name</td>
				<td>free slots</td>
				<td>max players</td>
			</tr>
			<%
				for (GameMap map : maps) {
			%>
			<tr>
				<td><a href="index.jsp?page=map&amp;id=<%=map.getId()%>"><%=map.getName()%></a>
				</td>
				<td><%=map.getMaxUsers()
								- map.getParticipations().size()%></td>
				<td><%=map.getMaxUsers()%></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
	<%
		}
		} else {
	%>
	<p>
		<a href="index.jsp?page=login">login</a> first!
	</p>
	<%
		}
	%>
</div>
