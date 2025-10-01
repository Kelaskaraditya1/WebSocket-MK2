package com.starkIndustries.webSocket_Mk2.exception;

import ch.qos.logback.core.status.InfoStatus;
import com.starkIndustries.webSocket_Mk2.keys.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,Object> response = new LinkedHashMap<>();

        response.put(Keys.TIME_STAMP, Instant.now());
        response.put(Keys.STATUS,status);
        response.put(Keys.STATUS_CODE,status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getDefaultMessage())
                .collect(Collectors.toList());

        response.put(Keys.ERROR,errors);

        return new ResponseEntity<Object>(response,status);

    }
}
