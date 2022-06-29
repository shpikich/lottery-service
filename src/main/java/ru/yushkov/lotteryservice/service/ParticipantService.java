package ru.yushkov.lotteryservice.service;

import ru.yushkov.lotteryservice.entity.Participant;

import java.util.List;

public interface ParticipantService {
    void addParticipant(Participant participant);

    List<Participant> findAllParticipants();

    void saveAllParticipants(List<Participant> participants);

    void deleteAllParticipants();
}
