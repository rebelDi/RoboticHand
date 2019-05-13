package roboticHand.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Query;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import roboticHand.DAO.UserRepository;
import roboticHand.Model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserRepository {

    @Override
    public boolean login(String login, String password) {
        Session session = openSession();
        Query q = session.createQuery("SELECT login FROM User WHERE login = '" + login +
                "' AND password = '" + password +"'");
        List users = q.list();
        session.close();
        return !users.isEmpty();
    }

    @Override
    public boolean signUp(User user) {
        Session session = openSession();
        session.beginTransaction();
        try {
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return true;
        }catch (Exception e){
            session.close();
            return false;
        }
    }

    @Override
    public boolean checkPassword(String password, String oldLogin) {
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE login = '" + oldLogin +
                "' AND password = '" + password +"'");
        List users = q.list();
        session.close();
        return !users.isEmpty();
    }

    @Override
    public boolean checkAnswer(String answer, String oldLogin) {
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE login = '" + oldLogin +
                "' AND secretAnswer = '" + answer +"'");
        List users = q.list();
        session.close();
        return !users.isEmpty();
    }

    @Override
    public String edit(String oldLogin, User user) {
        if(getUserInfo(user.getLogin()) != null){
            return "Login is taken";
        }else {
            Session session = openSession();
            try {
                User tempUser = getUserInfo(oldLogin);
                tempUser.setLogin(user.getLogin());
                tempUser.setPassword(user.getPassword());
                tempUser.setName(user.getName());
                tempUser.setSurname(user.getSurname());
                tempUser.setSecretQuestion(user.getSecretQuestion());
                session.beginTransaction();
                session.update(tempUser);
                session.getTransaction().commit();
                session.close();
            }catch (Exception e){
                session.close();
                return "Something went wrong";
            }
        }
        return "";
    }

    @Override
    public void editRights(User user) {
        if(getUserInfo(user.getLogin()) != null){
            Session session = openSession();
            try {
                User tempUser = getUserInfo(user.getLogin());
                tempUser.setRights(user.getRights());
                session.beginTransaction();
                session.update(tempUser);
                session.getTransaction().commit();
                session.close();
            }catch (Exception ignored){
                session.close();
            }
        }
    }

    @Override
    public User getUserInfo(String login) {
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE login = '" + login +"'");
        List<User> users = q.list();
        session.close();
        if(users.size() >= 1) {
            return users.get(0);
        }else {
            return null;
        }
    }

    @Override
    public String getRights(String login){
        Session session = openSession();
        Query q = session.createQuery("SELECT rights FROM User WHERE login = '" + login + "'");
        List rights = q.list();
        session.close();
        return rights.get(0).toString();
    }

    @Override
    public void getEveryoneExceptSuperAdmin(HttpServletRequest request) {
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE rights != 'S'");
        List users = q.list();
        saveToSession(users, request);
        session.close();
    }

    @Override
    public void getUsersInWaitingList(HttpServletRequest request) {
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE rights = '0'");
        List users = q.list();
        saveToSession(users, request);
        session.close();
    }

    @Override
    public void getAllUsers(HttpServletRequest request) {
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE rights = 'U' OR rights = 'B' OR rights = '0'");
        List users = q.list();
        saveToSession(users, request);
        session.close();
    }

    private Session openSession(){
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory.openSession();
    }

    private void saveToSession(List dbUsers, HttpServletRequest request){
        ArrayList<User> users = new ArrayList<>();
        for(Object user : dbUsers){
            User actionNormal = (User) user;
            users.add(actionNormal);
        }

        HttpSession session = request.getSession();
        Gson gson = new GsonBuilder().create();
        String usersToJson = gson.toJson(users);
        session.setAttribute("users", usersToJson);
    }
}
