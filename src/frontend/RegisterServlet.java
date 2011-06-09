package frontend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp");

		HttpSession session = request.getSession(true);
		request.setAttribute("error", false);
		User user = (User) session.getAttribute("user");

		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
			session.setAttribute("user", user);
		} else {
			String un = request.getParameter("username").trim();
			String pw = request.getParameter("password").trim();
			String ln = request.getParameter("lastname").trim();
			String fn = request.getParameter("firstname").trim();
			String ad = request.getParameter("adress").trim();
			request.setAttribute("username", un);
			request.setAttribute("password", pw);
			request.setAttribute("lastname", ln);
			request.setAttribute("firstname", fn);
			request.setAttribute("adress", ad);
			try {
				// check if every field of the form is filled
				if (un.equals("")){
					request.setAttribute("error", true);
					request.setAttribute("errorMsg", "set username!");
					dispatcher = getServletContext().getRequestDispatcher(
							"/index.jsp?page=register");
				} else if (pw.equals("")){
					request.setAttribute("error", true);
					request.setAttribute("errorMsg", "set password!");
					dispatcher = getServletContext().getRequestDispatcher(
							"/index.jsp?page=register");
				} else if (fn.equals("")){
					request.setAttribute("error", true);
					request.setAttribute("errorMsg", "set first name!");
					dispatcher = getServletContext().getRequestDispatcher(
							"/index.jsp?page=register");
				} else if (ln.equals("")){
					request.setAttribute("error", true);
					request.setAttribute("errorMsg", "set last name!");
					dispatcher = getServletContext().getRequestDispatcher(
							"/index.jsp?page=register");
				}else if (ad.equals("")){
					request.setAttribute("error", true);
					request.setAttribute("errorMsg", "set adress!");
					dispatcher = getServletContext().getRequestDispatcher(
							"/index.jsp?page=register");
				} else if (!userExists(un)) {
					user = new User();
					user.setUsername(un);
					user.setPassword(pw);
					user.setAdress(ad);
					user.setFullName(fn);

					UserDAO dao = new UserDAO();
					dao.create(user);
					//auto login after user creation
					session.setAttribute("user", user);
					session.setAttribute("loggedIn", (Boolean) true);
				} else {
					request.setAttribute("error", true);
					request.setAttribute("errorMsg", "username already exists!");
					dispatcher = getServletContext().getRequestDispatcher(
							"/index.jsp?page=register");
				}
			} catch (DAOException e) {
				request.setAttribute("error", true);
				request.setAttribute("errorMsg", "error");
				dispatcher = getServletContext().getRequestDispatcher(
						"/index.jsp?page=register");
				e.printStackTrace();
			}
		}
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

	// checks if user already exists
	private boolean userExists(String username) throws DAOException {
		UserDAO findUser = new UserDAO();
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("username", username);
		for (User user : findUser.findByAttributes(attributes)) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

}
