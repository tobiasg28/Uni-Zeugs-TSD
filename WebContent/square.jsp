<%@page import="backend.Util"%>
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

	Participation player = null;
	for (Participation p : user.getParticipations()) {
		if (p.getMap().getId() == square.getMap().getId()) {
			player = p;
			break;
		}
	}

	if (player == null) {
%>
<div>
	you are not participating on map <a
		href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"> <%=square.getMap().getName()%></a>
</div>
<%
	} else {
%>

<h3>
	<a href="index.jsp?page=map&amp;id=<%=square.getMap().getId()%>"><%=square.getMap().getName()%></a>
	&rarr; Square <%= square.getId() %>
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

<% if (square.getBase() != null && square.getBase().getDestroyed() == null) { %>
<h4>Base</h4>
<%
    String baseType = square.getBase().getStarterBase()?"Starter base":"Base";
	String baseOwner = square.getBase().getParticipation().getParticipant().getUsername();
%>

<p>There is a <strong><%=baseType%></strong>
   owned by <strong><%=baseOwner%></strong>
   on this square.
</p>
<ul>
    <li><a href="index.jsp?page=basedetail&amp;id=<%=square.getId()%>">Base details</a></li>
</ul>
<% } %>

<h3>Characteristics</h3>
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

	<%
		if (square.getBase() == null || square.getBase().getDestroyed() != null) {
	%>
	<h4>No base here. Create one?</h4>
	<ul>
	<li><a href="CreateBaseServlet?id=<%=square.getId()%>">Create
			Base</a>
	</li>
	</ul>
	<%
		}
			if (request.getAttribute("errorMsg") != null) {
	%>
	<%=request.getAttribute("errorMsg")%>
	<%
		}
	%>
	
	<h4>Your troops on this square</h4>
	
	<% if (userTroops.size() == 0) { %>
	<p>You don't have any troops here.</p>
	<% } else { %>
	<table class="troopList">
	<tr>
		<th>Type</th>
		<th>Rating</th>
		<th>Movement</th>
		<th>Level Upgrade</th>
	</tr>
	
	<% for (Troop troop : userTroops) { %>
	
	<%
	       String troopType = troop.getUpgradeLevel().getName();
	       String speed = ""+troop.getUpgradeLevel().getSpeed();
	       String strength = ""+troop.getUpgradeLevel().getStrength();
	       
	       boolean moving = (troop.getTargetSquare() != null);
	       String movementTarget = moving?("square "+troop.getTargetSquare().getId()):"";
	       
	       boolean canUpgrade = (troop.getUpgradeLevel().getNextLevel() != null);
	       boolean upgradingNow = (troop.getLevelUpgradeFinish() != null);
	       String upgradeTarget = canUpgrade?(troop.getUpgradeLevel().getNextLevel().getName()):"";
	       String upgradeCosts = canUpgrade?("" + troop.getUpgradeLevel().getUpgradeCost().getAmount() +
                                 " " + troop.getUpgradeLevel().getUpgradeCost().getResource().getName() ):"";
	%>
	
	<tr>
		<td class="troopType"><%= troopType %></td>
		<td class="rating"><span title="speed <%=speed%>"><%= speed %></span>/<span title="strength <%=strength%>"><%= strength %></span></td>
		<td>
		<% if (moving) { %>
		    &rarr; <%= movementTarget %>
	    <% } else { %>
	        <a href="TroopServlet?action=move&amp;id=<%=square.getId()%>&amp;tid=<%=troop.getId()%>">select target</a>
        <% } %>
        </td>
        <td>
        <% if (canUpgrade && !upgradingNow) { %>
	        <a href="TroopServlet?action=upgrade&amp;id=<%=square.getId()%>&amp;tid=<%=troop.getId()%>">&rarr; <%= upgradeTarget %></a>
	        (costs: <%=upgradeCosts %>)
	    <% } else if (upgradingNow) { %>
	        &rarr; <%= upgradeTarget %> (in progress)
	    <% } else { %>
	        <em>fully upgraded</em>
        <% } %>
        
        </td>
	</tr>
	<% } %>
	<!-- 

	if (troop.getUpgradeLevel().getNextLevel() != null) {
		String upgrade = "<a href=\"TroopServlet?action=upgrade&id="
				+ square.getId()
				+ "&tid="
				+ troop.getId()
				+ "\">upgrade</a> ";

		if (troop.getLevelUpgradeFinish() != null) {
			upgrade = "upgrading";
		}

		out.println("<li>"
				+ upgrade
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
			-->
    </table>
    <% } /* end else-block for "if (userTroops.size() == 0)" */ %>
    
    <%
        /* user can only create a base if (s)he has a base on it */
		if (square.getBase() != null
				&& square.getBase().getParticipation().getParticipant()
						.getId() == user.getId() && square.getBase().getDestroyed() == null) {
	%>
	
	<p>You can create a
  <%
 	TroopTypeDAO ttDao = new TroopTypeDAO();
	List<TroopType> tts = ttDao.getAll();
	TroopType tt = tts.get(tts.size() - 1);
  %>
  	<a href="TroopServlet?action=create&id=<%=square.getId()%>">new
    <strong><%= tt.getName() %></strong> troop</a>
    
    (costs: <%= tt.getInitialCost().getAmount() %>
            <%= tt.getInitialCost().getResource().getName() %>)
	</p>
	<% } %>

    <jsp:include page="notification.jsp" />
<%
	}
%>