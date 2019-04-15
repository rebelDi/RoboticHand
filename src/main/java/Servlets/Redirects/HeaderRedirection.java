package Servlets.Redirects;

import Additions.Action;
import Additions.Message;
import Additions.User;
import Presenters.ActionsPresenter;
import Presenters.AdminPresenter;
import Presenters.MessagesPresenter;
import Presenters.UserPresenter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        name = "headerRedirect",
        urlPatterns = "/headerRedirect"
)
public class HeaderRedirection extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String rights = req.getParameter("rights");
        req.setAttribute("login", req.getParameter("login"));
        req.setAttribute("rights", rights);

        RequestDispatcher view;
        if("Admin".equals(action)){
            ArrayList<User> users;
            if(req.getParameter("rights").equals("S")) {
                users = AdminPresenter.getEveryoneExceptSuperAdmin();
                req.setAttribute("users", users);
                view = req.getRequestDispatcher("Views/adminMain.jsp");
            }else if(req.getParameter("rights").equals("A")) {
                users = AdminPresenter.getAllUsers();
                req.setAttribute("users", users);
                view = req.getRequestDispatcher("Views/adminMain.jsp");
            }else{
                ArrayList<Action> actions = ActionsPresenter.getAllActions();
                req.setAttribute("actions", actions);
                view = req.getRequestDispatcher("Views/main.jsp");
                view.forward(req, resp);
            }
            view.forward(req, resp);
        }else if("Control Panel".equals(action)){
            ArrayList<Action> actions = ActionsPresenter.getAllActions();
            req.setAttribute("actions", actions);
            view = req.getRequestDispatcher("Views/main.jsp");
            view.forward(req, resp);
        }else if("User Info".equals(action)) {
            User user = UserPresenter.getAllInfoAboutUser(req.getParameter("login"));
            req.setAttribute("user", user);
            view = req.getRequestDispatcher("Views/userInfo.jsp");
            view.forward(req, resp);
        }else if("Messages".equals(action)){
            ArrayList<Message> messages;
            if("S".equals(rights)){
                messages = MessagesPresenter.getAllMessages();
                req.setAttribute("messages", messages);
                view = req.getRequestDispatcher("Views/messagesA.jsp");
            }else if("A".equals(rights)){
                messages = MessagesPresenter.getMessagesForAdmin();
                req.setAttribute("messages", messages);
                view = req.getRequestDispatcher("Views/messagesA.jsp");
            }else{
                messages = MessagesPresenter.getUserMessages(req.getParameter("login"));
                req.setAttribute("messages", messages);
                view = req.getRequestDispatcher("Views/messagesU.jsp");
            }
            view.forward(req, resp);
        }else if("Exit".equals(action)){
            view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
        }
    }
}
