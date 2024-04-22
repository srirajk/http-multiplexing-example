package org.example.httpmultiplexingvirtualthreads.controller;

import org.example.httpmultiplexingvirtualthreads.service.ProviderService;
import org.example.model.WebResponseModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/virtualThreads")
public class HttpMultiplexingController {

    private final ProviderService providerService;

    public HttpMultiplexingController(final ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/request/{input}")
    public @ResponseBody WebResponseModel generateRequest(@PathVariable(name = "input") final String input) {
        return providerService.execute(input);
    }



}
