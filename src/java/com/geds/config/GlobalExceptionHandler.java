
package com.geds.config;

import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<map<String,Object>> handleValidation(MethodArgumentNotValidException ex){
        map<String,String> errores = ex.getBindingResult().getFieldErrors().stream()
              .collect(Collectors.toMap(
                    e -> e.getField(),
                    e -> e.getDefaultMessage() != null ? e.getDefaultMesaage() : "invalido",
                    (a, b) -> a));
                    return ResponseEntity.badRequest().body(map.of("errores", errores));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity,Map<String,String>> handleRutime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("mesaje", ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.Class)
    public ResponseEntity<Map<String,String>. handleAccess(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .doby(Map.of("mensaje", "Acceso denegado"));
    }

}