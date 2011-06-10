<jsp:useBean id="user" class="entities.User" scope="session" />
<div>
<h2><%= user.getUsername() %>'s account</h2>
<a href="AccountServlet?action=delete">delete account</a>
</div>