package com.imt.api_monstres.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Errors> handleValidationException(Exception ex) {
        Errors errors = new Errors(new ArrayList<>());
        CustomError customError = new CustomError(
                400,
                ex.getMessage()
        );
        errors.addError(customError);

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Errors errors = new Errors(new ArrayList<>());
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errors.addError(new CustomError(
                400,
                fieldError.getField() + " : " + fieldError.getDefaultMessage())));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({MonsterNotFoundException.class, SkillNotFoundException.class})
    public ResponseEntity<Errors> handleNotFoundException(RuntimeException ex) {
        Errors errors = new Errors(new ArrayList<>());
        errors.addError(new CustomError(404, ex.getMessage()));

        return ResponseEntity.status(404).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> handleUnexpectedException(Exception ex) {
        log.error("Erreur inattendue", ex);
        Errors errors = new Errors(new ArrayList<>());
        errors.addError(new CustomError(500, "Erreur interne du serveur"));

        return ResponseEntity.internalServerError().body(errors);
    }

}
