package roboticHand.DAO;

//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import roboticHand.Model.Action;

import javax.servlet.http.HttpServletRequest;

@Repository
public interface ActionRepository {
    void getAllActions(HttpServletRequest request);
    void sendData(String[] actions, String[] values, HttpServletRequest request);
    String edit(Action action);
    Action getActionByName(String name);
}