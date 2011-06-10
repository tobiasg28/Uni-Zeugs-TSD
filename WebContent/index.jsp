<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWAG</title>
</head>
<body>
	<jsp:useBean id="backend" class="frontend.BackendConnection"
		scope="session" />
	<%
		String p = request.getParameter("page");
		if (p == null) {
			p = "";
		}
		String id = request.getParameter("id");
		if (id == null) {
			id = "";
		}
	%>
	<center>
		<table width="800" border="1">
			<thead>
				<h1>SWAG</h1>
			</thead>
			<tbody>
				<tr>
					<td width="20%"><%@ include file="userBar.jsp"%></td>
					<td width="80%">
						<%
							if (p.equals("register")) {
						%> <jsp:include page="register.jsp" /> <%
 	} else if (p.equals("maps")) {
 %> <jsp:include page="maps.jsp" /> <%
 	} else if (p.equals("createMap")) {
 %> <jsp:include page="createMap.jsp" /> <%
 	} else if (p.equals("map") && !id.equals("")) {
 %> <jsp:include page="map.jsp" /> <%
 	} else if (p.equals("square") && !id.equals("")) {
 %> <jsp:include page="square.jsp" /> <%
 	} else if (p.equals("userAccount")) {
 %> <jsp:include page="userAccount.jsp" /> <%
 	} else {
 %> <jsp:include page="login.jsp" /> <%
 	}
 %>
					</td>
				</tr>
			</tbody>
		</table>
	</center>
</body>
</html>