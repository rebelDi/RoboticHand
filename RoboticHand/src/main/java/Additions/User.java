package Additions;

public class User {
    private String login;
    private String password;
    private String name;
    private String surname;
    private Character rights;
    private String secretQuestion;
    private String secretAnswer;

    public User(String login, String password, String name, String surname, Character rights, String secretQuestion, String secretAnswer) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.rights = rights;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
    }

    public User(String login, String name, String surname, Character rights) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.rights = rights;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Character getRights() {
        return rights;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }
}
