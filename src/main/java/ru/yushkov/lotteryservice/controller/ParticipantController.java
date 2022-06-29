package ru.yushkov.lotteryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yushkov.lotteryservice.controller.api.CreateParticipantRequest;
import ru.yushkov.lotteryservice.controller.api.CreateParticipantResponse;
import ru.yushkov.lotteryservice.entity.Participant;
import ru.yushkov.lotteryservice.service.ParticipantService;

import java.util.List;

@RestController
@RequestMapping(value = "/lottery/participant", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParticipantController {
    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateParticipantResponse> createParticipant(@RequestBody CreateParticipantRequest request) {
        Participant participant = new Participant.Builder()
                .withName(request.getName())
                .withAge(request.getAge())
                .withCity(request.getCity())
                .build();
        participantService.addParticipant(participant);
        return new ResponseEntity<>(CreateParticipantResponse.of(participant), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Participant>> getParticipants() {
        return new ResponseEntity<>(participantService.findAllParticipants(), HttpStatus.OK);
    }
}
