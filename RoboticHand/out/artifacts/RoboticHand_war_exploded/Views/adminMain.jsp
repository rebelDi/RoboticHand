<%@ page import="java.util.ArrayList" %>
<%@ page import="Additions.Action" %>
<%@ page import="Additions.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link href="CSS/style.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="JS/main.js" ></script>
</head>
<body>
<center>

    <form action="headerRedirect" method="post">
        <input type="hidden" name="login" value="<%=request.getAttribute("login")%>"/>
        <input type="hidden" name="rights" value="<%=request.getAttribute("rights")%>"/>
        <ul class="ulBar">
            <li class="ilBar"><input name="action" type="submit" value="Control Panel" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="User Info" class="ilButton" /></li>
            <%if(request.getAttribute("rights").equals("S") || request.getAttribute("rights").equals("A")){ %>
            <li class="ilBar"><input name="action" type="button" value="Admin" class="ilButton active" /></li>
            <%}%>
            <li class="ilBar"><input name="action" type="submit" value="Messages" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="Exit" class="ilButton" /></li>
        </ul>
    </form>
    <hr />

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
            <td colspan="2" width="200px">
                Rights
            </td>
        </tr>
        <center>
            <%
                ArrayList<User> result = (ArrayList<User>) request.getAttribute("users");
                String checked = "";
                for(int i = 0; i < result.size(); i++) {
            %>
            <tr>
                <td id="<%= result.get(i).getName()%>" style="text-align: center">
                    <%= result.get(i).getLogin()%>
                </td>
                <td style="text-align: center">
                    <%= result.get(i).getName()%>
                </td>
                <td style="text-align: center">
                    <%= result.get(i).getSurname()%>
                </td>
                <form action="userChange" method="post">
                    <input type="hidden" name="login" value="<%=request.getAttribute("login")%>"/>
                    <input type="hidden" name="rights" value="<%=request.getAttribute("rights")%>"/>
                    <input type="hidden" name="loginU" value="<%=result.get(i).getLogin()%>">
                    <td style="alignment: center; width: auto">
                        <input name="whatToChange" type="submit" value="Admin" class="ilButton" style="text-align: center;
                <%=(result.get(i).getRights() == 'A' ? "background-color:#2bff36;" : "")%>" />
                    </td>

                    <td style="alignment: center; width: auto">
                        <input name="whatToChange" type="submit" value="User" class="ilButton" style="text-align: center;
                <%=(result.get(i).getRights() == 'U' ? "background-color:#2bff36;" : "")%>" />
                    </td>
                </form>
            </tr>
            <%
                }%>
        </center>
    </table>
</center>
</body>
</html>