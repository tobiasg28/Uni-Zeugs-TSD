<%@ page import="dao.*,entities.*,java.util.*,swag.*" %>

<%

SquareDAO dao = new SquareDAO();

Square square = dao.get(Long.parseLong(request.getParameter("id")));

%>

<h1>SQUARE details for: <%= square.getId() %></h1>

<ul>
<li>Position X: <%= square.getPositionX() %></li>
<li>Position Y: <%= square.getPositionY() %></li>
<li>Privileged for: <%

if (square.getPrivilegedFor() != null) {
	String name = square.getPrivilegedFor().getName().toLowerCase();
	if (name.equals("food")) name = "meat"; // Yep, DIRTY HACK!
	out.println("<img src=\"images/big/" + name + ".png\"");
} else {
	out.println("<em>nothing</em>");
}

%></li>

</ul>

This is a square on <a href="?page=map&amp;id=<%= square.getMap().getId() %>">map <%=square.getMap().getId() %></a>
