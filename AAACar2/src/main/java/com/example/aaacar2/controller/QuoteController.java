package com.example.aaacar2.controller;

import com.example.aaacar2.dto.QuoteRequest;
import com.example.aaacar2.dto.QuoteResponse;
import com.example.aaacar2.service.QuoteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(
            QuoteService quoteService
    ) {
        this.quoteService = quoteService;
    }

    @PostMapping
    public QuoteResponse quote(
            @RequestBody QuoteRequest request
    ) {

        return quoteService.quote(
                request
        );
    }
}