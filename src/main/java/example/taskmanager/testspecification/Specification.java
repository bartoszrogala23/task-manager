package example.taskmanager.testspecification;

import com.github.javafaker.Faker;

public class Specification {

    public final static Faker FAKER = new Faker();

    public static TaskModel buildSampleTask() {
        return TaskModel.builder()
                .title(FAKER.dune().character())
                .description(FAKER.dune().quote())
                .build();
    }

}
