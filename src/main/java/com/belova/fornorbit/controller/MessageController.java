package com.belova.fornorbit.controller;

import com.belova.fornorbit.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("words")

public class MessageController {
    @Autowired
    DictionaryService dictionaryService;
    @Autowired
    public MessageController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
    @GetMapping
    public Long list() {
        return  dictionaryService.loading();
    }
    @PostMapping
    public String readMessage(@RequestBody String message) {
        dictionaryService.createConvertMessage(message);
        float rating = dictionaryService.rating();
        if(rating == 0) {
            return "Neutral";
        }
        else {
            if (rating > 0) {
                return "Positive";
            } else {
                return "Negative";
            }
        }
        }
}
