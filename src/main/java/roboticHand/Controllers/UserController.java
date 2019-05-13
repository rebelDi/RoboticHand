package roboticHand.Controllers;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import roboticHand.DAO.QuestionRepository;
import roboticHand.DAO.UserRepository;
import roboticHand.Model.User;
import roboticHand.Tools.Encryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping(value = "/signUpRedirect")
    public String signUpRedirect(){
        return "signUp";
    }

    @GetMapping(value = "/logInRedirect")
    public String LogInRedirect(){
        return "index";
    }

    @GetMapping(value = "/userInfoRedirect")
    public String  userInfoRedirect(HttpServletRequest request, Model model){
        String login = (String) request.getSession().getAttribute("login");
        User user = userRepository.getUserInfo(login);
        model.addAttribute("login", user.getLogin());
        model.addAttribute("name", user.getName());
        model.addAttribute("surname", user.getSurname());
        model.addAttribute("question", user.getSecretQuestion());
        return "userInfo";
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authorizeUser(@RequestBody String[] form, HttpServletRequest request){
        boolean flag = userRepository.login(form[0], Encryptor.hashPassword(form[1]));
        if(flag){
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("login", form[0]);
            httpSession.setAttribute("rights", userRepository.getRights(form[0]));

            return ResponseEntity.ok("");
        }else{
            return ResponseEntity.badRequest().body("");
        }
    }

    @PostMapping(value = "/signUp")
    public ResponseEntity<?> signUpUser(@RequestBody String[] form){
        User user = new User();
        user.setLogin(form[0]);
        user.setPassword(Encryptor.hashPassword(form[1]));
        user.setName(form[2]);
        user.setSurname(form[3]);
        user.setRights('0');
        user.setSecretQuestion(form[4]);
        user.setSecretAnswer(Encryptor.hashPassword(form[5]));

        boolean flag = userRepository.signUp(user);
        if(flag){
            return ResponseEntity.ok("");
        }else{
            return ResponseEntity.badRequest().body("");
        }
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public String editUser(@RequestBody String[] newData, HttpServletRequest request){
        String oldLogin = (String) request.getSession().getAttribute("login");

        boolean flag;
        if(newData[1].length() != 0){
            flag = userRepository.checkPassword(Encryptor.hashPassword(newData[1]), oldLogin);
        }else if(newData[6].length() != 0){
            flag = userRepository.checkAnswer(Encryptor.hashPassword(newData[6]), oldLogin);
        }else {
            flag = userRepository.checkPassword(Encryptor.hashPassword(newData[1]), oldLogin);
        }

        if(flag) {
            User user = new User();
            user.setLogin(newData[0]);
            user.setPassword(Encryptor.hashPassword(newData[2]));
            user.setName(newData[3]);
            user.setSurname(newData[4]);
            user.setSecretQuestion(newData[5]);

            String error = userRepository.edit(oldLogin, user);
            if (error.equals("")) {
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("login", newData[0]);
                questionRepository.updateUser(oldLogin, newData[0]);
                return "You are all set up!";
            } else {
                return error;
            }
        }else {
            return "Wrong answer on question or incorrect old password";
        }
    }
}
