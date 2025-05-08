package managers;

import tasks.Task;

import java.util.ArrayList;

/*
Я бы добавил в HistoryManager еще методы удаления и обновления, но в задаче про них ничего нет.
Хотя зачем нам знать, что мы просматривали какую-то задачу, если ее удалили из TaskManager
(как будто только если мы хотим потом восстановить эту задачу)
 */
public interface HistoryManager {

    void add(Task task);

    ArrayList<Task> getHistory();
}
