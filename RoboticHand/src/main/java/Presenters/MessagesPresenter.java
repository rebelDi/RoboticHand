package Presenters;

import Additions.Message;
import Models.MessagesModel;

import java.util.ArrayList;

public class MessagesPresenter {
    public static ArrayList<Message> getUserMessages(String login){
        return MessagesModel.getUserMessages(login);
    }

    public static ArrayList<Message> getMessagesForAdmin(){
        return MessagesModel.getMessagesForAdmin();
    }

    public static ArrayList<Message> getAllMessages(){
        return MessagesModel.getAllMessages();
    }

    public static void updateAnswer(String user, String answer, String status, String question){
        MessagesModel.updateAnswer(user, answer, status, question);
    }

    public static void createQuestion(String login, String newQuestion) {
        MessagesModel.updateAnswer(login, newQuestion);
    }
}
