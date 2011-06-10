package frontend;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import notification.Frontend;

import dao.UserDAOExtended;

import entities.User;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * handles the login operation
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);
		// an error-attribute is set
		request.setAttribute("loginError", false);
		
		User user = (User) session.getAttribute("user");

		//to check if the user is already logged in
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
			session.setAttribute("user", user);
		} else {
			UserDAOExtended ude = new UserDAOExtended();
			//checks if the user exists
			if ((user = ude.login(request.getParameter("username"), request.getParameter("password"))) != null) {
				session.setAttribute("user", user);
				session.setAttribute("loggedIn", (Boolean) true);
			} else{
				request.setAttribute("error", true);
				request.setAttribute("errorMsg", "wrong username or password!");
			}
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
}
