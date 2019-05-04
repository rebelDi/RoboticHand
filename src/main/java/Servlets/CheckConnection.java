package Servlets;

import Presenters.ArduinoProcessor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(
        name = "checkConnection",
        urlPatterns = "/checkConnection"
)
public class CheckConnection extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String answer = ArduinoProcessor.checkConnection();
        if(!answer.equals("Connected")) {
            resp.getWriter().write("Not Connected");
        }else {
            resp.getWriter().write(answer);
        }
    }
}

