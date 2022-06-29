package ru.yushkov.lotteryservice.service;

import ru.yushkov.lotteryservice.entity.Participant;
import ru.yushkov.lotteryservice.entity.Winner;

import java.util.List;

public interface LotteryService {
    void runLottery();

    List<Participant> getCurrentLotteryWinners();

    List<Winner> getWinners();
}
