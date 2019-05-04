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
        name = "authorize",
        urlPatterns = "/authorize"
)
public class Authorize extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String user = req.getParameter("username");
        String password = req.getParameter("userpass");

        if(UserPresenter.validate(user, password)){
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("login", user);
            httpSession.setAttribute("rights", UserPresenter.getRightsByLogin(user));

            req.setAttribute("action", "Control Panel");
            RequestDispatcher rd = req.getRequestDispatcher("headerRedirect");
            rd.forward(req,response);
        }else{
            response.setContentType("text/html");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Something is incorrect');");
            out.println("location='index.html';");
            out.println("</script>");

            RequestDispatcher rd = req.getRequestDispatcher("index.html");
            rd.include(req,response);
        }
        out.close();
    }
}
