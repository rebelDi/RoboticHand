<%@ page import="java.util.ArrayList" %>
<%@ page import="Additions.Action" %>
<%@ page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <link href="CSS/style.css" type="text/css" rel="stylesheet">
    <script src="JS/jQuery.js"></script>
    <script type="text/javascript" src="JS/main.js" ></script>
    <script type="text/javascript" src="JS/leap-0.6.4.min.js"></script>
    <script type="text/javascript" src="JS/leap.js" ></script>
</head>
<body>
<center>
    <form action="headerRedirect" method="post">
        <ul class="ulBar">
            <li class="ilBar"><input name="action" type="button" value="Control Panel" class="ilButton active" /></li>
            <li class="ilBar"><input name="action" type="submit" value="User Info" class="ilButton" /></li>
            <%if(session.getAttribute("rights").equals("S") || session.getAttribute("rights").equals("A")){ %>
                <li class="ilBar"><input name="action" type="submit" value="Admin" class="ilButton" /></li>
            <%}%>
            <li class="ilBar"><input name="action" type="submit" value="Messages" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="Exit" class="ilButton" /></li>
        </ul>
    </form>
    <hr />

    <button id="checkConnection" class="ilButton buttonLaunch" style="background-color: lightpink">Check Connection</button>
    <br>
    <div style="visibility: hidden" id="response" class="responseConnection"></div>
    <br>

    <table width="59%" border="1" class="tableActions" id="tableActions">
        <tr style="text-align: center; background-color: azure">
            <td>
                Actions
            </td>
            <td>
                Available
            </td>
            <td>
                Data
            </td>
        </tr>
        <center>
        <%
            ArrayList<Action> result = (ArrayList<Action>) request.getAttribute("actions");
            for(int i = 0; i < result.size(); i++) {
        %>
        <tr>
            <td style="text-align: center">
                <%= result.get(i).getName()%>
            </td>
            <td id="<%= i%>" style="text-align: center;
                <%=(result.get(i).getAvailability() == 0 ? "background-color:hotpink;" : "background-color:#2bff36;")%>"
                onclick="changeAvailability(<%= result.get(i).getAvailability()%>, <%= i%>)">
                <input type="hidden" id="flag<%=i%>" value="<%= result.get(i).getAvailability()%>" />
            </td>
            <td style="text-align: center; width: 150px" id="<%= result.get(i).getName()%>"></td>
        </tr>
        <%
            }%>
        </center>
    </table>
    <br /><br />
    <table style="alignment: center">
        <tr>
            <td>
                <button id="pause" onclick="togglePause()" class="ilButton buttonLaunch" style="background-color: lightpink">Start</button>
            </td>
            <td style="width: 10px"></td>
            <td>
                <div class="popup" onclick="popUpShow()">
                    <input class="helpImage" type="image" src="Additions/help.png">
                    <span class="popuptext" id="myPopup">If you do not see any data, your Leap Motion is not working!</span>
                </div>
            </td>
        </tr>
    </table>
</center>
</body>
</html>