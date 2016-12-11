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

/* Imports */
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;

class ZeroBank {

    /**
     * This is the primary entry function for the ZeroBank class.
     * All double values are exported in a standard currency format with only
     * two trailing decimal places being shown.
     * @param args String[]
     */
    public static void main(String[] args){
        System.out.println("Welcome to Zero Bank.");

        /* variables */
        String accountString = "account";
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\\n");//scanner uses newlines as delimiters for input
        int actionId,
                numberOfAccounts,
                accountCounter = 0;
        double averageAccountBalance = 0;
        double[] accountBalances,
                sortedAccountBalances;
        String[] accountHolders,
                alphabeticallySortedAccountBalances;

        /* get the total number of bank accounts and instantiate arrays of corresponding length */
        System.out.println("Please enter the number of bank accounts.");
        numberOfAccounts = getValidInteger(input, 1, 20);
        accountHolders = new String[numberOfAccounts];
        accountBalances = new double[numberOfAccounts];

        /* get the bank account holders' names and balances */
        do {
            System.out.printf("Please enter the holder's name for account #%d.\n",accountCounter+1);//arrays are zero indexed
            accountHolders[accountCounter] = getStringInput(input);
            System.out.printf("Please enter the balance for account #%d.\n",accountCounter+1);//arrays are zero indexed
            accountBalances[accountCounter] = getValidDouble(input, 0);
            accountCounter++;
        } while(accountCounter < numberOfAccounts);

        /* display welcome message to provide user feedback */
        if (accountHolders.length != 1){
            accountString += "s";//pluralize
        }
        System.out.printf("Hello, you have %d %s.\n",accountHolders.length,accountString);


        /* sort the arrays for faster searching and calculations */
        sortedAccountBalances = accountBalances;
        Arrays.sort(accountBalances);
        alphabeticallySortedAccountBalances = accountHolders;
        Arrays.sort(accountHolders);

        /* provide a menu to the user and allow investigation of the account data */
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
                    displayHighestAccountBalance(sortedAccountBalances);
                    break;
                case 3:
                    displayLowestAccountBalance(sortedAccountBalances);
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

    /* ***************** Display methods ***************** */

    /**
     * This method prints the average account balance to the console.
     * If the average account balance is not zero (meaning it's already been set),
     * the loop and calculations are skipped.
     * @param averageAccountBalance double
     * @param accountBalances double[]
     * @return double
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

    /**
     * This method prints the highest account balance to the console assuming a sorted array is provided.
     * @param sortedAccountBalances double[]
     */
    private static void displayHighestAccountBalance(double[] sortedAccountBalances){
        double highestAccountBalance = sortedAccountBalances[sortedAccountBalances.length-1];
        System.out.printf("Highest account balance: €%s\n",String.format("%.2f", highestAccountBalance));
    }

    /**
     * This method prints the lowest account balance to the console assuming a sorted array is provided.
     * @param sortedAccountBalances double[]
     */
    private static void displayLowestAccountBalance(double[] sortedAccountBalances){
        double lowestAccountBalance = sortedAccountBalances[0];
        System.out.printf("Lowest account balance: €%s\n",String.format("%.2f", lowestAccountBalance));
    }

    /**
     * This method prints the account balances in ascending order assuming a sorted array is provided.
     * @param sortedAccountBalances double[]
     */
    private static void displaySortedAccountBalances(double[] sortedAccountBalances) {
        for(int i = sortedAccountBalances.length; i > 0; i--){
            System.out.printf("Balance: €%s\n",String.format("%.2f", sortedAccountBalances[i-1]));
        }
    }

    /**
     * This method searches to determine if an account holder exists given a name entered by the client.
     * This method assumes a sorted array is provided.
     * @param input Scanner
     * @param alphabeticallySortedAccountBalances String[]
     */
    private static void displayAccountSearchResults(Scanner input, String[] alphabeticallySortedAccountBalances){
        System.out.println("Whose account do you want to search for?");
        String accountHolder = getStringInput(input);
        if(Arrays.binarySearch(alphabeticallySortedAccountBalances, accountHolder) >= 0){
            System.out.println("Account holder found");
        } else {
            System.out.println("Account holder not found");
        }
    }

    /* ***************** Input Validation methods ***************** */

    /**
     * This method gets a valid integer from from the user.
     * This overloaded method allows for an optional maximum value to be specified.
     * @param input Scanner
     * @param minimumValue int
     * @return int
     */
    private static int getValidInteger(Scanner input, int minimumValue){
        return getValidInteger(input,minimumValue,Integer.MAX_VALUE);
    }

    /**
     * This method gets a valid integer from the user given a minimum and maximum value.
     * @param input Scanner
     * @param minimumValue int
     * @param maximumValue int
     * @return int
     */
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

    /**
     * This method gets a valid double from from the user.
     * This overloaded method allows for an optional maximum value to be specified.
     * @param input Scanner
     * @param minimumValue double
     * @return double
     */
    private static double getValidDouble(Scanner input, double minimumValue) {
        return getValidDouble(input,minimumValue,Double.MAX_VALUE);
    }

    /**
     * This method gets a valid double from the user given a minimum and maximum value.
     * @param input Scanner
     * @param minimumValue double
     * @param maximumValue double
     * @return double
     */
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

    /* ***************** Input methods ***************** */

    /**
     * This method gets a valid integer from the console input from the user.
     * It utilizes recursion to ensure a correct input type is provided.
     * @param input Scanner
     * @return int
     */
    private static int getIntegerInput(Scanner input) {
        try {
            return input.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter an integer.");
            return getIntegerInput(input);//recursion
        }
    }

    /**
     * This method gets a valid double from the console input from the user.
     * It utilizes recursion to ensure a correct input type is provided.
     * @param input Scanner
     * @return double
     */
    private static double getDoubleInput(Scanner input) {
        try {
            return input.nextDouble();
        } catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a double.");
            return getDoubleInput(input);//recursion
        }
    }

    /**
     * This method gets a valid String from the console input from the user.
     * It utilizes recursion to ensure a correct input type is provided.
     * @param input Scanner
     * @return String
     */
    private static String getStringInput(Scanner input) {
        try {
            String inputLine = input.next().trim();
            if (inputLine.length() > 0) {//check that it's not just whitespace
                return inputLine;
            } else {
                System.out.println("Invalid input. Please enter a string of at least one character.");
                return getStringInput(input);//recursion
            }
        } catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a string.");
            return getStringInput(input);//recursion
        }
    }
}
