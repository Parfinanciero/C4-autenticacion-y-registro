package com.riwi.Authentication.Exception;

import com.riwi.Authentication.Exception.model.ApiError;
import com.riwi.Authentication.Exception.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // agregar mas excepciones: controlar bien

    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(ApiException.class)

    public ResponseEntity<ApiError> handleApiException(ApiException exception) {

        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        // Crear una instancia de ErrorDetails utilizando el constructor y los setters
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setAttributeName("path");
        errorDetails.setReason("El id introducido no se encuentra en la base de datos");

        // Agregar el errorDetails a la lista
        errorDetailsList.add(errorDetails);

        // Crear una instancia de ApiError utilizando el constructor por defecto
        ApiError apiError = new ApiError();

        // Establecer los valores de los atributos utilizando los setters
        apiError.setMessage(exception.getMessage());
        apiError.setHttpStatus(HttpStatus.NOT_FOUND);
        apiError.setErrorDetailList(errorDetailsList);


        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {

            String attributeName = ((FieldError) error).getField();
            String reason = error.getDefaultMessage();
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setAttributeName(attributeName);
            errorDetails.setReason(reason);
            errorDetailsList.add(errorDetails);
        });

        ApiError apiError = new ApiError();
        apiError.setMessage("Invalid input provided. Please check the details and try again.");
        apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
        apiError.setErrorDetailList(errorDetailsList);

        return  new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
