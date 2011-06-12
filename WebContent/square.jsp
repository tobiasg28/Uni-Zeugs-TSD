<%@page import="storage.DAOImpl"%>
<%@ page import="dao.*,entities.*,java.util.*,swag.*"%>
<jsp:useBean id="user" class="entities.User" scope="session" />

<%
	SquareDAO dao = new SquareDAO();
	Square square = dao.get(Long.parseLong(request.getParameter("id")));
	DAOImpl.getInstance().getEntityManager().refresh(square); // error when troops are deleted

	UserDAO uDao = new UserDAO();
	user = uDao.get(user.getId());
	DAOImpl.getInstance().getEntityManager().refresh(user); // error when troops are deleted

	String resources = "";
	for (Participation player : user.getParticipations()) {
		if (player.getMap().getId() == square.getMap().getId()) {
			for (ResourceAmount ra : player.getResources()) {
				resources += ra.getResource().getName() + "("
						+ ra.getAmount() + ")   ";
			}
		}
	}

	if (resources.equals("")) {
%>
<div>
	you are not participating on map <a
		href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"> <%=square.getMap().getName()%></a>
</div>
<%
	} else {
%>

<h1>
	SQUARE from <a
		href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"> <%=square.getMap().getName()%></a>
</h1>

<h3>your resources</h3>
<%=resources%>
<%
	
%>
<h3>characteristics</h3>
<ul>
	<li>Position x=<%=square.getPositionX()%>/y=<%=square.getPositionY()%></li>
	<li>Privileged for: <%
		if (square.getPrivilegedFor() != null) {
				String name = square.getPrivilegedFor().getName()
						.toLowerCase();
				if (name.equals("food"))
					name = "meat"; // Yep, DIRTY HACK!
				out.println("<img src=\"images/big/" + name + ".png\"");
			} else {
				out.println("<em>nothing</em>");
			}
	%>
	</li>
	<%
		if (square.getBase() != null) {
				String starterBase = "";
				if (square.getBase().getStarterBase()){
					starterBase = "starter ";
				}
				out.println("<li><a href=\"index.jsp?page=basedetail&id="
						+ square.getId()
						+ "\">"
						+ square.getBase().getParticipation()
								.getParticipant().getUsername()
						+ "'s " + starterBase + "base</a></li>");
			}

			List<Troop> userTroops = new ArrayList<Troop>();
			Map<User, Map<TroopType, Integer>> uTroops = new HashMap<User, Map<TroopType, Integer>>();
			for (Troop troop : square.getTroops()) {
				if (troop.getCreated() != null) {
					if (uTroops.get(troop.getParticipation()
							.getParticipant()) != null) {
						Map<TroopType, Integer> troops = uTroops.get(troop
								.getParticipation().getParticipant());
						if (troops.get(troop.getUpgradeLevel()) != null) {
							int i = troops.get(troop.getUpgradeLevel()) + 1;
							uTroops.remove(troops);
							troops.remove(troops.get(troop
									.getUpgradeLevel()));
							troops.put(troop.getUpgradeLevel(), i);
							uTroops.put(troop.getParticipation()
									.getParticipant(), troops);
						} else {
							uTroops.remove(troops);
							troops.put(troop.getUpgradeLevel(), 1);
							uTroops.put(troop.getParticipation()
									.getParticipant(), troops);
						}
					} else {
						Map<TroopType, Integer> troops = new HashMap<TroopType, Integer>();
						troops.put(troop.getUpgradeLevel(), 1);
						uTroops.put(troop.getParticipation()
								.getParticipant(), troops);
					}

					if (troop.getParticipation().getParticipant().getId() == user
							.getId()) {
						userTroops.add(troop);
					}
				}
			}

			Iterator<User> i = uTroops.keySet().iterator();
			while (i.hasNext()) {
				User u = (User) i.next();
				Map<TroopType, Integer> troops = uTroops.get(u);
				Iterator<TroopType> j = troops.keySet().iterator();
				while (j.hasNext()) {
					TroopType tt = (TroopType) j.next();
					int value = troops.get(tt);
					out.println("<li>" + u.getUsername() + "'s troop: "
							+ value + "x " + tt.getName() + "(s) (speed="
							+ tt.getSpeed() + ", strength="
							+ tt.getStrength() + ")</li>");
				}
			}
	%>
</ul>
<h3>actions</h3>
<ul>
	<%
		if (square.getBase() == null) {
	%>
	<li><a href="CreateBaseServlet?id=<%=square.getId()%>">Create
			Base</a>
	</li>

	<%
		}
			if (request.getAttribute("errorMsg") != null) {
	%>
	<%=request.getAttribute("errorMsg")%>
	<%
		}
			if (square.getBase() != null
					&& square.getBase().getParticipation().getParticipant()
							.getId() == user.getId()) {
	%>
	</li>
	<li><a href="TroopServlet?action=create&id=<%=square.getId()%>">create
			troop</a> <%
 	TroopTypeDAO ttDao = new TroopTypeDAO();
 			List<TroopType> tts = ttDao.getAll();
 			TroopType tt = tts.get(tts.size() - 1);
 			out.println("- " + tt.getName() + " (speed="
 					+ tt.getSpeed() + ", strength" + tt.getStrength()
 					+ ")" + "- costs: "
 					+ tt.getInitialCost().getResource().getName()
 					+ " (" + tt.getInitialCost().getAmount() + ")");
 %>
	</li>
	<%
		}
			for (Troop troop : userTroops) {
				out.println("<li>" + troop.getUpgradeLevel().getName()
						+ " (speed=" + troop.getUpgradeLevel().getSpeed()
						+ ", strength="
						+ troop.getUpgradeLevel().getStrength() + "):</li>");
				String movement = "";
				if (troop.getTargetSquare() != null) {
					movement += " (moving to x="
							+ troop.getTargetSquare().getPositionX()
							+ "/y="
							+ troop.getTargetSquare().getPositionY() + ")";
				}
				out.println("<ul><li><a href=\"TroopServlet?action=move&id="
						+ square.getId()
						+ "&tid="
						+ troop.getId()
						+ "\">move</a>" + movement + "</li>");

				if (troop.getUpgradeLevel().getNextLevel() != null) {
					out.println("<li><a href=\"TroopServlet?action=upgrade&id="
							+ square.getId()
							+ "&tid="
							+ troop.getId()
							+ "\">upgrade</a> "
							+ " to "
							+ troop.getUpgradeLevel().getNextLevel()
									.getName()
							+ " (speed="
							+ troop.getUpgradeLevel().getNextLevel()
									.getSpeed()
							+ ", strength="
							+ troop.getUpgradeLevel().getNextLevel()
									.getStrength()
							+ ") - costs: "
							+ troop.getUpgradeLevel().getUpgradeCost()
									.getResource().getName()
							+ " ("
							+ troop.getUpgradeLevel().getUpgradeCost()
									.getAmount() + ")</li>");
				}
				out.println("</ul>");
			}
	%>

</ul>

<div>
	go back to <a
		href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"><%=square.getMap().getName()%></a>
</div>
<%
	}
%>