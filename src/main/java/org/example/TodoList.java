package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * In-memory list of todo tasks.
 *
 * <p>Stores tasks in insertion order, ignores blank input and protects
 * callers from the internal state via defensive copies of the list.
 */
public class TodoList {

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Adds a task after trimming leading and trailing whitespace.
     * {@code null}, empty and blank values are ignored. New tasks are not done.
     *
     * @param task raw task text
     */
    public void add(String task) {
        if (task == null) {
            return;
        }
        String normalized = task.trim();
        if (normalized.isEmpty()) {
            return;
        }
        tasks.add(new Task(normalized));
    }

    /**
     * Removes the task at the given zero-based index.
     *
     * @param index zero-based position of the task
     * @return {@code true} if the task was removed, {@code false} if the index is out of bounds
     */
    public boolean remove(int index) {
        if (!isValidIndex(index)) {
            return false;
        }
        tasks.remove(index);
        return true;
    }

    /**
     * Marks the task at the given zero-based index as done.
     *
     * @param index zero-based position of the task
     * @return {@code true} if the status was changed, {@code false} if the index is out of bounds
     */
    public boolean markAsDone(int index) {
        if (!isValidIndex(index)) {
            return false;
        }
        tasks.get(index).markAsDone();
        return true;
    }

    /**
     * Removes all tasks from the list.
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Returns a copy of all stored tasks in insertion order.
     *
     * @return new mutable list with all tasks
     */
    public List<Task> getAll() {
        return new ArrayList<>(tasks);
    }

    /**
     * Returns the texts of all stored tasks in insertion order.
     *
     * @return new mutable list with task texts
     */
    public List<String> getAllTexts() {
        List<String> texts = new ArrayList<>(tasks.size());
        for (Task task : tasks) {
            texts.add(task.getText());
        }
        return texts;
    }

    /**
     * Searches tasks whose text contains the query, ignoring case.
     * An empty query matches every task, {@code null} matches nothing.
     *
     * @param query substring to look for
     * @return list of matching tasks, empty when nothing matches
     */
    public List<Task> search(String query) {
        List<Task> found = new ArrayList<>();
        if (query == null) {
            return found;
        }
        String needle = query.trim().toLowerCase(Locale.ROOT);
        for (Task task : tasks) {
            if (task.getText().toLowerCase(Locale.ROOT).contains(needle)) {
                found.add(task);
            }
        }
        return found;
    }

    /**
     * Returns the number of stored tasks.
     *
     * @return tasks count
     */
    public int size() {
        return tasks.size();
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }
}
