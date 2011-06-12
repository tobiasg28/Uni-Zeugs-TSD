<%@page import="backend.Util"%>
<%@page import="frontend.ActualGameStep"%>
<%@page import="frontend.BackendConnection"%>
<%@page import="storage.DAOImpl"%>
<jsp:useBean id="user" class="entities.User" scope="session" />
<%@ page import="dao.*,entities.*,java.util.*,swag.*"%>
<%
	GameMapDAO dao = new GameMapDAO();
	GameMap map = dao.get(Long.parseLong(request.getParameter("id")));
	DAOImpl.getInstance().getEntityManager().refresh(map); // error when troops are deleted
%>

<h3>
	<%=map.getName()%>
</h3>

<style type="text/css">
.swagsquare {
	position: absolute;
	display: block;
	width: <%=Constants.SQUARE_SIZE%>px;
	height: <%=Constants.SQUARE_SIZE%>px;
	background-color: #790;
	border: 1px white solid;
	text-decoration: none;
	color: white;
	text-align: right;
	font-weight: bold;
	background-repeat: no-repeat;
}

.swagsquare:hover {
	border-color: black;
	z-index: 10;
}

.swagsquare span {
	padding: 5px;
}

.Gold {
	background-image: url(images/gold.png);
	background-color: goldenrod;
}

.Wood {
	background-image: url(images/wood.png);
	background-color: saddlebrown;
}

.Food {
	background-image: url(images/meat.png);
	background-color: red;
}

.Stone {
	background-image: url(images/stone.png);
	background-color: slategrey;
}

img {
    border: 0px;
}

</style>
<div class="map_participation">
	<%
		int free = 0;
		boolean already = false;
		List<Participation> p = map.getParticipations();
		Participation player = null;

		if (p != null) {
			for (Participation pa : p) {
				if (user.getUsername().equals(
						pa.getParticipant().getUsername())) {
					already = true;
					player = pa;
				}
			}
			free = map.getMaxUsers() - p.size();
		} else {
			free = map.getMaxUsers();
		}

		if (already) {
    %>
 
 	<!--  begin resources table  -->
    <% List<ResourceAmount> availableRes = player.getResources(); %>		
	<table class="resources"><tr class="names">
	<th rowspan="2">Resources &raquo;</th>
	<% for (ResourceAmount ra : availableRes) { %>
	<td><%= ra.getResource().getName() %></td>
	<% } %>
	</tr><tr class="values">
	<% for (ResourceAmount ra : availableRes) { %>
	<td><%= ra.getAmount() %></td>
	<% } %>
	</tr></table>
	<!-- end resources table -->
	
	<%
		if (request.getAttribute("action") == null) {
	%>
	<p>click on a square and choose some action!</p>
	<%
		} else if (request.getAttribute("action").equals("move")) {
	%>
	<p>
		<big>click on a square on which you want to move your troop</big>
	</p>
	<%
		}
	%>
	<%
		} else if (free > 0) {
	%>
	<p><%=free%>
		free game slots. do you want to participate?
	</p>
	<form method="post" action="ParticipateServlet">
		<input type="hidden" name="id" value="<%=map.getId()%>" />
		<p>
			<input type="submit" value="participate">
		</p>
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
			List<String> tooltip = new LinkedList<String>();
			tooltip.add("Square " + square.getId());
			String privileged = "";
			Resource resource = square.getPrivilegedFor();
			String url = "index.jsp?page=square&amp;id=";
			if (resource != null) {
				privileged = resource.getName();
				tooltip.add("Privileged for "+privileged);
			}

			if (request.getAttribute("action") != null
					&& request.getAttribute("action").equals("move")) {
				url = "TroopServlet?action=move&to=0&tid="
						+ (Long) request.getAttribute("tid") + "&id=";
			}

			String base = "";
			if (square.getBase() != null && square.getBase().getDestroyed() == null) {
				String username = square.getBase().getParticipation().getParticipant().getUsername();
				if (square.getBase().getStarterBase()) {
					base = "<img src='map/starterbase.png' title='"+username+"'>";
					tooltip.add("Starter base of " + username);
				} else {
					base = "<img src='map/base.png' title='"+username+"'>";
					tooltip.add("Base of " + username);
				}
			}

			String troops = "";
			int alivecount = 0;
			for (Troop troop : square.getTroops()) {
				if (troop.getCreated() != null) {
					String username = troop.getParticipation().getParticipant()
								.getUsername();
					
					troops = "<img src='map/troop.png' title='"+username+"'>";
					tooltip.add("Troop of " + username);
					break;
				}
			}

			out.println("<a href=\"" + url + square.getId()
					+ "\" class=\"swagsquare " + privileged
					+ "\" style=\"left: "
					+ (square.getPositionX() * Constants.SQUARE_SIZE)
					+ "px; top: "
					+ (square.getPositionY() * Constants.SQUARE_SIZE)
					+ "px;\" title=\""+Util.join(tooltip)+"\"><span>" + square.getId() + "&nbsp; <center>" + base + troops
					+ "</center>"
					+ "</span></a>");
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
<jsp:include page="notification.jsp" />