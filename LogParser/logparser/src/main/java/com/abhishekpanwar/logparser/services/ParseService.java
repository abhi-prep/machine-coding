package com.abhishekpanwar.logparser.services;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ParseService {

    public String filter(String fileName, Map<String, String> filterCriteria){
        return filterCriteria.toString();
    }
}
