package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Tools.*;

public class Manager {
    private ArrayList <String> usernames;
    private ArrayList <String> nickNames;
    private ArrayList <User> usersByClasses;
    private ArrayList <User> forServer;
    String currUser = "";
    public Manager(){
        this.usernames = new ArrayList<>();
        this.usersByClasses = new ArrayList<>();
        this.nickNames = new ArrayList<>();
        this.forServer = new ArrayList<>();
    }
    private User findUserByName(String name){
        for (User usersByClass : usersByClasses) {
            if (usersByClass.getUsername().equals(name))
                return usersByClass;
        }
        return null;
    }
    public void addUser1(String username, String password,String password2, String email, String nickname){
        Pattern emailPattern = Pattern.compile("(.+?)@(.+?).com");
        Matcher emailMatcher = emailPattern.matcher(email);
        if (!username.matches("^[a-zA-Z0-9_]*$"))
            System.out.println(CheckErrorsAndMessages.WRONG_USERNAME_WHILE_MAKING_USER);
        else if (usernames.contains(username))
            System.out.println(CheckErrorsAndMessages.USERNAME_ِDUPLICATION);
        else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,}$")){
            if (password.length() < 8)
                System.out.println(CheckErrorsAndMessages.SHORT_PASSWORD);
            else
                System.out.println(CheckErrorsAndMessages.LOW_LEVEL_SECURITY_PASSWORD);
        }
        else if (!password.equals(password2))
            System.out.println(CheckErrorsAndMessages.FAILURE_PASSWORD_CONFIRMATION);
        else if (!emailMatcher.matches())
            System.out.println(CheckErrorsAndMessages.WRONG_EMAIL);
        else if (nickNames.contains(nickname)){
            System.out.println(CheckErrorsAndMessages.NICKNAME_DUPLICATION);
        }
        else {
            usernames.add(username);
            nickNames.add(nickname);
            User user = new User(username,password,nickname,email);
            forServer.add(user);
            currUser = username;
            selectActive(user);
            selectPassive(user);
            forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
            UserJsonFileWriter.writeUsersToFile(forServer,"Sever.json");
            usersByClasses.add(user);
            forServer.clear();
        }
    }
    public void addUser2(String username, String email, String nickname){
        Pattern emailPattern = Pattern.compile("(\\S+)@(\\S+).com");
        Matcher emailMatcher = emailPattern.matcher(email);
        if (!username.matches("^[a-zA-Z0-9_]*$"))
            System.out.println(CheckErrorsAndMessages.WRONG_USERNAME_WHILE_MAKING_USER);
        else if (usernames.contains(username))
            System.out.println(CheckErrorsAndMessages.USERNAME_ِDUPLICATION);
        else if (!emailMatcher.matches())
            System.out.println(CheckErrorsAndMessages.WRONG_EMAIL);
        else if (nickNames.contains(nickname)){
            System.out.println(CheckErrorsAndMessages.NICKNAME_DUPLICATION);
        }
        else {
            usernames.add(username);
            String password = RandomPasswordGenerator.generateRandomString();
            User user = new User(username,password,nickname,email);
            forServer.add(user);
            currUser = username;
            selectActive(user);
            selectPassive(user);
            forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
            UserJsonFileWriter.writeUsersToFile(forServer,"Sever.json");
            usersByClasses.add(user);
            System.out.println("Your random password : " + password);
            System.out.println("Please enter your password :");
            forServer.clear();
        }
    }
    public String returnRandPassword(){
        User user = findUserByName(currUser);
        if (user != null)
            return user.getPassword();
        return null;
    }
    private void selectActive(User user){
        String[] split1;
        if (user != null){
            for (String string : RandomCardSelector.randomMakerActive()) {
                split1 = string.split("'");
                ActiveCards activeCards1 = new ActiveCards(split1[0],split1[1],split1[2],split1[3],split1[4],split1[5],split1[6]);
                user.activeCards.add(activeCards1);
                Arrays.fill(split1,null);
            }
        }
    }
    private void selectPassive(User user){
        String[] split2;
        if (user != null){
            for (String string : RandomCardSelector.randomMakerPassive()) {
                split2 = string.split(",");
                PassiveCards passiveCards1 = new PassiveCards(split2[0],split2[1],split2[2],split2[3],split2[4]);
                user.passiveCards.add(passiveCards1);
                Arrays.fill(split2,null);
            }
        }
    }
    public void addSecurityWord(String num,String security){
        User user = findUserByName(currUser);
        if (user != null){
            user.setPasswordRecoveryQuestion(security,num);
        }
        currUser = "";
    }
    public void logIn(String name, String password){
        User user = null;
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
        for (User user1 : forServer) {
            if (user1 != null){
                if (user1.getUsername().equals(name))
                    user = user1;
            }
        }
        if (user != null){
            if (user.getPassword().equals(password)){
                System.out.println(CheckErrorsAndMessages.USER_LOGGED_IN_SUCCESSFULLY);
                user.loggedInSituation = true;
                currUser = name;
                UserJsonFileWriter.writeUsersToFile(forServer,"Sever.json");
            }
            else{
                System.out.println(CheckErrorsAndMessages.PASSWORD_AND_USERNAME_DOES_NOT_MATCH);
                user.lostLogIn++;
            }
        }
        else
            System.out.println(CheckErrorsAndMessages.USERNAME_DOES_NOT_EXIST);

        forServer.clear();
    }
    public int returnTime(){
        User user = findUserByName(currUser);
        if (user != null){
            return user.lostLogIn;
        }
        return 0;
    }
    public void forgotPass(String name, String num, String security, String newPass, String newPassConfirmation){
        User user = null;
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
        for (User user1 : forServer) {
            if (user1.getUsername().equals(name))
                user = user1;
        }
        if (user != null){
            if (!user.getPasswordRecoveryQuestionNum().equals(num)){
                System.out.println(CheckErrorsAndMessages.QUESTION_NUMBER_MISMATCH);
            }
            else if (!user.getPasswordRecoveryQuestion().equals(security)){
                System.out.println(CheckErrorsAndMessages.QUESTION_ANSWER_MISMATCH);
            }
            else if (!newPass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,}$")){
                if (newPass.length() < 8)
                    System.out.println(CheckErrorsAndMessages.SHORT_PASSWORD);
                else
                    System.out.println(CheckErrorsAndMessages.LOW_LEVEL_SECURITY_PASSWORD);
            }
            else if (!newPass.equals(newPassConfirmation)){
                System.out.println(CheckErrorsAndMessages.FAILURE_PASSWORD_CONFIRMATION);
            }
            else {
                System.out.println(CheckErrorsAndMessages.PASSWORD_HAS_CHANGED);
                user.setPassword(newPass);
            }
        }
        else
            System.out.println(CheckErrorsAndMessages.USERNAME_DOES_NOT_EXIST);
    }
    public void logOut(){
        boolean loggedIn = false;
        for (User usersByClass : usersByClasses) {
            if (usersByClass.loggedInSituation) {
                loggedIn = true;
                break;
            }
        }
        if (!loggedIn){
            System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
        }
        else {
            User user = findUserByName(currUser);
            if (user != null) {
                user.loggedInSituation = false;
                System.out.println(CheckErrorsAndMessages.USER_LOGGED_OUT_SUCCESSFULLY);
                currUser = "";
            }
        }
    }
    public void showInf(){
        boolean loggedIn = false;
        for (User usersByClass : usersByClasses) {
            if (usersByClass.loggedInSituation) {
                loggedIn = true;
                break;
            }
        }
        if (!loggedIn){
            System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
        }
        else {
            User user = findUserByName(currUser);
            if (user != null) {
                System.out.println(user.toString());
            }
        }
    }
    //******************************************************************************************************************
    //write in json
    public void changeUserName(String userName){
        boolean loggedIn = false;
        User user = null;
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
        for (User usersByClass : usersByClasses) {
            if (usersByClass.loggedInSituation) {
                loggedIn = true;
                break;
            }
        }
        for (User user1 : forServer) {
            if (user1.getUsername().equals(currUser))
                user = user1;
        }
        if (!loggedIn){
            System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
        }
        else {
            if (!userName.matches("^[a-zA-Z0-9_]*$")){
                System.out.println(CheckErrorsAndMessages.WRONG_USERNAME);
            }
            else if (usernames.contains(userName)){
                System.out.println(CheckErrorsAndMessages.USERNAME_ِDUPLICATION);
            }
            else {
                if (user != null) {
                    System.out.println(CheckErrorsAndMessages.USERNAME_HAS_CHANGED);
                    user.setUsername(userName);
                    UserJsonFileWriter.writeUsersToFile(forServer,"Sever.json");
                }
            }
        }
        forServer.clear();
    }
    public void changeNickName(String nickName){
        boolean loggedIn = false;
        User user = null;
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
        for (User usersByClass : usersByClasses) {
            if (usersByClass.loggedInSituation) {
                loggedIn = true;
                break;
            }
        }
        for (User user1 : forServer) {
            if (user1.getUsername().equals(currUser))
                user = user1;
        }
        if (!loggedIn){
            System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
        }
        else {
            if (nickNames.contains(nickName)){
                System.out.println(CheckErrorsAndMessages.NICKNAME_DUPLICATION);
            }
            else {
                if (user != null) {
                    System.out.println(CheckErrorsAndMessages.NICKNAME_HAS_CHANGED);
                    user.setNickname(nickName);
                    UserJsonFileWriter.writeUsersToFile(forServer,"Sever.json");
                }
            }
        }
        forServer.clear();
    }
    public void changePassword(String oldPass, String newPass){
        boolean loggedIn = false;
        User user = null;
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
        for (User usersByClass : usersByClasses) {
            if (usersByClass.loggedInSituation) {
                loggedIn = true;
                break;
            }
        }
        for (User user1 : forServer) {
            if (user1.getUsername().equals(currUser))
                user = user1;
        }
        if (!loggedIn){
            System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
        }
        else {
            if (user != null){
                if (!user.getPassword().equals(oldPass)){
                    System.out.println(CheckErrorsAndMessages.INCORRECT_CURRENT_PASSWORD);
                }
                else if (!newPass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,}$")){
                    if (newPass.length() < 8)
                        System.out.println(CheckErrorsAndMessages.SHORT_PASSWORD);
                    else
                        System.out.println(CheckErrorsAndMessages.LOW_LEVEL_SECURITY_PASSWORD);
                }
                else if (oldPass.equals(newPass)){
                    System.out.println(CheckErrorsAndMessages.NEW_PASSWORD_IS_NEEDED);
                }
                else {
                    System.out.println(CheckErrorsAndMessages.PASSWORD_HAS_CHANGED);
                    user.setPassword(newPass);
                    UserJsonFileWriter.writeUsersToFile(forServer,"Sever.json");
                }
            }
        }
        forServer.clear();
    }
    public void changeEmail(String email){
        boolean loggedIn = false;
        User user = findMemberInServer(currUser);
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
        for (User user1 : forServer) {
            if (user1.loggedInSituation) {
                loggedIn = true;
                break;
            }
        }
        if (!loggedIn){
            System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
        }
        else {
            Pattern emailPattern = Pattern.compile("(\\S+)@(\\S+).com");
            Matcher emailMatcher = emailPattern.matcher(email);
            if (emailMatcher.matches()){
                if (user != null){
                    System.out.println(CheckErrorsAndMessages.EMAIL_HAS_CHANGED);
                    user.setEmail(email);
                    UserJsonFileWriter.writeUsersToFile(forServer,"Sever.json");
                }
            }
            else
                System.out.println(CheckErrorsAndMessages.WRONG_EMAIL);
        }
        forServer.clear();
    }
    private User findMemberInServer(String name){
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
        for (User user : forServer) {
            if (user != null){
                if (user.getUsername().equals(name))
                    return user;
            }
        }
        forServer.clear();
        return null;
    }
    //******************************************************************************************************************
    public void enterMainMenu(){
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Sever.json"));
        for (User user1 : forServer) {
            if (user1 != null){
                if (user1.loggedInSituation){
                    System.out.println(CheckErrorsAndMessages.ENTERED_MAIN_MENU);
                    System.out.println(User.showMain());
                }
                else
                    System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
            }
        }
        forServer.clear();
    }
    public void showCards(){
        boolean loggedIn = false;
        forServer.addAll(UserJsonFileReader.readUsersFromFile("Server.json"));
        for (User user1 : forServer) {
            if (user1.loggedInSituation) {
                loggedIn = true;
                break;
            }
        }
        if (!loggedIn){
            System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
        }
        else {
            User user = null;
            for (User user1 : forServer) {
                if (user1 != null){
                    if (user1.getUsername().equals(currUser)){
                        user = user1;
                    }
                }
            }
            if (user != null){
                //
                for (ActiveCards activeCard : user.activeCards) {
                    System.out.println(activeCard.name + "'" + activeCard.cardDefenceAttack + "'" + activeCard.duration + "'" + activeCard.playerDamage + "'" + activeCard.cost + "'" + activeCard.upgradeCost + "'" + activeCard.upgradeLevel);
                }
                for (PassiveCards passiveCard : user.passiveCards) {
                    System.out.println(passiveCard.name + "'" + passiveCard.ability + "'" + passiveCard.cost + "'" + passiveCard.upgradeCost + "'" + passiveCard.upgradeLevel);
                }
            }
        }
        forServer.clear();
    }

}
