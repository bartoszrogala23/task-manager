package example.taskmanager.testspecification;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TaskModel {
    Integer  id;
    String title;
    String description;
    boolean completed;
}
