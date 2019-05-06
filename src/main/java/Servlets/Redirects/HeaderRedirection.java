package Servlets.Redirects;

import Additions.Action;
import Additions.Message;
import Additions.User;
import Presenters.ActionsPresenter;
import Presenters.AdminPresenter;
import Presenters.MessagesPresenter;
import Presenters.UserPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        if(action == null){
            action = (String) req.getAttribute("action");
        }

        HttpSession httpSession = req.getSession();
        String rights = (String) httpSession.getAttribute("rights");

        RequestDispatcher view;
        if("Users".equals(action) || "Admin".equals(action)){
            ArrayList<User> users;
            if(httpSession.getAttribute("rights").equals("S")) {
                users = AdminPresenter.getEveryoneExceptSuperAdmin();
                req.setAttribute("users", users);
                view = req.getRequestDispatcher("Views/adminUsers.jsp");
            }else if(httpSession.getAttribute("rights").equals("A")) {
                users = AdminPresenter.getAllUsers();
                req.setAttribute("users", users);
                view = req.getRequestDispatcher("Views/adminUsers.jsp");
            }else{
                saveActionsToSession(req);
                view = req.getRequestDispatcher("Views/main.jsp");
            }
            view.forward(req, resp);
        }else if("Imitator".equals(action)){
            if(httpSession.getAttribute("rights").equals("S")) {
                ArrayList<Action> actions = ActionsPresenter.getAllActions();
                req.setAttribute("actions", actions);
                view = req.getRequestDispatcher("Views/adminImitator.jsp");
            }else{
                saveActionsToSession(req);
                view = req.getRequestDispatcher("Views/main.jsp");
                view.forward(req, resp);
            }
            view.forward(req, resp);
        }else if("Control Panel".equals(action)){
            saveActionsToSession(req);
            view = req.getRequestDispatcher("Views/main.jsp");
            view.forward(req, resp);
        }else if("User Info".equals(action)) {
            User user = UserPresenter.getAllInfoAboutUser((String) httpSession.getAttribute("login"));
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
                messages = MessagesPresenter.getUserMessages((String) httpSession.getAttribute("login"));
                req.setAttribute("messages", messages);
                view = req.getRequestDispatcher("Views/messagesU.jsp");
            }
            view.forward(req, resp);
        }else if("Exit".equals(action) || "Log In".equals(action)){
            view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
        }else if("Sign Up".equals(action)){
            view = req.getRequestDispatcher("Views/signUp.html");
            view.forward(req, resp);
        }else if("waitingList".equals(action)){
            ArrayList<User> users;
            if(httpSession.getAttribute("rights").equals("S") || httpSession.getAttribute("rights").equals("A")) {
                users = AdminPresenter.getUsersInWaitingList();
                req.setAttribute("users", users);
                view = req.getRequestDispatcher("Views/adminUsers.jsp");
            }else{
                saveActionsToSession(req);
                view = req.getRequestDispatcher("Views/main.jsp");
            }
            view.forward(req, resp);
        }
    }

    static void saveActionsToSession(HttpServletRequest req) {
        ArrayList<Action> actions = ActionsPresenter.getActionsNameAndAvailability();

        HttpSession session = req.getSession();
        Gson gson = new GsonBuilder().create();
        String actionsToJson = gson.toJson(actions);
        session.setAttribute("actions", actionsToJson);
        req.setAttribute("actions", actions);
    }
}
