package com.abhishekpanwar.logparser.models;

import lombok.Data;

@Data
public class LogLine {
    long timestamp;
    int status;
    String method;
    String url;
}
