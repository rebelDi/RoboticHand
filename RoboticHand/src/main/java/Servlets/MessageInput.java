package Servlets;

import Additions.Message;
import Presenters.MessagesPresenter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        name = "messageInput",
        urlPatterns = "/messageInput"
)
public class MessageInput extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("login", req.getParameter("login"));
        req.setAttribute("rights", req.getParameter("rights"));

        String action = req.getParameter("action");

        if("Ask".equals(action)){
            String newQuestion = req.getParameter("newQuestion");
            MessagesPresenter.createQuestion(req.getParameter("login"), newQuestion);

        }else if("Send".equals(action)){
            String answer = req.getParameter("answer");
            String userLogin = req.getParameter("userLogin");
            String userQuestion = req.getParameter("userQ");
            String status = "N";
            if(!answer.equals("No answer yet")){
                status = "A";
            }
            MessagesPresenter.updateAnswer(userLogin, answer, status, userQuestion);
        }

        ArrayList<Message> messages;
        RequestDispatcher view;
        if("S".equals(req.getAttribute("rights"))){
            messages = MessagesPresenter.getAllMessages();
            req.setAttribute("messages", messages);
            view = req.getRequestDispatcher("Views/messagesA.jsp");
        }else if("A".equals(req.getAttribute("rights"))){
            messages = MessagesPresenter.getMessagesForAdmin();
            req.setAttribute("messages", messages);
            view = req.getRequestDispatcher("Views/messagesA.jsp");
        }else{
            messages = MessagesPresenter.getUserMessages(req.getParameter("login"));
            req.setAttribute("messages", messages);
            view = req.getRequestDispatcher("Views/messagesU.jsp");
        }
        view.forward(req, resp);
    }
}
