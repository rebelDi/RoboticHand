package Presenters;

import Additions.Action;
import Models.ActionsModel;
import java.util.ArrayList;

public class ActionsPresenter {
    public static ArrayList<Action> getAllActions(){
        return ActionsModel.getAllActions();
    }
}
