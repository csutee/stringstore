package com.example.stringstore.controllers;

import com.example.stringstore.models.dtos.StringStoreResponseDTO;
import com.example.stringstore.models.exceptions.AlreadyExistsException;
import com.example.stringstore.models.exceptions.NotFoundException;
import com.example.stringstore.services.StringStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StringStoreController {

    private final StringStoreService stringStoreService;

    @PostMapping("/stringstore")
    public ResponseEntity<String> uploadString(@RequestBody final String string) {
        if (string.length() >= 5 && string.length() <= 15) {
            if (string.matches("[a-zA-Z]+")) {
                try {
                    Long addedStringIndex = stringStoreService.uploadString(string);
                    return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(addedStringIndex));
                } catch (AlreadyExistsException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Van már ilyen szöveg a tárban");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A megadott szöveg csak az angol abc betűi tartalmazhatja!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A megadott szöveg 5 és 15 közötti hosszúsággal kell rendelkezzen!");
        }
    }

    @GetMapping("/stringstore/{index}")
    public ResponseEntity<?> getString(@PathVariable final int index) {
        try {
            StringStoreResponseDTO resultString = stringStoreService.getString(index);
            return ResponseEntity.status(HttpStatus.OK).body(resultString);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hibás index");
        }

    }

}
