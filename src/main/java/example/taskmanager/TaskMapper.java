package example.taskmanager;

import example.taskmanager.dto.TaskDTO;
import example.taskmanager.model.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDTO convertToDTO(TaskEntity task) {
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }

    public static TaskEntity convertToEntity(TaskDTO taskDTO) {
        TaskEntity task = new TaskEntity();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        return task;
    }
}
