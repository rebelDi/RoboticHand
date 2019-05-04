package Servlets;

import Presenters.UserPresenter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "adminPanel",
        urlPatterns = "/adminPanel"
)
public class AdminPanel extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String rights = (String) httpSession.getAttribute("rights");

        RequestDispatcher view;
        PrintWriter out = resp.getWriter();
        if(rights.equals("S") || rights.equals("A")) {
            view = req.getRequestDispatcher("adminMain.jsp");
        }else{
            out.println("<script type=\"text/javascript\">");
            out.println("alert('You are not an admin!');");
            out.println("location='main.jsp';");
            out.println("</script>");

            view = req.getRequestDispatcher("main.jsp");
        }
        view.forward(req, resp);
        out.close();
    }
}
