package roboticHand.Controllers;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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


    @RequestMapping(value = "/actionEdit", method = RequestMethod.POST)
    public String editAction(@RequestParam String actions, HttpServletRequest request){
        ArrayList<Action> actionsToEdit = new ArrayList<>();
        JsonObject jsonObject = new Gson().fromJson(actions, JsonObject.class);

        //Get actions name
        JsonArray jsonActionsName = jsonObject.get("actionsName").getAsJsonArray();
        JsonArray jsonServoNumbers= jsonObject.get("servosNum").getAsJsonArray();
        JsonArray jsonLeapMin = jsonObject.get("leapsMin").getAsJsonArray();
        JsonArray jsonLeapMax = jsonObject.get("leapsMax").getAsJsonArray();
        JsonArray jsonServoDirections = jsonObject.get("servosD").getAsJsonArray();
        JsonArray jsonServoMins = jsonObject.get("servosMin").getAsJsonArray();
        JsonArray jsonServoMax = jsonObject.get("servosMax").getAsJsonArray();
        JsonArray jsonAvailabilities = jsonObject.get("avails").getAsJsonArray();

        for(int i = 0; i < jsonActionsName.size(); i++){
            Action action = new Action();
            action.setActionLeap(jsonActionsName.get(i).getAsString());
            action.setHandAction(Integer.parseInt(jsonServoNumbers.get(i).getAsString()));
            action.setLeapMin(Integer.parseInt(jsonLeapMin.get(i).getAsString()));
            action.setLeapMax(Integer.parseInt(jsonLeapMax.get(i).getAsString()));
            action.setServoDirection(Integer.parseInt(jsonServoDirections.get(i).getAsString()));
            action.setServoMin(Integer.parseInt(jsonServoMins.get(i).getAsString()));
            action.setServoMax(Integer.parseInt(jsonServoMax.get(i).getAsString()));
            action.setAvailability(Integer.parseInt(jsonAvailabilities.get(i).getAsString()));
            actionsToEdit.add(action);
        }
        actionRepository.edit(actionsToEdit);
        actionRepository.getAllActions(request);
        return "admin/actionEdit";
    }
}
