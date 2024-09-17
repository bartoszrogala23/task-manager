package example.taskmanager.controller;

import example.taskmanager.TaskRepository;
import example.taskmanager.dto.TaskDTO;
import example.taskmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        return task;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask
                .map(task -> ResponseEntity.ok(convertToDTO(task)))
                .orElseGet(() -> ResponseEntity.notFound()
                        .build());
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task taskToUpdate = task.get();
            taskToUpdate.setTitle(taskDTO.getTitle());
            taskToUpdate.setDescription(taskDTO.getDescription());
            taskToUpdate.setCompleted(taskDTO.isCompleted());
            Task updatedTask = taskRepository.save(taskToUpdate);
            return ResponseEntity.ok(convertToDTO(updatedTask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
