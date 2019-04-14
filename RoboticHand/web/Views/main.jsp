<%@ page import="java.util.ArrayList" %>
<%@ page import="Additions.Action" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <link href="CSS/style.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="JS/main.js" ></script>
    <script type="text/javascript" src="JS/leap-0.6.3.min.js"></script>
    <script type="text/javascript" src="JS/leap.js" ></script>
</head>
<body>
<center>
    <form action="headerRedirect" method="post">
            <input type="hidden" name="login" value="<%=request.getAttribute("login")%>"/>
            <input type="hidden" name="rights" value="<%=request.getAttribute("rights")%>"/>
        <ul class="ulBar">
            <li class="ilBar"><input name="action" type="button" value="Control Panel" class="ilButton active" /></li>
            <li class="ilBar"><input name="action" type="submit" value="User Info" class="ilButton" /></li>
            <%if(request.getAttribute("rights").equals("S") || request.getAttribute("rights").equals("A")){ %>
                <li class="ilBar"><input name="action" type="submit" value="Admin" class="ilButton" /></li>
            <%}%>
            <li class="ilBar"><input name="action" type="submit" value="Messages" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="Exit" class="ilButton" /></li>
        </ul>
    </form>
    <hr />

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
                <%=(result.get(i).getStatus() == 0 ? "background-color:hotpink;" : "background-color:#2bff36;")%>"
                onclick="changeAvailability(<%= result.get(i).getStatus()%>, <%= i%>)">
                <input type="hidden" id="flag<%=i%>" value="<%= result.get(i).getStatus()%>" />
            </td>
            <td style="text-align: center; width: 150px" id="<%= result.get(i).getName()%>"></td>
        </tr>
        <%
            }%>
        </center>
    </table>
    <br /><br />
    <button id="pause" onclick="togglePause()" class="ilButton" style="background-color: lightpink">Start</button>
</center>
</body>
</html>