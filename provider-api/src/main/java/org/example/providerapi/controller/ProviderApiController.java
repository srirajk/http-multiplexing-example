package org.example.providerapi.controller;


import lombok.extern.slf4j.Slf4j;

import org.example.model.SampleRequest;
import org.example.model.SampleResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/provider")
@Slf4j
public class ProviderApiController {

    @PostMapping("/generateData")
    public Mono<SampleResponse> generateRequest(@RequestBody final SampleRequest sampleRequest) {
        log.info("Received request: {}", sampleRequest);
        return Mono.just(new SampleResponse(sampleRequest.getResponseField1() + " -- "+ UUID.randomUUID() + " -- responseField1", 1)).delayElement(Duration.ofMillis(280));
    }

}
