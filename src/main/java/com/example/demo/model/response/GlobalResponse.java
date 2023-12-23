package com.example.demo.model.response;

import com.example.demo.model.ApiResponseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalResponse<T> {

    private HttpStatus status;
    private String message;
    private ApiResponseStatus apiResponseStatus;
    private int responseCode;
    private T responseObject;

    public GlobalResponse(T data) {
        this.responseObject = data;
        this.status = HttpStatus.OK;
        this.message = "SUCCESS";
        this.apiResponseStatus = ApiResponseStatus.SUCCESS;
        this.responseCode = ApiResponseStatus.SUCCESS.getCode();
    }

}
