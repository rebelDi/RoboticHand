<%@ page import="java.util.ArrayList" %>
<%@ page import="Additions.Action" %>
<%@ page import="Additions.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Change Your Info</title>
    <link href="CSS/style.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="JS/signUp.js" ></script>
    <script type="text/javascript" src="JS/main.js" ></script>
</head>
<body>
<center>
    <form action="headerRedirect" method="post">
        <ul class="ulBar">
            <li class="ilBar"><input name="action" type="submit" value="Control Panel" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="button" value="User Info" class="ilButton active" /></li>
            <%if(session.getAttribute("rights").equals("S") || session.getAttribute("rights").equals("A")){ %>
                <li class="ilBar"><input name="action" type="submit" value="Admin" class="ilButton" /></li>
            <%}%>
            <li class="ilBar"><input name="action" type="submit" value="Messages" class="ilButton" /></li>
            <li class="ilBar"><input name="action" type="submit" value="Exit" class="ilButton" /></li>
        </ul>
    </form>
    <hr />

    <%
        User user = (User) request.getAttribute("user");
    %>

    <form action="userInfoChange" method="post" class="form">
        <br />
        <%
            if(request.getAttribute("error") != null){
        %>
            <p style="color: red"><%=request.getAttribute("error")%></p>
        <%
            }else{
        %>
            <p style="color: red"></p>
        <%  }%>
        <br />

        <label id="Label1">User Name </label>
        <label>
            <input name="loginU" type="text" style="width: 162px" value="<%=user.getLogin()%>" />
        </label><br /><br />

        Old Password&nbsp;&nbsp;&nbsp;
        <label>
            <input id="oldPassword" name="oldPassword" type="password" style="width: 162px" />
        </label>
        <br /><br />

        New Password&nbsp;&nbsp;&nbsp;
        <label>
            <input id="password" name="password" type="password" onkeyup="check()" style="width: 162px" />
            <span id='messagePass'></span>
        </label>
        <br /><br />

        Confirm Password&nbsp;&nbsp;&nbsp;
        <label>
            <input id="confirmPassword" name="confirmPassword" type="password" onkeyup="check()" style="width: 162px" />
            <span id='messageConf'></span>
        </label><br /><br />

        Name&nbsp;&nbsp;&nbsp;
        <label>
            <input name="name" type="text" style="width: 162px" value="<%=user.getName()%>" />
        </label><br /><br />

        Surname&nbsp;&nbsp;&nbsp;
        <label>
            <input name="surname" type="text" style="width: 162px" value="<%=user.getSurname()%>" />
        </label><br /><br />

        Secret Question&nbsp;&nbsp;&nbsp;
        <label>
            <input name="userQuest" type="text" value="<%=user.getSecretQuestion()%>" style="width: 162px" readonly/>
        </label><br /><br />

        Secret Answer&nbsp;&nbsp;&nbsp;
        <label>
            <input name="answer" type="text" style="width: 162px" onclick="mainCheck()"/>
        </label><br /><br />
        <center>
            <input type="submit" value="Update" class="ilButton"/>
        </center>
        <br />
    </form>
</center>
</body>
</html>