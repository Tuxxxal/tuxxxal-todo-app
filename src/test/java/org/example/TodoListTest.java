package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TodoListTest {

    @Test
    @DisplayName("add stores the task text as is")
    void addKeepsTaskText() {
        TodoList todoList = new TodoList();

        todoList.add("buy milk");

        assertEquals(List.of("buy milk"), todoList.getAll());
        assertEquals(1, todoList.size());
    }

    @Test
    @DisplayName("add trims leading and trailing whitespace")
    void addTrimsWhitespace() {
        TodoList todoList = new TodoList();

        todoList.add("   buy milk   ");
        todoList.add("\twrite report\n");

        assertEquals(List.of("buy milk", "write report"), todoList.getAll());
    }

    @Test
    @DisplayName("add ignores null, empty and blank strings")
    void addIgnoresEmptyValues() {
        TodoList todoList = new TodoList();

        todoList.add(null);
        todoList.add("");
        todoList.add("   ");
        todoList.add("\t \n");

        assertTrue(todoList.getAll().isEmpty());
        assertEquals(0, todoList.size());
    }

    @Test
    @DisplayName("getAll returns a defensive copy")
    void getAllReturnsCopy() {
        TodoList todoList = new TodoList();
        todoList.add("first");

        List<String> copy = todoList.getAll();
        copy.add("hacked");
        copy.remove(0);

        assertEquals(List.of("first"), todoList.getAll());
    }

    @Test
    @DisplayName("remove deletes an existing task and returns true")
    void removeExistingTask() {
        TodoList todoList = new TodoList();
        todoList.add("first");
        todoList.add("second");

        assertTrue(todoList.remove(0));

        assertEquals(List.of("second"), todoList.getAll());
        assertEquals(1, todoList.size());
    }

    @Test
    @DisplayName("remove returns false for negative index")
    void removeNegativeIndex() {
        TodoList todoList = new TodoList();
        todoList.add("first");

        assertFalse(todoList.remove(-1));
        assertEquals(1, todoList.size());
    }

    @Test
    @DisplayName("remove returns false for index equal to size")
    void removeIndexEqualToSize() {
        TodoList todoList = new TodoList();
        todoList.add("first");
        todoList.add("second");

        assertFalse(todoList.remove(2));
        assertEquals(2, todoList.size());
    }

    @Test
    @DisplayName("remove on an empty list always returns false")
    void removeFromEmptyList() {
        TodoList todoList = new TodoList();

        assertFalse(todoList.remove(0));
        assertFalse(todoList.remove(-5));
        assertEquals(0, todoList.size());
    }

    @Test
    @DisplayName("size reflects the number of stored tasks")
    void sizeTracksTasks() {
        TodoList todoList = new TodoList();

        assertEquals(0, todoList.size());

        todoList.add("one");
        todoList.add("two");
        assertEquals(2, todoList.size());

        todoList.remove(1);
        assertEquals(1, todoList.size());
    }
}
