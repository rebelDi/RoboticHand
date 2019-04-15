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
        name = "signup",
        urlPatterns = "/signUp"
)
public class SignUp extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String login = request.getParameter("userName");
        String password = request.getParameter("userPass");
        String name = request.getParameter("userRName");
        String surname = request.getParameter("userSurname");
        String question = request.getParameter("userQuest");
        String answer = request.getParameter("userSAnswer");

        if (!(UserPresenter.checkForRepetitiveLogin(login))) {
            if (UserPresenter.signUp(login, password, name, surname, question, answer)) {
                response.setContentType("text/html");

                out.println("<script type=\"text/javascript\">");
                out.println("alert('You are all signed up!');");
                out.println("location='index.html';");
                out.println("</script>");

                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                rd.include(request, response);
            } else {
                response.setContentType("text/html");

                out.println("<script type=\"text/javascript\">");
                out.println("alert('Something went wrong, try again later!');");
                out.println("location='signUp.html';");
                out.println("</script>");

//                request.setAttribute("message", "Something went wrong, try again later!");
                RequestDispatcher rd = request.getRequestDispatcher("Views/signUp.html");
                rd.include(request, response);
            }
        } else {
            response.setContentType("text/html");

            out.println("<script type=\"text/javascript\">");
            out.println("alert('This login is already registered, sorry!');");
            out.println("document.getElementById('userName').value = " + login + ";");
            out.println("location='signUp.html';");
            out.println("</script>");

//            request.setAttribute("message", "This login is already registered, sorry!");
            RequestDispatcher rd = request.getRequestDispatcher("Views/signUp.html");
            rd.include(request, response);
        }
        out.close();
    }
}
