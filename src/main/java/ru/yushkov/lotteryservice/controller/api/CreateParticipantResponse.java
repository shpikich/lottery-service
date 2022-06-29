package ru.yushkov.lotteryservice.controller.api;

import ru.yushkov.lotteryservice.entity.Participant;

import java.util.Objects;

public class CreateParticipantResponse {
    private final Long participantId;
    private final String name;
    private final int age;
    private final String city;

    private CreateParticipantResponse(Long participantId, String name, int age, String city) {
        this.participantId = Objects.requireNonNull(participantId);
        this.name = Objects.requireNonNull(name);
        this.age = age;
        this.city = Objects.requireNonNull(city);
    }

    public static CreateParticipantResponse of(Participant participant) {
        Objects.requireNonNull(participant);
        return new CreateParticipantResponse(participant.getParticipantId(),
                participant.getName(),
                participant.getAge(),
                participant.getCity());
    }

    public Long getParticipantId() {
        return participantId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "CreateParticipantResponse{" +
                "participantId=" + participantId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}
