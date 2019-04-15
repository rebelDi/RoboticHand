package Presenters;

import org.junit.Assert;

public class UserPresenterTest {

    @org.junit.Test
    public void validatePasswordRight() {
        String login = "Diana";
        String password = "";
        Assert.assertTrue(UserPresenter.validate(login, password));
    }

    @org.junit.Test
    public void validatePasswordWrong() {
        String login = "Diana";
        String password = "password";
        Assert.assertFalse(UserPresenter.validate(login, password));
    }

    @org.junit.Test
    public void validatePasswordWrong2() {
        String login = "";
        String password = "";
        Assert.assertFalse(UserPresenter.validate(login, password));
    }

    @org.junit.Test
    public void validatePasswordAnswerRight() {
        String login = "Di";
        String password = "";
        String answer = "";
        Assert.assertTrue(UserPresenter.validatePasswordAnswer(login, password, answer));
    }

    @org.junit.Test
    public void validatePasswordAnswerWrong() {
        String login = "Di";
        String password = "";
        String answer = "M";
        Assert.assertFalse(UserPresenter.validatePasswordAnswer(login, password, answer));
    }

    @org.junit.Test
    public void getRightsByLoginExistingUser() {
        String login = "Diana";
        Assert.assertEquals("S" ,UserPresenter.getRightsByLogin(login));
    }

    @org.junit.Test
    public void getRightsByLoginNonExistingUser() {
        String login = "fgsdfsdgDianak";
        Assert.assertEquals("" , UserPresenter.getRightsByLogin(login));
    }

    @org.junit.Test
    public void signUpNewUser() {
        Assert.assertTrue(UserPresenter.signUp("newU", "password", "New",
                "Surname", "SecretQ", "SecretA"));
    }

    @org.junit.Test
    public void signUpExistingUser() {
        Assert.assertTrue(UserPresenter.signUp("new", "password", "New",
                "Surname", "SecretQ", "SecretA"));
    }

    @org.junit.Test
    public void updateUser(){
        Assert.assertTrue(UserPresenter.updateUser("inna", "new", "Okay13", "Kale", "Grass"));
    }
}
