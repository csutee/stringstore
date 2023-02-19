package com.example.stringstore;

import com.example.stringstore.controllers.StringStoreController;
import com.example.stringstore.repositories.StringStoreRepository;
import com.example.stringstore.services.StringStoreService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {StringStoreController.class, StringStoreService.class, StringStoreRepository.class})
@AutoConfigureMockMvc
public class StringStoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StringStoreService stringStoreService;

    @Autowired
    StringStoreRepository stringStoreRepository;

    @Test
    public void stringStorePostWorksCorrectly() throws Exception {

        mockMvc.perform(post("/api/stringstore").content("tesztstring"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

    }

    @Test
    public void stringStorePostNotCorrectString() throws Exception {
        mockMvc.perform(post("/api/stringstore").content("1234567"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("A megadott szöveg csak az angol abc bet?i tartalmazhatja!"));
    }

    @Test
    public void stringStorePostNotCorrectStringLength() throws Exception {
        mockMvc.perform(post("/api/stringstore").content("asd"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("A megadott szöveg 5 és 15 közötti hosszúsággal kell rendelkezzen!"));
    }

    @Test
    public void stringStorePostCantAddDuplicate() throws Exception {
        stringStoreRepository.uploadString("tesztszoveg");

        mockMvc.perform(post("/api/stringstore").content("tesztszoveg"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Van már ilyen szöveg a tárban"));
    }

    @Test
    public void stringStoreGetEmptyList() throws Exception {

        mockMvc.perform(get("/api/stringstore/0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Hibás index"));
    }

    //TODO: Implement other tests

}
