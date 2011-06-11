<%@ page import="dao.*,entities.*,java.util.*,swag.*"%>
<jsp:useBean id="user" class="entities.User" scope="session" />

<%
	SquareDAO dao = new SquareDAO();

	Square square = dao.get(Long.parseLong(request.getParameter("id")));
%>

<h1>
	SQUARE from <a
		href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"> <%=square.getMap().getName()%></a>
</h1>

<h3>your resources</h3>
<%
	for (Participation player : user.getParticipations()) {
		if (player.getMap().getId() == square.getMap().getId()) {
			for (ResourceAmount ra : player.getResources()) {
				out.print(ra.getResource().getName() + "("
						+ ra.getAmount() + ")   ");
			}
		}
	}
%>
<h3>characteristics</h3>
<ul>
	<li>Position X: <%=square.getPositionX()%></li>
	<li>Position Y: <%=square.getPositionY()%></li>
	<li>Privileged for: <%
		if (square.getPrivilegedFor() != null) {
			String name = square.getPrivilegedFor().getName().toLowerCase();
			if (name.equals("food"))
				name = "meat"; // Yep, DIRTY HACK!
			out.println("<img src=\"images/big/" + name + ".png\"");
		} else {
			out.println("<em>nothing</em>");
		}
	%>
	</li>
</ul>
<h3>actions</h3>
<ul>
	<li>Base: <%
		if (square.getBase() == null) {
	%> <a href="CreateBaseServlet?id=<%=square.getId()%>">Create Base</a> <%
 	} else {
 		out.println(square.getBase().getParticipation()
 				.getParticipant().getUsername());
 %> <%
 	}
 %> <%
 	if (request.getAttribute("errorMsg") != null) {
 %> <%=request.getAttribute("errorMsg")%> <%
 	}
 %>
	</li>

</ul>

<div>
	go back to <a
		href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"><%=square.getMap().getName()%></a>
</div>