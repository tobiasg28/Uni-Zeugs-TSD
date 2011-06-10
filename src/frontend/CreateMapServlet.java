package frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GameMapDAO;
import dao.UserDAO;
import dao.UserDAOExtended;
import entities.GameMap;
import entities.User;

public class CreateMapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);
		// an error-attribute is set
		request.setAttribute("loginError", false);

		User user = (User) session.getAttribute("user");
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp?page=createMap");
		// to check if the user is already logged in
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
			session.setAttribute("user", user);
			String mapName = request.getParameter("mapname").trim();
			String mp = request.getParameter("maxplayers").trim();
			request.setAttribute("mapname", mapName);
			request.setAttribute("maxplayers", mp);
			int maxPlayers;
			request.setAttribute("error", true);
			try {
				if (mapName.equals("")) {
					request.setAttribute("errorMsg", "set map name!");
				} else if (mapExists(mapName)) {
					request.setAttribute("errorMsg", "map already exists!");
				} else {
					maxPlayers = Integer.parseInt(mp);
					request.setAttribute("error", false);
					long id = GameStart.newGame(mapName, maxPlayers);
					BackendConnection.getBackend().onMapCreated(id);
					
					dispatcher = getServletContext().getRequestDispatcher(
							"/index.jsp?page=maps");
				}
			} catch (NumberFormatException e) {
				request.setAttribute("errorMsg",
						"set a number for max players!");
			}

		}

		dispatcher.forward(request, response);

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp?page=createMap");
		dispatcher.forward(request, response);
	}

	private boolean mapExists(String name) {
		GameMapDAO findMap = new GameMapDAO();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("name", name);
		for (GameMap map : findMap.findByAttributes(attributes)) {
			if (map.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
