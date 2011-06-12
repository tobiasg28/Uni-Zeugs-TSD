<%@ page import="dao.*,entities.*,java.util.*,swag.*"%>
<jsp:useBean id="user" class="entities.User" scope="session" />

<%
	SquareDAO dao = new SquareDAO();

	Square square = dao.get(Long.parseLong(request.getParameter("id")));

	BuildingTypeDAO btdao = new BuildingTypeDAO();
%>

<h1><%=user.getUsername() %>'s Base</h1>

Location: 	<%=square.getMap().getName()%> (X: <%=square.getPositionX()%>/Y: <%=square.getPositionY()%>)<br/>
Privileged for: <%
		if (square.getPrivilegedFor() != null) {
			String name = square.getPrivilegedFor().getName().toLowerCase();
			if (name.equals("food"))
				name = "meat"; // Yep, DIRTY HACK!
			out.println("<img src=\"images/big/" + name + ".png\"");
		} else {
			out.println("<em>nothing</em>");
		}
%><br/>
<br/>

Resource Count:
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
<br />
Buildings:
<% if(request.getAttribute("errorMsg") != null) 
	out.print(request.getAttribute("errorMsg"));
%>
<br />
<table border="1">
	<%
		HashMap<String, Integer> buildingCount = new HashMap<String, Integer>();
		for(BuildingType bt: btdao.getAll()) {
			buildingCount.put(bt.getName(), 0);
		}
		for(Building b : square.getBase().getBuildings()) {
			if(buildingCount.containsKey(b.getType().getName())) {
				int value = buildingCount.get(b.getType().getName());
				buildingCount.put(b.getType().getName(), ++value);
			}
		}
		for (BuildingType bt : btdao.getAll()) {
			out.print("<tr>");
			out.print("<td>" + bt.getName() + " (" + buildingCount.get(bt.getName()) + "/4)</td)>");
			out.print("<td><a href=\"BuildingServlet?id="
					+ square.getId() + "&amp;action=add&amp;bt=" + bt.getId()
					+ "\">Add</a> (" + bt.getInitialCost().getAmount()
					+ " " + bt.getInitialCost().getResource().getName()
					+ ")</td>");
			out.print("<td><a href=\"BuildingServlet?id="
					+ square.getId() + "&amp;upgrade=" + bt.getId()
					+ "\">Upgrade</a> (" + bt.getUpgradeCost().getAmount()
					+ " " + bt.getUpgradeCost().getResource().getName()
					+ ")</td></tr>");
		}
	%>
</table>

<div>go back to <a
	href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"><%=square.getMap().getName()%></a>
</div>