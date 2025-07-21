package org.ludito.app.config.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.ludito.app.config.exceptions.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleUnknownExceptions(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new Message("Unknown error"));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Message> handleRuntimeExceptions(RuntimeException e, HttpServletRequest request) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new Message("Unknown error"));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Message> handleRuntimeExceptions(AccessDeniedException e, HttpServletRequest request) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new Message(e.getMessage()));

    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Message> handleRuntimeExceptions(CustomException e, HttpServletRequest request) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new Message(e.getMessage()));

    }


}
