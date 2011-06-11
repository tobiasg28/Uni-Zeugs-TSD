package frontend;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import storage.DAOException;
import dao.SquareDAO;
import entities.GameMap;
import entities.Participation;
import entities.Square;
import entities.User;

public class CreateBaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMsg;
	private GameMap currentMap;
	private List<Participation> participants;
	private Participation usersParticipation;
	private boolean isPlayerOnMap = false;
	private boolean hasEnoughResources = false;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		// Square ID
		long id = Long.parseLong(req.getParameter("id"));
		SquareDAO sdao = new SquareDAO();
		Square square = null;
		try {
			square = sdao.get(id);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
			if (square.getBase() != null) {
				errorMsg = "There's already a base on this square!";
				req.setAttribute("errorMsg", errorMsg);
			} else {
				currentMap = square.getMap();
				participants = currentMap.getParticipations();

				for (Participation p : participants) {
					if (p.getParticipant().getUsername().equals(user.getUsername())) {
						isPlayerOnMap = true;
						usersParticipation = p;
						break;
					}
				}

				try {
					GamePlay.createBase(usersParticipation.getId(), id);
				} catch (GamePlayException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					req.setAttribute("errorMsg", e.getMessage());
				}
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp?page=square&id=" + id);
		dispatcher.forward(req, resp);

	}
}
