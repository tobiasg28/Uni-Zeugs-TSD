<%@page import="backend.Util"%>
<%@page import="storage.DAOImpl"%>
<%@ page import="dao.*,entities.*,java.util.*,swag.*"%>
<hr>
<div class="map_actions">
	<%
		String pname = (String) request.getParameter("page");
		long pid = Long.parseLong((String) request.getParameter("id"));

		GameMapDAO gmDao = new GameMapDAO();
		GameMap map = null;

		if (pname.equals("map")) {
			map = gmDao.get(pid);
		} else if (pname.equals("square") || pname.equals("basedetail")) {
			SquareDAO sDao = new SquareDAO();
			map = sDao.get(pid).getMap();
		}

		GameStepDAO gDao = new GameStepDAO();
		List<GameStep> steps = gDao.getAll();
		if (steps != null && steps.size() > 0) {
			List<GameStep> lastSteps = new ArrayList<GameStep>();
			int tmp = 20;
			if (steps.size() < tmp) {
				tmp = steps.size();
			}
			for (int j = steps.size() - 1; j > steps.size() - tmp; j--) {
				lastSteps.add(steps.get(j));
			}

			Map<String, Date> msgs = new HashMap<String, Date>();
			for (Square s : map.getSquares()) {
				for (Troop troop : s.getTroops()) {
					if (troop.getMovementStart() != null) {
						if (isActualGameStep(troop.getMovementStart(),
								lastSteps)) {
							String msg = /**troop.getMovementStart().getDate()
											+ ": "
											+ **/
							troop.getParticipation().getParticipant()
									.getUsername()
									+ "'s "
									+ troop.getUpgradeLevel().getName()
									+ " on "
									+ troop.getCurrentSquare()
											.getPositionX()
									+ "/"
									+ troop.getCurrentSquare()
											.getPositionY()
									+ " has been killed";
							msgs.put(msg, troop.getMovementStart()
									.getDate());
						}
					}
				}
				Base base = s.getBase();
				if (base != null && base.getDestroyed() != null) {
					if (isActualGameStep(base.getDestroyed(), lastSteps)) {
						String msg = /**base.getDestroyed().getDate()
										+ ": "
										+ **/
						base.getParticipation().getParticipant()
								.getUsername()
								+ "'s base on "
								+ s.getPositionX()
								+ "/"
								+ s.getPositionY() + " has been destroyed";
						msgs.put(msg, base.getDestroyed().getDate());
					}
				}
			}

			msgs = Util.sortMapByValue(msgs);
			Iterator<String> msgi = msgs.keySet().iterator();
			while (msgi.hasNext()) {
				out.println(msgi.next() + "<br/>");
			}
		}
	%>
	<%!private boolean isActualGameStep(GameStep g, List<GameStep> lastSteps) {
		for (GameStep step : lastSteps) {
			if (step.getId() == g.getId()) {
				return true;
			}
		}
		return false;
	}%>
</div>