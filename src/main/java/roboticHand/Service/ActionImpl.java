package roboticHand.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import roboticHand.Controllers.ArduinoController;
import roboticHand.DAO.ActionRepository;
import roboticHand.Model.Action;
import roboticHand.Model.ArduinoData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ActionImpl implements ActionRepository {

    @Override
    public void getAllActions(HttpServletRequest request) {
        Session session = openSession();
        Query q = session.createQuery("SELECT _action FROM Action _action");
        List<Action> actions = q.list();
        saveToSession("actions", actions, request);
        session.close();
    }

    @Override
    public void edit(ArrayList<Action> actionsToEdit) {
        Session session = openSession();
        for(int i = 0; i < actionsToEdit.size(); i++){
            if(getActionByName(actionsToEdit.get(i).getActionLeap()) != null){
                try {
//                    Action tempAction = getActionByName(actionsToEdit.get(i).getActionLeap());
//                    tempAction.setHandAction(actionsToEdit.get(i).getHandAction());
//                    tempAction.setLeapMin(actionsToEdit.get(i).getLeapMin());
//                    tempAction.setLeapMax(actionsToEdit.get(i).getLeapMax());
//                    tempAction.setServoDirection(actionsToEdit.get(i).getServoDirection());
//                    tempAction.setServoMin(actionsToEdit.get(i).getServoMin());
//                    tempAction.setServoMax(actionsToEdit.get(i).getServoMax());
//                    tempAction.setAvailability(actionsToEdit.get(i).getAvailability());
                    session.beginTransaction();
                    session.update(actionsToEdit.get(i));
                    session.getTransaction().commit();
                }catch (Exception e){
                    session.close();
                }
            }
        }
        session.close();


//        if(getActionByName(action.getActionLeap()) == null){
//
//        }else {
//            Session session = openSession();
//            try {
//                Action tempAction = getActionByName(action.getActionLeap());
//                tempAction.setHandAction(action.getHandAction());
//                tempAction.setLeapMin(action.getLeapMin());
//                tempAction.setLeapMax(action.getLeapMax());
//                tempAction.setServoDirection(action.getServoDirection());
//                tempAction.setServoMin(action.getServoMin());
//                tempAction.setServoMax(action.getServoMax());
//                tempAction.setAvailability(action.getAvailability());
//                session.beginTransaction();
//                session.update(tempAction);
//                session.getTransaction().commit();
//            }catch (Exception e){
//                session.close();
//
//            }
//            session.close();
//        }
    }

    @Override
    public Action getActionByName(String name) {
        Session session = openSession();
        Query q = session.createQuery("SELECT _action FROM Action _action WHERE actionLeap='" + name + "'");
        List<Action> actions = q.list();
        session.close();
        if(actions.size() >= 1) {
            return actions.get(0);
        }else {
            return null;
        }
    }

    private Session openSession(){
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory.openSession();
    }

    private void saveToSession(String name, List actions, HttpServletRequest request){
        ArrayList<Action> actionNormals = new ArrayList<>();
        for(Object action : actions){
            Action actionNormal = (Action) action;
            actionNormals.add(actionNormal);
        }

        HttpSession session = request.getSession();
        Gson gson = new GsonBuilder().create();
        String actionsToJson = gson.toJson(actionNormals);
        session.setAttribute(name, actionsToJson);
    }

    @Override
    public void sendData(String[] actions, String[] values, HttpServletRequest request) {
        String actionsJson = (String) request.getSession().getAttribute("actions");
        Gson gson = new Gson();
        ArrayList<Action> action = gson.fromJson(actionsJson, new TypeToken<List<Action>>() {}.getType());
        ArduinoController arduinoController = new ArduinoController(action);
        //Check if correct data was received
        int[] arduinoActions = arduinoController.getNormalActions(actions);
        boolean flag = arduinoController.checkActions(arduinoActions);

        try{
            for (String value : values) {
                Double num = Double.parseDouble(value);
            }
        }catch (NumberFormatException e){
            flag = false;
        }

        //If everything's alright, app should keep going
        if(flag) {
            int[] vals = arduinoController.processDataForArduino(actions, values);
            gson = new GsonBuilder().create();
            for (int i = 0; i < vals.length; i++) {
                String sendData = gson.toJson(new ArduinoData(arduinoActions[i], vals[i]));
                arduinoController.sendData(sendData);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Action " + actions[i] + " do " + vals[i] + " actual " + values[i]);
            }
            System.out.println("=========================");
        }

    }
}
