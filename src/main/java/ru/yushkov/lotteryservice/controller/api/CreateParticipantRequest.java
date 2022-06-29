package ru.yushkov.lotteryservice.controller.api;

import java.util.Objects;

public class CreateParticipantRequest {
    private final String name;
    private final int age;
    private final String city;

    private CreateParticipantRequest(String name, int age, String city) {
        this.name = Objects.requireNonNull(name, "Please provide your first name");
        this.age = age;
        this.city = Objects.requireNonNull(city, "Please provide your city");
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
        return "CreateParticipantRequest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}
