package ru.yushkov.lotteryservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yushkov.lotteryservice.entity.Winner;

public interface WinnerRepository extends CrudRepository<Winner, Long> {
}
