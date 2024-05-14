package com.odeniz.dev.orbit.util;

import com.odeniz.dev.orbit.View;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Map;

public class ApiResponse<T> implements Serializable {

    @JsonProperty
    @JsonView(View.Public.class)
    @Getter
    @Setter
    private T data;

    @JsonProperty
    @JsonView(View.Public.class)
    @Getter
    @Setter
    private Boolean success;


    @JsonProperty
    @JsonView(View.Public.class)
    @Getter
    @Setter
    private String message;

    @JsonProperty(value = "demo")
    @JsonView(View.Public.class)
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> demo;

    public static <T> ApiResponse<T> create(T value, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(value);
        return response;
    }

    public static <T> ApiResponse<T> create(T value) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(value);
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> error() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage("An Error Has Occurred");
        return response;
    }

    public static <T> ApiResponse<T> success(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> success() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Successful");
        return response;
    }


    public static <T> ResponseEntity<ApiResponse<T>> createdData(T value, String message){
        return new ResponseEntity<>(ApiResponse.create(value, message), HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<ApiResponse<T>> updateData(T value, String message){
        return new ResponseEntity<>(ApiResponse.create(value, message), HttpStatus.OK);
    }
}