package com.example.stringstore.repositories;

import com.example.stringstore.models.dtos.StringStoreResponseDTO;
import com.example.stringstore.models.exceptions.AlreadyExistsException;
import com.example.stringstore.models.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StringStoreRepository {

    private List<String> storedStrings = new ArrayList<>();

    public Long uploadString(final String string) throws AlreadyExistsException {
        String reversedString = new StringBuilder(string).reverse().toString();

        if (storedStrings.contains(reversedString)) {
            throw new AlreadyExistsException("A megadott szöveg már létezik.");
        } else {
            storedStrings.add(reversedString);
            storedStrings.stream().sorted().collect(Collectors.toList());
            return Long.valueOf(storedStrings.indexOf(reversedString));
        }
    }

    public StringStoreResponseDTO getString(final int index) {
        if (storedStrings.size() <= index) {
            throw new NotFoundException("Nem található a megadott indexel érték!");
        } else {
            String foundString = storedStrings.get(index);
            return new StringStoreResponseDTO(foundString, new StringBuilder(foundString).reverse().toString());
        }
    }

}
