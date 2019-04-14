package Servlets.Redirects;

import Additions.Action;
import Presenters.ActionsPresenter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        name = "mainRedirect",
        urlPatterns = "/mainredirect"
)
public class MainRedirect extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Action> actions = ActionsPresenter.getAllActions();

        resp.setContentType("text/html");
        req.setAttribute("actions", actions);
        req.setAttribute("login", req.getAttribute("login"));
        req.setAttribute("rights", req.getAttribute("rights"));
        RequestDispatcher view = req.getRequestDispatcher("Views/main.jsp");
        view.forward(req, resp);
    }
}
