package Servlets;

import Additions.User;
import Presenters.AdminPresenter;
import Presenters.UserPresenter;

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
        name = "userChange",
        urlPatterns = "/userChange"
)
public class AdminUserChange extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("loginU");
        String rights = req.getParameter("whatToChange");

        HttpSession httpSession = req.getSession();
        String userRights = (String) httpSession.getAttribute("rights");

        if("Admin".equals(rights)){
            AdminPresenter.updateUser(login, "A");
        }else{
            AdminPresenter.updateUser(login, "U");
        }

        ArrayList<User> users;
        RequestDispatcher view = req.getRequestDispatcher("index.html");
        if(userRights.equals("S")) {
            users = AdminPresenter.getEveryoneExceptSuperAdmin();
            req.setAttribute("users", users);
            view = req.getRequestDispatcher("Views/adminUsers.jsp");
        }else if(userRights.equals("A")) {
            users = AdminPresenter.getAllUsers();
            req.setAttribute("users", users);
            view = req.getRequestDispatcher("Views/adminUsers.jsp");
        }
        view.forward(req, resp);
    }
}

