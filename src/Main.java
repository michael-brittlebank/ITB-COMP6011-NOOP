/* *************************************************/
/* Assignment 1 */
/* Written by: Mike Stumpf */
/* Course: COMP H6011 */
/* Language: Java */
/* Date Started: November 15, 2016 */
/* Last Update: December 12, 2016 */
/* Program Description: A banking institution */
/* requires a simple program for processing and analysing */
/* Account holders’ details. For simplicity and ease of */
/* testing reasons, the bank has a relatively small number */
/* of Account holders (maximum of 20).  */
/* *************************************************/

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;

class Main {

    /**
     * main methods
     */

    public static void main(String[] args){
        System.out.println("Welcome to Zero Bank.");

        String accountString = "account";
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\\n"); //http://stackoverflow.com/questions/5032356/using-scanner-nextline
        String[] accountHolders;
        double[] accountBalances;

        System.out.println("Please enter the number of bank accounts.");
        int numberOfAccounts = getValidInteger(input, 1, 20);

        accountHolders = new String[numberOfAccounts];
        accountBalances = new double[numberOfAccounts];

        int counter = 0;

        do {
            System.out.printf("Please enter the holder's name for account #%d.\n",counter+1);
            accountHolders[counter] = getStringInput(input);
            System.out.printf("Please enter the balance for account #%d.\n",counter+1);
            accountBalances[counter] = getValidDouble(input, 0);
            counter++;
        } while(counter < numberOfAccounts);

        //plural
        if (accountHolders.length != 1){
            accountString += "s";
        }

        System.out.printf("Hello, you have %d %s.\n",accountHolders.length,accountString);

        //variables
        int  actionId;
        double averageAccountBalance = 0,
                highestAccountBalance = 0,
                lowestAccountBalance = 0;

        //sort arrays
        double[] sortedAccountBalances = accountBalances;
        Arrays.sort(accountBalances);
        String[] alphabeticallySortedAccountBalances = accountHolders;
        Arrays.sort(accountHolders);

        do {
            System.out.println("\n-----What action would you like to perform?-----");
            System.out.println("Option 1: Display average account balance");
            System.out.println("Option 2: Display highest account balance");
            System.out.println("Option 3: Display lowest account balance");
            System.out.println("Option 4: Display the account balances in descending order");
            System.out.println("Option 5: Search for an individual account holder by name");
            System.out.println("Option 6: Exit and quit program");
            actionId = getIntegerInput(input);
            switch(actionId){
                case 1:
                    averageAccountBalance = displayAverageAccountBalance(averageAccountBalance,accountBalances);
                    break;
                case 2:
                    highestAccountBalance = displayHighestAccountBalance(highestAccountBalance,sortedAccountBalances);
                    break;
                case 3:
                    displayLowestAccountBalance(lowestAccountBalance,sortedAccountBalances);
                    break;
                case 4:
                    displaySortedAccountBalances(sortedAccountBalances);
                    break;
                case 5:
                    displayAccountSearchResults(input,alphabeticallySortedAccountBalances);
                    break;
                case 6:
                    System.out.println("Thank you, goodbye.");
                    break;
                default:
                    System.out.println("Invalid option selected.");
            }
        } while(actionId != 6);
    }

    /**
     * display methods
     */

    private static double displayAverageAccountBalance(double averageAccountBalance, double[] accountBalances){
        if (averageAccountBalance == 0){
            double totalAccountBalances = 0;
            for(double accountBalance:accountBalances){
                totalAccountBalances += accountBalance;
            }
            averageAccountBalance = totalAccountBalances/accountBalances.length;
        }
        System.out.printf("Average account balance: €%s\n",String.format("%.2f", averageAccountBalance));
        return averageAccountBalance;
    }

    private static double displayHighestAccountBalance(double highestAccountBalance, double[] sortedAccountBalances){
        if (highestAccountBalance == 0){
            highestAccountBalance = sortedAccountBalances[sortedAccountBalances.length-1];
        }
        System.out.printf("Highest account balance: €%s\n",String.format("%.2f", highestAccountBalance));
        return highestAccountBalance;
    }

    private static double displayLowestAccountBalance(double lowestAccountBalance, double[] sortedAccountBalances){
        if (lowestAccountBalance == 0){
            lowestAccountBalance = sortedAccountBalances[0];
        }
        System.out.printf("Lowest account balance: €%s\n",String.format("%.2f", lowestAccountBalance));
        return lowestAccountBalance;
    }

    private static void displaySortedAccountBalances(double[] sortedAccountBalances) {
        for(int i = sortedAccountBalances.length; i > 0; i--){
            System.out.printf("Balance: €%s\n",String.format("%.2f", sortedAccountBalances[i-1]));
        }
    }

    private static void displayAccountSearchResults(Scanner input, String[] alphabeticallySortedAccountBalances){
        System.out.println("Whose account do you want to search for?");
        String accountHolder = getStringInput(input);
        if(Arrays.binarySearch(alphabeticallySortedAccountBalances, accountHolder) >= 0){
            System.out.println("Account holder found");
        } else {
            System.out.println("Account holder not found");
        }
    }


    /**
     * input methods
     */
    private static int getIntegerInput(Scanner input) {
        try {
            return input.nextInt();
            //https://docs.oracle.com/javase/7/docs/api/java/util/InputMismatchException.html
        } catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter an integer.");
            input.next();
            //recursion
            return getIntegerInput(input);
        }
    }

    private static double getDoubleInput(Scanner input) {
        try {
            return input.nextDouble();
        } catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a double.");
            input.next();
            //recursion
            return getDoubleInput(input);
        }
    }

    private static String getStringInput(Scanner input) {
        try {
            return input.next();
        } catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a string.");
            input.next();
            //recursion
            return getStringInput(input);
        }
    }

    /**
     * validation methods
     */
    private static int getValidInteger(Scanner input, int minimumValue){
        return getValidInteger(input,minimumValue,Integer.MAX_VALUE);
    }

    private static int getValidInteger(Scanner input, int minimumValue, int maximumValue){
        int integerInput;
        do {
            integerInput = getIntegerInput(input);
            if (integerInput < minimumValue){
                System.out.println("Please enter a number greater than "+minimumValue+".");
            } else if (integerInput > maximumValue){
                System.out.println("Please enter a number less than or equal to "+maximumValue+".");
            }
        } while(integerInput > maximumValue || integerInput < minimumValue);
        return integerInput;
    }

    private static double getValidDouble(Scanner input, double minimumValue) {
        return getValidDouble(input,minimumValue,Double.MAX_VALUE);
    }

    private static double getValidDouble(Scanner input, double minimumValue, double maximumValue){
        double doubleInput;
        do {
            doubleInput = getDoubleInput(input);
            if (doubleInput < minimumValue){
                System.out.println("Please enter a number greater than "+String.format("%.2f", minimumValue)+".");
            } else if (doubleInput > maximumValue){
                System.out.println("Please enter a number less than or equal to "+String.format("%.2f", maximumValue)+".");
            }
        } while(doubleInput > maximumValue || doubleInput < minimumValue);
        return doubleInput;
    }
}
