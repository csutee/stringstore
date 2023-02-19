package com.example.stringstore.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StringStoreResponseDTO {

    private String storedElement;

    private String origElement;

}
