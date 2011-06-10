package frontend;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import entities.User;

public class ParticipateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(ParticipateServlet.class);

	@Override
	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		long id = Long.parseLong(request.getParameter("id"));
		
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp?page=map&id=" + id);
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
			try {
				GameStart.newPlayer(user, id);
			} catch (GameStartException e) {
				System.err.println(e);
				e.printStackTrace();
				logger.error(e);
			}
		}
		
		dispatcher.forward(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/?page=maps");
		dispatcher.forward(request, response);
	}
}
