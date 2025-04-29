import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {
    private final ArrayList<Integer> subtaskIds;

    public EpicTask(String name, String description, int id) {
        super(name, description, id);
        subtaskIds = new ArrayList<>();
    }

    public EpicTask(String name, String description, TaskStatus status, int id, ArrayList<Integer> subtaskIds) {
        super(name, description, status, id);
        this.subtaskIds = subtaskIds;
    }

    /*
    Если я возвращаю копию subtaskIds, то сам лист изменить нельзя. Но новый лист будет хранить ссылки на объекты,
    которые хранились в старом листе. В данном случае объекты Integer и их по ссылке изменить не получится,
    но если у меня объекты изменяемые, то что делать?
    */
    public ArrayList<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", subtasks=" + subtaskIds +
                "} ";
    }
}
