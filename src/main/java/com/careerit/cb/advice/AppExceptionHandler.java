package com.careerit.cb.advice;

import com.careerit.cb.exception.ContactAlreadyExistsException;
import com.careerit.cb.exception.ContactNotFoundException;
import com.careerit.cb.util.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({ContactNotFoundException.class,IllegalArgumentException.class})
  public ResponseEntity<AppResponse> handleNotFoundException(Throwable t){
    AppResponse appResponse = AppResponse.builder()
        .message(t.getMessage()).status(HttpStatus.BAD_REQUEST).date(LocalDateTime.now()).build();
    return  ResponseEntity.badRequest().body(appResponse);
  }
  @ExceptionHandler({ContactAlreadyExistsException.class})
  public ResponseEntity<AppResponse> handleAlreadyExistsException(Throwable t){
    AppResponse appResponse = AppResponse.builder()
        .message(t.getMessage()).status(HttpStatus.CONFLICT).date(LocalDateTime.now()).build();
    return  ResponseEntity.badRequest().body(appResponse);
  }
}
