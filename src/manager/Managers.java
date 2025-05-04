package manager;

import java.io.File;
import java.io.IOException;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getFileBacked(String name) {
        try {
            return FileBackedTaskManager.loadFromFile(new File(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
