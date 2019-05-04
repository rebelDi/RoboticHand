package Models;

import Additions.Action;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class ArduinoHandlerModel {
    private List<Action> dbData;

    public ArduinoHandlerModel(Object actions) {
        String jsonActions = (String) actions;
        Gson gson = new Gson();
        dbData = gson.fromJson(jsonActions, new TypeToken<List<Action>>() {}.getType());
    }

    public int[] processDataForArduino(String[] actions, String[] values){
        int[] rightValues = new int[values.length];
        int[] valuesChanged = new int[values.length];

        for(int i = 0; i < values.length; i++){
            valuesChanged[i] = Math.round(Float.parseFloat(values[i]));
        }

        for(int i = 0; i < actions.length; i++){
            Action rightInfoAction = getActionInfoByServoName(actions[i]);
            float point = 180.0f / (rightInfoAction.getLeapMaximum() - rightInfoAction.getLeapMinimum());
            float val = valuesChanged[i];
            if(rightInfoAction.getName().equals("Hand Roll")){
                if(val >= 158){
                    val *= -1;
                }
            }
            if (val > rightInfoAction.getLeapMaximum()){
                val = rightInfoAction.getLeapMaximum();
            }else if (val < rightInfoAction.getLeapMinimum()){
                val = rightInfoAction.getLeapMinimum();
            }
            float value = point * (val - rightInfoAction.getLeapMinimum());
            if(rightInfoAction.getServoDirection() == 0){
                value = 180.0f - value;
            }

            float determinant = (180.0f / (rightInfoAction.getServoMaximum() - rightInfoAction.getServoMinimum()));
            if(rightInfoAction.getName().equals("Hand Up/Down")){
                determinant = 0.65f;
            }

            value = Math.abs(value / determinant - rightInfoAction.getServoMinimum());
            rightValues[i] = Math.round(value);
        }
        return rightValues;
    }

    private Action getActionInfoByServoName(String name){
        for (Action dbDatum : dbData) {
            if (dbDatum.getName().equals(name)) {
                return dbDatum;
            }
        }
        return null;
    }

    //Gets numbers of servomotors by name of actions
    public int[] getNormalActions(String[] actions) {
        int[] acts = new int[actions.length];

        for(int i = 0; i < acts.length; i++) {
            boolean flag = false;
            for (int j = 0; j < dbData.size(); j++) {
                if (dbData.get(j).getName().equals(actions[i])) {
                    acts[i] = dbData.get(j).getArduinoIndex();
                    flag = true;
                }
            }
            if(!flag){
                acts[i] = -100;
            }
        }
        return acts;
    }

    //Check if all actions were correctly written from database
    public boolean checkActions(int[] actions){
        for(int i = 0; i < actions.length; i++) {
            if(actions[i] == -100){
                return false;
            }
        }
        return true;
    }
}
