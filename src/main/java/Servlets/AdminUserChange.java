package Servlets;

import Additions.User;
import Presenters.AdminPresenter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        name = "userChange",
        urlPatterns = "/userChange"
)
public class AdminUserChange extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("login", req.getParameter("login"));
        req.setAttribute("rights", req.getParameter("rights"));
        String login = req.getParameter("loginU");
        String rights = req.getParameter("whatToChange");

        if("Admin".equals(rights)){
            AdminPresenter.updateUser(login, "A");
        }else{
            AdminPresenter.updateUser(login, "U");
        }

        ArrayList<User> users;
        RequestDispatcher view = req.getRequestDispatcher("index.html");
        if(req.getParameter("rights").equals("S")) {
            users = AdminPresenter.getEveryoneExceptSuperAdmin();
            req.setAttribute("users", users);
            view = req.getRequestDispatcher("Views/adminMain.jsp");
        }else if(req.getParameter("rights").equals("A")) {
            users = AdminPresenter.getAllUsers();
            req.setAttribute("users", users);
            view = req.getRequestDispatcher("Views/adminMain.jsp");
        }
        view.forward(req, resp);
    }
}

