package traffic;

import java.util.Scanner;

public class Main {
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
    System.out.print("Input the number of roads: ");
    try (Scanner scanner = new Scanner(System.in)) {
      numberOfRoads = scanner.nextInt();
    }
    System.out.print("Input the interval: ");
    try (Scanner scanner = new Scanner(System.in)) {
      interval = scanner.nextInt();
    }
    do {
      System.out.println(MENU);
      try (Scanner scanner = new Scanner(System.in)) {
        int menuItem = scanner.nextInt();
        switch (menuItem) {
          case 0 -> {
            System.out.println("Bye!");
            isNotQuit = false;
          }
          case 1 -> System.out.println("Road added");
          case 2 -> System.out.println("Road deleted");
          case 3 -> System.out.println("System opened");
        }
      }
    } while (isNotQuit);
  }
}
