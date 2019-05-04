package Servlets;

import Additions.Action;
import Additions.User;
import Presenters.ActionsPresenter;
import Presenters.AdminPresenter;
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
        name = "imitatorChange",
        urlPatterns = "/imitatorChange"
)
public class AdminImitatorChange extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        Action action = new Action(req.getParameter("name"), Integer.parseInt(req.getParameter("servoNum")),
                Integer.parseInt(req.getParameter("leapMin")), Integer.parseInt(req.getParameter("leapMax")),
                        Integer.parseInt(req.getParameter("servoDirection")), Integer.parseInt(req.getParameter("servoMin")),
                                Integer.parseInt(req.getParameter("servoMax")), Integer.parseInt(req.getParameter("availability")));
        ActionsPresenter.changeAction(action);
        ArrayList<Action> actions = ActionsPresenter.getActionsNameAndAvailability();

        HttpSession session = req.getSession();
        Gson gson = new GsonBuilder().create();
        String actionsToJson = gson.toJson(actions);
        session.setAttribute("actions", actionsToJson);
    }
}

