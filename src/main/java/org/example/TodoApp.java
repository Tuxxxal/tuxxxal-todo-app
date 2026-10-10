package org.example;

import java.util.List;
import java.util.Scanner;

/**
 * Console shell for the todo list.
 *
 * <p>Supported commands: {@code add <task>}, {@code remove <index>},
 * {@code done <index>}, {@code search <text>}, {@code clear}, {@code list},
 * {@code help}, {@code exit}.
 */
public final class TodoApp {

    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_CLEAR = "clear";
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
            } else if (COMMAND_DONE.equals(command)) {
                handleDone(argument);
            } else if (COMMAND_SEARCH.equals(command)) {
                handleSearch(argument);
            } else if (COMMAND_CLEAR.equals(command)) {
                handleClear();
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
        Integer index = parseIndex(argument);
        if (index == null) {
            return;
        }
        if (todoList.remove(index)) {
            System.out.println("Removed task #" + index);
        } else {
            System.out.println("No task with index " + index + ".");
        }
    }

    private void handleDone(String argument) {
        Integer index = parseIndex(argument);
        if (index == null) {
            return;
        }
        if (todoList.markAsDone(index)) {
            System.out.println("Task #" + index + " is done.");
        } else {
            System.out.println("No task with index " + index + ".");
        }
    }

    private void handleSearch(String argument) {
        if (argument.isBlank()) {
            System.out.println("Usage: search <text>");
            return;
        }
        List<Task> found = todoList.search(argument);
        if (found.isEmpty()) {
            System.out.println("Nothing found.");
            return;
        }
        List<Task> allTasks = todoList.getAll();
        for (Task task : found) {
            System.out.println(allTasks.indexOf(task) + ": " + task.getStatus() + " " + task.getText());
        }
    }

    private void handleClear() {
        int removed = todoList.size();
        todoList.clear();
        System.out.println("Removed " + removed + " task(s).");
    }

    private void handleList() {
        List<Task> tasks = todoList.getAll();
        if (tasks.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        printTasks(tasks);
    }

    private Integer parseIndex(String argument) {
        int index;
        try {
            index = Integer.parseInt(argument.trim());
        } catch (NumberFormatException exception) {
            System.out.println("Index must be a number.");
            return null;
        }
        return index;
    }

    private void printTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println(i + ": " + task.getStatus() + " " + task.getText());
        }
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("  add <task>     - add a task");
        System.out.println("  remove <index> - remove a task by index");
        System.out.println("  done <index>   - mark a task as done");
        System.out.println("  search <text>  - find tasks by substring");
        System.out.println("  clear          - remove all tasks");
        System.out.println("  list           - show all tasks");
        System.out.println("  help           - show this help");
        System.out.println("  exit           - quit the app");
    }
}
