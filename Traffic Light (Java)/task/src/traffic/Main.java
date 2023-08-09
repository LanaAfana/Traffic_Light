package traffic;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  static int inputData(String text) {
    boolean isNotQuit = true;
    int numberOf = 0;
    do {
      System.out.print(text);
      try (Scanner scanner = new Scanner(System.in)) {
        numberOf = scanner.nextInt();
        if (numberOf < 1) {
          System.out.print("Error! Incorrect input. Try again: ");
        } else {
          isNotQuit = false;
        }
      } catch (InputMismatchException e) {
        System.out.print("Error! Incorrect input. Try again: ");
      }
    } while (isNotQuit);
    return numberOf;
  }

  public static void pressEnter() {
//    System.out.println("Press Enter and pass the move to another player\n");
    try (Scanner scanner = new Scanner(System.in)) {
      scanner.nextLine();
    }
  }

  public static void main(String[] args){
    final String MENU = """
            Menu:
            1. Add road
            2. Delete road
            3. Open system
            0. Quit
            """;
    int numberOfRoads;
    int interval;
    boolean isNotQuit = true;

    System.out.println("Welcome to the traffic management system!");
    numberOfRoads = inputData("Input the number of roads: ");
    interval = inputData("Input the interval: ");

    do {
      try {
        var clearCommand = System.getProperty("os.name").contains("Mac")
                ? new ProcessBuilder("cmd", "/c", "cls")
                : new ProcessBuilder("clear");
        clearCommand.inheritIO().start().waitFor();
      }
      catch (IOException | InterruptedException e) {}
      System.out.println(MENU);
      try (Scanner scanner = new Scanner(System.in)) {
        String menuItem = scanner.nextLine();
        switch (menuItem) {
          case "0" -> {
            System.out.println("Bye!");
            isNotQuit = false;
          }
          case "1" -> {
            System.out.println("Road added");
            pressEnter();
          }
          case "2" -> {
            System.out.println("Road deleted");
            pressEnter();
          }
          case "3" -> {
            System.out.println("System opened");
            pressEnter();
          }
          default -> {
            System.out.println("Incorrect option");
            pressEnter();
          }
        }
      }
    } while (isNotQuit);
  }
}
