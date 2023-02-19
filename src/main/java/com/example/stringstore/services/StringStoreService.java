package com.example.stringstore.services;

import com.example.stringstore.models.dtos.StringStoreResponseDTO;
import com.example.stringstore.repositories.StringStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StringStoreService {

    private final StringStoreRepository stringStoreRepository;

    public Long uploadString(final String string) {
        return stringStoreRepository.uploadString(string);
    }

    public StringStoreResponseDTO getString(final int index) {
        return stringStoreRepository.getString(index);
    }

}
