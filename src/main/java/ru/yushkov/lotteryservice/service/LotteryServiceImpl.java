package ru.yushkov.lotteryservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import ru.yushkov.lotteryservice.entity.Participant;
import ru.yushkov.lotteryservice.entity.Winner;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotteryServiceImpl implements LotteryService {
    private final ParticipantService participantService;
    private final WinnerService winnerService;
    private final TransactionTemplate transactionTemplate;
    private final static int MINIMUM_NUMBER_OF_PARTICIPANTS = 2;
    private final static int MINIMUM_WINNING_AMOUNT = 1;

    public LotteryServiceImpl(ParticipantService participantService, WinnerService winnerService, TransactionTemplate transactionTemplate) {
        this.participantService = participantService;
        this.winnerService = winnerService;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void runLottery() {
        List<Participant> participants = participantService.findAllParticipants();
        if (participants.size() < MINIMUM_NUMBER_OF_PARTICIPANTS) {
            throw new RuntimeException("There are not enough participants to start the lottery!");
        }
        List<Participant> newParticipants = participants.stream().map(
                participant ->
                        new Participant.Builder()
                                .withParticipantId(participant.getParticipantId())
                                .withName(participant.getName())
                                .withAge(participant.getAge())
                                .withCity(participant.getCity())
                                .withWinningAmount(calculateWinningAmount())
                                .build()
        ).collect(Collectors.toList());

        participantService.saveAllParticipants(newParticipants);
    }

    @Override
    public List<Participant> getCurrentLotteryWinners() {
        int highestWinningAmount = MINIMUM_WINNING_AMOUNT;
        List<Participant> potentialWinners = new ArrayList<>();

        for (Participant participant : participantService.findAllParticipants()) {
            int participantWinningAmount = participant.getWinningAmount();
            if (participantWinningAmount > highestWinningAmount) {
                potentialWinners.clear();
                potentialWinners.add(participant);
                highestWinningAmount = participantWinningAmount;
            } else if (participantWinningAmount == highestWinningAmount) {
                potentialWinners.add(participant);
            }
        }

        List<Winner> winners = potentialWinners.stream().map(
                participant -> new Winner.Builder()
                        .withName(participant.getName())
                        .withAge(participant.getAge())
                        .withCity(participant.getCity())
                        .withWinningAmount(participant.getWinningAmount())
                        .build()
        ).collect(Collectors.toList());

        transactionTemplate.executeWithoutResult(
                it -> {
                    participantService.deleteAllParticipants();
                    winnerService.saveAllWinners(winners);
                }
        );

        return potentialWinners;
    }

    @Override
    public List<Winner> getWinners() {
        return winnerService.findAllWinners();
    }

    private static int calculateWinningAmount() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.random.org/integers/?num=1&min=1&max=1000&col=1&base=10&format=plain"))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException exception) {
            throw new RuntimeException("Unable to calculate winning amount!", exception);
        }
        return Integer.parseInt(response.body().trim());

    }
}
