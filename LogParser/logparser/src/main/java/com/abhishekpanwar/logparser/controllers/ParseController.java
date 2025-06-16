package com.abhishekpanwar.logparser.controllers;

import com.abhishekpanwar.logparser.services.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ParseController {

    @Autowired
    ParseService parseService;

    @GetMapping("/filter")
    public String getFilteredLogs(@RequestParam Map<String, String> allParams, @RequestParam String fileName){
        return parseService.filter(fileName, allParams);
    }

}
