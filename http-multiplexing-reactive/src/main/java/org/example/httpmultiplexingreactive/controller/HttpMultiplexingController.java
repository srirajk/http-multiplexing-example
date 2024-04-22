package org.example.httpmultiplexingreactive.controller;

import org.example.httpmultiplexingreactive.service.ProviderService;
import org.example.model.WebResponseModel;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive")
public class HttpMultiplexingController {

    private final ProviderService providerService;

    public HttpMultiplexingController(final ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/request/{input}")
    public @ResponseBody Mono<WebResponseModel> generateRequest(@PathVariable(name = "input") final String input) {
        return providerService.execute(input);
    }



}
