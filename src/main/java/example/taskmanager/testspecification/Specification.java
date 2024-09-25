package example.taskmanager.testspecification;

import com.github.javafaker.Faker;

import static groovy.json.JsonOutput.toJson;
import static org.apache.http.HttpStatus.SC_OK;

public class Specification {

    public final static Faker FAKER = new Faker();

    public static TaskModel buildSampleTask() {
        return TaskModel.builder()
                .title(FAKER.dune().character())
                .description(FAKER.dune().quote())
                .build();
    }

    public static TaskModel addTask() {
        var response = TaskManagerService.postTask(toJson(buildSampleTask()), SC_OK);

        return ResponseParser.parse(response, TaskModel.class);
    }

    public static TaskModel getTaskById(int id) {
        var response = TaskManagerService.getTask(id, SC_OK);

        return ResponseParser.parse(response, TaskModel.class);
    }

    public static void feedData() {
        for (int i = 0; i < 3; i++) {
            addTask();
        }
    }
}
