package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TodoListExtraCommandsTest {

    @Test
    @DisplayName("new tasks are not done by default")
    void newTaskIsNotDone() {
        TodoList todoList = new TodoList();
        todoList.add("buy milk");

        Task task = todoList.getAll().get(0);

        assertFalse(task.isDone());
        assertEquals("[ ]", task.getStatus());
    }

    @Test
    @DisplayName("clear removes every task")
    void clearRemovesAllTasks() {
        TodoList todoList = new TodoList();
        todoList.add("first");
        todoList.add("second");

        todoList.clear();

        assertTrue(todoList.getAllTexts().isEmpty());
        assertEquals(0, todoList.size());
    }

    @Test
    @DisplayName("clear on an empty list does nothing")
    void clearOnEmptyList() {
        TodoList todoList = new TodoList();

        todoList.clear();

        assertEquals(0, todoList.size());
    }

    @Test
    @DisplayName("done marks an existing task as done")
    void doneMarksTask() {
        TodoList todoList = new TodoList();
        todoList.add("buy milk");

        assertTrue(todoList.markAsDone(0));

        Task task = todoList.getAll().get(0);
        assertTrue(task.isDone());
        assertEquals("[x]", task.getStatus());
        assertEquals("buy milk", task.getText());
    }

    @Test
    @DisplayName("done keeps the task order and other tasks untouched")
    void doneKeepsOtherTasksUntouched() {
        TodoList todoList = new TodoList();
        todoList.add("first");
        todoList.add("second");
        todoList.add("third");

        todoList.markAsDone(1);

        List<Task> tasks = todoList.getAll();
        assertFalse(tasks.get(0).isDone());
        assertTrue(tasks.get(1).isDone());
        assertFalse(tasks.get(2).isDone());
        assertEquals(List.of("first", "second", "third"), todoList.getAllTexts());
    }

    @Test
    @DisplayName("done on a negative index returns false")
    void doneWithNegativeIndex() {
        TodoList todoList = new TodoList();
        todoList.add("first");

        assertFalse(todoList.markAsDone(-1));
        assertFalse(todoList.getAll().get(0).isDone());
    }

    @Test
    @DisplayName("done on an index equal to size returns false")
    void doneWithIndexEqualToSize() {
        TodoList todoList = new TodoList();
        todoList.add("first");

        assertFalse(todoList.markAsDone(1));
        assertFalse(todoList.getAll().get(0).isDone());
    }

    @Test
    @DisplayName("done on an empty list returns false")
    void doneOnEmptyList() {
        TodoList todoList = new TodoList();

        assertFalse(todoList.markAsDone(0));
        assertEquals(0, todoList.size());
    }

    @Test
    @DisplayName("search ignores case")
    void searchIgnoresCase() {
        TodoList todoList = new TodoList();
        todoList.add("Buy Milk");
        todoList.add("write report");
        todoList.add("milk a cow");

        List<Task> found = todoList.search("MILK");

        assertEquals(List.of("Buy Milk", "milk a cow"), textsOf(found));
    }

    @Test
    @DisplayName("search returns an empty list when nothing matches")
    void searchReturnsEmptyListWhenNoMatch() {
        TodoList todoList = new TodoList();
        todoList.add("buy milk");

        assertTrue(todoList.search("pizza").isEmpty());
    }

    @Test
    @DisplayName("search on an empty list returns an empty list")
    void searchOnEmptyList() {
        TodoList todoList = new TodoList();

        assertTrue(todoList.search("milk").isEmpty());
    }

    @Test
    @DisplayName("search with an empty query matches every task")
    void searchWithEmptyQueryMatchesEverything() {
        TodoList todoList = new TodoList();
        todoList.add("buy milk");
        todoList.add("write report");

        assertEquals(List.of("buy milk", "write report"), textsOf(todoList.search("")));
    }

    @Test
    @DisplayName("search with a null query returns an empty list")
    void searchWithNullQuery() {
        TodoList todoList = new TodoList();
        todoList.add("buy milk");

        assertTrue(todoList.search(null).isEmpty());
    }

    @Test
    @DisplayName("search matches a substring inside the text")
    void searchMatchesSubstring() {
        TodoList todoList = new TodoList();
        todoList.add("buy milk");
        todoList.add("buy books");

        assertEquals(List.of("buy milk", "buy books"), textsOf(todoList.search("bu")));
    }

    private List<String> textsOf(List<Task> tasks) {
        return tasks.stream().map(Task::getText).toList();
    }
}
