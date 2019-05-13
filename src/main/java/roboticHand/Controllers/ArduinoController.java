package roboticHand.Controllers;

import roboticHand.Model.Action;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class ArduinoController {
    private List<Action> dbData;

    public ArduinoController() {
    }

    public ArduinoController(List<Action> dbData) {
        this.dbData = dbData;
    }

    public String sendData(String dataQuery){
        try{
            byte[] b=(dataQuery.getBytes());
            byte[] receiveData = new byte[1024];

            InetAddress ip = InetAddress.getByName("192.168.0.111");

            DatagramSocket datagram = new DatagramSocket();

            DatagramPacket send = new DatagramPacket(b, b.length, ip, 8032);

            datagram.send(send);

            DatagramPacket rec = new DatagramPacket(receiveData, receiveData.length);

            datagram.setSoTimeout(6000);

            datagram.receive(rec);
            String modifiedSentence = new String(rec.getData());

            InetAddress returnIPAddress = rec.getAddress();

            int port = rec.getPort();

            System.out.println ("From server at: " + returnIPAddress + ":" + port);
            System.out.println("Message: '" + modifiedSentence + "'");
            modifiedSentence = modifiedSentence.replace("\u0000", "");

            datagram.close();
            return modifiedSentence;
        }catch(Exception e){
            System.out.println("Error:" + e);
        }
        return "Not Connected";
    }

    synchronized public int[] processDataForArduino(String[] actions, String[] values){
        int[] rightValues = new int[values.length];
        int[] valuesChanged = new int[values.length];

        for(int i = 0; i < values.length; i++){
            valuesChanged[i] = Math.round(Float.parseFloat(values[i]));
        }

        for(int i = 0; i < actions.length; i++){
            Action rightInfoAction = getActionInfoByServoName(actions[i]);

            float point = 180.0f / (rightInfoAction.getLeapMax() - rightInfoAction.getLeapMin());
            float val = valuesChanged[i];
//            if(rightInfoAction.getName().equals("Hand Roll")){
//                if(val >= 158){
//                    val *= -1;
//                }
//            }
            if (val > rightInfoAction.getLeapMax()){
                val = rightInfoAction.getLeapMax();
            }else if (val < rightInfoAction.getLeapMin()){
                val = rightInfoAction.getLeapMin();
            }
            float value = point * (val - rightInfoAction.getLeapMin());
            if(rightInfoAction.getServoDirection() == 0){
                value = 180.0f - value;
            }

            float determinant = (180.0f / (rightInfoAction.getServoMax() - rightInfoAction.getServoMin()));

            value = Math.abs(value / determinant - rightInfoAction.getServoMin());
            if(rightInfoAction.getActionLeap().equals("Hand Up/Down")){
                value -= 80;
                if(value < 80){
                    value = 80;
                }
            }
            rightValues[i] = Math.round(value);
        }
        return rightValues;
    }

    private Action getActionInfoByServoName(String name){
        for (Action dbDatum : dbData) {
            if (dbDatum.getActionLeap().equals(name)) {
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
                if (dbData.get(j).getActionLeap().equals(actions[i])) {
                    acts[i] = dbData.get(j).getHandAction();
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
