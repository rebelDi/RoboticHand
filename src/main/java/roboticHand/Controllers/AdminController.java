package roboticHand.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roboticHand.DAO.ActionRepository;
import roboticHand.DAO.UserRepository;
import roboticHand.Model.Action;
import roboticHand.Model.User;
import roboticHand.Tools.Encryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/users")
    public String getAllusers(HttpServletRequest request){
        String rights = (String) request.getSession().getAttribute("rights");

        if(rights.equals("S")) {
            userRepository.getEveryoneExceptSuperAdmin(request);
            return "adminUsers";
        }else if(rights.equals("A")) {
            userRepository.getAllUsers(request);
            return "adminUsers";
        }else{
            return "/";
        }
    }

    @PostMapping(value = "/userRightsEdit")
    public String changeUserRights(@RequestBody String[] user, HttpServletRequest request){
        User user1 = new User();
        user1.setLogin(user[0]);
        user1.setRights(user[1].charAt(0));

        String rights = (String) request.getSession().getAttribute("rights");

        if(rights.equals("S") || rights.equals("A")) {
            userRepository.editRights(user1);
            return "adminUsers";
        }else{
            return "/";
        }
    }

    @GetMapping(value = "/waitingList")
    public String showUsersInWaitingList(HttpServletRequest request){
        String rights = (String) request.getSession().getAttribute("rights");

        if(rights.equals("S") || rights.equals("A")) {
            userRepository.getUsersInWaitingList(request);
            return "adminUsers";
        }else{
            return "/";
        }
    }

    @GetMapping(value = "/actions")
    public String changeActions(){
        return "adminImitator";
    }

    @PostMapping(value = "/actionEdit")
    @ResponseBody
    public String editAction(@RequestBody String[] newData, HttpServletRequest request){
        Action action = new Action();
        action.setActionLeap(newData[0]);
        action.setHandAction(Integer.parseInt(newData[1]));
        action.setLeapMin(Integer.parseInt(newData[2]));
        action.setLeapMax(Integer.parseInt(newData[3]));
        action.setServoDirection(Integer.parseInt(newData[4]));
        action.setServoMin(Integer.parseInt(newData[5]));
        action.setServoMax(Integer.parseInt(newData[6]));
        action.setAvailability(Integer.parseInt(newData[7]));

        String error = actionRepository.edit(action);
        if (error.equals("")) {
            actionRepository.getAllActions(request);
            return "";
        } else {
            return error;
        }
    }
}
