<%@ page import="java.util.ArrayList" %>
<%@ page import="Additions.Action" %>
<%@ page import="Additions.User" %>
<%@ page import="Additions.Message" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Messages</title>
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
            <li class="ilBar"><input name="action" type="submit" value="Admin" class="ilButton" /></li>
            <%}%>
            <li class="ilBar"><input name="action" type="button" value="Messages" class="ilButton active" /></li>
            <li class="ilBar"><input name="action" type="submit" value="Exit" class="ilButton" /></li>
        </ul>
    </form>
    <hr />

    <%
        ArrayList<Message> messages = (ArrayList<Message>) request.getAttribute("messages");
        boolean show;
    %>

    <table>
        <tr>
            <td>
                <center>
                    <table width="59%" border="1" class="tableActions" id="tableActions">
                        <tr style="text-align: center; background-color: azure">
                            <td>
                                User
                            </td>
                            <td>
                                Questions
                            </td>
                            <td>
                                Status
                            </td>
                        </tr>

                        <%if(messages.size() == 0){
                            show = false;
                        %>
                            <tr>
                                <td colspan="3">
                                    You have not asked anything yet!
                                </td>
                            </tr>
                        <%}else{
                            show = true;
                        %>
                            <%for(int i = 0; i < messages.size(); i++){%>
                                <tr style="text-align: center; background-color: white" onclick="showQuestionForUser(<%=i%>)">
                                    <td>
                                        <input id="U<%=i%>" value="<%=messages.get(i).getUser()%>" readonly>
                                    </td>
                                    <td>
                                        <input id="Q<%=i%>" value="<%=messages.get(i).getQuestion()%>"readonly>
                                        <input type="hidden" id="A<%=i%>" value="<%=messages.get(i).getAnswer()%>">
                                    </td>
                                    <td>
                                        <input value="<%=messages.get(i).getStatus()%>" readonly>
                                    </td>
                                </tr>
                            <%}
                        }%>
                    </table>
                </center>
            </td>
            <td>
        <%if(show){%>
            <tr>
                <table width="59%" border="1" class="tableActions">
                    <tr style="text-align: center; background-color: azure">
                        Question
                    </tr>
                    <br />
                    <tr style="text-align: center; background-color: white">
                        <textarea id="question" cols="40" rows="20" value="" readonly></textarea>
                    </tr>
                </table>
            </tr>
            <tr>
                <table width="59%" border="1" class="tableActions">
                    <tr style="text-align: center; background-color: azure">
                        Answer
                    </tr>
                    <br />
                    <tr style="text-align: center; background-color: white">
                        <textarea id="answer" name="answer" cols="40" rows="20" value="" readonly></textarea>
                        <br/>
                    </tr>
                </table>
            </tr>
        <%}%>
        <br/><br/>
        <tr>
            <table width="59%" border="1" class="tableActions">
                <tr style="text-align: center; background-color: azure">
                    Ask a question!
                </tr>
                <br />
                <tr style="text-align: center; background-color: white">
                    <form method="post" action="messageInput">
                        <input type="hidden" name="login" value="<%=request.getAttribute("login")%>"/>
                        <input type="hidden" name="rights" value="<%=request.getAttribute("rights")%>"/>
                        <input type="hidden" id="userLogin" name="userLogin" value=""/>

                        <textarea id="newQuestion" name="newQuestion" cols="40" rows="20" value=""></textarea>
                        <br/>
                        <input name="action" type="submit" value="Ask" class="ilButton">
                    </form>
                </tr>
            </table>
        </tr>
        </td>
        </tr>
    </table>

    <form action="userInfoChange" method="post" class="form">
    </form>
</center>
</body>
</html>