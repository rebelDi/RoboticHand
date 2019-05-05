package Servlets;

import Additions.User;
import Presenters.AdminPresenter;
import Presenters.UserPresenter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "userChange",
        urlPatterns = "/userChange"
)
public class AdminUserChange extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String rights = req.getParameter("rights");

        HttpSession httpSession = req.getSession();
        String userRights = (String) httpSession.getAttribute("rights");

        if(userRights.equals("A") && !rights.equals("A")){
            if("B".equals(rights) || "U".equals(rights)){
                AdminPresenter.updateUser(login, rights);
            }
        }else if(userRights.equals("S")){
            if("B".equals(rights) || "U".equals(rights) || "A".equals(rights)){
                AdminPresenter.updateUser(login, rights);
            }
        }
    }
}

