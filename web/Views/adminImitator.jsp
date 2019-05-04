<%@ page import="java.util.ArrayList" %>
<%@ page import="Additions.Action" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link href="CSS/style.css" type="text/css" rel="stylesheet">
    <script src="JS/jQuery.js"></script>
    <script type="text/javascript" src="JS/adminImitator.js" ></script>
</head>
<body>
<center>

    <form action="headerRedirect" method="post">
        <ul class="ulBar">
            <li class="ilBar"><input name="action" type="submit" value="Control Panel" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="User Info" class="ilButton" /></li>
            <%if(session.getAttribute("rights").equals("S") || session.getAttribute("rights").equals("A")){
                if(session.getAttribute("rights").equals("A")){%>
            <li class="ilBar"><input name="action" type="submit" value="Admin" class="ilButton active" />
                    <%}else{%>
            <li class="ilBar"><input name="action" type="button" value="Admin" class="ilButton active" />
                <%}%>
                <%if(session.getAttribute("rights").equals("S")){ %>
                <ul class="submenu">
                    <li><input name="action" type="submit" value="Users" class="ilButton adminButton" /></li>
                </ul>
                <%}%>
            </li>
            <%}%>
            <li class="ilBar"><input name="action" type="submit" value="Messages" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="Exit" class="ilButton" /></li>
        </ul>
    </form>
    <hr />

    <table width="59%" border="1" class="tableActions" id="tableActions">
        <tr style="text-align: center; background-color: azure">
            <td>
                Action Name
            </td>
            <td>
                MC Servo #
            </td>
            <td>
                Leap Minimum
            </td>
            <td>
                Leap Maximum
            </td>
            <td>
                Servo Direction
            </td>
            <td>
                Servo Minimum
            </td>
            <td>
                Servo Maximum
            </td>
            <td>
                Availability
            </td>
            <td>
            </td>
        </tr>
        <center>
            <%
                ArrayList<Action> result = (ArrayList<Action>) request.getAttribute("actions");
                for(int i = 0; i < result.size(); i++) {
            %>
            <tr>
                <td>
                    <%= result.get(i).getName()%>
                </td>
                <td>
                    <input id="ardInd<%=i%>" maxlength="2" value="<%= result.get(i).getArduinoIndex()%>"  class="servoInput">
                </td>
                <td>
                    <input id="leapMin<%=i%>" maxlength="4" value="<%= result.get(i).getLeapMinimum()%>" class="coordsInput">
                </td>
                <td>
                    <input id="leapMax<%=i%>" maxlength="4" value="<%= result.get(i).getLeapMaximum()%>" class="coordsInput">
                </td>
                <td>
                    <p class="servoDirection" id="servoD<%=i%>" onclick="changeServoDirection(<%=i%>)">
                        <% if(result.get(i).getServoDirection() == 1){ %>
                            Clockwise
                        <% }else{ %>
                            Counter-clockwise
                        <% }%>
                    </p>
                </td>
                <td>
                    <input id="servoMin<%=i%>" maxlength="4" value="<%= result.get(i).getServoMinimum()%>" class="coordsInput">
                </td>
                <td>
                    <input id="servoMax<%=i%>" maxlength="4" value="<%= result.get(i).getServoMaximum()%>" class="coordsInput">
                </td>
                <td id="avail<%=i%>" style="text-align: center; <%=(result.get(i).getAvailability() == 0 ? "background-color: hotpink;" : "background-color:#2bff36;")%>"
                onclick="changeAvailability(<%=i%>)">
                </td>
                <td>
                    <input class="submitImitatorChange" value="Change" type="button" onclick="sendChangedData(<%=i%>, '<%= result.get(i).getName()%>')">
                </td>
            </tr>
            <%
                }
            %>
        </center>
    </table>
</center>
</body>
</html>