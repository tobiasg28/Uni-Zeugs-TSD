<%@ page import="dao.*,entities.*,java.util.*,swag.*,storage.*"%>
<jsp:useBean id="user" class="entities.User" scope="session" />
<div>
<h2><%=user.getUsername()%>'s account</h2>
<br />
<%
	ParticipationDAO pdao = new ParticipationDAO();
	try {
		List<Participation> participations = pdao.getAll();
		List<Participation> myParticipations = new ArrayList<Participation>();
		for (Participation p : participations) {
			if (p.getParticipant().getUsername()
					.equals(user.getUsername()))
				myParticipations.add(p);
		}

		for (Participation p : myParticipations) {
			out.print("Map: ");
			out.print(p.getMap().getName() + "<br/>");
			for (int i = 0; i < 4; i++) {
				out.print(p.getResources().get(i).getResource()
						.getName());
				out.print(" (" + p.getResources().get(i).getAmount() + ")<br/>");
			}
		}
		out.print("<br/>");
	} catch (DAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
%> <a href="AccountServlet?action=delete">delete account</a></div>