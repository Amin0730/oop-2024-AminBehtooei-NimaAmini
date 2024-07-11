package org.example;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class InputProcessor {
    private Scanner scanner = new Scanner(System.in);
    private Manager manager;
    public InputProcessor(Manager manager) {
        this.manager = manager;
    }
    private Matcher getCommandMatcher(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }
    private void processMakeUser1(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty()){
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            }
            else {
                manager.addUser1(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
            }
        }
    }
    private void processMakeUser2(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty()){
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            }
            else {
                manager.addUser2(matcher.group(1),matcher.group(2),matcher.group(3));
            }
        }
    }
    private void processAddSecurityLetter(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty())
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            else {
                manager.addSecurityWord(matcher.group(1),matcher.group(2));
            }
        }
    }
    private boolean processCheckRandomPassword(String password){
        if (manager.checkPassword(password))
            return true;
        return false;
    }
    private void processLogIn(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty()){
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            }
            else
                manager.logIn(matcher.group(1),matcher.group(2));
        }
    }
    private void processForgotPass(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty()){
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            }
            else {
                manager.forgotPass(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
            }
        }
    }
    private void processLogOut(){
        manager.logOut();
    }
    private void processShowInf(){
        manager.showInf();
    }
    private void processChangeUsername(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty()){
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            }
            else {
                manager.changeUserName(matcher.group(1));
            }
        }
    }
    private void processChangeNickName(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty()){
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            }
            else {
                manager.changeNickName(matcher.group(1));
            }
        }
    }
    private void processChangePassword(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty()){
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            }
            else {
                manager.changePassword(matcher.group(1),matcher.group(2));
            }
        }
    }
    private void processChangeEmail(Matcher matcher){
        if (matcher.matches()){
            if (matcher.group().isEmpty()){
                System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
            }
            else {
                manager.changeEmail(matcher.group(1));
            }
        }
    }
    private boolean processEnterMainMenu(){
        if (manager.enterMainMenu())
            return true;
        return false;
    }
    private void processShowCards(){
        manager.showCards();
    }
    public void run(){
        String input;
        boolean isCorrect = false;
        while (!(input = scanner.nextLine()).trim().equals("end")){
            if (input.matches("user create -u\\s+(\\S+)\\s+-p\\s+(\\S+)\\s+(\\S+)\\s+-email\\s+(\\S+)\\s+-n\\s+(\\S+)\\s*")){
                processMakeUser1(getCommandMatcher(input,"user create -u\\s+(\\S+)\\s+-p\\s+(\\S+)\\s+(\\S+)\\s+-email\\s+(\\S+)\\s+-n\\s+(\\S+)\\s*"));
                System.out.println(User.ShowSecQuestion());
                input = scanner.nextLine();
                if (input.matches("question pick -q\\s+(\\S+)\\s+-a\\s+(\\S+)\\s+-c\\s+(\\S+)\\s*")){
                    processAddSecurityLetter(getCommandMatcher(input,"question pick -q\\s+(\\S+)\\s+-a\\s+(\\S+)\\s+-c\\s+(\\S+)\\s*"));
                }
                Captcha.makeAsciiCaptcha();
                Captcha.makeEquationCaptcha();
            }
            else if (input.matches("user create -u\\s+(\\S+)\\s+-p random -email\\s+(\\S+)\\s+-n\\s+(\\S+)\\s*")){
                processMakeUser2(getCommandMatcher(input,"user create -u\\s+(\\S+)\\s+-p random -email\\s+(\\S+)\\s+-n\\s+(\\S+)\\s*"));
                while (!isCorrect){
                    input = scanner.nextLine();
                    if (processCheckRandomPassword(input))
                        isCorrect = true;
                }
                System.out.println(User.ShowSecQuestion());
                input = scanner.nextLine();
                if (input.matches("question pick -q\\s+(\\S+)\\s+-a\\s+(\\S+)\\s+-c\\s+(\\S+)\\s*")){
                    processAddSecurityLetter(getCommandMatcher(input,"question pick -q\\s+(\\S+)\\s+-a\\s+(\\S+)\\s+-c\\s+(\\S+)\\s*"));
                }
                Captcha.makeAsciiCaptcha();
                Captcha.makeEquationCaptcha();
            }
            else if (input.matches("user login -u\\s+(\\S+)\\s+-p\\s+(\\S+)\\s*")){
                processLogIn(getCommandMatcher(input,"user login -u\\s+(\\S+)\\s+-p\\s+(\\S+)\\s*"));
                CountdownTimer countdownTimer = new CountdownTimer(scanner);
                countdownTimer.startCountdown(manager.returnTime() * 5000);
            }
            else if (input.matches("Forgot my password -u\\s+(\\S+)\\s*")){
                input = scanner.nextLine();
                processForgotPass(getCommandMatcher(input,"(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*"));
            }
            else if (input.matches("log out")){
                processLogOut();
            }
            else if (input.matches("Show information")){
                processShowInf();
            }
            else if (input.matches("Profile change -u\\s+(\\S+)\\s*")){
                processChangeUsername(getCommandMatcher(input,"Profile change -u\\s+(\\S+)\\s*"));
            }
            else if (input.matches("Profile change -n\\s+(\\S+)\\s*")){
                processChangeNickName(getCommandMatcher(input,"Profile change -n\\s+(\\S+)\\s*"));
            }
            else if (input.matches("profile change password -o\\s+(\\S+)\\s+-n\\s+(\\S+)\\s*")){
                processChangePassword(getCommandMatcher(input,"profile change password -o\\s+(\\S+)\\s+-n\\s+(\\S+)\\s*"));//todo
            }
            else if (input.matches("profile change -e\\s+(\\S+)\\s*")){
                processChangeEmail(getCommandMatcher(input,"profile change -e\\s+(\\S+)\\s*"));
            }
            else if (input.matches("Enter the main menu")){
                if (processEnterMainMenu()){
                    System.out.println(CheckErrorsAndMessages.ENTERED_MAIN_MENU);
                    input = scanner.nextLine();
                    if (input.matches("Start game")){

                    }
                    else if (input.matches("Show my cards")){
                        processShowCards();
                    }
                    else if (input.matches("Game history")){//todo

                    }
                    else if (input.matches("Shop")){//todo
                        System.out.println(CheckErrorsAndMessages.ENTERED_SHOP_MENU);

                    }
                    else if (input.matches("Profile")){
                        System.out.println(CheckErrorsAndMessages.ENTERED_PROFILE_MENU);
                        input = scanner.nextLine();
                        if (input.matches("Show information")){
                            processShowInf();
                        }
                        else if (input.matches("Profile change -u\\s+(\\S+)\\s*")){
                            processChangeUsername(getCommandMatcher(input,"Profile change -u\\s+(\\S+)\\s*"));
                        }
                        else if (input.matches("Profile change -n\\s+(\\S+)\\s*")){
                            processChangeNickName(getCommandMatcher(input,"Profile change -n\\s+(\\S+)\\s*"));
                        }
                        else if (input.matches("profile change password -o\\s+(\\S+)\\s+-n\\s+(\\S+)\\s*")){
                            processChangePassword(getCommandMatcher(input,"profile change password -o\\s+(\\S+)\\s+-n\\s+(\\S+)\\s*"));//todo
                        }
                        else if (input.matches("profile change -e\\s+(\\S+)\\s*")){
                            processChangeEmail(getCommandMatcher(input,"profile change -e\\s+(\\S+)\\s*"));
                        }
                    }
                    else if (input.matches("log out")){
                        processLogOut();
                    }
                }
                else {
                    System.out.println(CheckErrorsAndMessages.NO_ONE_LOGGED_IN);
                }
            }
        }
    }
}
