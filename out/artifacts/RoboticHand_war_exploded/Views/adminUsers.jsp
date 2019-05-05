<%@ page import="java.util.ArrayList" %>
<%@ page import="Additions.Action" %>
<%@ page import="Additions.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link href="CSS/style.css" type="text/css" rel="stylesheet">
    <script src="JS/jQuery.js"></script>
    <script type="text/javascript" src="JS/adminUsers.js" ></script>
</head>
<body>
<center>

    <form action="headerRedirect" method="post">
        <ul class="ulBar">
            <li class="ilBar"><input name="action" type="submit" value="Control Panel" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="User Info" class="ilButton" /></li>
            <% String rights = (String) session.getAttribute("rights");
                if(rights.equals("S") || rights.equals("A")){
                    if(rights.equals("A")){%>
            <li class="ilBar"><input name="action" type="submit" value="Admin" class="ilButton active" />
                  <%}else{%>
            <li class="ilBar"><input name="action" type="button" value="Admin" class="ilButton active" />
                  <%}%>
                <%if(session.getAttribute("rights").equals("S")){ %>
                <ul class="submenu">
                    <li><input name="action" type="submit" value="Imitator" class="ilButton adminButton" /></li>
                </ul>
                <%}%>
            </li>
            <%}%>
            <li class="ilBar"><input name="action" type="submit" value="Messages" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="Exit" class="ilButton" /></li>
        </ul>
    </form>
    <hr />

    <table>
        <tr>
            <td>
                <form action="headerRedirect" method="post" style="float: left">
                    <input type="hidden" name="action" value="waitingList" />
                    <button class="ilButton buttonLaunch" style="background-color: lightpink">Show only users waiting for approval</button>
                </form>
            </td>
            <td>
                <form action="headerRedirect" method="post" style="float: right">
                    <%if(session.getAttribute("rights").equals("A")){%>
                        <input type="hidden" name="action" value="Admin" />
                    <%}if(session.getAttribute("rights").equals("S")){ %>
                        <input type="hidden" name="action" value="Users" />
                    <%}%>
                    <button class="ilButton buttonLaunch" style="background-color: lightpink">Show all users</button>
                </form>
            </td>
        </tr>
    </table>
    <br>

    <table width="59%" border="1" class="tableActions" id="tableActions">
        <tr style="text-align: center; background-color: azure">
            <td>
                Login
            </td>
            <td>
                Name
            </td>
            <td>
                Surname
            </td>
            <% if(rights.equals("A")){ %>
                <td colspan="2">
            <% }else if(rights.equals("S")){%>
                <td colspan="3">
            <% }%>
                Rights
            </td>
        </tr>
        <center>
            <%
                ArrayList<User> result = (ArrayList<User>) request.getAttribute("users");
                for(int i = 0; i < result.size(); i++) {
            %>
            <tr>
                <td id="<%= result.get(i).getName()%>">
                    <%= result.get(i).getLogin()%>
                </td>
                <td>
                    <%= result.get(i).getName()%>
                </td>
                <td>
                    <%= result.get(i).getSurname()%>
                </td>
                <input type="hidden" name="loginU" value="<%=result.get(i).getLogin()%>">
                <td>
                    <input id="B<%=i%>" type="button" value="Ban" class="ilButton" style="text-align: center;
                <%=(result.get(i).getRights() == 'B' ? "background-color:#ff3a3a;" : "")%>"
                           onclick="changeUserData('<%=result.get(i).getLogin()%>', 'B', <%=i%>)"/>
                </td>
                <td>
                    <input id="U<%=i%>" type="button" value="User" class="ilButton" style="text-align: center;
            <%=(result.get(i).getRights() == 'U' ? "background-color:#2bff36;" : "")%>"
                           onclick="changeUserData('<%=result.get(i).getLogin()%>', 'U', <%=i%>)" />
                </td>
                <% if(rights.equals("S")){ %>
                    <td>
                        <input id="A<%=i%>" type="button" value="Admin" class="ilButton" style="text-align: center;
                    <%=(result.get(i).getRights() == 'A' ? "background-color:#2bff36;" : "")%>"
                               onclick="changeUserData('<%=result.get(i).getLogin()%>', 'A', <%=i%>)" />
                    </td>
                <% }%>
            </tr>
            <%
                }%>
        </center>
    </table>
</center>
</body>
</html>