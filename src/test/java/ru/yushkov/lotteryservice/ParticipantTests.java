package ru.yushkov.lotteryservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParticipantTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void createAndGetParticipants() throws Exception {
        this.mvc.perform(post("/lottery/participant")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "name": "Nikita",
                                    "age": 25,
                                    "city": "Saint-Petersburg"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.participantId").value(1))
                .andExpect(jsonPath("$.name").value("Nikita"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.city").value("Saint-Petersburg"));

        this.mvc.perform(post("/lottery/participant")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "name": "Ivan",
                                    "age": 50,
                                    "city": "Moscow"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.participantId").value(2))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.age").value(50))
                .andExpect(jsonPath("$.city").value("Moscow"));

        this.mvc.perform(get("/lottery/participant")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].participantId").value(1))
                .andExpect(jsonPath("$.[0].name").value("Nikita"))
                .andExpect(jsonPath("$.[0].age").value(25))
                .andExpect(jsonPath("$.[0].city").value("Saint-Petersburg"))
                .andExpect(jsonPath("$.[0].winningAmount").value(0))

                .andExpect(jsonPath("$.[1].participantId").value(2))
                .andExpect(jsonPath("$.[1].name").value("Ivan"))
                .andExpect(jsonPath("$.[1].age").value(50))
                .andExpect(jsonPath("$.[1].city").value("Moscow"))
                .andExpect(jsonPath("$.[1].winningAmount").value(0))

                .andExpect(jsonPath("$.[2]").doesNotExist());
    }
}