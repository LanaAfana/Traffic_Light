package traffic;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
  static int numberOfRoads;
  static int interval;
  static Thread threadSystem;
  static State state;
  static Queue<String> queueOfRoads = new ArrayDeque<>();
  static final String MENU = """
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit
            """;

  enum State {
//    NOT_STARTED,
    MENU,
    SYSTEM
  }

  static void addRoad(String name) {
    if (queueOfRoads.size() < numberOfRoads) {
      queueOfRoads.offer(name);
      System.out.printf("%s added!%n", name);
    } else {
      System.out.println("Queue is full");
    }
  }

  static void deleteRoad() {
    if (queueOfRoads.isEmpty()) {
      System.out.println("Queue is empty");
    } else {
      System.out.printf("%s deleted!%n", queueOfRoads.poll());
    }
  }
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

  public static void clearOutput() {
    try {
      var clearCommand = System.getProperty("os.name").contains("Mac")
              ? new ProcessBuilder("cmd", "/c", "cls")
              : new ProcessBuilder("clear");
      clearCommand.inheritIO().start().waitFor();
    }
    catch (IOException | InterruptedException e) {}
  }

  public static Thread systemInfoThread() {
    return new Thread(() -> {
      int count = 0;
      while (true) {
        try {
//          TimeUnit.SECONDS.sleep(1);
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        count++;
        if (state == State.SYSTEM) {
          clearOutput();
          System.out.printf("! %ds. have passed since system startup !%n", count);
          System.out.printf("! Number of roads: %d !%n", numberOfRoads);
          System.out.printf("! Interval: %d !%n", interval);
          queueOfRoads.forEach(System.out::println);
          System.out.println("! Press \"Enter\" to open menu !");
        }
      }
    });
  }
  public static void menu() {
    boolean isNotQuit = true;
    do {
      clearOutput();
      System.out.println(MENU);
      try (Scanner scanner = new Scanner(System.in)) {
        String menuItem = scanner.nextLine();
        switch (menuItem) {
          case "0" -> {
            System.out.println("Bye!");
            threadSystem.interrupt();
            isNotQuit = false;
          }
          case "1" -> {
            System.out.print("Input road name: ");
            try (Scanner scnr = new Scanner(System.in)) {
              String name = scnr.nextLine();
              addRoad(name);
            }
            pressEnter();
          }
          case "2" -> {
            deleteRoad();
            pressEnter();
          }
          case "3" -> {
            state = State.SYSTEM;
            pressEnter();
            state = State.MENU;
          }
          default -> {
            System.out.println("Incorrect option");
            pressEnter();
          }
        }
      }
    } while (isNotQuit);
  }

  public static void pressEnter() {
    try (Scanner scanner = new Scanner(System.in)) {
      scanner.nextLine();
    }
  }

  public static void main(String[] args){
    System.out.println("Welcome to the traffic management system!");
    numberOfRoads = inputData("Input the number of roads: ");
    interval = inputData("Input the interval: ");
    threadSystem = systemInfoThread();
    threadSystem.setName("QueueThread");
    threadSystem.start();
    menu();
  }
}
