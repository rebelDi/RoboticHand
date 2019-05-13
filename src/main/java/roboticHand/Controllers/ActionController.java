package roboticHand.Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roboticHand.DAO.ActionRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/action")
public class ActionController {
    @Autowired
    private ActionRepository actionRepository;

    @GetMapping(value = "/panel")
    public String controlPanelRedirect(HttpServletRequest request, Model model){
        actionRepository.getAllActions(request);
        return "main";
    }

    @PostMapping(value = "/checkConnection")
    public ResponseEntity<?> checkConnection(){
        String flag = new ArduinoController().sendData("Hello");
        if(flag.equals("Connected")) {
            return ResponseEntity.ok("");
        }else{
            return ResponseEntity.badRequest().body("");
        }
    }


    @RequestMapping(value = "/sendData", method = RequestMethod.POST)
    public void sendData(@RequestParam String queryData, HttpServletRequest request){
        JsonObject jsonObject = new Gson().fromJson(queryData, JsonObject.class);

        //Get all actions
        JsonArray jsonArray = jsonObject.get("actions").getAsJsonArray();
        String[] actions = new String[jsonArray.size()];
        for(int i = 0; i < actions.length; i++){
            actions[i] = jsonArray.get(i).getAsString();
        }

        jsonArray = jsonObject.get("values").getAsJsonArray();
        String[] values = new String[jsonArray.size()];
        for(int i = 0; i < values.length; i++){
            values[i] = jsonArray.get(i).getAsString();
        }

        actionRepository.sendData(actions, values, request);
    }
}
