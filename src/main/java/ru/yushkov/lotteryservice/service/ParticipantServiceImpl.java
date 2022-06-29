package ru.yushkov.lotteryservice.service;

import org.springframework.stereotype.Service;
import ru.yushkov.lotteryservice.entity.Participant;
import ru.yushkov.lotteryservice.repository.ParticipantRepository;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantServiceImpl(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public void addParticipant(Participant participant) {
        participantRepository.save(participant);
    }

    @Override
    public List<Participant> findAllParticipants() {
        return (List<Participant>) participantRepository.findAll();
    }

    @Override
    public void saveAllParticipants(List<Participant> participants) {
        participantRepository.saveAll(participants);
    }

    @Override
    public void deleteAllParticipants() {
        participantRepository.deleteAll();
    }
}
