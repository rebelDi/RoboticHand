package roboticHand.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import javax.persistence.*;
//
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    private String login;
    private String password;
    private String name;
    private String surname;
    private Character rights;
    private String secretQuestion;
    private String secretAnswer;

//    public User() {
//    }

//    public User(String login, String password, String name, String surname, Character rights, String secretQuestion, String secretAnswer) {
//        this.login = login;
//        this.password = password;
//        this.name = name;
//        this.surname = surname;
//        this.rights = rights;
//        this.secretQuestion = secretQuestion;
//        this.secretAnswer = secretAnswer;
//    }

//    public User(String login, String password) {
//        this.login = login;
//        this.password = password;
//    }


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }


//    public User(String login, String password, String name, String surname, String secretQuestion, String secretAnswer) {
//        this.login = login;
//        this.password = password;
//        this.name = name;
//        this.surname = surname;
//        this.secretQuestion = secretQuestion;
//        this.secretAnswer = secretAnswer;
//    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Character getRights() {
        return rights;
    }

    public void setRights(Character rights) {
        this.rights = rights;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }
}
