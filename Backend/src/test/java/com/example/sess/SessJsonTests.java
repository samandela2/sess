package com.example.sess;

import com.example.sess.models.*;
import org.assertj.core.util.Arrays;
// import org.hibernate.mapping.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ActiveProfiles("test")
public class SessJsonTests {

    @Autowired
    private JacksonTester<Task> json;
    @Autowired
    private JacksonTester<Task[]> jsonList;
    private Task[] tasks;
    // private List<Task> tasks;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    @BeforeEach
    void setUp() throws ParseException {

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy
        // HH:mm:ss");
        // tasks = Arrays.asList(
        // new Task(99L, LocalDateTime.parse("01/01/2024 10:00:00", formatter),
        // LocalDateTime.parse("01/01/2024 11:00:00", formatter), 30L, 50L, "3333 hell
        // st, San Francisco, CA, 94444", "appointment", "happy day"),
        // new Task(100L, LocalDateTime.parse("02/01/2024 10:00:00", formatter),
        // LocalDateTime.parse("02/01/2024 11:00:00", formatter), 31L, 50L, "3333 hell
        // st, San Francisco, CA, 94444", "appointment", null),
        // new Task(101L, LocalDateTime.parse("03/01/2024 12:00:00", formatter),
        // LocalDateTime.parse("03/01/2024 16:00:00", formatter), 30L, null, "33 hl st,
        // San Francisco, CA, 94444", "event", "happy day")
        // );
        tasks = Arrays.array(
                new Task(99L, LocalDateTime.parse("01/01/2024 10:00:00", formatter),
                        LocalDateTime.parse("01/01/2024 11:00:00", formatter), 30L, 50L,
                        "3333 hell st, San Francisco, CA, 94444", "appointment", "happy day"),
                new Task(101L, LocalDateTime.parse("03/01/2024 12:00:00", formatter),
                        LocalDateTime.parse("03/01/2024 16:00:00", formatter), 30L, null,
                        "33 hl st, San Francisco, CA, 94444", "event", "happy day"),
                new Task(100L, LocalDateTime.parse("02/01/2024 10:00:00", formatter),
                        LocalDateTime.parse("02/01/2024 11:00:00", formatter), 31L, 50L,
                        "3333 hell st, San Francisco, CA, 94444", "appointment", null));
    }

    @Test
    public void taskSerializationTest() throws IOException {
        // Task task = tasks.get(0);
        Task task = tasks[0];
        assertThat(json.write(task)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(task)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(task)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(task)).hasJsonPathValue("@.start_time");
        assertThat(json.write(task)).extractingJsonPathValue("@.start_time")
                .isEqualTo("2024-01-01 10:00:00");
        assertThat(json.write(task)).hasJsonPathValue("@.end_time");
        assertThat(json.write(task)).extractingJsonPathValue("@.end_time")
                .isEqualTo("2024-01-01 11:00:00");
        assertThat(json.write(task)).hasJsonPathNumberValue("@.owner_id");
        assertThat(json.write(task)).extractingJsonPathNumberValue("@.owner_id")
                .isEqualTo(30);
        assertThat(json.write(task)).hasJsonPathNumberValue("@.client_id");
        assertThat(json.write(task)).extractingJsonPathNumberValue("@.client_id")
                .isEqualTo(50);

        assertThat(json.write(task)).hasJsonPathStringValue("@.location");
        assertThat(json.write(task)).extractingJsonPathStringValue("@.location")
                .isEqualTo("3333 hell st, San Francisco, CA, 94444");
        assertThat(json.write(task)).hasJsonPathStringValue("@.type");
        assertThat(json.write(task)).extractingJsonPathStringValue("@.type")
                .isEqualTo("appointment");
        assertThat(json.write(task)).hasJsonPathStringValue("@.description");
        assertThat(json.write(task)).extractingJsonPathStringValue("@.description")
                .isEqualTo("happy day");
    }

    @Test
    public void taskSerializationTestWithNullField() throws IOException {
        Task task = tasks[1];
        assertThat(json.write(task)).isStrictlyEqualToJson("singleWithNull.json");
        assertThat(json.write(task)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(task)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(101);
        assertThat(json.write(task)).hasJsonPathValue("@.start_time");
        assertThat(json.write(task)).extractingJsonPathValue("@.start_time")
                .isEqualTo("2024-03-01 12:00:00");
        assertThat(json.write(task)).hasJsonPathValue("@.end_time");
        assertThat(json.write(task)).extractingJsonPathValue("@.end_time")
                .isEqualTo("2024-03-01 16:00:00");
        assertThat(json.write(task)).hasJsonPathNumberValue("@.owner_id");
        assertThat(json.write(task)).extractingJsonPathNumberValue("@.owner_id")
                .isEqualTo(30);
        assertThat(json.write(task)).hasEmptyJsonPathValue("@.client_id");
        // assertThat(json.write(task)).extractingJsonPathNumberValue("@.client_id")
        // .isNull();

        assertThat(json.write(task)).hasJsonPathStringValue("@.location");
        assertThat(json.write(task)).extractingJsonPathStringValue("@.location")
                .isEqualTo("33 hl st, San Francisco, CA, 94444");
        assertThat(json.write(task)).hasJsonPathStringValue("@.type");
        assertThat(json.write(task)).extractingJsonPathStringValue("@.type")
                .isEqualTo("event");
        assertThat(json.write(task)).hasJsonPathStringValue("@.description");
        assertThat(json.write(task)).extractingJsonPathStringValue("@.description")
                .isEqualTo("happy day");

    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(tasks)).isStrictlyEqualToJson("list.json");
    }

