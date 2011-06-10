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

import dao.UserDAO;

import entities.User;

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AccountServlet.class);

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp?page=userAccount");

		dispatcher.forward(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		
		if (session.getAttribute("loggedIn") != null
				&& (Boolean) session.getAttribute("loggedIn")) {
			if (request.getParameter("action") != null) {
				if (request.getParameter("action").equals("delete")) {
					UserDAO uDao = new UserDAO();
					try {
						uDao.delete(user.getId());
					} catch (DAOException e) {
						System.err.println("delete user daoexception");
						e.printStackTrace();
					}
					session.invalidate();
				}
			}
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
}
