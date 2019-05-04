package Presenters;

import Additions.ArduinoData;
import Additions.UDPClient;
import Models.ArduinoHandlerModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArduinoProcessor {
    public static void sendDataToArduino(String[] actions, String[] values, Object attribute){
        ArduinoHandlerModel model = new ArduinoHandlerModel(attribute);

        //Check if correct data was received
        int[] arduinoActions = model.getNormalActions(actions);
        boolean flag = model.checkActions(arduinoActions);

        try{
            for (String value : values) {
                Double num = Double.parseDouble(value);
            }
        }catch (NumberFormatException e){
            flag = false;
        }

        //If everything's alright, app should keep going
        if(flag) {
            int[] vals = model.processDataForArduino(actions, values);

            UDPClient udpClient = new UDPClient();
            Gson gson = new GsonBuilder().create();

            for (int i = 0; i < vals.length; i++) {
                String sendData = gson.toJson(new ArduinoData(arduinoActions[i], vals[i]));
                udpClient.sendData(sendData);
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Action " + actions[i] + " do " + vals[i] + " actual " + values[i]);
            }
            System.out.println("=========================");
        }
    }

    public static String checkConnection(){
        return new UDPClient().sendData("Hello");
    }
}
