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
import dao.SquareDAO;
import dao.UserDAO;
import entities.Participation;
import entities.Square;
import entities.User;

public class TroopServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(TroopServlet.class);
	private Square square;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);
		request.setAttribute("error", false);
		User user = (User) session.getAttribute("user");
		String url = "/index.jsp";

		try {
			String action = (String) request.getParameter("action");
			long squareId = Long.parseLong((String) request.getParameter("id"));
			url = "/index.jsp?page=square&id=" + squareId;

			if (session.getAttribute("loggedIn") != null
					&& (Boolean) session.getAttribute("loggedIn")) {
				long participationId = getParticipationId(squareId, user,
						session);
				if (participationId == -1) {
					System.err.println("TroopServlet: not participating!");
				} else {
					if (action.equals("create")) {
						GamePlay.createTroop(participationId, squareId);
					} else if (action.equals("move")) {
						long tid = Long.parseLong((String) request
								.getParameter("tid"));
						if (request.getParameter("to") == null) {
							request.setAttribute("tid", tid);
							request.setAttribute("action", action);
							url = "/index.jsp?page=map&id="
									+ square.getMap().getId();
						} else {
							GamePlay.moveTroop(tid, squareId);
						}
					} else if (action.equals("upgrade")) {
						long tid = Long.parseLong((String) request
								.getParameter("tid"));
						GamePlay.upgradeTroop(participationId, tid);
					} else {
						logger.error("TroopServlet: unknown action=" + action);
					}
				}
			}
		} catch (DAOException e) {
			request.setAttribute("error", true);
			request.setAttribute("errorMsg", "connection problem, please retry");
			e.printStackTrace();
		} catch (GamePlayException e) {
			request.setAttribute("error", true);
			request.setAttribute("errorMsg", e);
			e.printStackTrace();
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
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
