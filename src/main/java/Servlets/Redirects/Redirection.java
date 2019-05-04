package Servlets.Redirects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "redirection",
        urlPatterns = "/redirection"
)
public class Redirection extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String link = req.getParameter("where");
        RequestDispatcher view;
        if(link.equals("index")){
            view = req.getRequestDispatcher(link + ".html");
        }else {
            view = req.getRequestDispatcher("Views/" + link + ".html");
        }
        view.forward(req, response);
    }
}