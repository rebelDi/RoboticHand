package Servlets;

import Presenters.UserPresenter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "authorize",
        urlPatterns = "/authorize"
)
public class Authorize extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String n = request.getParameter("username");
        String p = request.getParameter("userpass");

        if(UserPresenter.validate(n, p)){
            request.setAttribute("login", n);
            request.setAttribute("rights", UserPresenter.getRightsByLogin(n));
            RequestDispatcher rd = request.getRequestDispatcher("mainredirect");
            rd.forward(request,response);
        }else{
            response.setContentType("text/html");

            out.println("<script type=\"text/javascript\">");
            out.println("alert('Something is incorrect');");
            out.println("location='index.html';");
            out.println("</script>");

            RequestDispatcher rd=request.getRequestDispatcher("index.html");
            rd.include(request,response);
        }
        out.close();
    }
}