    @Test
    public void taskDeserializationTest() throws IOException {
        String expected = """
                {
                    "id": 99,
                    "start_time": "2024-01-01 10:00:00",
                    "end_time": "2024-01-01 11:00:00",
                    "owner_id": 30,
                    "client_id": 50,
                    "location": "3333 hell st, San Francisco, CA, 94444",
                    "type": "appointment",
                    "description": "happy day"
                }
                    """;
        assertThat(json.parse(expected)).isEqualTo(new Task(99L, LocalDateTime.parse("01/01/2024 10:00:00", formatter),
                LocalDateTime.parse("01/01/2024 11:00:00", formatter), 30L, 50L,
                "3333 hell st, San Francisco, CA, 94444", "appointment", "happy day"));
        assertThat(json.parseObject(expected).getId()).isEqualTo(99);
        assertThat(json.parseObject(expected).getStart_time())
                .isEqualTo(LocalDateTime.parse("01/01/2024 10:00:00", formatter));
        assertThat(json.parseObject(expected).getEnd_time())
                .isEqualTo(LocalDateTime.parse("01/01/2024 11:00:00", formatter));
        assertThat(json.parseObject(expected).getOwner_id()).isEqualTo(30L);
        assertThat(json.parseObject(expected).getClient_id()).isEqualTo(50L);
        assertThat(json.parseObject(expected).getType()).isEqualTo("appointment");
        assertThat(json.parseObject(expected).getDescription()).isEqualTo("happy day");
    }

    @Test
    public void taskDeserializationTestWithNullField() throws IOException {
        String expected = """
                {
                    "id": 101,
                    "start_time": "2024-03-01 12:00:00",
                    "end_time": "2024-03-01 16:00:00",
                    "owner_id": 30,
                    "location": "33 hl st, San Francisco, CA, 94444",
                    "type": "event",
                    "description": "happy day"
                }
                    """;
        assertThat(json.parse(expected)).isEqualTo(new Task(101L, LocalDateTime.parse("03/01/2024 12:00:00", formatter),
                LocalDateTime.parse("03/01/2024 16:00:00", formatter), 30L, null, "33 hl st, San Francisco, CA, 94444",
                "event", "happy day"));
        assertThat(json.parseObject(expected).getId()).isEqualTo(101);
        assertThat(json.parseObject(expected).getStart_time())
                .isEqualTo(LocalDateTime.parse("03/01/2024 12:00:00", formatter));
        assertThat(json.parseObject(expected).getEnd_time())
                .isEqualTo(LocalDateTime.parse("03/01/2024 16:00:00", formatter));
        assertThat(json.parseObject(expected).getOwner_id()).isEqualTo(30L);
        assertThat(json.parseObject(expected).getClient_id()).isNull();
        ;
        assertThat(json.parseObject(expected).getType()).isEqualTo("event");
        assertThat(json.parseObject(expected).getDescription()).isEqualTo("happy day");

    }

    @Test
    public void taskListDeserializationTest() throws IOException {
        String expected = """
                [
                    {"id":99,"start_time": "2024-01-01 10:00:00","end_time": "2024-01-01 11:00:00","owner_id": 30,"client_id": 50,"location": "3333 hell st, San Francisco, CA, 94444","type": "appointment","description": "happy day"},
                    {"id":101,"start_time": "2024-03-01 12:00:00","end_time": "2024-03-01 16:00:00","owner_id": 30,"location": "33 hl st, San Francisco, CA, 94444","type": "event","description": "happy day"},
                    {"id":100,"start_time": "2024-02-01 10:00:00","end_time": "2024-02-01 11:00:00","owner_id": 31,"client_id": 50,"location": "3333 hell st, San Francisco, CA, 94444","type": "appointment","description": null}
                    ]
                """;

        // ObjectContent<Task[]> objectContent = jsonList.parse(expected);
        // Task[] tasks1 = objectContent.getObject();
        // assertThat(tasks1[0]).isEqualTo(tasks[0]);
        // assertThat(tasks1[1]).isEqualTo(tasks[1]);
        // assertThat(tasks1[1]).isEqualTo(tasks[1]);

        assertThat(jsonList.parse(expected)).isEqualTo(tasks);

    }
}
