package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory list of todo tasks.
 *
 * <p>Stores task texts in insertion order, ignores blank input and
 * protects callers from the internal state via defensive copies.
 */
public class TodoList {

    private final List<String> tasks = new ArrayList<>();

    /**
     * Adds a task after trimming leading and trailing whitespace.
     * {@code null}, empty and blank values are ignored.
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
        tasks.add(normalized);
    }

    /**
     * Removes the task at the given zero-based index.
     *
     * @param index zero-based position of the task
     * @return {@code true} if the task was removed, {@code false} if the index is out of bounds
     */
    public boolean remove(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.remove(index);
        return true;
    }

    /**
     * Returns a copy of all stored tasks in insertion order.
     *
     * @return new mutable list with all tasks
     */
    public List<String> getAll() {
        return new ArrayList<>(tasks);
    }

    /**
     * Returns the number of stored tasks.
     *
     * @return tasks count
     */
    public int size() {
        return tasks.size();
    }
}
