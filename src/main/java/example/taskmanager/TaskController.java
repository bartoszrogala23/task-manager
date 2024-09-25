package example.taskmanager;

import example.taskmanager.dto.TaskDTO;
import example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> getTasks(@RequestParam(value = "completed", required = false) Optional<Boolean> completed) {
        return taskService.getAllTasks(completed);
    }

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable("id") Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public TaskDTO postTask(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @PutMapping("/{id}")
    public TaskDTO putTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(id, taskDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        taskService.removeTask(id);
    }
}
