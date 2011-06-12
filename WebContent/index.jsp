<%@page import="swag.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SWAG</title>
<script language="JavaScript">
	function doRefresh(){
		setTimeout("refresh()",<%=Constants.GAMESTEP_DURATION_MS / 2%>);
	}
	function refresh() {
		window.location.href = sURL;
	}
</script>
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
			%> <jsp:include page="register.jsp" /> 
			<%
 	} else if (p.equals("maps")) {
 %> <jsp:include page="maps.jsp" /> 
  <script language="JavaScript">
	sURL = "index.jsp?page=maps%>";
	doRefresh();
</script> 
 <%
 	} else if (p.equals("createMap")) {
 %> <jsp:include page="createMap.jsp" /> <%
 	} else if (p.equals("map") && !id.equals("")) {
 %> <jsp:include page="map.jsp" /> 
 <script language="JavaScript">
	sURL = "index.jsp?page=map&id=<%=id%>";
	doRefresh();
</script> 
 <%
 	} else if (p.equals("square") && !id.equals("")) {
 %> <jsp:include page="square.jsp" />
 <script language="JavaScript">
	sURL = "index.jsp?page=square&id=<%=id%>";
	doRefresh();
</script> 
 <%
 	} else if (p.equals("basedetail")) {
 %> <jsp:include page="basedetail.jsp" /> 
 <script language="JavaScript">
	sURL = "index.jsp?page=basedetail&id=<%=id%>";
	doRefresh();
</script> 
 <%
 	} else if (p.equals("userAccount")) {
 %> <jsp:include page="userAccount.jsp" /> 
 <script language="JavaScript">
	sURL = "index.jsp?page=userAccount%>";
	doRefresh();
</script> 
 <%
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