package org.example.Tools;

import org.example.CheckErrorsAndMessages;

import java.util.Random;
import java.util.Scanner;

public class Captcha {
    public static void makeAsciiCaptcha() {
        Scanner scanner = new Scanner(System.in);
        String[] asciiDigits = new String[]{
                "  ***  \n *   * \n*  *  *\n* * * *\n*  *  *\n *   * \n  ***  ",

                "   *   \n  **   \n   *   \n   *   \n   *   \n   *   \n ***** ",

                "  ***  \n *   * \n    *  \n   *   \n  *    \n *   * \n ***** ",

                " ***** \n*     *\n      *\n ***** \n      *\n*     *\n ***** ",

                "*    * \n*    * \n*    * \n*******\n     * \n     * \n     * ",

                " ***** \n *    \n ***** \n      *\n      *\n*     *\n ***** ",

                "  ***  \n *    \n *    *\n  **** \n *    *\n *    *\n  ***  ",

                " ***** \n*    *\n     *\n   *  \n  *  \n *   \n *   ",

                "  ***  \n *   * \n *   * \n  ***  \n *   * \n *   * \n  ***  ",

                " ***** \n*    *\n*    *\n ***** \n     *\n    * \n **** "
        };
        boolean correct = false;
        while (!correct) {
            Random random = new Random();
            int numDigits = random.nextInt(3) + 5;
            String numm = "";

            String[] captchas = new String[numDigits];
            for (int i = 0; i < numDigits; i++) {
                int randomNumber = random.nextInt(10);
                captchas[i] = asciiDigits[randomNumber];
                numm += (String.valueOf(randomNumber));
            }

            for (int row = 0; row < 7; row++) {
                for (int i = 0; i < numDigits; i++) {
                    System.out.print(captchas[i].split("\n")[row] + "       ");
                }
                System.out.println();
            }
            int input;
            input = scanner.nextInt();
            if (String.valueOf(input).equals(numm)) {
                System.out.println(CheckErrorsAndMessages.CAPTCHA_HAS_ENTERED_CORRECTLY);
                correct = true;
            }
            else
                System.out.println(CheckErrorsAndMessages.TRY_AGAIN);
        }
    }
    //******************************************************************************************************************
    public static void makeEquationCaptcha() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            int num1 = random.nextInt(90) + 10;
            int num2 = random.nextInt(9) + 1;
            char operator = getRandomOperator();

            System.out.println(num1 + " " + operator + " " + num2 + " = ?");

            double correctAnswer = calculateResult(num1, num2, operator);
            double userAnswer = scanner.nextDouble();

            if (Double.compare(userAnswer, correctAnswer) == 0) {
                System.out.println(CheckErrorsAndMessages.CAPTCHA_HAS_ENTERED_CORRECTLY);
                System.out.println(CheckErrorsAndMessages.USER_HAS_SUCCESSFULLY_MADE);
                break;
            } else {
                System.out.println(CheckErrorsAndMessages.TRY_AGAIN);
            }
        }
    }
    public static char getRandomOperator() {
        Random random = new Random();
        String operators = "+-*/";
        int index = random.nextInt(operators.length());
        return operators.charAt(index);
    }
    public static double calculateResult(int num1, int num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return (double) num1 / num2;
            default:
                System.out.println("Invalid operator.");
                return 0;
        }
    }
}
