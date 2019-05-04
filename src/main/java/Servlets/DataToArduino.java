package Servlets;

import Additions.UDPClient;
import Presenters.ArduinoProcessor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(
        name = "dataToArduino",
        urlPatterns = "/dataToArduino"
)
public class DataToArduino extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String[] actions = req.getParameterValues("actions[]");
        String[] values = req.getParameterValues("value[]");
        int iteration = Integer.parseInt(req.getParameter("step"));
        boolean flag = true;

        //Every 20th iteration app checks for availability of Internet Connection
        if(iteration % 600 == 0){
            if((new UDPClient().sendData("Hello")).equals("Not Connected")){
                flag = false;
            }else {
                flag = true;
            }
        }

        if(flag) {
            HttpSession session = req.getSession();
            ArduinoProcessor.sendDataToArduino(actions, values, session.getAttribute("actions"));
        }
    }
}

