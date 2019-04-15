package Additions;

public class Message {
    private String user;
    private String question;
    private String answer;
    private String status;

    public Message(String user, String question, String answer, String status) {
        this.user = user;
        this.question = question;
        this.answer = answer;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getStatus() {
        return status;
    }
}
