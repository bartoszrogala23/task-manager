import example.taskmanager.testspecification.Specification;
import example.taskmanager.testspecification.TaskManagerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static groovy.json.JsonOutput.toJson;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskManagerTest {


    @Test
    @DisplayName("Task should be created successfully")
    void createTaskTest() {
        final var sampleTask = Specification.buildSampleTask();
        var response = TaskManagerService.addTask(toJson(sampleTask), SC_OK);

        assertThat(response.getBody().asString())
                .as("Response body should contain sampleTask and its values")
                .containsAnyOf(
                        sampleTask.getTitle(),
                        sampleTask.getDescription());
    }

}
