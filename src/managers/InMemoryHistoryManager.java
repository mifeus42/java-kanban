package managers;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private final ArrayList<Task> historyTasks;
    private final int COUNT_TASK_IN_HISTORY = 10;

    public InMemoryHistoryManager() {
        historyTasks = new ArrayList<>(COUNT_TASK_IN_HISTORY);
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        if (historyTasks.size() == COUNT_TASK_IN_HISTORY) {
            historyTasks.removeFirst();
        }

        historyTasks.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(historyTasks);
    }
}
