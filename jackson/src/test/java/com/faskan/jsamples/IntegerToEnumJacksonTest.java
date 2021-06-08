package com.faskan.jsamples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegerToEnumJacksonTest {

    @Test
    @SneakyThrows
    void test() {
        JsonPayload jsonPayload = new ObjectMapper().readValue("{\"name\":\"Faisal\", \"status\":101}", JsonPayload.class);
        assertEquals("Faisal", jsonPayload.getName());
        assertEquals(Status.CODE_101, jsonPayload.getStatus());
    }
}

@Data
class JsonPayload {
    private String name;
    private Status status;
}
enum Status {
//    @JsonProperty("101") // works only when status is a string
    CODE_101(101, "OK"),
    CODE_102(102, "NOK"),
    UNKNOWN(-1, "UNKNOWN");

    private int code;
    private String value;

    Status(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @JsonCreator
    public static Status fromCode(Integer code){
        return stream(values()).filter(e -> e.code == code)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
