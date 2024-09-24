package example.taskmanager.service;

import example.taskmanager.TaskMapper;
import example.taskmanager.TaskRepository;
import example.taskmanager.dto.TaskDTO;
import example.taskmanager.model.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static example.taskmanager.TaskMapper.convertToEntity;

@Component
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDTO> getAllTasks(@RequestParam(value = "completed", required = false) Optional<Boolean> completed) {
        List<TaskEntity> tasks;
        if (completed.isPresent()) {
            tasks = taskRepository.findByCompleted(completed.get());
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream()
                .map(taskMapper::convertToDTO)
                .toList();
    }

    public TaskDTO getTaskById(@PathVariable("id") Long id) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(id);
        return optionalTask
                .map(task -> taskMapper.convertToDTO(task))
                .orElseThrow(() -> new NoSuchElementException("Task with ID " + id + " not found"));
    }

    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        TaskEntity task = convertToEntity(taskDTO);
        TaskEntity savedTask = taskRepository.save(task);
        return taskMapper.convertToDTO(savedTask);
    }

    public TaskDTO updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (task.isPresent()) {
            TaskEntity taskToUpdate = task.get();
            taskToUpdate.setTitle(taskDTO.getTitle());
            taskToUpdate.setDescription(taskDTO.getDescription());
            taskToUpdate.setCompleted(taskDTO.isCompleted());
            TaskEntity updatedTask = taskRepository.save(taskToUpdate);
            return taskMapper.convertToDTO(updatedTask);
        } else {
            throw new NoSuchElementException("Task with ID " + id + " not found");
        }
    }

    public void removeTask(@PathVariable("id") Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Task with ID " + id + " not found");
        }
    }
}
