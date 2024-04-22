package org.example.httpmultiplexingregular.controller;

import org.example.httpmultiplexingregular.service.ProviderService;
import org.example.model.WebResponseModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regularThreads")
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
