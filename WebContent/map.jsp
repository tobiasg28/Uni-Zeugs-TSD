<jsp:useBean id="map" class="entities.GameMap" scope="session" />
<%@ page import="dao.*,entities.*,java.util.*,swag.*" %>

<h1>MAP ID <%= request.getParameter("id") %></h1>

<style type="text/css">

.swagsquare {
    position: absolute;
    display: block;
    width: <%= Constants.SQUARE_SIZE%>px;
    height: <%= Constants.SQUARE_SIZE%>px;
    background-color: #790;
    border: 1px black solid;
    text-decoration: none;
    color: white;
    text-align: right;
    font-weight: bold;
    background-repeat: no-repeat;
}

.swagsquare:hover {
    background-color: #ab0;
}

.swagsquare span {
    padding: 5px;
}

.Gold {
    background-image: url(images/gold.png);
}

.Wood {
    background-image: url(images/wood.png);
}

.Food {
    background-image: url(images/meat.png);
}

.Stone {
    background-image: url(images/stone.png);
}

</style>

<div class="map_squares" style="position: relative;">
<%

GameMapDAO dao = new GameMapDAO();
GameMap map = dao.get(Long.parseLong(request.getParameter("id")));

int maxY = 0;
int maxX = 0;

for (Square square : map.getSquares()) {
	String privileged = "";
	Resource resource = square.getPrivilegedFor();
	if (resource != null) {
		privileged = resource.getName();
	}
	out.println("<a href=\"?page=square&amp;id="+square.getId()+"\" class=\"swagsquare "+privileged+"\" style=\"left: "+(100+square.getPositionX()*Constants.SQUARE_SIZE)+ "px; top: "+(50+square.getPositionY()*Constants.SQUARE_SIZE)+"px;\"><span>"+square.getId()+"</span></a>");
	if (square.getPositionY() > maxY) {
		maxY = square.getPositionY();
	}
	if (square.getPositionX() > maxX) {
		maxX = square.getPositionX();
	}
}

%>
</div>

<%

// Vertikaler Spacer fuer die obige Square-Ansicht, die ja relativ positioniert ist
out.println("<div style=\"height: "+(Constants.SQUARE_SIZE * maxY + 200)+"px; width: "+(Constants.SQUARE_SIZE * maxX + 100)+"px;\"> </div>");

%>
