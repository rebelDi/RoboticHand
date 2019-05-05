package Presenters;

import Additions.User;
import Models.AdminModel;
import Models.UserModel;

import java.util.ArrayList;

public class AdminPresenter {
    public static ArrayList<User> getAllUsers(){
        return AdminModel.getAllUsers();
    }

    public static ArrayList<User> getEveryoneExceptSuperAdmin(){
        return AdminModel.getEveryoneExceptSuperAdmin();
    }

    public static ArrayList<User> getUsersInWaitingList(){
        return AdminModel.getUsersInWaitingList();
    }

    public static void updateUser(String login, String rights){
        UserModel.updateUser(login, rights);
    }
}
