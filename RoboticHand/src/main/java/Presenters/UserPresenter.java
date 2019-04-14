package Presenters;

import Additions.PasswordSecurity;
import Additions.User;
import Models.UserModel;

public class UserPresenter {
    public static boolean validate(String name,String pass){
        return UserModel.validate(name, pass);
    }

    public static boolean signUp(String login, String password, String name, String surname, String secretQuestion, String secretAnswer){
        return UserModel.signUp(login, password, name, surname, secretQuestion, secretAnswer);
    }

    public static boolean updateUser(String oldLogin, String login, String password, String name, String surname){
        return UserModel.updateUserInfo(oldLogin, login, password, name, surname);
    }

    public static boolean checkForRepetitiveLogin(String login){
        return UserModel.checkForRepetitiveLogin(login);
    }

    public static String getRightsByLogin(String login){
        return UserModel.getRightsByLogin(login);
    }

    public static User getAllInfoAboutUser(String login){
        return UserModel.getInfoAboutUser(login);
    }

    public static boolean validatePasswordAnswer(String login, String password, String answer){
        return UserModel.validatePasswordAnswer(login, password, answer);
    }

    public static String hashPassword(String password){
        return PasswordSecurity.hashPassword(password);
    }
}
