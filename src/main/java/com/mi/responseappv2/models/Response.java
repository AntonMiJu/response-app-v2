package com.mi.responseappv2.models;

import lombok.Data;

@Data
public class Response {
    private String name;
    private String phone;
    private String response;
    private GasStation gasStation;
}
