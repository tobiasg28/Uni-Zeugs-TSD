<jsp:useBean id="user" class="entities.User" scope="session" />
<%@ page import="dao.*,entities.*,java.util.*,swag.*"%>

<%
	GameMapDAO dao = new GameMapDAO();
	GameMap map = dao.get(Long.parseLong(request.getParameter("id")));
%>

<h1>
	<%=map.getName()%> (id=<%=map.getId()%>)
</h1>

<style type="text/css">
.swagsquare {
	position: absolute;
	display: block;
	width: <%=Constants.SQUARE_SIZE%>     px;
	height: <%=Constants.SQUARE_SIZE%>     px;
	background-color: #790;
	border: 1px black solid;
	text-decoration: none;
	color: white;
	text-align: right;
	font-weight: bold;
	background-repeat: no-repeat;
}

.swagsquare:hover {
	background-color: #ab0;
}

.swagsquare span {
	padding: 5px;
}

.Gold {
	background-image: url(images/gold.png);
}

.Wood {
	background-image: url(images/wood.png);
}

.Food {
	background-image: url(images/meat.png);
}

.Stone {
	background-image: url(images/stone.png);
}
</style>
<div class="map_participation">
	<%
		int free = 0;
		boolean already = false;
		List<Participation> p = map.getParticipations();
		
		if (p != null) {
			for (Participation pa : p){
				if (user.getUsername().equals(pa.getParticipant().getUsername())){
					already = true;
				}
			}
			free = map.getMaxUsers() - p.size();
		} else {
			free = map.getMaxUsers();
		}

		if (already){
			out.print("already participating");
		}
		else if (free > 0) {
	%>
	<p><%= free %> free game slots. do you want to participate?</p>
	<form method="post" action="ParticipateServlet">
		<input type="hidden" name="id" value="<%= map.getId() %>" />
		<p><input type="submit" value="participate"></p>
	</form>
	<%
		} else {
	%>
	<p>no free participation slots</p>
	<%
		}
	%>
</div>
<div class="map_squares" style="position: relative;">
	<%
		int maxY = 0;
		int maxX = 0;

		for (Square square : map.getSquares()) {
			String privileged = "";
			Resource resource = square.getPrivilegedFor();
			if (resource != null) {
				privileged = resource.getName();
			}
			out.println("<a href=\"?page=square&amp;id=" + square.getId()
					+ "\" class=\"swagsquare " + privileged
					+ "\" style=\"left: "
					+ (100 + square.getPositionX() * Constants.SQUARE_SIZE)
					+ "px; top: "
					+ (50 + square.getPositionY() * Constants.SQUARE_SIZE)
					+ "px;\"><span>" + square.getId() + "</span></a>");
			if (square.getPositionY() > maxY) {
				maxY = square.getPositionY();
			}
			if (square.getPositionX() > maxX) {
				maxX = square.getPositionX();
			}
		}
	%>
</div>

<%
	// Vertikaler Spacer fuer die obige Square-Ansicht, die ja relativ positioniert ist
	out.println("<div style=\"height: "
			+ (Constants.SQUARE_SIZE * maxY + 200) + "px; width: "
			+ (Constants.SQUARE_SIZE * maxX + 100) + "px;\"> </div>");
%>
