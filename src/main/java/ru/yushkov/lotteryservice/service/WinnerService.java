package ru.yushkov.lotteryservice.service;

import ru.yushkov.lotteryservice.entity.Winner;

import java.util.List;

public interface WinnerService {
    List<Winner> findAllWinners();

    void saveAllWinners(List<Winner> winners);
}
