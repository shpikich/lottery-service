package ru.yushkov.lotteryservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;
    @NotBlank
    private String name;
    @Positive
    private int age;
    @NotBlank
    private String city;
    private int winningAmount;

    private Participant(Long participantId, String name, int age, String city, int winningAmount) {
        this.participantId = participantId;
        this.name = Objects.requireNonNull(name);
        this.age = age;
        this.city = Objects.requireNonNull(city);
        this.winningAmount = winningAmount;
    }

    public Participant() {

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

    public int getWinningAmount() {
        return winningAmount;
    }

    public static class Builder {
        private Long participantId;
        private String name;
        private int age;
        private String city;
        private int winningAmount = 0;

        public Builder withParticipantId(Long participantId) {
            this.participantId = participantId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withWinningAmount(int winningAmount) {
            this.winningAmount = winningAmount;
            return this;
        }

        public Participant build() {
            return new Participant(participantId, name, age, city, winningAmount);
        }
    }

    @Override
    public String toString() {
        return "Participant{" +
                "participantId=" + participantId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", winningAmount=" + winningAmount +
                '}';
    }
}
