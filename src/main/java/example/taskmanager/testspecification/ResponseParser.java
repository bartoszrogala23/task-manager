package example.taskmanager.testspecification;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseParser {

    private static final Gson GSON_INSTANCE = new Gson();
    private static final ObjectMapper OBJECT_MAPPER_INSTANCE = new ObjectMapper();

    public static <T> T parse(Response response, Class<T> valueObject) {

        return parse(response.body().asString(), valueObject);
    }

    public static <T> T parse(String response, Class<T> valueObject) {

        return GSON_INSTANCE.fromJson(response, valueObject);
    }

    public static <T> List<T> parseCollection(String response, Class<T[]> valueObject) {
        final T[] jsonToObject = GSON_INSTANCE.fromJson(response, valueObject);

        return Arrays.asList(jsonToObject);
    }

    public static <T> List<T> parseCollection(Response response, Class<T[]> valueObject) {
        return parseCollection(response.body().asString(), valueObject);
    }

    public static <T> List<T> parseCollectionFromJsonNode(Response response, String jsonNode, Class<T> valueObject) {
        return response.jsonPath().getList(jsonNode, valueObject);
    }

    @SneakyThrows
    public static JsonNode parseToJsonNode(String json) {
        return OBJECT_MAPPER_INSTANCE.readTree(json);
    }
}
