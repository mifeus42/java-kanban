package tasks;

import java.util.Objects;

public class Task {
    protected final String name;
    protected final String description;
    protected final int id;
    protected final TaskStatus status;

    public Task(String name, String description, int id) {
        this(name, description, TaskStatus.NEW, id);
    }

    public Task(String name, String description, TaskStatus status, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Task task = (Task) object;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "tasks.Task{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
