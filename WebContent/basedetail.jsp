<%@ page import="dao.*,entities.*,java.util.*,swag.*"%>
<jsp:useBean id="user" class="entities.User" scope="session" />

<%
	SquareDAO dao = new SquareDAO();

	Square square = dao.get(Long.parseLong(request.getParameter("id")));

	Participation player = null;
	for (Participation p : user.getParticipations()) {
		if (p.getMap().getId() == square.getMap().getId()) {
			player = p;
			break;
		}
	}
	
%>

<h3>
	<a href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"><%=square.getMap().getName()%></a>
	&rarr; <a href="index.jsp?page=square&amp;id=<%=square.getId() %>">Square <%= square.getId() %></a>
	&rarr; Base of <%=user.getUsername() %>
</h3>


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
	BuildingTypeDAO btdao = new BuildingTypeDAO();
	
	if (square.getBase().getParticipation().getParticipant().getId() != user.getId()) {
		out.println("<h3>You can't look into this base...</h3> <!--");
	}
%>

<%
	if (request.getAttribute("errorMsg") != null) {
			out.print(request.getAttribute("errorMsg"));
			out.print("<br><br>");
	}
%>

<ul>
	<li>Location: <strong><%=square.getMap().getName()%>
             (X: <%=square.getPositionX()%>,
              Y: <%=square.getPositionY()%>)</strong></li>
	<li>Privileged for:
<%
	if (square.getPrivilegedFor() != null) {
		out.print("<strong>"+square.getPrivilegedFor().getName()+"</strong>");
	} else {
		out.println("<em>nothing</em>");
	}
%>
    </li>
    <li>Buildings: <strong><%= square.getBase().getBuildings().size() %> of 4</strong></li>
	</ul>
	
<h4>Buildings in this base</h4>
<table border="1">
	<%
		for (Building b : square.getBase().getBuildings()) {
			out.print("<tr>");
			out.print("<td>");
			out.print(b.getType().getName());
			out.print(" (Level " + b.getUpgradeLevel() + ")");
			out.print("</td>");
			out.print("<td>");
			if (b.getLevelUpgradeFinish() == null) {
				out.print("<a href=\"BuildingServlet?id=" + square.getId()
						+ "&amp;action=upgrade" + "&amp;bid=" + b.getId()
						+ "\">Upgrade</a> ("
						+ b.getType().getUpgradeCost().getAmount() + " "
						+ b.getType().getUpgradeCost().getResource().getName()
						+ ")");
			} else {
				out.print("currently upgrading...");
			}
			out.print("</td>");
			out.print("</tr>");
		}
	%>
</table>


<%
	if (square.getBase().getBuildings().size() < 4) {
%>
<h4>Create a new building</h4>
<table border="1">
	<%
		HashMap<String, Integer> buildingCount = new HashMap<String, Integer>();
			for (BuildingType bt : btdao.getAll()) {
				buildingCount.put(bt.getName(), 0);
			}
			for (Building b : square.getBase().getBuildings()) {
				if (buildingCount.containsKey(b.getType().getName())) {
					int value = buildingCount.get(b.getType().getName());
					buildingCount.put(b.getType().getName(), ++value);
				}
			}
			for (BuildingType bt : btdao.getAll()) {
				out.print("<tr>");
				out.print("<td>" + bt.getName() + "</td)>");
				out.print("<td><a href=\"BuildingServlet?id="
						+ square.getId() + "&amp;action=add&amp;bt="
						+ bt.getId() + "\">Add</a> ("
						+ bt.getInitialCost().getAmount() + " "
						+ bt.getInitialCost().getResource().getName()
						+ ")</td></tr>");
			}
	%>
</table>
<%
	}
%>


<%
	if (square.getBase().getParticipation().getParticipant().getId() != user.getId()) {
		out.println("-->");
	}
%>


<jsp:include page="notification.jsp" />
