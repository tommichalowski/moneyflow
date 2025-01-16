package com.nn.moneyflow.controller.errorhandling;

import com.nn.moneyflow.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public ResponseEntity<Object> handleError(HttpServletRequest request) {

        Object status = request.getAttribute("javax.servlet.error.status_code");
        HttpStatus httpStatus = status != null ? HttpStatus.valueOf(Integer.parseInt(status.toString())) : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseBuilder.build(httpStatus, "An unexpected error occurred", request.getRequestURI());
    }
}
