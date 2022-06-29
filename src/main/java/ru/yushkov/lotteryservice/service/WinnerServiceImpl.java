package ru.yushkov.lotteryservice.service;

import org.springframework.stereotype.Service;
import ru.yushkov.lotteryservice.entity.Winner;
import ru.yushkov.lotteryservice.repository.WinnerRepository;

import java.util.List;

@Service
public class WinnerServiceImpl implements WinnerService {
    private final WinnerRepository winnerRepository;

    public WinnerServiceImpl(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    @Override
    public List<Winner> findAllWinners() {
        return (List<Winner>) winnerRepository.findAll();
    }

    @Override
    public void saveAllWinners(List<Winner> winners) {
        winnerRepository.saveAll(winners);
    }
}
