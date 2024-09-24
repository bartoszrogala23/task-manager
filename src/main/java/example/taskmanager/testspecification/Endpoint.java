package example.taskmanager.testspecification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoint {

    LOCAL_HOST("http://localhost:8080"),
    TASK(LOCAL_HOST.endpoint + "/api/tasks/{id}"),
    TASKS(LOCAL_HOST.endpoint + "/api/tasks");

    public final String endpoint;
}
