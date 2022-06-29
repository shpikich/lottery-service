package ru.yushkov.lotteryservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long winnerId;
    private String name;
    private int age;
    private String city;
    private int winningAmount;

    public Winner(Long winnerId, String name, int age, String city, int winningAmount) {
        this.winnerId = winnerId;
        this.name = Objects.requireNonNull(name);
        this.age = age;
        this.city = Objects.requireNonNull(city);
        this.winningAmount = Objects.requireNonNull(winningAmount);
    }

    public Winner() {

    }

    public Long getWinnerId() {
        return winnerId;
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
        private Long winnerId;
        private String name;
        private int age;
        private String city;
        private int winningAmount = 0;

        public Builder withWinnerId(Long winnerId) {
            this.winnerId = winnerId;
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

        public Winner build() {
            return new Winner(winnerId, name, age, city, winningAmount);
        }
    }

    @Override
    public String toString() {
        return "Winner{" +
                "winnerId=" + winnerId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", winningAmount=" + winningAmount +
                '}';
    }
}
