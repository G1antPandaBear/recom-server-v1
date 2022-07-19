package com.pandabear.recom.global.exception.ro;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExceptionRO {

    @JsonProperty(value = "timestamp", index = 0)
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String message;

}
