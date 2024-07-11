package org.example;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String passwordRecoveryQuestion;
    private String passwordRecoveryQuestionNum;
    private String level;
    private String HP;
    private String XP;
    private String gold;
    boolean loggedInSituation = false;
    int lostLogIn = 0;
    public ArrayList<ActiveCards> activeCards;
    public ArrayList <PassiveCards> passiveCards;
    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.level = "1";
        this.HP = "100";
        this.XP = "0";
        this.gold = "1000";
        this.activeCards = new ArrayList<>();
        this.passiveCards = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPasswordRecoveryQuestion(String passwordRecoveryQuestion, String passwordRecoveryQuestionNum) {
        this.passwordRecoveryQuestion = passwordRecoveryQuestion;
        this.passwordRecoveryQuestionNum = passwordRecoveryQuestionNum;
    }
    public String getPasswordRecoveryQuestion() {
        return passwordRecoveryQuestion;
    }
    public String getPasswordRecoveryQuestionNum() {
        return passwordRecoveryQuestionNum;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public static String ShowSecQuestion() {
        return
                "User created successfully. Please choose a security question :\n" +
                        "* 1-What is your father’s name ?\n" +
                        "* 2-What is your favourite color ?\n" +
                        "* 3-What was the name of your first pet?";
    }
    public static String showMain() {
        return
                "You have entered Main Menu. Please choose your next move :\n" +
                        "* 1-Game Menu \n" +
                        "* 2-Show Cards \n" +
                        "* 3-Game History \n" +
                        "* 4-Shop Menu \n" +
                        "* 5-Profile Menu \n" +
                        "* 6-Log Out \n";
    }
    public static String chooseMode() {
        return
                "Choose your mode that you want to play :\n" +
                        "* 1- 1 VS 1 :\n" +
                        "* 2- Gambling :\n";
    }
    @Override
    public String toString() {
        return
                        "username = " + username + '\'' +
                        "password = " + password + '\'' +
                        "nickname = " + nickname + '\'' +
                        "email = " + email + '\'';
    }
}
