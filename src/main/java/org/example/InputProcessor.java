package org.example;
import org.example.Tools.Captcha;
import org.example.Tools.CountdownTimer;

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
        return pattern.matcher(input);
    }
    private void processMakeUser1(Matcher matcher){
        if (matcher.matches()){
            manager.addUser1(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
        }
    }
    private void processMakeUser2(Matcher matcher){
        if (matcher.matches()){
            manager.addUser2(matcher.group(1),matcher.group(2),matcher.group(3));
        }
    }
    private void processAddSecurityLetter(Matcher matcher){
        if (matcher.matches()){
            manager.addSecurityWord(matcher.group(1),matcher.group(2));
        }
    }
    private void processLogIn(Matcher matcher){
        if (matcher.matches()){
            manager.logIn(matcher.group(1),matcher.group(2));
        }
    }
    private void processForgotPassword(Matcher matcher){
        if (matcher.matches()){
            manager.forgotPass(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
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
            manager.changeUserName(matcher.group(1));
        }
    }
    private void processChangeNickName(Matcher matcher){
        if (matcher.matches()){
            manager.changeNickName(matcher.group(1));
        }
    }
    private void processChangePassword(Matcher matcher){
        if (matcher.matches()){
            manager.changePassword(matcher.group(1),matcher.group(2));
        }
    }
    private void processChangeEmail(Matcher matcher){
        if (matcher.matches()){
            manager.changeEmail(matcher.group(1));
        }
    }
    public void run(){
        String input;
        System.out.println(CheckErrorsAndMessages.PLEASE_SIGN_UP_OR_LOG_IN);
        while (!(input = scanner.nextLine()).trim().equals("end")){
            if (input.matches("user create -u-\\s+(.+?)\\s+-p-\\s+(.+?)\\s+(.+?)\\s+-email-\\s+(.+?)\\s+-n-\\s+(.+?)\\s*-")){
                if (input.replaceAll("\\s+", "").contains("--"))
                    System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                else {
                    processMakeUser1(getCommandMatcher(input,"user create -u-\\s+(.+?)\\s+-p-\\s+(.+?)\\s+(.+?)\\s+-email-\\s+(.+?)\\s+-n-\\s+(.+?)\\s*-"));
                    System.out.println(User.ShowSecQuestion());
                    input = scanner.nextLine();
                    while (input.replaceAll("\\s+", "").contains("--") || !input.matches("question pick -q-\\s+(.+?)\\s+-a-\\s+(.+?)\\s+-c-\\s+(.+?)\\s*-")){
                        System.out.println(CheckErrorsAndMessages.PROCESS_MAKE_SECURITY_QUESTION_HAS_FAILED);
                        System.out.println(User.ShowSecQuestion());
                        input = scanner.nextLine();
                    }
                    processAddSecurityLetter(getCommandMatcher(input,"question pick -q-\\s+(.+?)\\s+-a-\\s+(.+?)\\s+-c-\\s+(.+?)\\s*-"));
                    Captcha.makeAsciiCaptcha();
                    Captcha.makeEquationCaptcha();
                }
            }
            else if (input.matches("user create -u-\\s+(.+?)\\s+-p- random -email-\\s+(.+?)\\s+-n-\\s+(.+?)\\s*-")){
                if (input.replaceAll("\\s+", "").contains("--"))
                    System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                else {
                    processMakeUser2(getCommandMatcher(input,"user create -u-\\s+(.+?)\\s+-p- random -email-\\s+(.+?)\\s+-n-\\s+(.+?)\\s*-"));
                    input = scanner.nextLine();
                    while (!input.equals(manager.returnRandPassword())){
                        System.out.println(CheckErrorsAndMessages.PASSWORD_CONFIRMATION_HAS_FAILED_TRY_AGAIN);
                        input = scanner.nextLine();
                    }
                    System.out.println(CheckErrorsAndMessages.PASSWORD_HAS_CONFIRMED);
                    System.out.println(User.ShowSecQuestion());
                    input = scanner.nextLine();
                    while (input.replaceAll("\\s+", "").contains("--") || !input.matches("question pick -q-\\s+(.+?)\\s+-a-\\s+(.+?)\\s+-c-\\s+(.+?)\\s*-")){
                        System.out.println(CheckErrorsAndMessages.PROCESS_MAKE_SECURITY_QUESTION_HAS_FAILED);
                        System.out.println(User.ShowSecQuestion());
                        input = scanner.nextLine();
                    }
                    processAddSecurityLetter(getCommandMatcher(input,"question pick -q-\\s+(.+?)\\s+-a-\\s+(.+?)\\s+-c-\\s+(.+?)\\s*-"));
                    Captcha.makeAsciiCaptcha();
                    Captcha.makeEquationCaptcha();
                }
            }
            else if (input.matches("user login -u-\\s+(.+?)\\s+-p-\\s+(.+?)\\s*-")){
                if (input.replaceAll("\\s+", "").contains("--"))
                    System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                else {
                    processLogIn(getCommandMatcher(input,"user login -u-\\s+(.+?)\\s+-p-\\s+(.+?)\\s*-"));
                    CountdownTimer countdownTimer = new CountdownTimer(scanner);
                    countdownTimer.startCountdown(manager.returnTime() * 5000);
                }
            }
            else if (input.matches("Forgot my password -u-\\s+(.+?)\\s*-")){
                if (input.replaceAll("\\s+", "").contains("--"))
                    System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                else {
                    input = scanner.nextLine();
                    if (input.replaceAll("\\s+", "").contains("--"))
                        System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                    else
                        processForgotPassword(getCommandMatcher(input,"-(.+?)-\\s+-(.+?)-\\s+-(.+?)-\\s+-(.+?)-\\s+-(.+?)-\\s*"));
                }
            }
            else if (input.matches("log out")){
                processLogOut();
            }
            else if (input.matches("Show information")){
                processShowInf();
            }
            else if (input.matches("Profile change -u-\\s+(.+?)\\s*-")){
                if (input.replaceAll("\\s+", "").contains("--"))
                    System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                else
                    processChangeUsername(getCommandMatcher(input,"Profile change -u-\\s+(.+?)\\s*-"));
            }
            else if (input.matches("Profile change -n-\\s+(.+?)\\s*-")){
                if (input.replaceAll("\\s+", "").contains("--"))
                    System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                else
                    processChangeNickName(getCommandMatcher(input,"Profile change -n-\\s+(.+?)\\s*-"));
            }
            else if (input.matches("profile change password -o-\\s+(.+?)\\s+-n-\\s+(.+?)\\s*-")){
                if (input.replaceAll("\\s+", "").contains("--"))
                    System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                else
                    processChangePassword(getCommandMatcher(input,"profile change password -o-\\s+(.+?)\\s+-n-\\s+(.+?)\\s*-"));
            }
            else if (input.matches("profile change -e-\\s+(.+?)\\s*-")){
                if (input.replaceAll("\\s+", "").contains("--"))
                    System.out.println(CheckErrorsAndMessages.EMPTY_FIELD);
                else
                    processChangeEmail(getCommandMatcher(input,"profile change -e-\\s+(.+?)\\s*-"));
            }
            else if (input.matches("Enter the main menu")){
                manager.enterMainMenu();
                input = scanner.nextLine();
                if (input.matches("Game Menu")){
                    System.out.println(User.chooseMode());
                    input = scanner.nextLine();
                    if (input.matches("1")){
                        //logic
                    }
                    else if (input.matches("2")){
                        //logic
                    }
                }
                else if (input.matches("Show Cards")){
                    manager.showCards();
                }
                else if (input.matches("Game History")){

                }
                else if (input.matches("Shop Menu")){

                }
                else if (input.matches("Profile Menu")){
                    System.out.println(CheckErrorsAndMessages.ENTERED_PROFILE_MENU);
                    input = scanner.nextLine();
                    if (input.matches("Show information")){
                        processShowInf();
                    }
                    else if (input.matches("Profile change -u-\\s+(.+?)\\s*-")){
                        processChangeUsername(getCommandMatcher(input,"Profile change -u-\\s+(.+?)\\s*-"));
                    }
                    else if (input.matches("Profile change -n-\\s+(.+?)\\s*-")){
                        processChangeNickName(getCommandMatcher(input,"Profile change -n-\\s+(.+?)\\s*-"));
                    }
                    else if (input.matches("profile change password -o-\\s+(.+?)\\s+-n-\\s+(.+?)\\s*-")){
                        processChangePassword(getCommandMatcher(input,"profile change password -o-\\s+(.+?)\\s+-n-\\s+(.+?)\\s*-"));
                    }
                    else if (input.matches("profile change -e\\s+(\\S+)\\s*")){
                        processChangeEmail(getCommandMatcher(input,"profile change -e-\\s+(.+?)\\s*-"));
                    }
                }
                else if (input.matches("Log Out")){
                    processLogOut();
                }
            }
        }
    }
}
