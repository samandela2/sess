package com.example.sess;

import com.example.sess.models.*;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class SessJsonTests {
    
    @Autowired
    private JacksonTester<Task> json;
    private Task[] tasks;

    @BeforeEach
    void setUp() {
        tasks = Arrays.array(
                new Task(99L, "1/1", "John"),
                new Task(100L, "2/23", "Ray"),
                new Task(101L, "4/20", "Jay")
        );
    }


    @Test
    public void taskSerializationTest() throws IOException {
        Task task = tasks[0];
        
        assertThat(json.write(task)).isStrictlyEqualToJson("single.json");
        assertThat(json.write(task)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(task)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(task)).hasJsonPathStringValue("@.time");
        assertThat(json.write(task)).extractingJsonPathStringValue("@.time")
             .isEqualTo("1/1");
    }


    @Test
    public void taskDeserializationTest() throws IOException{
        String expected = """
            {
                "id": 99,
                "time": "1/1",
                "owner": "John"
            }
                """;
        assertThat(json.parse(expected)).isEqualTo(new Task(99, "1/1", "John"));
        assertThat(json.parseObject(expected).id()).isEqualTo(99);
        assertThat(json.parseObject(expected).time()).isEqualTo("1/1");
        assertThat(json.parseObject(expected).owner()).isEqualTo("John");

    }
    

}


