package example.taskmanager.testspecification;

import io.restassured.response.Response;

import static example.taskmanager.testspecification.Endpoint.TASK;
import static example.taskmanager.testspecification.Endpoint.TASKS;
import static io.restassured.RestAssured.given;

public class TaskManagerService {

    public static Response getTasks(int httpStatus) {
        return given()
                .log()
                .ifValidationFails()
                .contentType("application/json")
                .when()
                .get(TASKS.getEndpoint())
                .then()
                .log()
                .ifValidationFails()
                .statusCode(httpStatus)
                .extract()
                .response();
    }

    public static Response postTask(String body, int httpStatus) {
        return given()
                .log()
                .ifValidationFails()
                .contentType("application/json")
                .body(body)
                .when()
                .post(TASKS.getEndpoint())
                .then()
                .log()
                .ifValidationFails()
                .statusCode(httpStatus)
                .extract()
                .response();
    }

    public static Response getTask(int id, int httpStatus) {
        return given()
                .log()
                .ifValidationFails()
                .contentType("application/json")
                .when()
                .pathParam("id", id)
                .get(TASK.getEndpoint())
                .then()
                .log()
                .ifValidationFails()
                .statusCode(httpStatus)
                .extract()
                .response();
    }
}
