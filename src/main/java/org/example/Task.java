package org.example;

/**
 * Single todo task with a completion status.
 *
 * <p>The text is immutable, the status can only be switched to "done".
 */
public final class Task {

    private final String text;
    private boolean done;

    public Task(String text) {
        this(text, false);
    }

    public Task(String text, boolean done) {
        this.text = text;
        this.done = done;
    }

    /**
     * Returns the trimmed task text.
     *
     * @return task text
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return {@code true} if the task is done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.done = true;
    }

    /**
     * Returns the status marker used by the console output.
     *
     * @return {@code [x]} for done tasks and {@code [ ]} for the rest
     */
    public String getStatus() {
        return done ? "[x]" : "[ ]";
    }

    @Override
    public String toString() {
        return getStatus() + " " + text;
    }
}
