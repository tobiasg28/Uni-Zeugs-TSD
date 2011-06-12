package frontend;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import storage.DAOException;
import dao.BuildingTypeDAO;
import dao.SquareDAO;
import dao.UserDAO;
import entities.Base;
import entities.BuildingType;
import entities.Participation;
import entities.Square;
import entities.User;

public class BuildingServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BuildingServlet.class);
	private Square square;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp");

		HttpSession session = request.getSession(true);
		request.setAttribute("error", false);
		User user = (User) session.getAttribute("user");

		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {

		}
		dispatcher.forward(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);
		request.setAttribute("error", false);
		User user = (User) session.getAttribute("user");

		String action = (String) request.getParameter("action");
		long squareId = Long.parseLong((String) request.getParameter("id"));

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp?page=basedetail&id=" + squareId);

		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
			try {
				long participationId = getParticipationId(squareId, user,
						session);
				if (participationId == -1) {
					System.err.println("BuildingServlet: not participating!");
				} else {
					if (action.equals("add")) {
						long buildingTypeId = Long.parseLong(request.getParameter("bt"));
						BuildingTypeDAO btdao = new BuildingTypeDAO();
						BuildingType bt = btdao.get(buildingTypeId);
						SquareDAO sdao = new SquareDAO();
						Base base = sdao.get(squareId).getBase();
						GamePlay.createBuilding(participationId, base.getId(), bt);
					} else if (action.equals("upgrade")) {
						long buildingId = Long.parseLong(request.getParameter("bid"));
						GamePlay.upgradeBuilding(participationId, buildingId);
					} else {
						System.err.println("BuildingServlet: unknown action="
								+ action);
					}
				}
			} catch (DAOException e) {
				e.printStackTrace();
			} catch (GamePlayException e) {
				System.err.println("BuildingServlet: " + e);
				request.setAttribute("errorMsg", e.getMessage());
				e.printStackTrace();
			}
		}
		dispatcher.forward(request, response);
	}

	private long getParticipationId(long squareId, User user,
			HttpSession session) throws DAOException {
		SquareDAO sDao = new SquareDAO();
		UserDAO uDao = new UserDAO();
		square = sDao.get(squareId);
		user = uDao.get(user.getId());
		session.setAttribute("user", user);

		for (Participation p : user.getParticipations()) {
			if (p.getMap().getId() == square.getMap().getId()) {
				return p.getId();
			}
		}

		return -1;
	}
}
