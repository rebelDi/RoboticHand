package Additions;

public class Action {
    private String name;
    private int arduinoIndex;
    private int leapMinimum;
    private int leapMaximum;
    private int servoDirection;
    private int servoMinimum;
    private int servoMaximum;
    private int availability;

    public Action(String name, int arduinoIndex, int leapMinimum, int leapMaximum, int servoDirection, int servoMinimum, int servoMaximum, int availability) {
        this.name = name;
        this.arduinoIndex = arduinoIndex;
        this.leapMinimum = leapMinimum;
        this.leapMaximum = leapMaximum;
        this.servoDirection = servoDirection;
        this.servoMinimum = servoMinimum;
        this.servoMaximum = servoMaximum;
        this.availability = availability;
    }


    public String getName() {
        return name;
    }

    public int getArduinoIndex() {
        return arduinoIndex;
    }

    public int getLeapMinimum() {
        return leapMinimum;
    }

    public int getLeapMaximum() {
        return leapMaximum;
    }

    public int getServoDirection() {
        return servoDirection;
    }

    public int getServoMinimum() {
        return servoMinimum;
    }

    public int getServoMaximum() {
        return servoMaximum;
    }

    public int getAvailability() {
        return availability;
    }
}
