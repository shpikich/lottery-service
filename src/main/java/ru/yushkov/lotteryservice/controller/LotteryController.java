package ru.yushkov.lotteryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yushkov.lotteryservice.entity.Participant;
import ru.yushkov.lotteryservice.entity.Winner;
import ru.yushkov.lotteryservice.service.LotteryService;

import java.util.List;

@RestController
@RequestMapping(value = "/lottery", produces = MediaType.APPLICATION_JSON_VALUE)
public class LotteryController {
    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping(value = "/start")
    public ResponseEntity<List<Participant>> startLottery() {
        lotteryService.runLottery();
        return new ResponseEntity<>(lotteryService.getCurrentLotteryWinners(), HttpStatus.OK);
    }

    @GetMapping(value = "/winners")
    public ResponseEntity<List<Winner>> getLotteryWinners() {
        return new ResponseEntity<>(lotteryService.getWinners(), HttpStatus.OK);
    }

}
