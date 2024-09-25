import example.taskmanager.testspecification.Specification;
import example.taskmanager.testspecification.TaskManagerService;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static example.taskmanager.testspecification.Specification.addTask;
import static example.taskmanager.testspecification.Specification.buildSampleTask;
import static groovy.json.JsonOutput.toJson;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskManagerTest {


    // response approach
    @Test
    @DisplayName("Task should be created successfully")
    void createTaskTest() {
        final var sampleTask = buildSampleTask();
        var response = TaskManagerService.postTask(toJson(sampleTask), SC_OK);

        assertThat(response.getBody().asString())
                .as("Response body should contain sampleTask and its values")
                .containsAnyOf(
                        sampleTask.getTitle(),
                        sampleTask.getDescription());
        response
                .then()
                .body(matchesJsonSchemaInClasspath("taskSchema.json"));
    }

    @Test
    @DisplayName("Task should be returned by its id")
    void taskByIdTest() {
        var expectedTask = addTask();
        var taskId = expectedTask.getId();
        var response = TaskManagerService.getTask(taskId, SC_OK);

        assertThat(response.getBody().asString())
                .contains(taskId.toString())
                .contains(expectedTask.getDescription())
                .contains(expectedTask.getTitle());
    }

    @Test
    @DisplayName("Task should be returned by its id - model object approach")
    void getTaskByIdTest() {
        var expectedTask = addTask();
        var taskId = expectedTask.getId();

        var actualTask = Specification.getTaskById(taskId);

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(actualTask).usingRecursiveComparison()
                .as("Actual task should be equal to expected task")
                .isEqualTo(expectedTask);

        // other approach to assertion

        softly.assertThat(actualTask.getId()).
                isEqualTo(expectedTask.getId());
        softly.assertThat(actualTask.getDescription()).
                isEqualTo(expectedTask.getDescription());
        softly.assertThat(actualTask.getTitle()).
                isEqualTo(expectedTask.getTitle());
        softly.assertThat(actualTask.isCompleted()).
                isEqualTo(expectedTask.isCompleted());
    }
}
