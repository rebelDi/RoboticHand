package roboticHand.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Action {

    @Id
    private int handAction;

    private String actionLeap;
    private int leapMin;
    private int leapMax;
    private int servoDirection;
    private int servoMin;
    private int servoMax;
    private int availability;

    public Action() {
    }

    public int getHandAction() {
        return handAction;
    }

    public void setHandAction(int handAction) {
        this.handAction = handAction;
    }

    public String getActionLeap() {
        return actionLeap;
    }

    public void setActionLeap(String actionLeap) {
        this.actionLeap = actionLeap;
    }

    public int getLeapMin() {
        return leapMin;
    }

    public void setLeapMin(int leapMin) {
        this.leapMin = leapMin;
    }

    public int getLeapMax() {
        return leapMax;
    }

    public void setLeapMax(int leapMax) {
        this.leapMax = leapMax;
    }

    public int getServoDirection() {
        return servoDirection;
    }

    public void setServoDirection(int servoDirection) {
        this.servoDirection = servoDirection;
    }

    public int getServoMin() {
        return servoMin;
    }

    public void setServoMin(int servoMin) {
        this.servoMin = servoMin;
    }

    public int getServoMax() {
        return servoMax;
    }

    public void setServoMax(int servoMax) {
        this.servoMax = servoMax;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
