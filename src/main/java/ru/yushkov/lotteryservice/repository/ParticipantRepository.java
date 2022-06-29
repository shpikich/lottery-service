package ru.yushkov.lotteryservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yushkov.lotteryservice.entity.Participant;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
