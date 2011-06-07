package frontend;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import storage.DAOException;
import dao.UserDAO;

import entities.User;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);
		request.setAttribute("error", false);
		User user = (User) session.getAttribute("user");

		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
			session.setAttribute("user", user);
		} else {
			String un = request.getParameter("username");
			try {
				//if a user doesn't exist, a new one is created
				if (!userExists(un)) {
					user = new User();
					user.setUsername(un);
					user.setPassword(request.getParameter("password"));
					user.setAdress(request.getParameter("adress"));
					user.setFullName(request.getParameter("lastname") + " "
							+ request.getParameter("firstname"));

					UserDAO dao = new UserDAO();
					dao.create(user);

					session.setAttribute("user", user);
					session.setAttribute("loggedIn", (Boolean) true);
				} else {
					request.setAttribute("error", true);
					request.setAttribute("errorMsg",
							"username or password wrong!");
				}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	//checks if user already exists
	private boolean userExists(String username) throws DAOException {
		UserDAO allUser = new UserDAO();
		for (User user : allUser.getAll()) {
			System.out.println(user.getUsername());
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

}
