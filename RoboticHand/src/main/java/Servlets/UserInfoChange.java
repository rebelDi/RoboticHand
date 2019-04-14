package Servlets;

import Additions.User;
import Presenters.UserPresenter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "userInfoChange",
        urlPatterns = "/userInfoChange"
)
public class UserInfoChange extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        request.setAttribute("rights", request.getParameter("rights"));
        String login = request.getParameter("loginU");
        String oldPassword = request.getParameter("oldPassword");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String answer = request.getParameter("answer");

        String rightLogin = request.getParameter("login");
        if(!password.equals(confirmPassword)){
            request.setAttribute("error", "Passwords are not matching!");
        }else if(!UserPresenter.validatePasswordAnswer(request.getParameter("login"), oldPassword, answer)) {
            request.setAttribute("error", "Old password or secret answer is wrong!");
        }else if(!login.equals(rightLogin)) {
            if (!(UserPresenter.checkForRepetitiveLogin(login))) {
                if (UserPresenter.updateUser(request.getParameter("login"), login, password, name, surname)) {
                    request.setAttribute("error", "You are all settled!");
                    rightLogin = login;
                } else {
                    request.setAttribute("error", "Something went wrong, try again later!");
                }
            }
        }else{
            if (UserPresenter.updateUser(request.getParameter("login"), login, password, name, surname)) {
                rightLogin = login;
                request.setAttribute("error", "You are all settled!");
            } else {
                request.setAttribute("error", "Something went wrong, try again later!");
            }
        }
        User user = UserPresenter.getAllInfoAboutUser(rightLogin);
        request.setAttribute("login", rightLogin);
        request.setAttribute("user", user);
        RequestDispatcher view = request.getRequestDispatcher("Views/userInfo.jsp");
        view.forward(request, resp);
    }
}

