package com.pandabear.recom.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseData<T> extends Response {

    private T data;

    public ResponseData(HttpStatus status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
