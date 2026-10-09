package example.taskmanagerservice.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Endpoint {

    LOCAL_HOST("http://localhost:8080"),
    DOCKER_HOST("http://host.docker.internal:8080"),
    TASK(DOCKER_HOST.endpoint + "/api/tasks/{id}"),
    TASKS(DOCKER_HOST.endpoint + "/api/tasks");

    public final String endpoint;
}
