package org.example;

import java.util.List;
import java.util.Scanner;

/**
 * Console shell for the todo list.
 *
 * <p>Supported commands: {@code add <task>}, {@code remove <index>},
 * {@code list}, {@code help}, {@code exit}.
 */
public final class TodoApp {

    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_EXIT = "exit";

    private final TodoList todoList = new TodoList();

    public static void main(String[] args) {
        new TodoApp().run(new Scanner(System.in));
    }

    private void run(Scanner scanner) {
        printHelp();
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";

            if (COMMAND_EXIT.equals(command)) {
                System.out.println("Bye!");
                break;
            } else if (COMMAND_ADD.equals(command)) {
                handleAdd(argument);
            } else if (COMMAND_REMOVE.equals(command)) {
                handleRemove(argument);
            } else if (COMMAND_LIST.equals(command)) {
                handleList();
            } else if (COMMAND_HELP.equals(command)) {
                printHelp();
            } else {
                System.out.println("Unknown command: " + command);
            }
        }
    }

    private void handleAdd(String argument) {
        int before = todoList.size();
        todoList.add(argument);
        if (todoList.size() > before) {
            System.out.println("Added: " + argument.trim());
        } else {
            System.out.println("Task text is empty, nothing added.");
        }
    }

    private void handleRemove(String argument) {
        int index;
        try {
            index = Integer.parseInt(argument.trim());
        } catch (NumberFormatException exception) {
            System.out.println("Index must be a number.");
            return;
        }
        if (todoList.remove(index)) {
            System.out.println("Removed task #" + index);
        } else {
            System.out.println("No task with index " + index + ".");
        }
    }

    private void handleList() {
        List<String> tasks = todoList.getAll();
        if (tasks.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + ": " + tasks.get(i));
        }
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("  add <task>     - add a task");
        System.out.println("  remove <index> - remove a task by index");
        System.out.println("  list           - show all tasks");
        System.out.println("  help           - show this help");
        System.out.println("  exit           - quit the app");
    }
}
