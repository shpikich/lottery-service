package ru.yushkov.lotteryservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LotteryTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void successfulLottery() throws Exception {
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
                .andExpect(status().isCreated());

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
                .andExpect(status().isCreated());

        this.mvc.perform(get("/lottery/start")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].participantId").exists())
                .andExpect(jsonPath("$.[0].name").exists())
                .andExpect(jsonPath("$.[0].age").exists())
                .andExpect(jsonPath("$.[0].city").exists())
                .andExpect(jsonPath("$.[0].winningAmount").exists())

                .andExpect(jsonPath("$.[1]").doesNotExist());
    }

    @Test
    void lotteryNotAvailable() throws Exception {
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
                .andExpect(status().isCreated());

        this.mvc.perform(get("/lottery/start")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof RuntimeException))
                .andExpect(result -> Assertions.assertEquals("There are not enough participants to start the lottery!",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void getLotteryWinners() throws Exception {
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
                .andExpect(status().isCreated());

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
                .andExpect(status().isCreated());

        this.mvc.perform(get("/lottery/start")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        this.mvc.perform(get("/lottery/participant")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        // Возможна ситуация, когда победителей будет несколько
        this.mvc.perform(get("/lottery/winners")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].winnerId").value(1))
                .andExpect(jsonPath("$.[0].name").exists())
                .andExpect(jsonPath("$.[0].age").exists())
                .andExpect(jsonPath("$.[0].city").exists())
                .andExpect(jsonPath("$.[0].winningAmount").exists());
    }
}