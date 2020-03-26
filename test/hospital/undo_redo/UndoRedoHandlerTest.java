package hospital.undo_redo;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/*
Test cases:
    * add action
    * correct method and arguments for undo and redo
    * undo
    * redo
    * executor doesn't have a method like undo or redo
    * Wrong arguments for a undo or redo
    * clear undo and redo stack
    * undo on empty stack
    * redo on empty stack
    * is redo stack deleted after new action added?
    * is redo stack deleted after an unsuccessful undo?
    * make undo or redo not possible (e.g. remove element which should be removed by undo)
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UndoRedoHandlerTest {

    static class TestExecutor implements UndoRedoExecutor {

        private Set<String> set = new HashSet<>();

        public char add(String element) {
            set.add(element);
            return element.charAt(0);
        }

        public boolean remove(String element) {
            if (element.equals("throw")) {
                throw new Error();
            }
            return set.remove(element);
        }

        public Set<String> getSet() {
            return set;
        }
    }

    class TestCommand {
        static final String addName = "Add new element to set";
        static final String removeName = "Remove element from set";

        boolean isAdd;
        String data;

        TestCommand(boolean isAdd, String data) {
            this.isAdd = isAdd;
            this.data = data;
        }
    }

    private static UndoRedoHandler undoHandler;

    static TestExecutor testData;

    @BeforeAll
    static void beforeAll() {
        undoHandler = new UndoRedoHandler();
        testData = new TestExecutor();
    }

    @Test
    @Order(1)
    void undoAndRedoOnEmptyUndoHistory() {
        assertAll(
                () -> assertNull(undoHandler.undo()),
                () -> assertNull(undoHandler.redo()),
                () -> assertEquals(new HashSet<String>(), testData.getSet())
        );
    }

    @Test
    @Order(2)
    void doOneAction() {
        testData.add("1");
        try {
            undoHandler.addAction(new Action(
                    "Add new element to list",
                    testData,
                    testData.getClass().getMethod("remove", String.class),
                    new Object[]{"1"},
                    testData.getClass().getMethod("add", String.class),
                    new Object[]{"1"})
            );
        } catch (NoSuchMethodException e) {
            fail("There should be no exception", e);
        }
    }

    @Test
    @Order(3)
    void undoOneAction() {
        try {
            boolean returned = (boolean) undoHandler.undo();
            assertAll(
                    () -> assertEquals(new HashSet<>(), testData.getSet()),
                    () -> assertTrue(returned)
            );
        } catch (UndoNotPossibleException e) {
            fail("There shouldn't be any exception", e);
        }
    }

    @Test
    @Order(4)
    void redoOneAction() {
        try {
            char returned = (char) undoHandler.redo();
            assertAll(
                    () -> assertEquals(new HashSet<>(Collections.singleton("1")), testData.getSet()),
                    () -> assertEquals('1', returned)
            );
        } catch (RedoNotPossibleException e) {
            fail("There shouldn't be any exception", e);
        }
    }

    @Test
    @Order(5)
    void doMultipleAction() {
        TestCommand[] commands = new TestCommand[]{
                new TestCommand(true, "1"),
                new TestCommand(true, "test"),
                new TestCommand(true, "hhh"),
                new TestCommand(false, "test"),
                new TestCommand(true, "jjj"),
                new TestCommand(false, "1")
        };
        try {
            for (TestCommand command : commands) {
                if (command.isAdd) {
                    testData.add(command.data);
                } else {
                    testData.remove(command.data);
                }
                undoHandler.addAction(new Action(
                        command.isAdd ? TestCommand.addName : TestCommand.removeName,
                        testData,
                        testData.getClass().getMethod(command.isAdd ? "remove" : "add", String.class),
                        new Object[]{command.data},
                        testData.getClass().getMethod(command.isAdd ? "add" : "remove", String.class),
                        new Object[]{command.data})
                );
            }
        } catch (NoSuchMethodException e) {
            fail("There should be no exception", e);
        }
    }

    @Test
    @Order(6)
    void undoAndRedoMultipleTimes() {
        try {
            List<Object> results = new ArrayList<>();
            results.add(undoHandler.undo()); // '1'
            results.add(undoHandler.undo()); // true
            results.add(undoHandler.undo()); // 't'
            results.add(undoHandler.undo()); // true
            results.add(undoHandler.redo()); // 'h'
            results.add(undoHandler.redo()); // true
            results.add(undoHandler.redo()); // 'j'
            results.add(undoHandler.undo()); // true
            results.add(undoHandler.redo()); // 'j'
            assertAll(
                    () -> assertEquals(new HashSet<>(Arrays.asList("1", "1", "hhh", "jjj")), testData.getSet()),
                    () -> assertEquals(
                            Arrays.asList('1', true, 't', true, 'h', true, 'j', true, 'j'),
                            results)
            );
        } catch (UndoNotPossibleException | RedoNotPossibleException e) {
            fail("There shouldn't be any exception", e);
        }
    }

    @Test
    @Order(7)
    void testIfRedoHistoryIsClearedAfterNewAction() {
        testData.add("new");
        try {
            undoHandler.addAction(new Action(
                    "Test redo clear",
                    testData,
                    testData.getClass().getMethod("remove", String.class),
                    new Object[]{"new"},
                    testData.getClass().getMethod("add", String.class),
                    new Object[] {"new"}
            ));
        } catch (NoSuchMethodException e) {
            fail("There shouldn't be an exception", e);
        }
        assertAll(
                () -> assertNull(undoHandler.redo()),
                () -> assertEquals(new HashSet<>(Arrays.asList("1", "1", "hhh", "jjj", "new")), testData.getSet())
        );
    }

    @Test
    @Order(8)
    void testWhenUndoUnsuccessful() {
        testData.add("throw");
        try {
            undoHandler.addAction(new Action(
                    "Test exception throw",
                    testData,
                    testData.getClass().getMethod("remove", String.class),
                    new Object[] {"throw"},
                    testData.getClass().getMethod("add", String.class),
                    new Object[] {"throw"}
            ));
        } catch (NoSuchMethodException e) {
            fail("There shouldn't be an exception", e);
        }
        assertAll(
                () -> assertThrows(UndoNotPossibleException.class, () -> undoHandler.undo()),
                // Test if the redo stack is empty as it should be
                () -> assertNull(undoHandler.redo()),
                // Test if the next undo will be successful, because the previous action is removed from the undoable actions stack.
                () -> undoHandler.undo(),
                () -> assertEquals(new HashSet<>(Arrays.asList("1", "1", "hhh", "jjj", "throw")), testData.getSet())
        );
    }

    @Test
    @Order(9)
    void testWhenRedoUnsuccessful() {
        try {
            // Add an action which cannot be redo.
            undoHandler.addAction(new Action(
                    "Test exception throw",
                    testData,
                    testData.getClass().getMethod("add", String.class),
                    new Object[] {"throw"},
                    testData.getClass().getMethod("remove", String.class),
                    new Object[] {"throw"}
            ));
            // Add another action, so after two undo, we'll have a not possible redo, than a possible redo in the redoable actions stack.
            testData.add("xyz");
            undoHandler.addAction(new Action(
                    TestCommand.addName,
                    testData,
                    testData.getClass().getMethod("remove", String.class),
                    new Object[] {"xyz"},
                    testData.getClass().getMethod("add", String.class),
                    new Object[] {"xyz"}
            ));
            undoHandler.undo();
            undoHandler.undo();
        } catch (NoSuchMethodException | UndoNotPossibleException e) {
            fail("There shouldn't be an exception", e);
        }
        assertAll(
                () -> assertThrows(RedoNotPossibleException.class, () -> undoHandler.redo()),
                // Test if the next redo will be successful, because the previous action is removed from the redoable actions stack.
                // This way we can test how the redoable stack act after an unsuccessful redo.
                () -> undoHandler.redo(),
                () -> assertEquals(new HashSet<>(Arrays.asList("1", "1", "hhh", "jjj", "xyz", "throw")), testData.getSet())
        );
    }

    @Test
    @Order(10)
    void clearUndoHistory() {
        undoHandler.clearHistory();
        assertAll(
                () -> assertNull(undoHandler.undo()),
                () -> assertEquals(new HashSet<>(Arrays.asList("1", "1", "hhh", "jjj", "xyz", "throw")), testData.getSet()),
                () -> assertNull(undoHandler.redo()),
                () -> assertEquals(new HashSet<>(Arrays.asList("1", "1", "hhh", "jjj", "xyz", "throw")), testData.getSet())
        );
    }

    @Test
    void addActionWithNonexistentMethod() {
        assertThrows(NoSuchMethodException.class,
                () -> undoHandler.addAction(new Action(
                        "Nonexistent method",
                        testData,
                        testData.getClass().getMethod("remove", String.class),
                        new Object[] {"1"},
                        testData.getClass().getMethod("nonexistent"),
                        null))
        );
    }

    @Test
    void addActionWithMethodFromWrongClass() {
        assertThrows(IllegalArgumentException.class,
                () -> undoHandler.addAction(new Action(
                        "Method from third class",
                        testData,
                        Action.class.getMethod("undo"),
                        null,
                        testData.getClass().getMethod("add", String.class),
                        new Object[] {"1"}
                )));
    }

    @Test
    void addActionWithWrongArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> undoHandler.addAction(new Action(
                        "Wrong arguments",
                        testData,
                        testData.getClass().getMethod("add", String.class),
                        new Object[] {new Object()},
                        testData.getClass().getMethod("remove", String.class),
                        new Object[] {"1"}
                )));
    }
}